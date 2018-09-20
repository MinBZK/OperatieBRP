/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Inject
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    private final String adresHistorieSql =
        "SELECT adres FROM PersoonAdresHisModel adres WHERE adres.persoonAdres.persoon.id = :persoonId "
            + "ORDER BY adres.historie.datumAanvangGeldigheid DESC, adres.historie.datumTijdRegistratie DESC";

    @Test
    public void testOpslaanNieuwPersoonAdres() {
        final Datum datumAanvangGeldigheid = new Datum(20120228);
        ActieModel actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        PersoonAdresModel adres =
            persoonAdresRepository.opslaanNieuwPersoonAdres(maakNieuwAdres(), actie, datumAanvangGeldigheid);

        // Haal de adres history op
        @SuppressWarnings("unchecked")
        List<PersoonAdresHisModel> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 1001).getResultList();

        // Verwacht wordt dat er 5 items zijn in de history, 3 in de C-laag en 2 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(5, adresHistorie.size());

        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());

        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);

        // C-laag - Nieuwe adres
        PersoonAdresHisModel nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid.getWaarde().intValue(), nieuwHisAdres.getDatumAanvangGeldigheid()
                                                                                        .getWaarde().intValue());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(2), nieuwHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter().getWaarde());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Ambtshalve", nieuwHisAdres.getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals(AangeverAdreshoudingIdentiteit.GEZAGHOUDER, nieuwHisAdres.getAangeverAdresHouding());
        Assert.assertEquals(nieuwHisAdres.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(nieuwHisAdres.getActieVerval());
        Assert.assertNull(nieuwHisAdres.getActieAanpassingGeldigheid());

        // C-laag - Oude (ingekorte) adres
        PersoonAdresHisModel oudHisAdres = adresHistorie.get(1);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(oudHisAdres.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120228, oudHisAdres.getDatumEindeGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", oudHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(1), oudHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals(oudHisAdres.getActieAanpassingGeldigheid().getId(), actie.getId());
        Assert.assertNull(oudHisAdres.getActieVerval());

        // D-laag - Historie van oude adres
        PersoonAdresHisModel oudHisAdresDLaag = adresHistorie.get(2);
        // Ingevulde tijd verval geeft aan dat de history item in laag D zit
        Assert.assertNotNull(oudHisAdresDLaag.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertNull(oudHisAdresDLaag.getDatumEindeGeldigheid());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", oudHisAdresDLaag.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(1), oudHisAdresDLaag.getHuisnummer().getWaarde());
        Assert.assertEquals(oudHisAdresDLaag.getActieVerval().getId(), actie.getId());
    }

    @Test
    public void testOpslaanNieuwPersoonAdresMinimaalAdresObject() {
        PersoonModel persoon = em.find(PersoonModel.class, 1001);

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setGegevens(new PersoonAdresStandaardGroepBericht());

        PersoonAdresModel adres = new PersoonAdresModel(nieuwAdres, persoon);

        final Datum datumAanvangGeldigheid = new Datum(20111111);

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, maakActie(), datumAanvangGeldigheid);
    }

    @Test
    public void testTerugwerkendeKrachtCorrectie() {
        final Datum datumAanvangGeldigheid = new Datum(20111111);
        final Datum datumEindeGeldigheid = new Datum(20120128);

        ActieModel actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.voerCorrectieAdresUit(maakNieuwAdres(), actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Haal de adres history op
        List<PersoonAdresHisModel> adresHistorie =
            em.createQuery(adresHistorieSql, PersoonAdresHisModel.class).setParameter("persoonId", 1001)
              .getResultList();
        // Verwacht wordt dat er 6 items zijn in de history, 3 in de C-laag en 3 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(6, adresHistorie.size());
        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(5).getPersoonAdres());
        // Controlleer huidige adres in de A-laag - Deze zou niet gewijzigd moeten zijn!
        Assert.assertEquals(20120101, huidigAdres.getGegevens().getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", huidigAdres.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damstr", huidigAdres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(1), huidigAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("a", huidigAdres.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("II", huidigAdres.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3984NX", huidigAdres.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("1492", huidigAdres.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1581", huidigAdres.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());

        // TODO Tim: Hier moeten nog tests op inhoud, want dit gaat nog niet helemaal goed.
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresNietIngeschreven() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(maakNieuwAdres()));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetAlleenHuisnummer() {
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(1), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(2), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(3), null, null, null)));
        // True ondanks dat db naast dit adres ook een gemdeel heeft ingevuld, maar daar wordt niet op gecontroleerd
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(4), null, null, null)));
        // True ondanks dat db naast dit adres ook een locatietovadres heeft ingevuld, maar daar wordt niet op
        // gecontroleerd
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(5), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(6), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(7), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(8), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(9), null, null, null)));
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(new Huisnummer(10), null, null, null)));

        // En test met woonplaats
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(
            creerTestAdresVoorTestReedsBewoners(new Huisnummer(1), null, null, new Short((short) 34))));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(
            creerTestAdresVoorTestReedsBewoners(new Huisnummer(6), null, null, new Short((short) 34))));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerEnHuisletter() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(1), new Huisletter("a"), null, null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(2), new Huisletter("a"), null, null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(3), new Huisletter("a"), null, null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(7), new Huisletter("b"), null, null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(7), new Huisletter("b"), null, new Short((short) 34))));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerEnHuisnummerToevoeging() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(1), null, new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(2), null, new Huisnummertoevoeging("I"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(3), null, new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(8), null, new Huisnummertoevoeging("II"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(8), null, new Huisnummertoevoeging("II"), new Short((short) 34))));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerHuisLetterEnHuisnummerToevoeging() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(1), new Huisletter("a"), new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(2), new Huisletter("a"), new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(3), new Huisletter("a"), new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(9), new Huisletter("c"), new Huisnummertoevoeging("III"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(9), new Huisletter("c"), new Huisnummertoevoeging("III"), new Short((short) 34))));
    }

    private PersoonAdresModel creerTestAdres() {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Damstraat"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Damstr"));
        paGegevens.setHuisnummer(new Huisnummer(1));
        paGegevens.setHuisletter(new Huisletter("a"));
        paGegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("II"));
        paGegevens.setPostcode(new Postcode("3984NX"));
        paGegevens.setWoonplaats(referentieDataRepository.vindWoonplaatsOpCode(new PlaatsCode((short) 34)));
        paGegevens.setAdresseerbaarObject(new Adresseerbaarobject("1492"));
        paGegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1581"));
        paGegevens.setLand(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        paGegevens.setGemeente(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34)));
        PersoonAdresModel persoonAdres = new PersoonAdresModel(adres, null);
        ReflectionTestUtils.setField(persoonAdres, "statusHistorie", StatusHistorie.A);
        return persoonAdres;
    }

    private PersoonAdresModel creerTestAdresVoorTestReedsBewoners(final Huisnummer huisnummer,
        final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging, final Short wplId)
    {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Straatweg"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Straatweg"));
        paGegevens.setHuisnummer(huisnummer);
        paGegevens.setHuisletter(huisletter);
        paGegevens.setHuisnummertoevoeging(huisnummertoevoeging);
        paGegevens.setPostcode(new Postcode("1555BB"));
        if (wplId != null) {
            paGegevens.setWoonplaats(referentieDataRepository.vindWoonplaatsOpCode(new PlaatsCode(wplId)));
        }
        paGegevens.setLand(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        paGegevens.setGemeente(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34)));
        PersoonAdresModel persoonAdres = new PersoonAdresModel(adres, null);
        ReflectionTestUtils.setField(persoonAdres, "statusHistorie", StatusHistorie.A);
        return persoonAdres;
    }

    private PersoonAdresModel maakNieuwAdres2(final PersoonModel persoon) {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setGegevens(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Damweg"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Damwg"));
        paGegevens.setHuisnummer(new Huisnummer(2));
        paGegevens.setHuisletter(new Huisletter("b"));
        paGegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("III"));
        paGegevens.setPostcode(new Postcode("3063NB"));
        paGegevens.setWoonplaats(referentieDataRepository.vindWoonplaatsOpCode(new PlaatsCode((short) 1352)));
        paGegevens.setAdresseerbaarObject(new Adresseerbaarobject("1753"));
        paGegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1815"));
        paGegevens.setLand(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        paGegevens.setGemeente(referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34)));
        paGegevens.setRedenwijziging(referentieDataRepository.vindRedenWijzingAdresOpCode(
            new RedenWijzigingAdresCode("A")));
        paGegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.GEZAGHOUDER);

        // referentieDataRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("G")));
        PersoonAdresModel persoonAdres = new PersoonAdresModel(adres, persoon);
        ReflectionTestUtils.setField(persoonAdres, "statusHistorie", StatusHistorie.A);
        return persoonAdres;
    }

    @Test
    public void testIsIemandIngeschrevenOpAdres() {
        PersoonAdresModel adres = creerTestAdres();
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummer ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummer() {
        PersoonAdresModel adres = creerTestAdres();
        // Geen huisnummer
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummer", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisletter ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisletter() {
        PersoonAdresModel adres = creerTestAdres();
        // Geen huisletter
        ReflectionTestUtils.setField(adres.getGegevens(), "huisletter", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummertoevoeging ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerToevoeging() {
        PersoonAdresModel adres = creerTestAdres();
        // zonder huisnummertoevoeging
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummertoevoeging", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresHuisletterVerschillend() {
        PersoonAdresModel adres = creerTestAdres();
        // Andere huisletter
        ReflectionTestUtils.setField(adres.getGegevens(), "huisletter", new Huisletter("b"));

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderGenesteObjecten() {
        PersoonAdresModel adres = maakNieuwAdres2(em.find(PersoonModel.class, 1001));
        ReflectionTestUtils.setField(adres.getGegevens(), "gemeente", null);
        ReflectionTestUtils.setField(adres.getGegevens(), "woonplaats", null);
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testNieuwAdresVoorPersoonZonderAdres() {
        final Datum datumAanvangGeldigheid = new Datum(20120228);
        ActieModel actie = maakActie();
        PersoonAdresModel adres = maakNieuwAdres();
        ReflectionTestUtils.setField(adres, "persoon", em.find(PersoonModel.class, 3));

        // Voer de functie uit om de nieuwe adres op te slaan
        PersoonAdresModel adres1 =
            persoonAdresRepository.opslaanNieuwPersoonAdres(adres, actie, datumAanvangGeldigheid);

        // Haal de adres history op
        @SuppressWarnings("unchecked")
        List<PersoonAdresHisModel> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 3).getResultList();
        // Verwacht wordt dat er 1 item is in de history
        Assert.assertEquals(1, adresHistorie.size());
        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);
        // C-laag - Nieuwe adres
        PersoonAdresHisModel nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid.getWaarde(), nieuwHisAdres.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(2), nieuwHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter().getWaarde());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals(nieuwHisAdres.getActieInhoud().getId(), actie.getId());
    }

    @Test
    public void testVindHuidigAdresVoorPersoon() {
        PersoonAdresModel adres =
            persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getId().intValue());
    }

    /**
     * Controleert het huidige adres uit de A-laag.
     *
     * @param huidigAdres het huidige adres uit de A-laag, na uitvoer van de verhuizing.
     */
    private void controleerHuidigAdresInALaag(final PersoonAdresModel huidigAdres) {
        Assert.assertEquals(20120229, huidigAdres.getGegevens().getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", huidigAdres.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", huidigAdres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(Integer.valueOf(2), huidigAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("b", huidigAdres.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("III", huidigAdres.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", huidigAdres.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", huidigAdres.getGegevens().getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", huidigAdres.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", huidigAdres.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
    }

    private PersoonAdresModel maakNieuwAdres() {
        PersoonModel persoon = em.find(PersoonModel.class, 1001);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        gegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Damweg"));
        gegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Damwg"));
        gegevens.setHuisnummer(new Huisnummer(2));
        gegevens.setHuisletter(new Huisletter("b"));
        gegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("III"));
        gegevens.setPostcode(new Postcode("3063NB"));
        gegevens.setWoonplaats(em.find(Plaats.class, 1302));
        gegevens.setAdresseerbaarObject(new Adresseerbaarobject("1753"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1815"));
        gegevens.setLand(em.find(Land.class, 2));
        gegevens.setGemeente(em.find(Partij.class, (short) 3));
        gegevens.setRedenwijziging(em.find(RedenWijzigingAdres.class, (short) 2));
        gegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.GEZAGHOUDER);

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setGegevens(gegevens);

        PersoonAdresModel adres = new PersoonAdresModel(nieuwAdres, persoon);

        return adres;
    }

    // @Test
    // public void testZoekAdresMetVolledigFilter() throws Exception {
    // AdresSelectieFilter filter = new AdresSelectieFilter();
    // filter.setAdresSoorten(FunctieAdres.BRIEFADRES, FunctieAdres.WOONADRES);
    // filter.setHuisletter("h");
    // filter.setHuisnummer("123");
    // filter.setHuisnummertoevoeging("t123");
    // filter.setIdentificatiecodeNummeraanduiding("t123");
    // filter.setNaamOpenbareRuimte("t123");
    // filter.setPeilDatum(464333);
    // filter.setPostcode("Postcode");
    // filter.setWoonplaatscodes("1234", "3445", "2222");
    // persoonAdresRepository.zoekBinnelandseAdressen(filter);
    //
    // }

    private ActieModel maakActie() {
        ActieBericht actieBericht = new ActieBericht();

        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        actieBericht.setPartij(em.find(Partij.class, (short) 4));

        ActieModel actie = new ActieModel(actieBericht);
        em.persist(actie);

        return actie;
    }

    @Test
    public void haalPersoonAdresOp() {
        PersoonAdresModel pa = em.find(PersoonAdresModel.class, 10001);

        Assert.assertEquals(new Burgerservicenummer("135867277"), pa.getPersoon().getIdentificatienummers()
                                                                    .getBurgerservicenummer());
        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("1492", pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Dorpstr", pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals(Integer.valueOf(512), pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta", pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getGegevens().getWoonplaats().getCode().toString());
        Assert.assertEquals("Almeres", pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals(StatusHistorie.A, pa.getStatusHistorie());
    }

    @Test
    public void slaPersoonAdresOp() {
        PersoonModel persoon = em.find(PersoonModel.class, 1);
        RedenWijzigingAdres rdnWijzig = em.find(RedenWijzigingAdres.class, (short) 1);
        Land land = em.find(Land.class, 2);
        Plaats woonplaats = em.find(Plaats.class, 1);
        Partij gemeente = em.find(Partij.class, (short) 3);

        PersoonAdresBericht pa = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        pa.setGegevens(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120802));
        paGegevens.setRedenwijziging(rdnWijzig);
        paGegevens.setGemeente(gemeente);
        paGegevens.setWoonplaats(woonplaats);
        paGegevens.setLand(land);
        paGegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.HOOFDINSTELLING);
        paGegevens.setAdresseerbaarObject(new Adresseerbaarobject("AdreerbaarObject x"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Afgekorte NOR"));
        paGegevens.setBuitenlandsAdresRegel1(new Adresregel("Regel 1"));
        paGegevens.setBuitenlandsAdresRegel2(new Adresregel("Regel 2"));
        paGegevens.setBuitenlandsAdresRegel3(new Adresregel("Regel 3"));
        paGegevens.setBuitenlandsAdresRegel4(new Adresregel("Regel 4"));
        paGegevens.setBuitenlandsAdresRegel5(new Adresregel("Regel 5"));
        paGegevens.setBuitenlandsAdresRegel6(new Adresregel("Regel 6"));
        paGegevens.setDatumVertrekUitNederland(new Datum(12020508));
        paGegevens.setGemeentedeel(new Gemeentedeel("GemDeel"));
        paGegevens.setHuisletter(new Huisletter("a"));
        paGegevens.setHuisnummer(new Huisnummer(512));
        paGegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("IV"));
        paGegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1581"));
        paGegevens.setLocatieOmschrijving(new LocatieOmschrijving("Omschrijving"));
        paGegevens.setLocatietovAdres(new LocatieTovAdres("ta"));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Dorpstraat"));
        paGegevens.setPostcode(new Postcode("7812PK"));

        PersoonAdresModel persoonAdres = new PersoonAdresModel(pa, persoon);
        ReflectionTestUtils.setField(persoonAdres, "statusHistorie", StatusHistorie.A);

        em.persist(persoonAdres);
        Assert.assertTrue("Fout gegaan met wegschrijven, null Id", (persoonAdres.getId() != null));
        persoonAdres = em.find(PersoonAdresModel.class, persoonAdres.getId());
        System.out.println("id=" + persoonAdres.getId());

        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("AdreerbaarObject x", pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Afgekorte NOR", pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120802), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals(Integer.valueOf(512), pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta", pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getGegevens().getWoonplaats().getCode().toString());
        Assert.assertEquals("Almeres", pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals("Actueel voorkomend", persoonAdres.getStatusHistorie().getNaam());
    }

    @Test
    public void haalStatischeObjectenOp() {
        Land land = em.find(Land.class, 4);
        Assert.assertEquals("Frankrijk", land.getNaam().getWaarde());
        AdellijkeTitel adellijkeTitel = em.find(AdellijkeTitel.class, (short) 1);
        Assert.assertEquals("B", adellijkeTitel.getAdellijkeTitelCode().getWaarde());
        AangeverAdreshouding aangeverAdreshouding = em.find(AangeverAdreshouding.class, (short) 1);
        Assert.assertEquals("Gezaghouder", aangeverAdreshouding.getNaam().getWaarde());
        Nationaliteit nationaliteit = em.find(Nationaliteit.class, 1);
        Assert.assertEquals("Onbekend", nationaliteit.getNaam().getWaarde());
        Partij party = em.find(Partij.class, (short) 1);
        Assert.assertEquals("Regering en Staten-Generaal", party.getNaam().getWaarde());
        Plaats plaats = em.find(Plaats.class, 1);
        Assert.assertEquals("Almeres", plaats.getNaam().getWaarde());
        Predikaat predikaat = em.find(Predikaat.class, (short) 1);
        Assert.assertEquals("K", predikaat.getCode().getWaarde());
        //
        // RedenBeeindigingRelatie redenBeeindigingRelatie = em.find(RedenBeeindigingRelatie.class, 3);
        // System.out.println(redenBeeindigingRelatie + "," + redenBeeindigingRelatie.getCode());
        RedenWijzigingAdres redenWijzigingAdres = em.find(RedenWijzigingAdres.class, (short) 1);
        Assert.assertEquals("Aangifte door persoon", redenWijzigingAdres.getNaam().getWaarde());
    }

    @Test
    public void testVindHuidigWoonAdresVoorPersoon() {
        PersoonAdresModel adres =
            persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getId().longValue());
    }

    @Test
    public void testAlleHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);

        List<PersoonAdresHisModel> historie = persoonAdresHistorieRepository.haalopHistorie(adres, true);
        Assert.assertEquals(3, historie.size());
        Assert.assertEquals(new Integer(20120101), historie.get(0).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, historie.get(0).getDatumEindeGeldigheid());
        Assert.assertEquals(new Integer(20110102), historie.get(1).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(new Integer(20120101), historie.get(1).getDatumEindeGeldigheid().getWaarde());
        Assert.assertEquals(1328184793000L, historie.get(2).getDatumTijdVerval().getWaarde().getTime());
        Assert.assertEquals(new Integer(20110102), historie.get(2).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, historie.get(2).getDatumEindeGeldigheid());

    }

    @Test
    public void haalHistorieGewijzigdeRecordsOp() throws ParseException {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);

        ActieModel actie = maakActie();

        persoonAdresRepository.voerCorrectieAdresUit(adres, actie, new Datum(20110601), new Datum(20120601));

        List<PersoonAdresHisModel> his = persoonAdresHistorieRepository.haalHistorieGewijzigdeRecordsOp(adres.getPersoon(), actie.getTijdstipRegistratie());

        Assert.assertEquals(3, his.size());
    }

    @Test
    public void tesCLaagHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);

        List<PersoonAdresHisModel> historie = persoonAdresHistorieRepository.haalopHistorie(adres, false);
        Assert.assertEquals(2, historie.size());
        Assert.assertEquals(new Integer(20120101), historie.get(0).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, historie.get(0).getDatumEindeGeldigheid());
        Assert.assertEquals(new Integer(20110102), historie.get(1).getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(new Integer(20120101), historie.get(1).getDatumEindeGeldigheid().getWaarde());
    }

    @Test
    public void testHaalAdresEnVulAanMetHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);
        Assert.assertEquals(null, adres.getHistorie());

        // de inhoud hebben we al hierboven getest
        persoonAdresRepository.vulaanAdresMetHistorie(adres, true);
        Assert.assertEquals(3, adres.getHistorie().size());

        // de inhoud hebben we al hierboven getest
        persoonAdresRepository.vulaanAdresMetHistorie(adres, false);
        Assert.assertEquals(2, adres.getHistorie().size());
    }

    @Test
    public void testHaalAdresEnVulAanMetHistorieNull() {
        // in de praktijk gebeurt dit niet (altijd uit list<PersoonAdres>),
        // maar for the sake of completeness.
        PersoonAdresModel nieuw = persoonAdresRepository.vulaanAdresMetHistorie(null, true);
        Assert.assertEquals(null, nieuw);
    }

    @Test
    public void testHaalAdresEnVulAanMetHistoriVoorAdresZonderHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 21);
        Assert.assertNotNull(adres);
        Assert.assertEquals(null, adres.getHistorie());

        // Dit is ook een completeness test, theoretisch zou elk persoonAdres ook een historie hebben.
        // de testdata (id=21) heeft geen historie record.
        Assert.assertEquals(null, adres.getHistorie());
    }

    @Test
    public void haalHuidigRecordUitHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);
        PersoonAdresHisModel pah = persoonAdresHistorieRepository.haalGeldigRecord(adres, new Datum(20160509));
        Assert.assertNotNull(pah);
        Long id = pah.getId();
        Assert.assertEquals(new Integer(20120101), pah.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, pah.getDatumEindeGeldigheid());

        // haal op met dezelfde datum
        pah = persoonAdresHistorieRepository.haalGeldigRecord(adres, new Datum(20120101));
        Assert.assertEquals(id, pah.getId());

        // haal op met een dag eerder, krijgt een record eerder
        pah = persoonAdresHistorieRepository.haalGeldigRecord(adres, new Datum(20111231));
        Assert.assertEquals(new Long(1202L), pah.getId());

        //haal op met een dag eerder dat de 1e inschrijviving
        pah = persoonAdresHistorieRepository.haalGeldigRecord(adres, new Datum(20091231));
        Assert.assertEquals(null, pah);

        // code coverage, geen peildatum meegeven, zelfde als systeem datum.
        pah = persoonAdresHistorieRepository.haalGeldigRecord(adres);
        Assert.assertEquals(id, pah.getId());
    }
}
