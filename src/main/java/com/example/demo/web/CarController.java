package com.example.demo.web;

import com.example.demo.model.Car;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@Tag( name = "carros" , description = "Endpoints para gerenciamento de carros")
public class CarController {
    private CarService carService;
    public CarController(CarService carService){
        this.carService = carService;
    }
    @GetMapping
    public ResponseEntity<List<Car>> listar(){
        return ResponseEntity.ok(carService.findAll());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Car> findById (@PathVariable Long id){
        return ResponseEntity.ok(carService.findById(id));
    }
    @PostMapping
    @ApiResponse(responseCode = "200", description = "Carro criado com sucesso")
    public ResponseEntity<Car> create( @RequestBody Car car){
        return ResponseEntity.ok(carService.createCar(car));

    }
    @PutMapping("/{id}")
    public ResponseEntity<Car>   update(@PathVariable Long id, @RequestBody Car car){
        return ResponseEntity.ok(carService.carUpdate(id, car));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete( @PathVariable  Long id){
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<List<Car>> findAll(String s) {
    }
}
