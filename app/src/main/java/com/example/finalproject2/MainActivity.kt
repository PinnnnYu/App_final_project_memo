package com.example.finalproject2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout

    var gridlength:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*toolbar = findViewById(R.id.include1)
        setSupportActionBar(toolbar)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }*/

        val toolbar = findViewById<Toolbar>(R.id.include1)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        //navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val fab: View = findViewById(R.id.floatingActionButton)

        //OkHttp調用
        //Read api
        val client = OkHttpClient()
        val urlBuilder = "http://192.168.206.74/app/read.php".toHttpUrlOrNull()
            ?.newBuilder()
            ?.addQueryParameter("title", "st")
            ?.addQueryParameter("limit", "50")
        val url = urlBuilder?.build().toString()
        val request = Request.Builder()
            .url(url)
            .build()

        val gridView = findViewById<GridView>(R.id.gridView)
        val grid = ArrayList<HashMap<String, Any>>()
        val list: MutableList<String> = ArrayList()



        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    val responseBody = response.body?.string()
                    //println("-------${responseBody}")

                    val jsonObject = JSONObject(responseBody)
                    val dataArray = jsonObject.getJSONArray("data")


                    // 將以 Read api 讀取到的資料用到 gridView 的方式排列，使其更像備忘錄
                    // 並設定當 gridView 按下時，可跳轉畫面至 MainActivity (代表編輯頁面)
                    // 找到欄位序列，將資料以 Hashmap 的形式傳送，再到 MainActivity 做處理資料
                    runOnUiThread {
                        for (i in 0 until dataArray.length()){
                            val chatObject = dataArray.getJSONObject(i)
                            //val no = chatObject.getString("no")
                            val timestamp = chatObject.getString("timestamp")

                            val title = chatObject.getString("title")
                            val content = chatObject.getString("content")
                            list.add("標題：${title}\n內容：\n${content}\n")
                        }

                        for (i in list.indices){
                            val map = HashMap<String, Any>()
                            map["title"] = list[i]
                            grid.add(map)
                        }
                        val fromData = arrayOf("title")
                        val gridlength = grid.size
                        //println("gridlength = " + gridlength)
                        val toData = intArrayOf(R.id.textView6)
                        val simpleAdapter = SimpleAdapter(this@MainActivity, grid, R.layout.grid_item, fromData, toData)
                        gridView.adapter = simpleAdapter
                        gridView.setOnItemClickListener { adapterView, view, i, l ->
                            val intent = Intent()
                            intent.setClass(this@MainActivity, MainActivity2::class.java)
                            intent.putExtra("no", gridlength-i)
                            intent.putExtra("title",grid[i])
                            startActivity(intent)
                        }
                        fab.setOnClickListener {
                            val intent = Intent()
                            intent.setClass(this@MainActivity, MainActivity2::class.java)
                            startActivity(intent)
                        }
                    }
                }
                else{
                    println("Request failed")
                    runOnUiThread {
                        findViewById<TextView>(R.id.textView6).text = "資料錯誤"
                    }
                }
            }
        })


        val timer = Timer()
        val timerTask = object : TimerTask(){
            override fun run(){
                val client = OkHttpClient()
        val urlBuilder = "http://192.168.206.74/app/read.php".toHttpUrlOrNull()
            ?.newBuilder()
            ?.addQueryParameter("title", "st")
            ?.addQueryParameter("limit", "50")
        val url = urlBuilder?.build().toString()
        val request = Request.Builder()
            .url(url)
            .build()

        val gridView = findViewById<GridView>(R.id.gridView)
        val grid = ArrayList<HashMap<String, Any>>()
        val list: MutableList<String> = ArrayList()



        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    val responseBody = response.body?.string()
                    //println("-------${responseBody}")

                    val jsonObject = JSONObject(responseBody)
                    val dataArray = jsonObject.getJSONArray("data")


                    // 將以 Read api 讀取到的資料用到 gridView 的方式排列，使其更像備忘錄
                    // 並設定當 gridView 按下時，可跳轉畫面至 MainActivity (代表編輯頁面)
                    // 找到欄位序列，將資料以 Hashmap 的形式傳送，再到 MainActivity 做處理資料
                    runOnUiThread {
                        for (i in 0 until dataArray.length()){
                            val chatObject = dataArray.getJSONObject(i)
                            //val no = chatObject.getString("no")
                            val timestamp = chatObject.getString("timestamp")

                            val title = chatObject.getString("title")
                            val content = chatObject.getString("content")
                            list.add("標題：${title}\n內容：\n${content}\n")
                        }

                        for (i in list.indices){
                            val map = HashMap<String, Any>()
                            map["title"] = list[i]
                            grid.add(map)
                        }
                        val fromData = arrayOf("title")
                        val gridlength = grid.size
                        //println("gridlength = " + gridlength)
                        val toData = intArrayOf(R.id.textView6)
                        val simpleAdapter = SimpleAdapter(this@MainActivity, grid, R.layout.grid_item, fromData, toData)
                        gridView.adapter = simpleAdapter
                        gridView.setOnItemClickListener { adapterView, view, i, l ->
                            val intent = Intent()
                            intent.setClass(this@MainActivity, MainActivity2::class.java)
                            intent.putExtra("no", gridlength-i)
                            intent.putExtra("title",grid[i])
                            startActivity(intent)
                        }
                        fab.setOnClickListener {
                            val intent = Intent()
                            intent.setClass(this@MainActivity, MainActivity2::class.java)
                            startActivity(intent)
                        }
                    }
                }
                else{
                    println("Request failed")
                    runOnUiThread {
                        findViewById<TextView>(R.id.textView6).text = "資料錯誤"
                    }
                }
            }
                })
            }
        }
        timer.schedule(timerTask, 0, 5000)

    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when (item.itemId){
            R.id.login -> startActivity(Intent(this,loginMainActivity::class.java))
            //supportFragmentManager.beginTransaction()
            //.replace(R.id.fragment_container,LoginFragment()).commit()
            R.id.logout -> Toast.makeText(this,"Logout!",Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }


}