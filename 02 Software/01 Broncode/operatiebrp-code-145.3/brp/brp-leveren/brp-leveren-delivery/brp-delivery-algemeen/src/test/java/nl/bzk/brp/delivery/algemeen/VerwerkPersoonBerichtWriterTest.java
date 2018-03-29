/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.bericht.BerichtTestUtil;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * VerwerkPersoonBerichtWriterTest.
 */
public class VerwerkPersoonBerichtWriterTest extends BerichtTestUtil {

    @Test
    public void testWriteVerwerkPersoonBericht_MutatieBerichtMetAdmnHnd() throws StapException, TransformerException, IOException, SAXException {

        final List<BijgehoudenPersoon> bijgehoudenPersonen = maakBijgehoudenPersoonLijst();
        final String referentienummer = "88409eeb-1aa5-43fc-8614-43055123a165";

        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                    .metAdministratieveHandelingId(1L)
                    .metSoortNaam("soortNaam")
                    .metCategorieNaam("categorieNaam")
                    .metTijdstipRegistratie(ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID))
                    .metParameters()
                        .metDienst(maakDienst())
                        .metSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT)
                    .eindeParameters()
                    .metStuurgegevens()
                        .metTijdstipVerzending(getVasteDatum())
                        .metReferentienummer(referentienummer)
                        .metCrossReferentienummer(referentienummer)
                        .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                        .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
                        .metZendendeSysteem("BRP")
                    .eindeStuurgegevens().build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, bijgehoudenPersonen);
        final MaakPersoonBerichtServiceImpl berichtService = new MaakPersoonBerichtServiceImpl();
        final String xml = berichtService.maakPersoonBericht(bericht);
        assertGelijk("verwerkpersoonbericht1.xml", indent(xml));
    }

    @Test
    public void testWriteVerwerkPersoonBericht_VolledigBerichtZonderAdmnHnd()
            throws TransformerException, XMLStreamException, IOException, SAXException, StapException {

        final List<BijgehoudenPersoon> bijgehoudenPersonen = maakBijgehoudenPersoonLijst();
        final String referentienummer = "88409eeb-1aa5-43fc-8614-43055123a165";

        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metTijdstipRegistratie(ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID))
                .metParameters()
                    .metDienst(maakDienst())
                    .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                .eindeParameters()
                .metStuurgegevens()
                    .metTijdstipVerzending(getVasteDatum())
                    .metReferentienummer(referentienummer)
                    .metCrossReferentienummer(referentienummer)
                    .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                    .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
                    .metZendendeSysteem("BRP")
                .eindeStuurgegevens().build();
        //@formatter:on
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, bijgehoudenPersonen);
        final MaakPersoonBerichtServiceImpl berichtService = new MaakPersoonBerichtServiceImpl();
        final String xml = berichtService.maakPersoonBericht(bericht);
        assertGelijk("verwerkpersoonbericht2.xml", indent(xml));
    }

    private Dienst maakDienst() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        leveringsautorisatie.setId(4);
        final List<Dienst> diensten = new ArrayList<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.ATTENDERING);
        dienst.setId(1);
        return dienst;
    }

    private List<BijgehoudenPersoon> maakBijgehoudenPersoonLijst() {
        final ZonedDateTime tsReg = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metObject()
            .metId(123)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(124)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .metRecord()
                        .metId(100)
                        .metActieInhoud(TestVerantwoording.maakActie(1, tsReg))
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER),  1)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .eindeObject().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(persoonslijst, new BerichtElement("test")).build();
        return Lists.newArrayList(bijgehoudenPersoon);
    }
}
