package com.example.move_whole_project.Register_Login;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// create construct => 2번째거 선택(alt + enter)

public class LoginRequest extends StringRequest {
    // url 설정(서버 url)(PHP 파일 연동)

    final static private String URL = "http://ghzzzang81.dothome.co.kr/Login.php";

    // 해쉬맵 생성 (인텐트 풋 엑스트라와 비슷한것)
    private Map<String, String> map;

    // 생성자 만들기, 로그인에서 입력받는 데이터들 (아이디, 패스워드)
    public LoginRequest(String userID, String userPass, Response.Listener<String> listener) {
        // 서버 전송 방식 (POST 방식)
        super(Method.POST, URL, listener,null);


        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPass", userPass);

    }

    // ctrl + o => getParams map 형태 가져오기


    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
