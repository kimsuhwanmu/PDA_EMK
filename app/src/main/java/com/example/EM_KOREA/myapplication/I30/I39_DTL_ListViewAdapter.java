package com.example.EM_KOREA.myapplication.I30;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.EM_KOREA.myapplication.R;

import java.util.ArrayList;

public class I39_DTL_ListViewAdapter extends BaseAdapter {

    private ArrayList<I39_HDR> listViewItem = new ArrayList<I39_HDR>();

    public I39_DTL_ListViewAdapter() {

    }

    @Override
    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    public int getCount() {
        return listViewItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItem.get(position);
    }

    public void clear() {
        listViewItem.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_i39_hdr, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView prodt_order_no         = (TextView) convertView.findViewById(R.id.prodt_order_no);
        TextView item_nm                = (TextView) convertView.findViewById(R.id.item_nm);
        TextView tracking_no            = (TextView) convertView.findViewById(R.id.tracking_no);
        TextView job_nm                 = (TextView) convertView.findViewById(R.id.job_nm);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        I39_HDR item = listViewItem.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        prodt_order_no.setText(item.getPRODT_ORDER_NO());
        item_nm.setText(item.getITEM_NM());
        tracking_no.setText(item.getTRACKING_NO());
        job_nm.setText(item.getJOB_NM());

        return convertView;
    }

    public void add_Item(String PRODT_ORDER_NO,
                         String ITEM_CD,
                         String PRODT_ORDER_UNIT,
                         String ITEM_NM,
                         String SPEC,
                         String TRACKING_NO,
                         String PRODT_ORDER_QTY,
                         String GOOD_QTY,
                         String BAD_QTY,
                         String REMAIN_QTY,
                         String SL_CD,
                         String JOB_NM) {

        I39_HDR item = new I39_HDR();

        item.setPRODT_ORDER_NO(PRODT_ORDER_NO);
        item.setPRODT_ORDER_UNIT(PRODT_ORDER_UNIT);
        item.setTRACKING_NO(TRACKING_NO);
        item.setPRODT_ORDER_QTY (PRODT_ORDER_QTY );
        item.setGOOD_QTY(GOOD_QTY);
        item.setBAD_QTY(BAD_QTY);
        item.setREMAIN_QTY(REMAIN_QTY);
        item.setSL_CD(SL_CD);
        item.setJOB_NM(JOB_NM);
        listViewItem.add(item);
    }

    protected void addHDRItem(I39_HDR item) {
        listViewItem.add(item);
    }

}
