/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;

public class VoorvoegselScheidingstekenConversietabel implements
        Conversietabel<String, VoorvoegselScheidingstekenPaar> {

    /** Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk). */
    public static final String LO3_NIET_VALIDE_UITZONDERING = "qqq";

    @Override
    public VoorvoegselScheidingstekenPaar converteerNaarBrp(final String input) {
        if (input == null) {
            return new VoorvoegselScheidingstekenPaar(null, null);
        }
        final int indexApostrof = input.lastIndexOf('\'');
        final String voorvoegsel;
        if (indexApostrof == input.length() - 1) {
            voorvoegsel = input.substring(0, indexApostrof);
        } else {
            voorvoegsel = input;
        }

        final char scheidingsteken = input.endsWith("'") ? '\'' : ' ';

        return new VoorvoegselScheidingstekenPaar(voorvoegsel, scheidingsteken);
    }

    @Override
    public String converteerNaarLo3(final VoorvoegselScheidingstekenPaar input) {
        if (input == null) {
            return null;
        }
        final String brpVoorvoegsel = input.getVoorvoegsel();
        final Character brpScheidingsteken = input.getScheidingsteken();

        final String lo3Voorvoegsel;
        if (new Character(' ').equals(brpScheidingsteken)) {
            // DEF051 scheidingsteken is gevuld met een spatie
            lo3Voorvoegsel = brpVoorvoegsel;
        } else if (brpScheidingsteken != null) {
            // DEF052 scheidingsteken is gevuld, maar geen spatie
            lo3Voorvoegsel = brpVoorvoegsel + brpScheidingsteken;
        } else {
            // DEF053 geen scheidingsteken (dus ook geen voorvoegsel)
            lo3Voorvoegsel = null;
        }
        return lo3Voorvoegsel;
    }

    @Override
    public boolean valideerLo3(final String input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input);
    }

    @Override
    public boolean valideerBrp(final VoorvoegselScheidingstekenPaar input) {
        return true;
    }

}
