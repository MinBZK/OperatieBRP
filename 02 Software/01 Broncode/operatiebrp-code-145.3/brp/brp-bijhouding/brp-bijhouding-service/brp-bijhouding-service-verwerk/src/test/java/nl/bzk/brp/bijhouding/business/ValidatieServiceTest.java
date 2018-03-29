/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.DatumElement;
import nl.bzk.brp.bijhouding.bericht.model.GeboorteElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;
import org.junit.Test;

/**
 * Unittest voor {@link ValidatieServiceImpl}.
 */
public class ValidatieServiceTest extends AbstractBijhoudingTest {

    private final ValidatieService validatieService = new ValidatieServiceImpl();

    @Test
    public void testValideer() {
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.valideer()).thenReturn(Collections.emptyList());
        final List<MeldingElement> meldingen = validatieService.valideer(bericht);
        assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testKanVerwerkingDoorgaan() {
        assertTrue(validatieService.kanVerwerkingDoorgaan(Collections.emptyList()));
        final GeboorteElement geboorteElement =
                new GeboorteElement(getAttributen(), new DatumElement(20160101), null, null, null, null, null, new StringElement("6030"));
        final List<MeldingElement> meldingen = new ArrayList<>();
        // WAARSCHUWING
        meldingen.add(MeldingElement.getInstance(Regel.R1572, geboorteElement));
        assertTrue(validatieService.kanVerwerkingDoorgaan(meldingen));

        // DEBLOKKEERBAAR
        meldingen.clear();
        meldingen.add(MeldingElement.getInstance(Regel.R1378, geboorteElement));
        assertFalse(validatieService.kanVerwerkingDoorgaan(meldingen));
        // FOUT
        meldingen.clear();
        meldingen.add(MeldingElement.getInstance(Regel.R2030, geboorteElement));
        assertFalse(validatieService.kanVerwerkingDoorgaan(meldingen));
    }

    @Test
    public void testBepaalHoogsteMeldingNiveau() {
        assertEquals(SoortMelding.GEEN, validatieService.bepaalHoogsteMeldingNiveau(Collections.emptyList()));

        final GeboorteElement geboorteElement =
                new GeboorteElement(getAttributen(), new DatumElement(20160101), null, null, null, null, null, new StringElement("6030"));
        final List<MeldingElement> meldingen = new ArrayList<>();
        meldingen.add(MeldingElement.getInstance(Regel.R1572, geboorteElement));
        assertEquals(SoortMelding.WAARSCHUWING, validatieService.bepaalHoogsteMeldingNiveau(meldingen));

        meldingen.add(MeldingElement.getInstance(Regel.R1378, geboorteElement));
        assertEquals(SoortMelding.DEBLOKKEERBAAR, validatieService.bepaalHoogsteMeldingNiveau(meldingen));

        meldingen.add(MeldingElement.getInstance(Regel.R2030, geboorteElement));
        assertEquals(SoortMelding.FOUT, validatieService.bepaalHoogsteMeldingNiveau(meldingen));
    }
}
