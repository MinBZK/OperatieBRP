/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.expressietaal;

import static junit.framework.TestCase.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.symbols.ExpressieMap;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Deze tests kunnen alle personen in de database langs expressies halen. Om een andere dan de junit-db te gebruiken, pas dan (tijdelijk) lokaal de
 * levering-database.properties aan (in de testscope).
 */
@ContextConfiguration(locations = { "/config/expressies/expressies-test-context.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AlleBMRExpressiesIntegratieTest extends AbstractIntegratieTest {

    //TODO: uiteindelijk zou geen enkele expressie overgeslagen moeten worden.
    private static final List<String> EXPRESSIES_DIE_OVERGESLAGEN_MOETEN_WORDEN =
        Collections.singletonList("$administratief.gegevens_in_onderzoek");

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final List<Integer> PERSOON_IDS_ALS_NIET_ALLE_PERSONEN_MOETEN = Arrays.asList(1, 46, 47, 48);

    private static List<PersoonHisVolledigImpl> personenInDb;

    private static final boolean ALLE_PERSONEN_VERWERKEN = false;

    private static final boolean MAAK_NIEUWE_BLOBS = false;

    @Inject
    private BlobifierService blobifierService;

    @Inject
    private ExpressiesTestDataOphaler expressiesTestDataOphaler;

    @Inject
    @Named("alleenLezenDataSource")
    @Override
    public final void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Before
    public final void setup() {
        if (personenInDb == null) {
            if (!ALLE_PERSONEN_VERWERKEN) {
                personenInDb = blobifierService.leesBlobs(PERSOON_IDS_ALS_NIET_ALLE_PERSONEN_MOETEN);
            } else {
                LOGGER.info("Gaat ALLE personen uit de database halen...");
                personenInDb = expressiesTestDataOphaler.getAllePersonenUitDatabase();
                LOGGER.info("Klaar met alle personen uit de database halen...");
            }
            if (MAAK_NIEUWE_BLOBS) {
                zorgVoorBlobsInDatabase();
            }
        }
    }

    private void zorgVoorBlobsInDatabase() {
        LOGGER.info("Gaat blobs maken voor alle personen");
        for (final PersoonHisVolledig persoonHisVolledig : personenInDb) {
            blobifierService.blobify(persoonHisVolledig.getID(), false);
        }
        LOGGER.info("Klaar met het maken van blobs voor alle personen.");
    }

    @Test
    public final void testOpUitgeschakeldeExpressiesUitDatabase() {
        final Collection<String> expressieTekstLijst = expressiesTestDataOphaler.getAlleExpressiesUitDatabase();

        for (final String expressieTekst : expressieTekstLijst) {
            assertFalse("Expressie gevonden in de database die ge-disabled zou moeten zijn: " + expressieTekst,
                EXPRESSIES_DIE_OVERGESLAGEN_MOETEN_WORDEN.contains(expressieTekst));
        }
    }

    @Test
    public void testExpressiesUitDatabase1Voor1() {
        final Collection<String> expressieTekstLijst = expressiesTestDataOphaler.getAlleExpressiesUitDatabase();

        evalueerExpressiesVoorAllePersonen(expressieTekstLijst, false);
    }

    @Test
    public void testExpressiesUitDatabaseIn1Keer() {
        final Collection<String> expressieTekstLijst = expressiesTestDataOphaler.getAlleExpressiesUitDatabase();

        evalueerExpressiesVoorAllePersonen(expressieTekstLijst, true);
    }

    @Test
    public void testExpressiesUitExpressietaalMap1Voor1() {
        final List<String> testbareExpressies = getTestbareExpressiesUitMap();

        evalueerExpressiesVoorAllePersonen(testbareExpressies, false);
    }

    @Test
    public void testExpressiesUitExpressietaalMapIn1Keer() {
        final List<String> testbareExpressies = getTestbareExpressiesUitMap();

        evalueerExpressiesVoorAllePersonen(testbareExpressies, true);
    }

    private List<String> getTestbareExpressiesUitMap() {
        final Collection<String> expressieTekstLijst = ExpressieMap.getMap().values();
        final List<String> testbareExpressies = new ArrayList<>();
        for (final String expressieTekst : expressieTekstLijst) {
            if (!EXPRESSIES_DIE_OVERGESLAGEN_MOETEN_WORDEN.contains(expressieTekst)) {
                testbareExpressies.add(expressieTekst);
            }
        }
        return testbareExpressies;
    }

    private void evalueerExpressiesVoorAllePersonen(final Collection<String> expressieTekstLijst,
        final boolean in1Keer)
    {
        for (final PersoonHisVolledig persoonHisVolledig : personenInDb) {

            if (persoonHisVolledig == null) {
                LOGGER.error("Persoon niet in database aanwezig.");
                continue;
            }

            final PersoonHisVolledig persoonUitBlob = blobifierService.leesBlob(persoonHisVolledig.getID());

            if (!in1Keer) {
                matchAlleExpressies1Voor1(expressieTekstLijst, persoonUitBlob);
            } else {
                matchAlleExpressiesIn1Keer(expressieTekstLijst, persoonUitBlob);
            }
        }
    }

    private void matchAlleExpressies1Voor1(final Collection<String> expressieTekstLijst,
        final PersoonHisVolledig persoon)
    {
        for (final String expressieTekst : expressieTekstLijst) {
            logPersoonEnExpressie(persoon, expressieTekst);
            final Expressie expressieResultaat = BRPExpressies.evalueer(expressieTekst, persoon);

            assertFalse("Parsen van expressie is mislukt: " + expressieTekst + " FOUTMELDING: "
                + expressieResultaat.alsString(), expressieResultaat.isFout());
        }
    }

    private void matchAlleExpressiesIn1Keer(final Collection<String> expressieTekstLijst,
        final PersoonHisVolledig persoon)
    {
        final StringBuilder totaleExpressie = new StringBuilder("{");
        for (final String expressieTekst : expressieTekstLijst) {
            totaleExpressie.append(expressieTekst);
            totaleExpressie.append(",");
        }
        totaleExpressie.deleteCharAt(totaleExpressie.length() - 1);
        totaleExpressie.append("}");

        logPersoonEnExpressie(persoon, totaleExpressie.toString());
        final Expressie expressieResultaat = BRPExpressies.evalueer(totaleExpressie.toString(), persoon);

        assertFalse("Parsen van LIJST-expressie is mislukt: " + totaleExpressie.toString() + " foutmelding: "
            + expressieResultaat.alsString(), expressieResultaat.isFout());
    }

    private void logPersoonEnExpressie(final PersoonHisVolledig persoon, final String totaleExpressie) {
        LOGGER.info("Expressie tegen persoon " + persoon.getID() + ": " + totaleExpressie);
    }

}
