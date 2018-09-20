/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import org.junit.Test;

public class HisAfnemerindicatieTabelJpaRepositoryIntegratieTest extends AbstractDBUnitIntegratieTest {

    @Inject
    private HisAfnemerindicatieBlobRepository repository;

    @Test
    public void leestAfnemerindicatiesVoorAlleenLezen() {
        final Integer persoonId = 2000001;

        final Set<?> indicaties = repository.leesGenormaliseerdModelVoorInMemoryBlob(persoonId);

        assertThat(indicaties.size(), is(2));
    }

    @Test
    public void leestAfnemerindicatiesVoorAlleenLezenNietBestaandPersoon() {
        final Integer persoonId = 123;

        final Set<?> indicaties = repository.leesGenormaliseerdModelVoorInMemoryBlob(persoonId);

        assertThat(indicaties.size(), is(0));
    }

    @Test
    public void leestAfnemerindicatiesVoorSchrijven() {
        final Integer persoonId = 2000001;

        final Set<?> indicaties = repository.leesGenormaliseerdModelVoorNieuweBlob(persoonId);

        assertThat(indicaties.size(), is(2));
    }

    @Test
    public void leestAfnemerindicatiesVoorSchrijvenNietBestaandPersoon() {
        final Integer persoonId = 123;

        final Set<?> indicaties = repository.leesGenormaliseerdModelVoorNieuweBlob(persoonId);

        assertThat(indicaties.size(), is(0));
    }
}
