package com.usr.usrsimplebleassistent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ButtonsDIYSettingActivity extends AppCompatActivity {

    private EditText b1n,b1v,b2n,b2v,b3n,b3v,b4n,b4v,b5n,b5v,b6n,b6v,b7n,b7v,b8n,b8v;
    private Button ssbtn;

    public static final String DATABASE = "Database";
    SharedPreferences sp ;
    // 获取Editor对象
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_diysetting);
        sp = getSharedPreferences(DATABASE, Activity.MODE_PRIVATE);
        editor = sp.edit();
        b1n = (EditText) findViewById(R.id.key1name);
        b1v = (EditText) findViewById(R.id.key1value);
        b2n = (EditText) findViewById(R.id.key2name);
        b2v = (EditText) findViewById(R.id.key2value);
        b3n = (EditText) findViewById(R.id.key3name);
        b3v = (EditText) findViewById(R.id.key3value);
        b4n = (EditText) findViewById(R.id.key4name);
        b4v = (EditText) findViewById(R.id.key4value);
        b5n = (EditText) findViewById(R.id.key5name);
        b5v = (EditText) findViewById(R.id.key5value);
        b6n = (EditText) findViewById(R.id.key6name);
        b6v = (EditText) findViewById(R.id.key6value);
        b7n = (EditText) findViewById(R.id.key7name);
        b7v = (EditText) findViewById(R.id.key7value);
        b8n = (EditText) findViewById(R.id.key8name);
        b8v = (EditText) findViewById(R.id.key8value);
        ssbtn = (Button) findViewById(R.id.savebtnsettingbt);


        b1n.setText(sp.getString("b1n", ""));
        b1v.setText(sp.getString("b1v", ""));
        b2n.setText(sp.getString("b2n", ""));
        b2v.setText(sp.getString("b2v", ""));
        b3n.setText(sp.getString("b3n", ""));
        b3v.setText(sp.getString("b3v", ""));
        b4n.setText(sp.getString("b4n", ""));
        b4v.setText(sp.getString("b4v", ""));
        b5n.setText(sp.getString("b5n", ""));
        b5v.setText(sp.getString("b5v", ""));
        b6n.setText(sp.getString("b6n", ""));
        b6v.setText(sp.getString("b6v", ""));
        b7n.setText(sp.getString("b7n", ""));
        b7v.setText(sp.getString("b7v", ""));
        b8n.setText(sp.getString("b8n", ""));
        b8v.setText(sp.getString("b8v", ""));

        ssbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( isinputvalueright(b1v.getText().toString())&&isinputvalueright(b2v.getText().toString())&&
                    isinputvalueright(b3v.getText().toString())&&isinputvalueright(b4v.getText().toString())&&
                    isinputvalueright(b5v.getText().toString())&&isinputvalueright(b6v.getText().toString())&&
                    isinputvalueright(b7v.getText().toString())&&isinputvalueright(b8v.getText().toString())){

                    editor.putString("b1n", b1n.getText().toString());
                    editor.putString("b1v", b1v.getText().toString());
                    editor.putString("b2n", b2n.getText().toString());
                    editor.putString("b2v", b2v.getText().toString());
                    editor.putString("b3n", b3n.getText().toString());
                    editor.putString("b3v", b3v.getText().toString());
                    editor.putString("b4n", b4n.getText().toString());
                    editor.putString("b4v", b4v.getText().toString());
                    editor.putString("b5n", b5n.getText().toString());
                    editor.putString("b5v", b5v.getText().toString());
                    editor.putString("b6n", b6n.getText().toString());
                    editor.putString("b6v", b6v.getText().toString());
                    editor.putString("b7n", b7n.getText().toString());
                    editor.putString("b7v", b7v.getText().toString());
                    editor.putString("b8n", b8n.getText().toString());
                    editor.putString("b8v", b8v.getText().toString());
                    editor.commit();
                }else{
                    new AlertDialog.Builder(ButtonsDIYSettingActivity.this)
                            .setMessage("输入错误，键值内容要么为空，要么为0-9，A-F的16进制字串，中间不需要加空格")
                            .setNegativeButton("确定",null)
                            .show();
                }
            }
        });



    }

    private boolean isinputvalueright(String str){
        String regex = "([A-F]|[0-9]){0,}";
        if(str == null || str.trim().equals("") || str.trim().matches(regex)){
            return true;
        }else {
            return false;
        }
    }
}
