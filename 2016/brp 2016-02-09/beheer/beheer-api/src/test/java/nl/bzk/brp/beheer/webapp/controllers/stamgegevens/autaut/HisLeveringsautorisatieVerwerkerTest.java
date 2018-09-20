/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerkerTestUtil;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.HisLeveringsautorisatie;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class HisLeveringsautorisatieVerwerkerTest {

    private final HisLeveringsautorisatieVerwerker subject = new HisLeveringsautorisatieVerwerker();

    @Test
    public void testMaakHis() {
        final Leveringsautorisatie item = new Leveringsautorisatie();
        item.setPopulatiebeperking(new PopulatiebeperkingAttribuut("POPBEPERK"));
        item.setDatumIngang(new DatumAttribuut(19710101));
        item.setDatumEinde(new DatumAttribuut(19720202));

        final HisLeveringsautorisatie historie = subject.maakHistorie(item);
        Assert.assertEquals(item, historie.getLeveringsautorisatie());
        Assert.assertEquals(item.getPopulatiebeperking(), historie.getPopulatiebeperking());
        Assert.assertEquals(item.getDatumIngang(), historie.getDatumIngang());
        Assert.assertEquals(item.getDatumEinde(), historie.getDatumEinde());
        Assert.assertNotNull(historie.getTijdstipRegistratie());
    }

    @Test
    public void testMaakHisLeeg() {
        Assert.assertNull(subject.maakHistorie(new Leveringsautorisatie()));
    }

    @Test
    public void testIsHistorieInhoudelijkGelijk() {
        final HisLeveringsautorisatie nieuweHistorie =
                maakHisLeveringsautorisatie(
                    new PopulatiebeperkingAttribuut("POPBEPERK"),
                    new DatumAttribuut(19710101),
                    new DatumAttribuut(19720101));
        final HisLeveringsautorisatie actueleRecord =
                maakHisLeveringsautorisatie(
                    new PopulatiebeperkingAttribuut("POPBEPERK"),
                    new DatumAttribuut(19710101),
                    new DatumAttribuut(19720101));

        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "populatiebeperking", new PopulatiebeperkingAttribuut("ANDERS"));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumIngang", new DatumAttribuut(19830303));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumEinde", new DatumAttribuut(19840404));
        //HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "toestand", Toestand.DEFINITIEF);
    }

    private HisLeveringsautorisatie maakHisLeveringsautorisatie(
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde)
    {
        final HisLeveringsautorisatie result = new HisLeveringsautorisatie();
        result.setPopulatiebeperking(naderePopulatiebeperking);
        result.setDatumIngang(datumEinde);
        result.setDatumEinde(datumEinde);

        return result;
    }
}
