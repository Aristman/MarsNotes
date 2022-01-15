package ru.marslab.marsnotes.ui.auth

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import ru.marslab.marslablib.FragmentBinding
import ru.marslab.marsnotes.R
import ru.marslab.marsnotes.databinding.FragmentGoogleAuthBinding

class GoogleAuthFragment : FragmentBinding<FragmentGoogleAuthBinding>(FragmentGoogleAuthBinding::inflate) {
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), signInOptions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonGoogleSingIn.setOnClickListener {
            googleSignInClient?.signInIntent?.let { intent ->
                registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult().apply {
                        createIntent(requireContext(), intent)
                    }
                ) { activityResult ->
                    if (activityResult.resultCode == AUTH_REQUEST_CODE) {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                        try {
                            val result = task.result
                        } catch (e: Exception) {
                            Snackbar.make(
                                requireContext(),
                                requireView(),
                                getString(R.string.no_google_auth),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "GoogleAuthFragment"
        private const val AUTH_REQUEST_CODE = 1
        fun newInstance(): GoogleAuthFragment {
            return GoogleAuthFragment()
        }
    }
}
