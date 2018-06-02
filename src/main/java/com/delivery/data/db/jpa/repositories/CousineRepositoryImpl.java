package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.cousine.CousineRepository;
import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CousineRepositoryImpl implements CousineRepository {

    private JpaCousineRepository jpaCousineRepository;

    public CousineRepositoryImpl(JpaCousineRepository jpaCousineRepository) {
        this.jpaCousineRepository = jpaCousineRepository;
    }

    @Override
    public List<Store> getStoresById(Identity id) {
        return jpaCousineRepository
                .findStoresById(id.getNumber())
                .parallelStream()
                .map(StoreData::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cousine> getAll() {
        return jpaCousineRepository
                .findAll()
                .parallelStream()
                .map(CousineData::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cousine> searchByName(String search) {
        return jpaCousineRepository
                .findByNameContainingIgnoreCase(search)
                .parallelStream()
                .map(CousineData::fromThis)
                .collect(Collectors.toList());
    }
}
