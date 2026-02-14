package jdbc.service;

import jdbc.dao.MotorcyclesDao;
import jdbc.dto.MotorcyclesDto;

import java.util.List;
import java.util.stream.Collectors;

public class MotorcyclesService {
    private static final MotorcyclesService INSTANCE = new MotorcyclesService();
    private MotorcyclesDao motorcyclesDao = MotorcyclesDao.getInstance();

    private MotorcyclesService() {
    }

    public static MotorcyclesService getInstance (){
        return INSTANCE;
    }

    public List<MotorcyclesDto> findAllByCustomerId (Integer customerId){
        return motorcyclesDao.findAllByCustomerId(customerId).stream().map(
                motorcycles -> new MotorcyclesDto(motorcycles.getId(),
                        motorcycles.getModel(), motorcycles.getYear(), motorcycles.getPrice())
                        ).collect(Collectors.toList());

    }

    public List<MotorcyclesDto> findAll (){
        return motorcyclesDao.findAll().stream().map(motorcycles ->
                new MotorcyclesDto(motorcycles.getId(),
                        motorcycles.getModel(),
                        motorcycles.getYear(),
                        motorcycles.getPrice())).collect(Collectors.toList());
    }
}
