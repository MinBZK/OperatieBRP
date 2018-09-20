/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractNaamOpenbareRuimteConversietabel;

public class NaamOpenbareRuimteConversietabel extends AbstractNaamOpenbareRuimteConversietabel implements
        Conversietabel<String, String> {

    @Override
    public boolean valideerLo3(final String input) {
        return true;
    }

    @Override
    public boolean valideerBrp(final String input) {
        return true;
    }

}
