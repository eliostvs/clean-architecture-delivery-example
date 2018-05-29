package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Identity;

public final class IdConverter {

    public static Long convertId(Identity id) {
        return id != null ? id.getNumber() : null;
    }
}
