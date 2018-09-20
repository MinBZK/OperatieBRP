/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Test;

public class AutorisatieConversieHelperTest extends AbstractComponentTest {

    @Inject
    private AutorisatieConversieHelper autorisatieConversieHelper;

    @Test
    public void testBeideNietLeeg() {
        final Lo3AutorisatieInhoud meestRecenteNietLegeInhoud =
                autorisatieConversieHelper.bepaalMeesteRecenteNietLegeInhoud(maakStapel(19900101, false, 19910101, false));
        Assert.assertNotNull(meestRecenteNietLegeInhoud);

        Assert.assertFalse(meestRecenteNietLegeInhoud.isLeeg());
        Assert.assertEquals("afnemer 19910101", meestRecenteNietLegeInhoud.getAfnemernaam());
    }

    @Test
    public void testMeestRecenteLeegAndereNietLeeg() {
        final Lo3AutorisatieInhoud meestRecenteNietLegeInhoud =
                autorisatieConversieHelper.bepaalMeesteRecenteNietLegeInhoud(maakStapel(19900101, false, 19910101, true));
        Assert.assertNotNull(meestRecenteNietLegeInhoud);

        Assert.assertFalse(meestRecenteNietLegeInhoud.isLeeg());
        Assert.assertEquals("afnemer 19900101", meestRecenteNietLegeInhoud.getAfnemernaam());
    }

    @Test
    public void testMeestRecenteNietLeegAndereLeeg() {
        final Lo3AutorisatieInhoud meestRecenteNietLegeInhoud =
                autorisatieConversieHelper.bepaalMeesteRecenteNietLegeInhoud(maakStapel(19900101, true, 19910101, false));
        Assert.assertNotNull(meestRecenteNietLegeInhoud);

        Assert.assertFalse(meestRecenteNietLegeInhoud.isLeeg());
        Assert.assertEquals("afnemer 19910101", meestRecenteNietLegeInhoud.getAfnemernaam());
    }

    @Test
    public void testBeideLeeg() {
        final Lo3AutorisatieInhoud meestRecenteNietLegeInhoud =
                autorisatieConversieHelper.bepaalMeesteRecenteNietLegeInhoud(maakStapel(19900101, true, 19910101, true));
        Assert.assertNull(meestRecenteNietLegeInhoud);
    }

    private Lo3Stapel<Lo3AutorisatieInhoud> maakStapel(final int datumEerste, final boolean eersteLeeg, final int datumTweede, final boolean tweedeLeeg) {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900101));

        final Lo3AutorisatieInhoud inhoud = maakAutorisatie();
        inhoud.setAfnemernaam("afnemer " + datumEerste);
        if (eersteLeeg) {
            inhoud.setAfnemersindicatie(null);
        }
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);

        final Lo3Historie historie2 = new Lo3Historie(null, new Lo3Datum(19910101), new Lo3Datum(19910101));
        final Lo3AutorisatieInhoud inhoud2 = maakAutorisatie();
        inhoud2.setAfnemernaam("afnemer " + datumTweede);
        if (tweedeLeeg) {
            inhoud2.setAfnemersindicatie(null);
        }
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie2 = new Lo3Categorie<>(inhoud2, null, historie2, null);

        final List<Lo3Categorie<Lo3AutorisatieInhoud>> categorieen = new ArrayList<>();
        categorieen.add(categorie);
        categorieen.add(categorie2);

        return new Lo3Stapel<>(Collections.unmodifiableList(categorieen));
    }

    public static Lo3AutorisatieInhoud maakAutorisatie() {
        final Lo3AutorisatieInhoud lo3Autorisatie = new Lo3AutorisatieInhoud();
        lo3Autorisatie.setIndicatieGeheimhouding(new Lo3IndicatieGeheimCode(0));
        lo3Autorisatie.setVerstrekkingsbeperking(0);
        lo3Autorisatie.setAdresvraagBevoegdheid(0);
        lo3Autorisatie.setAfnemernaam("afnemernaam");
        lo3Autorisatie.setAfnemersindicatie(100220);
        lo3Autorisatie.setAfnemersverstrekking("852018 852102");
        lo3Autorisatie.setBerichtaanduiding(0);
        lo3Autorisatie.setConditioneleVerstrekking(0);
        lo3Autorisatie.setEersteSelectiedatum(new Lo3Datum(20110101));
        lo3Autorisatie.setMediumAdHoc("N");
        lo3Autorisatie.setMediumAdresgeorienteerd("N");
        lo3Autorisatie.setMediumSelectie("N");
        lo3Autorisatie.setMediumSpontaan("A");
        lo3Autorisatie.setPlaatsingsbevoegdheidPersoonslijst(1);
        lo3Autorisatie.setRubrieknummerAdHoc("01.01.10");
        lo3Autorisatie.setRubrieknummerAdresgeorienteerd("01.01.20");
        lo3Autorisatie.setRubrieknummerSelectie("09.01.10");
        lo3Autorisatie.setRubrieknummerSpontaan("09.01.20");
        lo3Autorisatie.setSelectieperiode(12);
        lo3Autorisatie.setSelectiesoort(0);
        lo3Autorisatie.setSleutelrubriek("01.01.10#01.01.20#09.01.10#09.01.20");
        lo3Autorisatie.setVoorwaarderegelAdHoc("KNV 07.67.10");
        lo3Autorisatie.setVoorwaarderegelAdresgeorienteerd("KV 01.01.10");
        lo3Autorisatie.setVoorwaarderegelSelectie("KV 01.02.10");
        lo3Autorisatie.setVoorwaarderegelSpontaan("KV 01.04.10");
        lo3Autorisatie.setDatumIngang(new Lo3Datum(19900101));
        lo3Autorisatie.setDatumEinde(null);
        return lo3Autorisatie;
    }
}
