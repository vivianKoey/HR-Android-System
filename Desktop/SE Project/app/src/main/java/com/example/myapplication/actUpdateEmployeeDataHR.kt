package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.text.TextUtils.split
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.sp
import com.example.myapplication.backup.EmployeeDatabase
import com.example.myapplication.backup.employeeRecord
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.sql.Connection

class actUpdateEmployeeDataHR : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = EmployeeDatabase.getDatabase(applicationContext)

        val recipeDao = database.EmployeeDao()
        var selectedInfo:String=""

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
                            "Update Employee Data"
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
                                                this@actUpdateEmployeeDataHR,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
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
                                                this@actUpdateEmployeeDataHR,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
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
                                                this@actUpdateEmployeeDataHR,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
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
                                                this@actUpdateEmployeeDataHR,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actUpdateEmployeeDataHR,
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
                    var txtID by rememberSaveable { mutableStateOf(0) }
                    var txtName by rememberSaveable { mutableStateOf("") }
                    var txtAge by rememberSaveable { mutableStateOf("") }
                    var txtSalary by rememberSaveable { mutableStateOf(0.00) }
                    var txtFA by rememberSaveable { mutableStateOf("") }
                    var txtOTB by rememberSaveable { mutableStateOf(0.00) }
                    var txtTR by rememberSaveable { mutableStateOf("") }
                    var txtEPF by rememberSaveable { mutableStateOf(0) }
                    var txtPosition by rememberSaveable { mutableStateOf("") }
                    var txtPwd by rememberSaveable { mutableStateOf("") }
                    var txtManager by rememberSaveable { mutableStateOf("") }
                    var txtStatus by rememberSaveable { mutableStateOf("") }
                    var arrEmInfo1 by rememberSaveable { mutableStateOf(ArrayList<String>()) }
                    var arrEmInfo by rememberSaveable { mutableStateOf(ArrayList<String>()) }
                    var selectedEmInfo by rememberSaveable { mutableStateOf("Choose Employee") }

                    Column {
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
                                                            selectedInfo=selectedEmInfo
                                                            expanded = false

                                                            var emInfo=split(selectedEmInfo,",")
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
                        Row {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(text = "Name:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtName,
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtName = it
                                        },
                                        label = { Text("Name") },
                                    )


                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Age:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtAge.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtAge = it
                                        },
                                        label = { Text("Age") },
                                    )
                                }
                            }
                        }
                        Row {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Salary:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtSalary.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtSalary = it.toDouble()
                                        },
                                        label = { Text("Salary") },
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Fixed\nAllowance:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtFA.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtFA = it
                                        },
                                        label = { Text("FA") },
                                    )
                                }
                            }
                        }
                        Row {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "One Time\nBonus:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtOTB.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtOTB = it.toDouble()
                                        },
                                        label = { Text("OTB") },
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Tax\nResident:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtTR.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtTR = it
                                        },
                                        label = { Text("TR") },
                                    )
                                }
                            }
                        }

                        Row {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "EPF\nRate:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtEPF.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtEPF = it.toInt()
                                        },
                                        label = { Text("EPF") },
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Position:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtPosition,
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtPosition = it
                                        },
                                        label = { Text("Position") },
                                    )
                                }
                            }
                        }

                        Row {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Password:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtPwd.toString(),
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtPwd = it
                                        },
                                        label = { Text("Password") },
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Manager:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtManager,
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtManager = it
                                        },
                                        label = { Text("") },
                                    )
                                }
                            }



                            }
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Status:")

                            Box(modifier = Modifier.width(120.dp)) {
                                OutlinedTextField(
                                    value = txtStatus,
                                    textStyle = TextStyle(color = Color.DarkGray),
                                    onValueChange = {
                                        txtStatus = it
                                    },
                                    label = { Text("") },
                                )
                            }
                        }


                        Row ( horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically) {
//                            Button(onClick = {
////                                val executor = Executors.newSingleThreadExecutor()
////
////                                executor.execute {
////                                    val allRecipes = recipeDao.getAllRecipes()
////                                    Log.d(ContentValues.TAG, "allRecipes=" + allRecipes)
////
////                                    for (item in allRecipes) {
////
////                                        Log.d(TAG,"item.id=" + item.id)
////txtID=item.id
////                                    }
////
////
//////                                var bol:Boolean= DataHolder.arrayList.add(EmployeeData(id, txtName, txtSalary, txtFA, txtOTB, txtAge, txtTR, txtEPF, txtPosition, txtManager))
//////                                DataHolder.arrayList.add(EmployeeData(id,txtName, txtSalary, txtFA, txtOTB, txtAge, txtTR, txtEPF, txtPosition, txtManager))
////                                    recipeDao.insertRecipe(
////                                        employee(
////                                            txtID+1,
////                                            txtName,
////                                            txtAge,
////                                            txtSalary,
////                                            txtFA,
////                                            txtOTB,
////                                            txtTR,
////                                            txtEPF,
////                                            txtPosition,
////                                            txtPwd,
////                                            txtManager,
////                                            txtStatus,
////                                            "false"
////                                        )
////                                    )
////                                }
//                                var conn: Connection? = null
//                                val connsql = MainActivity()
//                                conn = connsql.connClass()
//                                Log.i(ControlsProviderService.TAG, "conn=$conn")
//val bolStatus:Boolean=false
//                                if (conn != null) {
//                                    val sQuery = "SELECT * FROM tblEmployeeRecord"
//                                    val st2=conn.createStatement()
//                                    val rs=st2.executeQuery(sQuery)
//                                    var emID: Int =0
//                                    while(rs.next()){
//                                      emID+=1
//                                    }
//                                    emID+=1
//                                    val iQuery =
//                                        "INSERT INTO tblEmployeeRecord (emID, emName,emSal,emFA,emOTB,emAge,emTR,emEPF,emPwd,emPosition,emManager,emStatus,emActive) values ('"+ emID + "','" + txtName +"', '" + txtSalary + "','" + txtFA + "','" + txtOTB + "','" + txtAge + "','" + txtTR + "','" + txtEPF +"','" + txtPwd + "','" + txtPosition +"','" + txtManager + "','" + txtStatus +  "','" + bolStatus + "');"
//                                    val st = conn.createStatement()
//                                    st.executeUpdate(iQuery)
//                                } else {
//                                    Log.i(ControlsProviderService.TAG, "check connection")
//                                }
//                            }) {
//                                Text(text = "Submit")
//                            }
//                            Spacer(modifier = Modifier.width(50.dp))
//                            Button(
//                                    onClick = {
////                                val executor = Executors.newSingleThreadExecutor()
////
////                                executor.execute {
////                                    val allRecipes = recipeDao.getAllRecipes()
////                                    for (item in allRecipes) {
////                                       Log.d(TAG,"item=" + item.id+","+item.name+","+item.age+","+item.salary+","+item.FA+","+item.OTB+","+item.TA+","+item.EPF+","+item.position+","+item.manager)
////                                    }
////                                }
//                                        var conn: Connection? = null
//                                        val connsql = MainActivity()
//                                        conn = connsql.connClass()
//                                        Log.i(ControlsProviderService.TAG, "conn=$conn")
//                                        val bolStatus:Boolean=false
//                                        var strEmRecord:String=""
//                                        val builder = StringBuilder()
//                                        if (conn != null) {
//                                            val sQuery = "SELECT * FROM tblEmployeeRecord"
//                                            val st2 = conn!!.createStatement()
//                                            val rs = st2.executeQuery(sQuery)
//                                            var emID: Int = 0
//                                            while (rs.next()) {
//                                                builder.append("ID: ")
//                                                    .append(rs.getString(1))
//                                                    .append(", Name: ")
//                                                    .append(rs.getString(2))
//                                                    .append(", Salary: ")
//                                                    .append(rs.getString(3))
//                                                    .append(", Fixed Allowance: ")
//                                                    .append(rs.getString(4))
//                                                    .append(", One Time Bonus: ")
//                                                    .append(rs.getString(5))
//                                                    .append(", Age: ")
//                                                    .append(rs.getString(6))
//                                                    .append(", Tax Residents: ")
//                                                    .append(rs.getString(7))
//                                                    .append(", EPF: ")
//                                                    .append(rs.getString(8))
//                                                    .append(", Password: ")
//                                                    .append(rs.getString(9))
//                                                    .append(", Position: ")
//                                                    .append(rs.getString(10))
//                                                    .append(", Manager: ")
//                                                    .append(rs.getString(11))
//                                                    .append(", Status: ")
//                                                    .append(rs.getString(12))
//                                                    .append("\n")
//                                                //strEmRecord+=rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3)+ "," + rs.getString(4)+ "," + rs.getString(5)+ "," + rs.getString(6)+ "," + rs.getString(7)+ "," + rs.getString(8)+ "," + rs.getString(9)+ "," + rs.getString(10)+ "," + rs.getString(11)+ "," + rs.getString(12) + "\n"
//                                            }
//                                        }
//                                        Toast.makeText(this@actUpdateEmployeeDataHR,builder.toString(), Toast.LENGTH_SHORT).show()
//
//                                    }) {
//                                Text(text = "View")
//                            }
                            Spacer(modifier = Modifier.width(50.dp))
                            Button(
                                onClick = {
                                    var conn2: Connection? = null
                                    val connsql = MainActivity()
                                    conn2 = connsql.connClass()
                                    if (conn2 != null) {
                                        var emInfo=split(selectedInfo,",")
                                        var emID=emInfo[0]
                                        val iQuery =
                                            "UPDATE tblEmployeeRecord\n" +
                                                    "SET emName = '" + txtName + "', emSal= '" + txtSalary + "', emFA= '" + txtFA + "', emOTB= '" + txtOTB + "', emAge= '" + txtAge + "', emTR= '" + txtTR + "', emEPF= '" + txtEPF + "', emPwd= '" + txtPwd + "', emPosition= '" + txtPosition + "', emManager= '" + txtManager + "', emStatus= '" + txtStatus + "'\n" +
                                                    "WHERE CONVERT(NVARCHAR(MAX), emID) =  N'"+ emID + "';"
                                        val st = conn2!!.createStatement()
                                       var rs= st.executeUpdate(iQuery)
                                        if (rs==1){
                                            Toast.makeText(this@actUpdateEmployeeDataHR,"Update Successful",Toast.LENGTH_SHORT).show()

                                            val sQuery = "SELECT * FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emID) =  N'"+ emID + "'"
                                            val st2 = conn2!!.createStatement()
                                            val rs = st2.executeQuery(sQuery)
                                            arrEmInfo.clear()
                                            while (rs.next()) {
                                                arrEmInfo.add(rs.getString(1).toString()+"," + rs.getString(2).toString())
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
                                            selectedEmInfo=arrEmInfo[0]
                                        Toast.makeText(this@actUpdateEmployeeDataHR,"selectedEmInfo3(2)=" + selectedEmInfo, Toast.LENGTH_SHORT).show()

                                        }
                                    }

                                }){
                                Text(text = "Update")
                            }

                            Spacer(modifier = Modifier.width(50.dp))
                            Button(
                                onClick = {
                                    var conn3: Connection? = null
                                    val connsql = MainActivity()
                                    conn3 = connsql.connClass()
                                    if (conn3 != null) {
                                        var emInfo=split(selectedInfo,",")
                                        var emID=emInfo[0]
                                        val iQuery =
                                            "DELETE FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emID) =  N'"+ emID + "'"
                                        val st = conn3.createStatement()
                                        var rs= st.executeUpdate(iQuery)
                                        if (rs==1){
                                            Toast.makeText(this@actUpdateEmployeeDataHR,"Delete Successful",Toast.LENGTH_SHORT).show()


                                            var conn4: Connection? = null
                                            val connsql4 = MainActivity()
                                            conn4 = connsql4.connClass()
                                            if (conn4 != null) {
                                                val sQuery =
                                                    "SELECT * FROM tblEmployeeRecord"
                                                val st4 = conn4.createStatement()
                                                val rs = st4.executeQuery(sQuery)
                                                arrEmInfo.clear()
                                                while (rs.next()) {
                                                    arrEmInfo.add(
                                                        rs.getString(1)
                                                            .toString() + "," + rs.getString(2)
                                                            .toString()
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
                                                selectedEmInfo = arrEmInfo[0]
                                                Toast.makeText(
                                                    this@actUpdateEmployeeDataHR,
                                                    "selectedEmInfo3(2)=" + selectedEmInfo,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }

                                }){
                                Text(text = "Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dropDownList() {
    var conn: Connection? = null
    val connsql = MainActivity()
    conn = connsql.connClass()
    Log.i(ControlsProviderService.TAG, "conn=$conn")
    val bolStatus: Boolean = false
    var arrEmInfo = remember { mutableStateListOf<String>() }
    if (conn != null) {
        val sQuery = "SELECT * FROM tblEmployeeRecord"
        val st2 = conn.createStatement()
        val rs = st2.executeQuery(sQuery)
        var emID: Int = 0
        while (rs.next()) {
            arrEmInfo.add(employeeRecord(rs.getString(1).toInt(), rs.getString(2)).toString())
        }
        var expanded by remember {
            mutableStateOf(false)
        }
        var selectedEmInfo by remember {
            mutableStateOf(arrEmInfo[0])
        }
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
                                selectedEmInfo = it.toString()
                                expanded = false
                            }) {
                            Text(text = it)
                        }
                    }
                }

            }
        }
    }
}

