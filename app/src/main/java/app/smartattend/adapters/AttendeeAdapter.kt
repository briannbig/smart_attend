package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.commons.CalenderUtil
import app.smartattend.model.Attendee
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class AttendeeAdapter(options: FirebaseRecyclerOptions<Attendee>) : FirebaseRecyclerAdapter<Attendee,AttendeeAdapter.ViewHolder>(options){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvRegNo: TextView = itemView.findViewById(R.id.tvRegNo)
        val tvCheckIn: TextView = itemView.findViewById(R.id.tv_check_in)
        init{
            itemView.setOnClickListener{

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.list_item_attendee, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Attendee) {
        holder.apply {
            tvRegNo.text = model.reg_No
//            tvCheckIn.text = CalenderUtil.longToTime(model.time_In)
        }
    }
}