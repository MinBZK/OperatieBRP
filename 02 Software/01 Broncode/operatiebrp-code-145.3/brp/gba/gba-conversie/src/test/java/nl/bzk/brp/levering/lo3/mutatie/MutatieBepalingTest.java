/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mutatie;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;
import static nl.bzk.brp.levering.lo3.support.TestHelper.assertElementen;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.builder.MetaObjectAdder;
import nl.bzk.brp.levering.lo3.conversie.AbstractConversieIntegratieTest;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.mutatie.MutatieConverteerder;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MutatieBepalingTest extends AbstractConversieIntegratieTest {

    @Autowired
    private MutatieConverteerder subject;

    private AdministratieveHandeling geboorteHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(11L, "000034", ZonedDateTime.of(1920, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID), SoortAdministratieveHandeling
                    .GEBOORTE_IN_NEDERLAND)
            .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, ZonedDateTime.of(1920, 1, 2, 0, 0, 0, 0, DatumUtil
                    .BRP_ZONE_ID), "000001", 19200101)
            ).build());
    private Actie actieGeboorte = geboorteHandeling.getActies().iterator().next();

    @Test
    public void testOverlijden() {
        final ZonedDateTime tsReg = ZonedDateTime.of(2014, 7, 12, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling overlijdenHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(24, "000034", tsReg, SoortAdministratieveHandeling
                        .OVERLIJDEN_IN_NEDERLAND)
                .metObject(TestVerantwoording.maakActieBuilder(3L, SoortActie.REGISTRATIE_OVERLIJDEN, tsReg, "000001", 20140711)
                ).build());
        final Actie actieOverlijden = overlijdenHandeling.getActies().iterator().next();

        final MetaObject.Builder builder =
                maakIngeschrevene(
                        Collections.emptyList(),
                        Arrays.asList(
                                b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(
                                        b,
                                        actieGeboorte,
                                        actieGeboorte.getDatumOntlening(),
                                        "6030",
                                        "6030"),
                                b -> MetaObjectUtil.voegPersoonOverlijdenGroepToe(
                                        b,
                                        actieOverlijden,
                                        null,
                                        null,
                                        actieOverlijden.getDatumOntlening(),
                                        "0518",
                                        "6030",
                                        null,
                                        null)));

        final Persoonslijst persoonslijst =
                new Persoonslijst(builder.build(), 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoonslijst, null, overlijdenHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_06,
                true,
                Lo3ElementEnum.ELEMENT_0820,
                "0518",
                Lo3ElementEnum.ELEMENT_8610,
                "20140712",
                Lo3ElementEnum.ELEMENT_0810,
                "20140711",
                Lo3ElementEnum.ELEMENT_0830,
                "6030",
                Lo3ElementEnum.ELEMENT_8510,
                "20140711");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_56,
                true,
                Lo3ElementEnum.ELEMENT_0820,
                "",
                Lo3ElementEnum.ELEMENT_8610,
                "",
                Lo3ElementEnum.ELEMENT_0810,
                "",
                Lo3ElementEnum.ELEMENT_0830,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "");
        Assert.assertEquals(2, resultaat.size());
    }

    @Test
    public void testVerhuizen() {
        final ZonedDateTime tsReg = ZonedDateTime.of(1947, 3, 8, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling verhuizenHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(24, "000034", tsReg, SoortAdministratieveHandeling
                        .VERHUIZING_BINNENGEMEENTELIJK)
                .metObject(TestVerantwoording.maakActieBuilder(2L, SoortActie.REGISTRATIE_ADRES, tsReg, "000001", 19470307)
                ).build());
        final Actie actieVerhuizen = verhuizenHandeling.getActies().iterator().next();

        MetaObject.Builder adresBuilder =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                        .metId(11)
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                        .metRecord()
                        .metId(12)
                        .eindeRecord()
                        .eindeGroep()
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                        .metId(44)
                        .metActieInhoud(actieGeboorte)
                        .metDatumAanvangGeldigheid(19200101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "I")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19200101)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 10)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "A")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "Naam")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2245HJ")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
                        .eindeRecord()
                        .eindeGroep()
                        .eindeObject();

        final MetaObject.Builder builder =
                maakIngeschrevene(
                        () -> adresBuilder,
                        b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(
                                b,
                                actieGeboorte,
                                actieGeboorte.getDatumOntlening(),
                                "6030",
                                "6030"));

        MetaRecord mutatie =
                MetaObject.maakBuilder()
                        .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId()))
                        .metGroep()
                        .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                        .metId(44)
                        .metActieInhoud(actieVerhuizen)
                        .metDatumAanvangGeldigheid(19470307)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "I")
                        .metAttribuut(
                                ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()),
                                actieVerhuizen.getDatumOntlening())
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 46)
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "Pippelingstraat")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2522HT")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                        .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
                        .eindeRecord()
                        .eindeGroep()
                        .eindeObject()
                        .build()
                        .getGroep(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .getRecords()
                        .iterator()
                        .next();

        MetaObjectAdder persoonAdder = MetaObjectAdder.nieuw(builder.build());
        persoonAdder.voegPersoonMutatieToe(mutatie);

        final Persoonslijst persoonslijst =
                new Persoonslijst(persoonAdder.build(), 0L);

        final List<Lo3CategorieWaarde> resultaat = subject.converteer(persoonslijst, null, verhuizenHandeling, null, new ConversieCache());

        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_08,
                true,
                Lo3ElementEnum.ELEMENT_1115,
                "Pippelingstraat",
                Lo3ElementEnum.ELEMENT_1160,
                "2522HT",
                Lo3ElementEnum.ELEMENT_1030,
                "19470307",
                Lo3ElementEnum.ELEMENT_1120,
                "46",
                Lo3ElementEnum.ELEMENT_1130,
                "",
                Lo3ElementEnum.ELEMENT_1020,
                "",
                Lo3ElementEnum.ELEMENT_1140,
                "",
                Lo3ElementEnum.ELEMENT_8510,
                "19470307",
                Lo3ElementEnum.ELEMENT_8610,
                "19470308");
        assertElementen(
                resultaat,
                Lo3CategorieEnum.CATEGORIE_58,
                true,
                Lo3ElementEnum.ELEMENT_1115,
                "Naam",
                Lo3ElementEnum.ELEMENT_1160,
                "2245HJ",
                Lo3ElementEnum.ELEMENT_1030,
                "19200101",
                Lo3ElementEnum.ELEMENT_1120,
                "10",
                Lo3ElementEnum.ELEMENT_1130,
                "A",
                Lo3ElementEnum.ELEMENT_1020,
                "deel vd gemeente",
                Lo3ElementEnum.ELEMENT_1140,
                "A",
                Lo3ElementEnum.ELEMENT_8510,
                "19200101",
                Lo3ElementEnum.ELEMENT_8610,
                "19200102");
        Assert.assertEquals(2, resultaat.size());
    }
}
