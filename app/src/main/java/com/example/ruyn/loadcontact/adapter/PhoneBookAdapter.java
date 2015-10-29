package com.example.ruyn.loadcontact.adapter;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruyn.loadcontact.R;
import com.example.ruyn.loadcontact.helper.PhoneBookOpenHelper;
import com.example.ruyn.loadcontact.model.PhoneBook;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jce on 8/10/15.
 */
public class PhoneBookAdapter extends BaseAdapter {

    Context context;
    ArrayList<PhoneBook> listData = new ArrayList<PhoneBook>();
    SQLiteDatabase database_ob;
    PhoneBookOpenHelper openHelper_ob;

    public PhoneBookAdapter(Context context, ArrayList<PhoneBook> listData) {
        this.context = context;
        this.listData = listData;
    }

    public PhoneBookAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private TextView tvUserName, tvPhoneNumber;
        private LinearLayout lnNhanRequest;
        private RelativeLayout rlAddFriend,bgItem;
        private ImageView imgAgree, imgCancel, imgSentRequest, imgAddFriend;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_phonebook, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUserName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvPhoneNumber = (TextView) view.findViewById(R.id.tvPhoneNumber);
            viewHolder.lnNhanRequest = (LinearLayout) view.findViewById(R.id.lnNhanRequest);
            viewHolder.imgAgree = (ImageView) view.findViewById(R.id.imgAgree);
            viewHolder.imgCancel = (ImageView) view.findViewById(R.id.imgCancer);
            viewHolder.imgAddFriend = (ImageView) view.findViewById(R.id.imgAddFriend);
            viewHolder.imgSentRequest = (ImageView) view.findViewById(R.id.imgRequest);
            viewHolder.rlAddFriend = (RelativeLayout) view.findViewById(R.id.rlAddFriend);
            viewHolder.bgItem = (RelativeLayout) view.findViewById(R.id.bgItem);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final PhoneBook phoneBook = listData.get(position);
        String userName = phoneBook.getName();
        final String phoneNumber = phoneBook.getPhoneNumber();

        /**
         * Show list item with type
         */
        int type = phoneBook.getType();
        switch (type) {
            case 0:
                viewHolder.bgItem.setBackgroundResource(R.drawable.bg_item_friend);
                viewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
                viewHolder.rlAddFriend.setVisibility(View.VISIBLE);
                viewHolder.imgAddFriend.setVisibility(View.VISIBLE);
                viewHolder.imgSentRequest.setVisibility(View.INVISIBLE);
                break;
            case 1:
                viewHolder.bgItem.setBackgroundResource(R.drawable.bg_item_request);
                viewHolder.lnNhanRequest.setVisibility(View.VISIBLE);
                viewHolder.rlAddFriend.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewHolder.bgItem.setBackgroundResource(R.drawable.bg_item_friend);
                viewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
                viewHolder.rlAddFriend.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.bgItem.setBackgroundResource(R.drawable.bg_item_friend);
                viewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
                viewHolder.rlAddFriend.setVisibility(View.VISIBLE);
                viewHolder.imgAddFriend.setVisibility(View.VISIBLE);
                viewHolder.imgSentRequest.setVisibility(View.INVISIBLE);
                break;
            case 4:
                viewHolder.bgItem.setBackgroundResource(R.drawable.bg_item_friend);
                viewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
                viewHolder.rlAddFriend.setVisibility(View.VISIBLE);
                viewHolder.imgAddFriend.setVisibility(View.INVISIBLE);
                viewHolder.imgSentRequest.setVisibility(View.VISIBLE);
                break;

        }
        viewHolder.tvUserName.setText(userName);
        viewHolder.tvPhoneNumber.setText(phoneNumber);
        return view;

        /**
         * Dong y ket ban
         */
//
//        final ViewHolder finalViewHolder = viewHolder;
//        final View finalView = view;
//        viewHolder.imgAgree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(context, phoneBook.getPhoneNumber().toString(), Toast.LENGTH_SHORT).show();
//                ObjectMapper mapper1 = new ObjectMapper();
//                HashMap<Object, Object> map = new HashMap<Object, Object>();
//
//                map.put("RequestType", 3);
//                map.put("ToUser", phoneBook.getEmail());
//                map.put("IsAccept", 1);
//                try {
//                    byte[] mRequestData = mapper1.writeValueAsBytes(map);
//                    ClientSocket.sendStream.write(mRequestData, 0, mRequestData.length);
//                    ClientSocket.sendStream.flush();
//                    MainActivity.pb.updateContactList(2, phoneBook.getEmail());
//                    phoneBook.setType(2);
////                    finalView.setBackgroundResource(R.drawable.img_itemcheck);
////                    finalViewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
////                    finalViewHolder.rlAddFriend.setVisibility(View.INVISIBLE);
//                    notifyDataSetChanged();
//
//                } catch (Exception e) {
//                    Log.d("tai sao vang", e.getMessage());
//                }
////                notifyDataSetChanged();
//
//            }
//        });
//
//        /**
//         * Huy yeu cau
//         */
//        viewHolder.imgCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObjectMapper mapper1 = new ObjectMapper();
//                HashMap<Object, Object> map = new HashMap<Object, Object>();
//
//                map.put("RequestType", 3);
//                map.put("ToUser", phoneBook.getEmail());
//                map.put("IsAccept", 0);
//                try {
//                    byte[] mRequestData = mapper1.writeValueAsBytes(map);
//                    ClientSocket.sendStream.write(mRequestData, 0, mRequestData.length);
//                    ClientSocket.sendStream.flush();
//                    MainActivity.pb.updateContactList(3, phoneBook.getEmail());
//                    phoneBook.setType(3);
////                    finalView.setBackgroundResource(R.drawable.img_itemcheck);
////                    finalViewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
////                    finalViewHolder.rlAddFriend.setVisibility(View.VISIBLE);
////                    finalViewHolder.imgAddFriend.setVisibility(View.VISIBLE);
////                    finalViewHolder.imgSentRequest.setVisibility(View.INVISIBLE);
//                    notifyDataSetChanged();
//                } catch (Exception e) {
//                    Log.d("tai sao vang", e.getMessage());
//                }
////                notifyDataSetChanged();
//
//            }
//        });
//
//        /**
//         * Add friend
//         */
//        viewHolder.imgAddFriend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (phoneBook.getType() == 0) {
//                    final Dialog dialog;
//                    dialog = new Dialog(context, R.style.sentsmsdialogstyle);
//                    dialog.setContentView(R.layout.custom_dialog_sent_sms);
//                    final EditText edtPhoneNumber = (EditText) dialog.findViewById(R.id.edtPhone);
//                    final EditText edtContent = (EditText) dialog.findViewById(R.id.edtContent);
//                    Button btnSent = (Button) dialog.findViewById(R.id.btnSentSMS);
//                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//                    final String toPhoneNumber = phoneBook.getPhoneNumber();
//                    edtPhoneNumber.setText(toPhoneNumber);
//                    final String smsMessage = edtContent.getText().toString();
//
//                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//                    int width = metrics.widthPixels;
//                    dialog.getWindow().setLayout((6 * width) / 7, ActionBar.LayoutParams.WRAP_CONTENT);
//                    dialog.show();
//                    btnSent.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            try {
//                                SmsManager smsManager = SmsManager.getDefault();
//                                smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
//                                Toast.makeText(context, "SMS sent.",
//                                        Toast.LENGTH_LONG).show();
//                            } catch (Exception e) {
//                                Toast.makeText(context,
//                                        "Sending SMS failed.",
//                                        Toast.LENGTH_LONG).show();
//                                e.printStackTrace();
//                            }
//                            dialog.dismiss();
//                        }
//
//                    });
//                    btnCancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                } else {
//
//                    ObjectMapper mapper1 = new ObjectMapper();
//                    HashMap<Object, Object> map = new HashMap<Object, Object>();
//                    String name = null, phoneNumber = null, email = null;
//                    /**
//                     *get data
//                     */
//                    PhoneBookOpenHelper pb = new PhoneBookOpenHelper(context);
//                    Cursor cursor = pb.getInfomationUser(pb);
//                    if (cursor.moveToFirst()) {
//                        do {
//                            name = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.U_FULLNAME));
//                            phoneNumber = cursor.getString(cursor.getColumnIndex(PhoneBookOpenHelper.U_PHONUMBER));
//
//                        } while (cursor.moveToNext());
//                    } else Log.e("Table", " null");
//                    map.put("RequestType", 2);
//                    map.put("ToUser", phoneBook.getEmail());
//                    map.put("MyFullName", name);
//                    map.put("MyPhoneNumber", phoneNumber);
//                    try {
//                        byte[] mRequestData = mapper1.writeValueAsBytes(map);
//                        ClientSocket.sendStream.write(mRequestData, 0, mRequestData.length);
//                        ClientSocket.sendStream.flush();
//                        MainActivity.pb.updateContactList(4, phoneBook.getEmail());
//                        phoneBook.setType(4);
//                        notifyDataSetChanged();
//                        Toast.makeText(context,"Da gui",Toast.LENGTH_SHORT).show();
//
//                    } catch (Exception e) {
//                        Log.d("tai sao vang", e.getMessage());
//                    }
////                    notifyDataSetChanged();
////                    v.setVisibility(View.INVISIBLE);
////                    finalViewHolder.imgSentRequest.setVisibility(View.VISIBLE);
////                    finalViewHolder.lnNhanRequest.setVisibility(View.INVISIBLE);
//
//                }
//

                //Socket
//
//            }
//        });

    }

}