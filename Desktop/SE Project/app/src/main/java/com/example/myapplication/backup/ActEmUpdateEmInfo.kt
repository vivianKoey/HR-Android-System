package com.example.myapplication.backup

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
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
import com.example.myapplication.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.concurrent.Executors

class actEmUpdateEmInfo : ComponentActivity() {
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


                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar3(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            },
                            "Update Employee Info"
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
                                        Intent(this@actEmUpdateEmInfo, actEmployeeDataHR::class.java)
                                    startActivity(intent)
                                }
                                if (it.id == "emLeave") {
                                    val intent =
                                        Intent(this@actEmUpdateEmInfo, actEmLeave::class.java)
                                    startActivity(intent)
                                }
                                if (it.id == "emPCB") {
                                    val intent =
                                        Intent(this@actEmUpdateEmInfo, actHRPCBCal::class.java)
                                    startActivity(intent)
                                }
                                if (it.id == "emGeneratePayslip") {
                                    val intent =
                                        Intent(this@actEmUpdateEmInfo, actGeneratePayslip::class.java)
                                    startActivity(intent)
                                }
                                println("Clicked on ${it.title}")
                            }
                        )
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
                            modifier = Modifier.padding(50.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Button(onClick = {
                                val executor = Executors.newSingleThreadExecutor()

                                executor.execute {
                                    val allRecipes = recipeDao.getAllRecipes()
                                    for (item in allRecipes) {

                                        Log.d(ContentValues.TAG,"item.id=" + item.id)
                                        txtID=item.id
                                    }


//                                var bol:Boolean= DataHolder.arrayList.add(EmployeeData(id, txtName, txtSalary, txtFA, txtOTB, txtAge, txtTR, txtEPF, txtPosition, txtManager))
//                                DataHolder.arrayList.add(EmployeeData(id,txtName, txtSalary, txtFA, txtOTB, txtAge, txtTR, txtEPF, txtPosition, txtManager))
                                    recipeDao.insertRecipe(
                                        employee(
                                            txtID+1,
                                            txtName,
                                            txtAge,
                                            txtSalary,
                                            txtFA,
                                            txtOTB,
                                            txtTR,
                                            txtEPF,
                                            txtPosition,
                                            txtPwd,
                                            txtManager,
                                            txtStatus,
                                            "true"
                                        )
                                    )
                                    Toast.makeText(applicationContext,"Insert successful", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text(text = "Submit")
                            }
                            Spacer(modifier = Modifier.width(50.dp))
                            Button(
                                onClick = {
                                    val executor = Executors.newSingleThreadExecutor()

                                    executor.execute {
                                        val allRecipes = recipeDao.getAllRecipes()
                                        for (item in allRecipes) {
                                            Log.d(ContentValues.TAG,"item=" + item.id+","+item.name+","+item.age+","+item.salary+","+item.FA+","+item.OTB+","+item.TA+","+item.EPF+","+item.position+","+item.manager)
                                        }
                                    }
                                }) {
                                Text(text = "View")
                            }
                        }
                    }
                }
            }
        }
    }
}
