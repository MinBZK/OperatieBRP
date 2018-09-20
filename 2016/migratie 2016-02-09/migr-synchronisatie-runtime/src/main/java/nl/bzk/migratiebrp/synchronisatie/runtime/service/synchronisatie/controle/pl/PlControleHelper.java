/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuutMetOnderzoek;

/**
 * Helper functies bij persoonslijst controle.
 */
public final class PlControleHelper {

    private PlControleHelper() {
        // / Niet instantieerbaar
    }

    /**
     * Null-safe vergelijking (null is gelijk aan null).
     *
     * @param waarde
     *            waarde
     * @param andereWaarde
     *            andere waarde
     * @return true als de waarden beide null zijn, of equals().
     */
    public static boolean isGelijk(final Object waarde, final Object andereWaarde) {
        if (waarde == null) {
            return andereWaarde == null;
        } else {
            return waarde.equals(andereWaarde);
        }
    }

    /**
     * Null-safe waarde teruggeef methode voor attributen met onderzoek.
     *
     * @param attribuut
     *            Het attribuut waarvan we de waarde willen teruggeven.
     * @return De waarde van het attribuut.
     */
    public static Object geefAttribuutWaarde(final BrpAttribuutMetOnderzoek attribuut) {
        if (attribuut == null) {
            return null;
        } else {
            return attribuut.getWaarde();
        }
    }
}
