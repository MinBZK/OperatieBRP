/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Stam tabel repository test.
 */
public class StamTabelRepositoryTest extends AbstractIntegratieTest {

    @Inject
    private StamTabelRepository stamTabelRepository;

    @Test
    public final void findAllGemeentes() {
        final Collection<Gemeente> result = stamTabelRepository.findAllGemeente();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllGemeenteCodes() {
        final Collection<Short> result = stamTabelRepository.findAllGemeenteCodes();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllLandCodes() {
        final Collection<Short> result = stamTabelRepository.findAllLandCodes();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllNationaliteitCodes() {
        final Collection<Short> result = stamTabelRepository.findAllNationaliteitCodes();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllVerblijfstitel() {
        final Collection<AanduidingVerblijfsrecht> result = stamTabelRepository.findAllAanduidingVerblijfsrecht();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllVerkrijgingNLNationaliteit() {
        final Collection<RedenVerkrijgingNLNationaliteit> result = stamTabelRepository.findAllVerkrijgingNLNationaliteit();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllVerliesNLNationaliteit() {
        final Collection<RedenVerliesNLNationaliteit> result = stamTabelRepository.findAllVerliesNLNationaliteit();
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public final void findAllPlaats() {
        final Collection<Plaats> result = stamTabelRepository.findAllPlaats();
        Assert.assertFalse(result.isEmpty());
    }
}
