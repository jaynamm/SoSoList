package com.example.solist.View;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solist.Adapter.HomeAdapter;
import com.example.solist.MainActivity;
import com.example.solist.R;
import com.example.solist.ViewModel.ListViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HomeFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private RelativeLayout layout;
    private ListViewModel listViewModel;
    private DividerItemDecoration dividerItemDecoration;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_home, container, false);

        TextView getDataView = (TextView) layout.findViewById(R.id.get_data_view);
        getDataView.setMovementMethod(new ScrollingMovementMethod());

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.getAllLists().observe(this, listVOS -> getDataView.setText(listVOS.toString()));

        // recycler view set
        RecyclerView recyclerView = layout.findViewById(R.id.list_recycler_view_home);

        // LayoutManager 를 통해서 LinearLayout 의 VERTICAL 로 정해준다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        // RecyclerView 를 Adapter 생성 후 연결해준다.
        HomeAdapter adapter = new HomeAdapter(getContext());
        // 갱신할 때 화면 깜빡임 없애기
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        // listViewModel 을 가져온다.
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        // LiveData 를 통해서 자동으로 리스트를 갱신시켜준다.
        listViewModel.getUnfinishedData().observe(this, listVOS -> adapter.setLists(listVOS));

        ImageButton moveListFragment = (ImageButton) layout.findViewById(R.id.move_fragment_button);
        moveListFragment.setOnClickListener(v -> {
            // 버튼을 누르면 viewpager 에서 item 을 가져와서 보여준다.
            ((MainActivity) getActivity()).mViewPager.setCurrentItem(1);
        });

        // Admob 광고 추가
        MobileAds.initialize(getContext(), initializationStatus -> {
        });

        mAdView = layout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return layout;
    }

    // DB 에 들어갈 날짜 가져오기
    private String getInputDate() {
        //날짜 가져오기
        Calendar cal = new GregorianCalendar();
        String Today;

        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DATE);

        Today = year + "-" + (month + 1) + "-" + day;

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
