/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.objecttype.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import org.junit.Test;

public class RelatieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;
//
//    @Inject
//    private ReferentieDataRepository referentieDataRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Inject
    private RelatieMdlRepository relatieMdlRepository;

//    @Inject
//    private PersoonNationaliteitHistorieRepository persoonNationaliteitHistorieRepository;
//
    @PersistenceContext
    private EntityManager em;

    @Test
    public void testZoekPartnersNull() throws Exception {
        List<Long> personen = relatieRepository.haalopPartners(null);
        Assert.assertNotNull(personen);
        Assert.assertTrue(personen.isEmpty());
    }

    @Test
    public void testZoekPartners() throws Exception {
        List<Long> personen = relatieRepository.haalopPartners(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenoten() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp20121231() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L, 20121231);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp20151231() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L, 20151231);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp19631231() throws Exception {
        List<Long> personen = relatieRepository.haalopEchtgenoten(8731137L, 19631231);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderen() throws Exception {
        List<Long> personen = relatieRepository.haalopKinderen(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
    }

    @Test
    public void testZoekOuders() throws Exception {
        List<Long> personen = relatieRepository.haalopOuders(3L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekfamilie() throws Exception {
        List<Long> personen = relatieRepository.haalopFamilie(3L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
        personen = relatieRepository.haalopFamilie(8731137L);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilterMannelijk() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(8731137), personen.get(0));
    }

    @Test
    public void testZoekKinderenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(3), personen.get(0));
    }

    @Test
    public void testZoekKinderenDochterFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.VROUW);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderenVaderFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(8731137), personen.get(0));
    }
    @Test
    public void testZoekKinderenMoederFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.VROUW);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(2), personen.get(0));
    }

    @Test
    public void testZoekFamilieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekFamilieIngezetenenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        filter.setUitPersoonTypen(SoortPersoon.INGESCHREVENE);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekAlleRelatieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortRelatie.HUWELIJK);
        filter.setSoortBetrokkenheden(SoortBetrokkenheid.KIND, SoortBetrokkenheid.OUDER, SoortBetrokkenheid.PARTNER);
        List<Long> personen = relatieMdlRepository.haalopRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testVindSoortRelatiesMetPersonenFamilieRechtelijkeBetrekkingVaderMoederKind() {
        initRelatiesEnPersonen();
         //Vind relaties waarin vader en moeder ouder zijn:
        List<PersistentRelatie> relaties = relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer("vader"),
                persoonRepository.findByBurgerservicenummer("moeder"),
                nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER,
                20120101,
                nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
        );

        Assert.assertNotNull(relaties);
        Assert.assertEquals(1, relaties.size());
        PersistentRelatie relatie = relaties.get(0);
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatie());
        boolean vaderGevonden = false;
        boolean moederGevonden = false;
        boolean kindGevonden = false;
        for (PersistentBetrokkenheid betr : relaties.get(0).getBetrokkenheden()) {
            if (nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER == betr.getSoortBetrokkenheid()) {
                if (betr.getBetrokkene().getBurgerservicenummer().equals("vader")) {
                    vaderGevonden = true;
                } else if (betr.getBetrokkene().getBurgerservicenummer().equals("moeder")) {
                    moederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else if (nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.KIND == betr.getSoortBetrokkenheid()) {
                if (betr.getBetrokkene().getBurgerservicenummer().equals("kind")) {
                    kindGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else {
                Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
            }
        }
        Assert.assertTrue(vaderGevonden && moederGevonden && kindGevonden);
    }

    @Test
    public void testVindSoortRelatiesMetPersonenGeregistreerdPartnerschapVaderMoeder() {
        initRelatiesEnPersonen();
        //Vind relaties waarin vader en moeder een geregistreerd partnerschap hebben
        List<PersistentRelatie> relatieList = relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer("vader"),
                persoonRepository.findByBurgerservicenummer("moeder"),
                nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER,
                20120101,
                nl.bzk.brp.model.gedeeld.SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        Assert.assertEquals(1, relatieList.size());
        boolean partner1Gevonden = false;
        boolean partner2Gevonden = false;
        for (PersistentBetrokkenheid betr : relatieList.get(0).getBetrokkenheden()) {
            if (nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER == betr.getSoortBetrokkenheid()) {
                if (betr.getBetrokkene().getBurgerservicenummer().equals("moeder")) {
                    partner1Gevonden = true;
                } else if (betr.getBetrokkene().getBurgerservicenummer().equals("vader")) {
                    partner2Gevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else {
                Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
            }
        }
        Assert.assertTrue(partner1Gevonden && partner2Gevonden);
    }

    @Test
    public void testVindSoortRelatiesMetPersonenOuderschapOpaMoederOmaMoeder() {
        initRelatiesEnPersonen();
        List<PersistentRelatie> relatieList = relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer("opaMoeder"),
                persoonRepository.findByBurgerservicenummer("omaMoeder"),
                nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER,
                20120101,
                nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
        );

        Assert.assertEquals(1, relatieList.size());
        PersistentRelatie familie = relatieList.get(0);
        boolean moederGevonden = false;
        boolean opaMoederGevonden = false;
        boolean omaMoederGevonden = false;
        for (PersistentBetrokkenheid betr : familie.getBetrokkenheden()) {
            if (nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER == betr.getSoortBetrokkenheid()) {
                if (betr.getBetrokkene().getBurgerservicenummer().equals("opaMoeder")) {
                    opaMoederGevonden = true;
                } else if (betr.getBetrokkene().getBurgerservicenummer().equals("omaMoeder")) {
                    omaMoederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else if (nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.KIND == betr.getSoortBetrokkenheid()) {
                if (betr.getBetrokkene().getBurgerservicenummer().equals("moeder")) {
                    moederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else {
                Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
            }
        }

        Assert.assertTrue(moederGevonden && opaMoederGevonden && omaMoederGevonden);
    }


    @Test
    public void testVindSoortRelatiesMetPersonenHuwelijkVaderMoeder() {
        initRelatiesEnPersonen();
        //Vind relaties waarin vader en moeder getrouwd zijn:
        Assert.assertTrue(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        persoonRepository.findByBurgerservicenummer("vader"),
                        persoonRepository.findByBurgerservicenummer("moeder"),
                        nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER,
                        20120101,
                        nl.bzk.brp.model.gedeeld.SoortRelatie.HUWELIJK).isEmpty()
        );
    }

    @Test
    public void testVindSoortRelatiesMetPersonenFamilieRechtelijkeBetrekkingVaderMoederAlsKind() {
        initRelatiesEnPersonen();
        //Vind relaties waarin vader en moeder kind zijn:
        Assert.assertTrue(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        persoonRepository.findByBurgerservicenummer("vader"),
                        persoonRepository.findByBurgerservicenummer("moeder"),
                        nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.KIND,
                        20120101,
                        nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING).isEmpty()
        );
    }

    @Test
    public void testVindSoortRelatiesMetPersonenHuwelijkOpaVaderOmaMoeder() {
        initRelatiesEnPersonen();
        //Vind relaties waarin opaVader getrouwd is met omaMoeder:
        Assert.assertTrue(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        persoonRepository.findByBurgerservicenummer("opaVader"),
                        persoonRepository.findByBurgerservicenummer("omaMoeder"),
                        nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER,
                        20120101,
                        nl.bzk.brp.model.gedeeld.SoortRelatie.HUWELIJK).isEmpty()
        );
    }

    @Test
    public void testVindSoortRelatiesMetPersonenGeregistreerdPartnerschapVaderMoederBeeindigd() {
        initRelatiesEnPersonen();
        //Beeindig geregistreerd partnerschap
        List<PersistentRelatie> relatieList = relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer("vader"),
                persoonRepository.findByBurgerservicenummer("moeder"),
                nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER,
                20120101,
                nl.bzk.brp.model.gedeeld.SoortRelatie.GEREGISTREERD_PARTNERSCHAP
        );

        PersistentRelatie partnerschap = relatieList.get(0);
        partnerschap.setDatumEinde(20120101);
        em.merge(partnerschap);

        Assert.assertTrue(
                relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        persoonRepository.findByBurgerservicenummer("vader"),
                        persoonRepository.findByBurgerservicenummer("moeder"),
                        nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER,
                        20120101,
                        nl.bzk.brp.model.gedeeld.SoortRelatie.GEREGISTREERD_PARTNERSCHAP).isEmpty()
        );
    }

    private void initRelatiesEnPersonen() {
       //De vader van vader  = opaVader
        //De moeder van vader = omaVader
        //Huwelijk tussen opaVader en omaVader:
        PersistentPersoon opaVader = maakPersoon("opaVader");
        PersistentPersoon omaVader = maakPersoon("omaVader");
        HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> huwelijkOpaOmaVader
                = new HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid>();
        huwelijkOpaOmaVader.put(opaVader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER);
        huwelijkOpaOmaVader.put(omaVader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER);
        maakRelatie(nl.bzk.brp.model.gedeeld.SoortRelatie.HUWELIJK, huwelijkOpaOmaVader);


        //De vader en familie rechtelijke betrekking met opa- en omaVader
        PersistentPersoon vader = maakPersoon("vader");
        HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> familieVader
                = new HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid>();
        familieVader.put(vader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.KIND);
        familieVader.put(opaVader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER);
        familieVader.put(omaVader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER);
        maakRelatie(nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, familieVader);


        //De vader van moeder  = opaMoeder
        //De moeder van moeder = omaMoeder
        //Huwelijk tussen opaMoeder en omaMoeder:
        PersistentPersoon opaMoeder = maakPersoon("opaMoeder");
        PersistentPersoon omaMoeder = maakPersoon("omaMoeder");
        HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> huwelijkOpaOmaMoeder
                = new HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid>();
        huwelijkOpaOmaMoeder.put(opaMoeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER);
        huwelijkOpaOmaMoeder.put(omaMoeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER);
        maakRelatie(nl.bzk.brp.model.gedeeld.SoortRelatie.HUWELIJK, huwelijkOpaOmaMoeder);


        //De moeder en familie rechtelijke betrekking met opa- en omaMoeder
        PersistentPersoon moeder = maakPersoon("moeder");
        HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> familieMoeder
                = new HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid>();
        familieMoeder.put(moeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.KIND);
        familieMoeder.put(opaMoeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER);
        familieMoeder.put(omaMoeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER);
        maakRelatie(nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, familieMoeder);


        //Geregistreerd partnerschap tussen vader en moeder:
        HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> huwelijkVaderMoeder
                = new HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid>();
        huwelijkVaderMoeder.put(vader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER);
        huwelijkVaderMoeder.put(moeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.PARTNER);
        maakRelatie(nl.bzk.brp.model.gedeeld.SoortRelatie.GEREGISTREERD_PARTNERSCHAP, huwelijkVaderMoeder);


        //Kind van vader en moeder
        PersistentPersoon kind = maakPersoon("kind");
        HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> familieKind
                = new HashMap<PersistentPersoon, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid>();
        familieKind.put(kind, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.KIND);
        familieKind.put(vader, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER);
        familieKind.put(moeder, nl.bzk.brp.model.gedeeld.SoortBetrokkenheid.OUDER);
        maakRelatie(nl.bzk.brp.model.gedeeld.SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, familieKind);
    }

    private PersistentRelatie maakRelatie(
            final nl.bzk.brp.model.gedeeld.SoortRelatie soort, final Map<PersistentPersoon,
            nl.bzk.brp.model.gedeeld.SoortBetrokkenheid> betrokkenheden) {
        final int datumAanvang = 20120101;
        PersistentRelatie relatie = new PersistentRelatie();
        relatie.setSoortRelatie(soort);
        relatie.setDatumAanvang(datumAanvang);
        relatie.setStatusHistorie(StatusHistorie.A);
        relatie.setBetrokkenheden(new HashSet<PersistentBetrokkenheid>());
        em.persist(relatie);

        for (PersistentPersoon persoon : betrokkenheden.keySet()) {
            PersistentBetrokkenheid betr = new PersistentBetrokkenheid();
            betr.setSoortBetrokkenheid(betrokkenheden.get(persoon));
            betr.setBetrokkene(persoon);
            betr.setRelatie(relatie);
            betr.setOuderStatusHistorie(StatusHistorie.A);
            betr.setOuderlijkGezagStatusHistorie(StatusHistorie.A);
            em.persist(betr);
            relatie.getBetrokkenheden().add(betr);
        }

        return relatie;
    }

    private PersistentPersoon maakPersoon(final String bsn) {
        PersistentPersoon pers = new PersistentPersoon();
        pers.setBurgerservicenummer(bsn);
        pers.setSoortPersoon(nl.bzk.brp.model.gedeeld.SoortPersoon.INGESCHREVENE);

        pers.setGeboorteStatusHis(StatusHistorie.A);
        pers.setSamengesteldeNaamStatusHis(StatusHistorie.A);
        pers.setGeslachtsaanduidingStatusHis(StatusHistorie.A);
        pers.setStatushistorie(StatusHistorie.A);
        pers.setAanschrijvingStatusHis(StatusHistorie.A);
        pers.setOverlijdenStatusHis(StatusHistorie.A);
        pers.setVerblijfsrechtStatusHis(StatusHistorie.A);
        pers.setUitsluitingNLKiesrechtStatusHis(StatusHistorie.A);
        pers.setEUVerkiezingenStatusHis(StatusHistorie.A);
        pers.setBijhoudingsverantwoordelijkheidStatusHis(StatusHistorie.A);
        pers.setOpschortingStatusHis(StatusHistorie.A);
        pers.setBijhoudingsgemeenteStatusHis(StatusHistorie.A);
        pers.setPersoonskaartStatusHis(StatusHistorie.A);
        pers.setImmigratieStatusHis(StatusHistorie.A);
        pers.setInschrijvingStatusHis(StatusHistorie.A);
        em.persist(pers);
        return pers;
    }
}
