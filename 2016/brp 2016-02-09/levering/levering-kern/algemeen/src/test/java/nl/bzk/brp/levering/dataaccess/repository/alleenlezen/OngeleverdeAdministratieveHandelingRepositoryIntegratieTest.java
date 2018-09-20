/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.alleenlezen;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class OngeleverdeAdministratieveHandelingRepositoryIntegratieTest extends
    AbstractIntegratieTest
{

    @Autowired
    private OngeleverdeAdministratieveHandelingRepository ongeleverdeAdministratieveHandelingRepository;

    @Test
    public final void testHaalOnverwerkteAdministratieveHandelingenOp() {
        // when
        final List<BigInteger> onverwerkteAdministratieveHandelingen =
            ongeleverdeAdministratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp(null, null);

        // then
        assertEquals(5, onverwerkteAdministratieveHandelingen.size());
    }

    @Test
    public final void testSorteringOnverwerkteAdministratieveHandelingen() {
        // when
        final List<BigInteger> onverwerkteAdministratieveHandelingen =
            ongeleverdeAdministratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp(null, null);

        // then
        assertEquals(1L, onverwerkteAdministratieveHandelingen.get(0).longValue());
    }

    @Test
    public final void testHaalOnverwerkteAdministratieveHandelingenOpMetLegeLijstOverslaanAdmHnd() {
        // when
        final List<BigInteger> onverwerkteAdministratieveHandelingen =
            ongeleverdeAdministratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp(
                new ArrayList<SoortAdministratieveHandeling>(), null);

        // then
        assertEquals(5, onverwerkteAdministratieveHandelingen.size());
    }

    @Test
    public final void testHaalOnverwerkteAdministratieveHandelingenOpMetUitzondering() {
        final List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen =
            new ArrayList<>();
        overslaanSoortAdministratieveHandelingen.add(SoortAdministratieveHandeling.ERKENNING_VOOR_GEBOORTE_VERVALLEN);

        // when
        final List<BigInteger> onverwerkteAdministratieveHandelingen =
            ongeleverdeAdministratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp(
                overslaanSoortAdministratieveHandelingen, null);

        // then
        assertEquals(5, onverwerkteAdministratieveHandelingen.size());
    }

    @Test
    public final void testHaaltAantalOnverwerkteAdministratieveHandelingenOpMetUitzondering() {
        final List<SoortAdministratieveHandeling> overslaanSoortAdministratieveHandelingen =
            new ArrayList<>();
        overslaanSoortAdministratieveHandelingen.add(SoortAdministratieveHandeling.ERKENNING_VOOR_GEBOORTE_VERVALLEN);

        // when
        final List<BigInteger> onverwerkteAdministratieveHandelingen =
            ongeleverdeAdministratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp(
                overslaanSoortAdministratieveHandelingen, 2);

        // then
        assertEquals(2, onverwerkteAdministratieveHandelingen.size());
    }

}
