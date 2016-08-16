package com.example.jhtwl.safty360.PhoneTheft;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.jhtwl.safty360.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    private ListView lvListView;
    private ArrayList<HashMap<String, String>> readContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        lvListView = (ListView) findViewById(R.id.contactLV);
        readContact = readContact();

        lvListView.setAdapter(new SimpleAdapter(this, readContact, R.layout.layout_contact_item, new String[] {"name", "phone"}, new int[] {R.id.tv_name, R.id.tv_phone}));
////
//        lvListView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return readContact.size();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return readContact.get(i);
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                View contentView = View.inflate(ContactActivity.this, R.layout.layout_contact_item, null);
//
//                TextView nameTv = (TextView) contentView.findViewById(R.id.tv_name);
//                TextView phoneTv = (TextView) contentView.findViewById(R.id.tv_phone);
//
//                HashMap<String, String> map = readContact.get(i);
//
//                nameTv.setText(map.get("name"));
//                phoneTv.setText(map.get("phone"));
//
//
//                return contentView;
//            }
//        });

        lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 读取当前item的电话号码
                String phone = readContact.get(i).get("phone");
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private ArrayList<HashMap<String,String>> readContact() {
        // 首次从raw_contacts中读取联系人的id("contact_id")
        // 其次，根据contact_id从data表中查询出相应的电话号码和联系人名称
        // 然后，根据mimetype来区分哪个室联系人，哪个是电话号码
        Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // 从raw_contacts中读取联系人的id("contact_id")
        Cursor rawContactCursor = getContentResolver().query(rawContactsUri, new String[] {"contact_id"}, null, null, null);
        if (rawContactCursor != null) {
            while (rawContactCursor.moveToNext()) {
                String contactId = rawContactCursor.getString(0);

//                System.out.print(contactId);
                // 根据contact_id从data表中查询相应的电话号码和联系人名称，实际上查询的是视图View_data
                Cursor dataCursor = getContentResolver().query(dataUri, new String[] {"data1", "mimetype"}, "contact_id=?", new String[] {contactId}, null);
                if (dataCursor != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    while (dataCursor.moveToNext()) {
                        String data1 = dataCursor.getString(0);
                        String mimetype = dataCursor.getString(1);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            map.put("phone", data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            map.put("name", data1);
                        }
                    }
                    list.add(map);
                    dataCursor.close();
                }
            }
            rawContactCursor.close();
        }
        return list;
    }
}
