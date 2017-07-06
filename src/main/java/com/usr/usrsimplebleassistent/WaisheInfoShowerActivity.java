package com.usr.usrsimplebleassistent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usr.usrsimplebleassistent.bean.DianchiRequest;
import com.usr.usrsimplebleassistent.bean.WaisheRequest;
import com.usr.usrsimplebleassistent.dashboardviewdemo.DashboardView;
import com.usr.usrsimplebleassistent.dashboardviewdemo.HighlightCR;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WaisheInfoShowerActivity extends AppCompatActivity {

    private MyReceiver receiver=null;
    private TextView rtime;

    //====================仪表盘=====================
    private DashboardView dianjizhuansu;
    private DashboardView gongzuodianliu;

    //=============dianji_温度计==============
    private LinearLayout dianji_alcohol;
    private LinearLayout dianji_meter;
    private TextView dianji_thermo_c;
    private TextView dianji_thermo_f;
    public float dianji_staratemp = 0;
    public float dianji_temp;
    private float dianji_temperatureC;

    //=============kongzhiqi_温度计==============
    private LinearLayout kongzhiqi_alcohol;
    private LinearLayout kongzhiqi_meter;
    private TextView kongzhiqi_thermo_c;
    private TextView kongzhiqi_thermo_f;
    public float kongzhiqi_staratemp = 0;
    public float kongzhiqi_temp;
    private float kongzhiqi_temperatureC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waishe_info_shower);
        rtime = (TextView) findViewById(R.id.rtime23);

        //====================仪表盘=====================
        dianjizhuansu = (DashboardView) findViewById(R.id.dianjizhuansu);
        gongzuodianliu = (DashboardView) findViewById(R.id.gongzuodianliu);
        dianjizhuansu.setMaxValue(1000);
        dianjizhuansu.setHeaderTitle("RPM");
        dianjizhuansu.setBigSliceCount(10);
        gongzuodianliu.setMaxValue(10000);
        gongzuodianliu.setHeaderTitle("mA");
        gongzuodianliu.setBigSliceCount(10);
        List<HighlightCR> highlight1 = new ArrayList<>();
        highlight1.add(new HighlightCR(150, 100, Color.parseColor("#4CAF50")));
        highlight1.add(new HighlightCR(250, 80, Color.parseColor("#FFEB3B")));
        highlight1.add(new HighlightCR(330, 60, Color.parseColor("#F44336")));

        dianjizhuansu.setStripeHighlightColorAndRange(highlight1);
        gongzuodianliu.setStripeHighlightColorAndRange(highlight1);

        //===================dianji_温度计===================
        dianji_meter = ((LinearLayout) findViewById(R.id.dianji_meter));
        dianji_alcohol = ((LinearLayout) findViewById(R.id.dianji_alcohol));
        dianji_thermo_c = (TextView) findViewById(R.id.dianji_thermo_c);
        dianji_thermo_f = (TextView) findViewById(R.id.dianji_thermo_f);

        //===================kongzhiqi_温度计===================
        kongzhiqi_meter = ((LinearLayout) findViewById(R.id.kongzhiqi_meter));
        kongzhiqi_alcohol = ((LinearLayout) findViewById(R.id.kongzhiqi_alcohol));
        kongzhiqi_thermo_c = (TextView) findViewById(R.id.kongzhiqi_thermo_c);
        kongzhiqi_thermo_f = (TextView) findViewById(R.id.kongzhiqi_thermo_f);

        //注册广播接收器
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("waishereceiver");
        registerReceiver(receiver,filter);
    }

    /**
     * 获取华氏温度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     * @return
     */
    public float dianji_getTemperatureF() {
        float temperatureF = (dianji_temperatureC * 9 / 5) + 32;
        return getFloatOne(temperatureF);
    }

    /**
     * 保留一位小数点
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     * @param tempFloat
     * @return
     */
    public float getFloatOne(float tempFloat) {
        return (float) (Math.round(tempFloat * 10)) / 10;
    }

    /**
     * 获取摄氏温度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     * @return
     */
    public float dianji_getTemperatureC() {
        return getFloatOne(dianji_temperatureC);
    }


    public void setTemperatureC(float temperatureC) {
        this.dianji_temperatureC = temperatureC;
    }

    /**
     * 更新刻度上水银柱的长度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     */
    private void dianji_mUpdateUi() {
        ScaleAnimation localScaleAnimation1 = new ScaleAnimation(1.0F, 1.0F, this.dianji_staratemp, this.dianji_temp, 1, 0.5F, 1,
                1.0F);
        localScaleAnimation1.setDuration(6000L);
        localScaleAnimation1.setFillEnabled(true);
        localScaleAnimation1.setFillAfter(true);
        this.dianji_alcohol.startAnimation(localScaleAnimation1);
        this.dianji_staratemp = this.dianji_temp;

        ScaleAnimation localScaleAnimation2 = new ScaleAnimation(1.0F, 1.0F, 1.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation2.setDuration(10L);
        localScaleAnimation2.setFillEnabled(true);
        localScaleAnimation2.setFillAfter(true);
        this.dianji_meter.startAnimation(localScaleAnimation2);

        // 把刻度表看出总共700份，如何计算缩放比例。从-20°到50°。
        // 例如，现在温度是30°的话，应该占（30+20）*10=500份 其中20是0到-20°所占有的份
        this.dianji_temp = (float) ((20.0F + dianji_getTemperatureC()) * 10) / (70.0F * 10);

        dianji_thermo_c.setText(dianji_getTemperatureC() + "");
        dianji_thermo_f.setText(dianji_getTemperatureF() + "");
    }

    /**
     * 获取华氏温度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     * @return
     */
    public float kongzhiqi_getTemperatureF() {
        float temperatureF = (kongzhiqi_temperatureC * 9 / 5) + 32;
        return getFloatOne(temperatureF);
    }


    /**
     * 获取摄氏温度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     * @return
     */
    public float kongzhiqi_getTemperatureC() {
        return getFloatOne(kongzhiqi_temperatureC);
    }


    public void kongzhiqi_setTemperatureC(float temperatureC) {
        this.kongzhiqi_temperatureC = temperatureC;
    }

    /**
     * 更新刻度上水银柱的长度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     */
    private void kongzhiqi_mUpdateUi() {
        ScaleAnimation localScaleAnimation1 = new ScaleAnimation(1.0F, 1.0F, this.kongzhiqi_staratemp, this.kongzhiqi_temp, 1, 0.5F, 1,
                1.0F);
        localScaleAnimation1.setDuration(6000L);
        localScaleAnimation1.setFillEnabled(true);
        localScaleAnimation1.setFillAfter(true);
        this.kongzhiqi_alcohol.startAnimation(localScaleAnimation1);
        this.kongzhiqi_staratemp = this.kongzhiqi_temp;

        ScaleAnimation localScaleAnimation2 = new ScaleAnimation(1.0F, 1.0F, 1.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation2.setDuration(10L);
        localScaleAnimation2.setFillEnabled(true);
        localScaleAnimation2.setFillAfter(true);
        this.kongzhiqi_meter.startAnimation(localScaleAnimation2);

        // 把刻度表看出总共700份，如何计算缩放比例。从-20°到50°。
        // 例如，现在温度是30°的话，应该占（30+20）*10=500份 其中20是0到-20°所占有的份
        this.kongzhiqi_temp = (float) ((20.0F + kongzhiqi_getTemperatureC()) * 10) / (70.0F * 10);

        kongzhiqi_thermo_c.setText(kongzhiqi_getTemperatureC() + "");
        kongzhiqi_thermo_f.setText(kongzhiqi_getTemperatureF() + "");
    }

    /**
     * 获取广播数据
     *
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            WaisheRequest waishebean=(WaisheRequest)bundle.getSerializable("22");

            dianjizhuansu.setRealTimeValue(Float.parseFloat(waishebean.getMotorSpeed()));
            gongzuodianliu.setRealTimeValue(Float.parseFloat(waishebean.getWorkCurrent()));

            setTemperatureC(Float.parseFloat(waishebean.getMotorTemperature()));
            dianji_temp = (float) ((20.0F + dianji_getTemperatureC())*10 / (70.0F * 10));
            dianji_mUpdateUi();
            kongzhiqi_setTemperatureC(Float.parseFloat(waishebean.getControllerTemperature()));
            kongzhiqi_temp = (float) ((20.0F + kongzhiqi_getTemperatureC())*10 / (70.0F * 10));
            kongzhiqi_mUpdateUi();
            rtime.setText("（最近更新时间：\n"+ new SimpleDateFormat("yyyy年MM月dd日\nHH:mm:ss").format(new Date())+"）");
        }
    }
}
