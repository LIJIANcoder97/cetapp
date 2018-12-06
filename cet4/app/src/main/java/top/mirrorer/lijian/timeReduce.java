package top.mirrorer.lijian;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/6/1 0001.
 */

public class timeReduce {
    private int day=0,hour,min,second;
    timeReduce(int y,int ma,int d,int h,int m,int s){
        Calendar cal =Calendar.getInstance();
//        long t1=cal.getTimeInMillis();
//        cal.set(y,ma,d);
//        long t2=cal.getTimeInMillis();
//        day = (int)(t2-t1)/(1000*60*60*24);
        while(cal.get(cal.YEAR)!=y||(cal.get(cal.MONTH)!=ma||cal.get(cal.DATE)!=d)) {
            cal.add(cal.DATE, 1);
            day++;
        }
        int d1=cal.get(cal.DATE);
        int h1=cal.get(cal.HOUR);
        int m1=cal.get(cal.MINUTE);
        int s1=cal.get(cal.SECOND);
        if (s-s1>=0)second=s-s1;
        else {second=s+60-s1;m--;}
        if (m-m1>=0)min=m-m1;
        else {min=m+60-m1;h--;}
        if (h-h1>=0)hour=h-h1;
        else {hour=h+24-h1;day--;}
    }
    public int getDay(){
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSecond() {
        return second;
    }
}
