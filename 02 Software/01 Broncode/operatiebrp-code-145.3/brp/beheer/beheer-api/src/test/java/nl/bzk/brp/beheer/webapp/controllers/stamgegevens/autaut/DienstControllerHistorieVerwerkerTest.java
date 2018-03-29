/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerkerTestUtil;
import nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut.DienstController.DienstHistorieVerwerker;

import org.junit.Assert;
import org.junit.Test;

public class DienstControllerHistorieVerwerkerTest {

    private static final Leveringsautorisatie LEVERINGSAUTORISATIE = new Leveringsautorisatie(Stelsel.GBA, Boolean.FALSE);
    private static final Dienstbundel DIENSTBUNDEL = new Dienstbundel(LEVERINGSAUTORISATIE);
    private final DienstHistorieVerwerker subject = new DienstHistorieVerwerker();

    @Test
    public void testMaakHis() {
        final Dienst item = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        item.setAttenderingscriterium("ATT");
        item.setDatumIngang(19710101);
        item.setDatumEinde(19720202);

        final DienstHistorie historie = subject.maakHistorie(item);
        Assert.assertEquals(item, historie.getDienst());
        Assert.assertEquals(item.getDatumIngang(), Integer.valueOf(historie.getDatumIngang()));
        Assert.assertEquals(item.getDatumEinde(), historie.getDatumEinde());
        Assert.assertNotNull(historie.getDatumTijdRegistratie());
    }

    @Test
    public void testIsHistorieInhoudelijkGelijk() {
        final Dienst item = new Dienst(DIENSTBUNDEL, SoortDienst.ATTENDERING);
        item.setAttenderingscriterium("ATT");
        final DienstHistorie nieuweHistorie = maakHisDienst(item, 19710101, 19720101);
        final DienstHistorie actueleRecord = maakHisDienst(item, 19710101, 19720101);

        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumIngang", 19830303);
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumEinde", 19840404);
    }

    private DienstHistorie maakHisDienst(final Dienst dienst, final Integer datumIngang, final Integer datumEinde) {
        final DienstHistorie result = new DienstHistorie(dienst, Timestamp.valueOf(LocalDateTime.now()), datumIngang);
        result.setDatumEinde(datumEinde);

        return result;
    }
}
