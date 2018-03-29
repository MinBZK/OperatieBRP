/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import org.junit.Test;

/**
 * PersoonMetaObjectValidatorTest.
 */
public class PersoonMetaObjectValidatorTest {

    @Test(expected = IllegalStateException.class)
    public void testValideerInvalideParentVoorGroep() throws Exception {

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(999)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                    .metRecord().metId(1).eindeRecord()
            .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        PersoonMetaObjectValidator.valideer(persoonMeta);
    }

    @Test(expected = IllegalStateException.class)
    public void testValideerGeenValideParentVoorObject() throws Exception {

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(999)
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metObjectElement(Element.BETROKKENHEID.getId())
            .eindeObject()
        .build();
        //@formatter:on
        PersoonMetaObjectValidator.valideer(persoonMeta);
    }

    @Test(expected = IllegalStateException.class)
    public void testValideerNullObject() throws Exception {
        PersoonMetaObjectValidator.valideer(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testValideerGeenPersoonObject() throws Exception {

        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metId(999)
            .metObjectElement(Element.DOCUMENT)
            .eindeObject()
        .build();
        //@formatter:on
        PersoonMetaObjectValidator.valideer(metaObject);
    }

    @Test
    public void testAttrWaardeCorrect_ActieVerval() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_ACTIEVERVAL, TestVerantwoording.maakActie(1, TestDatumUtil.gisteren())));
    }
    @Test
    public void testAttrWaardeCorrect_ActieInhoud() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_ACTIEINHOUD, TestVerantwoording.maakActie(1, TestDatumUtil.gisteren())));
    }
    @Test
    public void testAttrWaardeCorrect_ActieAanpassingGeldigheid() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_ACTIEAANPASSINGGELDIGHEID, TestVerantwoording.maakActie(1, TestDatumUtil.gisteren())));
    }
    @Test
    public void testAttrWaardeCorrect_ActieVervalTbvLevMut() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES, TestVerantwoording.maakActie(1, TestDatumUtil.gisteren())));
    }

    @Test
    public void testAttrWaardeCorrect_Boolean() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES, true));
    }

    @Test
    public void testAttrWaardeCorrect_Datum() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID, 20140101));
    }

    @Test
    public void testAttrWaardeCorrect_DatumTijd() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_TIJDSTIPREGISTRATIE, ZonedDateTime.now()));
    }

    @Test
    public void testAttrWaardeCorrect_Getal() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_GEMEENTECODE, 1));
    }

    @Test
    public void testAttrWaardeCorrect_GrootGetal() {
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
                        .metGroep()
                            .metGroepElement(Element.PERSOON_INSCHRIJVING.getId())
                                .metRecord()
                                    .metAttribuut(Element.PERSOON_INSCHRIJVING_VERSIENUMMER.getId(), 123456789L)
                                .eindeRecord()
                        .eindeGroep()
                .eindeObject()
        .build();
        //@formatter:on
        PersoonMetaObjectValidator.valideer(metaObject);
    }

    @Test
    public void testAttrWaardeCorrect_String() {
        PersoonMetaObjectValidator.valideer(maakObjectMetAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1, "regel"));
    }

    @Test
    public void valideObject() {
        final Actie actie1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(999)
            .metObjectElement(Element.PERSOON)

            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()

            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(20)
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(15)
                        .metActieInhoud(actie1)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 2)
                    .eindeRecord()
                    .metRecord()
                        .metId(25)
                        .metActieInhoud(actie1)
                        .metActieVerval(actie1)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 20)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                    .metRecord().metId(1).eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actie1)
                        .metDatumAanvangGeldigheid(20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        PersoonMetaObjectValidator.valideer(persoonMeta);
    }

    private MetaObject maakObjectMetAttribuut(final Element attrElement, final Object waarde) {
        //@formatter:off
        return MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES)
                        .metGroep()
                            .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                                .metRecord()
                                    .metAttribuut(attrElement.getId(), waarde)
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
                .eindeObject()
        .build();
        //@formatter:on
    }
}
