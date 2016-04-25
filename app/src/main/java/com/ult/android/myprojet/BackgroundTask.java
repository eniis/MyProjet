package com.ult.android.myprojet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Mohamed on 18/04/2016.
 */
public class BackgroundTask extends AsyncTask<String, Void, String> {

    Context ctx;
    AlertDialog alertDialog;
    BackgroundTask(Context ctx)
    {
        this.ctx = ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://10.0.3.2/php/register.php";
        String login_url = "http://10.0.3.2/php/login.php";
        String method = params[0];
        if(method.equals("register"))
        {

            String nom = params[1];
            String prenom = params[2];
            String email = params[3];
            String pwd = params[4];
            String tel = params[5];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("nom", "UTF-8")+"="+URLEncoder.encode(nom,"UTF-8")+"&"+
                    URLEncoder.encode("prenom", "UTF-8")+"="+URLEncoder.encode(prenom,"UTF-8")+"&"+
                    URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                    URLEncoder.encode("pwd", "UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8")+"&"+
                    URLEncoder.encode("tel", "UTF-8")+"="+URLEncoder.encode(tel,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Success..";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(method.equals("login"))
        {
            String email = params[1];
            String pwd = params[2];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("pwd", "UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while (( line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public String getResult(){
        return this.doInBackground();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        if (result.equals("Registration Success.."))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

        }else if(result.equals("Login Failed ... Try Again!"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}
