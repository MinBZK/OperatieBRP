/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 * ModelFilterTest.
 */
public class ModelFilterTest {

    @Test
    public void testMetObjectZonderGroepMaarMetObject() {
        final MetaObject persoon = maakObjectMetLegeGroep();
        System.out.println(ModelAfdruk.maakAfdruk(persoon));
        final MetaObjectFilter filter = new MetaObjectFilter(persoon).addRecordFilter(metaRecord -> false);
        MetaObject resultaat = filter.filter();
        Assert.assertNull(resultaat);

    }

    @Test
    public void testMetGroepen() {
        final MetaObject persoon = maakObjectMetGroepDirectOnderObject();
        System.out.println(ModelAfdruk.maakAfdruk(persoon));

        final MetaObjectFilter filter = new MetaObjectFilter(persoon);
        MetaObject resultaat = filter.filter();
        System.out.println(ModelAfdruk.maakAfdruk(resultaat));
        final Set<MetaObject> objecten = resultaat.getObjecten();
        Assert.assertTrue(objecten.isEmpty());

    }

    @Test
    public void testMetLeegObject() {
        final MetaObject persoon = maakObjectMetLeegObject();
        System.out.println(ModelAfdruk.maakAfdruk(persoon));

        final MetaObjectFilter filter = new MetaObjectFilter(persoon);
        MetaObject resultaat = filter.filter();
        Assert.assertNull(resultaat);
    }

    @Test
    public void testMetComplexObject() {
        final MetaObject persoon = maakComplexObject();
        final MetaObjectFilter filter = new MetaObjectFilter(persoon);
        MetaObject resultaat = filter.filter();
        Assert.assertNotNull(resultaat);
        Assert.assertTrue(persoon.deepEquals(resultaat));
    }

    private MetaObject maakObjectMetLeegObject() {
        return MetaObject.maakBuilder().metObjectElement(Element.PERSOON.getId()).metId(1)
                .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .eindeObject().build();
    }


    private MetaObject maakObjectMetGroepDirectOnderObject() {
        return TestBuilders.maakLeegPersoon(1)
                .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime()))
                .metAttribuut(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM.getId(), "Amsterdam")
                .eindeRecord()
                .eindeGroep().build();
    }


    private MetaObject maakObjectMetLegeGroep() {
        final Actie actieInhoud = TestVerantwoording.maakActie(1);
        return MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId()).metId(1)
                .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(actieInhoud)
                .metDatumAanvangGeldigheid(DatumUtil.vandaag())
                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                .eindeRecord()
                .eindeGroep().eindeObject().build();
    }


    private MetaObject maakComplexObject() {
        int id = 0;
        //@formatter:off
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        return TestBuilders.maakIngeschrevenPersoon()
            .metObject()
                .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                .metId(id++)
                .metGroep()
                    .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                        .metRecord()
                            .metId(id++)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), 1)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), 3)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 223)
                        .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                        .metRecord()
                            .metId(id++)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 10102001)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 3)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(),
                                 nu)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL.getId(), 3)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId(),
                                nu)
                        .eindeRecord()
                        .metRecord()
                            .metId(id++)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 10102003)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 2)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), nu)
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
            .metObjectElement(Element.PERSOON_ADRES.getId())
            .metId(id++)
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                        .metRecord()
                            .metId(id++)
                            .metAttribuut(Element.PERSOON_ADRES_GEMEENTECODE.getId(), 1)
                            .eindeRecord()
                        .metRecord()
                            .metId(id++)
                            .metAttribuut(Element.PERSOON_ADRES_GEMEENTECODE.getId(), 2)
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(id++)
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                            .metRecord()
                                .metId(id++)
                                .metActieInhoud(TestVerantwoording.maakActie(1))
                            .eindeRecord()
                    .eindeGroep()
                    .metGroep()
                        .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                            .metRecord()
                                .metId(id++)
                                .metAttribuut(Element.PERSOON_ADRES_GEMEENTECODE.getId(), 1)
                            .eindeRecord()
                            .metRecord()
                                .metId(id)
                                .metAttribuut(Element.PERSOON_ADRES_GEMEENTECODE.getId(), 2)
                            .eindeRecord()
                    .eindeGroep()
            .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
    }
}
