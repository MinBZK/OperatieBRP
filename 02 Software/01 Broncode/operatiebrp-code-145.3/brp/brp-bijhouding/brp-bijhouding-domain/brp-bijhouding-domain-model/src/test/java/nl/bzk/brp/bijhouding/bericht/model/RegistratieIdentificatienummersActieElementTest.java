/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECT_SLEUTEL_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unittests voor {@link RegistratieIdentificatienummersActieElement}.
 */
public class RegistratieIdentificatienummersActieElementTest extends AbstractElementTest {

    private static final String ANUMMER = "6508521746";
    private static final String BSN = "655742153";
    private static final String COMM_ID = "ci_test";
    private static final DatumElement PEIL_DATUM = new DatumElement(20160101);
    @Mock
    private BijhoudingVerzoekBericht bericht;
    @Mock
    private ActieElement dummyActie;

    private ElementBuilder builder;
    private Map<String, String> attr;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        attr = new LinkedHashMap<>();
        attr.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attr.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), COMM_ID);
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(dummyActie)).soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND).partijCode("5100").bronnen(
                Collections.emptyList());
        final AdministratieveHandelingElement administratieveHandelingElement = builder.maakAdministratieveHandelingElement("com_ah", ahParams);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
    }

    @Test
    public void testSoortActie() {
        assertEquals(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, maakElement("6508521746", "655742153").getSoortActie());
    }

    @Test
    public void testPeildatum() {
        assertEquals(PEIL_DATUM, maakElement("6508521746", "655742153").getPeilDatum());
    }

    @Test
    public void testVerwerk() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters
                .geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("bla", null, null, null, null, "Janssen")));
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoon = actieElement.getPersoon();
        persoon.initialiseer(commMap);
        final BijhoudingPersoon persoonEntiteit = persoon.getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);

        final BRPActie actie = actieElement.verwerk(bericht, getAdministratieveHandeling());
        assertNotNull(actie);
        assertNotNull(persoonEntiteit);
        assertEquals(1, persoonEntiteit.getPersoonIDHistorieSet().size());
    }

    @Test
    public void testVerwerk_PersoonNietVerwerkbaar() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters
                .geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("bla", null, null, null, null, "Janssen")));
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoon = actieElement.getPersoon();
        persoon.initialiseer(commMap);
        final BijhoudingPersoon persoonEntiteit = persoon.getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN);

        final BRPActie actie = actieElement.verwerk(bericht, getAdministratieveHandeling());
        assertNull(actie);
        assertEquals(0, persoonEntiteit.getPersoonIDHistorieSet().size());
    }

    @Test
    public void testR1586_Bsn_uniek() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters
                .geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("bla", null, null, null, null, "Janssen")));
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(persoon, new Partij("partij", "000001"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.FOUT));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(bijhoudingPersoon);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);
        when(getPersoonRepository().komtBsnReedsVoor(anyString())).thenReturn(false);
        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoonElement = actieElement.getPersoon();
        persoonElement.initialiseer(commMap);
        assertEquals(0, actieElement.valideerSpecifiekeInhoud().size());
    }

    @Test
    public void testR1586_Bsn_niet_uniek() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters
                .geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("bla", null, null, null, null, "Janssen")));
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        when(getPersoonRepository().komtBsnReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoon = actieElement.getPersoon();
        persoon.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        final MeldingElement melding = meldingen.get(0);
        assertEquals(Regel.R1586, melding.getRegel());
    }


    @Test
    public void testR1586_Bsn_Persoon_Pseudo() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        when(getPersoonRepository().komtBsnReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoon = actieElement.getPersoon();
        persoon.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1586_Bsn_Persoon_Bestaande_Persoon_Opgeschort_Fout() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        attr.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "123");
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);
        nieuwPersoon.setVerzoekBericht(bericht);
        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(persoon, new Partij("partij", "000001"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.FOUT));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(bijhoudingPersoon);

        when(getPersoonRepository().komtBsnReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoonElement = actieElement.getPersoon();
        persoonElement.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1586_Bsn_Persoon_Bestaande_Persoon_Opgeschort_Gewist() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        attr.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "123");
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);
        nieuwPersoon.setVerzoekBericht(bericht);
        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(persoon, new Partij("partij", "000001"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.GEWIST));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(bijhoudingPersoon);

        when(getPersoonRepository().komtBsnReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoonElement = actieElement.getPersoon();
        persoonElement.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1588_ANr_Persoon_Pseudo() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        when(getPersoonRepository().komtAdministratienummerReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoon = actieElement.getPersoon();
        persoon.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1588_Anr_Persoon_Bestaande_Persoon_Opgeschort_Fout() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        attr.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), "123");
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);
        nieuwPersoon.setVerzoekBericht(bericht);
        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(persoon, new Partij("partij", "000001"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.FOUT));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(bijhoudingPersoon);

        when(getPersoonRepository().komtAdministratienummerReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoonElement = actieElement.getPersoon();
        persoonElement.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1588_ANr_niet_uniek() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters
                .geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("bla", null, null, null, null, "Janssen")));
        final PersoonGegevensElement nieuwPersoon = builder.maakPersoonGegevensElement(attr, persoonParameters);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(COMM_ID, nieuwPersoon);

        when(getPersoonRepository().komtAdministratienummerReedsVoor(anyString())).thenReturn(true);

        final RegistratieIdentificatienummersActieElement actieElement = maakElement(ANUMMER, BSN);
        final PersoonGegevensElement persoon = actieElement.getPersoon();
        persoon.initialiseer(commMap);

        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        final MeldingElement melding = meldingen.get(0);
        assertEquals(Regel.R1588, melding.getRegel());
    }

    private RegistratieIdentificatienummersActieElement maakElement(final String anummer, final String bsn) {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.identificatienummers(builder.maakIdentificatienummersElement("ci_idnum", bsn, anummer));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("ci_persoon", null, COMM_ID, persoonParameters);
        RegistratieIdentificatienummersActieElement
                element =
                new RegistratieIdentificatienummersActieElement(attr, PEIL_DATUM, null, Collections.emptyList(), persoonElement);
        persoonElement.setVerzoekBericht(bericht);
        element.setVerzoekBericht(bericht);
        return element;
    }
}
