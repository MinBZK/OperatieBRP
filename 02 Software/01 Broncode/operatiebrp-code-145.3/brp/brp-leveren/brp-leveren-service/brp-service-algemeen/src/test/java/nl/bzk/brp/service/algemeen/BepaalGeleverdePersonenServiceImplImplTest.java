/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Unit test voor {@link BepaalGeleverdePersonenServiceImpl}.
 */
public class BepaalGeleverdePersonenServiceImplImplTest {

    private BepaalGeleverdePersonenService service = new BepaalGeleverdePersonenServiceImpl();

    @Test
    public void testMetEnkelePersoonMutLev() throws StapException {
        final Persoonslijst persoon = maakPersoon(1, 20160906);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service
                .bepaal(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1, singletonList(persoon), null);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(20160906));
    }

    @Test
    public void testMetEnkelePersoonSynchrPers() throws StapException {
        final Persoonslijst persoon = maakPersoon(1, 20160906);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service.bepaal(SoortDienst.SYNCHRONISATIE_PERSOON, 1, singletonList(persoon), null);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(20160906));
    }

    @Test
    public void testMetEnkelePersoonPlaatsAfnIndicatie() throws StapException {
        final Persoonslijst persoon = maakPersoon(1, 20160906);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service.bepaal(SoortDienst.PLAATSING_AFNEMERINDICATIE, 1, singletonList(persoon), null);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(20160906));
    }


    @Test
    public void testMetEnkelePersoonDienstNvt() throws StapException {
        final Persoonslijst persoon = maakPersoon(1, 20160906);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service.bepaal(SoortDienst.ZOEK_PERSOON, 1, singletonList(persoon), null);

        Assert.isNull(resultaat.getDatumAanvangMaterielePeriodeResultaat());
    }

    @Test
    public void testMeerderePersonenVerschillendeDampen() throws Exception {
        final Persoonslijst persoon1 = maakPersoon(1, 20160906);
        final Persoonslijst persoon2 = maakPersoon(2, 20160905);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service
                .bepaal(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1, asList(persoon1, persoon2), null);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(nullValue()));
        assertThat(resultaat.getLeveringPersonen().get(0).getDatumAanvangMaterielePeriode(), is(20160906));
        assertThat(resultaat.getLeveringPersonen().get(1).getDatumAanvangMaterielePeriode(), is(20160905));
    }

    @Test
    public void testMeerderePersonenGelijkeDampen() throws Exception {
        final Persoonslijst persoon1 = maakPersoon(1, 20160906);
        final Persoonslijst persoon2 = maakPersoon(2, 20160906);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service
                .bepaal(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1, asList(persoon1, persoon2), null);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(20160906));
        assertThat(resultaat.getLeveringPersonen().get(0).getDatumAanvangMaterielePeriode(), is(nullValue()));
        assertThat(resultaat.getLeveringPersonen().get(1).getDatumAanvangMaterielePeriode(), is(nullValue()));
    }

    @Test
    public void testEnkelePersoonGeenDamp() throws Exception {
        //@formatter:off
        final Persoonslijst persoon = new Persoonslijst(
            MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON)
                .metId(1)
                .metObject()
                    .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                    .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metActieInhoud(TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime()))
                            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 1)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .build(), 0L);
        //@formatter:off

        final BepaalGeleverdePersonenService.Resultaat resultaat = service.bepaal(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
            singletonList(persoon), null);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(nullValue()));
        assertThat(resultaat.getLeveringPersonen().get(0).getDatumAanvangMaterielePeriode(), is(nullValue()));
    }

    @Test
    public void testDampMeegegeven() {
        final Persoonslijst persoon1 = maakPersoon(1, 20160906);
        final Persoonslijst persoon2 = maakPersoon(2, 20160907);

        final BepaalGeleverdePersonenService.Resultaat resultaat = service.bepaal(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
            asList(persoon1, persoon2), 20160908);

        assertThat(resultaat.getDatumAanvangMaterielePeriodeResultaat(), is(20160908));
        assertThat(resultaat.getLeveringPersonen().get(0).getDatumAanvangMaterielePeriode(), is(nullValue()));
        assertThat(resultaat.getLeveringPersonen().get(1).getDatumAanvangMaterielePeriode(), is(nullValue()));
    }

    private Persoonslijst maakPersoon(long persoonId, int damp) {
        //@formatter:off
        MetaObject persoon = TestBuilders.maakIngeschrevenPersoon()
            .metId(persoonId)
            .metObject()
                .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId()))
                .metGroep()
                    .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId()))
                    .metRecord()
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId()), 1)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()), DatumUtil
                            .nuAlsZonedDateTime())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId()), damp)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId()))
                    .metRecord()
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId()), 1)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId()), "000001")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId()), 1)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        return new Persoonslijst(persoon, 0L);
    }

}
