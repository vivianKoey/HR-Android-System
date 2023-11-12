package com.example.myapplication

//import com.example.myapplication.DB.DatabaseHelper
import android.Manifest.permission
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.Environment
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.backup.EmployeeDatabase
import com.example.myapplication.backup.employee
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class actGeneratePayslip : ComponentActivity() {
    val pageHeight = 1120
    val pagewidth = 792

    //    var dbHelper: DatabaseHelper? = null
    var selectedText: String? = null
    var name: String? = null
    var salary: Double? = null
    var FA: String? = null
    var OTB: Double? = null
    var age: String? = null
    var tr: String? = null
    var epfrate: Int? = null
    var status: String? = null
    var totalEPFRate: Double = 0.00
    lateinit var selectedName: String
    private val PERMISSION_REQUEST_CODE = 200

    @OptIn(ExperimentalMaterialApi::class)
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
                var arrayList5 = remember { mutableStateListOf<String>() }
                var bol by rememberSaveable { mutableStateOf("false") }
                val database = EmployeeDatabase.getDatabase(applicationContext)
                var firstItem = ""
                var selecteddrinks by rememberSaveable { mutableStateOf(firstItem) }


                val employeeDao = database.EmployeeDao()
                var arrayList6 = remember { mutableStateListOf<employee>() }
                var courseList = remember { mutableStateListOf<String>() }
                var bol2 by rememberSaveable { mutableStateOf("false") }
                var arrEmInfo by rememberSaveable { mutableStateOf(ArrayList<String>()) }
                var selectedEmInfo by rememberSaveable { mutableStateOf("Choose Employee") }
                if (checkPermission()==true) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    requestPermission()
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
                            "Generate Payslip"
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
                                                this@actGeneratePayslip,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
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
                                                this@actGeneratePayslip,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
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
                                                this@actGeneratePayslip,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
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
                                                this@actGeneratePayslip,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actGeneratePayslip,
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

                    var expanded by remember {
                        mutableStateOf(false)
                    }

//
//                    arrayList5.clear()
//                    val executor = Executors.newSingleThreadExecutor()
//
//                    executor.execute {
//                        val allRecipes = employeeDao.getAllRecipes()
//
//                        for (item in allRecipes) {
//                            arrayList5.add(employee(item.id,item.name,item.age,item.salary,item.FA,item.OTB,item.TA,item.EPF,item.position,item.pwd,item.manager))
//                            Log.d(TAG,"name1=" +item.name)
//                        }
//                        executor.shutdown()
//                    }
//
//                  arrayList5.forEach{
//                      Log.d(TAG,"arraylist5=" + it.name)
//                  }
                    Column {

//
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
                            val st2 = conn!!.createStatement()
                            val rs = st2.executeQuery(sQuery)
                            var emID: Int = 0
                            while (rs.next()) {
                                arrEmInfo.add(
                                    rs.getString(1).toString() + "," + rs.getString(2).toString()
                                )
                            }
                            var expanded by remember {
                                mutableStateOf(false)
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()


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
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }) {
                                        arrEmInfo.forEach {
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedEmInfo = it
                                                    expanded = false
                                                }) {
                                                Text(text = it.toString())
                                            }
                                        }
                                    }

                                }

                            }
                            Button(

                                onClick = {

                                    bol2 = "true"

                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0XFF0F9D58)

                                ),
                                        modifier = Modifier
                                        .padding(16.dp)
                                            .width(200.dp)

                            ) {
                                Text(
                                    text = "Generate PDF",
                                    color = Color.White
                                )
                            }
//                        }
                            if (bol2 == "true") {
                                val pdfDocument = PdfDocument()
                                val paint = Paint()
                                val title = Paint()
                                val mypageInfo = PageInfo.Builder(pagewidth, pageHeight, 1).create()
                                val myPage = pdfDocument.startPage(mypageInfo)
                                val canvas = myPage.canvas
                                title.typeface =
                                    Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                                title.textSize = 15f
                                title.color =
                                    ContextCompat.getColor(applicationContext, R.color.black)
                                val currentDate = SimpleDateFormat(
                                    "dd-MM-yyyy",
                                    Locale.getDefault()
                                ).format(Date())
                                canvas.drawText("Payslip", 350f, 50f, title)
                                canvas.drawText("ABC SDN BHD", 80f, 100f, title)
                                canvas.drawText("Date=$currentDate", 600f, 100f, title)


//                                val executor = Executors.newSingleThreadExecutor()
//
//                                executor.execute {
//                                    val allRecipes = employeeDao.getAllRecipes()
//                                    for (item in allRecipes) {
//                                        Log.d(ContentValues.TAG, "item.id=" + item.id)
//
//                                        arrayList6.add(
//                                            employee(
//                                                item.id,
//                                                item.name,
//                                                item.age,
//                                                item.salary,
//                                                item.FA,
//                                                item.OTB,
//                                                item.TA,
//                                                item.EPF,
//                                                item.position,
//                                                item.pwd,
//                                                item.manager,
//                                                item.status,
//                                                "false"
//                                            )
//                                        )
//                                    }
//
//                                }
                                var emInfo=split(selectedEmInfo,",")
                                var emID=emInfo[0]

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
                                    val sQuery = "SELECT * FROM tblEmployeeRecord WHERE CONVERT(NVARCHAR(MAX), emID) =  N'"+ emID + "'"
                                    val st2 = conn!!.createStatement()
                                    val rs = st2.executeQuery(sQuery)
                                    var emID: Int = 0
                                    while (rs.next()) {
//                                        arrEmInfo.add(
//                                            rs.getString(0).toString() + "," + rs.getString(1).toString() + "," + rs.getString(2).toString()+ "," + rs.getString(3).toString()+ "," + rs.getString(4).toString()+ "," + rs.getString(5).toString()+ "," + rs.getString(6).toString()+ "," + rs.getString(7).toString()+ "," + rs.getString(8).toString()+ "," + rs.getString(9).toString()+ "," + rs.getString(10).toString()+ "," + rs.getString(11).toString()
//                                        )
//                                    }


                                canvas.drawText("Employee No:"+ rs.getString(1).toString(), 80f, 150f, title)
//                                arrEmInfo.forEach {
//                                    Log.d(ContentValues.TAG, "it1=" + rs.getString(1).toString())
//                                    if (it.name == selectedName) {
//                                        Log.d(ContentValues.TAG, "it2=" + it.name)

                                        name = rs.getString(2).toString()
                                        salary =rs.getString(3).toDouble()
                                        OTB = rs.getString(5).toDouble()
                                        canvas.drawText("Name:$name", 200f, 150f, title)
                                        canvas.drawText("Basic Salary:", 80f, 220f, title)
                                        canvas.drawText("RM   $salary", 290f, 220f, title)
                                        canvas.drawText("Bonus:", 80f, 250f, title)
                                        val salaryInt: Int = salary!!.toInt()
                                        canvas.drawText("RM  $OTB", 290f, 250f, title)
                                        val otbInt: Double? = OTB
                                        canvas.drawText("Gross Pay:  ", 80f, 420f, title)
                                        canvas.drawLine(270f, 400f, 380f, 400f, paint)
                                        val total = salaryInt + otbInt!!
                                        canvas.drawText("RM  $total", 290f, 420f, title)
                                        canvas.drawText("Less:  ", 80f, 480f, title)
                                        val epfInt: Int = rs.getString(7).toInt()
                                        val totalEPF = epfInt / 100.0
                                        totalEPFRate = total * totalEPF
                                        canvas.drawText("Employee EPF:  ", 120f, 510f, title)
                                        canvas.drawText("RM   $totalEPFRate", 290f, 510f, title)
                                        val totalSocso = total * (0.5 / 100)
                                        canvas.drawText("Employee SOCSO:  ", 120f, 540f, title)
                                        canvas.drawText("RM   $totalSocso", 290f, 540f, title)
                                        val totalEIS = total * (0.2 / 100)
                                        canvas.drawText("Employee EIS:  ", 120f, 570f, title)
                                        canvas.drawText("RM   $totalEIS", 290f, 570f, title)
                                        canvas.drawLine(270f, 600f, 380f, 600f, paint)
                                        val deduction: Double =
                                            totalEPFRate!! + totalSocso + totalEIS
                                        canvas.drawText("Total Deduction:  ", 120f, 620f, title)
                                        canvas.drawText("RM  $deduction", 290f, 620f, title)
                                        canvas.drawLine(270f, 640f, 380f, 640f, paint)
                                        canvas.drawText("Net Pay:  ", 80f, 660f, title)
                                        val totDed: Double =
                                            total - totalEPFRate!! - totalSocso - totalEIS
                                        canvas.drawText("RM  $totDed", 290f, 660f, title)
                                        canvas.drawLine(270f, 670f, 380f, 670f, paint)
                                        canvas.drawLine(270f, 675f, 380f, 675f, paint)
                                        canvas.drawText("PCB: Monthly:  0.00", 80f, 700f, title)

                                        canvas.drawText(
                                            "Payment Method: Bank Transfer",
                                            520f,
                                            150f,
                                            title
                                        )
                                        canvas.drawLine(10f, 180f, 800f, 180f, paint)
                                        canvas.drawText("MTD", 600f, 220f, title)
                                        canvas.drawText("YTD", 700f, 220f, title)
                                        canvas.drawText("EPF", 550f, 260f, title)
                                        canvas.drawLine(450f, 270f, 800f, 270f, paint)
                                        canvas.drawText("Employee:", 450f, 300f, title)
                                        canvas.drawText("" + totalEPFRate, 595f, 300f, title)
                                        canvas.drawText("" + totalEPFRate, 695f, 300f, title)
                                        val ytdEmployer: Double = totalEPFRate * 2.5
                                        canvas.drawText("Employer:", 450f, 330f, title)
                                        canvas.drawText("" + ytdEmployer, 595f, 330f, title)
                                        canvas.drawText("" + ytdEmployer, 695f, 330f, title)
                                        canvas.drawText("Total:", 450f, 360f, title)
                                        canvas.drawText("SOCSO", 550f, 410f, title)
                                        canvas.drawLine(450f, 420f, 800f, 420f, paint)
                                        canvas.drawText("Employee:", 450f, 450f, title)
                                        canvas.drawText("Employer:", 450f, 480f, title)
                                        canvas.drawText("Total:", 450f, 510f, title)
                                        canvas.drawText("EIS", 550f, 560f, title)
                                        canvas.drawLine(450f, 570f, 800f, 570f, paint)
                                        canvas.drawText("Employee:", 450f, 600f, title)
                                        canvas.drawText("Employer:", 450f, 630f, title)
                                        canvas.drawText("Total:", 450f, 660f, title)
                                        canvas.drawText("TAX", 550f, 710f, title)
                                        canvas.drawLine(450f, 720f, 800f, 720f, paint)
                                        canvas.drawText("Employee:", 450f, 750f, title)
                                        canvas.drawText("Approved by:  ", 450f, 800f, title)
                                        canvas.drawLine(420f, 900f, 580f, 900f, paint)
                                        canvas.drawText("Received by:  ", 650f, 800f, title)
                                        canvas.drawLine(620f, 900f, 780f, 900f, paint)
                                        title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                                        title.color = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.black
                                        )
                                        title.textSize = 15f
                                        pdfDocument.finishPage(myPage)
                                        val file = File(
                                            Environment.getExternalStorageDirectory(),
                                            "GFG.pdf"
                                        )
                                        Log.d(
                                            TAG,
                                            "Environment.getExternalStorageDirectory()=" + Environment.getExternalStorageDirectory()
                                        )
                                        try {
                                            pdfDocument.writeTo(FileOutputStream(file))
//                                                Toast.makeText(
//                                                    this@actGeneratePayslip,
//                                                    "PDF file generated successfully.",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
                                            Log.d(
                                                TAG,
                                                "PDF file generated successfully. " + Environment.getExternalStorageDirectory()
                                            )
                                        } catch (e: IOException) {
                                            //  e.printStackTrace()
                                            Log.e(
                                                TAG,
                                                "error=  " + e.printStackTrace()
                                            )
                                        }
                                        pdfDocument.close()

                                        bol2 = "false"
                                    }


//                                }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
        fun checkPermission(): Boolean {
            val permission1 =
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission.WRITE_EXTERNAL_STORAGE
                )
            val permission2 =
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission.READ_EXTERNAL_STORAGE
                )
            return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermission() {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE),
                this.PERMISSION_REQUEST_CODE
            )
        }

}

