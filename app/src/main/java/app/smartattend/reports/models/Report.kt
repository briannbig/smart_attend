package app.smartattend.reports.models

import app.smartattend.model.ReportItem
import com.google.firebase.database.DataSnapshot
import com.google.gson.Gson
import com.google.gson.JsonSerializationContext
import java.util.ArrayList

object Report {
    private val lessons = ArrayList<Lesson>()
    private val reportItems = ArrayList<ReportItem>()
    private val regPool = ArrayList<String>()
    private var snapshot: DataSnapshot? =null
    suspend fun generateReport(): ArrayList<ReportItem> {
        prepareLesson(this.snapshot!!)
        prepareRegPool()
        countFrequencies()
        return reportItems
    }
    fun post(snapshot: DataSnapshot){
        this.snapshot = snapshot
    }
    fun isSnapNull(): Boolean{
        return snapshot != null
    }

    private fun countFrequencies() {
        val hm = HashMap<String, Int>()

        for (i in regPool) {
            val j = hm[i]
            hm[i] = if (j == null) 1 else j + 1
        }
        for ((key, value) in hm) {
            analyzeStudPercentage(lessons.size,value, key)
        }

    }
    private fun analyzeStudPercentage(count: Int, sum: Int, reg_no: String){
        val avg = (count/sum) * 100
        reportItems.add(ReportItem(reg_no, avg))
    }
    private fun prepareLesson(snapshot: DataSnapshot){
        for (snap in snapshot.children) {
            val les = Lesson()
            val arrayList = ArrayList<Attendee>()
            for (i in snap.children){
                val path = i.child("attendees")
//                for (j in path.children){
                    arrayList.add(Attendee(i.child(i.key!!).child("reg_No").value.toString(),
                        i.child(i.key!!).child("time_In").value.toString()))
//                }
                les.attendees = arrayList
            }
            lessons.add(les)
        }
    }
    private fun prepareRegPool(){
        for (i in lessons){
            for (j in lessons){
                for (k in j.attendees){
                    regPool.add(k.reg_No)
                }
            }
        }
    }

}