/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.junit.Assert;
import org.junit.Test;

public class CsvAutorisatieReaderTest {
    private final CsvAutorisatieReader subject = new CsvAutorisatieReader();

    @Test
    public void test() throws Exception {
        final List<Lo3Autorisatie> autorisaties = subject.read(CsvAutorisatieReaderTest.class.getResourceAsStream("/test.csv"));

        Assert.assertEquals(2, autorisaties.size());

        checkAutorisatie(autorisaties.get(0));
        checkAutorisatie(autorisaties.get(1));
    }

    private void checkAutorisatie(final Lo3Autorisatie lo3Autorisatie) {
        final Integer afnemer = lo3Autorisatie.getAfnemersindicatie();

        if (Integer.valueOf(800007).equals(afnemer)) {
            check800007(lo3Autorisatie);
        } else if (Integer.valueOf(800008).equals(afnemer)) {
            check800008(lo3Autorisatie);
        } else {
            Assert.fail("Onbekende afnemer: " + afnemer);
        }

    }

    private void check800007(final Lo3Autorisatie lo3Autorisatie) {
        Assert.assertEquals(3, lo3Autorisatie.getAutorisatieStapel().size());

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = lo3Autorisatie.getAutorisatieStapel().get(0);
        if ("Afnemer 7.1".equals(categorie.getInhoud().getAfnemernaam())) {
            Assert.assertEquals(Integer.valueOf(19800101), categorie.getInhoud().getDatumIngang().getIntegerWaarde());
            Assert.assertEquals(Integer.valueOf(19850101), categorie.getInhoud().getDatumEinde().getIntegerWaarde());
        } else if ("Afnemer 7.2".equals(categorie.getInhoud().getAfnemernaam())) {
            Assert.assertEquals(Integer.valueOf(19900101), categorie.getInhoud().getDatumIngang().getIntegerWaarde());
            Assert.assertEquals(Integer.valueOf(19920101), categorie.getInhoud().getDatumEinde().getIntegerWaarde());
        } else if ("Afnemer 7.3".equals(categorie.getInhoud().getAfnemernaam())) {
            Assert.assertEquals(Integer.valueOf(19920101), categorie.getInhoud().getDatumIngang().getIntegerWaarde());
            Assert.assertNull(categorie.getInhoud().getDatumEinde());
        }

    }

    private void check800008(final Lo3Autorisatie lo3Autorisatie) {
        Assert.assertEquals(1, lo3Autorisatie.getAutorisatieStapel().size());

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = lo3Autorisatie.getAutorisatieStapel().get(0);
        final Lo3AutorisatieInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
        Assert.assertNotNull(herkomst);

        // Controleer inhoud
        Assert.assertEquals(Integer.valueOf(7), inhoud.getAdresvraagBevoegdheid());
        Assert.assertEquals("Afnemer 8", inhoud.getAfnemernaam());
        Assert.assertEquals(Integer.valueOf(800008), inhoud.getAfnemersindicatie());
        Assert.assertEquals("Ja", inhoud.getAfnemersverstrekking());
        Assert.assertEquals(Integer.valueOf(4), inhoud.getBerichtaanduiding());
        Assert.assertEquals(Integer.valueOf(2), inhoud.getConditioneleVerstrekking());
        Assert.assertEquals(new Lo3Datum(19500101), inhoud.getEersteSelectiedatum());
        Assert.assertEquals(new Lo3IndicatieGeheimCode(0), inhoud.getIndicatieGeheimhouding());
        Assert.assertEquals("S", inhoud.getMediumAdHoc());
        Assert.assertEquals("X", inhoud.getMediumAdresgeorienteerd());
        Assert.assertEquals("N", inhoud.getMediumSelectie());
        Assert.assertEquals("A", inhoud.getMediumSpontaan());
        Assert.assertEquals(Integer.valueOf(6), inhoud.getPlaatsingsbevoegdheidPersoonslijst());
        Assert.assertEquals("03.10", inhoud.getRubrieknummerAdHoc());
        Assert.assertEquals("04.10", inhoud.getRubrieknummerAdresgeorienteerd());
        Assert.assertEquals("02.10", inhoud.getRubrieknummerSelectie());
        Assert.assertEquals("01.10", inhoud.getRubrieknummerSpontaan());
        Assert.assertEquals(Integer.valueOf(5), inhoud.getSelectieperiode());
        Assert.assertEquals(Integer.valueOf(3), inhoud.getSelectiesoort());
        Assert.assertEquals("01.20", inhoud.getSleutelrubriek());
        Assert.assertEquals(Integer.valueOf(1), inhoud.getVerstrekkingsbeperking());
        Assert.assertEquals("KNV 03.20", inhoud.getVoorwaarderegelAdHoc());
        Assert.assertEquals("KNV 04.20", inhoud.getVoorwaarderegelAdresgeorienteerd());
        Assert.assertEquals("KNV 02.20", inhoud.getVoorwaarderegelSelectie());
        Assert.assertEquals("KNV 01.10", inhoud.getVoorwaarderegelSpontaan());
        Assert.assertEquals(Integer.valueOf(19940415), inhoud.getDatumIngang().getIntegerWaarde());

        // Controleer historie
        Assert.assertEquals(Integer.valueOf(19940415), categorie.getHistorie().getDatumVanOpneming().getIntegerWaarde());
        Assert.assertEquals(Integer.valueOf(19940415), categorie.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde());
    }
}
