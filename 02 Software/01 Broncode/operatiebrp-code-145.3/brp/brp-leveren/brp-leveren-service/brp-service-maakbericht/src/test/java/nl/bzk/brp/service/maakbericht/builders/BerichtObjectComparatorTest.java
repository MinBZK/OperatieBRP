/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtObjectComparatorTest.
 */
public class BerichtObjectComparatorTest {

    @Test
    public void testSorteerOnderzoek() {
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        builder1.metId(111)
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                .metRecord()
                .metId(456)
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.ONDERZOEK_DATUMAANVANG.getId(), 20101011)
                .eindeRecord()
                .eindeGroep().eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                .metRecord()
                .metId(457)
                .metActieInhoud(actieInhoud)
                .metAttribuut(Element.ONDERZOEK_DATUMAANVANG.getId(), 19000000)
                .eindeRecord()
                .eindeGroep().eindeObject();

        final Persoonslijst persoonsLijst = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage datumaanvang eerst
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testSorteerBetrokkenheid() {

        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
                .metObjectElement(Element.PERSOON_KIND.getId())
                .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(112)
                .metObjectElement(Element.PERSOON_OUDER.getId())
                .eindeObject();

        final Persoonslijst persoonsLijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //kind, ouder, partner
        Assert.assertEquals(-1, compare);

    }

    @Test
    public void testSorteerVolgordeBetrokkenPersonen() {
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
            builder1.metId(111)
            .metObjectElement(Element.PERSOON_KIND.getId())
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(5)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT))
                            .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                .eindeObject()
            .eindeObject();

       final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(112)
            .metObjectElement(Element.PERSOON_KIND.getId())
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(4)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT))
                            .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                .eindeObject()
            .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //betrokken persoon
        Assert.assertEquals(1, compare);
    }


    @Test
    public void testSorteerVolgordeBetrokkenPersoon() {
        //1 betrokkenheid geen persoon, sorteert op betrokkenheid id
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
            builder1.metId(111)
            .metObjectElement(Element.PERSOON_KIND.getId())
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(4)
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT))
                            .eindeGroep()
                            .eindeObject()
                        .eindeObject()
                .eindeObject()
            .eindeObject();

       final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(110)
            .metObjectElement(Element.PERSOON_KIND.getId())
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD))
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
            .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst = new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //betrokken persoon
        Assert.assertEquals(1, compare);
    }


    @Test
    public void testDefaultSorteringVolgnummerAttribuut() {

        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
                .metObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT.getId())
                .metRecord()
                .metId(456)
                .metAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER.getId(), 3)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId()))
                .metRecord()
                .metId(4)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.gisteren()))
                .eindeRecord()
                .eindeGroep().eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
                .metObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT.getId())
                .metRecord()
                .metId(457)
                .metAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER.getId(), 1)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId()))
                .metRecord()
                .metId(4)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.gisteren()))
                .eindeRecord()
                .eindeGroep().eindeObject();
        final MetaObject persoon = TestBuilders.maakLeegPersoon().metObject(builder1).metObject(builder2).build();
        final Persoonslijst persoonsLijst = new Persoonslijst(persoon, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage volgnummer eerst, builder2 eerst
        Assert.assertEquals(1, compare);

    }


    @Test
    public void testDefaultSorteringVolgnummerElement() {
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(110)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .eindeObject();

        final MetaObject persoon = TestBuilders.maakLeegPersoon().metObject(builder1).metObject(builder2).build();
        final Persoonslijst persoonsLijst = new Persoonslijst(persoon, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //element volgnummer, eerst adres
        Assert.assertEquals(1, compare);
    }

    @Test
    public void testDefaultSorteringObjectSleutel() {
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(110)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .eindeObject();

        final MetaObject persoon = TestBuilders.maakLeegPersoon().metObject(builder1).metObject(builder2).build();
        final Persoonslijst persoonsLijst = new Persoonslijst(persoon, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //hoogste objectsleutel eerst
        Assert.assertEquals(-1, compare);
    }


    @Test
    public void testDefaultSorteringNationaliteit() {
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 2)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 1)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage nationaliteitcode eerst
        Assert.assertEquals(1, compare);

        final int compare1 = berichtObjectComparator.compare(builder2.build(), builder1.build());
        //lage nationaliteitcode eerst
        Assert.assertEquals(-1, compare1);
    }

    @Test
    public void testDefaultSorteringVerificatiePartijOngelijk() {
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_VERIFICATIE_SOORT.getId(), "A")
                    .metAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId(), 2)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_VERIFICATIE_SOORT.getId(), "B")
                    .metAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId(), 1)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);
        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage partij eerst
        Assert.assertEquals(1, compare);

        final int compare1 = berichtObjectComparator.compare(builder2.build(), builder1.build());
        //lage partij eerst
        Assert.assertEquals(-1, compare1);
    }

    @Test
    public void testDefaultSorteringVerificatiePartijGelijkSoortOngelijk() {
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        //@formatter:off
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_VERIFICATIE_SOORT.getId(), "A")
                    .metAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId(), 2)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_VERIFICATIE_SOORT.getId(), "B")
                    .metAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId(), 2)
                .eindeRecord()
                .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on

        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage soort eerst
        Assert.assertEquals(-1, compare);

        final int compare1 = berichtObjectComparator.compare(builder2.build(), builder1.build());
        //lage soort eerst
        Assert.assertEquals(1, compare1);
    }

    @Test
    public void testDefaultSorteringVerificatieAllesGelijk() {
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_VERIFICATIE_SOORT.getId(), "A")
                    .metAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId(), 2)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_VERIFICATIE.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_VERIFICATIE_SOORT.getId(), "A")
                    .metAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId(), 2)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());

        Assert.assertEquals(0, compare);
    }

    @Test
    public void testSorteringBlPersNummer_AutVanAfgifteOngelijk_NummerGelijk() {
        //@formatter:on
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
                .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                .metId(456)
                .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(), 1)
                .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                .metId(457)
                .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
                .eindeGroep()
                .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
                .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                .metId(457)
                .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(), 2)
                .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                .metId(457)
                .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
                .eindeGroep()
                .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage aut_van_afgiftecode eerst
        Assert.assertEquals(-1, compare);

        final int compare1 = berichtObjectComparator.compare(builder2.build(), builder1.build());
        Assert.assertEquals(1, compare1);
    }

    @Test
    public void testSorteringBlPersNummer_AutVanAfgifteGelijk_NummerOngelijk() {
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(), 1)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(), 1)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "2")
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage nummer eerst
        Assert.assertEquals(-1, compare);

        final int compare1 = berichtObjectComparator.compare(builder2.build(), builder1.build());
        //lage nummer eerst
        Assert.assertEquals(1, compare1);
    }


    @Test
    public void testSorteringBlPersNummer_AllesOnGelijk() {
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(), 1)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(),  2)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "2")
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        //lage aut_van_afgiftecode eerst
        Assert.assertEquals(-1, compare);

        final int compare1 = berichtObjectComparator.compare(builder2.build(), builder1.build());
        Assert.assertEquals(1, compare1);
    }


    @Test
    public void testSorteringBlPersNummer_AllesGelijk() {
        //@formatter:off
        final MetaObject.Builder builder1 = MetaObject.maakBuilder();
        builder1.metId(111)
            .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                    .metId(456)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(),  1)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();

        final MetaObject.Builder builder2 = MetaObject.maakBuilder();
        builder2.metId(111)
            .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                .metRecord()
                    .metId(457)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE.getId(),  1)
                    .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD.getId())
                .metRecord()
                    .metId(457)
                    .metActieInhoud(TestVerantwoording.maakActie(1))
                .eindeRecord()
            .eindeGroep()
        .eindeObject();
        //@formatter:on
        final Persoonslijst persoonsLijst1 = new Persoonslijst(TestBuilders.maakLeegPersoon()
                .metObject(builder1).metObject(builder2).build(), 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(null, persoonsLijst1, null, null, new StatischePersoongegevens());
        final BerichtObjectComparator berichtObjectComparator = new BerichtObjectComparator(berichtgegevens);

        final int compare = berichtObjectComparator.compare(builder1.build(), builder2.build());
        Assert.assertEquals(0, compare);
    }
}
