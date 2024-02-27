package com.cutconnect.services;

import com.cutconnect.domains.BarberShop;
import com.cutconnect.repositories.BarberShopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarberShopService {
    private BarberShopRepository barberShopRepository;

    @Autowired
    public BarberShopService(BarberShopRepository barberShopRepository) {
        this.barberShopRepository = barberShopRepository;
    }

    public List<BarberShop> findAll() {
        return barberShopRepository.findAll();
    }

    public BarberShop find(String id) {
        Optional<BarberShop> object = barberShopRepository.findById(id);
        return object.orElse(null);
    }

    public BarberShop insert(BarberShop barberShop) {
        barberShop.setId(null);
        return barberShopRepository.save(barberShop);
    }

    public BarberShop update(BarberShop barberShop) {
        BarberShop barberShopDB = find(barberShop.getId());
        updateData(barberShopDB, barberShop);
        return barberShopRepository.save(barberShopDB);
    }

    private void updateData(BarberShop barberShopDB, BarberShop barberShop) {
        barberShopDB.setName(barberShop.getName());
        barberShopDB.setProfessionals(barberShop.getProfessionals());
    }

    public void delete(BarberShop barberShop) {
        if (barberShop == null) {
            throw new IllegalArgumentException("BarberShop não pode ser nulo.");
        }

        BarberShop barberShopDB = find(barberShop.getId());

        if (barberShopDB == null) {
            throw new EntityNotFoundException("BarberShop não encontrada para o ID: " + barberShop.getId());
        }

        barberShopRepository.delete(barberShopDB);
    }

    public Page<BarberShop> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        if (page == null || linesPerPage == null || page < 0 || linesPerPage < 1) {
            throw new IllegalArgumentException("Parâmetros de paginação inválidos.");
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return barberShopRepository.findAll(pageRequest);
    }

}
