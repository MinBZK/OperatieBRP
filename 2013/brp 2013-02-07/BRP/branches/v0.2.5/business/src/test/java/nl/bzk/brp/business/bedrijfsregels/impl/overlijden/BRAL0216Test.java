/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.List;

import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;


/**
 * Unit test voor de {@link BRYBY0216} bedrijfsregel.
 *
 */
public class BRAL0216Test {

    private BRAL0216 bral0216;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bral0216 = new BRAL0216();
    }

    @Test
    public void testPersoonIngeschrevenen() {
        PersoonModel persoon = maakBestaandePersoon(SoortPersoon.INGESCHREVENE, Verantwoordelijke.COLLEGE);
        List<Melding> meldingen = bral0216.executeer(persoon, null, null);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testPersoonNietIngeschrevenen() {
        PersoonModel persoon = maakBestaandePersoon(SoortPersoon.INGESCHREVENE, Verantwoordelijke.MINISTER);
        List<Melding> meldingen = bral0216.executeer(persoon, null, null);

        Assert.assertEquals(1, meldingen.size());


        persoon = maakBestaandePersoon(SoortPersoon.NIET_INGESCHREVENE, Verantwoordelijke.COLLEGE);
        meldingen = bral0216.executeer(persoon, null, null);

        Assert.assertEquals(1, meldingen.size());


        persoon = maakBestaandePersoon(SoortPersoon.NIET_INGESCHREVENE, Verantwoordelijke.MINISTER);
        meldingen = bral0216.executeer(persoon, null, null);

        Assert.assertEquals(1, meldingen.size());
    }


    private PersoonModel maakBestaandePersoon(final SoortPersoon soortPersoon, final Verantwoordelijke verantwoordelijke) {
        PersoonBericht persoonBericht =
            PersoonBuilder.bouwPersoon("1234567890", Geslachtsaanduiding.MAN, 19660606, null, "vn", "vg", "gsln");
        persoonBericht.setSoort(soortPersoon);
        persoonBericht.getBijhoudingsverantwoordelijkheid().setVerantwoordelijke(verantwoordelijke);

        PersoonModel persoon = new PersoonModel(persoonBericht);

        return persoon;
    }
}
