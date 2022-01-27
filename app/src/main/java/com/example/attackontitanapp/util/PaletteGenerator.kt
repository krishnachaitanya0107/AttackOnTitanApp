package com.example.attackontitanapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.attackontitanapp.util.Constants.DARK_VIBRANT
import com.example.attackontitanapp.util.Constants.ON_DARK_VIBRANT
import com.example.attackontitanapp.util.Constants.VIBRANT

object PaletteGenerator {

    suspend fun convertImageUrlToBitmap(
        imageUrl: String,
        context: Context
    ): Bitmap? {
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(request = request)
        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            VIBRANT to parseColorSwatch(
                color = Palette.from(bitmap).generate().vibrantSwatch,
                darkVibrant = false
            ),
            DARK_VIBRANT to parseColorSwatch(
                color = Palette.from(bitmap).generate().darkVibrantSwatch,
                darkVibrant = true
            ),
            ON_DARK_VIBRANT to parseBodyColor(
                color = Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            )
        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?, darkVibrant:Boolean): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            return "#$parsedColor"
        } else {
            if(!darkVibrant){
                "#FFFFFF"
            } else {
                "#000000"
            }
        }
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color)
            "#$parsedColor"
        } else {
            "#FFFFFF"
        }
    }

}