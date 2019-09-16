package com.recruiters.recruiterssupportbackEnd.controller;

import com.recruiters.recruiterssupportbackEnd.controller.exceptions.ExpectationFailedException;
import com.recruiters.recruiterssupportbackEnd.controller.exceptions.UnauthorizedException;
import com.recruiters.recruiterssupportbackEnd.controller.http.HttpResponseEntity;
import com.recruiters.recruiterssupportbackEnd.controller.http.ResponseUtils;
import com.recruiters.recruiterssupportbackEnd.controller.request_entities.CreateVacantRequest;
import com.recruiters.recruiterssupportbackEnd.model.entities.Career;
import com.recruiters.recruiterssupportbackEnd.model.entities.CareerJobPosition;
import com.recruiters.recruiterssupportbackEnd.model.entities.JobPosition;
import com.recruiters.recruiterssupportbackEnd.model.entities.RecruiterVacant;
import com.recruiters.recruiterssupportbackEnd.model.entities.Vacant;
import com.recruiters.recruiterssupportbackEnd.model.entities.VacantForPostulantWithoutRelation;
import com.recruiters.recruiterssupportbackEnd.repository.CareerJobPositionRepository;
import com.recruiters.recruiterssupportbackEnd.repository.CareerRepository;
import com.recruiters.recruiterssupportbackEnd.repository.JobPositionRepository;
import com.recruiters.recruiterssupportbackEnd.repository.RecruiterVacantRepository;
import com.recruiters.recruiterssupportbackEnd.repository.VacantRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JorgeDíaz
 */
@RestController
@RequestMapping("/vacant")
public class VacantController {

    private final VacantRepository vacantRepository;
    private final RecruiterVacantRepository recruiterVacantRepository;
    private final CareerJobPositionRepository careerJobPositionRepository;
    private final JobPositionRepository jobPositionRepository;

    @Autowired
    public VacantController(VacantRepository vacantRepository, RecruiterVacantRepository recruiterVacantRepository, CareerJobPositionRepository careerJobPositionRepository, JobPositionRepository jobPositionRepository) {
        this.vacantRepository = vacantRepository;
        this.recruiterVacantRepository = recruiterVacantRepository;
        this.careerJobPositionRepository = careerJobPositionRepository;
        this.jobPositionRepository = jobPositionRepository;
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.POST}, allowedHeaders = {"Content-Type", "Authorization"})
    @PostMapping("/")
    public ResponseEntity<Vacant> createVacant(@RequestBody CreateVacantRequest newVacant, @RequestHeader(value = "Authorization") String token) throws Throwable {
        if (Integer.parseInt(ResponseUtils.Validation(token).get(0)) != 1 /*&& ResponseUtils.Validation(token).get(1)== "COMPANY"*/) {//1 para no se vencio   
            throw new UnauthorizedException("Validation Problem");
        } else {

            Vacant vacant = new Vacant();
            vacant.setPlacesNumber(newVacant.getPlacesNumber());
            vacant.setNitJobPosition(newVacant.getIdJobPosition());

            Date date = new Date(System.currentTimeMillis());
            vacant.setStartDate(date);

            try {
                vacantRepository.save(vacant);
            } catch (Exception e) {
                throw new ExpectationFailedException("Vacant data is incorrect");
            }
            try {
                RecruiterVacant recruiterVacant;
                for (String idRecruiter : newVacant.getPostulantsId()) {
                    recruiterVacant = new RecruiterVacant();
                    recruiterVacant.setIdPerson(idRecruiter);
                    recruiterVacant.setIdVacant(vacant.getId());
                    recruiterVacantRepository.save(recruiterVacant);
                }
            } catch (Exception e) {
                throw new ExpectationFailedException("Any recruiter doesn't exist");
            }

            return HttpResponseEntity.getOKStatus(vacant);
        }
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET}, allowedHeaders = {"Content-Type", "Authorization"})
    @GetMapping("/withoutRelation/{id_career}")
    public ResponseEntity<List<VacantForPostulantWithoutRelation>> getVacantsByCareer(@PathVariable int id_career, @RequestHeader(value = "Authorization") String token) throws Throwable {
        if (Integer.parseInt(ResponseUtils.Validation(token).get(0)) != 1 /*&& ResponseUtils.Validation(token).get(1)== "COMPANY"*/) {//1 para no se vencio   
            throw new UnauthorizedException("Validation Problem");
        } else {

            List<CareerJobPosition> careerJobPositionList = careerJobPositionRepository.findByCareerId(id_career);

            if (!careerJobPositionList.isEmpty()) {
                List<VacantForPostulantWithoutRelation> vacantForPostulantWithoutRelationList = new ArrayList<>();

                for (CareerJobPosition careerJobPosition : careerJobPositionList) {

                    int jobPositionId = careerJobPosition.getJobPositionId();
                    JobPosition jobPosition = jobPositionRepository.findById(jobPositionId).get();

                    List<Vacant> vacants = vacantRepository.findByJobPositionId(jobPositionId);

                    VacantForPostulantWithoutRelation newVacant;
                    for (Vacant vacant : vacants) {
                        newVacant = new VacantForPostulantWithoutRelation();
                        newVacant.setId(vacant.getId());
                        newVacant.setStartDate(vacant.getStartDate());
                        newVacant.setEndDate(vacant.getEndDate());
                        newVacant.setPlacesNumber(vacant.getPlacesNumber());
                        newVacant.setJobPositionName(jobPosition.getName());
                        newVacant.setMaxSalary(jobPosition.getSalaryMax());
                        newVacant.setMinSalary(jobPosition.getSalaryMin());
                        vacantForPostulantWithoutRelationList.add(newVacant);
                    }
                }

                return HttpResponseEntity.getOKStatus(vacantForPostulantWithoutRelationList);

            } else {
                throw new ExpectationFailedException("Career doesn't exist on career_job_position data");
            }

        }
    }
}
