package com.example.myapplication.backup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.*
//import com.example.myapplication.DB.DatabaseHelper
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.Statement

class actEmData : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var expanded by remember { mutableStateOf(false) }

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
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "emData",
                                    title = "Employee Data",
                                    contentDescription = "Go to employee data",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "emLeave",
                                    title = "Leave Data",
                                    contentDescription = "Go to leave data",
                                    icon = Icons.Default.Info
                                ),
                                MenuItem(
                                    id = "emPCB",
                                    title = "PCB Calculator",
                                    contentDescription = "Go to PCB Calculator",
                                    icon = Icons.Default.AccountCircle
                                ),
                                MenuItem(
                                    id = "emGeneratePayslip",
                                    title = "Generate Payslip",
                                    contentDescription = "Go to Generate Payslip",
                                    icon = Icons.Default.Edit
                                ),
                                MenuItem(
                                    id = "emSendPayslip",
                                    title = "Send Payslip",
                                    contentDescription = "Go to Send Payslip",
                                    icon = Icons.Default.Email
                                ),
                            ),
                            onItemClick = {
                                if (it.id == "emData") {
                                    val intent =
                                        Intent(this@actEmData, actEmployeeDataHR::class.java)
                                    startActivity(intent)
                                }
                                if (it.id == "emLeave") {
                                    val intent =
                                        Intent(this@actEmData, actEmLeave::class.java)
                                    startActivity(intent)
                                }
                                if (it.id == "emPCB") {
                                    val intent =
                                        Intent(this@actEmData, actHRPCBCal::class.java)
                                    startActivity(intent)
                                }
                                if (it.id == "emGeneratePayslip") {
                                    val intent =
                                        Intent(this@actEmData, actGeneratePayslip::class.java)
                                    startActivity(intent)
                                }
                                println("Clicked on ${it.title}")
                            }
                        )
                    }
                ) {
                    insertToDB(LocalContext.current)
                    }
                }
            }
            }
        }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dropDownMenu2(){
    var expanded by remember {
        mutableStateOf(false)
    }
    var drinks= arrayOf("AL-Annual Leave", "C.L (Natural) - Compassions Leave", "C.L (Grandparents) - Compassionate Leave", "C.L (Family) - Compassionate Leave", "Carry Forward 2022", "Floating Holiday", "Marriage Leave", "MC - Medical Leave", "ML - Maternity Leave", "UL - Unpaid Leave")
    var selecteddrinks by remember{
        mutableStateOf(drinks[0])
    }
    Box(modifier = Modifier
        .width(150.dp)
        .height(70.dp)){
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(value = selecteddrinks, onValueChange ={}, readOnly = true, trailingIcon ={ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = expanded)}, textStyle = TextStyle.Default.copy(fontSize =10.sp))
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded=false }) {
                drinks.forEach {
                    DropdownMenuItem(
                        onClick = {
                            selecteddrinks = it
                            expanded = false
                        }) {
                        Text(text = it)
                    }
                }
            }

        }
    }
}

@Composable
fun insertToDB(context: Context){
    var emID by rememberSaveable { mutableStateOf("Text") }
    var emName by rememberSaveable { mutableStateOf("Text") }
    var emSal by rememberSaveable { mutableStateOf("Text") }
    var emFA by rememberSaveable { mutableStateOf("Text") }
    var emOTB by rememberSaveable { mutableStateOf("Text") }
    var emAge by rememberSaveable { mutableStateOf("Text") }
    var emTR by rememberSaveable { mutableStateOf("Text") }
    var emEPF by rememberSaveable { mutableStateOf("Text") }
    var emPwd by rememberSaveable { mutableStateOf("Text") }
    var emPosition by rememberSaveable { mutableStateOf("Text") }
    var emManager by rememberSaveable { mutableStateOf("Text") }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp)
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
    ) {

        //var dbHelper: DatabaseHelper = DatabaseHelper(context)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Select Employee ID= ")
            dropDownMenu2()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ID= ")

            TextField(
                value = emID,
                onValueChange = {
                    emID = it
                },
                label = { Text("ID") }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name= ")

            TextField(
                value = emName,
                onValueChange = {
                    emName = it
                },
                label = { Text("Name") }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Salary= ")

            TextField(
                value = emSal,
                onValueChange = {
                    emSal = it
                },
                label = { Text("Salary") }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Fixed Allowance= ")

            TextField(
                value = emFA,
                onValueChange = {
                    emFA = it
                },
                label = { Text("Fixed Allowance") }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "One Time Bonus= ")

            TextField(
                value = emOTB,
                onValueChange = {
                    emOTB = it
                },
                label = { Text("One Time Bonus") }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Age= ")

            TextField(
                value = emAge,
                onValueChange = {
                    emAge = it
                },
                label = { Text("Age") }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tax Residence= ")

            TextField(
                value = emTR,
                onValueChange = {
                    emTR = it
                },
                label = { Text("Tax Residence") }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "EPF Rate= ")

            TextField(
                value = emEPF,
                onValueChange = {
                    emEPF = it
                },
                label = { Text("EPF Rate") }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Password= ")

            TextField(
                value = emPwd,
                onValueChange = {
                    emPwd = it
                },
                label = { Text("Password") }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Position= ")

            TextField(
                value = emPosition,
                onValueChange = {
                    emPosition = it
                },
                label = { Text("Position") }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Manager= ")

            TextField(
                value = emManager,
                onValueChange = {
                    emManager = it
                },
                label = { Text("Manager") }
            )
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
//                    dbHelper.addRecord(emID,emName,emSal,emFA,emOTB,emAge,emTR,emEPF,emPwd,emPosition,emManager)
//                    Toast.makeText(context, "Course Added to Database", Toast.LENGTH_SHORT).show()

//                String emName="Select * from tblEmployeeRecord";
//                Statement st=conn.createStatement();
//                ResultSet rs=st.executeQuery(emName);
//
//                while(rs.next()){
//                    Log.i(TAG,"rs result="+ rs.getString(2));
//                }
//                    var conn: Connection? = null
//                    val connsql = MainActivity()
//                    conn = connsql.connClass()
//                    if (conn != null) {
//                        val iQuery =
//                            "INSERT INTO tblEmployeeRecord (emID,emName,emSal,emFA,emOTB,emAge,emTR,emEPF,emPwd,emPosition,emManager) values ('$emID','$emName','$emSal','$emFA','$emOTB','$emAge','$emTR','$emEPF','$emPwd','$emPosition','$emManager'); "
//                        val st: Statement = conn.createStatement()
//                        st.executeUpdate(iQuery)
//                    }


                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0XFF0F9D58)
                )
            ) {
                Text(
                    text = "Add",
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    var conn: Connection? = null
                    val connsql = MainActivity()
                    conn = connsql.connClass()
                    if (conn != null) {
                        val emName = "Select * from tblEmployeeRecord"
                        val st: Statement = conn!!.createStatement()
                        val rs = st.executeQuery(emName)
                        val sb = StringBuffer()
                        while (rs.next()) {
//                            Log.i(ControlsProviderService.TAG, "rs result=" + rs.getString(2))
                            sb.append(rs.getString(1) + ", " + rs.getString(2) + "\n")
                        }
                        Log.i("Details", sb.toString())
                    } else {
                        Log.i(ControlsProviderService.TAG, "check connection")
                    }


//                    val cur = dbHelper.viewall2()
//                    if (cur.count == 0) {
//                        Toast.makeText(context,"Error, No Data",Toast.LENGTH_SHORT).show()
//                    }
//                    val sb = StringBuffer()
//                    while (cur.moveToNext()) {
//                        sb.append(
//                            """
//            employeeNo=${cur.getString(0)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            employeeName=${cur.getString(1)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            Salary=${cur.getString(2)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            FixedAllowance=${cur.getString(3)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            OneTimeBonus=${cur.getString(4)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            Age=${cur.getString(5)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            TaxResident=${cur.getString(6)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            EPFRate=${cur.getString(7)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            Password=${cur.getString(8)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            Position=${cur.getString(9)}
//
//            """.trimIndent()
//                        )
//                        sb.append(
//                            """
//            Position=${cur.getString(10)}
//
//            """.trimIndent()
//                        )
//                        sb.append("\n")
//                    }
//                    Log.i("Details", sb.toString())
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0XFF0F9D58)
                )
            ) {
                Text(
                    text = "View",
                    color = Color.White
                )
            }

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0XFF0F9D58)
                )
            ) {
                Text(
                    text = "Delete",
                    color = Color.White
                )
            }
        }

    }
}


