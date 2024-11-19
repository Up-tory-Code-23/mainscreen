package com.example.uptory

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var isHeartFilled = false // 하트의 상태를 추적하는 변수
    private var selectedButton: Button? = null // 클릭된 버튼 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recommendButton = findViewById<Button>(R.id.recommend_button)
        val popularButton = findViewById<Button>(R.id.popular_button)
        val followingButton = findViewById<Button>(R.id.following_button)
        val liveButton = findViewById<Button>(R.id.live_button)
        val recentButton = findViewById<Button>(R.id.recent_button)

        // 하트 클릭 시 채움
        val heartImageView: ImageView = findViewById(R.id.heart2)
        heartImageView.setOnClickListener {
            // 하트 상태를 반전
            isHeartFilled = !isHeartFilled
            // 하트 색상 변경
            if (isHeartFilled) {
                heartImageView.setColorFilter(Color.RED) // 하트를 빨간색으로 채움
            } else {
                heartImageView.clearColorFilter() // 색상 필터 제거
            }
        }

        // image_container1 클릭 시 VideosActivity로 이동
        val imageContainer: LinearLayout = findViewById(R.id.image_container1)
        imageContainer.setOnClickListener {
            val intent = Intent(this, VideosActivity::class.java)
            startActivity(intent)
        }

        // 버튼 리스트로 관리
        val buttons = listOf(recommendButton, popularButton, followingButton, liveButton, recentButton)
        for (button in buttons) {
            button.setOnClickListener {
                // 이전에 선택된 버튼이 있으면 선택 해제
                selectedButton?.isSelected = false

                // 현재 클릭된 버튼을 선택된 상태로 변경
                button.isSelected = true

                // 현재 클릭된 버튼을 저장
                selectedButton = button
            }
        }

        val videoView = findViewById<VideoView>(R.id.videoView)
        setupVideoView(videoView, R.raw.videoview)  // videoview.mp4 파일

        val videoView6 = findViewById<VideoView>(R.id.videoView6)
        setupVideoView(videoView6, R.raw.videoview6)

    }

    fun setupVideoView(videoView: VideoView, rawVideoId: Int) {
        // 비디오 URI 설정
        val videoUri = Uri.parse("android.resource://$packageName/$rawVideoId")
        videoView.setVideoURI(videoUri)

        // MediaController 추가 (재생/일시정지 등 제어)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // 비디오가 준비되면 자동 재생 시작
        videoView.setOnPreparedListener {
            videoView.start() // 비디오 자동 재생
        }

        // 비디오가 끝났을 때 자동으로 반복 재생되도록 설정
        videoView.setOnCompletionListener {
            videoView.start() // 비디오 끝나면 다시 재생
        }

        // 비디오를 클릭했을 때 재생/일시정지 동작 추가
        videoView.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause() // 비디오 일시정지
            } else {
                videoView.start() // 비디오 재생
            }
        }
    }

    // enableEdgeToEdge() 함수 추가
    private fun enableEdgeToEdge() {
        // 이 메서드에서 Edge-to-Edge 설정
        window.decorView.systemUiVisibility = (android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
    }
}
