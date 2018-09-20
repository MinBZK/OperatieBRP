/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.bericht;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.copy.constanten.BrpConstanten;
import nl.bzk.copy.model.RootObject;
import nl.bzk.copy.model.objecttype.bericht.basis.AbstractRelatieBericht;
import nl.bzk.copy.model.objecttype.logisch.Relatie;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortRelatie;


/**
 *
 */
@SuppressWarnings("serial")
public class RelatieBericht extends AbstractRelatieBericht implements Relatie, RootObject {

    @Override
    public Set<nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht> getOuderBetrokkenheden() {
        final Set<nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht> ouderBetr =
                new HashSet<nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht>();
        if (getBetrokkenheden() != null) {
            for (nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == betrokkenheidBericht.getRol()) {
                    ouderBetr.add(betrokkenheidBericht);
                }
            }
        }
        return ouderBetr;
    }

    @Override
    public nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht getKindBetrokkenheid() {
        if (getBetrokkenheden() != null) {
            for (nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
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
    public Set<nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht> getPartnerBetrokkenheden() {
        final Set<nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht> partnerBetr =
                new HashSet<nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht>();
        if (getBetrokkenheden() != null) {
            for (nl.bzk.copy.model.objecttype.bericht.BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
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
     *
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
}
