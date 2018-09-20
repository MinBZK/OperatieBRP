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
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonAdresRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Inject
    private HistoriePersoonAdresRepository historiePersoonAdresRepository;

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @SuppressWarnings("JpaQlInspection")
    private final String adresHistorieSql =
        "SELECT adres FROM HisPersoonAdresModel adres WHERE adres.persoonAdres.persoon.id = :persoonId ORDER BY " +
            "adres.materieleHistorie.datumAanvangGeldigheid DESC, adres.materieleHistorie.datumTijdRegistratie DESC";

    @Test
    public void testOpslaanNieuwPersoonAdres() {
        final Datum datumAanvangGeldigheid = new Datum(20120228);
        ActieModel actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.opslaanNieuwPersoonAdres(maakNieuwAdres(), actie, datumAanvangGeldigheid);

        // Haal de adres historie op
        @SuppressWarnings("unchecked")
        List<HisPersoonAdresModel> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 1001).getResultList();

        // Verwacht wordt dat er 5 items zijn in de historie, 3 in de C-laag en 2 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(5, adresHistorie.size());

        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elk historie item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());

        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);

        // C-laag - Nieuwe adres
        HisPersoonAdresModel nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid.getWaarde().intValue(),
            nieuwHisAdres.getMaterieleHistorie().getDatumAanvangGeldigheid()
                         .getWaarde().intValue());
        Assert.assertNull(nieuwHisAdres.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(new Integer(2), huidigAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter().getWaarde());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Ambtshalve", nieuwHisAdres.getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Ingeschrevene", nieuwHisAdres.getAangeverAdreshouding().getNaam().getWaarde());
        Assert.assertEquals(nieuwHisAdres.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
        Assert.assertNull(nieuwHisAdres.getMaterieleHistorie().getActieVerval());
        Assert.assertNull(nieuwHisAdres.getMaterieleHistorie().getActieAanpassingGeldigheid());

        // C-laag - Oude (ingekorte) adres
        HisPersoonAdresModel oudHisAdres = adresHistorie.get(1);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(oudHisAdres.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals(20120101,
            oudHisAdres.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120228,
            oudHisAdres.getMaterieleHistorie().getDatumEindeGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", oudHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(new Integer(1), oudHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals(oudHisAdres.getMaterieleHistorie().getActieAanpassingGeldigheid().getID(), actie.getID());
        Assert.assertNull(oudHisAdres.getMaterieleHistorie().getActieVerval());

        // D-laag - Historie van oude adres
        HisPersoonAdresModel oudHisAdresDLaag = adresHistorie.get(2);
        // Ingevulde tijd verval geeft aan dat de history item in laag D zit
        Assert.assertNotNull(oudHisAdresDLaag.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals(20120101,
            oudHisAdresDLaag.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertNull(oudHisAdresDLaag.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", oudHisAdresDLaag.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(new Integer(1), oudHisAdresDLaag.getHuisnummer().getWaarde());
        Assert.assertEquals(oudHisAdresDLaag.getMaterieleHistorie().getActieVerval().getID(), actie.getID());
    }

    @Test
    public void testOpslaanNieuwPersoonAdresMinimaalAdresObject() {
        PersoonModel persoon = em.find(PersoonModel.class, 1001);

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setStandaard(new PersoonAdresStandaardGroepBericht());

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
        persoonAdresRepository
            .voerCorrectieAdresUit(maakNieuwAdres(), actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Haal de adres history op
        List<HisPersoonAdresModel> adresHistorie =
            em.createQuery(adresHistorieSql, HisPersoonAdresModel.class).setParameter("persoonId", 1001)
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
        Assert.assertEquals(20120101, huidigAdres.getStandaard().getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", huidigAdres.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damstr", huidigAdres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(new Integer(1), huidigAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("a", huidigAdres.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals("II", huidigAdres.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3984NX", huidigAdres.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("1492", huidigAdres.getStandaard().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1581", huidigAdres.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());

        // TODO Tim: Hier moeten nog tests op inhoud, want dit gaat nog niet helemaal goed.
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresNietIngeschreven() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(maakNieuwAdres()));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetAlleenHuisnummer() {
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(1), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
                                   .isIemandIngeschrevenOpAdres(
                                           creerTestAdresVoorTestReedsBewoners(new Huisnummer(2), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(3), null, null, null)));
        // True ondanks dat db naast dit adres ook een gemdeel heeft ingevuld, maar daar wordt niet op gecontroleerd
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(4), null, null, null)));
        // True ondanks dat db naast dit adres ook een locatietovadres heeft ingevuld, maar daar wordt niet op
        // gecontroleerd
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(5), null, null, null)));
        // Indien woonplaats niet wordt meegegeven, maar wel bekend is in de db, wordt dat niet als verschillend gezien
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(6), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
                                   .isIemandIngeschrevenOpAdres(
                                           creerTestAdresVoorTestReedsBewoners(new Huisnummer(7), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(8), null, null, null)));
        Assert.assertFalse(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(9), null, null, null)));
        Assert.assertTrue(persoonAdresRepository
            .isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(10), null, null, null)));

        // En test met woonplaats
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(
            creerTestAdresVoorTestReedsBewoners(new Huisnummer(1), null, null, Short.parseShort("0034"))));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(
            creerTestAdresVoorTestReedsBewoners(new Huisnummer(6), null, null, Short.parseShort("0034"))));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(
                creerTestAdresVoorTestReedsBewoners(new Huisnummer(6), null, null, (short) 1352)));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerEnHuisletter() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(1), new Huisletter("a"), null, null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new Huisnummer(2), new Huisletter("a"), null, null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(3), new Huisletter("a"), null, null)));
        // Zonder woonplaats moet adres ook gevonden worden, ook al is woonplaats in db wel bekend
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(7), new Huisletter("b"), null, null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(7), new Huisletter("b"), null, Short.parseShort("0034"))));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new Huisnummer(7), new Huisletter("b"), null, (short) 1352)));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerEnHuisnummerToevoeging() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(1), null, new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(2), null, new Huisnummertoevoeging("I"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(3), null, new Huisnummertoevoeging("I"), null)));
        // Zonder woonplaats moet adres ook gevonden worden, ook al is woonplaats in db wel bekend
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(8), null, new Huisnummertoevoeging("II"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(8), null, new Huisnummertoevoeging("II"), Short.parseShort("0034"))));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new Huisnummer(8), null, new Huisnummertoevoeging("II"), (short) 1352)));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresMetHuisnummerHuisLetterEnHuisnummerToevoeging() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(1), new Huisletter("a"), new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(2), new Huisletter("a"), new Huisnummertoevoeging("I"), null)));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(3), new Huisletter("a"), new Huisnummertoevoeging("I"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
                new Huisnummer(9), new Huisletter("c"), new Huisnummertoevoeging("III"), null)));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(creerTestAdresVoorTestReedsBewoners(
            new Huisnummer(9), new Huisletter("c"), new Huisnummertoevoeging("III"),Short.parseShort("0034"))));
    }

    private PersoonAdresModel creerTestAdres() {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Damstraat"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Damstr"));
        paGegevens.setHuisnummer(new Huisnummer(1));
        paGegevens.setHuisletter(new Huisletter("a"));
        paGegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("II"));
        paGegevens.setPostcode(new Postcode("3984NX"));
        paGegevens.setWoonplaats(referentieDataRepository.vindWoonplaatsOpCode(
                new Woonplaatscode(Short.parseShort("0034"))));
        paGegevens.setAdresseerbaarObject(new AanduidingAdresseerbaarObject("1492"));
        paGegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1581"));
        paGegevens.setLand(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        paGegevens.setGemeente(referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034"))));
        PersoonAdresModel persoonAdres = new PersoonAdresModel(adres, null);
        ReflectionTestUtils.setField(persoonAdres, "persoonAdresStatusHis", StatusHistorie.A);
        return persoonAdres;
    }

    private PersoonAdresModel creerTestAdresVoorTestReedsBewoners(final Huisnummer huisnummer,
        final Huisletter huisletter,
        final Huisnummertoevoeging huisnummertoevoeging,
        final Short wplCode)
    {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Straatweg"));
        paGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Straatweg"));
        paGegevens.setHuisnummer(huisnummer);
        paGegevens.setHuisletter(huisletter);
        paGegevens.setHuisnummertoevoeging(huisnummertoevoeging);
        paGegevens.setPostcode(new Postcode("1555BB"));
        if (wplCode != null) {
            paGegevens
                .setWoonplaats(referentieDataRepository.vindWoonplaatsOpCode(new Woonplaatscode(wplCode)));
        }
        paGegevens.setLand(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        paGegevens.setGemeente(referentieDataRepository.vindGemeenteOpCode(new GemeenteCode(Short.parseShort("0034"))));
        PersoonAdresModel persoonAdres = new PersoonAdresModel(adres, null);
        ReflectionTestUtils.setField(persoonAdres, "persoonAdresStatusHis", StatusHistorie.A);
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
        ReflectionTestUtils.setField(adres.getStandaard(), "huisnummer", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisletter ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisletter() {
        PersoonAdresModel adres = creerTestAdres();
        // Geen huisletter
        ReflectionTestUtils.setField(adres.getStandaard(), "huisletter", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummertoevoeging ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerToevoeging() {
        PersoonAdresModel adres = creerTestAdres();
        // zonder huisnummertoevoeging
        ReflectionTestUtils.setField(adres.getStandaard(), "huisnummertoevoeging", null);

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresHuisletterVerschillend() {
        PersoonAdresModel adres = creerTestAdres();
        // Andere huisletter
        ReflectionTestUtils.setField(adres.getStandaard(), "huisletter", new Huisletter("b"));

        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresWoonplaatsVerschillend() {
        PersoonAdresModel adres = creerTestAdres();

        // Met zelfde woonplaats en de rest hetzelfde, zou uiteraard als zelfde adres moeten worden gemarkeerd
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));

        // Zonder woonplaats, maar de rest hetzelfde, zou als zelfde adres moeten worden gemarkeerd
        ReflectionTestUtils.setField(adres.getStandaard(), "woonplaats", null);
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));

        // Met andere woonplaats, maar de rest hetzelfde, zou NIET als zelfde adres moeten worden gemarkeerd
        ReflectionTestUtils
            .setField(adres.getStandaard(), "woonplaats",
                em.find(Plaats.class, 1303));
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));

        // Met woonplaats, maar de rest hetzelfde, alleen in de db geen woonplaats; zou ook als zelfde moeten
        // worden gemarkeerd
        adres = creerTestAdresVoorTestReedsBewoners(new Huisnummer(1), null, null, null);
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
        ReflectionTestUtils
            .setField(adres.getStandaard(), "woonplaats",
                em.find(Plaats.class, 1303));
        Assert.assertTrue(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderGemeente() {
        PersoonAdresModel adres = creerTestAdres();
        ReflectionTestUtils.setField(adres.getStandaard(), "gemeente", null);
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testNieuwAdresVoorPersoonZonderAdres() {
        final Datum datumAanvangGeldigheid = new Datum(20120228);
        ActieModel actie = maakActie();
        PersoonAdresModel adres = maakNieuwAdres();
        ReflectionTestUtils.setField(adres, "persoon", em.find(PersoonModel.class, 3));

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, actie, datumAanvangGeldigheid);

        // Haal de adres history op
        @SuppressWarnings("unchecked")
        List<HisPersoonAdresModel> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 3).getResultList();
        // Verwacht wordt dat er 1 item is in de history
        Assert.assertEquals(1, adresHistorie.size());
        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);
        // C-laag - Nieuwe adres
        HisPersoonAdresModel nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getMaterieleHistorie().getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid.getWaarde(),
            nieuwHisAdres.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(nieuwHisAdres.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(new Integer(2), huidigAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter().getWaarde());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals(nieuwHisAdres.getMaterieleHistorie().getActieInhoud().getID(), actie.getID());
    }

    @Test
    public void testVindHuidigAdresVoorPersoon() {
        PersoonAdresModel adres =
            persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer(123456789));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getID().intValue());
    }

    /**
     * Controleert het huidige adres uit de A-laag.
     *
     * @param huidigAdres het huidige adres uit de A-laag, na uitvoer van de verhuizing.
     */
    private void controleerHuidigAdresInALaag(final PersoonAdresModel huidigAdres) {
        Assert.assertEquals(20120229, huidigAdres.getStandaard().getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", huidigAdres.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", huidigAdres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals(new Integer(2), huidigAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("b", huidigAdres.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals("III", huidigAdres.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", huidigAdres.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", huidigAdres.getStandaard().getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", huidigAdres.getStandaard().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", huidigAdres.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());
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
        gegevens.setAdresseerbaarObject(new AanduidingAdresseerbaarObject("1753"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("1815"));
        gegevens.setLand(em.find(Land.class, 2));
        gegevens.setGemeente(em.find(Partij.class, (short) 3));
        gegevens.setRedenWijziging(em.find(RedenWijzigingAdres.class, (short) 2));
        gegevens.setAangeverAdreshouding(em.find(AangeverAdreshouding.class, (short) 3));
        gegevens.setIndicatiePersoonNietAangetroffenOpAdres(JaNee.NEE);

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setStandaard(gegevens);

        PersoonAdresModel persoonAdresModel = new PersoonAdresModel(nieuwAdres, persoon);
        persoonAdresModel.setPersoonAdresStatusHis(StatusHistorie.A);
        return persoonAdresModel;
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
        ActieBericht actieBericht = new ActieRegistratieNationaliteitBericht();

        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setPartij(em.find(Partij.class, (short) 4));

        ActieModel actie = new ActieModel(actieBericht, null);
        em.persist(actie);

        return actie;
    }

    @Test
    public void haalPersoonAdresOp() {
        PersoonAdresModel pa = em.find(PersoonAdresModel.class, 10001);

        Assert.assertEquals(new Burgerservicenummer(135867277), pa.getPersoon().getIdentificatienummers()
                                                                    .getBurgerservicenummer());
        Assert.assertEquals("Hoofd instelling", pa.getStandaard().getAangeverAdreshouding().getNaam().getWaarde());
        Assert.assertEquals("1492", pa.getStandaard().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Dorpstr", pa.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getStandaard().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getStandaard().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getStandaard().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getStandaard().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getStandaard().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getStandaard().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getStandaard().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getStandaard().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getStandaard().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getStandaard().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals(new Integer(512), pa.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getStandaard().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getStandaard().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals(AanduidingBijHuisnummer.BY, pa.getStandaard().getLocatietovAdres());
        Assert.assertEquals("Dorpstraat", pa.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getStandaard().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getStandaard().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getStandaard().getWoonplaats().getCode().toString());
        Assert.assertEquals("Almere", pa.getStandaard().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals(StatusHistorie.A, pa.getPersoonAdresStatusHis());
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
        pa.setStandaard(paGegevens);

        paGegevens.setSoort(FunctieAdres.WOONADRES);
        paGegevens.setDatumAanvangAdreshouding(new Datum(20120802));
        paGegevens.setRedenWijziging(rdnWijzig);
        paGegevens.setGemeente(gemeente);
        paGegevens.setWoonplaats(woonplaats);
        paGegevens.setLand(land);

        // opzoeken in aangeveradreshouding repository
        //paGegevens.setAangeverAdreshouding(AangeverAdreshouding()
        //        AangeverAdreshoudingIdentiteit.HOOFDINSTELLING);
        paGegevens.setAdresseerbaarObject(new AanduidingAdresseerbaarObject("AdreerbaarObject x"));
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
        paGegevens.setLocatietovAdres(AanduidingBijHuisnummer.TO);
        paGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Dorpstraat"));
        paGegevens.setPostcode(new Postcode("7812PK"));

        PersoonAdresModel persoonAdres = new PersoonAdresModel(pa, persoon);
        persoonAdres.setPersoonAdresStatusHis(StatusHistorie.A);

        em.persist(persoonAdres);
        Assert.assertTrue("Fout gegaan met wegschrijven, null Id", (persoonAdres.getID() != null));
        persoonAdres = em.find(PersoonAdresModel.class, persoonAdres.getID());

        // TODO opvragen via tabel
        //Assert.assertEquals("Hoofd instelling", pa.getStandaard().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("AdreerbaarObject x", pa.getStandaard().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Afgekorte NOR", pa.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getStandaard().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getStandaard().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getStandaard().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getStandaard().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getStandaard().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getStandaard().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120802), pa.getStandaard().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getStandaard().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getStandaard().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getStandaard().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals(new Integer(512), pa.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getStandaard().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getStandaard().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals(AanduidingBijHuisnummer.TO, pa.getStandaard().getLocatietovAdres());
        Assert.assertEquals("Dorpstraat", pa.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getStandaard().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getStandaard().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getStandaard().getWoonplaats().getCode().toString());
        Assert.assertEquals("Almere", pa.getStandaard().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals("A", persoonAdres.getPersoonAdresStatusHis().getWaarde());
    }

    @Test
    public void haalStatischeObjectenOp() {
        Land land = em.find(Land.class, 4);
        Assert.assertEquals("Frankrijk", land.getNaam().getWaarde());
        AdellijkeTitel adellijkeTitel = em.find(AdellijkeTitel.class, (short) 1);
        Assert.assertEquals("B", adellijkeTitel.getCode().getWaarde());
        AangeverAdreshouding aangeverAdreshouding = em.find(AangeverAdreshouding.class, (short) 1);
        Assert.assertEquals("Gezaghouder", aangeverAdreshouding.getNaam().getWaarde());
        Nationaliteit nationaliteit = em.find(Nationaliteit.class, 1);
        Assert.assertEquals("Onbekend", nationaliteit.getNaam().getWaarde());
        Partij party = em.find(Partij.class, (short) 1);
        Assert.assertEquals("Regering en Staten-Generaal", party.getNaam().getWaarde());
        Plaats plaats = em.find(Plaats.class, 1);
        Assert.assertEquals("Almere", plaats.getNaam().getWaarde());
        Predikaat predikaat = em.find(Predikaat.class, (short) 1);
        Assert.assertEquals("K", predikaat.getCode().getWaarde());
        //
        // RedenBeeindigingRelatie redenBeeindigingRelatie = em.find(RedenBeeindigingRelatie.class, 3);
        RedenWijzigingAdres redenWijzigingAdres = em.find(RedenWijzigingAdres.class, (short) 1);
        Assert.assertEquals("Aangifte door persoon", redenWijzigingAdres.getNaam().getWaarde());
    }

    @Test
    public void testVindHuidigWoonAdresVoorPersoon() {
        PersoonAdresModel adres =
            persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer(123456789));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getID().longValue());
    }

    @Test
    public void testAlleHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);

        List<HisPersoonAdresModel> historie = historiePersoonAdresRepository.haalopHistorie(adres, true);
        Assert.assertEquals(3, historie.size());
        Assert.assertEquals(new Integer(20120101),
            historie.get(0).getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, historie.get(0).getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(new Integer(20110102),
            historie.get(1).getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(new Integer(20120101),
            historie.get(1).getMaterieleHistorie().getDatumEindeGeldigheid().getWaarde());
        Assert.assertEquals(1328184793000L,
            historie.get(2).getMaterieleHistorie().getDatumTijdVerval().getWaarde().getTime());
        Assert.assertEquals(new Integer(20110102),
            historie.get(2).getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, historie.get(2).getMaterieleHistorie().getDatumEindeGeldigheid());

    }

    @Test
    public void haalHistorieGewijzigdeRecordsOp() throws ParseException {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);

        ActieModel actie = maakActie();

        persoonAdresRepository.voerCorrectieAdresUit(adres, actie, new Datum(20110601), new Datum(20120601));

        List<HisPersoonAdresModel> his = historiePersoonAdresRepository
            .haalHistorieGewijzigdeRecordsOp(adres.getPersoon(), actie.getTijdstipRegistratie());

        Assert.assertEquals(3, his.size());
    }

    @Test
    public void tesCLaagHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);

        List<HisPersoonAdresModel> historie = historiePersoonAdresRepository.haalopHistorie(adres, false);
        Assert.assertEquals(2, historie.size());
        Assert.assertEquals(new Integer(20120101),
            historie.get(0).getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, historie.get(0).getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(new Integer(20110102),
            historie.get(1).getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(new Integer(20120101),
            historie.get(1).getMaterieleHistorie().getDatumEindeGeldigheid().getWaarde());
    }

    @Test
    public void haalHuidigRecordUitHistorie() {
        PersoonAdresModel adres = em.find(PersoonAdresModel.class, 1101);
        Assert.assertNotNull(adres);
        HisPersoonAdresModel pah = historiePersoonAdresRepository.haalGeldigRecord(adres, new Datum(20160509));
        Assert.assertNotNull(pah);
        Integer id = pah.getID();
        Assert.assertEquals(new Integer(20120101), pah.getMaterieleHistorie().getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(null, pah.getMaterieleHistorie().getDatumEindeGeldigheid());

        // haal op met dezelfde datum
        pah = historiePersoonAdresRepository.haalGeldigRecord(adres, new Datum(20120101));
        Assert.assertEquals(id, pah.getID());

        // haal op met een dag eerder, krijgt een record eerder
        pah = historiePersoonAdresRepository.haalGeldigRecord(adres, new Datum(20111231));
        Assert.assertEquals(new Integer(1202), pah.getID());

        //haal op met een dag eerder dat de 1e inschrijviving
        pah = historiePersoonAdresRepository.haalGeldigRecord(adres, new Datum(20091231));
        Assert.assertEquals(null, pah);

        // code coverage, geen peildatum meegeven, zelfde als systeem datum.
        pah = historiePersoonAdresRepository.haalGeldigRecord(adres);
        Assert.assertEquals(id, pah.getID());
    }
}
