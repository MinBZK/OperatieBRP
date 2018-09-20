/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import java.util.Collections;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

public class Uc307EsbTest extends AbstractEsbTest {

    @Test
    public void testHappyFlow() {
        // //////////////////////////////////////////////////////////////////
        // LO3 GBA WERELD
        // //////////////////////////////////////////////////////////////////

        // maak Tb01-bericht
        final Bericht input = new Bericht();
        // final input.set
        input.setMessageId(BerichtId.generateMessageId());

        final Tb01Bericht tb01Bericht = new Tb01Bericht();

        // TODO: fill tb01Bericht met correcte data...
        final Lo3Persoonslijst lo3Persoonslijst = maakGeboorte(maakLo3PersoonInhoudVoorKind());
        tb01Bericht.setLo3Persoonslijst(lo3Persoonslijst);

        final String tb01String = tb01Bericht.format();
        System.out.println("Tb01Bericht: " + tb01String);
        input.setInhoud(tb01String);

        // verstuur Tb01-bericht
        verstuurVospgBericht(input);

        final Bericht converteerAntwoordBericht = ontvangSyncBericht();
        Assert.assertNotNull(converteerAntwoordBericht);
    }

    private static Lo3PersoonInhoud maakLo3PersoonInhoudVoorKind() {
        final Long aNummer = null;
        final Long burgerservicenummer = null;
        final String voornamen = "Klaas";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = null;
        final String geslachtsnaam = "Janssen";
        final Lo3Datum geboortedatum = new Lo3Datum(20121126);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("0518");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = new Lo3AanduidingNaamgebruikCode("E");
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }

    private static Lo3Persoonslijst maakGeboorte(final Lo3PersoonInhoud lo3PersoonInhoud) {
        final Lo3Historie historie = Lo3Builder.createLo3Historie("S", 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<Lo3PersoonInhoud>(lo3PersoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                new Lo3Stapel<Lo3PersoonInhoud>(Collections.singletonList(persoon1));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        // final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaats = VerplichteStapel.createVerblijfplaatsStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .build();
    }
}
