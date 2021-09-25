package app.smartattend.commons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.smartattend.model.Class

class ClassViewModel: ViewModel() {
    var classs = MutableLiveData<Class>()
}