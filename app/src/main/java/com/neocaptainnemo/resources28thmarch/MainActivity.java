package com.neocaptainnemo.resources28thmarch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private static final String THEME_KEY = "THEME_KEY";

    private static final String THEME_ONE = "THEME_ONE";
    private static final String THEME_TWO = "THEME_TWO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String theme = sharedPreferences.getString(THEME_KEY, THEME_ONE);

        switch (theme){
            case THEME_TWO:
                setTheme(R.style.Theme_Resources28thMarch_V2);
                break;
            default:
                setTheme(R.style.Theme_Resources28thMarch);
                break;
        }

        setContentView(R.layout.activity_calc);

        float dimenSize = getResources().getDimension(R.dimen.hello_world_size);

        int color = ContextCompat.getColor(this, R.color.hello_world_color);

        Toast.makeText(this, getString(R.string.hello_world) + dimenSize, Toast.LENGTH_SHORT).show();


        TextInputLayout inputLayout = findViewById(R.id.text_input_layout);
        TextInputEditText editText = findViewById(R.id.text_input_edit);

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

        findViewById(R.id.theme_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences.edit()
                        .putString(THEME_KEY, THEME_ONE)
                        .apply();

                recreate();
            }
        });

        findViewById(R.id.theme_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit()
                        .putString(THEME_KEY, THEME_TWO)
                        .apply();

                recreate();
            }
        });

    }
}