package com.jet2traveltech.article.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jet2traveltech.article.databinding.CellArticleRecyclerViewBinding
import com.jet2traveltech.article.model.Article
import java.text.DecimalFormat
import kotlin.math.pow

class WordListAdapter(val context: Context) : RecyclerView.Adapter<WordListAdapter.ArticleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words: List<Article> = emptyList()

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.setData(words[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ArticleViewHolder(CellArticleRecyclerViewBinding.inflate(inflater, parent, false))

    fun setPage(page: List<Article>) {
        words = page
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(val binding: CellArticleRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(article: Article) {
            binding.txtUserName.text = article.user_name ?: "N/A"
            binding.txtUserDesignation.text = article.user_designation ?: "N/A"
            binding.txtArticleContent.text = article.post_content ?: "N/A"
            binding.txtArticleTitle.text = article.media_title ?: "N/A"
            binding.txtLikes.text = formatCount(article.post_likes!!) + " Likes"
            binding.txtComments.text = formatCount(article.post_comments!!) + " Comments"

            Glide.with(context)
                    .load(article.user_avatar)
                    .into(binding.ivUserPhoto)

            Glide.with(context)
                    .load(article.media_image)
                    .into(binding.ivArticleImage)


        }

        fun formatCount(number: Long): String? {
            val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
            val value = Math.floor(Math.log10(number.toDouble())).toInt()
            val base = value / 3
            return if (value >= 3 && base < suffix.size) {
                DecimalFormat("#0.0").format(number / 10.0.pow(base * 3.toDouble())) + suffix[base]
            } else {
                DecimalFormat("#,##0").format(number)
            }
        }
    }
}