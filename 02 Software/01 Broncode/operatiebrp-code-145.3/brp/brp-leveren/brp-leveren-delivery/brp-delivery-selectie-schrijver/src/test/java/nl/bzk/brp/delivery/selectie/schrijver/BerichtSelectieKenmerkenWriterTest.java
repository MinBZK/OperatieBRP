/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.delivery.algemeen.writer.BerichtTestUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

public class BerichtSelectieKenmerkenWriterTest extends BerichtTestUtil {

    private static final ZonedDateTime zdt = ZonedDateTime.of(2017, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);

    private static final String expectedAlleAttributenGevuld =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<brp:test xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">\n"
                    + "  <brp:selectiekenmerken>\n"
                    + "    <brp:soortSelectieresultaatSet>SoortSelectiekenmerken</brp:soortSelectieresultaatSet>\n"
                    + "    <brp:volgnummerSelectieresultaatSet>2</brp:volgnummerSelectieresultaatSet>\n"
                    + "    <brp:soortSelectie>Standaard selectie</brp:soortSelectie>\n"
                    + "    <brp:leveringsautorisatieIdentificatie>1</brp:leveringsautorisatieIdentificatie>\n"
                    + "    <brp:dienstIdentificatie>null</brp:dienstIdentificatie>\n"
                    + "    <brp:selectietaakIdentificatie>1</brp:selectietaakIdentificatie>\n"
                    + "    <brp:selectieDatum>2014-01-01</brp:selectieDatum>\n"
                    + "    <brp:peilmomentMaterieelResultaat>2015-01-01</brp:peilmomentMaterieelResultaat>\n"
                    + "    <brp:peilmomentFormeelResultaat>2017-01-03T00:00:00.000Z</brp:peilmomentFormeelResultaat>\n"
                    + "  </brp:selectiekenmerken>\n"
                    + "</brp:test>\n";

    private static final String expectedGeenPeilmomenten =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<brp:test xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">\n"
                    + "  <brp:selectiekenmerken>\n"
                    + "    <brp:soortSelectieresultaatSet>SoortSelectiekenmerken</brp:soortSelectieresultaatSet>\n"
                    + "    <brp:volgnummerSelectieresultaatSet>2</brp:volgnummerSelectieresultaatSet>\n"
                    + "    <brp:soortSelectie>Standaard selectie</brp:soortSelectie>\n"
                    + "    <brp:leveringsautorisatieIdentificatie>1</brp:leveringsautorisatieIdentificatie>\n"
                    + "    <brp:dienstIdentificatie>null</brp:dienstIdentificatie>\n"
                    + "    <brp:selectietaakIdentificatie>1</brp:selectietaakIdentificatie>\n"
                    + "    <brp:selectieDatum>2014-01-01</brp:selectieDatum>\n"
                    + "  </brp:selectiekenmerken>\n"
                    + "</brp:test>\n";

    @Test
    public void testHappyFlow() throws Exception {

        final Leveringsautorisatie levaut = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_VOORNAAM_STANDAARD;
        final Dienst dienst = TestAutorisaties.maakDienst(levaut, Element.ADMINISTRATIEVEHANDELING_SOORTNAAM, SoortDienst.SELECTIE, groepDefinitie);
        final SelectieKenmerken selectieKenmerken = SelectieKenmerken.builder()
                .metSelectietaakId(1)
                .metSoortSelectie(SoortSelectie.STANDAARD_SELECTIE)
                .metDatumUitvoer(20140101)
                .metDienst(dienst)
                .metLeveringsautorisatie(levaut)
                .metPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(zdt))
                .metPeilmomentMaterieelResultaat(20150101)
                .metSoortSelectieresultaatSet("SoortSelectiekenmerken")
                .metSoortSelectieresultaatVolgnummer(2)
                .build();

        final String output = geefOutput(
                writer -> BerichtSelectieKenmerkenWriter.write(writer, selectieKenmerken));
        final Diff diff = DiffBuilder.compare(expectedAlleAttributenGevuld).withTest(output).ignoreComments().ignoreWhitespace().build();
        Assert.assertFalse(diff.hasDifferences());
    }

    @Test
    public void testHappyFlow_GeenPeilmomenten() throws Exception {

        final Leveringsautorisatie levaut = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_VOORNAAM_STANDAARD;
        final Dienst dienst = TestAutorisaties.maakDienst(levaut, Element.ADMINISTRATIEVEHANDELING_SOORTNAAM, SoortDienst.SELECTIE, groepDefinitie);
        final SelectieKenmerken selectieKenmerken = SelectieKenmerken.builder()
                .metSelectietaakId(1)
                .metSoortSelectie(SoortSelectie.STANDAARD_SELECTIE)
                .metDatumUitvoer(20140101)
                .metDienst(dienst)
                .metLeveringsautorisatie(levaut)
                .metSoortSelectieresultaatSet("SoortSelectiekenmerken")
                .metSoortSelectieresultaatVolgnummer(2)
                .build();

        final String output = geefOutput(
                writer -> BerichtSelectieKenmerkenWriter.write(writer, selectieKenmerken));

        final Diff diff = DiffBuilder.compare(expectedGeenPeilmomenten).withTest(output).ignoreComments().ignoreWhitespace().build();
        Assert.assertFalse(diff.hasDifferences());
    }

}
