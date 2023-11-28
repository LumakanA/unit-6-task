package com.example.unit_6_task.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unit_6_task.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val progressButton = binding.progressButton
        progressButton.setOnClickListener {
            progressButton.isLoading = true
            Handler(Looper.getMainLooper()).postDelayed({
                progressButton.isLoading = false
            }, 2000)
        }

        return binding.root
    }
}