package com.zykj.samplechat.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.zykj.samplechat.model.Contacts;

import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/8.
 */
public class PhoneUtil {

    private Context context;

    public PhoneUtil() {
    }

    public PhoneUtil(Context context) {
        this.context = context;
    }

    public ArrayList<Contacts> testReadAllContacts() {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        ArrayList<Contacts> contactses = new ArrayList<>();

        if (cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while (cursor.moveToNext()) {
            try {

                Contacts contacts = new Contacts();

                String contactId = cursor.getString(contactIdIndex);
                String name = cursor.getString(nameIndex);

                contacts.name = name;

                /*
                * 查找该联系人的phone信息
                */
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                        null, null);
                int phoneIndex = 0;
                if (phones.getCount() > 0) {
                    phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                }
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phoneIndex);
                    //格式化手机号
                    phoneNumber = phoneNumber.replace("-","");
                    phoneNumber = phoneNumber.replace(" ","");
                    contacts.phones.add(phoneNumber);
                }

                contactses.add(contacts);
            }catch (Exception ex){}
        }

        return contactses;
    }
}
