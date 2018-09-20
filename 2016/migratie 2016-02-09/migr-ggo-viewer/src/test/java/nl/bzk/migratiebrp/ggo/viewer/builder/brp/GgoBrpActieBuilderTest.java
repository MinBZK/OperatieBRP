/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class GgoBrpActieBuilderTest {
    @Inject
    private GgoBrpActieBuilder builder;

    private Partij partij;

    @Before
    public void setUp() {
        partij = new Partij("tegen Alles", 518);
    }

    @Test
    public void testAddActieFilled() {
        final Map<String, String> voorkomen = new LinkedHashMap<>();

        final SoortActie soortActieCode = SoortActie.CONVERSIE_GBA;
        final Timestamp datumTijdRegistratie = new Timestamp(new Date().getTime());
        final BRPActie brpActie =
                new BRPActie(soortActieCode, new AdministratieveHandeling(
                    partij,
                    SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND), partij, datumTijdRegistratie);

        builder.addActie(voorkomen, brpActie, null);

        assertContains(voorkomen, GgoBrpElementEnum.SOORT_ACTIE, "Conversie GBA");
        assertContains(voorkomen, GgoBrpElementEnum.PARTIJ, "0518 (tegen Alles)");
        assertContains(voorkomen, GgoBrpElementEnum.TIJDSTIP_REGISTRATIE, ViewerDateUtil.formatDatumTijdUtc(datumTijdRegistratie));
    }

    private void assertContains(final Map<String, String> voorkomen, final GgoBrpElementEnum element, final String expected) {
        final String key = element.getLabel();
        Assert.assertTrue("Key: " + key + " komt niet voor!", voorkomen.containsKey(key));
        Assert.assertNotNull("Geen waarde bij key: " + key, voorkomen.get(key));
        Assert.assertEquals("Waarde is niet verwacht.", expected, voorkomen.get(key));
    }
}
