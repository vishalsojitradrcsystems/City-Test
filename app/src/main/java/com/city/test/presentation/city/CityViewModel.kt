package com.city.test.presentation.city

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.city.test.domain.model.City
import com.city.test.domain.usecase.GetCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**To store & manage UI-related data in a lifecycle conscious way!
 * this class allows data to survive configuration changes such as screen rotation
 * by interacting with [GetCityUseCase]
 *
 * */
@HiltViewModel
class CityViewModel @Inject constructor(val getAlbumListUseCase: GetCityUseCase) :
    ViewModel() {

    val albumsReceivedLiveData = MutableLiveData<List<City>>()
    var cityList: List<City>? = null
    val isLoad = MutableLiveData(false)
    val isEmpty = MutableLiveData(false)
    var isFromSearch = false

    fun loadSearchCities(query: String) {
        isFromSearch = true
        isLoad.value = false
        viewModelScope.launch {
            albumsReceivedLiveData.value = cityList?.filter { it.getDisplayName().startsWith(query,true) }
            Log.e("query stop", System.currentTimeMillis().toString())
        }.invokeOnCompletion {
            isLoad.value = true
        }
    }


    fun loadAlbums() {
        isFromSearch = false
        if (cityList?.isNullOrEmpty() != false) {
            getAlbumListUseCase.execute(
                onSuccess = {
                    isLoad.value = true
                    cityList = it
                    albumsReceivedLiveData.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }else{
            albumsReceivedLiveData.value = cityList
        }
    }
}