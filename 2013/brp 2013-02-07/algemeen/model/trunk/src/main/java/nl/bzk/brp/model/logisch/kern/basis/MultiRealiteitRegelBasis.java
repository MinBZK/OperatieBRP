/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMultiRealiteitRegel;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;


/**
 * De regel waarmee de Multirealiteit wordt vastgelegd.
 *
 * De BRP bevat normaal gesproken op elk moment in de tijd ��n consistent beeld van heden en verleden van de
 * werkelijkheid. In uitzonderlijke gevallen dient de BRP in staat te zijn verschillende, onderling conflicterende,
 * werkelijkheden vast te leggen.
 * Dit gebeurt door de constructie van Multirealiteit.
 * Zie voor verdere toelichting het LO BRP.
 *
 *
 *
 */
public interface MultiRealiteitRegelBasis extends ObjectType {

    /**
     * Retourneert Geldig voor van Multi-realiteit regel.
     *
     * @return Geldig voor.
     */
    Persoon getGeldigVoor();

    /**
     * Retourneert Soort van Multi-realiteit regel.
     *
     * @return Soort.
     */
    SoortMultiRealiteitRegel getSoort();

    /**
     * Retourneert Persoon van Multi-realiteit regel.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Multi-realiteit persoon van Multi-realiteit regel.
     *
     * @return Multi-realiteit persoon.
     */
    Persoon getMultiRealiteitPersoon();

    /**
     * Retourneert Relatie van Multi-realiteit regel.
     *
     * @return Relatie.
     */
    Relatie getRelatie();

    /**
     * Retourneert Betrokkenheid van Multi-realiteit regel.
     *
     * @return Betrokkenheid.
     */
    Betrokkenheid getBetrokkenheid();

}
