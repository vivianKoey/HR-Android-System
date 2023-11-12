package com.example.myapplication

//import com.example.myapplication.DB.DatabaseHelper
import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorFilter
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.myapplication.backup.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.sql.Connection
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class actEmDashboard : ComponentActivity() {
//    var dbHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dbHelper = DatabaseHelper(this)
    val database = LeaveDatabase.getDatabase(applicationContext)
    val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1

// Check if the permission is already granted
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Toast.makeText(
            this@actEmDashboard,
            "!PERMISSION_GRANTED",
            Toast.LENGTH_SHORT
        ).show()
        // Permission is not granted, request it
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
        )
    }
        setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var txtPosition by rememberSaveable { mutableStateOf("") }
                var txtName by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBarDahsboard(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
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
                                        id = "actEmDashboard",
                                        title = "Dashboard",
                                        contentDescription = "Go to Act Dashboard",
                                        icon = Icons.Default.List
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
                                                this@actEmDashboard,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actEmUpdatePassword::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmDashboard") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actEmDashboard::class.java
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
                                                this@actEmDashboard,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
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
                                    if (it.id == "actEmployeeDataHR") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actCreatePost::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
                                                actEmUpdatePassword::class.java
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
                                                this@actEmDashboard,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmDashboard,
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
                    val items= remember{ mutableListOf<Post>()}
                    val selectedName = remember { mutableStateOf("Name") }
                    val selectedPost = remember { mutableStateOf("Post") }

                    // on below line we are creating and initializing our array list
                    LazyColumn {
                        var conn: Connection? = null
                        val connsql = MainActivity()
                        conn = connsql.connClass()
                        Log.i(ControlsProviderService.TAG, "conn=$conn")
                        val bolStatus: Boolean = false
//                        arrEmInfo.clear()
//                                var arrEmInfo by remember {
//                                    mutableStateOf(arrEmInfo1)
//                                }
                        if (conn != null) {
                            val sQuery = "SELECT * FROM tblHRPost"
                            val st2 = conn?.createStatement()
                            val rs = st2?.executeQuery(sQuery)
                            var emID: Int = 0
                            while (rs?.next() == true) {
//                                Toast.makeText(
//                                    this@actHRLeave,
//                                    rs.getString(2).toString(),
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                items.add(Post(rs.getString(4),rs.getString(5),rs.getString(3)))
                            }
                        }
                        // on below line we are setting data for each item of our listview.
                        itemsIndexed(items) { index, item ->
                            // on below line we are creating a card for our list view item.
                            var name = "Thundersoft HR"
                            var postTitle = items[index].postTitle
                            var postContent = items[index].postContent

                            Card(
                                // on below line we are adding padding from our all sides.
                                modifier = Modifier.padding(8.dp),
                                // on below line we are adding elevation for the card.
                                elevation = 6.dp
                            ) {
                                // on below line we are creating a row for our list view item.
                                Column(
                                    // for our row we are adding modifier to set padding from all sides.
                                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text = "Thundersoft HR",

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Left, fontWeight=FontWeight.Bold,fontSize=30.sp
                                    )

                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text = items[index].postTime,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Left,fontSize=10.sp
                                    )

                                    Spacer(modifier = Modifier.height(30.dp))

                                    // on the below line we are creating a text.
                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text =  items[index].postTitle,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Left
                                    )

                                    // on below line inside row we are adding spacer
                                    Spacer(modifier = Modifier.height(3.dp))

                                    // on the below line we are creating a text.
                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text =  items[index].postContent,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Left
                                    )
                                }
//                                    Button(
//                                        onClick = {
//
//
//                                        },
//                                        colors = ButtonDefaults.buttonColors(
//                                            backgroundColor = Color(0XFF0F9D58)
//                                        )
//                                    ) {
//
//                                        Text(
//                                            text = "Download Claim Receipt",
//                                            color = Color.White
//                                        )
//                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
}




