package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAccountService {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Transactional
    public CustomerAccount createCustomerAccount(String email, String password) {

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail(email);
        customerAccount.setPassword(password);
        customerAccountRepository.save(customerAccount);
        return customerAccount;
    }

    @Transactional
    public CustomerAccount getCustomerAccount(Integer customerId) {
        return customerAccountRepository.findById(customerId).orElse(null);
    }

    @Transactional
    public void updateCustomerAccount(String oldEmail, String email, String password) {
        CustomerAccount customerAccount = customerAccountRepository.findByEmail(oldEmail).orElse(null);

        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer Account does not exist!");
        }

        customerAccount.setEmail(email);
        customerAccount.setPassword(password);

        customerAccountRepository.save(customerAccount);
    }
    @Transactional
    public void deleteCustomerAccount(String email) {
        CustomerAccount customerAccount = customerAccountRepository.findByEmail(email).orElse(null);

        if (customerAccount == null) {
            throw new IllegalArgumentException("Customer Account does not exist!");
        }

        customerAccountRepository.delete(customerAccount);
    }

    @Transactional
    public List<CustomerAccount> getAllCustomerAccounts() {
        return toList(customerAccountRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
