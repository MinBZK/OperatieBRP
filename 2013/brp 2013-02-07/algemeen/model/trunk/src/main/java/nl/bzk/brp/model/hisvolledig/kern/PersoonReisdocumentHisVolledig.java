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

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Persoon \ Reisdocument.
 *
 */
@Entity
@Table(schema = "Kern", name = "PersReisdoc")
public class PersoonReisdocumentHisVolledig {

    @Id
    @JsonProperty
    private Integer                          iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonHisVolledig               persoon;

    @ManyToOne
    @JoinColumn(name = "Srt")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private SoortNederlandsReisdocument      soort;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonReisdocument")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonReisdocumentModel> hisPersoonReisdocumentLijst;

    /**
     * Retourneert ID van Persoon \ Reisdocument.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Reisdocument.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Soort van Persoon \ Reisdocument.
     *
     * @return Soort.
     */
    public SoortNederlandsReisdocument getSoort() {
        return soort;
    }

    /**
     * Retourneert lijst van historie van Standaard van Persoon \ Reisdocument.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisPersoonReisdocumentModel> getHisPersoonReisdocumentLijst() {
        return hisPersoonReisdocumentLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Persoon \ Reisdocument.
     *
     * @param hisPersoonReisdocumentLijst lijst van historie voor groep Standaard
     */
    public void setHisPersoonReisdocumentLijst(final Set<HisPersoonReisdocumentModel> hisPersoonReisdocumentLijst) {
        this.hisPersoonReisdocumentLijst = hisPersoonReisdocumentLijst;
    }

}
