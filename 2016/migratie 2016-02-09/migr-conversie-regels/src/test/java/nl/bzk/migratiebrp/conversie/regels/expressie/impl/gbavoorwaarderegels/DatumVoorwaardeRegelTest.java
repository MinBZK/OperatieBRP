/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test voor de DatumVoorwaardeRegel.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public class DatumVoorwaardeRegelTest {

    @Inject
    private DatumVoorwaardeRegel instance;

    @Test
    public void testOnbekendDatumGedeelten() throws Exception {
        testVoorwaarde("01.03.10 GA1 00000000", "geboorte.datum = ?/?/?");
        testVoorwaarde("01.03.10 GA1 19330000", "geboorte.datum = 1933/?/?");
        testVoorwaarde("01.03.10 GA1 19330700", "geboorte.datum = 1933/07/?");
        testVoorwaarde("01.03.10 GDOG1 19330700", "geboorte.datum >= 1933/07/?");
        testVoorwaarde("01.03.10 GDOGA 19330700", "geboorte.datum >= 1933/07/?");
        testVoorwaarde("08.10.30 GD1 19330700", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v > 1933/07/?)");
    }

    @Test
    public void testGD1() throws Exception {
        testVoorwaarde("01.03.10 GD1 19800301", "geboorte.datum > 1980/03/01");
        testVoorwaarde("08.10.30 GD1 19800301", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v > 1980/03/01)");
        testVoorwaarde("01.03.10 GD1 19800301 - 00290000", "geboorte.datum > 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 GD1 19800301 - 00290000", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v > 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 GD1 19800301 - 0029", "JAAR(geboorte.datum) > JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 GD1 19800301 - 0029", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) > JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 GD1 19800301 - 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) > MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GD1 19800301 - 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) > MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 GD1 19800301 + 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) > MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GD1 19800301 + 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) > MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testKD1() throws Exception {
        testVoorwaarde("01.03.10 KD1 19800301", "geboorte.datum < 1980/03/01");
        testVoorwaarde("08.10.30 KD1 19800301", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v < 1980/03/01)");
        testVoorwaarde("01.03.10 KD1 19800301 - 00290000", "geboorte.datum < 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 KD1 19800301 - 00290000", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v < 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 KD1 19800301 - 0029", "JAAR(geboorte.datum) < JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 KD1 19800301 - 0029", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) < JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 KD1 19800301 - 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) < MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KD1 19800301 - 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) < MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 KD1 19800301 + 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) < MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KD1 19800301 + 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) < MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testGDOG1() throws Exception {
        testVoorwaarde("01.03.10 GDOG1 19800301", "geboorte.datum >= 1980/03/01");
        testVoorwaarde("08.10.30 GDOG1 19800301", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v >= 1980/03/01)");
        testVoorwaarde("01.03.10 GDOG1 19800301 - 00290000", "geboorte.datum >= 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 GDOG1 19800301 - 00290000", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v >= 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 GDOG1 19800301 - 0029", "JAAR(geboorte.datum) >= JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 GDOG1 19800301 - 0029", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) >= JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 GDOG1 19800301 - 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) >= MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GDOG1 19800301 - 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) >= MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 GDOG1 19800301 + 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) >= MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GDOG1 19800301 + 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) >= MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testKDOG1() throws Exception {
        testVoorwaarde("01.03.10 KDOG1 19800301", "geboorte.datum <= 1980/03/01");
        testVoorwaarde("08.10.30 KDOG1 19800301", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v <= 1980/03/01)");
        testVoorwaarde("01.03.10 KDOG1 19800301 - 00290000", "geboorte.datum <= 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 KDOG1 19800301 - 00290000", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v <= 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 KDOG1 19800301 - 0029", "JAAR(geboorte.datum) <= JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 KDOG1 19800301 - 0029", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) <= JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 KDOG1 19800301 - 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) <= MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KDOG1 19800301 - 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) <= MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 KDOG1 19800301 + 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) <= MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KDOG1 19800301 + 002900",
            "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) <= MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testGDA() throws Exception {
        testVoorwaarde("01.03.10 GDA 19800301", "geboorte.datum > 1980/03/01");
        testVoorwaarde("08.10.30 GDA 19800301", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v > 1980/03/01)");
        testVoorwaarde("01.03.10 GDA 19800301 - 00290000", "geboorte.datum > 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 GDA 19800301 - 00290000", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v > 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 GDA 19800301 - 0029", "JAAR(geboorte.datum) > JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 GDA 19800301 - 0029", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) > JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 GDA 19800301 - 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) > MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GDA 19800301 - 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) > MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 GDA 19800301 + 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) > MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GDA 19800301 + 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) > MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testKDA() throws Exception {
        testVoorwaarde("01.03.10 KDA 19800301", "geboorte.datum < 1980/03/01");
        testVoorwaarde("08.10.30 KDA 19800301", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v < 1980/03/01)");
        testVoorwaarde("01.03.10 KDA 19800301 - 00290000", "geboorte.datum < 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 KDA 19800301 - 00290000", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v < 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 KDA 19800301 - 0029", "JAAR(geboorte.datum) < JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 KDA 19800301 - 0029", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) < JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 KDA 19800301 - 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) < MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KDA 19800301 - 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) < MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 KDA 19800301 + 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) < MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KDA 19800301 + 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) < MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testGDOGA() throws Exception {
        testVoorwaarde("01.03.10 GDOGA 19800301", "geboorte.datum >= 1980/03/01");
        testVoorwaarde("08.10.30 GDOGA 19800301", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v >= 1980/03/01)");
        testVoorwaarde("01.03.10 GDOGA 19800301 - 00290000", "geboorte.datum >= 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 GDOGA 19800301 - 00290000", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v >= 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 GDOGA 19800301 - 0029", "JAAR(geboorte.datum) >= JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 GDOGA 19800301 - 0029", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) >= JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 GDOGA 19800301 - 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) >= MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GDOGA 19800301 - 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) >= MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 GDOGA 19800301 + 002900",
            "((JAAR(geboorte.datum) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) >= MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 GDOGA 19800301 + 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) > JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) >= MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testKDOGA() throws Exception {
        testVoorwaarde("01.03.10 KDOGA 19800301", "geboorte.datum <= 1980/03/01");
        testVoorwaarde("08.10.30 KDOGA 19800301", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v <= 1980/03/01)");
        testVoorwaarde("01.03.10 KDOGA 19800301 - 00290000", "geboorte.datum <= 1980/03/01 - ^29/0/0");
        testVoorwaarde("08.10.30 KDOGA 19800301 - 00290000", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v <= 1980/03/01 - ^29/0/0)");
        testVoorwaarde("01.03.10 KDOGA 19800301 - 0029", "JAAR(geboorte.datum) <= JAAR(1980/03/01 - ^29/0/0)");
        testVoorwaarde("08.10.30 KDOGA 19800301 - 0029", "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, JAAR(v) <= JAAR(1980/03/01 - ^29/0/0))");
        testVoorwaarde(
            "01.03.10 KDOGA 19800301 - 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(geboorte.datum) <= MAAND(1980/03/01 - ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KDOGA 19800301 - 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 - ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 - ^29/0/0) "
                    + "EN MAAND(v) <= MAAND(1980/03/01 - ^29/0/0))))");
        testVoorwaarde(
            "01.03.10 KDOGA 19800301 + 002900",
            "((JAAR(geboorte.datum) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(geboorte.datum) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(geboorte.datum) <= MAAND(1980/03/01 + ^29/0/0)))");
        testVoorwaarde(
            "08.10.30 KDOGA 19800301 + 002900",
            "ALLE(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, ((JAAR(v) < JAAR(1980/03/01 + ^29/0/0)) OF (JAAR(v) = JAAR(1980/03/01 + ^29/0/0) "
                    + "EN MAAND(v) <= MAAND(1980/03/01 + ^29/0/0))))");
    }

    @Test
    public void testVandaagEnSelectieDatum() throws Exception {
        testVoorwaarde("01.03.10 KDOG1 19.89.30 - 00290000", "geboorte.datum <= VANDAAG() - ^29/0/0");
        testVoorwaarde("01.03.10 KDOG1 19.89.20 - 00290000", "geboorte.datum <= SELECTIE_DATUM() - ^29/0/0");
        testVoorwaarde("01.03.10 KDOG1 19.89.30 - 0029", "JAAR(geboorte.datum) <= JAAR(VANDAAG() - ^29/0/0)");
        testVoorwaarde("01.03.10 KDOG1 19.89.30 - 002900", "((JAAR(geboorte.datum) < JAAR(VANDAAG() - ^29/0/0)) "
                                                           + "OF (JAAR(geboorte.datum) = JAAR(VANDAAG() - ^29/0/0) "
                                                           + "EN MAAND(geboorte.datum) <= MAAND(VANDAAG() - ^29/0/0)))");
        testVoorwaarde("01.03.10 KDOG1 19.89.20 - 00000000", "geboorte.datum <= SELECTIE_DATUM() - ^0/0/0");
    }

    @Test
    public void testVergelijkingMetRubriek() throws Exception {
        testVoorwaarde("01.03.10 KDOG1 07.68.10", "geboorte.datum <= inschrijving.datum");
        testVoorwaarde("01.03.10 OGA1 08.10.30", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, geboorte.datum <> v)");
        testVoorwaarde("08.10.30 GDOG1 01.03.10", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v >= geboorte.datum)");
    }

    @Test
    public void testVergelijkingMetRubriekMetPeriode() throws Exception {
        testVoorwaarde("01.03.10 KDOG1 07.68.10 - 00290000", "geboorte.datum <= inschrijving.datum - ^29/0/0");
        testVoorwaarde("08.10.30 GDOG1 01.03.10 - 00290000", "ER_IS(RMAP(adressen, x, x.datum_aanvang_geldigheid), v, v >= geboorte.datum - ^29/0/0)");
    }

    private void testVoorwaarde(final String gbaVoorwaarde, final String brpExpressie) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String result = instance.getBrpExpressie(gbaVoorwaarde);
        assertEquals(brpExpressie, result);
    }
}
