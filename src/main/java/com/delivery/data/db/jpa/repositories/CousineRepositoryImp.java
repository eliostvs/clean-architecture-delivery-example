package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.cousine.CousineRepository;
import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CousineRepositoryImp implements CousineRepository {

    private JpaCousineRepository jpaCousineRepository;

    public CousineRepositoryImp(JpaCousineRepository jpaCousineRepository) {
        this.jpaCousineRepository = jpaCousineRepository;
    }

    @Override
    public Set<Store> getStoresByIdentity(Identity id) {
        return jpaCousineRepository
                .findStoresById(id.getNumber())
                .parallelStream()
                .map(StoreData::toStore)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Cousine> getAll() {
        return jpaCousineRepository
                .findAll()
                .parallelStream()
                .map(CousineData::toCousine)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cousine> searchByName(String search) {
        return jpaCousineRepository
                .findByNameContainingIgnoreCase(search)
                .parallelStream()
                .map(CousineData::toCousine)
                .collect(Collectors.toList());
    }
}
