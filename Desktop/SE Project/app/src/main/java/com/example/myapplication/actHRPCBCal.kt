package com.example.myapplication

//import com.example.myapplication.DB.DatabaseHelper
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import com.example.myapplication.backup.EmployeeDatabase
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.sql.Connection
import java.util.concurrent.Executors

class actHRPCBCal : ComponentActivity() {


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var arrayList5 by rememberSaveable { mutableStateOf(ArrayList<String>()) }
                var bol by rememberSaveable { mutableStateOf("false") }
                var status by rememberSaveable { mutableStateOf("") }
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
                            "PCB Calculator"
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
                                                    this@actHRPCBCal,
                                                    actEmLeave::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actHRPCBCal,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                        }
                                        if (it.id == "actEmUpdatePwd") {
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
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
                                                    this@actHRPCBCal,
                                                    "Logout Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
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
                                                    this@actHRPCBCal,
                                                    actEmployeeDataHR::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actGeneratePayslip") {
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
                                                    actGeneratePayslip::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRLeave") {
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
                                                    actHRLeave::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRPCBCal") {
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
                                                    actHRPCBCal::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actSendPayslip") {
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
                                                    actSendPayslip::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRClaim") {
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
                                                    actHRClaim::class.java
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
                                                    this@actHRPCBCal,
                                                    "Logout Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val intent =
                                                Intent(
                                                    this@actHRPCBCal,
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

                    val executor = Executors.newSingleThreadExecutor()
                    var txtID by rememberSaveable { mutableStateOf(0) }
                    var txtName by rememberSaveable { mutableStateOf("") }
                    var txtAge by rememberSaveable { mutableStateOf("") }
                    var txtSalary by rememberSaveable { mutableStateOf(0.00) }
                    var txtFA by rememberSaveable { mutableStateOf("") }
                    var txtOTB by rememberSaveable { mutableStateOf(0.00) }
                    var txtTR by rememberSaveable { mutableStateOf("") }
                    var txtEPF by rememberSaveable { mutableStateOf(0) }
                    var txtAnnualBTax by rememberSaveable { mutableStateOf(0) }
                    var txtAnnualATax by rememberSaveable { mutableStateOf(0) }
                    var txtTotalAnnualTax by rememberSaveable { mutableStateOf(0.00) }
                    var txtMonthAnnualTax by rememberSaveable { mutableStateOf(0.00) }
                    var txtNetSalary by rememberSaveable { mutableStateOf(0.00) }
                    var txtPosition by rememberSaveable { mutableStateOf("") }
                    var txtPwd by rememberSaveable { mutableStateOf("") }
                    var txtManager by rememberSaveable { mutableStateOf("") }
                    var txtStatus by rememberSaveable { mutableStateOf("") }
                    var EPFCon by rememberSaveable { mutableStateOf(0.00) }
                    var arrEmInfo by rememberSaveable { mutableStateOf(ArrayList<String>()) }
                    var selectedEmInfo by rememberSaveable { mutableStateOf("Choose Employee") }
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
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
                                                                txtName = rs.getString(2).toString()
                                                                txtSalary =
                                                                    rs.getString(3).toDouble()
                                                                txtFA = rs.getString(4)
                                                                txtOTB = rs.getString(5).toDouble()
                                                                txtAge = rs.getString(6)
                                                                txtTR = rs.getString(7)
                                                                txtEPF = rs.getString(8).toInt()
                                                                txtPwd = rs.getString(9)
                                                                txtPosition = rs.getString(10)
                                                                txtManager = rs.getString(11)
                                                                txtStatus = rs.getString(12)
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
                        val salary: Double = txtSalary
                        val socsoRate1 = 0.5
                        val socsoRate2 = socsoRate1 / 100
                        val SOCSO = salary * socsoRate2
                        var SOCSO2 = java.lang.Double.toString(SOCSO)
//                            Log.d(TAG, "socsoRate2=" + socsoRate2 )
//                            Log.d(TAG, "salary=" + salary )
//                            Log.d(TAG, "SOCSO=" + SOCSO )
//                            Log.d(TAG, "SOCSO2=" + SOCSO2 )


                        val epfRate= 11.0
                        val epfAmount:Double= epfRate /100
                        EPFCon = (salary * epfAmount)
                        Log.d(TAG, "epfRate=" + epfRate )
                        Log.d(TAG, "salary=" + salary )
                        Log.d(TAG, "epfAmount=" + epfAmount )

                        Log.d(TAG, "EPFCon=" + EPFCon )



                        val EISRate1 = 0.2
                        val EISRate2 = EISRate1 / 100
                        val EIS = salary * EISRate2
                        var EIS2 = java.lang.Double.toString(EIS)
                            Log.d(TAG, "EISRate2=" + EISRate2 )
//                            Log.d(TAG, "EIS=" + EIS )
//                            Log.d(TAG, "EIS2=" + EIS2 )

                        val TAX1 = 12
                        val ATax1 = salary * TAX1
                        val ATax2 = java.lang.Double.toString(ATax1)
                        Log.d(TAG, "salary=" + TAX1 )
                        Log.d(TAG, "TAX1=" + TAX1 )
                        Log.d(TAG, "ATax1=" + ATax1 )
                        Log.d(TAG, "ATax2=" + ATax2 )




                            val initialCost = 1.0
                            val initialNumber = 3141
                            val costIncrease = 0.15
                            if (salary>=3141) {
                            var cost = initialCost + ((salary - initialNumber) / 4) * costIncrease
                            cost = String.format("%.2f", cost).toDouble()
                            txtMonthAnnualTax = cost
                                txtTotalAnnualTax=txtMonthAnnualTax*12
                            }else{
                                txtMonthAnnualTax=0.00
                                txtTotalAnnualTax=0.00
                            }



                        txtAnnualBTax= (salary*12).toInt()
                        txtNetSalary=salary - EPFCon-SOCSO-EIS
                        txtAnnualATax=txtAnnualBTax - 13000


//                        Row {
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
                                .padding(1.dp),
                            // on below line we are specifying horizontal
                            // and vertical alignment for our column
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Status: ")

            Box( modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtStatus,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtStatus = it
                    },
                    label = { Text("txtStatus") },
                )


            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Salary: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtSalary.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtSalary = it.toDouble()
                    },
                    label = { Text("txtSalary") },
                )


            }

        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Fixed\nAllowance: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtFA,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtFA = it
                    },
                    label = { Text("txtFA") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "One Time \nBonus: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtOTB.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtOTB = it.toDouble()
                    },
                    label = { Text("txtOTB") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tax\nResident: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtTR,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtTR = it.toString()
                    },
                    label = { Text("txtTR") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Age: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtAge,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtAge = it
                    },
                    label = { Text("txtAge") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "(-) Monthly \nTax\nDeduction\n(PCB):  ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtMonthAnnualTax.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtMonthAnnualTax = it.toDouble()
                    },
                    label = { Text("txtMonthAnnualTax") },
                )


            }
        }
                            Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "EPF \nRate: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtEPF.toString() + "%",
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtEPF = it.toInt()
                    },
                    label = { Text("txtEPF") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "(-)EPF \nContribution: ")
            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = EPFCon.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        EPFCon = it.toDouble()
                    },
                    label = { Text("EPFCon") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "(-)Social \nSecurity: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = SOCSO2,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        SOCSO2 = it
                    },
                    label = { Text("SOCSO2") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "(-)Employee \nInsurance: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = EIS2,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        EIS2 = it
                    },
                    label = { Text("EIS2") },
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Annual Taxable \nIncome Before\nRelief: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtAnnualBTax.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtAnnualBTax = it.toInt()
                    },
                    label = { Text("txtAnnualTax") },
                )


            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Annual Taxable \nIncome After\nRelief: ")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtAnnualATax.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtAnnualATax = it.toInt()
                    },
                    label = { Text("txtAnnualTax") },
                )


            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Total \nAnnual \nTax:")

            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                OutlinedTextField(
                    value = txtTotalAnnualTax.toString(),
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        txtTotalAnnualTax = it.toDouble()
                    },
                    label = { Text("txtTotalAnnualTax") },
                )


            }
        }

    }
//    Column {
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "EPF \nRate: ")
//
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = txtEPF.toString() + "%",
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        txtEPF = it.toInt()
//                    },
//                    label = { Text("txtEPF") },
//                )
//
//
//            }
//        }
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "(-)EPF \nContribution: ")
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = EPFCon.toString(),
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        EPFCon = it.toDouble()
//                    },
//                    label = { Text("EPFCon") },
//                )
//
//
//            }
//        }
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "(-)Social \nSecurity: ")
//
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = SOCSO2,
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        SOCSO2 = it
//                    },
//                    label = { Text("SOCSO2") },
//                )
//
//
//            }
//        }
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(5.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "(-)Employee \nInsurance: ")
//
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = EIS2,
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        EIS2 = it
//                    },
//                    label = { Text("EIS2") },
//                )
//            }
//        }
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Annual Taxable \nIncome Before\nRelief: ")
//
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = txtAnnualBTax.toString(),
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        txtAnnualBTax = it.toInt()
//                    },
//                    label = { Text("txtAnnualTax") },
//                )
//
//
//            }
//        }
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Annual Taxable \nIncome After\nRelief: ")
//
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = txtAnnualATax.toString(),
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        txtAnnualATax = it.toInt()
//                    },
//                    label = { Text("txtAnnualTax") },
//                )
//
//
//            }
//        }
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.padding(5.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Total \nAnnual \nTax:")
//
//            Box(modifier = Modifier.width(80.dp)) {
//                OutlinedTextField(
//                    value = txtTotalAnnualTax.toString(),
//                    textStyle = TextStyle(color = Color.DarkGray),
//                    onValueChange = {
//                        txtTotalAnnualTax = it.toDouble()
//                    },
//                    label = { Text("txtTotalAnnualTax") },
//                )
//
//
//            }
//        }
//
//
//
//
//    }
//}
                        Row(modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Net\nSalary:  ")
                            Box(modifier = Modifier.width(80.dp)) {
                                OutlinedTextField(
                                    value = txtNetSalary.toString(),
                                    textStyle = TextStyle(color = Color.DarkGray),
                                    onValueChange = {
                                        txtNetSalary = it.toDouble()
                                    },
                                    label = { Text("txtNetSalary") },
                                )


                            }
                        }
                        }
                        }
                        }
                    }
                    }
                }

    @Composable
    fun pcbCal() {
//        var expanded by remember {
//            mutableStateOf(false)
//        }
//        val database = EmployeeDatabase.getDatabase(applicationContext)
//
//        val employeeDao = database.EmployeeDao()
//
//        var context: Context? = null
//
//        val executor = Executors.newSingleThreadExecutor()
//
//        executor.execute {
//            val allRecipes = employeeDao.getAllRecipes()
//            var id = 0
//            var staffName = ""
//
//            for (item in allRecipes) {
//                id = item.id
//                staffName = item.name
//                courseList.add(id.toString() + "," + staffName)
//                Log.d(TAG, "courseList1=" + id.toString() + "," + staffName)
//
//            }
//
//            Log.d(TAG, "courseList2=" + courseList)
//
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    modifier = Modifier.padding(20.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(text = "Select Employee ID= ")
//                    Box(
//                        modifier = Modifier
//                            .width(150.dp)
//                            .height(70.dp)
//                    ) {
//                        ExposedDropdownMenuBox(
//                            expanded = expanded,
//                            onExpandedChange = { expanded = !expanded }) {
//                            TextField(
//                                value = selectedItem.toString(),
//                                onValueChange = {},
//                                readOnly = true,
//                                textStyle = TextStyle.Default.copy(fontSize = 10.sp)
//                            )
//                            ExposedDropdownMenu(
//                                expanded = expanded,
//                                onDismissRequest = { expanded = false }) {
//                                courseList.forEach {
//                                    DropdownMenuItem(
//                                        onClick = {
//                                            selectedItem = it
//                                            expanded = false
//                                        }) {
//                                        Text(text = it.toString())
//                                    }
//                                }
//                            }
//
//
//                        }
//                    }
//                }
//            }
//        }
    }

//}
//        Log.i(TAG, "selectedItem=" + selectedItem)
//
//        val list = selectedItem.split(",")
//        Log.i(TAG, "list0=" + list[0])
//        lateinit var emEmployeeData: ArrayList<empl>
//        emEmployeeData = ArrayList<employeedata>()
//        var context: Context? = null
//
//        val dbHandler: DatabaseHelper = DatabaseHelper(context);
//        emEmployeeData = dbHandler.viewallPCB(list[0].toInt())!!
//
//        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(15.dp)) {
//            Column(modifier = Modifier.padding(20.dp)) {
//                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//                    Text(text = "Status: ")
//                    Text(text = emEmployeeData[0].emStatus)
//
//                }
//                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//                    Text(text = "Fixed Allowance: ")
//                    Text(text = emEmployeeData[0].emFA)
//
//                }
//                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//                    Text(text = "Tax Resident: ")
//                    Text(text = emEmployeeData[0].emTR)
//
//                }
//            }
//        }
//    }
//}
