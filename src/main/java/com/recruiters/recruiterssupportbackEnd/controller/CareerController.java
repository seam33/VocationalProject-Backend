package com.recruiters.recruiterssupportbackEnd.controller;

import com.recruiters.recruiterssupportbackEnd.controller.exceptions.UnauthorizedException;
import com.recruiters.recruiterssupportbackEnd.controller.http.HttpResponseEntity;
import com.recruiters.recruiterssupportbackEnd.controller.http.ResponseUtils;
import com.recruiters.recruiterssupportbackEnd.controller.request_entities.CreateRequestCareer;
import com.recruiters.recruiterssupportbackEnd.controller.request_entities.CreateRequestCareerJob;
import com.recruiters.recruiterssupportbackEnd.model.entities.Career;
import com.recruiters.recruiterssupportbackEnd.repository.CareerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author seam33
 */
@RestController
@RequestMapping("/career")
public class CareerController {

    private final CareerRepository careerRepository;

    @Autowired
    public CareerController(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET}, allowedHeaders = {"Content-Type", "Authorization"})
    @GetMapping("/")
    public ResponseEntity<List<Career>> getAllCareer(@RequestHeader(value = "Authorization") String token) throws UnauthorizedException {
        if (Integer.parseInt(ResponseUtils.Validation(token).get(0)) != 1 /*&& ResponseUtils.Validation(token).get(1)== "COMPANY"*/) {//1 para no se vencio   
            throw new UnauthorizedException("Validation Problem");
        } else {

        return HttpResponseEntity.getOKStatus(careerRepository.findAll());
        }
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.POST}, allowedHeaders = {"Content-Type", "Authorization"})
    @PostMapping("/register")
    public ResponseEntity<Career> createCareer(@RequestBody CreateRequestCareer createRequestCareer, @RequestHeader(value = "Authorization") String token) throws Throwable {
        if (Integer.parseInt(ResponseUtils.Validation(token).get(0)) != 1 /*&& ResponseUtils.Validation(token).get(1)== "COMPANY"*/) {//1 para no se vencio   
            throw new UnauthorizedException("Validation Problem");
        } else {

        String name = createRequestCareer.getName();

        Optional<Career> optCareer = careerRepository.findByName(name); //Busqueda por ID

        if (optCareer.isPresent()) { // Si existe envia mensaje de Error
            throw new UnauthorizedException("area already exist");
        } else {

            Career career = new Career();
            career.setName(name);
            careerRepository.save(career);
            return HttpResponseEntity.getOKStatus(career);

        }}
    }

    
    /*
    @PostMapping("/RegisterJob")
<<<<<<< HEAD
    public ResponseEntity<UserEntity> createRecruiter(@RequestBody CreateRequestCareerJob createRequestCareerJob) throws Throwable {

        String name = createRequestCareerJob.getName();

=======
    public ResponseEntity<UserEntity> createRecruiter(@RequestBody CreateRequestCareerJob createRequestCareerJob, @RequestHeader(value = "Authorization") String token) throws Throwable {
    
        String name=createRequestCareerJob.getName();
        
>>>>>>> 38af9a6f30a608d912f0ca221003e86c32dd22f4
        Optional<Career> optCareer = careerRepository.findByName(name); //Busqueda por ID

        if (optCareer.isPresent()) { // Si existe envia mensaje de Error
            throw new UnauthorizedException("area already exist");
        } else {

            Career career = new Career();
            career.setName(name);
            careerRepository.save(career);
            
            return HttpResponseEntity.getOKStatus(career);

        }
    }*/

}
