/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * De conversietabel voor de converie van woonplaatsen.
 */
public final class WoonplaatsConversietabel implements Conversietabel<String, String> {

    /**
     * Toegestane woonplaatsen.
     */
    private final Set<String> woonplaatsnamen = new HashSet<>();

    /**
     * Maakt een WoonplaatsConversietabel object.
     * @param plaatsen de lijst met alle toegestane plaatsen
     */
    public WoonplaatsConversietabel(final Collection<Plaats> plaatsen) {
        for (final Plaats plaats : plaatsen) {
            woonplaatsnamen.add(plaats.getNaam());
        }
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
