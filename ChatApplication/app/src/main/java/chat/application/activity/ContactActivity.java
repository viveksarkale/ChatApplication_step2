package chat.application.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

import chat.application.R;
import chat.application.adapter.ContactAdapter;
import chat.application.database.DatabaseProvider;
import chat.application.helper.NonScrollListView;
import chat.application.model.Contact;
import chat.application.model.ContactObject;

@SuppressWarnings("ALL")
public class ContactActivity extends AppCompatActivity
{
    private NonScrollListView listContact;
    private ArrayList<Contact> list = new ArrayList<>();
    private ContactAdapter adapter;
    private DatabaseProvider databaseProvider;
//    private ImageView imAddContact;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();

    }

    private void init()
    {
        databaseProvider=new DatabaseProvider(ContactActivity.this);
        listContact = (NonScrollListView) findViewById(R.id.list_contact_view);

        list=databaseProvider.getContacts();
        adapter = new ContactAdapter(ContactActivity.this, R.layout.item_contact, list);
        listContact.setAdapter(adapter);

        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ComposeActivity.checkIntent = "Check";
                Intent i = new Intent(ContactActivity.this, ComposeActivity.class);
                i.putExtra("senderName", list.get(position).getUsername());
                i.putExtra("subject", "");
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(ContactActivity.this, AddContactActivity.class);
                AddContactActivity.ISNEWCONTACT=true;
                startActivityForResult(i,101);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("ContactActivity","ContactActivity onRestart()");
        refreshList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101){
            refreshList();
        }
    }
    public void refreshList(){
        list=databaseProvider.getContacts();
        adapter = new ContactAdapter(ContactActivity.this, R.layout.item_contact, list);
        listContact.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
