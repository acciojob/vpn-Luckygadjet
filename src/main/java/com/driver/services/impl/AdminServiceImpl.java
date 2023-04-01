package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService  {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;


    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin(username,password);
        adminRepository1.save(admin);

        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {

        Admin admin = adminRepository1.findById(adminId).get();
        ServiceProvider serviceProvider = new ServiceProvider(providerName);

        admin.getServiceProviders().add(serviceProvider);
        serviceProvider.setAdmin(admin);

        // bcos of Cascading  serive provider automatically saves
        adminRepository1.save(admin);

        return admin;
    }


    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{

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

        // If cn is not null

        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();

        Country country = new Country(cn,cn.toCode());

        country.setServiceProvider(serviceProvider);

        serviceProvider.getCountryList().add(country);

        serviceProviderRepository1.save(serviceProvider); // Due to Cascading only saving serviceprovider

        return serviceProvider;

    }
}
