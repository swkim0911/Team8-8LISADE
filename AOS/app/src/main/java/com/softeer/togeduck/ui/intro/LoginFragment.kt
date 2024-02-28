package com.softeer.togeduck.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.ViewModelLifecycle

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.run {
            registerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_intro_login_to_intro_register)
            }
            loginBtn.setOnClickListener {
                loginViewModel.saveSessionId()
                findNavController().navigate(R.id.action_intro_login_to_mainActivity)
            }
        }
    }

    private fun init() {
        binding.vm = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

}