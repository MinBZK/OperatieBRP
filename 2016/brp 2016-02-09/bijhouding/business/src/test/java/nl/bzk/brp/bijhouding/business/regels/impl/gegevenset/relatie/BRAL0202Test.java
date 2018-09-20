/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL0202Test {

    private BRAL0202 bral0202;

    @Before
    public void init() {
        bral0202 = new BRAL0202();
    }

    @Test
    public void testGetRegel() throws Exception {
        Assert.assertEquals(Regel.BRAL0202, bral0202.getRegel());
    }

    @Test
    public void test2VerschillendePersonenInHuwelijk() {
        final PersoonBericht persoonBericht = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 123, null, 19840404, null, null, null, null, null);
        persoonBericht.setObjectSleutelDatabaseID(1);
        final PersoonBericht persoonBericht1 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 45678, null, 19850404, null, null, null, null, null);
        persoonBericht1.setObjectSleutelDatabaseID(2);
        final RelatieBericht huwelijk = new RelatieBuilder().bouwHuwelijkRelatie().
                voegPartnerToe(persoonBericht).
                voegPartnerToe(persoonBericht1).
                setDatumAanvang(20010404).
                getRelatie();

        final List<BerichtEntiteit> resultaat = bral0202.voerRegelUit(null, huwelijk, null, null);
        Assert.assertTrue(resultaat.isEmpty());
    }

    @Test
    public void test1Persoon2MaalInHuwelijk() {
        final PersoonBericht persoonBericht1 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 123, null, 19840404, null, null, null, null, null);
        persoonBericht1.setObjectSleutelDatabaseID(123);
        final PersoonBericht persoonBericht2 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 123, null, 19850404, null, null, null, null, null);
        persoonBericht2.setObjectSleutelDatabaseID(123);
        final RelatieBericht huwelijk = new RelatieBuilder().bouwHuwelijkRelatie().
                voegPartnerToe(persoonBericht1).
                voegPartnerToe(persoonBericht2).
                setDatumAanvang(20010404).
                getRelatie();

        final List<BerichtEntiteit> resultaat = bral0202.voerRegelUit(null, huwelijk, null, null);
        Assert.assertTrue(resultaat.size() == 1);
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertEquals("" + 123, resultaat.get(0).getObjectSleutel());
    }

    @Test
    public void testFamilieRechtelijkeBetrekking() {
        final PersoonBericht ouderPersoon1 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 1212, null, 19840404, null, null, null, null, null);
        ouderPersoon1.setObjectSleutelDatabaseID(123);
        final PersoonBericht ouderPersoon2 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 3232, null, 19850404, null, null, null, null, null);
        ouderPersoon2.setObjectSleutelDatabaseID(456);
        final PersoonBericht kindPersoon = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 4567, null, 19850404, null, null, null, null, null);
        kindPersoon.setObjectSleutelDatabaseID(789);
        final RelatieBericht familie = new RelatieBuilder().bouwFamilieRechtelijkeBetrekkingRelatie().
                voegOuderToe(ouderPersoon1).
                voegOuderToe(ouderPersoon2).
                voegKindToe(kindPersoon).
                getRelatie();

        final List<BerichtEntiteit> resultaat = bral0202.voerRegelUit(null, familie, null, null);
        Assert.assertTrue(resultaat.isEmpty());
    }

    @Test
    public void test1Persoon2MaalInFamilieRechtelijkeBetrekking() {
        final PersoonBericht ouderPersoon1 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 1212, null, 19840404, null, null, null, null, null);
        ouderPersoon1.setObjectSleutelDatabaseID(123);
        final PersoonBericht ouderPersoon2 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 3232, null, 19850404, null, null, null, null, null);
        ouderPersoon2.setObjectSleutelDatabaseID(456);
        final PersoonBericht kindPersoon = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 1212, null, 19850404, null, null, null, null, null);
        kindPersoon.setObjectSleutelDatabaseID(789);
        final RelatieBericht familie = new RelatieBuilder().bouwFamilieRechtelijkeBetrekkingRelatie().
                voegOuderToe(ouderPersoon1).
                voegOuderToe(ouderPersoon1).
                voegKindToe(kindPersoon).
                getRelatie();

        final List<BerichtEntiteit> resultaat = bral0202.voerRegelUit(null, familie, null, null);
        Assert.assertTrue(resultaat.size() == 1);
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertEquals("" + 1212, resultaat.get(0).getObjectSleutel());
    }

    @Test
    public void testFamilieRechtelijkeBetrekkingMetHuidigeFamilie() {
        final FamilierechtelijkeBetrekkingHisVolledigImpl huidigeFamilie = new FamilierechtelijkeBetrekkingHisVolledigImpl();

        // Vader
        final PersoonHisVolledigImpl vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(20010101, null, 20010101)
                    .burgerservicenummer(1212)
                .eindeRecord()
                .build();
        final OuderHisVolledigImpl ouder = new OuderHisVolledigImpl(huidigeFamilie, vader);
        huidigeFamilie.getBetrokkenheden().add(ouder);

        // Moeder
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(20010101, null, 20010101)
                    .burgerservicenummer(3232)
                .eindeRecord()
                .build();
        final OuderHisVolledigImpl ouder2 = new OuderHisVolledigImpl(huidigeFamilie, moeder);
        huidigeFamilie.getBetrokkenheden().add(ouder2);

        final PersoonBericht ouderPersoon1 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 2222, null, 19840404, null, null, null, null, null);
        ouderPersoon1.setObjectSleutelDatabaseID(123);
        final PersoonBericht ouderPersoon2 = PersoonBuilder
                .bouwPersoon(SoortPersoon.INGESCHREVENE, 3333, null, 19850404, null, null, null, null, null);
        ouderPersoon2.setObjectSleutelDatabaseID(456);
        final RelatieBericht nieuweFamilie = new RelatieBuilder<>().bouwFamilieRechtelijkeBetrekkingRelatie().
                voegOuderToe(ouderPersoon1).
                voegOuderToe(ouderPersoon2).
                getRelatie();

        final List<BerichtEntiteit> resultaat = bral0202.voerRegelUit(new FamilierechtelijkeBetrekkingView(huidigeFamilie), nieuweFamilie, null, null);
        Assert.assertTrue(resultaat.isEmpty());
    }
}
