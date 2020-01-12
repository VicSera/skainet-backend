package com.victor.skainet.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

class HelloWorldBean(var message: String)

@CrossOrigin(origins = ["http://localhost:4200"], allowCredentials = "true")
@RestController
class HelloWorldController {

    @GetMapping(path = ["/hello-world"])
    fun helloWorld() : String {
        return "Hello World!"
    }

    @GetMapping(path = ["/hello-world-bean"])
    fun helloWorldBean() : HelloWorldBean {
        return HelloWorldBean("Hi, Bean!")
    }

    @GetMapping(path = ["/hello-world/pv/{name}"])
    fun customHelloWord(@PathVariable name : String) : HelloWorldBean {
        return HelloWorldBean(String.format("Hello, %s", name))
    }
}