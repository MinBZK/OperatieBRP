/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerkerTestUtil;
import nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern.PartijController.HisPartijVerwerker;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.beheer.kern.HisPartij;
import nl.bzk.brp.model.beheer.kern.Partij;
import org.junit.Assert;
import org.junit.Test;

public class PartijControllerHistorieVerwerkerTest {

    private final HisPartijVerwerker subject = new HisPartijVerwerker();

    @Test
    public void testMaakHis() {
        final Partij item = new Partij();
        item.setDatumIngang(new DatumEvtDeelsOnbekendAttribuut(19710101));
        item.setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(19720202));
        item.setIndicatieVerstrekkingsbeperkingMogelijk(new JaNeeAttribuut(Boolean.TRUE));

        final HisPartij historie = subject.maakHistorie(item);
        Assert.assertEquals(item, historie.getPartij());
        Assert.assertEquals(item.getDatumIngang(), historie.getDatumIngang());
        Assert.assertEquals(item.getDatumEinde(), historie.getDatumEinde());
        Assert.assertEquals(item.getIndicatieVerstrekkingsbeperkingMogelijk(), historie.getIndicatieVerstrekkingsbeperkingMogelijk());
        Assert.assertNotNull(historie.getTijdstipRegistratie());
    }

    @Test
    public void testMaakHisLeeg() {
        Assert.assertNull(subject.maakHistorie(new Partij()));
    }

    @Test
    public void testIsHistorieInhoudelijkGelijk() {
        final HisPartij nieuweHistorie =
                maakHisPartij(new DatumEvtDeelsOnbekendAttribuut(19710101), new DatumEvtDeelsOnbekendAttribuut(19720101), new JaNeeAttribuut(Boolean.TRUE));
        final HisPartij actueleRecord =
                maakHisPartij(new DatumEvtDeelsOnbekendAttribuut(19710101), new DatumEvtDeelsOnbekendAttribuut(19720101), new JaNeeAttribuut(Boolean.TRUE));

        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumIngang", new DatumEvtDeelsOnbekendAttribuut(19830303));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumEinde", new DatumEvtDeelsOnbekendAttribuut(19840404));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "indicatieVerstrekkingsbeperkingMogelijk", new JaNeeAttribuut(
                Boolean.FALSE));
    }

    private HisPartij maakHisPartij(
            final DatumEvtDeelsOnbekendAttribuut datumAanvang,
            final DatumEvtDeelsOnbekendAttribuut datumEinde,
            final JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk)
    {
        final HisPartij result = new HisPartij();
        result.setDatumIngang(datumAanvang);
        result.setDatumEinde(datumEinde);
        result.setIndicatieVerstrekkingsbeperkingMogelijk(indicatieVerstrekkingsbeperkingMogelijk);

        return result;
    }
}
