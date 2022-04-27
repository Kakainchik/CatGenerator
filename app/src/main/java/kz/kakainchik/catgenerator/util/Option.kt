package kz.kakainchik.catgenerator.util

data class Option<T>(val value: T, val displayName: String) {
    override fun toString(): String = displayName
}