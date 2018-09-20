/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTest;

import org.junit.Test;

public class BrpPersoonslijstBuilderTest {

    private static final BrpHistorie historie = new BrpHistorie(new BrpDatum(20120203, null), null, BrpDatumTijd.fromDatum(20120203, null), null, null);
    private static final BrpStapel<BrpIdentificatienummersInhoud> idNummers = new BrpStapel<>(Arrays.asList(new BrpGroep<>(
        new BrpIdentificatienummersInhoud(new BrpLong(1234567890L), new BrpInteger(123456789)),
        historie,
        null,
        null,
        null)));
    private static final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsAanduiding = new BrpStapel<>(Arrays.asList(new BrpGroep<>(
        new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
        historie,
        null,
        null,
        null)));
    private static final BrpStapel<BrpGeboorteInhoud> geboorte = new BrpStapel<>(Arrays.asList(new BrpGroep<>(
        new BrpGeboorteInhoud(new BrpDatum(20120203, null), new BrpGemeenteCode(Short.parseShort("1234")), null, null, null, new BrpLandOfGebiedCode(
            Short.parseShort("6030")), null), historie, null, null, null)));
    private static final BrpStapel<BrpNaamgebruikInhoud> naamgebruik = new BrpStapel<>(Arrays.asList(new BrpGroep<>(new BrpNaamgebruikInhoud(
        BrpNaamgebruikCode.E,
        null,
        null,
        null,
        null,
        null,
        null,
        null), historie, null, null, null)));
    private static final BrpStapel<BrpInschrijvingInhoud> inschrijving = new BrpStapel<>(Arrays.asList(new BrpGroep<>(new BrpInschrijvingInhoud(
        new BrpDatum(20120203, null),
        new BrpLong(1L),
        BrpDatumTijd.fromDatum(20130101, null)), historie, null, null, null)));

    private static final BrpStapel<BrpAdresInhoud> adres = new BrpStapel<>(Arrays.asList(new BrpGroep<>(new BrpAdresInhoud(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null), historie, null, null, null)));

    public static BrpPersoonslijstBuilder maakMinimaleBuilder() {
        return maakMinimaleBuilder(null);
    }

    public static BrpPersoonslijstBuilder maakMinimaleBuilder(Long aNummer) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        if (aNummer == null) {
            builder.identificatienummersStapel(idNummers);
        } else {
            builder.identificatienummersStapel(new BrpStapel<>(Arrays.asList(new BrpGroep<>(new BrpIdentificatienummersInhoud(
                new BrpLong(aNummer),
                new BrpInteger(123456789)), historie, null, null, null))));
        }
        builder.geslachtsaanduidingStapel(geslachtsAanduiding);
        builder.geboorteStapel(geboorte);
        builder.naamgebruikStapel(naamgebruik);
        builder.inschrijvingStapel(inschrijving);
        builder.adresStapel(adres);

        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamComp =
                new BrpStapel<>(Arrays.asList(new BrpGroep<>(new BrpGeslachtsnaamcomponentInhoud(
                    new BrpString("van"),
                    new BrpCharacter(' '),
                    new BrpString("HorenZeggen"),
                    null,
                    null,
                    new BrpInteger(1)), historie, null, null, null)));
        builder.geslachtsnaamcomponentStapels(Arrays.asList(geslachtsnaamComp));
        return builder;
    }

    @Test
    public void testBuilderCompleet() {
        BrpPersoonslijstBuilder b = BrpPersoonslijstBuilderTest.maakMinimaleBuilder();
        b.build();
    }

    @Test
    public void testGeslachtsnaamcomponentStapel() {
        BrpPersoonslijstBuilder b = BrpPersoonslijstBuilderTest.maakMinimaleBuilder();
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getGeslachtsnaamcomponentStapels().size());
        b.geslachtsnaamcomponentStapel(BrpGeslachtsnaamcomponentInhoudTest.createStapel());
        assertEquals(2, pl.getGeslachtsnaamcomponentStapels().size());
    }

    @Test
    public void testNationaliteitenStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getNationaliteitStapels().size());
        b.nationaliteitStapel(BrpNationaliteitInhoudTest.createStapel(true));
        assertEquals(2, pl.getNationaliteitStapels().size());
    }

    @Test
    public void testReisDocumentenStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getReisdocumentStapels().size());
        b.reisdocumentStapel(BrpReisdocumentInhoudTest.createStapel());
        assertEquals(2, pl.getReisdocumentStapels().size());
    }

    @Test
    public void testRelatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getRelaties().size());
        b.relatie(BrpRelatieTest.createOuderRelatieZonderOuders());
        assertEquals(2, pl.getRelaties().size());
    }

    @Test
    public void testverificatieStapelStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getVerificatieStapels().size());
        b.verificatieStapel(BrpVerificatieInhoudTest.createStapel());
        assertEquals(2, pl.getVerificatieStapels().size());
    }

}
