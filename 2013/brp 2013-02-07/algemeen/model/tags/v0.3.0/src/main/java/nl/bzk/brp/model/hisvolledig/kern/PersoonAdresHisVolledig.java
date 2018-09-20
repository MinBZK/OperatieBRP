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

import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Persoon \ Adres.
 *
 */
@Entity
@Table(schema = "Kern", name = "PersAdres")
public class PersoonAdresHisVolledig {

    @Id
    @JsonProperty
    private Integer                   iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig        persoon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonAdres")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonAdresModel> hisPersoonAdresLijst;

    /**
     * Retourneert ID van Persoon \ Adres.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Adres.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Retourneert lijst van historie van Standaard van Persoon \ Adres.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisPersoonAdresModel> getHisPersoonAdresLijst() {
        return hisPersoonAdresLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Persoon \ Adres.
     *
     * @param hisPersoonAdresLijst lijst van historie voor groep Standaard
     */
    public void setHisPersoonAdresLijst(final Set<HisPersoonAdresModel> hisPersoonAdresLijst) {
        this.hisPersoonAdresLijst = hisPersoonAdresLijst;
    }

}
