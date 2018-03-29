/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerkerTestUtil;
import nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern.PartijController.PartijHistorieVerwerker;

import org.junit.Assert;
import org.junit.Test;

public class PartijControllerHistorieVerwerkerTest {

    private final PartijHistorieVerwerker subject = new PartijHistorieVerwerker();

    @Test
    public void testMaakHis() {
        final Partij item = new Partij("testpartij", "001234");
        item.setDatumIngang(19710101);
        item.setDatumEinde(19720202);
        item.setIndicatieVerstrekkingsbeperkingMogelijk(Boolean.TRUE);

        final PartijHistorie historie = subject.maakHistorie(item);
        Assert.assertEquals(item, historie.getPartij());
        Assert.assertEquals(item.getDatumIngang(), Integer.valueOf(historie.getDatumIngang()));
        Assert.assertEquals(item.getDatumEinde(), historie.getDatumEinde());
        Assert.assertEquals(item.isIndicatieVerstrekkingsbeperkingMogelijk(), historie.isIndicatieVerstrekkingsbeperkingMogelijk());
        Assert.assertNotNull(historie.getDatumTijdRegistratie());
    }

    @Test
    public void testMaakHisLeeg() {
        final Partij partij = new Partij("testpartij", "001234");
        partij.setDatumIngang(19700101);
        Assert.assertNotNull(subject.maakHistorie(partij));
    }

    @Test
    public void testIsHistorieInhoudelijkGelijk() {
        final Partij partij = new Partij("testpartij", "001234");
        final PartijHistorie nieuweHistorie = maakHisPartij(partij, partij.getNaam(), 19710101, 19720101, Boolean.TRUE);
        final PartijHistorie actueleRecord = maakHisPartij(partij, partij.getNaam(), 19710101, 19720101, Boolean.TRUE);

        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumIngang", 19830303);
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumEinde", 19840404);
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "indicatieVerstrekkingsbeperkingMogelijk", Boolean.FALSE);
    }

    private PartijHistorie maakHisPartij(
        final Partij partij,
        final String naam,
        final Integer datumIngang,
        final Integer datumEinde,
        final Boolean indicatieVerstrekkingsbeperkingMogelijk)
    {
        final PartijHistorie historie =
                new PartijHistorie(partij, Timestamp.valueOf(LocalDateTime.now()), datumIngang, indicatieVerstrekkingsbeperkingMogelijk, naam);
        historie.setDatumEinde(datumEinde);
        return historie;
    }
}
