package com.hzw.signcalendarprogect;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.zwcalendar.ZWCalendarView;
import com.hzw.zwcalendar.ZWSignCalendarView;
import com.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String stardate;
    private String yujidate;
    private void testvoid(){
        Mydatebasehelper dbhelper=new Mydatebasehelper(this,"Dataset.db",null,1);
        dbhelper.getReadableDatabase();
        setdate(setyear,setmonth,setday);
        chaxun();
        ddList.clear();
        panduanjieshu();
        initData();
    }
    private void jiluvoid(){
        Mydatebasehelper dbhelper=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor=db.query("Dataset",null,null,null,null,null,null);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String day1="";
        if(month<10)
        {
            day1+="0"+String.valueOf(month);
        }
        else day1=month+"";
        String pq=year+"-"+day1+"-"+day;
        boolean panduan=true;
        int id=0;
        String queren="";
        if(cursor.moveToFirst()){
            do{
                String begindate=cursor.getString(cursor.getColumnIndex("datebegin"));
                int pid=cursor.getInt(cursor.getColumnIndex("id"));
                String[] p=begindate.split(" ");
                for (int i=0;i<p.length;i++){
                    if(pq.equals(p[i])){
                        panduan=false;//还没结束
                        id=pid;
                        for(int qq=1;qq<=i;qq++){
                            queren+=" "+p[qq];
                        }
                        break;
                    }
                }
            }while (cursor.moveToNext()&&panduan);
        }
        cursor.close();
        db.close();
        Mydatebasehelper dbhelper1=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db1=dbhelper1.getReadableDatabase();
        String sql="delete from Dataset where id="+id;
        db1.execSQL(sql);
        db1.close();
        HashMap<String, Boolean> sign = new HashMap<>();
        sign.put("0000-00-00",true);
        calendarView.setSignRecords(sign);
        Mydatebasehelper dbhelper2=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db3 = dbhelper2.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("datebegin",queren);
        values.put("peace","1");
        db3.insert("Dataset",null,values);
        values.clear();

        chaxun();
    }

    private void panduanjieshu(){
        Mydatebasehelper dbhelper=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor=db.query("Dataset",null,null,null,null,null,null);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String day1="";
        if(month<10)
        {
            day1+="0"+String.valueOf(month);
        }
        else day1=month+"";
        String pq=year+"-"+day1+"-"+day;
        boolean panduan=true;
        if(cursor.moveToFirst()){
            do{
                String begindate=cursor.getString(cursor.getColumnIndex("datebegin"));
                String[] p=begindate.split(" ");
                for (int i=0;i<p.length;i++){
                    if(pq.equals(p[i])){
                        panduan=false;//还没结束
                        Toast.makeText(MainActivity.this,"宝贝，请问今天结束了吗",Toast.LENGTH_LONG).show();
                        stardate=p[1];
                        yujidate=p[p.length-1];
                        break;
                    }
                }
            }while (cursor.moveToNext()&&panduan);
        }
        cursor.close();
        if(!panduan){//还没结束

        }
        else{
            stardate="现在还没开始哟宝~";
            yujidate="现在还没开始哟~";
        }
    }
    private void setdate(int year,int month,int day){
        int[] mon={0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        HashMap<String, Boolean> sign = new HashMap<>();
        Mydatebasehelper dbhelper=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor=db.query("Dataset",null,null,null,null,null,null);
        String day11="";
        if(month<10)
        {
            day11+="0"+String.valueOf(month);
        }
        else day11=month+"";
        String pq=year+"-"+day11+"-"+day;
        int t=cursor.getCount();
        boolean panduan=true;
        if(cursor.moveToFirst()){
            do{
                String begindate=cursor.getString(cursor.getColumnIndex("datebegin"));
                String[] p=begindate.split(" ");
                for (int i=0;i<p.length;i++){
                    if(pq.equals(p[i])){
                        panduan=false;
                        Toast.makeText(MainActivity.this,"宝贝，是不是时间设置错了！",Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }while (cursor.moveToNext()&&panduan);
        }
        cursor.close();
        if(panduan){
            ContentValues values=new ContentValues();
            String date="";
            String dd="";
            if(year%4==0&&year%100!=0||year%400==0){
                mon[2]=29;
            }
            if(day+6<mon[month]){
                int y=year;
                int mm=month;
                String m="";
                int d=day;

                for (int i=0;i<7;i++){
                    if(mm<10)
                    {
                        m="0"+String.valueOf(mm);
                    }
                    else m=String.valueOf(mm);
                    if(d<10)
                    {
                        dd="0"+String.valueOf(d);
                    }
                    else dd=String.valueOf(d);
                    String pp=String.valueOf(y)+"-"+m+"-"+dd;
                    date+=" "+pp;
                    d++;
                }
            }
            else
            {
                if(month!=12){
                    int p=7-(mon[month]-day+1);
                    int y=year;
                    int mm=month;
                    String m=" ";
                    int d=day;
                    for (int i=0;i<mon[month]-day+1;i++)
                    {

                        if(month<10)
                        {
                            m="0"+String.valueOf(month);
                        }
                        else m=String.valueOf(month);
                        if(d<10)
                        {
                            dd="0"+String.valueOf(d);
                        }
                        else dd=String.valueOf(d);
                        String pp=String.valueOf(y)+"-"+m+"-"+dd;
                        date+=" "+pp;
                        d++;
                    }
                    mm++;
                    d=0;
                    for (int i=0;i<p;i++)
                    {
                        d++;
                        if(mm<10)
                        {
                            m="0"+String.valueOf(mm);
                        }
                        else m=String.valueOf(mm);
                        if(d<10)
                        {
                            dd="0"+String.valueOf(d);
                        }
                        else dd=String.valueOf(d);
                        String pp=String.valueOf(y)+"-"+m+"-"+dd;
                        date+=" "+pp;
                    }
                }
                else{
                    int p=7-(mon[month]-day+1);
                    int y=year;
                    int mm=month;
                    String m=" ";
                    int d=day;
                    for (int i=0;i<mon[month]-day+1;i++)
                    {

                        if(month<10)
                        {
                            m="0"+String.valueOf(month);
                        }
                        else m=String.valueOf(month);
                        if(d<10)
                        {
                            dd="0"+String.valueOf(d);
                        }
                        else dd=String.valueOf(d);
                        String pp=String.valueOf(y)+"-"+m+"-"+dd;
                        date+=" "+pp;
                        d++;
                    }
                    y++;
                    mm=1;
                    d=0;
                    for (int i=0;i<p;i++)
                    {
                        d++;
                        if(mm<10)
                        {
                            m="0"+String.valueOf(mm);
                        }
                        else m=String.valueOf(mm);
                        if(d<10)
                        {
                            dd="0"+String.valueOf(d);
                        }
                        else dd=String.valueOf(d);
                        String pp=String.valueOf(y)+"-"+m+"-"+dd;
                        date+=" "+pp;
                    }
                }

            }
            values.put("datebegin",date);
            values.put("peace","0");
            db.insert("Dataset",null,values);
            values.clear();
        }


    }//计划
    private void chaxun(){
        Mydatebasehelper dbhelper=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        Cursor cursor=db.query("Dataset",null,null,null,null,null,null);
        int t=cursor.getCount();
        HashMap<String, Boolean> sign = new HashMap<>();
        if(cursor.moveToFirst()){
            do{
                String begindate=cursor.getString(cursor.getColumnIndex("datebegin"));
                String peace=cursor.getString(cursor.getColumnIndex("peace"));

                String[] p=begindate.split(" ");
                for (int i=0;i<p.length;i++){
                    if(peace.equals("1")){
                        sign.put(p[i],true);
                    }
                    else sign.put(p[i],false);
                    calendarView.setSignRecords(sign);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


    }//查询日期
    private ZWCalendarView calendarView;
    private ZWSignCalendarView signCalendarView;
    private TextView show;
    private Calendar calendar = Calendar.getInstance();
    private int setyear,setmonth,setday;
    private String nearlydate;//设置最近一次时间


    private ListView listView;
    private List<DingDan> ddList = new ArrayList<DingDan>();

    private void initView(){
        listView = (ListView)findViewById(R.id.testlistview);
    }
    private void initData(){

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String day1="";
        if(month<10)
        {
            day1+="0"+String.valueOf(month);
        }
        else day1=month+"";
        String pq=year+"-"+day1+"-"+day;
        Mydatebasehelper helper=new Mydatebasehelper(this,"Dataset.db",null,1);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Dataset",null,null,null,null,null,null);
        Date sdate=new Date();
        try {
            java.text.SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            sdate = formatter.parse(pq);
            }
        catch (Exception e){

        }
        Datejisuan check=new Datejisuan();
        int min=1000;
        String mindex="";
        int ic=0;
        if(cursor.moveToFirst()){
            do{
                String begindate=cursor.getString(cursor.getColumnIndex("datebegin"));
                String[] p=begindate.split(" ");
                for (int i=0;i<p.length;i++){//i为0无数据
                    if(i>0){
                        try {
                            Date edate = (new SimpleDateFormat("yyyy-MM-dd")).parse(p[i]);
                            int qq=check.calInterval(edate,sdate,"d");
                            if(qq<min){
                                min=qq;
                                mindex=p[i];
                            }
                            ic++;
                            break;
                        }catch (Exception e){

                        }
                    }
                }

            }while (cursor.moveToNext());
        }
        nearlydate=mindex;
        cursor.close();
        setlistview();
        /*if(ic==0){
            DingDan dd=new DingDan();
            dd.setTupian(R.drawable.ic_baseline_4k_24);
            dd.setBianhao("暂无记录");
            dd.setXingming("暂无记录");
            dd.setDianhua("17390365410");
            dd.setDizhi("暂无记录");
            ddList.add(dd);
        }
        else{
            DingDan dd=new DingDan();
            dd.setTupian(R.drawable.ic_baseline_4k_24);
            dd.setBianhao(mindex);
            dd.setXingming(mindex);
            dd.setDianhua("17390365410");
            dd.setDizhi("暂无");
            ddList.add(dd);
        }
        ListCardAdapter adapter=new ListCardAdapter(MainActivity.this,ddList);
        listView = (ListView) findViewById(R.id.testlistview);
        listView.setAdapter(adapter);*/

        //模拟数据库存取数据。需要注意一点，调用数据库数据显示图片要用到bitmap转化。以后讨论。


    }//判断最近日期
    private void setlistview(){
        DingDan dd=new DingDan();
        dd.setTupian(R.drawable.ic_baseline_4k_24);
        dd.setBianhao(stardate);
        dd.setXingming(yujidate);
        dd.setDianhua("17390365410");
        dd.setDizhi(nearlydate);
        ddList.add(dd);
    ListCardAdapter adapter=new ListCardAdapter(MainActivity.this,ddList);
    listView = (ListView) findViewById(R.id.testlistview);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.setTitle("小宝的小脾气~");
        TextView mytext=(TextView) findViewById(R.id.mytext);
        shige shi=new shige();
        String shihehe="每日一诗："+shi.getshige();
        mytext.setText(shihehe);
        initView();
        panduanjieshu();
        //判断最近的一天并初始化
        initData();

        ListCardAdapter adapter=new ListCardAdapter(MainActivity.this,ddList);
        listView = (ListView) findViewById(R.id.testlistview);
        listView.setAdapter(adapter);
        panduanjieshu();
        //删除
        adapter.setOnItemDeleteClickListener(new ListCardAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                Toast.makeText(MainActivity.this,"你关不掉他，就像你心里丢不下我~",Toast.LENGTH_LONG).show();
                /*ddList.clear();
                ListCardAdapter adapter=new ListCardAdapter(MainActivity.this,ddList);
                listView = (ListView) findViewById(R.id.testlistview);
                listView.setAdapter(adapter);*/
            }
        });

        //卡片编辑事件代码
        adapter.setOnItemUpdateClickListener(new ListCardAdapter.onItemUpdateListener() {
            @Override
            public void onUpdateClick(int i) {
                Toast.makeText(MainActivity.this,"动动小手指点击下方电话号码联系田田哦",Toast.LENGTH_LONG).show();
            }
        });


        Button test=(Button) findViewById(R.id.qqmybuton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               testvoid();
            }
        });
        Button jilu=(Button) findViewById(R.id.mybutton);

        jilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jiluvoid();




            }
        });


        calendarView = (ZWCalendarView) findViewById(R.id.calendarView);
        show = (TextView) findViewById(R.id.tv_calendar_show);

        calendarView.setSelectListener(new ZWCalendarView.SelectListener() {
            @Override
            public void change(int year, int month) {
                show.setText(String.format("%s 年 %s 月", year, month));
            }

            @Override
            public void select(int year, int month, int day, int week) {
                setyear=year;
                setmonth=month;
                setday=day;
                //Toast.makeText(getApplicationContext(),
                       // String.format("%s 年 %s 月 %s日，周%s", year, month, day, week),
                       // Toast.LENGTH_SHORT).show();
            }
        });

        //代码选中一个日期
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.selectDate(2021, 4, 24);
            }
        });

        //前一月
        findViewById(R.id.calendar_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.showPreviousMonth();
            }
        });

        //后一月
        findViewById(R.id.calendar_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.showNextMonth();
            }
        });

        //返回今天
        findViewById(R.id.tv_calendar_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.backToday();
            }
        });

       chaxun();



        //
        //
        /*------------------------------------------------------------------------------*/
        //
        //



    }
}
