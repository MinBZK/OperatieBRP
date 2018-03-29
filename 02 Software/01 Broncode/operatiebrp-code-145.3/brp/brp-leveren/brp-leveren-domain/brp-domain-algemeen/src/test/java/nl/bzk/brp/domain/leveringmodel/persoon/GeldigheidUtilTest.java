/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.time.LocalDate;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * GeldigheidUtilTest.
 */
public class GeldigheidUtilTest {

    @Test
    public void testMaterieelDatumaanvangLeegEindeLeeg_GEEN() {
        final boolean geldig = GeldigheidUtil.materieelGeldig(null, null, HistorieVorm.GEEN, 20151010);
        Assert.assertTrue(geldig);
    }

    @Test
    public void testMaterieelDatumaanvangLeegPeilNaEindeGeldig_GEEN() {
        final boolean geldig = GeldigheidUtil.materieelGeldig(20130000, 20140101, HistorieVorm.GEEN, 20151010);
        Assert.assertFalse(geldig);
    }

    @Test
    public void testMaterieelPeilNaEindeGeldig_MATERIEEL() {
        final boolean geldig = GeldigheidUtil.materieelGeldig(20130000, 20140101, HistorieVorm.MATERIEEL, 20151010);
        Assert.assertTrue(geldig);
    }

    @Test
    public void testMaterieelDatumaanvangGelijkDatumSoepelGeenDatumEinde_GEEN() {
        final boolean geldig = GeldigheidUtil.materieelGeldig(20150000, null, HistorieVorm.GEEN, 20151010);
        Assert.assertTrue(geldig);
    }

    @Test
    public void testMaterieelDatumaanvangGelijkDatumSoepelDatumEindeNietNaDatumSoepel_GEEN() {
        //beide waar dus geen historisch record, geldig ook zmet historievorm geen
        final boolean geldig = GeldigheidUtil.materieelGeldig(20150000, 20150000, HistorieVorm.GEEN, 20151010);
        Assert.assertTrue(geldig);
    }

    @Test
    public void testFormeelTijdstipRegistratieNaPeilmomentGeenVerval_GEEN() {
        final boolean geldig = GeldigheidUtil
                .formeelGeldig(LocalDate.of(2015, 2, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID), null, HistorieVorm.GEEN,
                        LocalDate.of(2015, 1, 1).atStartOfDay(DatumUtil.BRP_ZONE_ID));
        Assert.assertFalse(geldig);
    }

    @Test
    public void testFormeelTijdstipRegistratieVoorPeilmomentGeenVerval_GEEN() {
        final boolean geldig = GeldigheidUtil
                .formeelGeldig(LocalDate.of(2015, 2, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID), null, HistorieVorm.GEEN,
                        LocalDate.of(2015, 4, 1).atStartOfDay(DatumUtil.BRP_ZONE_ID));
        Assert.assertTrue(geldig);
    }

    @Test
    public void testFormeelTijdstipRegistratieGelijkPeilmomentGeenVerval_GEEN() {
        final boolean geldig = GeldigheidUtil
                .formeelGeldig(LocalDate.of(2015, 2, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID), null, HistorieVorm.GEEN,
                        LocalDate.of(2015, 2, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID));
        Assert.assertFalse(geldig);
    }

    @Test
    public void testFormeelTijdstipVervalVoorPeilmoment_GEEN() {
        final boolean geldig = GeldigheidUtil
                .formeelGeldig(LocalDate.of(2015, 2, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID),
                        LocalDate.of(2015, 3, 3).atStartOfDay(DatumUtil.BRP_ZONE_ID), HistorieVorm.GEEN,
                        LocalDate.of(2015, 4, 4).atStartOfDay(DatumUtil.BRP_ZONE_ID));
        Assert.assertFalse(geldig);
    }

    @Test
    public void testFormeelTijdstipVervalVoorPeilmoment_FORMEEL() {
        final boolean geldig = GeldigheidUtil
                .formeelGeldig(LocalDate.of(2015, 2, 2).atStartOfDay(DatumUtil.BRP_ZONE_ID),
                        LocalDate.of(2015, 3, 3).atStartOfDay(DatumUtil.BRP_ZONE_ID), HistorieVorm.MATERIEEL_FORMEEL,
                        LocalDate.of(2015, 4, 4).atStartOfDay(DatumUtil.BRP_ZONE_ID));
        Assert.assertTrue(geldig);
    }
}
