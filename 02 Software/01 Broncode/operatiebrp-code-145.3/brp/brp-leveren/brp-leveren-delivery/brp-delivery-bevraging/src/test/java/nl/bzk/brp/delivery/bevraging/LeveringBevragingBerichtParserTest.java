/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekbereik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.service.algemeen.request.MaakDomSourceException;
import nl.bzk.brp.service.algemeen.request.XmlUtils;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 * UT voor {@link LeveringBevragingBerichtParser}.
 */
public class LeveringBevragingBerichtParserTest {

    @Test
    public void parseGeefDetailsPersoon()
            throws MaakDomSourceException, VerzoekParseException, TransformerException {
        InputStream stream = this.getClass().getResourceAsStream("/lvg_bvgGeefDetailsPersoon_bsn.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(stream));

        final GeefDetailsPersoonVerzoek bericht = valideerBasisGeefDetailsPersoon(domSource);

        final Set<String> scopingElementen = bericht.getScopingElementen().getElementen();
        final String communicatieIdScopingElementen = bericht.getScopingElementen().getCommunicatieId();
        assertEquals(2, scopingElementen.size());
        assertTrue(scopingElementen.contains("Persoon.Geboorte.Datum"));
        assertTrue(scopingElementen.contains("Persoon.Identificatienummers.Burgerservicenummer"));
        Assert.assertEquals("04V", communicatieIdScopingElementen);
        Assert.assertEquals("Geen", bericht.getParameters().getHistorieFilterParameters().getVerantwoording());
    }

    private GeefDetailsPersoonVerzoek valideerBasisGeefDetailsPersoon(final DOMSource domSource)
            throws VerzoekParseException, TransformerException {
        final LeveringBevragingBerichtParser parser = new LeveringBevragingBerichtParser();
        final GeefDetailsPersoonVerzoek bericht = (GeefDetailsPersoonVerzoek) parser.parse(domSource);
        assertStuurgegevensEnParameters(bericht);
        assertEquals(HistorieVorm.MATERIEEL_FORMEEL, bericht.getParameters().getHistorieFilterParameters().getHistorieVorm());
        assertEquals("2012-01-01T22:22:22.017+01:00", bericht.getParameters().getHistorieFilterParameters().getPeilMomentFormeelResultaat());
        assertEquals("2012-05-01", bericht.getParameters().getHistorieFilterParameters().getPeilMomentMaterieelResultaat());
        assertEquals(SoortDienst.GEEF_DETAILS_PERSOON, bericht.getSoortDienst());
        return bericht;
    }

    @Test
    public void parseZoekPersoon()
            throws MaakDomSourceException, VerzoekParseException, TransformerException {
        InputStream stream = this.getClass().getResourceAsStream("/lvg_bvgZoekPersoon_Verzoek.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(stream));

        final AbstractZoekPersoonVerzoek bericht = valideerBasisZoekPersoon(domSource);

        final Set<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteria = bericht.getZoekCriteriaPersoon();
        final Map<String, AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaMap = new HashMap<>();
        for (AbstractZoekPersoonVerzoek.ZoekCriteria criteria : zoekCriteria) {
            zoekCriteriaMap.put(criteria.getElementNaam(), criteria);
        }
        valideer("Persoon.Adres.GemeenteCode", "0518", Zoekoptie.EXACT, zoekCriteriaMap);
        valideer("Persoon.SamengesteldeNaam.Geslachtsnaamstam", "Pronk", Zoekoptie.KLEIN, zoekCriteriaMap);
        valideer("Persoon.Geboorte.Datum", "1978-06-26", Zoekoptie.EXACT, zoekCriteriaMap);
    }

    @Test
    public void parseGeefMedebewoners()
            throws MaakDomSourceException, VerzoekParseException, TransformerException {
        InputStream stream = this.getClass().getResourceAsStream("/lvg_bvgGeefMedebewoners_Verzoek.xml");
        DOMSource domSource = XmlUtils.toDOMSource(new InputSource(stream));

        final LeveringBevragingBerichtParser parser = new LeveringBevragingBerichtParser();
        final GeefMedebewonersVerzoek bericht = (GeefMedebewonersVerzoek) parser.parse(domSource);

        assertStuurgegevensEnParameters(bericht);
        assertEquals("2016-04-01", bericht.getParameters().getPeilmomentMaterieel());
        assertEquals(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, bericht.getSoortDienst());
        assertThat(bericht.getIdentificatiecriteria().getCommunicatieId(), is("02I"));
        assertThat(bericht.getIdentificatiecriteria().getBurgerservicenummer(), is("1234567890"));
        assertThat(bericht.getIdentificatiecriteria().getAdministratienummer(), is("1122334455"));
        assertThat(bericht.getIdentificatiecriteria().getObjectSleutel(), is("123"));
        assertThat(bericht.getIdentificatiecriteria().getGemeenteCode(), is("AMS"));
        assertThat(bericht.getIdentificatiecriteria().getAfgekorteNaamOpenbareRuimte(), is("uh?"));
        assertThat(bericht.getIdentificatiecriteria().getHuisnummer(), is("53"));
        assertThat(bericht.getIdentificatiecriteria().getHuisletter(), is("A"));
        assertThat(bericht.getIdentificatiecriteria().getHuisnummertoevoeging(), is("B"));
        assertThat(bericht.getIdentificatiecriteria().getLocatieTenOpzichteVanAdres(), is("ergens aan de overkant"));
        assertThat(bericht.getIdentificatiecriteria().getPostcode(), is("1111AB"));
        assertThat(bericht.getIdentificatiecriteria().getWoonplaatsnaam(), is("Amsterdam"));
        assertThat(bericht.getIdentificatiecriteria().getIdentificatiecodeNummeraanduiding(), is("A151"));
    }

    private void valideer(final String elementNaam1, final String waarde, final Zoekoptie zoekOptie, final Map<String, AbstractZoekPersoonVerzoek.ZoekCriteria>
            zoekCriteriaMap) {
        final AbstractZoekPersoonVerzoek.ZoekCriteria zoekCriteria = zoekCriteriaMap.get(elementNaam1);
        Assert.assertNotNull(zoekCriteria);
        Assert.assertEquals(waarde, zoekCriteria.getWaarde());
        Assert.assertEquals(zoekOptie, zoekCriteria.getZoekOptie());
    }

    private AbstractZoekPersoonVerzoek valideerBasisZoekPersoon(final DOMSource domSource)
            throws VerzoekParseException, TransformerException {
        final LeveringBevragingBerichtParser parser = new LeveringBevragingBerichtParser();
        final AbstractZoekPersoonVerzoek bericht = (AbstractZoekPersoonVerzoek) parser.parse(domSource);

        assertStuurgegevensEnParameters(bericht);
        assertEquals("2016-04-01", bericht.getParameters().getZoekBereikParameters().getPeilmomentMaterieel());
        assertEquals(Zoekbereik.PEILMOMENT, bericht.getParameters().getZoekBereikParameters().getZoekBereik());
        assertEquals(SoortDienst.ZOEK_PERSOON, bericht.getSoortDienst());
        return bericht;
    }


    private void assertStuurgegevensEnParameters(final BevragingVerzoek bericht) {
        assertEquals("930101", bericht.getStuurgegevens().getZendendePartijCode());
        assertEquals("Afnemer A", bericht.getStuurgegevens().getZendendSysteem());
        assertEquals("88409eeb-1aa5-43fc-8614-43055123a165", bericht.getStuurgegevens().getReferentieNummer());
        assertThat(bericht.getStuurgegevens().getTijdstipVerzending(), is(instanceOf(ZonedDateTime.class)));
        assertEquals("Afnemer", bericht.getParameters().getRolNaam());
        assertEquals("123", bericht.getParameters().getLeveringsAutorisatieId());
        assertEquals("567", bericht.getParameters().getDienstIdentificatie());
    }
}
