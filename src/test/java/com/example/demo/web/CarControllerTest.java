package com.example.demo.web;

import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    CarController carController;


    @InjectMocks
    CarService carService;


    @Test
    void findAllDeveUsarFiltroQuandoPlacaforInformada(){
        Car car = new Car("ljlssjgfk","Toyota","2001","2302BJD" );
        when(carService.findByPlaca("2302BJD")).thenReturn(List.of(car));

        ResponseEntity<List<Car>> response = carController.findAll("ABC-2323");

        assertEquals(200, response.getStatusCode());

        assertEquals(1,response.getBody().size());


    }

}