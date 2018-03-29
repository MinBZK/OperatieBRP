/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractVerzoekParserTest;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import org.junit.Test;

/**
 * Unit test voor {@link GeefDetailsPersoonVerzoekParser}.
 */
public class GeefDetailsPersoonVerzoekParserTest extends AbstractVerzoekParserTest {

    private static final String BRP_PARAMETERS_BRP_HISTORIEVORM = "/brp:parameters/brp:historievorm";
    private static final String BRP_PARAMETERS_BRP_PEILMOMENTFORMEELRESULTAAT = "/brp:parameters/brp:peilmomentFormeelResultaat";
    private static final String BRP_PARAMETERS_BRP_PEILMOMENTMATERIEELRESULTAAT = "/brp:parameters/brp:peilmomentMaterieelResultaat";

    private final GeefDetailsPersoonVerzoekParser parser = new GeefDetailsPersoonVerzoekParser();

    @Test
    public void testVulHistorieFilterParameters() throws Exception {
        final GeefDetailsPersoonVerzoek testVerzoek = new GeefDetailsPersoonVerzoek();
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + BRP_PARAMETERS_BRP_HISTORIEVORM, "MaterieelFormeel");
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + BRP_PARAMETERS_BRP_PEILMOMENTFORMEELRESULTAAT, "1980-07-23T11:11:11");
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + BRP_PARAMETERS_BRP_PEILMOMENTMATERIEELRESULTAAT, "1980-07-23");

        parser.vulParameters(testVerzoek, node, xPath);

        assertThat(testVerzoek.getParameters().getHistorieFilterParameters().getHistorieVorm(), is(HistorieVorm.MATERIEEL_FORMEEL));
        assertThat(testVerzoek.getParameters().getHistorieFilterParameters().getPeilMomentFormeelResultaat(),
                is("1980-07-23T11:11:11"));
        assertThat(testVerzoek.getParameters().getHistorieFilterParameters().getPeilMomentMaterieelResultaat(),
                is("1980-07-23"));
    }

    @Test
    public void testParseParametersLeegVerzoek() {
        // Voor coverage null checks.
        parser.vulParameters(new GeefDetailsPersoonVerzoek(), node, xPath);
    }

    @Test
    public void testParseDienstSpecifiekLegeScopeElementLijst() throws Exception {
        final GeefDetailsPersoonVerzoek verzoek = new GeefDetailsPersoonVerzoek();
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + "/brp:identificatiecriteria/@brp:communicatieID", "commid");
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + "/brp:identificatiecriteria/@brp:objectSleutel", "sleutel");
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + "/brp:identificatiecriteria/brp:burgerservicenummer", "123456789");
        mockNode("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + "/brp:identificatiecriteria/brp:administratienummer", "987654321");
        mockNodeListLength("/brp:" + SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier() + "/brp:scopeElementen/brp:elementNaam", 0);
        parser.vulDienstSpecifiekeGegevens(verzoek, node, xPath);
    }

}