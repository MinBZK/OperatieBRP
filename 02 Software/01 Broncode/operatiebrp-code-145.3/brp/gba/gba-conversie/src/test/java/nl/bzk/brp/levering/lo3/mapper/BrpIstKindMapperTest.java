/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.HashSet;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import org.junit.Assert;
import org.junit.Test;

public class BrpIstKindMapperTest {

    private final BrpIstKindMapper mapper = new BrpIstKindMapper();

    @Test
    public void testSucces() {

        final List<BrpStapel<BrpIstRelatieGroepInhoud>> brpInhoud = mapper.map(BrpIstTestUtils.maakSimpeleStapelAlleCategorien());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> brpInhoud = mapper.map(new HashSet<Stapel>());
        Assert.assertEquals(true, brpInhoud.isEmpty());
    }

    /**
     * Verwacht geen NPE vanwege controle op null-waarden.
     */
    @Test
    public void testGeenWaarde() {
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> brpInhoud = mapper.map(null);
        Assert.assertEquals(true, brpInhoud.isEmpty());
    }
}
