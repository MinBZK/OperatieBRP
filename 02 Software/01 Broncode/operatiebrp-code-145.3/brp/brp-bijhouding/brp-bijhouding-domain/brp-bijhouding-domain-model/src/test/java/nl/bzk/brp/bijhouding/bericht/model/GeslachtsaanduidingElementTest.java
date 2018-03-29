/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;
import org.mockito.Mockito;

public class GeslachtsaanduidingElementTest extends AbstractElementTest {

    @Test
    public void geslachtsaanduidingOngeldig() {
        final GeslachtsaanduidingElement geslachtsaanduiding =
                new GeslachtsaanduidingElement(new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").build(), new StringElement("Z"));
        assertEquals(1, geslachtsaanduiding.valideerInhoud().size());
        assertEquals(Regel.R1569, geslachtsaanduiding.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void geslachtsaanduidingGeldig() {
        final GeslachtsaanduidingElement geslachtsaanduiding = new GeslachtsaanduidingElement(new HashMap<>(), new StringElement("M"));
        assertEquals(0, geslachtsaanduiding.valideerInhoud().size());
    }

    @Test
    public void testMaakPersoonGeslachtsaanduidingHistorieEntiteit() {
        final GeslachtsaanduidingElement element = new GeslachtsaanduidingElement(new HashMap<>(), new StringElement("M"));
        assertEquals(0, element.valideerInhoud().size());
        final int aanvangGeldigheid = 20010101;
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.voegPersoonGeslachtsaanduidingHistorieToe(element, getActie(), aanvangGeldigheid);
        final PersoonGeslachtsaanduidingHistorie entiteit =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonGeslachtsaanduidingHistorieSet());
        assertNull(entiteit.getId());
        assertEquals(Geslachtsaanduiding.MAN, entiteit.getGeslachtsaanduiding());
        assertEquals(1, persoon.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals(entiteit, persoon.getPersoonGeslachtsaanduidingHistorieSet().iterator().next());
        assertEntiteitMetMaterieleHistorie(entiteit, aanvangGeldigheid);
    }

    @Test
    public void testGetInstance() {
        //setup
        final PersoonGeslachtsaanduidingHistorie
                geslachtsaanduidingHistorie =
                new PersoonGeslachtsaanduidingHistorie(new Persoon(SoortPersoon.INGESCHREVENE), Geslachtsaanduiding.MAN);
        final BijhoudingVerzoekBericht verzoekBericht = Mockito.mock(BijhoudingVerzoekBericht.class);
        //execute
        final GeslachtsaanduidingElement geslachtsaanduidingElement = GeslachtsaanduidingElement.getInstance(geslachtsaanduidingHistorie, verzoekBericht);
        //verify
        assertEquals(geslachtsaanduidingHistorie.getGeslachtsaanduiding().getCode(), geslachtsaanduidingElement.getCode().getWaarde());
        //verify null voorkomen
        assertNull(GeslachtsaanduidingElement.getInstance(null, verzoekBericht));
    }

}
