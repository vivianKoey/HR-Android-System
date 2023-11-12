package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.DatePicker
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
import java.util.*


class actEmClaim : ComponentActivity() {
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
                        AppBarEmClaim(
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
                                                this@actEmClaim,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
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
                                                this@actEmClaim,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
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
                                                this@actEmClaim,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
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
                                                this@actEmClaim,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmClaim,
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

        var desc by rememberSaveable { mutableStateOf("") }
        var amount by rememberSaveable { mutableStateOf("") }
        var mDate by rememberSaveable { mutableStateOf("") }
        var claimType by rememberSaveable { mutableStateOf("Select Claim Type") }

        // on below line we are creating
        // a variable for a context
        val ctx = LocalContext.current

        // on below line we are creating a column
        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
        ) {

            // on the below line, we are
            // creating a text field.
            TextField(
                // on below line we are specifying
                // value for our  text field.
                value = desc,

                // on below line we are adding on value
                // change for text field.
                onValueChange = { desc = it },

                // on below line we are adding place holder as text
                placeholder = { Text(text = "Description") },

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
            var txtStartDate by rememberSaveable { mutableStateOf("Select Date") }
            val mContext = LocalContext.current

            // Declaring integer values
            // for year, month and day
            val mYear: Int
            val mMonth: Int
            val mDay: Int

            // Initializing a Calendar
            val mCalendar = Calendar.getInstance()

            // Fetching current year, month and day
            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH)
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

            mCalendar.time = Date()

            // Declaring a string value to
            // store date in string format

            val mDate2 = remember { mutableStateOf("") }

            // Declaring DatePickerDialog and setting
            // initial values as current values (present year, month and day)
            val mDatePickerDialog = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    mDate = "$mDayOfMonth/${mMonth+1}/$mYear"
                }, mYear, mMonth, mDay
            )

            val mDatePickerDialog2 = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    mDate2.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                }, mYear, mMonth, mDay
            )


            Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Date:  ")

                txtStartDate = "Select Date"
                Button(onClick = {
                    mDatePickerDialog.show()
                    txtStartDate = "Select Date"
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
                    if (mDate != "") {
                        txtStartDate = mDate
                    } else {
                        txtStartDate = "Select Date"
                    }

                    Text(
                        text = txtStartDate,
                        color = Color.White
                    )
                }
            }

            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(10.dp))
            var expanded by remember {
                mutableStateOf(false)
            }
            var totalRecipeType = ArrayList<String>()

            val recipeTypes = resources.getStringArray(R.array.claimtype)
            for (recipeType in recipeTypes) {
                totalRecipeType.add(recipeType)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Claim Type: ")

                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(70.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }) {
                        TextField(
                            value = claimType,
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
                            totalRecipeType.forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        claimType = it
                                        expanded = false

                                    }) {
                                    Toast.makeText(this@actEmClaim,"selectedStatus=" + claimType, Toast.LENGTH_SHORT).show()
                                    Text(text = it)
                                }
                            }
                        }
                    }
                }
            }

            // on below line adding a spacer.
            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Amount:  ")

                TextField(
                    // on below line we are specifying
                    // value for our  text field.
                    value = amount,

                    // on below line we are adding on value
                    // change for text field.
                    onValueChange = { amount = it },

                    // on below line we are adding place holder as text
                    placeholder = { Text(text = "Amount") },

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
            }

            Spacer(modifier = Modifier.height(20.dp))
//
//            Button(onClick = { getContent.launch("*/*") }) {
//                Text(text = "Select Attachment")
//            }
//
//            if (attachmentUri.value!=null) {
//                Spacer(modifier = Modifier.height(20.dp))
//                Text(text = "Claim File:  " + attachmentUri.value)
//            }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Claim File:  ")
                    Spacer(modifier = Modifier.width(20.dp))

                    Button(onClick = { getContent.launch("*/*") }) {
                        Text(text = "Select Attachment")
                    }
                }
                if (attachmentUri.value != null) {

                    Text(text = attachmentUri.value.toString())
                }

            Spacer(modifier = Modifier.height(20.dp))
            var iQuery: String? =null

            Row(horizontalArrangement = Arrangement.End,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {

                Button(
                    onClick = {
                        var conn3: Connection? = null
                        val connsql3 = MainActivity()
                        conn3 = connsql3.connClass()

                        var username = activeUser.username;
                        val sQuery = "SELECT * FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"  + username + "'"
                        val st2= conn3!!.createStatement()
                        val rs3=st2.executeQuery(sQuery)
                        var emID: Int =0
                        while(rs3.next()){
                            emID=rs3.getString(1).toInt()
                        }


                        iQuery =
                            "INSERT INTO tblEmClaim (emID,emName,desc1,claimDate,claimType,amount,claimFile,claimStatus) values ('$emID','$username','$desc','$mDate','$claimType','$amount','${attachmentUri.value}','Pending Request'); "
                        val st = conn3.createStatement()
                        var rs= st.executeUpdate(iQuery)
                        if (rs==1) {
                            Toast.makeText(
                                this@actEmClaim,
                                "insert claim success",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0XFF0F9D58)
                    )
                ) {

                    Text(
                        text = "Submit",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

        }
    }
}

