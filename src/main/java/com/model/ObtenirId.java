package com.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ObtenirId implements Serializable {

    private Integer idEmploye;
    private Integer idSalaireExtra;

    public Integer getIdEmploye() {
        return idEmploye;
    }

    public Integer getIdSalaireExtra() {
        return idSalaireExtra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObtenirId)) return false;
        ObtenirId that = (ObtenirId) o;
        return Objects.equals(idEmploye, that.idEmploye)
                && Objects.equals(idSalaireExtra, that.idSalaireExtra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmploye, idSalaireExtra);
    }
}

