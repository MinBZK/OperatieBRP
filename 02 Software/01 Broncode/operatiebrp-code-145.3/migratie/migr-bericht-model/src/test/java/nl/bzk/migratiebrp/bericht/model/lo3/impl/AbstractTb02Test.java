/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * Abstract class waar in een Lo3PersoonInhoud wordt gemaakt vor de Tb02 berichten tests.
 */
public abstract class AbstractTb02Test {

    public static Lo3PersoonInhoud maakLo3PersoonInhoud() {
        final Lo3String aNummer = Lo3String.wrap("2349326344");
        final Lo3String burgerservicenummer = new Lo3String("123456789");
        final Lo3String voornamen = new Lo3String("Henk Jan");
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final Lo3String voorvoegselGeslachtsnaam = new Lo3String("van");
        final Lo3String geslachtsnaam = new Lo3String("Dalen");
        final Lo3Datum geboortedatum = new Lo3Datum(2012_10_24);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("1234");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3String vorigANummer = null;
        final Lo3String volgendANummer = null;
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = new Lo3AanduidingNaamgebruikCode("E");

        return new Lo3PersoonInhoud(
                aNummer,
                burgerservicenummer,
                voornamen,
                adellijkeTitelPredikaatCode,
                voorvoegselGeslachtsnaam,
                geslachtsnaam,
                geboortedatum,
                geboorteGemeenteCode,
                geboorteLandCode,
                geslachtsaanduiding,
                vorigANummer,
                volgendANummer,
                aanduidingNaamgebruikCode);
    }
}
