/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import org.junit.Test;

public class OnderzoekMapperUtilTest {

    @Test
    public void testBepaalDbobject() throws Exception {
        assertEquals(Element.PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID, OnderzoekMapperUtil.bepaalDbobject(
            new PersoonGeslachtsaanduidingHistorie(new Persoon(SoortPersoon.INGESCHREVENE), Geslachtsaanduiding.MAN),
            OnderzoekMapperUtil.Historieveldnaam.AANVANG));
        assertEquals(Element.PERSOON_GESLACHTSNAAMCOMPONENT_DATUMEINDEGELDIGHEID, OnderzoekMapperUtil.bepaalDbobject(
            new PersoonGeslachtsnaamcomponentHistorie(new PersoonGeslachtsnaamcomponent(new Persoon(SoortPersoon.INGESCHREVENE), 1), "Stam"),
            OnderzoekMapperUtil.Historieveldnaam.EINDE));
    }
}
