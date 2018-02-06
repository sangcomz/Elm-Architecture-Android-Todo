package xyz.sangcomz.elm_architecture_android_todo

/**
 * Created by sangcomz on 20/01/2017.
 */
data class TodoInfo(val id: Int,
                    val todo: String,
                    var isDone: Boolean,
                    var at: Long)