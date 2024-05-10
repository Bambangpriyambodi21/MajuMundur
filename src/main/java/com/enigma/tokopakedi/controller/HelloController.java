package com.enigma.tokopakedi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private List<Map<String, Object>> users = List.of(
            Map.of(
                    "id", 1,
                    "name", "Budi"
            ),
            Map.of(
                    "id", 2,
                    "name", "bambang"
            )
    );

    private List<String> fruits = List.of("Apel", "Mangga a", "Mangga b", "Mangga c", "Nanas a", "Nanas b");

    @GetMapping(path = "/fruits")
//    localhost:8080/fruits?search=mangga
    public List<String> getList(@RequestParam String search){
        List<String> temp = new ArrayList<>();

        List<String> result = fruits.stream()
                .filter(fruit -> fruit.toLowerCase().contains(search.toLowerCase()))
                .toList();

        return result;
    }

    @GetMapping(path = "/users/{userid}")
    public Map<String, Object> getUsersById(@PathVariable Integer userid){
        Map<String, Object> temp = null;

        for (Map<String, Object> user : users) {
            if (user.get("id").equals(userid)){
                temp = user;
            }
        }
        return temp;
    }

    @GetMapping(path = "/")
    public String helloWorld(){
        return "Hello world";
    }

//    @GetMapping(path = "/fruits")
//    public List<String> getString(){
//        return List.of("mangga", "apel", "melon");
//    }

    @GetMapping(path = "/cars")
    public Map<String, Object> getCar(){
        return Map.of(
                "brand", "toyota",
                "name", "supra",
                "year", "2020"
        );
    }

    @PostMapping(path = "/userss")
    public User postUser(@RequestBody User user){
        return user;
    }
}
