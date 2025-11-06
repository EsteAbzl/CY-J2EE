package com.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ParticiperId implements Serializable {

    private Integer idEmploye;
    private Integer idProjet;


    public Integer getIdEmploye() {
        return idEmploye;
    }

    public Integer getIdProjet() {
        return idProjet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticiperId)) return false;
        ParticiperId that = (ParticiperId) o;
        return Objects.equals(idEmploye, that.idEmploye) &&
                Objects.equals(idProjet, that.idProjet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmploye, idProjet);
    }
}
