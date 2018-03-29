/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Lists;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BevragingBerichtWriterTest {

    private static final String REF_NR = "88409eeb-1aa5-43fc-8614-43055123a165";
    private static final ZonedDateTime TS = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);

    private static final String GEEF_DET_PERS_ROOTELEM = "lvg_bvgGeefDetailsPersoon_R";
    private static final String ZOEK_PERS_ROOTELEM = "lvg_bvgZoekPersoon_R";
    private static final String ZOEK_PERS_OP_ADRES_ROOT_ELEM = "lvg_bvgZoekPersoonOpAdres_R";

    @Test
    public void writeDienstSpecifiekElementGeefDetailsPersoon() throws Exception {
        final BevragingCallbackImpl bevragingBerichtWriter = new BevragingCallbackImpl();
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setBericht(maakVerwerkPersoonBericht());
        bevragingBerichtWriter.verwerkResultaat(verzoek, berichtResultaat);
        //geef details persoon dus geen sortering op bsn
        final String berichtDummy = removeLineEndings(
                IOUtils.toString(BevragingBerichtWriterTest.class.getResourceAsStream("/geef_det_pers_bevragingberichtwritertest.xml"),
                        StandardCharsets.UTF_8));
        Assert.assertEquals(berichtDummy, removeLineEndings(bevragingBerichtWriter.getResultaat()));
    }

    @Test
    public void writeDienstSpecifiekElementZoekPersoon() throws Exception {
        final BevragingCallbackImpl bevragingBerichtWriter = new BevragingCallbackImpl();
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setBericht(maakVerwerkPersoonBericht());
        bevragingBerichtWriter.verwerkResultaat(verzoek, berichtResultaat);
        //zoek-persoon > personen worden gesorteerd op bsn
        final String berichtDummy = removeLineEndings(
                IOUtils.toString(BevragingBerichtWriterTest.class.getResourceAsStream("/zoek_persoon_bevragingberichtwritertest.xml"),
                        StandardCharsets.UTF_8));
        Assert.assertEquals(berichtDummy, removeLineEndings(bevragingBerichtWriter.getResultaat()));
    }

    @Test
    public void writeDienstSpecifiekElementZoekPersoonOpAdres() throws Exception {
        final BevragingCallbackImpl bevragingBerichtWriter = new BevragingCallbackImpl();
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS);
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setBericht(maakVerwerkPersoonBericht());
        bevragingBerichtWriter.verwerkResultaat(verzoek, berichtResultaat);
        //zoek-persoon > personen worden gesorteerd op bsn
        final String berichtDummy = removeLineEndings(
                IOUtils.toString(getClass().getResourceAsStream("/zoek_persoon_op_adres_bevragingberichtwritertest.xml"),
                        StandardCharsets.UTF_8));
        Assert.assertEquals(berichtDummy, removeLineEndings(bevragingBerichtWriter.getResultaat()));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void writeDienstSpecifiekElementOngeldigeDienst() throws Exception {
        final BevragingVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        verzoek.setSoortDienst(SoortDienst.ATTENDERING);
        final BevragingCallbackImpl callback = new BevragingCallbackImpl();
        final BevragingResultaat berichtResultaat = new BevragingResultaat();
        berichtResultaat.setBericht(maakVerwerkPersoonBericht());
        callback.verwerkResultaat(verzoek, berichtResultaat);
    }

    private VerwerkPersoonBericht maakVerwerkPersoonBericht() {
        final List<BijgehoudenPersoon> bijgehoudenPersonen = Lists.newArrayList(maakBijgehoudenPersoon("333333333"),
                maakBijgehoudenPersoon("999999999"),
                maakBijgehoudenPersoon("111111111"));

        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metTijdstipRegistratie(TS)
                .metParameters()
                .metDienst(maakDienst())
                .metSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT)
                .eindeParameters()
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau("Geslaagd").build())
                .metStuurgegevens()
                .metTijdstipVerzending(TS)
                .metReferentienummer(REF_NR)
                .metCrossReferentienummer(REF_NR)
                .metZendendePartij(TestPartijBuilder.maakBuilder().metCode("000123").build())
                .metOntvangendePartij(TestPartijBuilder.maakBuilder().metCode("000456").build())
                .metZendendeSysteem("BRP")
                .eindeStuurgegevens().build();
        //@formatter:on
        return new VerwerkPersoonBericht(basisBerichtGegevens, null, bijgehoudenPersonen);
    }

    private BijgehoudenPersoon maakBijgehoudenPersoon(final String bsn) {
        final ZonedDateTime tsReg = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final Actie actie = TestVerantwoording.maakActie(1, tsReg);
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
            .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS))
            .metRecord()
            .metId(111)
            .metActieInhoud(actie)
            .metAttribuut(getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER), bsn)
            .eindeRecord()
            .eindeGroep()
            .eindeObject().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        return new BijgehoudenPersoon.Builder(persoonslijst, bouwBerichtElement(bsn)).build();
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

    public BerichtElement bouwBerichtElement(final String bsn) {
        final String naam = "bsn";
        final String waarde = String.valueOf(bsn);
        final BerichtElement.Builder builder = BerichtElement.builder();
        builder.metNaam(naam);
        builder.metWaarde(waarde);
        return builder.build();
    }

    private String removeLineEndings(String value) {
        return value.replaceAll("\\s+", "");
    }
}
