package com.example.hdh.smgproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PTNumUpdateRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/PTNumUpdate_SYG.php";
    private Map<String , String> parameters;

    //admin으로 접속했을때
    public PTNumUpdateRequest(String userID , int userPT, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null); //해당 URL의 parameters들을 POST방식으로 해당 요청을 숨겨서 보여주어라
        parameters = new HashMap<>();

        parameters.put("userID" , userID);
        //Int형으로 전달하지 못하기 때문에 String형으로 형변환.
        parameters.put("userPT" , String.valueOf(userPT));
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
