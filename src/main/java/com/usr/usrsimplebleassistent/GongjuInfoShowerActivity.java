package com.usr.usrsimplebleassistent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usr.usrsimplebleassistent.bean.GongjuRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GongjuInfoShowerActivity extends AppCompatActivity {

    private MyReceiver receiver=null;
    private TextView BatteryIDtv;
    private TextView ChargerStatustv;
    private TextView rtimetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongju_info_shower);
        BatteryIDtv = (TextView)findViewById(R.id.BatteryIDtv);
        ChargerStatustv = (TextView)findViewById(R.id.ChargerStatustv);
        rtimetv = (TextView)findViewById(R.id.rtime_123);

        //注册广播接收器
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("gongjureceiver");
        registerReceiver(receiver,filter);
    }

    /**
     * 获取广播数据
     *
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            GongjuRequest gongjubean=(GongjuRequest)bundle.getSerializable("21");
            BatteryIDtv.setText("电池ID："+gongjubean.getBatteryID());
            if(gongjubean.getChargerStatus().trim().equals("00")){
                ChargerStatustv.setText("充电器状态：正常");
            }else{
                ChargerStatustv.setText("充电器状态：异常");
            }
            rtimetv.setText("最近更新：\n"+ new SimpleDateFormat("yyyy年MM月dd日\nHH:mm:ss").format(new Date()));

        }
    }
}
