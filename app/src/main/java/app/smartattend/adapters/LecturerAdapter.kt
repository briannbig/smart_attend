package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.model.Lecturer
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class LecturerAdapter(options : FirebaseRecyclerOptions<Lecturer>): FirebaseRecyclerAdapter<Lecturer, LecturerAdapter.ViewHolder>(options){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvRegNo: TextView = itemView.findViewById(R.id.tv_lec_no)
        val tvName: TextView = itemView.findViewById(R.id.tv_lec_name)
        init{
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  ViewHolder(inflater.inflate(R.layout.list_item_lecturer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Lecturer) {
        holder.apply {
            tvRegNo.text = model.lecNo
            tvName.text = model.name
        }
    }


}