package com.example.move_whole_project.Register_Login;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// create construct => 2번째걸 선택(alt + enter)

public class RegisterRequest extends StringRequest {

    // 1. url 설정(서버 url)(Java 파일 연동)

    final static private String URL = "http://ghzzzang81.dothome.co.kr/Register.php";

    // 해쉬맵 생성(인텐트 풋 엑스트라와 비슷한것)
    private Map<String, String> map;


    // 생성자 만들기, 회원가입에서 입력받는 데이터들 (이름, 닉네임, 아이디, 패스워드, 성별, 지역, 생년월)
    public RegisterRequest(String userName, String userNickname, String userID, String userPass, String userGender,
                           String userRegion, int userYear, Response.Listener<String> listener) {

        // 서버 전송 방식 POST 방식
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        // 키랑 벨류 지정해서 맵으로 자료형을 구현한다.
        // 서버에서 지정하는 키값(컬럼 값으로 설정해줘야한다.)

        map.put("userName",userName);
        map.put("userNickname",userNickname);
        map.put("userID",userID);
        map.put("userPass",userPass);
        map.put("userGender",userGender);
        map.put("userRegion",userRegion);
        map.put("userYear",userYear+"");// int 형이여서 생기는 오류를 "" 추가해 문자열로 인식하게 한다.

    }

   // ctrl + o => getParams map 형태 가져오기

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
       return map;
    }
}
