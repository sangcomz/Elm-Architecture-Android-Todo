package xyz.sangcomz.elm_architecture_android_todo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatch(this)
    }
}


fun dispatch(activity: Activity, action: Action = Action.INIT, model: Model = Model()) {
    val updatedModel = update(action, model)
    val dispatcher = dispatcher(activity, updatedModel)

    activity.setContentView(view(activity, updatedModel, dispatcher))
}

fun dispatcher(activity: Activity, model: Model) = { newAction: Action ->
    dispatch(activity, newAction, model)
}
