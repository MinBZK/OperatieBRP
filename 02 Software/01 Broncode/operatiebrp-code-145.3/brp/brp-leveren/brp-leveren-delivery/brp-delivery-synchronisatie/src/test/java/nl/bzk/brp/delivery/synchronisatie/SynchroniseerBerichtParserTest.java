/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 * SynchroniseerBerichtParserTest
 */

public class SynchroniseerBerichtParserTest {

    private SynchronisatieBerichtParser parser;

    @Test
    public void parseSynchroniseerPersoonverzoek() throws MaakDomSourceException, VerzoekParseException, TransformerException {
        final DOMSource source = XmlUtils.toDOMSource(new InputSource(this.getClass().getResourceAsStream("/lvg_synGeefSynchronisatiePersoon.xml")));
        parser = new SynchronisatieBerichtParser();
        final SynchronisatieVerzoek synchronisatieVerzoek = parser.parse(TransformerUtil.initializeNode(source));

        //stuurgegegevens
        Assert.assertEquals("identificatie00V", synchronisatieVerzoek.getStuurgegevens().getCommunicatieId());
        Assert.assertEquals("053001", synchronisatieVerzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals("BZM Leverancier A", synchronisatieVerzoek.getStuurgegevens().getZendendSysteem());
        Assert.assertEquals("88409eeb-1aa5-43fc-8614-43055123a165", synchronisatieVerzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals("2015-11-30T14:32:03.234Z[UTC]", synchronisatieVerzoek.getStuurgegevens().getTijdstipVerzending().toString());

        //parameters
        Assert.assertEquals("identificatie01V", synchronisatieVerzoek.getParameters().getCommunicatieId());
        Assert.assertEquals("123", synchronisatieVerzoek.getParameters().getLeveringsAutorisatieId());

        //zoekcriteria persoon
        Assert.assertEquals("identificatie02V", synchronisatieVerzoek.getZoekCriteriaPersoon().getCommunicatieId());
        Assert.assertEquals("123456789", synchronisatieVerzoek.getZoekCriteriaPersoon().getBsn());
    }

    @Test
    public void parseSynchroniseerStamgegevenverzoek() throws MaakDomSourceException, VerzoekParseException, TransformerException {
        final DOMSource source = XmlUtils.toDOMSource(new InputSource(this.getClass()
                .getResourceAsStream("/lvg_synGeefSynchronisatieStamgegeven.xml")));
        parser = new SynchronisatieBerichtParser();
        final SynchronisatieVerzoek synchronisatieVerzoek = parser.parse(TransformerUtil.initializeNode(source));

        //stuurgegegevens
        Assert.assertEquals("identificatie00V", synchronisatieVerzoek.getStuurgegevens().getCommunicatieId());
        Assert.assertEquals("053001", synchronisatieVerzoek.getStuurgegevens().getZendendePartijCode());
        Assert.assertEquals("BZM Leverancier A", synchronisatieVerzoek.getStuurgegevens().getZendendSysteem());
        Assert.assertEquals("88409eeb-1aa5-43fc-8614-43055123a165", synchronisatieVerzoek.getStuurgegevens().getReferentieNummer());
        Assert.assertEquals("2015-11-30T14:32:03.234Z[UTC]", synchronisatieVerzoek.getStuurgegevens().getTijdstipVerzending().toString());

        //parameters
        Assert.assertEquals("identificatie01V", synchronisatieVerzoek.getParameters().getCommunicatieId());
        Assert.assertEquals("123", synchronisatieVerzoek.getParameters().getLeveringsAutorisatieId());
        Assert.assertEquals("ElementTabel", synchronisatieVerzoek.getParameters().getStamgegeven());
    }
}
