package app.smartattend.reports

import app.smartattend.model.ReportItem

class CallBackImpl : MyCallBack {
    private var allReg = ArrayList<String>()
    var courseReport = ArrayList<ReportItem>()
    private var total: Int = 1

    override fun onResponse(value: String?) {
        if (value != null) {
            allReg.add(value)
            total++
        }
    }

    override fun onComplete() : ArrayList<ReportItem> {
        countFrequencies(allReg)
        return courseReport
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
    private fun analyzeStudPercentage(count: Int, sum: Int, reg_no: String){
        val avg = (count/sum) * 100
        courseReport.add(ReportItem(reg_no, avg))
    }
}