package xyz.sangcomz.elm_architecture_android_todo.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

/**
 * Created by sangcomz on 18/01/2017.
 */
fun Int.getColor(context: Context): Int = ContextCompat.getColor(context, this)
fun Int.getDrawable(context: Context): Drawable = ContextCompat.getDrawable(context, this)