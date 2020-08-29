package com.example.movie

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class ConnectSQL (private val jdbc:JdbcTemplate) {
    var movieName=""
    private val movieStatusMapper = RowMapper { rs, _ ->
        DataMovieStatus(
                rs.getString("name"),
                rs.getString("day"),
                rs.getString("time"),
                rs.getInt("vseat"))
    }

    private val SeatStausMapper= RowMapper{
        rs, _ ->
        DataSeatStatus(
            rs.getString("day"),
            rs.getString("time"),
            rs.getString("r"),
                rs.getString("c"),
            rs.getBoolean("varcant"))
    }

    private val VseatMapper = RowMapper{
        rs, _ ->
        DataVSeat(
                rs.getString("T")
        )
    }

    fun reset(){
        movieName = ""
    }

    fun movieNameSetter(name: String){
        movieName = name
    }

    fun First(){
        val ML:List<DataMovieStatus> = jdbc.query("select * from movie_status ",movieStatusMapper)

        for (A in ML){
          val L = jdbc.query("select count(varcant = true or null) as T from ${A.name} where day='${A.day}' and time='${A.time}' ",VseatMapper)

        val seat = L[0].Vseat.toInt()

        jdbc.update("update movie_status set vseat=${seat} where name='${A.name}' and day='${A.day}' and time='${A.time}'")
        }
    }

    fun showMovieList(): List<DataMovieStatus> = jdbc.query("SELECT DISTINCT ON(name)* FROM MOVIE_STATUS ", movieStatusMapper)

    fun getDays(name: String): List<DataMovieStatus> {
        return jdbc.query("select * from movie_status where name = '${name}'",movieStatusMapper)
    }

    fun showSeat(C:String,name:String,manth:String,day:String,time:String):List<DataSeatStatus>{
        movieName=name
        val D ="${manth}/${day}"
        return jdbc.query("select * from ${name} where day='${D}' and time='${time}'and C='${C}'",SeatStausMapper)
    }

    fun makeQR(name: String, manth: String,day: String,time: String,R:String,C: String):List<String>{
        val D ="${manth}/${day}"
        val L = listOf<String>(name,D,time,R,C)
        return L
    }

    fun update(name: String,manth: String,day: String,time: String,R:String,C: String){
        val D ="${manth}/${day}"
        jdbc.update("update ${name} SET varcant=false where R='${R}' and C='${C}' and day='${D}' and time='${time}'")
        val L = jdbc.query("select count(varcant = true or null) as T from ${name} where day='${D}' and " +
                "time='${time}'  ",
                VseatMapper)
        var seat = L[0].Vseat.toInt()
        jdbc.update("update movie_status set vseat=${seat} where name='${name}' and day='${D}' and time='${time}'")
    }

//debs
    val line = listOf("a","b","c")
    fun makeSeat(name: String,day: String,time: String){
        for (L in 0..2){
            for (i in 1..10) {
                if (i<10){
                    jdbc.update("insert into ${name} values('${day}','${time}','0${i}','${line[L]}',true)")
                }
                else{
                    jdbc.update("insert into ${name} values('${day}','${time}','${i}','${line[L]}',true)")
                }
            }
        }
    }

    fun makeMovie(
            name: String,
            day: String,
            time: String
    ){
            jdbc.update("insert into movie_status  values('${name}','${day}','${time}',30)")
    }

    fun makeSS(
            name: String
    ){
        jdbc.execute("""
            create table if not exists ${name}(
            day char(5),
            time char(5),
            R char(2),
            C char(1),
            varcant boolean
            )
        """)
    }
}

