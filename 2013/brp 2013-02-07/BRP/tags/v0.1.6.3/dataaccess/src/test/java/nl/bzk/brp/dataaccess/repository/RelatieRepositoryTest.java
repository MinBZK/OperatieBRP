/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.VerplichteDataNietAanwezigExceptie;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.junit.Test;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor het testen van de {@link RelatieMdlRepository}. */
public class RelatieRepositoryTest extends AbstractRepositoryTestCase {

    private static final String RELATIE_SQL_QUERY       =
        "SELECT id, srt, dataanv, gemaanv, omslocaanv, relatiestatushis "
            + "FROM kern.relatie WHERE id = ?";
    private static final String BETROKKENHEID_SQL_QUERY = "SELECT id, relatie, rol, betrokkene "
        + "FROM kern.betr WHERE id = ?";

    @Inject
    private PersoonMdlRepository persoonMdlRepository;

    @Inject
    private RelatieMdlRepository relatieMdlRepository;

    @PersistenceContext
    private EntityManager em;


    /**
     * Unit test voor het testen van de {@link RelatieMdlRepository#opslaanNieuweRelatie(
     *nl.bzk.brp.model.objecttype.operationeel.RelatieModel, nl.bzk.brp.model.attribuuttype.Datum, java.util.Date,
     * nl.bzk.brp.model.objecttype.operationeel.ActieModel)} methode, waarin een relatie wordt opgeslagen.
     */
    @Test
    public void testOpslaanNieuweRelatie() {
        Datum aanvangGeldigheid = new Datum(20120325);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie = bouwNieuweHuwelijksRelatie(aanvangGeldigheid, bouwPersoon(1L, "123456789"),
            bouwPersoon(2L, "234567890"));
        ActieModel actie = bouwActie(SoortActie.HUWELIJK, aanvangGeldigheid, tijdstipRegistratie);

        Assert.assertNull(relatie.getId());
        relatie = relatieMdlRepository.opslaanNieuweRelatie(relatie, aanvangGeldigheid, new Date(), actie);
        em.flush();
        Assert.assertNotNull(relatie.getId());

        valideerRelatieInRepository(relatie);
    }

    /**
     * Unit test voor het testen van de {@link RelatieMdlRepository#opslaanNieuweRelatie(
     *nl.bzk.brp.model.objecttype.operationeel.RelatieModel, nl.bzk.brp.model.attribuuttype.Datum, java.util.Date,
     * nl.bzk.brp.model.objecttype.operationeel.ActieModel)} methode, waarin een relatie wordt opgeslagen.
     */
    @Test
    public void testOpslaanRelatie() {
        final int initieleAantalRelaties = countRowsInTable("kern.relatie");
        final int initieleAantalBetrokkenheden = countRowsInTable("kern.betr");
        final int initieleAantalPersonen = countRowsInTable("kern.pers");

        Datum aanvangGeldigheid = new Datum(20120605);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie = bouwNieuweFamilierechtelijkeBetrekking(aanvangGeldigheid, bouwPersoon(1L, "123456789"),
            bouwPersoon(2L, "234567890"), bouwPersoon(3L, "345678901"));
        ActieModel actie = bouwActie(SoortActie.AANGIFTE_GEBOORTE, aanvangGeldigheid, tijdstipRegistratie);

        Assert.assertNull(relatie.getId());
        relatie = relatieMdlRepository.opslaanNieuweRelatie(relatie, aanvangGeldigheid, new Date(), actie);
        em.flush();
        Assert.assertNotNull(relatie.getId());

        Assert.assertEquals(initieleAantalRelaties + 1, countRowsInTable("kern.relatie"));
        Assert.assertEquals(initieleAantalBetrokkenheden + 3, countRowsInTable("kern.betr"));
        Assert.assertEquals(initieleAantalPersonen, countRowsInTable("kern.pers"));

        Long relatieId = relatie.getId();

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
        Assert.assertEquals(historieBetrokkenheidOuder1.get("actieinh"), actie.getId());
        Assert.assertNull(historieBetrokkenheidOuder1.get("actieverval"));
        Assert.assertNull(historieBetrokkenheidOuder1.get("actieaanpgel"));
    }

    @Test(expected = VerplichteDataNietAanwezigExceptie.class)
    public void testOpslaanRelatieMetKindZonderId() {
        Datum aanvangGeldigheid = new Datum(20120325);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie = bouwNieuweFamilierechtelijkeBetrekking(aanvangGeldigheid, bouwPersoon(null, "123456789"),
            bouwPersoon(2L, "234567891"), bouwPersoon(3L, "345678912"));
        ActieModel actie = bouwActie(SoortActie.AANGIFTE_GEBOORTE, aanvangGeldigheid, tijdstipRegistratie);

        relatieMdlRepository.opslaanNieuweRelatie(relatie, aanvangGeldigheid, tijdstipRegistratie, actie);
        em.flush();
    }

    @Test(expected = VerplichteDataNietAanwezigExceptie.class)
    public void testOpslaanRelatieZonderBetrokkenheden() {
        Datum aanvangGeldigheid = new Datum(20120325);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie = bouwNieuweFamilierechtelijkeBetrekking(aanvangGeldigheid, null, null, null);
        ActieModel actie = bouwActie(SoortActie.AANGIFTE_GEBOORTE, aanvangGeldigheid, tijdstipRegistratie);

        relatieMdlRepository.opslaanNieuweRelatie(relatie, aanvangGeldigheid, tijdstipRegistratie, actie);
    }

    /**
     * Test of de opgegeven relatie ook zo in de repository aanwezig is.
     *
     * @param relatie de relatie die getest dient te worden.
     */
    private void valideerRelatieInRepository(final RelatieModel relatie) {
        List<Map<String, Object>> resultaat = simpleJdbcTemplate.query(RELATIE_SQL_QUERY, new ColumnMapRowMapper(),
            relatie.getId());
        Assert.assertEquals(1, resultaat.size());

        Map<String, Object> properties = resultaat.get(0);
        Assert.assertEquals(relatie.getId(), properties.get("id"));
        Assert.assertEquals(relatie.getSoort().ordinal(), properties.get("srt"));
        Assert.assertEquals(relatie.getGegevens().getDatumAanvang().getWaarde().intValue(),
            ((BigDecimal) properties.get("dataanv")).intValue());
        Assert.assertEquals(relatie.getStatusHistorie().getCode(), (String) properties.get("relatiestatushis"));

        for (BetrokkenheidModel betrokkenheid : relatie.getBetrokkenheden()) {
            valideerBetrokkenheidInRepository(betrokkenheid);
        }
    }

    /**
     * Test of de opgegeven betrokkenheid ook zo in de repository aanwezig is.
     *
     * @param betrokkenheid de betrokkenheid die getest dient te worden.
     */
    private void valideerBetrokkenheidInRepository(final BetrokkenheidModel betrokkenheid) {
        List<Map<String, Object>> resultaat = simpleJdbcTemplate.query(BETROKKENHEID_SQL_QUERY,
            new ColumnMapRowMapper(), betrokkenheid.getId());
        Assert.assertEquals(1, resultaat.size());

        Map<String, Object> properties = resultaat.get(0);
        Assert.assertEquals(betrokkenheid.getId(), properties.get("id"));
        Assert.assertEquals(betrokkenheid.getRelatie().getId(), properties.get("relatie"));
        Assert.assertEquals(betrokkenheid.getRol().ordinal(), properties.get("rol"));
        Assert.assertEquals(betrokkenheid.getBetrokkene().getId(), properties.get("betrokkene"));
    }

    /**
     * Retourneert een (nieuwe) Huwelijks relatie ({@link RelatieModel}) met de twee opgegeven personen als partners
     * en de opgegeven datum van aanvang.
     *
     * @param datumAanvangGeldigheid datum van aanvang van het huwelijk
     * @param persoon1 de persoon die trouwt met het andere persoon
     * @param persoon2 de persoon die trouwt met het andere persoon
     * @return een gevulde huwelijksrelatie
     */
    private RelatieModel bouwNieuweHuwelijksRelatie(final Datum datumAanvangGeldigheid, final PersoonModel persoon1,
        final PersoonModel persoon2)
    {
        RelatieBericht relatieBlauwdruk = new RelatieBericht();
        relatieBlauwdruk.setSoort(SoortRelatie.HUWELIJK);
        relatieBlauwdruk.setGegevens(new RelatieStandaardGroepBericht());
        relatieBlauwdruk.getGegevens().setDatumAanvang(datumAanvangGeldigheid);

        RelatieModel relatie = new RelatieModel(relatieBlauwdruk);
        relatie.getBetrokkenheden().add(bouwNieuweBetrokkenheid(persoon1, SoortBetrokkenheid.PARTNER, relatie, null));
        relatie.getBetrokkenheden().add(bouwNieuweBetrokkenheid(persoon2, SoortBetrokkenheid.PARTNER, relatie, null));
        return relatie;
    }

    /**
     * Retourneert een (nieuwe) familierechtelijke betrekking relatie ({@link RelatieModel}) met de drie opgegeven
     * personen als betrokkenen (twee ouders en een kind) en de opgegeven datum van aanvang.
     *
     * @param datumAanvangGeldigheid datum van aanvang van de familierechtelijke betrekking (voor alle hetzelfde)
     * @param kind het kind
     * @param ouder1 de eerste ouder
     * @param ouder2 de tweede ouder
     * @return een gevulde familierechtelijke betrekking
     */
    private RelatieModel bouwNieuweFamilierechtelijkeBetrekking(final Datum datumAanvangGeldigheid,
        final PersoonModel kind, final PersoonModel ouder1, final PersoonModel ouder2)
    {
        RelatieBericht relatieBlauwdruk = new RelatieBericht();
        relatieBlauwdruk.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatieBlauwdruk.setGegevens(new RelatieStandaardGroepBericht());

        RelatieModel relatie = new RelatieModel(relatieBlauwdruk);
        relatie.getBetrokkenheden().add(bouwNieuweBetrokkenheid(kind, SoortBetrokkenheid.KIND, relatie, null));
        relatie.getBetrokkenheden().add(bouwNieuweBetrokkenheid(ouder1, SoortBetrokkenheid.OUDER, relatie,
            datumAanvangGeldigheid));
        relatie.getBetrokkenheden().add(bouwNieuweBetrokkenheid(ouder2, SoortBetrokkenheid.OUDER, relatie,
            datumAanvangGeldigheid));

        // Verwijder alle null elementen, daar deze eventueel kunnen zijn toegevoegd indien personen null waren.
        relatie.getBetrokkenheden().removeAll(Collections.singleton(null));
        return relatie;
    }

    /**
     * Retourneert een (nieuwe) betrokkenheid ({@link BetrokkenheidModel}) van het opgegeven soort en met de opgegeven
     * persoon als betrokkene. Indien de betrokkenheid van het soort {@link SoortBetrokkenheid#OUDER} is, dan worden
     * de groepen op de betrokkenheid gezet, waarbij de waardes in de groepen standaard waardes krijgen.
     *
     * @param persoon de betrokkene van de betrokkenheid
     * @param soortBetrokkenheid het soort van de betrokkenheid
     * @param relatie de relatie waartoe de betrokkenheid behoord.
     * @param aanvangGeldigheid aanvang geldigheid voor de betrokkenheid (indien van toepassing)
     * @return een gevulde betrokkenheid
     */
    private BetrokkenheidModel bouwNieuweBetrokkenheid(final PersoonModel persoon,
        final SoortBetrokkenheid soortBetrokkenheid, final RelatieModel relatie, final Datum aanvangGeldigheid)
    {
        if (persoon == null) {
            return null;
        }

        BetrokkenheidBericht betrokkenheidBlauwdruk = new BetrokkenheidBericht();
        betrokkenheidBlauwdruk.setRol(soortBetrokkenheid);
        if (betrokkenheidBlauwdruk.getRol() == SoortBetrokkenheid.OUDER) {
            betrokkenheidBlauwdruk.setBetrokkenheidOuderlijkGezag(new BetrokkenheidOuderlijkGezagGroepBericht());
            betrokkenheidBlauwdruk.getBetrokkenheidOuderlijkGezag().setIndOuderlijkGezag(JaNee.Ja);

            betrokkenheidBlauwdruk.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
            betrokkenheidBlauwdruk.getBetrokkenheidOuderschap().setIndOuder(Ja.Ja);
            betrokkenheidBlauwdruk.getBetrokkenheidOuderschap().setDatumAanvang(aanvangGeldigheid);
        }

        return new BetrokkenheidModel(betrokkenheidBlauwdruk, persoon, relatie);
    }

    /**
     * Retourneert een (nieuw) persoon ({@link PersoonModel}) met de opgegeven technische id en bsn.
     *
     * @param id het technische id van de persoon
     * @param bsn het burgerservicenummer van de persoon
     * @return een gevuld persoon
     */
    private PersoonModel bouwPersoon(final Long id, final String bsn) {
        PersoonBericht persoonBlauwdruk = new PersoonBericht();

        persoonBlauwdruk.setSoort(SoortPersoon.INGESCHREVENE);
        persoonBlauwdruk.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoonBlauwdruk.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer(bsn));

        PersoonModel persoon = new PersoonModel(persoonBlauwdruk);
        ReflectionTestUtils.setField(persoon, "id", id);
        return persoon;
    }

    /**
     * Retourneert een (nieuwe) actie ({@link ActieModel}) van de opgegeven soort en opgegeven datums voor aanvang en
     * registratie.
     *
     * @param soort het soort actie
     * @param datumAanvangGeldigheid de datum van de aanvang van de geldigheid
     * @param tijdstipRegistratie het tijdstip van registratie
     * @return een gevulde actie
     */
    private ActieModel bouwActie(final SoortActie soort, final Datum datumAanvangGeldigheid,
        final Date tijdstipRegistratie)
    {
        ActieBericht actieBlauwdruk = new ActieBericht();
        actieBlauwdruk.setSoort(soort);
        actieBlauwdruk.setTijdstipRegistratie(new DatumTijd(tijdstipRegistratie));
        actieBlauwdruk.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        actieBlauwdruk.setPartij(em.find(Partij.class, 4));

        ActieModel actie = new ActieModel(actieBlauwdruk);
        em.persist(actie);
        return actie;
    }

    @Test
    public void testZoekPartnersNull() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopPartners(null);
        Assert.assertNotNull(personen);
        Assert.assertTrue(personen.isEmpty());
    }

    @Test
    public void testZoekPartners() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopPartners(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenoten() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopEchtgenoten(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp20121231() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopEchtgenoten(8731137L, new Datum(20121231));
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp20151231() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopEchtgenoten(8731137L, new Datum(20151231));
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp19631231() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopEchtgenoten(8731137L, new Datum(19631231));
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderen() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopKinderen(8731137L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
    }

    @Test
    public void testZoekOuders() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopOuders(3L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekfamilie() throws Exception {
        List<Long> personen = relatieMdlRepository.haalopFamilie(3L);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
        personen = relatieMdlRepository.haalopFamilie(8731137L);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilterMannelijk() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(8731137), personen.get(0));
    }

    @Test
    public void testZoekKinderenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(3), personen.get(0));
    }

    @Test
    public void testZoekKinderenDochterFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.VROUW);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderenVaderFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.MAN);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(8731137), personen.get(0));
    }

    @Test
    public void testZoekKinderenMoederFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsAanduidingen(GeslachtsAanduiding.VROUW);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(new Long(2), personen.get(0));
    }

    @Test
    public void testZoekFamilieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekFamilieIngezetenenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        filter.setUitPersoonTypen(SoortPersoon.INGESCHREVENE);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testZoekAlleRelatieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortRelatie.HUWELIJK);
        filter.setSoortBetrokkenheden(SoortBetrokkenheid.KIND, SoortBetrokkenheid.OUDER, SoortBetrokkenheid.PARTNER);
        List<Long> personen = relatieMdlRepository.haalopPersoonIdsVanRelatiesVanPersoon(3L, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(3, personen.size());
    }

    @Test
    public void testVindSoortRelatiesMetPersonenFamilieRechtelijkeBetrekkingVaderMoederKind() {
        //Vind relaties waarin vader en moeder ouder zijn:
        List<RelatieModel> relaties = relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("vader")),
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")),
            SoortBetrokkenheid.OUDER, new Datum(20120101), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
        );

        Assert.assertNotNull(relaties);
        Assert.assertEquals(1, relaties.size());
        RelatieModel relatie = relaties.get(0);
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoort());
        boolean vaderGevonden = false;
        boolean moederGevonden = false;
        boolean kindGevonden = false;
        for (BetrokkenheidModel betr : relaties.get(0).getBetrokkenheden()) {
            Burgerservicenummer bsn = betr.getBetrokkene().getIdentificatieNummers().getBurgerServiceNummer();
            if (SoortBetrokkenheid.OUDER == betr.getRol()) {
                if (bsn.getWaarde().equals("vader")) {
                    vaderGevonden = true;
                } else if (bsn.getWaarde().equals("moeder")) {
                    moederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else if (SoortBetrokkenheid.KIND == betr.getRol()) {
                if (bsn.getWaarde().equals("kind")) {
                    kindGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else {
                Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
            }
        }
        Assert.assertTrue(vaderGevonden && moederGevonden && kindGevonden);
    }

    @Test
    public void testVindSoortRelatiesMetPersonenGeregistreerdPartnerschapVaderMoeder() {
        List<RelatieModel> relatieList = relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("vader")),
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")),
            SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        Assert.assertEquals(1, relatieList.size());
        boolean partner1Gevonden = false;
        boolean partner2Gevonden = false;
        for (BetrokkenheidModel betr : relatieList.get(0).getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betr.getRol()) {
                Burgerservicenummer bsn = betr.getBetrokkene().getIdentificatieNummers().getBurgerServiceNummer();
                if (bsn.getWaarde().equals("moeder")) {
                    partner1Gevonden = true;
                } else if (bsn.getWaarde().equals("vader")) {
                    partner2Gevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else {
                Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
            }
        }
        Assert.assertTrue(partner1Gevonden && partner2Gevonden);
    }

    @Test
    public void testVindSoortRelatiesMetPersonenOuderschapOpaMoederOmaMoeder() {
        List<RelatieModel> relatieList = relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("opaMoeder")),
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("omaMoeder")),
            SoortBetrokkenheid.OUDER, new Datum(20120101), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING
        );

        Assert.assertEquals(1, relatieList.size());
        RelatieModel familie = relatieList.get(0);
        boolean moederGevonden = false;
        boolean opaMoederGevonden = false;
        boolean omaMoederGevonden = false;
        for (BetrokkenheidModel betr : familie.getBetrokkenheden()) {
            Burgerservicenummer bsn = betr.getBetrokkene().getIdentificatieNummers().getBurgerServiceNummer();
            if (SoortBetrokkenheid.OUDER == betr.getRol()) {
                if (bsn.getWaarde().equals("opaMoeder")) {
                    opaMoederGevonden = true;
                } else if (bsn.getWaarde().equals("omaMoeder")) {
                    omaMoederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else if (SoortBetrokkenheid.KIND == betr.getRol()) {
                if (bsn.getWaarde().equals("moeder")) {
                    moederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else {
                Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
            }
        }

        Assert.assertTrue(moederGevonden && opaMoederGevonden && omaMoederGevonden);
    }


    @Test
    public void testVindSoortRelatiesMetPersonenHuwelijkVaderMoeder() {
        //Vind relaties waarin vader en moeder getrouwd zijn:
        Assert.assertTrue(
            relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("vader")),
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")),
                SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.HUWELIJK).isEmpty()
        );
    }

    @Test
    public void testVindSoortRelatiesMetPersonenFamilieRechtelijkeBetrekkingVaderMoederAlsKind() {
        //Vind relaties waarin vader en moeder kind zijn:
        Assert.assertTrue(
            relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("vader")),
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")),
                SoortBetrokkenheid.KIND, new Datum(20120101), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING).isEmpty()
        );
    }

    @Test
    public void testVindSoortRelatiesMetPersonenHuwelijkOpaVaderOmaMoeder() {
        //Vind relaties waarin opaVader getrouwd is met omaMoeder:
        Assert.assertTrue(
            relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("opaVader")),
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("omaMoeder")),
                SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.HUWELIJK).isEmpty()
        );
    }

    @Test
    public void testVindSoortRelatiesMetPersonenGeregistreerdPartnerschapVaderMoederBeeindigd() {
        //Beeindig geregistreerd partnerschap
        List<RelatieModel> relatieList = relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("vader")),
            persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")),
            SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.GEREGISTREERD_PARTNERSCHAP
        );

        RelatieModel partnerschap = relatieList.get(0);
        ReflectionTestUtils.setField(partnerschap.getGegevens(), "datumEinde", new Datum(20120101));
        em.merge(partnerschap);

        Assert.assertTrue(
            relatieMdlRepository.vindSoortRelatiesMetPersonenInRol(
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("vader")),
                persoonMdlRepository.findByBurgerservicenummer(new Burgerservicenummer("moeder")),
                SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.GEREGISTREERD_PARTNERSCHAP).isEmpty()
        );
    }

    @Override
    protected List<String> getAdditioneleDataBestanden() {
        return Arrays.asList("/data/testdata-relaties.xml");
    }

}
