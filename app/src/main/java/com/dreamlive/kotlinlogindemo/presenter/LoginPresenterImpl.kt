package cn.jowan.logintest.presenter


import com.dreamlive.kotlinlogindemo.view.LoginView
import com.dreamlive.kotlinlogindemo.bean.LoginResponse
import com.dreamlive.kotlinlogindemo.bean.RegisterResponse
import com.dreamlive.kotlinlogindemo.model.LoginModel
import com.dreamlive.kotlinlogindemo.model.LoginModelImpl
import com.dreamlive.kotlinlogindemo.presenter.LoginPresenter

/**
 * Created by pc on 2017/3/8.
 */
class LoginPresenterImpl(val loginView: LoginView) : LoginPresenter, LoginPresenter.onLoginListener, LoginPresenter.onRegisterListener {

    val mLoginModel: LoginModel

    init {
        mLoginModel = LoginModelImpl()
    }

    override fun login(username: String, password: String) {
        mLoginModel.login(this, username, password)
    }

    override fun loginSuccess(result: LoginResponse) {
        loginView.loginSuccess(result)
    }

    override fun loginFailed(message: String?) {
        loginView.loginFailed(message)
    }

    override fun register(username: String, password: String, email: String) {
        mLoginModel.register(this, username, password, email)
    }

    override fun registerSuccess(result: RegisterResponse) {
        loginView.registerSuccess(result)
    }

    override fun registerFailed(message: String?) {
        loginView.registerFailed(message)
    }
}