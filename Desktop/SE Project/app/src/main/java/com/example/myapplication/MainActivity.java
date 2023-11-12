package com.example.myapplication;

import static android.service.controls.ControlsProviderService.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends Activity {

    private Connection connection = null;
    @SuppressLint("NewAPI")

    public Connection connClass(){

        String ip=sqlInfo.INSTANCE.getIp();
        String port=sqlInfo.INSTANCE.getPort();
       String Classes = "net.sourceforge.jtds.jdbc.Driver";// the driver that is required for this connection use                                                                           "org.postgresql.Driver" for connecting to postgresql
       String database = "employeeHR";// the data base name
        String username = sqlInfo.INSTANCE.getUsername();// the user name
        String password = sqlInfo.INSTANCE.getPassword();// the password
        String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database; // t
        Log.i(TAG,"after connClass");

        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        Log.i(TAG,"after requestPermissions");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.i(TAG,"after StrictMode");

        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Log.i(TAG,"connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG,"Class fail");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i(TAG,"Connected no");
        }
        Log.i(TAG,"connection=" + connection);

        return connection;
    }
}