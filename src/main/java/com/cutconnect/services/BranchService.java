package com.cutconnect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;

import com.cutconnect.domains.Branch;
import com.cutconnect.repositories.BranchRepository;

@Service
public class BranchService {
    private BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    public Branch find(String id) {
        Optional<Branch> object = branchRepository.findById(id);
        return object.orElse(null);
    }

    public Branch save(Branch branch) {
        branch.setId(null);
        return branchRepository.save(branch);
    }

    public Branch update(Branch branch) {
        Branch branchDB = find(branch.getId());

        if (branch == null || branch.getId() == null) {
            throw  new IllegalArgumentException("Não foi possível encontrar registros para serem atualizados");
        }

        updateData(branchDB, branch);
        return branchRepository.save(branchDB);
    }

    public void updateData(Branch branchDB, Branch branch) {
        branchDB.setName(branch.getName());
        branchDB.setBarberShop(branch.getBarberShop());
    }

    public void delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id da fIlial não pode ser nulo");
        }

        Branch branchDB = find(id);

        if (branchDB == null) {
            throw new EntityNotFoundException("Filial não encontrada para o ID: " + branchDB.getId());
        }

        branchRepository.delete(branchDB);
    }

    public Page<Branch> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        if (page == null || linesPerPage == null || page < 0 || linesPerPage < 1) {
            throw new IllegalArgumentException("Parâmetros de paginação inválidos");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return branchRepository.findAll(pageRequest);
    }
}
