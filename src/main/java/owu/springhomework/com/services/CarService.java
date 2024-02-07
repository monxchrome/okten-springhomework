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

    public Optional<CarDto> getById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);

        return carOptional.map(carMapper::toDto);
    }

    public Car createCar(Car source) {
        Car savedCar = carRepository.save(source);
        mailService.sendMail(
                "inacheat@gmail.com",
                "New car created",
                "Car %s was created".formatted(savedCar.getModel()));
        return savedCar;
    }

    public Car createCar(Car source, byte[] imageBytes) {
        source.setImage(imageBytes);
        Car savedCar = carRepository.save(source);
        mailService.sendMail(
                "inacheat@gmail.com",
                "New car created",
                "Car %s was created".formatted(savedCar.getModel()),
                imageBytes);
        return savedCar;
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

    @Transactional
    public void updateCarImage(Long carId, byte[] imageBytes) {
        carRepository
                .findById(carId)
                .ifPresent(car -> {
                    car.setImage(imageBytes);
                    mailService.sendMail(
                            "inacheat@gmail.com",
                            "Car image was updated",
                            "Car %s image was update".formatted(car.getModel()));
                });
    }
}
