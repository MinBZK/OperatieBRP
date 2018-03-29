/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.service.selectie.schrijver.SelectieResultaatVerwerkException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.test.util.ReflectionTestUtils;

public class SelectieResultaatBerichtWriterTest {

    private static final String REFNR = "refNr";
    private static final String CROSSREFNR = "crossRefNr";
    private static final Partij ZENDENDE_PARTIJ = TestPartijBuilder.maakBuilder().metCode("111111").build();
    private static final Partij ONTVANGENDE_PARTIJ = TestPartijBuilder.maakBuilder().metCode("222222").build();
    private static final String ZENDEND_SYSTEEM = "BRP";
    private static final ZonedDateTime TS_VERZENDING = ZonedDateTime.of(2017, 1, 1, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID);

    private Leveringsautorisatie levaut;
    private Dienst dienst;

    private static final String PERSOONRESULTAAT_EXPECTED =
            //@formatter:off
                "<?xml version='1.0' encoding='UTF-8'?><brp:lvg_selVerwerkSelectieresultaatSet xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">"
                + "<brp:stuurgegevens>"
                    + "<brp:zendendePartij>111111</brp:zendendePartij>"
                    + "<brp:zendendeSysteem>BRP</brp:zendendeSysteem>"
                    + "<brp:ontvangendePartij>222222</brp:ontvangendePartij>"
                    + "<brp:referentienummer>refNr</brp:referentienummer>"
                    + "<brp:crossReferentienummer>crossRefNr</brp:crossReferentienummer>"
                    + "<brp:tijdstipVerzending>2016-12-31T23:00:00.000Z</brp:tijdstipVerzending>"
                + "</brp:stuurgegevens>"
                + "<brp:selectiekenmerken>"
                    + "<brp:soortSelectieresultaatSet>SoortSelResultaatSet</brp:soortSelectieresultaatSet>"
                    + "<brp:volgnummerSelectieresultaatSet>1</brp:volgnummerSelectieresultaatSet>"
                    + "<brp:soortSelectie>Standaard selectie</brp:soortSelectie>"
                    + "<brp:leveringsautorisatieIdentificatie>1</brp:leveringsautorisatieIdentificatie>"
                    + "<brp:dienstIdentificatie>null</brp:dienstIdentificatie>"
                    + "<brp:selectietaakIdentificatie>1</brp:selectietaakIdentificatie>"
                    + "<brp:selectieDatum>2014-01-01</brp:selectieDatum>"
                + "</brp:selectiekenmerken>"
                + "<brp:geselecteerdePersonen>persoon</brp:geselecteerdePersonen>"
                + "</brp:lvg_selVerwerkSelectieresultaatSet>";

    private static final String TOTALENRESULTAAT_EXPECTED =
            "<?xml version='1.0' encoding='UTF-8'?><brp:lvg_selVerwerkSelectieresultaatSet xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">"
                    + "<brp:stuurgegevens>"
                        + "<brp:zendendePartij>111111</brp:zendendePartij>"
                        + "<brp:zendendeSysteem>BRP</brp:zendendeSysteem>"
                        + "<brp:ontvangendePartij>222222</brp:ontvangendePartij>"
                        + "<brp:referentienummer>refNr</brp:referentienummer>"
                        + "<brp:crossReferentienummer>crossRefNr</brp:crossReferentienummer>"
                        + "<brp:tijdstipVerzending>2016-12-31T23:00:00.000Z</brp:tijdstipVerzending>"
                    + "</brp:stuurgegevens>"
                    + "<brp:selectiekenmerken>"
                        + "<brp:soortSelectieresultaatSet>SoortSelResultaatSet</brp:soortSelectieresultaatSet>"
                        + "<brp:volgnummerSelectieresultaatSet>1</brp:volgnummerSelectieresultaatSet>"
                        + "<brp:soortSelectie>Standaard selectie</brp:soortSelectie>"
                        + "<brp:leveringsautorisatieIdentificatie>1</brp:leveringsautorisatieIdentificatie>"
                        + "<brp:dienstIdentificatie>null</brp:dienstIdentificatie>"
                        + "<brp:selectietaakIdentificatie>1</brp:selectietaakIdentificatie>"
                        + "<brp:selectieDatum>2014-01-01</brp:selectieDatum>"
                    + "</brp:selectiekenmerken>"
                    + "<brp:resultaat>"
                        + "<brp:aantalSelectieresultaatPersonen>1</brp:aantalSelectieresultaatPersonen>"
                        + "<brp:aantalSelectieresultaatSets>1</brp:aantalSelectieresultaatSets>"
                    + "</brp:resultaat>"
                    + "</brp:lvg_selVerwerkSelectieresultaatSet>";
    //@formatter:on

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void voorTest() {
        levaut = TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE);
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_VOORNAAM_STANDAARD;
        dienst = TestAutorisaties.maakDienst(levaut, Element.ADMINISTRATIEVEHANDELING_SOORTNAAM, SoortDienst.SELECTIE, groepDefinitie);
    }

    @Test
    public void testSchrijfPersoonResultaatBericht() throws IOException, SelectieResultaatVerwerkException {
        final Path path = folder.newFile("personen").toPath();
        final SelectieResultaatBericht resultaatBericht = maakSelectieResultaatBericht();

        final SelectieResultaatBerichtWriter.BrpPersoonWriter brpPersoonWriter =
                new SelectieResultaatBerichtWriter.BrpPersoonWriter(path, resultaatBericht);
        brpPersoonWriter.voegPersoonToe("persoon");
        brpPersoonWriter.eindeBericht();

        assertEquals(PERSOONRESULTAAT_EXPECTED, new String(Files.readAllBytes(path)));
    }

    @Test
    public void testSchrijfPersoonResultaatBericht_MetBasisBerichtgegevensParameters() throws IOException, SelectieResultaatVerwerkException {
        final Path path = folder.newFile("personen").toPath();
        final SelectieResultaatBericht resultaatBericht = maakSelectieResultaatBerichtMetParameters();

        final SelectieResultaatBerichtWriter.BrpPersoonWriter brpPersoonWriter =
                new SelectieResultaatBerichtWriter.BrpPersoonWriter(path, resultaatBericht);
        brpPersoonWriter.voegPersoonToe("persoon");
        brpPersoonWriter.eindeBericht();

        assertEquals(PERSOONRESULTAAT_EXPECTED, new String(Files.readAllBytes(path)));
    }

    @Test
    public void testSchrijfTotalenResultaatBericht() throws IOException, SelectieResultaatVerwerkException {
        final Path path = folder.newFile("totalen").toPath();
        final SelectieResultaatBericht resultaatBericht = maakSelectieResultaatBericht();

        final SelectieResultaatBerichtWriter.BrpTotalenWriter brpTotalenWriter =
                new SelectieResultaatBerichtWriter.BrpTotalenWriter(path, resultaatBericht);
        brpTotalenWriter.schrijfTotalen(1, 1);
        brpTotalenWriter.eindeBericht();

        System.out.println(new String(Files.readAllBytes(path)));
        assertEquals(TOTALENRESULTAAT_EXPECTED, new String(Files.readAllBytes(path)));
    }

    @Test(expected = SelectieResultaatVerwerkException.class)
    public void testSchrijfTotalenResultaatBericht_invalidPath() throws IOException, SelectieResultaatVerwerkException {
        final Path path = folder.newFolder("totalen").toPath();
        final SelectieResultaatBericht resultaatBericht = maakSelectieResultaatBericht();

        new SelectieResultaatBerichtWriter.BrpTotalenWriter(path, resultaatBericht);
    }

    @Test(expected = SelectieResultaatVerwerkException.class)
    public void testSchrijfPersoonResultaatBericht_invalidPath() throws IOException, SelectieResultaatVerwerkException {
        final Path path = folder.newFolder("personen").toPath();
        final SelectieResultaatBericht resultaatBericht = maakSelectieResultaatBericht();

        new SelectieResultaatBerichtWriter.BrpPersoonWriter(path, resultaatBericht);
    }

    @Test
    public void testDoClose_WriterIsNull() throws Exception {
        final Path path = folder.newFile("personen").toPath();
        final SelectieResultaatBericht resultaatBericht = maakSelectieResultaatBericht();

        final SelectieResultaatBerichtWriter.BrpPersoonWriter brpPersoonWriter =
                new SelectieResultaatBerichtWriter.BrpPersoonWriter(path, resultaatBericht);
        SelectieResultaatBerichtWriter writer = (SelectieResultaatBerichtWriter) ReflectionTestUtils.getField(brpPersoonWriter, "writer");
        ReflectionTestUtils.setField(writer,  "writer", null);
        ReflectionTestUtils.invokeMethod(writer, "doClose", null);
    }

    private SelectieResultaatBericht maakSelectieResultaatBericht() {
        return new SelectieResultaatBericht(maakBasisBerichtgegevensBuilder().build(), maakSelectieKenmerkenBuilder().build());
    }

    private SelectieResultaatBericht maakSelectieResultaatBerichtMetParameters() {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = maakBasisBerichtgegevensBuilder()
            .metParameters()
                .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                .metDienst(dienst)
            .eindeParameters()
        .build();
        //@formatter:on
        return new SelectieResultaatBericht(basisBerichtGegevens, maakSelectieKenmerkenBuilder().build());
    }

    private BasisBerichtGegevens.Builder maakBasisBerichtgegevensBuilder() {
        return BasisBerichtGegevens.builder()
                .metAdministratieveHandelingId(1L)
                .metStuurgegevens()
                .metReferentienummer(REFNR)
                .metCrossReferentienummer(CROSSREFNR)
                .metZendendePartij(ZENDENDE_PARTIJ)
                .metOntvangendePartij(ONTVANGENDE_PARTIJ)
                .metZendendeSysteem(ZENDEND_SYSTEEM)
                .metTijdstipVerzending(TS_VERZENDING)
                .eindeStuurgegevens();
    }

    private SelectieKenmerken.Builder maakSelectieKenmerkenBuilder() {
        return SelectieKenmerken.builder()
                .metSoortSelectieresultaatSet("SoortSelResultaatSet")
                .metSoortSelectieresultaatVolgnummer(1)
                .metSoortSelectie(SoortSelectie.STANDAARD_SELECTIE)
                .metLeveringsautorisatie(levaut)
                .metDienst(dienst)
                .metSelectietaakId(1)
                .metDatumUitvoer(20140101);
    }

}
