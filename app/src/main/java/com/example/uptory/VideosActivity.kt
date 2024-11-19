package com.example.uptory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.w3c.dom.Comment
import android.widget.Toast

class VideosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var commentEditText: EditText
    private lateinit var postButton: ImageView  // ImageView로 변경
    private var comments = mutableListOf<MyComment>() // 댓글 리스트 (MyComment 사용)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videos)  // Activity 레이아웃 설정

        // RecyclerView 설정
        recyclerView = findViewById(R.id.recyclerViewComments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RecyclerView 어댑터 설정
        videoAdapter = VideoAdapter(comments)
        recyclerView.adapter = videoAdapter

        // ViewPager2 설정
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val imageList = listOf(R.drawable.picture1, R.drawable.picture2, R.drawable.picture3)
        val viewPagerAdapter = ViewPagerAdapter(this, imageList)
        viewPager.adapter = viewPagerAdapter

        // 댓글 입력 EditText
        commentEditText = findViewById(R.id.editTextComment)
        postButton = findViewById(R.id.buttonSubmitComment)  // ImageView로 연결

        // 댓글 업로드 ImageView 클릭 리스너
        postButton.setOnClickListener {
            val userName = "User" // 예시로 "User"라는 이름 사용
            val commentText = commentEditText.text.toString().trim()

            if (commentText.isNotEmpty()) {
                // 새로운 댓글 객체 생성
                val newComment = MyComment(userName, commentText)

                // 댓글 리스트에 새로운 댓글 추가
                videoAdapter.addComment(newComment)

                // 댓글 입력창 비우기
                commentEditText.text.clear()

                // 스크롤을 가장 아래로 이동시켜서 새 댓글이 보이도록
                recyclerView.scrollToPosition(comments.size - 1)
            } else {
                // 댓글이 비었을 때 안내 메시지
                Toast.makeText(this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 이전 버튼 클릭 리스너 (메인 화면으로 돌아가기)
        val previousButton = findViewById<ImageView>(R.id.previous)
        previousButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // 현재 Activity 종료
        }

        // scrap ImageView 클릭 리스너 (스크랩 기능)
        val scrapImageView = findViewById<ImageView>(R.id.scrap)
        var isScrap = false
        scrapImageView.setOnClickListener {
            if (isScrap) {
                scrapImageView.setImageResource(R.drawable.scrap_1)
            } else {
                scrapImageView.setImageResource(R.drawable.scrap_2)
            }
            isScrap = !isScrap
        }
    }
}








