package com.example.solist.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solist.Adapter.ListAdapter;
import com.example.solist.Decorator.OneDayDecorator;
import com.example.solist.Decorator.SaturdayDecorator;
import com.example.solist.Decorator.SundayDecorator;
import com.example.solist.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import io.realm.Realm;


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
    private Realm realm;

    private MaterialCalendarView materialCalendarView;
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private SaturdayDecorator saturdayDecorator = new SaturdayDecorator();
    private SundayDecorator sundayDecorator = new SundayDecorator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_list, container, false);

        // calendar view set
        setCalendarView();

        realm = Realm.getDefaultInstance();

        // recycler view set
        RecyclerView recyclerView = layout.findViewById(R.id.list_recycler_view);

        // LayoutManager 를 통해서 LinearLayout 의 VERTICAL 로 정해준다.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        // RecyclerView 를 Adapter 생성 후 연결해준다.
        ListAdapter adapter = new ListAdapter();
        recyclerView.setAdapter(adapter);

        return layout;
    }

    private void setCalendarView() {
        Log.d(TAG, "onCreateView: CALENDAR VIEW START");

        materialCalendarView = layout.findViewById(R.id.calendarView);
        materialCalendarView.clearSelection();
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1999, 0, 1))
                .setMaximumDate(CalendarDay.from(2999, 11, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        materialCalendarView.addDecorator(oneDayDecorator);
        // 일요일 토요일 색 넣기
        //materialCalendarView.addDecorators(oneDayDecorator, sundayDecorator, saturdayDecorator);
        // 오늘 날짜를 선택한 상태로 시작
        materialCalendarView.setSelectedDate(CalendarDay.today());
        // 날짜 클릭 이벤트
        materialCalendarView.setOnDateChangedListener((materialCalendarView1, date, b) -> {
            int Year = date.getYear();
            int Month = date.getMonth() + 1;
            int Day = date.getDay();

            String selectedDate;
            if(Day < 10) {
                selectedDate = Year + "-" + Month + "-0" + Day;
            } else {
                selectedDate = Year + "-" + Month + "-" + Day;
            }

            // 선택 초기화
            //materialCalendarView.clearSelection();

            Toast.makeText(getContext(), selectedDate, Toast.LENGTH_SHORT).show();
        });

        Log.d(TAG, "onCreateView: CALENDAR VIEW END");
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
