package com.example.uptory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


// 데이터 클래스
data class MyComment(val userName: String, val commentText: String)

class VideoAdapter(
    private val comments: MutableList<MyComment> // 댓글을 동적으로 추가/삭제할 수 있도록 MutableList로 설정
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_COMMENT = 1
    }

    // getItemViewType에서 모든 아이템을 댓글로 처리
    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_COMMENT
    }

    // onCreateViewHolder에서 항상 item_comment를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    // onBindViewHolder에서 댓글 데이터를 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentViewHolder) {
            val comment = comments[position] // position에 맞는 댓글 데이터
            holder.userName.text = comment.userName
            holder.commentText.text = comment.commentText
        }
    }

    // getItemCount에서 댓글의 수만큼 아이템 수 반환
    override fun getItemCount(): Int = comments.size

    // 댓글 추가 메서드
    fun addComment(comment: MyComment) {
        comments.add(comment)  // 댓글 리스트에 새로운 댓글 추가
        notifyItemInserted(comments.size - 1) // 새로운 아이템이 추가되었음을 RecyclerView에 알려줌
    }

    // CommentViewHolder: 댓글 항목을 표시하는 ViewHolder
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.textUserName)
        val commentText: TextView = itemView.findViewById(R.id.textComment)
    }
}

