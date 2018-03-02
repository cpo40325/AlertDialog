package com.example.administrator.alertdialog;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Timer;

import static java.lang.System.currentTimeMillis;


public class MainActivity extends AppCompatActivity {
    private ArrayList<Food> data;
    private Button btnList;
    private View.OnClickListener clickListener;
    private MyAdapter adapter;
    private ArrayList myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        data.add(new Food("白飯", 10));
        data.add(new Food("泡麵", 20));
        data.add(new Food("滷肉飯", 40));
        data.add(new Food("大滷麵", 60));
        data.add(new Food("牛肉麵", 100));
        data.add(new Food("咖哩飯", 70));

        initView();


    }

    private void initView() {
        setContentView(R.layout.activity_main);


        btnList = findViewById(R.id.btnList);
        ListView listView = findViewById(R.id.lvMain);
        adapter = new MyAdapter(this, data);
        listView.setAdapter(adapter);
        click();
        btnList.setOnClickListener(clickListener);
    }

    private void click() {
        clickListener = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {


                if (adapter.getOrder() != null) {
                    myList = adapter.getOrder();
                }
                myList = adapter.getOrder();
                System.out.println(myList);


                MyAdapter listAdapter = new MyAdapter(MainActivity.this, myList);


                ListView myListView = new ListView(MainActivity.this);
//
                myListView.setAdapter(listAdapter);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("已點項目");
                dialog.setView(myListView);
                int sum = 0;
                for (int i = 0; i < myList.size(); i++) {
                    Food food = (Food) myList.get(i);
                    sum = sum + food.getPrice();
                }
                dialog.setNegativeButton("總共" + sum + "元", null);
                final int finalSum = sum;
                dialog.setPositiveButton("確認並付款", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (finalSum>0){
                            setNotification(finalSum);
                        }else {
                            new AlertDialog.Builder(MainActivity.this).setPositiveButton("好的",null).setMessage("尚未選購商品").show();
                        }
                    }
                });
                dialog.show();
            }
        };
    }

    private void setNotification(int sum) {
        NotificationManager manager = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            PendingIntent pintent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            String id = "channel_1";
            String description = "描述";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, "123", importance);
            mChannel.setDescription(description);
            manager.createNotificationChannel(mChannel);

            Notification notification = new Notification.Builder(MainActivity.this, id).setContentTitle("QQ")
                    .setSmallIcon(R.drawable.ic_menu_camera)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_gallery))
                    .setContentTitle("結帳成功")
                    .setContentText("已扣款"+sum+"元")
                    .setWhen(currentTimeMillis())
                    .setTicker("你有通知")
                    .setAutoCancel(true)
                    .setContentIntent(pintent)
                    .build();

            manager.notify(1, notification);
        }else {
            sendNotification_24();
        }
    }

    private void sendNotification_24() {
        NotificationManager manager = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_send)             //一定要设置
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_manage))
                .setContentTitle("新通知")
                .setContentText("沒事")
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }
}
