package com.example.myapplication.backup;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity2 extends AppCompatActivity {
Connection conn;
String connResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try {
            MainActivity connsql = new MainActivity();
            conn=connsql.connClass();
            Log.i(TAG,"conn=" + conn);

            if (conn!=null){
//                String emName="Select * from tblEmployeeRecord";
//                Statement st=conn.createStatement();
//                ResultSet rs=st.executeQuery(emName);
//
//                while(rs.next()){
//                    Log.i(TAG,"rs result="+ rs.getString(2));
//                }
                String iQuery="INSERT INTO tblEmployeeRecord (emID, emName) values ('5','Emma'); ";
                Statement st=conn.createStatement();
                st.executeUpdate(iQuery);

            }else{
                Log.i(TAG,"check connection");
            }
        }catch (Exception ex){
            Log.e(TAG,"exMessage=" + ex.getMessage());
        }
    }
}