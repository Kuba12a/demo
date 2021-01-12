package com.example.demo.api;

import com.example.demo.dao.AddressesDAO;
import com.example.demo.dao.LoginsDAO;
import com.example.demo.dao.Person2DAO;
import com.example.demo.model.Address;
import com.example.demo.model.Login;
import com.example.demo.model.Person2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AddressController {

    @Autowired
    private AddressesDAO addressesDAO;
    @Autowired
    private Person2DAO person2DAO;

    private LoginsDAO loginsDAO= new LoginsDAO();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/HomePage")
    public String viewHomePage(Model model){
    List<Address> listAddress=addressesDAO.list();
    model.addAttribute("listAddress",listAddress);

    return "index";

    }

    @RequestMapping("/persons")
    public String viewPersons(Model model){
            List<Person2> person2List =  person2DAO.list();
            model.addAttribute("person2List",person2List);

            return "persons";

    }

    @RequestMapping("/addPerson")
    public String showAddPerson(Model model){
        Person2 person=new Person2();
        model.addAttribute("person",person);

        return "add_person";
    }

    @RequestMapping(value="/savePerson", method = RequestMethod.POST)
    public String savePerson(@ModelAttribute("person") Person2 person){
        person2DAO.save(person);

        return "redirect:/persons";

    }






    @RequestMapping("/new")
    public String showNewForm(Model model){
        Address address=new Address();
        model.addAttribute("address",address);

        return "add_address";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("address") Address address){
        addressesDAO.save(address);

        return "redirect:/HomePage";

    }
    @RequestMapping("/")
    public String loginPage(Model model){
        Login login = new Login();
        model.addAttribute("login",login);
        return "login_screen";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("login") Login login){

        if(loginsDAO.verify(login)){
            System.out.println(login.getLogin());
            System.out.println(login.getPassword());
            return "redirect:/HomePage";
        }
        System.out.println(login.getLogin());
        System.out.println(login.getPassword());
        return "redirect:/";

    }


}
