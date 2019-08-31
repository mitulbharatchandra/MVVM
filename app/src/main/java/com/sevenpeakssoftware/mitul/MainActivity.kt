package com.sevenpeakssoftware.mitul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sevenpeakssoftware.mitul.adapters.ContentListAdapter
import com.sevenpeakssoftware.mitul.databinding.ActivityMainBinding
import com.sevenpeakssoftware.mitul.domain.Content
import com.sevenpeakssoftware.mitul.viewmodel.ContentViewModel


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.cars)
        val binding: ActivityMainBinding  = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel: ContentViewModel = ViewModelProviders.of(this, ContentViewModel.Factory(application))
            .get(ContentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val viewModelAdapter = ContentListAdapter()
        binding.recyclerView.adapter = viewModelAdapter
        viewModel.contentList.observe(this, Observer<List<Content>> { list ->
            list?.apply {
                viewModelAdapter?.list = list
            }
        })
    }
}
