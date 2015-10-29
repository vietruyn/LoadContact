package com.example.ruyn.loadcontact;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruyn.loadcontact.adapter.PhoneBookAdapter;
import com.example.ruyn.loadcontact.helper.PhoneBookOpenHelper;
import com.example.ruyn.loadcontact.model.PhoneBook;
import com.example.ruyn.loadcontact.view.LoadMoreListview;

import java.util.ArrayList;

/**
 * Created by Ruyn on 10/29/2015.
 */
public class PhoneBookActivity extends Activity {

    public static LoadMoreListview lvPhoneBook;
    public static PhoneBookAdapter adapter;
    public static ArrayList<PhoneBook> phoneBookArrayList;
    public static PhoneBookOpenHelper pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);
        lvPhoneBook = (LoadMoreListview) findViewById(R.id.lvPhonebook);
        phoneBookArrayList = new ArrayList<PhoneBook>();
        adapter = new PhoneBookAdapter(this);
        pb = new PhoneBookOpenHelper(this);
        Cursor cursor = pb.getInfomationContactList(pb);
        if (cursor.moveToFirst()) {
            /**
             * Get contact from database
             */
            do {
                String name = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.FNAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.PNUMBER));
                String email = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.EMAIL));
                int tyoe = cursor.getInt(cursor.getColumnIndex(PhoneBookOpenHelper.TYPE));
                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setName(name);
                phoneBook.setEmail(email);
                phoneBook.setPhoneNumber(phoneNumber);
                phoneBook.setType(tyoe);
                phoneBookArrayList.add(phoneBook);
            } while (cursor.moveToNext());
        } else Toast.makeText(PhoneBookActivity.this, "Data null", Toast.LENGTH_LONG).show();
        adapter = new PhoneBookAdapter(PhoneBookActivity.this, phoneBookArrayList);
        lvPhoneBook.setAdapter(adapter);

        lvPhoneBook.setOnLoadMoreListener(new LoadMoreListview.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new LoadDataTask().executeOnExecutor(
                            AsyncTask.THREAD_POOL_EXECUTOR, null);
                else
                    new LoadDataTask().execute();


            }
        });
    }

    private class LoadDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Cursor cursor = pb.getInfomationContactList(pb);
            int a = adapter.getCount();
            int b = cursor.getCount();
            updatedata(a,b);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    adapter.notifyDataSetChanged();
                    lvPhoneBook.onLoadMoreComplete();
                }
            }.start();

            super.onPostExecute(result);
        }


    }


    public static void updatedata(int adapterSize, int dataSize) {
        adapterSize = adapter.getCount();
        Cursor cursor = pb.getInfomationContactList(pb);
        if (cursor.moveToPosition(adapterSize)) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.FNAME));
                String phoneNumber1 = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.PNUMBER));
                String email = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.EMAIL));
                int tyoe = cursor.getInt(cursor.getColumnIndex(PhoneBookOpenHelper.TYPE));
                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setName(name);
                phoneBook.setEmail(email);
                phoneBook.setPhoneNumber(phoneNumber1);
                phoneBook.setType(tyoe);
                phoneBookArrayList.add(phoneBook);
                adapterSize++;
            } while (cursor.moveToNext()&&adapterSize<dataSize);
        }


    }

    public static void changeData() {
        if (phoneBookArrayList != null) {
            phoneBookArrayList.clear();

            Cursor cursor = pb.getInfomationContactList(pb);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.FNAME));
                    String phoneNumber1 = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.PNUMBER));
                    String email = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.EMAIL));
                    int tyoe = cursor.getInt(cursor.getColumnIndex(PhoneBookOpenHelper.TYPE));
                    PhoneBook phoneBook = new PhoneBook();
                    phoneBook.setName(name);
                    phoneBook.setEmail(email);
                    phoneBook.setPhoneNumber(phoneNumber1);
                    phoneBook.setType(tyoe);
                    phoneBookArrayList.add(phoneBook);
                } while (cursor.moveToNext());
            }


        }


    }
}
