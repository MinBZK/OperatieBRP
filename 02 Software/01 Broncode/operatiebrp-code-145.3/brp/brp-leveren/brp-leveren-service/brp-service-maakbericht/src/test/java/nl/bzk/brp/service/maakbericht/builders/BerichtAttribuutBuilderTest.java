/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtAttribuutBuilderTest.
 */
public class BerichtAttribuutBuilderTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, ZonedDateTime.of(2010, 3, 22, 4, 10, 30, 17000000, DatumUtil.BRP_ZONE_ID));

    @Test
    public void testBuildBerichtAttrVersleuteldOnderzoekVerwijzing() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoon(), 0L);
        final MaakBerichtPersoonInformatie persoon = new MaakBerichtPersoonInformatie(null);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst, persoon, null, new StatischePersoongegevens());
        new AutorisatieAlles(berichtgegevens);

        final MetaAttribuut gegevenAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId())).iterator().next();
        final BerichtElement gegevenElementNietVersleuteld = BerichtAttribuutBuilder.build(gegevenAttr, berichtgegevens).build();
        final String sleutel1 = gegevenElementNietVersleuteld.getWaarde();
        Assert.assertEquals(gegevenAttr.getWaarde().toString(), sleutel1);

        final String versleuteld = "test";
        berichtgegevens.addVersleuteldeObjectSleutel(persoonslijst.getMetaObject(), versleuteld);
        final BerichtElement gegevenElementVersleuteld = BerichtAttribuutBuilder.build(gegevenAttr, berichtgegevens).build();
        Assert.assertEquals(versleuteld, gegevenElementVersleuteld.getWaarde().toString());
    }

    @Test
    public void testBuildBerichtAttr() {

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoon(), 0L);
        final MaakBerichtPersoonInformatie persoon = new MaakBerichtPersoonInformatie(null);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst, persoon, null, new StatischePersoongegevens());
        new AutorisatieAlles(berichtgegevens);
        //testGetal
        final MetaAttribuut huisnummerAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .PERSOON_ADRES_HUISNUMMER.getId())).iterator().next();
        final BerichtElement berichtElementHuisnummer = BerichtAttribuutBuilder.build(huisnummerAttr, berichtgegevens).build();
        final Integer huisnummer = huisnummerAttr.getWaarde();
        Assert.assertEquals(String.valueOf(huisnummer), berichtElementHuisnummer.getWaarde());
        //gemeente code
        final MetaAttribuut gemeenteCodeAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .PERSOON_ADRES_GEMEENTECODE.getId())).iterator().next();
        final BerichtElement berichtElementGemeenteCode = BerichtAttribuutBuilder.build(gemeenteCodeAttr, berichtgegevens).build();
        Assert.assertEquals("0002", berichtElementGemeenteCode.getWaarde());
        //indicatie
        final MetaAttribuut indicatieAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId())).iterator().next();
        final BerichtElement indicatieAttrElement = BerichtAttribuutBuilder.build(indicatieAttr, berichtgegevens).build();
        Assert.assertEquals("J", indicatieAttrElement.getWaarde());
        //postcode
        final MetaAttribuut postcodeAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .PERSOON_ADRES_POSTCODE.getId())).iterator().next();
        final BerichtElement postcodeAttrElement = BerichtAttribuutBuilder.build(postcodeAttr, berichtgegevens).build();
        Assert.assertEquals("1029PO", postcodeAttrElement.getWaarde());
        //datum aanvang
        final MetaAttribuut dagAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId())).iterator().next();
        final BerichtElement dagAttrElement = BerichtAttribuutBuilder.build(dagAttr, berichtgegevens).build();
        Assert.assertEquals("2010", dagAttrElement.getWaarde());
        //tsreg
        final MetaAttribuut tsregAttr = persoonslijst.getModelIndex().geefAttributen(ElementHelper.getAttribuutElement(Element
                .PERSOON_ADRES_TIJDSTIPREGISTRATIE.getId())).iterator().next();
        final BerichtElement tsregAttrElement = BerichtAttribuutBuilder.build(tsregAttr, berichtgegevens).build();
        Assert.assertEquals("2010-03-22T04:10:30.017Z", tsregAttrElement.getWaarde());
    }


    private MetaObject maakPersoon() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1).metId(111)
            .metObject()
                .metId(112)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metDatumAanvangGeldigheid(20100000)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                        .metAttribuut(Element.PERSOON_ADRES_GEMEENTECODE.getId(), "0002")
                        .metAttribuut(Element.PERSOON_ADRES_POSTCODE.getId(), "1029PO")
                        .metAttribuut(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId(), 20100000)
                        .metAttribuut(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId(), true)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.ONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metId(456)
                            .metAttribuut(Element.ONDERZOEK_PARTIJCODE.getId(), 12)
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                            .metGroep()
                                .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                                .metRecord()
                                    .metId(456)
                                    .metActieInhoud(actieInhoud)
                                    .metAttribuut(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId(), 111)
                                    .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON.getNaam())
                                .eindeRecord()
                            .eindeGroep()
                    .eindeObject()
            .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }
}
