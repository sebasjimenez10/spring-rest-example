package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Example;

@Controller
@RequestMapping("/example")
public class ExampleController {

	@RequestMapping(method = RequestMethod.GET)
    public String exampleMethod(ModelMap model) {
        model.addAttribute("message", "Example - Spring 3 MVC Hello LAM");
        return "../../index";
    }
	
	@RequestMapping(value="{name}", method = RequestMethod.GET)
    public @ResponseBody Example exampleJson(@PathVariable  String name) {
        
		Example ex = new Example();
		ex.setName(name);
		ex.setMessage("Hello from 'ExampleController' - Json Response");
		
		return ex;
		
    }
	
}
