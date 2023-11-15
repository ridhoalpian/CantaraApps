package com.example.cantaraapps.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cantaraapps.activity.ChatActivity
import com.example.cantaraapps.activity.ViewProductActivity
import com.example.cantaraapps.databinding.FragmentHomeBinding


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.materialCardView.setOnClickListener {
            val intent = Intent(requireActivity(), ViewProductActivity::class.java)
            startActivity(intent)
        }

        binding.whatsapp.setOnClickListener {
            val intent = Intent(requireActivity(), ChatActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}