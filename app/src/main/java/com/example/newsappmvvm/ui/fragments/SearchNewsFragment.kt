package com.example.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.R
import com.example.newsappmvvm.adapter.NewsAdapter
import com.example.newsappmvvm.ui.NewsActivity
import com.example.newsappmvvm.ulti.Resouce
import com.example.newsappmvvm.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val viewModel: NewsViewModel = (activity as NewsActivity).viewModel
        val adapter = NewsAdapter()
        rvSearchNews.adapter = adapter
        rvSearchNews.layoutManager = LinearLayoutManager(activity)
        adapter.onItemClickListener = {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController()
                .navigate(SearchNewsFragmentDirections
                    .actionSearchNewsFragmentToArticleNewsFragment(it)
                )
        }

        var job: Job? = null
        edtSearchNews.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getSearchNews(editable.toString())
                    }
                }

            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resouce.Success -> {
                    response?.data?.let {
                        adapter.differ.submitList(it.articles)
                    }
                }
                is Resouce.Error -> {
                    Toast.makeText(activity,response.message!!,Toast.LENGTH_LONG).show()
                }
             }
        })


    }
}