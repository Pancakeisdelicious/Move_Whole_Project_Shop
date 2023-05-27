package com.example.move_whole_project.Profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.move_whole_project.Fragment.HomeFragment;
import com.example.move_whole_project.Fragment.MissionFragment;
import com.example.move_whole_project.Fragment.RecordFragment;
import com.example.move_whole_project.Fragment.ShopFragment;
import com.example.move_whole_project.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Activity_Profile extends AppCompatActivity {


    // 막대 그래프
    private BarChart barChart;
    HomeFragment homeFragment;
    MissionFragment missionFragment;
    RecordFragment recordFragment;

    ShopFragment shopFragment;

    // 1주, 1달, 1년 스피너
    private Spinner spinnerTimeRange; // 1주, 1달, 1년 스피너
    private String[] timeRangeOptions = {"일주일", "1달", "1년"}; // 스피너 옵션
    private String[] spinner_weekdays = {"월", "화", "수", "목", "금", "토", "일"}; // 1주 x축 값
    private String[] spinner_months = {"1주", "2주", "3주", "4주", "5주"}; // 1달 x축 값
    private String[] spinner_year = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"}; // 1년 x축 값

    // json 수신
    private String steps;
    private String date;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ArrayList<BarEntry> stepData = new ArrayList<>(); // 막대 그래프에 표시할 데이터를 담을 ArrayList

        barChart = findViewById(R.id.tv_profile_graph_input);

        BarDataSet stepDataSet = new BarDataSet(stepData, "걸음 수");
        stepDataSet.setColor(Color.BLACK); // 막대 그래프 색상 설정

        BarData barData = new BarData(stepDataSet);
        barData.setBarWidth(0.9f); // 막대의 너비 설정 (0.9f는 90%를 의미)

        barChart.setData(barData);
        barChart.setFitBars(true); // 막대 그래프의 막대를 차트에 꽉 채우도록 설정

        // X축 설정
        String[] weekdays = {"월", "화", "수", "목", "금", "토", "일"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays)); // X축 값 포맷터 설정
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 위치 설정

        // Y축 설정
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f); // Y축 최소값 설정
        yAxis.setGranularity(500f); // Y축 간격 설정

        barChart.getAxisRight().setEnabled(false); // 오른쪽 Y축 비활성화

        barChart.getDescription().setEnabled(false); // 차트 설명 비활성화
        barChart.setTouchEnabled(false); // 차트 터치 비활성화

        barChart.invalidate(); // 차트 업데이트

        // 스피너 로직
        spinnerTimeRange = findViewById(R.id.spinner_time_range);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeRangeOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeRange.setAdapter(spinnerAdapter);

        spinnerTimeRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = timeRangeOptions[position];
                switch (selectedOption) {
                    case "일주일":
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(spinner_weekdays));
                        stepData.clear();
                        stepData.add(new BarEntry(0, 1000f)); // 월요일의 걸음 수
                        stepData.add(new BarEntry(1, 2000f)); // 화요일의 걸음 수
                        stepData.add(new BarEntry(2, 1500f)); // 수요일의 걸음 수
                        stepData.add(new BarEntry(3, 3000f)); // 목요일의 걸음 수
                        stepData.add(new BarEntry(4, 2500f)); // 금요일의 걸음 수
                        stepData.add(new BarEntry(5, 4000f)); // 토요일의 걸음 수
                        stepData.add(new BarEntry(6, 3500f)); // 일요일의 걸음 수
                        break;
                    case "1달":
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(spinner_months));
                        stepData.clear();
                        stepData.add(new BarEntry(0, 1000f)); // 1주의 걸음 수
                        stepData.add(new BarEntry(1, 2000f)); // 2주의 걸음 수
                        stepData.add(new BarEntry(2, 1500f)); // 3주의 걸음 수
                        stepData.add(new BarEntry(3, 3000f)); // 4주의 걸음 수
                        stepData.add(new BarEntry(4, 2500f)); // 5주의 걸음 수
                        break;
                    case "1년":
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(spinner_year));
                        stepData.clear();
                        stepData.add(new BarEntry(0, 1000f)); // 1월의 걸음 수
                        stepData.add(new BarEntry(1, 2000f)); // 2월의 걸음 수
                        stepData.add(new BarEntry(2, 1500f)); // 3월의 걸음 수
                        stepData.add(new BarEntry(3, 3000f)); // 4월의 걸음 수
                        stepData.add(new BarEntry(4, 2500f)); // 5월의 걸음 수
                        stepData.add(new BarEntry(5, 4000f)); // 6월의 걸음 수
                        stepData.add(new BarEntry(6, 3500f)); // 7월의 걸음 수
                        stepData.add(new BarEntry(7, 2800f)); // 8월의 걸음 수
                        stepData.add(new BarEntry(8, 3200f)); // 9월의 걸음 수
                        stepData.add(new BarEntry(9, 3900f)); // 10월의 걸음 수
                        stepData.add(new BarEntry(10, 2700f)); // 11월의 걸음 수
                        stepData.add(new BarEntry(11, 2200f)); // 12월의 걸음 수
                        break;
                }

                barData.notifyDataChanged();
                barChart.notifyDataSetChanged();
                barChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.mission:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, missionFragment).commit();
                        return true;
                    case R.id.record:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, recordFragment).commit();
                        return true;
                    case R.id.shop:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, shopFragment).commit();
                        return true;
                }
                return false;
            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);

        // JSON 데이터 생성
        JSONObject jsonRequest = new JSONObject();
        try {
            JSONArray stepArray = new JSONArray();
            for (BarEntry entry : stepData) {
                JSONObject stepObject = new JSONObject();
                stepObject.put("x", entry.getX()); // 막대 그래프의 X 좌표
                stepObject.put("y", entry.getY()); // 막대 그래프의 Y 좌표
                stepArray.put(stepObject);
            }
            jsonRequest.put("stepData", stepArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 요청 생성
        String url = "http://localhost:8080";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 서버로부터의 응답 처리
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 에러 처리
            }
        });
        queue.add(request);
    }
}







