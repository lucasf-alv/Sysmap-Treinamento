package com.example.demo.service;

import com.example.demo.Config.S3Properties;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Getter
@Setter
public class CarService {

    private final CarRepository carRepository;
    private final S3Client s3client;
    private final S3Properties s3Properties;

    public CarService(
            CarRepository carRepository,
            S3Client s3client,
            S3Properties s3Properties
    ) {
        this.carRepository = carRepository;
        this.s3client = s3client;
        this.s3Properties = s3Properties;
    }


    public Car createCar(Car car, MultipartFile file) {

        Car savedCar = carRepository.save(car);

        if(file != null && !file.isEmpty()) {
            uploadPhotoCar(savedCar, file);
        }

        return carRepository.findById(savedCar.getId())
                .orElseThrow(() -> new BusinessException("Falha ao carregar carro"));
    }


    public Car carUpdate(Long id, Car carUpdate){

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));

        car.setMarca(carUpdate.getMarca());
        car.setModelo(carUpdate.getModelo());
        car.setAno(carUpdate.getAno());
        car.setPlaca(carUpdate.getPlaca());

        return carRepository.save(car);
    }


    public void delete(Long id){

        if(!carRepository.existsById(id)){
            throw new RuntimeException("Carro não encontrado");
        }

        carRepository.deleteById(id);
    }


    public List<Car> findAll(){
        return carRepository.findAll();
    }


    public Car findById(Long id){

        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));
    }


    private void uploadPhotoCar(Car car, MultipartFile file){

        try {

            String extension = getExtension(file.getOriginalFilename());

            String photoId =
                    "cars/"
                            + car.getId()
                            + "/"
                            + UUID.randomUUID()
                            + extension;


            System.out.println("EXTENSÃO: " + extension);
            System.out.println("PHOTO ID: " + photoId);


            PutObjectRequest request =
                    PutObjectRequest.builder()
                            .bucket(s3Properties.getBucket())
                            .key(photoId)
                            .contentType(file.getContentType())
                            .build();


            s3client.putObject(
                    request,
                    RequestBody.fromBytes(file.getBytes())
            );


            String photoUrl =
                    s3Properties.getEndpoint()
                            + "/"
                            + s3Properties.getBucket()
                            + "/"
                            + photoId;


            car.setPhoto_id(photoId);
            car.setPhoto_url(photoUrl);

            carRepository.save(car);


        } catch (IOException e){

            throw new BusinessException(
                    "Falha ao enviar foto: " + e.getMessage()
            );
        }
    }



    private String getExtension(String fileName){

        if(fileName == null || !fileName.contains(".")){
            return "";
        }

        return fileName.substring(
                fileName.lastIndexOf(".")
        );
    }
}
