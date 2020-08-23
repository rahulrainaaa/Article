package com.jet2traveltech.article.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jet2traveltech.article.databinding.RvCellArticeBinding
import com.jet2traveltech.article.databinding.RvCellProgressBinding
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.utils.StringFormatUtil

/**
 * RecyclerView Adapter class to publish Article list from ArticleRepository.
 *
 * @param context Context to be passed for LayoutInflater object.
 */
class WordListAdapter(val context: Context, owner: LifecycleOwner) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var articles: List<Article> = emptyList()
    var isFooterVisible = MutableLiveData(false)

    init {
        isFooterVisible.observe(owner, { notifyDataSetChanged() })
    }

    /**
     * Enumerated types of cells being used in recycler view.
     */
    enum class CellType(val code: Int) {
        HEADER_CELL(1),
        ARTICLE_CELL(2),
        FOOTER_CEL(3)
    }

    override fun getItemViewType(position: Int): Int {
        return if (articles.size > position) CellType.ARTICLE_CELL.code
        else CellType.FOOTER_CEL.code
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CellType.ARTICLE_CELL.code)
            ArticleViewHolder(RvCellArticeBinding.inflate(inflater, parent, false))
        else FooterViewHolder(RvCellProgressBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ArticleViewHolder) holder.setData(articles[position], position)
    }

    override fun getItemCount(): Int {

        return when {
            articles.isEmpty() -> 0
            isFooterVisible.value == true -> articles.size + 1
            else -> articles.size
        }
    }

    /**
     * Update RecyclerView article list data upon change and notify.
     *
     * @param articles List of type Article data class.
     */
    fun setPage(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class to handle Footer Cell View of the RecyclerView.
     */
    private class FooterViewHolder(binding: RvCellProgressBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * ViewHolder class to handle Article Cell View in RecyclerView.
     */
    private inner class ArticleViewHolder(private val binding: RvCellArticeBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Method to update the given article data inside given cell.
         *
         * @param article Article data object to be published in this view.
         */
        fun setData(article: Article, position: Int) {
            binding.txtUserName.text = article.user_name ?: "N/A"
            binding.txtUserDesignation.text = article.user_designation ?: "N/A"
            binding.txtArticleContent.text = article.post_content ?: "N/A"
            binding.txtLikes.text = StringFormatUtil.formatCount(article.post_likes!!) + " Likes"
            binding.txtComments.text = StringFormatUtil.formatCount(article.post_comments!!) + " Comments"
            binding.txtTime.text = StringFormatUtil.getElapsedTime(article.user_createdAt!!)
            binding.txtArticleTitle.text = article.media_title ?: "N/A"

            if (article?.media_createdAt == null) {
                binding.txtArticleTitle.visibility = View.GONE
                binding.ivArticleImage.visibility = View.GONE
            } else {
                binding.txtArticleTitle.visibility = View.VISIBLE
                binding.ivArticleImage.visibility = View.VISIBLE
            }

            Glide.with(context)
                .load(article.user_avatar)
                .into(binding.ivUserPhoto)

            Glide.with(context)
                .load(article.media_image)
                .into(binding.ivArticleImage)

        }
    }
}