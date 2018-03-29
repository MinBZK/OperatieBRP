/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * De conversietabel voor de converie van 'LO3 Adellijke Titel/Predikaat'-code naar 'BRP Adellijke Titel,
 * Predikaat'-paar en vice versa.
 */
public final class WoonplaatsnaamConversietabel implements Conversietabel<String, String> {

    private final Set<String> woonplaatsnamen = new HashSet<>();

    /**
     * Maakt een AdellijkeTitelPredikaatConversietabel object.
     * @param woonplaatsnamen de lijst met titel conversies
     */
    public WoonplaatsnaamConversietabel(final Collection<String> woonplaatsnamen) {
        this.woonplaatsnamen.addAll(woonplaatsnamen);
    }

    @Override
    public String converteerNaarBrp(final String input) {
        return input;
    }

    @Override
    public boolean valideerLo3(final String input) {
        return woonplaatsnamen.contains(input);
    }

    @Override
    public String converteerNaarLo3(final String input) {
        return input;
    }

    @Override
    public boolean valideerBrp(final String input) {
        return woonplaatsnamen.contains(input);
    }
}
