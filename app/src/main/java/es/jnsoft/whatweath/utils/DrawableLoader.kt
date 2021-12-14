package es.jnsoft.whatweath.utils

import android.content.Context

enum class DrawableType(val value: String) {
    FULLSCREEN("fs_"),
    DRAWABLEELEMENT("di_"),
    ICONELEMENT("ic_")
}

object DrawableLoader {
    fun loadDrawable(resourceName: String, drawableType: DrawableType, context: Context): Int {
        return try {
            context.resources.getIdentifier(
                drawableType.value.plus(resourceName),
                "drawable",
                context.packageName
            )
        } catch (exception: Exception) {
            throw exception
        }
    }
}