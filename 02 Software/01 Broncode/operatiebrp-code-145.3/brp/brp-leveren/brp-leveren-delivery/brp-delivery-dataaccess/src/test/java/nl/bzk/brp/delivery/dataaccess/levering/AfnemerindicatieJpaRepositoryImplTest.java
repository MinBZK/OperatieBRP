/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * AfnemerindicatieJpaRepositoryImplTest.
 */
@Data(resources = {
        "classpath:/data/aut-lev.xml", "/data/dataset_zoekpersoon.xml"})
public class AfnemerindicatieJpaRepositoryImplTest extends AbstractDataAccessTest {

    private static final ZonedDateTime TSREG_NU = DatumUtil.nuAlsZonedDateTime();
    private static final int DATUM_AANVANG = 20140101;
    private static final int DATUM_EINDE = 20150101;

    @Inject
    private AfnemerindicatieRepository afnemerindicatieRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testHaalAfnemerindicatiesOp() {
        final List<PersoonAfnemerindicatie> afnemerindicaties = afnemerindicatieRepository.haalAfnemerindicatiesOp(1L);
        Assert.assertNotNull(afnemerindicaties);
        Assert.assertEquals(1, afnemerindicaties.size());
    }

    @Test
    public void testVerwijderAfnemerIndicatie() {
        final List<PersoonAfnemerindicatie> afnemerindicatiesInit = afnemerindicatieRepository.haalAfnemerindicatiesOp(1L);

        afnemerindicatieRepository.verwijderAfnemerindicatie(1L, (short) 1, 1, 1);

        final List<PersoonAfnemerindicatie> afnemerindicaties = afnemerindicatieRepository.haalAfnemerindicatiesOp(1L);
        Assert.assertNotNull(afnemerindicaties);
        Assert.assertEquals(1, afnemerindicaties.size());
        boolean actueel = false;
        for (final PersoonAfnemerindicatieHistorie his : afnemerindicaties.get(0).getPersoonAfnemerindicatieHistorieSet()) {
            if (his.isActueel()) {
                actueel = true;
            }
        }
        Assert.assertFalse(actueel);
    }

    @Test
    public void testVerwijderVervallenAfnemerIndicatie() {
        expectedException.expect(OptimisticLockException.class);

        afnemerindicatieRepository.verwijderAfnemerindicatie(2L, (short) 1, 1, 1);
    }

    @Test
    public void testPlaatsAfnemerIndicatie() {
        afnemerindicatieRepository.plaatsAfnemerindicatie(1, (short) 2, 1, 1, DATUM_EINDE, DATUM_AANVANG, TSREG_NU);
        final List<PersoonAfnemerindicatie> afnemerindicaties = afnemerindicatieRepository.haalAfnemerindicatiesOp(1L);
        Assert.assertNotNull(afnemerindicaties);
        Assert.assertEquals(2, afnemerindicaties.size());

        final PersoonAfnemerindicatie persAfnIndicatie = Iterables.filter(afnemerindicaties, afnemerindicatie ->
                afnemerindicatie.getPartij().getId().equals((short) 2)).iterator().next();
        Assert.assertNotNull(persAfnIndicatie);
        Assert.assertEquals(persAfnIndicatie.getDatumAanvangMaterielePeriode().intValue(), DATUM_AANVANG);
        Assert.assertEquals(persAfnIndicatie.getDatumEindeVolgen().intValue(), DATUM_EINDE);
        Assert.assertEquals(persAfnIndicatie.getPersoonAfnemerindicatieHistorieSet()
                .iterator().next().getDatumTijdRegistratie().getTime(), DatumUtil.vanDateTimeNaarLong(TSREG_NU).longValue());
    }

    @Test
    public void testPlaatsVerwijderPlaatsAfnemerIndicatie() {
        final long persoonId = 4;
        afnemerindicatieRepository.plaatsAfnemerindicatie(persoonId, (short) 2, 1, 1, null, null, DatumUtil.nuAlsZonedDateTime());
        //valideer
        final List<PersoonAfnemerindicatie> afnemerindicatiesValideer = afnemerindicatieRepository.haalAfnemerindicatiesOp(persoonId);
        Assert.assertEquals(1, afnemerindicatiesValideer.size());
        Assert.assertTrue(afnemerindicatiesValideer.get(0).isActueelEnGeldig());
        //verwijder
        afnemerindicatieRepository.verwijderAfnemerindicatie(persoonId, (short) 2, 1, 1);
        //valideer
        final List<PersoonAfnemerindicatie> afnemerindicatiesValideerNaVerwijder = afnemerindicatieRepository.haalAfnemerindicatiesOp(persoonId);
        Assert.assertEquals(1, afnemerindicatiesValideerNaVerwijder.size());
        Assert.assertFalse(afnemerindicatiesValideer.get(0).isActueelEnGeldig());
        //plaats
        afnemerindicatieRepository.plaatsAfnemerindicatie(persoonId, (short) 2, 1, 1, null, null, DatumUtil.nuAlsZonedDateTime());
        final List<PersoonAfnemerindicatie> afnemerindicaties = afnemerindicatieRepository.haalAfnemerindicatiesOp(persoonId);
        Assert.assertNotNull(afnemerindicaties);
        Assert.assertEquals(1, afnemerindicaties.size());
        Assert.assertTrue(afnemerindicaties.get(0).isActueelEnGeldig());
    }
}
