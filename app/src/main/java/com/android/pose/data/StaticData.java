package com.android.pose.data;

import com.android.pose.model.PosePic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoyao on 16/9/7.
 */

public class StaticData {

    public static List<PosePic> getData(){
        List<PosePic> mPics=new ArrayList<>();
        PosePic posePic1=new PosePic();
        posePic1.imgUrl="https://thumbnail0.baidupcs.com/thumbnail/bb229ae31036dcca87c6a92fbba0aa70?fid=1949288303-250528-816811836451505&time=1473300000&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-sw9KNfFFQJ3%2Bn%2F4fnzYSXa3wzVk%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=5831198630893288882&dp-callid=0&size=c710_u400&quality=100";
        mPics.add(posePic1);
        mPics.add(posePic1);

        PosePic posePic2=new PosePic();
        posePic2.imgUrl="https://thumbnail0.baidupcs.com/thumbnail/c982ef5fe968466431d7428696a24bd9?fid=1949288303-250528-401224750428810&time=1473300000&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-wvCa0eWh1efL6L9ldOVxPeq3Vy4%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=5831222366299201433&dp-callid=0&size=c710_u400&quality=100";
        mPics.add(posePic2);
        mPics.add(posePic2);

        PosePic posePic3=new PosePic();
        posePic3.imgUrl="https://thumbnail0.baidupcs.com/thumbnail/b77278a7a31d971c05729601172b5bb0?fid=1949288303-250528-957106251350843&time=1473300000&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-4O3NYwAGJn6Y67eNG525%2B4jhfUM%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=5831227312798991505&dp-callid=0&size=c710_u400&quality=100";
        mPics.add(posePic3);
        mPics.add(posePic3);
        return mPics;
    }
}
