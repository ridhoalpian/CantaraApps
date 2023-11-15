package com.example.cantaraapps.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cantaraapps.R
import com.example.cantaraapps.activity.LoginActivity
import com.example.cantaraapps.activity.RiwayatTransaksiActivity
import com.example.cantaraapps.activity.SettingAkunActivity
import com.example.cantaraapps.activity.UbahPassActivity
import com.example.cantaraapps.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSetAkun.setOnClickListener {
            val intent = Intent(requireActivity(), SettingAkunActivity::class.java)
            startActivity(intent)
        }

        binding.btnRiwayatTran.setOnClickListener {
            val intent = Intent(requireActivity(), RiwayatTransaksiActivity::class.java)
            startActivity(intent)
        }

        binding.btnUbahPass.setOnClickListener {
            val intent = Intent(requireActivity(), UbahPassActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}