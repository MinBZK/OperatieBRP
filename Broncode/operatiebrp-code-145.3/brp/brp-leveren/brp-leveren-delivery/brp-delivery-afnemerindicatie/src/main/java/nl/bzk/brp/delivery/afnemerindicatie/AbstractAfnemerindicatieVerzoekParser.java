/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractDienstVerzoekParser;
import nl.bzk.brp.service.afnemerindicatie.Afnemerindicatie;
import nl.bzk.brp.service.afnemerindicatie.AfnemerindicatieVerzoek;
import org.w3c.dom.Node;

/**
 * Abstracte basis voor het parsen van afnemerindicatieverzoeken.
 */
abstract class AbstractAfnemerindicatieVerzoekParser extends AbstractDienstVerzoekParser<AfnemerindicatieVerzoek> {

    @Override
    public final String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE.getIdentifier();
    }

    @Override
    public final void vulParameters(final AfnemerindicatieVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.getParameters().setCommunicatieId(
                getNodeTextContent(getPrefix() + "/brp:parameters/@brp:communicatieID", xPath, node));
        verzoek.getParameters()
                .setLeveringsAutorisatieId(
                        getNodeTextContent(getPrefix() + "/brp:parameters/brp:leveringsautorisatieIdentificatie", xPath, node));
    }

    /**
     * Geef dienst specifieke prefix.
     * @return dienst specifieke prefix
     */
    protected abstract String getDienstSpecifiekePrefix();

    /**
     * Geef actie specifieke prefix.
     * @return actie specifieke prefix
     */
    protected abstract String getActieSpecifiekePrefix();

    @Override
    public void vulDienstSpecifiekeGegevens(final AfnemerindicatieVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.setDummyAfnemerCode(getNodeTextContent(getDienstSpecifiekePrefix() + "/brp:partijCode", xPath, node));

        final Afnemerindicatie afnemerindicatie = getAfnemerIndicatie(xPath, node);
        verzoek.setAfnemerindicatie(afnemerindicatie);
    }

    private Afnemerindicatie getAfnemerIndicatie(final XPath xPath, final Node node) {
        final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
        afnemerindicatie
                .setBsn(getNodeTextContent(getActieSpecifiekePrefix() + "/brp:persoon/brp:identificatienummers/brp:burgerservicenummer", xPath, node));
        afnemerindicatie.setPartijCode(
                getNodeTextContent(getActieSpecifiekePrefix() + "/brp:persoon/brp:afnemerindicaties/brp:afnemerindicatie/brp:partijCode", xPath, node));
        final String datumAanvangMaterielePeriode =
                getNodeTextContent(
                        getActieSpecifiekePrefix() + "/brp:persoon/brp:afnemerindicaties/brp:afnemerindicatie/brp:datumAanvangMaterielePeriode", xPath, node);
        if (datumAanvangMaterielePeriode != null) {
            afnemerindicatie
                    .setDatumAanvangMaterielePeriode(datumAanvangMaterielePeriode);
        }
        final String datumEindeVolgen = getNodeTextContent(
                getActieSpecifiekePrefix() + "/brp:persoon/brp:afnemerindicaties/brp:afnemerindicatie/brp:datumEindeVolgen", xPath, node);
        if (datumEindeVolgen != null) {
            afnemerindicatie.setDatumEindeVolgen(datumEindeVolgen);
        }
        return afnemerindicatie;
    }
}
