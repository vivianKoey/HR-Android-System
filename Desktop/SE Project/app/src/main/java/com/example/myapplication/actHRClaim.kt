package com.example.myapplication

//import com.example.myapplication.DB.DatabaseHelper
import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorFilter
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.myapplication.backup.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.sql.Connection
import androidx.compose.ui.graphics.ColorFilter.Companion.tint

class actHRClaim : ComponentActivity() {
//    var dbHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dbHelper = DatabaseHelper(this)

    val database = LeaveDatabase.getDatabase(applicationContext)
    val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1

// Check if the permission is already granted
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Toast.makeText(
            this@actHRClaim,
            "!PERMISSION_GRANTED",
            Toast.LENGTH_SHORT
        ).show()
        // Permission is not granted, request it
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var txtPosition by rememberSaveable { mutableStateOf("") }
                var txtName by rememberSaveable { mutableStateOf("") }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBarHRClaim(
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
                                                this@actHRClaim,
                                                actEmLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmClaim") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
                                                actEmClaim::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actEmUpdatePwd") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
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
                                                this@actHRClaim,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
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
                                                this@actHRClaim,
                                                actEmployeeDataHR::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actGeneratePayslip") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
                                                actGeneratePayslip::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRLeave") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
                                                actHRLeave::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actHRPCBCal") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
                                                actHRPCBCal::class.java
                                            )
                                        startActivity(intent)
                                    }
                                    if (it.id == "actSendPayslip") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
                                                actSendPayslip::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRClaim") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
                                                actHRClaim::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    if (it.id == "actHRPost") {
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
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
                                                this@actHRClaim,
                                                "Logout Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        val intent =
                                            Intent(
                                                this@actHRClaim,
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

                    val items= remember{ mutableListOf<claim>()}
                    val selectedID = remember { mutableStateOf("ID") }
                    val selectedName = remember { mutableStateOf("Name") }
                    val selectedDesc = remember { mutableStateOf("Description") }
                    val selectedClaimDate = remember { mutableStateOf("ClaimDate") }
                    val selectedClaimType = remember { mutableStateOf("ClaimType") }
                    val selectedAmount = remember { mutableStateOf("Amount") }
                    val selectedClaimFile = remember { mutableStateOf("ClaimFile") }

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
                            val sQuery = "SELECT * FROM tblEmClaim WHERE claimStatus='Pending Request'"
                            val st2 = conn?.createStatement()
                            val rs = st2?.executeQuery(sQuery)
                            var emID: Int = 0
                            while (rs?.next() == true) {
//                                Toast.makeText(
//                                    this@actHRLeave,
//                                    rs.getString(2).toString(),
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                Log.d(TAG,"rs item=" + rs.getString((1)))
                                items.add(claim(rs.getString(1).toInt(), rs.getString(2).toString(), rs.getString(3).toString(), rs.getString(4).toString(), rs.getString(5).toString(),rs.getString(6).toString(),rs.getString(7).toString(),rs.getString(8).toString()))
                            }
                        }
                        // on below line we are setting data for each item of our listview.
                        itemsIndexed(items) { index, item ->
                            // on below line we are creating a card for our list view item.
                            var emID = items[index].id
                            var emName = items[index].name
                            var emDesc = items[index].desc
                            var emClaimDate = items[index].claimDate
                            var emClaimType = items[index].claimType
                            var emAmount = items[index].amount
                            var emClaimFile = items[index].claimFile
                            var emClaimStatus=items[index].claimStatus

                            Card(
                                // on below line we are adding padding from our all sides.
                                modifier = Modifier.padding(8.dp)
                                    .clickable {
                                        selectedID.value= emID.toString()
                                        selectedName.value=emName
                                        selectedDesc.value=emDesc
                                        selectedClaimDate.value=emClaimDate
                                        selectedClaimType.value=emClaimType
                                        selectedAmount.value=emAmount
                                        selectedClaimFile.value=emClaimFile

                                        val intent = Intent(
                                            this@actHRClaim,
                                            activityDetailClaim::class.java
                                        )

                                        DataHolder.arrClaim.add(claim(selectedID.value.toInt(),selectedName.value,selectedDesc.value,  selectedClaimDate.value, selectedClaimType.value,selectedAmount.value,selectedClaimFile.value,emClaimStatus))
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

                                        text = "Description : " + items[index].desc,

                                        // on below line we are adding padding
                                        // for our text from all sides.
                                        modifier = Modifier.padding(4.dp),

                                        // on below line we are adding color for our text
                                        color = Color.Black, textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(

                                        text = "Date : " + items[index].claimDate,

                                        modifier = Modifier.padding(4.dp),

                                        color = Color.Black, textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(

                                        text = "Claim Type : " + items[index].claimType,

                                        modifier = Modifier.padding(4.dp),

                                        color = Color.Black, textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(

                                        text = "Amount : " + items[index].amount,
                                        modifier = Modifier.padding(4.dp),

                                        color = Color.Black, textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))

                                    var txtImageUri by rememberSaveable {
                                        mutableStateOf(emClaimFile)

                                    }
                                    var imageUri by remember {
                                        mutableStateOf<Uri?>(txtImageUri.toUri())
                                    }
                                    val context = LocalContext.current
                                    val bitmap =  remember {
                                        mutableStateOf<Bitmap?>(null)
                                    }
                                    val launcher = rememberLauncherForActivityResult(contract =
                                    ActivityResultContracts.GetContent()) { uri: Uri? ->
                                        imageUri = uri
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        imageUri?.let {

                                            if (Build.VERSION.SDK_INT < 28) {
                                                bitmap.value = MediaStore.Images
                                                    .Media.getBitmap(context.contentResolver,it)

                                            } else {
                                                val source = ImageDecoder
                                                    .createSource(context.contentResolver,it)
                                                bitmap.value = ImageDecoder.decodeBitmap(source)
                                            }




                                            bitmap.value?.let {  btm ->
                                                Image(bitmap = btm.asImageBitmap(),
                                                    contentDescription =null,
                                                    modifier = Modifier.size(100.dp)
                                                        .clickable {
                                                            launcher.launch("image/*")
                                                        }
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

            }
        }
}




