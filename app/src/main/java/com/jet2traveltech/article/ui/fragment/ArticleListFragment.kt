package com.jet2traveltech.article.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jet2traveltech.article.adapter.WordListAdapter
import com.jet2traveltech.article.databinding.FragmentArticleListBinding
import com.jet2traveltech.article.ui.viewModel.WordViewModel
import com.jet2traveltech.article.ui.viewModel.WordViewModelFactory

class ArticleListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: WordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, instsance: Bundle?): View? {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        viewModel = WordViewModelFactory(requireActivity().application).create(WordViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WordListAdapter(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.allArticles.observe(viewLifecycleOwner, Observer { adapter.setPage(it) })
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

                outRect.left = 12
                outRect.right = 12
                outRect.top = 16
                outRect.bottom = 16
            }
        })

    }

}