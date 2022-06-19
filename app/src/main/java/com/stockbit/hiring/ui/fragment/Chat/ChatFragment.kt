package com.stockbit.hiring.ui.fragment.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.test.stockbit.R
import com.test.stockbit.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private val binding: FragmentChatBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}