package owu.springhomework.com.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import owu.springhomework.com.entities.Car;
import owu.springhomework.com.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final MailService mailService;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> findAllByPower(Integer power) {
        return carRepository.findAllByPower(power);
    }

    public List<Car> findAllByProducer(String producer) {
        return carRepository.findAllByProducer(producer);
    }

    public List<Car> findAllByExample(Car example) {
        return carRepository.findAll(Example.of(example));
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
                            "Car %s image was update".formatted(car.getModel()),
                            imageBytes);
                });
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

    public void deleteCar(Long carId) {
        carRepository
                .findById(carId)
                .ifPresent(car -> {
                    carRepository.deleteById(car.getId());
                    mailService.sendMail(
                            "inacheat@gmail.com",
                            "Car was deleted",
                            "Car %s was just deleted".formatted(car.getModel()),
                            car.getImage());
                });
    }
}
