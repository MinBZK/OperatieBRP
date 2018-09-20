/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import org.junit.Test;


public class HisAfnemerindicatieTabelRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private HisAfnemerindicatieTabelRepository hisAfnemerindicatieTabelRepository;

    @Test
    public void maakNieuweAfnemerIndicatie() {
        final int persoonId = 1;

        final PartijAttribuut testPartij = new PartijAttribuut(TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(34).maak());
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();

        hisAfnemerindicatieTabelRepository.maakNieuweAfnemerIndicatie(persoonId, testPartij, new LeveringsautorisatieAttribuut(la));

    }
}
