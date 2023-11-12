package com.example.myapplication.backup

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.actHRLeave
import com.example.myapplication.activeUser
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.sql.Connection
import java.util.concurrent.Executors

class activityDetail : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = LeaveDatabase.getDatabase(applicationContext)

        val leaveDao = database.LeaveDao()
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column{
                        var recipeList = DataHolder.arrayList
                        var selectedID: Int = 0
                        var selectedName: String = ""
                        var selectedType: String = ""
                        var selectedBF: String = ""
                        var selectedExpired: String = ""
                        var selectedEntitled: String = ""
                        var selectedTaken: String = ""
                        var selectedCredited: String = ""
                        var selectedBalance: String = ""
                        var selectedReason: String = ""
                        var selectedStartDate: String = ""
                        var selectedEndDate: String = ""
                        var selectedHalfDay: String = ""
                        var selectedStatus by rememberSaveable { mutableStateOf("Pending Request") }
                        for (recipe in recipeList) {
                            selectedID=recipe.id
                            selectedName = recipe.name
                            selectedType = recipe.leaveType
                            Log.d(TAG,"selectedType=" + selectedType)
                            selectedBF = recipe.BF
                            selectedExpired = recipe.expired
                            selectedEntitled= recipe.entitled
                            selectedTaken= recipe.taken
                            selectedCredited= recipe.credited
                            selectedBalance= recipe.balance
                            selectedReason= recipe.reason
                            selectedStartDate= recipe.startDate
                            selectedEndDate= recipe.endDate
                            selectedHalfDay= recipe.halfDay
                            selectedStatus= recipe.status


                        }
                        var mutableStatus by rememberSaveable { mutableStateOf(selectedStatus) }

                        var txtID by rememberSaveable { mutableStateOf(selectedID) }
                        var txtUsername by rememberSaveable { mutableStateOf(selectedName) }
                        var txtLeaveType by rememberSaveable { mutableStateOf(selectedType) }
                        var txtBF by rememberSaveable { mutableStateOf(selectedBF) }
                        var txtExpired by rememberSaveable { mutableStateOf(selectedExpired) }
                        var txtEntitled by rememberSaveable { mutableStateOf(selectedEntitled) }
                        var txtTaken by rememberSaveable { mutableStateOf(selectedTaken) }
                        var txtCredited by rememberSaveable { mutableStateOf(selectedCredited) }
                        var txtBalance by rememberSaveable { mutableStateOf(selectedBalance) }
                        var txtReason by rememberSaveable { mutableStateOf(selectedReason) }
                        var txtStartDate by rememberSaveable { mutableStateOf(selectedStartDate) }
                        var txtEndDate by rememberSaveable { mutableStateOf(selectedEndDate) }
                        var txtHalfDay by rememberSaveable { mutableStateOf(selectedHalfDay) }
                        var txtStatus by rememberSaveable { mutableStateOf(selectedStatus) }
                        var expanded by remember {
                            mutableStateOf(false)
                        }
                        var selecteddrinks by remember {
                            mutableStateOf(selectedType)
                        }
                        var totalRecipeType = ArrayList<String>()

                        val recipeTypes = resources.getStringArray(R.array.leavestatus)
                        for (recipeType in recipeTypes) {
                            totalRecipeType.add(recipeType)
                        }
                        var txtType by rememberSaveable { mutableStateOf(selectedType) }

                        Toolbar(
                            title = "Detail Leave",
                            navigationIcon = Icons.Default.ArrowBack,
                            onNavigationIconClick = {
                                val intent =
                                    Intent(this@activityDetail, actHRLeave::class.java)
                                startActivity(intent)
                            })
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Name: ")
                            Text(txtUsername)
                        }

                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Leave Type: ")
                            Text(txtLeaveType)
                        }

                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Reason: ")
                            Text(txtReason)
                        }
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Start Date: ")
                            Text(txtStartDate)
                        }

                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("End Date: ")
                            Text(txtEndDate)
                        }
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Half Day: ")
                            Text(txtHalfDay)
                        }
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Leave Status: ")

                            Box(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }) {
                                    TextField(
                                        value = mutableStatus,
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
                                                    mutableStatus = it
                                                    expanded = false

                                                }) {
                                                Toast.makeText(this@activityDetail,"selectedStatus=" + selectedStatus, Toast.LENGTH_SHORT).show()
                                                Text(text = it)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(20.dp)
                        )
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                            var txtLeaveTaken by rememberSaveable { mutableStateOf(0) }
                            var txtLeaveType2 by rememberSaveable { mutableStateOf("") }

                            Button(
                                onClick = {
                                    var conn2: Connection? = null
                                    val connsql = MainActivity()
                                    conn2 = connsql.connClass()


                                    if (conn2 != null) {
                                        var username = activeUser.username;
                                        val sQuery =
                                            "SELECT * FROM tblLeaveType2 WHERE CONVERT(NVARCHAR(MAX), emName) =  N'" + txtUsername + "'"
                                        val st2 = conn2.createStatement()
                                        val rs = st2.executeQuery(sQuery)
                                        var emID: Int = 0

                                        while (rs.next()) {
                                            Log.d(TAG,"txtLeaveType=" + txtLeaveType)

                                            if (txtLeaveType.trim() == "AL-Annual Leave") {
                                                txtLeaveTaken = rs.getString(3).trim().toInt()
                                                txtLeaveType2="ALTaken"
                                            } else if (txtLeaveType.trim() == "C.L (Natural) - Compassions Leave") {
                                                txtLeaveTaken = rs.getString(5).trim().toInt()
                                                txtLeaveType2="CLTaken"
                                            } else if (txtLeaveType.trim() == "C.L (Grandparents) - Compassionate Leave") {
                                                txtLeaveTaken = rs.getString(5).trim().toInt()
                                                txtLeaveType2="CLTaken"
                                            }else if (txtLeaveType.trim() == "C.L (Family) - Compassionate Leave") {
                                                txtLeaveTaken = rs.getString(5).trim().toInt()
                                                txtLeaveType2="CLTaken"
                                            }else if (txtLeaveType.trim() == "Marriage Leave") {
                                                txtLeaveTaken = rs.getString(7).trim().toInt()
                                                txtLeaveType2="MarriageLeaveTaken"
                                            }else if (txtLeaveType.trim() == "MC - Medical Leave") {
                                                txtLeaveTaken = rs.getString(14).trim().toInt()
                                                txtLeaveType2="MCTaken"
                                            }else if (txtLeaveType.trim() == "ML - Maternity Leave") {
                                                txtLeaveTaken = rs.getString(9).trim().toInt()
                                                txtLeaveType2="MaternityLeaveTaken"
                                            }else if (txtLeaveType.trim() == "UL - Unpaid Leave") {
                                                txtLeaveTaken = rs.getString(11).trim().toInt()
                                                txtLeaveType2="ULTaken"
                                            }
                                        }
                                    }
                                  //mutableStatus is latest
                                    //selectedStatus is first

                                    if (selectedStatus == "Pending Request" && mutableStatus=="Approved"){
                                        Log.d(TAG,"selectedStatus == Pending Request && mutableStatus==Approved" )
                                        txtLeaveTaken=+1
                                    }else if (selectedStatus == "Approved" && mutableStatus=="Rejected"){
                                        Log.d(TAG,"selectedStatus == Approved && mutableStatus==Rejected" )

                                        txtLeaveTaken=-1
                                    }else if (selectedStatus == "Rejected" && mutableStatus=="Approved"){
                                        Log.d(TAG,"selectedStatus == Rejected && mutableStatus==Approved" )

                                        txtLeaveTaken=+1
                                    }

                                    Log.d(TAG,"txtLeaveType2=" + txtLeaveType2)
                                    Log.d(TAG,"txtLeaveTaken=" + txtLeaveTaken)

                                    val iQuery =
                                        "UPDATE tblEmLeave\n" +
                                                "SET emStatus='" + mutableStatus + "'\n" +
                                                "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "' and CONVERT(NVARCHAR(MAX), emStartDate) =  N'"+ txtStartDate + "';"
                                    val st = conn2.createStatement()
                                    var rs= st.executeUpdate(iQuery)
                                    var iQuery2:String=""

//                                    val iQuery2 =
//                                        "UPDATE tblLeaveType2\n" +
//                                                "SET '" + txtLeaveType2 + "' ='" + txtLeaveTaken + "'\n" +
//                                                "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    if ( txtLeaveType2=="ALTaken"){
                                         iQuery2 =
                                        "UPDATE tblLeaveType2\n" +
                                                "SET ALTaken ='" + txtLeaveTaken + "'\n" +
                                                "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    }else if(txtLeaveType2=="CLTaken"){
                                        iQuery2 =
                                            "UPDATE tblLeaveType2\n" +
                                                    "SET CLTaken ='" + txtLeaveTaken + "'\n" +
                                                    "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    }else if(txtLeaveType2=="MarriageLeaveTaken"){
                                        iQuery2 =
                                            "UPDATE tblLeaveType2\n" +
                                                    "SET MarriageLeaveTaken ='" + txtLeaveTaken + "'\n" +
                                                    "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    }else if(txtLeaveType2=="MCTaken"){
                                        iQuery2 =
                                            "UPDATE tblLeaveType2\n" +
                                                    "SET MCTaken ='" + txtLeaveTaken + "'\n" +
                                                    "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    }else if(txtLeaveType2=="MaternityLeaveTaken"){
                                        iQuery2 =
                                            "UPDATE tblLeaveType2\n" +
                                                    "SET MaternityLeaveTaken ='" + txtLeaveTaken + "'\n" +
                                                    "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    }else if(txtLeaveType2=="ULTaken"){
                                        iQuery2 =
                                            "UPDATE tblLeaveType2\n" +
                                                    "SET ULTaken ='" + txtLeaveTaken + "'\n" +
                                                    "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ txtUsername + "';"
                                    }
                                    var rs2= st.executeUpdate(iQuery2)
                                    if (rs2==1) {
                                        Toast.makeText(
                                            this@activityDetail,
                                            "Update leave status Successful",
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
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun Toolbar(
        title: String,
        navigationIcon: ImageVector?,
        onNavigationIconClick: () -> Unit
    ) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (navigationIcon != null) {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(imageVector = navigationIcon, contentDescription = null)
                    }
                }
            },
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}