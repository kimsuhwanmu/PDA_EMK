package com.example.EM_KOREA.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class I70_Activity extends BaseActivity {

    //== Intent에서 받을 값 선언 ==//
    private String vMenuID, vMenuNm, vMenuRemark, vStartCommand;

    //== View 선언(버튼) ==//
    private Button btn_inventory_count, btn_sl_cd_all_inventory_count, btn_table_inventory_count, btn_inventory_status;
    private Button btn_menu;

    //== Grant 관련 변수 ==//
    private String ADMIN_CHK = "N", W_MOVE = "N", I71 = "N", I74 = "N", I75 = "N", I78 = "N";     //Grant

    //== Request Code 상수 선언 ==//
    private final int I71_HDR_REQUEST_CODE = 1, I74_HDR_REQUEST_CODE = 4, I75_HDR_REQUEST_CODE = 5, I78_HDR_REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i70_sub_menu);

        this.initializeView();

        this.initializeListener();

        this.initializeData();
    }

    private void initializeView() {
        //== BaseActivity 에서 SESSION 값 셋팅 ==//
        this.init();

        //== Intent 값 바인딩 ==//
        vMenuID             = getIntent().getStringExtra("MENU_ID");
        vMenuNm             = getIntent().getStringExtra("MENU_NM");
        vMenuRemark         = getIntent().getStringExtra("MENU_REMARK");
        vStartCommand       = getIntent().getStringExtra("START_COMMAND"); //다음 페이지에 가지고 넘어갈 코드

        //== ID값 바인딩 ==//
        btn_menu                        = (Button) findViewById(R.id.btn_menu);                         // 메뉴버튼
        btn_inventory_count             = (Button) findViewById(R.id.btn_inventory_count);              // 1. 창고별실사등록
        btn_sl_cd_all_inventory_count   = (Button) findViewById(R.id.btn_sl_cd_all_inventory_count);    // 4. 실사등록(통합)
        btn_table_inventory_count       = (Button) findViewById(R.id.btn_table_inventory_count);        // 5. 재고실사표실사등록
        btn_inventory_status            = (Button) findViewById(R.id.btn_inventory_status);             // 8. 실사현황
    }

    private void initializeListener() {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_menu: // 메뉴버튼
                        // 저장 후 결과 값 돌려주기
                        Intent resultIntent = new Intent();
                        // 부른 Activity에게 결과 값 반환
                        setResult(RESULT_CANCELED, resultIntent);
                        // 현재 Activity 종료
                        break;
                    case R.id.btn_inventory_count:  // 1. 창고별실사등록
                        if(start_grant("I71")) {
                            String sMenuName = "메뉴 > 재고실사관리 > 실사등록";

                            Intent intent = TGSClass.ChangeView(getPackageName(), I71_Activity.class.getSimpleName());
                            intent.putExtra("MENU_ID", "I71");
                            intent.putExtra("MENU_NM", sMenuName);
                            intent.putExtra("MENU_REMARK", vMenuRemark);
                            intent.putExtra("START_COMMAND", vStartCommand);
                            startActivityForResult(intent, I71_HDR_REQUEST_CODE);
                        }
                        break;
                    case R.id.btn_table_inventory_count:    // 4. 재고실사표실사등록
                        if (start_grant("I74")) {
                            String sMenuName = "메뉴 > 재고실사관리 > 재고실사표 실사등록";

                            Intent intent = TGSClass.ChangeView(getPackageName(), I74_Activity.class.getSimpleName());
                            intent.putExtra("MENU_ID", "I74");
                            intent.putExtra("MENU_NM", sMenuName);
                            intent.putExtra("MENU_REMARK", vMenuRemark);
                            intent.putExtra("START_COMMAND", vStartCommand);
                            startActivityForResult(intent, I74_HDR_REQUEST_CODE);
                        }
                        break;
                    case R.id.btn_inventory_status: // 5. 실사현황
                        if (start_grant("I75")) {
                            String sMenuName = "메뉴 > 재고실사관리 > 실사현황";

                            Intent intent = TGSClass.ChangeView(getPackageName(), I75_Activity.class.getSimpleName());
                            intent.putExtra("MENU_ID", "I75");
                            intent.putExtra("MENU_NM", sMenuName);
                            intent.putExtra("MENU_REMARK", vMenuRemark);
                            intent.putExtra("START_COMMAND", vStartCommand);
                            startActivityForResult(intent, I75_HDR_REQUEST_CODE);
                        }
                        break;
                    case R.id.btn_sl_cd_all_inventory_count:    // 8. 실사등록(통합)
                        if (start_grant("I78")) {
                            String sMenuName = "메뉴 > 재고실사관리 > 실사등록(통합)";

                            Intent intent = TGSClass.ChangeView(getPackageName(), I78_Activity.class.getSimpleName());
                            intent.putExtra("MENU_ID", "I78");
                            intent.putExtra("MENU_NM", sMenuName);
                            intent.putExtra("MENU_REMARK", vMenuRemark);
                            intent.putExtra("START_COMMAND", vStartCommand);
                            startActivityForResult(intent, I78_HDR_REQUEST_CODE);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        btn_menu.setOnClickListener(clickListener);                         // 메뉴버튼
        btn_inventory_count.setOnClickListener(clickListener);              // 1. 창고별실사등록
        btn_table_inventory_count.setOnClickListener(clickListener);        // 4. 실사등록(통합)
        btn_inventory_status.setOnClickListener(clickListener);             // 5. 재고실사표실사등록
        btn_sl_cd_all_inventory_count.setOnClickListener(clickListener);    // 8. 실사현황
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
                W_MOVE = LEVEL_CD.equals("W_MOVE") || W_MOVE.equals("Y") ? "Y" : "N";
                I71 = LEVEL_CD.equals("I71") || I71.equals("Y") ? "Y" : "N";
                I74 = LEVEL_CD.equals("I74") || I74.equals("Y") ? "Y" : "N";
                I75 = LEVEL_CD.equals("I75") || I75.equals("Y") ? "Y" : "N";
                I78 = LEVEL_CD.equals("I78") || I78.equals("Y") ? "Y" : "N";
            }

            if (ADMIN_CHK.equals("N") && W_MOVE.equals("N")) {
                if (MenuID.equals("I71") && I71.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "창고별 실사등록 메뉴에 대한 접속 권한이 없습니다.");
                    return false;
                } else if (MenuID.equals("I74") && I74.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "재고실사표 실사등록 메뉴에 대한 접속 권한이 없습니다.");
                    return false;
                } else if (MenuID.equals("I75") && I75.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "실사현황 메뉴에 대한 접속 권한이 없습니다.");
                    return false;
                } else if (MenuID.equals("I78") && I78.equals("N")) {
                    TGSClass.AlterMessage(getApplicationContext(), "실사등록(통합) 메뉴에 대한 접속 권한이 없습니다.");
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
                case I71_HDR_REQUEST_CODE:
                    //Log.d("OK", "I71_HDR");
                    break;
                case I74_HDR_REQUEST_CODE:
                    //Log.d("OK", "I74_HDR");
                    break;
                case I75_HDR_REQUEST_CODE:
                    //Log.d("OK", "I75_HDR");
                    break;
                case I78_HDR_REQUEST_CODE:
                    //Log.d("OK", "I78_HDR");
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case I71_HDR_REQUEST_CODE:
                    //Log.d("CANCELED", "I71_HDR");
                    break;
                case I74_HDR_REQUEST_CODE:
                    //Log.d("CANCELED", "I74_HDR");
                    break;
                case I75_HDR_REQUEST_CODE:
                    //Log.d("CANCELED", "I75_HDR");
                    break;
                case I78_HDR_REQUEST_CODE:
                    //Log.d("CANCELED", "I78_HDR");
                    break;
                default:
                    break;
            }
        }
    }
}