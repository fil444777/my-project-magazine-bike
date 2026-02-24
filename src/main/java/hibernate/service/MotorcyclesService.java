package hibernate.service;


import hibernate.dao.MotorcyclesDao;
import hibernate.entity.Motorcycle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MotorcyclesService {
    private static final MotorcyclesService INSTANCE = new MotorcyclesService();
    private MotorcyclesDao motorcyclesDao = MotorcyclesDao.getInstance();

    private MotorcyclesService() {
    }

    public static MotorcyclesService getInstance() {
        return INSTANCE;
    }

    public List<Motorcycle> findAllByCustomerId(Integer userId) {
        log.info("Loading motorcycles for user ID: {}", userId);

        List<Motorcycle> motorcycles = motorcyclesDao.findAllByUserId(userId);

        log.info("Loaded {} motorcycles for user ID: {}", motorcycles.size(), userId);

        return motorcycles;
    }

    /*
    public List<Motorcycles> findAll (){
        return motorcyclesDao.findAll().stream().map(motorcycles ->
                new Motorcycles(motorcycles.getId(),
                        motorcycles.getModel(),
                        motorcycles.getYear(),
                        motorcycles.getPrice())).collect(Collectors.toList());
    }

     */
}
