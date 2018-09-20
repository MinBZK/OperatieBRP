/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerkerTestUtil;
import nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut.DienstController.HisDienstVerwerker;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.beheer.autaut.Dienst;
import nl.bzk.brp.model.beheer.autaut.HisDienst;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DienstControllerHistorieVerwerkerTest {

    private final HisDienstVerwerker subject = new HisDienstVerwerker();

    @Test
    public void testMaakHis() {
        final Dienst item = new Dienst();
        item.setAttenderingscriterium(new AttenderingscriteriumAttribuut("ATT"));
        item.setDatumIngang(new DatumAttribuut(19710101));
        item.setDatumEinde(new DatumAttribuut(19720202));

        final HisDienst historie = subject.maakHistorie(item);
        Assert.assertEquals(item, historie.getDienst());
        Assert.assertEquals(item.getDatumIngang(), historie.getDatumIngang());
        Assert.assertEquals(item.getDatumEinde(), historie.getDatumEinde());
        Assert.assertNotNull(historie.getTijdstipRegistratie());
    }

    @Test
    public void testIsHistorieInhoudelijkGelijk() {
        final HisDienst nieuweHistorie =
                maakHisDienst(new PopulatiebeperkingAttribuut("POPBEPERK"), new AttenderingscriteriumAttribuut("ATT"), new DatumAttribuut(
                    19710101), new DatumAttribuut(19720101));
        final HisDienst actueleRecord =
                maakHisDienst(new PopulatiebeperkingAttribuut("POPBEPERK"), new AttenderingscriteriumAttribuut("ATT"), new DatumAttribuut(
                    19710101), new DatumAttribuut(19720101));

        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "naderePopulatiebeperking", new PopulatiebeperkingAttribuut("ANDERS"));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "attenderingscriterium", new AttenderingscriteriumAttribuut("ANDERS"));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumIngang", new DatumEvtDeelsOnbekendAttribuut(19830303));
        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "datumEinde", new DatumEvtDeelsOnbekendAttribuut(19840404));
//        HistorieVerwerkerTestUtil.controleer(subject, nieuweHistorie, actueleRecord, "toestand", Toestand.DEFINITIEF);
    }

    private HisDienst maakHisDienst(
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final AttenderingscriteriumAttribuut attenderingscriterium,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde)
    {
        final HisDienst result = new HisDienst();
        result.setDatumIngang(datumEinde);
        result.setDatumEinde(datumEinde);

        return result;
    }
}
