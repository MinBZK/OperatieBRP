/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.VerplichteDataNietAanwezigExceptie;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.junit.Test;

/** Unit test class voor het testen van de {@link RelatieRepository} class. */
public class RelatieJpaRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private RelatieRepository relatieRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testOpslaanRelatie() {
        final int initieleAantalRelaties = countRowsInTable("kern.relatie");
        final int initieleAantalBetrokkenheden = countRowsInTable("kern.betr");
        final int initieleAantalPersonen = countRowsInTable("kern.pers");

        Relatie relatie = bouwRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, Integer.valueOf(20120325));

        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.KIND, bouwPersoon(1L, "123456789")));
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.OUDER, bouwPersoon(2L, "234567891")));
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.OUDER, bouwPersoon(3L, "345678912")));
        relatie.setBetrokkenheden(betrokkenheden);

        Long relatieId = relatieRepository.opslaanNieuweRelatie(relatie, 20120605, new Date());
        em.flush();

        Assert.assertEquals(initieleAantalRelaties + 1, countRowsInTable("kern.relatie"));
        Assert.assertEquals(initieleAantalBetrokkenheden + 3, countRowsInTable("kern.betr"));
        Assert.assertEquals(initieleAantalPersonen, countRowsInTable("kern.pers"));
        Assert.assertNotNull(relatieId);

        // Test Relatie
        List<Map<String, Object>> relaties =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.relatie WHERE id = " + relatieId);
        Assert.assertEquals(1, relaties.size());
        Assert.assertEquals("Relatie heeft niet het verwachte soort", 3, relaties.get(0).get("srt"));
        Assert.assertNull("Aanvangsdatum zou null moeten zijn voor deze relatiesoort", relaties.get(0).get("dataanv"));
        Assert.assertEquals("A", relaties.get(0).get("relatiestatushis"));

        // Test Betrokkenheid voor Kind
        List<Map<String, Object>> betrokkenhedenKind =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE betrokkene = 1 and relatie = " + relatieId);
        Assert.assertEquals(1, betrokkenhedenKind.size());

        Map<String, Object> betrokkenheidKind = betrokkenhedenKind.get(0);
        Assert.assertEquals(relatieId, betrokkenheidKind.get("relatie"));
        Assert.assertEquals("Betrokkenheid heeft niet de 'kind' soort", 3, betrokkenheidKind.get("rol"));
        Assert.assertNull("Betrokkenheid mag geen indicatie ouder hebben", betrokkenheidKind.get("indouder"));
        Assert.assertNull("Betrokkenheid mag geen indicatie gezag hebben", betrokkenheidKind.get("indouderheeftgezag"));
        Assert.assertEquals("Betrokkenheid mag geen geldige statushis hebben", "X",
            betrokkenheidKind.get("ouderstatushis"));
        Assert.assertEquals("Betrokkenheid mag geen geldige statushis hebben", "X",
            betrokkenheidKind.get("ouderlijkgezagstatushis"));

        // Test Betrokkenheid voor Ouder 1
        List<Map<String, Object>> betrokkenhedenOuder1 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE betrokkene = 2 and relatie = " + relatieId);
        Assert.assertEquals(1, betrokkenhedenOuder1.size());

        Map<String, Object> betrokkenheidOuder1 = betrokkenhedenOuder1.get(0);
        Assert.assertEquals(relatieId, betrokkenheidOuder1.get("relatie"));
        Assert.assertEquals("Betrokkenheid heeft niet de 'ouder' soort", 2, betrokkenheidOuder1.get("rol"));
        Assert.assertEquals("Betrokkenheid moet indicatie ouder hebben", true, betrokkenheidOuder1.get("indouder"));
        Assert.assertEquals("Betrokkenheid moet indicatie gezag hebben", true,
            betrokkenheidOuder1.get("indouderheeftgezag"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder1.get("ouderstatushis"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder1.get("ouderlijkgezagstatushis"));

        // Test Betrokkenheid voor Ouder 2
        List<Map<String, Object>> betrokkenhedenOuder2 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE betrokkene = 3 and relatie = " + relatieId);
        Assert.assertEquals(1, betrokkenhedenOuder2.size());

        Map<String, Object> betrokkenheidOuder2 = betrokkenhedenOuder2.get(0);
        Assert.assertEquals(relatieId, betrokkenheidOuder2.get("relatie"));
        Assert.assertEquals("Betrokkenheid heeft niet de 'ouder' soort", 2, betrokkenheidOuder2.get("rol"));
        Assert.assertEquals("Betrokkenheid moet indicatie ouder hebben", true, betrokkenheidOuder2.get("indouder"));
        Assert.assertEquals("Betrokkenheid moet indicatie gezag hebben", true,
            betrokkenheidOuder2.get("indouderheeftgezag"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder2.get("ouderstatushis"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder2.get("ouderlijkgezagstatushis"));

        // Test Historie Betrokkenheid
        List<Map<String, Object>> historieBetrokkenhedenOuder1 = simpleJdbcTemplate.queryForList(
            "SELECT * FROM kern.his_betrouder WHERE betr = " + betrokkenheidOuder1.get("id"));
        Assert.assertEquals(1, historieBetrokkenhedenOuder1.size());

        Map<String, Object> historieBetrokkenheidOuder1 = historieBetrokkenhedenOuder1.get(0);
        Assert.assertEquals(true, historieBetrokkenheidOuder1.get("indouder"));
        Assert.assertEquals("20120605", historieBetrokkenheidOuder1.get("dataanvgel").toString());
    }

    @Test(expected = VerplichteDataNietAanwezigExceptie.class)
    public void testOpslaanRelatieMetKindZonderId() {
        Relatie relatie = bouwRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, Integer.valueOf(20120325));

        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.KIND, bouwPersoon(null, "123456789")));
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.OUDER, bouwPersoon(2L, "234567891")));
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.OUDER, bouwPersoon(3L, "345678912")));
        relatie.setBetrokkenheden(betrokkenheden);

        Long relatieId = relatieRepository.opslaanNieuweRelatie(relatie, 20120605, new Date());
        em.flush();
    }

    @Test(expected = VerplichteDataNietAanwezigExceptie.class)
    public void testOpslaanRelatieZonderBetrokkenheden() {
        Relatie relatie = bouwRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, Integer.valueOf(20120325));
        relatie.setBetrokkenheden(null);

        relatieRepository.opslaanNieuweRelatie(relatie, 20120605, new Date());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOpslaanHuwelijkRelatie() {
        Relatie relatie = bouwRelatie(SoortRelatie.HUWELIJK, Integer.valueOf(20120325));

        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.PARTNER, bouwPersoon(1L, "123456789")));
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.PARTNER, bouwPersoon(2L, "234567891")));
        relatie.setBetrokkenheden(betrokkenheden);

        relatieRepository.opslaanNieuweRelatie(relatie, 20120605, new Date());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOpslaanGeregistreerdPartnerschap() {
        Relatie relatie = bouwRelatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, Integer.valueOf(20120325));

        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.PARTNER, bouwPersoon(1L, "123456789")));
        betrokkenheden.add(bouwBetrokkenheid(relatie, SoortBetrokkenheid.PARTNER, bouwPersoon(2L, "234567891")));
        relatie.setBetrokkenheden(betrokkenheden);

        relatieRepository.opslaanNieuweRelatie(relatie, 20120605, new Date());
    }

    private Persoon bouwPersoon(final Long id, final String bsn) {
        Persoon persoon = new Persoon();
        persoon.setId(id);
        persoon.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(bsn);
        return persoon;
    }

    private Betrokkenheid bouwBetrokkenheid(final Relatie relatie, final SoortBetrokkenheid soortBetrokkenheid,
        final Persoon persoon)
    {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setRelatie(relatie);
        betrokkenheid.setBetrokkene(persoon);
        betrokkenheid.setSoortBetrokkenheid(soortBetrokkenheid);
        if (SoortBetrokkenheid.OUDER == soortBetrokkenheid) {
            betrokkenheid.setIndOuder(true);
            betrokkenheid.setIndOuderHeeftGezag(true);
            betrokkenheid.setDatumAanvangOuderschap(Integer.valueOf(20060325));
        }
        return betrokkenheid;
    }

    private Relatie bouwRelatie(final SoortRelatie soortRelatie, final Integer datumAanvang,
        final Betrokkenheid... betrokkenheden)
    {
        Relatie relatie = new Relatie();
        relatie.setSoortRelatie(soortRelatie);
        if (SoortRelatie.HUWELIJK == soortRelatie || SoortRelatie.GEREGISTREERD_PARTNERSCHAP == soortRelatie) {
            relatie.setDatumAanvang(datumAanvang);
        }
        relatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
        relatie.getBetrokkenheden().addAll(Arrays.asList(betrokkenheden));
        return relatie;
    }

}
