/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Persoon \ Voornaam.
 *
 */
@Entity
@Table(schema = "Kern", name = "PersVoornaam")
public class PersoonVoornaamHisVolledig {

    @Id
    @JsonProperty
    private Integer                      iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonHisVolledig           persoon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonVoornaam")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonVoornaamModel> hisPersoonVoornaamLijst;

    /**
     * Retourneert ID van Persoon \ Voornaam.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Voornaam.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Retourneert lijst van historie van Standaard van Persoon \ Voornaam.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisPersoonVoornaamModel> getHisPersoonVoornaamLijst() {
        return hisPersoonVoornaamLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Persoon \ Voornaam.
     *
     * @param hisPersoonVoornaamLijst lijst van historie voor groep Standaard
     */
    public void setHisPersoonVoornaamLijst(final Set<HisPersoonVoornaamModel> hisPersoonVoornaamLijst) {
        this.hisPersoonVoornaamLijst = hisPersoonVoornaamLijst;
    }

}
