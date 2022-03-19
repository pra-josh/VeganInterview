package com.pra.myapplication.UI.fragment.titlescreen

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pra.myapplication.data.model.Content
import com.pra.myapplication.data.model.TitleResponseModel
import com.pra.myapplication.data.model.api.WebApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TitleViewModel (private val app: Context) : ViewModel() {
    private var mTitleList: MutableList<Content> = ArrayList()

    val titleObserver = MutableLiveData<List<Content>>()

    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var webApiClient: WebApiClient? = WebApiClient(app)


    init {

    }


    public fun fetchtitle(boolean: Boolean) {
        if(boolean) {
            loading.value = true
        }
        var responsecall: Call<TitleResponseModel> = WebApiClient(app).getTitle()
        responsecall.enqueue(object : Callback<TitleResponseModel> {
            override fun onResponse(
                call: Call<TitleResponseModel>,
                response: Response<TitleResponseModel>
            ) {
                loading.value = false
                when {
                    response.isSuccessful -> {
                        val titleResponseModel = response.body()!!
                        mTitleList = titleResponseModel.content as MutableList<Content>
                        titleObserver.value = mTitleList
                    }
                    response.errorBody() != null -> {
                        countryLoadError.value = true
                      /*  Toast.makeText(mActivity, "Error in Response Body", Toast.LENGTH_SHORT)
                            .show()*/
                    }
                    else -> {
                        countryLoadError.value = true
                     /*   Toast.makeText(mActivity, "Something in went wrong", Toast.LENGTH_SHORT)
                            .show()*/
                    }
                }
            }

            override fun onFailure(call: Call<TitleResponseModel>, t: Throwable) {
                loading.value = false
                countryLoadError.value = true
            }
        })
    }


    override fun onCleared() {
        super.onCleared()
    }

}