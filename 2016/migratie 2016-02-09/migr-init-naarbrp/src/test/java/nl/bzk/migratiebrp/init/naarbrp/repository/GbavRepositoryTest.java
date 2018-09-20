/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.service.bericht.SyncNaarBrpBericht;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.SynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class GbavRepositoryTest {

    private static final String VIND_BERICHT_MISLUKT = "Vind bericht mislukt.";

    @Inject
    private GbavRepository gbavRepository;

    @Inject
    private DataSource dataSource;

    @Inject
    private ExcelAdapter excelAdapter;

    private int berichtenTeller;

    private final SynchronisatieBerichtVerwerker verwerker = new SynchronisatieBerichtVerwerker() {
        private boolean done;
        private int teller;

        @Override
        public void voegBerichtToe(final SyncNaarBrpBericht bericht) {
            teller += 1;
        }

        @Override
        public void verwerkBerichten() {
            berichtenTeller += teller;
            teller = 0;
            done = true;
        }

        @Override
        public int aantalBerichten() {
            if (done) {
                return 0;
            } else {
                return teller;
            }
        }
    };

    @Before
    public void before() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE initvul.initvullingresult SET conversie_resultaat='TE_VERZENDEN'");

        gbavRepository.setAnummerLimitString("");
        gbavRepository.setStartDatumString("");
        gbavRepository.setEindDatumString("");
        gbavRepository.setGemeenteString("");
    }

    @Ignore
    public void findAlleLO3Berichten() {
        berichtenTeller = 0;

        try {
            gbavRepository.setStartDatumString("01-01-1990");
            gbavRepository.setEindDatumString("01-01-2013");
            gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 1);
        } catch (final ParseException e) {
            Assert.fail(VIND_BERICHT_MISLUKT);
        }

        Assert.assertTrue("Geen berichten gevonden!", berichtenTeller == 28);
    }

    @Test
    public void findGeenLO3Berichten() {

        berichtenTeller = 0;

        try {
            gbavRepository.setStartDatumString("01-01-2114");
            gbavRepository.setEindDatumString("01-01-2115");

            gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 1);
        } catch (final ParseException e) {
            Assert.fail(VIND_BERICHT_MISLUKT);
        }

        Assert.assertEquals("Onverwachts berichten gevonden!", 0, berichtenTeller);
    }

    @Test
    public void findGeenLO3BerichtenVoorStatus() {

        berichtenTeller = 0;

        try {
            gbavRepository.setStartDatumString("01-01-1990");
            gbavRepository.setEindDatumString("01-01-2013");
            gbavRepository.verwerkLo3Berichten(ConversieResultaat.VERZONDEN, verwerker, 1);
        } catch (final ParseException e) {
            Assert.fail(VIND_BERICHT_MISLUKT);
        }

        Assert.assertTrue("Onverwachts berichten gevonden!", berichtenTeller == 0);
    }

    @Test(expected = NumberFormatException.class)
    public void findLO3BerichtenNumberFormatException() throws ParseException {
        gbavRepository.setStartDatumString("01-01-2014");
        gbavRepository.setEindDatumString("01-01-2015");
        gbavRepository.setGemeenteString("ddd");
        gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 1);
    }

    @Test(expected = ParseException.class)
    public void findLO3BerichtenParseException() throws ParseException {
        gbavRepository.setStartDatumString("01-01-a014");
        gbavRepository.setEindDatumString("01-01-2015");
        gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findLO3BerichtenIllegalArgumentException() throws ParseException {
        gbavRepository.setStartDatumString("01-01-2014");
        gbavRepository.setEindDatumString("01-01-2013");
        gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 1);
    }

    @Ignore
    public void testSaveLg01() throws ExcelAdapterException, Lo3SyntaxException, IOException {
        final String file = "src/test/resources/excel/gbavrepo/pl-1.xls";

        try (final FileInputStream fin = new FileInputStream(file)) {
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(fin);
            final ExcelData data = excelDatas.get(0);
            final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
            final Lo3Persoonslijst pl = parser.parse(data.getCategorieLijst());
            final Integer gemeenteVanInschrijving =
                    Integer.parseInt(pl.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving().getWaarde());
            gbavRepository.saveLg01(formateerAlsLg01(pl), pl.getActueelAdministratienummer(), gemeenteVanInschrijving, ConversieResultaat.TE_VERZENDEN);
        }

        final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        final Date tomorrow = cal.getTime();

        berichtenTeller = 0;

        try {
            gbavRepository.setStartDatumString("01-01-1990");
            gbavRepository.setEindDatumString(df.format(tomorrow));
            gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 1);
        } catch (final ParseException e) {
            Assert.fail(VIND_BERICHT_MISLUKT);
        }

        Assert.assertEquals(29, berichtenTeller); // 28 + 1
    }

    private String formateerAlsLg01(final Lo3Persoonslijst lo3) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }

}
