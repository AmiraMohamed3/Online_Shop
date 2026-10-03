package com.example.onlineshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.SliderModel
import com.example.onlineshop.model.ItemsModel
import com.example.onlineshop.repository.MainRepository

class MainViewModel() : ViewModel() {
    private val repository = MainRepository()
    val banners : LiveData<List<SliderModel>> =repository.loadBanner()
    val categories : LiveData<List<CategoryModel>> =repository.loadCategory()

    val recomended : LiveData<MutableList<ItemsModel>> =repository.loadRecomended()

    fun loadFiltered (id: String): LiveData<MutableList<ItemsModel>>{
        return repository.loadFiltered(id)
    }
}