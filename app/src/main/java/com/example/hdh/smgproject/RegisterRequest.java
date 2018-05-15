package com.example.hdh.smgproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/UserRegister_SYG.php";
    private Map<String , String> parameters;

    public RegisterRequest(String userID , String userPassword, String userName, String userEmail, String userGender, String userHeight, String userWeight, String userAge , String userValue ,  String userPT ,Response.Listener<String> listener){
        super(Method.POST , URL , listener , null); //해당 URL의 parameters들을 POST방식으로 해당 요청을 숨겨서 보여주어라
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
        parameters.put("userPassword" , userPassword);
        parameters.put("userName" , userName);
        parameters.put("userEmail" , userEmail);
        parameters.put("userGender" , userGender);
        parameters.put("userHeight" , userHeight);
        parameters.put("userWeight" , userWeight);
        parameters.put("userAge" , userAge);
        parameters.put("userValue" , userValue);
        parameters.put("userPT" , userPT);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
