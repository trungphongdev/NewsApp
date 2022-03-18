package com.example.newsappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.example.newsappmvvm.R
import com.example.newsappmvvm.ResponseStatus
import com.example.newsappmvvm.adapter.NewsAdapter
import com.example.newsappmvvm.ui.NewsActivity
import com.example.newsappmvvm.ulti.Resouce
import com.example.newsappmvvm.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.coroutines.delay
import java.time.LocalDate

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news){
     lateinit var viewModel: NewsViewModel
     lateinit var adapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        adapter = NewsAdapter()
        rvBreakingNews.adapter = adapter
        rvBreakingNews.layoutManager = LinearLayoutManager(activity)
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resouce.Success -> {
                    progressBarNews.visibility = View.GONE
                    response.data?.let {
                        adapter.differ.submitList(it.articles)
                    }
                }
                is Resouce.Error -> {
                    Toast.makeText(activity,"Loading error network",Toast.LENGTH_SHORT).show()
                }
                is Resouce.Loading -> {
                    progressBarNews.visibility = View.VISIBLE
                    Toast.makeText(activity,"Loading...",Toast.LENGTH_SHORT).show()
                }
            }


        })
        adapter.onItemClickListener = {
            findNavController().navigate(BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleNewsFragment(it))
        }





}
}