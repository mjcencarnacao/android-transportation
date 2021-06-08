package com.mjceo.transportation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mjceo.transportation.R

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val args: WebViewFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.wvWebSearch)
        webView.settings.javaScriptEnabled = true
        args.requestUrl?.let { webView.loadUrl(it) }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                webView.loadUrl(url).also { return false }
            }
        }
    }
}