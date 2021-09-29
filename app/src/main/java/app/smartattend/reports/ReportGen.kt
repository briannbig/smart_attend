package app.smartattend.reports

import android.util.Log
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ReportGen {

    private var allReg = ArrayList<String>()
    var courseReport = ArrayList<ReportItem>()
    private var total: Int = 1
    private lateinit var studSnapshot: Query
    fun analyzeForSpecificCourse(callBack: MyCallBack, course: Course) {
        val lessonSnapshotQuery = FirebaseDB.lessonRef.orderByChild("course").equalTo(course.code)
        studSnapshot = FirebaseDB.studentRef.orderByChild("enrolledClass").equalTo(course.classId)
        lessonSnapshotQuery.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        Log.d("Child---->", i.toString())
                        if (i.hasChild("attendees")){
                            Log.d("Child----> has child", i.toString())
                            for (j in i.children){
                                Log.d("Child----> hhhhhh", j.child("reg_No").value.toString())
                                if (j.hasChild("reg_No")) {
                                    allReg.add(j.child("reg_No").value.toString())
                                    Log.d("the_child---->", i.child("reg_No").value.toString())
                                    callBack.onResponse(j.child("reg_No").value.toString())
                                    total ++
                                }
                            }
                        }
                    }
//                    countFrequencies(allReg)
                    callBack.onComplete()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun countFrequencies(regPool: java.util.ArrayList<String>) {
        val hm = HashMap<String, Int>()

        for (i in regPool) {
            val j = hm[i]
            hm[i] = if (j == null) 1 else j + 1
        }
        for ((key, value) in hm) {
            analyzeStudPercentage(value, total, key)
        }

    }

    private suspend fun analyzeStudPercentage(snapshot: DataSnapshot, reg_no: String){
        var sum = 0; var count = 0
        for (i in snapshot.children){
            if (i.hasChild(reg_no)){
                sum ++
            }
            count ++
        }
        val avg = (sum/count) * 100
        courseReport.add(ReportItem(reg_no, avg))
    }
    private fun analyzeStudPercentage(count: Int, sum: Int, reg_no: String){
        val avg = (count/sum) * 100
        courseReport.add(ReportItem(reg_no, avg))
    }

}