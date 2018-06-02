package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Identity;

public final class IdConverter {

    public static Long convertId(Identity id) {
        if (id != null && id.getNumber() != Long.MIN_VALUE) {
            return id.getNumber();
        }

        return null;
    }
}
