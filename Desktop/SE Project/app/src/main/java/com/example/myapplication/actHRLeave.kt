package com.example.myapplication

//import com.example.myapplication.DB.DatabaseHelper
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.backup.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection

class actHRLeave : ComponentActivity() {
//    var dbHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dbHelper = DatabaseHelper(this)
    //val database = LeaveDatabase.getDatabase(applicationContext)


//    var allLeave: ArrayList<leave>?=null

//    val executor: ExecutorService = Executors.newFixedThreadPool(2)

        setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var txtPosition by rememberSaveable { mutableStateOf("") }
                var txtName by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBarMainActivity2(
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
                                                    this@actHRLeave,
                                                    actEmLeave::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actHRLeave,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                        }
                                        if (it.id == "actEmUpdatePwd") {
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
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
                                                    this@actHRLeave,
                                                    "Logout Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
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
                                                    this@actHRLeave,
                                                    actEmployeeDataHR::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actGeneratePayslip") {
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
                                                    actGeneratePayslip::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRLeave") {
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
                                                    actHRLeave::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRPCBCal") {
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
                                                    actHRPCBCal::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actSendPayslip") {
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
                                                    actSendPayslip::class.java
                                                )
                                            startActivity(intent)
                                        }
                                        if (it.id == "actHRClaim") {
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
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
                                                    this@actHRLeave,
                                                    "Logout Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            val intent =
                                                Intent(
                                                    this@actHRLeave,
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
                    val items= remember{ mutableListOf<leave>()}
                    val selectedName = remember { mutableStateOf("Name") }
                    val selectedLeaveType = remember { mutableStateOf("LeaveType") }
                    val selectedBF = remember { mutableStateOf("BF") }
                    val selectedExpired = remember { mutableStateOf("Expired") }
                    val selectedEntitled = remember { mutableStateOf("Entitled") }
                    val selectedTaken = remember { mutableStateOf("Taken") }
                    val selectedCredited = remember { mutableStateOf("Credited") }
                    val selectedBalance = remember { mutableStateOf("Balance") }
                    val selectedReason = remember { mutableStateOf("Reason") }
                    val selectedStartDate = remember { mutableStateOf("StartDate") }
                    val selectedEndDate = remember { mutableStateOf("EndDate") }
                    val selectedHalfDay = remember { mutableStateOf("HalfDay") }
                    val selectedStatus = remember { mutableStateOf("Status") }
                    // Use LaunchedEffect to call the coroutine function and update the items state
                    LaunchedEffect(applicationContext) {
//                        val itemList = getItemsFromDatabase(applicationContext)
//                        items = itemList


                    }
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
                            val sQuery = "SELECT * FROM tblEmLeave"
                            val st2 = conn?.createStatement()
                            val rs = st2?.executeQuery(sQuery)
                            var emID: Int = 0
                            while (rs?.next() == true) {
//                                Toast.makeText(
//                                    this@actHRLeave,
//                                    rs.getString(2).toString(),
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                Log.d(TAG,"rs info=" + rs.getString(1).toInt()  +"," + rs.getString(2).toString()+"," + rs.getString(3).toString()+"," + rs.getString(4).toString()+"," + rs.getString(5).toString()+"," + rs.getString(6).toString()+"," + rs.getString(7).toString()+"," + rs.getString(8).toString()+"," + rs.getString(9).toString()+"," + rs.getString(10).toString()+"," + rs.getString(11).toString()+"," + rs.getString(12).toString()+"," + rs.getString(13).toString()+"," + rs.getString(14).toString())
                                items.add(leave(rs.getString(1).toInt(), rs.getString(2).toString(), rs.getString(3).toString(), "-", "-","-","-","-","-",rs.getString(10).toString(),rs.getString(11).toString(),rs.getString(12).toString(),rs.getString(13).toString(),rs.getString(14).toString()))
                                //items.add(leave(2, "Brian", "Annual Leave", "-", "-","-","-","-","-","Go back hometown",rs.getString(11).toString(),"10/11/2023","11/11/2023","Pending Request"))

                            }
                        }
                        // on below line we are setting data for each item of our listview.
                        itemsIndexed(items) { index, item ->
                            Log.i(TAG,"items2=" + items[index].name)
                            // on below line we are creating a card for our list view item.
                            var emName = items[index].name
                            var emLeaveType = items[index].leaveType
                            var emBF = items[index].BF
                            var emExpired = items[index].expired
                            var emEntitled = items[index].entitled
                            var emTaken = items[index].taken
                            var emCredited = items[index].credited
                            var emBalance = items[index].balance
                            var emReason = items[index].reason
                            var emStartDate = items[index].startDate
                            var emEndDate = items[index].endDate
                            var emHalfDay = items[index].halfDay
                            var emStatus = items[index].status


                            Card(
                                // on below line we are adding padding from our all sides.
                                modifier = Modifier.padding(8.dp)
                                    .clickable {
                                        selectedName.value=emName
                                        selectedLeaveType.value=emLeaveType
                                        selectedBF.value=emBF
                                        selectedExpired.value=emExpired
                                        selectedEntitled.value=emEntitled
                                        selectedTaken.value=emTaken
                                        selectedCredited.value=emCredited
                                        selectedBalance.value=emBalance
                                        selectedReason.value=emReason
                                        selectedStartDate.value=emStartDate
                                        selectedEndDate.value=emEndDate
                                        selectedHalfDay.value=emHalfDay
                                        selectedStatus.value=emStatus
                                        val intent = Intent(
                                            this@actHRLeave,
                                            activityDetail::class.java
                                        )

                                        DataHolder.arrayList.add(leave(1,selectedName.value,selectedLeaveType.value, selectedBF.value, selectedExpired.value,selectedEntitled.value,selectedTaken.value,selectedCredited.value,selectedBalance.value,selectedReason.value,selectedStartDate.value,selectedEndDate.value,selectedHalfDay.value,selectedStatus.value))
                                        startActivity(intent)
                                    },
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
                                    Log.i(TAG,"items3=" + items[index].name)
                                    // on the below line we are creating a text.
                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text = items[index].name,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Center
                                    )
                                    // on below line inside row we are adding spacer
                                    Spacer(modifier = Modifier.width(5.dp))

                                    // on the below line we are creating a text.
                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text = "Leave Type : " + items[index].leaveType,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Center
                                    )
                                    // on below line inside row we are adding spacer
                                    Spacer(modifier = Modifier.width(5.dp))

                                    // on the below line we are creating a text.
                                    Text(
                                        // inside the text on below line we are
                                        // setting text as the language name
                                        // from our model class.
                                        text = "Leave Status : " + items[index].status,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
// Function to get items from the database using coroutines
suspend fun getItemsFromDatabase(context: Context): List<leave> = withContext(Dispatchers.IO) {
    val db = LeaveDatabase.getDatabase(context)
    db.LeaveDao().getAllEmLeave()
}

@Composable
fun ListItem(name : String){

    val expanded = remember { mutableStateOf(false)}
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)){

        Column(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()) {

            Row{

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "Course")
                    Text(text = name, style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    ))
                }

                OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                    Text(if (expanded.value) "Show less" else "Show more")
                }
            }

            if (expanded.value){

                Column(modifier = Modifier.padding(
                    bottom = extraPadding.coerceAtLeast(0.dp)
                )) {
                    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
                }

            }
        }

    }
}
@Composable
fun DisplayItemsFromDatabase(context: Context) {
    // Use remember to store the list of items
    var items by remember { mutableStateOf(emptyList<leave>()) }

    // Use LaunchedEffect to call the coroutine function and update the items state
    LaunchedEffect(context) {
        val itemList = getItemsFromDatabase(context)
        items = itemList
    }

    LazyColumn {
        items(items) { item ->
            // Display the item in a Composable Row or Card
            // For example:
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                elevation = 4.dp
            ) {
                Text(text = item.name)
            }
        }
    }
}



@Composable
fun readDataFromDatabase(context: Context) {


}
