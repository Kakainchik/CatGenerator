package kz.kakainchik.catgenerator.data

data class CatQuery(
    val tag: String? = null,
    val filter: String? = null,
    val description: String? = null,
    val fontSize: Int? = null,
    val color: String? = null
)
