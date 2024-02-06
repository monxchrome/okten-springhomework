package owu.springhomework.com.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import owu.springhomework.com.dto.CarDto;
import owu.springhomework.com.services.CarService;
import owu.springhomework.com.util.View;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @JsonView(View.External.class)
    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getCars(@RequestParam(required = false) String producer) {
        return ResponseEntity.ok(carService.getAll(producer));
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.of(carService.getById(id));
    }

    @JsonView(View.Internal.class)
    @GetMapping("/cars/power/{value}")
    public ResponseEntity<List<CarDto>> getByPower(@PathVariable("value") Integer power) {
        return ResponseEntity.ok(carService.getByPower(power));
    }

    @JsonView(View.Internal.class)
    @GetMapping("/cars/producer/{value}")
    public ResponseEntity<List<CarDto>> getByProducer(@PathVariable("value") String producer) {
        return ResponseEntity.ok(carService.getByProducer(producer));
    }

    @PostMapping("/cars")
    public ResponseEntity<CarDto> createCar(@RequestBody @Valid CarDto car) {
        CarDto createdCar = carService.createCar(car);
        URI uri = UriComponentsBuilder.fromPath("/cars/{id}").build(createdCar.getId());

        return ResponseEntity.created(uri).body(createdCar);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.accepted().build();
    }

//    @PostMapping("/cars/photo/{id}")
//    public ResponseEntity<CarDto> uploadPhoto (
//                @PathVariable("id") Long id,
//                @RequestParam("file") MultipartFile file ) {
//        carService.uploadPhoto(id, file);
//        return ResponseEntity.accepted().build();
//    }
}
