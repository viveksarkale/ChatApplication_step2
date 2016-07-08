package chat.application;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import chat.application.activity.ComposeActivity;
import chat.application.activity.ContactActivity;
import chat.application.activity.ReadMessageActivity;
import chat.application.activity.SettingActivity;
import chat.application.adapter.MainAdapter;
import chat.application.database.DatabaseProvider;
import chat.application.helper.MySharedPreferences;
import chat.application.helper.NonScrollListView;
import chat.application.model.MainObject;
import chat.application.model.Message;

public class MainActivity extends AppCompatActivity
{
    private LinearLayout lvSetting, lvContact, lvCompose;
    private NonScrollListView listMain;
    private MainAdapter adapter;
    private DatabaseProvider databaseProvider;
    private ArrayList<Message> list = new ArrayList<>();
    public static final int REQ_MESSAGE=101;
    public static final int REQ_SETTING=102;
    public static final int REQ_CONTACT=103;
    public static final int REQ_COMPOSE=104;
    public static final String ISFIRSTUSE="isfirstuse";
    Handler handler1,handler2,handler3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setClickEvent();

    }

    private void init()
    {
        lvSetting = (LinearLayout) findViewById(R.id.linear_setting);
        lvContact = (LinearLayout) findViewById(R.id.linear_contact);
        lvCompose = (LinearLayout) findViewById(R.id.linear_Compose);

        listMain = (NonScrollListView) findViewById(R.id.list_main);
        databaseProvider=new DatabaseProvider(MainActivity.this);
        list=databaseProvider.getMessages();
        adapter = new MainAdapter(MainActivity.this, R.layout.item_main, list);
        listMain.setAdapter(adapter);

        boolean is_first_use= MySharedPreferences.readBooleanFromPreference(MainActivity.this
                ,ISFIRSTUSE,false);
        if (!is_first_use){
            MySharedPreferences.saveBooleanToPreference(MainActivity.this
                    ,ISFIRSTUSE,true);
            //scheduling deletion of messages according to their TIME_TO_LIVE..

            MyRunnable myRunnable1=new MyRunnable(list.get(0).getId());
            MyRunnable myRunnable2=new MyRunnable(list.get(1).getId());
            MyRunnable myRunnable3=new MyRunnable(list.get(2).getId());
            //setting handler for each task...

            handler1=new Handler();
            handler2=new Handler();
            handler3=new Handler();

            handler1.postDelayed(myRunnable1,list.get(0).getTime_to_live());
            handler2.postDelayed(myRunnable2,list.get(1).getTime_to_live());
            handler3.postDelayed(myRunnable3,list.get(2).getTime_to_live());
        }
    }

    private void setClickEvent()
    {
        listMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ReadMessageActivity.class);
                i.putExtra("id", list.get(position).getId());
                i.putExtra("senderName", list.get(position).getUsername());
                i.putExtra("subject", list.get(position).getSubject());
                i.putExtra("body", list.get(position).getMsg_body());
                startActivityForResult(i,REQ_MESSAGE);
            }
        });

        lvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        lvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(i);
            }
        });

        lvCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ComposeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_MESSAGE){
            list=databaseProvider.getMessages();
            adapter = new MainAdapter(MainActivity.this, R.layout.item_main, list);
            listMain.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
    class MyRunnable implements Runnable
    {
        private int id;
        private DatabaseProvider databaseProvider;
        public MyRunnable(int id){
            this.id=id;
            databaseProvider=new DatabaseProvider(getApplicationContext());

        }
        @Override
        public void run() {
            Log.v("MyRunnable","going delete msg by handler..");
            databaseProvider.deleteMessage(id);
            list=databaseProvider.getMessages();
            adapter = new MainAdapter(MainActivity.this, R.layout.item_main, list);
            listMain.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
