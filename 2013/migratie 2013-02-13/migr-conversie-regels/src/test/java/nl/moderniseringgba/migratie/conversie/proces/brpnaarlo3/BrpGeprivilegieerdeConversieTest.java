/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.doc;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.geboorte;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.inschrijving;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.nationaliteit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;

import org.junit.Test;

/**
 * Test de scenarios waarbij een persoon geprivilegieerd is.
 * 
 */
public class BrpGeprivilegieerdeConversieTest extends AbstractComponentTest {

    @Inject
    private BrpConverteerder brpConverteerder;

    @Test
    public void testNietGeprivilegieerd() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", "033", null);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerd() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", "033", null);
        vulGeprivilegieerde(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdLegeNationaliteit() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, null, null, "034");
        vulGeprivilegieerde(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMeerdereNationaliteiten() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        // nationaliteit
        vulNationaliteit(builder, "0001", null, null);
        vulNationaliteit(builder, "0002", null, null);
        // geprivilegieerde
        vulGeprivilegieerde(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdVervallen() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", null, null);
        vulGeprivilegieerde(builder, false, true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdBeeindigd() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", null, null);
        vulGeprivilegieerde(builder, true, false);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdBeeindigdEnVervallen() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", null, null);
        vulGeprivilegieerde(builder, true, true);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertGeenProbasVermeldingen(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMetProbasAlGevuld() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteitMetProbas(builder, "0001", null, null);
        vulGeprivilegieerde(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMetProbasAlGevuldMetMeerdereNationaliteiten() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", null, null);
        vulNationaliteitMetProbas(builder, "0002", null, null);
        vulGeprivilegieerde(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels());
    }

    @Test
    public void testGeprivilegieerdMetProbasAlGevuldInMeerdereNationaliteiten() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteitMetProbas(builder, "0001", null, null);
        vulNationaliteitMetProbas(builder, "0002", null, null);
        vulGeprivilegieerde(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);
        assertProbasVermelding(lo3Pl.getNationaliteitStapels(), 2);
    }

    @Test
    @Preconditie(Precondities.PRE017)
    public void testGeprivilegieerdeMetNationaliteitValidatie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulGeprivilegieerde(builder);
        vulNationaliteit(builder, "0001", null, null);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        brpPersoonslijst.valideer();
    }

    @Test
    @Preconditie(Precondities.PRE017)
    public void testGeprivilegieerdeZonderNationaliteitValidatie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulGeprivilegieerde(builder);
        final BrpPersoonslijst brpPersoonslijst = builder.build();
        try {
            brpPersoonslijst.valideer();
            fail("Validatie fout verwacht omdat er geen nationaliteit aanwezig is");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE017.name()));
        }
    }

    @Test
    @Preconditie(Precondities.PRE017)
    public void testGeprivilegieerdeZonderActueleNationaliteitValidatie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulGeprivilegieerde(builder);
        vulNationaliteit(builder, "0001", null, null, his(20100101, 20120101, 20100102000000L, 20120102000000L));

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        try {
            brpPersoonslijst.valideer();
            fail("Validatie fout verwacht omdat er geen actuele nationaliteit aanwezig is");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE017.name()));
        }
    }

    private void vulGeprivilegieerde(final BrpPersoonslijstBuilder builder) {
        vulGeprivilegieerde(builder, false, false);
    }

    private void vulGeprivilegieerde(
            final BrpPersoonslijstBuilder builder,
            final boolean beeindigd,
            final boolean vervallen) {
        final List<BrpGroep<BrpGeprivilegieerdeIndicatieInhoud>> groepen =
                new ArrayList<BrpGroep<BrpGeprivilegieerdeIndicatieInhoud>>();
        final BrpGeprivilegieerdeIndicatieInhoud inhoud = new BrpGeprivilegieerdeIndicatieInhoud(true);
        final BrpHistorie his =
                his(19900101, beeindigd ? 19900103 : null, 19900102000000L, vervallen ? 19900105000000L : null);
        final BrpGroep<BrpGeprivilegieerdeIndicatieInhoud> groep =
                new BrpGroep<BrpGeprivilegieerdeIndicatieInhoud>(inhoud, his, act(123, 19900102), null, null);
        groepen.add(groep);
        final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel =
                new BrpStapel<BrpGeprivilegieerdeIndicatieInhoud>(groepen);
        builder.geprivilegieerdeIndicatieStapel(geprivilegieerdeIndicatieStapel);
    }

    private void vulNationaliteit(
            final BrpPersoonslijstBuilder builder,
            final String natCode,
            final String verkrijgCode,
            final String verliesCode) {
        vulNationaliteit(builder, natCode, verkrijgCode, verliesCode, his(20100101, 20100102000000L));
    }

    private void vulNationaliteit(
            final BrpPersoonslijstBuilder builder,
            final String natCode,
            final String verkrijgCode,
            final String verliesCode,
            final BrpHistorie historie) {
        final List<BrpGroep<BrpNationaliteitInhoud>> natGroepen = new ArrayList<BrpGroep<BrpNationaliteitInhoud>>();
        final BrpGroep<BrpNationaliteitInhoud> natGroep =
                new BrpGroep<BrpNationaliteitInhoud>(nationaliteit(natCode, verkrijgCode, verliesCode), historie,
                        act(1, 20100102), null, null);
        natGroepen.add(natGroep);
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel =
                new BrpStapel<BrpNationaliteitInhoud>(natGroepen);
        builder.nationaliteitStapel(nationaliteitStapel);
    }

    private void vulNationaliteitMetProbas(
            final BrpPersoonslijstBuilder builder,
            final String natCode,
            final String verkrijgCode,
            final String verliesCode) {
        final List<BrpGroep<BrpNationaliteitInhoud>> natGroepen = new ArrayList<BrpGroep<BrpNationaliteitInhoud>>();
        final BrpDocumentInhoud doc = doc("PROBAS", "042");
        final BrpActie actie = act(123L, "Conversie GBA", "042", null, null, 20010101000000L, doc);
        final BrpGroep<BrpNationaliteitInhoud> natGroep =
                new BrpGroep<BrpNationaliteitInhoud>(nationaliteit(natCode, verkrijgCode, verliesCode), his(20100101,
                        20100102000000L), actie, null, null);
        natGroepen.add(natGroep);
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel =
                new BrpStapel<BrpNationaliteitInhoud>(natGroepen);
        builder.nationaliteitStapel(nationaliteitStapel);
    }

    private void vulVerplichteStapels(final BrpPersoonslijstBuilder builder) {
        addGeboorteStapel(builder);
        addInschrijving(builder);
    }

    private void addInschrijving(final BrpPersoonslijstBuilder builder) {
        final BrpInschrijvingInhoud inschrijving = inschrijving(null, null, 19800101, 1);
        final List<BrpGroep<BrpInschrijvingInhoud>> groepen = new ArrayList<BrpGroep<BrpInschrijvingInhoud>>();
        final BrpGroep<BrpInschrijvingInhoud> groep =
                new BrpGroep<BrpInschrijvingInhoud>(inschrijving, his(19800101, 19800102000000L), act(1, 19800102),
                        null, null);
        groepen.add(groep);
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel = new BrpStapel<BrpInschrijvingInhoud>(groepen);
        builder.inschrijvingStapel(inschrijvingStapel);

    }

    private void addGeboorteStapel(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<BrpGroep<BrpGeboorteInhoud>>();
        final BrpGeboorteInhoud geboorte = geboorte(19800101, "042");
        final BrpGroep<BrpGeboorteInhoud> groep =
                new BrpGroep<BrpGeboorteInhoud>(geboorte, his(19800101, 19800102000000L), act(1, 19800102), null,
                        null);
        groepen.add(groep);
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = new BrpStapel<BrpGeboorteInhoud>(groepen);
        builder.geboorteStapel(geboorteStapel);

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
    private void assertProbasVermelding(
            final List<Lo3Stapel<Lo3NationaliteitInhoud>> nationaliteitStapels,
            final int aantal) {
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
        return documentatie != null && documentatie.getBeschrijvingDocument() != null
                && documentatie.getBeschrijvingDocument().startsWith("PROBAS");
    }
}
