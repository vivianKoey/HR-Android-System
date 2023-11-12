package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.sql.Connection
import java.util.ArrayList


class actSendPayslip : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        attachmentUri.value = uri
    }

    private val attachmentUri = mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        AppBar4(
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
                                                this@actSendPayslip,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
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
                                                this@actSendPayslip,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
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
                                                this@actSendPayslip,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
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
                                                this@actSendPayslip,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actSendPayslip,
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
//                    SendEmailWithAttachmentExample()
                    openEmailer()
                }
            }
        }
    }

    @Composable
    fun SendEmailWithAttachmentExample() {
        val context = LocalContext.current

        Column {
            Button(onClick = { getContent.launch("*/*") }) {
                Text(text = "Select Attachment")
            }

//            Button(onClick = { sendEmail(context, attachmentUri.value) }) {
//                Text(text = "Send Email")
//            }
        }
    }



    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun openEmailer() {


        // in the below line, we are
        // creating variables for URL
        val senderEmail = remember {
            mutableStateOf(TextFieldValue())
        }
        val emailSubject = remember {
            mutableStateOf("Payslip")
        }
        val emailBody = remember {
            mutableStateOf("Hi, this is payslip. You are able to download and view.")
        }

        // on below line we are creating
        // a variable for a context
        val ctx = LocalContext.current

        var arrEmInfo by rememberSaveable { mutableStateOf(ArrayList<String>()) }
        var selectedEmInfo by rememberSaveable { mutableStateOf("Choose Employee") }
        var txtEmail by rememberSaveable { mutableStateOf("") }
        Row {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var conn: Connection? = null
                val connsql = MainActivity()
                conn = connsql.connClass()
                Log.i(ControlsProviderService.TAG, "conn=$conn")
                val bolStatus: Boolean = false
                arrEmInfo.clear()
//                                var arrEmInfo by remember {
//                                    mutableStateOf(arrEmInfo1)
//                                }
                if (conn != null) {
                    val sQuery = "SELECT * FROM tblEmployeeRecord"
                    val st2 = conn?.createStatement()
                    val rs = st2?.executeQuery(sQuery)
                    var emID: Int = 0
                    while (rs?.next() == true) {
                        arrEmInfo.add(rs.getString(1).toString()+"," + rs.getString(2).toString())
                    }
                    var expanded by remember {
                        mutableStateOf(false)
                    }
//                                    var selectedEmInfo by remember {
//                                        mutableStateOf(arrEmInfo[0])
//                                    }
//                                    selectedEmInfo2=selectedEmInfo
//                                    selectedEmInfo=arrEmInfo[0]
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(70.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }) {
                            TextField(
                                value = selectedEmInfo,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                textStyle = TextStyle.Default.copy(fontSize = 10.sp)
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                arrEmInfo.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedEmInfo = it
                                            expanded = false

                                            var emInfo=
                                                TextUtils.split(selectedEmInfo, ",")
                                            var emID=emInfo[0]

                                            val sQuery = "SELECT * FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emID) =  N'"+ emID + "'"
                                            val st2 = conn!!.createStatement()
                                            val rs = st2.executeQuery(sQuery)
                                            while (rs.next()) {
                                                arrEmInfo.add(
                                                    rs.getString(1)
                                                        .toString() + "," + rs.getString(
                                                        2
                                                    ).toString()
                                                )
                                                txtEmail = rs.getString(14).toString()
                                            }
                                        }) {
                                        Text(text = it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // on below line we are creating a column
        Column(
            // on below line we are specifying modifier
            // and setting max height and max width
            // for our column
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth()
                // on below line we are
                // adding padding for our column
                .padding(5.dp),
            // on below line we are specifying horizontal
            // and vertical alignment for our column
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // on the below line, we are
            // creating a text field.
            TextField(
                // on below line we are specifying
                // value for our  text field.
                value = senderEmail.value,

                // on below line we are adding on value
                // change for text field.
                onValueChange = { senderEmail.value = it },

                // on below line we are adding place holder as text
                placeholder = { Text(text = txtEmail) },

                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                // on below line we are
                // adding single line to it.
                singleLine = true,
            )
            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(10.dp))
            var txtEmailSub by rememberSaveable { mutableStateOf("Payslip") }

            // on the below line, we are creating a text field.
            TextField(
                // on below line we are specifying
                // value for our  text field.
                value = emailSubject.value,

                // on below line we are adding on value change
                // for text field.
                onValueChange = { emailSubject.value = it },

                // on below line we are adding place holder as text
                placeholder = { Text(text ="") },

                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                // on below line we are
                // adding single line to it.
                singleLine = true,
            )
            var txtEmailBody by rememberSaveable { mutableStateOf("Hi, this is payslip. You are able to download and view.") }
            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(10.dp))

            // on the below line, we are creating a text field.
            TextField(
                // on below line we are specifying
                // value for our  text field.
                value = emailBody.value,

                // on below line we are adding on value
                // change for text field.
                onValueChange = { emailBody.value = it },

                // on below line we are adding place holder as text
                placeholder = { Text(text = "") },

                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                // on below line we are
                // adding single line to it.
                singleLine = true,
            )

            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(20.dp))

//            // on below line adding a
//            // button to send an email
//            Button(onClick = {
//
////                // on below line we are creating
////                // an intent to send an email
////                val i = Intent(Intent.ACTION_SEND)
////
////                // on below line we are passing email address,
////                // email subject and email body
////                val emailAddress = arrayOf(senderEmail.value.text)
////                i.putExtra(Intent.EXTRA_EMAIL,emailAddress)
////                i.putExtra(Intent.EXTRA_SUBJECT,emailSubject.value.text)
////                i.putExtra(Intent.EXTRA_TEXT,emailBody.value.text)
////i.putExtra(Intent.EXTRA_STREAM,attachmentUri)
////                // on below line we are
////                // setting type of intent
////                i.setType("message/rfc822")
////
////                // on the below line we are starting our activity to open email application.
////                ctx.startActivity(Intent.createChooser(i,"Choose an Email client : "))
//
//            }) {
//                // on the below line creating a text for our button.
//                Text(
//                    // on below line adding a text ,
//                    // padding, color and font size.
//                    text = "Send Email",
//                    modifier = Modifier.padding(10.dp),
//                    color = Color.White,
//                    fontSize = 15.sp
//                )
//            }
            Button(onClick = { sendEmail(ctx, attachmentUri.value,txtEmail,emailSubject.value,emailBody.value) }) {
                Text(text = "Send Email")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { getContent.launch("*/*") }) {
                Text(text = "Select Attachment")
            }
//
        }
    }
    private fun sendEmail(context: Context, attachmentUri: Uri?, sender:String,subject:String,body:String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(sender))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)

        attachmentUri?.let { uri ->
            intent.putExtra(Intent.EXTRA_STREAM, uri)
        }

        context.startActivity(Intent.createChooser(intent, "Send Email"))
    }
}

