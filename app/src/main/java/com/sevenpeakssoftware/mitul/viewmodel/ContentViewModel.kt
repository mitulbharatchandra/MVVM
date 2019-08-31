package com.sevenpeakssoftware.mitul.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sevenpeakssoftware.mitul.database.getDatabase
import com.sevenpeakssoftware.mitul.domain.Content
import com.sevenpeakssoftware.mitul.network.NetworkContentContainer
import com.sevenpeakssoftware.mitul.repository.ContentRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/** it's safe to hold a reference to applications across rotation
 * since Application is never recreated during actiivty
 * or fragment lifecycle events.
 */
class ContentViewModel(application: Application): AndroidViewModel(application){

    private val database = getDatabase(application)
    private val repository = ContentRepository(database)
    private val compositeDisposable = CompositeDisposable()

    val isLoading = MutableLiveData<Boolean>()
    val contentList = repository.content

    fun getContent(): List<Content>?{
        return contentList.value
    }

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        loadRepositories()
    }


    private fun loadRepositories() {
        //isLoading.set(true)
        compositeDisposable.add(repository
            .getRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<NetworkContentContainer>() {

                override fun onError(e: Throwable) {
                    //if some error happens in our data layer our app will not crash, we will
                    // get error here
                    Log.d("SRD", "Error : ${e.message}")
                }

                override fun onNext(t: NetworkContentContainer) {
                    Log.d("SRD", "Loaded Successfully ...")
                }

                override fun onComplete() {
                    //sLoading.set(false)
                    Log.d("SRD", "Completed Successfully ...")
                }
            }))
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    /**
     * Factory for constructing ContentViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContentViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}