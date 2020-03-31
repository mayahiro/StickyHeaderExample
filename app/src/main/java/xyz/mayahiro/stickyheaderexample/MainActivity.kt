package xyz.mayahiro.stickyheaderexample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.mayahiro.stickyheaderexample.databinding.ActivityMainBinding
import xyz.mayahiro.stickyheaderexample.recyclerview.StickyHeaderAdapter
import xyz.mayahiro.stickyheaderexample.recyclerview.StickyHeaderItemDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.listData.observe(this) {
            (binding.recyclerView.adapter as StickyHeaderAdapter).addData(it)
        }

        binding.recyclerView.let {
            val adapter = StickyHeaderAdapter()
            it.addItemDecoration(StickyHeaderItemDecoration(adapter))
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.getItems()
        }

        viewModel.getItems()
    }
}
