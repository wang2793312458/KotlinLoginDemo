package com.dreamlive.kotlinlogindemo.model

import com.dreamlive.kotlinlogindemo.presenter.LoginPresenter
import com.dreamlive.kotlinlogindemo.util.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by pc on 2017/3/8.
 */
class LoginModelImpl : LoginModel {

    var mOnLoginListener: LoginPresenter.onLoginListener? = null
    var mOnRegisterListener: LoginPresenter.onRegisterListener? = null

    override fun login(onLoginListener: LoginPresenter.onLoginListener, username: String, password: String) {
        if (mOnLoginListener == null) {
            mOnLoginListener = onLoginListener
        }
        RetrofitUtils.Companion.retrofitService
                .userLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            when (result.retCode) {
                                "200" ->
                                    mOnLoginListener?.loginSuccess(result)
                                else ->
                                    mOnLoginListener?.loginFailed(result.msg)
                            }
                        },
                        {
                            error ->
                            mOnLoginListener?.loginFailed(error.message)
                        })
    }

    override fun register(onRegisterListener: LoginPresenter.onRegisterListener, username: String, password: String, email: String) {
        if (mOnRegisterListener == null) {
            mOnRegisterListener = onRegisterListener
        }
        RetrofitUtils.Companion.retrofitService
                .userRegister(username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            when (result.retCode) {
                                "200" ->
                                    mOnRegisterListener?.registerSuccess(result)
                                else ->
                                    mOnRegisterListener?.registerFailed(result.msg)
                            }
                        },
                        {
                            error ->
                            mOnRegisterListener?.registerFailed(error.message)
                        })
    }

}