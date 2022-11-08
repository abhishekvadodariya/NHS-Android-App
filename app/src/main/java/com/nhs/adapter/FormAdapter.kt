package com.nhs.adapter

import android.content.Context
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nhs.R
import com.nhs.adapter.recycler.SmartRecyclerAdapter
import com.nhs.model.repos.Data

class FormAdapter(var context: Context): SmartRecyclerAdapter<Data>() {

    override fun getRowLayoutId(viewType: Int): Int {
        return R.layout.r_form
    }

    override fun bind(viewHolder: RecyclerView.ViewHolder?, position: Int) {

        var lastcheck : RadioButton? = null
        var holder = viewHolder as ViewHolder
        var fromResult = dataList.get(position)
        val question = fromResult.question
        holder.tvQuestions.text = question
        val choices = fromResult.choices
        println(choices.toString())
        for (ans in 0 until choices.lastIndex){
            val answer = choices.get(ans).choice
            val id = choices.get(ans).id
            println(id.toString())
            println(answer.toString())
            if (id!!.equals(1)){
                holder.rbAnsOne.text = answer
            }else if (id.equals(11)){
                holder.rbAnsTwo.text = answer
            }else if (id.equals(21)){
                holder.rbAnsThree.text = answer
            }else if (id.equals(31)){
                holder.rbAnsFour.text = answer
            }else if (id.equals(41)){
                holder.rbAnsFive.text = answer
            }
        }

        holder.radioGroupForm.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val checked_rb = group.findViewById<View>(checkedId) as RadioButton
            if (lastcheck != null) {
                lastcheck!!.setChecked(false)
            }
                lastcheck = checked_rb
        })
    }

    override fun getViewHolder(view: View?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(view!!)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var tvQuestions = itemView.findViewById(R.id.r_form_tv_questions) as TextView
        var radioGroupForm = itemView.findViewById(R.id.r_form_rg) as RadioGroup
        var rbAnsOne = itemView.findViewById(R.id.r_form_rb_ans_one) as RadioButton
        var rbAnsTwo = itemView.findViewById(R.id.r_form_rb_ans_two) as RadioButton
        var rbAnsThree = itemView.findViewById(R.id.r_form_rb_ans_three) as RadioButton
        var rbAnsFour = itemView.findViewById(R.id.r_form_rb_ans_four) as RadioButton
        var rbAnsFive = itemView.findViewById(R.id.r_form_rb_ans_five) as RadioButton

        init {

        }
    }

}