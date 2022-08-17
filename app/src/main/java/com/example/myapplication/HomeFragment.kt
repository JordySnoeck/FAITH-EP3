package com.example.myapplication

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.core.view.isVisible
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.domain.AuthTokenSecureFile
import com.example.myapplication.domain.SecureFileHandle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // Login/logout-related properties
    private lateinit var account: Auth0
    private var cachedCredentials: Credentials? = null
    private var cachedUserProfile: UserProfile? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.buttonLogin.setOnClickListener { login() }
        binding.buttonLogout.setOnClickListener { logout() }

        checkIfToken()
        showUserProfile()


        return view


    }

    private fun checkIfToken(){
        val token = CredentialsManager.getAccessToken(requireContext())
        if(token != null){
            Log.d("TOKEN","YESSSSSSSSSSSSSS")
        }
        else {
            val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply(){
                putString("email", null)
            }.apply()
            Log.d("NOTOKEN","DAMNNNNNNN")
            Toast.makeText(context, "Token doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun login() {
        WebAuthProvider
            .login(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .withScope(getString(R.string.login_scopes))
            .withAudience(getString(R.string.login_audience, getString(R.string.com_auth0_domain)))
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {

                override fun onFailure(exception: AuthenticationException) {
                    showSnackBar(getString(R.string.login_failure_message, exception.getCode()))
                }

                override fun onSuccess(credentials: Credentials) {
                    cachedCredentials = credentials
                    CredentialsManager.saveCredentials(requireContext(), credentials)
                    checkIfToken()
                    updateUI()
                    showUserProfile()

                }
            })
    }

    private fun logout() {
        WebAuthProvider
            .logout(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .start(requireContext(), object : Callback<Void?, AuthenticationException> {

                override fun onFailure(exception: AuthenticationException) {
                    updateUI()
                    showSnackBar(getString(R.string.general_failure_with_exception_code,
                        exception.getCode()))
                }

                override fun onSuccess(payload: Void?) {
                    cachedCredentials = null
                    cachedUserProfile = null
                    CredentialsManager.saveCredentials(requireContext(),cachedCredentials)
                    updateUI()
                }

            })
    }

    private fun showUserProfile() {
        // Guard against showing the profile when no user is logged in
        val client = AuthenticationAPIClient(account)
        CredentialsManager.getAccessToken(requireContext())?.let {
            client
                .userInfo(it)
                .start(object : Callback<UserProfile, AuthenticationException> {

                    override fun onFailure(exception: AuthenticationException) {
                        showSnackBar(getString(R.string.general_failure_with_exception_code,
                            exception.getCode()))
                    }

                    override fun onSuccess(profile: UserProfile) {
                        cachedUserProfile = profile
                        updateUI()
                    }

                })
        }
    }

    private fun updateUI() {
        val token = CredentialsManager.getAccessToken(requireContext())
        val isLoggedIn = token != null
        binding.textviewTitle.text = if (isLoggedIn) {
            getString(R.string.logged_in_title)
        } else {
            getString(R.string.logged_out_title)
        }
        binding.buttonLogin.isEnabled = !isLoggedIn
        binding.buttonLogout.isEnabled = isLoggedIn

        binding.textviewUserProfile.isVisible = isLoggedIn

        val userName = cachedUserProfile?.name ?: ""
        val userEmail = cachedUserProfile?.email ?: ""
        binding.textviewUserProfile.text = getString(R.string.user_profile, userName, userEmail)

        if(userEmail == ""){
            val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply(){
                putString("email", null)
            }.apply()
        }else {
            val sharedPreferences = this.requireActivity()
                .getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply() {
                putString("email", userEmail)
            }.apply()
        }

    }

    private fun showSnackBar(text: String) {
        Snackbar
            .make(
                binding.root,
                text,
                Snackbar.LENGTH_LONG
            ).show()
    }



}