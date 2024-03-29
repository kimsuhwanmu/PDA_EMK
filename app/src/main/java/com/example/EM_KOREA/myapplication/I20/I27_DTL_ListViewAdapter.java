package com.example.EM_KOREA.myapplication.I20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.EM_KOREA.myapplication.R;

import java.util.ArrayList;

public class I27_DTL_ListViewAdapter extends BaseAdapter {

    private ArrayList<I27_DTL> listViewItem = new ArrayList<I27_DTL>();

    public I27_DTL_ListViewAdapter() {

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

    public Object getItems(){return listViewItem;}

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
            convertView = inflater.inflate(R.layout.list_view_i27_dtl, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView ITEM_CD            = (TextView) convertView.findViewById(R.id.ITEM_CD);
        TextView ITEM_NM            = (TextView) convertView.findViewById(R.id.ITEM_NM);
        TextView MOVE_QTY           = (TextView) convertView.findViewById(R.id.MOVE_QTY);
        Button btn_del              = (Button) convertView.findViewById(R.id.btn_del);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        I27_DTL item = listViewItem.get(position);

        ITEM_CD.setText(item.getITEM_CD());
        ITEM_NM.setText(item.getITEM_NM());
        MOVE_QTY.setText(item.getMOVE_QTY());


        return convertView;
    }

    public void add_Item(String ITEM_CD, String ITEM_NM, String SL_CD, String SL_NM, String GOOD_ON_HAND_QTY, String BAD_ON_HAND_QTY, String TRACKING_NO) {
        I27_DTL item = new I27_DTL();

        item.setITEM_CD(ITEM_CD);
        item.setITEM_NM(ITEM_NM);
        item.setSL_CD(SL_CD);
        item.setSL_NM(SL_NM);
        item.setGOOD_ON_HAND_QTY(GOOD_ON_HAND_QTY);
        item.setBAD_ON_HAND_QTY(BAD_ON_HAND_QTY);
        item.setTRACKING_NO(TRACKING_NO);

        //listViewItem.add(item);
        listViewItem.add(0,item);
    }

    protected void addDTLItem(I27_DTL item) {
        listViewItem.add(0,item);
    }

    protected void RemoveDTLItem(int idx) {
        listViewItem.remove(idx);
    }

    protected void delDTLItem(int idx) {
        if(listViewItem.size() >= idx){
            listViewItem.remove(idx);
        }
    }
}
