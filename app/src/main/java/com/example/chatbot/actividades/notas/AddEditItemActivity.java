package com.example.chatbot.actividades.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditItemActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    public static final String TITLE_PUT_EXTRA_INTENT
            = "com.example.dailynote.TITLE_PUT_EXTRA_INTENT";
    public static final String DESCRIPTION_PUT_EXTRA_INTENT
            = "com.example.dailynote.DESCRIPTION_PUT_EXTRA_INTENT";
    public static final String TIME_PUT_EXTRA_INTENT
            = "com.example.dailynote.TIME_PUT_EXTRA_INTENTT";
    public static final String STRING_INT_VALUE_PUT_EXTRA_INTENT
            = "com.example.dailynote.STRING_INT_VALUE_PUT_EXTRA_INTENT";
    public static final int DEFAULT_VALUE_PUT_EXTRA_INTENT_ADD_ITEM_REQUEST = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);
        initialize();
        checkIntentAndSetTitle();
    }

    private void initialize() {
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void checkIntentAndSetTitle() {
        Intent intent = getIntent();
        if (intent.hasExtra(STRING_INT_VALUE_PUT_EXTRA_INTENT)) {
            setTitle(R.string.edit_note);
            editTextTitle.setText(intent.getStringExtra(TITLE_PUT_EXTRA_INTENT));
            editTextDescription.setText(intent.getStringExtra(DESCRIPTION_PUT_EXTRA_INTENT));
        } else {
            setTitle(R.string.add_note);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cancel) {
            cancelItem();
            return true;
        } else if (item.getItemId() == R.id.save) {
            saveItem();
            return true;
        } else {
            super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void cancelItem() {
        Intent intent = new Intent(AddEditItemActivity.this, NotasActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveItem() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat(getResources().getString(R.string.simple_date_format), Locale.ENGLISH);
        String time = simpleDateFormat.format(new Date());

        if (title.trim().isEmpty()) {
            toastShow(getResources().getString(R.string.please_insert_title));
            return;
        }

        Intent data = new Intent();
        data.putExtra(TITLE_PUT_EXTRA_INTENT, title);
        data.putExtra(DESCRIPTION_PUT_EXTRA_INTENT, description);
        data.putExtra(TIME_PUT_EXTRA_INTENT, time);
        int id = getIntent().getIntExtra(STRING_INT_VALUE_PUT_EXTRA_INTENT, DEFAULT_VALUE_PUT_EXTRA_INTENT_ADD_ITEM_REQUEST);

        if (id != DEFAULT_VALUE_PUT_EXTRA_INTENT_ADD_ITEM_REQUEST) {
            data.putExtra(STRING_INT_VALUE_PUT_EXTRA_INTENT, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private void toastShow(String textToast) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_layout_id));
        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(textToast);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}