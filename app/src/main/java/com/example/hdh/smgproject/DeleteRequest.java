package com.example.hdh.smgproject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwangdahyeon on 2018. 5. 3..
 */

public class DeleteRequest extends StringRequest {
    final static private String URL = "http://kjg123kg.cafe24.com/UserDelete_SYG.php";

    private Map<String , String> parameters;

    public DeleteRequest(String userID , Response.Listener<String> listener){
        super(Request.Method.POST , URL , listener , null);
        parameters = new HashMap<>();
        parameters.put("userID" , userID);
    }
    @Override
    public Map<String , String> getParams() {
        return parameters;
    }
}