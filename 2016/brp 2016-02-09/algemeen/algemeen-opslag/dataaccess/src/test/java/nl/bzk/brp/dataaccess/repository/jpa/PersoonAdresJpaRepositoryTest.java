/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test klasse voor de niet interface gedeeltes van de persoon adres jpa repo.
 */
public class PersoonAdresJpaRepositoryTest {

    private static final String DUMMY = "Dummy";
    private PersoonAdresJpaRepository repository;

    @Before
    public void init() {
        repository = new PersoonAdresJpaRepository();
    }

    @Test
    public void testIsNotBlankAttribuut() {
        Assert.assertFalse(repository.isNotBlankAttribuut(null));
        Assert.assertFalse(repository.isNotBlankAttribuut(new NaamEnumeratiewaardeAttribuut(null)));
        Assert.assertFalse(repository.isNotBlankAttribuut(new NaamEnumeratiewaardeAttribuut("")));
        Assert.assertTrue(repository.isNotBlankAttribuut(new NaamEnumeratiewaardeAttribuut(DUMMY)));
    }

    @Test
    public void testIsNotNullAttribuut() {
        Assert.assertFalse(repository.isNotNullAttribuut(null));
        Assert.assertFalse(repository.isNotNullAttribuut(new NaamEnumeratiewaardeAttribuut(null)));
        Assert.assertTrue(repository.isNotNullAttribuut(new NaamEnumeratiewaardeAttribuut("")));
        Assert.assertTrue(repository.isNotNullAttribuut(new NaamEnumeratiewaardeAttribuut(DUMMY)));
    }

}
