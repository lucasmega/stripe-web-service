package com.cutconnect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;

import com.cutconnect.domains.Professional;
import com.cutconnect.repositories.ProfessionalRepository;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    @Autowired
    ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<Professional> findAll() {
        return professionalRepository.findAll();
    }

    public Professional find(String id) {
        Optional<Professional> object = professionalRepository.findById(id);
        return object.orElse(null);
    }

    public Professional save(Professional professional) {
        professional.setId(null);
        return professionalRepository.save(professional);
    }

    public Professional update(Professional professional) {
        Professional professionalDB = find(professional.getId());

        if (professionalDB == null || professionalDB.getId() == null) {
            throw  new IllegalArgumentException("Não foi possível encontrar profissionais");
        }

        updateData(professionalDB, professional);
        return professionalRepository.save(professional);
    }

    private void updateData(Professional professionalDB, Professional professional) {
        professionalDB.setName(professional.getName());
//        professionalDB.setBarberShop(professional.getBarberShop());
    }

    public void delete(String id) {
        if (id == null) {
            throw  new IllegalArgumentException("Profissional não pode ser nulo");
        }

        Professional professionalDB = find(id);

        if(professionalDB == null) {
            throw new EntityNotFoundException("Profissional não encontrado para o ID" + professionalDB.getId());
        }

        professionalRepository.delete(professionalDB);
    }
}
