package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
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
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.backup.EmployeeDatabase
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class actEmployeeDataHR : ComponentActivity() {
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
                            "Employee Data"
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
                                                this@actEmployeeDataHR,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
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
                                                this@actEmployeeDataHR,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
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
                                                this@actEmployeeDataHR,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
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
                                                this@actEmployeeDataHR,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmployeeDataHR,
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
                    var txtEmail by rememberSaveable { mutableStateOf("") }


                    Column {
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


                        Row {
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
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Email:")

                                Box(modifier = Modifier.width(120.dp)) {
                                    OutlinedTextField(
                                        value = txtEmail,
                                        textStyle = TextStyle(color = Color.DarkGray),
                                        onValueChange = {
                                            txtEmail = it
                                        },
                                        label = { Text("") },
                                    )
                                }
                            }



                        }

                        Row ( horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(50.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Button(onClick = {
//                                val executor = Executors.newSingleThreadExecutor()
//
//                                executor.execute {
//                                    val allRecipes = recipeDao.getAllRecipes()
//                                    Log.d(ContentValues.TAG, "allRecipes=" + allRecipes)
//
//                                    for (item in allRecipes) {
//
//                                        Log.d(TAG,"item.id=" + item.id)
//txtID=item.id
//                                    }
//
//
////                                var bol:Boolean= DataHolder.arrayList.add(EmployeeData(id, txtName, txtSalary, txtFA, txtOTB, txtAge, txtTR, txtEPF, txtPosition, txtManager))
////                                DataHolder.arrayList.add(EmployeeData(id,txtName, txtSalary, txtFA, txtOTB, txtAge, txtTR, txtEPF, txtPosition, txtManager))
//                                    recipeDao.insertRecipe(
//                                        employee(
//                                            txtID+1,
//                                            txtName,
//                                            txtAge,
//                                            txtSalary,
//                                            txtFA,
//                                            txtOTB,
//                                            txtTR,
//                                            txtEPF,
//                                            txtPosition,
//                                            txtPwd,
//                                            txtManager,
//                                            txtStatus,
//                                            "false"
//                                        )
//                                    )
//                                }
                                var conn: Connection? = null
                                val connsql = MainActivity()
                                conn = connsql.connClass()
                                Log.i(ControlsProviderService.TAG, "conn=$conn")
val bolStatus:Boolean=false
                                if (conn != null) {
                                    val sQuery = "SELECT * FROM tblEmployeeRecord"
                                    val st2= conn!!.createStatement()
                                    val rs=st2.executeQuery(sQuery)
                                    var emID: Int =0
                                    while(rs.next()){
                                      emID+=1
                                    }
                                    emID+=1
                                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                    val current = LocalDateTime.now().format(formatter)
                                    val iQuery =
                                        "INSERT INTO tblEmployeeRecord (emID, emName,emSal,emFA,emOTB,emAge,emTR,emEPF,emPwd,emPosition,emManager,emStatus,emActive,emEmail,emLeaveTaken,emDateIn) values ('"+ emID + "','" + txtName +"', '" + txtSalary + "','" + txtFA + "','" + txtOTB + "','" + txtAge + "','" + txtTR + "','" + txtEPF +"','" + txtPwd + "','" + txtPosition +"','" + txtManager + "','" + txtStatus +  "','" + bolStatus + "','" + txtEmail + "','0','" + current + "');"
                                    val st = conn!!.createStatement()
                                    st.executeUpdate(iQuery)
                                    val iQuery2 =
                                        "INSERT INTO tblLeaveType2 (emID, emName,ALTaken,ALTotal,CLTaken,CLTotal,MarriageLeaveTaken,MarriageLeaveTotal,MaternityLeaveTaken,MaternityLeaveTotal,ULTaken,ULTotal,emDateIn,MCTaken,MCTotal) values ('"+ emID + "','" + txtName +"', '0','0','0','0','0','0','0','0','0','0','" + current + "','0','0');"
                                    st.executeUpdate(iQuery2)
                                } else {
                                    Log.i(ControlsProviderService.TAG, "check connection")
                                }
                            }) {
                                Text(text = "Submit")
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                            Button(
                                onClick = {
                                    val intent =
                                        Intent(this@actEmployeeDataHR, actUpdateEmployeeDataHR::class.java)
                                    startActivity(intent)
                                }){
                                Text(text = "Modify")
                            }
                        }
                    }
                }
            }
        }
    }
}

