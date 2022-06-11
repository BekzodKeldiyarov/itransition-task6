package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.helper.MutilateString;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class MainController {


    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/generateData/{numberOfData}/{locale}/{error}/{seed}")
    public List<Person> handleIndexForm(@PathVariable String locale, @PathVariable Integer numberOfData, @PathVariable Double error, @PathVariable Integer seed) {
        int errorIntValue = error.intValue();
        float errorFloatValue = error.floatValue() - errorIntValue;


        if (Math.random() < errorFloatValue) {
            error = (double) (errorIntValue + 1);
        } else {
            error = (double) errorIntValue;
        }
        return generatePersonData(locale, error, seed, numberOfData);
    }

    private List<Person> generatePersonData(String locale, Double error, Integer seed, Integer numberOfData) {
        List<Person> persons = new ArrayList<>();
        Random random = new Random(seed);
        Faker faker = new Faker(new Locale(locale), random);

        for (int i = 0; i < numberOfData; i++) {
            String name = faker.name().fullName();
            String address = faker.address().fullAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            boolean isContainsHashCharAtBeginning = Pattern.compile("^[#]+").matcher(address).find();
            if (isContainsHashCharAtBeginning) {
                for (int j = 0; j < address.length(); j++) {
                    if (address.charAt(j) != '#') {
                        address = address.substring(j);
                        break;
                    }
                }
            }
            for (int j = 0; j < error; j++) {
                switch (random.nextInt(3)) {
                    case 0: {
                        name = MutilateString.mutilateWord(name);
                        break;
                    }
                    case 1: {
                        address = MutilateString.mutilateWord(address);
                        break;
                    }
                    default: {
                        phoneNumber = MutilateString.mutilateWord(phoneNumber);
                        break;
                    }
                }
            }
            Person person = new Person(faker.number().numberBetween(1, 10000000), name, address, phoneNumber);
            persons.add(person);
        }
        return persons;
    }

}
