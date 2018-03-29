/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.io.FileInputStream;
import java.io.IOException;
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
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:runtime-test-beans.xml", initializers = PortInitializer.class)
public class PersoonRepositoryTest {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private DataSource dataSource;

    @Inject
    private ExcelAdapter excelAdapter;

    @Before
    public void before() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE initvul.initvullingresult SET conversie_resultaat='TE_VERZENDEN'");
    }

    @Test
    public void findAlleLO3Berichten() {
        final BerichtTeller verwerker = new BerichtTeller();
        Assert.assertFalse(persoonRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 100));

        Assert.assertEquals("Verkeerd aantal berichten gevonden", 28, verwerker.aantalBerichten());
    }

    @Test
    public void findGeenLO3BerichtenVoorStatus() {
        final BerichtTeller<SyncNaarBrpBericht> verwerker = new BerichtTeller<>();
        Assert.assertFalse(persoonRepository.verwerkLo3Berichten(ConversieResultaat.VERZONDEN, verwerker, 100));

        Assert.assertEquals("Verkeerd aantal berichten gevonden", 0, verwerker.aantalBerichten());
    }

    @Test
    public void testSaveLg01() throws ExcelAdapterException, Lo3SyntaxException, IOException {
        final String file = "src/test/resources/excel/gbavrepo/pl-1.xls";

        try (final FileInputStream fin = new FileInputStream(file)) {
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(fin);
            final ExcelData data = excelDatas.get(0);
            final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
            final Lo3Persoonslijst pl = parser.parse(data.getCategorieLijst());
            final String gemeenteVanInschrijving = pl.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving().getWaarde();
            persoonRepository.saveLg01(formateerAlsLg01(pl), pl.getActueelAdministratienummer(), gemeenteVanInschrijving, ConversieResultaat.TE_VERZENDEN);
        }

        final BerichtTeller verwerker = new BerichtTeller();
        Assert.assertFalse(persoonRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, 100));

        Assert.assertEquals("Verkeerd aantal berichten gevonden", 29, verwerker.aantalBerichten()); // 28 + 1
    }

    private String formateerAlsLg01(final Lo3Persoonslijst lo3) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }

}
