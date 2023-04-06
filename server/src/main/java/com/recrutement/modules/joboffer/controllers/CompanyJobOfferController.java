package com.recrutement.modules.joboffer.controllers;

import com.recrutement.dtos.compact.ApplicantDTO;
import com.recrutement.dtos.compact.ApplicationDTO;
import com.recrutement.dtos.compact.JobOfferDTO;
import com.recrutement.enums.ApplicationStatus;
import com.recrutement.modules.joboffer.services.ApplicationManagementService;
import com.recrutement.modules.joboffer.services.JobOfferManagementService;
import com.recrutement.utils.UtilsService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



public class CompanyJobOfferController extends JobOfferController {

    private final Logger logger = LoggerFactory.getLogger(CompanyJobOfferController.class);

    public CompanyJobOfferController(UtilsService utilsService, ApplicationManagementService applicationManagementService, JobOfferManagementService jobOfferManagementService) {
        super(utilsService, applicationManagementService, jobOfferManagementService);
    }

    @PostMapping
    public ResponseEntity<?> createJobOffer(@RequestBody JobOfferDTO jobOffer){
        try{
            JobOfferDTO result = jobOfferManagementService.createJobOffer(jobOffer, utilsService.getCompanyId());
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateJobOffer(@RequestBody JobOfferDTO jobOffer){
        try{
            JobOfferDTO result = jobOfferManagementService.updateJobOffer(jobOffer, utilsService.getCompanyId());
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException | AccessDeniedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{jobOfferId}")
    public ResponseEntity<?> removeJobOffer(@PathVariable Long jobOfferId){
        try{
            jobOfferManagementService.removeJobOffer(jobOfferId, utilsService.getCompanyId());
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException | AccessDeniedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/application/change-status/{id}/{status}")
    public ResponseEntity<?> updateJobOffer(@PathVariable("id") Long id, @PathVariable("status")ApplicationStatus status){
        try{
            ApplicationDTO application = applicationManagementService.changeApplicationStatus(id, status, utilsService.getCompanyId());
            return ResponseEntity.ok(application);
        } catch (EntityNotFoundException | AccessDeniedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}