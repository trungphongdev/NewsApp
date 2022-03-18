package com.example.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsappmvvm.R
import com.example.newsappmvvm.ui.NewsActivity
import com.example.newsappmvvm.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article_news.*

class ArticleNewsFragment : Fragment(R.layout.fragment_article_news){
     lateinit var viewModel: NewsViewModel
     val args: ArticleNewsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        webViewArticle.loadUrl(article.url)
        webViewArticle.webViewClient = WebViewClient()
        fabAddNews.setOnClickListener {
            viewModel.saveArticle(article)
            Toast.makeText(activity,"Saved News SuccessFully",Toast.LENGTH_SHORT).show()
        }


    }
}