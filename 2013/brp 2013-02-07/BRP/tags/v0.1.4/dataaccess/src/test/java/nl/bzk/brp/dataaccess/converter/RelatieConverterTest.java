/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.RedenBeeindigingRelatie;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import org.junit.Test;

/**
 * Unit test class voor de {@link RelatieConverter} class, waarin de verschillende methodes van deze class worden
 * getest.
 */
public class RelatieConverterTest {

    @Test
    public void testLogischNaarOperationeelConversieLeegObject() {
        Relatie relatie = new Relatie();
        PersistentRelatie geconverteerdeRelatie = RelatieConverter.bouwPersistentRelatie(relatie, Integer.valueOf(2));

        Assert.assertNotNull(geconverteerdeRelatie);
        Assert.assertNull(geconverteerdeRelatie.getDatumAanvang());
        Assert.assertNull(geconverteerdeRelatie.getBetrokkenheden());
    }

    @Test
    public void testLogischNaarOperationeelConversieCompleetObjectHuwelijk() {
        PersistentRelatie geconverteerdeRelatie = RelatieConverter
            .bouwPersistentRelatie(bouwLogischCompleteRelatie(SoortRelatie.HUWELIJK, null), Integer.valueOf(20));

        Assert.assertNotNull(geconverteerdeRelatie);
        Assert.assertEquals(Integer.valueOf(20), geconverteerdeRelatie.getDatumAanvang());
        Assert.assertEquals(SoortRelatie.HUWELIJK, geconverteerdeRelatie.getSoortRelatie());
        Assert.assertEquals(Integer.valueOf(3), geconverteerdeRelatie.getDatumEinde());
        Assert.assertEquals("l aanv", geconverteerdeRelatie.getLandAanvang().getLandcode());
        Assert.assertEquals("l eind", geconverteerdeRelatie.getLandEinde().getLandcode());
        Assert.assertEquals("g aanv", geconverteerdeRelatie.getGemeenteAanvang().getGemeentecode());
        Assert.assertEquals("g eind", geconverteerdeRelatie.getGemeenteEinde().getGemeentecode());
        Assert.assertEquals("p aanv", geconverteerdeRelatie.getPlaatsAanvang().getWoonplaatscode());
        Assert.assertEquals("p eind", geconverteerdeRelatie.getPlaatsEinde().getWoonplaatscode());
        Assert.assertEquals("bp aanv", geconverteerdeRelatie.getBuitenlandsePlaatsAanvang());
        Assert.assertEquals("bp eind", geconverteerdeRelatie.getBuitenlandsePlaatsEinde());
        Assert.assertEquals("br aanv", geconverteerdeRelatie.getBuitenlandseRegioAanvang());
        Assert.assertEquals("br eind", geconverteerdeRelatie.getBuitenlandseRegioEinde());
        Assert.assertEquals("ol aanv", geconverteerdeRelatie.getOmschrijvingLocatieAanvang());
        Assert.assertEquals("ol eind", geconverteerdeRelatie.getOmschrijvingLocatieEinde());
        Assert.assertNotNull(geconverteerdeRelatie.getRedenBeeindigingRelatie());

        Assert.assertNull(geconverteerdeRelatie.getBetrokkenheden());
    }

    @Test
    public void testLogischNaarOperationeelConversieCompleetObjectGeregPartnerschap() {
        PersistentRelatie geconverteerdeRelatie = RelatieConverter
            .bouwPersistentRelatie(bouwLogischCompleteRelatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, 2),
                Integer.valueOf(20));

        Assert.assertNotNull(geconverteerdeRelatie);
        Assert.assertEquals(Integer.valueOf(2), geconverteerdeRelatie.getDatumAanvang());
        Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, geconverteerdeRelatie.getSoortRelatie());
        Assert.assertEquals(Integer.valueOf(3), geconverteerdeRelatie.getDatumEinde());
        Assert.assertEquals("l aanv", geconverteerdeRelatie.getLandAanvang().getLandcode());
        Assert.assertEquals("l eind", geconverteerdeRelatie.getLandEinde().getLandcode());
        Assert.assertEquals("g aanv", geconverteerdeRelatie.getGemeenteAanvang().getGemeentecode());
        Assert.assertEquals("g eind", geconverteerdeRelatie.getGemeenteEinde().getGemeentecode());
        Assert.assertEquals("p aanv", geconverteerdeRelatie.getPlaatsAanvang().getWoonplaatscode());
        Assert.assertEquals("p eind", geconverteerdeRelatie.getPlaatsEinde().getWoonplaatscode());
        Assert.assertEquals("bp aanv", geconverteerdeRelatie.getBuitenlandsePlaatsAanvang());
        Assert.assertEquals("bp eind", geconverteerdeRelatie.getBuitenlandsePlaatsEinde());
        Assert.assertEquals("br aanv", geconverteerdeRelatie.getBuitenlandseRegioAanvang());
        Assert.assertEquals("br eind", geconverteerdeRelatie.getBuitenlandseRegioEinde());
        Assert.assertEquals("ol aanv", geconverteerdeRelatie.getOmschrijvingLocatieAanvang());
        Assert.assertEquals("ol eind", geconverteerdeRelatie.getOmschrijvingLocatieEinde());
        Assert.assertNotNull(geconverteerdeRelatie.getRedenBeeindigingRelatie());

        Assert.assertNull(geconverteerdeRelatie.getBetrokkenheden());
    }


    @Test
    public void testLogischNaarOperationeelConversieCompleetObjectFamRechtBetr() {
        PersistentRelatie geconverteerdeRelatie = RelatieConverter
            .bouwPersistentRelatie(bouwLogischCompleteRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, 2),
                Integer.valueOf(20));

        Assert.assertNotNull(geconverteerdeRelatie);
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, geconverteerdeRelatie.getSoortRelatie());

        Assert.assertNull(geconverteerdeRelatie.getDatumAanvang());
        Assert.assertNull(geconverteerdeRelatie.getDatumEinde());
        Assert.assertNull(geconverteerdeRelatie.getLandAanvang());
        Assert.assertNull(geconverteerdeRelatie.getLandEinde());
        Assert.assertNull(geconverteerdeRelatie.getGemeenteAanvang());
        Assert.assertNull(geconverteerdeRelatie.getGemeenteEinde());
        Assert.assertNull(geconverteerdeRelatie.getPlaatsAanvang());
        Assert.assertNull(geconverteerdeRelatie.getPlaatsEinde());
        Assert.assertNull(geconverteerdeRelatie.getBuitenlandsePlaatsAanvang());
        Assert.assertNull(geconverteerdeRelatie.getBuitenlandsePlaatsEinde());
        Assert.assertNull(geconverteerdeRelatie.getBuitenlandseRegioAanvang());
        Assert.assertNull(geconverteerdeRelatie.getBuitenlandseRegioEinde());
        Assert.assertNull(geconverteerdeRelatie.getOmschrijvingLocatieAanvang());
        Assert.assertNull(geconverteerdeRelatie.getOmschrijvingLocatieEinde());
        Assert.assertNull(geconverteerdeRelatie.getRedenBeeindigingRelatie());

        Assert.assertNull(geconverteerdeRelatie.getBetrokkenheden());
    }

    @Test
    public void testLogischNaarOperationeelConversieBetrokkenheden() {
        Relatie relatie = new Relatie();
        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();
        betrokkenheden.add(
            bouwBetrokkenheid(relatie, bouwPersoon(2L, "123456789"), SoortBetrokkenheid.OUDER, Integer.valueOf(4)));
        betrokkenheden
            .add(bouwBetrokkenheid(relatie, bouwPersoon(3L, "234567890"), SoortBetrokkenheid.KIND, Integer.valueOf(5)));
        betrokkenheden.add(bouwBetrokkenheid(relatie, bouwPersoon(4L, "345678901"), SoortBetrokkenheid.OUDER, null));
        relatie.setBetrokkenheden(betrokkenheden);

        PersistentRelatie geconverteerdeRelatie = RelatieConverter.bouwPersistentRelatie(relatie, Integer.valueOf(6));
        Assert.assertNotNull(geconverteerdeRelatie.getBetrokkenheden());
        for (PersistentBetrokkenheid betrokkenheid : geconverteerdeRelatie.getBetrokkenheden()) {
            Assert.assertNotNull(betrokkenheid.getSoortBetrokkenheid());
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()) {
                Assert.assertTrue(Long.valueOf(2L).equals(betrokkenheid.getBetrokkene().getId())
                    || Long.valueOf(4L).equals(betrokkenheid.getBetrokkene().getId()));
                Assert.assertNotNull(betrokkenheid.getRelatie());

                if (Long.valueOf(2L).equals(betrokkenheid.getBetrokkene().getId())) {
                    Assert.assertEquals(Integer.valueOf(4), betrokkenheid.getDatumAanvangOuderschap());
                } else if (Long.valueOf(4L).equals(betrokkenheid.getBetrokkene().getId())) {
                    // Test dat als betrokkenheid geen datum heeft, dat dan datum van bericht wordt genomen
                    Assert.assertEquals(Integer.valueOf(6), betrokkenheid.getDatumAanvangOuderschap());
                }

                Assert.assertEquals(Boolean.TRUE, betrokkenheid.isIndOuder());
                Assert.assertEquals(StatusHistorie.A, betrokkenheid.getOuderStatusHistorie());
                Assert.assertTrue(betrokkenheid.isIndOuderHeeftGezag());
                Assert.assertEquals(StatusHistorie.A, betrokkenheid.getOuderlijkGezagStatusHistorie());
            } else if (SoortBetrokkenheid.KIND == betrokkenheid.getSoortBetrokkenheid()) {
                Assert.assertEquals(Long.valueOf(3), betrokkenheid.getBetrokkene().getId());
                Assert.assertNotNull(betrokkenheid.getRelatie());
                Assert.assertNull(betrokkenheid.getDatumAanvangOuderschap());
                Assert.assertNull(betrokkenheid.isIndOuder());
                Assert.assertEquals(StatusHistorie.X, betrokkenheid.getOuderStatusHistorie());
                Assert.assertNull(betrokkenheid.isIndOuderHeeftGezag());
                Assert.assertEquals(StatusHistorie.X, betrokkenheid.getOuderlijkGezagStatusHistorie());
            }
        }
    }

    /** Bouwt een geheel gevulde relatie (excl. betrokkenheden). */
    private Relatie bouwLogischCompleteRelatie(final SoortRelatie soortRelatie, final Integer datumAanvang) {
        Relatie relatie = new Relatie();
        relatie.setSoortRelatie(soortRelatie);
        relatie.setDatumAanvang(datumAanvang);
        relatie.setDatumEinde(Integer.valueOf(3));
        relatie.setLandAanvang(bouwLand(4, "l aanv"));
        relatie.setLandEinde(bouwLand(5, "l eind"));
        relatie.setGemeenteAanvang(bouwPartij(6, "g aanv"));
        relatie.setGemeenteEinde(bouwPartij(7, "g eind"));
        relatie.setPlaatsAanvang(bouwPlaats(8, "p aanv"));
        relatie.setPlaatsEinde(bouwPlaats(9, "p eind"));
        relatie.setBuitenlandsePlaatsAanvang("bp aanv");
        relatie.setBuitenlandsePlaatsEinde("bp eind");
        relatie.setBuitenlandseRegioAanvang("br aanv");
        relatie.setBuitenlandseRegioEinde("br eind");
        relatie.setOmschrijvingLocatieAanvang("ol aanv");
        relatie.setOmschrijvingLocatieEinde("ol eind");
        relatie.setRedenBeeindigingRelatie(new RedenBeeindigingRelatie());
        return relatie;
    }

    /**
     * Bouwt een nieuwe {@link Betrokkenheid} met opgegeven relatie, betrokkene en soort. Indien de soort van het type
     * {@link SoortBetrokkenheid#OUDER} is, dan zullen ook de datumaanvang van het ouderschap alsmede de indicaties
     * voor ouder en gezag worden gezet.
     */
    private Betrokkenheid bouwBetrokkenheid(final Relatie relatie, final Persoon betrokkene,
        final SoortBetrokkenheid soortBetrokkenheid, final Integer datumAanvangOuderschap)
    {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setBetrokkene(betrokkene);
        betrokkenheid.setRelatie(relatie);
        betrokkenheid.setSoortBetrokkenheid(soortBetrokkenheid);
        if (SoortBetrokkenheid.OUDER == soortBetrokkenheid) {
            betrokkenheid.setDatumAanvangOuderschap(datumAanvangOuderschap);
            betrokkenheid.setIndOuder(true);
            betrokkenheid.setIndOuderHeeftGezag(true);
        }
        return betrokkenheid;
    }

    /** Bouwt een {@link Land) met opgegeven id en land code. */
    private Land bouwLand(final Integer id, final String landCode) {
        Land land = new Land();
        land.setId(id);
        land.setLandcode(landCode);
        return land;
    }

    /** Bouwt een {@link Partij) met opgegeven id en gemeente code. */
    private Partij bouwPartij(final Integer id, final String gemCode) {
        Partij partij = new Partij();
        partij.setId(id);
        partij.setGemeentecode(gemCode);
        return partij;
    }

    /** Bouwt een {@link Plaats) met opgegeven id en woonplaats code. */
    private Plaats bouwPlaats(final Integer id, final String plaatsCode) {
        Plaats plaats = new Plaats();
        plaats.setId(id);
        plaats.setWoonplaatscode(plaatsCode);
        return plaats;
    }

    /** Bouwt een {@link Persoon} met opgegeven id en BSN. */
    private Persoon bouwPersoon(final Long id, final String bsn) {
        Persoon persoon = new Persoon();
        persoon.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);
        persoon.setId(id);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(bsn);
        return persoon;
    }
}
