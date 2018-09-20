/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
@Ignore //TODO Refactor fix, test tijdelijk uitgezet
public class ActieBerichtBindingTest extends AbstractBindingInTest<ActieBericht> {

    @Test
    public void testActie() throws JiBXException {
        final String xml = "<brp:Objecttype_Actie brp:cIDVerzendend=\"id.inschrijvingGeboorte\" "
            + "xmlns:brp=\"" + NAMESPACE_BRP + "\">\n"
            + "      <brp:datumAanvangGeldigheid " +
            "brp:noValue=\"nietOndersteund\">20120101</brp:datumAanvangGeldigheid>\n"
            + "      <brp:datumEindeGeldigheid brp:noValue=\"nietOndersteund\">20120101</brp:datumEindeGeldigheid>\n"
            + "      <brp:tijdstipOntlening brp:noValue=\"nietOndersteund\">2012012109051235</brp:tijdstipOntlening>\n"
            + "      <brp:toelichtingOntlening " +
            "brp:noValue=\"nietOndersteund\">toelichtingOntlening</brp:toelichtingOntlening>\n"
            + "      <brp:bronnen>\n"
            + "        <brp:bron brp:cIDVerzendend=\"id.bron1\">\n"
            + "          <brp:document brp:cIDVerzendend=\"id.document1\">\n"
            + "            <brp:soortNaam brp:noValue=\"nietOndersteund\">V</brp:soortNaam>\n"
            + "            <brp:identificatie brp:noValue=\"nietOndersteund\">docindent</brp:identificatie>\n"
            + "            <brp:aktenummer brp:noValue=\"nietOndersteund\">aktenr</brp:aktenummer>\n"
            + "            <brp:omschrijving brp:noValue=\"nietOndersteund\">abcd</brp:omschrijving>\n"
            + "            <brp:partijCode brp:noValue=\"nietOndersteund\">1000</brp:partijCode>\n"
            + "          </brp:document>\n"
            + "        </brp:bron>\n"
            + "      </brp:bronnen>\n"
            + "    </brp:Objecttype_Actie>";

        final ActieBericht actie = unmarshalObject(xml);
        Assert.assertNotNull(actie);
        Assert.assertEquals(new Datum(20120101), actie.getDatumAanvangGeldigheid());
        Assert.assertEquals(new Datum(20120101), actie.getDatumEindeGeldigheid());

        Assert.assertEquals(1, actie.getBronnen().size());
        Assert.assertEquals("id.bron1", actie.getBronnen().get(0).getCommunicatieID());

        Assert.assertNotNull(actie.getBronnen().get(0).getDocument());
        Assert.assertEquals("V", actie.getBronnen().get(0).getDocument().getSoortNaam());
        Assert.assertEquals("id.document1", actie.getBronnen().get(0).getDocument().getCommunicatieID());

        Assert.assertNotNull(actie.getBronnen().get(0).getDocument().getStandaard());
        DocumentStandaardGroepBericht gegevens = actie.getBronnen().get(0).getDocument().getStandaard();
        Assert.assertEquals("aktenr", gegevens.getAktenummer().getWaarde());
        Assert.assertEquals("docindent", gegevens.getIdentificatie().getWaarde());
        Assert.assertEquals("abcd", gegevens.getOmschrijving().getWaarde());
        Assert.assertEquals("1000", gegevens.getPartijCode());
    }

    @Test
    public void testActieBronNil() throws JiBXException {
        final String xml = "<brp:Objecttype_Actie brp:cIDVerzendend=\"id.inschrijvingGeboorte\""
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + "xmlns:brp=\"" + NAMESPACE_BRP + "\">\n"
            + "      <brp:datumAanvangGeldigheid " +
            "brp:noValue=\"nietOndersteund\">20120101</brp:datumAanvangGeldigheid>\n"
            + "      <brp:datumEindeGeldigheid brp:noValue=\"nietOndersteund\">20120101</brp:datumEindeGeldigheid>\n"
            + "      <brp:tijdstipOntlening brp:noValue=\"nietOndersteund\">2012010116450733</brp:tijdstipOntlening>\n"
            + "      <brp:toelichtingOntlening " +
            "brp:noValue=\"nietOndersteund\">toelichtingOntlening</brp:toelichtingOntlening>\n"
            + "      <brp:bronnen>\n"
            + "         <brp:bron xsi:nil=\"true\"/>\n"
            + "      </brp:bronnen>\n"
            + "    </brp:Objecttype_Actie>";

        final ActieBericht actie = unmarshalObject(xml);
        Assert.assertNotNull(actie);
        Assert.assertEquals(new Datum(20120101), actie.getDatumAanvangGeldigheid());
        Assert.assertEquals(new Datum(20120101), actie.getDatumEindeGeldigheid());

        Assert.assertEquals(1, actie.getBronnen().size());
        ActieBronBericht bron = actie.getBronnen().get(0);
        Assert.assertNull(bron);
    }


    @Override
    protected Class<ActieBericht> getBindingClass() {
        return ActieBericht.class;
    }
}
