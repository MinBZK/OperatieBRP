/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsVerantwoordelijke;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonIndicatieMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsGemeente;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsVerantwoordelijkheid;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorte;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduiding;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijving;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaam;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import org.junit.Assert;
import org.junit.Test;


public class PersoonRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;

    @PersistenceContext
    private EntityManager     em;

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private GroepHistorieRepository bijhoudingsGemeenteHistorieRepository;

    @Inject
    private PersoonMdlRepository persoonMdlRepository;

    @Inject
    private ReferentieDataMdlRepository referentieDataMdlRepository;

    @Test
    public void testfindByBurgerservicenummerResultaat() {
        PersoonMdl persoon = persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(1, persoon.getId().longValue());
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummer() {
        PersoonMdl persoon = persoonMdlRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("234567891"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(2, persoon.getId().longValue());

        // Test nationaliteiten collectie
        Assert.assertFalse(persoon.getNationaliteiten().isEmpty());
        Assert.assertEquals("0001",
            persoon.getNationaliteiten().iterator().next().getNationaliteit().getNationaliteitCode().getWaarde());

        // Test indicaties collectie
        Assert.assertFalse(persoon.getIndicaties().isEmpty());
        List<SoortIndicatie> list = new ArrayList<SoortIndicatie>();
        for (PersoonIndicatieMdl ind : persoon.getIndicaties()) {
            list.add(ind.getSoort());
        }
        Assert.assertTrue(list.contains(SoortIndicatie.VERSTREKKINGSBEPERKING));
        Assert.assertTrue(list.contains(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT));

        Assert.assertFalse(list.contains(SoortIndicatie.DERDE_HEEFT_GEZAG));
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummerEnTestOuderschap() {
        PersoonMdl persoon = persoonMdlRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("345678912"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(3, persoon.getId().longValue());

        for (BetrokkenheidMdl betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().equals(SoortBetrokkenheid.KIND)) {
                RelatieMdl relatie = betrokkenheid.getRelatie();
                Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING,
                        relatie.getSoort());
                // TODO: testen met ouderlijk betrokkenheid; is opgeslagen in de historie, wordt nu nog niet opgehaald.
//                for (BetrokkenheidMdl relatieBetrokkenheid : relatie.getBetrokkenheden()) {
//                    PersoonMdl betrokkene = relatieBetrokkenheid.getBetrokkene();
//                    if (betrokkene.getId().equals(2L)) {
//                        Assert.assertNull(relatieBetrokkenheid.getBetrokkenheidOuderschap().getDatumAanvangOuderschap());
//                    } else if (betrokkene.getId().equals(8731137L)) {
//                        Assert.assertEquals(Integer.valueOf(17240422), relatieBetrokkenheid.getDatumAanvangOuderschap());
//                    }
//                }
            }
        }
    }

    @Test
    public void testFindByBurgerservicenummerGeenResultaat() {
        Assert.assertNull(persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("")));
    }

    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummer() {
        List<PersoonMdl> personen = persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer(""));
        Assert.assertTrue("personen zou leeg moeten zijn", personen.size() == 0);

        personen = persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertEquals(1, personen.size());

        // BSN met alleen post adres
        personen = persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer("100001002"));
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaVolledigAdres() {
        Plaats plaats = referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0034"));
        List<PersoonMdl> personen =
            persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                    new NaamOpenbareRuimte("Damstraat"),
                    new Huisnummer("1"), new Huisletter("a"), new Huisnummertoevoeging("II"),
                    null, null,
                    plaats);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Long.valueOf(1001), personen.get(0).getId());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaVolledigAdresMetLegeStringsIpvNulls() {
        Plaats plaats = referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0034"));

        List<PersoonMdl> personen =
            persoonMdlRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                    new NaamOpenbareRuimte("Damstraat"),
                    new Huisnummer("1"), new Huisletter("a"), new Huisnummertoevoeging("II"),
                    new LocatieOmschrijving(""),
                    new LocatieTovAdres(""),
                    plaats);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Long.valueOf(1001), personen.get(0).getId());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding() {
        List<PersoonMdl> personen =
            persoonMdlRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummerAanduiding("1581"));
        Assert.assertEquals(2, personen.size());
        List<Long> ids = Arrays.asList(personen.get(0).getId(), personen.get(1).getId());
        Assert.assertTrue(ids.contains(new Long(1001)));
        Assert.assertTrue(ids.contains(new Long(8731137)));
    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummer() {
        List<PersoonMdl> personen =
            persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                    new Postcode("1334NA"),
                    new Huisnummer("73"), new Huisletter("A"), new Huisnummertoevoeging("sous"));
        Assert.assertEquals(1, personen.size());

        Assert.assertEquals(new Burgerservicenummer("123456789"),
                personen.get(0).getIdentificatieNummers().getBurgerServiceNummer());

        personen = persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                new Postcode("1000AA"), new Huisnummer("1"), null, null);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer("234567891"),
                personen.get(0).getIdentificatieNummers().getBurgerServiceNummer());

    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummerMetLegeStringsIpvNulls() {
        List<PersoonMdl> personen =
            persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                    new Postcode("1334NA"),
                    new Huisnummer("73"), new Huisletter("A"), new Huisnummertoevoeging("sous"));
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer("123456789"),
                personen.get(0).getIdentificatieNummers().getBurgerServiceNummer());

        personen = persoonMdlRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                new Postcode("1000AA"),
                new Huisnummer("1"), new Huisletter(""), new Huisnummertoevoeging(""));
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer("234567891"),
                personen.get(0).getIdentificatieNummers().getBurgerServiceNummer());
    }

    @Test
    public void testOpslaanNieuwPersoon() {
        PersistentActie actie = maakActie();
        Persoon persoonLogisch = maakNieuwPersoon();
        PersistentPersoon persoonOperationeel = persoonRepository.opslaanNieuwPersoon(persoonLogisch, 20101212, new Date());
        Long id = persoonOperationeel.getId();

        persoonRepository.werkHistorieBij(persoonOperationeel, persoonLogisch, actie, 20101212);

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersistentPersoon persoon WHERE burgerservicenummer = :burgerservicenummer";

        PersistentPersoon persoon =
            (PersistentPersoon) em.createQuery(persoonsql).setParameter("burgerservicenummer", "bsnnummer")
                    .getSingleResult();

        // Controleer de geretourneerde id
        Assert.assertEquals(persoon.getId(), id);

        // Controleer A-laag
        Assert.assertEquals("bsnnummer", persoon.getBurgerservicenummer());
        Assert.assertEquals("anummer", persoon.getANummer());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.SoortPersoon.INGESCHREVENE, persoon.getSoortPersoon());
        Assert.assertEquals(StatusHistorie.A, persoon.getStatushistorie());
        Assert.assertEquals(20101212, persoon.getInschrijvingDatum().longValue());

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
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.AdellijkeTitel.BARON, pg1.getAdellijkeTitel());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.Predikaat.HOOGHEID, pg1.getPredikaat());
        Assert.assertTrue(",".equals(pg1.getScheidingsteken()));

        PersistentPersoonGeslachtsnaamcomponent pg2 =
            haalopGeslachtsnaam("geslachtsnaamcomp2", persoon.getPersoonGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg2);
        Assert.assertTrue("geslachtsnaamcomp2".equals(pg2.getNaam()));
        Assert.assertTrue("voorvoeg2".equals(pg2.getVoorvoegsel()));
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.AdellijkeTitel.GRAAF, pg2.getAdellijkeTitel());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.Predikaat.JONKHEER, pg2.getPredikaat());
        Assert.assertTrue(";".equals(pg2.getScheidingsteken()));

        Assert.assertNull(persoon.getVoornaam());

        // Geboorte
        Assert.assertEquals(19700101, persoon.getDatumGeboorte().longValue());
        Assert.assertEquals("0034", persoon.getGemeenteGeboorte().getGemeentecode());
        Assert.assertEquals("Almeres", persoon.getWoonplaatsGeboorte().getNaam());
        Assert.assertEquals("Afghanistan", persoon.getLandGeboorte().getNaam());

        // Bijhoudinggemeente
        Assert.assertEquals(20120101, persoon.getBijhoudingsGemeenteDatumInschrijving().longValue());
        Assert.assertEquals("0034", persoon.getBijhoudingsGemeente().getGemeentecode());
        Assert.assertFalse(persoon.getBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig());

        // Geslachtsaanduiding
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.GeslachtsAanduiding.MAN, persoon.getGeslachtsAanduiding());

        // Tijdstip laatste wijziging
        Assert.assertNotNull(persoon.getLaatstGewijzigd());

        // Adressen
        Assert.assertEquals(2, persoon.getAdressen().size());
        boolean adres1Aanwezig = false;
        boolean adres2Aanwezig = false;
        for (PersistentPersoonAdres persoonAdres : persoon.getAdressen()) {
            if ("1".equals(persoonAdres.getHuisnummer())) {
                adres1Aanwezig = true;
            } else if ("2".equals(persoonAdres.getHuisnummer())) {
                adres2Aanwezig = true;
            }
        }
        Assert.assertTrue(adres1Aanwezig);
        Assert.assertTrue(adres2Aanwezig);

        // Controlleer C-laag

        // hisVoornaam
        final String hisPersoonvoornaam =
            "SELECT hisPersoonVoornaam FROM HisPersoonVoornaam hisPersoonVoornaam WHERE persoonVoornaam.persoon.burgerservicenummer = :burgerservicenummer order by naam";

        @SuppressWarnings("unchecked")
        List<HisPersoonVoornaam> hisVoornamen =
            em.createQuery(hisPersoonvoornaam).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonVoornaam hisVoornaam1 = hisVoornamen.get(0);
        // BRPUC00115:
        Assert.assertEquals(20101212, hisVoornaam1.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisVoornaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam1.getDatumTijdVerval());
        Assert.assertEquals("voornaam1", hisVoornaam1.getNaam());
        Assert.assertEquals(hisVoornaam1.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisVoornaam1.getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam1.getActieVerval());

        HisPersoonVoornaam hisVoornaam2 = hisVoornamen.get(1);
        Assert.assertEquals(20101212, hisVoornaam2.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisVoornaam2.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam2.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam2.getDatumTijdVerval());
        Assert.assertEquals("voornaam2", hisVoornaam2.getNaam());
        Assert.assertEquals(hisVoornaam2.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisVoornaam2.getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam2.getActieVerval());

        // hisGeslachtsnaam
        final String hisGeslachtsnaamcomp =
            "SELECT hisGesln FROM HisPersoonGeslachtsnaamcomponent hisGesln WHERE persoonGeslachtsnaamcomponent.persoon.burgerservicenummer = :burgerservicenummer order by naam";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeslachtsnaamcomponent> hisGeslachtsnaamcompenenten =
            em.createQuery(hisGeslachtsnaamcomp).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonGeslachtsnaamcomponent hisGeslachtsNaam1 = hisGeslachtsnaamcompenenten.get(0);
        // BRPUC00115:
        Assert.assertEquals(20101212, hisGeslachtsNaam1.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisGeslachtsNaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam1.getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp1", hisGeslachtsNaam1.getNaam());
        Assert.assertEquals("voorvoeg1", hisGeslachtsNaam1.getVoorvoegsel());
        Assert.assertEquals(hisGeslachtsNaam1.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsNaam1.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsNaam1.getActieVerval());

        HisPersoonGeslachtsnaamcomponent hisGeslachtsNaam2 = hisGeslachtsnaamcompenenten.get(1);
        // BRPUC00115:
        Assert.assertEquals(20101212, hisGeslachtsNaam2.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisGeslachtsNaam2.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam2.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam2.getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp2", hisGeslachtsNaam2.getNaam());
        Assert.assertEquals("voorvoeg2", hisGeslachtsNaam2.getVoorvoegsel());
        Assert.assertEquals(hisGeslachtsNaam2.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsNaam2.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsNaam2.getActieVerval());

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
        Assert.assertEquals(hisGeboorte.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeboorte.getActieVerval());

        // HisBijhoudingGemeente
        final String hisBijhoudingGemeente =
            "SELECT hisBijhoudingGem FROM HisPersoonBijhoudingsGemeente hisBijhoudingGem WHERE persoon.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonBijhoudingsGemeente> hisPersoonBijhoudingsGemeenten =
            em.createQuery(hisBijhoudingGemeente).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonBijhoudingsGemeente hisPersoonBijhoudingsGemeente = hisPersoonBijhoudingsGemeenten.get(0);
        Assert.assertEquals(20120101, hisPersoonBijhoudingsGemeente.getDatumInschrijvingInGemeente().longValue());
        Assert.assertEquals("0034", hisPersoonBijhoudingsGemeente.getBijhoudingsGemeente().getGemeentecode());
        Assert.assertFalse(hisPersoonBijhoudingsGemeente.isIndOnverwDocAanw());
        Assert.assertEquals(20101212, hisPersoonBijhoudingsGemeente.getDatumAanvangGeldigheid().longValue());
        Assert.assertEquals(hisPersoonBijhoudingsGemeente.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonBijhoudingsGemeente.getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsGemeente.getActieVerval());

        // HisPersoonGeslachtsaanduiding
        final String hisGeslachtsaand =
            "SELECT hisGesl FROM HisPersoonGeslachtsaanduiding hisGesl WHERE persoon.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeslachtsaanduiding> hisGeslachtsaanduidingen =
            em.createQuery(hisGeslachtsaand).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        HisPersoonGeslachtsaanduiding hisGeslachtsaanduiding = hisGeslachtsaanduidingen.get(0);
        // BRPUC00115:
        Assert.assertEquals(20101212, hisGeslachtsaanduiding.getDatumAanvangGeldigheid().longValue());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsaanduiding.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumTijdVerval());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.GeslachtsAanduiding.MAN, hisGeslachtsaanduiding.getGeslachtsAanduiding());
        Assert.assertEquals(hisGeslachtsaanduiding.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsaanduiding.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsaanduiding.getActieVerval());

        // HisPersoonInschrijving
        final String hisPersInschr =
            "SELECT hisPersInschr FROM HisPersoonInschrijving hisPersInschr WHERE persoon.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<HisPersoonInschrijving> hisPersoonInschrijvingen =
            em.createQuery(hisPersInschr).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        Assert.assertEquals(20101212, hisPersoonInschrijvingen.get(0).getDatInschr().longValue());
        Assert.assertEquals(hisPersoonInschrijvingen.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonInschrijvingen.get(0).getActieVerval());

        // HisPersoonAdres
        final String hisPersoonAdres =
            "SELECT hisPersoonAdres FROM HisPersoonAdres hisPersoonAdres WHERE hisPersoonAdres.persoonAdres.persoon.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonAdres> hisPersoonAdressen =
            em.createQuery(hisPersoonAdres).setParameter("burgerservicenummer", "bsnnummer").getResultList();
        Assert.assertEquals(2, hisPersoonAdressen.size());
        int aantalVervallen = 0;
        for (HisPersoonAdres hisAdres : hisPersoonAdressen) {
            // BRPUC00115:
            Assert.assertEquals(20101212, hisAdres.getDatumAanvangGeldigheid().longValue());
            Assert.assertNull(hisAdres.getDatumEindeGeldigheid());
            Assert.assertNotNull(hisAdres.getDatumTijdRegistratie());
            if (hisAdres.getDatumTijdVerval() != null) {
                aantalVervallen++;
                Assert.assertEquals(hisAdres.getActieVerval().getId(), actie.getId());
            } else if (hisAdres.getDatumEindeGeldigheid() != null) {
                Assert.assertEquals(hisAdres.getActieAanpassingGeldigheid().getId(), actie.getId());
            } else {
                Assert.assertEquals(hisAdres.getActieInhoud().getId(), actie.getId());
            }
        }
        Assert.assertEquals(1, aantalVervallen);

        // BijhoudingsVerwantwoordelijkheid
        final String hisBijhoudingsVerwantwoordelijkheid =
            "SELECT hisPersBijhVer FROM HisPersoonBijhoudingsVerantwoordelijkheid hisPersBijhVer WHERE hisPersBijhVer.persoon.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonBijhoudingsVerantwoordelijkheid> hisPersoonBijhoudingsVerantwoordelijkheden =
            em.createQuery(hisBijhoudingsVerwantwoordelijkheid).setParameter("burgerservicenummer", "bsnnummer")
                    .getResultList();
        Assert.assertEquals(20101212, hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getDatumAanvangGeldigheid()
                .longValue());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.Verantwoordelijke.COLLEGE, hisPersoonBijhoudingsVerantwoordelijkheden.get(0)
                .getVerwantwoordelijke());
        Assert.assertEquals(hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getActieVerval());

        // PersoonIdentificatienummers
        final String hisPersoonIdentificatienummers =
            "SELECT hisPersIdn FROM HisPersoonIdentificatienummers hisPersIdn WHERE hisPersIdn.persoon.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonIdentificatienummers> hisPersoonIdentificatienummersResultaat =
            em.createQuery(hisPersoonIdentificatienummers).setParameter("burgerservicenummer", "bsnnummer")
                    .getResultList();
        Assert.assertEquals(20101212, hisPersoonIdentificatienummersResultaat.get(0).getDatumAanvangGeldigheid()
                .longValue());
        Assert.assertEquals("anummer", hisPersoonIdentificatienummersResultaat.get(0).getANummer());
        Assert.assertEquals("bsnnummer", hisPersoonIdentificatienummersResultaat.get(0).getBurgerservicenummer());
        Assert.assertEquals(hisPersoonIdentificatienummersResultaat.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getActieVerval());
    }

    @Test
    public void testOpslaanNieuwPersoonSamenGesteldeNaamGroep() {
        final PersistentActie actie = maakActie();
        Persoon persoon = maakNieuwPersoon();
        final PersoonSamengesteldeNaam samengesteldeNaam = new PersoonSamengesteldeNaam();
        samengesteldeNaam.setAdellijkeTitel(nl.bzk.brp.model.gedeeld.AdellijkeTitel.BARON);
        samengesteldeNaam.setGeslachtsnaam("geslachtsnaamafgeleid");
        samengesteldeNaam.setIndAlgoritmischAfgeleid(true);
        samengesteldeNaam.setPredikaat(nl.bzk.brp.model.gedeeld.Predikaat.HOOGHEID);
        samengesteldeNaam.setScheidingsTeken("/");
        samengesteldeNaam.setVoornamen("voornaam1 voornaam2");
        samengesteldeNaam.setVoorvoegsel("der");
        samengesteldeNaam.setIndNamenreeksAlsGeslachtsnaam(false);
        persoon.setSamengesteldenaam(samengesteldeNaam);
        PersistentPersoon persoon1 = persoonRepository.opslaanNieuwPersoon(persoon, 20101212, new Date());
        //sla op historie
        persoonRepository.werkHistorieBij(persoon1, persoon, actie, 20101212);

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersistentPersoon persoon WHERE burgerservicenummer = :burgerservicenummer";

        PersistentPersoon ppersoon =
            (PersistentPersoon) em.createQuery(persoonsql).setParameter("burgerservicenummer", "bsnnummer")
                    .getSingleResult();

        Assert.assertEquals(nl.bzk.brp.model.gedeeld.AdellijkeTitel.BARON, ppersoon.getAdellijkeTitel());
        Assert.assertEquals("geslachtsnaamafgeleid", ppersoon.getGeslachtsNaam());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.Predikaat.HOOGHEID, ppersoon.getPredikaat());
        Assert.assertEquals("/", ppersoon.getScheidingsTeken());
        Assert.assertEquals("voornaam1 voornaam2", ppersoon.getVoornaam());
        Assert.assertEquals("der", ppersoon.getVoorvoegsel());
        Assert.assertTrue(ppersoon.getIndAlgoritmischAfgeleid());
        Assert.assertFalse(ppersoon.getIndReeksAlsGeslachtnaam());

        // Check historie
        final String historieQuery = "SELECT sgn FROM HisPersoonSamengesteldeNaam sgn WHERE sgn.persoon = :persoon";
        List<HisPersoonSamengesteldeNaam> hisPersoonSamengesteldeNaamLijst =
            em.createQuery(historieQuery, HisPersoonSamengesteldeNaam.class).setParameter("persoon", ppersoon)
                    .getResultList();

        Assert.assertEquals(1, hisPersoonSamengesteldeNaamLijst.size());
        HisPersoonSamengesteldeNaam hisPersoonSamengesteldeNaam = hisPersoonSamengesteldeNaamLijst.get(0);

        Assert.assertEquals(nl.bzk.brp.model.gedeeld.AdellijkeTitel.BARON, hisPersoonSamengesteldeNaam.getAdellijkeTitel());
        Assert.assertEquals("geslachtsnaamafgeleid", hisPersoonSamengesteldeNaam.getGeslachtsNaam());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.Predikaat.HOOGHEID, hisPersoonSamengesteldeNaam.getPredikaat());
        Assert.assertEquals("/", hisPersoonSamengesteldeNaam.getScheidingsTeken());
        Assert.assertEquals("voornaam1 voornaam2", hisPersoonSamengesteldeNaam.getVoornamen());
        Assert.assertEquals("der", hisPersoonSamengesteldeNaam.getVoorvoegsel());
        // BRPUC00115:
        Assert.assertEquals(20101212, hisPersoonSamengesteldeNaam.getDatumAanvangGeldigheid().intValue());
        Assert.assertFalse(hisPersoonSamengesteldeNaam.isIndNreeksAlsGeslnaam());
        Assert.assertTrue(hisPersoonSamengesteldeNaam.isIndAlgoritmischAfgeleid());
        Assert.assertEquals(hisPersoonSamengesteldeNaam.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonSamengesteldeNaam.getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonSamengesteldeNaam.getActieVerval());
    }

    @Test
    public void testPersoonOpslaanGeenPlaats() {
        Persoon persoon = maakNieuwPersoon();
        persoon.getIdentificatienummers().setBurgerservicenummer("bsnummer2");
        persoon.getGeboorte().setWoonplaatsGeboorte(null);

        Long id = persoonRepository.opslaanNieuwPersoon(persoon, 20101212, new Date()).getId();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersistentPersoon persoon WHERE burgerservicenummer = :burgerservicenummer";

        PersistentPersoon persoonCheck =
            (PersistentPersoon) em.createQuery(persoonsql).setParameter("burgerservicenummer", "bsnummer2")
                    .getSingleResult();

        Assert.assertNull(persoonCheck.getWoonplaatsGeboorte());
        Assert.assertEquals(persoonCheck.getId(), id);
    }

    @Test(expected = ObjectReedsBestaandExceptie.class)
    public void testOpslaanNieuwPersoonAlBestaand() {
        Persoon persoon = maakNieuwPersoon();
        persoon.getIdentificatienummers().setBurgerservicenummer("123456789");

        persoonRepository.opslaanNieuwPersoon(persoon, 20101212, new Date());
    }

    @Test
    public void testWerkbijBijhoudingsGemeente() {
        PersistentActie actie = maakActie();
        PersistentPersoon persistentPersoon = em.find(PersistentPersoon.class, 1L);
        Assert.assertEquals("Almere", persistentPersoon.getBijhoudingsGemeente().getNaam());
        Assert.assertNull(persistentPersoon.getInschrijvingDatum());

        // Gemeente
        nl.bzk.brp.model.gedeeld.Partij gemeente = new nl.bzk.brp.model.gedeeld.Partij();
        gemeente.setGemeentecode("0363");

        PersoonBijhoudingsGemeente bijhoudingsGemeente = new PersoonBijhoudingsGemeente();
        bijhoudingsGemeente.setDatumInschrijving(20120101);
        bijhoudingsGemeente.setGemeente(gemeente);
        bijhoudingsGemeente.setIndOnverwerktDocumentAanwezig(true);

        persoonRepository.werkbijBijhoudingsGemeente("123456789", bijhoudingsGemeente, 20110101, new Date());

        bijhoudingsGemeenteHistorieRepository.persisteerHistorie(persistentPersoon, actie, 20110101, null);

        persistentPersoon = em.find(PersistentPersoon.class, 1L);
        Assert.assertEquals("Amsterdan", persistentPersoon.getBijhoudingsGemeente().getNaam());
        Assert.assertEquals(20120101, persistentPersoon.getBijhoudingsGemeenteDatumInschrijving().longValue());

        // Controlleer historie
        final String hisbijhoudingsGemeenteSql =
            "SELECT hisBijhGem FROM HisPersoonBijhoudingsGemeente hisBijhGem WHERE hisBijhGem.persoon.id = 1 ORDER BY hisBijhGem.datumAanvangGeldigheid DESC";
        TypedQuery<HisPersoonBijhoudingsGemeente> typedQuery =
            em.createQuery(hisbijhoudingsGemeenteSql, HisPersoonBijhoudingsGemeente.class);

        List<HisPersoonBijhoudingsGemeente> bijhoudingGemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(1, bijhoudingGemeenteHistorie.size());

        Assert.assertEquals(bijhoudingGemeenteHistorie.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(bijhoudingGemeenteHistorie.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(bijhoudingGemeenteHistorie.get(0).getActieVerval());

        bijhoudingsGemeente = new PersoonBijhoudingsGemeente();
        bijhoudingsGemeente.setDatumInschrijving(20120102);
        nl.bzk.brp.model.gedeeld.Partij gemeente2 = new nl.bzk.brp.model.gedeeld.Partij();
        gemeente2.setGemeentecode("0034");
        bijhoudingsGemeente.setGemeente(gemeente2);
        bijhoudingsGemeente.setIndOnverwerktDocumentAanwezig(false);

        persoonRepository.werkbijBijhoudingsGemeente("123456789", bijhoudingsGemeente, 20110110, new Date());

        PersistentActie actie2 = maakActie();

        bijhoudingsGemeenteHistorieRepository.persisteerHistorie(persistentPersoon, actie2, 20110110,  null);

        bijhoudingGemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(3, bijhoudingGemeenteHistorie.size());

        for (HisPersoonBijhoudingsGemeente hisPersoonBijhoudingsGemeente : bijhoudingGemeenteHistorie) {
            if (hisPersoonBijhoudingsGemeente.getDatumTijdVerval() != null) {
                Assert.assertEquals(hisPersoonBijhoudingsGemeente.getActieVerval().getId(), actie2.getId());
            } else {
                if (hisPersoonBijhoudingsGemeente.getDatumEindeGeldigheid() == null
                    && hisPersoonBijhoudingsGemeente.getDatumTijdVerval() == null)
                {
                    Assert.assertEquals(hisPersoonBijhoudingsGemeente.getActieInhoud().getId(), actie2.getId());
                } else {
                    Assert.assertEquals(hisPersoonBijhoudingsGemeente.getActieAanpassingGeldigheid().getId(),
                            actie2.getId());
                }
            }
        }
    }

    private Persoon maakNieuwPersoon() {
        // PersoonIdentiteit
        PersoonIdentiteit identiteit = new PersoonIdentiteit();
        identiteit.setSoort(nl.bzk.brp.model.gedeeld.SoortPersoon.INGESCHREVENE);

        // Identificatie nummer
        PersoonIdentificatienummers persoonIdentificatienummers = new PersoonIdentificatienummers();
        persoonIdentificatienummers.setAdministratienummer("anummer");
        persoonIdentificatienummers.setBurgerservicenummer("bsnnummer");

        // Gemeente
        nl.bzk.brp.model.gedeeld.Partij gemeente = new nl.bzk.brp.model.gedeeld.Partij();
        gemeente.setGemeentecode("0034");

        // Land
        nl.bzk.brp.model.gedeeld.Land land = new nl.bzk.brp.model.gedeeld.Land();
        land.setLandcode("6023");

        // nl.bzk.brp.model.gedeeld.Plaats
        nl.bzk.brp.model.gedeeld.Plaats plaats = new nl.bzk.brp.model.gedeeld.Plaats();
        plaats.setWoonplaatscode("0034");

        // Persoon geboorte
        PersoonGeboorte persoonGeboorte = new PersoonGeboorte();
        persoonGeboorte.setDatumGeboorte(19700101);
        persoonGeboorte.setGemeenteGeboorte(gemeente);
        persoonGeboorte.setLandGeboorte(land);
        persoonGeboorte.setWoonplaatsGeboorte(plaats);

        // Geslachtsaanduiding
        PersoonGeslachtsAanduiding persoonGeslachtsAanduiding = new PersoonGeslachtsAanduiding();
        persoonGeslachtsAanduiding.setGeslachtsAanduiding(nl.bzk.brp.model.gedeeld.GeslachtsAanduiding.MAN);

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
        component1.setAdellijkeTitel(nl.bzk.brp.model.gedeeld.AdellijkeTitel.BARON);
        component1.setPredikaat(nl.bzk.brp.model.gedeeld.Predikaat.HOOGHEID);
        component1.setScheidingsTeken(",");
        persoon.getGeslachtsnaamcomponenten().add(component1);

        PersoonGeslachtsnaamcomponent component2 = new PersoonGeslachtsnaamcomponent();
        component2.setVolgnummer(2);
        component2.setNaam("geslachtsnaamcomp2");
        component2.setVoorvoegsel("voorvoeg2");
        component2.setAdellijkeTitel(nl.bzk.brp.model.gedeeld.AdellijkeTitel.GRAAF);
        component2.setPredikaat(nl.bzk.brp.model.gedeeld.Predikaat.JONKHEER);
        component2.setScheidingsTeken(";");
        persoon.getGeslachtsnaamcomponenten().add(component2);

        persoon.setGeboorte(persoonGeboorte);
        persoon.setPersoonGeslachtsAanduiding(persoonGeslachtsAanduiding);

        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(maakNieuwAdres(persoon, nl.bzk.brp.model.gedeeld.FunctieAdres.WOONADRES, "1000AB", "1"));
        adressen.add(maakNieuwAdres(persoon, nl.bzk.brp.model.gedeeld.FunctieAdres.WOONADRES, "1000AB", "2"));
        persoon.setAdressen(adressen);

        PersoonBijhoudingsGemeente bijhoudingsGemeente = new PersoonBijhoudingsGemeente();
        bijhoudingsGemeente.setDatumInschrijving(20120101);
        bijhoudingsGemeente.setGemeente(gemeente);
        bijhoudingsGemeente.setIndOnverwerktDocumentAanwezig(false);
        persoon.setBijhoudingGemeente(bijhoudingsGemeente);

        persoon.setBijhoudingVerantwoordelijke(new PersoonBijhoudingsVerantwoordelijke());
        persoon.getBijhoudingVerantwoordelijke().setVerantwoordelijke(nl.bzk.brp.model.gedeeld.Verantwoordelijke.COLLEGE);

        return persoon;
    }

    private PersoonAdres maakNieuwAdres(final Persoon persoon, final nl.bzk.brp.model.gedeeld.FunctieAdres functieAdres, final String postcode,
            final String huisnummer)
    {
        PersoonAdres adres = new PersoonAdres();
        adres.setPersoon(persoon);
        adres.setDatumAanvangAdreshouding(20120229);
        adres.setNaamOpenbareRuimte("Damweg");
        adres.setAfgekorteNaamOpenbareRuimte("Damwg");
        adres.setHuisnummer(huisnummer);
        adres.setHuisletter("b");
        adres.setHuisnummertoevoeging("III");
        adres.setPostcode(postcode);
        adres.setSoort(functieAdres);
        adres.setWoonplaats(em.find(nl.bzk.brp.model.gedeeld.Plaats.class, 1));
        adres.setAdresseerbaarObject("1753");
        adres.setIdentificatiecodeNummeraanduiding("1815");
        adres.setLand(em.find(nl.bzk.brp.model.gedeeld.Land.class, 1));
        adres.setPersoonAdresStatusHis("A");
        adres.setGemeente(em.find(nl.bzk.brp.model.gedeeld.Partij.class, 1));
        return adres;
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

    private PersistentActie maakActie() {
        PersistentActie actie = new PersistentActie();
        actie.setTijdstipRegistratie(new Date(System.currentTimeMillis() - 1));
        actie.setSoort(nl.bzk.brp.model.gedeeld.SoortActie.REGISTRATIE_NATIONALITEIT);
        actie.setPartij(
                partijRepository.findOne(4)
        );
        em.persist(actie);
        return actie;
    }

}
