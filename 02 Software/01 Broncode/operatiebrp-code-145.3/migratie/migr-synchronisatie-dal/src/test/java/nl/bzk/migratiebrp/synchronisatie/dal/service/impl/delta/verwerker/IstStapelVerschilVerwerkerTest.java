/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelVoorkomenDecorator;

/**
 * Unittests voor {@link IstStapelVerschilVerwerker}.
 */
public class IstStapelVerschilVerwerkerTest extends AbstractDeltaTest {

    private Persoon bestaandePersoon;
    private VergelijkerResultaat vergelijkerResultaat;
    private DeltaBepalingContext context;
    private IstStapelVerschilVerwerker verwerker;

    @Before
    public void setUp() {
        bestaandePersoon = maakPersoon(true);
        final Persoon nieuwePersoon = maakPersoon(false);
        voegAnummerToeAanPersoon(bestaandePersoon, "1234567890");
        voegAnummerToeAanPersoon(nieuwePersoon, "0987654321");
        vergelijkerResultaat = new DeltaVergelijkerResultaat();
        context = new DeltaBepalingContext(nieuwePersoon, bestaandePersoon, null, false);
        verwerker = new IstStapelVerschilVerwerker();
    }

    private void voegAnummerToeAanPersoon(final Persoon persoon, final String anummer) {
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setAdministratienummer(anummer);
        persoon.addPersoonIDHistorie(idHistorie);
    }

    @Test(expected = IllegalStateException.class)
    public void testVerwerkWijzigingenVerwijderenStapelStapelBestaatNiet() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "02", 0);
        final Sleutel sleutel = new IstSleutel(bestaandeStapel, true);
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, bestaandeStapel, null, VerschilType.RIJ_VERWIJDERD, null, null));

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
    }

    @Test()
    public void testVerwerkWijzigingenVerwijderenStapelStapelOuder1Bestaat() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "02", 0);
        final Relatie bestaandeRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie bestaandeRelatieHistorie = new RelatieHistorie(bestaandeRelatie);

        bestaandeRelatie.addRelatieHistorie(bestaandeRelatieHistorie);
        bestaandeStapel.addRelatie(bestaandeRelatie);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Sleutel sleutel = new IstSleutel(bestaandeStapel, true);
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, bestaandeStapel, null, VerschilType.RIJ_VERWIJDERD, null, null));

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertTrue(bestaandePersoon.getStapels().isEmpty());
        assertNull(bestaandeRelatieHistorie.getActieVervalTbvLeveringMutaties());
    }

    @Test()
    public void testVerwerkWijzigingenVerwijderenStapelStapelOuder2Bestaat() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "03", 0);
        final Relatie bestaandeRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie bestaandeRelatieHistorie = new RelatieHistorie(bestaandeRelatie);

        bestaandeRelatie.addRelatieHistorie(bestaandeRelatieHistorie);
        bestaandeStapel.addRelatie(bestaandeRelatie);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Sleutel sleutel = new IstSleutel(bestaandeStapel, true);
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, bestaandeStapel, null, VerschilType.RIJ_VERWIJDERD, null, null));

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertTrue(bestaandePersoon.getStapels().isEmpty());
        assertNull(bestaandeRelatieHistorie.getActieVervalTbvLeveringMutaties());
    }

    @Test()
    public void testVerwerkWijzigingenVerwijderenStapelStapelOuderHuwelijkBestaat() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "05", 0);
        final Relatie bestaandeRelatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie bestaandeRelatieHistorie = new RelatieHistorie(bestaandeRelatie);

        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, bestaandeRelatie);
        final BetrokkenheidHistorie ikBetrokkenheidHistorie = new BetrokkenheidHistorie(ikBetrokkenheid);
        ikBetrokkenheid.addBetrokkenheidHistorie(ikBetrokkenheidHistorie);

        bestaandeRelatie.addRelatieHistorie(bestaandeRelatieHistorie);
        bestaandeRelatie.addBetrokkenheid(ikBetrokkenheid);
        bestaandePersoon.addBetrokkenheid(ikBetrokkenheid);

        bestaandeStapel.addRelatie(bestaandeRelatie);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Sleutel sleutel = new IstSleutel(bestaandeStapel, true);
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, bestaandeStapel, null, VerschilType.RIJ_VERWIJDERD, null, null));

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertTrue(bestaandePersoon.getStapels().isEmpty());
        assertNotNull(bestaandeRelatieHistorie.getActieVervalTbvLeveringMutaties());
        assertNotNull(ikBetrokkenheidHistorie.getActieVervalTbvLeveringMutaties());
    }

    @Test
    public void testVerwijderenToevoegenVoorkomensBijStapel() {
        final Integer datum1 = 20150101;
        final Integer datum2 = 20160101;
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD);
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "02", 0);
        final Stapel nieuweStapel = new Stapel(bestaandePersoon, "02", 0);

        final StapelVoorkomen bestaandVoorkomen = new StapelVoorkomen(bestaandeStapel, 0, administratieveHandeling);
        bestaandVoorkomen.setDatumAanvang(datum1);
        bestaandeStapel.addStapelVoorkomen(bestaandVoorkomen);

        final StapelVoorkomen nieuwVoorkomen = new StapelVoorkomen(nieuweStapel, 0, administratieveHandeling);
        nieuwVoorkomen.setDatumAanvang(datum2);
        nieuweStapel.addStapelVoorkomen(nieuwVoorkomen);

        final Verschil verwijderdVerschil =
                new Verschil(
                        new IstSleutel(bestaandeStapel, null, true),
                        StapelDecorator.decorate(bestaandeStapel),
                        null,
                        VerschilType.ELEMENT_VERWIJDERD,
                        null,
                        null);
        final Verschil nieuwVerschil =
                new Verschil(
                        new IstSleutel(nieuweStapel, null, false),
                        null,
                        StapelDecorator.decorate(nieuweStapel),
                        VerschilType.ELEMENT_NIEUW,
                        null,
                        null);

        vergelijkerResultaat.voegToeOfVervangVerschil(verwijderdVerschil);
        vergelijkerResultaat.voegToeOfVervangVerschil(nieuwVerschil);

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertEquals(1, bestaandeStapel.getStapelvoorkomens().size());
        assertEquals(datum2, bestaandeStapel.getStapelvoorkomens().iterator().next().getDatumAanvang());
    }

    @Test
    public void testVerwijderenRelaties() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "05", 0);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);

        bestaandeStapel.addRelatie(relatie);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Verschil verschil =
                new Verschil(new IstSleutel(bestaandeStapel, Stapel.VELD_RELATIES, true), relatie, null, VerschilType.RIJ_VERWIJDERD, null, null);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertTrue(bestaandeStapel.getRelaties().isEmpty());
    }

    @Test
    public void testToevoegenRelaties() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, "05", 0);
        final Stapel nieuweStapel = new Stapel(bestaandePersoon, "05", 0);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);

        bestaandePersoon.addStapel(bestaandeStapel);
        assertTrue(bestaandeStapel.getRelaties().isEmpty());

        final Verschil verschil =
                new Verschil(new IstSleutel(nieuweStapel, Stapel.VELD_RELATIES, true), null, relatie, VerschilType.RIJ_TOEGEVOEGD, null, null);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertEquals(1, bestaandeStapel.getRelaties().size());
    }

    @Test
    public void testHerschikStapelsStapelOmhoog() {
        final Stapel stapel0 = new Stapel(bestaandePersoon, "09", 0);
        final Stapel stapel1 = new Stapel(bestaandePersoon, "09", 1);
        final StapelVoorkomen sv1 = new StapelVoorkomen(stapel1, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        stapel1.addStapelVoorkomen(sv1);

        final StapelDecorator sd0 = StapelDecorator.decorate(stapel0);
        final StapelDecorator sd1 = StapelDecorator.decorate(stapel1);
        final Verschil verschilVerwijderenStapel = new Verschil(new IstSleutel(stapel0, true), sd0, null, VerschilType.RIJ_VERWIJDERD, null, null);
        final Verschil verschil0 = new Verschil(new IstSleutel(stapel1, true), sd1, sd0, VerschilType.ELEMENT_AANGEPAST, null, null);

        bestaandePersoon.addStapel(stapel0);
        bestaandePersoon.addStapel(stapel1);
        assertEquals(2, bestaandePersoon.getStapels().size());

        // Verwijderen stapel nodig om verplaatsen goed te laten werken
        vergelijkerResultaat.voegToeOfVervangVerschil(verschilVerwijderenStapel);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertEquals(1, stapel0.getStapelvoorkomens().size());
        assertEquals(1, bestaandePersoon.getStapels().size());
    }

    @Test
    public void testHerschikStapelsStapelOmlaag() {
        final Stapel stapel0 = new Stapel(bestaandePersoon, "09", 0);
        final Stapel stapel1 = new Stapel(bestaandePersoon, "09", 1);
        final StapelVoorkomen sv0 = new StapelVoorkomen(stapel0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        stapel0.addStapelVoorkomen(sv0);

        final StapelDecorator sd0 = StapelDecorator.decorate(stapel0);
        final StapelDecorator sd1 = StapelDecorator.decorate(stapel1);
        final Verschil verschil0 = new Verschil(new IstSleutel(stapel0, true), sd0, sd1, VerschilType.ELEMENT_AANGEPAST, null, null);

        bestaandePersoon.addStapel(stapel0);
        assertEquals(1, bestaandePersoon.getStapels().size());

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertEquals(1, bestaandePersoon.getStapels().size());
        final Stapel stapelN1 = bestaandePersoon.getStapels().iterator().next();
        assertEquals(1, stapelN1.getVolgnummer());
        assertEquals(1, stapelN1.getStapelvoorkomens().size());
    }

    @Test
    public void testHerschikStapelsStapelMeerdereStapelsOmlaag() {
        final Stapel stapel0 = new Stapel(bestaandePersoon, "09", 0);
        final Stapel stapel1 = new Stapel(bestaandePersoon, "09", 1);
        final Stapel stapel2 = new Stapel(bestaandePersoon, "09", 2);
        final StapelVoorkomen sv0 = new StapelVoorkomen(stapel0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        final StapelVoorkomen sv10 = new StapelVoorkomen(stapel1, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        final StapelVoorkomen sv11 = new StapelVoorkomen(stapel1, 1, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        stapel0.addStapelVoorkomen(sv0);
        stapel1.addStapelVoorkomen(sv10);
        stapel1.addStapelVoorkomen(sv11);
        stapel1.addRelatie(relatie);

        final StapelDecorator sd0 = StapelDecorator.decorate(stapel0);
        final StapelDecorator sd1 = StapelDecorator.decorate(stapel1);
        final StapelDecorator sd2 = StapelDecorator.decorate(stapel2);
        final Verschil verschil0 = new Verschil(new IstSleutel(stapel0, Stapel.VELD_VOLGNUMMER, true), sd0, sd1, VerschilType.ELEMENT_AANGEPAST, null, null);
        final Verschil verschil1 = new Verschil(new IstSleutel(stapel1, Stapel.VELD_VOLGNUMMER, true), sd1, sd2, VerschilType.ELEMENT_AANGEPAST, null, null);

        bestaandePersoon.addStapel(stapel0);
        bestaandePersoon.addStapel(stapel1);
        assertEquals(2, bestaandePersoon.getStapels().size());

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil1);

        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
        assertEquals(2, bestaandePersoon.getStapels().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testHerschikStapelsStapelDoelStapelNietLeeg() {
        final Stapel stapel0 = new Stapel(bestaandePersoon, "09", 0);
        final Stapel stapel1 = new Stapel(bestaandePersoon, "09", 1);
        final StapelVoorkomen sv0 = new StapelVoorkomen(stapel0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        final StapelVoorkomen sv10 = new StapelVoorkomen(stapel1, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        final StapelVoorkomen sv11 = new StapelVoorkomen(stapel1, 1, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        stapel0.addStapelVoorkomen(sv0);
        stapel1.addStapelVoorkomen(sv10);
        stapel1.addStapelVoorkomen(sv11);

        final StapelDecorator sd0 = StapelDecorator.decorate(stapel0);
        final StapelDecorator sd1 = StapelDecorator.decorate(stapel1);
        final Verschil verschil0 = new Verschil(new IstSleutel(stapel0, Stapel.VELD_VOLGNUMMER, true), sd0, sd1, VerschilType.ELEMENT_AANGEPAST, null, null);

        bestaandePersoon.addStapel(stapel0);
        bestaandePersoon.addStapel(stapel1);
        assertEquals(2, bestaandePersoon.getStapels().size());

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);
        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);
    }

    @Test
    public void testToevoegenStapels() {
        final Stapel stapel0 = new Stapel(bestaandePersoon, "09", 0);
        final Verschil verschil0 = new Verschil(new IstSleutel(stapel0, true), null, stapel0, VerschilType.RIJ_TOEGEVOEGD, null, null);

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);
        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);

        assertEquals(1, bestaandePersoon.getStapels().size());
    }

    @Test
    public void testToevoegenStapelsBestaandeStapel() {
        final Stapel stapelOud0 = new Stapel(bestaandePersoon, "09", 0);
        final StapelVoorkomen svOud0 = new StapelVoorkomen(stapelOud0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        svOud0.setDatumAanvang(DATUM_OUD);
        stapelOud0.addStapelVoorkomen(svOud0);

        final Stapel stapel0 = new Stapel(bestaandePersoon, "09", 0);
        final StapelVoorkomen sv0 = new StapelVoorkomen(stapelOud0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        sv0.setDatumAanvang(DATUM_NIEUW);
        stapel0.addStapelVoorkomen(sv0);
        stapel0.addRelatie(relatie);

        bestaandePersoon.addStapel(stapelOud0);

        final Verschil verschil0 = new Verschil(new IstSleutel(stapel0, true), null, stapel0, VerschilType.RIJ_TOEGEVOEGD, null, null);

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);
        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);

        assertEquals(1, bestaandePersoon.getStapels().size());
        assertEquals(DATUM_NIEUW, stapel0.getStapelvoorkomens().iterator().next().getDatumAanvang());
        assertTrue(stapel0.getRelaties().isEmpty());
    }

    @Test
    public void testToevoegenStapelVoorkomens() {
        final Stapel stapelOud0 = new Stapel(bestaandePersoon, "09", 0);
        final StapelVoorkomen svOud0 = new StapelVoorkomen(stapelOud0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        svOud0.setDatumAanvang(DATUM_OUD);
        stapelOud0.addStapelVoorkomen(svOud0);

        final StapelVoorkomen sv0 = new StapelVoorkomen(stapelOud0, 0, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_OUD));
        sv0.setDatumAanvang(DATUM_NIEUW);

        bestaandePersoon.addStapel(stapelOud0);

        final Verschil verschil0 =
                new Verschil(
                        new IstSleutel(sv0, Stapel.STAPEL_VOORKOMENS, false),
                        null,
                        StapelVoorkomenDecorator.decorate(sv0),
                        VerschilType.RIJ_TOEGEVOEGD,
                        null,
                        null);

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil0);
        verwerker.verwerkWijzigingen(vergelijkerResultaat, context);

        assertEquals(2, stapelOud0.getStapelvoorkomens().size());
    }
}
