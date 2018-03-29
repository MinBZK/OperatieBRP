/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.brp.gba.dataaccess.stam.GemeenteRepository;
import nl.bzk.brp.gba.dataaccess.stam.LandOfGebiedRepository;
import nl.bzk.brp.gba.dataaccess.stam.NationaliteitRepository;
import nl.bzk.brp.gba.dataaccess.stam.PlaatsRepository;
import nl.bzk.brp.gba.dataaccess.stam.RedenVerkrijgingNLNationaliteitRepository;
import nl.bzk.brp.gba.dataaccess.stam.RedenVerliesNLNationaliteitRepository;
import nl.bzk.brp.gba.dataaccess.stam.VerblijfsrechtRepository;
import org.junit.Assert;
import org.junit.Test;

/**
 * Stam tabel repository test.
 */
public class StamTabelRepositoryTest extends AbstractIntegratieTest {

    @Inject
    private GemeenteRepository gemeenteRepository;
    @Inject
    private LandOfGebiedRepository landOfGebiedRepository;
    @Inject
    private NationaliteitRepository nationaliteitRepository;
    @Inject
    private PlaatsRepository plaatsRepository;
    @Inject
    private RedenVerkrijgingNLNationaliteitRepository redenVerkrijgingNLNationaliteitRepository;
    @Inject
    private RedenVerliesNLNationaliteitRepository redenVerliesNLNationaliteitRepository;
    @Inject
    private VerblijfsrechtRepository verblijfsrechtRepository;

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllGemeentes() {
        final Collection<Gemeente> result = gemeenteRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }


    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllLand() {
        final Collection<LandOfGebied> result = landOfGebiedRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllNationaliteit() {
        final Collection<Nationaliteit> result = nationaliteitRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllVerblijfstitel() {
        final Collection<Verblijfsrecht> result = verblijfsrechtRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllVerkrijgingNLNationaliteit() {
        final Collection<RedenVerkrijgingNLNationaliteit> result = redenVerkrijgingNLNationaliteitRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllVerliesNLNationaliteit() {
        final Collection<RedenVerliesNLNationaliteit> result = redenVerliesNLNationaliteitRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }

    @DBUnit.InsertBefore({"/data/kern.xml", "/data/autaut.xml", "/data/ist.xml"})
    @Test
    public final void findAllPlaats() {
        final Collection<Plaats> result = plaatsRepository.findAll();
        Assert.assertFalse(result.isEmpty());
    }
}
