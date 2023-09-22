package com.example.EM_KOREA.myapplication.M20;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.EM_KOREA.myapplication.BaseActivity;
import com.example.EM_KOREA.myapplication.R;
import com.example.EM_KOREA.myapplication.TGSClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class M20_Activity extends BaseActivity {

    //== Intent에서 받을 값 선언 ==//
    private String vMenuID, vMenuNm, vMenuRemark, vStartCommand;

    //== View 선언(Button) ==//
    private Button btn_Prodt_Warehousing_Out_save, btn_Prodt_Warehousing_Out_query, btn_menu,btn_Prodt_Warehousing_Out_set;

    //== Grant 관련 변수 ==//
    private String ADMIN_CHK = "N", W_IN = "N", M21 = "N", M22 = "N",M23 = "N";     //Grant

    //== ActivityForResult 관련 변수 ==//
    private final int M21_HDR_REQUEST_CODE = 1, M22_QUERY_REQUEST_CODE = 2,M23_HDR_REQUEST_CODE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m20_sub_menu);

        this.initializeView();

        this.initializeListener();

        this.initializeData();
    }

    private void initializeView() {
        //== BaseActivity 에서 SESSION 값 셋팅 ==//
        this.init();

        //== Intent 값 바인딩 ==//
        vMenuID         = getIntent().getStringExtra("MENU_ID");
        vMenuNm         = getIntent().getStringExtra("MENU_NM");
        vMenuRemark     = getIntent().getStringExtra("MENU_REMARK");
        vStartCommand   = getIntent().getStringExtra("START_COMMAND"); //다음 페이지에 가지고 넘어갈 코드

        //== ID값 바인딩 ==//
        btn_Prodt_Warehousing_Out_save  = findViewById(R.id.btn_Prodt_Warehousing_Out_save);    // 1. 생산입고등록
        btn_Prodt_Warehousing_Out_query = findViewById(R.id.btn_Prodt_Warehousing_Out_query);   // 2. 생산입고조회
        btn_Prodt_Warehousing_Out_set   = findViewById(R.id.btn_Prodt_Warehousing_Out_set);   // 3. 생산입고등록(함안)
        btn_menu                        = (Button) findViewById(R.id.btn_menu);                 // 메뉴
    }

    private void initializeListener() {
        //== Click 이벤트 만들기 ==//
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_menu) {            // 메뉴 버튼
                    // 저장 후 결과 값 돌려주기
                    Intent resultIntent = new Intent();
                    // 부른 Activity에게 결과 값 반환
                    setResult(RESULT_CANCELED, resultIntent);
                    // 현재 Activity 종료
                    finish();
                } else if (v == btn_Prodt_Warehousing_Out_save) {   // 생산입고등록 버튼
                    if (start_grant("M21")) {
                        String sMenuName = "메뉴 > 생산입고관리 > 생산입고등록\n\n검사요청번호 OR 제조오더번호 입력";

                        Intent intent = TGSClass.ChangeView(getPackageName(), M21_HDR_Activity.class);
                        intent.putExtra("MENU_ID", "M21");
                        intent.putExtra("MENU_NM", sMenuName);
                        intent.putExtra("MENU_REMARK", vMenuRemark);
                        intent.putExtra("START_COMMAND", vStartCommand);
                        startActivityForResult(intent, M21_HDR_REQUEST_CODE);
                    }
                } else if (v == btn_Prodt_Warehousing_Out_query) {  // 생산입고조회 버튼
                    if (start_grant("M22")) {
                        String sMenuName = "메뉴 > 생산입고관리 > 생산입고현황조회";

                        Intent intent = TGSClass.ChangeView(getPackageName(), M22_QUERY_Activity.class);
                        intent.putExtra("MENU_ID", "M22");
                        intent.putExtra("MENU_NM", sMenuName);
                        intent.putExtra("MENU_REMARK", vMenuRemark);
                        intent.putExtra("START_COMMAND", vStartCommand);
                        startActivityForResult(intent, M22_QUERY_REQUEST_CODE);
                    }
                }
                else if (v == btn_Prodt_Warehousing_Out_set) {  // 생산입고조회 버튼
                    if (start_grant("M23")) {
                        String sMenuName = "메뉴 > 생산입고관리 > 생산입고등록(함안)";

                        Intent intent = TGSClass.ChangeView(getPackageName(), M23_HDR_Activity.class);
                        intent.putExtra("MENU_ID", "M23");
                        intent.putExtra("MENU_NM", sMenuName);
                        intent.putExtra("MENU_REMARK", vMenuRemark);
                        intent.putExtra("START_COMMAND", vStartCommand);
                        startActivityForResult(intent, M23_HDR_REQUEST_CODE);
                    }
                }
            }
        };
        //== 이벤트 부여 ==//
        btn_menu.setOnClickListener(clickListener);
        btn_Prodt_Warehousing_Out_save.setOnClickListener(clickListener);
        btn_Prodt_Warehousing_Out_query.setOnClickListener(clickListener);
        btn_Prodt_Warehousing_Out_set.setOnClickListener(clickListener);

    }

    private void initializeData() {
        getGrant(vUSER_ID);
    }

    private boolean start_grant(final String MenuID) {
        try {
            JSONArray ja = new JSONArray(sJson_grant);

            for (int idx = 0; idx < ja.length(); idx++) {
                JSONObject jObject = ja.getJSONObject(idx);

                final String LEVEL_CD = jObject.getString("LEVEL_CD");

                ADMIN_CHK = LEVEL_CD.equals("ADMIN") || ADMIN_CHK.equals("Y") ? "Y" : "N";
                W_IN = LEVEL_CD.equals("W_IN") || W_IN.equals("Y") ? "Y" : "N";
                M21 = LEVEL_CD.equals("M21") || M21.equals("Y") ? "Y" : "N";
                M22 = LEVEL_CD.equals("M22") || M22.equals("Y") ? "Y" : "N";
                M23 = LEVEL_CD.equals("M23") || M23.equals("Y") ? "Y" : "N";

            }

            if (ADMIN_CHK.equals("N") && W_IN.equals("N")) {
                if (MenuID.equals("M21") && M21.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "생산입고등록 메뉴에 대한 접속 권한이 없습니다.");
                    return false;
                } else if (MenuID.equals("M22") && M22.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "생산입고조회 메뉴에 대한 접속 권한이 없습니다.");
                    return false;
                }
                else if (MenuID.equals("M23") && M23.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "생산입고등록(함안) 메뉴에 대한 접속 권한이 없습니다.");
                    return false;
                }
            }
            return true;
        } catch (JSONException exJson) {
            TGSClass.AlterMessage(getApplicationContext(), "catch : exJson");
            return false;
        } catch (Exception ex) {
            TGSClass.AlterMessage(getApplicationContext(), "catch : ex");
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case M21_HDR_REQUEST_CODE:
                    //Log.d("OK", "M21_HDR");
                    break;
                case M22_QUERY_REQUEST_CODE:
                    //Log.d("OK", "M22_QUERY");
                    break;
                case M23_HDR_REQUEST_CODE:
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case M21_HDR_REQUEST_CODE:
                    //Log.d("CANCELED", "M21_HDR");
                    break;
                case M22_QUERY_REQUEST_CODE:
                    //Log.d("CANCELED", "M22_QUERY");
                    break;
                case M23_HDR_REQUEST_CODE:
                    break;
                default:
                    break;
            }
        }
    }
}
