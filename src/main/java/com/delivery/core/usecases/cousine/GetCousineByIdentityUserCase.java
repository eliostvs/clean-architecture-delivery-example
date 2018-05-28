package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCase;

public class GetCousineByIdentityUserCase implements UseCase<Identity, Cousine> {
    private CousineRepository repository;

    public GetCousineByIdentityUserCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cousine execute(Identity id) {
        return repository
                .getByIdentity(id)
                .orElseThrow(() -> new NotFoundException("No cousine found for identity: " + id.getNumber()));
    }
}
