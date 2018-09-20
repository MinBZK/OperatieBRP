/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

public class RelatieBerichtBindingTest extends AbstractBindingInTest<RelatieBericht> {

    @Test
    public void testFamilieRechtelijkeBetrekking() throws JiBXException {
        final String xml = "<ns:Objecttype_Relatie xmlns:ns=\"" + NAMESPACE_BRP + "\">"
                + "                <ns:betrokkenheden>\n"
                + "                    <ns:kind ns:cIDVerzendend=\"?\">\n"
                + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "                            <ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
                + "                                <ns:code>V</ns:code>\n"
                + "                            </ns:geslachtsaanduiding>\n"
                + "                            <ns:geboorte ns:cIDVerzendend=\"?\">\n"
                + "                                <ns:datum>20120101</ns:datum>\n"
                + "                                <ns:landCode>6039</ns:landCode>\n"
                + "                            </ns:geboorte>\n"
                + "                        </ns:persoon>\n"
                + "                    </ns:kind>\n"
                + "                    <ns:ouder ns:cIDVerzendend=\"?\">\n"
                + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "                        </ns:persoon>\n"
                + "                        <ns:ouderschap ns:cIDVerzendend=\"?\">\n"
                + "                        </ns:ouderschap>\n"
                + "                    </ns:ouder>\n"
                + "                    <ns:ouder ns:cIDVerzendend=\"?\">\n"
                + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "                        </ns:persoon>\n"
                + "                        <ns:ouderschap ns:cIDVerzendend=\"?\">\n"
                + "                        </ns:ouderschap>\n"
                + "                    </ns:ouder>\n"
                + "                </ns:betrokkenheden>\n"
                + "</ns:Objecttype_Relatie>";
        final RelatieBericht relatie = unmarshalObject(xml);
        Assert.assertNotNull(relatie);
        Assert.assertNotNull(relatie.getBetrokkenheden());
        Assert.assertTrue(relatie.getBetrokkenheden().size() == 3);

        Assert.assertNotNull(relatie.getKindBetrokkenheid());
        Assert.assertNotNull(relatie.getKindBetrokkenheid().getBetrokkene());

        Set<BetrokkenheidBericht> oudersBetr = relatie.getOuderBetrokkenheden();
        Assert.assertNotNull(oudersBetr);
        Assert.assertTrue(oudersBetr.size() == 2);
        for (BetrokkenheidBericht betrokkenheidBericht : oudersBetr) {
            Assert.assertNotNull(betrokkenheidBericht.getBetrokkene());
            Assert.assertNotNull(betrokkenheidBericht.getBetrokkenheidOuderschap());
        }

    }

    @Test
    public void testHuwelijk() throws JiBXException {
        final String xml = "<ns:Objecttype_Relatie xmlns:ns=\"" + NAMESPACE_BRP + "\">"
                + "                <ns:datumAanvang>20120101</ns:datumAanvang>"
                + "                <ns:gemeenteAanvangCode>1000</ns:gemeenteAanvangCode>"
                + "                <ns:woonplaatsAanvangCode>1000</ns:woonplaatsAanvangCode>"
                + "                <ns:buitenlandsePlaatsAanvang>blplaatsaanv</ns:buitenlandsePlaatsAanvang>"
                + "                <ns:buitenlandseRegioAanvang>blregioaanv</ns:buitenlandseRegioAanvang>"
                + "                <ns:landAanvangCode>1000</ns:landAanvangCode>"
                + "                <ns:omschrijvingLocatieAanvang>omslocaanv</ns:omschrijvingLocatieAanvang>"
                + "                <ns:redenEindeCode>1000</ns:redenEindeCode>"
                + "                <ns:datumEinde>20120101</ns:datumEinde>"
                + "                <ns:gemeenteEindeCode>1000</ns:gemeenteEindeCode>"
                + "                <ns:woonplaatsEindeCode>1000</ns:woonplaatsEindeCode>"
                + "                <ns:buitenlandsePlaatsEinde>blplaatseinde</ns:buitenlandsePlaatsEinde>"
                + "                <ns:buitenlandseRegioEinde>blregioeinde</ns:buitenlandseRegioEinde>"
                + "                <ns:landEindeCode>1000</ns:landEindeCode>"
                + "                <ns:omschrijvingLocatieEinde>omsloceinde</ns:omschrijvingLocatieEinde>"
                + "                <ns:betrokkenheden>\n"
                + "                    <ns:partner ns:cIDVerzendend=\"?\">\n"
                + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "                            <ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
                + "                                <ns:code>V</ns:code>\n"
                + "                            </ns:geslachtsaanduiding>\n"
                + "                            <ns:geboorte ns:cIDVerzendend=\"?\">\n"
                + "                                <ns:datum>20120101</ns:datum>\n"
                + "                                <ns:landCode>6039</ns:landCode>\n"
                + "                            </ns:geboorte>\n"
                + "                        </ns:persoon>\n"
                + "                    </ns:partner>\n"
                + "                    <ns:partner ns:cIDVerzendend=\"?\">\n"
                + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "                            <ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
                + "                                <ns:code>V</ns:code>\n"
                + "                            </ns:geslachtsaanduiding>\n"
                + "                            <ns:geboorte ns:cIDVerzendend=\"?\">\n"
                + "                                <ns:datum>20120101</ns:datum>\n"
                + "                                <ns:landCode>6039</ns:landCode>\n"
                + "                            </ns:geboorte>\n"
                + "                        </ns:persoon>\n"
                + "                    </ns:partner>\n"
                + "                </ns:betrokkenheden>\n"
                + "</ns:Objecttype_Relatie>";
        final RelatieBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        List<BetrokkenheidBericht> partners = bericht.getBetrokkenheden();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        RelatieStandaardGroepBericht standaardGroep = bericht.getGegevens();
        Assert.assertNotNull(standaardGroep);
        Assert.assertEquals(new Datum(20120101), standaardGroep.getDatumAanvang());
        Assert.assertEquals(new Datum(20120101), standaardGroep.getDatumEinde());
        Assert.assertEquals(new Gemeentecode((short) 1000), standaardGroep.getGemeenteAanvangCode());
        Assert.assertEquals(new Gemeentecode((short) 1000), standaardGroep.getGemeenteEindeCode());
        Assert.assertEquals(new PlaatsCode((short) 1000), standaardGroep.getWoonPlaatsAanvangCode());
        Assert.assertEquals(new PlaatsCode((short) 1000), standaardGroep.getWoonPlaatsEindeCode());
        Assert.assertEquals(new BuitenlandsePlaats("blplaatsaanv"), standaardGroep.getBuitenlandsePlaatsAanvang());
        Assert.assertEquals(new BuitenlandsePlaats("blplaatseinde"), standaardGroep.getBuitenlandsePlaatsEinde());
        Assert.assertEquals(new Landcode((short) 1000), standaardGroep.getLandAanvangCode());
        Assert.assertEquals(new Landcode((short) 1000), standaardGroep.getLandEindeCode());
        Assert.assertEquals(new BuitenlandseRegio("blregioaanv"), standaardGroep.getBuitenlandseRegioAanvang());
        Assert.assertEquals(new BuitenlandseRegio("blregioeinde"), standaardGroep.getBuitenlandseRegioEinde());
        Assert.assertEquals(new Omschrijving("omslocaanv"), standaardGroep.getOmschrijvingLocatieAanvang());
        Assert.assertEquals(new Omschrijving("omsloceinde"), standaardGroep.getOmschrijvingLocatieEinde());
        /*Assert.assertEquals(new PlaatsCode("1000"), standaardGroep.getRedenBeeindigingRelatie());*/
    }

    @Override
    protected Class<RelatieBericht> getBindingClass() {
        return RelatieBericht.class;
    }
}
