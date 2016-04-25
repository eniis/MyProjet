package com.ult.android.myprojet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InscriptionActivity extends Activity {

    EditText ED_NOM, ED_PRENOM, ED_EMAIL, ED_PASS, ED_TEL;
    String nom, prenom, email, pwd, tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        final Button btannuler = (Button)findViewById(R.id.btAnn);
        btannuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        ED_NOM = (EditText)findViewById(R.id.edNom);
        ED_PRENOM = (EditText)findViewById(R.id.edPrenom);
        ED_EMAIL = (EditText)findViewById(R.id.edEmail);
        ED_PASS = (EditText)findViewById(R.id.edPassword);
        ED_TEL = (EditText)findViewById(R.id.edTele);




    }


    public void userReg(View view)
    {
        nom = ED_NOM.getText().toString();
        prenom = ED_PRENOM.getText().toString();
        email = ED_EMAIL.getText().toString();
        pwd = ED_PASS.getText().toString();
        tel = ED_TEL.getText().toString();
        String method = "register";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,nom,prenom,email,pwd,tel);
        finish();

    }




}
