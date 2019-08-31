package com.sevenpeakssoftware.mitul.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sevenpeakssoftware.mitul.domain.Content

//Database Data
@Entity
data class DatabaseContent constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val dateTime: String,
    val ingress: String,
    val image: String)

//Convert to Domain Data
fun List<DatabaseContent>.asDomainModel(): List<Content> {
    return map {
        Content(
            id = it.id,
            title = it.title,
            dateTime = it.dateTime,
            ingress = it.ingress,
            image = it.image)
    }
}