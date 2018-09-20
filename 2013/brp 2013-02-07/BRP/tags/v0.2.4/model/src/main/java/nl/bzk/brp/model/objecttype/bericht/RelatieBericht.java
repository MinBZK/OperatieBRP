/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.basis.AbstractRelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;


/**
 *
 */
@SuppressWarnings("serial")
public class RelatieBericht extends AbstractRelatieBericht implements Relatie, RootObject {


    /**
     * De unieke (database) id van de relatie
     */
    private Integer id;

    @Override
    public Set<BetrokkenheidBericht> getOuderBetrokkenheden() {
        final Set<BetrokkenheidBericht> ouderBetr = new HashSet<BetrokkenheidBericht>();
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == betrokkenheidBericht.getRol()) {
                    ouderBetr.add(betrokkenheidBericht);
                }
            }
        }
        return ouderBetr;
    }

    @Override
    public BetrokkenheidBericht getKindBetrokkenheid() {
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (SoortBetrokkenheid.KIND == betrokkenheidBericht.getRol()) {
                    return betrokkenheidBericht;
                }
            }
        }
        return null;
    }

    /**
     * Retourneert de Partner betrokkenheid uit de relatie (indien aanwezig).
     *
     * @return de partner betrokkenheden uit de relatie.
     */
    @Override
    public Set<BetrokkenheidBericht> getPartnerBetrokkenheden() {
        final Set<BetrokkenheidBericht> partnerBetr = new HashSet<BetrokkenheidBericht>();
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (SoortBetrokkenheid.PARTNER == betrokkenheidBericht.getRol()) {
                    partnerBetr.add(betrokkenheidBericht);
                }
            }
        }
        return partnerBetr;
    }

    /**
     * Test of een huwelijk of geregisteerd partnerschap de aanvang land Nederland is.
     * Zo ja, kunnen meerdere business regels op toegepast worden .
     * @return true als soort is (geregistreerd partnerschap dan wel huwelijk) en landcode aanvang is NL. false anders.
     */
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

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
