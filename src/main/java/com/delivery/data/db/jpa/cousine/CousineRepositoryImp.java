package com.delivery.data.db.jpa.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Cousine;
import com.delivery.core.usecases.cousine.CousineRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CousineRepositoryImp implements CousineRepository {

    private JpaCousineRepository jpaCousineRepository;

    public CousineRepositoryImp(JpaCousineRepository jpaCousineRepository) {
        this.jpaCousineRepository = jpaCousineRepository;
    }

    @Override
    public Optional<Cousine> getByIdentity(Identity id) {
        return jpaCousineRepository
                .findById(id.getNumber())
                .map(CousineData::toCousine);
    }

    @Override
    public List<Cousine> getAll() {
        return jpaCousineRepository
                .findAll()
                .parallelStream()
                .map(CousineData::toCousine)
                .collect(Collectors.toList());
    }
}
