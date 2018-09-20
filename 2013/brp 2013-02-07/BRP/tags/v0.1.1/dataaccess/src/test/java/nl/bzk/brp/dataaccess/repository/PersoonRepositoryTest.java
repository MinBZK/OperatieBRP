/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.Predikaat;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorte;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduiding;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import org.junit.Assert;
import org.junit.Test;


public class PersoonRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;

    @PersistenceContext
    private EntityManager     em;

    @Test
    public void testfindByBurgerservicenummerResultaat() {
        PersistentPersoon persoon = persoonRepository.findByBurgerservicenummer("123456789");
        Assert.assertNotNull(persoon);
        Assert.assertEquals(1, persoon.getId().longValue());
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummer() {
        Persoon persoon = persoonRepository.haalPersoonOpMetBurgerservicenummer("234567891");
        Assert.assertNotNull(persoon);
        Assert.assertEquals(2, persoon.getId().longValue());

        // Test nationaliteiten collectie
        Assert.assertFalse(persoon.getNationaliteiten().isEmpty());
        Assert.assertEquals("1", persoon.getNationaliteiten().get(0).getNationaliteit().getCode());

        // Test indicaties collectie
        Assert.assertFalse(persoon.getIndicaties().isEmpty());
        Assert.assertTrue(persoon.getIndicatieWaarde(SoortIndicatie.VERSTREKKINGSBEPERKING));
        Assert.assertNull(persoon.getIndicatieWaarde(SoortIndicatie.DERDE_HEEFT_GEZAG));
        Assert.assertFalse(persoon.getIndicatieWaarde(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT));
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummerEnTestOuderschap() {
        Persoon persoon = persoonRepository.haalPersoonOpMetBurgerservicenummer("345678912");
        Assert.assertNotNull(persoon);
        Assert.assertEquals(3, persoon.getId().longValue());

        for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getSoortBetrokkenheid().equals(SoortBetrokkenheid.KIND)) {
                Relatie relatie = betrokkenheid.getRelatie();
                Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatie());
                for (Betrokkenheid relatieBetrokkenheid : relatie.getBetrokkenheden()) {
                    Persoon betrokkene = relatieBetrokkenheid.getBetrokkene();
                    if (betrokkene.getId().equals(2L)) {
                        Assert.assertNull(relatieBetrokkenheid.getDatumAanvangOuderschap());
                    } else if (betrokkene.getId().equals(8731137L)) {
                        Assert.assertEquals(Integer.valueOf(17240422), relatieBetrokkenheid.getDatumAanvangOuderschap());
                    }
                }
            }
        }
    }

    @Test
    public void testFindByBurgerservicenummerGeenResultaat() {
        Assert.assertNull(persoonRepository.findByBurgerservicenummer(""));
    }

    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummer() {
        List<Persoon> personen = persoonRepository.haalPersonenMetAdresOpViaBurgerservicenummer("");
        Assert.assertTrue("personen zou leeg moeten zijn", personen.size() == 0);
    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummer() {
        List<Persoon> personen = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer("1334NA", "73", "A", "sous");
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals("123456789", personen.get(0).getIdentificatienummers().getBurgerservicenummer());

        personen = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer("1000AA", "1", null, null);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals("234567891", personen.get(0).getIdentificatienummers().getBurgerservicenummer());

    }

    @Test
    public void testOpslaanNieuwPersoon() {
        persoonRepository.opslaanNieuwPersoon(maakNieuwPersoon(), 20101212);

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersistentPersoon persoon WHERE burgerservicenummer = :burgerservicenummer";

        PersistentPersoon persoon =
            (PersistentPersoon) em.createQuery(persoonsql).setParameter("burgerservicenummer", "bsnnummer")
                    .getSingleResult();

        // Controleer A-laag
        Assert.assertEquals("bsnnummer", persoon.getBurgerservicenummer());
        Assert.assertEquals("anummer", persoon.getANummer());
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
        Assert.assertEquals(StatusHistorie.A, persoon.getStatushistorie());

        // Voornaam
        Assert.assertEquals(2, persoon.getPersoonVoornamen().size());
        Assert.assertNotNull(haalopVoornaam("voornaam1", persoon.getPersoonVoornamen()));
        Assert.assertNotNull(haalopVoornaam("voornaam2", persoon.getPersoonVoornamen()));
        Assert.assertNull(persoon.getVoornaam());

        // Geslachtsnaam
        PersistentPersoonGeslachtsnaamcomponent pg1 =
            haalopGeslachtsnaam("geslachtsnaamcomp1", persoon.getPersoonGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg1);
        Assert.assertTrue("geslachtsnaamcomp1".equals(pg1.getNaam()));
        Assert.assertTrue("voorvoeg1".equals(pg1.getVoorvoegsel()));
        Assert.assertEquals(AdellijkeTitel.BARON, pg1.getAdellijkeTitel());
        Assert.assertEquals(Predikaat.HOOGHEID, pg1.getPredikaat());
        Assert.assertTrue(",".equals(pg1.getScheidingsteken()));

        PersistentPersoonGeslachtsnaamcomponent pg2 =
            haalopGeslachtsnaam("geslachtsnaamcomp2", persoon.getPersoonGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg2);
        Assert.assertTrue("geslachtsnaamcomp2".equals(pg2.getNaam()));
        Assert.assertTrue("voorvoeg2".equals(pg2.getVoorvoegsel()));
        Assert.assertEquals(AdellijkeTitel.GRAAF, pg2.getAdellijkeTitel());
        Assert.assertEquals(Predikaat.JONKHEER, pg2.getPredikaat());
        Assert.assertTrue(";".equals(pg2.getScheidingsteken()));

        Assert.assertNull(persoon.getVoornaam());

        // Geboorte
        Assert.assertEquals(19700101, persoon.getDatumGeboorte().longValue());
        Assert.assertEquals("0034", persoon.getGemeenteGeboorte().getGemeentecode());
        Assert.assertEquals("Almeres", persoon.getWoonplaatsGeboorte().getNaam());
        Assert.assertEquals("Afghanistan", persoon.getLandGeboorte().getNaam());

        // Geslachtsaanduiding
        Assert.assertEquals(GeslachtsAanduiding.MAN, persoon.getGeslachtsAanduiding());

        // Controlleer C-laag

        // hisVoornaam
        final String hisPersoonvoornaam =
            "SELECT hisPersoonVoornaam FROM HisPersoonVoornaam hisPersoonVoornaam WHERE persoonVoornaam.persoon.burgerservicenummer = :burgerservicenummer order by naam";

        @SuppressWarnings("unchecked")
        List<HisPersoonVoornaam> hisVoornamen =
            em.createQuery(hisPersoonvoornaam).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonVoornaam hisVoornaam1 = hisVoornamen.get(0);
        Assert.assertEquals(20101212, hisVoornaam1.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisVoornaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam1.getDatumTijdVerval());
        Assert.assertEquals("voornaam1", hisVoornaam1.getNaam());

        HisPersoonVoornaam hisVoornaam2 = hisVoornamen.get(1);
        Assert.assertEquals(20101212, hisVoornaam2.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisVoornaam2.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam2.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam2.getDatumTijdVerval());
        Assert.assertEquals("voornaam2", hisVoornaam2.getNaam());

        // hisGeslachtsnaam
        final String hisGeslachtsnaamcomp =
            "SELECT hisGesln FROM HisPersoonGeslachtsnaamcomponent hisGesln WHERE persoonGeslachtsnaamcomponent.persoon.burgerservicenummer = :burgerservicenummer order by naam";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeslachtsnaamcomponent> hisGeslachtsnaamcompenenten =
            em.createQuery(hisGeslachtsnaamcomp).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonGeslachtsnaamcomponent hisGeslachtsNaam1 = hisGeslachtsnaamcompenenten.get(0);
        Assert.assertEquals(20101212, hisGeslachtsNaam1.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisGeslachtsNaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam1.getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp1", hisGeslachtsNaam1.getNaam());
        Assert.assertEquals("voorvoeg1", hisGeslachtsNaam1.getVoorvoegsel());

        HisPersoonGeslachtsnaamcomponent hisGeslachtsNaam2 = hisGeslachtsnaamcompenenten.get(1);
        Assert.assertEquals(20101212, hisGeslachtsNaam2.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisGeslachtsNaam2.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam2.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam2.getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp2", hisGeslachtsNaam2.getNaam());
        Assert.assertEquals("voorvoeg2", hisGeslachtsNaam2.getVoorvoegsel());

        // HisPersoonGeboorte
        final String hisPersoonGeboorte =
            "SELECT hisGeboorte FROM HisPersoonGeboorte hisGeboorte WHERE persoon.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeboorte> hisPersoonGeboorten =
            em.createQuery(hisPersoonGeboorte).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonGeboorte hisGeboorte = hisPersoonGeboorten.get(0);
        Assert.assertNotNull(hisGeboorte.getDatumTijdRegistratie());
        Assert.assertNull(hisGeboorte.getDatumTijdVerval());
        Assert.assertEquals("0034", hisGeboorte.getGemeenteGeboorte().getGemeentecode());
        Assert.assertEquals("0034", hisGeboorte.getWoonplaatsGeboorte().getWoonplaatscode());

        // HisPersoonGeboorte
        final String hisGeslachtsaand =
            "SELECT hisGesl FROM HisPersoonGeslachtsaanduiding hisGesl WHERE persoon.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeslachtsaanduiding> hisGeslachtsaanduidingen =
            em.createQuery(hisGeslachtsaand).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonGeslachtsaanduiding hisGeslachtsaanduiding = hisGeslachtsaanduidingen.get(0);
        Assert.assertEquals(20101212, hisGeslachtsaanduiding.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsaanduiding.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumTijdVerval());
        Assert.assertEquals(GeslachtsAanduiding.MAN, hisGeslachtsaanduiding.getGeslachtsAanduiding());
    }

    @Test
    public void testPersoonOpslaanGeenPlaats() {
        Persoon persoon = maakNieuwPersoon();
        persoon.getIdentificatienummers().setBurgerservicenummer("bsnummer2");
        persoon.getGeboorte().getWoonplaatsGeboorte().setWoonplaatscode(null);

        persoonRepository.opslaanNieuwPersoon(persoon, 20101212);

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersistentPersoon persoon WHERE burgerservicenummer = :burgerservicenummer";

        PersistentPersoon persoonCheck =
            (PersistentPersoon) em.createQuery(persoonsql).setParameter("burgerservicenummer", "bsnummer2")
                    .getSingleResult();


        Assert.assertNull(persoonCheck.getWoonplaatsGeboorte());
    }


    @Test(expected = ObjectReedsBestaandExceptie.class)
    public void testOpslaanNieuwPersoonAlBestaand() {
        Persoon persoon = maakNieuwPersoon();
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");

        persoonRepository.opslaanNieuwPersoon(persoon, 20101212);
    }


    private Persoon maakNieuwPersoon() {
        // PersoonIdentiteit
        PersoonIdentiteit identiteit = new PersoonIdentiteit();
        identiteit.setSoort(SoortPersoon.INGESCHREVENE);

        // Identificatie nummer
        PersoonIdentificatienummers persoonIdentificatienummers = new PersoonIdentificatienummers();
        persoonIdentificatienummers.setAdministratienummer("anummer");
        persoonIdentificatienummers.setBurgerservicenummer("bsnnummer");

        // Gemeente
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0034");

        // Land
        Land land = new Land();
        land.setLandcode("6023");

        // Plaats
        Plaats plaats = new Plaats();
        plaats.setWoonplaatscode("0034");

        // Persoon geboorte
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19700101);
        persoonGeboorte.setGemeenteGeboorte(gemeente);
        persoonGeboorte.setLandGeboorte(land);
        persoonGeboorte.setWoonplaatsGeboorte(plaats);

        // Geslachtsaanduiding
        PersoonGeslachtsAanduiding persoonGeslachtsAanduiding = new PersoonGeslachtsAanduiding();
        persoonGeslachtsAanduiding.setGeslachtsAanduiding(GeslachtsAanduiding.MAN);

        Persoon persoon = new Persoon();
        persoon.setIdentiteit(identiteit);
        persoon.setIdentificatienummers(persoonIdentificatienummers);

        PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        persoonVoornaam1.setVolgnummer(1);
        persoonVoornaam1.setNaam("voornaam1");
        persoon.getPersoonVoornamen().add(persoonVoornaam1);

        PersoonVoornaam persoonVoornaam2 = new PersoonVoornaam();
        persoonVoornaam2.setVolgnummer(2);
        persoonVoornaam2.setNaam("voornaam2");
        persoon.getPersoonVoornamen().add(persoonVoornaam2);

        PersoonGeslachtsnaamcomponent component1 = new PersoonGeslachtsnaamcomponent();
        component1.setVolgnummer(1);
        component1.setNaam("geslachtsnaamcomp1");
        component1.setVoorvoegsel("voorvoeg1");
        component1.setAdellijkeTitel(AdellijkeTitel.BARON);
        component1.setPredikaat(Predikaat.HOOGHEID);
        component1.setScheidingsTeken(",");
        persoon.getGeslachtsnaamcomponenten().add(component1);

        PersoonGeslachtsnaamcomponent component2 = new PersoonGeslachtsnaamcomponent();
        component2.setVolgnummer(2);
        component2.setNaam("geslachtsnaamcomp2");
        component2.setVoorvoegsel("voorvoeg2");
        component2.setAdellijkeTitel(AdellijkeTitel.GRAAF);
        component2.setPredikaat(Predikaat.JONKHEER);
        component2.setScheidingsTeken(";");
        persoon.getGeslachtsnaamcomponenten().add(component2);

        persoon.setGeboorte(persoonGeboorte);
        persoon.setPersoonGeslachtsAanduiding(persoonGeslachtsAanduiding);

        return persoon;
    }

    private PersistentPersoonVoornaam haalopVoornaam(final String voornaam,
            final Set<PersistentPersoonVoornaam> voornamen)
    {
        for (PersistentPersoonVoornaam n : voornamen) {
            if (voornaam.equals(n.getNaam())) {
                return n;
            }
        }

        return null;
    }

    private PersistentPersoonGeslachtsnaamcomponent haalopGeslachtsnaam(final String geslachtsnaam,
            final Set<PersistentPersoonGeslachtsnaamcomponent> geslachtsnamen)
    {
        for (PersistentPersoonGeslachtsnaamcomponent n : geslachtsnamen) {
            if (geslachtsnaam.equals(n.getNaam())) {
                return n;
            }
        }

        return null;
    }

}
