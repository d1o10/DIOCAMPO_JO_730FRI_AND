package com.example.listview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    private ListView listView;
    private ArrayList<Item> itemList;
    private CustomAdapter adapter;
    private ImageView currentImageView; // Track the current ImageView to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Edge-to-Edge handling for status and navigation bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addItemEditText), (view, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(0, systemBarsInsets.top, 0, systemBarsInsets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        listView = findViewById(R.id.listView);
        itemList = new ArrayList<>();
        adapter = new CustomAdapter(this, itemList);
        listView.setAdapter(adapter);

        EditText addItemEditText = findViewById(R.id.addItemEditText);
        addItemEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Item");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final ImageView imageView = view.findViewById(R.id.dialogImage);
        final EditText nameEditText = view.findViewById(R.id.dialogName);

        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        itemList.add(new Item(name, image));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

        currentImageView = imageView; // Set the current ImageView for dialog
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void showEditDialog(final Item item, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final ImageView imageView = view.findViewById(R.id.dialogImage);
        final EditText nameEditText = view.findViewById(R.id.dialogName);

        nameEditText.setText(item.getName());
        imageView.setImageBitmap(item.getImage());

        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        item.setName(name);
                        item.setImage(image);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

        currentImageView = imageView; // Set the current ImageView for dialog
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void deleteItem(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                if (currentImageView != null) {
                    currentImageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class CustomAdapter extends ArrayAdapter<Item> {
        public CustomAdapter(Context context, ArrayList<Item> items) {
            super(context, R.layout.itemlist, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Reuse the convertView if possible to optimize memory usage
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemlist, parent, false);
            }

            final Item item = getItem(position);

            // Ensure item is not null
            if (item != null) {
                // Find views from the itemlist layout
                ImageView imageView = convertView.findViewById(R.id.itemImage);
                TextView nameTextView = convertView.findViewById(R.id.itemName);
                Button editButton = convertView.findViewById(R.id.editButton);
                Button deleteButton = convertView.findViewById(R.id.deleteButton);

                // Set the image and text to the corresponding views
                imageView.setImageBitmap(item.getImage());
                nameTextView.setText(item.getName());

                // Set click listeners for edit and delete buttons
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditDialog(item, position);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(position);
                    }
                });
            }

            return convertView;
        }
    }
}
