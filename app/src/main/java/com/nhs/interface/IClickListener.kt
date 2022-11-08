package com.nhs

interface IClickListener<T> {
    fun onItemClicked(item: T, position: Int)
}