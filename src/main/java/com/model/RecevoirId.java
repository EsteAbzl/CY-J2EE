package com.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RecevoirId implements Serializable {

    private Integer idEmploye;
    private Integer idSalaireEmployeDate;


    public Integer getIdEmploye() {
        return idEmploye;
    }

    public Integer getIdSalaireEmployeDate() {
        return idSalaireEmployeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecevoirId)) return false;
        RecevoirId that = (RecevoirId) o;
        return Objects.equals(idEmploye, that.idEmploye) &&
                Objects.equals(idSalaireEmployeDate, that.idSalaireEmployeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmploye, idSalaireEmployeDate);
    }
}

