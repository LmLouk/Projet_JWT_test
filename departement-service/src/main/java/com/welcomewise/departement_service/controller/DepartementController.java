package com.welcomewise.departement_service.controller;

import com.welcomewise.departement_service.model.Departement;
import com.welcomewise.departement_service.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Departement> getAllDepartements(){
        return departementService.getAllDepartements();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Departement getDepartementById(@PathVariable Long id){
        return departementService.getDepartementById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Departement createDepartement(@RequestBody Departement departement){
        return departementService.saveDepartement(departement);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Departement updateDepartement(@PathVariable Long id, @RequestBody Departement departement){
        Departement existingDepartement = departementService.getDepartementById(id);
        if (existingDepartement != null) {
            existingDepartement.setNom(departement.getNom());
            existingDepartement.setDescription(departement.getDescription());
            return departementService.saveDepartement(existingDepartement);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteDepartement(@PathVariable Long id){
        departementService.deleteDepartement(id);
    }
}
