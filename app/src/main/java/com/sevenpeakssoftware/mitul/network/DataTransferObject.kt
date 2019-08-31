package com.sevenpeakssoftware.mitul.network

import com.sevenpeakssoftware.mitul.database.DatabaseContent
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkContentContainer(val content: List<NetworkContent>)

@JsonClass(generateAdapter = true)
data class NetworkContent(val id: Int,
                   val title: String,
                   val dateTime: String,
                   val ingress: String,
                   val image: String)

fun NetworkContentContainer.asDatabaseModel(): Array<DatabaseContent> {
    return content.map {
        DatabaseContent(
            id = it.id,
            title = it.title,
            dateTime = it.dateTime,
            ingress = it.ingress,
            image = it.image)
    }.toTypedArray()
}