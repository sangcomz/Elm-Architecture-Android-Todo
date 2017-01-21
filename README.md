<img src="/pic/background.png">
![logo.png](quiver-image-url/CCF0AF407ED626BC8FFECE364D9751AC.png)
# Elm-Architecture-Android-Todo
Android application with `The Elm Architecture`
<img src="/pic/logo.png" width=50%>

# The Elm Architecture
>The logic of every Elm program will break up into three cleanly separated parts:

>Model — the state of your application

>Update — a way to update your state

>View — a way to view your state as HTML

>This pattern is so reliable that I always start with the following skeleton and fill in details for my particular case.

[Elm Architecture](https://guide.elm-lang.org/architecture/)

## Model
```
data class Model(val todoList: ArrayList<TodoInfo> = arrayListOf())
```


## Update
```
sealed class Action {
    object INIT : Action()

    class ADD(val todo: String) : Action()

    class DONE(val position: Int) : Action()

    class ING(val position: Int) : Action()

    class DEL(val todoInfo: TodoInfo) : Action()
}


fun update(action: Action, model: Model): Model {
    return when (action) {
        is Action.INIT -> Model()
        is Action.ADD -> {
            model.todoList.add(TodoInfo(model.todoList.size + 1,
                    action.todo,
                    false,
                    System.currentTimeMillis() / 1000))
            model
        }
        is Action.DONE -> {
            model.todoList[action.position].isDone = true
            model
        }
        is Action.ING -> {
            model.todoList[action.position].isDone = false
            model
        }
        is Action.DEL -> {
            model.todoList.remove(action.todoInfo)
            model
        }
    }
}
```

## View(with anko)

```
fun view(context: Context, model: Model, dispatcher: (Action) -> Unit): LinearLayout {
    with(context) {
        return verticalLayout {
            cardView {
                setContentPadding(dip(30), dip(20), dip(30), dip(20))
                verticalLayout {
                    textView(context.getString(R.string.title_todo)) {
                        textSize = 18f
                    }

                    val e = editText {
                        lparams {
                            width = matchParent
                            bottomMargin = dip(10)
                        }
                        hint = context.getString(R.string.hint_edit)
                        lines = 1
                        inputType = InputType.TYPE_CLASS_TEXT
                    }

                    textView(context.getString(R.string.txt_msg)) {
                        lparams { gravity = Gravity.END }
                        gravity = Gravity.CENTER_VERTICAL
                        textColor = R.color.colorTodo.getColor(context)
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_todo_24dp, 0, 0, 0)
                        compoundDrawablePadding = dip(3)
                        textSize = 16f
                        val attrs = intArrayOf(R.attr.selectableItemBackgroundBorderless)
                        val typedArray = context.obtainStyledAttributes(attrs)
                        val backgroundResource = typedArray.getResourceId(0, 0)
                        setBackgroundResource(backgroundResource)
                        typedArray.recycle()

                        onClick {
                            val todo = e.text
                            if (todo.isNotEmpty())
                                dispatcher(Action.ADD(e.text.toString()))
                            else
                                Snackbar.make(this, context.getString(R.string.input_msg), Snackbar.LENGTH_SHORT).show()
                        }
                    }

                }
            }.lparams(width = matchParent, height = wrapContent) {
                bottomMargin = dip(20)
            }

            cardView {
                setContentPadding(dip(30), dip(30), dip(30), dip(30))
                verticalLayout {
                    textView(context.getString(R.string.title_list)) {
                        textSize = 18f
                    }

                    imageView {
                        backgroundColor = android.R.color.darker_gray.getColor(context)
                    }.lparams {
                        width = matchParent
                        height = dip(2)
                        topMargin = dip(5)
                        bottomMargin = dip(5)
                    }

                    recyclerView {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TodoListAdapter(model.todoList,
                                { position, isDone ->
                                    View.OnClickListener {
                                        if (isDone)
                                            dispatcher(Action.ING(position))
                                        else
                                            dispatcher(Action.DONE(position))
                                    }
                                },
                                { todoInfo ->
                                    View.OnClickListener {
                                        dispatcher(Action.DEL(todoInfo))
                                    }
                                })
                    }.lparams(width = matchParent, height = matchParent)
                }
            }.lparams(width = matchParent, height = matchParent) {
                padding = dip(20)
            }

        }.applyRecursively {
            when (it) {is TextView -> {
                it.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
            }
            }
        }


    }
}
```
# Feedback
welcome any feedback.

# Thanks to
[glung/elm-architecture-android](https://github.com/glung/elm-architecture-android)

