package com.example.roomradar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.roomradar.Activity.AddPostActivity;
import com.example.roomradar.Database.entity.Category;
import com.example.roomradar.Database.entity.Post;
import com.example.roomradar.Database.entity.Security;
import com.example.roomradar.Database.entity.Utils;
import com.example.roomradar.R;
import com.example.roomradar.adapter.CategoryRadioAdapter;
import com.example.roomradar.adapter.SecurityCheckBoxAdapter;
import com.example.roomradar.adapter.UtilsCheckboxAdapter;
import com.example.roomradar.service.CategoryService;
import com.example.roomradar.service.PostService;
import com.example.roomradar.service.SecurityService;
import com.example.roomradar.service.UtilsService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Addpostfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Addpostfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Addpostfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Addpostfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Addpostfragment newInstance(String param1, String param2) {
        Addpostfragment fragment = new Addpostfragment();
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

        View view = inflater.inflate(R.layout.fragment_addpostfragment, container, false);
//
        CategoryService categoryService = CategoryService.getInstance(getActivity().getApplicationContext());

        UtilsService utilService = UtilsService.getInstance(getActivity().getApplicationContext());
        SecurityService securityService = SecurityService.getInstance(getActivity().getApplicationContext());
        categoryService.insertCategory(new Category("Nhà Nguyên Căn"));
        utilService.addnewUtils(new Utils("Tu lanh"));
        securityService.addNewSecurity(new Security("PCCC"));

        List<Category> categoryList = categoryService.getAllCategory();
        List<Utils> utilsList = utilService.getAllUtils();
        List<Security> securityList = securityService.getAllSecurity();

        ListView lv_category = view.findViewById(R.id.addPost_lv_category);
        ListView lv_security = view.findViewById(R.id.addPost_lv_security);
        ListView lv_utils = view.findViewById(R.id.addPost_lv_utils);

        EditText edt_title = view.findViewById(R.id.add_title);
        EditText edt_description = view.findViewById(R.id.add_description);
        EditText edt_address = view.findViewById(R.id.add_address);
        EditText edt_area = view.findViewById(R.id.add_area);
        EditText edt_maxPeople = view.findViewById(R.id.add_maxpeople);
        EditText edt_price = view.findViewById(R.id.add_price);
        EditText edt_deposit = view.findViewById(R.id.add_desposit);
//
//
        CategoryRadioAdapter categoryRadioAdapter = new CategoryRadioAdapter(getActivity(),R.layout.item_check_box,categoryList);
        lv_category.setAdapter(categoryRadioAdapter);
        SecurityCheckBoxAdapter securityCheckBoxAdapter = new SecurityCheckBoxAdapter(getActivity(),R.layout.item_check_box,securityList);
        lv_security.setAdapter(securityCheckBoxAdapter);


        UtilsCheckboxAdapter utilsCheckboxAdapter = new UtilsCheckboxAdapter(getActivity(),R.layout.item_check_box,utilsList);
        lv_utils.setAdapter(utilsCheckboxAdapter);


        view.findViewById(R.id.add_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> securityId = securityCheckBoxAdapter.getSecurityId();
                List<Integer> utils = utilsCheckboxAdapter.getUtilsId();
                int categoryId = categoryRadioAdapter.getCategoryId();

                String title = edt_title.getText().toString();
                String description = edt_description.getText().toString();
                String address =  edt_address.getText().toString();
                int area =  Integer.parseInt(edt_area.getText().toString()) ;
                int maxPeople =  Integer.parseInt(edt_maxPeople.getText().toString());
                float price =  Float.valueOf(edt_price.getText().toString());
                float deposit =  Float.valueOf(edt_deposit.getText().toString());
                Date currentTime = new Date();

                // Define a format for the time as a string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Format the current time as a string
                String currentTimeString = dateFormat.format(currentTime);
                Post post = new Post(title,description,address,area,maxPeople,price,deposit,"owner",currentTimeString,categoryId,
                        "https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:80/plain/https://cellphones.com.vn/media/catalog/product/1/_/1_251_1.jpg",
                        securityId.toString(),utils.toString());
                PostService postService = PostService.getInstance(getContext());
                postService.addNewPost(post);
            }
        });

        return view;
    }

}