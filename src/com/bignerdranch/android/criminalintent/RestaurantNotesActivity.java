package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by aakritprasad on 8/28/13.
 */
public class RestaurantNotesActivity extends Activity implements Serializable
{
    private static final String TAG = "RestNotesActivity";
    public static final String EXTRA_NOTES = "criminalintent.NOTES";


    private EditText mRestNotes;
    private TextView mTextView;
    private Button mSave, mCancel;
    private Restaurant mRestaurant = null;

    private String restNotes = null;
    private String oldNotes = null;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_rest_notes);
        String name = (String) getIntent().getExtras().get("NAME");
        String oldNotes = (String) getIntent().getExtras().get("OLDNOTES");

        mTextView = (TextView)findViewById(R.id.rest_notes_textView);
        mTextView.setText("Add some Notes for: "+name+" ");

        mRestNotes = (EditText)findViewById(R.id.rest_notes_editText);
        if(mRestNotes != null)
            mRestNotes.setText(oldNotes);

        mRestNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                  restNotes = charSequence.toString();
//                mCallbacks.onCrimeUpdated(mRestaurant);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mSave = (Button)findViewById(R.id.rest_notes_button_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save and return to list view
                Intent resultData = new Intent();
                resultData.putExtra(EXTRA_NOTES, restNotes);
                setResult(Activity.RESULT_OK, resultData);
                finish();
            }
        });

        mCancel = (Button)findViewById(R.id.rest_notes_button_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //return to list view and clean start

                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }
}
