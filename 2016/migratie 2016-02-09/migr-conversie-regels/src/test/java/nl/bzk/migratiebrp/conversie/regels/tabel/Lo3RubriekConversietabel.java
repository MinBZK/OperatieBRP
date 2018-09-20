/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractLo3RubriekConversietabel;

/**
 * Dummy conversietabel implementatie omdat dit wordt afgedwonengen door de interface klasse.
 */
public class Lo3RubriekConversietabel extends AbstractLo3RubriekConversietabel {

    private static final Pattern EXPRESSIE_TEKST = Pattern.compile("[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean valideerLo3(final String input) {

        final Matcher matcher = EXPRESSIE_TEKST.matcher(input);

        return matcher.matches();
    }

    @Override
    public boolean valideerBrp(final String input) {
        return true;
    }

}
