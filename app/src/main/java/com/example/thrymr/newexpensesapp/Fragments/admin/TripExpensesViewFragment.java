package com.example.thrymr.newexpensesapp.Fragments.admin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thrymr.newexpensesapp.Activity.admin.TripExpensesActivity;
import com.example.thrymr.newexpensesapp.Adapter.admin.AdminTripViewAdapter;
import com.example.thrymr.newexpensesapp.R;
import com.example.thrymr.newexpensesapp.listners.admin.AdminTripItemClickListner;
import com.example.thrymr.newexpensesapp.models.AdminTrip;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TripExpensesViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TripExpensesViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripExpensesViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView tripViewRecycle;

    public TripExpensesViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripExpensesViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripExpensesViewFragment newInstance(String param1, String param2) {
        TripExpensesViewFragment fragment = new TripExpensesViewFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_trip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripViewRecycle = (RecyclerView) view.findViewById(R.id.rcv_admin_trip);
        setValues();
    }

    private void setValues() {

        tripViewRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ArrayList<AdminTrip> adminTripArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AdminTrip adminTrip = new AdminTrip();
            adminTrip.setEmpName("Shrikant Tripathi");
            adminTrip.setEmpId("#EMPID-139");
            adminTrip.setTripName("Singpore");
            adminTrip.setTripDate("12-07-2017");
            adminTrip.setTotalAmount("$500");
            adminTrip.setTripStatus("Pending");
            adminTripArrayList.add(adminTrip);

        }
        tripViewRecycle.setAdapter(new AdminTripViewAdapter(getActivity(), adminTripArrayList, new AdminTripItemClickListner() {
            @Override
            public void itemOnClickListner(AdminTrip adminTrip) {
                startActivity(new Intent(getActivity(), TripExpensesActivity.class));
            }
        }));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String shri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(shri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        void onFragmentInteraction(String shri);
    }
}
