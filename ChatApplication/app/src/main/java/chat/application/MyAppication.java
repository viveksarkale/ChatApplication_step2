package chat.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Random;

import chat.application.database.DatabaseProvider;
import chat.application.helper.MySharedPreferences;
import chat.application.model.Contact;
import chat.application.model.Message;

/**
 * Created by Pbuhsoft on 06/07/2016.
 */
public class MyAppication extends Application{
    public static final String KEY_PAIR="key_pair";
    private DatabaseProvider databaseProvider;
    public static final long ONE_SECOND=1000;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseProvider=new DatabaseProvider(getApplicationContext());
        String keyPair="IAmAdmin:"+RandomStringUtils.randomAlphabetic(5);
        //set keypair in preference...
        MySharedPreferences.saveToPreference(getApplicationContext(),KEY_PAIR,keyPair);

        //genetating 3 fake contacts....
        Contact contact1=new Contact(1,"user101",null, RandomStringUtils.randomAlphabetic(5));
        Contact contact2=new Contact(2,"user102",null,RandomStringUtils.randomAlphabetic(5));
        Contact contact3=new Contact(3,"user103",null,RandomStringUtils.randomAlphabetic(5));
        databaseProvider.addContact(contact1);
        databaseProvider.addContact(contact2);
        databaseProvider.addContact(contact3);

        //generating 3 fake messages..
        Message message1=new Message(1,"user101","here is subject1"
                ,"This is message1.",5*ONE_SECOND);
        Message message2=new Message(2,"user102","here is subject2"
                ,"This is message2.",15*ONE_SECOND);
        Message message3=new Message(3,"user103","here is subject3"
                ,"This is message3.",5*60*ONE_SECOND);
        databaseProvider.addNewMessage(message1);
        databaseProvider.addNewMessage(message2);
        databaseProvider.addNewMessage(message3);


    }

}
