/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link BronElement}.
 */
public class BronElementTest extends AbstractElementTest {

    private final static String NIET_BESTAANDE_RECHTSGROND_CODE = "123";
    private final static String GELDIGE_RECHTSGROND_CODE = "456";
    private final static String ONGELDIGE_RECHTSGROND_CODE = "789";

    private ElementBuilder builder;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        final Rechtsgrond geldigeRechtsgrond = new Rechtsgrond(GELDIGE_RECHTSGROND_CODE, "Omschrijving");
        geldigeRechtsgrond.setDatumAanvangGeldigheid(20000101);
        final Rechtsgrond ongeldigeRechtsgrond = new Rechtsgrond(ONGELDIGE_RECHTSGROND_CODE, "Omschrijving");
        ongeldigeRechtsgrond.setDatumAanvangGeldigheid(20000101);
        ongeldigeRechtsgrond.setDatumEindeGeldigheid(20000101);
        when(getDynamischeStamtabelRepository().getRechtsgrondByCode(GELDIGE_RECHTSGROND_CODE))
                .thenReturn(geldigeRechtsgrond);
        when(getDynamischeStamtabelRepository().getRechtsgrondByCode(ONGELDIGE_RECHTSGROND_CODE))
                .thenReturn(ongeldigeRechtsgrond);
    }

    @Test
    public void testNietBestaandeRechtsgrondCode() {
        //setup
        final BronElement bronElementOngeldig = maakBronElement(NIET_BESTAANDE_RECHTSGROND_CODE);
        //execute
        final List<MeldingElement> meldingen =  bronElementOngeldig.valideerInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R2430);
    }

    @Test
    public void testLegeRechtsgrondCode() {
        //setup
        final BronElement bronElementOngeldig = maakBronElement(null);
        //execute
        final List<MeldingElement> meldingen =  bronElementOngeldig.valideerInhoud();
        //valideer
        controleerRegels(meldingen);
    }

    @Test
    public void testGeldigeRechtsgrondCode() {
        //setup
        final BronElement bronElementOngeldig = maakBronElement(GELDIGE_RECHTSGROND_CODE);
        //execute
        final List<MeldingElement> meldingen =  bronElementOngeldig.valideerInhoud();
        //valideer
        controleerRegels(meldingen);
    }

    @Test
    public void testOnGeldigeRechtsgrondCode() {
        //setup
        final BronElement bronElementOngeldig = maakBronElement(ONGELDIGE_RECHTSGROND_CODE);
        //execute & validate
        assertTrue(bronElementOngeldig.isRechtsgrondOngeldigOpPeildatum(20170101));
    }

    private BronElement maakBronElement(final String rechtsgrondCode) {
        final DocumentElement document = builder.maakDocumentElement("CI_document_1", "Nederlandse rechterlijke uitspraak", null, null, "507013");
        if (rechtsgrondCode == null) {
            return builder.maakBronElement("CI_bron_1", document);
        } else {
            return builder.maakBronElement("CI_bron_1", document, rechtsgrondCode);
        }
    }
}
