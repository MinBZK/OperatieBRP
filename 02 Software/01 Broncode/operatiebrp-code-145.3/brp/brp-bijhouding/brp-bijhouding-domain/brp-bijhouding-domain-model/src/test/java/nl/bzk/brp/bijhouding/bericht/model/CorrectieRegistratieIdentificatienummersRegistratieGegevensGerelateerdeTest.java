/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerdeTest {

    private static final String PARTNER_OBJ_SLEUTEL = "partnerObjSleutel";
    private static final String IK_OBJ_SLEUTEL = "ikObjSleutel";
    private ElementBuilder builder;
    private BijhoudingVerzoekBericht bericht;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
        bericht = mock(BijhoudingVerzoekBericht.class);
    }

    @Test
    public void testGetSoortActie() {
        final PersoonRelatieElement persoonRelatieElement = builder.maakPersoonRelatieElement("persoon", null, "1234", Collections.emptyList());
        final CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde actieElement =
                builder.maakCorrectieRegistratieIdentificatienummersGerelateerdeActieElement("commId", 2016_01_01, null, persoonRelatieElement);
        assertEquals(SoortActie.CORRECTIEREGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE, actieElement.getSoortActie());
    }

    @Test
    public void testBepaalSetVoorNieuwVoorkomen() {
        final CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde actie = maakBasisActie(new ElementBuilder.PersoonParameters());

        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        partner.addPersoonIDHistorie(historie);
        final BijhoudingPersoon bijhoudingPartner = new BijhoudingPersoon(partner);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(bijhoudingPartner);

        final Set historieSet = actie.bepaalSetVoorNieuwVoorkomen();
        assertTrue(historieSet instanceof BijhoudingPersoonGroepSet);
        assertEquals(1, historieSet.size());
        assertTrue(historieSet.contains(historie));
    }

    @Test
    public void testVerwerk() {
        final Integer datumAanvang = 2001_01_01;
        final Integer datumEinde = 2016_01_01;
        final ElementBuilder.PersoonParameters partnerParams = new ElementBuilder.PersoonParameters();
        final IdentificatienummersElement identificatienummersElement = builder.maakIdentificatienummersElement("idComm", "123456789", "1234567890");
        partnerParams.identificatienummers(identificatienummersElement);
        final CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde actieElement = maakActie(partnerParams, datumAanvang, datumEinde);

        final BijhoudingPersoon ik = mock(BijhoudingPersoon.class);
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        final Set<PersoonIDHistorie> historieSet = new HashSet<>();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_OBJ_SLEUTEL)).thenReturn(ik);
        when(ik.getBijhoudingSituatie()).thenReturn(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        when(partner.maakNieuweIDHistorieVoorCorrectie(identificatienummersElement)).thenReturn(historie);
        when(partner.getPersoonIDHistorieSet()).thenReturn(historieSet);

        final AdministratieveHandeling administratieveHandeling = mock(AdministratieveHandeling.class);
        final Partij partij = mock(Partij.class);
        when(administratieveHandeling.getPartij()).thenReturn(partij);
        when(administratieveHandeling.getDatumTijdRegistratie()).thenReturn(Timestamp.from(Instant.now()));

        final BRPActie actie = actieElement.verwerk(bericht, administratieveHandeling);
        assertNotNull(actie);
        assertEquals(1, historieSet.size());
        final MaterieleHistorie nieuweHistorie = historieSet.iterator().next();
        assertNotNull(nieuweHistorie.getActieInhoud());
        assertEquals(datumAanvang, nieuweHistorie.getDatumAanvangGeldigheid());
        assertEquals(datumEinde, nieuweHistorie.getDatumEindeGeldigheid());
        assertEquals(actie.getDatumTijdRegistratie(), nieuweHistorie.getDatumTijdRegistratie());
    }

    @Test
    public void testVerwerk_datumEindeNull() {
        final Integer datumAanvang = 2001_01_01;
        final Integer datumEinde = null;
        final ElementBuilder.PersoonParameters partnerParams = new ElementBuilder.PersoonParameters();
        final IdentificatienummersElement identificatienummersElement = builder.maakIdentificatienummersElement("idComm", "123456789", "1234567890");
        partnerParams.identificatienummers(identificatienummersElement);
        final CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde actieElement = maakActie(partnerParams, datumAanvang, datumEinde);

        final BijhoudingPersoon ik = mock(BijhoudingPersoon.class);
        final BijhoudingPersoon partner = mock(BijhoudingPersoon.class);
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        final Set<PersoonIDHistorie> historieSet = new HashSet<>();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_OBJ_SLEUTEL)).thenReturn(ik);
        when(ik.getBijhoudingSituatie()).thenReturn(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        when(partner.maakNieuweIDHistorieVoorCorrectie(identificatienummersElement)).thenReturn(historie);
        when(partner.getPersoonIDHistorieSet()).thenReturn(historieSet);

        final AdministratieveHandeling administratieveHandeling = mock(AdministratieveHandeling.class);
        final Partij partij = mock(Partij.class);
        when(administratieveHandeling.getPartij()).thenReturn(partij);
        when(administratieveHandeling.getDatumTijdRegistratie()).thenReturn(Timestamp.from(Instant.now()));

        final BRPActie actie = actieElement.verwerk(bericht, administratieveHandeling);
        assertNotNull(actie);
        assertEquals(1, historieSet.size());
        final MaterieleHistorie nieuweHistorie = historieSet.iterator().next();
        assertNotNull(nieuweHistorie.getActieInhoud());
        assertEquals(datumAanvang, nieuweHistorie.getDatumAanvangGeldigheid());
        assertNull(nieuweHistorie.getDatumEindeGeldigheid());
        assertEquals(actie.getDatumTijdRegistratie(), nieuweHistorie.getDatumTijdRegistratie());
    }

    @Bedrijfsregel(Regel.R2528)
    @Test
    public void testR2528() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(persoon);
        final PersoonIDHistorie historie = new PersoonIDHistorie(persoon);
        when(persoon.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(historie);

        assertEquals(0, maakBasisActie(new ElementBuilder.PersoonParameters()).valideerSpecifiekeInhoud().size());

        builder = new ElementBuilder();
        assertEquals(0, maakActie(new ElementBuilder.PersoonParameters(), 2016_01_01, 2017_01_01).valideerSpecifiekeInhoud().size());

        builder = new ElementBuilder();
        final List<MeldingElement> meldingen = maakActie(new ElementBuilder.PersoonParameters(), 2016_01_01, 2016_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2528, meldingen.get(0).getRegel());

        builder = new ElementBuilder();
        final List<MeldingElement> meldingen2 = maakActie(new ElementBuilder.PersoonParameters(), 2016_01_01, 2015_01_01).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen2.size());
        assertEquals(Regel.R2528, meldingen2.get(0).getRegel());
    }


    private CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde maakBasisActie(final ElementBuilder.PersoonParameters partnerParams) {
        return maakActie(partnerParams, 2016_01_01, null);
    }

    private CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde maakActie(final ElementBuilder.PersoonParameters partnerParams,
                                                                                              final int datumAanvang, final Integer datumEinde) {
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner", PARTNER_OBJ_SLEUTEL, null, partnerParams);
        final BetrokkenheidElement partnerBetrokkenheid = builder.maakBetrokkenheidElement("partnerBetr", "partnerBetrObjSleutel",
                BetrokkenheidElementSoort.PARTNER, partner, null);
        final GeregistreerdPartnerschapElement gpElement =
                builder.maakGeregistreerdPartnerschapElement("gpElement", null, Collections.singletonList(partnerBetrokkenheid));
        final BetrokkenheidElement ikBetrokkenheid =
                builder.maakBetrokkenheidElement("ikBetr", "betrObjSleutel", null, BetrokkenheidElementSoort.OUDER, gpElement);
        final PersoonRelatieElement ikPersoon = builder.maakPersoonRelatieElement("persoon", null, IK_OBJ_SLEUTEL, Collections.singletonList(ikBetrokkenheid));
        final CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde actie =
                builder.maakCorrectieRegistratieIdentificatienummersGerelateerdeActieElement("commId", datumAanvang, datumEinde, ikPersoon);
        partner.setVerzoekBericht(bericht);
        ikPersoon.setVerzoekBericht(bericht);
        actie.setVerzoekBericht(bericht);
        return actie;
    }
}