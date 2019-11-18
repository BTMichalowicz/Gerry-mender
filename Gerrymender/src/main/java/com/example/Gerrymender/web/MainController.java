package com.example.Gerrymender.web;


import com.example.Gerrymender.exception.ResourceNotFoundException;
import com.example.Gerrymender.model.State;
import com.example.Gerrymender.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    StateRepository stateRepository;

    @RequestMapping("/")
    public String welcome(){
        return "Homepage";
    }

    @ResponseBody
    @RequestMapping("/Test")
    public List<State> stateList(){
        return stateRepository.findAll();
    }


}
