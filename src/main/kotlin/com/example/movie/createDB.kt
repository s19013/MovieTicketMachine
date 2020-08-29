package com.example.movie
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class createDB{

    @Bean
    fun F(jdbc: JdbcTemplate) = CommandLineRunner{
        jdbc.execute("""
           create table if not exists movie_status(
           name varchar(50),
           day nchar(5),
           time nchar(5),
           Vseat int
        )""")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(createDB::class.java,*args)
}