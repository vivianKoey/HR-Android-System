package com.example.myapplication.backup

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.io.File
import java.sql.Connection
import java.util.concurrent.Executors
import coil.compose.rememberImagePainter
import com.example.myapplication.*
import com.example.myapplication.R

class activityDetailClaim : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = LeaveDatabase.getDatabase(applicationContext)
        val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1

// Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
            )
        }
        val leaveDao = database.LeaveDao()
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column{
                        var recipeList = DataHolder.arrClaim
                        var selectedID: Int = 0
                        var selectedName: String = ""
                        var selectedDesc: String = ""
                        var selectedDate: String = ""
                        var selectedType: String = ""
                        var selectedAmount: String = ""
                        var selectedFile: String = ""
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
                            selectedDesc = recipe.desc
                            selectedDate = recipe.claimDate
                            selectedType = recipe.claimType
                            selectedAmount= recipe.amount
                            selectedFile= recipe.claimFile
                        }

                        var txtID by rememberSaveable { mutableStateOf(selectedID) }
                        var txtUsername by rememberSaveable { mutableStateOf(selectedName) }
                        var txtDesc by rememberSaveable { mutableStateOf(selectedDesc) }
                        var txtDate by rememberSaveable { mutableStateOf(selectedDate) }
                        var txtSelectedType by rememberSaveable { mutableStateOf(selectedType) }
                        var txtAmount by rememberSaveable { mutableStateOf(selectedAmount) }
                        var txtFile by rememberSaveable { mutableStateOf(selectedFile) }

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
                                    Intent(this@activityDetailClaim, actHRClaim::class.java)
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
                                .height(5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Description: ")
                           Text(txtDesc)
                        }

                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Claim Date: ")
                           Text(txtDate)
                        }
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Claim Type: ")
                           Text(txtSelectedType)
                        }

                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Claim Amount: ")
                            Text(txtAmount)
                        }

                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Claim Status: ")

                            Box(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }) {
                                    TextField(
                                        value = selectedStatus,
                                        onValueChange = {selectedStatus=it},
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
                                                    selectedStatus = it
                                                    expanded = false

                                                }) {
                                                Toast.makeText(this@activityDetailClaim,"selectedStatus=" + selectedStatus, Toast.LENGTH_SHORT).show()
                                                Text(text = it)
                                            }
                                        }
                                    }
                                }
                            }
                        }
//                        Spacer(
//                            modifier = Modifier
//                                .width(16.dp)
//                                .height(5.dp)
//                        )
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {

                            Button(
                                onClick = {
                                    var conn2: Connection? = null
                                    val connsql = MainActivity()
                                    conn2 = connsql.connClass()
                                    val iQuery =
                                        "UPDATE tblEmClaim\n" +
                                                "SET claimStatus='" + selectedStatus + "'\n" +
                                                "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'" + txtUsername + "' and CONVERT(NVARCHAR(MAX), claimDate) =  N'" + txtDate + "';"
                                    val st = conn2.createStatement()
                                    var rs = st.executeUpdate(iQuery)
                                    if (rs == 1) {
                                        Toast.makeText(
                                            this@activityDetailClaim,
                                            "Update claim status Successful",
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
                        Spacer(
                            modifier = Modifier
                                .width(16.dp)
                                .height(5.dp)
                        )
                        var scale by remember { mutableStateOf(1f) }
                        var offsetX by remember { mutableStateOf(0f) }
                        var offsetY by remember { mutableStateOf(0f) }
//                        Text(
//                            // inside the text on below line we are
//                            // setting text as the language name
//                            // from our model class.
//                            text = "Claim File : "+ txtFile ,
//
//                            // on below line we are adding padding
//                            // for our text from all sides.
//                            modifier = Modifier.padding(4.dp),
//
//                            // on below line we are adding color for our text
//                            color = Color.Black, textAlign = TextAlign.Left
//                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    forEachGesture {
                                        awaitPointerEventScope {
                                            awaitFirstDown()
                                            do {
                                                val event = awaitPointerEvent()
                                                scale *= event.calculateZoom()
                                                val offset = event.calculatePan()
                                                offsetX += offset.x
                                                offsetY += offset.y
                                            } while (event.changes.any { it.pressed })
                                        }
                                    }
                                }
                        ) {

                            var txtImageUri by rememberSaveable {
                                mutableStateOf(txtFile)

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

                            // painterResource method loads an image resource asynchronously
//                            val imagepainter = painterResource(id = R.drawable.ic_launcher_background)
//                            // We use the graphicsLayer modifier to modify the scale & translation
//                            // of the image.
//                            // This is read from the state properties that we created above.
//                            Image(
//                                modifier = Modifier.fillMaxSize().graphicsLayer(
//                                    scaleX = scale,
//                                    scaleY = scale,
//                                    translationX = offsetX,
//                                    translationY = offsetY
//                                ),
//                                painter = imagepainter,
//                                contentDescription = "androids launcher default launcher background image"
//                            )
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
                                        modifier = Modifier.fillMaxSize().graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    translationX = offsetX,
                                    translationY = offsetY
                                ),
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
