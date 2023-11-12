package com.example.myapplication.backup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class MainActivity3 : AppCompatActivity() {

    val ip =
        "192.168.100.31" // this is the host ip that your data base exists on you can use 10.0.2.2 for local host                                                    found on your pc. use if config for windows to find the ip if the database exists on                                                    your pc

    val port = "1433" // the port sql server runs on

    var Classes =
        "net.sourceforge.jtds.jdbc.Driver" // the driver that is required for this connection use                                                                           "org.postgresql.Driver" for connecting to postgresql

    val database = "employeeHR" // the data base name

    val username = "weiwen" // the user name

    val password = "kk@321" // the password

    val url =
        "jdbc:jtds:sqlserver://$ip:$port/$database" // the connection url string


    var connection: Connection? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        connClass()
    }
    fun connClass(): Connection? {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            PackageManager.PERMISSION_GRANTED
        )
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            Class.forName(Classes)
            connection = DriverManager.getConnection(
                url,
               username,
               password
            )
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            Log.i(ControlsProviderService.TAG, "Class fail")
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i(ControlsProviderService.TAG, "Connected no")
        }
        return connection

    }
}