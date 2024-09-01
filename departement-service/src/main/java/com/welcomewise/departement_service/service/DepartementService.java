package com.welcomewise.departement_service.service;

import com.welcomewise.departement_service.model.Departement;
import com.welcomewise.departement_service.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    public List<Departement> getAllDepartements(){
        return departementRepository.findAll();
    }

    public Departement getDepartementById(Long id){
        return departementRepository.findById(id).orElse(null);
    }

    public Departement saveDepartement(Departement departement){
        return departementRepository.save(departement);
    }

    public void deleteDepartement(Long id){
        departementRepository.deleteById(id);
    }


}
