package com.example.exoplayeryoutubesample.domain

class Sheep {
    private var items: MutableList<Int> = mutableListOf()

    fun add() {
        items.add(0)
    }
    fun getCount(): Int {
        return items.count()
    }
    fun getCountText(): String {
        return "ひつじが ${getCount()} 匹"
    }
    fun isStanding(): Boolean {
        return when(getCount() % 2) {
            0 -> true
            else -> false
        }
    }
}