package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.backup.EmployeeDatabase
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.sql.Connection

class actLoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = EmployeeDatabase.getDatabase(applicationContext)

        val recipeDao = database.EmployeeDao()

        setContent {
            MyApplicationTheme {
                var emName by rememberSaveable { mutableStateOf("") }
                var emPwd by rememberSaveable { mutableStateOf("") }

                var result=""
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.imagecompany),
                            contentDescription = "Round Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(20.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color.Red, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(50.dp))



                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Username: ")

                            TextField(
                                value = emName,
                                onValueChange = {
                                    emName = it
                                },
                                label = { Text("Username") }
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Password: ")

                            TextField(
                                value = emPwd,
                                onValueChange = {
                                    emPwd = it
                                },
                                label = { Text("Password") }
                            )
                        }

                        Column(
                            modifier =  Modifier.padding(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    var conn: Connection? = null
                                    val connsql = MainActivity()
                                    conn = connsql.connClass()
                                    Log.i(ControlsProviderService.TAG, "conn=$conn")
                                    var bolStatus:Boolean=true
                                    if (conn != null) {
                                        val sQuery = "SELECT * FROM tblEmployeeRecord"
                                        val st2 = conn.createStatement()
                                        val rs = st2.executeQuery(sQuery)
                                        var emID: Int = 0
                                        while (rs.next()) {
                                            Log.i(ControlsProviderService.TAG, "rs=" + rs.getString(2) + "," + rs.getString(9))
                                            if (rs.getString(2) == emName) {
                                                if (rs.getString(9) == emPwd) {
                                                    bolStatus=true
                                                    var conn2: Connection? = null
                                                    val connsql = MainActivity()
                                                    conn2 = connsql.connClass()
                                                    val iQuery =
                                                        "UPDATE tblEmployeeRecord\n" +
                                                                "SET emActive='true'\n" +
                                                                "WHERE CONVERT(NVARCHAR(MAX), emName) =  N'"+ emName + "';"
                                                    val st = conn2.createStatement()
                                                    var rs= st.executeUpdate(iQuery)
                                                    if (rs==1) {
                                                        activeUser.username=emName;
                                                        Toast.makeText(
                                                            this@actLoginPage,
                                                            "Update active Successful",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                    Toast.makeText(this@actLoginPage,"Login Success",Toast.LENGTH_SHORT).show()
                                                    val intent =
                                                        Intent(this@actLoginPage, actEmDashboard::class.java)
                                                    startActivity(intent)

                                                }
                                            }
                                        }
                                    }
                                    if (bolStatus==false){
                                        Toast.makeText(this@actLoginPage,"Wrong Username or Password. Please insert correct username and password",Toast.LENGTH_SHORT).show()
                                    }
//                                    if (result=="Login Success"){
//                                        Toast.makeText(this@actLoginPage,"Login Success",Toast.LENGTH_SHORT).show()
//                                        val intent =
//                                            Intent(this@actLoginPage, actEmployeeDataHR::class.java)
//                                        startActivity(intent)
//                                   }
//                                    if (result=="Wrong Username or Password"){
//                                        Toast.makeText(this@actLoginPage,"Wrong Username or Password. Please insert correct username and password",Toast.LENGTH_SHORT).show()
//                                    }

//                                    executor.execute {
//                                        val allRecipes = recipeDao.getAllRecipes()
//                                        for (item in allRecipes) {
//                                            if (item.name == emName) {
//                                                Log.d(
//                                                    ContentValues.TAG,
//                                                    "item=" + item.name + "," + item.active
//                                                )
//                                            }
//                                        }
//                                    }
//                                    result=""
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
//

                }
            }
        }
    }
}
