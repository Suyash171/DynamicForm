package com.example.dynamicformdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.CalendarContract.Calendars.NAME;

public class GetContactDetailsActivity extends AppCompatActivity {

    private static final int CONTACT_PICKER_RESULT = 101;
    private static final int PERMISSION_REQUEST_CONTACT = 1003;
    private Button btnFetchDetails;
    private TextView tvName;


    ArrayList<String> namelist = new ArrayList<String>();
    ArrayList<String> number = new ArrayList<String>();
    ArrayList<String> contact_id = new ArrayList<String>();
    ArrayList<String> contact_image_uri = new ArrayList<String>();
    ArrayList<String> contact_email = new ArrayList<String>();
    String email;

    public static void start(Context context) {
        Intent intent = new Intent(context, GetContactDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_getcontact_details);

        btnFetchDetails = findViewById(R.id.btnFetchDetails);
        tvName = findViewById(R.id.tvSelectedName);


        btnFetchDetails.setOnClickListener(view -> {
            askForContactPermission();
        });


    }

    private void getContactDetails() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactDetails();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "No Permissions ", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {
                    // No expanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                getContactDetails();
            }
        } else {
            getContactDetails();
        }
    }


    public ArrayList<String> getNameEmailDetails() {
        ArrayList<String> names = new ArrayList<String>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
                    String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.e("Name :", name);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.e("Email", email);
                    if (email != null) {
                        names.add(name);
                    }
                }
                cur1.close();
            }
        }
        return names;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    getSelectedContactDetails(data);
                default:
                    break;
            }
        }
    }

    private void getSelectedContactDetails(Intent data) {
        Cursor cursor = null;
        String email = "", name = "", contactNumber = "";
        StringBuilder phoneBuilder = new StringBuilder();
        StringBuilder emailBuilder = new StringBuilder();

        try {
            Uri result = data.getData();
            // get the contact id from the Uri
            String id = result.getLastPathSegment();

            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[]{id}, null);

            //Fetch Display Name
            int nameId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

            //retrive multiple emails
            while (cursor.moveToNext()) {
                //get single email using id
                //email = cursor.getString(emailIdx);
                name = cursor.getString(nameId);
                Log.v(GetContactDetailsActivity.class.getName(), "Got email: " + email);
                email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                emailBuilder.append(email + ", \n");
                Log.d("Emails" ,"" + email);
            }

            // Get Phone Number....
            Uri URI_PHONE = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String SELECTION_PHONE = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?";
            String[] SELECTION_ARRAY_PHONE = new String[] { id };

            Cursor currPhone = getContentResolver().query(URI_PHONE, null,SELECTION_PHONE, SELECTION_ARRAY_PHONE, null);
            int indexPhoneNo = currPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int indexPhoneType = currPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);

            if (currPhone.getCount() > 0) {
                while (currPhone.moveToNext()) {
                    String phoneNoStr = currPhone.getString(indexPhoneNo);
                    String phoneTypeStr = currPhone.getString(indexPhoneType);
                    phoneBuilder.append(phoneNoStr + ", \n");
                    Log.d("Phone Numbers " , " " + phoneNoStr.concat(" Type :".concat(phoneTypeStr)));
                }
            }

            currPhone.close();

        } catch (Exception e) {
            Log.e(GetContactDetailsActivity.class.getName(), "Failed to get email data", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            tvName.setText("Name : " + name.concat(" \n Email :" + emailBuilder.toString()).concat(" \n Contact : " + phoneBuilder.toString()));
            if (email.length() == 0 && name.length() == 0) {
                Toast.makeText(this, "No Email for Selected Contact", Toast.LENGTH_LONG).show();
            }
        }
    }

    public ArrayList<String> ShowContact() {

        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> phoneList = new ArrayList<String>();
        ArrayList<String> emailList = new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // Query phone here. Covered next
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        // Do something with phones
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        nameList.add(name); // Here you can list of contact.
                        phoneList.add(phoneNo); // Here you will get list of phone number.

                        Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            emailList.add(email); // Here you will get list of email

                        }
                        emailCur.close();
                    }
                    pCur.close();
                }
            }
        }

        return nameList; // here you can return whatever you want.
    }


    public void getContacts() {
        ContentResolver cr = getApplicationContext().getContentResolver();

        Cursor managedCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_URI}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE NOCASE ASC");


        while (managedCursor.moveToNext()) {
            String id = managedCursor.getString(0);
            String name = managedCursor.getString(1);
            String phoneNumber = managedCursor.getString(2);
            String image_uri = managedCursor.getString(3);
            email = getEmail(id);
            contact_id.add(id);
            namelist.add(name);
            number.add(phoneNumber);
            contact_image_uri.add(image_uri);
            contact_email.add(email);
        }
        managedCursor.close();
    }


    private String getEmail(String contactId) {

        String mailE = null;
        ContentResolver cr = getApplicationContext().getContentResolver();
        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contactId}, null);

        while (cursor.moveToNext()) {
            mailE = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
        }

        cursor.close();
        return mailE;
    }
}
