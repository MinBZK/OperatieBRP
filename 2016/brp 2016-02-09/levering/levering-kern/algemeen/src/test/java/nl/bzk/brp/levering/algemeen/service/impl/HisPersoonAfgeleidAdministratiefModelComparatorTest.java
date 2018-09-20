/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HisPersoonAfgeleidAdministratiefModelComparatorTest {

    private HisPersoonAfgeleidAdministratiefModelComparator comparator = new HisPersoonAfgeleidAdministratiefModelComparator();

    @Test
    public void testCompareGelijkeIds() {
        final HisPersoonAfgeleidAdministratiefModel afgAdm1 =
            maakHisPersoonAfgeleidAdministratiefModel(123, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final HisPersoonAfgeleidAdministratiefModel afgAdm2 =
            maakHisPersoonAfgeleidAdministratiefModel(123, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(212, 1, 1).getWaarde()));
        final int resultaat = comparator.compare(afgAdm1, afgAdm2);

        Assert.assertEquals(0, resultaat);
    }

    @Test
    public void testCompareEerder() {
        final HisPersoonAfgeleidAdministratiefModel afgAdm1 =
            maakHisPersoonAfgeleidAdministratiefModel(123, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final HisPersoonAfgeleidAdministratiefModel afgAdm2 =
            maakHisPersoonAfgeleidAdministratiefModel(345, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(212, 1, 1).getWaarde()));
        final int resultaat = comparator.compare(afgAdm1, afgAdm2);

        Assert.assertEquals(-1, resultaat);
    }

    @Test
    public void testCompareLater() {
        final HisPersoonAfgeleidAdministratiefModel afgAdm1 =
            maakHisPersoonAfgeleidAdministratiefModel(123, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final HisPersoonAfgeleidAdministratiefModel afgAdm2 =
            maakHisPersoonAfgeleidAdministratiefModel(345, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(210, 1, 1).getWaarde()));
        final int resultaat = comparator.compare(afgAdm1, afgAdm2);

        Assert.assertEquals(1, resultaat);
    }

    @Test
    public void testCompareGelijkLagereId() {
        final HisPersoonAfgeleidAdministratiefModel afgAdm1 =
            maakHisPersoonAfgeleidAdministratiefModel(123, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final HisPersoonAfgeleidAdministratiefModel afgAdm2 =
            maakHisPersoonAfgeleidAdministratiefModel(345, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final int resultaat = comparator.compare(afgAdm1, afgAdm2);

        Assert.assertEquals(-1, resultaat);
    }

    @Test
    public void testCompareGelijkHogereId() {
        final HisPersoonAfgeleidAdministratiefModel afgAdm1 =
            maakHisPersoonAfgeleidAdministratiefModel(345, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final HisPersoonAfgeleidAdministratiefModel afgAdm2 =
            maakHisPersoonAfgeleidAdministratiefModel(123, new DatumTijdAttribuut(DatumTijdAttribuut.bouwDatumTijd(211, 1, 1).getWaarde()));
        final int resultaat = comparator.compare(afgAdm1, afgAdm2);

        Assert.assertEquals(1, resultaat);
    }

    /**
     * Maakt een his persoon afgeleid administratief model.
     *
     * @param id de identifier van de his persoon afgeleid administratief model
     * @param tijdstipLaatsteWijziging het datum tijd attribuut dat tijdstipLaatsteWijziging voorstelt
     */
    private HisPersoonAfgeleidAdministratiefModel maakHisPersoonAfgeleidAdministratiefModel(final int id,
                                                                                            final DatumTijdAttribuut tijdstipLaatsteWijziging)
    {
        final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel = Mockito.mock(HisPersoonAfgeleidAdministratiefModel.class);
        Mockito.when(hisPersoonAfgeleidAdministratiefModel.getID()).thenReturn(id);
        Mockito.when(hisPersoonAfgeleidAdministratiefModel.getTijdstipLaatsteWijziging()).thenReturn(tijdstipLaatsteWijziging);

        return hisPersoonAfgeleidAdministratiefModel;
    }

}
