package owu.springhomework.com.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import owu.springhomework.com.dto.CarDto;
import owu.springhomework.com.entities.Car;
import owu.springhomework.com.mapper.CarMapper;
import owu.springhomework.com.repository.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public List<CarDto> getAll(String producer) {
        return Optional.ofNullable(producer)
                .map(carRepository::findAllByProducer)
                .orElseGet(carRepository::findAll)
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    public CarDto createCar(CarDto carDto) {
        Car car = carMapper.fromDto(carDto);

        Car createdCar = carRepository.save(car);

        return carMapper.toDto(createdCar);
    }

    public Optional<CarDto> getById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);

        return carOptional.map(carMapper::toDto);
    }

    public List<CarDto> getByPower(Integer power) {
        List<Car> cars = carRepository.findAllByPower(power);

        return cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CarDto> getByProducer(String producer) {
        List<Car> cars = carRepository.findAllByProducer(producer);

        return cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
