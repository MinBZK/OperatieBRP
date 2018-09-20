/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ontleningstoelichting;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Versienummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.ActieBron;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocument;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfstitelGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsaardModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsgemeenteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOpschortingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Test
    public void testFindPersoonMetId() {
        PersoonModel persoon = persoonRepository.findPersoonMetId(1);
        Assert.assertNotNull(persoon);
        Assert.assertEquals(Integer.valueOf(1), persoon.getID());
    }

    @Test
    public void testFindByBurgerservicenummerResultaat() {
        PersoonModel persoon = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(123456789));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(1, persoon.getID().intValue());
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummer() {
        PersoonModel persoon =
            persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer(234567891));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(new Integer(2), persoon.getID());

        // Test nationaliteiten collectie
        Assert.assertFalse(persoon.getNationaliteiten().isEmpty());
        Assert.assertEquals("0001", persoon.getNationaliteiten().iterator().next()
                                           .getNationaliteit().getCode().toString());

        // Test indicaties collectie
        Assert.assertFalse(persoon.getIndicaties().isEmpty());
        List<SoortIndicatie> list = new ArrayList<SoortIndicatie>();
        for (PersoonIndicatieModel ind : persoon.getIndicaties()) {
            list.add(ind.getSoort());
        }
        Assert.assertTrue(list.contains(SoortIndicatie.INDICATIE_VERSTREKKINGSBEPERKING));
        Assert.assertTrue(list.contains(SoortIndicatie.INDICATIE_STAATLOOS));
        Assert.assertFalse(list.contains(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG));
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummerEnTestOuderschap() {
        PersoonModel persoon =
            persoonRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer(345678912));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(3, persoon.getID().longValue());

        for (BetrokkenheidModel betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().equals(SoortBetrokkenheid.KIND)) {
                RelatieModel relatie = betrokkenheid.getRelatie();
                Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING,
                    relatie.getSoort());
                // TODO: testen met ouderlijk betrokkenheid; is opgeslagen in de historie, wordt nu nog niet opgehaald.
//                for (Betrokkenheid relatieBetrokkenheid : relatie.getBetrokkenheden()) {
//                    PersoonMdl betrokkene = relatieBetrokkenheid.getBetrokkene();
//                    if (betrokkene.getID().equals(2L)) {
//                        Assert.assertNull(relatieBetrokkenheid.getBetrokkenheidOuderschap()
// .getDatumAanvangOuderschap());
//                    } else if (betrokkene.getID().equals(8731137L)) {
//                        Assert.assertEquals(Integer.valueOf(17240422),
// relatieBetrokkenheid.getDatumAanvangOuderschap());
//                    }
//                }
            }
        }
    }

    @Test
    public void testFindByBurgerservicenummerGeenResultaat() {
        Assert.assertNull(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("0")));
    }

    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummer() {
        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer("0"));
        Assert.assertTrue("personen zou leeg moeten zijn", personen.size() == 0);

        personen =
            persoonRepository
                .haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer(123456789));
        Assert.assertEquals(1, personen.size());

        // BSN met alleen post adres
        personen =
            persoonRepository
                .haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer(100001002));
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaVolledigAdres() {
        Plaats plaats = referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode(Short.parseShort("0034")));
        Partij gemeente = referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034")));
        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimte("Damstraat"),
                new Huisnummer(1), new Huisletter("a"), new Huisnummertoevoeging("II"),
                plaats, gemeente);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(1001), personen.get(0).getID());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaVolledigAdresMetLegeStringsIpvNulls() {
        Plaats plaats = referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode(Short.parseShort("0034")));
        Partij gemeente = referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034")));

        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimte("Damstraat"),
                new Huisnummer(1), new Huisletter("a"), new Huisnummertoevoeging("II"),
                plaats, gemeente);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(1001), personen.get(0).getID());
    }

    @Test
    public void testHaalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding() {
        List<PersoonModel> personen =
            persoonRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                new IdentificatiecodeNummeraanduiding("1581"));
        Assert.assertEquals(2, personen.size());
        List<Integer> ids = Arrays.asList(personen.get(0).getID(), personen.get(1).getID());
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

        Assert.assertEquals(new Burgerservicenummer(123456789),
            personen.get(0).getIdentificatienummers().getBurgerservicenummer());

        personen = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
            new Postcode("1000AA"), new Huisnummer(1), null, null);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer(234567891),
            personen.get(0).getIdentificatienummers().getBurgerservicenummer());

    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummerMetLegeStringsIpvNulls() {
        List<PersoonModel> personen =
            persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
                new Postcode("1334NA"),
                new Huisnummer(73), new Huisletter("A"), new Huisnummertoevoeging("sous"));
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer(123456789),
            personen.get(0).getIdentificatienummers().getBurgerservicenummer());

        personen = persoonRepository.haalPersonenOpMetAdresViaPostcodeHuisnummer(
            new Postcode("1000AA"),
            new Huisnummer(1), new Huisletter(""), new Huisnummertoevoeging(""));
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Burgerservicenummer(234567891),
            personen.get(0).getIdentificatienummers().getBurgerservicenummer());
    }

    @Test
    public void testHaalPersoonOpMetAdresViaBetrokkenheid() {
        BetrokkenheidModel betrokkenheid = em.find(BetrokkenheidModel.class, 1);

        PersoonModel persoon = persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(betrokkenheid);
        Assert.assertNotNull(persoon);
    }


    @Test
    public void testOpslaanNieuwPersoon() {
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_GEBOORTE);
        Datum datumAanvangGeldigheid = new Datum(20101212);

        PersoonModel persoonOperationeel = maakNieuwPersoon();
        PersoonModel persoonNieuw = persoonRepository.opslaanNieuwPersoon(persoonOperationeel, actie,
            datumAanvangGeldigheid);
        Integer id = persoonNieuw.getID();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE persoon.identificatienummers.burgerservicenummer = " +
                ":burgerservicenummer";
        final Burgerservicenummer bsn = new Burgerservicenummer(111456789);
        PersoonModel persoon =
            (PersoonModel) em.createQuery(persoonsql).setParameter("burgerservicenummer", bsn)
                             .getSingleResult();
        // Controleer de geretourneerde id
        Assert.assertEquals(persoon.getID(), id);

        // Controleer A-laag
        Assert.assertEquals(bsn, persoon.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals(new ANummer(1114567890L),
            persoon.getIdentificatienummers().getAdministratienummer());
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());
//        Assert.assertEquals(StatusHistorie.A, persoon.getInschrijving().getStatusHistorie());
        Assert.assertEquals(new Integer(20120205), persoon.getInschrijving().getDatumInschrijving().getWaarde());

        // Voornaam
        Assert.assertEquals(2, persoon.getVoornamen().size());
        Assert.assertNotNull(haalopVoornaam("voornaam1", persoon.getVoornamen()));
        Assert.assertNotNull(haalopVoornaam("voornaam2", persoon.getVoornamen()));

        // Geslachtsnaam
        PersoonGeslachtsnaamcomponentModel pg1 =
            haalopGeslachtsnaam("geslachtsnaamcomp1", persoon.getGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg1);
        Assert.assertTrue("geslachtsnaamcomp1".equals(pg1.getStandaard().getNaam().getWaarde()));
        Assert.assertTrue("voorvoeg1".equals(pg1.getStandaard().getVoorvoegsel().getWaarde()));
        Assert.assertEquals("B", pg1.getStandaard().getAdellijkeTitel().getCode().getWaarde());
        Assert.assertEquals("H", pg1.getStandaard().getPredikaat().getCode().getWaarde());
        Assert.assertTrue(",".equals(pg1.getStandaard().getScheidingsteken().getWaarde()));

        PersoonGeslachtsnaamcomponentModel pg2 =
            haalopGeslachtsnaam("geslachtsnaamcomp2", persoon.getGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg2);
        Assert.assertTrue("geslachtsnaamcomp2".equals(pg2.getStandaard().getNaam().getWaarde()));
        Assert.assertTrue("voorvoeg2".equals(pg2.getStandaard().getVoorvoegsel().getWaarde()));
        Assert.assertEquals("G", pg2.getStandaard().getAdellijkeTitel().getCode().getWaarde());
        Assert.assertEquals("J", pg2.getStandaard().getPredikaat().getCode().getWaarde());
        Assert.assertTrue(";".equals(pg2.getStandaard().getScheidingsteken().getWaarde()));

        // Geboorte
        Assert.assertEquals(new Integer(19700101), persoon.getGeboorte().getDatumGeboorte().getWaarde());
        Assert.assertEquals("0034", persoon.getGeboorte().getGemeenteGeboorte().getCode().toString());
        Assert.assertEquals("Almere", persoon.getGeboorte().getWoonplaatsGeboorte().getNaam().getWaarde());
        Assert.assertEquals("Afghanistan", persoon.getGeboorte().getLandGeboorte().getNaam().getWaarde());

        // Bijhoudinggemeente
        Assert.assertEquals(new Integer(20120101),
            persoon.getBijhoudingsgemeente().getDatumInschrijvingInGemeente().getWaarde());
        Assert.assertEquals("0034", persoon.getBijhoudingsgemeente().getBijhoudingsgemeente().getCode().toString());
        Assert.assertEquals(JaNee.NEE, persoon.getBijhoudingsgemeente().getIndicatieOnverwerktDocumentAanwezig());

        // Opschorting
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoon.getOpschorting().getRedenOpschortingBijhouding());

        // Geslachtsaanduiding
        Assert.assertEquals(Geslachtsaanduiding.MAN, persoon.getGeslachtsaanduiding().getGeslachtsaanduiding());

        // Tijdstip laatste wijziging
        Assert.assertNotNull(persoon.getAfgeleidAdministratief().getTijdstipLaatsteWijziging());

        // Adressen
        Assert.assertEquals(2, persoon.getAdressen().size());
        boolean adres1Aanwezig = false;
        boolean adres2Aanwezig = false;
        for (PersoonAdresModel persoonAdres : persoon.getAdressen()) {
            if ("1".equals(persoonAdres.getStandaard().getHuisnummer().getWaarde().toString())) {
                adres1Aanwezig = true;
            } else if ("2".equals(persoonAdres.getStandaard().getHuisnummer().getWaarde().toString())) {
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
        Assert.assertEquals(BrpConstanten.NL_NATIONALITEIT_CODE, nat1.getNationaliteit().getCode());
        Assert.assertEquals("001", nat1.getStandaard().getRedenVerkrijging().getCode().toString());


        // Controlleer C-laag

        // hisVoornaam
        @SuppressWarnings("JpaQlInspection")
        final String hisPersoonvoornaam =
            "SELECT hisPersoonVoornaam FROM HisPersoonVoornaamModel hisPersoonVoornaam WHERE "
                +
                " persoonVoornaam.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer order by " +
                "naam";

        @SuppressWarnings("unchecked")
        List<HisPersoonVoornaamModel> hisVoornamen =
            em.createQuery(hisPersoonvoornaam)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        HisPersoonVoornaamModel hisVoornaam1 = hisVoornamen.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212),
            hisVoornaam1.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisVoornaam1.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam1.getMaterieleHistorie().getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam1.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals("voornaam1", hisVoornaam1.getNaam().getWaarde());
        Assert.assertEquals(hisVoornaam1.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisVoornaam1.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam1.getMaterieleHistorie().getActieVerval());

        HisPersoonVoornaamModel hisVoornaam2 = hisVoornamen.get(1);
        Assert.assertEquals(new Integer(20101212),
            hisVoornaam2.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisVoornaam2.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam2.getMaterieleHistorie().getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam2.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals("voornaam2", hisVoornaam2.getNaam().getWaarde());
        Assert.assertEquals(hisVoornaam2.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisVoornaam2.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam2.getMaterieleHistorie().getActieVerval());

        // hisGeslachtsnaam .
        @SuppressWarnings("JpaQlInspection") final String hisGeslachtsnaamcomp =
            "SELECT hisGesln FROM HisPersoonGeslachtsnaamcomponentModel hisGesln WHERE "
                + " persoonGeslachtsnaamcomponent.persoon.identificatienummers.burgerservicenummer = "
                + " :burgerservicenummer order by naam";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeslachtsnaamcomponentModel> hisGeslachtsnaamcompenenten =
            em.createQuery(hisGeslachtsnaamcomp)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        HisPersoonGeslachtsnaamcomponentModel hisGeslachtsNaam1 = hisGeslachtsnaamcompenenten.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212),
            hisGeslachtsNaam1.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsNaam1.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam1.getMaterieleHistorie().getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam1.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp1", hisGeslachtsNaam1.getNaam().getWaarde());
        Assert.assertEquals("voorvoeg1", hisGeslachtsNaam1.getVoorvoegsel().getWaarde());
        Assert.assertEquals(hisGeslachtsNaam1.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisGeslachtsNaam1.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsNaam1.getMaterieleHistorie().getActieVerval());

        HisPersoonGeslachtsnaamcomponentModel hisGeslachtsNaam2 = hisGeslachtsnaamcompenenten.get(1);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212),
            hisGeslachtsNaam2.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsNaam2.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsNaam2.getMaterieleHistorie().getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsNaam2.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals("geslachtsnaamcomp2", hisGeslachtsNaam2.getNaam().getWaarde());
        Assert.assertEquals("voorvoeg2", hisGeslachtsNaam2.getVoorvoegsel().getWaarde());
        Assert.assertEquals(hisGeslachtsNaam2.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisGeslachtsNaam2.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsNaam2.getMaterieleHistorie().getActieVerval());

        // HisPersoonGeboorte
        final String hisPersoonGeboorte =
            "SELECT hisGeboorte FROM HisPersoonGeboorteModel hisGeboorte WHERE "
                + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<HisPersoonGeboorteModel> hisPersoonGeboorten =
            em.createQuery(hisPersoonGeboorte)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        HisPersoonGeboorteModel hisGeboorte = hisPersoonGeboorten.get(0);
        Assert.assertNotNull(hisGeboorte.getFormeleHistorie().getDatumTijdRegistratie());
        Assert.assertNull(hisGeboorte.getFormeleHistorie().getDatumTijdVerval());
        Assert.assertEquals("0034", hisGeboorte.getGemeenteGeboorte().getCode().toString());
        Assert.assertEquals("0034", hisGeboorte.getWoonplaatsGeboorte().getCode().toString());
        Assert.assertEquals(hisGeboorte.getFormeleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisGeboorte.getFormeleHistorie().getActieVerval());

        // HisBijhoudingGemeente

        final String hisBijhoudingGemeente =
            "SELECT hisBijhoudingGem FROM HisPersoonBijhoudingsgemeenteModel hisBijhoudingGem WHERE "
                + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonBijhoudingsgemeenteModel> hisPersoonBijhoudingsgemeenten =
            em.createQuery(hisBijhoudingGemeente)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        HisPersoonBijhoudingsgemeenteModel hisPersoonBijhoudingsgemeente = hisPersoonBijhoudingsgemeenten.get(0);
        Assert.assertEquals(new Integer(20120101), hisPersoonBijhoudingsgemeente
            .getDatumInschrijvingInGemeente().getWaarde());
        Assert.assertEquals("0034",
            hisPersoonBijhoudingsgemeente.getBijhoudingsgemeente().getCode().toString());
        Assert.assertEquals(JaNee.NEE, hisPersoonBijhoudingsgemeente.getIndicatieOnverwerktDocumentAanwezig());
        Assert.assertEquals(new Integer(20101212),
            hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getDatumAanvangGeldigheid()
                                         .getWaarde());
        Assert.assertEquals(hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getActieInhoud().getID(),
            actie.getID());
        Assert.assertNull(hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getActieVerval());

        // HisPersoonGeslachtsaanduiding
        final String hisGeslachtsaand =
            "SELECT hisGesl FROM HisPersoonGeslachtsaanduidingModel hisGesl WHERE "
                + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonGeslachtsaanduidingModel> hisGeslachtsaanduidingen =
            em.createQuery(hisGeslachtsaand)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        HisPersoonGeslachtsaanduidingModel hisGeslachtsaanduiding = hisGeslachtsaanduidingen.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212),
            hisGeslachtsaanduiding.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsaanduiding.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsaanduiding.getMaterieleHistorie().getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsaanduiding.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals(Geslachtsaanduiding.MAN, hisGeslachtsaanduiding.getGeslachtsaanduiding());
        Assert.assertEquals(hisGeslachtsaanduiding.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisGeslachtsaanduiding.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsaanduiding.getMaterieleHistorie().getActieVerval());

        // HisPersoonInschrijving
        final String hisPersInschr =
            "SELECT hisPersInschr FROM HisPersoonInschrijvingModel hisPersInschr WHERE "
                + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<HisPersoonInschrijvingModel> hisPersoonInschrijvingen =
            em.createQuery(hisPersInschr)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        Assert.assertEquals(new Integer(20120205), hisPersoonInschrijvingen.get(0).getDatumInschrijving().getWaarde());
        Assert.assertEquals(hisPersoonInschrijvingen.get(0).getFormeleHistorie().getActieInhoud().getID(),
            actie.getID());
        Assert.assertNull(hisPersoonInschrijvingen.get(0).getFormeleHistorie().getActieVerval());


        // HisOpschorting
        final String hisPersOpschorting =
            "SELECT hisPersOpschor FROM HisPersoonOpschortingModel hisPersOpschor WHERE "
                + " persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        List<HisPersoonOpschortingModel> hisPersoonOpschortingen =
            em.createQuery(hisPersOpschorting, HisPersoonOpschortingModel.class)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN,
            hisPersoonOpschortingen.get(0).getRedenOpschortingBijhouding());
        Assert.assertEquals(new Integer(20101212),
            hisPersoonOpschortingen.get(0).getMaterieleHistorie().getDatumAanvangGeldigheid()
                                   .getWaarde());
        Assert.assertEquals(actie.getID(),
            hisPersoonOpschortingen.get(0).getMaterieleHistorie().getActieInhoud().getID());

        // HisPersoonAdres + gesorteerd op Id
        @SuppressWarnings("JpaQlInspection")
        final String hisPersoonAdres =
            "SELECT hisPersoonAdres FROM HisPersoonAdresModel hisPersoonAdres WHERE "
                + " hisPersoonAdres.persoonAdres.persoon.identificatienummers.burgerservicenummer = " +
                ":burgerservicenummer"
                + " ORDER by hisPersoonAdres.materieleHistorie.datumTijdVerval DESC, "
                +
                " hisPersoonAdres.materieleHistorie.datumAanvangGeldigheid.waarde DESC, hisPersoonAdres.id ASC";
        @SuppressWarnings("unchecked")
        List<HisPersoonAdresModel> hisPersoonAdressen =
            em.createQuery(hisPersoonAdres)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        Assert.assertEquals(2, hisPersoonAdressen.size());
        int aantalVervallen = 0;
        for (HisPersoonAdresModel hisAdres : hisPersoonAdressen) {
            // BRPUC00115:
            Assert.assertEquals(new Integer(20101212),
                hisAdres.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
            Assert.assertNull(hisAdres.getMaterieleHistorie().getDatumEindeGeldigheid());
            Assert.assertNotNull(hisAdres.getMaterieleHistorie().getDatumTijdRegistratie());
            if (hisAdres.getMaterieleHistorie().getDatumTijdVerval() != null) {
                aantalVervallen++;
                Assert.assertEquals(hisAdres.getMaterieleHistorie().getActieVerval().getID(), actie.getID());
            } else if (hisAdres.getMaterieleHistorie().getDatumEindeGeldigheid() != null) {
                Assert.assertEquals(hisAdres.getMaterieleHistorie().getActieAanpassingGeldigheid().getID(),
                    actie.getID());
            } else {
                Assert.assertEquals(hisAdres.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
            }
        }
        // ze zijn beide ingeschoten met deze actie, beide zelfde aanvangGeldigheid, zelfde tijdstip registratie.
        // dus zijn geen enkel vervallen.
        Assert.assertEquals(0, aantalVervallen);

        // BijhoudingsVerwantwoordelijkheid
        final String hisBijhoudingsverwantwoordelijkheid =
            "SELECT hisPersBijhAard FROM HisPersoonBijhoudingsaardModel hisPersBijhAard WHERE "
                + " hisPersBijhAard.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonBijhoudingsaardModel> hisPersoonBijhoudingsverantwoordelijkheden =
            em.createQuery(hisBijhoudingsverwantwoordelijkheid)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        Assert.assertEquals(new Integer(20101212), hisPersoonBijhoudingsverantwoordelijkheden.get(0)
                                                                                  .getMaterieleHistorie()
                                                                                  .getDatumAanvangGeldigheid()
                                                                                  .getWaarde());
        Assert.assertEquals(Bijhoudingsaard.INGEZETENE,
            hisPersoonBijhoudingsverantwoordelijkheden.get(0).getBijhoudingsaard());
        Assert.assertEquals(
            hisPersoonBijhoudingsverantwoordelijkheden.get(0).getMaterieleHistorie().getActieInhoud().getID(),
            actie.getID());
        Assert.assertNull(hisPersoonBijhoudingsverantwoordelijkheden.get(0).getMaterieleHistorie()
                                                                    .getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsverantwoordelijkheden.get(0).getMaterieleHistorie().getActieVerval());

        // PersoonIdentificatienummers

        final String hisPersoonIdentificatienummers =
            "SELECT hisPersIdn FROM HisPersoonIdentificatienummersModel hisPersIdn WHERE "
                + " hisPersIdn.persoon.identificatienummers.burgerservicenummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<HisPersoonIdentificatienummersModel> hisPersoonIdentificatienummersResultaat =
            em.createQuery(hisPersoonIdentificatienummers)
              .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
              .getResultList();
        Assert.assertEquals(new Integer(20101212), hisPersoonIdentificatienummersResultaat.get(0).getMaterieleHistorie()
                                                                                          .getDatumAanvangGeldigheid()
                                                                                          .getWaarde());
        Assert.assertEquals("1114567890",
            hisPersoonIdentificatienummersResultaat.get(0).getAdministratienummer().toString());
        Assert.assertEquals("111456789",
            hisPersoonIdentificatienummersResultaat.get(0).getBurgerservicenummer().toString());
        Assert.assertEquals(
            hisPersoonIdentificatienummersResultaat.get(0).getMaterieleHistorie().getActieInhoud().getID(),
            actie.getID());
        Assert.assertNull(
            hisPersoonIdentificatienummersResultaat.get(0).getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getMaterieleHistorie().getActieVerval());
    }

    @Test
    public void testOpslaanNieuwPersoonSamenGesteldeNaamGroep() {
        final ActieModel actie = maakActie(SoortActie.REGISTRATIE_HUWELIJK);
        PersoonModel persoon = maakNieuwPersoon();

        @SuppressWarnings("serial")
        PersoonSamengesteldeNaamGroepModel groep = new PersoonSamengesteldeNaamGroepModel(
            new PersoonSamengesteldeNaamGroep() {
                @Override
                public Voorvoegsel getVoorvoegsel() {
                    return new Voorvoegsel("der");
                }

                @Override
                public Scheidingsteken getScheidingsteken() {
                    return new Scheidingsteken("/");
                }

                @Override
                public Geslachtsnaam getGeslachtsnaam() {
                    return new Geslachtsnaam("geslachtsnaamafgeleid");
                }

                @Override
                public JaNee getIndicatieAlgoritmischAfgeleid() {
                    return JaNee.JA;
                }

                @Override
                public JaNee getIndicatieNamenreeks() {
                    return JaNee.NEE;
                }

                @Override
                public Predikaat getPredikaat() {
                    return referentieDataRepository.vindPredikaatOpCode(new PredikaatCode("H"));
                }

                @Override
                public Voornamen getVoornamen() {
                    return new Voornamen("voornaam1 voornaam2");
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
                             .setParameter("burgerservicenummer", new Burgerservicenummer(111456789))
                             .getSingleResult();

        Assert.assertEquals("B", ppersoon.getSamengesteldeNaam().getAdellijkeTitel().getCode().getWaarde());
        Assert.assertEquals("geslachtsnaamafgeleid", ppersoon.getSamengesteldeNaam().getGeslachtsnaam().getWaarde());
        Assert.assertEquals("H", ppersoon.getSamengesteldeNaam().getPredikaat().getCode().getWaarde());
        Assert.assertEquals("/", ppersoon.getSamengesteldeNaam().getScheidingsteken().getWaarde());
        Assert.assertEquals("voornaam1 voornaam2", ppersoon.getSamengesteldeNaam().getVoornamen().getWaarde());
        Assert.assertEquals("der", ppersoon.getSamengesteldeNaam().getVoorvoegsel().getWaarde());
        Assert.assertEquals(JaNee.JA, ppersoon.getSamengesteldeNaam().getIndicatieAlgoritmischAfgeleid());
        Assert.assertEquals(JaNee.NEE, ppersoon.getSamengesteldeNaam().getIndicatieNamenreeks());

        // Check historie

        final String historieQuery = "SELECT sgn FROM HisPersoonSamengesteldeNaamModel sgn WHERE "
            + " sgn.persoon = :persoon";
        List<HisPersoonSamengesteldeNaamModel> hisPersoonSamengesteldeNaamLijst =
            em.createQuery(historieQuery, HisPersoonSamengesteldeNaamModel.class).setParameter("persoon", ppersoon)
              .getResultList();

        Assert.assertEquals(1, hisPersoonSamengesteldeNaamLijst.size());
        HisPersoonSamengesteldeNaamModel hisPersoonSamengesteldeNaam = hisPersoonSamengesteldeNaamLijst.get(0);

        Assert.assertEquals("B", hisPersoonSamengesteldeNaam.getAdellijkeTitel().getCode().getWaarde());
        Assert.assertEquals("geslachtsnaamafgeleid", hisPersoonSamengesteldeNaam.getGeslachtsnaam().getWaarde());
        Assert.assertEquals("H", hisPersoonSamengesteldeNaam.getPredikaat().getCode().getWaarde());
        Assert.assertEquals("/", hisPersoonSamengesteldeNaam.getScheidingsteken().getWaarde());
        Assert.assertEquals("voornaam1 voornaam2", hisPersoonSamengesteldeNaam.getVoornamen().getWaarde());
        Assert.assertEquals("der", hisPersoonSamengesteldeNaam.getVoorvoegsel().getWaarde());
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212),
            hisPersoonSamengesteldeNaam.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(JaNee.JA, hisPersoonSamengesteldeNaam.getIndicatieAlgoritmischAfgeleid());
        Assert.assertEquals(JaNee.NEE, hisPersoonSamengesteldeNaam.getIndicatieNamenreeks());
        Assert.assertEquals(hisPersoonSamengesteldeNaam.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(hisPersoonSamengesteldeNaam.getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonSamengesteldeNaam.getMaterieleHistorie().getActieVerval());
    }

    @Test
    public void testPersoonOpslaanGeenPlaats() {
        final ActieModel actie = maakActie(SoortActie.REGISTRATIE_ADRES);
        PersoonModel persoon = maakNieuwPersoon();
        ReflectionTestUtils.setField(persoon.getIdentificatienummers(), "burgerservicenummer",
            new Burgerservicenummer(222456789));
        ReflectionTestUtils.setField(persoon.getGeboorte(), "woonplaatsGeboorte", null);

        Integer id = persoonRepository.opslaanNieuwPersoon(persoon, actie,
            new Datum(20101212)).getID();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE "
                + " identificatienummers.burgerservicenummer = :burgerservicenummer";

        PersoonModel persoonCheck =
            (PersoonModel) em.createQuery(persoonsql)
                             .setParameter("burgerservicenummer", new Burgerservicenummer(222456789))
                             .getSingleResult();

        Assert.assertEquals(id, persoonCheck.getID());
        Assert.assertEquals(null, persoonCheck.getGeboorte().getWoonplaatsGeboorte());
    }

    @Test(expected = ObjectReedsBestaandExceptie.class)
    public void testOpslaanNieuwPersoonAlBestaand() {
        final ActieModel actie = maakActie(SoortActie.REGISTRATIE_GEBOORTE);
        PersoonModel persoon = maakNieuwPersoon();
        ReflectionTestUtils.setField(persoon.getIdentificatienummers(), "burgerservicenummer",
            new Burgerservicenummer(123456789));
        persoonRepository.opslaanNieuwPersoon(persoon, actie,
            new Datum(20101212));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWerkbijBijhoudingsgemeenteMetOnbekendeBsn() throws Throwable {
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_ADRES);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        final Burgerservicenummer bsn = new Burgerservicenummer(999999999);

        // Gemeente
        PersoonBijhoudingsgemeenteGroepModel bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(
            new PersoonBijhoudingsgemeenteGroep() {

                @Override
                public Datum getDatumInschrijvingInGemeente() {
                    return new Datum(20120708);
                }

                @Override
                public JaNee getIndicatieOnverwerktDocumentAanwezig() {
                    return JaNee.JA;
                }

                @Override
                public Partij getBijhoudingsgemeente() {
                    return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0363")));
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
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_ADRES);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        final Burgerservicenummer bsn = persoon.getIdentificatienummers().getBurgerservicenummer();

        // Gemeente
        PersoonBijhoudingsgemeenteGroepModel bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(
            new PersoonBijhoudingsgemeenteGroep() {

                @Override
                public Datum getDatumInschrijvingInGemeente() {
                    return new Datum(20120708);
                }

                @Override
                public JaNee getIndicatieOnverwerktDocumentAanwezig() {
                    return JaNee.JA;
                }

                @Override
                public Partij getBijhoudingsgemeente() {
                    return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0363")));
                }
            });

        persoonRepository.werkbijBijhoudingsgemeente(bsn, bijhoudingsgemeente, actie,
            new Datum(20120708));

        persoon = em.find(PersoonModel.class, 1);
        Assert.assertEquals("Amsterdam", persoon.getBijhoudingsgemeente()
                                                .getBijhoudingsgemeente().getNaam().getWaarde());
        Assert.assertEquals(new Integer(20120708), persoon.getBijhoudingsgemeente()
                                                          .getDatumInschrijvingInGemeente().getWaarde());

        // Controlleer historie

        @SuppressWarnings("JpaQlInspection")
        final String hisBijhoudingsgemeenteSql =
            "SELECT hisBijhGem FROM HisPersoonBijhoudingsgemeenteModel hisBijhGem WHERE "
                + " hisBijhGem.persoon.id = 1 ORDER BY hisBijhGem.materieleHistorie.datumAanvangGeldigheid DESC";

        TypedQuery<HisPersoonBijhoudingsgemeenteModel> typedQuery =
            em.createQuery(hisBijhoudingsgemeenteSql, HisPersoonBijhoudingsgemeenteModel.class);

        List<HisPersoonBijhoudingsgemeenteModel> bijhoudingsgemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(1, bijhoudingsgemeenteHistorie.size());

        Assert.assertEquals(actie.getID(),
            bijhoudingsgemeenteHistorie.get(0).getMaterieleHistorie().getActieInhoud().getID());
        Assert.assertEquals(null,
            bijhoudingsgemeenteHistorie.get(0).getMaterieleHistorie().getActieAanpassingGeldigheid());
        Assert.assertEquals(null, bijhoudingsgemeenteHistorie.get(0).getMaterieleHistorie().getActieVerval());

        // nu corrigeer de wiziging die we net hebben gedaan.
        bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(
            new PersoonBijhoudingsgemeenteGroep() {

                @Override
                public Datum getDatumInschrijvingInGemeente() {
                    return new Datum(20120908);
                }

                @Override
                public JaNee getIndicatieOnverwerktDocumentAanwezig() {
                    return JaNee.NEE;
                }

                @Override
                public Partij getBijhoudingsgemeente() {
                    return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034")));
                }
            });

        ActieModel actie2 = maakActie(SoortActie.REGISTRATIE_ADRES);
        persoonRepository.werkbijBijhoudingsgemeente(bsn, bijhoudingsgemeente, actie2,
            new Datum(20120908));

        bijhoudingsgemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(3, bijhoudingsgemeenteHistorie.size());

        for (HisPersoonBijhoudingsgemeenteModel hisPersoonBijhoudingsgemeente : bijhoudingsgemeenteHistorie) {
            if (hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getDatumTijdVerval() != null) {
                Assert.assertEquals(hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getActieVerval().getID(),
                    actie2.getID());
            } else {
                if (hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getDatumEindeGeldigheid() == null
                    && hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getDatumTijdVerval() == null)
                {
                    Assert.assertEquals(hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getActieInhoud().getID(),
                        actie2.getID());
                } else {
                    Assert.assertEquals(
                        hisPersoonBijhoudingsgemeente.getMaterieleHistorie().getActieAanpassingGeldigheid().getID(),
                        actie2.getID());
                }
            }
        }
    }


    @SuppressWarnings("serial")
    @Test
    public void testWerkbijOverlijden() {
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_OVERLIJDEN);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        // verifieer dat de persoon nog niet is overleden.
        Assert.assertEquals(null, persoon.getOverlijden());
        // verifieer dat de persoon nog niet is opgeschort.
        Assert.assertEquals(null, persoon.getOpschorting());

        // LET op: we kunnen StatischeObjecttypeBuilder niet gebruiken omdat dit geen Hibernate Object aanmaakt
        // (object zinder id te zetten).
        PersoonOverlijdenGroepBericht overlBericht = PersoonBuilder.bouwPersoonOverlijdenGroepbericht(
            20120102,
            em.find(Plaats.class, 1301), // Scheveningen
            em.find(Partij.class, (short) 4), // Amsterdan
            em.find(Land.class, 2)); // Nederland
        // Overlijden
        PersoonOverlijdenGroepModel overlijden = new PersoonOverlijdenGroepModel(overlBericht);

        persoonRepository.werkbijOverlijden(persoon, overlijden, null, actie, new Datum(20120708));
        em.flush();
        persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon.getOverlijden());

        // test de A-Laag
        // pers.overlijdenStatusHis kunnen we niet bij, deze zou op A moeten zijn.
        Assert.assertEquals(new Datum(20120102), persoon.getOverlijden().getDatumOverlijden());


        Assert
            .assertEquals(1301, ReflectionTestUtils.getField(persoon.getOverlijden().getWoonplaatsOverlijden(), "iD"));
        Assert.assertEquals((short) 4,
            ReflectionTestUtils.getField(persoon.getOverlijden().getGemeenteOverlijden(), "iD"));
        Assert.assertEquals(2, ReflectionTestUtils.getField(persoon.getOverlijden().getLandOverlijden(), "iD"));

        // test de C-Laag
        List<HisPersoonOverlijdenModel> hisOverlijden = em.createQuery(
            "SELECT his FROM HisPersoonOverlijdenModel his where his.persoon = :pers",
            HisPersoonOverlijdenModel.class)
                                                          .setParameter("pers", persoon)
                                                          .getResultList();
        Assert.assertEquals(1, hisOverlijden.size());

        Assert.assertEquals(new Datum(20120102), hisOverlijden.get(0).getDatumOverlijden());
        Assert.assertEquals(1301, ReflectionTestUtils.getField(hisOverlijden.get(0).getWoonplaatsOverlijden(), "iD"));
        Assert
            .assertEquals((short) 4, ReflectionTestUtils.getField(hisOverlijden.get(0).getGemeenteOverlijden(), "iD"));
        Assert.assertEquals(2, ReflectionTestUtils.getField(hisOverlijden.get(0).getLandOverlijden(), "iD"));
        Assert.assertEquals(null, hisOverlijden.get(0).getFormeleHistorie().getDatumTijdVerval());
        Assert.assertEquals(actie.getTijdstipRegistratie(),
            hisOverlijden.get(0).getFormeleHistorie().getDatumTijdRegistratie());
    }

    @SuppressWarnings("serial")
    @Test(expected = IllegalArgumentException.class)
    public void testWerkbijOverlijdenMetNullOverlijden() throws Throwable {
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_OVERLIJDEN);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        try {
            persoonRepository.werkbijOverlijden(persoon, null, null, actie, new Datum(20120708));
        } catch (InvalidDataAccessApiUsageException ex) {
            throw ex.getCause();
        }
    }

    @SuppressWarnings("serial")
    @Test(expected = IllegalArgumentException.class)
    public void testWerkbijOverlijdenMetNullPersoon() throws Throwable {
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_OVERLIJDEN);
        try {
            persoonRepository.werkbijOverlijden(null, null, null, actie, new Datum(20120708));
        } catch (InvalidDataAccessApiUsageException ex) {
            throw ex.getCause();
        }
    }

    @SuppressWarnings("serial")
    @Test
    public void testWerkbijOverlijdenBuitenland1() {
        ActieModel actie = maakActie(SoortActie.REGISTRATIE_OVERLIJDEN);
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon);
        // verifieer dat de persoon nog niet is overleden.
        Assert.assertEquals(null, persoon.getOverlijden());
        // verifieer dat de persoon nog niet is opgeschort.
        Assert.assertEquals(null, persoon.getOpschorting());

        // LET op: we kunnen StatischeObjecttypeBuilder niet gebruiken omdat dit geen Hibernate Object aanmaakt
        // (object zinder id te zetten).
        PersoonOverlijdenGroepBericht overlBericht =
            PersoonBuilder.bouwPersoonOverlijdenGroepbericht(
                20020708, "BuitenlandsePlaats", "regio", em.find(Land.class, 5), null);
        // Overlijden
        PersoonOverlijdenGroepModel overlijden = new PersoonOverlijdenGroepModel(overlBericht);

        persoonRepository.werkbijOverlijden(persoon, overlijden, null, actie, new Datum(20120708));
        em.flush();
        persoon = em.find(PersoonModel.class, 1);
        Assert.assertNotNull(persoon.getOverlijden());

        // test de A-Laag
        // pers.overlijdenStatusHis kunnen we niet bij, deze zou op A moeten zijn.
        Assert.assertEquals(new Integer(20020708), persoon.getOverlijden().getDatumOverlijden().getWaarde());
        Assert.assertEquals(null, persoon.getOverlijden().getWoonplaatsOverlijden());
        Assert.assertEquals(null, persoon.getOverlijden().getGemeenteOverlijden());
        Assert.assertEquals(5, ReflectionTestUtils.getField(persoon.getOverlijden().getLandOverlijden(), "iD"));
        Assert
            .assertEquals("BuitenlandsePlaats",
                persoon.getOverlijden().getBuitenlandsePlaatsOverlijden().getWaarde());
        Assert.assertEquals("regio", persoon.getOverlijden().getBuitenlandseRegioOverlijden().getWaarde());

        // test de C-Laag
        List<HisPersoonOverlijdenModel> hisOverlijden = em.createQuery(
            "SELECT his FROM HisPersoonOverlijdenModel his where his.persoon = :pers",
            HisPersoonOverlijdenModel.class)
                                                          .setParameter("pers", persoon)
                                                          .getResultList();
        Assert.assertEquals(1, hisOverlijden.size());

        Assert.assertEquals(new Datum(20020708), hisOverlijden.get(0).getDatumOverlijden());
        Assert.assertEquals(null, hisOverlijden.get(0).getWoonplaatsOverlijden());
        Assert.assertEquals(null, hisOverlijden.get(0).getGemeenteOverlijden());
        Assert.assertEquals(5, ReflectionTestUtils.getField(hisOverlijden.get(0).getLandOverlijden(), "iD"));
        Assert.assertEquals("BuitenlandsePlaats", hisOverlijden.get(0).getBuitenlandsePlaatsOverlijden().getWaarde());
        Assert.assertEquals("regio", hisOverlijden.get(0).getBuitenlandseRegioOverlijden().getWaarde());
        Assert.assertEquals(null, hisOverlijden.get(0).getFormeleHistorie().getDatumTijdVerval());
        Assert.assertEquals(actie.getTijdstipRegistratie(),
            hisOverlijden.get(0).getFormeleHistorie().getDatumTijdRegistratie());
    }


    //    private void printAanschrijfHistRecord(final PersoonAanschrijvingHisModel his) {
//        System.out.printf("%d[%d-%d] %s,%s,%s,%s,%s,[%s][%s][%s][%s]\n"
//                , his.getID()
//                , his.getDatumAanvangGeldigheid().getOmschrijving()
//                , ((null != his.getDatumEindeGeldigheid()) ? his.getDatumEindeGeldigheid().getOmschrijving() : 0)
//                , ((null != his.getAdellijkeTitel()) ? his.getAdellijkeTitel().getOmschrijving().getOmschrijving()
// : null)
//                , ((null != his.getPredikaat()) ? his.getPredikaat().getOmschrijving().getOmschrijving() : null)
//                , ((null != his.getGebruikGeslachtsnaam()) ? his.getGebruikGeslachtsnaam().getOmschrijving() : null)
//                , ((null != his.getIndAanschrijvenMetAdellijkeTitel()) ? his.getIndAanschrijvenMetAdellijkeTitel()
// : null)
//                , ((null != his.getIndAanschrijvingAlgorthmischAfgeleid()) ? his
// .getIndAanschrijvingAlgorthmischAfgeleid() : null)
//                , ((null != his.getVoornamen()) ? his.getVoornamen().getOmschrijving() : null)
//                , ((null != his.getVoorvoegsel()) ? his.getVoorvoegsel().getOmschrijving() : null)
//                , ((null != his.getScheidingsteken()) ? his.getScheidingsteken().getOmschrijving() : null)
//                , ((null != his.getGeslachtsnaam()) ? his.getGeslachtsnaam().getOmschrijving() : null)
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


    // TODO: aanschrijving is niet materieel meer. Test moet naar formeel omgeschreven worden
    @Test
    @Ignore
    public void testWerkbijAanschrijving() {
//        ActieModel actie = maakActie(SoortActie.WIJZIGING_NAAMGEBRUIK);
//        Datum aanvangGeldigheid = new Datum(20120305);
//        PersoonModel persoonMdl = em.find(PersoonModel.class, 2);
//        Assert.assertNotNull(persoonMdl);
//        TypedQuery<HisPersoonAanschrijvingModel> queryCLaag = em.
//                createQuery(
//                        "SELECT his from HisPersoonAanschrijvingModel his "
//                                + " WHERE his.persoon.id = :id "
//                                + " AND his.materieleHistorie.datumTijdVerval is NULL "
//                                + " ORDER BY his.materieleHistorie.datumAanvangGeldigheid ",
//                                HisPersoonAanschrijvingModel.class)
//                .setParameter("id", persoonMdl.getID());
//
//        TypedQuery<HisPersoonAanschrijvingModel> queryDLaag = em.
//                createQuery(
//                        "SELECT his from HisPersoonAanschrijvingModel his "
//                                + " WHERE his.persoon.id = :id "
//                                + " AND his.historie.datumTijdVerval is NOT NULL "
//                                + " ORDER BY his.historie.datumTijdRegistratie ",
//                                HisPersoonAanschrijvingModel.class)
//                .setParameter("id", persoonMdl.getID());
//
//
//        List<HisPersoonAanschrijvingModel> aanHisListCLaag = queryCLaag.getResultList();
//        List<HisPersoonAanschrijvingModel> aanHisListDLaag = queryDLaag.getResultList();
////        printHistorie(aanHisListCLaag, "C-Laag");
////        printHistorie(aanHisListDLaag, "D-Laag");
//
//        persoonRepository.werkbijNaamGebruik(persoonMdl,
//                                             maakAanschrijvingGroep("B", WijzeGebruikGeslachtsnaam.PARTNER,
//                                                                    JaNee.NEE, JaNee.NEE, "Jan Jans", "in het", null,
//                                                                    "Peterson"),
//                                             actie, aanvangGeldigheid);
//
//        List<HisPersoonAanschrijvingModel> aanHisListCLaagNew = queryCLaag.getResultList();
//        List<HisPersoonAanschrijvingModel> aanHisListDLaagNew = queryDLaag.getResultList();
////      printHistorie(aanHisListCLaag, "C-Laag");
////      printHistorie(aanHisListDLaag, "D-Laag");
//
//        // test dat de actuele data is geupdate.
//        Assert.assertEquals("B", persoonMdl.getAanschrijving().getAdellijkeTitelAanschrijving().getOmschrijving()
// .getWaarde
// ());
//        Assert.assertEquals(null, persoonMdl.getAanschrijving().getPredikaatAanschrijving());
//        Assert.assertEquals(WijzeGebruikGeslachtsnaam.PARTNER, persoonMdl.getAanschrijving().getNaamgebruik());
//        Assert.assertEquals(JaNee.NEE, persoonMdl.getAanschrijving().getIndicatieTitelsPredikatenBijAanschrijven());
//        Assert.assertEquals(JaNee.NEE, persoonMdl.getAanschrijving().getIndicatieAanschrijvingAlgoritmischAfgeleid());
//        Assert.assertEquals("Jan Jans", persoonMdl.getAanschrijving().getVoornamenAanschrijving().getWaarde());
//        Assert.assertEquals("in het", persoonMdl.getAanschrijving().getVoorvoegselAanschrijving().getWaarde());
//        Assert.assertEquals(null, persoonMdl.getAanschrijving().getScheidingstekenAanschrijving());
//        Assert.assertEquals("Peterson", persoonMdl.getAanschrijving().getGeslachtsnaamAanschrijving().getWaarde());
//        // test dat #recs CLaag is 1 bijgekomen.
//        Assert.assertEquals(aanHisListCLaag.size() + 1, aanHisListCLaagNew.size());
//        // test dat de laatste Claag een einDatum heeft (en wel de aanvangsDatum)
//        Assert.assertEquals(aanvangGeldigheid,
//                            aanHisListCLaagNew.get(aanHisListCLaag.size() - 1).getFormeleHistorie()
// .getDatumEindeGeldigheid());
//        Assert.assertEquals(aanvangGeldigheid,
//                            aanHisListCLaagNew.get(aanHisListCLaagNew.size() - 1).getFormeleHistorie()
// .getDatumAanvangGeldigheid());
//        Assert.assertEquals(null, aanHisListCLaagNew.get(aanHisListCLaagNew.size() - 1).getFormeleHistorie()
// .getDatumEindeGeldigheid());
//
//
//        // test dat #recs DLaag is 1 bijgekomen en dat de laatste was gekomen uit de oude CLaag
//        Assert.assertEquals(aanHisListDLaag.size() + 1, aanHisListDLaagNew.size());
//        Assert.assertEquals(aanHisListCLaag.get(aanHisListCLaag.size() - 1).getID()
//                , aanHisListDLaagNew.get(aanHisListDLaagNew.size() - 1).getID());
//
    }


    @Test
    public void testGesorteerdeSetsOpPersoon() {
        PersoonModel persoon = em.find(PersoonModel.class, 8731137);
        Assert.assertNotNull(persoon);

        PersoonVoornaamModel vorigeVoornaam = null;
        for (PersoonVoornaamModel voornaam : persoon.getVoornamen()) {
            if (vorigeVoornaam != null) {
                Assert.assertTrue("Vorige voornaam is groter dan huidige in set --> Volgorde is niet goed.",
                    voornaam.compareTo(vorigeVoornaam) >= 0);
            }
            vorigeVoornaam = voornaam;
        }
    }

    private PersoonModel maakNieuwPersoon() {

        @SuppressWarnings("serial")
        PersoonModel persoon = new PersoonModel(new Persoon() {
            @Override
            public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
                // TODO Auto-generated method stub
                return new PersoonUitsluitingNLKiesrechtGroep() {

                    @Override
                    public Ja getIndicatieUitsluitingNLKiesrecht() {
                        return null;
                    }

                    @Override
                    public Datum getDatumEindeUitsluitingNLKiesrecht() {
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
                    public Voornamen getVoornamen() {
                        return new Voornamen("Marieke");
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
                    public Geslachtsnaam getGeslachtsnaam() {
                        // TODO Auto-generated method stub
                        return new Geslachtsnaam("Sneeuw");
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public JaNee getIndicatieAlgoritmischAfgeleid() {
                        return JaNee.NEE;
                    }

                    @Override
                    public JaNee getIndicatieNamenreeks() {
                        return JaNee.NEE;
                    }
                };
            }

            @Override
            public PersoonOverlijdenGroep getOverlijden() {
                // TODO Auto-generated method stub
                return new PersoonOverlijdenGroep() {

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

                    @Override
                    public Partij getGemeenteOverlijden() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Plaats getWoonplaatsOverlijden() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonOpschortingGroep getOpschorting() {
                return new PersoonOpschortingGroep() {

                    @Override
                    public RedenOpschorting getRedenOpschortingBijhouding() {
                        return RedenOpschorting.OVERLIJDEN;
                    }
                };
            }

            @Override
            public PersoonInschrijvingGroep getInschrijving() {
                // TODO Auto-generated method stub
                return new PersoonInschrijvingGroep() {

                    @Override
                    public Persoon getVorigePersoon() {
                        return null;
                    }

                    @Override
                    public Persoon getVolgendePersoon() {
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
                return new PersoonIdentificatienummersGroepModel(
                    new PersoonIdentificatienummersGroep() {

                        @Override
                        public Burgerservicenummer getBurgerservicenummer() {
                            return new Burgerservicenummer(111456789);
                        }

                        @Override
                        public ANummer getAdministratienummer() {
                            return new ANummer(1114567890L);
                        }
                    });
            }

            @Override
            public PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding() {
                return new PersoonGeslachtsaanduidingGroepModel(
                    new PersoonGeslachtsaanduidingGroep() {
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
                        return referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode(
                                Short.parseShort("0034")));
                    }

                    @Override
                    public Land getLandGeboorte() {
                        return referentieDataRepository.vindLandOpCode(new Landcode((short) 6023));
                    }

                    @Override
                    public Partij getGemeenteGeboorte() {
                        return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034")));
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
                    public BuitenlandsePlaats getBuitenlandsePlaatsGeboorte() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public LocatieOmschrijving getOmschrijvingLocatieGeboorte() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
                // TODO Auto-generated method stub
                return new PersoonEUVerkiezingenGroep() {

                    @Override
                    public JaNee getIndicatieDeelnameEUVerkiezingen() {
                        return JaNee.JA;
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
            public PersoonBijhoudingsaardGroep getBijhoudingsaard() {
                return new PersoonBijhoudingsaardGroep() {

                    @Override
                    public Bijhoudingsaard getBijhoudingsaard() {
                        return Bijhoudingsaard.INGEZETENE;
                    }
                };
            }

            @Override
            public PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeente() {
                return new PersoonBijhoudingsgemeenteGroep() {

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120101);
                    }

                    @Override
                    public Partij getBijhoudingsgemeente() {
                        return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034")));
                    }

                    @Override
                    public JaNee getIndicatieOnverwerktDocumentAanwezig() {
                        return JaNee.NEE;
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
                    public JaNee getIndicatieGegevensInOnderzoek() {
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
                    public WijzeGebruikGeslachtsnaam getNaamgebruik() {
                        return WijzeGebruikGeslachtsnaam.EIGEN_PARTNER;
                    }

                    @Override
                    public JaNee getIndicatieTitelsPredikatenBijAanschrijven() {
                        return JaNee.NEE;
                    }

                    @Override
                    public JaNee getIndicatieAanschrijvingAlgoritmischAfgeleid() {
                        return JaNee.NEE;
                    }

                    @Override
                    public Predikaat getPredikaatAanschrijving() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Voornamen getVoornamenAanschrijving() {
                        return new Voornamen("Johannes");
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitelAanschrijving() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Voorvoegsel getVoorvoegselAanschrijving() {
                        return new Voorvoegsel("van der");
                    }

                    @Override
                    public Scheidingsteken getScheidingstekenAanschrijving() {
                        return new Scheidingsteken("-");
                    }

                    @Override
                    public Geslachtsnaam getGeslachtsnaamAanschrijving() {
                        return new Geslachtsnaam("Water");
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
            public Set<? extends PersoonVoornaam> getVoornamen() {
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

            @Override
            public PersoonVerblijfstitelGroep getVerblijfstitel() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public PersoonPersoonskaartGroep getPersoonskaart() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public PersoonBijzondereVerblijfsrechtelijkePositieGroep getBijzondereVerblijfsrechtelijkePositie() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends PersoonReisdocument> getReisdocumenten() {
                // TODO Auto-generated method stub
                return null;
            }

        });

        persoon.setIdentificatienummersStatusHis(StatusHistorie.A);
        persoon.setAanschrijvingStatusHis(StatusHistorie.A);
        persoon.setBijhoudingsgemeenteStatusHis(StatusHistorie.A);
        persoon.setBijhoudingsaardStatusHis(StatusHistorie.A);
        persoon.setBijzondereVerblijfsrechtelijkePositieStatusHis(StatusHistorie.A);
        persoon.setEUVerkiezingenStatusHis(StatusHistorie.A);
        persoon.setGeboorteStatusHis(StatusHistorie.A);
        persoon.setGeslachtsaanduidingStatusHis(StatusHistorie.A);
        persoon.setImmigratieStatusHis(StatusHistorie.A);
        persoon.setInschrijvingStatusHis(StatusHistorie.A);
        persoon.setOpschortingStatusHis(StatusHistorie.A);
        persoon.setOverlijdenStatusHis(StatusHistorie.A);
        persoon.setPersoonskaartStatusHis(StatusHistorie.A);
        persoon.setSamengesteldeNaamStatusHis(StatusHistorie.A);
        persoon.setUitsluitingNLKiesrechtStatusHis(StatusHistorie.A);
        persoon.setVerblijfstitelStatusHis(StatusHistorie.A);

        persoon.getVoornamen().add(maakNieuwVoornaam(persoon, 1, "voornaam1"));
        persoon.getVoornamen().add(maakNieuwVoornaam(persoon, 2, "voornaam2"));
        persoon.getGeslachtsnaamcomponenten().add(maakNieuwGeslachtsnaamcomponent(persoon, 1, "geslachtsnaamcomp1",
            "voorvoeg1", "B", "H", ","));
        persoon.getGeslachtsnaamcomponenten().add(maakNieuwGeslachtsnaamcomponent(persoon, 2, "geslachtsnaamcomp2",
            "voorvoeg2", "G", "J", ";"));
        persoon.getAdressen().add(maakNieuwAdres(persoon, FunctieAdres.WOONADRES, "1000AB", 1));
        persoon.getAdressen().add(maakNieuwAdres(persoon, FunctieAdres.WOONADRES, "1000AB", 2));

        persoon.getNationaliteiten().add(maakNieuwNationaliteit(persoon, Short.parseShort("001"), null));
        return persoon;
    }

    @SuppressWarnings("serial")
    private PersoonVoornaamModel maakNieuwVoornaam(final PersoonModel persoon, final Integer volgnummer,
        final String voornaam)
    {
        PersoonVoornaamModel voornaamModel = new PersoonVoornaamModel(new PersoonVoornaam() {
            @Override
            public Volgnummer getVolgnummer() {
                return new Volgnummer(volgnummer);
            }

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @Override
            public PersoonVoornaamStandaardGroep getStandaard() {
                return new PersoonVoornaamStandaardGroep() {
                    @Override
                    public Voornaam getNaam() {
                        return new Voornaam(voornaam);
                    }
                };
            }
        }, persoon);

        voornaamModel.setPersoonVoornaamStatusHis(StatusHistorie.A);
        return voornaamModel;
    }

    @SuppressWarnings("serial")
    private PersoonNationaliteitModel maakNieuwNationaliteit(final PersoonModel persoon,
        final Short rdnVerkrijg, final Short rdnVerlies)
    {
        PersoonNationaliteitModel nationaliteitModel = new PersoonNationaliteitModel(new PersoonNationaliteit() {

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @Override
            public Nationaliteit getNationaliteit() {
                return referentieDataRepository.vindNationaliteitOpCode(BrpConstanten.NL_NATIONALITEIT_CODE);
            }

            @Override
            public PersoonNationaliteitStandaardGroep getStandaard() {
                return new PersoonNationaliteitStandaardGroep() {

                    @Override
                    public RedenVerliesNLNationaliteit getRedenVerlies() {
                        if (rdnVerlies != null) {
                            return referentieDataRepository.vindRedenVerliesNLNationaliteitOpCode(
                                new RedenVerliesCode(rdnVerlies));
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
                        if (rdnVerkrijg != null) {
                            return referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(
                                new RedenVerkrijgingCode(rdnVerkrijg));
                        } else {
                            return null;
                        }
                    }
                };
            }
        }, persoon);

        nationaliteitModel.setPersoonNationaliteitStatusHis(StatusHistorie.A);
        return nationaliteitModel;
    }

    @SuppressWarnings("serial")
    private PersoonGeslachtsnaamcomponentModel maakNieuwGeslachtsnaamcomponent(final PersoonModel persoon,
        final Integer volgnummer,
        final String naam,
        final String voorvoegsel,
        final String adellijkeTitelCode,
        final String predikaatCode,
        final String scheidingsteken)
    {
        PersoonGeslachtsnaamcomponentModel geslachtsnaamcomponentModel = new PersoonGeslachtsnaamcomponentModel(
            new PersoonGeslachtsnaamcomponent() {
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
                public PersoonGeslachtsnaamcomponentStandaardGroep getStandaard() {
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
                            return referentieDataRepository
                                .vindAdellijkeTitelOpCode(new AdellijkeTitelCode(adellijkeTitelCode));
                        }
                    };
                }
            }, persoon);

        geslachtsnaamcomponentModel.setPersoonGeslachtsnaamcomponentStatusHis(StatusHistorie.A);
        return geslachtsnaamcomponentModel;
    }

    @SuppressWarnings("serial")
    private PersoonAdresModel maakNieuwAdres(final PersoonModel persoon, final FunctieAdres functieAdres,
        final String postcode,
        final Integer huisnummer)
    {
        PersoonAdresModel adres = new PersoonAdresModel(new PersoonAdres() {

            @Override
            public Persoon getPersoon() {
                return persoon;
            }

            @SuppressWarnings("serial")
            @Override
            public PersoonAdresStandaardGroep getStandaard() {
                return new PersoonAdresStandaardGroep() {

                    @Override
                    public Plaats getWoonplaats() {
                        return referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode(
                                Short.parseShort("0034")));
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
                        return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034")));
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
                    public AangeverAdreshouding getAangeverAdreshouding() {
                        return null;
                    }

                    @Override
                    public AanduidingBijHuisnummer getLocatietovAdres() {
                        return null;
                    }

                    @Override
                    public JaNee getIndicatiePersoonNietAangetroffenOpAdres() {
                        return JaNee.NEE;
                    }
                };
            }
        }, persoon);
        adres.setPersoonAdresStatusHis(StatusHistorie.A);
        return adres;
    }

    private PersoonVoornaam haalopVoornaam(final String voornaam,
        final Set<PersoonVoornaamModel> voornamen)
    {
        for (PersoonVoornaam n : voornamen) {
            if (voornaam.equals(n.getStandaard().getNaam().getWaarde())) {
                return n;
            }
        }

        return null;
    }

    private PersoonGeslachtsnaamcomponentModel haalopGeslachtsnaam(final String geslachtsnaam,
        final Set<PersoonGeslachtsnaamcomponentModel> geslachtsnamen)
    {
        for (PersoonGeslachtsnaamcomponentModel n : geslachtsnamen) {
            if (geslachtsnaam.equals(n.getStandaard().getNaam().getWaarde())) {
                return n;
            }
        }

        return null;
    }

    private ActieModel maakActie(final SoortActie soortActie) {
        final AdministratieveHandelingModel admhnd = new AdministratieveHandelingModel(
            SoortAdministratieveHandeling.INSCHRIJVING_DOOR_GEBOORTE,
            referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0363"))),
            new DatumTijd(new Date()), new Ontleningstoelichting("Test"), null);

        @SuppressWarnings("serial")
        ActieModel actie = new ActieModel(new Actie() {

            @Override
            public DatumTijd getTijdstipRegistratie() {
                return new DatumTijd(
                    new Timestamp(new Date(System.currentTimeMillis() - 1).getTime()));
            }


            @Override
            public SoortActie getSoort() {
                return soortActie;
            }

            @Override
            public AdministratieveHandeling getAdministratieveHandeling() {
                return admhnd;
            }

            @Override
            public Partij getPartij() {
                return referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0363")));
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

            @Override
            public List<? extends ActieBron> getBronnen() {
                return null;
            }

            @Override
            public List<RootObject> getRootObjecten() {
                return null;
            }
        }, admhnd);
        em.persist(admhnd);
        em.persist(actie);
        return actie;
    }

}
