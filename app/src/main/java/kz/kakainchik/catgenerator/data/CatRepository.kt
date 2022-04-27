package kz.kakainchik.catgenerator.data

import kz.kakainchik.catgenerator.util.ApiResponse
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource
) {
    suspend fun getCatPhotoData(query: CatQuery): ApiResponse<CatPhoto> {
        return try {
            ApiResponse.Success(remoteDataSource.askCat(query))
        } catch(t: Throwable) {
            ApiResponse.Error(t)
        }
    }

    suspend fun getCatPhoto(url: String): ApiResponse<ByteArray> {
        return try {
            ApiResponse.Success(remoteDataSource.loadCatImage(url))
        } catch(t: Throwable) {
            ApiResponse.Error(t)
        }
    }

    suspend fun getTags(): ApiResponse<List<String>> {
        return try {
            ApiResponse.Success(remoteDataSource.askAllTags())
        } catch(t: Throwable) {
            ApiResponse.Error(t)
        }
    }
}