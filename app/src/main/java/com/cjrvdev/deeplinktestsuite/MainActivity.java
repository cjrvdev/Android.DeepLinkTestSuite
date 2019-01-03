package com.cjrvdev.deeplinktestsuite;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvPreview;
    EditText etScheme, etHost, etData;
    CheckBox cbNewTask, cbSingleTop, cbClearTop;

    MyTextWatcher textListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPreview = findViewById(R.id.tvPreview);
        etScheme = findViewById(R.id.etScheme);
        etHost = findViewById(R.id.etHost);
        etData = findViewById(R.id.etData);
        cbNewTask = findViewById(R.id.cbNewTask);
        cbSingleTop = findViewById(R.id.cbSingleTop);
        cbClearTop = findViewById(R.id.cbClearTop);

        textListener = new MyTextWatcher();

        AutofillData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        etScheme.addTextChangedListener(textListener);
        etHost.addTextChangedListener(textListener);
        etData.addTextChangedListener(textListener);
    }

    @Override
    protected void onPause() {
        etScheme.removeTextChangedListener(textListener);
        etHost.removeTextChangedListener(textListener);
        etData.removeTextChangedListener(textListener);

        super.onPause();
    }

    public void btStartIntent(View view) {
        Uri uri = Uri.parse(tvPreview.getText().toString());

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        LoadIntentFlags(intent);

        try{
            startActivity(intent);

        }catch (ActivityNotFoundException ex){
            Toast.makeText(this, "Activity Not Found", Toast.LENGTH_LONG).show();
        }
    }

    private void AutofillData() {
        etScheme.setText("ibercajaapptest");
        etHost.setText("deeplink");
        etData.setText("/?data=");

        ComposeUri();
    }

    private void LoadIntentFlags(Intent intent) {
        if (cbNewTask.isChecked()){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (cbSingleTop.isChecked()){
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        if (cbClearTop.isChecked()){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
    }

    private String ComposeUri(){
        String uriPreview = etScheme.getText() + "://" + etHost.getText() + etData.getText();
        tvPreview.setText(uriPreview);

        return uriPreview;
    }

    class MyTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ComposeUri();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
