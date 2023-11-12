package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class actFirstPage : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

                    var txtIP by rememberSaveable { mutableStateOf("") }
                    var txtPort by rememberSaveable { mutableStateOf("") }
            var txtUsername by rememberSaveable { mutableStateOf("") }
            var txtPwd by rememberSaveable { mutableStateOf("") }

                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(40.dp) ) {

                        TextField(value = txtIP, onValueChange ={
                            txtIP=it
                        },
                            placeholder = {
                                Text(text="IP")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(value = txtPort, onValueChange ={
                            txtPort=it
                        },
                            placeholder = {
                                Text(text="Port")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(value = txtUsername, onValueChange ={
                            txtUsername=it
                        },
                            placeholder = {
                                Text(text="Username")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(value = txtPwd, onValueChange ={
                            txtPwd=it
                        },
                            placeholder = {
                                Text(text="Password")
                            }
                        )
                        var iQuery: String? =null
                        var emID: Int = 0
                        var emName:String=""
                        Button(onClick = {
                            var conn: Connection? = null
                            val connsql = MainActivity()

                            sqlInfo.ip=txtIP
                            sqlInfo.port=txtPort
                            sqlInfo.username=txtUsername
                            sqlInfo.password=txtPwd

                            val intent =
                                Intent(this@actFirstPage, actLoginPage::class.java)
                            startActivity(intent)

                        }) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }


