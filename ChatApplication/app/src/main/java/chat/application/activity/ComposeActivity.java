package chat.application.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import chat.application.R;

@SuppressWarnings("ALL")
public class ComposeActivity extends AppCompatActivity
{
    private TextView tvSend, tvTo;
    private EditText /*edTo,*/ edSubject, edBody;
    private String sTo = "", sSubject = "";
    public static String checkIntent = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        init();
        setClickEvent();

        if (!checkIntent.equals(""))
        {
            Intent i = getIntent();
            if (i != null)
            {
                sTo = i.getStringExtra("senderName");
                sSubject = i.getStringExtra("subject");

                tvTo.setText("" + sTo);
                edSubject.setText("" + sSubject);
                checkIntent = "";
            }
        }
    }

    private void init()
    {
        tvSend = (TextView) findViewById(R.id.tv_Compose_send);
        tvTo = (TextView) findViewById(R.id.tv_Compose_To);
        edSubject = (EditText) findViewById(R.id.ed_Compose_subject);
        edBody = (EditText) findViewById(R.id.ed_Compose_body);
    }

    private void setClickEvent()
    {
        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ComposeActivity.this, ContactActivity.class);
                startActivity(i);
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ComposeActivity.this,edBody.getText(),Toast.LENGTH_LONG).show();
                ComposeActivity.this.finish();
            }
        });
    }
}
