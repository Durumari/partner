package com.a3did.partner.account;

import com.a3did.partner.adapterlist.AchievementListData;
import com.a3did.partner.adapterlist.AssistantListData;
import com.a3did.partner.adapterlist.CompletedListData;
import com.a3did.partner.adapterlist.MissedListData;
import com.a3did.partner.adapterlist.RewardListData;
import com.a3did.partner.adapterlist.SafetyData;

import java.util.ArrayList;

/**
 * Created by Joonhyun on 2016-11-26.
 */

public class PartnerUserInfo {
    //이름
    public String mName;
    //유니크 아이디
    public int mUID;
    //비콘 정보
    public String mBeaconInfo;
    //폰 번호
    public String mUserPhoneNumber;
    //부모님 번호
    public String mParentPhoneNumber;
    //보유 별
    public int mStarNumber;
    //일정 리스트
    public ArrayList<AssistantListData> mScheduleInfoList;
    //목표 리스트
    public ArrayList<AchievementListData> mAchievementInfoList;

    //리워드 리스트
    public ArrayList<RewardListData> mRewardInfoList;
    //위험 지역 리스트
    public ArrayList<SafetyData> mSafetyInfoList;
    //완료 리스트
    public ArrayList<CompletedListData> mCompletedInfoList;
    //실패 리스트
    public ArrayList<MissedListData> mMissedInfoList;

    public PartnerUserInfo()
    {
        mName = null;
        mUID = 0;
        mBeaconInfo = null;
        mUserPhoneNumber = null;
        mParentPhoneNumber = null;
        mStarNumber = 0;
        mScheduleInfoList = new ArrayList<AssistantListData>();
        mAchievementInfoList = new ArrayList<AchievementListData>();
        mRewardInfoList = new ArrayList<RewardListData>();
        mSafetyInfoList = new ArrayList<SafetyData>();
        mCompletedInfoList = new ArrayList<CompletedListData>();
        mMissedInfoList = new ArrayList<MissedListData>();
    };
}
