/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de BRP regels en LO3 foutmeldingen.
 *
 * TODO: Dit moet een dynamische conversietabel worden.
 */
public final class RegelConversietabel implements Conversietabel<Character, String> {

    private static final Map<String, Character> CONVERSIE_TABEL = new HashMap<String, Character>() {
        private static final long serialVersionUID = 1L;

        {
            // UCS-LV.1.AL
            put("R1260", 'X');
            put("BRLV0017", 'X');
            put("R1261", 'X');
            put("BRLV0018", 'X');
            put("R1262", 'X');
            put("BRLV0019", 'X');
            put("R1263", 'X');
            put("BRLV0020", 'X');
            put("R1264", 'X');
            put("BRLV0021", 'X');
            put("R2055", 'X');
            put("R2056", 'X');

            // UCS-LV.1.AU
            put("R1257", 'X');
            put("BRLV0005", 'X');
            put("R1258", 'X');
            put("BRLV0043", 'X');
            put("R2052", 'X');

            // UCS-SA.1.PA
            put("R1407", 'R');
            put("BRLV0014", 'R');
            put("R1339", 'H');
            put("BRLV0031", 'H');
            put("R1402", 'I');
            put("BRLV0003", 'I');
            put("R1538", 'G');
            put("BRLV0006", 'G');
            put("R1539", 'G');
            put("BRLV0022", 'G');

            // UCS-SA.1.VA
            put("R1401", 'I');
            put("BRLV0001", 'I');

            // Code
            put("BRBV0006", 'G');
        }
    };

    @Override
    public String converteerNaarBrp(final Character input) {
        throw new IllegalArgumentException("Vertaling van LO3 naar BRP niet ondersteund");
    }

    @Override
    public boolean valideerLo3(final Character input) {
        return false;
    }

    @Override
    public Character converteerNaarLo3(final String input) {
        return CONVERSIE_TABEL.get(input);
    }

    @Override
    public boolean valideerBrp(final String input) {
        return converteerNaarLo3(input) != null;
    }

}
