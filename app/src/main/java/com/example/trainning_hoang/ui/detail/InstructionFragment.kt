package com.example.trainning_hoang.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.trainning_hoang.databinding.FragmentInstructionBinding
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.util.Constant.BUNDLE_KEY

class InstructionFragment : Fragment() {

    private lateinit var binding: FragmentInstructionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val srcUrl = arguments!!.getParcelable<Result>(BUNDLE_KEY)!!.sourceUrl
        srcUrl?.let {
            binding.webView.apply {
                webViewClient = WebViewClient()
                loadUrl(it)
            }
        }

    }

}