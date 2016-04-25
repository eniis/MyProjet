package com.ult.android.myprojet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    EditText ED_EMAIL,ED_PASS;
    String email, pwd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button breg = (Button) findViewById(R.id.btRegister);


        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ireg = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(ireg);
            }
        });


        ED_EMAIL = (EditText)findViewById(R.id.edEmail);
        ED_PASS = (EditText)findViewById(R.id.edPassword);


    }

    public void userLogin(View view)
    {
        email = ED_EMAIL.getText().toString();
        pwd = ED_PASS.getText().toString();
        String method = "login";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,email,pwd);

        
            startActivity(new Intent(this, ListActivity.class));



    }


}