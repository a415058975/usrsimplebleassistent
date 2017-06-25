package com.usr.usrsimplebleassistent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.usr.usrsimplebleassistent.BlueToothLeService.BluetoothLeService;
import com.usr.usrsimplebleassistent.Utils.AnimateUtils;
import com.usr.usrsimplebleassistent.Utils.Constants;
import com.usr.usrsimplebleassistent.Utils.GattAttributes;
import com.usr.usrsimplebleassistent.Utils.Utils;
import com.usr.usrsimplebleassistent.Utils.httpconnectutil;
import com.usr.usrsimplebleassistent.Utils.xmlutil;
import com.usr.usrsimplebleassistent.adapter.MessagesAdapter;
import com.usr.usrsimplebleassistent.adapter.OptionsSelectAdapter;
import com.usr.usrsimplebleassistent.bean.DengluRequest;
import com.usr.usrsimplebleassistent.bean.DianchiRequest;
import com.usr.usrsimplebleassistent.bean.DianchiRespones;
import com.usr.usrsimplebleassistent.bean.GongjuRequest;
import com.usr.usrsimplebleassistent.bean.GongjuRespones;
import com.usr.usrsimplebleassistent.bean.Message;
import com.usr.usrsimplebleassistent.bean.Option;
import com.usr.usrsimplebleassistent.bean.WaisheRequest;
import com.usr.usrsimplebleassistent.bean.WaisheRespones;
import com.usr.usrsimplebleassistent.views.OptionsMenuManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class GattDetailActivity extends MyBaseActivity {

    @Bind(R.id.btn_options)
    ImageButton btnOptions;
    @Bind(R.id.btn_option)
    Button btnOption;
    @Bind(R.id.lv_msg)
    RecyclerView rvMsg;
    @Bind(R.id.tv_properties)
    TextView tvProperties;
//    @Bind(R.id.et_write)
//    EditText etWrite;
//    @Bind(R.id.btn_send)
//    Button btnSend;
    @Bind(R.id.rl_write)
    LinearLayout rlWrite;
    @Bind(R.id.rl_content)
    RelativeLayout rlContent;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.view_bottom_shadow)
    View bottomShadow;
    @Bind(R.id.view_top_shadow)
    View topShadow;
    @Bind(R.id.view_filter)
    View filterView;

    private boolean issendable = true;
    private int getcountc ;
    private String gjid ="";
    private String gjst ="";
    private String tmp ="";
    private byte receive[] = {};
    private char receivedindex=0;

    private DianchiRequest dianchibean;
    private DianchiRespones dcrprs;
    private GongjuRequest gongjubean;
    private GongjuRespones gjrprs ;
    private WaisheRequest waishebean;
    private WaisheRespones wsrprs ;

    //private byte[] dianchib=new byte[512];
    private List<Byte> received ;
    private int dianchiblen = 0;
    private int dianchibindex = 0;
    private byte nowsento ;


    private final int dianchi =0x20;
    private final int gongju = 0x21;
    private final int waishe = 0x22;
    private int command = dianchi;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private int count ;
    private boolean isPause = false;
    private boolean isStop = true;
    private static int delay = 3000;  //1s
    private static int period = 35000;  //1s

    private Handler mmHandler  = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case dianchi:
                    byte b[] = {0x02,0x00,0x01,0x20,0x12,0x03};
                    writeOption(b);
                    break;
                case gongju:
                    byte b2[] = {0x02,0x00,0x01,0x21,0x13,0x03};
                    writeOption(b2);
                    break;
                case waishe:
                    byte b3[] = {0x02,0x00,0x01,0x22,0x10,0x03};
                    writeOption(b3);
                    break;
                default:
                    break;
            }
        }
    };



    private Handler houtaifeedback = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 1){
                Message mesg = new Message(Message.MESSAGE_TYPE.SEND,"电池存入后台成功");
                notifyAdapter(mesg);
            }else if(msg.what == 2){
                Message mesg = new Message(Message.MESSAGE_TYPE.SEND,"工具存入后台成功");
                notifyAdapter(mesg);
            }else if(msg.what == 3){
                Message mesg = new Message(Message.MESSAGE_TYPE.SEND,"外设存入后台成功");
                notifyAdapter(mesg);
            }else if(msg.what == 0){
                Message mesg = new Message(Message.MESSAGE_TYPE.SEND,"网络不通畅，已挂起到后台！");
                notifyAdapter(mesg);
            }else if(msg.what == -1){
                Message mesg = new Message(Message.MESSAGE_TYPE.SEND,"发生错误"+msg.getData().getString("Reason"));
                notifyAdapter(mesg);

            }
        }

    };

    private final List<Message> list = new ArrayList<>();

    private MessagesAdapter adapter;

    private BluetoothGattCharacteristic notifyCharacteristic;
    private BluetoothGattCharacteristic readCharacteristic;
    private BluetoothGattCharacteristic writeCharacteristic;
    private BluetoothGattCharacteristic indicateCharacteristic;

    private MyApplication myApplication;
    private String properties;
    private OptionsMenuManager optionsMenuManager;

    private List<Option> options = new ArrayList<>();
    private Option currentOption;

    private boolean isHexSend;

    private boolean nofityEnable;
    private boolean indicateEnable;
    private boolean isDebugMode;

    public int whichoperation ;
    public boolean istouchuan = false;
    public String alertwhat;

    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            //There are four basic operations for moving data in BLE: read, write, notify,
            // and indicate. The BLE protocol specification requires that the maximum data
            // payload size for these operations is 20 bytes, or in the case of read operations,
            // 22 bytes. BLE is built for low power consumption, for infrequent short-burst data transmissions.
            // Sending lots of data is possible, but usually ends up being less efficient than classic Bluetooth
            // when trying to achieve maximum throughput.  从google查找的，解释了为什么android下notify无法解释超过
            //20个字节的数据
            Bundle extras = intent.getExtras();
            if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                // Data Received
                if (extras.containsKey(Constants.EXTRA_BYTE_VALUE)) {
                    if (extras.containsKey(Constants.EXTRA_BYTE_UUID_VALUE)) {
                        if (myApplication != null) {
                            BluetoothGattCharacteristic requiredCharacteristic = myApplication.getCharacteristic();
                            String uuidRequired = requiredCharacteristic.getUuid().toString();
                            String receivedUUID = intent.getStringExtra(Constants.EXTRA_BYTE_UUID_VALUE);
                            //getcount++;
                            if (isDebugMode){
                                byte[] array = intent.getByteArrayExtra(Constants.EXTRA_BYTE_VALUE);
                                //System.out.println("erwerrvwerw:"+getcountc+":"+Utils.ByteArraytoHex(array));
                                //for(int i=0;i<array.length;i++){
                                    //System.arraycopy(receive,0,array,0,length);
                                    //System.out.println("数组:"+array[i]);
                                    System.arraycopy(array, 0, receive, receivedindex, array.length);
                                    //receive[receivedindex++] = array[i];
                                    System.out.println("当前长度："+receivedindex);
                               // }
                                System.out.println("in:"+getcountc+":"+Utils.ByteArraytoHex(array));
                                //getcountc++;
                                if(getcountc == 0 ){
                                    //gjid="";
                                    gjst="";
                                    receive=null;
                                    dianchibean = new DianchiRequest();
                                    gongjubean = new GongjuRequest();
                                    waishebean = new WaisheRequest();
                                }

                                getcountc++;
                                if(getcountc == 4){
                                    alertwhat = Utils.ByteArraytoHex(array);
                                }
                                if(getcountc == 5){
                                    if(Utils.ByteArraytoHex(array).trim().equals("20")){
                                        whichoperation = 20;
                                    }else if(Utils.ByteArraytoHex(array).trim().equals("21")){
                                        whichoperation = 21;
                                    }else if(Utils.ByteArraytoHex(array).trim().equals("22")){
                                        whichoperation = 22;
                                    }
                                   // System.out.println(getcountc+":whichoperation:"+Utils.ByteArraytoHex(array));
                                    //whichoperation = Integer.parseInt(Utils.byteToASCII(array).trim());
                                    //System.out.println(getcountc+":whichoperation:"+whichoperation);
                                }
                                if(getcountc == 6){
                                    if(Utils.ByteArraytoHex(array).trim().equals("00")){
                                        istouchuan = true;
                                    }
                                    if(Utils.ByteArraytoHex(array).trim().equals("01")){
                                        istouchuan = false;
                                    }
                                    // System.out.println(getcountc+":whichoperation:"+Utils.ByteArraytoHex(array));
                                    //whichoperation = Integer.parseInt(Utils.byteToASCII(array).trim());
                                    //System.out.println(getcountc+":whichoperation:"+whichoperation);
                                }
                                if(whichoperation == 20){
                                  //if(true){
                                    if(getcountc >= 7 && getcountc <= 22){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 22){
                                            //dianchibean.setBatteryID(Utils.ByteArrToIntStr(tmp.getBytes()));
                                            dianchibean.setBatteryID(""+tmp.replaceAll(" ", ""));
                                            tmp= "";
                                        }
                                    }
                                    if(getcountc == 23){
                                        dianchibean.setBatteryString(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc == 24){
                                        dianchibean.setBatteryStatus(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc >= 25 && getcountc <= 28){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 28){
                                            dianchibean.setTotalVoltage(""+Integer.parseInt(tmp.replaceAll(" ", ""),16));
                                            tmp = "";
                                        }
                                    }
                                    if(getcountc == 29){
                                        dianchibean.setSocElePercentage(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc >=30 && getcountc <=33){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 33){
                                            dianchibean.setElectricity(""+Long.parseLong(tmp.replaceAll(" ", ""),16));
                                            tmp = "";
                                        }
                                    }
                                    if(getcountc >=34 && getcountc <=35){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 35){
                                            dianchibean.setResiduallife(""+Long.parseLong(tmp.replaceAll(" ", ""),16));
                                            tmp = "";
                                        }
                                    }
                                    if(getcountc == 36){
                                        dianchibean.setMaxTemperature(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc >=37 && getcountc <=116){
                                        tmp += Utils.byteToASCII(array);
                                        if(getcountc == 116){
                                            dianchibean.setMonomerVoltage(""+tmp.replaceAll(" ", ""));
                                            tmp = "";
                                        }
                                    }
                                    if(getcountc >=117 && getcountc <=126){
                                        tmp += Utils.byteToASCII(array);
                                        if(getcountc == 126){
                                            dianchibean.setSensorTemperature(""+tmp.replaceAll(" ", ""));
                                            tmp = "";
                                        }
//                                        tmp += Utils.ByteArraytoHex(array);
//                                        if(getcountc == 126){
//                                            dianchibean.setSensorTemperature(""+tmp.replaceAll(" ", ""));
//                                            tmp = "";
//                                        }
                                    }
                                    if(getcountc == 127){
                                        dianchibean.setBatteryLockStatus(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc >=128 && getcountc <=129){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 129){
                                            dianchibean.setCumulativeNum(""+Long.parseLong(tmp.replaceAll(" ", ""),16));
                                            tmp = "";
                                        }
                                    }
                                    if(getcountc == 130){
                                        dianchibean.setBatteryPackVs(""+Utils.ByteArrToIntStr(array));
                                    }if(getcountc == 131){
                                        System.out.println("接受结果："+getcountc);
                                    }if(getcountc == 132){
                                        getcountc = 0;
                                        for (byte b : receive) {
                                            System.out.println(""+b);
                                        }
                                        String showresult = "";
                                        showresult += "电池ID:" + dianchibean.getBatteryID().trim() + "\n";
                                        showresult += "电池串数:" + dianchibean.getBatteryString().trim() + "\n";
                                        if(dianchibean.getBatteryStatus().trim().equals("00")){
                                            showresult += "当前电池状态:断电" + "\n";
                                        }else{
                                            showresult += "当前电池状态:充电" + "\n";
                                        }
                                        showresult += "当前总电压:" + dianchibean.getTotalVoltage().trim() + "mV\n";
                                        showresult += "当前SOC电量百分比:" + dianchibean.getSocElePercentage().trim() + "%\n";
                                        showresult += "当前电流:" + dianchibean.getElectricity().trim() + "ma\n";
                                        showresult += "当前剩余寿命:" + dianchibean.getResiduallife().trim() + "\n";
                                        showresult += "当前最高温度:" + dianchibean.getMaxTemperature() + "℃\n";
                                        showresult += "单体电压:" + dianchibean.getMonomerVoltage().trim().replaceAll("(.{2})","$1 ") + "(单位:mv)\n";
                                        showresult += "传感器温度:" + dianchibean.getSensorTemperature().trim() + "(单位:℃)\n";
                                        showresult += "电池锁状态:" + dianchibean.getBatteryLockStatus() + "\n";
                                        showresult += "充放电累计:" + dianchibean.getCumulativeNum() + "\n";
                                        showresult += "电池包固件版本:" + dianchibean.getBatteryPackVs() + "\n";
                                        System.out.println("aaaaaaaaa"+dianchibean.getTotalVoltage().trim());
                                        dianchibean.setPosCode("01");
                                        //System.out.println("编译结果:"+getcountc);
                                        if(!istouchuan){
                                            Message msg2 = new Message(Message.MESSAGE_TYPE.SEND,showresult);
                                            notifyAdapter(msg2);
                                        }

                                        new Thread(sendDianchiThread).start();
                                        Message msg3 = new Message(Message.MESSAGE_TYPE.SEND,"正在上传到服务器...");
                                        notifyAdapter(msg3);
                                    }
                                }else if(whichoperation == 21){
                                    if(getcountc >= 7 && getcountc <= 22){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 22){
                                            gongjubean.setBatteryID(""+tmp.replaceAll(" ", ""));
                                            tmp= "";
                                        }
                                    }
                                    if(getcountc == 23){
                                        gongjubean.setChargerStatus(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc == 25){
                                        getcountc = 0;
                                        String showresult = "";
                                        showresult += "工具/充电器ID:" + gongjubean.getBatteryID().trim() + "\n";
                                        if(gongjubean.getChargerStatus().trim().equals("00")){
                                            showresult += "当前工具/充电器状态:断电" + "\n";
                                        }else{
                                            showresult += "当前工具/充电器状态:充电" + "\n";
                                        }
                                        System.out.println("编译结果:"+getcountc);
                                        //System.out.println("showresult"+showresult);
                                        if(!istouchuan){
                                            Message msg2 = new Message(Message.MESSAGE_TYPE.SEND,showresult);
                                            notifyAdapter(msg2);
                                        }
                                        gongjubean.setPosCode("01");
                                        new Thread(sendGongjuThread).start();
                                        Message msg3 = new Message(Message.MESSAGE_TYPE.SEND,"正在上传到服务器...");
                                        notifyAdapter(msg3);
                                    }
                                }else if(whichoperation == 22){
                                    if(getcountc == 7){
                                        waishebean.setPeriFirmwareVs(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc == 8){
                                        waishebean.setMotorTemperature(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc >= 9 && getcountc <= 10){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 10){
                                            waishebean.setMotorSpeed(""+Long.parseLong(tmp.replaceAll(" ", ""),16));
                                            tmp= "";
                                        }
                                    }
                                    if(getcountc == 11){
                                        waishebean.setControllerTemperature(""+Utils.ByteArrToIntStr(array));
                                    }
                                    if(getcountc >= 12 && getcountc <= 13){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 13){
                                            waishebean.setWorkCurrent(""+Long.parseLong(tmp.replaceAll(" ", ""),16));
                                            tmp= "";
                                        }
                                    }
                                    if(getcountc >= 14 && getcountc <= 112){
                                        tmp += Utils.ByteArraytoHex(array);
                                        if(getcountc == 112){
                                            waishebean.setReservedBit(""+tmp.replaceAll(" ", ""));
                                            tmp= "";
                                        }
                                    }
                                    if(getcountc == 114){
                                        getcountc = 0;
                                        String showresult = "";
                                        showresult += "外设固件版本:" + waishebean.getPeriFirmwareVs().trim() + "\n";
                                        showresult += "电机温度:" + waishebean.getMotorTemperature().trim() + "\n";
                                        showresult += "电机转速:" + waishebean.getMotorSpeed().trim() + "\n";
                                        showresult += "控制器温度:" + waishebean.getControllerTemperature().trim() + "\n";
                                        showresult += "工作电流:" + waishebean.getWorkCurrent().trim() + "\n";
                                        showresult += "保留位、扩展位:" + waishebean.getReservedBit().trim() + "\n";
                                        System.out.println("编译结果:"+getcountc);
                                        if(!istouchuan) {
                                            Message msg2 = new Message(Message.MESSAGE_TYPE.SEND, showresult);
                                            notifyAdapter(msg2);
                                        }
                                        waishebean.setPosCode("01");
                                        new Thread(sendWaisheThread).start();
                                        Message msg3 = new Message(Message.MESSAGE_TYPE.SEND,"正在上传到服务器...");
                                        notifyAdapter(msg3);
                                    }

                                }


//
//
//                                if(getcountc == 6){
//                                    if((""+array).trim().equals("00")){
//                                        Message msg2 = new Message(Message.MESSAGE_TYPE.SEND,"电池状态：断电");
//                                        notifyAdapter(msg2);
//                                    }else if((""+array).trim().equals("01")){
//                                        Message msg2 = new Message(Message.MESSAGE_TYPE.SEND,"电池状态：充电");
//                                        notifyAdapter(msg2);
//                                    }
//                                }
//                                if(getcountc == 7){
//                                    Message msg = new Message(Message.MESSAGE_TYPE.SEND,"电池已充电"+Utils.ByteArrToIntStr(array)+"次");
//                                    notifyAdapter(msg);
//                                }
//                                if(getcountc == 8){
//                                    Message msg = new Message(Message.MESSAGE_TYPE.SEND,"电池电压"+Utils.ByteArrToIntStr(array)+"V");
//                                    notifyAdapter(msg);
//                                }
//                                if(getcountc == 9){
//                                    gjst += ""+Utils.byteToASCII(array);
//                                }
//                                if(getcountc == 10){
//                                    gjst += ""+Utils.byteToASCII(array);
//                                    Message msg = new Message(Message.MESSAGE_TYPE.SEND,"电池剩余寿命"+Utils.ByteArrToIntStr(gjst.getBytes())+"小时");
//                                    notifyAdapter(msg);
//                                }
//                                if(getcountc == 12){
//                                    System.out.println("in:"+getcountc+":"+Utils.ByteArraytoHex(array));
//                                    //gjst = ""+ Utils.byteToASCII(array);
//                                    getcountc = 0;
//                                    gjrq = new GongjuRequest("01",""+gjst,""+gjst);
//                                    Message msg = new Message(Message.MESSAGE_TYPE.SEND,"上传数据到后台服务器");
//                                    notifyAdapter(msg);
//                                    new Thread(sendGongjuThread).start();
//                                }



//
//                                if(nowsento == 0x20 && getcount == 3){
//                                    dianchiblen = Integer.parseInt(""+array[0], 16);
//                                }
//                                if(nowsento == 0x20 && getcount  > 3 && getcount < (4+dianchiblen)){
//                                    dianchib[dianchibindex++] = array[0];
//                                }
//                                if(nowsento == 0x20 && getcount  == 4+dianchiblen){
//
//                                }if(nowsento == 0x20 && getcount  == 5+dianchiblen){
//                                    if(array[0] == 0x03){
//                                        issendable = true;
//                                    }else{
//                                        dianchib = new byte[256];
//                                        dianchibindex = 0;
//                                        nowsento = 0x00;
//                                        Message msg = new Message(Message.MESSAGE_TYPE.SEND,"校验结果错误！");
//                                        //new Thread(sendGongjuThread).start();
//                                        notifyAdapter(msg);
//                                        issendable = true;
//                                    }
//                                }


//                                Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,formatMsgContent(array));
//                                notifyAdapter(msg);
//                                if(getcountc == 25){
//                                    getcountc = 0;
//                                    if(gjst.trim().equals("00")){
//                                        Message msg2 = new Message(Message.MESSAGE_TYPE.SEND,"电池id:\n"+gjid+"\n电池状态：断电");
//                                        notifyAdapter(msg2);
//                                    }else if(gjst.trim().equals("01")){
//                                        Message msg2 = new Message(Message.MESSAGE_TYPE.SEND,"电池id:\n"+gjid+"\n电池状态：充电");
//                                        notifyAdapter(msg2);
//                                    }
//                                    new Thread(sendGongjuThread).start();
//                                    Message msg3 = new Message(Message.MESSAGE_TYPE.SEND,"正在上传到服务器...");
//                                    notifyAdapter(msg3);
//                                }
                            }else if (uuidRequired.equalsIgnoreCase(receivedUUID)) {
                                byte[] array = intent.getByteArrayExtra(Constants.EXTRA_BYTE_VALUE);
                                Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,formatMsgContent(array,MyApplication.serviceType));
                                notifyAdapter(msg);
                            }
                        }
                    }
                }
                if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE)) {
                    if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID)) {
                        BluetoothGattCharacteristic requiredCharacteristic = myApplication.
                                getCharacteristic();
                        String uuidRequired = requiredCharacteristic.getUuid().toString();
                        String receivedUUID = intent.getStringExtra(
                                Constants.EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID);

                        byte[] array = intent
                                .getByteArrayExtra(Constants.EXTRA_DESCRIPTOR_BYTE_VALUE);

//                        System.out.println("GattDetailActivity---------------------->descriptor:" + Utils.ByteArraytoHex(array));
                        if (isDebugMode){
                            updateButtonStatus(array);
                        }else if (uuidRequired.equalsIgnoreCase(receivedUUID)) {
                            updateButtonStatus(array);
                        }

                    }
                }
            }

            if (action.equals(BluetoothLeService.ACTION_GATT_DESCRIPTORWRITE_RESULT)){
                if (extras.containsKey(Constants.EXTRA_DESCRIPTOR_WRITE_RESULT)){
                    int status = extras.getInt(Constants.EXTRA_DESCRIPTOR_WRITE_RESULT);
                    if (status != BluetoothGatt.GATT_SUCCESS){
                        Snackbar.make(rlContent,R.string.option_fail,Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            if (action.equals(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_ERROR)) {
                if (extras.containsKey(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE)) {
                    String errorMessage = extras.
                            getString(Constants.EXTRA_CHARACTERISTIC_ERROR_MESSAGE);
                    System.out.println("GattDetailActivity---------------------->err:" + errorMessage);
                    showDialog(errorMessage);
                }

            }

            //write characteristics succcess
            if (action.equals(BluetoothLeService.ACTION_GATT_CHARACTERISTIC_WRITE_SUCCESS)){
                list.get(list.size()-1).setDone(true);
                adapter.notifyItemChanged(list.size()-1);
            }

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
//                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
//                if (state == BluetoothDevice.BOND_BONDING) {}
//                else if (state == BluetoothDevice.BOND_BONDED) {}
//                else if (state == BluetoothDevice.BOND_NONE) {}
            }

            //connect break (连接断开)
            if (action.equals(BluetoothLeService.ACTION_GATT_DISCONNECTED)){
                showDialog(getString(R.string.conn_disconnected));
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatt_detail);
        ButterKnife.bind(this);
        bindToolBar();
        myApplication = (MyApplication) getApplication();
        optionsMenuManager = OptionsMenuManager.getInstance();



        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvMsg.setLayoutManager(llm);

        adapter = new MessagesAdapter(this, list);
        rvMsg.setAdapter(adapter);

        initCharacteristics();
        initProperties();

        registerReceiver(mGattUpdateReceiver, Utils.makeGattUpdateIntentFilter());

        rvMsg.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (optionsMenuManager.getOptionsMenu()!=null)
                    dismissMenu();
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        if (savedInstanceState == null) {
            filterView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    filterView.getViewTreeObserver().removeOnPreDrawListener(this);
                    startEndAnimation();
                    return true;
                }
            });
        }

        int sdkInt = Build.VERSION.SDK_INT;
        System.out.println("sdkInt------------>"+sdkInt);
        if (sdkInt>=21){
            //设置最大发包、收包的长度为512个字节
            if(BluetoothLeService.requestMtu(512)){
                Toast.makeText(this,getString(R.string.transmittal_length,"512"),Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(this,getString(R.string.transmittal_length,"20"),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,getString(R.string.transmittal_length,"20"),Toast.LENGTH_LONG).show();
        }
    }

    private void initCharacteristics(){
        BluetoothGattCharacteristic characteristic = myApplication.getCharacteristic();
        if (characteristic.getUuid().toString().equals(GattAttributes.USR_SERVICE)){
            isDebugMode = true;
            List<BluetoothGattCharacteristic> characteristics = ((MyApplication)getApplication()).getCharacteristics();

            for (BluetoothGattCharacteristic c :characteristics){
                if (Utils.getPorperties(this,c).equals("Notify")){
                    notifyCharacteristic = c;
                    continue;
                }

                if (Utils.getPorperties(this,c).equals("Write")){
                    writeCharacteristic = c;
                    continue;
                }
            }

            properties = "Notify & Write";

        }else {
            properties = Utils.getPorperties(this, characteristic);

            notifyCharacteristic = characteristic;
            readCharacteristic = characteristic;
            writeCharacteristic = characteristic;
            indicateCharacteristic = characteristic;
        }
    }


    private void initProperties() {
        if (TextUtils.isEmpty(properties))
            return;
        tvProperties.setText(properties);
        String[] property = properties.split("&");

        if (property.length == 1) {
            btnOptions.setVisibility(View.GONE);
            Option option = new Option(properties.trim(),Option.OPTIONS_MAP.get(properties.trim()));
            setOption(option);
        } else {
            for (int i=0;i<property.length;i++){
                String p = property[i];
                Option option = new Option();
                option.setName(p.trim());
                option.setPropertyType(Option.OPTIONS_MAP.get(p.trim()));
                options.add(option);
                if (i==0){
                  setOption(option);
                }
            }
        }
    }


    private void setOption(Option option){
        currentOption = option;
        switch (option.getPropertyType()){
            case PROPERTY_NOTIFY:
                if (!nofityEnable)
                    btnOption.setText(Option.NOTIFY);
                else
                    btnOption.setText(Option.STOP_NOTIFY);
                showViewIsEdit(false);
                break;
            case PROPERTY_READ:
                btnOption.setText(Option.READ);
                showViewIsEdit(false);
                break;
            case PROPERTY_INDICATE:
                if (!indicateEnable)
                   btnOption.setText(Option.INDICATE);
                else
                   btnOption.setText(Option.STOP_INDICATE);
                showViewIsEdit(false);
                break;
            case PROPERTY_WRITE:
                showViewIsEdit(true);
                break;
        }
    }


    private void showViewIsEdit(boolean isEdit){
        if (isEdit){
            btnOption.setVisibility(View.GONE);
            rlWrite.setVisibility(View.VISIBLE);
        }else {
            btnOption.setVisibility(View.VISIBLE);
            rlWrite.setVisibility(View.GONE);
        }
    }



    @OnClick(R.id.btn_options)
    public void onOptionsClick() {
        optionsMenuManager.toggleContextMenuFromView(options, btnOptions, new OptionsSelectAdapter.OptionsOnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                dismissMenu();
                setOption(options.get(position));
            }
        });
    }


    @OnClick(R.id.btn_option)
    public void onOptionClick() {
        if (optionsMenuManager.getOptionsMenu()!=null){
            dismissMenu();
            return;
        }
        switch (currentOption.getPropertyType()){
            case PROPERTY_NOTIFY:
                notifyOption();
                break;
            case PROPERTY_INDICATE:
                indicateOption();
                break;
            case PROPERTY_READ:
                readOption();
                break;
            case PROPERTY_WRITE:
                break;
        }
    }

//三个按钮
    @OnClick(R.id.btn_send20)
    public void onSend20Click(){
        if(isStop  == true){
            command = dianchi;
            startTimer();
            isStop =false;
        }else{
            stopTimer();
            command = dianchi;
            isStop = true;
            startTimer();
            isStop =false;
        }
    }

    @OnClick(R.id.btn_send21)
    public void onSend21Click(){
        if(isStop  == true){
            command = gongju;
            startTimer();
            isStop =false;
        }else{
            stopTimer();
            command = gongju;
            isStop = true;
            startTimer();
            isStop =false;
        }
    }

    @OnClick(R.id.btn_send22)
    public void onSend22Click(){
        if(isStop  == true){
            command = waishe;
            startTimer();
            isStop =false;
        }else{
            stopTimer();
            command = waishe;
            isStop = true;
            startTimer();
            isStop =false;
        }
    }

    @OnClick(R.id.stopall)
    public void onstopallClick(){
        if(isStop  == false){
            stopTimer();
            isStop = true;
        }
    }

    private void notifyOption(){
       if (nofityEnable){
           nofityEnable = false;
           btnOption.setText(Option.NOTIFY);
           stopBroadcastDataNotify(notifyCharacteristic);
           Message msg = new Message(Message.MESSAGE_TYPE.SEND,Option.STOP_NOTIFY);
           notifyAdapter(msg);
       }else {
           nofityEnable = true;
           btnOption.setText(Option.STOP_NOTIFY);
           prepareBroadcastDataNotify(notifyCharacteristic);
           Message msg = new Message(Message.MESSAGE_TYPE.SEND,Option.NOTIFY);
           notifyAdapter(msg);
       }
    }


    private void indicateOption(){
        if (indicateEnable){
            indicateEnable = false;
            btnOption.setText(Option.INDICATE);
            stopBroadcastDataIndicate(indicateCharacteristic);
            Message msg = new Message(Message.MESSAGE_TYPE.SEND,Option.STOP_INDICATE);
            notifyAdapter(msg);
        }else {
            nofityEnable = true;
            btnOption.setText(Option.STOP_INDICATE);
            prepareBroadcastDataIndicate(indicateCharacteristic);
            Message msg = new Message(Message.MESSAGE_TYPE.SEND,Option.INDICATE);
            notifyAdapter(msg);
        }
    }



    private void readOption(){
        Message msg = new Message(Message.MESSAGE_TYPE.SEND,Option.READ);
        notifyAdapter(msg);
        prepareBroadcastDataRead(readCharacteristic);
    }

    //发送文本给蓝牙
    private void writeOption(byte[] b){
        //String text = etWrite.getText().toString();
//        try {
//            text = Utils.ByteArraytoHex(text.getBytes("US-ASCII"));
//        } catch (UnsupportedEncodingException e) {
//            return;
//        }
//        if (TextUtils.isEmpty(text)){
//            //AnimateUtils.shake(etWrite);
//            return;
//        }

//        if (isHexSend){
//            text = text.replace(" ","");
//            if (!Utils.isRightHexStr(text)){
               // AnimateUtils.shake(etWrite);
//                return;
//            }
//            if(issendable){

//                issendable = false;
                getcountc = 0;
                receivedindex = 0;
               // for(int i=0;i<b.length;i++){
                    writeCharacteristic(writeCharacteristic, b);
               // }

//            }

//        }else {

//            if(Utils.isAtCmd(text))
//                text = text + "\r\n";
//            try {
//                byte[] array = text.getBytes("US-ASCII");
//                writeCharacteristic(writeCharacteristic,array);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                System.out.println("--------------------->write text exception");
//                return;
//            }

//        }

        Message msg = new Message(Message.MESSAGE_TYPE.SEND,""+b.toString());
        notifyAdapter(msg);
    }


    /**
     * update option button status (更新Option按钮的操作状态)
     * @param array
     */
    private void updateButtonStatus(byte[] array) {
        int status=array[0];
        switch (status) {
            case 0:
                if(btnOption.getText().toString().equals(Option.STOP_NOTIFY)){
                    btnOption.setText(Option.NOTIFY);
                    Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,Option.STOP_NOTIFY);
                    notifyAdapter(msg);
                }

                if (btnOption.getText().toString().equals(Option.STOP_INDICATE)){
                    btnOption.setText(Option.INDICATE);
                    Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,Option.STOP_INDICATE);
                    notifyAdapter(msg);
                }
                break;
            case 1:
                if (btnOption.getText().toString().equals(Option.NOTIFY)){
                    btnOption.setText(Option.STOP_NOTIFY);
                    Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,Option.NOTIFY);
                    notifyAdapter(msg);
                }
                break;
            case 2:
                if (btnOption.getText().toString().equals(Option.INDICATE)){
                    btnOption.setText(Option.STOP_INDICATE);
                    Message msg = new Message(Message.MESSAGE_TYPE.RECEIVE,Option.INDICATE);
                    notifyAdapter(msg);
                }
                break;
        }
    }





    private void startEndAnimation() {

        filterView.setAlpha(0.0f);
        filterView.setVisibility(View.VISIBLE);
        filterView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        ObjectAnimator animator1 = ObjectAnimator.ofInt(filterView,"backgroundColor",
                Color.parseColor("#0277bd"),Color.parseColor("#009688"));
        animator1.setDuration(200);
        animator1.setEvaluator(new ArgbEvaluator());


        filterView.animate()
                .alpha(0.6f)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvProperties.setVisibility(View.VISIBLE);
                        rvMsg.setVisibility(View.VISIBLE);
                        rlBottom.setVisibility(View.VISIBLE);
                        topShadow.setVisibility(View.VISIBLE);
                        bottomShadow.setVisibility(View.VISIBLE);

                        tvProperties.setTranslationY(-Utils.dpToPx(40));
                        topShadow.setTranslationY(-Utils.dpToPx(40));
                        bottomShadow.setAlpha(0.0f);
                        rlBottom.setTranslationY(Utils.dpToPx(56));
                        btnOptions.setTranslationY(Utils.dpToPx(56));
                        AnimateUtils.translationY(rlBottom,0,300,200);
                        AnimateUtils.alpha(bottomShadow,0.3f,100,450);
                        AnimateUtils.translationY(btnOptions,0,300,300);
                        AnimateUtils.translationY(tvProperties,0,300,300);
                        AnimateUtils.translationY(topShadow,0,300,300);
                        if (currentOption.getPropertyType() == Option.OPTION_PROPERTY.PROPERTY_WRITE){
                            //etWrite.setTranslationY(Utils.dpToPx(56));
                            //btnSend.setTranslationY(Utils.dpToPx(56));
                           // AnimateUtils.translationY(etWrite,0,300,400);
                           // AnimateUtils.translationY(btnSend,0,300,500);
                        }else {
                            btnOption.setTranslationY(Utils.dpToPx(56));
                            AnimateUtils.translationY(btnOption,0,300,500);
                        }

                        animate2();
                    }
                })
                .start();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            toolbar.setAlpha(0.0f);
            AnimateUtils.alpha(toolbar,1.0f,200,0);
        }

        animator1.start();
    }

    private void animate2(){
        filterView.animate()
                .alpha(0.0f)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        filterView.setLayerType(View.LAYER_TYPE_NONE, null);
                        filterView.setVisibility(View.GONE);
                    }
                })
                .start();
    }






    private void notifyAdapter(Message msg){
        list.add(msg);
        adapter.notifyLastItem();
        rvMsg.smoothScrollToPosition(adapter.getItemCount() - 1);
    }





    private void dismissMenu() {
        if (optionsMenuManager.getOptionsMenu() != null) {
            optionsMenuManager.toggleContextMenuFromView(null, null, null);
        }
    }



    private String formatMsgContent(byte[] data){
        return "HEX:"+Utils.ByteArraytoHex(data)+"  (ASSCII:"+Utils.byteToASCII(data)+")";
    }


    private String formatMsgContent(byte[] data,MyApplication.SERVICE_TYPE type){
        String res = "HEX:"+Utils.ByteArraytoHex(data);
        switch (type){
            case TYPE_STR:
                res += "  (ASSCII:"+Utils.byteToASCII(data)+")";
                break;
            case TYPE_USR_DEBUG:
                res += "  (ASSCII:"+Utils.byteToASCII(data)+")";
                break;
            case TYPE_NUMBER:
                res+= "  (int:"+Utils.ByteArrToIntStr(data)+")";
                break;
            case TYPE_OTHER:
                break;
        }
        return  res;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (optionsMenuManager.getOptionsMenu()!=null){
            dismissMenu();
            return false;
        }
        super.onOptionsItemSelected(item);
       // String text = etWrite.getText().toString();
        switch (item.getItemId()){
//            case R.id.menu_hex_send:
//                isHexSend = true;
//                if (!TextUtils.isEmpty(text)){
//                    if(Utils.isAtCmd(text))
//                        text = text + "\r\n";
//                    try {
//                        etWrite.setText(Utils.ByteArraytoHex(text.getBytes("US-ASCII")));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                        etWrite.setText("");
//                    }
//                }
//                break;
//            case R.id.menu_asscii_send:
//                isHexSend = false;
//                etWrite.setText("");
//                break;
            case R.id.menu_clear_display:
                list.clear();
                adapter.notifyDataSetChanged();
                break;
        }

        return false;
    }

    /**
     * Preparing Broadcast receiver to broadcast read characteristics
     *
     * @param characteristic
     */
    void prepareBroadcastDataRead(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            BluetoothLeService.readCharacteristic(characteristic);
        }
    }

    /**
     * Preparing Broadcast receiver to broadcast notify characteristics
     *
     * @param characteristic
     */
    void prepareBroadcastDataNotify(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            BluetoothLeService.setCharacteristicNotification(characteristic, true);
        }

    }

    /**
     * Stopping Broadcast receiver to broadcast notify characteristics
     *
     * @param characteristic
     */
    void stopBroadcastDataNotify(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
            BluetoothLeService.setCharacteristicNotification(characteristic, false);
        }
    }

    /**
     * Preparing Broadcast receiver to broadcast indicate characteristics
     *
     * @param characteristic
     */
    void prepareBroadcastDataIndicate(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();

        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
            BluetoothLeService.setCharacteristicIndication(characteristic, true);
        }
    }

    /**
     * Stopping Broadcast receiver to broadcast indicate characteristics
     *
     * @param characteristic
     */
    void stopBroadcastDataIndicate(
            BluetoothGattCharacteristic characteristic) {
        final int charaProp = characteristic.getProperties();

        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0) {
            BluetoothLeService.setCharacteristicIndication(characteristic, false);
        }

    }


    private void writeCharacteristic(BluetoothGattCharacteristic characteristic, byte[] bytes) {
        // Writing the hexValue to the characteristics
        try {
            BluetoothLeService.writeCharacteristicGattDb(characteristic,
                    bytes);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }




    private void showDialog(String info){

        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.setTitle(getString(R.string.alert))
                .setMessage(info)
                .setPositiveButton(R.string.ok,new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }



    private void stopNotifyOrIndicate(){
        if (nofityEnable)
            stopBroadcastDataNotify(notifyCharacteristic);
        if (indicateEnable)
            stopBroadcastDataIndicate(indicateCharacteristic);
    }


    @Override
    public void onBackPressed() {
        if (optionsMenuManager.getOptionsMenu()!=null){
            dismissMenu();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopNotifyOrIndicate();
        unregisterReceiver(mGattUpdateReceiver);
    }

    //启动定时发送请求码
    private void startTimer(){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    //Log.i(TAG, "count: "+String.valueOf(count));
                    if(command == dianchi){
                        sendMessage(dianchi);
                    }else if(command == gongju){
                        sendMessage(gongju);
                    }else if(command == waishe){
                        sendMessage(waishe);
                    }


                    do {
                        try {
                            //Log.i(TAG, "sleep(1000)...");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    } while (isPause);

                    count ++;
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, delay, period);

    }

    //停止定时发送请求码
    private void stopTimer(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        count = 0;
    }

    //通知线程要发送的内容
    public void sendMessage(int id){
        if (mmHandler != null) {
            android.os.Message message = android.os.Message.obtain(mmHandler, id);
            mmHandler.sendMessage(message);
        }
    }

    Runnable sendDianchiThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                String str = new httpconnectutil().Post(1, xmlutil.DianchiRquestXmlstr(dianchibean));
                System.out.println(str);
                dcrprs = xmlutil.DianchiResponesResponseXmlstr(str);
                if(dcrprs == null){
                    houtaifeedback.sendEmptyMessage(0);
                }else if(dcrprs.getResultCode().equals("0000")){
                    houtaifeedback.sendEmptyMessage(1);
                }else{
                    android.os.Message message=new android.os.Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("Reason", dcrprs.getNote());
                    message.setData(bundle);//bundle传值，耗时，效率低
                    message.what=-1;//标志是哪个线程传数据
                    houtaifeedback.sendMessage(message);//发送message信息
                }

            }catch(Exception e){
                houtaifeedback.sendEmptyMessage(0);
            }
        }

    };

    Runnable sendGongjuThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                String str = new httpconnectutil().Post(2, xmlutil.GongjuRquestXmlstr(gongjubean));
                System.out.println(str);
                gjrprs = xmlutil.GongjuResponseXmlstr(str);
                if(gjrprs == null){
                    houtaifeedback.sendEmptyMessage(0);
                }else if(gjrprs.getResultCode().equals("0000")){
                    houtaifeedback.sendEmptyMessage(2);
                }else{
                    android.os.Message message=new android.os.Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("Reason", gjrprs.getNote());
                    message.setData(bundle);//bundle传值，耗时，效率低
                    message.what=-1;//标志是哪个线程传数据
                    houtaifeedback.sendMessage(message);//发送message信息
                }

            }catch(Exception e){
                houtaifeedback.sendEmptyMessage(0);
            }
        }

    };
//
//
    Runnable sendWaisheThread = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                String str = new httpconnectutil().Post(3, xmlutil.WaisheRquestXmlstr(waishebean));
                System.out.println("wer个玩儿玩儿线程："+str);
                wsrprs = xmlutil.WaisheResponseXmlstr(str);
                if(wsrprs == null){
                    houtaifeedback.sendEmptyMessage(0);
                }else if(wsrprs.getResultCode().equals("0000")){
                    houtaifeedback.sendEmptyMessage(3);
                }else{
                    android.os.Message message=new android.os.Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("Reason", wsrprs.getNote());
                    message.setData(bundle);//bundle传值，耗时，效率低
                    message.what=-1;//标志是哪个线程传数据
                    houtaifeedback.sendMessage(message);//发送message信息
                }

            }catch(Exception e){
                houtaifeedback.sendEmptyMessage(0);
            }
        }

    };

    public int yihuo(Byte b[]){
        int j = b[0];
        for(int i=1;i<b.length;i++)
        {
            j = j^b[i];
        }
        return j;
    }

}
