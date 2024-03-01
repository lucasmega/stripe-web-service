package com.cutconnect.services;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.cutconnect.repositories.BarberShopRepository;
import com.cutconnect.domains.BarberShop;

import java.util.List;
import java.util.Optional;

@Service
public class BarberShopService {
    private final BarberShopRepository barberShopRepository;

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

        if (barberShopDB == null || barberShopDB.getId() == null) {
            throw new IllegalArgumentException("Não foi possível encontrar registros para serem atualizados");
        }

        updateData(barberShopDB, barberShop);
        return barberShopRepository.save(barberShopDB);
    }

    private void updateData(BarberShop barberShopDB, BarberShop barberShop) {
        barberShopDB.setName(barberShop.getName());
        barberShopDB.setProfessionails(barberShop.getProfessionails());
    }

    public void delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("BarberShop não pode ser nulo.");
        }

        BarberShop barberShopDB = find(id);

        if (barberShopDB == null) {
            throw new EntityNotFoundException("BarberShop não encontrada para o ID: " + barberShopDB.getId());
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

    public List<BarberShop> findByPartialName(String partialName) {
        return barberShopRepository.findByNameContainingIgnoreCase(partialName);
    }
}
