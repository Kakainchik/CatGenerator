package kz.kakainchik.catgenerator.data

import androidx.annotation.StringRes
import kz.kakainchik.catgenerator.R

enum class DescriptionColor(@StringRes val res: Int) {
    BLUE(R.string.blue_color),
    RED(R.string.red_color),
    GREEN(R.string.green_color),
    PINK(R.string.pink_color),
    WHITE(R.string.white_color),
    BLACK(R.string.black_color),
    ORANGE(R.string.orange_color),
    YELLOW(R.string.yellow_color)
}