package kz.kakainchik.catgenerator.data

import androidx.annotation.StringRes
import kz.kakainchik.catgenerator.R

enum class CatFilter(val code: String, @StringRes val res: Int) {
    BLUR("blur", R.string.blur_fulter),
    MONO("mono", R.string.mono_filter),
    SEPIA("sepia", R.string.sepia_filter),
    NEGATIVE("negative", R.string.negative_filter),
    PAINT("paint", R.string.paint_filter),
    PIXEL("pixel", R.string.pixel_filter);
}