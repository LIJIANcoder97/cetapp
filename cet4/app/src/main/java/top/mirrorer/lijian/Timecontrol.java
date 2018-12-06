package top.mirrorer.lijian;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2018/6/10 0010.
 */

public class Timecontrol {
    private String path=null,filename=null;
    public Timecontrol() throws IOException {
        File file = Environment.getExternalStorageDirectory();
         path=file.getAbsolutePath()+"/SJJ";
         //检测文件是否存在
        File fn=new File(path,"name.s");
        File ft=new File(path,"time.s");
        File clocklist=new  File(path,"allclocklist.json");
        File nowclocklist= new File(path,"nowclocklist.json");
        File allclockname=new File(path,"allclockname.s");
        File nowclockname=new File(path,"nowclockname.s");
        //String path=context.getFilesDir().getAbsolutePath();
        //Log.d("666", path);
        if (!fn.exists()) fn.createNewFile();
        if (!ft.exists()) ft.createNewFile();
        if (!clocklist.exists()) clocklist.createNewFile();
        if (!nowclocklist.exists()) nowclocklist.createNewFile();
        if (! allclockname.exists()) allclockname.createNewFile();
        if (!nowclockname.exists()) nowclockname.createNewFile();
    }
    //增
    public void newTimeclock(String name,int year,int month,int day,int hour,int minute,int second ,boolean chose,String planfile) throws IOException {
        if (!isrepeat(name)){//查重
            TimeclockBine bine = new TimeclockBine();
            bine.setName(name);
            bine.setYear(year);
            bine.setMonth(month);
            bine.setDay(day);
            bine.setHour(hour);
            bine.setMinute(minute);
            bine.setSecond(second);
            bine.setChose(chose);
            bine.setPlanfile(planfile);
            Gson gson=new Gson();
            String s=gson.toJson(bine);
            //注册name以备查重
            FileWriter w2 = new FileWriter(path+"/allclockname.s",true);
            BufferedWriter writer2 = new BufferedWriter(w2);
            writer2.write(name);writer2.newLine();
            writer2.flush();writer2.close();
            w2.close();
            //写入
            FileWriter w = new FileWriter(path+"/allclocklist.json",true);
            BufferedWriter writer = new BufferedWriter(w);
            writer.write(s);
            writer.newLine();
            writer.flush();writer.close();
            w.close();
            if (chose&&!nowisrepeat(name)) {
                //注册name以备查重
                FileWriter w3 = new FileWriter(path+"/nowclockname.s",true);
                BufferedWriter writer3 = new BufferedWriter(w3);
                writer3.write(name);writer3.newLine();
                writer3.flush();writer3.close();
                w3.close();
                newnowtimeclock(s);
            }
        }

    }
    public void setTimenow(String name) throws IOException {
        Gson gson = new Gson();
        TimeclockBine bine;
        FileWriter w = new FileWriter(path+"/time.s");
        BufferedWriter writer = new BufferedWriter(w);
        FileReader r = new FileReader(path+"/allclocklist.json");
        BufferedReader reader = new BufferedReader(r);
        String s=null;
        while ((s=reader.readLine())!=null) {
            bine=gson.fromJson(s,TimeclockBine.class);
            if (bine.getName().equals(name)){
                Log.d("111", "setTimenow: 000");
              writer.write(s);
              writer.flush();writer.close();w.close();
              if (!nowisrepeat(name))newnowtimeclock(s);
              changestage(name,true);
              break;
            }
        }
    }
public void newnowtimeclock(String s) throws IOException {
    FileWriter w1 = new FileWriter(path+"/nowclocklist.json",true);
    BufferedWriter writer1 = new BufferedWriter(w1);
    writer1.write(s);
    writer1.newLine();
    writer1.flush();writer1.close();
    w1.close();
}


    //查重
    public boolean isrepeat(String name) throws IOException {
        File f =new File(path,"allclockname.s");
        if (!f.exists()) f.createNewFile();
        FileReader r = new FileReader(path+"/allclockname.s");
        BufferedReader reader = new BufferedReader(r);
        String s=null;
        while ((s=reader.readLine())!=null) {
                if (name.equals(s)) return true;
        }
            return false;

    }
    public boolean nowisrepeat(String name) throws IOException {
        File f =new File(path,"nowclockname.s");
        if (!f.exists()) f.createNewFile();
        FileReader r = new FileReader(path+"/nowclockname.s");
        BufferedReader reader = new BufferedReader(r);
        String s=null;
        while ((s=reader.readLine())!=null) {
            if (name.equals(s)) return true;
        }
        return false;
    }
    //改
    public void changestage(String name, boolean b) throws IOException {
        FileReader r = new FileReader(path+"/allclocklist.json");
        BufferedReader reader = new BufferedReader(r);
        TimeclockBine[] bines=new TimeclockBine[30];
        int i=0;
        Gson gson = new Gson();
        String s=null;
        while ((s=reader.readLine())!=null) {
            if(s.equals("null"))continue;
            else {
                bines[i] = gson.fromJson(s, TimeclockBine.class);
                if (name.equals(bines[i].getName())) {
                    bines[i].setChose(b);
                }
                i++;
            }
        }
        reader.close();r.close();
        FileWriter w = new FileWriter(path+"/allclocklist.json");
        BufferedWriter writer = new BufferedWriter(w);
        for (int k=0;k<bines.length;k++){
            if (bines[k]==null)continue;
            writer.write(gson.toJson(bines[k]));
            writer.newLine();
        }
        writer.flush();
        writer.close();
        w.close();
    }

    public void exchangeClock(String oldname,String name,int year,int month,int day,int hour,int minute,boolean chose) throws IOException {
        changeclockname(oldname,name,"allclockname.s");//改名查重
        FileReader r = new FileReader(path+"/allclocklist.json");
        BufferedReader reader = new BufferedReader(r);

        TimeclockBine[] bines=new TimeclockBine[100];
        int i=0;
        Gson gson = new Gson();
        String s=null;
        while ((s=reader.readLine())!=null) {
            if(s.equals("null"))continue;
            else {
                bines[i] = gson.fromJson(s, TimeclockBine.class);
                if (oldname.equals(bines[i].getName())) {
                    bines[i].setName(name);
                    bines[i].setYear(year);
                    bines[i].setMonth(month);
                    bines[i].setDay(day);
                    bines[i].setHour(hour);
                    bines[i].setMinute(minute);
                    bines[i].setChose(chose);
                }
                i++;
            }
        }
        reader.close();r.close();
        FileWriter w = new FileWriter(path+"/allclocklist.json");
        BufferedWriter writer = new BufferedWriter(w);
        for (int k=0;k<bines.length;k++){
            if (bines[k]==null)continue;
            writer.write(gson.toJson(bines[k]));
            writer.newLine();
        }
        writer.flush();
        writer.close();
        w.close();
        if (chose){
            if(!nowisrepeat(name)) {
                changeclockname(oldname,name,"nowclockname.s");
            }
            setTimenow(name);
        }
    }

    private void changeclockname(String oldname, String name,String pathname) throws IOException {
        FileReader r = new FileReader(path+"/"+pathname);
        BufferedReader reader = new BufferedReader(r);
        String []str=new String[30];
        int i=0;
        String s=null;
        while ((s=reader.readLine())!=null) {
            if(s.equals("null"))continue;
            else {
                str[i] =s;
                if (oldname.equals(s)){
                    str[i]=name;
                }
                i++;
            }
        }
        reader.close();r.close();
        FileWriter w = new FileWriter(path+"/"+pathname);
        BufferedWriter writer = new BufferedWriter(w);
        for (int k=0;k<str.length;k++){
            if (str[k]==null)continue;
            writer.write(str[k]);
            writer.newLine();
        }
        writer.flush();
        writer.close();
        w.close();
    }
    }

