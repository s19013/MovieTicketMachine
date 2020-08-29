package com.example.movie

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class Handler (val sql: ConnectSQL){
    @GetMapping("")
    fun index(model: Model):String {
        model.addAttribute("movieList",sql.showMovieList())
        return "index"
    }

    @GetMapping("{name}")
    fun jumpToDay(@PathVariable("name") name:String,
    model: Model):String {
        sql.First()
        model.addAttribute("DayList",sql.getDays(name))
        return "Day"
    }

    @GetMapping("{name}/{manth}/{day}/{time}")
    fun selectSeat(
            @PathVariable("name") name:String,
            @PathVariable("manth") manth:String,
            @PathVariable("day") day:String,
            @PathVariable("time") time:String,
            model: Model):String {
        val ASeatList = sql.showSeat("a",name,manth,day,time)
        model.addAttribute("ASeatList",ASeatList)

        val BSeatList = sql.showSeat("b",name,manth,day,time)
        model.addAttribute("BSeatList",BSeatList)

        val CSeatList = sql.showSeat("c",name,manth,day,time)
        model.addAttribute("CSeatList",CSeatList)
        return "Seat"
    }

    @GetMapping("{name}/{manth}/{day}/{time}/{R}/{C}")
    fun ticket(
            @PathVariable("name") name:String,
            @PathVariable("manth") manth:String,
            @PathVariable("day") day:String,
            @PathVariable("time") time:String,
            @PathVariable("R") R:String,
            @PathVariable("C") C:String,
            model: Model): String {
        sql.update(name, manth, day, time, R, C )
        val qr ="${name}+${manth}/${day}+${time}+${R}+${C}"

        model.addAttribute("qr",qr)
        return "ticket"
    }

//deb
@GetMapping("/deb/seat/{name}/{manth}/{day}/{time}")
fun makeSeat(
        @PathVariable("name") name:String,
        @PathVariable("manth") manth:String,
        @PathVariable("day") day:String,
        @PathVariable("time") time:String
        ): String {
    val d="${manth}/${day}"
        sql.makeSeat(name, d, time)
    return "s"
}

    @GetMapping("/deb/movie/{name}/{manth}/{day}/{time}")
    fun makeMovie(
        @PathVariable("name") name:String,
        @PathVariable("manth") manth:String,
        @PathVariable("day") day:String,
        @PathVariable("time") time:String
        ):String{
    val d="${manth}/${day}"
        sql.makeMovie(name,d,time)
        return "s"
    }

    @GetMapping("/deb/ss/{name}")
    fun makeSS(
            @PathVariable("name") name:String
    ):String{
        sql.makeSS(name)
        return "s"
    }
}