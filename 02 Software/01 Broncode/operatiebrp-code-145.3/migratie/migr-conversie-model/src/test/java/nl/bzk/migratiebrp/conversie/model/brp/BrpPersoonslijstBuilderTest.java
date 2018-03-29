/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTestUtil;
import org.junit.Test;

public class BrpPersoonslijstBuilderTest {

    private static final BrpHistorie historie = new BrpHistorie(new BrpDatum(20120203, null), null, BrpDatumTijd.fromDatum(20120203, null), null, null);
    private static final BrpStapel<BrpIdentificatienummersInhoud> idNummers = new BrpStapel<>(Collections.singletonList(new BrpGroep<>(
            new BrpIdentificatienummersInhoud(new BrpString("1234567890"), new BrpString("123456789")),
            historie,
            null,
            null,
            null)));
    private static final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsAanduiding = new BrpStapel<>(Collections.singletonList(new BrpGroep<>(
            new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN),
            historie,
            null,
            null,
            null)));
    private static final BrpStapel<BrpGeboorteInhoud> geboorte = new BrpStapel<>(Collections.singletonList(new BrpGroep<>(
            new BrpGeboorteInhoud(new BrpDatum(20120203, null), new BrpGemeenteCode("1234"), null, null, null, new BrpLandOfGebiedCode(
                    "6030"), null), historie, null, null, null)));
    private static final BrpStapel<BrpNaamgebruikInhoud> naamgebruik = new BrpStapel<>(Collections.singletonList(new BrpGroep<>(new BrpNaamgebruikInhoud(
            BrpNaamgebruikCode.E,
            null,
            null,
            null,
            null,
            null,
            null,
            null), historie, null, null, null)));
    private static final BrpStapel<BrpInschrijvingInhoud> inschrijving = new BrpStapel<>(Collections.singletonList(new BrpGroep<>(new BrpInschrijvingInhoud(
            new BrpDatum(20120203, null),
            new BrpLong(1L),
            BrpDatumTijd.fromDatum(20130101, null)), historie, null, null, null)));

    private static final BrpStapel<BrpAdresInhoud> adres = new BrpStapel<>(Collections.singletonList(new BrpGroep<>(new BrpAdresInhoud(
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

    private static BrpPersoonslijstBuilder maakMinimaleBuilder() {
        return maakMinimaleBuilder(null);
    }

    public static BrpPersoonslijstBuilder maakMinimaleBuilder(final String aNummer) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        if (aNummer == null) {
            builder.identificatienummersStapel(idNummers);
        } else {
            builder.identificatienummersStapel(new BrpStapel<>(Collections.singletonList(new BrpGroep<>(new BrpIdentificatienummersInhoud(
                    new BrpString(aNummer), new BrpString("123456789")), historie, null, null, null))));
        }
        builder.geslachtsaanduidingStapel(geslachtsAanduiding);
        builder.geboorteStapel(geboorte);
        builder.naamgebruikStapel(naamgebruik);
        builder.inschrijvingStapel(inschrijving);
        builder.adresStapel(adres);

        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamComp =
                new BrpStapel<>(Collections.singletonList(new BrpGroep<>(new BrpGeslachtsnaamcomponentInhoud(
                        new BrpString("van"),
                        new BrpCharacter(' '),
                        new BrpString("HorenZeggen"),
                        null,
                        null,
                        new BrpInteger(1)), historie, null, null, null)));
        builder.geslachtsnaamcomponentStapels(Collections.singletonList(geslachtsnaamComp));
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
        b.geslachtsnaamcomponentStapel(null);
        assertEquals(2, pl.getGeslachtsnaamcomponentStapels().size());
    }

    @Test
    public void testNationaliteitenStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getNationaliteitStapels().size());
        b.nationaliteitStapel(BrpNationaliteitInhoudTest.createStapel(true));
        assertEquals(2, pl.getNationaliteitStapels().size());
        b.nationaliteitStapel(null);
        assertEquals(2, pl.getNationaliteitStapels().size());
    }

    @Test
    public void testReisDocumentenStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getReisdocumentStapels().size());
        b.reisdocumentStapel(BrpReisdocumentInhoudTest.createStapel());
        assertEquals(2, pl.getReisdocumentStapels().size());
        b.reisdocumentStapel(null);
        assertEquals(2, pl.getReisdocumentStapels().size());
    }

    @Test
    public void testRelatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getRelaties().size());
        b.relatie(BrpRelatieTest.createOuderRelatieZonderOuders());
        assertEquals(2, pl.getRelaties().size());
        b.relatie(null);
        assertEquals(2, pl.getRelaties().size());
    }

    @Test
    public void testverificatieStapelStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getVerificatieStapels().size());
        b.verificatieStapel(BrpVerificatieInhoudTestUtil.createStapel());
        assertEquals(2, pl.getVerificatieStapels().size());
        b.verificatieStapel(null);
        assertEquals(2, pl.getVerificatieStapels().size());
    }

    @Test
    public void testBuitenlandsPersoonsnummerStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getBuitenlandsPersoonsnummerStapels().size());

        b.buitenlandsPersoonsnummerStapel(BrpBuitenlandsPersoonsnummerInhoudTest.createStapel(true));
        assertEquals(2, pl.getBuitenlandsPersoonsnummerStapels().size());

        b.buitenlandsPersoonsnummerStapel(null);
        assertEquals(2, pl.getBuitenlandsPersoonsnummerStapels().size());
    }

    @Test
    public void testadministratieveHandelingId() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertNull(pl.getAdministratieveHandelingId());

        b.administratieveHandelingId(2L);
        pl = b.build();
        assertEquals(2, pl.getAdministratieveHandelingId().intValue());

    }

    @Test
    public void testpersoonVersie() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getPersoonVersie().intValue());

        b.persoonVersie(2L);
        pl = b.build();
        assertEquals(2, pl.getPersoonVersie().intValue());

    }

    @Test
    public void testpersoonAfgeleidAdministratiefStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getPersoonAfgeleidAdministratiefStapel().size());

        b.persoonAfgeleidAdministratiefStapel(null);
        pl = b.build();
        assertNull(pl.getPersoonAfgeleidAdministratiefStapel());

    }

    @Test
    public void testbehandeldAlsNederlanderIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getBehandeldAlsNederlanderIndicatieStapel().size());

        b.behandeldAlsNederlanderIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getBehandeldAlsNederlanderIndicatieStapel());

    }

    @Test
    public void testonverwerktDocumentAanwezigIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getOnverwerktDocumentAanwezigIndicatieStapel().size());

        b.onverwerktDocumentAanwezigIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getOnverwerktDocumentAanwezigIndicatieStapel());
    }

    @Test
    public void testsignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel().size());

        b.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(null);
        pl = b.build();
        assertNull(pl.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
    }

    @Test
    public void testbijhoudingStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getBijhoudingStapel().size());

        b.bijhoudingStapel(null);
        pl = b.build();
        assertNull(pl.getBijhoudingStapel());
    }

    @Test
    public void testderdeHeeftGezagIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getDerdeHeeftGezagIndicatieStapel().size());

        b.derdeHeeftGezagIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getDerdeHeeftGezagIndicatieStapel());
    }

    @Test
    public void testdeelnameEuVerkiezingenStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getDeelnameEuVerkiezingenStapel().size());

        b.deelnameEuVerkiezingenStapel(null);
        pl = b.build();
        assertNull(pl.getDeelnameEuVerkiezingenStapel());
    }

    @Test
    public void testmigratieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getMigratieStapel().size());

        b.migratieStapel(null);
        pl = b.build();
        assertNull(pl.getMigratieStapel());
    }

    @Test
    public void testnummerverwijzingStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getNummerverwijzingStapel().size());

        b.nummerverwijzingStapel(null);
        pl = b.build();
        assertNull(pl.getNummerverwijzingStapel());
    }

    @Test
    public void testonderCurateleIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getOnderCurateleIndicatieStapel().size());

        b.onderCurateleIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getOnderCurateleIndicatieStapel());
    }

    @Test
    public void testoverlijdenStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getOverlijdenStapel().size());

        b.overlijdenStapel(null);
        pl = b.build();
        assertNull(pl.getOverlijdenStapel());
    }

    @Test
    public void testpersoonskaartStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getPersoonskaartStapel().size());

        b.persoonskaartStapel(null);
        pl = b.build();
        assertNull(pl.getPersoonskaartStapel());
    }

    @Test
    public void testsamengesteldeNaamStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getSamengesteldeNaamStapel().size());

        b.samengesteldeNaamStapel(null);
        pl = b.build();
        assertNull(pl.getSamengesteldeNaamStapel());
    }

    @Test
    public void teststaatloosIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getStaatloosIndicatieStapel().size());

        b.staatloosIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getStaatloosIndicatieStapel());
    }

    @Test
    public void testuitsluitingKiesrechtStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getUitsluitingKiesrechtStapel().size());

        b.uitsluitingKiesrechtStapel(null);
        pl = b.build();
        assertNull(pl.getUitsluitingKiesrechtStapel());
    }

    @Test
    public void testvastgesteldNietNederlanderIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getVastgesteldNietNederlanderIndicatieStapel().size());

        b.vastgesteldNietNederlanderIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getVastgesteldNietNederlanderIndicatieStapel());
    }

    @Test
    public void testverblijfsrechtStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getVerblijfsrechtStapel().size());

        b.verblijfsrechtStapel(null);
        pl = b.build();
        assertNull(pl.getVerblijfsrechtStapel());
    }

    @Test
    public void testverstrekkingsbeperkingIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getVerstrekkingsbeperkingIndicatieStapel().size());

        b.verstrekkingsbeperkingIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getVerstrekkingsbeperkingIndicatieStapel());
    }

    @Test
    public void testbijzondereVerblijfsrechtelijkePositieIndicatieStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel().size());

        b.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(null);
        pl = b.build();
        assertNull(pl.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
    }

    @Test
    public void testistOuder1Stapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getIstOuder1Stapel().size());

        b.istOuder1Stapel(null);
        pl = b.build();
        assertNull(pl.getIstOuder1Stapel());
    }

    @Test
    public void testistOuder2Stapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(1, pl.getIstOuder2Stapel().size());

        b.istOuder2Stapel(null);
        pl = b.build();
        assertNull(pl.getIstOuder2Stapel());
    }

    @Test
    public void testistHuwelijkOfGpStapels() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(0, pl.getIstHuwelijkOfGpStapels().size());

        final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> stapels = new LinkedList<>();
        stapels.add(BrpIstHuwelijkOfGpGroepInhoudTest.createStapel());
        b.istHuwelijkOfGpStapels(stapels);
        assertEquals(1, pl.getIstHuwelijkOfGpStapels().size());
        b.istHuwelijkOfGpStapels(null);
        assertEquals(1, pl.getIstHuwelijkOfGpStapels().size());
    }

    @Test
    public void testistKindStapels() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(0, pl.getIstKindStapels().size());

        final List<BrpStapel<BrpIstRelatieGroepInhoud>> stapels = new LinkedList<>();
        stapels.add(BrpIstRelatieGroepInhoudTest.createStapel());
        b.istKindStapels(stapels);
        assertEquals(1, pl.getIstKindStapels().size());
        b.istKindStapels(null);
        assertEquals(1, pl.getIstKindStapels().size());
    }

    @Test
    public void testistGezagsverhoudingStapel() {
        BrpPersoonslijstBuilder b = new BrpPersoonslijstBuilder(BrpPersoonslijstTest.createPL());
        BrpPersoonslijst pl = b.build();
        assertEquals(0, pl.getIstKindStapels().size());

        b.istGezagsverhoudingStapel(BrpIstGezagsVerhoudingGroepInhoudTest.createStapel());
        pl = b.build();
        assertEquals(1, pl.getIstGezagsverhoudingsStapel().size());
    }

}
