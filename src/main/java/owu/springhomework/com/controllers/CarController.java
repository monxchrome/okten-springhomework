package owu.springhomework.com.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owu.springhomework.com.entities.Car;
import owu.springhomework.com.services.CarService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars() {
        return ResponseEntity.ok(carService.getAll());
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") ObjectId id) {
        return ResponseEntity.of(carService.getById(id));
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody() Car source) {
        return ResponseEntity.ok(carService.createCar(source));
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable("id") ObjectId id) {
        carService.deleteCar(id);
    }
}
