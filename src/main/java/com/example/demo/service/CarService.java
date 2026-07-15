package com.example.demo.service;

import com.example.demo.model.Car;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private CarRepository carRepository;
    public CarService(CarRepository carRepository){
        this.carRepository =  carRepository;
    }
    public List<Car> findByPlaca(String placa){
        return carRepository.findAll();
    }
    public Car createCar(Car car){
        return carRepository.save(car);

    }
    public Car carUpdate(Long id, Car car_update){
       Car car= carRepository.findById(id).orElseThrow(() -> new RuntimeException("carro não encontrado"));
       car.setMarca(car_update.getMarca());
       car.setAno(car_update.getAno());
       car.setPlaca(car_update.getPlaca());
       car.setModelo(car_update.getModelo());
       return carRepository.save(car);
    }
    public void delete( Long id){
        Car car= carRepository.findById(id).orElseThrow(() -> new RuntimeException("carro não encontrado"));
        carRepository.deleteById(id);

    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Carro não encontrado"));
    }
}
