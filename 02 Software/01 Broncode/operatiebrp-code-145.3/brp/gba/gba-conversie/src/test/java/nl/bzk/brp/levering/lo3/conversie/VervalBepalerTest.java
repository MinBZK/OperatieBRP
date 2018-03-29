/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
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
import org.junit.Test;

public class VervalBepalerTest {

    private final Actie actieInhoud;
    private final Actie actieVerval;

    public VervalBepalerTest() {

        final AdministratieveHandeling administratievehandeling = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(1, "000001", null,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(1L, SoortActie.REGISTRATIE_OVERLIJDEN, ZonedDateTime.of(1930, 1, 2, 0, 0, 0, 0, DatumUtil
                        .BRP_ZONE_ID), "000001", null)
                ))
                .build());
        actieInhoud = administratievehandeling.getActie(1);

        final AdministratieveHandeling administratievehandeling2 = AdministratieveHandeling.converter()
            .converteer(TestVerantwoording.maakAdministratieveHandeling(2, "000001", null,
                SoortAdministratieveHandeling
                    .GBA_INITIELE_VULLING)
                .metObjecten(Lists.newArrayList(
                    TestVerantwoording.maakActieBuilder(2L, SoortActie.VERVAL_OVERLIJDEN, ZonedDateTime.of(1920, 1, 2, 0, 0, 0, 0, DatumUtil
                        .BRP_ZONE_ID), "000001", null)
                ))
                .build());
        actieVerval = administratievehandeling.getActie(1);

    }

    @Test
    public void zonderActieVerval() {

        final MetaRecord record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieInhoud(actieInhoud)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM.getId()), 19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_GEMEENTECODE.getId()), 52)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE.getId()), 6030)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Collections.singletonList(record);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals("moet geen record vinden", null, vervallenRecord);
    }

    @Test
    public void metActieVerval() {

        final MetaRecord record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieVerval(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM.getId()), 19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_GEMEENTECODE.getId()), 52)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE.getId()), 6030)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Collections.singletonList(record);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals("gevonden record moet juiste id hebben", 1L, vervallenRecord.getVoorkomensleutel());
    }

    @Test
    public void metActieVervalTbvLeveringMutaties() {

        final MetaRecord record =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieVervalTbvLeveringMutaties(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM.getId()), 19400101)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_GEMEENTECODE.getId()), 52)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE.getId()), 6030)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Collections.singletonList(record);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals("gevonden record moet juiste id hebben", 1L, vervallenRecord.getVoorkomensleutel());
    }

    @Test
    public void metActieVervalEnActieAanpassingGeldigheid() {

        final MetaRecord record1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieAanpassingGeldigheid(actieInhoud)
                          .metActieVerval(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord record2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(2L)
                          .metActieVerval(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Arrays.asList(record1, record2);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals(
            "record met actie verval is belangrijker dan record met actie verval en actie aanpassing geldigheid",
            2L,
            vervallenRecord.getVoorkomensleutel());
    }

    @Test
    public void metActieVervalEnActieAanpassingGeldigheidOmgedraaid() {

        final MetaRecord record1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieVerval(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord record2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(2L)
                          .metActieAanpassingGeldigheid(actieInhoud)
                          .metActieVerval(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Arrays.asList(record1, record2);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals(
            "record met actie verval is belangrijker dan record met actie verval en actie aanpassing geldigheid",
            1L,
            vervallenRecord.getVoorkomensleutel());
    }

    @Test
    public void metVervalZonderAanpassingGeldigheidEnVervalTbvLevMuts() {

        final MetaRecord record1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieVerval(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord record2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(2L)
                          .metActieVervalTbvLeveringMutaties(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Arrays.asList(record1, record2);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals(
            "record met actie verval zonder actie aanpassing geldigheid is belangrijker dan record met actie verval tbv levmuts",
            1L,
            vervallenRecord.getVoorkomensleutel());
    }

    @Test
    public void metVervalMetAanpassingGeldigheidEnVervalTbvLevMuts() {

        final MetaRecord record1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(1L)
                          .metActieVerval(actieVerval)
                          .metActieAanpassingGeldigheid(actieInhoud)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord record2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .metRecord()
                          .metId(2L)
                          .metActieVervalTbvLeveringMutaties(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), 59901)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Arrays.asList(record1, record2);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals(
            "record met actie verval tbv levmuts is belangrijker dan record met actie verval en actie aanpassing geldigheid",
            2L,
            vervallenRecord.getVoorkomensleutel());
    }

    @Test
    public void metVervalTbvLeveringMutatiesZonderAanpassingGeldigheid() {

        final MetaRecord record1 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                          .metRecord()
                          .metId(1)
                          .metActieInhoud(actieInhoud)
                          .metActieVerval(actieInhoud)
                          .metActieVervalTbvLeveringMutaties(actieVerval)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), 7341508385L)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final MetaRecord record2 =
                MetaObject.maakBuilder()
                          .metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId()))
                          .metGroep()
                          .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                          .metRecord()
                          .metId(2)
                          .metActieInhoud(actieInhoud)
                          .metActieVerval(actieInhoud)
                          .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), 9694369569L)
                          .eindeRecord()
                          .eindeGroep()
                          .build()
                          .getGroep(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                          .getRecords()
                          .iterator()
                          .next();

        final Collection<MetaRecord> records = Arrays.asList(record1, record2);
        final List<Long> acties = Lists.newArrayList(actieInhoud.getId(), actieVerval.getId());
        final MetaRecord vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        assertEquals(
            "record met actie verval tbv levmuts zonder actie aanpassing geldigheid is belangrijker dan record met actie verval zonder actie aanpassing geldigheid",
            1L,
            vervallenRecord.getVoorkomensleutel());
    }
}
