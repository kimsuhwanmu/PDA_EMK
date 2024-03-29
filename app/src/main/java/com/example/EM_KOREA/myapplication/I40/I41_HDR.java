package com.example.EM_KOREA.myapplication.I40;

import java.io.Serializable;

public class I41_HDR implements Serializable {   //Serializable 객체를 파라미터로 다른 ACTIVITY에 전달할때 필요함.

    private  String PRODT_ORDER_NO;
    private  String OPR_NO;
    private  String WC_CD;

    private  String ITEM_CD;
    private  String ITEM_NM;
    private  String TRACKING_NO;
    private  String MOV_TYPE;
    private  String REQ_QTY;
    private  String MOV_STATUS;
    private  String LOT_NO;
    private  String SL_CD;

    private  int NO_SEQ;


    public String getPRODT_ORDER_NO() { return PRODT_ORDER_NO;}
    public void setPRODT_ORDER_NO(String prodt_order_no) { PRODT_ORDER_NO = prodt_order_no; }

    public String getOPR_NO() { return OPR_NO;}
    public void setOPR_NO(String opr_no) { OPR_NO = opr_no; }

    public String getWC_CD() { return WC_CD;}
    public void setWC_CD(String wc_cd) { WC_CD = wc_cd; }

    public String getITEM_CD() { return ITEM_CD; }
    public void setITEM_CD(String item_cd) { ITEM_CD = item_cd; }

    public String getITEM_NM() { return ITEM_NM; }
    public void setITEM_NM(String item_nm) { ITEM_NM = item_nm; }

    public String getTRACKING_NO() { return TRACKING_NO; }
    public void setTRACKING_NO(String tracking_no) { TRACKING_NO = tracking_no; }

    public String getMOV_TYPE() { return MOV_TYPE; }
    public void setMOV_TYPE(String mov_type) { MOV_TYPE = mov_type; }


    public String getREQ_QTY() { return REQ_QTY; }
    public void setREQ_QTY(String req_qty) { REQ_QTY = req_qty; }

    public String getMOV_STATUS() { return MOV_STATUS; }
    public void setMOV_STATUS(String mov_status) { MOV_STATUS = mov_status; }

    public String getLOT_NO() { return LOT_NO; }
    public void setLOT_NO(String lot_no) { LOT_NO = lot_no; }

    public String getSL_CD() { return SL_CD; }
    public void setSL_CD(String sl_cd) { SL_CD = sl_cd; }

    public int getNO_SEQ() { return NO_SEQ; }
    public void setNO_SEQ(int no_seq) { NO_SEQ = no_seq; }
}
