package kz.kakainchik.catgenerator.vm

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.kakainchik.catgenerator.data.CatFilter
import kz.kakainchik.catgenerator.data.CatQuery
import kz.kakainchik.catgenerator.data.CatRepository
import kz.kakainchik.catgenerator.data.DescriptionColor
import kz.kakainchik.catgenerator.util.ApiResponse
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository
) : BaseViewModel() {
    private val _tags = MutableLiveData<List<String>>()
    private val _currentTag = MutableLiveData<String?>()
    private val _currentFilter = MutableLiveData<CatFilter?>()
    private val _currentFontSize = MutableLiveData<Int?>()
    private val _currentColor = MutableLiveData<DescriptionColor?>()
    private val _currentDescription = MutableLiveData<String?>()
    private val _catPhoto = MutableLiveData<ByteArray?>()

    val tags: LiveData<List<String>> = _tags
    val catPhoto: LiveData<ByteArray?> = _catPhoto

    init {
        loadTags()
    }

    fun setTag(tag: String?) {
        _currentTag.value = tag
    }

    fun setFilter(filter: CatFilter?) {
        _currentFilter.value = filter
    }

    fun setDescription(desc: String?) {
        _currentDescription.value = desc
    }

    fun setFontSize(size: Int?) {
        _currentFontSize.value = size
    }

    fun setColor(color: DescriptionColor) {
        _currentColor.value = color
    }

    fun fetchCatPhoto(doProvideOptions: Boolean) {
        isProcessing.value = true

        val query: CatQuery =
            if(doProvideOptions) CatQuery(
                _currentTag.value,
                _currentFilter.value?.code,
                _currentDescription.value,
                _currentFontSize.value,
                _currentColor.value?.name
            )
            else CatQuery(
            _currentTag.value,
            _currentFilter.value?.code
            )

        viewModelScope.launch {
            with(repository.getCatPhotoData(query)) {
                when(this) {
                    is ApiResponse.Success -> loadPhoto(body.url)
                    is ApiResponse.Error -> handleError(type)
                }
            }
        }
    }

    fun loadTags() {
        viewModelScope.launch {
            with(repository.getTags()) {
                when(this) {
                    is ApiResponse.Success -> _tags.postValue(body)
                    is ApiResponse.Error -> handleError(type)
                }
            }
        }
    }

    private fun loadPhoto(url: String) {
        viewModelScope.launch {
            with(repository.getCatPhoto(url)) {
                when(this) {
                    is ApiResponse.Success -> {
                        _catPhoto.postValue(body)
                        isProcessing.value = false
                    }
                    is ApiResponse.Error -> handleError(type)
                }
            }
        }
    }
}