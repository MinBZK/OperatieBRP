/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;

public abstract class AbstractNaamOpenbareRuimteConversietabel implements Conversietabel<String, String> {

    @Override
    public final String converteerNaarLo3(final String input) {
        return input;
    }

    @Override
    public final String converteerNaarBrp(final String input) {
        return input;
    }
}
