package com.example.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappmvvm.R
import com.example.newsappmvvm.adapter.NewsAdapter
import com.example.newsappmvvm.ui.NewsActivity
import com.example.newsappmvvm.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_save_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class SaveNewsFragment : Fragment(R.layout.fragment_save_news){
     lateinit var viewModel: NewsViewModel
     lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        adapter = NewsAdapter()
        rvSaveNews.adapter = adapter
        rvSaveNews.layoutManager = LinearLayoutManager(activity)
        adapter.onItemClickListener =  {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(SaveNewsFragmentDirections.actionSavedNewsFragmentToArticleNewsFragment(it))
        }
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position]
                viewModel.deleteAritcle(article)
               val snackbar = Snackbar.make(view,"Deleted Successfully",Snackbar.LENGTH_SHORT)
                   snackbar.setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    snackbar.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply { attachToRecyclerView(rvSaveNews) }


        viewModel.getSaveNews().observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })

    }
}