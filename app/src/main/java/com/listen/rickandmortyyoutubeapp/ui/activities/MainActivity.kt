package com.listen.rickandmortyyoutubeapp.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.listen.rickandmortyyoutubeapp.databinding.ActivityMainBinding
import com.listen.rickandmortyyoutubeapp.ui.MainViewModel
import com.listen.rickandmortyyoutubeapp.ui.adapters.CharactersAdapter
import com.listen.rickandmortyyoutubeapp.uils.ScreenState


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(MainViewModel::class.java)



       setupRecyclerView()
       fetch()



    }

    private fun setupRecyclerView(){
        viewAdapter = CharactersAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }
    }

    private fun fetch() = lifecycleScope.launchWhenStarted {
        viewModel.fetchCharacters()
        viewModel.state.collect{ state ->
            when(state){
                is ScreenState.Error -> {

                }
                is ScreenState.Internet -> {

                }
                is ScreenState.Loading -> {

                }
                is ScreenState.Success -> {
                    state.data?.let {
                        viewAdapter.differ.submitList(it.results)
                    }
                }
                else -> {}
            }
        }
    }





}