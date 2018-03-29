/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.doc;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.nationaliteit;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.samengesteldnaam;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.junit.Test;

/**
 * Test de scenarios waarbij een persoon geprivilegieerd is.
 */
public class BrpGeprivilegieerdeConversieTest extends AbstractBrpConversieTest {

    private static final String NAT_CODE = "0001";
    @Inject
    private BrpConverteerder brpConverteerder;

    @Test
    public void testNietGeprivilegieerd() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, "033", null);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerd() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, "033", null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdLegeNationaliteit() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, null, null, "034");
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMeerdereNationaliteiten() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        // nationaliteit
        vulNationaliteit(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);
        vulNationaliteit(builder, "0002", null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdVervallen() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, 20110102000000L), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdBeeindigd() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, 20110101, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdBeeindigdEnVervallen() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, 20110101, 20100102000000L, 20110102000000L), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMetProbasAlGevuld() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteitMetProbas(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMetProbasAlGevuldMetMeerdereNationaliteiten() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);
        vulNationaliteitMetProbas(builder, "0002", null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMetProbasAlGevuldInMeerdereNationaliteiten() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteitMetProbas(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);
        vulNationaliteitMetProbas(builder, "0002", null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels(), 2);
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE017)
    public void testGeprivilegieerdeMetNationaliteitValidatie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, null, null);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);
        addSamengesteldeNaamStapel(builder);
        final BrpPersoonslijst brpPersoonslijst = builder.build();
        brpPersoonslijst.valideer();
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE017)
    public void testGeprivilegieerdeZonderNationaliteitValidatie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        try {
            brpPersoonslijst.valideer();
            fail("Validatie fout verwacht omdat er geen nationaliteit aanwezig is");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE017.name()));
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE017)
    public void testGeprivilegieerdeZonderActueleNationaliteitValidatie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, NAT_CODE, null, null, his(20100101, 20120101, 20100102000000L, 20120102000000L));
        vulVerblijfsrechtelijkePositie(builder, his(20100101, null, 20100102000000L, null), true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        try {
            brpPersoonslijst.valideer();
            fail("Validatie fout verwacht omdat er geen actuele nationaliteit aanwezig is");
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE017.name()));
        }
    }

    private void vulVerblijfsrechtelijkePositie(final BrpPersoonslijstBuilder builder, final BrpHistorie brpHistorie, final Boolean heeftIndicatie) {
        final List<BrpGroep<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud>> bijzVerblijfGroepen = new ArrayList<>();

        final BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud inhoud =
                new BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud(new BrpBoolean(heeftIndicatie), null, null);

        final BrpGroep<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzVerblijfGroep =
                new BrpGroep<>(inhoud, brpHistorie, act(1, 20100102), null, null);
        bijzVerblijfGroepen.add(bijzVerblijfGroep);
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel =
                new BrpStapel<>(bijzVerblijfGroepen);
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(bijzondereVerblijfsrechtelijkePositieIndicatieStapel);
    }

    private void vulNationaliteitMetProbas(final BrpPersoonslijstBuilder builder, final String natCode, final String verkrijgCode, final String verliesCode) {
        final List<BrpGroep<BrpNationaliteitInhoud>> natGroepen = new ArrayList<>();
        final BrpDocumentInhoud doc = doc("PROBAS", "000042");
        final BrpActie actie = act(123L, "Conversie GBA", "000042", 20010101000000L, null, doc);
        final BrpGroep<BrpNationaliteitInhoud> natGroep =
                new BrpGroep<>(nationaliteit(natCode, verkrijgCode, verliesCode), his(20100101, 20100102000000L), actie, null, null);
        natGroepen.add(natGroep);
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = new BrpStapel<>(natGroepen);
        builder.nationaliteitStapel(nationaliteitStapel);
    }

    private void addSamengesteldeNaamStapel(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpSamengesteldeNaamInhoud>> groepen = new ArrayList<>();
        final BrpSamengesteldeNaamInhoud naam = samengesteldnaam("Jan", "Janssen");
        final BrpGroep<BrpSamengesteldeNaamInhoud> groep = new BrpGroep<>(naam, his(19800101, 19800102000000L), act(1, 19800102), null, null);
        groepen.add(groep);
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel = new BrpStapel<>(groepen);
        builder.samengesteldeNaamStapel(samengesteldeNaamStapel);
    }

    /**
     * Loop alle stapels na om na te gaan dat nergens PROBAS voorkomt in de documentatie
     */
    private void assertGeenProbasVermeldingen(final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels) {
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : nationaliteitStapels) {
            for (final Lo3Categorie<Lo3NationaliteitInhoud> lo3Categorie : stapel) {
                final Lo3Documentatie documentatie = lo3Categorie.getDocumentatie();
                assertFalse(bevatProbas(documentatie));
            }
        }

    }

    /**
     * Loop alle stapels na om na te gaan dat er één keer PROBAS voorkomt in de documentatie
     */
    private void assertProbasVermelding(final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels) {
        assertProbasVermelding(nationaliteitStapels, 1);
    }

    /**
     * Loop alle stapels na om na te gaan dat er n keer PROBAS voorkomt in de documentatie
     */
    private void assertProbasVermelding(final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels, final int aantal) {
        int aantalGevondenProbas = 0;
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : nationaliteitStapels) {
            for (final Lo3Categorie<Lo3NationaliteitInhoud> lo3Categorie : stapel) {
                final Lo3Documentatie documentatie = lo3Categorie.getDocumentatie();
                if (bevatProbas(documentatie)) {
                    aantalGevondenProbas++;
                }
            }
        }
        assertEquals(aantal, aantalGevondenProbas);

    }

    private boolean bevatProbas(final Lo3Documentatie documentatie) {
        return documentatie != null
                && documentatie.getBeschrijvingDocument() != null
                && Lo3String.unwrap(documentatie.getBeschrijvingDocument()).startsWith("PROBAS");
    }
}
