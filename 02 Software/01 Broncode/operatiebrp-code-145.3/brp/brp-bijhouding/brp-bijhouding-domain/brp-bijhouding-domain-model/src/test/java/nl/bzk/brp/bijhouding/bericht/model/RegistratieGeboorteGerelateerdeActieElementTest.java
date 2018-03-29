/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest voor {@link RegistratieGeboorteGerelateerdeActieElement}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistratieGeboorteGerelateerdeActieElementTest extends AbstractElementTest {
    private static final String IK_PERSOON_SLEUTEL = "ikPersoonSleutel";
    private static final String GERELATEERDE_PERSOON_SLEUTEL = "gerelateerdeSleutel";
    private static final String RELATIE_SLEUTEL = "relatieSleutel";
    private static final String IK_BETROKKENHEID_SLEUTEL = "ikBetrokkenheidSleutel";
    private static final String GERELATEERDE_BETROKKENHEID_SLEUTEL = "gerelateerdePersoonSleutel";
    private ElementBuilder builder;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructieZonderPersoon() {
        builder.maakRegistratieGeboorteGerelateerdeActieElement("actie", 2016_01_01, null);
    }

    @Test
    public void testConstructie() {
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement partnerPersoon = builder.maakPersoonGegevensElement("persoon_2", "persoon2Sleutel", null, persoonParams);
        final BetrokkenheidElement
                partner =
                builder.maakBetrokkenheidElement("partner", "partnerSleutel", BetrokkenheidElementSoort.PARTNER, partnerPersoon, null);
        final HuwelijkElement huwelijk = builder.maakHuwelijkElement("huwelijk", "huwelijkSleutel", null, Collections.singletonList(partner));
        final BetrokkenheidElement
                betrokkenheid =
                builder.maakBetrokkenheidElement("betrokkenheid", "betrokkenheidSleutel", null, BetrokkenheidElementSoort.PARTNER, huwelijk);
        final String persoonSleutel = "persoonSleutel";
        final PersoonRelatieElement persoon =
                builder.maakPersoonRelatieElement("persoon_1", null, persoonSleutel, Collections.singletonList(betrokkenheid));
        persoon.setVerzoekBericht(getBericht());

        final RegistratieGeboorteGerelateerdeActieElement actie =
                builder.maakRegistratieGeboorteGerelateerdeActieElement("actie", 2016_01_01, persoon);

        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonSleutel)).thenReturn(bijhoudingPersoon);

        assertEquals(persoon, actie.getPersoonRelatieElement());
        assertEquals(SoortActie.REGISTRATIE_GEBOORTE_GERELATEERDE, actie.getSoortActie());
        assertEquals(persoon, actie.getPersoonElementen().get(0));
        assertEquals(bijhoudingPersoon, actie.getHoofdPersonen().get(0));
    }

    @Test
    public void testVerwerking_persoonNietVerwerkbaar() {
        final RegistratieGeboorteGerelateerdeActieElement actie = maakActieElementHuwelijkOfGp();
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.UITGESLOTEN_PERSOON);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_PERSOON_SLEUTEL)).thenReturn(bijhoudingPersoon);
        assertNull(actie.verwerk(getBericht(), getAdministratieveHandeling()));
    }

    @Test
    public void testVerwerking_persoonVerwerkbaar() {
        final RegistratieGeboorteGerelateerdeActieElement actie = maakActieElementHuwelijkOfGp();

        final RelatieHelper relatieHelper = RelatieHelper.maakHuwelijkOfGp(getBericht(), true);
        final BijhoudingRelatie bijhoudingRelatie = BijhoudingRelatie.decorate(relatieHelper.relatie);

        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode("6030")).thenReturn(new LandOfGebied("6030", "Nederland"));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(bijhoudingRelatie);
        assertEquals(0, relatieHelper.anderePersoon.getPersoonGeboorteHistorieSet().size());
        assertNotNull(actie.verwerk(getBericht(), getAdministratieveHandeling()));
        assertEquals(1, relatieHelper.anderePersoon.getPersoonGeboorteHistorieSet().size());
    }

    @Test
    public void testPeilDatum_actueleRelatieHistorie() {
        final RegistratieGeboorteGerelateerdeActieElement actie = maakActieElementHuwelijkOfGp();

        final DatumElement peilDatum = actie.getPeilDatum();
        assertEquals((Integer) DatumUtil.vandaag(), peilDatum.getWaarde());
    }

    @Bedrijfsregel(Regel.R2654)
    @Test
    public void testHuwelijkNietActueel() {
        final RelatieHelper relatieHelper = RelatieHelper.maakHuwelijkOfGp(getBericht(), true);
        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));
        final Relatie huwelijk = relatieHelper.relatie;
        final RelatieHistorie huwelijkHistorie = new RelatieHistorie(huwelijk);
        huwelijkHistorie.setDatumAanvang(2015_01_01);
        huwelijkHistorie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        huwelijk.addRelatieHistorie(huwelijkHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(huwelijk));

        final List<MeldingElement> meldingen = maakActieElementHuwelijkOfGp().valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2654, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2654)
    @Test
    public void testOuderschapNietActueel() {
        final RelatieHelper relatieHelper = RelatieHelper.maakOuderNaarKind(getBericht());
        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));

        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(ikBetrokkenheid);
        ouderHistorie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        ikBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);

        final Relatie relatie = relatieHelper.relatie;

        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        final int datumAanvang = 2015_01_01;
        relatieHistorie.setDatumAanvang(datumAanvang);
        relatie.addRelatieHistorie(relatieHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(relatie));

        final List<MeldingElement> meldingen = maakActieElementOuderNaarKind(datumAanvang).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2654, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2653)
    @Test
    public void testDatumAanvangGeldigheidVoorDatumAanvangHuwelijk() {
        final RelatieHelper relatieHelper = RelatieHelper.maakHuwelijkOfGp(getBericht(), true);
        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));
        final Relatie huwelijk = relatieHelper.relatie;
        final RelatieHistorie huwelijkHistorie = new RelatieHistorie(huwelijk);
        huwelijkHistorie.setDatumAanvang(2015_01_01);
        huwelijk.addRelatieHistorie(huwelijkHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(huwelijk));

        final List<MeldingElement> meldingen = maakActieElementHuwelijkOfGp(2005_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2653, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2653)
    @Test
    public void testGeenDatumAanvangGeldigheid() {
        final RelatieHelper relatieHelper = RelatieHelper.maakHuwelijkOfGp(getBericht(), true);
        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));
        final Relatie huwelijk = relatieHelper.relatie;
        final RelatieHistorie huwelijkHistorie = new RelatieHistorie(huwelijk);
        huwelijkHistorie.setDatumAanvang(2015_01_01);
        huwelijk.addRelatieHistorie(huwelijkHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(huwelijk));

        final List<MeldingElement> meldingen = maakActieElementHuwelijkOfGp(null).valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2653)
    @Test
    public void testDatumAanvangGeldigheidVoorDatumAanvangOuderschap() {
        final RelatieHelper relatieHelper = RelatieHelper.maakOuderNaarKind(getBericht());
        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;

        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(ikBetrokkenheid);
        ouderHistorie.setDatumAanvangGeldigheid(2015_01_01);
        ikBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));

        final Relatie relatie = relatieHelper.relatie;
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumAanvang(2015_01_01);
        relatie.addRelatieHistorie(relatieHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(relatie));

        final List<MeldingElement> meldingen = maakActieElementOuderNaarKind(2005_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2653, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2653)
    @Bedrijfsregel(Regel.R2654)
    @Test
    public void testValidatieOK_Huwelijk() {
        final RelatieHelper relatieHelper = RelatieHelper.maakHuwelijkOfGp(getBericht(), true);
        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;
        ikBetrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.PARTNER);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));
        final Relatie huwelijk = relatieHelper.relatie;
        final RelatieHistorie huwelijkHistorie = new RelatieHistorie(huwelijk);
        final int datumAanvang = 2015_01_01;
        huwelijkHistorie.setDatumAanvang(datumAanvang);
        huwelijk.addRelatieHistorie(huwelijkHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(huwelijk));

        final List<MeldingElement> meldingen = maakActieElementHuwelijkOfGp(datumAanvang).valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2653)
    @Bedrijfsregel(Regel.R2654)
    @Test
    public void testValidatieOK_OuderNaarKind() {
        final int datumAanvang = 2015_01_01;
        final RelatieHelper relatieHelper = RelatieHelper.maakOuderNaarKind(getBericht());

        final Betrokkenheid ikBetrokkenheid = relatieHelper.ikBetrokkenheid;
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(ikBetrokkenheid);
        ouderHistorie.setDatumAanvangGeldigheid(datumAanvang);
        ikBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ikBetrokkenheid));

        final Relatie relatie = relatieHelper.relatie;
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumAanvang(datumAanvang);
        relatie.addRelatieHistorie(relatieHistorie);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(relatie));

        final List<MeldingElement> meldingen = maakActieElementOuderNaarKind(datumAanvang).valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2653)
    @Bedrijfsregel(Regel.R2654)
    @Test
    public void testValidatieOK_KindNaarOuder() {
        final int datumAanvang = 2015_01_01;
        final RelatieHelper relatieHelper = RelatieHelper.maakKindNaarOuder(getBericht());

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, IK_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(relatieHelper.ikBetrokkenheid));

        final Betrokkenheid ouderBetrokkenheid = relatieHelper.gerelateerdeBetrokkenheid;
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(ouderBetrokkenheid);
        ouderHistorie.setDatumAanvangGeldigheid(datumAanvang);
        ouderBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, GERELATEERDE_BETROKKENHEID_SLEUTEL))
                .thenReturn(BijhoudingBetrokkenheid.decorate(ouderBetrokkenheid));

        final Relatie relatie = relatieHelper.relatie;
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumAanvang(datumAanvang);
        relatie.addRelatieHistorie(relatieHistorie);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, RELATIE_SLEUTEL)).thenReturn(BijhoudingRelatie.decorate(relatie));

        final List<MeldingElement> meldingen = maakActieElementOuderNaarKind(datumAanvang).valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    private RegistratieGeboorteGerelateerdeActieElement maakActieElementHuwelijkOfGp() {
        return maakActieElementHuwelijkOfGp(2016_01_01);
    }

    private RegistratieGeboorteGerelateerdeActieElement maakActieElementHuwelijkOfGp(final Integer datumAanvangGeldigheid) {
        final ElementBuilder.PersoonParameters gerelateerdePersoonParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.datum(2016_01_01);
        geboorteParams.landGebiedCode("6030");
        gerelateerdePersoonParameters.geboorte(builder.maakGeboorteElement("geboorte", geboorteParams));

        final PersoonGegevensElement partnerPersoon =
                builder.maakPersoonGegevensElement("persoon_2", GERELATEERDE_PERSOON_SLEUTEL, null, gerelateerdePersoonParameters);
        partnerPersoon.setVerzoekBericht(getBericht());

        final BetrokkenheidElement partner =
                builder.maakBetrokkenheidElement("partner", GERELATEERDE_BETROKKENHEID_SLEUTEL, BetrokkenheidElementSoort.PARTNER, partnerPersoon, null);
        partner.setVerzoekBericht(getBericht());

        final HuwelijkElement huwelijk = builder.maakHuwelijkElement("huwelijk", RELATIE_SLEUTEL, null, Collections.singletonList(partner));
        huwelijk.setVerzoekBericht(getBericht());

        final BetrokkenheidElement betrokkenheid =
                builder.maakBetrokkenheidElement("betrokkenheid", IK_BETROKKENHEID_SLEUTEL, null, BetrokkenheidElementSoort.PARTNER, huwelijk);
        betrokkenheid.setVerzoekBericht(getBericht());

        final PersoonRelatieElement persoon =
                builder.maakPersoonRelatieElement("persoon_1", null, IK_PERSOON_SLEUTEL, Collections.singletonList(betrokkenheid));
        persoon.setVerzoekBericht(getBericht());

        return builder.maakRegistratieGeboorteGerelateerdeActieElement("actie", datumAanvangGeldigheid, persoon);
    }

    private RegistratieGeboorteGerelateerdeActieElement maakActieElementOuderNaarKind(final int datumAanvangGeldigheid) {
        final ElementBuilder.PersoonParameters gerelateerdePersoonParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.datum(2016_01_01);
        geboorteParams.landGebiedCode("6030");
        gerelateerdePersoonParameters.geboorte(builder.maakGeboorteElement("geboorte", geboorteParams));

        final PersoonGegevensElement gerelateerdePersoon =
                builder.maakPersoonGegevensElement("persoon_2", GERELATEERDE_PERSOON_SLEUTEL, null, gerelateerdePersoonParameters);
        gerelateerdePersoon.setVerzoekBericht(getBericht());

        final BetrokkenheidElement kind =
                builder.maakBetrokkenheidElement("kind", GERELATEERDE_BETROKKENHEID_SLEUTEL, BetrokkenheidElementSoort.KIND, gerelateerdePersoon, null);
        kind.setVerzoekBericht(getBericht());

        final FamilierechtelijkeBetrekkingElement fbrElement =
                builder.maakFamilierechtelijkeBetrekkingElement("fbrElement", RELATIE_SLEUTEL, null, null, Collections.singletonList(kind));
        fbrElement.setVerzoekBericht(getBericht());

        final BetrokkenheidElement betrokkenheid =
                builder.maakBetrokkenheidElement("betrokkenheid", IK_BETROKKENHEID_SLEUTEL, null, BetrokkenheidElementSoort.OUDER, fbrElement);
        betrokkenheid.setVerzoekBericht(getBericht());

        final PersoonRelatieElement persoon =
                builder.maakPersoonRelatieElement("persoon_1", null, IK_PERSOON_SLEUTEL, Collections.singletonList(betrokkenheid));
        persoon.setVerzoekBericht(getBericht());

        return builder.maakRegistratieGeboorteGerelateerdeActieElement("actie", datumAanvangGeldigheid, persoon);
    }

    private static class RelatieHelper {
        private final Relatie relatie;
        private final Persoon ik;
        private final Betrokkenheid ikBetrokkenheid;
        private final Betrokkenheid gerelateerdeBetrokkenheid;
        private final Persoon anderePersoon;

        private RelatieHelper(final Relatie relatie, final Persoon anderePersoon, final Persoon ik, final Betrokkenheid ikBetrokkenheid,
                              final Betrokkenheid gerelateerdeBetrokkenheid) {
            this.ik = ik;
            this.anderePersoon = anderePersoon;
            this.relatie = relatie;
            this.ikBetrokkenheid = ikBetrokkenheid;
            this.gerelateerdeBetrokkenheid = gerelateerdeBetrokkenheid;
        }

        private static RelatieHelper maakOuderNaarKind(final BijhoudingVerzoekBericht bericht) {
            final Persoon ik = maakIkPersoon(bericht);
            final Persoon kind = maakGerelateerdePersoon();

            final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
            final Betrokkenheid ikBetrokkenheid = maakBetrokkenheid(ik, relatie, SoortBetrokkenheid.OUDER);
            final Betrokkenheid kindBetrokkenheid = maakBetrokkenheid(kind, relatie, SoortBetrokkenheid.KIND);

            relatie.addBetrokkenheid(ikBetrokkenheid);
            relatie.addBetrokkenheid(kindBetrokkenheid);

            return new RelatieHelper(relatie, kind, ik, ikBetrokkenheid, kindBetrokkenheid);
        }

        private static RelatieHelper maakKindNaarOuder(final BijhoudingVerzoekBericht bericht) {
            final Persoon ik = maakIkPersoon(bericht);
            final Persoon ouder = maakGerelateerdePersoon();

            final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
            final Betrokkenheid ikBetrokkenheid = maakBetrokkenheid(ik, relatie, SoortBetrokkenheid.KIND);
            final Betrokkenheid ouderBetrokkenheid = maakBetrokkenheid(ouder, relatie, SoortBetrokkenheid.OUDER);

            relatie.addBetrokkenheid(ikBetrokkenheid);
            relatie.addBetrokkenheid(ouderBetrokkenheid);

            return new RelatieHelper(relatie, ouder, ik, ikBetrokkenheid, ouderBetrokkenheid);
        }

        private static RelatieHelper maakHuwelijkOfGp(final BijhoudingVerzoekBericht bericht, final boolean isHuwelijk) {
            final Persoon ik = maakIkPersoon(bericht);
            final Persoon partner = maakGerelateerdePersoon();

            final Relatie relatie;
            if (isHuwelijk) {
                relatie = new Relatie(SoortRelatie.HUWELIJK);
            } else {
                relatie = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
            }

            final Betrokkenheid ikBetrokkenheid = maakBetrokkenheid(ik, relatie, SoortBetrokkenheid.PARTNER);
            final Betrokkenheid partnerBetrokkenheid = maakBetrokkenheid(partner, relatie, SoortBetrokkenheid.PARTNER);

            relatie.addBetrokkenheid(ikBetrokkenheid);
            relatie.addBetrokkenheid(partnerBetrokkenheid);

            return new RelatieHelper(relatie, partner, ik, ikBetrokkenheid, partnerBetrokkenheid);
        }

        private static Persoon maakGerelateerdePersoon() {
            final Persoon gerelateerde = new Persoon(SoortPersoon.PSEUDO_PERSOON);
            gerelateerde.setId(2L);
            return gerelateerde;
        }

        private static Betrokkenheid maakBetrokkenheid(final Persoon persoon, final Relatie relatie, final SoortBetrokkenheid soortBetrokkenheid) {
            final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(soortBetrokkenheid, relatie);
            persoon.addBetrokkenheid(ikBetrokkenheid);
            return ikBetrokkenheid;
        }

        private static Persoon maakIkPersoon(final BijhoudingVerzoekBericht bericht) {
            final Persoon ik = new Persoon(SoortPersoon.INGESCHREVENE);
            ik.setId(1L);
            final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(ik);
            bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
            when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_PERSOON_SLEUTEL)).thenReturn(bijhoudingPersoon);
            return ik;
        }
    }
}
