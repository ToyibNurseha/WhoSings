package com.toyibnurseha.whosings.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.databinding.FragmentLoginBinding
import com.toyibnurseha.whosings.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        getLoggedUser()
        loginForm()
    }

    private fun loginForm() {
        viewModel.bind(binding.etUsername)
        viewModel.filledUser.observe(viewLifecycleOwner) {
            if (it.name.isNotEmpty()) {
                binding.btnLogin.run {
                    isClickable = true
                    alpha = 1f
                }
            } else {
                binding.btnLogin.run {
                    isClickable = false
                    alpha = 0.5f
                }
            }
        }
    }

    private fun onClickListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.insertUser()
            viewModel.getLoggedUser().removeObservers(viewLifecycleOwner)
            getLoggedUser()
        }
    }

    private fun getLoggedUser() {
        viewModel.getLoggedUser().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        GlobalScope.launch(Dispatchers.Main) {
                            delay(1500L)
                            showLoading(false)
                            findNavController().navigate(
                                R.id.action_loginFragment_to_homeFragment,
                                Bundle().apply {
                                    putParcelable("user", it.data)
                                },
                                NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                            )
                        }
                    } else {
                        showLoading(false)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.layoutLoading.visibility = View.VISIBLE
        }else {
            binding.layoutLoading.visibility = View.GONE
        }
    }

}