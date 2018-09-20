/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.aut.DoelBinding;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.AbonnementSoortBericht;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link PartijRepository} class.
 */
public class PartijRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PartijRepository partijRepository;

    /**
     * Test of findByPartij method, of class DoelBindingRepository.
     */
    @Test
    public void testFindOne() {
        Partij partij = partijRepository.findOne(3L);
        Assert.assertEquals(1, partij.getDoelBindingen().size());
        DoelBinding doelBinding = new ArrayList<DoelBinding>(partij.getDoelBindingen()).get(0);
        Assert.assertEquals(1, doelBinding.getAbonnementen().size());
        Abonnement abonnement = new ArrayList<Abonnement>(doelBinding.getAbonnementen()).get(0);
        Assert.assertEquals("Levering", abonnement.getSoort().getNaam());
        AbonnementSoortBericht soortBericht = new ArrayList<AbonnementSoortBericht>(abonnement.getSoortBerichten()).get(0);
        Assert.assertSame(SoortBericht.OPVRAGEN_PERSOON_VRAAG, soortBericht.getSoortBericht());
    }

    @Test
    public void testFindMinister() {
        final Partij partij = partijRepository.findOne(PartijRepository.ID_PARTIJ_MINISTER);
        assertEquals(SoortPartij.VERTEGENWOORDIGER_REGERING, partij.getSoort());
    }

    @Test
    public void testFindRegeringEnStatenGeneraal() {
        final Partij partij = partijRepository.findOne(PartijRepository.ID_PARTIJ_REGERING_EN_STATEN_GENERAAL);
        assertEquals(SoortPartij.WETGEVER, partij.getSoort());
    }


}
