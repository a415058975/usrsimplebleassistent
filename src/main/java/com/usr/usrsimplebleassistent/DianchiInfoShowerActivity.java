package com.usr.usrsimplebleassistent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usr.usrsimplebleassistent.bean.DianchiRequest;
import com.usr.usrsimplebleassistent.dashboardviewdemo.DashboardView;
import com.usr.usrsimplebleassistent.dashboardviewdemo.HighlightCR;
import com.usr.usrsimplebleassistent.library.WaveHelper;
import com.usr.usrsimplebleassistent.library.WaveView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DianchiInfoShowerActivity extends AppCompatActivity {

    private MyReceiver receiver=null;

    // 调试
    //private static final String TAG = "BluetoothChat";
    private static final boolean D = true;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    //从BluetoothChatService发送处理程序的消息类型
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    //==============电池=============
    private WaveHelper mWaveHelper;
    private WaveView waveView;
    private int mBorderColor = Color.parseColor("#c0c0c0");
    private int mBorderWidth = 10;
    private TextView TvLevel;
    private int bq = 0;
    private BatteryManager batteryManager;

    //=============温度计==============
    private LinearLayout alcohol;
    private LinearLayout meter;
    private TextView thermo_c;
    private TextView thermo_f;
    public float staratemp = 0;
    public float temp;
    private float temperatureC;

    //====================仪表盘=====================
    private DashboardView dashboardView1;

    //==============手机电池传感器================
    private static final String TAG = "F_B";
    private Button cdv;
    private TextView mTvVoltage;
    private TextView mTvTemperature;
    private TextView mTvLevel;
    private TextView mTvStatus;
    private TextView mTvHealth;
    private TextView mTvTechnology;
    private TextView mTitle;
    private TextView rtime1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianchi_info_shower);

        //==================电池==================
        TvLevel = (TextView) findViewById(R.id.battery_quantity);
        waveView = (WaveView) findViewById(R.id.wave);
        waveView.setBorder(5, mBorderColor);
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setWaveColor(Color.parseColor("#00FF00"),Color.parseColor("#00EE00"));
        mWaveHelper = new WaveHelper(waveView, 0);
        mWaveHelper.start();

        //===================温度计===================
        meter = ((LinearLayout) findViewById(R.id.meter));
        alcohol = ((LinearLayout) findViewById(R.id.alcohol));
        thermo_c = (TextView) findViewById(R.id.thermo_c);
        thermo_f = (TextView) findViewById(R.id.thermo_f);
//        setTemperatureC(getBatteryTemperature());
//        temp = (float) ((20.0F + getTemperatureC()) * 10) / (70.0F * 10);
//        mUpdateUi();
        //====================仪表盘=====================
        dashboardView1 = (DashboardView) findViewById(R.id.dashboard_view_2);
        dashboardView1.setMaxValue(70000);
        dashboardView1.setHeaderTitle("mV");
        dashboardView1.setBigSliceCount(7);

        List<HighlightCR> highlight1 = new ArrayList<>();
        highlight1.add(new HighlightCR(150, 100, Color.parseColor("#4CAF50")));
        highlight1.add(new HighlightCR(250, 80, Color.parseColor("#FFEB3B")));
        highlight1.add(new HighlightCR(330, 60, Color.parseColor("#F44336")));
        dashboardView1.setStripeHighlightColorAndRange(highlight1);
        //===================手机电池传感器================

        mTvVoltage = (TextView)findViewById(R.id.tv_voltage);
        mTvTemperature = (TextView)findViewById(R.id.tv_temperature);
        mTvLevel = (TextView)findViewById(R.id.tv_level);
        mTvStatus = (TextView)findViewById(R.id.tv_status);
        mTvHealth = (TextView)findViewById(R.id.tv_health);
        mTvTechnology = (TextView)findViewById(R.id.tv_technology);
        mTitle = (TextView) findViewById(R.id.title_right_text);
        rtime1 = (TextView) findViewById(R.id.rtime1);

        //getActivity().startService(new Intent(getActivity(), MyService.class));
        //注册广播接收器
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("dianchireceiver");
        registerReceiver(receiver,filter);
    }


    @Override
    public void onDestroy() {
        //结束服务
        // getActivity().stopService(new Intent(getActivity(), MyService.class));
        super.onDestroy();
    }


    /**
     * 获取华氏温度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     * @return
     */
    public float getTemperatureF() {
        float temperatureF = (temperatureC * 9 / 5) + 32;
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
    public float getTemperatureC() {
        return getFloatOne(temperatureC);
    }


    public void setTemperatureC(float temperatureC) {
        this.temperatureC = temperatureC;
    }


    /**
     * 更新刻度上水银柱的长度
     *
     * @author 熊猫丁浩
     * @date 2017年5月11日
     */
    private void mUpdateUi() {
        ScaleAnimation localScaleAnimation1 = new ScaleAnimation(1.0F, 1.0F, this.staratemp, this.temp, 1, 0.5F, 1,
                1.0F);
        localScaleAnimation1.setDuration(6000L);
        localScaleAnimation1.setFillEnabled(true);
        localScaleAnimation1.setFillAfter(true);
        this.alcohol.startAnimation(localScaleAnimation1);
        this.staratemp = this.temp;

        ScaleAnimation localScaleAnimation2 = new ScaleAnimation(1.0F, 1.0F, 1.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation2.setDuration(10L);
        localScaleAnimation2.setFillEnabled(true);
        localScaleAnimation2.setFillAfter(true);
        this.meter.startAnimation(localScaleAnimation2);

        // 把刻度表看出总共700份，如何计算缩放比例。从-20°到50°。
        // 例如，现在温度是30°的话，应该占（30+20）*10=500份 其中20是0到-20°所占有的份
        this.temp = (float) ((20.0F + getTemperatureC()) * 10) / (70.0F * 10);

        thermo_c.setText(getTemperatureC() + "");
        thermo_f.setText(getTemperatureF() + "");
    }

    /**
     * 获取广播数据
     *
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            DianchiRequest dianchibean=(DianchiRequest)bundle.getSerializable("20");
            dashboardView1.setRealTimeValue(Float.parseFloat(dianchibean.getTotalVoltage()));
            //更新电池电量
            waveView.setWaterLevelRatio(Integer.parseInt(dianchibean.getSocElePercentage())/100f);
            TvLevel.setText(Integer.parseInt(dianchibean.getSocElePercentage()) + "%");
            //更新温度计
            setTemperatureC(Float.parseFloat(dianchibean.getMaxTemperature()));
            temp = (float) ((20.0F + getTemperatureC())*10 / (70.0F * 10));
            mUpdateUi();
            mTitle.setText("电池ID："+dianchibean.getBatteryID());
            mTvVoltage.setText("电池串数："+dianchibean.getBatteryString()+"串");
            if(dianchibean.getBatteryStatus().trim().equals("00")){
                mTvTemperature.setText("电池状态：放电");
            }else{
                mTvTemperature.setText("电池状态：充电");
            }

            mTvLevel.setText("电流："+dianchibean.getElectricity()+"mA");
            mTvStatus.setText("寿命剩余："+dianchibean.getResiduallife()+"H");
            if(dianchibean.getBatteryLockStatus().trim().equals("00")){
                mTvHealth.setText("电池锁：开启");
            }else{
                mTvHealth.setText("电池锁：关闭");
            }
            mTvTechnology.setText("充放电总计："+dianchibean.getCumulativeNum()+"次");
            rtime1.setText("（最近更新时间：\n"+ new SimpleDateFormat("yyyy年MM月dd日\nHH:mm:ss").format(new Date())+"）");

        }
    }

}
