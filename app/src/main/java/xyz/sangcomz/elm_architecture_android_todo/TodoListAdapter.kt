package xyz.sangcomz.elm_architecture_android_todo

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import java.util.*


/**
 * Created by sangcomz on 19/01/2017.
 */

class TodoListAdapter(
    private var todoList: ArrayList<TodoInfo>,
    private val onCheck: (Int, Boolean) -> View.OnClickListener,
    private val delete: (TodoInfo) -> View.OnClickListener
) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    override fun onBindViewHolder(holder: TodoListViewHolder?, position: Int) {
        holder?.setView(position, todoList[position], onCheck, delete)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return TodoListViewHolder(
            TodoItemUI().createView(
                AnkoContext.Companion.create(
                    parent.context,
                    parent
                )
            )
        )
    }


    class TodoListViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val txtTodo: TextView = item.find(R.id.txt_todo)
        private val chkTodo: CheckBox = item.find(R.id.chk_todo)
        private val imgTodo: ImageButton = item.find(R.id.img_todo)

        fun setView(
            position: Int,
            todoInfo: TodoInfo, onCheck: (Int, Boolean) -> View.OnClickListener,
            delete: (TodoInfo) -> View.OnClickListener
        ) {
            chkTodo.setOnClickListener(onCheck(position, todoInfo.isDone))
            imgTodo.setOnClickListener(delete(todoInfo))
            chkTodo.isChecked = todoInfo.isDone
            val todo = SpannableString(todoInfo.todo)
            if (chkTodo.isChecked) {
                todo.setSpan(StrikethroughSpan(), 0, todoInfo.todo.length, 0)
            }
            txtTodo.text = todo

        }
    }

    class TodoItemUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = dip(40))
                    orientation = LinearLayout.HORIZONTAL
                    checkBox {
                        id = R.id.chk_todo
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                    }
                    textView {
                        id = R.id.txt_todo
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                    imageButton {
                        id = R.id.img_todo
                        imageResource = R.drawable.ic_clear_black_24dp

                        val attrs = intArrayOf(R.attr.selectableItemBackgroundBorderless)
                        val typedArray = context.obtainStyledAttributes(attrs)
                        val backgroundResource = typedArray.getResourceId(0, 0)
                        setBackgroundResource(backgroundResource)
                        typedArray.recycle()
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        gravity = Gravity.CENTER_VERTICAL
                    }
                }.applyRecursively {
                    when (it) {
                        is TextView -> {
                            it.textSize = 16f
                            it.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
                        }
                    }
                }
            }
        }
    }
}