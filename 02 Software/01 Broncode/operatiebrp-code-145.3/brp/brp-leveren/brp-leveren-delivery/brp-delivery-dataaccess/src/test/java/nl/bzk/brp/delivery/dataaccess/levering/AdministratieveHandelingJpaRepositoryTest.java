/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.dalapi.TeLeverenHandelingDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * AdministratieveHandelingJpaRepositoryTest.
 */
@Data(resources = {
        "classpath:/data/dataset-OngeleverdeAdministratieveHandelingRepositoryIntegratieTest.xml"})
public class AdministratieveHandelingJpaRepositoryTest extends AbstractDataAccessTest {

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingJpaRepository;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(administratieveHandelingJpaRepository, "maxHandelingenVoorPublicatie", 100);
    }

    @Test
    public void testHaalAdministratieveHandeling() {
        final List<BRPActie> acties = administratieveHandelingJpaRepository.haalAdministratieveHandelingOp(1L);
        Assert.assertEquals(1, acties.size());
    }

    @Test
    public void testZetHandelingenInLevering() {
        final Set<Long> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            ids.add((long) i);
        }
        //3,4,5 zitten in de lijst
        int updateCount = administratieveHandelingJpaRepository.zetHandelingenStatusInLevering(ids);
        Assert.assertEquals(3, updateCount);
    }

    @Test
    public void testZetHandelingenInLeveringGeenHandelingen() {
        final Set<Long> ids = new HashSet<>();
        int updateCount = administratieveHandelingJpaRepository.zetHandelingenStatusInLevering(ids);
        Assert.assertEquals(0, updateCount);
    }

    @Test
    public void geefHandelingenVoorAdmhndPublicatie() {
        final List<TeLeverenHandelingDTO> teLeverenHandelingen = administratieveHandelingJpaRepository.geefHandelingenVoorAdmhndPublicatie();
        //3 te leveren en 1 in levering
        Assert.assertEquals(4, teLeverenHandelingen.size());
        //geordend, laagste tsreg eerst
        Assert.assertEquals(3, teLeverenHandelingen.get(0).getAdmhndId().intValue());
        Assert.assertEquals(4, teLeverenHandelingen.get(1).getAdmhndId().intValue());
        Assert.assertEquals(5, teLeverenHandelingen.get(2).getAdmhndId().intValue());
        //in levering. Laagste id maar laatst
        Assert.assertEquals(1, teLeverenHandelingen.get(3).getAdmhndId().intValue());

        ReflectionTestUtils.setField(administratieveHandelingJpaRepository, "maxHandelingenVoorPublicatie", 2);

        final List<TeLeverenHandelingDTO> teLeverenHandelingenOnderLimiet = administratieveHandelingJpaRepository.geefHandelingenVoorAdmhndPublicatie();
        Assert.assertEquals(3, teLeverenHandelingenOnderLimiet.size());
        //geordend, laagste tsreg eerst
        Assert.assertEquals(3, teLeverenHandelingenOnderLimiet.get(0).getAdmhndId().intValue());
        Assert.assertEquals(4, teLeverenHandelingenOnderLimiet.get(1).getAdmhndId().intValue());
        //in levering. Laagste id maar laatst
        Assert.assertEquals(1, teLeverenHandelingenOnderLimiet.get(2).getAdmhndId().intValue());
    }

    @Test
    public void testMarkeerAdministratieveHandelingAlsVerwerktDieAlVerwerktIs() throws SQLException {
        final int updateCount = administratieveHandelingJpaRepository.markeerAdministratieveHandelingAlsVerwerkt(2L);
        Assert.assertEquals(0, updateCount);
    }

    @Test
    public void testMarkeerAdministratieveHandelingAlsVerwerkt() throws SQLException {
        //geeft alle handelingen met status lev null en 1
        final List<TeLeverenHandelingDTO> teLeverenHandelingen = administratieveHandelingJpaRepository.geefHandelingenVoorAdmhndPublicatie();
        Assert.assertEquals(4, teLeverenHandelingen.size());
        administratieveHandelingJpaRepository.markeerAdministratieveHandelingAlsVerwerkt(1L);
        final List<TeLeverenHandelingDTO> teLeverenHandelingenNieuw = administratieveHandelingJpaRepository.geefHandelingenVoorAdmhndPublicatie();
        Assert.assertEquals(3, teLeverenHandelingenNieuw.size());
    }

    @Test
    public void testMarkeerAdministratieveHandelingAlsFout() {
        //geeft alle handelingen met status lev null en 1
        final List<TeLeverenHandelingDTO> teLeverenHandelingen = administratieveHandelingJpaRepository.geefHandelingenVoorAdmhndPublicatie();
        Assert.assertEquals(4, teLeverenHandelingen.size());
        //nieuwer dan onverwerkte handelingen
        administratieveHandelingJpaRepository.markeerAdministratieveHandelingAlsFout(1L);
        final List<TeLeverenHandelingDTO> teLeverenHandelingenNieuw = administratieveHandelingJpaRepository.geefHandelingenVoorAdmhndPublicatie();
        Assert.assertEquals(3, teLeverenHandelingenNieuw.size());
    }
}
