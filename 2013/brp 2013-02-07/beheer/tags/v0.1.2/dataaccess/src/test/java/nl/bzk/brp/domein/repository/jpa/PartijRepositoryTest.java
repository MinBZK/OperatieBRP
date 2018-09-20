/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.repository.jpa;

import java.util.ArrayList;

import javax.inject.Inject;

import nl.bzk.brp.domein.autaut.Doelbinding;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.AbonnementSoortBericht;
import nl.bzk.brp.domein.repository.PartijRepository;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link PersoonRepositoryJPA} class.
 */
public class PartijRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PartijRepository partijRepository;

    /**
     * Test of findByPartij method, of class DoelbindingRepository.
     */
    @Test
    public void testFindOne() {
        Partij partij = partijRepository.findOne(1L);
        Assert.assertEquals("Almere", partij.getNaam());
        Assert.assertEquals(1, partij.getDoelbindingen().size());
        Doelbinding doelBinding = partij.getDoelbindingen().get(0);
        Assert.assertEquals(1, doelBinding.getAbonnementen().size());
        Abonnement abonnement = doelBinding.getAbonnementen().get(0);
        Assert.assertEquals("Levering", abonnement.getSoortAbonnement().getNaam());
        AbonnementSoortBericht soortBericht =
            new ArrayList<AbonnementSoortBericht>(abonnement.getAbonnementSoortBerichten()).get(0);
        Assert.assertSame(SoortBericht.OPVRAGENPERSOONVRAAG, soortBericht.getSoortBericht());
    }
}
