/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.FamilierechtelijkeBetrekkingModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.KindModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import org.junit.Test;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor het testen van de {@link RelatieRepository}. */
public class RelatieRepositoryTest extends AbstractRepositoryTestCase {

    //TODO: Alle plain SQL queries omcatten naar JPA!!

    private static final String RELATIE_SQL_QUERY       =
        "SELECT id, srt, dataanv, gemaanv, omslocaanv, HuwelijkGeregistreerdPartner "
            + "FROM kern.relatie WHERE id = ?";
    private static final String BETROKKENHEID_SQL_QUERY = "SELECT id, relatie, rol, pers "
        + "FROM kern.betr WHERE id = ?";

    @Inject
    private ActieRepository actieRepository;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    /**
     * Unit test voor het testen van de
     * {@link RelatieRepository#opslaanNieuweRelatie(nl.bzk.brp.model.operationeel.kern.RelatieModel,
     * nl.bzk.brp.model.operationeel.kern.ActieModel, nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum)}
     * methode, waarin een relatie wordt opgeslagen.
     */
    @Test
    public void testOpslaanNieuweRelatie() {
        Datum aanvangGeldigheid = new Datum(20120325);
        Date tijdstipRegistratie = new Date();

        HuwelijkModel huwelijk =
            bouwNieuweHuwelijksRelatie(aanvangGeldigheid, bouwPersoon(1, "123456789"), bouwPersoon(2, "234567890"));
        ActieModel actie = bouwActie(SoortActie.REGISTRATIE_HUWELIJK, aanvangGeldigheid,
            tijdstipRegistratie);

        Assert.assertNull(huwelijk.getID());
        HuwelijkModel relatie = relatieRepository.opslaanNieuweRelatie(huwelijk, actie, aanvangGeldigheid);
        em.flush();
        Assert.assertNotNull(relatie.getID());

        valideerRelatieInRepository(relatie);
    }

    /**
     * Unit test voor het testen van de
     * {@link RelatieRepository#opslaanNieuweRelatie(nl.bzk.brp.model.operationeel.kern.RelatieModel,
     * nl.bzk.brp.model.operationeel.kern.ActieModel, nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum)}
     * methode, waarin een relatie wordt opgeslagen.
     */
    @Test
    public void testOpslaanRelatie() {
        final int initieleAantalRelaties = countRowsInTable("kern.relatie");
        final int initieleAantalBetrokkenheden = countRowsInTable("kern.betr");
        final int initieleAantalPersonen = countRowsInTable("kern.pers");

        Datum aanvangGeldigheid = new Datum(20120605);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie =
            bouwNieuweFamilierechtelijkeBetrekking(bouwPersoon(1, "123456789"),
                bouwPersoon(2, "234567890"), bouwPersoon(3, "345678901"));
        ActieModel actie = bouwActie(SoortActie.REGISTRATIE_GEBOORTE, aanvangGeldigheid, tijdstipRegistratie);

        Assert.assertNull(relatie.getID());
        relatie = relatieRepository.opslaanNieuweRelatie(relatie, actie, aanvangGeldigheid);
        em.flush();
        Assert.assertNotNull(relatie.getID());

        Assert.assertEquals(initieleAantalRelaties + 1, countRowsInTable("kern.relatie"));
        Assert.assertEquals(initieleAantalBetrokkenheden + 3, countRowsInTable("kern.betr"));
        Assert.assertEquals(initieleAantalPersonen, countRowsInTable("kern.pers"));

        Integer relatieId = relatie.getID();

        // Test Relatie
        List<Map<String, Object>> relaties =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.relatie WHERE id = " + relatieId);
        Assert.assertEquals(1, relaties.size());
        Assert.assertEquals("Relatie heeft niet het verwachte soort",
            SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.ordinal(),
            relaties.get(0).get("srt"));
        Assert.assertNull("Aanvangsdatum zou null moeten zijn voor deze relatiesoort", relaties.get(0).get("dataanv"));
        Assert.assertEquals("X", relaties.get(0).get("HuwelijkGeregistreerdPartner"));

        // Test Betrokkenheid voor Kind
        List<Map<String, Object>> betrokkenhedenKind =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE pers = 1 AND relatie = " + relatieId);
        Assert.assertEquals(1, betrokkenhedenKind.size());

        Map<String, Object> betrokkenheidKind = betrokkenhedenKind.get(0);
        Assert.assertEquals(relatieId, betrokkenheidKind.get("relatie"));
        Assert.assertEquals("Betrokkenheid heeft niet de 'kind' soort", SoortBetrokkenheid.KIND.ordinal(),
            betrokkenheidKind.get("rol"));
        Assert.assertNull("Betrokkenheid mag geen indicatie ouder hebben", betrokkenheidKind.get("indouder"));
        Assert.assertNull("Betrokkenheid mag geen indicatie gezag hebben", betrokkenheidKind.get("indouderheeftgezag"));
        Assert.assertEquals("Betrokkenheid mag geen geldige statushis hebben", "X",
            betrokkenheidKind.get("ouderschapstatushis"));
        Assert.assertEquals("Betrokkenheid mag geen geldige statushis hebben", "X",
            betrokkenheidKind.get("ouderlijkgezagstatushis"));

        // Test Betrokkenheid voor Ouder 1
        List<Map<String, Object>> betrokkenhedenOuder1 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE pers = 2 AND relatie = " + relatieId);
        Assert.assertEquals(1, betrokkenhedenOuder1.size());

        Map<String, Object> betrokkenheidOuder1 = betrokkenhedenOuder1.get(0);
        Assert.assertEquals(relatieId, betrokkenheidOuder1.get("relatie"));
        Assert.assertEquals("Betrokkenheid heeft niet de 'ouder' soort", SoortBetrokkenheid.OUDER.ordinal(),
            betrokkenheidOuder1.get("rol"));
        Assert.assertEquals("Betrokkenheid moet indicatie ouder hebben", true, betrokkenheidOuder1.get("indouder"));
        Assert.assertEquals("Betrokkenheid moet indicatie gezag hebben", true,
            betrokkenheidOuder1.get("indouderheeftgezag"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder1.get("ouderschapstatushis"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder1.get("ouderlijkgezagstatushis"));

        // Test Betrokkenheid voor Ouder 2
        List<Map<String, Object>> betrokkenhedenOuder2 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.betr WHERE pers = 3 AND relatie = " + relatieId);
        Assert.assertEquals(1, betrokkenhedenOuder2.size());

        Map<String, Object> betrokkenheidOuder2 = betrokkenhedenOuder2.get(0);
        Assert.assertEquals(relatieId, betrokkenheidOuder2.get("relatie"));
        Assert.assertEquals("Betrokkenheid heeft niet de 'ouder' soort", SoortBetrokkenheid.OUDER.ordinal(),
            betrokkenheidOuder2.get("rol"));
        Assert.assertEquals("Betrokkenheid moet indicatie ouder hebben", true, betrokkenheidOuder2.get("indouder"));
        Assert.assertEquals("Betrokkenheid moet indicatie gezag hebben", true,
            betrokkenheidOuder2.get("indouderheeftgezag"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder2.get("ouderschapstatushis"));
        Assert.assertEquals("Betrokkenheid moet actuele statushis hebben", "A",
            betrokkenheidOuder2.get("ouderlijkgezagstatushis"));

        // Test Historie Betrokkenheid
        List<Map<String, Object>> historieBetrokkenhedenOuder1 =
            simpleJdbcTemplate.queryForList("SELECT * FROM kern.his_ouderouderschap WHERE betr = "
                + betrokkenheidOuder1.get("id"));
        Assert.assertEquals(1, historieBetrokkenhedenOuder1.size());

        Map<String, Object> historieBetrokkenheidOuder1 = historieBetrokkenhedenOuder1.get(0);
        Assert.assertEquals(true, historieBetrokkenheidOuder1.get("indouder"));
        Assert.assertEquals("20120605", historieBetrokkenheidOuder1.get("dataanvgel").toString());
        Assert.assertEquals(historieBetrokkenheidOuder1.get("actieinh").getClass(), actie.getID().getClass());
        Assert.assertNull(historieBetrokkenheidOuder1.get("actieverval"));
        Assert.assertNull(historieBetrokkenheidOuder1.get("actieaanpgel"));
    }

    @Test(expected = VerplichteDataNietAanwezigExceptie.class)
    public void testOpslaanRelatieMetKindZonderId() {
        Datum aanvangGeldigheid = new Datum(20120325);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie =
            bouwNieuweFamilierechtelijkeBetrekking(bouwPersoon(null, "123456789"),
                bouwPersoon(2, "234567891"), bouwPersoon(3, "345678912"));
        ActieModel actie = bouwActie(SoortActie.REGISTRATIE_GEBOORTE, aanvangGeldigheid, tijdstipRegistratie);

        relatieRepository.opslaanNieuweRelatie(relatie, actie, aanvangGeldigheid);
        em.flush();
    }

    @Test(expected = VerplichteDataNietAanwezigExceptie.class)
    public void testOpslaanRelatieZonderBetrokkenheden() {
        Datum aanvangGeldigheid = new Datum(20120325);
        Date tijdstipRegistratie = new Date();

        RelatieModel relatie = bouwNieuweFamilierechtelijkeBetrekking(null, null, null);
        ActieModel actie = bouwActie(SoortActie.REGISTRATIE_GEBOORTE, aanvangGeldigheid, tijdstipRegistratie);

        relatieRepository.opslaanNieuweRelatie(relatie, actie, aanvangGeldigheid);
    }

    /**
     * Test of de opgegeven relatie ook zo in de repository aanwezig is.
     *
     * @param hgpModel de relatie die getest dient te worden.
     */
    private void valideerRelatieInRepository(final HuwelijkGeregistreerdPartnerschapModel hgpModel) {
        List<Map<String, Object>> resultaat =
            simpleJdbcTemplate.query(RELATIE_SQL_QUERY, new ColumnMapRowMapper(), hgpModel.getID());
        Assert.assertEquals(1, resultaat.size());

        Map<String, Object> properties = resultaat.get(0);
        Assert.assertEquals(hgpModel.getID(), properties.get("id"));
        Assert.assertEquals(hgpModel.getSoort().ordinal(), properties.get("srt"));
        Assert.assertEquals(hgpModel.getStandaard().getDatumAanvang().getWaarde(), properties.get("dataanv"));
        Assert.assertEquals(hgpModel.getHuwelijkGeregistreerdPartnerschapStatusHis().name(),
            (String) properties.get("HuwelijkGeregistreerdPartner"));

        for (BetrokkenheidModel betrokkenheid : hgpModel.getBetrokkenheden()) {
            valideerBetrokkenheidInRepository(betrokkenheid);
        }

        // Test Historie Betrokkenheid
        List<Map<String, Object>> hisRelatie =
            simpleJdbcTemplate
                .queryForList("SELECT * FROM kern.his_huwelijkgeregistreerdpar WHERE relatie = " + hgpModel.getID());
        Assert.assertEquals(1, hisRelatie.size());
        Assert.assertNull(hisRelatie.get(0).get("datumTijdVerval"));
    }

    /**
     * Test of de opgegeven betrokkenheid ook zo in de repository aanwezig is.
     *
     * @param betrokkenheid de betrokkenheid die getest dient te worden.
     */
    private void valideerBetrokkenheidInRepository(final BetrokkenheidModel betrokkenheid) {
        List<Map<String, Object>> resultaat =
            simpleJdbcTemplate.query(BETROKKENHEID_SQL_QUERY, new ColumnMapRowMapper(), betrokkenheid.getID());
        Assert.assertEquals(1, resultaat.size());

        Map<String, Object> properties = resultaat.get(0);
        Assert.assertEquals(betrokkenheid.getID(), properties.get("id"));
        Assert.assertEquals(betrokkenheid.getRelatie().getID(), properties.get("relatie"));
        Assert.assertEquals(betrokkenheid.getRol().ordinal(), properties.get("rol"));
        Assert.assertEquals(betrokkenheid.getPersoon().getID(), properties.get("pers"));
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
    private HuwelijkModel bouwNieuweHuwelijksRelatie(final Datum datumAanvangGeldigheid, final PersoonModel persoon1,
        final PersoonModel persoon2)
    {
        HuwelijkBericht relatieBlauwdruk = new HuwelijkBericht();
        relatieBlauwdruk.setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht());
        relatieBlauwdruk.getStandaard().setDatumAanvang(datumAanvangGeldigheid);
        Land landAanvang = em.find(Land.class, 2); //Nederland
        relatieBlauwdruk.getStandaard().setLandAanvang(landAanvang);

        HuwelijkModel huwelijk = new HuwelijkModel(relatieBlauwdruk);
        huwelijk.getBetrokkenheden().add(bouwNieuweBetrokkenheid(persoon1, SoortBetrokkenheid.PARTNER, huwelijk));
        huwelijk.getBetrokkenheden().add(bouwNieuweBetrokkenheid(persoon2, SoortBetrokkenheid.PARTNER, huwelijk));
        return huwelijk;
    }

    /**
     * Retourneert een (nieuwe) familierechtelijke betrekking relatie ({@link RelatieModel}) met de drie opgegeven
     * personen als betrokkenen (twee ouders en een kind) en de opgegeven datum van aanvang.
     *
     * @param kind het kind
     * @param ouder1 de eerste ouder
     * @param ouder2 de tweede ouder
     * @return een gevulde familierechtelijke betrekking
     */
    private RelatieModel bouwNieuweFamilierechtelijkeBetrekking(
        final PersoonModel kind, final PersoonModel ouder1, final PersoonModel ouder2)
    {
        FamilierechtelijkeBetrekkingBericht relatieBlauwdruk = new FamilierechtelijkeBetrekkingBericht();

        FamilierechtelijkeBetrekkingModel familierechtelijkeBetrekkingModel =
            new FamilierechtelijkeBetrekkingModel(relatieBlauwdruk);
        voegBetrokkenheidToeAanRelatie(familierechtelijkeBetrekkingModel, kind, SoortBetrokkenheid.KIND);
        voegBetrokkenheidToeAanRelatie(familierechtelijkeBetrekkingModel, ouder1, SoortBetrokkenheid.OUDER);
        voegBetrokkenheidToeAanRelatie(familierechtelijkeBetrekkingModel, ouder2, SoortBetrokkenheid.OUDER);

        return familierechtelijkeBetrekkingModel;
    }

    /**
     * Voegt de opgegeven persoon als betrokkenheid met de opgegeven rol toe aan de opgegeven familierechtelijke
     * betrekking.
     *
     * @param familierechtelijkeBetrekkingModel de relatie waaraan de betrokkenheid moet worden toegevoegd.
     * @param persoon de persoon die als betrokkene toegevoegd dient te worden.
     * @param rol de rol van betrokkenheid waarin de betrokkene moet worden toegevoegd.
     */
    private void voegBetrokkenheidToeAanRelatie(
        final FamilierechtelijkeBetrekkingModel familierechtelijkeBetrekkingModel, final PersoonModel persoon,
        final SoortBetrokkenheid rol)
    {
        if (persoon != null) {
            familierechtelijkeBetrekkingModel.getBetrokkenheden().add(
                bouwNieuweBetrokkenheid(persoon, rol, familierechtelijkeBetrekkingModel));
        }
    }

    /**
     * Retourneert een (nieuwe) betrokkenheid ({@link BetrokkenheidModel}) van het opgegeven soort en met de opgegeven
     * persoon als betrokkene. Indien de betrokkenheid van het soort {@link SoortBetrokkenheid#OUDER} is, dan worden
     * de groepen op de betrokkenheid gezet, waarbij de waardes in de groepen standaard waardes krijgen.
     *
     * @param persoon de betrokkene van de betrokkenheid
     * @param soortBetrokkenheid het soort van de betrokkenheid
     * @param relatie de relatie waartoe de betrokkenheid behoord.
     * @return een gevulde betrokkenheid
     */
    private BetrokkenheidModel bouwNieuweBetrokkenheid(final PersoonModel persoon,
        final SoortBetrokkenheid soortBetrokkenheid,
        final RelatieModel relatie)
    {
        if (persoon == null) {
            return null;
        }

        BetrokkenheidModel betrokkenheidModel;
        switch (soortBetrokkenheid) {
            case KIND:
                betrokkenheidModel = new KindModel(new KindBericht(), relatie, persoon);
                break;
            case OUDER:
                OuderBericht ouderBericht = new OuderBericht();
                ouderBericht.setOuderlijkGezag(new OuderOuderlijkGezagGroepBericht());
                ouderBericht.getOuderlijkGezag().setIndicatieOuderHeeftGezag(JaNee.JA);
                ouderBericht.setOuderschap(new OuderOuderschapGroepBericht());
                ouderBericht.getOuderschap().setIndicatieOuder(Ja.J);
                betrokkenheidModel = new OuderModel(ouderBericht, relatie, persoon);
                ((OuderModel) betrokkenheidModel).setOuderlijkGezagStatusHis(StatusHistorie.A);
                ((OuderModel) betrokkenheidModel).setOuderschapStatusHis(StatusHistorie.A);
                break;
            case PARTNER:
                betrokkenheidModel = new PartnerModel(new PartnerBericht(), relatie, persoon);
                break;
            default:
                return null;
        }
        return betrokkenheidModel;
    }

    /**
     * Retourneert een (nieuw) persoon ({@link PersoonModel}) met de opgegeven technische id en bsn.
     *
     * @param id het technische id van de persoon
     * @param bsn het burgerservicenummer van de persoon
     * @return een gevuld persoon
     */
    private PersoonModel bouwPersoon(final Integer id, final String bsn) {
        PersoonBericht persoonBlauwdruk = new PersoonBericht();

        persoonBlauwdruk.setSoort(SoortPersoon.INGESCHREVENE);
        persoonBlauwdruk.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBlauwdruk.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));

        PersoonModel persoon = new PersoonModel(persoonBlauwdruk);
        ReflectionTestUtils.setField(persoon, "iD", id);
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
        ActieBericht actieBlauwdruk;
        switch (soort) {
            case REGISTRATIE_GEBOORTE:
                actieBlauwdruk = new ActieRegistratieGeboorteBericht();
                break;
            case REGISTRATIE_HUWELIJK:
                actieBlauwdruk = new ActieRegistratieHuwelijkBericht();
                break;
            default:
                throw new IllegalArgumentException("Soort wordt nog niet ondersteund.");
        }
        actieBlauwdruk.setTijdstipRegistratie(new DatumTijd(tijdstipRegistratie));
        actieBlauwdruk.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        actieBlauwdruk.setPartij(em.find(Partij.class, (short) 4));

        ActieModel actie = new ActieModel(actieBlauwdruk, null);

        return actieRepository.opslaanNieuwActie(actie);
    }

    @Test
    public void testHeeftPartners() {
        Assert.assertTrue(relatieRepository.heeftPartners(8731137, new Datum(19681116)));
    }

    @Test
    public void testHeeftPartnersGeenPartner() {
        Assert.assertFalse(relatieRepository.heeftPartners(8731137, new Datum(19681115)));
    }

    @Test
    public void testZoekPartnersNull() throws Exception {
        List<Integer> personen = relatieRepository.haalopPartners(null, null);
        Assert.assertNotNull(personen);
        Assert.assertTrue(personen.isEmpty());
    }

    @Test
    public void testZoekPartnersVoorHuwelijk() throws Exception {
        List<Integer> personen = relatieRepository.haalopPartners(8731137, new Datum(19681115));
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekPartners() throws Exception {
        List<Integer> personen = relatieRepository.haalopPartners(8731137, new Datum(19681116));
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertTrue(personen.get(0) != 8731137);
    }

    @Test
    public void testZoekPartnersNaEindeHuwelijk() throws Exception {
        List<Integer> personen = relatieRepository.haalopPartners(8731137, new Datum(20140519));
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekEchtgenoten() throws Exception {
        List<Integer> personen = relatieRepository.haalopEchtgenoten(8731137);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertTrue(personen.get(0) != 8731137);
    }

    @Test
    public void testZoekEchtgenotenOp20121231() throws Exception {
        List<Integer> personen = relatieRepository.haalopEchtgenoten(8731137, new Datum(20121231));
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertTrue(personen.get(0) != 8731137);
    }

    @Test
    public void testZoekEchtgenotenOp20151231() throws Exception {
        List<Integer> personen = relatieRepository.haalopEchtgenoten(8731137, new Datum(20151231));
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekEchtgenotenOp19631231() throws Exception {
        List<Integer> personen = relatieRepository.haalopEchtgenoten(8731137, new Datum(19631231));
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderen() throws Exception {
        List<Integer> personen = relatieRepository.haalopKinderen(8731137);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
    }

    @Test
    public void testZoekOuders() throws Exception {
        List<Integer> personen = relatieRepository.haalopOuders(3);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekfamilie() throws Exception {
        List<Integer> personen = relatieRepository.haalopFamilie(3);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
        personen = relatieRepository.haalopFamilie(8731137);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekEchtgenotenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertTrue(personen.get(0) != 8731137);
    }

    @Test
    public void testZoekEchtgenotenFilterVrouwelijk() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setUitGeslachtsaanduidingen(Geslachtsaanduiding.VROUW);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(2), personen.get(0));
    }

    @Test
    public void testZoekKinderenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(3), personen.get(0));
    }

    @Test
    public void testZoekKinderenDochterFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        filter.setUitGeslachtsaanduidingen(Geslachtsaanduiding.VROUW);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(8731137, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(0, personen.size());
    }

    @Test
    public void testZoekKinderenVaderFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsaanduidingen(Geslachtsaanduiding.MAN);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(3, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(8731137), personen.get(0));
    }

    @Test
    public void testZoekKinderenMoederFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        filter.setUitGeslachtsaanduidingen(Geslachtsaanduiding.VROUW);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(3, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(1, personen.size());
        Assert.assertEquals(Integer.valueOf(2), personen.get(0));
    }

    @Test
    public void testZoekFamilieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(3, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekFamilieIngezetenenFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND);
        filter.setUitPersoonTypen(SoortPersoon.INGESCHREVENE);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(3, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekAlleRelatieFilter() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortRelatie.HUWELIJK);
        filter.setSoortBetrokkenheden(SoortBetrokkenheid.KIND, SoortBetrokkenheid.OUDER, SoortBetrokkenheid.PARTNER);
        List<Integer> personen = relatieRepository.haalopPersoonIdsVanRelatiesVanPersoon(3, filter);
        Assert.assertNotNull(personen);
        Assert.assertEquals(2, personen.size());
    }

    @Test
    public void testZoekAlleRelatieFilterMetHaalOpBetrokkenhedenVanPersoon() throws Exception {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortRelatie.HUWELIJK);
        filter.setSoortBetrokkenheden(SoortBetrokkenheid.KIND, SoortBetrokkenheid.OUDER, SoortBetrokkenheid.PARTNER);
        PersoonModel persoonModel = new PersoonModel(new PersoonBericht());
        ReflectionTestUtils.setField(persoonModel, "iD", 3);
        List<BetrokkenheidModel> betrokkenheden =
            relatieRepository.haalOpBetrokkenhedenVanPersoon(persoonModel, filter);
        Assert.assertNotNull(betrokkenheden);
        Assert.assertEquals(2, betrokkenheden.size());
    }


    @Test
    public void testVindSoortRelatiesMetPersonenFamilieRechtelijkeBetrekkingVaderMoederKind() {
        // Vind relaties waarin vader en moeder ouder zijn:
        List<RelatieModel> relaties =
            relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("300000000")),
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("400000000")),
                SoortBetrokkenheid.OUDER, new Datum(20120101), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        Assert.assertNotNull(relaties);
        Assert.assertEquals(1, relaties.size());
        RelatieModel relatie = relaties.get(0);
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoort());
        boolean vaderGevonden = false;
        boolean moederGevonden = false;
        boolean kindGevonden = false;
        for (BetrokkenheidModel betr : relaties.get(0).getBetrokkenheden()) {
            Burgerservicenummer bsn = betr.getPersoon().getIdentificatienummers().getBurgerservicenummer();
            if (SoortBetrokkenheid.OUDER == betr.getRol()) {
                if (bsn.toString().equals("300000000")) {
                    vaderGevonden = true;
                } else if (bsn.toString().equals("400000000")) {
                    moederGevonden = true;
                } else {
                    Assert.fail(
                        String.format("Betrokkenheid (bsn: %s) hoort niet thuis in deze relatie.", bsn.toString()));
                }
            } else if (SoortBetrokkenheid.KIND == betr.getRol()) {
                if (bsn.toString().equals("500000000")) {
                    kindGevonden = true;
                } else {
                    Assert.fail(
                        String.format("Betrokkenheid (bsn: %s) hoort niet thuis in deze relatie.", bsn.toString()));
                }
            } else {
                Assert.fail(String
                    .format("Betrokkenheid (soort: %s) hoort niet thuis in deze relatie.", betr.getRol().getNaam()));
            }
        }
        Assert.assertTrue(vaderGevonden && moederGevonden && kindGevonden);
    }

    @Test
    public void testVindSoortRelatiesMetPersonenGeregistreerdPartnerschapVaderMoeder() {
        List<RelatieModel> relatieList =
            relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("300000000")),
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("400000000")),
                SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        Assert.assertEquals(1, relatieList.size());
        boolean partner1Gevonden = false;
        boolean partner2Gevonden = false;
        for (BetrokkenheidModel betr : relatieList.get(0).getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betr.getRol()) {
                Burgerservicenummer bsn = betr.getPersoon().getIdentificatienummers().getBurgerservicenummer();
                if (bsn.toString().equals("400000000")) {
                    partner1Gevonden = true;
                } else if (bsn.toString().equals("300000000")) {
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
        List<RelatieModel> relatieList =
            relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("200000000")),
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("200020000")),
                SoortBetrokkenheid.OUDER, new Datum(20120101), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        Assert.assertEquals(1, relatieList.size());
        RelatieModel familie = relatieList.get(0);
        boolean moederGevonden = false;
        boolean opaMoederGevonden = false;
        boolean omaMoederGevonden = false;
        for (BetrokkenheidModel betr : familie.getBetrokkenheden()) {
            Burgerservicenummer bsn = betr.getPersoon().getIdentificatienummers().getBurgerservicenummer();
            if (SoortBetrokkenheid.OUDER == betr.getRol()) {
                if (bsn.toString().equals("200000000")) {
                    opaMoederGevonden = true;
                } else if (bsn.toString().equals("200020000")) {
                    omaMoederGevonden = true;
                } else {
                    Assert.fail("Betrokkenheid hoort niet thuis in deze relatie.");
                }
            } else if (SoortBetrokkenheid.KIND == betr.getRol()) {
                if (bsn.toString().equals("400000000")) {
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
        // Vind relaties waarin vader en moeder getrouwd zijn:
        Assert.assertTrue(relatieRepository.vindSoortRelatiesMetPersonenInRol(
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("300000000")),
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("400000000")),
            SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.HUWELIJK).isEmpty());
    }

    @Test
    public void testVindSoortRelatiesMetPersonenFamilieRechtelijkeBetrekkingVaderMoederAlsKind() {
        // Vind relaties waarin vader en moeder kind zijn:
        Assert.assertTrue(relatieRepository.vindSoortRelatiesMetPersonenInRol(
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("300000000")),
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("400000000")),
            SoortBetrokkenheid.KIND, new Datum(20120101), SoortRelatie.FAMILIERECHTELIJKE_BETREKKING).isEmpty());
    }

    @Test
    public void testVindSoortRelatiesMetPersonenHuwelijkOpaVaderOmaMoeder() {
        // Vind relaties waarin opaVader getrouwd is met omaMoeder:
        Assert.assertTrue(relatieRepository.vindSoortRelatiesMetPersonenInRol(
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("100000000")),
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("200020000")),
            SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.HUWELIJK).isEmpty());
    }

    @Test
    public void testVindSoortRelatiesMetPersonenGeregistreerdPartnerschapVaderMoederBeeindigd() {
        // Beeindig geregistreerd partnerschap
        List<RelatieModel> relatieList =
            relatieRepository.vindSoortRelatiesMetPersonenInRol(
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("300000000")),
                persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("400000000")),
                SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        HuwelijkGeregistreerdPartnerschapModel partnerschap =
            (HuwelijkGeregistreerdPartnerschapModel) relatieList.get(0);
        ReflectionTestUtils.setField(partnerschap.getStandaard(), "datumEinde", new Datum(20120101));
        em.merge(partnerschap);

        Assert.assertTrue(relatieRepository.vindSoortRelatiesMetPersonenInRol(
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("300000000")),
            persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("400000000")),
            SoortBetrokkenheid.PARTNER, new Datum(20120101), SoortRelatie.GEREGISTREERD_PARTNERSCHAP).isEmpty());
    }

    @Test
    public void testVerwantschapBroerZus() {
        Assert.assertTrue(relatieRepository.isVerwant(3000, 3001));
    }

    @Test
    public void testVerwantschapBroerMoeder() {
        Assert.assertTrue(relatieRepository.isVerwant(3000, 3003));
    }

    @Test
    public void testVerwantschapBroerOma() {
        Assert.assertTrue(relatieRepository.isVerwant(3000, 3005));
    }

    @Test
    public void testVerwantschapVaderMoeder() {
        Assert.assertFalse(relatieRepository.isVerwant(3002, 3003));
    }

    @Test
    public void testVerwantschapZusOom() {
        Assert.assertFalse(relatieRepository.isVerwant(3001, 3006));
    }

    @Test
    public void testVerwantschapZichZelf() {
        Assert.assertFalse(relatieRepository.isVerwant(3001, 3001));
    }


    @Override
    protected List<String> getAdditioneleDataBestanden() {
        return Arrays.asList("/data/testdata-relaties.xml");
    }

}
