package org.wit.autofile.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.wit.autofile.views.BaseView
import org.wit.autofile.R

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class LoginView : BaseView() {

  lateinit var presenter: LoginPresenter
  private lateinit var googleSignInClient: GoogleSignInClient
  var auth: FirebaseAuth = FirebaseAuth.getInstance()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    progressBar.visibility = View.GONE
    //super.init(toolbar, false)

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(this, gso)


    btn_register.setOnClickListener {
      val email = et_email.text.toString()
      val password = et_password.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doSignUp(email,password)
      }
    }


    btn_submit.setOnClickListener {
      val email = et_email.text.toString()
      val password = et_password.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doLogin(email,password)
      }
    }

    btn_google_login.setOnClickListener {
      doSignIn();
    }
  }

  private fun doSignIn() {
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
  }


  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
  }


  public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      val task = GoogleSignIn.getSignedInAccountFromIntent(data)
      try {
        // Google Sign In was successful, authenticate with Firebase
        val account = task.getResult(ApiException::class.java)
        presenter.doGoogleLogin(account!!)
      } catch (e: ApiException) {
        // Google Sign In failed
        Log.d(TAG, "Google sign in failed: $e")
      }
    }
  }

  companion object {
    private const val TAG = "Bloq"
    private const val RC_SIGN_IN = 9001
  }
}
