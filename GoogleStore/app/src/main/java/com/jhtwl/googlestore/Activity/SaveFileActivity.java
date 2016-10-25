package com.jhtwl.googlestore.Activity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jhtwl.googlestore.Helper.DataBaseDao;
import com.jhtwl.googlestore.Helper.DataBaseHelper;
import com.jhtwl.googlestore.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SaveFileActivity extends AppCompatActivity implements View.OnClickListener{

    private DataBaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_file);

        Button insertBtn = (Button) findViewById(R.id.insertBtn);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        Button updateBtn = (Button) findViewById(R.id.updateBtn);
        Button quartBtn = (Button) findViewById(R.id.quartBtn);
        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        quartBtn.setOnClickListener(this);


//        // MODE_PRIVATE 表示私有，不能被其他程序访问
//        SharedPreferences sharedPreferences = getSharedPreferences("fileName", MODE_PRIVATE);
//        // 保存在包名文件夹下的shared_prefs文件夹中
//        sharedPreferences.edit().putString("key", "value").commit();


//        Button writeBtn = (Button) findViewById(R.id.writeBtn);
//        Button readBtn = (Button) findViewById(R.id.readBtn);
//
//        writeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // File存储 FileInputStream FileOutputStream  存放在files文件夹中
//                try {
//                    FileOutputStream outputStream = openFileOutput("file.txt", MODE_APPEND);
//                    PrintStream printStream = new PrintStream(outputStream);
//                    printStream.print("test fileOutputStream");
//                    printStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        readBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    FileInputStream inputStream = openFileInput("file.txt");
//                    byte read[] = new byte[1024];
//                    int readlength = 0;
//                    StringBuffer buffer = new StringBuffer("");
//                    while ((readlength = inputStream.read(read)) > 0) {
//                        buffer.append(new String(read, 0 , readlength));
//                    }
//                    inputStream.close();
//                    Toast.makeText(SaveFileActivity.this, buffer, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });




    }

    @Override
    public void onClick(View v) {
        long state = -5;
        switch (v.getId()) {
            case R.id.insertBtn:
                state = DataBaseDao.insert();
                break;
            case R.id.deleteBtn:
                state = DataBaseDao.delete();
                break;
            case R.id.updateBtn:
                state = DataBaseDao.update();
                break;
            case R.id.quartBtn:
                state = DataBaseDao.quary();
                break;
        }

        if (state != -1) {
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
        }
    }
}
