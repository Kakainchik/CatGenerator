package kz.kakainchik.catgenerator.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatPhoto(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String
    )