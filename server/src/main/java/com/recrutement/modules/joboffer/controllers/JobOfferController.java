package com.recrutement.modules.joboffer.controllers;

import com.recrutement.dtos.compact.ApplicationDTO;
import com.recrutement.dtos.compact.JobOfferDTO;
import com.recrutement.enums.ApplicationStatus;
import com.recrutement.modules.joboffer.services.ApplicationManagementService;
import com.recrutement.modules.joboffer.services.JobOfferManagementService;
import com.recrutement.utils.UtilsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/job-offer")
@RequiredArgsConstructor
public class JobOfferController {
    private final Logger logger = LoggerFactory.getLogger(JobOfferController.class);

    protected final UtilsService utilsService;
    protected final ApplicationManagementService applicationManagementService;
    protected final JobOfferManagementService jobOfferManagementService;

    @GetMapping("/{jobOfferId}")
    public ResponseEntity<?> getJobOffer(@PathVariable Long jobOfferId){
        try{
            JobOfferDTO jobOffers = jobOfferManagementService.getJobOffer(jobOfferId);
            return ResponseEntity.ok(jobOffers);
        } catch (EntityNotFoundException | AccessDeniedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list/{isOpen}")
    public ResponseEntity<?> getJobOffers(@PathVariable(required = false) Boolean isOpen){
        try{
            Set<JobOfferDTO> jobOffers = new HashSet<>();
            if(isOpen == null)
                jobOffers = jobOfferManagementService.getJobOffers();

            jobOfferManagementService.getJobOffers(isOpen);
            return ResponseEntity.ok(jobOffers);
        } catch (EntityNotFoundException | AccessDeniedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list-by-company/{companyId}/{isOpen}")
    public ResponseEntity<?> getJobOffersByCompany(@PathVariable Long companyId, @PathVariable(required = false) Boolean isOpen){
        try{
            Set<JobOfferDTO> jobOffers = new HashSet<>();
            if(isOpen == null)
                jobOffers = jobOfferManagementService.getJobOffers(companyId);

            jobOfferManagementService.getJobOffers(isOpen, companyId);
            return ResponseEntity.ok(jobOffers);
        } catch (EntityNotFoundException | AccessDeniedException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<?> getApplication(@PathVariable(required = true) Long id) {
        try{
            ApplicationDTO result = applicationManagementService.getApplication(id, utilsService.getApplicantId());
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<?> getApplicationStatus(@PathVariable(required = true) Long id) {
        try{
            ApplicationStatus result = applicationManagementService.getApplicationStatus(id, utilsService.getApplicantId());
            return ResponseEntity.ok(result);
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
