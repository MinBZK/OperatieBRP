/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0007Test {

    private final BRBY0007 brby0007 = new BRBY0007();

    private PersoonBericht eerstePersoon;
    private PersoonBericht tweedePersoon;

    @Before
    public void init() {
        eerstePersoon = new PersoonBericht();
        eerstePersoon.setGeboorte(maakGeboorte());
        eerstePersoon.setGeslachtsaanduiding(maakGeslachtsaanduiding());
        eerstePersoon.setSamengesteldeNaam(maakSamengesteldenaam());

        tweedePersoon = new PersoonBericht();
        tweedePersoon.setGeboorte(maakGeboorte());
        tweedePersoon.setGeslachtsaanduiding(maakGeslachtsaanduiding());
        tweedePersoon.setSamengesteldeNaam(maakSamengesteldenaam());
    }


    @Test
    public void testIsPersonenGelijk() {
        Assert.assertTrue(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteOngelijk() {
        eerstePersoon.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20130101));
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboortePersoon1Null() {
        eerstePersoon.setGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboortePersoon2Null() {
        tweedePersoon.setGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteGemeentePersoon1Null() {
        eerstePersoon.getGeboorte().setGemeenteGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteGemeentePersoon2Null() {
        tweedePersoon.getGeboorte().setGemeenteGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteWoonplaatsPersoon1Null() {
        eerstePersoon.getGeboorte().setWoonplaatsnaamGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteWoonplaatsPersoon2Null() {
        tweedePersoon.getGeboorte().setWoonplaatsnaamGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteLandPersoon1Null() {
        eerstePersoon.getGeboorte().setLandGebiedGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeboorteLandPersoon2Null() {
        tweedePersoon.getGeboorte().setLandGebiedGeboorte(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeslachtsaanduidingOngelijk() {
        eerstePersoon.getGeslachtsaanduiding().setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.VROUW));
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeslachtsaanduidingPersoon1Null() {
        eerstePersoon.setGeslachtsaanduiding(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testGeslachtsaanduidingPersoon2Null() {
        tweedePersoon.setGeslachtsaanduiding(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testSamengesteldenaamOngelijk() {
        eerstePersoon.getSamengesteldeNaam().setVoornamen(new VoornamenAttribuut("abc"));
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testSamengesteldenaamPersoon1Null() {
        eerstePersoon.setSamengesteldeNaam(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    @Test
    public void testSamengesteldenaamPersoon2Null() {
        tweedePersoon.setSamengesteldeNaam(null);
        Assert.assertFalse(brby0007.isGelijk(eerstePersoon, tweedePersoon));
    }

    private PersoonGeboorteGroepBericht maakGeboorte() {
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20101010));
        persoonGeboorteGroepBericht.setGemeenteGeboorte(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        persoonGeboorteGroepBericht.setWoonplaatsnaamGeboorte(StatischeObjecttypeBuilder.WOONPLAATS_ALMERE);
        persoonGeboorteGroepBericht.setBuitenlandsePlaatsGeboorte(new BuitenlandsePlaatsAttribuut("bl"));
        persoonGeboorteGroepBericht.setBuitenlandseRegioGeboorte(new BuitenlandseRegioAttribuut("reg"));
        persoonGeboorteGroepBericht.setOmschrijvingLocatieGeboorte(new LocatieomschrijvingAttribuut("oms"));
        persoonGeboorteGroepBericht.setLandGebiedGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);

        return persoonGeboorteGroepBericht;
    }

    private PersoonGeslachtsaanduidingGroepBericht maakGeslachtsaanduiding() {
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));

        return persoonGeslachtsaanduidingGroepBericht;
    }

    private PersoonSamengesteldeNaamGroepBericht maakSamengesteldenaam() {
        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaamGroepBericht.setVoornamen(new VoornamenAttribuut("voornaam"));
        samengesteldeNaamGroepBericht.setVoorvoegsel(new VoorvoegselAttribuut("voorv"));
        samengesteldeNaamGroepBericht.setScheidingsteken(new ScheidingstekenAttribuut(";"));
        samengesteldeNaamGroepBericht.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut("gesln"));

        return samengesteldeNaamGroepBericht;
    }
}
