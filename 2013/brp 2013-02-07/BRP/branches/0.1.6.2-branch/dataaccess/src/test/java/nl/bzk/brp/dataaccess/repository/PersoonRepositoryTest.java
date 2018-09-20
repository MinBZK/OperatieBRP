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

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.OntleningsToelichting;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsGemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsVerantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsAanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsnaamCompStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatieNummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.groep.logisch.PersoonskaartGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsGemeenteGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsAanduidingGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatieNummersGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsGemeenteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsAanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonIdentificatieNummersGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsGemeenteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsVerantwoordelijkheidHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsAanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamComponentHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatieNummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonInschrijvingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOpschortingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonVoornaamHisModel;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamComponent;
import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.logisch.Verdrag;
import nl.bzk.brp.model.objecttype.logisch.basis.ActieBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonGeslachtsnaamComponentBasis;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonVoornaamBasis;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verblijfsrecht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager     em;

    @Inject
    private PartijMdlRepository partijRepository;

    @Inject
    private PersoonMdlRepository persoonMdlRepository;

    @Inject
    private ReferentieDataMdlRepository referentieDataMdlRepository;

    @Test
    public void testfindByBurgerservicenummerResultaat() {
        PersoonModel persoon = persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(1, persoon.getId().longValue());
    }

    @Test
    public void testHaalPersoonOpMetBurgerservicenummer() {
        PersoonModel persoon = persoonMdlRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("234567891"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(2, persoon.getId().longValue());

        // Test nationaliteiten collectie
        Assert.assertFalse(persoon.getNationaliteiten().isEmpty());
        Assert.assertEquals("0001",
            persoon.getNationaliteiten().iterator().next().getNationaliteit().getNationaliteitCode().getWaarde());

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
        PersoonModel persoon = persoonMdlRepository.haalPersoonOpMetBurgerservicenummer(new Burgerservicenummer("345678912"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(3, persoon.getId().longValue());

        for (BetrokkenheidModel betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().equals(SoortBetrokkenheid.KIND)) {
                RelatieModel relatie = betrokkenheid.getRelatie();
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
        List<PersoonModel> personen = persoonMdlRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer(new Burgerservicenummer(""));
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
        List<PersoonModel> personen =
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

        List<PersoonModel> personen =
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
        List<PersoonModel> personen =
            persoonMdlRepository.haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummerAanduiding("1581"));
        Assert.assertEquals(2, personen.size());
        List<Long> ids = Arrays.asList(personen.get(0).getId(), personen.get(1).getId());
        Assert.assertTrue(ids.contains(new Long(1001)));
        Assert.assertTrue(ids.contains(new Long(8731137)));
    }

    @Test
    public void testHaalPersonenOpMetAdresViaPostcodeHuisnummer() {
        List<PersoonModel> personen =
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
        List<PersoonModel> personen =
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
        ActieModel actie = maakActie();
        Datum datumAanvangGeldigheid = new Datum(20101212);

        PersoonModel persoonOperationeel = maakNieuwPersoon();
        PersoonModel persoonNieuw = persoonMdlRepository.opslaanNieuwPersoon(persoonOperationeel, actie,
                datumAanvangGeldigheid, new DatumTijd(new Timestamp(new Date().getTime())));
        Long id = persoonNieuw.getId();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        final Burgerservicenummer bsn = new Burgerservicenummer("bsnnummer");
        PersoonModel persoon =
            (PersoonModel) em.createQuery(persoonsql).setParameter("burgerservicenummer", bsn)
                    .getSingleResult();

        // Controleer de geretourneerde id
        Assert.assertEquals(persoon.getId(), id);

        // Controleer A-laag
        Assert.assertEquals(bsn, persoon.getIdentificatieNummers().getBurgerServiceNummer());
        Assert.assertEquals(new Administratienummer("anummer"), persoon.getIdentificatieNummers().getAdministratieNummer());
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());
//        Assert.assertEquals(StatusHistorie.A, persoon.getInschrijving().getStatusHistorie());
        Assert.assertEquals(new Integer(20120205), persoon.getInschrijving().getDatumInschrijving().getWaarde());

        // Voornaam
        Assert.assertEquals(2, persoon.getPersoonVoornaam().size());
        Assert.assertNotNull(haalopVoornaam("voornaam1", persoon.getPersoonVoornaam()));
        Assert.assertNotNull(haalopVoornaam("voornaam2", persoon.getPersoonVoornaam()));

        // Geslachtsnaam
        PersoonGeslachtsnaamComponentModel pg1 =
            haalopGeslachtsnaam("geslachtsnaamcomp1", persoon.getGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg1);
        Assert.assertTrue("geslachtsnaamcomp1".equals(pg1.getGegevens().getNaam().getWaarde()));
        Assert.assertTrue("voorvoeg1".equals(pg1.getGegevens().getVoorvoegsel().getWaarde()));
        Assert.assertEquals("B", pg1.getGegevens().getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("H", pg1.getGegevens().getPredikaat().getCode().getWaarde());
        Assert.assertTrue(",".equals(pg1.getGegevens().getScheidingsteken().getWaarde()));

        PersoonGeslachtsnaamComponentModel pg2 =
            haalopGeslachtsnaam("geslachtsnaamcomp2", persoon.getGeslachtsnaamcomponenten());
        Assert.assertNotNull(pg2);
        Assert.assertTrue("geslachtsnaamcomp2".equals(pg2.getGegevens().getNaam().getWaarde()));
        Assert.assertTrue("voorvoeg2".equals(pg2.getGegevens().getVoorvoegsel().getWaarde()));
        Assert.assertEquals("G", pg2.getGegevens().getAdellijkeTitel().getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("J", pg2.getGegevens().getPredikaat().getCode().getWaarde());
        Assert.assertTrue(";".equals(pg2.getGegevens().getScheidingsteken().getWaarde()));

        // Geboorte
        Assert.assertEquals(new Integer(19700101), persoon.getGeboorte().getDatumGeboorte().getWaarde());
        Assert.assertEquals("0034", persoon.getGeboorte().getGemeenteGeboorte().getGemeenteCode().getWaarde());
        Assert.assertEquals("Almeres", persoon.getGeboorte().getWoonplaatsGeboorte().getNaam().getWaarde());
        Assert.assertEquals("Afghanistan", persoon.getGeboorte().getLandGeboorte().getNaam().getWaarde());

        // Bijhoudinggemeente
        Assert.assertEquals(new Integer(20120101), persoon.getBijhoudenGemeente().getDatumInschrijvingInGemeente().getWaarde());
        Assert.assertEquals("0034", persoon.getBijhoudenGemeente().getBijhoudingsGemeente().getGemeenteCode().getWaarde());
        Assert.assertEquals(JaNee.Nee, persoon.getBijhoudenGemeente().getIndOnverwerktDocumentAanwezig());

        // Opschorting
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoon.getOpschorting().getRedenOpschorting());

        // Geslachtsaanduiding
        Assert.assertEquals(GeslachtsAanduiding.MAN, persoon.getGeslachtsAanduiding().getGeslachtsAanduiding());

        // Tijdstip laatste wijziging
        Assert.assertNotNull(persoon.getAfgeleidAdministratief().getTijdstipLaatsteWijziging());

        // Adressen
        Assert.assertEquals(2, persoon.getAdressen().size());
        boolean adres1Aanwezig = false;
        boolean adres2Aanwezig = false;
        for (PersoonAdresModel persoonAdres : persoon.getAdressen()) {
            if ("1".equals(persoonAdres.getGegevens().getHuisnummer().getWaarde())) {
                adres1Aanwezig = true;
            } else if ("2".equals(persoonAdres.getGegevens().getHuisnummer().getWaarde())) {
                adres2Aanwezig = true;
            }
        }
        Assert.assertTrue(adres1Aanwezig);
        Assert.assertTrue(adres2Aanwezig);

        // Letop: We testen hier helemaal niet op nationaliteit. We gaan vanuit dat de nationaliteit via een ander weg
        // later aan de persoon wordt toegevoegd.

        // Controlleer C-laag

        // hisVoornaam
        final String hisPersoonvoornaam =
            "SELECT hisPersoonVoornaam FROM PersoonVoornaamHisModel hisPersoonVoornaam WHERE "
            + " persoonVoornaam.persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer order by voornaam";

        @SuppressWarnings("unchecked")
        List<PersoonVoornaamHisModel> hisVoornamen =
            em.createQuery(hisPersoonvoornaam).setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
            .getResultList();
        PersoonVoornaamHisModel hisVoornaam1 = hisVoornamen.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisVoornaam1.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisVoornaam1.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisVoornaam1.getDatumTijdRegistratie());
        Assert.assertNull(hisVoornaam1.getDatumTijdVerval());
        Assert.assertEquals("voornaam1", hisVoornaam1.getVoornaam().getWaarde());
        Assert.assertEquals(hisVoornaam1.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisVoornaam1.getActieAanpassingGeldigheid());
        Assert.assertNull(hisVoornaam1.getActieVerval());

        PersoonVoornaamHisModel hisVoornaam2 = hisVoornamen.get(1);
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
            "SELECT hisGesln FROM PersoonGeslachtsnaamComponentHisModel hisGesln WHERE "
            + " persoonGeslachtsnaamcomponent.persoon.identificatieNummers.burgerServiceNummer = "
            + " :burgerservicenummer order by geslachtsnaamComponent";

        @SuppressWarnings("unchecked")
        List<PersoonGeslachtsnaamComponentHisModel> hisGeslachtsnaamcompenenten =
            em.createQuery(hisGeslachtsnaamcomp)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        PersoonGeslachtsnaamComponentHisModel hisGeslachtsNaam1 = hisGeslachtsnaamcompenenten.get(0);
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

        PersoonGeslachtsnaamComponentHisModel hisGeslachtsNaam2 = hisGeslachtsnaamcompenenten.get(1);
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
            + " persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<PersoonGeboorteHisModel> hisPersoonGeboorten =
            em.createQuery(hisPersoonGeboorte)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        PersoonGeboorteHisModel hisGeboorte = hisPersoonGeboorten.get(0);
        Assert.assertNotNull(hisGeboorte.getDatumTijdRegistratie());
        Assert.assertNull(hisGeboorte.getDatumTijdVerval());
        Assert.assertEquals("0034", hisGeboorte.getGemeenteGeboorte().getGemeenteCode().getWaarde());
        Assert.assertEquals("0034", hisGeboorte.getWoonplaatsGeboorte().getCode().getWaarde());
        Assert.assertEquals(hisGeboorte.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeboorte.getActieVerval());

        // HisBijhoudingGemeente

        final String hisBijhoudingGemeente =
            "SELECT hisBijhoudingGem FROM PersoonBijhoudingsGemeenteHisModel hisBijhoudingGem WHERE "
            + " persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonBijhoudingsGemeenteHisModel> hisPersoonBijhoudingsGemeenten =
            em.createQuery(hisBijhoudingGemeente)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        PersoonBijhoudingsGemeenteHisModel hisPersoonBijhoudingsGemeente = hisPersoonBijhoudingsGemeenten.get(0);
        Assert.assertEquals(new Integer(20120101), hisPersoonBijhoudingsGemeente
            .getDatumInschrijvingInGemeente().getWaarde());
        Assert.assertEquals("0034", hisPersoonBijhoudingsGemeente.getBijhoudingsGemeente().getGemeenteCode().getWaarde());
        Assert.assertEquals(JaNee.Nee, hisPersoonBijhoudingsGemeente.getIndOnverwerktDocumentAanwezig());
        Assert.assertEquals(new Integer(20101212), hisPersoonBijhoudingsGemeente.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(hisPersoonBijhoudingsGemeente.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonBijhoudingsGemeente.getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsGemeente.getActieVerval());

        // HisPersoonGeslachtsaanduiding
        final String hisGeslachtsaand =
            "SELECT hisGesl FROM PersoonGeslachtsAanduidingHisModel hisGesl WHERE "
            + " persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonGeslachtsAanduidingHisModel> hisGeslachtsaanduidingen =
            em.createQuery(hisGeslachtsaand)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        PersoonGeslachtsAanduidingHisModel hisGeslachtsaanduiding = hisGeslachtsaanduidingen.get(0);
        // BRPUC00115:
        Assert.assertEquals(new Integer(20101212), hisGeslachtsaanduiding.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumEindeGeldigheid());
        Assert.assertNotNull(hisGeslachtsaanduiding.getDatumTijdRegistratie());
        Assert.assertNull(hisGeslachtsaanduiding.getDatumTijdVerval());
        Assert.assertEquals(GeslachtsAanduiding.MAN, hisGeslachtsaanduiding.getGeslachtsAanduiding());
        Assert.assertEquals(hisGeslachtsaanduiding.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisGeslachtsaanduiding.getActieAanpassingGeldigheid());
        Assert.assertNull(hisGeslachtsaanduiding.getActieVerval());

        // HisPersoonInschrijving
        final String hisPersInschr =
            "SELECT hisPersInschr FROM PersoonInschrijvingHisModel hisPersInschr WHERE "
            + " persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";

        @SuppressWarnings("unchecked")
        List<PersoonInschrijvingHisModel> hisPersoonInschrijvingen =
            em.createQuery(hisPersInschr)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        Assert.assertEquals(new Integer(20120205), hisPersoonInschrijvingen.get(0).getDatumInschrijving().getWaarde());
        Assert.assertEquals(hisPersoonInschrijvingen.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonInschrijvingen.get(0).getActieVerval());




        // HisOpschorting
        final String hisPersOpschorting =
                "SELECT hisPersOpschor FROM PersoonOpschortingHisModel hisPersOpschor WHERE "
                + " persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        List<PersoonOpschortingHisModel> hisPersoonOpschortingen =
                em.createQuery(hisPersOpschorting, PersoonOpschortingHisModel.class)
                    .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                    .getResultList();
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, hisPersoonOpschortingen.get(0).getRedenOpschorting());
        Assert.assertEquals(new Integer(20101212), hisPersoonOpschortingen.get(0).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(actie.getId(), hisPersoonOpschortingen.get(0).getActieInhoud().getId());

        // HisPersoonAdres
        final String hisPersoonAdres =
            "SELECT hisPersoonAdres FROM PersoonAdresHisModel hisPersoonAdres WHERE "
            + " hisPersoonAdres.persoonAdres.persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonAdresHisModel> hisPersoonAdressen =
            em.createQuery(hisPersoonAdres)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        Assert.assertEquals(2, hisPersoonAdressen.size());
        int aantalVervallen = 0;
        for (PersoonAdresHisModel hisAdres : hisPersoonAdressen) {
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
        Assert.assertEquals(1, aantalVervallen);

        // BijhoudingsVerwantwoordelijkheid
        final String hisBijhoudingsVerwantwoordelijkheid =
            "SELECT hisPersBijhVer FROM PersoonBijhoudingsVerantwoordelijkheidHisModel hisPersBijhVer WHERE "
            + " hisPersBijhVer.persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonBijhoudingsVerantwoordelijkheidHisModel> hisPersoonBijhoudingsVerantwoordelijkheden =
            em.createQuery(hisBijhoudingsVerwantwoordelijkheid)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        Assert.assertEquals(new Integer(20101212), hisPersoonBijhoudingsVerantwoordelijkheden.get(0)
                .getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(Verantwoordelijke.COLLEGE, hisPersoonBijhoudingsVerantwoordelijkheden.get(0)
                .getVerantwoordelijke());
        Assert.assertEquals(hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonBijhoudingsVerantwoordelijkheden.get(0).getActieVerval());

        // PersoonIdentificatienummers

        final String hisPersoonIdentificatienummers =
            "SELECT hisPersIdn FROM PersoonIdentificatieNummersHisModel hisPersIdn WHERE "
            + " hisPersIdn.persoon.identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        @SuppressWarnings("unchecked")
        List<PersoonIdentificatieNummersHisModel> hisPersoonIdentificatienummersResultaat =
            em.createQuery(hisPersoonIdentificatienummers)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
                .getResultList();
        Assert.assertEquals(new Integer(20101212), hisPersoonIdentificatienummersResultaat.get(0)
                .getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals("anummer", hisPersoonIdentificatienummersResultaat.get(0)
                .getAdministratieNummer().getWaarde());
        Assert.assertEquals("bsnnummer", hisPersoonIdentificatienummersResultaat.get(0)
                .getBurgerServiceNummer().getWaarde());
        Assert.assertEquals(hisPersoonIdentificatienummersResultaat.get(0).getActieInhoud().getId(), actie.getId());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getActieAanpassingGeldigheid());
        Assert.assertNull(hisPersoonIdentificatienummersResultaat.get(0).getActieVerval());
    }

    @Test
    public void testOpslaanNieuwPersoonSamenGesteldeNaamGroep() {
        final ActieModel actie = maakActie();
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
                    public ScheidingsTeken getScheidingsteken() {
                        return new ScheidingsTeken("/");
                    }

                    @Override
                    public Predikaat getPredikaat() {
                        return referentieDataMdlRepository.vindPredikaatOpCode(new PredikaatCode("H"));
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
                    public GeslachtsnaamComponent getGeslachtsnaam() {
                        return new GeslachtsnaamComponent("geslachtsnaamafgeleid");
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        return referentieDataMdlRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("B"));
                    }
                });

        ReflectionTestUtils.setField(persoon, "samengesteldeNaam", groep);
        PersoonModel persoon1 = persoonMdlRepository.opslaanNieuwPersoon(persoon, actie,
                new Datum(20101212),
                new DatumTijd(new Timestamp(new Date().getTime())));

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE "
            + " identificatieNummers.burgerServiceNummer = :burgerservicenummer";
        PersoonModel ppersoon =
            (PersoonModel) em.createQuery(persoonsql)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnnummer"))
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
        final ActieModel actie = maakActie();
        PersoonModel persoon = maakNieuwPersoon();
        ReflectionTestUtils.setField(persoon.getIdentificatieNummers(), "burgerServiceNummer",
                new Burgerservicenummer("bsnummer2"));
        ReflectionTestUtils.setField(persoon.getGeboorte(), "woonplaatsGeboorte", null);

        Long id = persoonMdlRepository.opslaanNieuwPersoon(persoon, actie,
                new Datum(20101212), new DatumTijd(new Timestamp(new Date().getTime()))).getId();

        // Haal de persoon op
        final String persoonsql =
            "SELECT persoon FROM PersoonModel persoon WHERE "
            + " identificatieNummers.burgerServiceNummer = :burgerservicenummer";

        PersoonModel persoonCheck =
            (PersoonModel) em.createQuery(persoonsql)
                .setParameter("burgerservicenummer", new Burgerservicenummer("bsnummer2"))
                .getSingleResult();

        Assert.assertEquals(id, persoonCheck.getId());
        Assert.assertEquals(null, persoonCheck.getGeboorte().getWoonplaatsGeboorte());
    }

    @Test(expected = ObjectReedsBestaandExceptie.class)
    public void testOpslaanNieuwPersoonAlBestaand() {
        final ActieModel actie = maakActie();
        PersoonModel persoon = maakNieuwPersoon();
        ReflectionTestUtils.setField(persoon.getIdentificatieNummers(), "burgerServiceNummer",
                new Burgerservicenummer("123456789"));
        persoonMdlRepository.opslaanNieuwPersoon(persoon, actie,
                new Datum(20101212), new DatumTijd(new Timestamp(new Date().getTime())));
    }

    @SuppressWarnings("serial")
    @Test
    public void testWerkbijBijhoudingsGemeente() {
        ActieModel actie = maakActie();
        PersoonModel persoonMdl = em.find(PersoonModel.class, 1L);
        Assert.assertNotNull(persoonMdl);
        final Burgerservicenummer bsn = persoonMdl.getIdentificatieNummers().getBurgerServiceNummer();

        // Gemeente
        PersoonBijhoudingsGemeenteGroepModel bijhoudingsGemeente = new PersoonBijhoudingsGemeenteGroepModel(
                new PersoonBijhoudingsGemeenteGroepBasis() {
                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Ja;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120708);
                    }

                    @Override
                    public Partij getBijhoudingsGemeente() {
                        return referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0363"));
                    }
                });

        persoonMdlRepository.werkbijBijhoudingsGemeente(bsn, bijhoudingsGemeente, actie,
                new Datum(20120708) , new DatumTijd(new Timestamp(new Date().getTime())));

        persoonMdl = em.find(PersoonModel.class, 1L);
        Assert.assertEquals("Amsterdan", persoonMdl.getBijhoudenGemeente()
                .getBijhoudingsGemeente().getNaam().getWaarde());
        Assert.assertEquals(new Integer(20120708), persoonMdl.getBijhoudenGemeente()
                .getDatumInschrijvingInGemeente().getWaarde());

        // Controlleer historie

        final String hisbijhoudingsGemeenteSql =
            "SELECT hisBijhGem FROM PersoonBijhoudingsGemeenteHisModel hisBijhGem WHERE "
            + " hisBijhGem.persoon.id = 1 ORDER BY hisBijhGem.historie.datumAanvangGeldigheid DESC";

        TypedQuery<PersoonBijhoudingsGemeenteHisModel> typedQuery =
            em.createQuery(hisbijhoudingsGemeenteSql, PersoonBijhoudingsGemeenteHisModel.class);

        List<PersoonBijhoudingsGemeenteHisModel> bijhoudingGemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(1, bijhoudingGemeenteHistorie.size());

        Assert.assertEquals(actie.getId(), bijhoudingGemeenteHistorie.get(0).getActieInhoud().getId());
        Assert.assertEquals(null, bijhoudingGemeenteHistorie.get(0).getActieAanpassingGeldigheid());
        Assert.assertEquals(null, bijhoudingGemeenteHistorie.get(0).getActieVerval());

        // nu corrigeer de wiziging die we net hebben gedaan.
        bijhoudingsGemeente = new PersoonBijhoudingsGemeenteGroepModel(
                new PersoonBijhoudingsGemeenteGroepBasis() {
                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Nee;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120908);
                    }

                    @Override
                    public Partij getBijhoudingsGemeente() {
                        return referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034"));
                    }
                });

        ActieModel actie2 = maakActie();
        persoonMdlRepository.werkbijBijhoudingsGemeente(bsn, bijhoudingsGemeente, actie2,
                new Datum(20120908) , new DatumTijd(new Timestamp(new Date().getTime())));


        bijhoudingGemeenteHistorie = typedQuery.getResultList();
        Assert.assertEquals(3, bijhoudingGemeenteHistorie.size());

        for (PersoonBijhoudingsGemeenteHisModel hisPersoonBijhoudingsGemeente : bijhoudingGemeenteHistorie) {
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
                    public ScheidingsTeken getScheidingsteken() {
                        return new ScheidingsTeken(":");
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
                    public GeslachtsnaamComponent getGeslachtsnaam() {
                        // TODO Auto-generated method stub
                        return new GeslachtsnaamComponent("Sneeuw");
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
            }

            @Override
            public PersoonskaartGroep getPersoonsKaart() {
                // TODO Auto-generated method stub
                return new PersoonskaartGroep() {

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
                        return referentieDataMdlRepository.vindLandOpCode(new LandCode("6030"));
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
            public PersoonIdentificatieNummersGroep getIdentificatieNummers() {
                @SuppressWarnings("serial")
                PersoonIdentificatieNummersGroep idnrs = new PersoonIdentificatieNummersGroepModel(
                        new PersoonIdentificatieNummersGroepBasis()
                    {

                        @Override
                        public Burgerservicenummer getBurgerServiceNummer() {
                            return new Burgerservicenummer("bsnnummer");
                        }

                        @Override
                        public Administratienummer getAdministratieNummer() {
                            return new Administratienummer("anummer");
                        }
                    });
                return idnrs;
            }

            @Override
            public PersoonGeslachtsAanduidingGroep getGeslachtsAanduiding() {
                return new PersoonGeslachtsAanduidingGroepModel(
                        new PersoonGeslachtsAanduidingGroepBasis()
                    {
                        @Override
                        public GeslachtsAanduiding getGeslachtsAanduiding() {
                            return GeslachtsAanduiding.MAN;
                        }
                    });
            }

            @Override
            public PersoonGeboorteGroep getGeboorte() {
                return new PersoonGeboorteGroep() {
                    @Override
                    public Plaats getWoonplaatsGeboorte() {
                        return referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0034"));
                    }

                    @Override
                    public Omschrijving getOmschrijvingGeboorteLocatie() {
                        return null;
                    }

                    @Override
                    public Land getLandGeboorte() {
                        return referentieDataMdlRepository.vindLandOpCode(new LandCode("6023"));
                    }

                    @Override
                    public Partij getGemeenteGeboorte() {
                        return referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034"));
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
            public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
                // TODO Auto-generated method stub
                return new PersoonEUVerkiezingenGroep() {

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
            public PersoonBijhoudingsVerantwoordelijkheidGroep getBijhoudingVerantwoordelijke() {
                return new PersoonBijhoudingsVerantwoordelijkheidGroep() {

                    @Override
                    public Verantwoordelijke getVerantwoordelijke() {
                        return Verantwoordelijke.COLLEGE;
                    }
                };
            }

            @Override
            public PersoonBijhoudingsGemeenteGroep getBijhoudenGemeente() {
                return new PersoonBijhoudingsGemeenteGroep() {

                    @Override
                    public JaNee getIndOnverwerktDocumentAanwezig() {
                        return JaNee.Nee;
                    }

                    @Override
                    public Datum getDatumInschrijvingInGemeente() {
                        return new Datum(20120101);
                    }

                    @Override
                    public Partij getBijhoudingsGemeente() {
                        return referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034"));
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
                    public ScheidingsTeken getScheidingsteken() {
                        return new ScheidingsTeken("-");
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
                    public GeslachtsnaamComponent getGeslachtsnaam() {
                        return new GeslachtsnaamComponent("Water");
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
            public Set<? extends PersoonGeslachtsnaamComponent> getGeslachtsnaamcomponenten() {
                // TODO Auto-generated method stub
                return new HashSet<PersoonGeslachtsnaamComponent>();
            }
            @Override
            public Set<? extends PersoonIndicatie> getIndicaties() {
                // TODO Auto-generated method stub
                return new HashSet<PersoonIndicatie>();
            }

        });

        persoon.getPersoonVoornaam().add(maakNieuwVoornaam(persoon, 1, "voornaam1"));
        persoon.getPersoonVoornaam().add(maakNieuwVoornaam(persoon, 2, "voornaam2"));
        persoon.getGeslachtsnaamcomponenten().add(maakNieuwGeslachtsnaamComponent(persoon, 1, "geslachtsnaamcomp1",
            "voorvoeg1", "B", "H", ","));
        persoon.getGeslachtsnaamcomponenten().add(maakNieuwGeslachtsnaamComponent(persoon, 2, "geslachtsnaamcomp2",
                "voorvoeg2", "G", "J", ";"));
        persoon.getAdressen().add(maakNieuwAdres(persoon, FunctieAdres.WOONADRES, "1000AB", "1"));
        persoon.getAdressen().add(maakNieuwAdres(persoon, FunctieAdres.WOONADRES, "1000AB", "2"));

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
    private PersoonGeslachtsnaamComponentModel maakNieuwGeslachtsnaamComponent(final PersoonModel persoon,
            final Integer volgnummer, final String naam, final String voorvoegsel,
            final String adellijkeTitelCode, final String predikaatCode, final String scheidingsTeken)
    {
        return new PersoonGeslachtsnaamComponentModel(new PersoonGeslachtsnaamComponentBasis() {
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
            public PersoonGeslachtsnaamCompStandaardGroep getGegevens() {
                return new PersoonGeslachtsnaamCompStandaardGroep() {
                    @Override
                    public Voorvoegsel getVoorvoegsel() {
                        return new Voorvoegsel(voorvoegsel);
                    }

                    @Override
                    public ScheidingsTeken getScheidingsteken() {
                        return new ScheidingsTeken(scheidingsTeken);
                    }

                    @Override
                    public Predikaat getPredikaat() {
                        return referentieDataMdlRepository.vindPredikaatOpCode(new PredikaatCode(predikaatCode));
                    }

                    @Override
                    public GeslachtsnaamComponent getNaam() {
                        return new GeslachtsnaamComponent(naam);
                    }

                    @Override
                    public AdellijkeTitel getAdellijkeTitel() {
                        return referentieDataMdlRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode(adellijkeTitelCode));
                    }
                };
            }
        }, persoon);
    }

    @SuppressWarnings("serial")
    private PersoonAdresModel maakNieuwAdres(final PersoonModel persoon, final FunctieAdres functieAdres, final String postcode,
            final String huisnummer)
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
                        return referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0034"));
                    }

                    @Override
                    public FunctieAdres getSoort() {
                        return functieAdres;
                    }

                    @Override
                    public RedenWijzigingAdres getRedenWijziging() {
                        return referentieDataMdlRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("P"));
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
                    public LocatieTovAdres getLocatietovAdres() {
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
                        return referentieDataMdlRepository.vindLandOpCode(new LandCode("6030"));
                    }

                    @Override
                    public IdentificatiecodeNummerAanduiding getIdentificatiecodeNummeraanduiding() {
                        return new IdentificatiecodeNummerAanduiding("1815");
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
                        return referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034"));
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
                    public Adresseerbaarobject getAdresseerbaarObject() {
                        return new Adresseerbaarobject("1753");
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

    private PersoonGeslachtsnaamComponentModel haalopGeslachtsnaam(final String geslachtsnaam,
            final Set<PersoonGeslachtsnaamComponentModel> geslachtsnamen)
    {
        for (PersoonGeslachtsnaamComponentModel n : geslachtsnamen) {
            if (geslachtsnaam.equals(n.getGegevens().getNaam().getWaarde())) {
                return n;
            }
        }

        return null;
    }

    private ActieModel maakActie() {
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
                return SoortActie.REGISTRATIE_NATIONALITEIT;
            }

            @Override
            public Partij getPartij() {
                return referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0363"));
            }

            @Override
            public OntleningsToelichting getOntleningsToelichting() {
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
                return new Datum(20120212);
            }
        }
        );
        em.persist(actie);
        return actie;
    }

}
