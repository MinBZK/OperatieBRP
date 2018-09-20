/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.Timestamp;
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

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.ANummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.AanduidingBijHuisnummer;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.attribuuttype.Ontleningstoelichting;
import nl.bzk.brp.model.attribuuttype.Woonplaatscode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.PersoonEuVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsgemeenteGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsaanduidingGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAanschrijvingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAanschrijvingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresStandaardHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsgemeenteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsverantwoordelijkheidHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamcomponentStandaardHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonInschrijvingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOpschortingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonVoornaamStandaardHisModel;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.logisch.Verdrag;
import nl.bzk.brp.model.objecttype.logisch.basis.ActieBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonGeslachtsnaamcomponentBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonNationaliteitBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonVoornaamBasis;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNlNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNlNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verblijfsrecht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager     em;

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Test
    public void testfindByBurgerservicenummerResultaat() {
        PersoonModel persoon = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(1, persoon.getId().intValue());
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummer() {
        PersoonModel persoon = persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("234567891"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(new Integer(2), persoon.getId());

        // Test nationaliteiten collectie
        Assert.assertFalse(persoon.getNationaliteiten().isEmpty());
        Assert.assertEquals("0001", persoon.getNationaliteiten().iterator().next()
                                           .getNationaliteit().getNationaliteitcode().toString());

        // Test indicaties collectie
        Assert.assertFalse(persoon.getIndicaties().isEmpty());
        List<SoortIndicatie> list = new ArrayList<SoortIndicatie>();
        for (PersoonIndicatieModel ind : persoon.getIndicaties()) {
            list.add(ind.getGegevens().getSoort());
        }
        Assert.assertTrue(list.contains(SoortIndicatie.VERSTREKKINGSBEPERKING));
        Assert.assertTrue(list.contains(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT));

        Assert.assertFalse(list.contains(SoortIndicatie.DERDE_HEEFT_GEZAG));
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummerEnTestOuderschap() {
        PersoonModel persoon = persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("345678912"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(3, persoon.getId().longValue());

        for (BetrokkenheidModel betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().equals(SoortBetrokkenheid.KIND)) {
                RelatieModel relatie = betrokkenheid.getRelatie();
                Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING,
                        relatie.getSoort());
                // TODO: testen met ouderlijk betrokkenheid; is opgeslagen in de historie, wordt nu nog niet opgehaald.
//                for (Betrokkenheid relatieBetrokkenheid : relatie.getBetrokkenheden()) {
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
        Assert.assertNull(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("")));
    }

    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummer() {
        List<PersoonModel> personen = persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer(""));
        Assert.assertTrue("personen zou leeg moeten zijn", personen.size() == 0);

        personen = persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertEquals(1, personen.size());

        // BSN met alleen post adres
        personen = persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer("100001002"));
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaVolledigAdres() {
        Plaats plaats = referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode((short) 34));
        Partij gemeente = referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));
        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                    new NaamOpenbareRuimte("Damstraat"),
                    new Huisnummer(1), new Huisletter("a"), new Huisnummertoevoeging("II"),
                    plaats, gemeente);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(1001), personen.get(0).getId());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaVolledigAdresMetLegeStringsIpvNulls() {
        Plaats plaats = referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode((short) 34));
        Partij gemeente = referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));

        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                    new NaamOpenbareRuimte("Damstraat"),
                    new Huisnummer(1), new Huisletter("a"), new Huisnummertoevoeging("II"),
                    plaats, gemeente);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(1001), personen.get(0).getId());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding() {
        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummeraanduiding("1581"));
        Assert.assertEquals(2, personen.size());
        List<Integer> ids = Arrays.asList(personen.get(0).getId(), personen.get(1).getId());
        Assert.assertTrue(ids.contains(1001));
        Assert.assertTrue(ids.contains(8731137));
    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummer() {
        List<PersoonModel> personen =
            persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                    new Postcode("1334NA"),
                    new Huisnummer(73), new Huisletter("A"), new Huisnummertoevoeging("sous"));
        Assert.assertEquals(1, personen.size());

        Assert.assertEquals(new Burgerservicenummer("123456789"),
                personen.get(0).getIdentificatienummers().getBurgerservicenummer());

        personen = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                new Postcode("1000AA"), new Huisnummer(1), null, null);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer("234567891"),
                personen.get(0).getIdentificatienummers().getBurgerservicenummer());

    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummerMetLegeStringsIpvNulls() {
        List<PersoonModel> personen =
            persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                    new Postcode("1334NA"),
                    new Huisnummer(73), new Huisletter("A"), new Huisnummertoevoeging("sous"));
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer("123456789"),
                personen.get(0).getIdentificatienummers().getBurgerservicenummer());

        personen = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                new Postcode("1000AA"),
                new Huisnummer(1), new Huisletter(""), new Huisnummertoevoeging(""));
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer("234567891"),
                personen.get(0).getIdentificatienummers().getBurgerservicenummer());
    }

    @Test
    public void testHaalPersonenOpMetAdresHistorie() {
        // new Burgerservicenummer("100001001")
        // alleen voor code coverage, gebruik nu haalPersoonOpMetAdres(id);
        PersoonModel persoon = persoonRepository.haalPersoonMetAdres(new Integer(1001));
        Assert.assertNotNull(persoon);

        Assert.assertEquals(1, persoon.getAdressen().size());
        PersoonAdresModel adres = persoon.getAdressen().iterator().next();
        Assert.assertEquals(null, adres.getHistorie());
        persoonRepository.vulaanAdresMetHistorie(persoon, false);
        adres = persoon.getAdressen().iterator().next();
        Assert.assertNotNull(adres.getHistorie());
        Assert.assertEquals(2, adres.getHistorie().size());
        persoonRepository.vulaanAdresMetHistorie(persoon, true);
        Assert.assertNotNull(adres.getHistorie());
        Assert.assertEquals(3, adres.getHistorie().size());

    }

    @Test
    public void testOpslaanNieuwPersoon() {
        ActieModel actie = maakActie(SoortActie.AANGIFTE_GEBOORTE);
        Datum datumAanvangGeldigheid = new Datum(20101212);

        PersoonModel persoonOperationeel = maakNieuwPersoon();
        PersoonModel persoonNieuw = persoonRepository.opslaanNieuwPersoon(persoonOperationeel, actie,
                datumAanvangGeldigheid);
        Integer id = persoonNieuw.getId();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        final Burgerservicenummer bsn = new Burgerservicenummer("111456789");
        PersoonModel persoon =
            (PersoonModel) em.createQuery(persoonsql).setParameter("burgerservicenummer", bsn)
                    .getSingleResult();

        // Controleer de geretourneerde id
        Assert.assertEquals(persoon.getId(), id);

        // Controleer A-laag
        Assert.assertEquals(bsn, persoon.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals(new ANummer(Long.valueOf(1114567890L)),
            persoon.getIdentificatienummers().getAdministratienummer());
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());
//        Assert.assertEquals(StatusHistorie.A, persoon.getInschrijving().getStatusHistorie());
        Assert.assertEquals(new Integer(20120205), persoon.getInschrijving().getDatumInschrijving().getWaarde());

        // Voornaam
        Assert.assertEquals(2, persoon.getPersoonVoornaam().size());
        Assert.assertNotNull(haalopVoornaam("voornaam1", persoon.getPersoonVoornaam()));
        Assert.assertNotNull(haalopVoornaam("voornaam2", persoon.getPersoonVoornaam()));

        // Geslachtsnaam
        PersoonGeslachtsnaamcomponentModel pg1 =
            haalopGeslachtsnaam("geslachtsnaamcomp1", persoon.getGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg1);
        Assert.assertTrue("geslachtsnaamcomp1".equals(pg1.getGegevens().getNaam().getWaarde()));
        Assert.assertTrue("voorvoeg1".equals(pg1.getGegevens().getVoorvoegsel().getWaarde()));
        Assert.assertEquals("B", pg1.getGegevens().getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("H", pg1.getGegevens().getPredikaat().getCode().getWaarde());
        Assert.assertTrue(",".equals(pg1.getGegevens().getScheidingsteken().getWaarde()));

        PersoonGeslachtsnaamcomponentModel pg2 =
            haalopGeslachtsnaam("geslachtsnaamcomp2", persoon.getGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg2);
        Assert.assertTrue("geslachtsnaamcomp2".equals(pg2.getGegevens().getNaam().getWaarde()));
        Assert.assertTrue("voorvoeg2".equals(pg2.getGegevens().getVoorvoegsel().getWaarde()));
        Assert.assertEquals("G", pg2.getGegevens().getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("J", pg2.getGegevens().getPredikaat().getCode().getWaarde());
        Assert.assertTrue(";".equals(pg2.getGegevens().getScheidingsteken().getWaarde()));

        // Geboorte
        Assert.assertEquals(new Integer(19700101), persoon.getGeboorte().getDatumGeboorte().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 34), persoon.getGeboorte().getGemeenteGeboorte().getGemeentecode().getWaarde());
        Assert.assertEquals("Almeres", persoon.getGeboorte().getWoonplaatsGeboorte().getNaam().getWaarde());
        Assert.assertEquals("Afghanistan", persoon.getGeboorte().getLandGeboorte().getNaam().getWaarde());

        // Bijhoudinggemeente
        Assert.assertEquals(new Integer(20120101), persoon.getBijhoudingsgemeente().getDatumInschrijvingInGemeente().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 34), persoon.getBijhoudingsgemeente().getBijhoudingsgemeente().getGemeentecode().getWaarde());
        Assert.assertEquals(JaNee.Nee, persoon.getBijhoudingsgemeente().getIndOnverwerktDocumentAanwezig());

        // Opschorting
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoon.getOpschorting().getRedenOpschorting());

        // Geslachtsaanduiding
        Assert.assertEquals(Geslachtsaanduiding.MAN, persoon.getGeslachtsaanduiding().getGeslachtsaanduiding());

        // Tijdstip laatste wijziging
        Assert.assertNotNull(persoon.getAfgeleidAdministratief().getTijdstipLaatsteWijziging());

        // Adressen
        Assert.assertEquals(2, persoon.getAdressen().size());
        boolean adres1Aanwezig = false;
        boolean adres2Aanwezig = false;
        for (PersoonAdresModel persoonAdres : persoon.getAdressen()) {
            if (1 == persoonAdres.getGegevens().getHuisnummer().getWaarde().intValue()) {
                adres1Aanwezig = true;
            } else if (2 == persoonAdres.getGegevens().getHuisnummer().getWaarde().intValue()) {
                adres2Aanwezig = true;
            }
        }
        Assert.assertTrue(adres1Aanwezig);
        Assert.assertTrue(adres2Aanwezig);

        // De normale code, wordt de nationaliteit nooit meegeleverd, maar opgevoerd in de 2e actie
        // (Registratie nationaliteit).
        // maar for the sake of completeness, doen we hier WEL.
        Assert.assertEquals(1, persoon.getNationaliteiten().size());
        PersoonNationaliteitModel nat1 = persoon.getNationaliteiten().iterator().next();
        Assert.assertEquals(BrpConstanten.NL_NATIONALITEIT_CODE, nat1.getNationaliteit().getNationaliteitcode());
        Assert.assertEquals(new Short((short) 17),
                nat1.getGegevens().getRedenVerkregenNlNationaliteit().getCode().getWaarde());


        // Controlleer C-laag

        // hisVoornaam
        final String hisPersoonvoornaam =
            "SELECT hisPersoonVoornaam FROM PersoonVoornaamStandaardHisModel hisPersoonVoornaam WHERE "
            + " persoonVoornaam.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer order by voornaam";

        @SuppressWarnings("unchecked")
        List<PersoonVoornaamStandaardHisModel> hisVoornamen =
            em.createQuery(hisPersoonvoornaam).setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
              .getResultList();
        PersoonVoornaamStandaardHisModel hisVoornaam1 = hisVoornamen.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisVoornaam1.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisVoornaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam1.getDatumTijdVerval());
        Assert.assertEquals("voornaam1", hisVoornaam1.getVoornaam().getWaarde());
        Assert.assertEquals(hisVoornaam1.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisVoornaam1.getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam1.getActieVerval());

        PersoonVoornaamStandaardHisModel hisVoornaam2 = hisVoornamen.get(1);
        Assert.assertEquals(new Integer(20101212), hisVoornaam2.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisVoornaam2.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam2.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam2.getDatumTijdVerval());
        Assert.assertEquals("voornaam2", hisVoornaam2.getVoornaam().getWaarde());
        Assert.assertEquals(hisVoornaam2.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisVoornaam2.getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam2.getActieVerval());

        // hisGeslachtsnaam .
        final String hisGeslachtsnaamcomp =
            "SELECT hisGesln FROM PersoonGeslachtsnaamcomponentStandaardHisModel hisGesln WHERE "
            + " persoonGeslachtsnaamcomponent.persoon.identificatienummers.burgerservicenummer = "
            + " :burgerservicenummer order by geslachtsnaamcomponent";

        @SuppressWarnings("unchecked")
        List<PersoonGeslachtsnaamcomponentStandaardHisModel> hisGeslachtsnaamcompenenten =
            em.createQuery(hisGeslachtsnaamcomp)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        PersoonGeslachtsnaamcomponentStandaardHisModel hisGeslachtsNaam1 = hisGeslachtsnaamcompenenten.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisGeslachtsNaam1.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsNaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam1.getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp1", hisGeslachtsNaam1.getNaam().getWaarde());
        Assert.assertEquals("voorvoeg1", hisGeslachtsNaam1.getVoorvoegsel().getWaarde());
        Assert.assertEquals(hisGeslachtsNaam1.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsNaam1.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsNaam1.getActieVerval());

        PersoonGeslachtsnaamcomponentStandaardHisModel hisGeslachtsNaam2 = hisGeslachtsnaamcompenenten.get(1);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisGeslachtsNaam2.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsNaam2.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam2.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam2.getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp2", hisGeslachtsNaam2.getNaam().getWaarde());
        Assert.assertEquals("voorvoeg2", hisGeslachtsNaam2.getVoorvoegsel().getWaarde());
        Assert.assertEquals(hisGeslachtsNaam2.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsNaam2.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsNaam2.getActieVerval());

        // HisPersoonGeboorte
        final String hisPersoonGeboorte =
            "SELECT hisGeboorte FROM PersoonGeboorteHisModel hisGeboorte WHERE "
            + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<PersoonGeboorteHisModel> hisPersoonGeboorten =
            em.createQuery(hisPersoonGeboorte)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        PersoonGeboorteHisModel hisGeboorte = hisPersoonGeboorten.get(0);
        Assert.assertNotNull(hisGeboorte.getDatumTijdRegistratie());
        Assert.assertNull(hisGeboorte.getDatumTijdVerval());
        Assert.assertEquals("0034", hisGeboorte.getGemeenteGeboorte().getGemeentecode().toString());
        Assert.assertEquals("0034", hisGeboorte.getWoonplaatsGeboorte().getCode().toString());
        Assert.assertEquals(hisGeboorte.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeboorte.getActieVerval());

        // HisBijhoudingGemeente

        final String hisBijhoudingGemeente =
            "SELECT hisBijhoudingGem FROM PersoonBijhoudingsgemeenteHisModel hisBijhoudingGem WHERE "
            + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonBijhoudingsgemeenteHisModel> hisPersoonBijhoudingsgemeenten =
            em.createQuery(hisBijhoudingGemeente)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        PersoonBijhoudingsgemeenteHisModel hisPersoonBijhoudingsgemeente = hisPersoonBijhoudingsgemeenten.get(0);
        Assert.assertEquals(new Integer(20120101), hisPersoonBijhoudingsgemeente
            .getDatumInschrijvingInGemeente().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 34),
            hisPersoonBijhoudingsgemeente.getBijhoudingsgemeente().getGemeentecode().getWaarde());
        Assert.assertEquals(JaNee.Nee, hisPersoonBijhoudingsgemeente.getIndOnverwerktDocumentAanwezig());
        Assert.assertEquals(new Integer(20101212),
            hisPersoonBijhoudingsgemeente.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(hisPersoonBijhoudingsgemeente.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonBijhoudingsgemeente.getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsgemeente.getActieVerval());

        // HisPersoonGeslachtsaanduiding
        final String hisGeslachtsaand =
            "SELECT hisGesl FROM PersoonGeslachtsaanduidingHisModel hisGesl WHERE "
            + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonGeslachtsaanduidingHisModel> hisGeslachtsaanduidingen =
            em.createQuery(hisGeslachtsaand)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        PersoonGeslachtsaanduidingHisModel hisGeslachtsaanduiding = hisGeslachtsaanduidingen.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisGeslachtsaanduiding.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsaanduiding.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumTijdVerval());
        Assert.assertEquals(Geslachtsaanduiding.MAN, hisGeslachtsaanduiding.getGeslachtsaanduiding());
        Assert.assertEquals(hisGeslachtsaanduiding.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsaanduiding.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsaanduiding.getActieVerval());

        // HisPersoonInschrijving
        final String hisPersInschr =
            "SELECT hisPersInschr FROM PersoonInschrijvingHisModel hisPersInschr WHERE "
            + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<PersoonInschrijvingHisModel> hisPersoonInschrijvingen =
            em.createQuery(hisPersInschr)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        Assert.assertEquals(new Integer(20120205), hisPersoonInschrijvingen.get(0).getDatumInschrijving().getWaarde());
        Assert.assertEquals(hisPersoonInschrijvingen.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonInschrijvingen.get(0).getActieVerval());


        // HisOpschorting
        final String hisPersOpschorting =
                "SELECT hisPersOpschor FROM PersoonOpschortingHisModel hisPersOpschor WHERE "
                + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        List<PersoonOpschortingHisModel> hisPersoonOpschortingen =
                em.createQuery(hisPersOpschorting, PersoonOpschortingHisModel.class)
                    .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                    .getResultList();
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, hisPersoonOpschortingen.get(0).getRedenOpschorting());
        Assert.assertEquals(new Integer(20101212), hisPersoonOpschortingen.get(0).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(actie.getId(), hisPersoonOpschortingen.get(0).getActieInhoud().getId());

        // HisPersoonAdres + gesorteerd op Id
        final String hisPersoonAdres =
            "SELECT hisPersoonAdres FROM PersoonAdresStandaardHisModel hisPersoonAdres WHERE "
            + " hisPersoonAdres.persoonAdres.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer"
            + " ORDER by hisPersoonAdres.historie.datumTijdVerval DESC, "
                    + " hisPersoonAdres.historie.datumAanvangGeldigheid.waarde DESC, hisPersoonAdres.id ASC";
        @SuppressWarnings("unchecked")
        List<PersoonAdresStandaardHisModel> hisPersoonAdressen =
            em.createQuery(hisPersoonAdres)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        Assert.assertEquals(2, hisPersoonAdressen.size());
        int aantalVervallen = 0;
        for (PersoonAdresStandaardHisModel hisAdres : hisPersoonAdressen) {
            // BRPUC00115:
            Assert.assertEquals(new Integer(20101212), hisAdres.getDatumAanvangGeldigheid().getWaarde());
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
        // ze zijn beide ingeschoten met deze actie, beide zelfde aanvangGeldigheid, zelfde tijdstip registratie.
        // dus zijn geen enkel vervallen.
        Assert.assertEquals(0, aantalVervallen);

        // BijhoudingsVerwantwoordelijkheid
        final String hisBijhoudingsverwantwoordelijkheid =
            "SELECT hisPersBijhVer FROM PersoonBijhoudingsverantwoordelijkheidHisModel hisPersBijhVer WHERE "
            + " hisPersBijhVer.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonBijhoudingsverantwoordelijkheidHisModel> hisPersoonBijhoudingsverantwoordelijkheden =
            em.createQuery(hisBijhoudingsverwantwoordelijkheid)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        Assert.assertEquals(new Integer(20101212), hisPersoonBijhoudingsverantwoordelijkheden.get(0)

                                                                                             .getDatumAanvangGeldigheid()
                                                                                             .getWaarde());
        Assert.assertEquals(Verantwoordelijke.COLLEGE, hisPersoonBijhoudingsverantwoordelijkheden.get(0)
                                                                                                 .getVerantwoordelijke());
        Assert.assertEquals(hisPersoonBijhoudingsverantwoordelijkheden.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonBijhoudingsverantwoordelijkheden.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsverantwoordelijkheden.get(0).getActieVerval());

        // PersoonIdentificatienummers

        final String hisPersoonIdentificatienummers =
            "SELECT hisPersIdn FROM PersoonIdentificatienummersHisModel hisPersIdn WHERE "
            + " hisPersIdn.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonIdentificatienummersHisModel> hisPersoonIdentificatienummersResultaat =
            em.createQuery(hisPersoonIdentificatienummers)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getResultList();
        Assert.assertEquals(new Integer(20101212), hisPersoonIdentificatienummersResultaat.get(0)
                .getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(Long.valueOf(1114567890), hisPersoonIdentificatienummersResultaat.get(0)
                .getAdministratienummer().getWaarde());
        Assert.assertEquals("111456789", hisPersoonIdentificatienummersResultaat.get(0)
                .getBurgerservicenummer().getWaarde());
        Assert.assertEquals(hisPersoonIdentificatienummersResultaat.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getActieVerval());
    }

    @Test
    public void testOpslaanNieuwPersoonSamenGesteldeNaamGroep() {
        final ActieModel actie = maakActie(SoortActie.HUWELIJK);
        PersoonModel persoon = maakNieuwPersoon();
        @SuppressWarnings("serial")
        PersoonSamengesteldeNaamGroepModel groep = new PersoonSamengesteldeNaamGroepModel(
                new PersoonSamengesteldeNaamGroepBasis() {
                    @Override
                    public Voorvoegsel getVoorvoegsel() {
                        return new Voorvoegsel("der");
                    }

                    @Override
                    public Voornaam getVoornamen() {
                        return new Voornaam("voornaam1 voornaam2");
                    }

                    @Override
                    public Scheidingsteken getScheidingsteken() {
                        return new Scheidingsteken("/");
                    }

                    @Override
                    public Predikaat getPredikaat() {
                        return referentieDataRepository.vindPredikaatOpCode(new PredikaatCode("H"));
                    }

                    @Override
                    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
                        return JaNee.Nee;
                    }

                    @Override
                    public JaNee getIndAlgorithmischAfgeleid() {
                        return JaNee.Ja;
                    }

                    @Override
                    public Geslachtsnaamcomponent getGeslachtsnaam() {
                        return new Geslachtsnaamcomponent("geslachtsnaamafgeleid");
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        return referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("B"));
                    }
                });

        ReflectionTestUtils.setField(persoon, "samengesteldeNaam", groep);
        PersoonModel persoon1 = persoonRepository.opslaanNieuwPersoon(persoon, actie,
                new Datum(20101212));

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE "
            + " identificatienummers.burgerservicenummer = :burgerservicenummer";
        PersoonModel ppersoon =
            (PersoonModel) em.createQuery(persoonsql)
                .setParameter("burgerservicenummer", new Burgerservicenummer("111456789"))
                .getSingleResult();

        Assert.assertEquals("B", ppersoon.getSamengesteldeNaam().getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("geslachtsnaamafgeleid", ppersoon.getSamengesteldeNaam().getGeslachtsnaam().getWaarde());
        Assert.assertEquals("H", ppersoon.getSamengesteldeNaam().getPredikaat().getCode().getWaarde());
        Assert.assertEquals("/", ppersoon.getSamengesteldeNaam().getScheidingsteken().getWaarde());
        Assert.assertEquals("voornaam1 voornaam2", ppersoon.getSamengesteldeNaam().getVoornamen().getWaarde());
        Assert.assertEquals("der", ppersoon.getSamengesteldeNaam().getVoorvoegsel().getWaarde());
        Assert.assertEquals(JaNee.Ja, ppersoon.getSamengesteldeNaam().getIndAlgorithmischAfgeleid());
        Assert.assertEquals(JaNee.Nee, ppersoon.getSamengesteldeNaam().getIndNamenreeksAlsGeslachtsNaam());

        // Check historie

        final String historieQuery = "SELECT sgn FROM PersoonSamengesteldeNaamHisModel sgn WHERE "
             + " sgn.persoon = :persoon";
        List<PersoonSamengesteldeNaamHisModel> hisPersoonSamengesteldeNaamLijst =
            em.createQuery(historieQuery, PersoonSamengesteldeNaamHisModel.class).setParameter("persoon", ppersoon)
                    .getResultList();

        Assert.assertEquals(1, hisPersoonSamengesteldeNaamLijst.size());
        PersoonSamengesteldeNaamHisModel hisPersoonSamengesteldeNaam = hisPersoonSamengesteldeNaamLijst.get(0);

        Assert.assertEquals("B", hisPersoonSamengesteldeNaam.getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("geslachtsnaamafgeleid", hisPersoonSamengesteldeNaam.getGeslachtsnaam().getWaarde());
        Assert.assertEquals("H", hisPersoonSamengesteldeNaam.getPredikaat().getCode().getWaarde());
        Assert.assertEquals("/", hisPersoonSamengesteldeNaam.getScheidingsteken().getWaarde());
        Assert.assertEquals("voornaam1 voornaam2", hisPersoonSamengesteldeNaam.getVoornamen().getWaarde());
        Assert.assertEquals("der", hisPersoonSamengesteldeNaam.getVoorvoegsel().getWaarde());
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisPersoonSamengesteldeNaam.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(JaNee.Ja, hisPersoonSamengesteldeNaam.getIndAlgorithmischAfgeleid());
        Assert.assertEquals(JaNee.Nee, hisPersoonSamengesteldeNaam.getIndNamenreeksAlsGeslachtsNaam());
        Assert.assertEquals(hisPersoonSamengesteldeNaam.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonSamengesteldeNaam.getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonSamengesteldeNaam.getActieVerval());
    }

    @Test
    public void testPersoonOpslaanGeenPlaats() {
        final ActieModel actie = maakActie(SoortActie.VERHUIZING);
        PersoonModel persoon = maakNieuwPersoon();
        ReflectionTestUtils.setField(persoon.getIdentificatienummers(), "burgerservicenummer",
                new Burgerservicenummer("222456789"));
        ReflectionTestUtils.setField(persoon.getGeboorte(), "woonplaatsGeboorte", null);

        Integer id = persoonRepository.opslaanNieuwPersoon(persoon, actie,
                new Datum(20101212)).getId();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE "
            + " identificatienummers.burgerservicenummer = :burgerservicenummer";

        PersoonModel persoonCheck =
            (PersoonModel) em.createQuery(persoonsql)
                .setParameter("burgerservicenummer", new Burgerservicenummer("222456789"))
                .getSingleResult();

        Assert.assertEquals(id, persoonCheck.getId());
        Assert.assertEquals(null, persoonCheck.getGeboorte().getWoonplaatsGeboorte());
    }

    @Test(expected = ObjectReedsBestaandExceptie.class)
    public void testOpslaanNieuwPersoonAlBestaand() {
        final ActieModel actie = maakActie(SoortActie.AANGIFTE_GEBOORTE);
        PersoonModel persoon = maakNieuwPersoon();
        ReflectionTestUtils.setField(persoon.getIdentificatienummers(), "burgerservicenummer",
                new Burgerservicenummer("123456789"));
        persoonRepository.opslaanNieuwPersoon(persoon, actie,
                new Datum(20101212));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testWerkbijBijhoudingsgemeenteMetOnbekendeBsn() throws Throwable {
        ActieModel actie = maakActie(SoortActie.VERHUIZING);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        final Burgerservicenummer bsn = new Burgerservicenummer("999999999");

        // Gemeente
        PersoonBijhoudingsgemeenteGroepModel bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(
                new PersoonBijhoudingsgemeenteGroepBasis() {
                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Ja;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120708);
                    }

                    @Override
                    public Partij getBijhoudingsgemeente() {
                        return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 363));
                    }
                });

        try {
            persoonRepository.werkbijBijhoudingsgemeente(bsn, bijhoudingsgemeente, actie,
                    new Datum(20120708));

        } catch (InvalidDataAccessApiUsageException idae) {
            throw idae.getCause();
        }

    }

    @SuppressWarnings("serial")
    @Test
    public void testWerkbijBijhoudingsgemeente() {
        ActieModel actie = maakActie(SoortActie.VERHUIZING);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        final Burgerservicenummer bsn = persoon.getIdentificatienummers().getBurgerservicenummer();

        // Gemeente
        PersoonBijhoudingsgemeenteGroepModel bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(
                new PersoonBijhoudingsgemeenteGroepBasis() {
                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Ja;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120708);
                    }

                    @Override
                    public Partij getBijhoudingsgemeente() {
                        return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 363));
                    }
                });

        persoonRepository.werkbijBijhoudingsgemeente(bsn, bijhoudingsgemeente, actie,
            new Datum(20120708));

        persoon = em.find(PersoonModel.class, 1);
        Assert.assertEquals("Amsterdan", persoon.getBijhoudingsgemeente()
                .getBijhoudingsgemeente().getNaam().getWaarde());
        Assert.assertEquals(new Integer(20120708), persoon.getBijhoudingsgemeente()
                .getDatumInschrijvingInGemeente().getWaarde());

        // Controlleer historie

        final String hisBijhoudingsgemeenteSql =
            "SELECT hisBijhGem FROM PersoonBijhoudingsgemeenteHisModel hisBijhGem WHERE "
            + " hisBijhGem.persoon.id = 1 ORDER BY hisBijhGem.historie.datumAanvangGeldigheid DESC";

        TypedQuery<PersoonBijhoudingsgemeenteHisModel> typedQuery =
            em.createQuery(hisBijhoudingsgemeenteSql, PersoonBijhoudingsgemeenteHisModel.class);

        List<PersoonBijhoudingsgemeenteHisModel> bijhoudingsgemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(1, bijhoudingsgemeenteHistorie.size());

        Assert.assertEquals(actie.getId(), bijhoudingsgemeenteHistorie.get(0).getActieInhoud().getId());
        Assert.assertEquals(null, bijhoudingsgemeenteHistorie.get(0).getActieAanpassingGeldigheid());
        Assert.assertEquals(null, bijhoudingsgemeenteHistorie.get(0).getActieVerval());

        // nu corrigeer de wiziging die we net hebben gedaan.
        bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(
                new PersoonBijhoudingsgemeenteGroepBasis() {
                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Nee;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120908);
                    }

                    @Override
                    public Partij getBijhoudingsgemeente() {
                        return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));
                    }
                });

        ActieModel actie2 = maakActie(SoortActie.VERHUIZING);
        persoonRepository.werkbijBijhoudingsgemeente(bsn, bijhoudingsgemeente, actie2,
            new Datum(20120908));

        bijhoudingsgemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(3, bijhoudingsgemeenteHistorie.size());

        for (PersoonBijhoudingsgemeenteHisModel hisPersoonBijhoudingsgemeente : bijhoudingsgemeenteHistorie) {
            if (hisPersoonBijhoudingsgemeente.getDatumTijdVerval() != null) {
                Assert.assertEquals(hisPersoonBijhoudingsgemeente.getActieVerval().getId(), actie2.getId());
            } else {
                if (hisPersoonBijhoudingsgemeente.getDatumEindeGeldigheid() == null
                    && hisPersoonBijhoudingsgemeente.getDatumTijdVerval() == null)
                {
                    Assert.assertEquals(hisPersoonBijhoudingsgemeente.getActieInhoud().getId(), actie2.getId());
                } else {
                    Assert.assertEquals(hisPersoonBijhoudingsgemeente.getActieAanpassingGeldigheid().getId(),
                            actie2.getId());
                }
            }
        }
    }

//    private void printAanschrijfHistRecord(final PersoonAanschrijvingHisModel his) {
//        System.out.printf("%d[%d-%d] %s,%s,%s,%s,%s,[%s][%s][%s][%s]\n"
//                , his.getId()
//                , his.getDatumAanvangGeldigheid().getWaarde()
//                , ((null != his.getDatumEindeGeldigheid()) ? his.getDatumEindeGeldigheid().getWaarde() : 0)
//                , ((null != his.getAdellijkeTitel()) ? his.getAdellijkeTitel().getAdellijkeTitelCode().getWaarde() : null)
//                , ((null != his.getPredikaat()) ? his.getPredikaat().getCode().getWaarde() : null)
//                , ((null != his.getGebruikGeslachtsnaam()) ? his.getGebruikGeslachtsnaam().getCode() : null)
//                , ((null != his.getIndAanschrijvenMetAdellijkeTitel()) ? his.getIndAanschrijvenMetAdellijkeTitel() : null)
//                , ((null != his.getIndAanschrijvingAlgorthmischAfgeleid()) ? his.getIndAanschrijvingAlgorthmischAfgeleid() : null)
//                , ((null != his.getVoornamen()) ? his.getVoornamen().getWaarde() : null)
//                , ((null != his.getVoorvoegsel()) ? his.getVoorvoegsel().getWaarde() : null)
//                , ((null != his.getScheidingsteken()) ? his.getScheidingsteken().getWaarde() : null)
//                , ((null != his.getGeslachtsnaam()) ? his.getGeslachtsnaam().getWaarde() : null)
//                );
//    }
//
//    private void printHistorie(final List<PersoonAanschrijvingHisModel>hisList, final String header) {
//        if (header != null) {
//            System.out.println(header);
//        }
//        for (PersoonAanschrijvingHisModel his : hisList) {
//            printAanschrijfHistRecord(his);
//        }
//    }

    @Test
    public void testWerkbijAanschrijving() {
        ActieModel actie = maakActie(SoortActie.WIJZIGING_NAAMGEBRUIK);
        Datum aanvangGeldigheid = new Datum(20120305);
        PersoonModel persoonMdl = em.find(PersoonModel.class, 2);
        Assert.assertNotNull(persoonMdl);
        TypedQuery<PersoonAanschrijvingHisModel> queryCLaag = em.
                createQuery("SELECT his from PersoonAanschrijvingHisModel his "
                        + " WHERE his.persoon.id = :id "
                        + " AND his.historie.datumTijdVerval is NULL "
                        + " ORDER BY his.historie.datumAanvangGeldigheid ",
                            PersoonAanschrijvingHisModel.class)
                    .setParameter("id", persoonMdl.getId());

        TypedQuery<PersoonAanschrijvingHisModel> queryDLaag = em.
                createQuery("SELECT his from PersoonAanschrijvingHisModel his "
                        + " WHERE his.persoon.id = :id "
                        + " AND his.historie.datumTijdVerval is NOT NULL "
                        + " ORDER BY his.historie.datumTijdRegistratie ",
                            PersoonAanschrijvingHisModel.class)
                    .setParameter("id", persoonMdl.getId());


        List<PersoonAanschrijvingHisModel> aanHisListCLaag = queryCLaag.getResultList();
        List<PersoonAanschrijvingHisModel> aanHisListDLaag = queryDLaag.getResultList();
//        printHistorie(aanHisListCLaag, "C-Laag");
//        printHistorie(aanHisListDLaag, "D-Laag");

        persoonRepository.werkbijNaamGebruik(persoonMdl,
                maakAanschrijvingGroep("B", WijzeGebruikGeslachtsnaam.PARTNER,
                    JaNee.Nee, JaNee.Nee, "Jan Jans", "in het",  null, "Peterson"),
                actie, aanvangGeldigheid);

        List<PersoonAanschrijvingHisModel> aanHisListCLaagNew = queryCLaag .getResultList();
        List<PersoonAanschrijvingHisModel> aanHisListDLaagNew = queryDLaag .getResultList();
//      printHistorie(aanHisListCLaag, "C-Laag");
//      printHistorie(aanHisListDLaag, "D-Laag");

        // test dat de actuele data is geupdate.
        Assert.assertEquals("B", persoonMdl.getAanschrijving().getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals(null, persoonMdl.getAanschrijving().getPredikaat());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.PARTNER, persoonMdl.getAanschrijving().getGebruikGeslachtsnaam());
        Assert.assertEquals(JaNee.Nee, persoonMdl.getAanschrijving().getIndAanschrijvenMetAdellijkeTitel());
        Assert.assertEquals(JaNee.Nee, persoonMdl.getAanschrijving().getIndAanschrijvingAlgorthmischAfgeleid());
        Assert.assertEquals("Jan Jans", persoonMdl.getAanschrijving().getVoornamen().getWaarde());
        Assert.assertEquals("in het", persoonMdl.getAanschrijving().getVoorvoegsel().getWaarde());
        Assert.assertEquals(null, persoonMdl.getAanschrijving().getScheidingsteken());
        Assert.assertEquals("Peterson", persoonMdl.getAanschrijving().getGeslachtsnaam().getWaarde());
        // test dat #recs CLaag is 1 bijgekomen.
        Assert.assertEquals(aanHisListCLaag.size() + 1, aanHisListCLaagNew.size());
        // test dat de laatste Claag een einDatum heeft (en wel de aanvangsDatum)
        Assert.assertEquals(aanvangGeldigheid, aanHisListCLaagNew.get(aanHisListCLaag.size() - 1).getDatumEindeGeldigheid());
        Assert.assertEquals(aanvangGeldigheid, aanHisListCLaagNew.get(aanHisListCLaagNew.size() - 1).getDatumAanvangGeldigheid());
        Assert.assertEquals(null, aanHisListCLaagNew.get(aanHisListCLaagNew.size() - 1).getDatumEindeGeldigheid());


        // test dat #recs DLaag is 1 bijgekomen en dat de laatste was gekomen uit de oude CLaag
        Assert.assertEquals(aanHisListDLaag.size() + 1, aanHisListDLaagNew.size());
        Assert.assertEquals(aanHisListCLaag.get(aanHisListCLaag.size() - 1).getId()
                , aanHisListDLaagNew.get(aanHisListDLaagNew.size() - 1).getId());

    }

    /**
     * .
     * @param adellijkeTitelCode .
     * @param gebruik .
     * @param indMetAdellijkeTitel .
     * @param indAlgorithm .
     * @param voornamen .
     * @param voorVoegsel .
     * @param scheidingsTeken .
     * @param naam .
     * @return .
     */
    private PersoonAanschrijvingGroepModel maakAanschrijvingGroep(
        final String adellijkeTitelCode, final WijzeGebruikGeslachtsnaam gebruik,
        final JaNee indMetAdellijkeTitel, final JaNee indAlgorithm,
        final String voornamen, final String voorVoegsel, final String scheidingsTeken, final String naam)
    {
        PersoonAanschrijvingGroepBericht aanB = new PersoonAanschrijvingGroepBericht();
        if (adellijkeTitelCode != null) {
            aanB.setAdellijkeTitel(
                    referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode(adellijkeTitelCode)));
        }
        if (gebruik != null) {
            aanB.setGebruikGeslachtsnaam(gebruik);
        }
        if (indMetAdellijkeTitel != null) {
            aanB.setIndAanschrijvenMetAdellijkeTitel(indMetAdellijkeTitel);
        }
        if (indAlgorithm != null) {
            aanB.setIndAanschrijvingAlgorthmischAfgeleid(indAlgorithm);
        }
        if (voornamen != null) {
            aanB.setVoornamen(new Voornaam(voornamen));
        }
        if (voorVoegsel != null) {
            aanB.setVoorvoegsel(new Voorvoegsel(voorVoegsel));
        }
        if (scheidingsTeken != null) {
            aanB.setScheidingsteken(new Scheidingsteken(scheidingsTeken));
        }
        if (naam != null) {
            aanB.setGeslachtsnaam(new Geslachtsnaamcomponent(naam));
        }
        return new PersoonAanschrijvingGroepModel(aanB);
    }

    private PersoonModel maakNieuwPersoon() {

        @SuppressWarnings("serial")
        PersoonModel persoon = new PersoonModel(new PersoonBasis() {

            @Override
            public PersoonVerblijfsrechtGroep getVerblijfsrecht() {
                // TODO Auto-generated method stub
                return new PersoonVerblijfsrechtGroep() {

                    @Override
                    public Verblijfsrecht getVerblijfsrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumVoorzienEindeVerblijfsrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumAanvangVerblijfsrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumAanvangAaneensluitendVerblijfsrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
                // TODO Auto-generated method stub
                return new PersoonUitsluitingNLKiesrechtGroep() {

                    @Override
                    public JaNee getIndicatieUitsluitingNLKiesrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumEindeUitsluitingNLKiesrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public SoortPersoon getSoort() {
                return SoortPersoon.INGESCHREVENE;
            }

            @Override
            public PersoonSamengesteldeNaamGroep getSamengesteldeNaam() {
                return new PersoonSamengesteldeNaamGroep() {
                    @Override
                    public Voorvoegsel getVoorvoegsel() {
                        return new Voorvoegsel("'t");
                    }

                    @Override
                    public Voornaam getVoornamen() {
                        return new Voornaam("Marieke");
                    }

                    @Override
                    public Scheidingsteken getScheidingsteken() {
                        return new Scheidingsteken(":");
                    }

                    @Override
                    public Predikaat getPredikaat() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
                        // TODO Auto-generated method stub
                        return JaNee.Nee;
                    }

                    @Override
                    public JaNee getIndAlgorithmischAfgeleid() {
                        // TODO Auto-generated method stub
                        return JaNee.Nee;
                    }

                    @Override
                    public Geslachtsnaamcomponent getGeslachtsnaam() {
                        // TODO Auto-generated method stub
                        return new Geslachtsnaamcomponent("Sneeuw");
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonPersoonskaartGroep getPersoonsKaart() {
                // TODO Auto-generated method stub
                return new PersoonPersoonskaartGroep() {

                    @Override
                    public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Partij getGemeentePersoonskaart() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonOverlijdenGroep getOverlijden() {
                // TODO Auto-generated method stub
                return new PersoonOverlijdenGroep() {

                    @Override
                    public Plaats getWoonplaatsOverlijden() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Partij getOverlijdenGemeente() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Land getLandOverlijden() {
                        return referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE);
                    }

                    @Override
                    public Datum getDatumOverlijden() {
                        return new Datum(20500929);
                    }

                    @Override
                    public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonOpschortingGroep getOpschorting() {
                // TODO Auto-generated method stub
                return new PersoonOpschortingGroep() {

                    @Override
                    public RedenOpschorting getRedenOpschorting() {
                        return RedenOpschorting.OVERLIJDEN;
                    }
                };
            }

            @Override
            public PersoonInschrijvingGroep getInschrijving() {
                // TODO Auto-generated method stub
                return new PersoonInschrijvingGroep() {

                    @Override
                    public PersoonBasis getVorigePersoon() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public PersoonBasis getVolgendePersoon() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Versienummer getVersienummer() {
                        return new Versienummer(2L);
                    }

                    @Override
                    public Datum getDatumInschrijving() {
                        return new Datum(20120205);
                    }
                };
            }

            @Override
            public PersoonImmigratieGroep getImmigratie() {
                // TODO Auto-generated method stub
                return new PersoonImmigratieGroep() {

                    @Override
                    public Land getLandVanwaarGevestigd() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumVestigingInNederland() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonIdentificatienummersGroep getIdentificatienummers() {
                @SuppressWarnings("serial")
                PersoonIdentificatienummersGroep idnrs = new PersoonIdentificatienummersGroepModel(
                        new PersoonIdentificatienummersGroepBasis()
                    {

                        @Override
                        public Burgerservicenummer getBurgerservicenummer() {
                            return new Burgerservicenummer("111456789");
                        }

                        @Override
                        public ANummer getAdministratienummer() {
                            return new ANummer(Long.valueOf(1114567890L));
                        }
                    });
                return idnrs;
            }

            @Override
            public PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding() {
                return new PersoonGeslachtsaanduidingGroepModel(
                        new PersoonGeslachtsaanduidingGroepBasis()
                    {
                        @Override
                        public Geslachtsaanduiding getGeslachtsaanduiding() {
                            return Geslachtsaanduiding.MAN;
                        }
                    });
            }

            @Override
            public PersoonGeboorteGroep getGeboorte() {
                return new PersoonGeboorteGroep() {
                    @Override
                    public Plaats getWoonplaatsGeboorte() {
                        return referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode((short) 34));
                    }

                    @Override
                    public OmschrijvingEnumeratiewaarde getOmschrijvingGeboorteLocatie() {
                        return null;
                    }

                    @Override
                    public Land getLandGeboorte() {
                        return referentieDataRepository.vindLandOpCode(new Landcode((short) 6023));
                    }

                    @Override
                    public Partij getGemeenteGeboorte() {
                        return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));
                    }

                    @Override
                    public Datum getDatumGeboorte() {
                        return new Datum(19700101);
                    }

                    @Override
                    public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
                        return null;
                    }

                    @Override
                    public BuitenlandsePlaats getBuitenlandseGeboortePlaats() {
                        return null;
                    }
                };
            }

            @Override
            public PersoonEuVerkiezingenGroep getEUVerkiezingen() {
                // TODO Auto-generated method stub
                return new PersoonEuVerkiezingenGroep() {

                    @Override
                    public JaNee getIndicatieDeelnameEUVerkiezingen() {
                        return JaNee.Ja;
                    }

                    @Override
                    public Datum getDatumEindeUitsluitingEUKiesrecht() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonBijhoudingsverantwoordelijkheidGroep getBijhoudingsverantwoordelijkheid() {
                return new PersoonBijhoudingsverantwoordelijkheidGroep() {

                    @Override
                    public Verantwoordelijke getVerantwoordelijke() {
                        return Verantwoordelijke.COLLEGE;
                    }
                };
            }

            @Override
            public PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeente() {
                return new PersoonBijhoudingsgemeenteGroep() {

                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Nee;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120101);
                    }

                    @Override
                    public Partij getBijhoudingsgemeente() {
                        return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));
                    }
                };
            }


            @Override
            public PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief() {
                // TODO Auto-generated method stub
                return new PersoonAfgeleidAdministratiefGroep() {

                    @Override
                    public DatumTijd getTijdstipLaatsteWijziging() {
                        return new DatumTijd(new Timestamp(new Date().getTime()));
                    }

                    @Override
                    public JaNee getIndGegevensInOnderzoek() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }


            @Override
            public PersoonAanschrijvingGroep getAanschrijving() {
                // TODO Auto-generated method stub
                return new PersoonAanschrijvingGroep() {

                    @Override
                    public Voorvoegsel getVoorvoegsel() {
                        return new Voorvoegsel("van der");
                    }

                    @Override
                    public Voornaam getVoornamen() {
                        return new Voornaam("Johannes");
                    }

                    @Override
                    public Scheidingsteken getScheidingsteken() {
                        return new Scheidingsteken("-");
                    }

                    @Override
                    public Predikaat getPredikaat() {
                        return null;
                    }

                    @Override
                    public JaNee getIndAanschrijvingAlgorthmischAfgeleid() {
                        return JaNee.Nee;
                    }

                    @Override
                    public JaNee getIndAanschrijvenMetAdellijkeTitel() {
                        return JaNee.Nee;
                    }

                    @Override
                    public Geslachtsnaamcomponent getGeslachtsnaam() {
                        return new Geslachtsnaamcomponent("Water");
                    }

                    @Override
                    public WijzeGebruikGeslachtsnaam getGebruikGeslachtsnaam() {
                        return WijzeGebruikGeslachtsnaam.EIGEN_PARTNER;
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        return null;
                    }
                };
            }

            @Override
            public Set<? extends PersoonAdres> getAdressen() {
                // TODO Auto-generated method stub
                return new HashSet<PersoonAdres>();
            }

            @Override
            public Set<? extends PersoonNationaliteit> getNationaliteiten() {
                // TODO Auto-generated method stub
                return new HashSet<PersoonNationaliteit>();
            }

            @Override
            public Set<? extends PersoonVoornaam> getPersoonVoornaam() {
                return new HashSet<PersoonVoornaam>();
            }

            @Override
            public Set<? extends Betrokkenheid> getBetrokkenheden() {
                // TODO Auto-generated method stub
                return new HashSet<Betrokkenheid>();
            }

            @Override
            public Set<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
                // TODO Auto-generated method stub
                return new HashSet<PersoonGeslachtsnaamcomponent>();
            }
            @Override
            public Set<? extends PersoonIndicatie> getIndicaties() {
                // TODO Auto-generated method stub
                return new HashSet<PersoonIndicatie>();
            }

        });

        persoon.getPersoonVoornaam().add(maakNieuwVoornaam(persoon, 1, "voornaam1"));
        persoon.getPersoonVoornaam().add(maakNieuwVoornaam(persoon, 2, "voornaam2"));
        persoon.getGeslachtsnaamcomponenten().add(maakNieuwGeslachtsnaamcomponent(persoon, 1, "geslachtsnaamcomp1",
            "voorvoeg1", "B", "H", ","));
        persoon.getGeslachtsnaamcomponenten().add(maakNieuwGeslachtsnaamcomponent(persoon, 2, "geslachtsnaamcomp2",
            "voorvoeg2", "G", "J", ";"));
        persoon.getAdressen().add(maakNieuwAdres(persoon, FunctieAdres.WOONADRES, "1000AB", 1));
        persoon.getAdressen().add(maakNieuwAdres(persoon, FunctieAdres.WOONADRES, "1000AB", 2));

        persoon.getNationaliteiten().add(maakNieuwNationaliteit(persoon, "17", null));
        return persoon;
    }

    @SuppressWarnings("serial")
    private PersoonVoornaamModel maakNieuwVoornaam(final PersoonModel persoon, final Integer volgnummer, final String voornaam) {
        return new PersoonVoornaamModel(new PersoonVoornaamBasis() {
            @Override
            public Volgnummer getVolgnummer() {
                return new Volgnummer(volgnummer);
            }

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @Override
            public PersoonVoornaamStandaardGroep getGegevens() {
                return new PersoonVoornaamStandaardGroep() {
                    @Override
                    public Voornaam getVoornaam() {
                        return new Voornaam(voornaam);
                    }
                };
            }
        }, persoon);
    }

    @SuppressWarnings("serial")
    private PersoonNationaliteitModel maakNieuwNationaliteit(final PersoonModel persoon,
            final String rdnVerkrijg, final String rdnVerlies)
    {
        return new PersoonNationaliteitModel(new PersoonNationaliteitBasis() {

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @Override
            public Nationaliteit getNationaliteit() {
                return  referentieDataRepository.vindNationaliteitOpCode(BrpConstanten.NL_NATIONALITEIT_CODE);
            }

            @Override
            public PersoonNationaliteitStandaardGroep getGegevens() {
                return new PersoonNationaliteitStandaardGroep() {

                    @Override
                    public RedenVerliesNlNationaliteit getRedenVerliesNlNationaliteit() {

                        if (StringUtils.isNotEmpty(rdnVerlies)) {
                            return referentieDataRepository.vindRedenVerliesNLNationaliteitOpNaam(
                                    new RedenVerliesNaam(rdnVerlies));
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public RedenVerkrijgingNlNationaliteit getRedenVerkregenNlNationaliteit() {
                        if (StringUtils.isNotEmpty(rdnVerkrijg)) {
                            return referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(
                                    new RedenVerkrijgingCode(new Short(rdnVerkrijg)));
                        } else {
                            return null;
                        }
                    }
                };
            }
        }, persoon);
    }

    @SuppressWarnings("serial")
    private PersoonGeslachtsnaamcomponentModel maakNieuwGeslachtsnaamcomponent(final PersoonModel persoon,
        final Integer volgnummer, final String naam, final String voorvoegsel,
        final String adellijkeTitelCode, final String predikaatCode, final String scheidingsteken)
    {
        return new PersoonGeslachtsnaamcomponentModel(new PersoonGeslachtsnaamcomponentBasis() {
            @Override
            public Volgnummer getVolgnummer() {
                return new Volgnummer(volgnummer);
            }

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @SuppressWarnings("serial")
            @Override
            public PersoonGeslachtsnaamcomponentStandaardGroep getGegevens() {
                return new PersoonGeslachtsnaamcomponentStandaardGroep() {
                    @Override
                    public Voorvoegsel getVoorvoegsel() {
                        return new Voorvoegsel(voorvoegsel);
                    }

                    @Override
                    public Scheidingsteken getScheidingsteken() {
                        return new Scheidingsteken(scheidingsteken);
                    }

                    @Override
                    public Predikaat getPredikaat() {
                        return referentieDataRepository.vindPredikaatOpCode(new PredikaatCode(predikaatCode));
                    }

                    @Override
                    public Geslachtsnaamcomponent getNaam() {
                        return new Geslachtsnaamcomponent(naam);
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        return referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode(adellijkeTitelCode));
                    }
                };
            }
        }, persoon);
    }

    @SuppressWarnings("serial")
    private PersoonAdresModel maakNieuwAdres(final PersoonModel persoon, final FunctieAdres functieAdres, final String postcode,
            final Integer huisnummer)
    {
        PersoonAdresModel adres = new PersoonAdresModel(new PersoonAdres() {

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @SuppressWarnings("serial")
            @Override
            public PersoonAdresStandaardGroep getGegevens() {
                return new PersoonAdresStandaardGroep() {

                    @Override
                    public Plaats getWoonplaats() {
                        return referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode((short) 34));
                    }

                    @Override
                    public FunctieAdres getSoort() {
                        return functieAdres;
                    }

                    @Override
                    public RedenWijzigingAdres getRedenWijziging() {
                        return referentieDataRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("P"));
                    }

                    @Override
                    public Postcode getPostcode() {
                        return new Postcode(postcode);
                    }

                    @Override
                    public NaamOpenbareRuimte getNaamOpenbareRuimte() {
                        return new NaamOpenbareRuimte("Damweg");
                    }

                    @Override
                    public AanduidingBijHuisnummer getLocatietovAdres() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public LocatieOmschrijving getLocatieOmschrijving() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Land getLand() {
                        return referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE);
                    }

                    @Override
                    public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
                        return new IdentificatiecodeNummeraanduiding("1815");
                    }

                    @Override
                    public Huisnummertoevoeging getHuisnummertoevoeging() {
                        return new Huisnummertoevoeging("III");
                    }

                    @Override
                    public Huisnummer getHuisnummer() {
                        return new Huisnummer(huisnummer);
                    }

                    @Override
                    public Huisletter getHuisletter() {
                        return new Huisletter("b");
                    }

                    @Override
                    public Gemeentedeel getGemeentedeel() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Partij getGemeente() {
                        // TODO Auto-generated method stub
                        return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));
                    }

                    @Override
                    public Datum getDatumVertrekUitNederland() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Datum getDatumAanvangAdreshouding() {
                        return new Datum(20120229);
                    }

                    @Override
                    public Adresregel getBuitenlandsAdresRegel6() {
                        return null;
                    }

                    @Override
                    public Adresregel getBuitenlandsAdresRegel5() {
                        return null;
                    }

                    @Override
                    public Adresregel getBuitenlandsAdresRegel4() {
                        return null;
                    }

                    @Override
                    public Adresregel getBuitenlandsAdresRegel3() {
                        return null;
                    }

                    @Override
                    public Adresregel getBuitenlandsAdresRegel2() {
                        return null;
                    }

                    @Override
                    public Adresregel getBuitenlandsAdresRegel1() {
                        return null;
                    }

                    @Override
                    public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
                        return new AfgekorteNaamOpenbareRuimte("Damwg");
                    }

                    @Override
                    public AanduidingAdresseerbaarObject getAdresseerbaarObject() {
                        return new AanduidingAdresseerbaarObject("1753");
                    }

                    @Override
                    public AangeverAdreshoudingIdentiteit getAangeverAdresHouding() {
                        return AangeverAdreshoudingIdentiteit.INWONEND;
                    }
                };
            }
        }, persoon);
        return adres;
    }

    private PersoonVoornaam haalopVoornaam(final String voornaam,
            final Set<PersoonVoornaamModel> voornamen)
    {
        for (PersoonVoornaam n : voornamen) {
            if (voornaam.equals(n.getGegevens().getVoornaam().getWaarde())) {
                return n;
            }
        }

        return null;
    }

    private PersoonGeslachtsnaamcomponentModel haalopGeslachtsnaam(final String geslachtsnaam,
            final Set<PersoonGeslachtsnaamcomponentModel> geslachtsnamen)
    {
        for (PersoonGeslachtsnaamcomponentModel n : geslachtsnamen) {
            if (geslachtsnaam.equals(n.getGegevens().getNaam().getWaarde())) {
                return n;
            }
        }

        return null;
    }

    private ActieModel maakActie(final SoortActie soortActie) {
        @SuppressWarnings("serial")
        ActieModel actie = new ActieModel(new ActieBasis() {

            @Override
            public Verdrag getVerdrag() {
                return null;
            }

            @Override
            public DatumTijd getTijdstipRegistratie() {
                return new DatumTijd(
                        new Timestamp(new Date(System.currentTimeMillis() - 1).getTime()));
            }

            @Override
            public DatumTijd getTijdstipOntlening() {
                return null;
            }

            @Override
            public SoortActie getSoort() {
                return soortActie;
            }

            @Override
            public Partij getPartij() {
                return referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 363));
            }

            @Override
            public Ontleningstoelichting getOntleningstoelichting() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Datum getDatumEindeGeldigheid() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Datum getDatumAanvangGeldigheid() {
                return null;
            }
        }
        );
        em.persist(actie);
        return actie;
    }

}
