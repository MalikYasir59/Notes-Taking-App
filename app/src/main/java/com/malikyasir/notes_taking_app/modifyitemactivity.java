package com.malikyasir.notes_taking_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class modifyitemactivity extends Activity implements View.OnClickListener {

    private EditText titleText;
    private EditText descText;
    private Button updateBtn, deleteBtn;
    private long id;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify Record");
        setContentView(R.layout.activity_modifyitemactivity);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = findViewById(R.id.subject_edittext);
        descText = findViewById(R.id.description_edittext);
        updateBtn = findViewById(R.id.btn_update);
        deleteBtn = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String idStr = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        assert idStr != null;
        id = Long.parseLong(idStr);
        titleText.setText(name);
        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            if (v.getId() == R.id.btn_update) {
                String title = titleText.getText().toString();
                String desc = descText.getText().toString();
                dbManager.update(id, title, desc);
                returnHome();
            } else if (v.getId() == R.id.btn_delete) {
                dbManager.delete(id);
                returnHome();
            }
        }




    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), noteslistactivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
