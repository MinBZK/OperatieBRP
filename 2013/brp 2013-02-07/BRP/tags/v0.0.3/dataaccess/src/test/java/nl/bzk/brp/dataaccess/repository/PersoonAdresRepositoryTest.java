/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import org.junit.Assert;
import org.junit.Test;


public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @PersistenceContext
    private EntityManager          em;

    @Test
    public void testOpslaanNieuwPersoonAdres() {
        final int datumAanvangGeldigheid = 20120228;

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.opslaanNieuwPersoonAdres(maakNieuwAdres(), datumAanvangGeldigheid, null);

        // Haal de adres history op
        final String adresHistorieSql = "SELECT adres FROM HisPersoonAdres adres "
            + "WHERE adres.persoonAdres.persoon.id = :persoonId "
            + "ORDER BY adres.datumAanvangGeldigheid DESC, adres.datumTijdRegistratie DESC";
        @SuppressWarnings("unchecked")
        List<HisPersoonAdres> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 1001L).getResultList();

        // Verwacht wordt dat er 5 items zijn in de history, 3 in de C-laag en 2 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(5, adresHistorie.size());

        // A-laag
        PersistentPersoonAdres huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());

        // Controlleer huidige adres in de A-laag
        Assert.assertEquals(20120229, huidigAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damweg", huidigAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damwg", huidigAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("2", huidigAdres.getHuisnummer());
        Assert.assertEquals("b", huidigAdres.getHuisletter());
        Assert.assertEquals("III", huidigAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3063NB", huidigAdres.getPostcode());
        Assert.assertEquals("Kijkduin", huidigAdres.getWoonplaats().getNaam());
        Assert.assertEquals("1753", huidigAdres.getAdresseerbaarObject());
        Assert.assertEquals("1815", huidigAdres.getIdentificatiecodeNummeraanduiding());

        // C-laag - Nieuwe adres
        HisPersoonAdres nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid, nieuwHisAdres.getDatumAanvangGeldigheid().intValue());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("2", nieuwHisAdres.getHuisnummer());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding());


        // C-laag - Oude (ingekorte) adres
        HisPersoonAdres oudHisAdres = adresHistorie.get(1);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(oudHisAdres.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangGeldigheid().intValue());
        Assert.assertEquals(20120228, oudHisAdres.getDatumEindeGeldigheid().intValue());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damstraat", oudHisAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("1", oudHisAdres.getHuisnummer());

        // D-laag - Historie van oude adres
        HisPersoonAdres oudHisAdresDLaag = adresHistorie.get(2);
        // Ingevulde tijd verval geeft aan dat de history item in laag D zit
        Assert.assertNotNull(oudHisAdresDLaag.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangGeldigheid().intValue());
        Assert.assertNull(oudHisAdresDLaag.getDatumEindeGeldigheid());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damstraat", oudHisAdresDLaag.getNaamOpenbareRuimte());
        Assert.assertEquals("1", oudHisAdresDLaag.getHuisnummer());
    }

    @Test
    public void testTerugwerkendeKrachtVerhuizing() {
        final int datumAanvangGeldigheid = 20111111;
        final int datumEindeGeldigheid = 20120128;

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.opslaanNieuwPersoonAdres(maakNieuwAdres(), datumAanvangGeldigheid, datumEindeGeldigheid);

        // Haal de adres history op
        final String adresHistorieSql = "SELECT adres FROM HisPersoonAdres adres "
                + "WHERE adres.persoonAdres.persoon.id = :persoonId "
                + "ORDER BY adres.datumAanvangGeldigheid DESC, adres.datumTijdRegistratie DESC";
        @SuppressWarnings("unchecked")
        List<HisPersoonAdres> adresHistorie =
                em.createQuery(adresHistorieSql).setParameter("persoonId", 1001L).getResultList();

        // Verwacht wordt dat er 6 items zijn in de history, 3 in de C-laag en 3 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(6, adresHistorie.size());

        // A-laag
        PersistentPersoonAdres huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(5).getPersoonAdres());

        // Controlleer huidige adres in de A-laag - Deze zou niet gewijzigd moeten zijn!
        Assert.assertEquals(20120101, huidigAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damstraat", huidigAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damstr", huidigAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("1", huidigAdres.getHuisnummer());
        Assert.assertEquals("a", huidigAdres.getHuisletter());
        Assert.assertEquals("II", huidigAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3984NX", huidigAdres.getPostcode());
        Assert.assertEquals("1492", huidigAdres.getAdresseerbaarObject());
        Assert.assertEquals("1581", huidigAdres.getIdentificatiecodeNummeraanduiding());

        // TODO Tim: Hier moeten nog tests op inhoud, want dit gaat nog niet helemaal goed.
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testOpslaanNieuwPersoonAdresMetNietBestaandeGemeenteCode() {
        final int datumAanvangGeldigheid = 20120228;

        PersoonAdres adres = maakNieuwAdres();
        adres.getGemeente().setGemeentecode("000");
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null);
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testOpslaanNieuwPersoonAdresMetNietBestaandePlaatsCode() {
        final int datumAanvangGeldigheid = 20120228;

        PersoonAdres adres = maakNieuwAdres();
        adres.getWoonplaats().setWoonplaatscode("000");
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null);
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testOpslaanNieuwPersoonAdresMetNietBestaandeLandCode() {
        final int datumAanvangGeldigheid = 20120228;

        PersoonAdres adres = maakNieuwAdres();
        adres.getLand().setLandcode("000");
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null);
    }

    private PersoonAdres maakNieuwAdres() {
        PersoonAdres adres = new PersoonAdres();
        Persoon persoon = new Persoon();
        PersoonIdentificatienummers persoonIdentificatienummers = new PersoonIdentificatienummers();
        persoonIdentificatienummers.setBurgerservicenummer("100001001");
        persoon.setIdentificatienummers(persoonIdentificatienummers);
        adres.setPersoon(persoon);
        adres.setDatumAanvangAdreshouding(20120229);
        adres.setNaamOpenbareRuimte("Damweg");
        adres.setAfgekorteNaamOpenbareRuimte("Damwg");
        adres.setHuisnummer("2");
        adres.setHuisletter("b");
        adres.setHuisnummertoevoeging("III");
        adres.setPostcode("3063NB");
        Plaats woonplaats = new Plaats();
        woonplaats.setWoonplaatscode("1352");
        adres.setWoonplaats(woonplaats);
        adres.setAdresseerbaarObject("1753");
        adres.setIdentificatiecodeNummeraanduiding("1815");
        Land land = new Land();
        land.setId(2);
        land.setLandcode("6030");
        adres.setLand(land);
        adres.setPersoonAdresStatusHis("A");
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0034");
        adres.setGemeente(gemeente);

        return adres;
    }


}
