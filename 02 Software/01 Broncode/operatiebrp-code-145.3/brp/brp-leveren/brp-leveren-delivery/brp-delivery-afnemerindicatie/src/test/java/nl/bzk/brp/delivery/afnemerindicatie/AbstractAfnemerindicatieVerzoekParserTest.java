/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import nl.bzk.brp.delivery.algemeen.AbstractVerzoekParserTest;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import org.junit.Test;

/**
 * Unit test voor {@link AbstractAfnemerindicatieVerzoekParser}.
 */
public class AbstractAfnemerindicatieVerzoekParserTest extends AbstractVerzoekParserTest {

    private static final String BRP_PARAMETERS_BRP_COMMUNICATIE_ID = "/brp:parameters/@brp:communicatieID";
    private static final String BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE = "/brp:parameters/brp:leveringsautorisatieIdentificatie";
    private static final String BRP_PERSOON_BRP_IDENTIFICATIENUMMERS_BRP_BURGERSERVICENUMMER
            = "/brp:persoon/brp:identificatienummers/brp:burgerservicenummer";
    private static final String BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_PARTIJ_CODE
            =
            "/brp:persoon/brp:afnemerindicaties/brp:afnemerindicatie/brp:partijCode";
    private static final String BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_AANVANG_MATERIELE_PERIODE
            =
            "/brp:persoon/brp:afnemerindicaties/brp:afnemerindicatie/brp:datumAanvangMaterielePeriode";
    private static final String BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_AANVANG_MATERIELE_PERIODE_WAARDE
            = "2016-03-01";
    private static final String BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_EINDE_VOLGEN
            =
            "/brp:persoon/brp:afnemerindicaties/brp:afnemerindicatie/brp:datumEindeVolgen";
    private static final String BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_EINDE_VOLGEN_WAARDE
            = "2016-03-29";

    private final AbstractAfnemerindicatieVerzoekParser parser = new TestAbstractAfnemerindicatieVerzoekParser();
    private final AfnemerindicatieVerzoek testVerzoek = new AfnemerindicatieVerzoek();

    @Test
    public void testVulParameters() throws Exception {
        mockNode(parser.getPrefix() + BRP_PARAMETERS_BRP_COMMUNICATIE_ID);
        mockNode(parser.getPrefix() + BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE);

        parser.vulParameters(testVerzoek, node, xPath);

        assertThat(testVerzoek.getParameters().getCommunicatieId(), is(parser.getPrefix() + BRP_PARAMETERS_BRP_COMMUNICATIE_ID));
        assertThat(testVerzoek.getParameters().getLeveringsAutorisatieId(),
                is(parser.getPrefix() + BRP_PARAMETERS_BRP_LEVERINGSAUTORISATIE_IDENTIFICATIE));
    }

    @Test
    public void testVulDienstSpecifiekeGegevens() throws Exception {
        mockNode(parser.getPrefix() + BRP_PERSOON_BRP_IDENTIFICATIENUMMERS_BRP_BURGERSERVICENUMMER);
        mockNode(parser.getPrefix() + BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_PARTIJ_CODE);
        mockNode(parser.getPrefix() + BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_AANVANG_MATERIELE_PERIODE,
                BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_AANVANG_MATERIELE_PERIODE_WAARDE);
        mockNode(parser.getPrefix() + BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_EINDE_VOLGEN,
                BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_EINDE_VOLGEN_WAARDE);

        parser.vulDienstSpecifiekeGegevens(testVerzoek, node, xPath);

        assertThat(testVerzoek.getAfnemerindicatie().getBsn(), is(parser.getPrefix() + BRP_PERSOON_BRP_IDENTIFICATIENUMMERS_BRP_BURGERSERVICENUMMER));
        assertThat(testVerzoek.getAfnemerindicatie().getPartijCode(),
                is(parser.getPrefix() + BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_PARTIJ_CODE));
        assertThat(testVerzoek.getAfnemerindicatie().getDatumAanvangMaterielePeriode(),
                is(BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_AANVANG_MATERIELE_PERIODE_WAARDE));
        assertThat(testVerzoek.getAfnemerindicatie().getDatumEindeVolgen(),
                is(BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_DATUM_EINDE_VOLGEN_WAARDE));
    }

    @Test
    public void testVulDienstSpecifiekeGegevensZonderOptioneleVelden() throws Exception {
        mockNode(parser.getPrefix() + BRP_PERSOON_BRP_IDENTIFICATIENUMMERS_BRP_BURGERSERVICENUMMER);
        mockNode(parser.getPrefix() + BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_PARTIJ_CODE);

        parser.vulDienstSpecifiekeGegevens(testVerzoek, node, xPath);

        assertThat(testVerzoek.getAfnemerindicatie().getBsn(), is(parser.getPrefix() + BRP_PERSOON_BRP_IDENTIFICATIENUMMERS_BRP_BURGERSERVICENUMMER));
        assertThat(testVerzoek.getAfnemerindicatie().getPartijCode(),
                is(parser.getPrefix() + BRP_PERSOON_BRP_AFNEMERINDICATIES_BRP_AFNEMERINDICATIE_BRP_PARTIJ_CODE));
        assertThat(testVerzoek.getAfnemerindicatie().getDatumAanvangMaterielePeriode(),
                is(nullValue()));
        assertThat(testVerzoek.getAfnemerindicatie().getDatumEindeVolgen(),
                is(nullValue()));
    }

    private static final class TestAbstractAfnemerindicatieVerzoekParser extends AbstractAfnemerindicatieVerzoekParser {

        @Override
        protected String getDienstSpecifiekePrefix() {
            return getPrefix();
        }

        @Override
        protected String getActieSpecifiekePrefix() {
            return getPrefix();
        }
    }
}