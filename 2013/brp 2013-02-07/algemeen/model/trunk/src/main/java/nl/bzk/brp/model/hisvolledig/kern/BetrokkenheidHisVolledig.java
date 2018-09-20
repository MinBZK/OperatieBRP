/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Betrokkenheid.
 *
 */
@Entity
@Table(schema = "Kern", name = "Betr")
public class BetrokkenheidHisVolledig {

    @Id
    @JsonProperty
    private Integer                          iD;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieHisVolledig               relatie;

    @Enumerated
    @Column(name = "Rol", updatable = false, insertable = false)
    @JsonProperty
    private SoortBetrokkenheid               rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig               persoon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "betrokkenheid")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisOuderOuderschapModel>     hisOuderOuderschapLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "betrokkenheid")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisOuderOuderlijkGezagModel> hisOuderOuderlijkGezagLijst;

    /**
     * Retourneert ID van Betrokkenheid.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van Betrokkenheid.
     *
     * @return Relatie.
     */
    public RelatieHisVolledig getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Rol van Betrokkenheid.
     *
     * @return Rol.
     */
    public SoortBetrokkenheid getRol() {
        return rol;
    }

    /**
     * Retourneert Persoon van Betrokkenheid.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Retourneert lijst van historie van Ouderschap van Betrokkenheid.
     *
     * @return lijst van historie voor groep Ouderschap
     */
    public Set<HisOuderOuderschapModel> getHisOuderOuderschapLijst() {
        return hisOuderOuderschapLijst;
    }

    /**
     * Retourneert lijst van historie van Ouderlijk gezag van Betrokkenheid.
     *
     * @return lijst van historie voor groep Ouderlijk gezag
     */
    public Set<HisOuderOuderlijkGezagModel> getHisOuderOuderlijkGezagLijst() {
        return hisOuderOuderlijkGezagLijst;
    }

    /**
     * Zet lijst van historie van Ouderschap van Betrokkenheid.
     *
     * @param hisOuderOuderschapLijst lijst van historie voor groep Ouderschap
     */
    public void setHisOuderOuderschapLijst(final Set<HisOuderOuderschapModel> hisOuderOuderschapLijst) {
        this.hisOuderOuderschapLijst = hisOuderOuderschapLijst;
    }

    /**
     * Zet lijst van historie van Ouderlijk gezag van Betrokkenheid.
     *
     * @param hisOuderOuderlijkGezagLijst lijst van historie voor groep Ouderlijk gezag
     */
    public void setHisOuderOuderlijkGezagLijst(final Set<HisOuderOuderlijkGezagModel> hisOuderOuderlijkGezagLijst) {
        this.hisOuderOuderlijkGezagLijst = hisOuderOuderlijkGezagLijst;
    }

}
