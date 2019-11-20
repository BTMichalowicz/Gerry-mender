package com.example.Gerrymender.web;


import com.example.Gerrymender.exception.ResourceNotFoundException;
import com.example.Gerrymender.model.State;
import com.example.Gerrymender.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    @Autowired
    StateRepository stateRepository;

    @RequestMapping("/")
    public String welcome(){
        return "Homepage";
    }

    @RequestMapping(value="/getSelectArea",method = RequestMethod.POST)
    public @ResponseBody List<String> receiveTest(HttpServletRequest request,String params,Integer type) {
        System.out.println(params);
        System.out.println(type);
        List<String> res = new ArrayList<>();
        res.add("caonima");
        res.add("caonima1");
        res.add("caonima2");
        return res;
    }

    @ResponseBody
    @RequestMapping("/Test")
    public List<State> stateList(){
        return stateRepository.findAll();
    }


}
