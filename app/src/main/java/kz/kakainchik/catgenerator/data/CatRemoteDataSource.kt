package kz.kakainchik.catgenerator.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kz.kakainchik.catgenerator.util.ApiResponse
import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val client: HttpClient
) {
    suspend fun askCat(query: CatQuery): CatPhoto {
        return client.get(CAT_URL) {
            url {
                query.tag?.let { appendPathSegments(it) }
                query.description?.let { appendPathSegments(SAYS_SEGM, it) }
            }

            parameter("json", true)
            query.filter?.let { parameter("filter", it) }
            query.color?.let { parameter("color", it) }
            query.fontSize?.let { parameter("size", it) }
        }.body()
    }

    suspend fun loadCatImage(url: String): ByteArray {
        return client.get("$BASE_URL$url").body()
    }

    suspend fun askAllTags(): List<String> {
        return client.get("$API_URL/tags").body()
    }

    companion object {
        private const val BASE_URL = "https://cataas.com"
        private const val API_URL = "$BASE_URL/api"
        private const val CAT_URL = "$BASE_URL/cat"
        private const val SAYS_SEGM = "says"
    }
}