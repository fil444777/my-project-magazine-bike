package jdbc.utils;

import jdbc.dao.CustomersDao;
import jdbc.dto.CustomersDto;

public class JdbcRunner {
    public static void main(String[] args) {

        var  customersDao = CustomersDao.getInstance();

        System.out.println(customersDao.findAll());



    }
}
