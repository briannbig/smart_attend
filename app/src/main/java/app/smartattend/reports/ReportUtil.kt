package app.smartattend.reports

import com.google.firebase.database.DataSnapshot

class ReportUtil {

    fun analyzeStudPercentage(snapshot: DataSnapshot, reg_no: String): Int {
        var sum = 0; var count = 0
        for (i in snapshot.children){
            if (i.hasChild(reg_no)){
                sum ++
            }
            count ++
        }
        return (sum/count) * 100
    }
}