/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Assert;
import org.junit.Test;

public class Lo3AutorisatieInhoudTest {

    private static final String KNV_07_67_20 = "KNV 07.67.20";

    @Test
    public void testIsLeeg() {
        Assert.assertTrue(new Lo3AutorisatieInhoud().isLeeg());

        Lo3AutorisatieInhoud autorisatie = new Lo3AutorisatieInhoud();
        // autorisatie.setConditioneleVerstrekking(Integer.valueOf(0));
        Assert.assertTrue(autorisatie.isLeeg());
        autorisatie = new Lo3AutorisatieInhoud();
        autorisatie.setAfnemersindicatie("000007");
        Assert.assertTrue(!autorisatie.isLeeg());
    }

    @Test
    public void testAutorisatieInhoud() throws NoSuchMethodException, IllegalAccessException {
        final Lo3AutorisatieInhoud autorisatieInhoud = createAutorisatie("Belastingdienst", new Lo3Datum(20130101), null);
        final Lo3AutorisatieInhoud autorisatieInhoudEqual = createAutorisatie("Belastingdienst", new Lo3Datum(20130101), null);
        final Lo3AutorisatieInhoud autorisatieInhoudNotEqual = createAutorisatie("CBS", new Lo3Datum(20130202), null);

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(autorisatieInhoud, autorisatieInhoudEqual, autorisatieInhoudNotEqual);
    }

    private Lo3AutorisatieInhoud createAutorisatie(final String afnemerNaam, final Lo3Datum datumIngang, final Lo3Datum datumEinde) {
        final Lo3AutorisatieInhoud lo3Autorisatie = new Lo3AutorisatieInhoud();
        // lo3Autorisatie.setVersie(versie);
        lo3Autorisatie.setAfnemernaam(afnemerNaam);
        lo3Autorisatie.setIndicatieGeheimhouding(0);
        lo3Autorisatie.setVerstrekkingsbeperking(0);
        lo3Autorisatie.setAdresvraagBevoegdheid(0);
        lo3Autorisatie.setAfnemersindicatie("100220");
        lo3Autorisatie.setAfnemersverstrekking("852018 852102");
        lo3Autorisatie.setBerichtaanduiding(0);
        lo3Autorisatie.setConditioneleVerstrekking(0);
        lo3Autorisatie.setEersteSelectiedatum(new Lo3Datum(20110101));
        lo3Autorisatie.setMediumAdHoc("N");
        lo3Autorisatie.setMediumAdresgeorienteerd("N");
        lo3Autorisatie.setMediumSelectie("N");
        lo3Autorisatie.setMediumSpontaan("N");
        lo3Autorisatie.setPlaatsingsbevoegdheidPersoonslijst(0);
        lo3Autorisatie.setRubrieknummerAdHoc("010110");
        lo3Autorisatie.setRubrieknummerAdresgeorienteerd("010120");
        lo3Autorisatie.setRubrieknummerSelectie("090110");
        lo3Autorisatie.setRubrieknummerSpontaan("090120");
        lo3Autorisatie.setSelectieperiode(12);
        lo3Autorisatie.setSelectiesoort(0);
        lo3Autorisatie.setSleutelrubriek("010110 010120 090110 090120");
        lo3Autorisatie.setVoorwaarderegelAdHoc(KNV_07_67_20);
        lo3Autorisatie.setVoorwaarderegelAdresgeorienteerd(KNV_07_67_20);
        lo3Autorisatie.setVoorwaarderegelSelectie(KNV_07_67_20);
        lo3Autorisatie.setVoorwaarderegelSpontaan(KNV_07_67_20);
        lo3Autorisatie.setDatumIngang(datumIngang);
        lo3Autorisatie.setDatumEinde(datumEinde);
        return lo3Autorisatie;
    }
}
