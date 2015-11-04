package com.example.ruyn.loadcontact;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruyn.loadcontact.adapter.PhoneBookAdapter;
import com.example.ruyn.loadcontact.fragment.ContactFragment;
import com.example.ruyn.loadcontact.fragment.GroupContactFragment;
import com.example.ruyn.loadcontact.helper.PhoneBookOpenHelper;
import com.example.ruyn.loadcontact.model.PhoneBook;
import com.example.ruyn.loadcontact.view.LoadMoreListview;

import java.util.ArrayList;

/**
 * Created by Ruyn on 10/29/2015.
 */
public class PhoneBookActivity extends Activity implements View.OnClickListener {
    private Fragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);
        ImageView imgGroup = (ImageView) findViewById(R.id.imgGroup);
        imgGroup.setOnClickListener(this);
        if (savedInstanceState == null) {
            fr = new ListFragment();
            callFragment(fr);
        } else return;
    }


    private void callFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fmListContact, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.imgGroup)) {
            fr = new GroupContactFragment();
            callFragment(fr);

        } else {
            fr = new ContactFragment();

            callFragment(fr);
        }
    }
}
