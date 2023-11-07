package com.example.roomradar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addPostFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addPostFragment newInstance(String param1, String param2) {
        addPostFragment fragment = new addPostFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        RadioGroup radioGroup = view.findViewById(R.id.add_category);
        EditText edt_title = view.findViewById(R.id.add_title);
        EditText edt_description = view.findViewById(R.id.add_description);
        EditText edt_address = view.findViewById(R.id.add_address);
        EditText edt_area = view.findViewById(R.id.add_area);
        EditText edt_maxPeople = view.findViewById(R.id.add_maxpeople);
        EditText edt_price = view.findViewById(R.id.add_price);
        EditText edt_deposit = view.findViewById(R.id.add_desposit);
        CheckBox ckb_security_gated = view.findViewById(R.id.add_security_gated);
        CheckBox ckb_security_alarm = view.findViewById(R.id.add_security_alarm);
        EditText edt_images = view.findViewById(R.id.add_images);

        // Inflate the layout for this fragment

        return view;
    }
}