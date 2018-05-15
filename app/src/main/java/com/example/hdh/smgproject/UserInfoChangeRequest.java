package com.example.hdh.smgproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserInfoChangeRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/UserInfoChange_SYG.php";
    private Map<String , String> parameters;

    //일반회원으로 접속했을때
    public UserInfoChangeRequest(String userID , String userPassword, String userName, String userEmail, String userGender, String userHeight, String userWeight, String userAge ,  Response.Listener<String> listener){
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
        parameters.put("userValue" , "b");
}

    //admin으로 접속했을때
    public UserInfoChangeRequest(String userID , String userPassword, String userName, String userEmail, String userGender, String userHeight, String userWeight, String userAge ,int userPT,  Response.Listener<String> listener){
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
        //Int형으로 전달하지 못하기 때문에 String형으로 형변환.
        parameters.put("userPT" , String.valueOf(userPT));
        parameters.put("userValue" , "a");
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
