/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.HashSet;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import org.junit.Assert;
import org.junit.Test;

public class BrpIstGezagsverhoudingMapperTest {

    private final BrpIstGezagsverhoudingMapper mapper = new BrpIstGezagsverhoudingMapper();

    @Test
    public void testSucces() {

        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpInhoud = mapper.map(BrpIstTestUtils.maakSimpeleStapelAlleCategorien());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpInhoud = mapper.map(new HashSet<Stapel>());

        Assert.assertNull(brpInhoud);
    }

    /**
     * Verwacht geen NPE vanwege controle op null-waarden.
     */
    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpInhoud = mapper.map(null);
        Assert.assertNull(brpInhoud);
    }

}
