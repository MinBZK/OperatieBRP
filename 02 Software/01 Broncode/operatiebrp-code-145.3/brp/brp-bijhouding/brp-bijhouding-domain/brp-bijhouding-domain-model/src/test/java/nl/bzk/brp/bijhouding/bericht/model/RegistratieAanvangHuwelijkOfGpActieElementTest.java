/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R2117;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R2181;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class RegistratieAanvangHuwelijkOfGpActieElementTest extends AbstractHuwelijkInNederlandTestBericht {

    @Mock
    BijhoudingVerzoekBericht bericht;

    @Mock
    ActieElement dactie;

    private final Map<String, String> attr = new HashMap<>();
    private final StringElement dummy = new StringElement("dummy");
    private final IdentificatienummersElement identificatieNummers = new IdentificatienummersElement(attr, dummy, dummy);
    private final SamengesteldeNaamElement samengesteldeNaamElement =
            new SamengesteldeNaamElement(attr, BooleanElement.NEE, dummy, dummy, dummy, dummy, new CharacterElement('A'), dummy);
    private final GeboorteElement geboorte = new GeboorteElement(attr, new DatumElement(20010101), dummy, dummy, dummy, dummy, dummy, dummy);
    private final GeslachtsaanduidingElement geslacht = new GeslachtsaanduidingElement(attr, dummy);

    @Before
    public void setup() {
        attr.clear();
        attr.put(OBJECTTYPE_ATTRIBUUT.toString(), "persoon");
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(dactie);

        final AdministratieveHandelingElement element =
                new AdministratieveHandelingElement(
                        attr,
                        AdministratieveHandelingElementSoort.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                        new StringElement("1547"),
                        null,
                        null,
                        null,
                        Collections.emptyList(),
                        acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(element);
        when(dactie.getPeilDatum()).thenReturn(new DatumElement(20150101));
        SoortDocument soortDoc = mock(SoortDocument.class);
        when(soortDoc.getRegistersoort()).thenReturn('3');
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(soortDoc);
    }

    @Test
    public void R2181_groepen_op_pseudo() {
        RegistratieAanvangGeregistreerdPartnerschapActieElement actie =
                maakValidatieActie(identificatieNummers, samengesteldeNaamElement, geboorte, geslacht, null, null, null, null, true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON)));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "2")).thenReturn(
                BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)));
        final List<MeldingElement> meldingElements = actie.valideerInhoud();
        assertEquals(2, meldingElements.size());
        assertEquals(R2117, meldingElements.get(0).getRegel());
        assertEquals(R2181, meldingElements.get(1).getRegel());
    }

    @Bedrijfsregel(Regel.R1868)
    @Test
    public void R1868_heeftVerwantschap() {
        final Persoon kind = new Persoon(SoortPersoon.INGESCHREVENE);
        kind.setId(1L);
        final Persoon ouder = new Persoon(SoortPersoon.INGESCHREVENE);
        ouder.setId(2L);
        maakRelatieTussenPersonen(kind, ouder);

        assertTrue(Persoon.bestaatVerwantschap(kind, ouder));

        final RegistratieAanvangHuwelijkActieElement actieElement = getRegistratieAanvangHuwelijkActieElement(kind, ouder);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "0606", new Partij("partij", "023456")));
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        final MeldingElement melding = meldingen.get(0);
        assertEquals(Regel.R1868, melding.getRegel());
    }

    @Bedrijfsregel(Regel.R1868)
    @Test
    public void R1868_GeenVerwantschap() {
        final Persoon kind = new Persoon(SoortPersoon.INGESCHREVENE);
        kind.setId(1L);
        final Persoon ouder = new Persoon(SoortPersoon.INGESCHREVENE);
        ouder.setId(2L);

        assertFalse(Persoon.bestaatVerwantschap(kind, ouder));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "0606", new Partij("partij", "023456")));
        final RegistratieAanvangHuwelijkActieElement actieElement = getRegistratieAanvangHuwelijkActieElement(kind, ouder);
        assertEquals(0, actieElement.valideerSpecifiekeInhoud().size());
    }

    @Bedrijfsregel(Regel.R1868)
    @Test
    public void R1868_Betrokkenheid_Pseudo() {
        final Persoon kind = new Persoon(SoortPersoon.INGESCHREVENE);
        kind.setId(1L);
        final Persoon ouder = null;
        assertFalse(Persoon.bestaatVerwantschap(kind, ouder));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "0606", new Partij("partij", "023456")));
        final RegistratieAanvangHuwelijkActieElement actieElement = getRegistratieAanvangHuwelijkActieElement(kind, ouder);
        assertEquals(0, actieElement.valideerSpecifiekeInhoud().size());
    }

    @Bedrijfsregel(Regel.R1868)
    @Test
    public void R1868_Beide_Betrokkenheid_Pseudo() {
        final Persoon kind = null;
        final Persoon ouder = null;
        assertFalse(Persoon.bestaatVerwantschap(kind, ouder));
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "0606", new Partij("partij", "023456")));
        final RegistratieAanvangHuwelijkActieElement actieElement = getRegistratieAanvangHuwelijkActieElement(kind, ouder);
        assertEquals(0, actieElement.valideerSpecifiekeInhoud().size());
    }

    @Test
    public void tesGemeenteAanvangEnPartijAkteIncorrect() {
        final Persoon kind = new Persoon(SoortPersoon.INGESCHREVENE);
        kind.setId(1L);
        final Persoon ouder = new Persoon(SoortPersoon.INGESCHREVENE);
        ouder.setId(2L);

        final RegistratieAanvangHuwelijkActieElement registratieAanvangHuwelijkGPActieElement = getRegistratieAanvangHuwelijkActieElement(kind, ouder);
        registratieAanvangHuwelijkGPActieElement.setVerzoekBericht(bericht);

        final List<MeldingElement> result = registratieAanvangHuwelijkGPActieElement.valideerSpecifiekeInhoud();
        assertEquals(1, result.size());
        assertEquals(Regel.R1862, result.get(0).getRegel());
    }

    private RegistratieAanvangHuwelijkActieElement getRegistratieAanvangHuwelijkActieElement(final Persoon kind, final Persoon ouder) {
        final ElementBuilder builder = new ElementBuilder();

        final PersoonGegevensElement kindElement;
        if (kind != null) {
            final String kindObjectsleutel = "kind";
            when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, kindObjectsleutel)).thenReturn(new BijhoudingPersoon(kind));
            kindElement = builder.maakPersoonGegevensElement("kind", kindObjectsleutel);
            kindElement.setVerzoekBericht(bericht);
        } else {
            final ElementBuilder.PersoonParameters kindParams = new ElementBuilder.PersoonParameters();
            kindParams.identificatienummers(builder.maakIdentificatienummersElement("kindIdentNrs", "bsn", "anr"));
            kindElement = builder.maakPersoonGegevensElement("kind", null, null, kindParams);
        }
        final BetrokkenheidElement kindBetrokkenheidElement = builder.maakBetrokkenheidElement("kindBetr", BetrokkenheidElementSoort.KIND, kindElement, null);

        // Ouder
        final PersoonGegevensElement ouderElement;
        if (ouder != null) {
            final String ouderObjectsleutel = "ouder";
            when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, ouderObjectsleutel)).thenReturn(new BijhoudingPersoon(ouder));
            // Ouder PersoonElement
            ouderElement = builder.maakPersoonGegevensElement("ouder", ouderObjectsleutel);
            ouderElement.setVerzoekBericht(bericht);
        } else {
            final ElementBuilder.PersoonParameters ouderParams = new ElementBuilder.PersoonParameters();
            ouderParams.identificatienummers(builder.maakIdentificatienummersElement("ouderIdentNrs", "bsn", "anr"));
            ouderElement = builder.maakPersoonGegevensElement("ouder", null, null, ouderParams);
        }
        final BetrokkenheidElement ouderBetrokkenheidElement =
                builder.maakBetrokkenheidElement("ouderBetr", BetrokkenheidElementSoort.OUDER, ouderElement,
                        builder.maakOuderschapElement("ouderschap", true));

        // Relatie
        final ElementBuilder.RelatieGroepParameters relatieParams = new ElementBuilder.RelatieGroepParameters();
        relatieParams.datumAanvang(2016_01_01);
        relatieParams.gemeenteAanvangCode("0606");
        final RelatieGroepElement relatieGroep = builder.maakRelatieGroepElement("relatie", relatieParams);
        final HuwelijkElement
                huwelijkElement =
                builder.maakHuwelijkElement("huwelijk", relatieGroep, Arrays.asList(kindBetrokkenheidElement, ouderBetrokkenheidElement));

        final BronElement
                bronElement =
                builder.maakBronElement("ci_test_2",
                        new DocumentElement(attr, new StringElement("33"), new StringElement("3"), null, new StringElement("023456")));
        HashMap<String, BmrGroep> bronParams = new HashMap<>();
        bronParams.put("ci_test_2", bronElement);
        BronReferentieElement bronReferentieElement = builder.maakBronReferentieElement("com_bron", "ci_test_2");
        bronReferentieElement.initialiseer(bronParams);

        return builder.maakRegistratieAanvangHuwelijkActieElement("actie", 2016_01_01, Collections.singletonList(bronReferentieElement), huwelijkElement);
    }

    private void maakRelatieTussenPersonen(final Persoon kind, final Persoon ouder) {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        kindBetrokkenheid.addBetrokkenheidHistorie(new BetrokkenheidHistorie(kindBetrokkenheid));

        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        ouderBetrokkenheid.addBetrokkenheidHistorie(new BetrokkenheidHistorie(ouderBetrokkenheid));

        relatie.addBetrokkenheid(kindBetrokkenheid);
        relatie.addBetrokkenheid(ouderBetrokkenheid);
        kind.addBetrokkenheid(kindBetrokkenheid);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
    }

    private RegistratieAanvangGeregistreerdPartnerschapActieElement maakValidatieActie(
            final IdentificatienummersElement ids,
            final SamengesteldeNaamElement samnaam,
            final GeboorteElement geb,
            final GeslachtsaanduidingElement geslAand,
            final SamengesteldeNaamElement samnaamPs, final IdentificatienummersElement idsPs, final GeboorteElement gebPs,
            final GeslachtsaanduidingElement geslAandPs, final boolean p2Objectsleutel) {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(new HashMap<>(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        relatie.setVerzoekBericht(bericht);
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        final PersoonGegevensElement p1;
        final PersoonGegevensElement p2;
        final Map<String, String> at1 = new AbstractBmrGroep.AttributenBuilder().objecttype("persoon").objectSleutel("1").communicatieId("1").build();
        final Map<String, String> at2;
        if (p2Objectsleutel) {
            at2 = new AbstractBmrGroep.AttributenBuilder().objecttype("persoon").objectSleutel("2").communicatieId("2").build();
        } else {
            at2 = new AbstractBmrGroep.AttributenBuilder().objecttype("persoon").communicatieId("2").build();
        }
        final Map<String, String> at3 = new AbstractBmrGroep.AttributenBuilder().objecttype("relatie").communicatieId("3").build();
        p1 = createPersoon(at1, samnaam, ids, geb, geslAand);
        p2 = createPersoon(at2, samnaamPs, idsPs, gebPs, geslAandPs);

        p1.setVerzoekBericht(bericht);
        p2.setVerzoekBericht(bericht);

        final BetrokkenheidElement betrokkenheidElement1 = new BetrokkenheidElement(attr, BetrokkenheidElementSoort.PARTNER, p1, null, null, null);
        betrokkenheidElement1.setVerzoekBericht(bericht);
        betrokkenheden.add(betrokkenheidElement1);
        final BetrokkenheidElement betrokkenheidElement2 = new BetrokkenheidElement(attr, BetrokkenheidElementSoort.PARTNER, p2, null, null, null);
        betrokkenheidElement2.setVerzoekBericht(bericht);
        betrokkenheden.add(betrokkenheidElement2);

        final GeregistreerdPartnerschapElement gpElement = new GeregistreerdPartnerschapElement(at3, relatie, betrokkenheden);
        gpElement.setVerzoekBericht(bericht);
        final RegistratieAanvangGeregistreerdPartnerschapActieElement result =
                new RegistratieAanvangGeregistreerdPartnerschapActieElement(attr, new DatumElement(20010101), null, new ArrayList<>(), gpElement);
        result.setVerzoekBericht(bericht);
        return result;
    }

}
