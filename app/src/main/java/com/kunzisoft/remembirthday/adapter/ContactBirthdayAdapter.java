package com.kunzisoft.remembirthday.adapter;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunzisoft.remembirthday.R;
import com.kunzisoft.remembirthday.Utility;
import com.kunzisoft.remembirthday.element.Contact;
import com.kunzisoft.remembirthday.element.ContactBirthday;
import com.kunzisoft.remembirthday.element.DateUnknownYear;

/**
 * Adapter linked to contacts with birthday for data feeding
 */
public class ContactBirthdayAdapter extends ContactAdapter<ContactBirthdayViewHolder, ContactBirthday> {

    private static final String TAG = "ContactBirthdayAdapter";

    private final int contactBirthdayColIdx;

    public ContactBirthdayAdapter(Cursor cursor) {
        super(cursor);
        this.contactBirthdayColIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
    }

    @Override
    public ContactBirthdayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemListBuddyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contacts_birthday, parent, false);
        return new ContactBirthdayViewHolder(itemListBuddyView);
    }

    @Override
    protected Contact getItemFromCursor(Cursor cursor) {
        DateUnknownYear dateUnknownYear;
        try {
            dateUnknownYear = DateUnknownYear.stringToDateWithKnownYear(cursor.getString(contactBirthdayColIdx));
        } catch (Exception e) {
            Log.e(TAG, "Birthday can't be extract : " + e.getMessage());
            dateUnknownYear = DateUnknownYear.getDefault();
        }

        return new ContactBirthday(cursor.getLong(contactIdColIdx),
                cursor.getString(contactNameColIdx),
                dateUnknownYear);
    }

    @Override
    protected void assignDataToView(ContactBirthdayViewHolder holder, ContactBirthday contact) {
        super.assignDataToView(holder, contact);

        holder.age.setText(String.valueOf(contact.getAge()));
        Utility.assignDaysRemainingInTextView(holder.daysLeft, contact.getBirthdayDaysRemaining());
    }

}
