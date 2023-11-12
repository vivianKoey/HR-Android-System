package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.backup.EmployeeDatabase
import kotlinx.coroutines.launch
import java.sql.Connection
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class actCreatePost : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = EmployeeDatabase.getDatabase(applicationContext)

        val recipeDao = database.EmployeeDao()
        setContent {

            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var expanded by remember { mutableStateOf(false) }
                var listItems: ArrayList<String>
                var selectedItem by remember {
                    mutableStateOf("annual type")
                }
                var txtPosition by rememberSaveable { mutableStateOf("") }
                var txtName by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar3(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            },
                            "Create Post"
                        )
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        var conn: Connection? = null
                        val connsql = MainActivity()
                        conn = connsql.connClass()

                        if (conn != null) {
                            var username = activeUser.username;
                            val sQuery = "SELECT * FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emName) =  N'" + username + "'"
                            val st2 = conn.createStatement()
                            val rs = st2.executeQuery(sQuery)
                            var emID: Int = 0
                            while (rs.next()) {
                                txtPosition = rs.getString(10)

                            }
                        }
                            if (txtPosition != "HR") {

                                DrawerBody(
                                    items = listOf(
                                        MenuItem(
                                            id = "actEmLeave",
                                            title = "Leave",
                                            contentDescription = "Go to Act Employee Leave",
                                            icon = Icons.Default.Home
                                        ),
                                        MenuItem(
                                            id = "actEmClaim",
                                            title = "Claim",
                                            contentDescription = "Go to Act Employee Claim",
                                            icon = Icons.Default.Info
                                        ),
                                        MenuItem(
                                            id = "actEmUpdatePwd",
                                            title = "Update Password",
                                            contentDescription = "Go to Act Update Pwd",
                                            icon = Icons.Default.Favorite
                                        ),
                                        MenuItem(
                                            id = "logout",
                                            title = "Logout Account",
                                            contentDescription = "Go to Logout Account",
                                            icon = Icons.Default.ExitToApp
                                        ),
                                    ),
                                    onItemClick = {
                                        if (it.id == "actEmLeave") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actEmLeave::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actCreatePost,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                        }
                                        if (it.id == "actEmUpdatePwd") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actEmUpdatePassword::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "logout") {
                                            var username = activeUser.username;
                                            var conn2: Connection? = null
                                            val connsql = MainActivity()
                                            conn2 = connsql.connClass()
                                            val iQuery =
                                                "UPDATE tblEmployeeRecord\n" +
                                                        "SET emActive='false'\n" +
                                                        "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'" + username + "';"
                                            val st = conn2.createStatement()
                                            var rs = st.executeUpdate(iQuery)
                                            if (rs == 1) {
                                                Toast.makeText(
                                                    this@actCreatePost,
                                                    "Logout Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actLoginPage::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        println("Clicked on ${it.title}")
                                    }
                                )
                            } else {
                                DrawerBody(
                                    items = listOf(
                                        MenuItem(
                                            id = "actEmployeeDataHR",
                                            title = "Employee Data HR",
                                            contentDescription = "Go to Act Employee Data HR",
                                            icon = Icons.Default.Home
                                        ),
                                        MenuItem(
                                            id = "actGeneratePayslip",
                                            title = "Generate Payslip",
                                            contentDescription = "Go to Act Generate Payslip",
                                            icon = Icons.Default.Info
                                        ),
                                        MenuItem(
                                            id = "actHRLeave",
                                            title = "HR Leave",
                                            contentDescription = "Go to Act HR Leave",
                                            icon = Icons.Default.AccountCircle
                                        ),
                                        MenuItem(
                                            id = "actHRPCBCal",
                                            title = "PCB Cal",
                                            contentDescription = "Go to Act HR PCB Cal",
                                            icon = Icons.Default.Edit
                                        ),
                                        MenuItem(
                                            id = "actSendPayslip",
                                            title = "Send Payslip",
                                            contentDescription = "Go to Act HR Send Payslip",
                                            icon = Icons.Default.Email
                                        ),
                                        MenuItem(
                                            id = "actHRClaim",
                                            title = "HR Claim",
                                            contentDescription = "Go to Act HR Claim",
                                            icon = Icons.Default.Email
                                        ),
                                        MenuItem(
                                            id = "actHRPost",
                                            title = "HR Post",
                                            contentDescription = "Go to Act HR Post",
                                            icon = Icons.Default.Person
                                        ),
                                        MenuItem(
                                            id = "logout",
                                            title = "Logout Account",
                                            contentDescription = "Go to Logout Account",
                                            icon = Icons.Default.ExitToApp
                                        ),
                                    ),
                                    onItemClick = {
                                        if (it.id == "actEmployeeDataHR") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actEmployeeDataHR::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actGeneratePayslip") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actGeneratePayslip::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRLeave") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actHRLeave::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRPCBCal") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actHRPCBCal::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actSendPayslip") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actSendPayslip::class.java
                                                )
                                            startActivity(intent)
                                        }

                                        if (it.id == "actHRClaim") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actHRClaim::class.java
                                                )
                                            startActivity(intent)
                                        }

                                        if (it.id == "actHRPost") {
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actCreatePost::class.java
                                                )
                                            startActivity(intent)
                                        }

                                        if (it.id == "logout") {
                                            var username=activeUser.username;
                                            var conn2: Connection? = null
                                            val connsql = MainActivity()
                                            conn2 = connsql.connClass()
                                            val iQuery =
                                                "UPDATE tblEmployeeRecord\n" +
                                                        "SET emActive='false'\n" +
                                                        "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ username + "';"
                                            val st = conn2?.createStatement()
                                            var rs= st?.executeUpdate(iQuery)
                                            if (rs==1) {
                                                Toast.makeText(
                                                    this@actCreatePost,
                                                    "Logout Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val intent =
                                                Intent(
                                                    this@actCreatePost,
                                                    actLoginPage::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        println("Clicked on ${it.title}")
                                    }
                                )
                            }
                        }
                ) {
                    var txtTitle by rememberSaveable { mutableStateOf("") }
                    var txtPost by rememberSaveable { mutableStateOf("") }

                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(40.dp) ) {

                        TextField(value = txtTitle, onValueChange ={
                            txtTitle=it
                        },
                            placeholder = {
                                Text(text="Post HR Title")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(value = txtPost, onValueChange ={
                            txtPost=it
                        },
                            placeholder = {
                                Text(text="Post HR Info")
                            }
                        )
                        var iQuery: String? =null
                        var emID: Int = 0
                        var emName:String=""
                        Button(onClick = {
                            var conn: Connection? = null
                            val connsql = MainActivity()
                            conn = connsql.connClass()
                            if (conn != null) {
                                val sQuery =
                                    "SELECT * FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emActive) =  N'true'"
                                val st2 = conn!!.createStatement()
                                val rs2 = st2.executeQuery(sQuery)

                                while (rs2.next()) {
                                    emID = rs2.getString(1).toInt()
                                    emName=rs2.getString(2).toString()
                                }
                            }
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val current = LocalDateTime.now().format(formatter)
                            Log.d(TAG,"currentDateAndTime=" + current)
                            iQuery =
                                "INSERT INTO tblHRPost (emID,emName,post,postDate,postTitle) values ('$emID','$emName','$txtPost','$current','$txtTitle'); "
                            val st = conn!!.createStatement()
                            var rs= st.executeUpdate(iQuery)
                            if (rs==1) {
                                Toast.makeText(
                                    this@actCreatePost,
                                    "Post Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text(text = "Post")
                        }
                    }
                }
            }
        }
    }
}

