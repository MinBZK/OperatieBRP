/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test klasse voor de niet interface gedeeltes van de persoon jpa repo.
 */
public class PersoonJpaRepositoryTest {

    private PersoonJpaRepository repository;

    @Before
    public void init() {
        repository = new PersoonJpaRepository();
    }

    @Test
    public void testCreerZelfdeTypeUppercasedNullCases() {
        Assert.assertNull(repository.creerZelfdeTypeUppercased(null));
        Assert.assertNull(repository.creerZelfdeTypeUppercased(new NaamEnumeratiewaardeAttribuut(null)));
        Assert.assertNull(repository.creerZelfdeTypeUppercased(new Attribuut<String>() {
            @Override
            public void setMagGeleverdWorden(final boolean magGeleverdWorden) {
            }
            @Override
            public boolean isMagGeleverdWorden() {
                return false;
            }

            @Override
            public void setGroep(final Groep groep) {
            }

            @Override
            public Groep getGroep() {
                return null;
            }

            @Override
            public String getWaarde() {
                return "";
            }
            @Override
            public boolean heeftWaarde() {
                return true;
            }

            @Override
            public boolean isInOnderzoek() {
                return false;
            }

            @Override
            public void setInOnderzoek(boolean inOnderzoek) {
            }
        }));
    }

    @Test
    public void testCreerZelfdeHuisnummer() {
        Assert.assertNull(repository.creerZelfdeHuisnummer(null));
        Assert.assertNull(repository.creerZelfdeHuisnummer(new HuisnummerAttribuut(null)));
        Assert.assertEquals(1234, repository.creerZelfdeHuisnummer(new HuisnummerAttribuut(1234)).getWaarde().intValue());
    }

}
