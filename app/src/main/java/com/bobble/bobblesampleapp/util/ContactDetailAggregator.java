package com.bobble.bobblesampleapp.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Base64;

import com.bobble.bobblesampleapp.model.ContactData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by amitshekhar on 29/01/16.
 */
public class ContactDetailAggregator {

    private final Context context;
    private HashMap<Integer, ContactData> contactDataHashMap;

    public ContactDetailAggregator(final Context context) {
        this.context = context;
        contactDataHashMap = new HashMap<Integer, ContactData>();
    }

    public String getAll() {
        try {
            addMobileNumberToList();
            addEmailToList();
            addBirthdayToList();
            JSONArray jsonArrayData = new JSONArray();
            try {
                for (HashMap.Entry<Integer, ContactData> entry : contactDataHashMap.entrySet()) {
                    JSONObject jsonObjectData = new JSONObject();
                    JSONArray jsonArrayEmails = new JSONArray();
                    JSONArray jsonArrayMobileNumbers = new JSONArray();
                    List<String> emailList = entry.getValue().getEmails();
                    List<String> mobileNumberList = entry.getValue().getMobileNumbers();
                    if (emailList != null) {
                        for (String email : emailList) {
                            jsonArrayEmails.put(email);
                        }
                    }
                    if (mobileNumberList != null) {
                        for (String mobileNumber : mobileNumberList) {
                            jsonArrayMobileNumbers.put(mobileNumber);
                        }
                    }
                    jsonObjectData.put("name", entry.getValue().getName());
                    jsonObjectData.put("emails", jsonArrayEmails);
                    jsonObjectData.put("mobileNumbers", jsonArrayMobileNumbers);
                    jsonObjectData.put("birthday", entry.getValue().getBirthday());
                    jsonArrayData.put(jsonObjectData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return encrypt(jsonArrayData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, setKey("JaNdRgUkXp2s5v8y"));
            return Base64.encodeToString(
                    cipher.doFinal(strToEncrypt.getBytes("UTF-8")),
                    Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKeySpec setKey(String myKey) {
        SecretKeySpec secretKey = null;
        try {
            byte[] key = myKey.getBytes("UTF-8");
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BLog.d("encode", "key generated");
        return secretKey;
    }

    private void addMobileNumberToList() {
        try {
            ContentResolver cr = context.getContentResolver();
            String[] PROJECTION = new String[]{
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

            Cursor cur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION,
                    null, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " ASC");
            if (cur.moveToFirst()) {
                do {
                    final String mobileNumber = cur.getString(0);
                    final String name = cur.getString(1);
                    final int id = cur.getInt(2);
                    if (contactDataHashMap.get(id) == null) {
                        ContactData contactData = new ContactData();
                        contactData.setName(name);
                        List<String> mobileNumberList = new ArrayList<String>();
                        mobileNumberList.add(mobileNumber);
                        contactData.setMobileNumbers(mobileNumberList);
                        contactDataHashMap.put(id, contactData);
                    } else {
                        List<String> mobileNumberList = contactDataHashMap.get(id).getMobileNumbers();
                        mobileNumberList.add(mobileNumber);
                        contactDataHashMap.get(id).setMobileNumbers(mobileNumberList);
                    }
                } while (cur.moveToNext());
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEmailToList() {
        try {
            ContentResolver cr = context.getContentResolver();
            String[] PROJECTION = new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.CONTACT_ID};

            Cursor cur = cr.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION,
                    null, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " ASC");
            if (cur.moveToFirst()) {
                do {
                    String name = cur.getString(0);
                    String email = cur.getString(1);
                    int id = cur.getInt(2);
                    if (contactDataHashMap.get(id) != null) {
                        List<String> emailList = new ArrayList<String>();
                        if (contactDataHashMap.get(id).getEmails() != null) {
                            emailList = contactDataHashMap.get(id).getEmails();
                        }
                        emailList.add(email);
                        contactDataHashMap.get(id).setName(name);
                        contactDataHashMap.get(id).setEmails(emailList);
                    } else {
                        ContactData contactData = new ContactData();
                        List<String> emailList = new ArrayList<String>();
                        emailList.add(email);
                        contactData.setEmails(emailList);
                        contactDataHashMap.put(id, contactData);
                    }
                } while (cur.moveToNext());
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBirthdayToList() {
        try {
            ContentResolver cr = context.getContentResolver();
            String[] PROJECTION = new String[]{ContactsContract.CommonDataKinds.Event.TYPE, ContactsContract.CommonDataKinds.Event.START_DATE, ContactsContract.CommonDataKinds.Event.CONTACT_ID};

            String where = ContactsContract.Data.MIMETYPE + "= '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "'";

            Cursor cur = cr.query(
                    ContactsContract.Data.CONTENT_URI, PROJECTION,
                    where, null, ContactsContract.Data.CONTACT_ID + " ASC");
            if (cur.moveToFirst()) {
                do {
                    int type = cur.getInt(0);
                    switch (type) {
                        case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:
                            String birthday = cur.getString(1);
                            int id = cur.getInt(2);
                            if (contactDataHashMap.get(id) != null) {
                                contactDataHashMap.get(id).setBirthday(birthday);
                            }
                            break;
                    }
                } while (cur.moveToNext());
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}