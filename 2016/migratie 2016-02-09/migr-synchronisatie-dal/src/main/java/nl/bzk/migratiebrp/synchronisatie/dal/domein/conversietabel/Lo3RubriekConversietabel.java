/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.Collection;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractLo3RubriekConversietabel;

/**
 * LO3 Rubriek conversie tabel.
 */
public final class Lo3RubriekConversietabel extends AbstractLo3RubriekConversietabel {

    private final Collection<String> geldigeRubrieken;

    /**
     * Constructor.
     *
     * @param geldigeRubrieken
     *            geldige rubrieken.
     */
    public Lo3RubriekConversietabel(final Collection<String> geldigeRubrieken) {
        this.geldigeRubrieken = geldigeRubrieken;
    }

    @Override
    public boolean valideerLo3(final String input) {
        return input == null || geldigeRubrieken.contains(input);
    }

    @Override
    public boolean valideerBrp(final String input) {
        return input == null || geldigeRubrieken.contains(input);
    }

}
