package com.recrutement.modules.management.controllers;

import com.recrutement.DTOs.ApplicationDTO;
import com.recrutement.modules.management.exceptions.AlreadyAppliedToJobOfferException;
import com.recrutement.modules.management.exceptions.JobOfferClosedException;
import com.recrutement.modules.management.services.ApplicationManagementService;
import com.recrutement.modules.management.services.JobOfferManagementService;
import com.recrutement.utils.UtilsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Set;

public class ApplicantJobOfferController extends JobOfferController {

    private final Logger logger = LoggerFactory.getLogger(ApplicantJobOfferController.class);

    public ApplicantJobOfferController(UtilsService utilsService, ApplicationManagementService applicationManagementService, JobOfferManagementService jobOfferManagementService) {
        super(utilsService, applicationManagementService, jobOfferManagementService);
    }

    @PostMapping("/apply/{jobOfferId}")
    public ResponseEntity<?> applyForJob(@PathVariable Long jobOfferId) {
        try{
            ApplicationDTO result = applicationManagementService.applyForJob(jobOfferId, utilsService.getApplicantId());
            return ResponseEntity.ok(result);
        } catch (AlreadyAppliedToJobOfferException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/application/{jobOfferId}")
    public ResponseEntity<?> removeJobApplication(@PathVariable Long jobOfferId) {
        try {
            applicationManagementService.deleteApplicationFromJob(jobOfferId, utilsService.getApplicantId());
            return ResponseEntity.noContent().build();
        }catch (JobOfferClosedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getApplications() {
        try{
            Set<ApplicationDTO> results = applicationManagementService.getApplications(utilsService.getApplicantId());
            return ResponseEntity.ok(results);
        } catch (EntityNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
