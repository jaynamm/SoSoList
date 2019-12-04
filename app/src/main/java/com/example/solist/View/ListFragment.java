package com.example.solist.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solist.Adapter.ListAdapter;
import com.example.solist.Decorator.OneDayDecorator;
import com.example.solist.Util.EditCustomDialog;
import com.example.solist.R;
import com.example.solist.Database.ListVO;
import com.example.solist.Util.ClearEditText;
import com.example.solist.ViewModel.ListViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ListFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private LinearLayout layout;

    private MaterialCalendarView materialCalendarView;
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    //private SaturdayDecorator saturdayDecorator = new SaturdayDecorator();
    //private SundayDecorator sundayDecorator = new SundayDecorator();

    private DividerItemDecoration dividerItemDecoration;

    private ListViewModel listViewModel;

    private String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_list, container, false);

        // calendar view set
        setCalendarView();

        // recycler view set
        RecyclerView recyclerView = layout.findViewById(R.id.list_recycler_view);

        // LayoutManager 를 통해서 LinearLayout 의 VERTICAL 로 정해준다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        // RecyclerView 를 Adapter 생성 후 연결해준다.
        ListAdapter adapter = new ListAdapter(getContext());
        // 갱신할 때 화면 깜빡임 없애기
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        // listViewModel 을 가져온다.
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        // LiveData 를 통해서 자동으로 리스트를 갱신시켜준다.
        // 모든 리스트 가져오는 observe()
        //listViewModel.getAllLists().observe(this, listVOS -> adapter.setLists(listVOS) );

        // 현재 날짜 세팅
        selectedDate = getInputDate();
        // 현재 날짜를 넣어준다. 초기에 현재 날짜로 리스트 검색
        listViewModel.setDate(selectedDate);
        listViewModel.getAllListsForDate().observe(this, listVOS -> {
            Log.d(TAG, "onCreateView: " + listVOS);
            adapter.setLists(listVOS);
        });

        // 왼쪽이나 오른쪽으로 슬라이드를 하게되면 삭제가 된다.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                listViewModel.delete(adapter.getListAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "list deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        // List 를 입력하는 부분
        ClearEditText inputEditText = (ClearEditText) layout.findViewById(R.id.input_editText);
        ImageButton addListButton = (ImageButton) layout.findViewById(R.id.input_button);
        addListButton.setOnClickListener(v -> {
            int status = 1;
            ListVO listVO = new ListVO(inputEditText.getText().toString(), status, getInputDate());
            listViewModel.insert(listVO);
            inputEditText.setText("");
            inputEditText.clearFocus();

            onHideKeyboard(getContext(), inputEditText);
        });

        adapter.setOnStatusClickListener(new ListAdapter.ListAdapterClickListener() {
            // 아이템 클릭하면 수정하는 Dialog 가 나오게 된다.
            @Override
            public void onItemClick(ListVO listVO) {
                EditCustomDialog dialog = new EditCustomDialog(getContext());
                dialog.callFunction(listVO, listViewModel);
            }
            // 리스너를 통해서 status 를 클릭하면 변경되게 한다. 0 = 완료 , 1 = 진행중 , 2 = 보류
            @Override
            public void onStatusClick(ListVO listVO) {
                // 현재 상태 값을 가져온다.
                int getStat = listVO.getStatus();
                // 0 1 2 0 1 2  숫자를 증가시켜 반복되게 만들어준다.
                getStat++;
                // 0~2 의 숫자가 오기 때문에 2보다 크면 0으로 초기화 시켜준다.
                if(getStat > 2) getStat = 0;

                Toast.makeText(getContext(), "change status : " + getStat, Toast.LENGTH_SHORT).show();

                // 다시 넣은 후 update 해준다.
                listVO.setStatus(getStat);
                listViewModel.update(listVO);
            }
        });
        
        // layout 을 클릭하면 editText 창이 사라진다.
        layout.setOnClickListener(v -> { onHideKeyboard(getContext(), inputEditText); });

        return layout;
    }

    // 입력창에서 밖을 클릭했을 때 키보드 사라지게 만들기
    public void onHideKeyboard(Context context, EditText editText) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setCalendarView() {
        Log.d(TAG, "onCreateView: CALENDAR VIEW START");

        materialCalendarView = layout.findViewById(R.id.calendarView);
        //materialCalendarView.clearSelection();
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1999, 0, 1))
                .setMaximumDate(CalendarDay.from(2999, 11, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        // 오늘 날짜 굵은 글씨체로 표시하기
        materialCalendarView.addDecorator(oneDayDecorator);
        // 일요일 토요일 색 넣기
        //materialCalendarView.addDecorators(oneDayDecorator, sundayDecorator, saturdayDecorator);
        // 오늘 날짜를 선택한 상태로 시작
        materialCalendarView.setSelectedDate(CalendarDay.today());
        // 날짜 클릭 이벤트

        materialCalendarView.setOnDateChangedListener((MaterialCalendarView materialCalendarView, CalendarDay date, boolean b) -> {
            int Year = date.getYear();
            int Month = date.getMonth() + 1;
            int Day = date.getDay();

            //String selectedDate;
            selectedDate = Year + "-" + Month + "-" + Day;
            listViewModel.setDate(selectedDate);
            Toast.makeText(getContext(), selectedDate, Toast.LENGTH_SHORT).show();
        });

        Log.d(TAG, "onCreateView: CALENDAR VIEW END");
    }

    // DB 에 들어갈 날짜 가져오기
    private String getInputDate() {
        //날짜 가져오기
        Calendar cal = new GregorianCalendar();
        String Today;

        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DATE);

        Today = year + "-" + (month+1) + "-" + day;

        Log.d(TAG, "getInputDate: " + Today);

        return Today;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
