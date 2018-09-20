/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import static junit.framework.TestCase.assertEquals;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAfgeleidAdministratiefSorteervolgordeComparatorTest {

    private DatumTijdAttribuut                                     tsreg                     = DatumTijdAttribuut.nu();
    private PersoonAfgeleidAdministratiefSorteervolgordeComparator sorteervolgordeComparator =
            new PersoonAfgeleidAdministratiefSorteervolgordeComparator(
                    tsreg);

    @Test
    public void testCompareGelijk() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(1);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(1);

        assertEquals(0, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    @Test
    public void testCompareGelijkMaarVerschillendId() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(1);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(1);
        ReflectionTestUtils.setField(persoonHisVolledig1, "iD", 2);
        ReflectionTestUtils.setField(persoonHisVolledig2, "iD", 1);

        assertEquals(1, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    @Test
    public void testCompareGelijkMaarVerschillendIdEenNull() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(1);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(1);
        ReflectionTestUtils.setField(persoonHisVolledig1, "iD", null);
        ReflectionTestUtils.setField(persoonHisVolledig2, "iD", 1);

        assertEquals(-1, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    @Test
    public void testComparePersoon1Groter() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(2);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(1);

        assertEquals(1, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    @Test
    public void testComparePersoon1Kleiner() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(0);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(1);

        assertEquals(-1, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    @Test
    public void testComparePersoon1Null() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(null);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(1);

        assertEquals(-1, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    @Test
    public void testCompareBeidePersonenNull() {
        PersoonHisVolledig persoonHisVolledig1 = maakTestPersoonHisVolledig(null);
        PersoonHisVolledig persoonHisVolledig2 = maakTestPersoonHisVolledig(null);

        assertEquals(0, sorteervolgordeComparator.compare(persoonHisVolledig1, persoonHisVolledig2));
    }

    private PersoonHisVolledig maakTestPersoonHisVolledig(final Integer sorteerVolgorde) {
        ActieModel actieOud =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                        new DatumEvtDeelsOnbekendAttribuut(
                                DatumAttribuut.morgen()), null, DatumTijdAttribuut.over24Uur(), null);

        ActieModel actie =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                        new DatumEvtDeelsOnbekendAttribuut(
                                DatumAttribuut.gisteren()), null, tsreg, null);

        PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwAfgeleidAdministratiefRecord(actieOud).sorteervolgorde(new Byte("99")).eindeRecord();

        if (sorteerVolgorde != null) {
            builder.nieuwAfgeleidAdministratiefRecord(actie).sorteervolgorde(new Byte(sorteerVolgorde.toString()))
                    .eindeRecord();
        }

        return builder.build();
    }

}
