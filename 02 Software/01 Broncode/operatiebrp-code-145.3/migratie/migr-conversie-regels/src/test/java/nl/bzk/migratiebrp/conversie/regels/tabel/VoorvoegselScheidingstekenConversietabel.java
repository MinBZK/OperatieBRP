/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

public class VoorvoegselScheidingstekenConversietabel implements Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final String LO3_NIET_VALIDE_UITZONDERING = "qqq";

    @Override
    public VoorvoegselScheidingstekenPaar converteerNaarBrp(final Lo3String input) {
        final String lo3Voorvoegsel = Lo3String.unwrap(input);
        if (lo3Voorvoegsel == null) {
            return new VoorvoegselScheidingstekenPaar(null, null);
        }
        final int indexApostrof = lo3Voorvoegsel.lastIndexOf('\'');
        final String voorvoegsel;
        if (indexApostrof == lo3Voorvoegsel.length() - 1) {
            voorvoegsel = lo3Voorvoegsel.substring(0, indexApostrof);
        } else {
            voorvoegsel = lo3Voorvoegsel;
        }

        final char scheidingsteken = lo3Voorvoegsel.endsWith("'") ? '\'' : ' ';

        return new VoorvoegselScheidingstekenPaar(new BrpString(voorvoegsel), new BrpCharacter(scheidingsteken));
    }

    @Override
    public Lo3String converteerNaarLo3(final VoorvoegselScheidingstekenPaar input) {
        if (input == null) {
            return null;
        }
        final String brpVoorvoegsel = BrpString.unwrap(input.getVoorvoegsel());
        final Character brpScheidingsteken = BrpCharacter.unwrap(input.getScheidingsteken());

        final String lo3Voorvoegsel;
        if (Character.valueOf(' ').equals(brpScheidingsteken)) {
            // DEF051 scheidingsteken is gevuld met een spatie
            lo3Voorvoegsel = brpVoorvoegsel;
        } else if (brpScheidingsteken != null) {
            // DEF052 scheidingsteken is gevuld, maar geen spatie
            lo3Voorvoegsel = brpVoorvoegsel + brpScheidingsteken;
        } else {
            // DEF053 geen scheidingsteken (dus ook geen voorvoegsel)
            lo3Voorvoegsel = null;
        }
        return Lo3String.wrap(lo3Voorvoegsel);
    }

    @Override
    public boolean valideerLo3(final Lo3String input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(Lo3String.unwrap(input));
    }

    @Override
    public boolean valideerBrp(final VoorvoegselScheidingstekenPaar input) {
        return true;
    }

}
