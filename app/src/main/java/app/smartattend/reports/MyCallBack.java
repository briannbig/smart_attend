package app.smartattend.reports;

import java.util.ArrayList;

import app.smartattend.model.ReportItem;

public interface MyCallBack {
    void onResponse(String value);
    ArrayList<ReportItem> onComplete();

}
