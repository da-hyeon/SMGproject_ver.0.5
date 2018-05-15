package com.example.hdh.smgproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/UserLogin_SYG.php";
    private Map<String , String> parameters;

    public LoginRequest(String userID , String userPassword, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null); //해당 URL의 parameters들을 POST방식으로 해당 요청을 숨겨서 보여주어라
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
        parameters.put("userPassword" , userPassword);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
