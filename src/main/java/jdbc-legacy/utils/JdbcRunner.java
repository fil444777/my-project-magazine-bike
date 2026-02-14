package jdbc.utils;

import jdbc.dao.CustomersDao;
import jdbc.dao.MotorcyclesDao;
import jdbc.dto.CustomersDto;

public class JdbcRunner {
    public static void main(String[] args) {

        var  customersDao = CustomersDao.getInstance();
        var motorcyclesDao = MotorcyclesDao.getInstance();
        System.out.println(motorcyclesDao.findAllByCustomerId(2));



    }
}
