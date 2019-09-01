package org.wit.autofile.views.login


import org.wit.autofile.views.BasePresenter
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.autofile.models.firebase.AutofileFireStore
import org.wit.autofile.views.BaseView
import org.wit.autofile.views.VIEW
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class LoginPresenter(view: BaseView) : BasePresenter(view) {

  var auth: FirebaseAuth = FirebaseAuth.getInstance()
  var fireStore: AutofileFireStore? = null

  init {
    if (app.autofiles is AutofileFireStore) {
      fireStore = app.autofiles as AutofileFireStore
    }
  }

  fun doLogin(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        if (fireStore != null) {
          fireStore!!.fetchAutofiles {
            view?.hideProgress()
            view?.navigateTo(VIEW.LIST)
            view?.finish()
          }
        } else {
          view?.hideProgress()
          view?.navigateTo(VIEW.LIST)
          view?.finish()
        }
      } else {
        view?.hideProgress()
        view?.toast("Sign in Failed: ${task.exception?.message}")
      }
    }
  }


  fun doGoogleLogin(acct: GoogleSignInAccount) {
    view?.showProgress()
    val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        if (fireStore != null) {
          fireStore!!.fetchAutofiles {
            view?.hideProgress()
            view?.navigateTo(VIEW.LIST)
            view?.finish()
          }
        } else {
          view?.hideProgress()
          view?.navigateTo(VIEW.LIST)
          view?.finish()
        }
      } else {
        view?.hideProgress()
        view?.toast("Sign in Failed: ${task.exception?.message}")
      }
    }
  }


  fun doSignUp(email: String, password: String) {
    view?.showProgress()
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        view?.hideProgress()
        view?.navigateTo(VIEW.LIST)
      } else {
        view?.hideProgress()
        view?.toast("Sign Up Failed: ${task.exception?.message}")
      }
    }
  }

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.autofiles.clear()
    view?.navigateTo(VIEW.LOGIN)
    view?.finish()
  }

}
