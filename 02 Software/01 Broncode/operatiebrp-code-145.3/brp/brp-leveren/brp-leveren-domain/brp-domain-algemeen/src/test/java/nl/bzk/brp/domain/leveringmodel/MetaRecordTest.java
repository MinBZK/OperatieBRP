/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * MetaRecordTest
 */
public class MetaRecordTest {

    @Test
    public void testSetters() {

        final Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());
        final Actie actieVerval = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        final Actie actieAanpassingGeldigheid = TestVerantwoording.maakActie(1);
        final Actie actieTbvLeverMutaties = TestVerantwoording.maakActie(1);
        final Integer datumAanvangGeldigheid = 20010101;
        final Integer datumEindeGeldigheid = 20090101;
        final int ID = 10;
        final boolean indicatieTvbLeverMutaties = true;
        final String nadereAanduidingVerval = NadereAanduidingVerval.O.getCode();

        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
            .metRecord()
                .metId(ID)
                .metActieInhoud(actieInhoud)
                .metActieVerval(actieVerval)
                .metActieAanpassingGeldigheid(actieVerval)
                .metActieVervalTbvLeveringMutaties(actieTbvLeverMutaties)
                .metDatumAanvangGeldigheid(datumAanvangGeldigheid)
                .metDatumEindeGeldigheid(datumEindeGeldigheid)
                .metIndicatieTbvLeveringMutaties(indicatieTvbLeverMutaties)
                .metNadereAanduidingVerval(nadereAanduidingVerval)
            .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final MetaRecord record = metaObject.getGroepen().iterator().next().getRecords().iterator().next();

        Assert.assertEquals(ID, record.getVoorkomensleutel());
        Assert.assertEquals(actieInhoud, record.getActieInhoud());
        Assert.assertEquals(actieVerval, record.getActieVerval());
        Assert.assertEquals(actieAanpassingGeldigheid, record.getActieAanpassingGeldigheid());
        Assert.assertEquals(actieTbvLeverMutaties, record.getActieVervalTbvLeveringMutaties());
        Assert.assertEquals(datumAanvangGeldigheid, record.getDatumAanvangGeldigheid());
        Assert.assertEquals(datumEindeGeldigheid, record.getDatumEindeGeldigheid());
        Assert.assertEquals(nadereAanduidingVerval, record.getNadereAanduidingVerval());
        Assert.assertEquals(actieInhoud.getTijdstipRegistratie(), record.getTijdstipRegistratie());
        Assert.assertEquals(actieVerval.getTijdstipRegistratie(), record.getDatumTijdVerval());
        Assert.assertEquals(indicatieTvbLeverMutaties, record.isIndicatieTbvLeveringMutaties());

        Assert.assertEquals(actieInhoud.getTijdstipRegistratie(), record.getTijdstipRegistratieAttribuut());
        Assert.assertEquals(actieVerval.getTijdstipRegistratie(), record.getDatumTijdVervalAttribuut());
        Assert.assertEquals("Persoon.Identificatienummers [3344]", record.toString());


    }

    /**
     * Materiele verantwoording kan enkel gevuld zijn op records in materiele groepen..
     */
    @Test(expected = IllegalStateException.class)
    public void testMaterieleAttributenNietOpGroepMetFormeleVerantwoording() {
        final MetaObject metaObject = MetaObject.maakBuilder()
                .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                .metDatumAanvangGeldigheid(DatumUtil.gisteren())
                .eindeRecord()
                .eindeGroep()
                .build();
    }

    @Test
    public void testDienstVerantwoording() {

        Integer dienstInhoud = 1;
        ZonedDateTime tijdstipRegistratie = TestDatumUtil.gisteren();
        Integer dienstVerval = 2;
        ZonedDateTime tijdstipVerval = DatumUtil.nuAlsZonedDateTime();

        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()
            .metObject()
                .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                    .metRecord()
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), dienstInhoud)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), tijdstipRegistratie)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL.getId(), dienstVerval)
                        .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId(), tijdstipVerval)
                    .eindeRecord()
                .eindeGroep()
        .build();
        //@formatter:on

        final MetaRecord record = metaObject.getGroepen().iterator().next().getRecords().iterator().next();

        Assert.assertEquals(dienstInhoud, record.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD).getWaarde());
        Assert.assertEquals(dienstVerval, record.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL).getWaarde());
    }

    /**
     * records zijn uniek binnen een groep
     */
    @Test
    public void testRecordsMetGelijkIdKunnenNietBestaanInDezelfdeGroep() {
        //@formatter:off
        MetaObject metaObject = MetaObject.maakBuilder()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                    .metRecord().metId(1).eindeRecord()
                    .metRecord().metId(1).eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on
        Assert.assertEquals(1, Iterables.getOnlyElement(metaObject.getGroepen()).getRecords().size());
    }

    /**
     * records zijn uniek binnen een groep, maar niet over groepen heen
     */
    @Test
    public void testVerschillendeRecordsInDezelfdeGroep() {
        //@formatter:off
        MetaObject metaObject = MetaObject.maakBuilder()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord().metId(1).metActieVerval(TestVerantwoording.maakActie(1)).eindeRecord()
                //het actuele record
                .metRecord().metId(2).eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on
        Assert.assertEquals(2, Iterables.getOnlyElement(metaObject.getGroepen()).getRecords().size());
    }

    @Test
    public void testAttribuutSetters() {
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId(), "regel")
                        .metAttribuut()
                            .metType(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId())
                            .metWaarde("regel2")
                        .eindeAttribuut()
                        .metAttributen(Lists.newArrayList(
                            MetaAttribuut.maakBuilder().metType(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId()).metWaarde("regel3"),
                            MetaAttribuut.maakBuilder().metType(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId()).metWaarde("regel4")
                    ))
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final MetaRecord record = metaObject.getGroepen().iterator().next().getRecords().iterator().next();
        Assert.assertNotNull(record.getAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1));
        Assert.assertNotNull(record.getAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2));
        Assert.assertNotNull(record.getAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3));
        Assert.assertNotNull(record.getAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4));
    }
}
