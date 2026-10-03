package com.example.onlineshop.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.SliderModel
import com.example.onlineshop.model.ItemsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase: FirebaseDatabase? by lazy {
        try {
            FirebaseDatabase.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    fun loadBanner(): LiveData<List<SliderModel>> {
        val listData = MutableLiveData<List<SliderModel>>()
        firebaseDatabase?.getReference("Banner")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapShot in snapshot.children) {
                    val list = childSnapShot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return listData
    }

    fun loadCategory(): LiveData<List<CategoryModel>> {
        val listData = MutableLiveData<List<CategoryModel>>()
        firebaseDatabase?.getReference("Category")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapShot in snapshot.children) {
                    val list = childSnapShot.getValue(CategoryModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return listData
    }
    fun loadRecomended(): LiveData<MutableList<ItemsModel>>{
        val listData= MutableLiveData<MutableList<ItemsModel>>()
        firebaseDatabase?.getReference("Items")?.let { ref ->
            val query: Query = ref.orderByChild("showRecommended").equalTo(true)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lists = mutableListOf<ItemsModel>()
                    for (childSnapShot in snapshot.children) {
                        val list = childSnapShot.getValue(ItemsModel::class.java)
                        if (list != null) {
                            lists.add(list)
                        }
                    }
                    listData.value = lists
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
        return listData
    }
    fun loadFiltered(id: String): LiveData<MutableList<ItemsModel>>{
        val listData= MutableLiveData<MutableList<ItemsModel>>()
        firebaseDatabase?.getReference("Items")?.let { ref ->
            val query: Query = ref.orderByChild("categoryId").equalTo(id)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lists = mutableListOf<ItemsModel>()
                    for (childSnapShot in snapshot.children) {
                        val list = childSnapShot.getValue(ItemsModel::class.java)
                        if (list != null) {
                            lists.add(list)
                        }
                    }
                    listData.value = lists
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
        return listData
    }
}