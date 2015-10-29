package com.example.ruyn.loadcontact;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ruyn.loadcontact.connect.ConnectWebservice;
import com.example.ruyn.loadcontact.helper.PhoneBookOpenHelper;
import com.example.ruyn.loadcontact.model.PhoneBook;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    public static String xName = "", xPass = "";

    private String phoneNumber = null;
    private String email = null;

    private Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    private String _ID = ContactsContract.Contacts._ID;
    private String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    private Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;


    private String url = "http://192.168.1.200/closechatweb/", urlCC = "http://closechat.com/closechatwebservice/";
    private int type;
    private Intent intent;
    private Cursor cursor;

    private EditText edtEmail;
    public static PhoneBookOpenHelper pb;
    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhoneBookOpenHelper n = new PhoneBookOpenHelper(this);
        n.deleteAllContact();
        Button btnLoadData = (Button) findViewById(R.id.btnLoadData);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        mEmail = edtEmail.getText().toString();
        btnLoadData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadData:
                pb = new PhoneBookOpenHelper(MainActivity.this);
                if(pb.getCountContact()==0){
                    mProgressDialog = new ProgressDialog(MainActivity.this);
                    mProgressDialog.setMessage("aaaaaaaa");
                    mProgressDialog.show();
                    new getListContact().execute();
                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            if (mProgressDialog != null)
                                mProgressDialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, PhoneBookActivity.class);
                            startActivity(intent);
                        }
                    }.start();
                }
                else{

                }


        }
    }

    class getListContact extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ContentResolver contentResolver = getContentResolver();

            cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    PhoneBook phoneBook = new PhoneBook();
                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                    if (hasPhoneNumber > 0) {

                        // Query and loop for every phone number of the contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));

                        }

                        //Connect Sever
                        String url1 = urlCC + "index.php?route=webservice/users/phonebook&username=" + mEmail + "&array_telephone=" + phoneNumber.replaceAll("\\D+", "");
//                        Log.e("URL: ", url1);
                        ConnectWebservice connectWebservice = new ConnectWebservice(url1);
                        connectWebservice.fetchJSON();
                        while (connectWebservice.parsingComplete) ;

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(connectWebservice.getData());
                            JSONObject number = jsonObject.getJSONObject(phoneNumber.replaceAll("\\D+", ""));
                            type = Integer.parseInt(number.getString("check"));
                            if (type == 0) {
                                email = null;
                            } else {
                                name = number.getString("full_name");
                                email = number.getString("username");
                            }
                            phoneBook.setType(type);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        phoneCursor.close();

                        PhoneBookOpenHelper pb = new PhoneBookOpenHelper(MainActivity.this);
                        pb.putInfomationContacList(name, phoneNumber, email, type);
                    }
                }
            }
            return null;
        }
    }
}
