/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Persoon \ Nationaliteit.
 *
 */
@Entity
@Table(schema = "Kern", name = "PersNation")
public class PersoonNationaliteitHisVolledig {

    @Id
    @JsonProperty
    private Integer                           iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig                persoon;

    @ManyToOne
    @JoinColumn(name = "Nation")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Nationaliteit                     nationaliteit;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonNationaliteit")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonNationaliteitModel> hisPersoonNationaliteitLijst;

    /**
     * Retourneert ID van Persoon \ Nationaliteit.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Nationaliteit.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Nationaliteit van Persoon \ Nationaliteit.
     *
     * @return Nationaliteit.
     */
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    /**
     * Retourneert lijst van historie van Standaard van Persoon \ Nationaliteit.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisPersoonNationaliteitModel> getHisPersoonNationaliteitLijst() {
        return hisPersoonNationaliteitLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Persoon \ Nationaliteit.
     *
     * @param hisPersoonNationaliteitLijst lijst van historie voor groep Standaard
     */
    public void setHisPersoonNationaliteitLijst(final Set<HisPersoonNationaliteitModel> hisPersoonNationaliteitLijst) {
        this.hisPersoonNationaliteitLijst = hisPersoonNationaliteitLijst;
    }

}
