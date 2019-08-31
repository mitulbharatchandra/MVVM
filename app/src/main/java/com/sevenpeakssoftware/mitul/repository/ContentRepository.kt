package com.sevenpeakssoftware.mitul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sevenpeakssoftware.mitul.database.ContentDatabase
import com.sevenpeakssoftware.mitul.database.asDomainModel
import com.sevenpeakssoftware.mitul.domain.Content
import com.sevenpeakssoftware.mitul.network.Network
import com.sevenpeakssoftware.mitul.network.NetworkContentContainer
import com.sevenpeakssoftware.mitul.network.asDatabaseModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class ContentRepository (private val database: ContentDatabase){

    val content: LiveData<List<Content>> =
        Transformations.map(database.contentDao.getContents()) {
            it.asDomainModel()
        }

    fun getRepositories(): Observable<NetworkContentContainer> {
        return Network.contents.getContentlist().flatMap {
            return@flatMap saveRepositories(it)
                .toSingleDefault(it)
                .toObservable()
        }
    }

    private fun saveRepositories(network: NetworkContentContainer): Completable {
        database.contentDao.insertAll(*network.asDatabaseModel())
        return Single.just(1).toCompletable()
    }
}