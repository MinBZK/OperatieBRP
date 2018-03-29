/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.autorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Test;

public class CsvAutorisatieReaderTest {
    private final CsvAutorisatieReader subject = new CsvAutorisatieReader();

    @Test
    public void test() throws Exception {
        final List<Lo3Autorisatie> autorisaties = subject.read(CsvAutorisatieReaderTest.class.getResourceAsStream("/test.csv"));

        assertEquals(2, autorisaties.size());

        checkAutorisatie(autorisaties.get(0));
        checkAutorisatie(autorisaties.get(1));
    }

    private void checkAutorisatie(final Lo3Autorisatie lo3Autorisatie) {
        final String afnemer = lo3Autorisatie.getAfnemersindicatie();

        if ("800007".equals(afnemer)) {
            check800007(lo3Autorisatie);
        } else if ("800008".equals(afnemer)) {
            check800008(lo3Autorisatie);
        } else {
            fail("Onbekende afnemer: " + afnemer);
        }

    }

    private void check800007(final Lo3Autorisatie lo3Autorisatie) {
        assertEquals(3, lo3Autorisatie.getAutorisatieStapel().size());

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = lo3Autorisatie.getAutorisatieStapel().get(0);
        if ("Afnemer 7.1".equals(categorie.getInhoud().getAfnemernaam())) {
            assertEquals(Integer.valueOf(19800101), categorie.getInhoud().getDatumIngang().getIntegerWaarde());
            assertEquals(Integer.valueOf(19850101), categorie.getInhoud().getDatumEinde().getIntegerWaarde());
        } else if ("Afnemer 7.2".equals(categorie.getInhoud().getAfnemernaam())) {
            assertEquals(Integer.valueOf(19900101), categorie.getInhoud().getDatumIngang().getIntegerWaarde());
            assertEquals(Integer.valueOf(19920101), categorie.getInhoud().getDatumEinde().getIntegerWaarde());
        } else if ("Afnemer 7.3".equals(categorie.getInhoud().getAfnemernaam())) {
            assertEquals(Integer.valueOf(19920101), categorie.getInhoud().getDatumIngang().getIntegerWaarde());
            assertNull(categorie.getInhoud().getDatumEinde());
        }

    }

    private void check800008(final Lo3Autorisatie lo3Autorisatie) {
        assertEquals(1, lo3Autorisatie.getAutorisatieStapel().size());

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = lo3Autorisatie.getAutorisatieStapel().get(0);
        final Lo3AutorisatieInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
        assertNotNull(herkomst);

        // Controleer inhoud
        assertEquals(Integer.valueOf(7), inhoud.getAdresvraagBevoegdheid());
        assertEquals("Afnemer 8", inhoud.getAfnemernaam());
        assertEquals("800008", inhoud.getAfnemersindicatie());
        assertEquals("Ja", inhoud.getAfnemersverstrekking());
        assertEquals(Integer.valueOf(4), inhoud.getBerichtaanduiding());
        assertEquals(Integer.valueOf(2), inhoud.getConditioneleVerstrekking());
        assertEquals(new Lo3Datum(19500101), inhoud.getEersteSelectiedatum());
        assertEquals(Integer.valueOf(0), inhoud.getIndicatieGeheimhouding());
        assertEquals("S", inhoud.getMediumAdHoc());
        assertEquals("X", inhoud.getMediumAdresgeorienteerd());
        assertEquals("N", inhoud.getMediumSelectie());
        assertEquals("A", inhoud.getMediumSpontaan());
        assertEquals(Integer.valueOf(6), inhoud.getPlaatsingsbevoegdheidPersoonslijst());
        assertEquals("03.10", inhoud.getRubrieknummerAdHoc());
        assertEquals("04.10", inhoud.getRubrieknummerAdresgeorienteerd());
        assertEquals("02.10", inhoud.getRubrieknummerSelectie());
        assertEquals("01.10", inhoud.getRubrieknummerSpontaan());
        assertEquals(Integer.valueOf(5), inhoud.getSelectieperiode());
        assertEquals(Integer.valueOf(3), inhoud.getSelectiesoort());
        assertEquals("01.20", inhoud.getSleutelrubriek());
        assertEquals(Integer.valueOf(1), inhoud.getVerstrekkingsbeperking());
        assertEquals("KNV 03.20", inhoud.getVoorwaarderegelAdHoc());
        assertEquals("KNV 04.20", inhoud.getVoorwaarderegelAdresgeorienteerd());
        assertEquals("KNV 02.20", inhoud.getVoorwaarderegelSelectie());
        assertEquals("KNV 01.10", inhoud.getVoorwaarderegelSpontaan());
        assertEquals(Integer.valueOf(19940415), inhoud.getDatumIngang().getIntegerWaarde());

        // Controleer historie
        assertEquals(Integer.valueOf(19940415), categorie.getHistorie().getDatumVanOpneming().getIntegerWaarde());
        assertEquals(Integer.valueOf(19940415), categorie.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde());
    }
}
