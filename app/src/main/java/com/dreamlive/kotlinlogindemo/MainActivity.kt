package com.dreamlive.kotlinlogindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import cn.jowan.logintest.presenter.LoginPresenterImpl
import cn.pedant.SweetAlert.SweetAlertDialog
import com.dreamlive.kotlinlogindemo.bean.LoginResponse
import com.dreamlive.kotlinlogindemo.bean.RegisterResponse
import com.dreamlive.kotlinlogindemo.presenter.LoginPresenter
import com.dreamlive.kotlinlogindemo.view.LoginView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), View.OnClickListener, LoginView {
    var loginPresenter: LoginPresenter? = null
    var dialog: SweetAlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginPresenter = LoginPresenterImpl(this)
        login.setOnClickListener(this)
        register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login ->
                if (checkContent(true)) {
                    dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("正在登陆。。。")
                    dialog?.setCancelable(false)
                    dialog?.show()
                    loginPresenter?.login(username.text.toString(), password.text.toString())
                }
            R.id.register ->
                if (checkContent(false)) {
                    dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("正在注册。。。")
                    dialog?.setCancelable(false)
                    dialog?.show()
                    loginPresenter?.register(username.text.toString(), password.text.toString(), email.text.toString())
                }
        }
    }

    /**
     * 判断
     */
    private fun checkContent(login: Boolean): Boolean {
        username.error = null
        password.error = null
        email.error = null

        var cancel = false
        var focusView: View? = null

        if (!login) {
            if (TextUtils.isEmpty(email.text.toString())) {
                email.error = "Email不能为空"
                focusView = email
                cancel = true
            } else if (!isEmail(email.text.toString())) {
                email.error = "Email格式不正确"
                focusView = email
                cancel = true
            }
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            password.error = "密码不能为空"
            focusView = password
            cancel = true
        } else if (password.text.length < 6) {
            password.error = "密码长度不能小于6位"
            focusView = password
            cancel = true
        }

        if (TextUtils.isEmpty(username.text.toString())) {
            username.error = "用户名不能为空"
            focusView = username
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus()
            }
        } else {
            return true
        }
        return false
    }

    /**
     * 判断email地址
     * @param email
     * @return
     */
    fun isEmail(email: String?): Boolean {
        if (email == null) {
            return false
        }
        val regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(email).matches()
    }

    override fun loginSuccess(result: LoginResponse) {
        dialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
        dialog?.titleText = result.msg
    }

    override fun loginFailed(message: String?) {
        dialog?.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        dialog?.titleText = message
    }

    override fun registerSuccess(result: RegisterResponse) {
        dialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
        dialog?.titleText = result.msg
    }

    override fun registerFailed(message: String?) {
        dialog?.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        dialog?.titleText = message
    }
}
