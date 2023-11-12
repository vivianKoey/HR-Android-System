package com.example.myapplication

//import com.example.myapplication.DB.DatabaseHelper
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.backup.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.sql.Connection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.Executors


class actEmLeave : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = LeaveDatabase.getDatabase(applicationContext)

        val leaveDao = database.LeaveDao()
        val employeeDatabase = EmployeeDatabase.getDatabase(applicationContext)

        val employeeDao = employeeDatabase.EmployeeDao()
        var halfDay=""
        setContent {
            MyApplicationTheme {
                var bolClicked:Boolean=false
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var expanded by remember { mutableStateOf(false) }
                var selecteddrinks by remember{
                    mutableStateOf("Text")
                }
                var reason1 by rememberSaveable { mutableStateOf("Text") }
                var txtStartDate by rememberSaveable { mutableStateOf("Start Date") }
                var txtEndDate by rememberSaveable { mutableStateOf("End Date") }

                var checked by remember {
                    mutableStateOf(false)
                }

                var checked2 by remember {
                    mutableStateOf(false)
                }

                var checked3 by remember {
                    mutableStateOf(false)
                }

                var iQuery: String? =null
                var listItems:ArrayList<String>

                var selectedItem by rememberSaveable { mutableStateOf("annual type") }
                var mutableItem by rememberSaveable { mutableStateOf(selectedItem) }

                var txtID by rememberSaveable { mutableStateOf(0) }
                var txtName by rememberSaveable { mutableStateOf("") }
                txtName = activeUser.username;
                var txtPosition by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBarEmLeave(
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
                                                this@actEmLeave,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
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
                                                this@actEmLeave,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
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
                                                this@actEmLeave,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
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
                                                this@actEmLeave,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actEmLeave,
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
                    var activeUser: List<employee>
                    var emName:String=""
                    executor.execute {
                        var status="true"
                         activeUser=employeeDao.getActiveUser(status)
                        Log.d(TAG, "activeUser=" + activeUser)

                        for (userInfo in activeUser){
                            emName=userInfo.name
                            Log.d(TAG, "userInfo=" + userInfo.name)
                        }
                    }
                        Column(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                        ) {
                            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(20.dp),verticalAlignment = Alignment.CenterVertically) {
                                Text("Leave Type: ")
//                                dropDownMenu()
                                 listItems = arrayListOf("AL-Annual Leave", "C.L (Natural) - Compassions Leave", "C.L (Grandparents) - Compassionate Leave", "C.L (Family) - Compassionate Leave", "Carry Forward 2022", "Floating Holiday", "Marriage Leave", "MC - Medical Leave", "ML - Maternity Leave", "UL - Unpaid Leave")
                                selectedItem=listItems[0]
                                // state of the menu
                                var expanded by remember {
                                    mutableStateOf(false)
                                }


                                Box(modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp)){
                                    // box
                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = {
                                            expanded = !expanded
                                        }
                                    ) {
                                        TextField(
                                            value = mutableItem,
                                            onValueChange = { mutableItem = it },
                                            label = { Text(text = "Label") },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = expanded
                                                )
                                            },
                                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                                        )

                                        // filter options based on text field value
                                        val filteringOptions =
                                            listItems.filter { it.contains(mutableItem, ignoreCase = true) }

                                        if (listItems.isNotEmpty()) {
                                            // menu
                                            ExposedDropdownMenu(
                                                expanded = expanded,
                                                onDismissRequest = { expanded = false }
                                            ) {
                                                // this is a column scope
                                                // all the items are added vertically
                                                listItems.forEach { selectionOption ->
                                                    // menu item
                                                    DropdownMenuItem(
                                                        onClick = {
                                                            mutableItem = selectionOption
                                                            //Toast.makeText(contextForToast, mutableItem, Toast.LENGTH_SHORT).show()
                                                            Log.i(TAG,"mutableItem="+mutableItem)
                                                            expanded = false
                                                        }
                                                    ) {
                                                        Text(text = selectionOption)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
//                                MyUI()


                                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = checked,
                                            onCheckedChange = { checked_ ->
                                                checked = checked_
                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color.Green
                                            )
                                        )

                                        Text(
                                            modifier = Modifier.padding(start = 2.dp),
                                            text = "Half Day"
                                        )
                                    }
                                    if (checked == true) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = checked2,
                                                onCheckedChange = { checked_ ->
                                                    if (checked3 == true) {
                                                        checked2 = false
                                                    } else {
                                                        checked2 = checked_
                                                    }
                                                },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = Color.Green
                                                )
                                            )

                                            Text(
                                                modifier = Modifier.padding(start = 2.dp),
                                                text = "First Half"
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Checkbox(
                                                checked = checked3,
                                                onCheckedChange = { checked_ ->
                                                    if (checked2 == true) {
                                                        checked3 = false
                                                    } else {
                                                        checked3 = checked_
                                                    }

                                                },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = Color.Green
                                                )
                                            )

                                            Text(
                                                modifier = Modifier.padding(start = 2.dp),
                                                text = "Second Half"
                                            )
                                        }
                                    }
                                }

                            }
                            Row(horizontalArrangement = Arrangement.Start) {
                                LeaveBal1()
//                                LeaveBal12()

                            }

                            Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically){
                                Text(text="Reason: ")
//                                reason()

                                TextField(
                                    value = reason1,
                                    onValueChange = {
                                        reason1 = it
                                    },
                                    label = { Text("Label") }
                                )
                            }

                           // Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically){
//                                SelectDate()

                            // Fetching the Local Context
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
                            val mDate = remember { mutableStateOf("") }
                            val mDate2 = remember { mutableStateOf("") }

                            // Declaring DatePickerDialog and setting
                            // initial values as current values (present year, month and day)
                            val mDatePickerDialog = DatePickerDialog(
                                mContext,
                                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                    mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                                }, mYear, mMonth, mDay
                            )

                            val mDatePickerDialog2 = DatePickerDialog(
                                mContext,
                                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                    mDate2.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                                }, mYear, mMonth, mDay
                            )


                            Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically){
                                Text(text="Select Date:  ")

                                txtStartDate="Select Date"
                                Button(onClick = {
                                    mDatePickerDialog.show()
                                    txtStartDate="Start Date"
                                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
                                    if (mDate.value!=""){
                                        txtStartDate=mDate.value
                                    }else{
                                        txtStartDate="Start Date"
                                    }

                                    Text(text = txtStartDate,
                                        color = Color.White)
                                }

                                Button(onClick = {
                                    mDatePickerDialog2.show()
                                    txtEndDate="End Date"
                                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
                                    if (mDate2.value!=""){
                                        txtEndDate=mDate2.value
                                    }else{
                                        txtEndDate="End Date"
                                    }

                                    Text(text = txtEndDate,
                                        color = Color.White)
                                }
                            }
                           // }

                           Column(modifier=Modifier.fillMaxSize(),
                           verticalArrangement =Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally){
                               Button(onClick = {
                                   var conn: Connection? = null
                                   val connsql = MainActivity()
                                   conn = connsql.connClass()
                                   if (conn != null) {
                                       if (checked==true) {
                                           if (checked2==true) {
                                               iQuery =
                                                   "INSERT INTO tblEmLeave (emID,emName,emAnnualType,emReason,emStartDate,emEndDate,emHalfDay,emStatus) values ('1','$txtName','$mutableItem','$reason1','$txtStartDate','$txtEndDate','First Half','Pending Request'); "
                                           }else if (checked3==true){
                                               iQuery =
                                                   "INSERT INTO tblEmLeave (emID,emName,emAnnualType,emReason,emStartDate,emEndDate,emHalfDay,emStatus) values ('1','$txtName','$mutableItem','$reason1','$txtStartDate','$txtEndDate','Second Half','Pending Request'); "
                                           }
                                       }else{
                                           iQuery =
                                               "INSERT INTO tblEmLeave (emID,emName,emAnnualType,emReason,emStartDate,emEndDate,emHalfDay,emStatus) values ('1','$txtName','$mutableItem','$reason1','$txtStartDate','$txtEndDate','-','Pending Request'); "
                                       }
                                       val st= conn!!.createStatement()
                                       st.executeUpdate(iQuery)
                                        bolClicked=true
                                   }

                                   if (checked2==true){
                                       halfDay="firstHalf"
                                   }
                                   if (checked3==true){
                                       halfDay="secondHalf"
                                   }

                                   val executor = Executors.newSingleThreadExecutor()

                                   executor.execute {
//                                       val allRecipes = leaveDao.getAllEmLeave()
//                                       for (item in allRecipes) {
//
//                                           Log.d(TAG, "item.id=" + item.id)
//                                           txtID = item.id
//                                       }
                                       Log.d(TAG,"emName=" + emName )

                                       leaveDao.insertLeave(leave(txtID + 1,emName,selectedItem,"","","","","","",reason1,txtStartDate,txtEndDate,halfDay,"Pending Request"))
                                   var allLeave=leaveDao.getAllEmLeave()
                                       for (leaveItem in allLeave){
                                           Log.d(TAG,"leaveItem=" + leaveItem.name + "," + leaveItem.halfDay )
                                       }
                                   }
                               }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
                                   Text(text = "Submit",
                                       color = Color.White)
                               }
                               if (bolClicked==true) {
                                   Text(text = "Pending Requests")
                                   //disLeaveData2(LocalContext.current)
                               }
                           }

                        }

                }
            }
        }
    }
}
//@Composable
//fun disLeaveData2(context: Context){
//        // on below line we are creating and initializing our array list
//    //lateinit var courseList: List<leave>
//    var courseList = ArrayList<leave>()
//
//    //val dbHandler: DatabaseHelper = DatabaseHelper(context);
//   // courseList = dbHandler.viewall()!!
//
//    // on below line we are creating a lazy column for displaying a list view.
//    LazyColumn {
//        // on below line we are setting data for each item of our listview.
//        itemsIndexed(courseList) { index, item ->
//            // on below line we are creating a card for our list view item.
//            Card(
//                // on below line we are adding padding from our all sides.
//                modifier = Modifier.padding(13.dp),
//                // on below line we are adding elevation for the card.
//                elevation = 6.dp
//            ) {
//                // on below line we are creating a row for our list view item.
//                Column(
//                    // for our row we are adding modifier to set padding from all sides.
//                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
//                    horizontalAlignment = Alignment.Start,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    // on the below line we are creating a text.
//                    Text(
//                        // inside the text on below line we are
//                        // setting text as the language name
//                        // from our model class.
//                        text = courseList[index].emName,
//
//                        // on below line we are adding padding
//                        // for our text from all sides.
//                        modifier = Modifier.padding(4.dp),
//
//                        // on below line we are adding color for our text
//                        color = Color.Black, textAlign = TextAlign.Center
//                    )
//
//                    // on below line inside row we are adding spacer
//                    Spacer(modifier = Modifier.width(5.dp))
//
//                    // on the below line we are creating a text.
//                    Text(
//                        // inside the text on below line we are
//                        // setting text as the language name
//                        // from our model class.
//                        text = "Course Duration : " + courseList[index].emLeaveType,
//
//                        // on below line we are adding padding
//                        // for our text from all sides.
//                        modifier = Modifier.padding(4.dp),
//
//                        // on below line we are adding color for our text
//                        color = Color.Black, textAlign = TextAlign.Center
//                    )
//
//                    // on below line inside row we are adding spacer
//                    Spacer(modifier = Modifier.width(5.dp))
//
//                    // on the below line we are creating a text.
//                    Text(
//                        // inside the text on below line we are
//                        // setting text as the language name
//                        // from our model class.
//                        text = "Course Duration : " + courseList[index].emStartDate,
//
//                        // on below line we are adding padding
//                        // for our text from all sides.
//                        modifier = Modifier.padding(4.dp),
//
//                        // on below line we are adding color for our text
//                        color = Color.Black, textAlign = TextAlign.Center
//                    )
//
//                    // on below line inside row we are adding spacer
//                    Spacer(modifier = Modifier.width(5.dp))
//
//                    // on the below line we are creating a text.
//                    Text(
//                        // inside the text on below line we are
//                        // setting text as the language name
//                        // from our model class.
//                        text = "Course Duration : " + courseList[index].emEndDate,
//
//                        // on below line we are adding padding
//                        // for our text from all sides.
//                        modifier = Modifier.padding(4.dp),
//
//                        // on below line we are adding color for our text
//                        color = Color.Black, textAlign = TextAlign.Center
//                    )
//
//                    // on below line inside row we are adding spacer
//                    Spacer(modifier = Modifier.width(5.dp))
//
//                    // on the below line we are creating a text.
//                    Text(
//                        // inside the text on below line we are
//                        // setting text as the language name
//                        // from our model class.
//                        text = "Course Duration : " + courseList[index].emHalfDay,
//
//                        // on below line we are adding padding
//                        // for our text from all sides.
//                        modifier = Modifier.padding(4.dp),
//
//                        // on below line we are adding color for our text
//                        color = Color.Black, textAlign = TextAlign.Center
//                    )
//
//                    // on below line inside row we are adding spacer
//                    Spacer(modifier = Modifier.width(5.dp))
//
//                    // on the below line we are creating a text.
//                    Text(
//                        // inside the text on below line we are
//                        // setting text as the language name
//                        // from our model class.
//                        text = "Course Duration : " + courseList[index].emStatus,
//
//                        // on below line we are adding padding
//                        // for our text from all sides.
//                        modifier = Modifier.padding(4.dp),
//
//                        // on below line we are adding color for our text
//                        color = Color.Black, textAlign = TextAlign.Center
//                    )
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dropDownMenu(){
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
private fun MyUI() {
    val contextForToast = LocalContext.current.applicationContext

    var checked by remember {
        mutableStateOf(false)
    }

    var checked2 by remember {
        mutableStateOf(false)
    }

    var checked3 by remember {
        mutableStateOf(false)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {


        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked_ ->
                    checked = checked_
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green
                )
            )

            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = "Half Day"
            )
        }
        if (checked == true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked2,
                    onCheckedChange = { checked_ ->
                        if (checked3 == true) {
                            checked2 = false
                        } else {
                            checked2 = checked_
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green
                    )
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "First Half"
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked3,
                    onCheckedChange = { checked_ ->
                        if (checked2 == true) {
                            checked3 = false
                        } else {
                            checked3 = checked_
                        }

                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green
                    )
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Second Half"
                )
            }
        }
    }
}

@Composable
fun LeaveBal1(){
    Column(modifier=Modifier.padding(20.dp)){
        Text(text="Leave Balance")
        var conn3: Connection? = null
        val connsql3 = MainActivity()
        conn3 = connsql3.connClass()

        var username = activeUser.username;
        val sQuery = "SELECT * FROM tblLeaveType2 WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"  + username + "'"
        val st2= conn3!!.createStatement()
        val rs3=st2.executeQuery(sQuery)
        var emDateIn:String=""
        var emLeaveTaken:Int=0
        var CLLeaveTaken:Int=0
        var MarriageLeaveTaken:Int=0
        var MaternityLeaveTaken:Int=0
        var ULTaken:Int=0
        var MCTaken:Int=0


        while(rs3.next()){
            emDateIn=rs3.getString(13)
            emLeaveTaken=rs3.getString(3).trim().toInt()
            CLLeaveTaken=rs3.getString(5).trim().toInt()
            MarriageLeaveTaken=rs3.getString(7).trim().toInt()
            MaternityLeaveTaken=rs3.getString(9).trim().toInt()
            ULTaken=rs3.getString(11).trim().toInt()
            MCTaken=rs3.getString(14).trim().toInt()

        }
        val dateStr = emDateIn // Date in dd/MM/yyyy format
Log.d(TAG,"datStr:" + dateStr)

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/M/yyyy")
        val date1 = LocalDate.parse(dateStr.trim(), formatter) // Parse the date string


        val date2 = LocalDate.now() // Current date


        var monthsDifference: Long = ChronoUnit.MONTHS.between(date1, date2)

        println("The difference in months is: $monthsDifference")

    Row(horizontalArrangement = Arrangement.SpaceEvenly){
        Text(text="Annual Leave: ")
        Text(text="$emLeaveTaken/$monthsDifference")
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly){
        Text(text="Compassions Leave: ")

        Text(text="$CLLeaveTaken/$monthsDifference")
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly){
        Text(text="Marriage Leave: ")
        Text(text="$MarriageLeaveTaken/$monthsDifference")
    }
        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text="Maternity Leave: ")
            Text(text="$MaternityLeaveTaken/$monthsDifference")
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text="Unpaid Leave: ")
            Text(text="$ULTaken/$monthsDifference")
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text="MC: ")
            Text(text="$MCTaken/$monthsDifference")
        }
}

}

@Composable
fun LeaveBal12(){
    var conn3: Connection? = null
    val connsql3 = MainActivity()
    conn3 = connsql3.connClass()

    var username = activeUser.username;
    val sQuery = "SELECT * FROM tblLeaveType2 WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"  + username + "'"
    val st2= conn3!!.createStatement()
    val rs3=st2.executeQuery(sQuery)
    var emDateIn:String=""
    var emLeaveTaken:Int=0
    var CLLeaveTaken:Int=0
    var MarriageLeaveTaken:Int=0
    var MaternityLeaveTaken:Int=0
    var ULTaken:Int=0

    while(rs3.next()){
        emDateIn=rs3.getString(13)
        emLeaveTaken=rs3.getString(3).trim().toInt()
        CLLeaveTaken=rs3.getString(5).trim().toInt()
        MarriageLeaveTaken=rs3.getString(7).trim().toInt()
        MaternityLeaveTaken=rs3.getString(9).trim().toInt()
        ULTaken=rs3.getString(11).trim().toInt()

    }
    val dateStr = emDateIn // Date in dd/MM/yyyy format


    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/M/yyyy")
    val date1 = LocalDate.parse(dateStr.trim(), formatter) // Parse the date string


    val date2 = LocalDate.now() // Current date


    var monthsDifference: Long = ChronoUnit.MONTHS.between(date1, date2)

    println("The difference in months is: $monthsDifference")
    Column(modifier=Modifier.padding(20.dp)){
        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text=" ")
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text="Maternity Leave: ")
            Text(text="$MaternityLeaveTaken/$monthsDifference")
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text="Unpaid Leave: ")
            Text(text="$ULTaken/$monthsDifference")
        }
    }

}

@Composable
fun reason(){
    var text by rememberSaveable { mutableStateOf("Text") }
    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        label = { Text("Label") }
    )
}

@Composable
fun SelectDate(){

        selectDate2()


}

// Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content
// in the above function
@Composable
fun selectDate2(){

    // Fetching the Local Context
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
    val mDate = remember { mutableStateOf("") }
    val mDate2 = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val mDatePickerDialog2 = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate2.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    var text by rememberSaveable { mutableStateOf("Start Date") }
    var text2 by rememberSaveable { mutableStateOf("End Date") }
    Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically){
        Text(text="Select Date:  ")

        // Creating a button that on
        // click displays/shows the DatePickerDialog
        text="Select Date"
        Button(onClick = {
            mDatePickerDialog.show()
            text="Start Date"
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
            if (mDate.value!=""){
                text=mDate.value
            }else{
                text="Start Date"
            }

            Text(text = text,
                color = Color.White)
        }

        Button(onClick = {
            mDatePickerDialog2.show()
            text2="End Date"
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
            if (mDate2.value!=""){
                text2=mDate2.value
            }else{
                text2="End Date"
            }

            Text(text = text2,
                color = Color.White)
        }
    }
}