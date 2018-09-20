/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY2018Test {

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2018, new BRBY2018().getRegel());
    }

    @Test
    public void testOudersZijnGehuwd() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        //Gehuwde ouders:
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder1, "iD", 1);
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        final HuwelijkHisVolledigImpl huwelijk =
                new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20100101).datumAanvang(20100101).eindeRecord()
                                                    .build();
        final PartnerHisVolledigImpl partnerBetr1 = new PartnerHisVolledigImpl(huwelijk, ouder1);
        final PartnerHisVolledigImpl partnerBetr2 = new PartnerHisVolledigImpl(huwelijk, ouder2);
        huwelijk.getBetrokkenheden().addAll(Arrays.asList(partnerBetr1, partnerBetr2));
        ouder1.getBetrokkenheden().add(partnerBetr1);
        ouder2.getBetrokkenheden().add(partnerBetr2);

        //FamilieRechtelijkeBetrekking
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, actie);
        ReflectionTestUtils.setField(kind, "iD", 3);

        //Maak het bericht model aan.
        final PersoonBericht kindBericht = new PersoonBericht();

        //Zet ouderlijk gezag op één van de ouders
        zetOuderlijkGezagOpOuderBetrokkenheidVanPersoon(ouder2);

        //voer regel uit
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY2018().voerRegelUit(new PersoonView(kind), kindBericht);
        Assert.assertFalse(berichtEntiteiten.isEmpty());
        Assert.assertEquals(kindBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testOudersZijnGeregistreerdPartner() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        //Geregistreerde partners:
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder1, "iD", 1);
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        final GeregistreerdPartnerschapHisVolledigImpl gp =
                new GeregistreerdPartnerschapHisVolledigImplBuilder()
                        .nieuwStandaardRecord(20100101).datumAanvang(20100101).eindeRecord()
                                                    .build();
        final PartnerHisVolledigImpl partnerBetr1 = new PartnerHisVolledigImpl(gp, ouder1);
        final PartnerHisVolledigImpl partnerBetr2 = new PartnerHisVolledigImpl(gp, ouder2);
        gp.getBetrokkenheden().addAll(Arrays.asList(partnerBetr1, partnerBetr2));
        ouder1.getBetrokkenheden().add(partnerBetr1);
        ouder2.getBetrokkenheden().add(partnerBetr2);

        //FamilieRechtelijkeBetrekking
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, actie);
        ReflectionTestUtils.setField(kind, "iD", 3);

        //Maak het bericht model aan.
        final PersoonBericht kindBericht = new PersoonBericht();

        //Zet ouderlijk gezag op één van de ouders
        zetOuderlijkGezagOpOuderBetrokkenheidVanPersoon(ouder2);

        //voer regel uit
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY2018().voerRegelUit(new PersoonView(kind), kindBericht);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testOudersZijnGehuwdMaarGeenOuderlijkGezagOpEenVanDeOuders() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        //Gehuwde ouders:
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder1, "iD", 1);
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        final HuwelijkHisVolledigImpl huwelijk =
                new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20100101).datumAanvang(20100101).eindeRecord()
                                                    .build();
        final PartnerHisVolledigImpl partnerBetr1 = new PartnerHisVolledigImpl(huwelijk, ouder1);
        final PartnerHisVolledigImpl partnerBetr2 = new PartnerHisVolledigImpl(huwelijk, ouder2);
        huwelijk.getBetrokkenheden().addAll(Arrays.asList(partnerBetr1, partnerBetr2));
        ouder1.getBetrokkenheden().add(partnerBetr1);
        ouder2.getBetrokkenheden().add(partnerBetr2);

        //FamilieRechtelijkeBetrekking
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, actie);
        ReflectionTestUtils.setField(kind, "iD", 3);

        //Maak het bericht model aan.
        final PersoonBericht kindBericht = new PersoonBericht();

        //Géén ouderlijk gezag

        //voer regel uit
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY2018().voerRegelUit(new PersoonView(kind), kindBericht);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testOudersZonderVerbintenisMaarWelOuderlijkGezag() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);
        //ouders:
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder1, "iD", 1);
        final PersoonHisVolledigImpl ouder2 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder2, "iD", 2);

        //FamilieRechtelijkeBetrekking
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, ouder2, kind, actie);
        ReflectionTestUtils.setField(kind, "iD", 3);

        //Maak het bericht model aan.
        final PersoonBericht kindBericht = new PersoonBericht();

        //Zet ouderlijk gezag op beide van de ouders
        zetOuderlijkGezagOpOuderBetrokkenheidVanPersoon(ouder2);
        zetOuderlijkGezagOpOuderBetrokkenheidVanPersoon(ouder1);

        //voer regel uit
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY2018().voerRegelUit(new PersoonView(kind), kindBericht);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testEenOuderMetGezag() {
        final ActieModel actie =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20130101), null, new DatumTijdAttribuut(new Date()), null);

        //ouder:
        final PersoonHisVolledigImpl ouder1 = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(ouder1, "iD", 1);

        //FamilieRechtelijkeBetrekking
        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder1, null, kind, actie);
        ReflectionTestUtils.setField(kind, "iD", 3);

        //Maak het bericht model aan.
        final PersoonBericht kindBericht = new PersoonBericht();

        //Zet ouderlijk gezag op één van de ouders
        zetOuderlijkGezagOpOuderBetrokkenheidVanPersoon(ouder1);

        //voer regel uit
        final List<BerichtEntiteit> berichtEntiteiten = new BRBY2018().voerRegelUit(new PersoonView(kind), kindBericht);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    private void zetOuderlijkGezagOpOuderBetrokkenheidVanPersoon(final PersoonHisVolledigImpl ouder) {
        for (final BetrokkenheidHisVolledigImpl betr : ouder.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betr.getRol().getWaarde()) {
                final OuderOuderlijkGezagGroepBericht ouderOuderlijkGezagGroepBericht =
                        new OuderOuderlijkGezagGroepBericht();
                ouderOuderlijkGezagGroepBericht.setDatumAanvangGeldigheid(
                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
                ouderOuderlijkGezagGroepBericht.setIndicatieOuderHeeftGezag(new JaNeeAttribuut(true));
                final HisOuderOuderlijkGezagModel ouderlijkGezag = new HisOuderOuderlijkGezagModel(
                        betr, ouderOuderlijkGezagGroepBericht, ouderOuderlijkGezagGroepBericht,
                        new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()),
                                       null, DatumTijdAttribuut.nu(), null)
                );
                ((OuderHisVolledigImpl) betr).getOuderOuderlijkGezagHistorie().voegToe(ouderlijkGezag);
            }
        }
    }


}
