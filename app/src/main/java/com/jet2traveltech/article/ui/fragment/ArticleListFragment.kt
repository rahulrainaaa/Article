package com.jet2traveltech.article.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jet2traveltech.article.adapter.WordListAdapter
import com.jet2traveltech.article.databinding.FragmentArticleListBinding
import com.jet2traveltech.article.ui.viewModel.ArticleViewModel
import com.jet2traveltech.article.ui.viewModel.ArticleViewModelFactory

class ArticleListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: ArticleViewModel
    private val maxPageSize = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        instsance: Bundle?
    ): View? {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        viewModel = ArticleViewModelFactory(requireActivity().application).create(ArticleViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WordListAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // RecyclerView item decoration for spacing and padding.
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.left = 16
                outRect.right = 16
                outRect.top = 16
                outRect.bottom = 16
            }
        })

        // Observe article LiveData for data change then notify adapter to update on recycler view.
        viewModel.allArticles.observe(viewLifecycleOwner, { articles ->

            adapter.isFooterVisible.value = false
            if (articles.isEmpty()) {  // No Data cached in DB.
                binding.swipeRefreshLayout.isRefreshing = true
                viewModel.fetchArticles(1, maxPageSize)
            } else {
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.setPage(articles)
            }
        })

        // Scroll listener to fetch next page upon scrolling down to last cell.
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                Log.d("bbbbbbbbbbbbbbb", "Last Visible Item Index = $lastVisiblePosition")
                if (lastVisiblePosition == (adapter.articles.size - 1) && viewModel.isArticleReachedEof() == false) {
                    val page = (adapter.articles.size / maxPageSize) + 1
                    adapter.isFooterVisible.value = true
                    viewModel.fetchArticles(page, maxPageSize)
                }
            }
        })

        // Adding SwipeDownToRefresh event to remove all articles and fetch first page of article list.
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchArticles(1, maxPageSize)
        }

        // Observer on Live data to conform when EOF has reached in ArticleWebRepository.
        viewModel.articleWebRepository.articleEOF.observe(viewLifecycleOwner, { eof ->
            if (eof) {
                (binding.recyclerView.adapter as WordListAdapter).isFooterVisible.value = false
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}