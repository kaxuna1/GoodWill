package main.controllers;

import main.Repositorys.FilialRepository;
import main.Repositorys.ProductRepository;
import main.Repositorys.SessionRepository;
import main.Repositorys.UserRepository;
import main.models.Enum.JsonReturnCodes;
import main.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by vakhtanggelashvili on 10/21/15.
 */
@Controller
public class UsersController {

    @RequestMapping("/createuser")
    @ResponseBody
    public JsonMessage create(@CookieValue("projectSessionId") String sessionId,
                              @RequestParam(value = "username", required = true, defaultValue = "") String username,
                              @RequestParam(value = "password", required = true, defaultValue = "") String password,
                              @RequestParam(value = "email", required = true, defaultValue = "") String email,
                              @RequestParam(value = "name", required = false, defaultValue = "") String name,
                              @RequestParam(value = "surname", required = false, defaultValue = "") String surname,
                              @RequestParam(value = "address", required = false, defaultValue = "") String address,
                              @RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
                              @RequestParam(value = "personalNumber", required = false, defaultValue = "") String personalNumber,
                              @RequestParam(value = "type", required = true, defaultValue = "0") int type,
                              @RequestParam(value = "filialId", required = false, defaultValue = "0") long filialId) {
        Filial filial;

        filial = filialRepository.findOne(filialId);

        User user  = new UserBuilder().setAddress(address)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .setAddress(address)
                .setFilial(filial)
                .setMobile(mobile)
                .setPersonalNumber(personalNumber)
                .setType(type)
                .setSessions(new ArrayList<Session>())
                .createUser();
        try {

            userDao.save(user);
        } catch (Exception ex) {

            return new JsonMessage(JsonReturnCodes.ERROR.getCODE(), ex.toString());
        }
        return new JsonMessage(JsonReturnCodes.Ok.getCODE(), "მომხმარებელი შეიქმნა წარმატებით");
    }


    @RequestMapping("/getusers")
    @ResponseBody
    public Page<User> getusers(@CookieValue("projectSessionId") String sessionId, int index, String search) {
        return userDao.findByUsernameOrEmailOrAddress(search, search, search, constructPageSpecification(index));
    }


    @RequestMapping("/edituser")
    @ResponseBody
    public User editUser(long id, User k) {
        User user = userDao.findOne(id);

        if (k.getAddress() != null) {
            user.setAddress(k.getAddress());
        }
        if (k.getEmail() != null) {
            user.setEmail(k.getEmail());
        }
        if (k.getUsername() != null) {
            user.setUsername(k.getUsername());
        }
        if (k.getPassword() != null) {
            user.setPassword(k.getPassword());
        }
        if (k.getName() != null) {
            user.setName(k.getName());
        }
        if (k.getSurname() != null) {
            user.setSurname(k.getSurname());
        }
        if (k.getMobile() != null) {
            user.setMobile(k.getMobile());
        }
        if (k.getPersonalNumber() != null) {
            user.setPersonalNumber(k.getPersonalNumber());
        }
        if (k.getType() != 0) {
            user.setType(k.getType());
        }

        try {
            userDao.save(user);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @RequestMapping("/deleteuser")
    @ResponseBody
    public String deleteUser(long id) {
        try {
            userDao.delete(id);
            return "წაიშალა წარმატებით";
        } catch (Exception e) {
            return "წაშლის დროს მოხდა შეცდომა";
        }


    }

    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

    @Autowired
    private UserRepository userDao;
    @Autowired
    private SessionRepository sessionDao;
    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private ProductRepository productRepository;
}
