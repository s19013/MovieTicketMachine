package com.example.movie

import javax.swing.table.TableColumn

data class DataSeatStatus(
        val day:String,
        val time:String,
        val row:String,
        val column: String,
        val varcant:Boolean
)