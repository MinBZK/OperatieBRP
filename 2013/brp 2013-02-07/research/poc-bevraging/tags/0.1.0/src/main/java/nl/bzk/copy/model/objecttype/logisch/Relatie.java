/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.logisch;

import java.util.Set;

import nl.bzk.copy.model.RootObject;
import nl.bzk.copy.model.objecttype.logisch.basis.RelatieBasis;

/**
 * Interface voor objecttyp relatie.
 */
public interface Relatie extends RelatieBasis, RootObject {

    /**
     * Retourneert de ouder betrokkenheden uit de relatie (indien aanwezig).
     *
     * @return de ouder betrokkenheden uit de relatie.
     */
    Set<? extends nl.bzk.copy.model.objecttype.logisch.Betrokkenheid> getOuderBetrokkenheden();

    /**
     * Retourneert de kind betrokkenheid uit de relatie (indien aanwezig).
     *
     * @return de ouder betrokkenheden uit de relatie.
     */
    nl.bzk.copy.model.objecttype.logisch.Betrokkenheid getKindBetrokkenheid();

    /**
     * Retourneert een collectie van de partner betrokkenheiden uit de relatie (indien aanwezig).
     *
     * @return de partner betrokkenheid uit de relatie.
     */
    Set<? extends nl.bzk.copy.model.objecttype.logisch.Betrokkenheid> getPartnerBetrokkenheden();

    /**
     * Test of een huwelijk of geregisteerd partnerschap de aanvang land Nederland is.
     * Zo ja, kunnen meerdere business regels op toegepast worden .
     *
     * @return true als soort is (geregistreerd partnerschap dan wel huwelijk) en landcode aanvang is NL. false anders.
     */
    boolean isPartnershapVoltrokkenInNederland();

}
