/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BusinessUtilsTest {

    @Test
    public void testCheckPersonenInHoofdpersonenBijhPersoonVerwijstNaarHoofdPersoonViaObjectSleutel() {
        final List<Melding> meldingen = new ArrayList<>();
        final List<Persoon> hoofdPersonene = new ArrayList<>();
        final List<Persoon> bijhPersonene = new ArrayList<>();

        PersoonBericht bijhPersoon = new PersoonBericht();
        bijhPersoon.setObjectSleutelDatabaseID(1234);
        bijhPersonene.add(bijhPersoon);

        PersoonBericht hoofdPersoon = new PersoonBericht();
        hoofdPersoon.setObjectSleutelDatabaseID(1234);
        hoofdPersonene.add(hoofdPersoon);


        final boolean resultaat = BusinessUtils
                .checkPersonenInHoofdpersonen(hoofdPersonene, bijhPersonene, Regel.ALG0001, meldingen);

        Assert.assertTrue(resultaat);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testCheckPersonenInHoofdpersonenBijhPersoonVerwijstNaarHoofdPersoonViaReferentieId() {
        final List<Melding> meldingen = new ArrayList<>();
        final List<Persoon> hoofdPersonene = new ArrayList<>();
        final List<Persoon> bijhPersonene = new ArrayList<>();

        PersoonBericht bijhPersoon = new PersoonBericht();
        bijhPersoon.setReferentieID("foo");
        bijhPersonene.add(bijhPersoon);

        PersoonBericht hoofdPersoon = new PersoonBericht();
        hoofdPersoon.setCommunicatieID("foo");
        hoofdPersonene.add(hoofdPersoon);


        final boolean resultaat = BusinessUtils
                .checkPersonenInHoofdpersonen(hoofdPersonene, bijhPersonene, Regel.ALG0001, meldingen);

        Assert.assertTrue(resultaat);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testCheckPersonenInHoofdpersonenBijhPersoonVerwijstVerkeerdNaarHoofdPersoonViaReferentieId() {
        final List<Melding> meldingen = new ArrayList<>();
        final List<Persoon> hoofdPersonene = new ArrayList<>();
        final List<Persoon> bijhPersonene = new ArrayList<>();

        PersoonBericht bijhPersoon = new PersoonBericht();
        bijhPersoon.setReferentieID("foo");
        bijhPersonene.add(bijhPersoon);

        PersoonBericht hoofdPersoon = new PersoonBericht();
        hoofdPersoon.setCommunicatieID("bar");
        hoofdPersonene.add(hoofdPersoon);


        final boolean resultaat = BusinessUtils
                .checkPersonenInHoofdpersonen(hoofdPersonene, bijhPersonene, Regel.ALG0001, meldingen);

        Assert.assertTrue(resultaat);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(Regel.ALG0001, meldingen.get(0).getRegel());
    }

    @Test
    public void testMatchPersoonInRelatieBerichtViaObjectSleutel() {
        final int databaseId = 2001;
        final PersoonHisVolledigImpl brpPersoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(20120101, null, 20120101)
                .burgerservicenummer(123456789).eindeRecord().build();
        ReflectionTestUtils.setField(brpPersoon, "iD", databaseId);
        final PersoonView huidigePersoon = new PersoonView(brpPersoon);

        HuwelijkBericht nieuweSituatie = new HuwelijkBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        PartnerBericht partner1 = new PartnerBericht();
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setObjectSleutel("th3489t5");
        persoon1.setObjectSleutelDatabaseID(databaseId);
        partner1.setPersoon(persoon1);
        nieuweSituatie.getBetrokkenheden().add(partner1);

        PartnerBericht partner2 = new PartnerBericht();
        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon2.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(987654321));
        partner2.setPersoon(persoon2);
        nieuweSituatie.getBetrokkenheden().add(partner2);

        final PersoonBericht persoonBericht =
                BusinessUtils.matchPersoonInRelatieBericht(huidigePersoon, nieuweSituatie);

        Assert.assertEquals(persoonBericht, persoon1);
    }

    @Test
    public void testMatchPersoonInRelatieBerichtViaBsn() {
        final PersoonHisVolledigImpl brpPersoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(20120101, null, 20120101)
                .burgerservicenummer(123456789).eindeRecord().build();
        ReflectionTestUtils.setField(brpPersoon, "iD", 799);
        final PersoonView huidigePersoon = new PersoonView(brpPersoon);

        HuwelijkBericht nieuweSituatie = new HuwelijkBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        PartnerBericht partner1 = new PartnerBericht();
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon1.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(123456789));
        partner1.setPersoon(persoon1);
        nieuweSituatie.getBetrokkenheden().add(partner1);

        PartnerBericht partner2 = new PartnerBericht();
        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon2.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(987654321));
        partner2.setPersoon(persoon2);
        nieuweSituatie.getBetrokkenheden().add(partner2);

        final PersoonBericht persoonBericht =
                BusinessUtils.matchPersoonInRelatieBericht(huidigePersoon, nieuweSituatie);

        Assert.assertEquals(persoonBericht, persoon1);
    }

    @Test
    public void testMatchPersoonInRelatieBerichtViaObjectSleutelGeenMatch() {
        final int databaseId = 123;
        final PersoonHisVolledigImpl brpPersoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(20120101, null, 20120101)
                .burgerservicenummer(123456789).eindeRecord().build();
        ReflectionTestUtils.setField(brpPersoon, "iD", databaseId);
        final PersoonView huidigePersoon = new PersoonView(brpPersoon);

        HuwelijkBericht nieuweSituatie = new HuwelijkBericht();
        nieuweSituatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        PartnerBericht partner1 = new PartnerBericht();
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setObjectSleutelDatabaseID(8796548);
        partner1.setPersoon(persoon1);
        nieuweSituatie.getBetrokkenheden().add(partner1);

        PartnerBericht partner2 = new PartnerBericht();
        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon2.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(987654321));
        partner2.setPersoon(persoon2);
        nieuweSituatie.getBetrokkenheden().add(partner2);

        final PersoonBericht persoonBericht =
                BusinessUtils.matchPersoonInRelatieBericht(huidigePersoon, nieuweSituatie);

        Assert.assertNull(persoonBericht);
    }
}
