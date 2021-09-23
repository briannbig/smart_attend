package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.model.Class
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ClassAdapter(options: FirebaseRecyclerOptions<Class>) : FirebaseRecyclerAdapter<Class,ClassAdapter.ViewHolder>(options){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvId: TextView = itemView.findViewById(R.id.tv_class_id)
        val tvProgram: TextView = itemView.findViewById(R.id.tv_program)
        init{
            itemView.setOnClickListener{

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.list_item_class, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Class) {
        holder.apply {
            tvId.text = model.classCode
            tvProgram.text = model.program
        }
    }
}