package com.example.roomradar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.roomradar.api.APIService;
import com.example.roomradar.model.AddPostData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFrament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFrament extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFrament newInstance(String param1, String param2) {
        UserFrament fragment = new UserFrament();
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
        View view =  inflater.inflate(R.layout.fragment_user_frament, container, false);
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

        view.findViewById(R.id.add_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_select = radioGroup.getCheckedRadioButtonId();
                RadioButton category_selected = view.findViewById(id_select);
                String category = category_selected.getText().toString();

                String title = edt_title.getText().toString();
                String description = edt_description.getText().toString();
                String address =  edt_address.getText().toString();
                int area =  Integer.parseInt(edt_area.getText().toString()) ;
                int maxPeople =  Integer.parseInt(edt_maxPeople.getText().toString());
                String price =  edt_price.getText().toString();
                String deposit =  edt_deposit.getText().toString();
                String images = edt_images.getText().toString();

                ArrayList<String> security = new ArrayList<>();
                if(ckb_security_alarm.isChecked()){
                    security.add(ckb_security_alarm.getText().toString());
                }
                if (ckb_security_gated.isChecked()){
                    security.add(ckb_security_gated.getText().toString());
                }
                String imagesArray[] = {images};

                LocalDateTime createdAt = LocalDateTime.now();

                AddPostData newPost = new AddPostData(title,description,address,area,maxPeople,price,deposit,security,security,security,imagesArray,"653551a9974d61e79fc642a4");
                Log.d("New Post",newPost.toString());
                Call<AddPostData> call = APIService.apiservice.addNewPost(newPost);
                call.enqueue(new Callback<AddPostData>() {
                    @Override
                    public void onResponse(Call<AddPostData> call, Response<AddPostData> response) {
                        if (response.isSuccessful()){
                            Log.d("api","oke");
                        }
                        else{
                            try {
                                Log.d("api","false" + response.errorBody().string());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddPostData> call, Throwable t) {
                        Log.d("api","Error call");
                    }
                });




            }
        });
        return view;
    }
}