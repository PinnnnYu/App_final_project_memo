package com.example.finalproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class MainActivity2 : AppCompatActivity() {

    private lateinit var toolbar2: Toolbar
    var gridId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        toolbar2 = findViewById(R.id.include2)

        setSupportActionBar(toolbar2)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        toolbar2.setNavigationOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }

        //設定變數
        val send = findViewById<Button>(R.id.button)
        val title = findViewById<TextView>(R.id.editTextTextPersonName2)
        val content = findViewById<TextView>(R.id.editTextTextPersonName3)
        //var no:Int = 0

        intent?.extras?.let {
            val value = it.get("title").toString()
            val value1 = value.substring(9).split("：")[1].split("\n")[0]
            val value2 = value.substring(9).split("：")[2].split("\n")[1]
            println(value)
            title.setText(value1)
            content.setText(value2)
            gridId = it.getInt("no")
        }

        if(title.length() == 0 && content.length() == 0){
            // 當 button 按下時，將資料透過 Post api，建立到資料庫，
            // 並跳到 MainActivity2 (代表備忘錄主頁)
            send.setOnClickListener {
                val postTitle = title.text.toString()
                println("postTitle-------${postTitle}")
                val postContent = content.text.toString()
                println("postContent-----${postContent}")

                val clientPost = OkHttpClient()
                val responseBody = FormBody.Builder()
                    .add("title", "${postTitle}")
                    .add("content", "${postContent}")
                    .build()
                val requestPost = Request.Builder()
                    .url("http://192.168.206.74/app/insert.php")
                    .post(responseBody)
                    .build()
                clientPost.newCall(requestPost).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        println("POST--------${responseBody}")
                    }
                })


                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
        else{
            // 當 button 按下時，將資料透過 Post api，建立到資料庫，
            // 並跳到 MainActivity2 (代表備忘錄主頁)
            send.setOnClickListener {

                val postTitle = title.text.toString()
                println("updateTitle-------${postTitle}")
                val postContent = content.text.toString()
                println("updateContent-----${postContent}")

                val postno = gridId
                println("gridId = " + gridId)

                val clientPost = OkHttpClient()
                val responseBody = FormBody.Builder()
                    .add("title", "${postTitle}")
                    .add("content", "${postContent}")
                    .add("no", "${postno}")
                    .build()
                val requestPost = Request.Builder()
                    .url("http://192.168.206.74/app/put.php")
                    .post(responseBody)
                    .build()
                clientPost.newCall(requestPost).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        println("Update--------${responseBody}")
                    }
                })
                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.getItemId()
        if (id == R.id.delete) {
            //刪除指令放放這裡;
            println("gridId = " + gridId)
            val clientPost = OkHttpClient()
            val num = gridId
            val responseBody = FormBody.Builder()
                .add("no", "${num}")
                .build()
            val requestPost = Request.Builder()
                .url("http://192.168.206.74/app/delete.php")
                .post(responseBody)
                .build()
            clientPost.newCall(requestPost).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    println("Delete--------${responseBody}")
                }
            })
            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            return true;
        } else if (id == R.id.about) {
            Toast.makeText(this, "這是一個有登入功能的備忘錄", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
}