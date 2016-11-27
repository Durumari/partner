package com.a3did.partner.adapterlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a3did.partner.account.PartnerUserInfo;
import com.a3did.partner.account.UserManager;
import com.a3did.partner.partner.R;

import java.util.ArrayList;

/**
 * Created by Joonhyun on 2016-11-19.
 */

public class AccountListAdapter  extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<PartnerUserInfo> listViewItemList = null;


    // ListViewAdapter의 생성자
    public AccountListAdapter() {

    }

    public void setList(ArrayList<PartnerUserInfo> list){
        listViewItemList = list;
    }
    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_account, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView nameTextVuew = (TextView) convertView.findViewById(R.id.textView_username) ;
        TextView scheduleNumView = (TextView) convertView.findViewById(R.id.text_account_schedule) ;
        TextView taskNumVIew = (TextView) convertView.findViewById(R.id.text_account_goal) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        PartnerUserInfo listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        nameTextVuew.setText(listViewItem.mName);
        scheduleNumView.setText("" + listViewItem.mScheduleInfoList.size());
        taskNumVIew.setText("" + listViewItem.mAchievementInfoList.size());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(PartnerUserInfo userInfo){
        listViewItemList.add(userInfo);
    }

    /*
    public void addItem(Drawable icon, String goal, int rewardNumber, String fromDate, String toDate) {
        AchievementListData item = new AchievementListData();

        item.setIcon(icon);
        item.setTitle(goal);
        item.setStarNumber(rewardNumber);
        item.setFromStr("시작 : " + fromDate);
        item.setToStr("종료 : " + toDate);

        listViewItemList.add(item);
    }*/
}