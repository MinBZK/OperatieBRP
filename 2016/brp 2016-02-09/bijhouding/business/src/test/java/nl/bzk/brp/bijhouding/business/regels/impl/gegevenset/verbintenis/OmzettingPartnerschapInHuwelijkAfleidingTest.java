/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestGemeenteBuilder;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/** Unit test voor de functionaliteit zoals geboden in de {@link OmzettingPartnerschapInHuwelijkAfleiding} klasse. */
public class OmzettingPartnerschapInHuwelijkAfleidingTest {

    private static final Integer                 DATUM_EINDE    = 20120202;
    private static final Integer                 DATUM_AANVANG  = 20010101;
    private static final GemeenteCodeAttribuut   GEMEENTECODE   = new GemeenteCodeAttribuut((short) 34);
    private static final NaamEnumeratiewaardeAttribuut WOONPLAATSNAAM = new NaamEnumeratiewaardeAttribuut("Lutjesbroek");
    private static final LandGebiedCodeAttribuut       LANDCODE       = LandGebiedCodeAttribuut.NEDERLAND;

    private PersoonHisVolledigImpl man;
    private PersoonHisVolledigImpl vrouw;

    @Test
    public void testGetRegel() {
        final OmzettingPartnerschapInHuwelijkAfleiding afleiding =
            new OmzettingPartnerschapInHuwelijkAfleiding(null, null);

        Assert.assertEquals(Regel.VR02002c, afleiding.getRegel());
    }

    @Test
    public void testLeidAf() {
        final GeregistreerdPartnerschapHisVolledigImpl partnerschap =
            bouwOudePartnerschap(SoortPersoon.INGESCHREVENE, SoortPersoon.INGESCHREVENE);
        final ActieModel actie = maakActie(
            SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
            SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            20101231);

        final OmzettingPartnerschapInHuwelijkAfleiding afleiding =
            new OmzettingPartnerschapInHuwelijkAfleiding(partnerschap, actie);

        // Test de preconditie dat zowel de man als de vrouw slechts een enkele betrokkenheid hebben met als
        // relatie een geregistreerd partnerschap
        Assert.assertEquals(1, man.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP,
            man.getBetrokkenheden().iterator().next().getRelatie().getSoort().getWaarde());

        Assert.assertEquals(1, vrouw.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP,
            vrouw.getBetrokkenheden().iterator().next().getRelatie().getSoort().getWaarde());

        // Doe de afleiding
        afleiding.leidAf();

        // Controleer de personen dat ze nu twee betrokkenheden hebben, namelijk een beeindigd partnerschap en een
        // nieuwe huwelijk.
        for (final PersoonHisVolledigImpl persoon : new PersoonHisVolledigImpl[]{ man, vrouw }) {
            controleerBetrokkenhedenIngeschrevene(actie, persoon);
        }
    }

    /**
     * Controleert of de opgegeven persoon twee betrokkenheden heeft (een nieuw huwelijk en een beeindigd
     * partnerschap).
     *
     * @param actie de actie die heeft geleidt tot de omzetting.
     * @param persoon de persoon die gecontroleerd dient te worden.
     */
    private void controleerBetrokkenhedenIngeschrevene(final ActieModel actie, final PersoonHisVolledigImpl persoon) {
        Assert.assertEquals(2, persoon.getBetrokkenheden().size());

        final List<SoortRelatie> soortenRelaties = new ArrayList<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            soortenRelaties.add(betrokkenheid.getRelatie().getSoort().getWaarde());

            if (betrokkenheid.getRelatie().getSoort().getWaarde() == SoortRelatie.HUWELIJK) {
                final HuwelijkHisVolledigImpl nieuwHuwelijk = (HuwelijkHisVolledigImpl) betrokkenheid.getRelatie();
                final HisRelatieModel actueel = nieuwHuwelijk.getRelatieHistorie().getActueleRecord();

                Assert.assertEquals(DATUM_EINDE, actueel.getDatumAanvang().getWaarde());
                Assert.assertEquals(GEMEENTECODE, actueel.getGemeenteAanvang().getWaarde().getCode());
                Assert.assertEquals(WOONPLAATSNAAM, actueel.getWoonplaatsnaamAanvang());
                Assert.assertEquals(LANDCODE, actueel.getLandGebiedAanvang().getWaarde().getCode());
                Assert.assertEquals(actie, actueel.getVerantwoordingInhoud());
                Assert.assertNull(actueel.getVerantwoordingVerval());

                Assert.assertEquals(2, nieuwHuwelijk.getBetrokkenheden().size());
            } else {
                Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, betrokkenheid.getRelatie().getSoort().getWaarde());
            }
        }

        Assert.assertTrue(soortenRelaties.contains(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        Assert.assertTrue(soortenRelaties.contains(SoortRelatie.HUWELIJK));
    }

    @Test
    public void testLeidAfMetNietIngeschrevene() {
        final GeregistreerdPartnerschapHisVolledigImpl partnerschap = bouwOudePartnerschap(SoortPersoon.INGESCHREVENE, SoortPersoon.NIET_INGESCHREVENE);
        final ActieModel actie = maakActie(
            SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
            SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            20101231);

        final OmzettingPartnerschapInHuwelijkAfleiding afleiding = new OmzettingPartnerschapInHuwelijkAfleiding(partnerschap, actie);

        // Test de preconditie dat zowel de man als de vrouw slechts een enkele betrokkenheid hebben met als
        // relatie een geregistreerd partnerschap
        Assert.assertEquals(1, man.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP,
            man.getBetrokkenheden().iterator().next().getRelatie().getSoort().getWaarde());

        Assert.assertEquals(1, vrouw.getBetrokkenheden().size());
        Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP,
            vrouw.getBetrokkenheden().iterator().next().getRelatie().getSoort().getWaarde());

        // Doe de afleiding
        afleiding.leidAf();

        // Controleer dat er een nieuwe niet ingeschrevene is aangemaakt voor het huwelijk.
        final HuwelijkHisVolledigImpl huwelijk = getHuwelijkVanPersoon(man);
        Assert.assertNotNull(huwelijk);
        final PersoonHisVolledigImpl nieuweNietIngeschrevene = getPartnerVanUitRelatie(huwelijk, man);
        Assert.assertNotNull(nieuweNietIngeschrevene);
        Assert.assertEquals(SoortPersoon.NIET_INGESCHREVENE, nieuweNietIngeschrevene.getSoort().getWaarde());
        Assert.assertNotSame(vrouw, nieuweNietIngeschrevene);

        // Controleer de betrokkenheden
        controleerBetrokkenhedenIngeschrevene(actie, man);
        controleerBetrokkenhedenNietIngeschrevene(vrouw, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        controleerBetrokkenhedenNietIngeschrevene(nieuweNietIngeschrevene, SoortRelatie.HUWELIJK);
    }

    /**
     * Retourneert de partner van de opgegeven persoon, binnen de opgegeven verbintenis.
     *
     * @param verbintenis het huwelijk of geregistreerdpartnerschap waarbinnen het bepaald dient te worden.
     * @param persoon de persoon waarvan de partner opgehaald dient te worden.
     * @return de partner van de persoon.
     */
    private PersoonHisVolledigImpl getPartnerVanUitRelatie(
        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl verbintenis,
        final PersoonHisVolledigImpl persoon)
    {
        PersoonHisVolledigImpl partner = null;
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : verbintenis.getBetrokkenheden()) {
            if (!betrokkenheid.getPersoon().equals(persoon)) {
                partner = betrokkenheid.getPersoon();
                break;
            }
        }
        return partner;
    }

    /**
     * Retourneert het huwelijk van de opgegeven persoon.
     *
     * @param persoon de persoon waarvan het huwelijk geretourneerd dient te worden.
     * @return het huwelijk van de persoon.
     */
    private HuwelijkHisVolledigImpl getHuwelijkVanPersoon(final PersoonHisVolledigImpl persoon) {
        HuwelijkHisVolledigImpl huwelijk = null;
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRelatie().getSoort().getWaarde() == SoortRelatie.HUWELIJK) {
                huwelijk = (HuwelijkHisVolledigImpl) betrokkenheid.getRelatie();
                break;
            }
        }
        return huwelijk;
    }

    private void controleerBetrokkenhedenNietIngeschrevene(final PersoonHisVolledigImpl persoon,
        final SoortRelatie soortRelatie)
    {
        Assert.assertEquals(1, persoon.getBetrokkenheden().size());

        Assert.assertEquals(soortRelatie,
            persoon.getBetrokkenheden().iterator().next().getRelatie().getSoort().getWaarde());
    }

    /**
     * Bouwt de oude partnerschap waarvan de afleiding plaatsvindt.
     *
     * @return geregistreerd partnerschap his volledig impl
     */
    private GeregistreerdPartnerschapHisVolledigImpl bouwOudePartnerschap(final SoortPersoon soortPartner1,
        final SoortPersoon soortPartner2)
    {
        final GeregistreerdPartnerschapHisVolledigImpl relatie = new GeregistreerdPartnerschapHisVolledigImpl();

        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG));
        standaard.setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(DATUM_EINDE));
        standaard.setGemeenteEinde(new GemeenteAttribuut(TestGemeenteBuilder.maker()
            .metCode(GEMEENTECODE)
            .metDatumAanvang(20000101).maak()));
        standaard.setWoonplaatsnaamEinde(WOONPLAATSNAAM);
        standaard.setLandGebiedEinde(
                new LandGebiedAttribuut(new LandGebied(LANDCODE, null, null, new DatumEvtDeelsOnbekendAttribuut(20000101), null)));

        final HisRelatieModel partnerschapModel =
            new HisRelatieModel(relatie, standaard,
                maakActie(
                    SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK,
                    SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
                    20101231));

        relatie.getRelatieHistorie().voegToe(partnerschapModel);

        man = new PersoonHisVolledigImplBuilder(soortPartner1).build();
        vrouw = new PersoonHisVolledigImplBuilder(soortPartner2).build();

        relatie.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, man));
        relatie.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, vrouw));

        man.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, man));
        vrouw.getBetrokkenheden().add(new PartnerHisVolledigImpl(relatie, vrouw));

        return relatie;
    }

    /**
     * Creeert een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel maakActie(final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final SoortActie soortActie,
        final Integer datumAanvang)
    {
        return new ActieModel(new SoortActieAttribuut(soortActie),
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                soortAdministratieveHandeling), null, null, null),
            null, new DatumEvtDeelsOnbekendAttribuut(datumAanvang), null, DatumTijdAttribuut.nu(), null);
    }
}
