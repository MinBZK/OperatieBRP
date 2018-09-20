/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.gedeeld.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Predikaat;
import nl.bzk.brp.model.gedeeld.RedenOpschorting;
import nl.bzk.brp.model.gedeeld.RedenWijzigingAdres;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.Verantwoordelijke;
import nl.bzk.brp.model.gedeeld.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonIndicatie;
import org.junit.Assert;
import org.junit.Test;


public class PersoonConverterTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonRepository persoonRepository;

    @Test
    public void testHaalPersoonOpMetBurgerservicenummer135867277() throws Exception {
        // Test een persoon met zo compleet mogelijk gevuld data (+nationaliteit +adres) om te testen of alles
        // goed opgehaald en geconverteerd wordt..

        // TODO: Converter zou niet een persoon uit de database moeten halen voor de test!!
        Persoon persoon = persoonRepository.haalPersoonOpMetBurgerservicenummer("135867277");
        Assert.assertNotNull(persoon);
        Assert.assertEquals(8731137, persoon.getId().longValue());

        Assert.assertNotNull("Identiteit groep groep zou niet leeg moeten zijn", persoon.getIdentiteit());
        Assert.assertEquals(new Long(8731137), persoon.getIdentiteit().getId());
        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getIdentiteit().getSoort());

        Assert.assertNotNull("Identificatie nrs groep zou niet leeg moeten zijn", persoon.getIdentificatienummers());
        Assert.assertEquals("135867277", persoon.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals("1010104351", persoon.getIdentificatienummers().getAdministratienummer());

        Assert.assertNotNull("Samengestelde naam groep zou niet leeg moeten zijn", persoon.getSamengesteldenaam());
        Assert.assertEquals("Schessl", persoon.getSamengesteldenaam().getGeslachtsnaam());
        Assert.assertEquals(AdellijkeTitel.GRAAF, persoon.getSamengesteldenaam().getAdellijkeTitel());
        Assert.assertEquals(Boolean.FALSE, persoon.getSamengesteldenaam().getIndAlgoritmischAfgeleid());
        Assert.assertEquals(Boolean.TRUE, persoon.getSamengesteldenaam().getIndNamenreeksAlsGeslachtsnaam());
        Assert.assertEquals(Predikaat.KONINKLIJKE_HOOGHEID, persoon.getSamengesteldenaam().getPredikaat());
        Assert.assertEquals("/", persoon.getSamengesteldenaam().getScheidingsTeken());
        Assert.assertEquals("Roberto Marie Henriette", persoon.getSamengesteldenaam().getVoornamen());
        Assert.assertEquals("dos", persoon.getSamengesteldenaam().getVoorvoegsel());

        Assert.assertNotNull("Samengestelde aanschrijving groep zou niet leeg moeten zijn",
            persoon.getSamengesteldeAanschrijving());
        Assert.assertEquals("Bieren", persoon.getSamengesteldeAanschrijving().getGeslachtsnaam());
        Assert.assertEquals(Boolean.FALSE,
            persoon.getSamengesteldeAanschrijving().getIndAanschrijvingMetAdellijkeTitels());
        Assert.assertEquals(Boolean.TRUE, persoon.getSamengesteldeAanschrijving().getIndAlgoritmischAfgeleid());
        Assert.assertEquals(Predikaat.HOOGHEID, persoon.getSamengesteldeAanschrijving().getPredikaat());
        Assert.assertEquals("-", persoon.getSamengesteldeAanschrijving().getScheidingsTeken());
        Assert.assertEquals("Henriette Marie", persoon.getSamengesteldeAanschrijving().getVoornamen());
        Assert.assertEquals("den", persoon.getSamengesteldeAanschrijving().getVoorvoegsel());
        Assert.assertEquals(WijzeGebruikGeslachtsnaam.EIGEN,
            persoon.getSamengesteldeAanschrijving().getWijzeGebruikGeslachtsnaam());

        Assert.assertNotNull("Geboorte groep zou niet leeg moeten zijn", persoon.getGeboorte());
        Assert.assertEquals(new Integer(12120205), persoon.getGeboorte().getDatumGeboorte());
        Assert.assertEquals("Buitenlandse plaats", persoon.getGeboorte().getBuitenlandsePlaats());
        Assert.assertEquals("Buitenladse regio", persoon.getGeboorte().getBuitenlandseRegio());
        Assert.assertEquals(new Integer(3), persoon.getGeboorte().getGemeenteGeboorte().getId());
        Assert.assertEquals(new Integer(1302), persoon.getGeboorte().getWoonplaatsGeboorte().getId());
        Assert.assertEquals(new Integer(4), persoon.getGeboorte().getLandGeboorte().getId());
        Assert.assertEquals("omschrijving Geboorte", persoon.getGeboorte().getOmschrijvingLocatie());

        Assert.assertNotNull("Overlijden groep zou niet leeg moeten zijn", persoon.getOverlijden());
        Assert.assertEquals(new Integer(12300205), persoon.getOverlijden().getDatumOverlijden());
        Assert.assertEquals("BL Plaats overlijden", persoon.getOverlijden().getBuitenlandsePlaats());
        Assert.assertEquals("Bl Regio Overlijden", persoon.getOverlijden().getBuitenlandseRegio());
        Assert.assertEquals(new Integer(1), persoon.getOverlijden().getGemeenteOverlijden().getId());
        Assert.assertEquals(new Integer(1301), persoon.getOverlijden().getWoonplaatsOverlijden().getId());
        Assert.assertEquals(new Integer(3), persoon.getOverlijden().getLandOverlijden().getId());
        Assert.assertEquals("omschrijving Overlijden", persoon.getOverlijden().getOmschrijvingLocatie());

        Assert.assertNotNull("Bijhoudingsgemeente groep zou niet leeg moeten zijn", persoon.getBijhoudingGemeente());
        Assert.assertEquals(new Integer(12120103), persoon.getBijhoudingGemeente().getDatumInschrijving());
        Assert.assertEquals(new Integer(2), persoon.getBijhoudingGemeente().getGemeente().getId());
        Assert.assertEquals(Boolean.FALSE, persoon.getBijhoudingGemeente().getIndOnverwerktDocumentAanwezig());

        Assert.assertNotNull("RedenOpschorting groep zou niet leeg moeten zijn", persoon.getRedenOpschorting());
        Assert.assertEquals(RedenOpschorting.MINISTER, persoon.getRedenOpschorting().getRedenOpschortingBijhouding());

        //TODO: verblijfsrecht, uitsluitingNLKiesrecht, persoonskaart, ...

        Assert.assertNotNull("Bijhoudingsverantwoordelijk groep zou niet leeg moeten zijn",
            persoon.getBijhoudingVerantwoordelijke());
        Assert
            .assertEquals(Verantwoordelijke.MINISTER, persoon.getBijhoudingVerantwoordelijke().getVerantwoordelijke());

        Assert.assertNotNull("Afgeleid administratief groep zou niet leeg moeten zijn",
            persoon.getAfgeleidAdministratief());
        Date laatstGewijzigd = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse("2012-02-05 11:03:12");
        Assert.assertEquals(laatstGewijzigd, persoon.getAfgeleidAdministratief().getLaatstGewijzigd());
        Assert.assertEquals(Boolean.FALSE, persoon.getAfgeleidAdministratief().getIndGegevensInOnderzoek());

        // TODO Inschrijving is nog te complex.

        // Nationaliteiten [0..n]
        Assert.assertNotNull("Nationaliteiten groep zou niet leeg moeten zijn", persoon.getNationaliteiten());
        Assert.assertFalse("Adressen groep zou niet leeg moeten zijn", persoon.getNationaliteiten().isEmpty());
        PersoonNationaliteit nat = persoon.getNationaliteiten().get(0);
        // database (PK=2: Nederland, code=1, aanvangDatum:null, eindeDatum:null)
        Assert.assertEquals("0001", nat.getNationaliteit().getCode());

        // Adressen [0..n]
        Assert.assertNotNull("Adressen groep zou niet leeg moeten zijn", persoon.getAdressen());
        Assert.assertFalse("Adressen groep zou niet leeg moeten zijn", persoon.getAdressen().isEmpty());
        PersoonAdres adres = persoon.getAdressen().iterator().next();
        // Assert.assertEquals(new Integer(10001), adres.getId());
        Assert.assertEquals(FunctieAdres.WOONADRES, adres.getSoort());
        Assert.assertEquals(new Integer(20120101), adres.getDatumAanvangAdreshouding());
        Assert.assertEquals(RedenWijzigingAdres.PERSOON, adres.getRedenWijziging());
        Assert.assertEquals(AangeverAdreshoudingIdentiteit.HOOFDINSTELLING, adres.getAangeverAdreshouding());
        Assert.assertEquals("1581", adres.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals("1492", adres.getAdresseerbaarObject());
        Assert.assertEquals(new Integer(3), adres.getGemeente().getId());
        Assert.assertEquals("Dorpstraat", adres.getNaamOpenbareRuimte());
        Assert.assertEquals("Dorpstr", adres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("GemDeel", adres.getGemeentedeel());
        Assert.assertEquals("512", adres.getHuisnummer());
        Assert.assertEquals("a", adres.getHuisletter());
        Assert.assertEquals("IV", adres.getHuisnummertoevoeging());
        Assert.assertEquals("7812PK", adres.getPostcode());
        Assert.assertEquals(new Integer(1), adres.getWoonplaats().getId());
        Assert.assertEquals("ta", adres.getLocatieTovAdres());
        Assert.assertEquals("Omschrijving", adres.getLocatieOmschrijving());
        Assert.assertEquals("Regel 1", adres.getBuitenlandsAdresRegel1());
        Assert.assertEquals("Regel 2", adres.getBuitenlandsAdresRegel2());
        Assert.assertEquals("Regel 3", adres.getBuitenlandsAdresRegel3());
        Assert.assertEquals("Regel 4", adres.getBuitenlandsAdresRegel4());
        Assert.assertEquals("Regel 5", adres.getBuitenlandsAdresRegel5());
        Assert.assertEquals("Regel 6", adres.getBuitenlandsAdresRegel6());
        Assert.assertEquals(new Integer(2), adres.getLand().getId());
        Assert.assertEquals(new Integer(12020508), adres.getDatumVertrekUitNederland());

        // Voornamen [0..n]
        // Test, dit zou gesorteerd moeten zijn op volgnr de testdata.xml is expliciet de volgorde omgekeerd.
        Assert.assertNotNull("Voornamen groep zou niet leeg moeten zijn", persoon.getPersoonVoornamen());
        Assert.assertFalse("Voornamen groep zou niet leeg moeten zijn", persoon.getPersoonVoornamen().isEmpty());
        PersoonVoornaam[] voornamen = persoon.getPersoonVoornamen().toArray(
            new PersoonVoornaam[persoon.getPersoonVoornamen().size()]);
        Assert.assertEquals("Karel", voornamen[0].getNaam());
        Assert.assertEquals("Johan", voornamen[1].getNaam());

        // GeslachtsnaamComponent [0..]
        // Test, dit zou gesorteerd moeten zijn op volgnr de testdata.xml is expliciet de volgorde omgekeerd.
        Assert.assertNotNull("Geslachtsnaamcomponent groep zou niet leeg moeten zijn",
            persoon.getGeslachtsnaamcomponenten());
        Assert.assertFalse("Geslachtsnaamcomponent groep zou niet leeg moeten zijn",
            persoon.getGeslachtsnaamcomponenten().isEmpty());
        PersoonGeslachtsnaamcomponent[] namen = persoon.getGeslachtsnaamcomponenten()
                                                       .toArray(new PersoonGeslachtsnaamcomponent[persoon
                                                           .getGeslachtsnaamcomponenten().size()]);
        Assert.assertEquals("Kriuizer", namen[0].getNaam());
        Assert.assertEquals(AdellijkeTitel.HERTOG, namen[0].getAdellijkeTitel());
        Assert.assertEquals(Predikaat.HOOGHEID, namen[0].getPredikaat());
        Assert.assertEquals(".", namen[0].getScheidingsTeken());
        Assert.assertEquals("der", namen[0].getVoorvoegsel());

        Assert.assertEquals("Jansens", namen[1].getNaam());

        //Test kopie relaties en betrokkenheden (zie testdata.xml)
        Assert.assertNotNull(persoon.getBetrokkenheden());
        Assert.assertTrue(persoon.getBetrokkenheden().size() == 2);

        boolean isGetrouwd = false;
        int aantalKinderen = 0;

        for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.OUDER) {
                aantalKinderen++;
                Assert.assertNotNull(betrokkenheid.getRelatie());
                Assert.assertEquals(3, betrokkenheid.getRelatie().getBetrokkenheden().size());
            } else if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.PARTNER) {
                isGetrouwd = true;
                Assert.assertNotNull(betrokkenheid.getRelatie());
                Assert.assertEquals(2, betrokkenheid.getRelatie().getBetrokkenheden().size());
            }
        }
        Assert.assertTrue(isGetrouwd);
        Assert.assertEquals(1, aantalKinderen);
    }

    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummer135867277() throws Exception {
        List<Persoon> personen = persoonRepository.haalPersonenMetWoonAdresOpViaBurgerservicenummer("135867277");
        Assert.assertEquals(1, personen.size());
        Persoon persoon = personen.get(0);
        Assert.assertNotNull("Identificatienummers MAG niet null", persoon.getIdentificatienummers());
        Assert.assertNotNull("GeslachtsAanduiding MAG niet null", persoon.getPersoonGeslachtsAanduiding());
        Assert.assertNotNull("Samengesteldenaam MAG niet null", persoon.getSamengesteldenaam());
        Assert.assertNotNull("Geboorte MAG niet null", persoon.getGeboorte());
        Assert.assertNotNull("AfgeleidAdministratief MAG niet null", persoon.getAfgeleidAdministratief());

        Assert.assertTrue("GeslachtsnaamComponent MOET leeg zijn", persoon.getGeslachtsnaamcomponenten().isEmpty());
        Assert.assertTrue("Voornamen MOET leeg zijn", persoon.getPersoonVoornamen().isEmpty());
        Assert.assertTrue("Nationaliteit MOET leeg zijn", persoon.getNationaliteiten().isEmpty());
        //Assert.assertNull("Reisdocumenten zou niet opgehaald moeten worden",
        //      persoon.getReisdocumenten());
        Assert.assertTrue("Betrokkenheden MAG niet leeg", !persoon.getBetrokkenheden().isEmpty());
        Assert.assertNotNull("Adressen MAG niet leeg", persoon.getAdressen());
        Assert.assertTrue("Adressen MAG niet leeg", !persoon.getAdressen().isEmpty());
        Assert.assertNotNull("Indicaties MAG niet null", persoon.getIndicaties());
        // TODO: indicaties zijn nog niet klaar.
        Assert.assertTrue("Indicaties MAG niet leeg", !persoon.getIndicaties().isEmpty());

        //Check dat van de betrokkenheden alleen de volgende groepen zijn gekopieerd:
        // - Identificatie nummers
        // - Afgeleid administratief
        for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
            Relatie relatie = betrokkenheid.getRelatie();
            for (Betrokkenheid betrokkenheidInRelatie : relatie.getBetrokkenheden()) {
                //Sla de betrokkenheid van persoon over, oftewel zoek de andere kant van de betrokkenheid van persoon,
                //oftewel de partner of de ouders of kinderen.
                if (!betrokkenheidInRelatie.getBetrokkene().getId().equals(persoon.getId())) {
                    Persoon betrokkenPersoon = betrokkenheidInRelatie.getBetrokkene();
                    Assert.assertNotNull(betrokkenPersoon.getIdentificatienummers());
                    Assert.assertNotNull(betrokkenPersoon.getAfgeleidAdministratief());

                    Assert.assertNull(betrokkenPersoon.getAdressen());
                    Assert.assertNull(betrokkenPersoon.getGeboorte());
                    Assert.assertNull(betrokkenPersoon.getSamengesteldenaam());
                    Assert.assertNull(betrokkenPersoon.getPersoonGeslachtsAanduiding());
                    Assert.assertNull(betrokkenPersoon.getBijhoudingGemeente());
                    Assert.assertNull(betrokkenPersoon.getBijhoudingVerantwoordelijke());
                    Assert.assertTrue(betrokkenPersoon.getGeslachtsnaamcomponenten().isEmpty());
                    Assert.assertNull(betrokkenPersoon.getInschrijving());
                    Assert.assertTrue(betrokkenPersoon.getIndicaties().isEmpty());
                    Assert.assertTrue(betrokkenPersoon.getNationaliteiten().isEmpty());
                    Assert.assertNull(betrokkenPersoon.getOverlijden());
                    Assert.assertTrue(betrokkenPersoon.getPersoonVoornamen().isEmpty());
                    Assert.assertNull(betrokkenPersoon.getRedenOpschorting());
                    Assert.assertNull(betrokkenPersoon.getSamengesteldeAanschrijving());
                }
            }
        }
    }

    @Test
    public void testHaalPotentieleVader() throws Exception {

        Persoon persoon = persoonRepository.haalPersoonMetAdres(8731137L);
        Assert.assertNotNull("Identificatienummers MAG niet null", persoon.getIdentificatienummers());
        Assert.assertNotNull("GeslachtsAanduiding MAG niet null", persoon.getPersoonGeslachtsAanduiding());
        Assert.assertNotNull("Samengesteldenaam MAG niet null", persoon.getSamengesteldenaam());
        Assert.assertNotNull("Geboorte MAG niet null", persoon.getGeboorte());
        Assert.assertNotNull("AfgeleidAdministratief MAG niet null", persoon.getAfgeleidAdministratief());

        Assert.assertTrue("GeslachtsnaamComponent zou niet opgehaald moeten worden",
            persoon.getGeslachtsnaamcomponenten().isEmpty());
        Assert.assertTrue("Voornamen zou niet opgehaald moeten worden",
            persoon.getPersoonVoornamen().isEmpty());
        Assert.assertTrue("Nationaliteit zou niet opgehaald moeten worden",
            persoon.getNationaliteiten().isEmpty());
        //Assert.assertNull("Reisdocumenten zou niet opgehaald moeten worden",
        //      persoon.getReisdocumenten());
        Assert.assertTrue("betrokkenheden MOET leeg zijn", persoon.getBetrokkenheden().isEmpty());
        Assert.assertNotNull("Adressen zou WEL opgehaald moeten worden",
            persoon.getAdressen());
        Assert.assertTrue("Adressen zou WEL opgehaald moeten worden",
            !persoon.getAdressen().isEmpty());
        Assert.assertNotNull("Indicaties zou WEL opgehaald moeten worden",
            persoon.getIndicaties());
        // tenslotte testen we dat de bsn die we terug krijgen is inderdaad de partner(s)
        // 234567891 was getrouw met 135867277
        Assert.assertEquals("135867277", persoon.getIdentificatienummers().getBurgerservicenummer());
    }

    @Test
    public void testHaalPotentieleVaderNietIngeschrevene() throws Exception {
        Persoon persoon = persoonRepository.haalPersoonMetAdres(215215L);
        Assert.assertNotNull("Identificatienummers MAG niet null", persoon.getIdentificatienummers());
        Assert.assertNotNull("GeslachtsAanduiding MAG niet null", persoon.getPersoonGeslachtsAanduiding());
        Assert.assertNotNull("Samengesteldenaam MAG niet null", persoon.getSamengesteldenaam());
        Assert.assertNotNull("Geboorte MAG niet null", persoon.getGeboorte());
        Assert.assertNotNull("AfgeleidAdministratief MAG niet null", persoon.getAfgeleidAdministratief());
        Assert.assertTrue("GeslachtsnaamComponent zou niet opgehaald moeten worden",
                persoon.getGeslachtsnaamcomponenten().isEmpty());
        Assert.assertTrue("Voornamen zou niet opgehaald moeten worden",
            persoon.getPersoonVoornamen().isEmpty());
        Assert.assertTrue("Nationaliteit zou niet opgehaald moeten worden",
            persoon.getNationaliteiten().isEmpty());
        //Assert.assertNull("Reisdocumenten zou niet opgehaald moeten worden",
        //      persoon.getReisdocumenten());
        Assert.assertTrue("betrokkenheden MOET leeg zijn", persoon.getBetrokkenheden().isEmpty());
        Assert.assertNotNull("Adressen zou WEL opgehaald moeten worden",
            persoon.getAdressen());
        Assert.assertTrue("Adressen zou WEL opgehaald moeten worden",
            !persoon.getAdressen().isEmpty());
        Assert.assertNotNull("Indicaties zou WEL opgehaald moeten worden",
            persoon.getIndicaties());

        // niet ingezetene als kandidaatvader
        Assert.assertEquals(null, persoon.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals("Gaulle", persoon.getSamengesteldenaam().getGeslachtsnaam());
    }
    /**
     * Bouwt een set van {@link PersistentPersoonIndicatie}s op, waarbij de opgegeven arrays van {@link
     * SoortIndicatie}s en waardes worden gebruikt om de lijst te vullen met indicaties. Deze twee arrays dienen
     * uiteraard even groot te zijn.
     *
     * @param persoon de persoon voor wie de indicaties gelden
     * @param soorten een array met soort indicaties; voor elke soort wordt een indicatie aangemaakt met de opgegeven
     * waarde
     * @param waardes een array met waardes voor de indicaties
     * @return een set van persoon indicaties.
     */
    private Set<PersistentPersoonIndicatie> bouwPersistentPersoonIndicaties(final PersistentPersoon persoon,
        final SoortIndicatie[] soorten, final Boolean[] waardes)
    {
        Set<PersistentPersoonIndicatie> dbIndicaties = new HashSet<PersistentPersoonIndicatie>();
        for (int i = 0; i < soorten.length; i++) {
            PersistentPersoonIndicatie dbIndicatie = new PersistentPersoonIndicatie();
            dbIndicatie.setPersoon(persoon);
            dbIndicatie.setSoort(soorten[i]);
            dbIndicatie.setWaarde(waardes[i]);
            dbIndicaties.add(dbIndicatie);
        }
        return dbIndicaties;
    }

    @Test
    public void testIndicatieConversieVoorNull() {
        PersistentPersoon dbPersoon = new PersistentPersoon();
        dbPersoon.setPersoonIndicaties(null);

        Persoon persoon = PersoonConverter.converteerOperationeelNaarLogisch(dbPersoon, false);
        Assert.assertTrue(persoon.getIndicaties().isEmpty());
    }

    @Test
    public void testIndicatieConversieVoorLeeg() {
        PersistentPersoon dbPersoon = new PersistentPersoon();
        dbPersoon.setPersoonIndicaties(new HashSet<PersistentPersoonIndicatie>());

        Persoon persoon = PersoonConverter.converteerOperationeelNaarLogisch(dbPersoon, false);
        Assert.assertTrue(persoon.getIndicaties().isEmpty());
    }

    @Test
    public void testIndicatieConversieMetIndicaties() {
        PersistentPersoon dbPersoon = new PersistentPersoon();
        dbPersoon.setPersoonIndicaties(bouwPersistentPersoonIndicaties(dbPersoon,
            new SoortIndicatie[]{ SoortIndicatie.VERSTREKKINGSBEPERKING, SoortIndicatie.ONDER_CURATELE },
            new Boolean[]{ Boolean.TRUE, Boolean.FALSE }));

        Persoon persoon = PersoonConverter.converteerOperationeelNaarLogisch(dbPersoon, false);
        Assert.assertFalse(persoon.getIndicaties().isEmpty());
        Assert.assertTrue(persoon.getIndicatieWaarde(SoortIndicatie.VERSTREKKINGSBEPERKING));
        Assert.assertFalse(persoon.getIndicatieWaarde(SoortIndicatie.ONDER_CURATELE));
        Assert.assertNull(persoon.getIndicatieWaarde(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT));
    }

    @Test
    public void testOphalenPesoonNietIngeschreveneMetId215215KopieertAlleenDeJuisteGroepen() {
        final PersistentPersoon ppersoon = persoonRepository.findByBurgerservicenummer("100001001");
        PersoonConverterConfiguratie config = new PersoonConverterConfiguratie();
        Persoon persoon = PersoonConverter.converteerOperationeelNaarLogisch(ppersoon, true, config, true);

        //Controleer dat alleen de volgende groepen aanwezig zijn:
        // - Geboorte
        // - Samengestelde naam
        // - GeslachtsAanduiding
        for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
            Relatie relatie = betrokkenheid.getRelatie();
            for (Betrokkenheid betrokkenheidInRelatie : relatie.getBetrokkenheden()) {
                //Sla de betrokkenheid van persoon over, oftewel zoek de andere kant van de betrokkenheid van persoon,
                //oftewel de partner of de ouders of kinderen.
                if (!betrokkenheidInRelatie.getBetrokkene().getId().equals(persoon.getId())) {
                    Persoon partner = betrokkenheidInRelatie.getBetrokkene();
                    Assert.assertNotNull(partner);
                    Assert.assertNotNull(partner.getGeboorte());
                    Assert.assertNotNull(partner.getSamengesteldenaam());
                    Assert.assertNotNull(partner.getPersoonGeslachtsAanduiding());

                    Assert.assertNull(partner.getIdentificatienummers());
                    Assert.assertNull(partner.getAfgeleidAdministratief());
                    Assert.assertNull(partner.getAdressen());
                    Assert.assertNull(partner.getBijhoudingGemeente());
                    Assert.assertNull(partner.getBijhoudingVerantwoordelijke());
                    Assert.assertTrue(partner.getGeslachtsnaamcomponenten().isEmpty());
                    Assert.assertNull(partner.getInschrijving());
                    Assert.assertTrue(partner.getIndicaties().isEmpty());
                    Assert.assertTrue(partner.getNationaliteiten().isEmpty());
                    Assert.assertNull(partner.getOverlijden());
                    Assert.assertTrue(partner.getPersoonVoornamen().isEmpty());
                    Assert.assertNull(partner.getRedenOpschorting());
                    Assert.assertNull(partner.getSamengesteldeAanschrijving());
                }
            }
        }
    }
}
