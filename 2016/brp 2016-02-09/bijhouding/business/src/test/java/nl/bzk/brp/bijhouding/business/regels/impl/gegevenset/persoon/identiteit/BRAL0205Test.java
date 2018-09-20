/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNummerverwijzingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class BRAL0205Test {

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL0205, new BRAL0205().getRegel());
    }

    @Test
    public void testIngeschreveneDieDeGroepenNietHeeft() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenDieDeGroepenHeeftEnGeenAndereGroepen() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testNietIngeschreveneMetIdentificatieNummersGroep() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenDieDeGroepenHeeftEnAndereGroepen() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());

        //Extra niet toegestande groep:
        persoon.setNummerverwijzing(new PersoonNummerverwijzingGroepBericht());

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenDieGeboorteMist() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenDieSamengesteldeNaamMist() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenDieGeslachtsaanduidingMist() {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.setGeboorte(new PersoonGeboorteGroepBericht());

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, persoon, null, null);

        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testRelatieMix() {
        final PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        persoon1.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon1.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        persoon1.setGeboorte(new PersoonGeboorteGroepBericht());
        final PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        final PersoonBericht persoon3 = new PersoonBericht();
        persoon3.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final OuderBericht ouder1 = new OuderBericht();
        ouder1.setPersoon(persoon1);
        final OuderBericht ouder2 = new OuderBericht();
        ouder2.setPersoon(persoon2);
        final KindBericht kind = new KindBericht();
        kind.setPersoon(persoon3);
        relatie.getBetrokkenheden().add(ouder1);
        relatie.getBetrokkenheden().add(ouder2);
        relatie.getBetrokkenheden().add(kind);

        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(null, relatie, null, null);

        // Ouder 2 / persoon 2 voldoet niet.
        Assert.assertEquals(1, resultaat.size());
        Assert.assertEquals(persoon2, resultaat.get(0));
    }

    @Test
    public void testVoorMaximaleCoverage() {
        final List<BerichtEntiteit> resultaat = new BRAL0205().voerRegelUit(
                null, Mockito.mock(BerichtRootObject.class), null, null);

        Assert.assertEquals(0, resultaat.size());
    }


}
