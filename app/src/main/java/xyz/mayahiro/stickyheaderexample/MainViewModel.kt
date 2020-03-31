package xyz.mayahiro.stickyheaderexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDateTime
import xyz.mayahiro.stickyheaderexample.data.ListData

class MainViewModel : ViewModel() {
    private val _listData = MutableLiveData<List<ListData>>()
    val listData: LiveData<List<ListData>>
        get() = _listData

    private var dateTime = LocalDateTime.now()

    fun getItems() {
        val data = mutableListOf<ListData>()
        for (i in 0..99) {
            data.add(ListData(dateTime))
            dateTime = dateTime.plusHours(1)
        }

        _listData.value = data
    }
}
