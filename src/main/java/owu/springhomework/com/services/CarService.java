package owu.springhomework.com.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import owu.springhomework.com.dto.CarDto;
import owu.springhomework.com.entities.Car;
import owu.springhomework.com.mapper.CarMapper;
import owu.springhomework.com.repository.CarRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final MailService mailService;

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

        mailService.sendMail(
                "inacheat@gmail.com",
                "Car %s %s has been created with power: %s".formatted(
                        car.getProducer(),
                        car.getModel(),
                        car.getPower()
                ),
                "Subject");

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
        mailService.sendMail(
                "inacheat@gmail.com",
                "Car has been deleted",
                "Subject");

        carRepository.deleteById(id);
    }

//    @Transactional
//    @SneakyThrows
//    public void uploadPhoto(Long carId, MultipartFile file) {
//        Car car = carRepository.findById(carId)
//                .orElseThrow(() -> new IOException("Car with id " + carId + " not found"));
//
//        byte[] photoBytes = file.getBytes();
//        car.setPhoto(photoBytes);
//        carRepository.save(car);
//    }
}
