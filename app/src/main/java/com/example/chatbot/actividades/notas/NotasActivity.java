package com.example.chatbot.actividades.notas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotasActivity extends AppCompatActivity {
    private static final int REQUEST_ITEM_ADD = 111;
    private static final int REQUEST_ITEM_EDIT = 222;
    private static final int DEFAULT_VALUE = -1;
    private final ItemAdapter itemAdapter = new ItemAdapter();
    private ItemViewModel itemViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        initialize();
        setListToBeDisplayed();
        swipeToDeleteLeftRight();
        editItem();
    }

    private void swipeToDeleteLeftRight() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(itemAdapter.getItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void editItem() {
        itemAdapter.mySetOnItemClickListener(new ItemAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(Item item) {
                Intent intentEditItem = new Intent(NotasActivity.this, AddEditItemActivity.class);
                intentEditItem.putExtra(AddEditItemActivity.STRING_INT_VALUE_PUT_EXTRA_INTENT, item.getId());
                intentEditItem.putExtra(AddEditItemActivity.TITLE_PUT_EXTRA_INTENT, item.getItemTitle());
                intentEditItem.putExtra(AddEditItemActivity.DESCRIPTION_PUT_EXTRA_INTENT, item.getItemDescription());
                intentEditItem.putExtra(AddEditItemActivity.TIME_PUT_EXTRA_INTENT, item.getItemTime());
                startActivityForResult(intentEditItem, REQUEST_ITEM_EDIT);
            }
        });
    }

    private void initialize() {
        initFloatingActionButton();
        initRecyclerView();
    }

    private void initFloatingActionButton() {
        floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButtonSetOnClickListener();
    }

    private void floatingActionButtonSetOnClickListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddItem = new Intent(NotasActivity.this, AddEditItemActivity.class);
                startActivityForResult(intentAddItem, REQUEST_ITEM_ADD);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itemAdapter);
    }

    private void setListToBeDisplayed() {
        itemViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(ItemViewModel.class);

        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                itemAdapter.submitList(items);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_delete_item, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ITEM_ADD && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(AddEditItemActivity.TITLE_PUT_EXTRA_INTENT);
            String description = data.getStringExtra(AddEditItemActivity.DESCRIPTION_PUT_EXTRA_INTENT);
            String time = data.getStringExtra(AddEditItemActivity.TIME_PUT_EXTRA_INTENT);

            Item item = new Item(title, description, time);
            itemViewModel.insert(item);
            toastShow(getResources().getString(R.string.saved_successfully));
        } else if (requestCode == REQUEST_ITEM_EDIT && resultCode == RESULT_OK) {
            assert data != null;
            int id = data.getIntExtra(AddEditItemActivity.STRING_INT_VALUE_PUT_EXTRA_INTENT, DEFAULT_VALUE);

            if (id == DEFAULT_VALUE) {
                toastShow(getResources().getString(R.string.not_updated));
                return;
            }

            String title = data.getStringExtra(AddEditItemActivity.TITLE_PUT_EXTRA_INTENT);
            String description = data.getStringExtra(AddEditItemActivity.DESCRIPTION_PUT_EXTRA_INTENT);
            String time = data.getStringExtra(AddEditItemActivity.TIME_PUT_EXTRA_INTENT);

            Item item = new Item(title, description, time);
            item.setId(id);
            itemViewModel.update(item);
            toastShow(getResources().getString(R.string.updated));
        } else {
            toastShow(getResources().getString(R.string.not_saved));
        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_items) {
            itemViewModel.deleteAllItems();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}