/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Persoon \ Indicatie.
 *
 */
@Entity
@Table(schema = "Kern", name = "PersIndicatie")
public class PersoonIndicatieHisVolledig {

    @Id
    @JsonProperty
    private Integer                       iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonHisVolledig            persoon;

    @Enumerated
    @Column(name = "Srt", updatable = false, insertable = false)
    @JsonProperty
    private SoortIndicatie                soort;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonIndicatie")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonIndicatieModel> hisPersoonIndicatieLijst;

    /**
     * Retourneert ID van Persoon \ Indicatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Indicatie.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Soort van Persoon \ Indicatie.
     *
     * @return Soort.
     */
    public SoortIndicatie getSoort() {
        return soort;
    }

    /**
     * Retourneert lijst van historie van Standaard van Persoon \ Indicatie.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisPersoonIndicatieModel> getHisPersoonIndicatieLijst() {
        return hisPersoonIndicatieLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Persoon \ Indicatie.
     *
     * @param hisPersoonIndicatieLijst lijst van historie voor groep Standaard
     */
    public void setHisPersoonIndicatieLijst(final Set<HisPersoonIndicatieModel> hisPersoonIndicatieLijst) {
        this.hisPersoonIndicatieLijst = hisPersoonIndicatieLijst;
    }

}
