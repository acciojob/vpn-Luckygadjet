package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.OneToOne;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;


    @Override
    public User register(String username, String password, String countryName) throws Exception{
        countryName = countryName.toUpperCase();

        CountryName cn = null;

        for(CountryName c : CountryName.values())
        {
            if(c.name().equals(countryName))
            {
                cn = c;
                break;
            }
        }

        if(cn == null)
        {
            throw new Exception("Country not found");
        }

        User user = new User();
        Country country = new Country(cn,cn.toCode());

        user.setUsername(username);
        user.setPassword(password);
        user.setConnected(false);
        user.setOriginalCountry(country);
        //String code = country.getCode()+"."+;
        String code = country.getCode()+"."+user.getId();
        user.setOriginalIp(code);

        country.setUser(user);

        userRepository3.save(user);
        return user;
    }


    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        user.getServiceProviderList().add(serviceProvider);

        serviceProvider.getUsers().add(user);

        serviceProviderRepository3.save(serviceProvider);// Due to Cascading

        return user;




    }
}
