/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;

public class NationaliteitElementTest extends AbstractNaamTest {
    private static final Nationaliteit DLD = new Nationaliteit("DLD", "0032");
    private static final Nationaliteit NL = new Nationaliteit("NL", "0001");
    private AbstractBmrGroep.AttributenBuilder abuilder = new AbstractBmrGroep.AttributenBuilder();

    @Before
    public void setup() {
        abuilder.objecttype("Nationaliteit");
        abuilder.communicatieId("nattionaliteit");
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0032")).thenReturn(DLD);
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0001")).thenReturn(NL);

        when(getDynamischeStamtabelRepository().getRedenVerliesNLNationaliteitByCode("001")).thenReturn(new RedenVerliesNLNationaliteit("001", "omschrijving"));
    }

    @Test
    public void success() {
        final StringElement natCode = new StringElement("0032");
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, null, null);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testReferentie() {
        final ElementBuilder builder = new ElementBuilder();
        final PersoonElement persoon = builder.maakPersoonGegevensElement("actieNPers", "1234");
        final NationaliteitElement nationaliteit = builder.maakNationaliteitElementVerlies("einde", null, "ref123", null);
        final NationaliteitElement nationaliteitObj = builder.maakNationaliteitElement("ref123", "0016", null);
        Map<String, BmrGroep> map = new HashMap<>();
        map.put("ref123", nationaliteitObj);
        nationaliteit.initialiseer(map);
        assertEquals(nationaliteitObj, nationaliteit.getReferentie());
    }

    @Bedrijfsregel(Regel.R1685)
    @Test
    public void NationaliteitCodeIsGeenBestaandStamGegeven() {
        final StringElement natCode = new StringElement("500");
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, null, null);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1685, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1688)
    @Test
    public void NationaliteitNLZonderReden() {
        final StringElement natCode = new StringElement(Nationaliteit.NEDERLANDSE);
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, null, null);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1688, meldingen.get(0).getRegel());
    }

    @Test
    public void testR1686_GeenMelding() {
        when(getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode("001"))
                .thenReturn(new RedenVerkrijgingNLNationaliteit("001", "reden"));

        final StringElement natCode = new StringElement(Nationaliteit.NEDERLANDSE);
        final StringElement redenVerkrijgingCode = new StringElement("001");
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, redenVerkrijgingCode, null);
        assertEquals(0, element.valideerInhoud().size());
    }

    @Bedrijfsregel(Regel.R1686)
    @Test
    public void testR1686_WelMelding() {
        when(getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode("2")).thenReturn(null);

        final StringElement natCode = new StringElement(Nationaliteit.NEDERLANDSE);
        final StringElement redenVerkrijgingCode = new StringElement("001");
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, redenVerkrijgingCode, null);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1686, meldingen.get(0).getRegel());
    }

    @Test
    public void testR1687GeldigeCode() {
        final StringElement natCode = new StringElement("0032");
        final StringElement verliesCode = new StringElement("001");
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, null, verliesCode);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R1687)
    public void testR1687GeenGeldigeCode() {
        final StringElement natCode = new StringElement("0032");
        final StringElement verliesCode = new StringElement("002");
        NationaliteitElement element = new NationaliteitElement(abuilder.build(), natCode, null, verliesCode);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1687, meldingen.get(0).getRegel());
    }
}
