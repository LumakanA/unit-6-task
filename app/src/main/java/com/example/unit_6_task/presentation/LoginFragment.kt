package com.example.unit_6_task.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unit_6_task.R
import com.example.unit_6_task.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainFragment: MainFragment
    private lateinit var progressContainer: ProgressContainer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        mainFragment = MainFragment()
        progressContainer =
            ProgressContainer(requireContext())

        binding.progressButton.setOnClickListener {
            simulateLoginSuccess()
        }
        return binding.root
    }

    private fun simulateLoginSuccess() {
        val progressButton = binding.progressButton
        progressButton.isLoading = true

        Handler(Looper.getMainLooper()).postDelayed({
            progressButton.isLoading = false

            val loginSuccess = true

            if (loginSuccess) {
                val bundle = Bundle().apply {
                    putBoolean("loginSuccess", true)
                }
                mainFragment.arguments = bundle
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, mainFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                progressContainer.state =
                    ProgressContainer.State.Notice("Login failed. Please try again.")
            }
        }, 2000)
    }
}

