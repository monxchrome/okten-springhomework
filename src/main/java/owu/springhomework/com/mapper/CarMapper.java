package owu.springhomework.com.mapper;

import org.springframework.stereotype.Component;
import owu.springhomework.com.entities.Car;

@Component
public class CarMapper {

    public CarDto toDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .power(car.getPower())
                .producer(car.getProducer())
                .build();
    }

    public Car fromDto(CarDto dto) {
        Car car = new Car();
        car.setModel(dto.getModel());
        car.setPower(dto.getPower());
        car.setProducer(dto.getProducer());
        return car;
    }
}
