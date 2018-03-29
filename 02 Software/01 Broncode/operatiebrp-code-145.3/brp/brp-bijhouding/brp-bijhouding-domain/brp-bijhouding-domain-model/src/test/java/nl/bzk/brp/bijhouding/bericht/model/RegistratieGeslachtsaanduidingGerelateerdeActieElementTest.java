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

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest voor {@link RegistratieGeslachtsaanduidingGerelateerdeActieElement}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistratieGeslachtsaanduidingGerelateerdeActieElementTest extends AbstractElementTest {
    private static final String PERSOON_SLEUTEL = "persoonSleutel";
    private static final String HUWELIJK_SLEUTEL = "huwelijkSleutel";
    private ElementBuilder builder;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructieZonderPersoon() {
        builder.maakRegistratieGeslachtsaanduidingGerelateerdeActieElement("actie", 2016_01_01, null);
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

        final Integer datumAanvangGeldigheid = 2016_01_01;
        final RegistratieGeslachtsaanduidingGerelateerdeActieElement
                actie =
                builder.maakRegistratieGeslachtsaanduidingGerelateerdeActieElement("actie", datumAanvangGeldigheid, persoon);

        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonSleutel)).thenReturn(bijhoudingPersoon);

        assertEquals(persoon, actie.getPersoonElementen().get(0));
        assertEquals(SoortActie.REGISTRATIE_GESLACHTSAANDUIDING_GERELATEERDE, actie.getSoortActie());
        assertEquals(persoon, actie.getPersoonElementen().get(0));
        assertEquals(bijhoudingPersoon, actie.getHoofdPersonen().get(0));
    }

    @Test
    public void testVerwerking_persoonNietVerwerkbaar() {
        final RegistratieGeslachtsaanduidingGerelateerdeActieElement actie = maakActie(new ElementBuilder.PersoonParameters());
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.GBA);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL)).thenReturn(bijhoudingPersoon);
        assertNull(actie.verwerk(getBericht(), getAdministratieveHandeling()));
    }

    @Test
    public void testVerwerking_persoonVerwerkbaar() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("geslachtsaanduiding", "M"));
        final RegistratieGeslachtsaanduidingGerelateerdeActieElement actie = maakActie(persoonParameters);
        final Persoon ik = new Persoon(SoortPersoon.INGESCHREVENE);
        ik.setId(1L);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(ik);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL)).thenReturn(bijhoudingPersoon);

        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        bijhoudingPersoon.addBetrokkenheid(ikBetrokkenheid);

        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Persoon partner = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        partner.setId(2L);
        partner.addBetrokkenheid(partnerBetrokkenheid);

        relatie.addBetrokkenheid(ikBetrokkenheid);
        relatie.addBetrokkenheid(partnerBetrokkenheid);

        final BijhoudingRelatie bijhoudingRelatie = BijhoudingRelatie.decorate(relatie);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, HUWELIJK_SLEUTEL)).thenReturn(bijhoudingRelatie);
        assertEquals(0, partner.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertNotNull(actie.verwerk(getBericht(), getAdministratieveHandeling()));
        assertEquals(1, partner.getPersoonGeslachtsaanduidingHistorieSet().size());
    }

    private RegistratieGeslachtsaanduidingGerelateerdeActieElement maakActie(final ElementBuilder.PersoonParameters gerelateerdePersoonParameters) {
        final PersoonGegevensElement partnerPersoon = builder.maakPersoonGegevensElement("persoon_2", "persoon2Sleutel", null, gerelateerdePersoonParameters);
        final BetrokkenheidElement partner =
                builder.maakBetrokkenheidElement("partner", "partnerSleutel", BetrokkenheidElementSoort.PARTNER, partnerPersoon, null);
        final HuwelijkElement huwelijk = builder.maakHuwelijkElement("huwelijk", HUWELIJK_SLEUTEL, null, Collections.singletonList(partner));
        huwelijk.setVerzoekBericht(getBericht());
        final BetrokkenheidElement betrokkenheid =
                builder.maakBetrokkenheidElement("betrokkenheid", "betrokkenheidSleutel", null, BetrokkenheidElementSoort.PARTNER, huwelijk);
        final PersoonRelatieElement persoon =
                builder.maakPersoonRelatieElement("persoon_1", null, PERSOON_SLEUTEL, Collections.singletonList(betrokkenheid));
        persoon.setVerzoekBericht(getBericht());
        final Integer datumAanvangGeldigheid = 2016_01_01;
        return builder.maakRegistratieGeslachtsaanduidingGerelateerdeActieElement("actie", datumAanvangGeldigheid, persoon);

    }
}
