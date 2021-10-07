package es.jnsoft.whatweath.utils

import android.content.Context

enum class DrawableType(val value: String) {
    FULLSCREEN("fs_"),
    LISTELEMENT("le_")
}

object DrawableLoader {
    fun loadDrawable(resourceName: String, type: DrawableType, context: Context): Int {
        return try {
            context.resources.getIdentifier(
                type.value.plus(resourceName),
                "drawable",
                context.packageName
            )
        } catch (exception: Exception) {
            throw exception
        }
    }
}