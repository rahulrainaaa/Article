package com.jet2traveltech.article.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jet2traveltech.article.adapter.ArticleRecyclerViewAdapter
import com.jet2traveltech.article.databinding.FragmentArticleListBinding
import com.jet2traveltech.article.ui.viewModel.ArticleViewModel

/**
 * Fragment class to show list of Article(s) in recyclerView with pagination.
 * Create FragmentArticleListBinding and ArticleViewModel mandatory for the Fragment.
 * 1. Upon the Fragment View created set RecyclerView with adapter, Layout and decoration patter.
 * 2. Observe on Article LiveData in ViewModel to get notified with data update(s).
 * 3. Set RecyclerView ScrollListener to screen its scroll to last item for next page loading.
 * 4. Used Footer Cell in RecyclerView to indicate progressBar of API call for next page.
 * 5. SwipeRefreshLayout used to refresh the list with updated data and remove all older cached ones.
 * 6. Observer on articleEOF ListData object to confirm the end of record(s) in ArticleWebRepository.
 */
class ArticleListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding  // ViewBinding object to access layout element(s).
    private lateinit var viewModel: ArticleViewModel          // ViewModel class to handler data and observer(s).
    private val maxPageSize = 10                              // Page size limit.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, instsance: Bundle?): View? {

        // Create ViewBinding object from fragment_article_list layout file.
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        // Create ViewModel for the fragment.
        viewModel = ViewModelProvider(this).get(ArticleViewModel::class.java).initialize(requireActivity().application)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        SavedStateViewModelFactory(requireActivity().application, requireActivity())

        // Prepare RecyclerView with adapter, LayoutManager and decoration.
        val adapter = ArticleRecyclerViewAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
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

            // Hide footer after every web response from server.
            adapter.isFooterVisible.value = false

            // Check if article(s) present in DB cache.
            if (articles.isEmpty()) {
                // Nothing in cache. Then fetch first page of Article(s).
                binding.swipeRefreshLayout.isRefreshing = true
                viewModel.fetchArticles(1, maxPageSize)
            } else {
                // Already present in DB cache. Fetch from Article DBRepository and publish data.
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.setPage(articles)
                // Scroll to current position in case of screen orientation change.
                binding.recyclerView.scrollToPosition(viewModel.currentRecyclerPosition)
            }
        })

        // Scroll listener to fetch next page upon scrolling down to last cell.
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // Fetch the last visible recycler view cell.
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                viewModel.currentRecyclerPosition = lastVisiblePosition

                // Check if the last visible cell is actually last element in the article list.
                // Also check if there is EOF or still data pending in Article WebRepository to fetch.
                if (lastVisiblePosition == (adapter.articles.size - 1) && viewModel.isArticleReachedEof() == false) {
                    // Make recycler view footer visible to show progress there to indicate next page fetching from web repository.
                    adapter.isFooterVisible.value = true
                    // Calculate the next page number and call for data.
                    val page = (adapter.articles.size / maxPageSize) + 1
                    viewModel.fetchArticles(page, maxPageSize)
                }
            }
        })

        // Adding SwipeDownToRefresh event to remove all articles and fetch first page of article list.
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.fetchArticles(1, maxPageSize) }

        // Observer on Live data to get notified when EOF has reached in ArticleWebRepository.
        viewModel.articleWebRepository.articleEOF.observe(viewLifecycleOwner, { eof ->
            if (eof) {
                // Upon reaching EOF in Article Web Repository disable the footer progressbar cell visibility.
                (binding.recyclerView.adapter as ArticleRecyclerViewAdapter).isFooterVisible.value = false
                // Dismiss if swipe refresh is working.
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}