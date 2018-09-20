/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.logisch.basis.RelatieBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractRelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;

/**
 *
 */
//TODO: rename de entity naam terug naar de echte
@Entity
@Table(schema = "Kern", name = "Relatie")
@SuppressWarnings("serial")
public class RelatieModel extends AbstractRelatieModel implements Relatie, RootObject {

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param relatie Object type dat gekopieerd dient te worden.
     */
    public RelatieModel(final RelatieBasis relatie) {
        super(relatie);
    }

    /**
     * Standaard lege constructor (niet public en puur voor gebruik binnen andere frameworks).
     */
    protected RelatieModel() {
    }

    /**
     * Retourneert kind betrokkenheid in deze relatie.
     * @return Kind betrokkenheid.
     */
    @Override
    @Transient
    public BetrokkenheidModel getKindBetrokkenheid() {
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                return betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Retourneert ouder betrokkenheden in deze relatie.
     * @return Ouder betrokkenheden.
     */
    @Override
    @Transient
    public Set<BetrokkenheidModel> getOuderBetrokkenheden() {
        Set<BetrokkenheidModel> ouderBetrokkenheden = new HashSet<BetrokkenheidModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
                ouderBetrokkenheden.add(betrokkenheid);
            }
        }
        return ouderBetrokkenheden;
    }

    @Transient
    @Override
    public Set<BetrokkenheidModel>  getPartnerBetrokkenheden() {
        Set<BetrokkenheidModel> partnerBetrokkenheden = new HashSet<BetrokkenheidModel>();
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betrokkenheid.getRol()) {
                partnerBetrokkenheden.add(betrokkenheid);
            }
        }
        return partnerBetrokkenheden;
    }

    @Transient
    @Override
    public boolean isPartnershapVoltrokkenInNederland() {
        boolean retval = false;
        if ((getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP || getSoort() == SoortRelatie.HUWELIJK)
            && (getGegevens().getLandAanvang() != null))
        {
            retval = BrpConstanten.NL_LAND_CODE.equals(getGegevens().getLandAanvang().getCode());
        }
        return retval;
    }

    /**
     * Retourneert partner betrokkenheden in deze relatie.
     * @return Partner betrokkenheden.
     */
    @Transient
    public BetrokkenheidModel  getPartnerBetrokkenheid() {
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betrokkenheid.getRol()) {
                return betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Geeft aan of deze relatie een huwelijk is.
     * @return True indien huwelijk anders false.
     */
    public boolean isHuwelijk() {
        return SoortRelatie.HUWELIJK == getSoort();
    }

    /**
     * Geeft aan of deze relatie een geregistreerd partnerschap is.
     * @return True indien geregistreerd partnerschap anders false.
     */
    public boolean isGeregistreerdPartnerschap() {
        return SoortRelatie.GEREGISTREERD_PARTNERSCHAP == getSoort();
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenheidToe(final BetrokkenheidModel betr) { }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenhedenToe(final Set<BetrokkenheidModel> betr) { }

}
