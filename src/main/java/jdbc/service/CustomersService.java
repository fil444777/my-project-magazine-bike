package jdbc.service;

import jdbc.dao.CustomersDao;
import jdbc.dto.CustomersDto;

import java.util.List;
import java.util.stream.Collectors;

public class CustomersService {
    private final static CustomersService INSTANCE = new CustomersService();
    private final CustomersDao customersDao = CustomersDao.getInstance();

    private CustomersService() {
    }

    public static CustomersService getInstance(){
        return INSTANCE;
    }

    public List<CustomersDto> findAll (){
        return customersDao.findAll().stream().map(customers ->
                new CustomersDto(customers.getId(),
                        customers.getName(),
                        customers.getEmail(),
                        customers.getPhone())).collect(Collectors.toList());
    }

}
