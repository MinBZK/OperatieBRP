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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.operationeel.kern.HisErkenningOngeborenVruchtModel;
import nl.bzk.brp.model.operationeel.kern.HisHuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HisNaamskeuzeOngeborenVruchtModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * HisVolledig klasse voor Relatie.
 *
 */
@Entity
@Table(schema = "Kern", name = "Relatie")
public class RelatieHisVolledig {

    @Id
    @JsonProperty
    private Integer                                        iD;

    @Enumerated
    @Column(name = "Srt", updatable = false, insertable = false)
    @JsonProperty
    private SoortRelatie                                   soort;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "relatie")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisErkenningOngeborenVruchtModel>          hisErkenningOngeborenVruchtLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "relatie")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisHuwelijkGeregistreerdPartnerschapModel> hisHuwelijkGeregistreerdPartnerschapLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "relatie")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisNaamskeuzeOngeborenVruchtModel>         hisNaamskeuzeOngeborenVruchtLijst;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatie")
    private Set<BetrokkenheidHisVolledig>                  betrokkenheden;

    /**
     * Retourneert ID van Relatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Relatie.
     *
     * @return Soort.
     */
    public SoortRelatie getSoort() {
        return soort;
    }

    /**
     * Retourneert lijst van historie van Standaard van Relatie.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisErkenningOngeborenVruchtModel> getHisErkenningOngeborenVruchtLijst() {
        return hisErkenningOngeborenVruchtLijst;
    }

    /**
     * Retourneert lijst van historie van Standaard van Relatie.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisHuwelijkGeregistreerdPartnerschapModel> getHisHuwelijkGeregistreerdPartnerschapLijst() {
        return hisHuwelijkGeregistreerdPartnerschapLijst;
    }

    /**
     * Retourneert lijst van historie van Standaard van Relatie.
     *
     * @return lijst van historie voor groep Standaard
     */
    public Set<HisNaamskeuzeOngeborenVruchtModel> getHisNaamskeuzeOngeborenVruchtLijst() {
        return hisNaamskeuzeOngeborenVruchtLijst;
    }

    /**
     * Retourneert lijst van Betrokkenheid.
     *
     * @return lijst van Betrokkenheid
     */
    public Set<BetrokkenheidHisVolledig> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Zet lijst van historie van Standaard van Relatie.
     *
     * @param hisErkenningOngeborenVruchtLijst lijst van historie voor groep Standaard
     */
    public void setHisErkenningOngeborenVruchtLijst(
            final Set<HisErkenningOngeborenVruchtModel> hisErkenningOngeborenVruchtLijst)
    {
        this.hisErkenningOngeborenVruchtLijst = hisErkenningOngeborenVruchtLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Relatie.
     *
     * @param hisHuwelijkGeregistreerdPartnerschapLijst lijst van historie voor groep Standaard
     */
    public void setHisHuwelijkGeregistreerdPartnerschapLijst(
            final Set<HisHuwelijkGeregistreerdPartnerschapModel> hisHuwelijkGeregistreerdPartnerschapLijst)
    {
        this.hisHuwelijkGeregistreerdPartnerschapLijst = hisHuwelijkGeregistreerdPartnerschapLijst;
    }

    /**
     * Zet lijst van historie van Standaard van Relatie.
     *
     * @param hisNaamskeuzeOngeborenVruchtLijst lijst van historie voor groep Standaard
     */
    public void setHisNaamskeuzeOngeborenVruchtLijst(
            final Set<HisNaamskeuzeOngeborenVruchtModel> hisNaamskeuzeOngeborenVruchtLijst)
    {
        this.hisNaamskeuzeOngeborenVruchtLijst = hisNaamskeuzeOngeborenVruchtLijst;
    }

    /**
     * Zet lijst van Betrokkenheid.
     *
     * @param betrokkenheden lijst van Betrokkenheid
     */
    public void setBetrokkenheden(final Set<BetrokkenheidHisVolledig> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

}
