package com.neocaptainnemo.resources28thmarch.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.neocaptainnemo.resources28thmarch.R;
import com.neocaptainnemo.resources28thmarch.storage.Theme;
import com.neocaptainnemo.resources28thmarch.storage.ThemeStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThemeStorage storage = ThemeStorage.getInstance(getApplicationContext());

        Theme savedTheme = storage.getTheme();

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    Theme chosenTheme = (Theme) data.getSerializableExtra(ThemeSelectionActivity.CHOSEN_THEME);

                    storage.saveTheme(chosenTheme);

                    recreate();
                }
            }
        });


        setTheme(savedTheme.getTheme());

        setContentView(R.layout.activity_calc);

        float dimenSize = getResources().getDimension(R.dimen.hello_world_size);

        int color = ContextCompat.getColor(this, R.color.hello_world_color);

        Toast.makeText(this, getString(R.string.hello_world) + dimenSize, Toast.LENGTH_SHORT).show();

        TextInputLayout inputLayout = findViewById(R.id.text_input_layout);
        TextInputEditText editText = findViewById(R.id.text_input_edit);

        if (getIntent() != null && getIntent().hasExtra("welcome")) {
            String message = getIntent().getStringExtra("welcome");

            editText.setText(message);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() < 5) {
                    inputLayout.setError(getString(R.string.input_error));
                    inputLayout.setErrorEnabled(true);
                } else {
                    inputLayout.setErrorEnabled(false);
                }
            }
        });

        Button showToast = findViewById(R.id.toast_button);

        if (showToast != null) {
            showToast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, R.string.show_toast, Toast.LENGTH_SHORT).show();
                }
            });

        }

        findViewById(R.id.preferences).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThemeSelectionActivity.class);
                intent.putExtra(ThemeSelectionActivity.SELECTED_THEME, savedTheme);

                launcher.launch(intent);
            }
        });

        findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("some://yandex.ru");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);

                startActivity(Intent.createChooser(intent, null));
            }
        });
    }
}