package app.smartattend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.smartattend.R
import app.smartattend.model.ReportItem

class ReportAdapter(private val reports: ArrayList<ReportItem>): RecyclerView.Adapter<ReportAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNo: TextView = itemView.findViewById(R.id.tv_no)
        val tvReg: TextView = itemView.findViewById(R.id.tv_reg_no)
        val tvPercent: TextView = itemView.findViewById(R.id.tv_percentage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reportItem = reports[position]
        holder.apply {
            val pos =position + 1
            tvNo.text = pos.toString()
            tvReg.text = reportItem.reg_no
            tvPercent.text = "${reportItem.percentage}%"
        }
    }

    override fun getItemCount(): Int {
        return reports.size
    }
}