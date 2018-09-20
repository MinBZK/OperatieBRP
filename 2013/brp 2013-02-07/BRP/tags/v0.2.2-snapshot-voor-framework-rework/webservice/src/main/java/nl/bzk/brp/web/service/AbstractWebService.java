/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.xml.ws.WebServiceContext;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.service.ArchiveringService;
import nl.bzk.brp.business.service.AuthenticatieService;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.logisch.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.web.AbstractAntwoordBericht;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstracte class die standaard methodes biedt die gebruikt worden door de verschillende service implementaties voor
 * generieke zaken als authenticatie, bericht context vulling etc.
 *
 * @param <T> Het type bericht dat door deze webservice wordt afgehandeld.
 * @param <Y> Het type bericht resultaat dat door de webservice wordt geretourneerd.
 */
public abstract class AbstractWebService<T extends BerichtBericht, Y extends BerichtVerwerkingsResultaat> {

    private static final Logger  LOG = LoggerFactory.getLogger(AbstractWebService.class);

    @Inject
    private AuthenticatieService authenticatieService;

    @Inject
    private ArchiveringService archiveringService;

    @Inject
    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Resource
    private WebServiceContext    wsContext;

    /**
     * Voert het bericht uit door eerst het authenticatiemiddel te controleren, de context te initialiseren en dan
     * de business laag aan te roepen voor de werkelijke verwerking.
     *
     * @param bericht het bericht dat verwerkt dient te worden.
     * @return het uiteindelijke resultaat van de verwerking.
     */
    protected Y voerBerichtUit(final T bericht) {
        Y resultaat;
        AuthenticatieMiddelModel authenticatieMiddel;
        BerichtenIds berichtenIds = null;

        try {
            berichtenIds = WebserviceUtil.haalBerichtenIdsOp();
            authenticatieMiddel = checkAuthenticatieMiddel(bericht, berichtenIds);
            Sleutelwaardetekst berichtReferentieNummer = null;

            final BerichtStuurgegevensGroep berichtStuurgegevens = bericht.getBerichtStuurgegevensGroep();
            if (berichtStuurgegevens != null) {
                berichtReferentieNummer = berichtStuurgegevens.getReferentienummer();
            }

            if (isGeauthenticeerd(authenticatieMiddel)) {
                BerichtContext context = bouwBerichtContext(berichtReferentieNummer, berichtenIds, authenticatieMiddel);
                resultaat = verwerkBericht(bericht, context);
            } else {
                LOG.info("Authenticatie Fout: Partij niet geauthenticeerd in bericht {}",
                        berichtenIds.getIngaandBerichtId());
                resultaat = bouwBerichtResultaatMetMelding(MeldingCode.AUTH0001, null,
                        (Identificeerbaar) berichtStuurgegevens);
            }
        } catch (Throwable t) {
            String berichtId = "<<onbekend>>";
            if (berichtenIds != null) {
                berichtId = berichtenIds.getIngaandBerichtId().toString();
            }
            LOG.error(String.format("Onbekende fout opgetreden in bericht %s", berichtId), t);
            resultaat =
                bouwBerichtResultaatMetMelding(MeldingCode.ALG0001,
                        "Er is een onbekende fout opgetreden in de verwerking. Probeer later nogmaals",
                        (Identificeerbaar) bericht.getBerichtStuurgegevensGroep());
        }

        return resultaat;
    }

    /**
     * Het antwoord bericht dat door de webservice moet worden geretourneerd aan de client, wordt hier opgebouwd.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @param resultaat Het resultaat van de verwerking van het ingaand bericht.
     * @return Het antwoord bericht.
     */
    protected AbstractAntwoordBericht stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(
            final BerichtBericht ingaandBericht,
            final BerichtVerwerkingsResultaat resultaat)
    {
        final BerichtenIds berichtenIds = WebserviceUtil.haalBerichtenIdsOp();
        archiveringService.werkIngaandBerichtInfoBij(
                berichtenIds.getIngaandBerichtId(),
                ingaandBericht.getBerichtStuurgegevensGroep(),
                ingaandBericht.getSoortBericht());

        final AbstractAntwoordBericht antwoordBericht =
                antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        archiveringService.werkUitgaandBerichtInfoBij(
                berichtenIds.getUitgaandBerichtId(),
                antwoordBericht.getBerichtStuurgegevensGroep(),
                antwoordBericht.getBerichtResultaatGroep());

        return antwoordBericht;
    }

    /**
     * Verwerkt het bericht via de bericht verwerker.
     *
     * @param bericht Het te verwerken bericht.
     * @param context De bericht context.
     * @return Een concrete instantie van een bericht resultaat.
     */
    protected abstract Y verwerkBericht(final T bericht, BerichtContext context);

    /**
     * Controleert of de aanroep correct is geauthenticeerd door te controleren of er een geldig authenticatieMiddel
     * is gevonden.
     *
     * @param authenticatieMiddel het authenticatie middel dat is gevonden en dat gecontroleerd dient te worden.
     * @return of de authenticatie correct is verlopen.
     */
    protected boolean isGeauthenticeerd(final AuthenticatieMiddelModel authenticatieMiddel) {
        return (authenticatieMiddel != null);
    }

    /**
     * Bouwt een {@link BerichtContext} instantie met daarin onder andere de opgegeven berichten ids en het opgegeven
     * authenticatiemiddel. Tevens zal de partij in de context worden gezet.
     *
     * @param berichtReferentieNummer Het referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param berichtenIds de ids van het in- en uitgaande bericht behorende bij deze berichtverwerking.
     * @param authenticatieMiddel het authenticatiemiddel waarmee de partij zich heeft geauthenticeerd.
     * @return een geinitialiseerde berichten context met daarin de berichtenids, het authenticatiemiddel en de partij.
     */
    protected BerichtContext bouwBerichtContext(final Sleutelwaardetekst berichtReferentieNummer,
                                                final BerichtenIds berichtenIds,
                                                final AuthenticatieMiddelModel authenticatieMiddel)
    {
        return new BerichtContext(berichtenIds, authenticatieMiddel.getId(), authenticatieMiddel.getPartij(),
                berichtReferentieNummer.getWaarde());
    }

    /**
     * Bouwt een standaard {@link nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat} dat geretourneerd kan worden en
     * voegt daar een enkele melding aan
     * toe met de opgegeven {@link nl.bzk.brp.model.validatie.MeldingCode} en omschrijving. Indien de omschrijving
     * <code>null</code> is, zal de standaard omschrijving behorende bij de MeldingCode worden gebruikt als
     * omschrijving.
     *
     * @param meldingCode de code van de melding die aangeeft wat voor probleem er eventueel is geconstateerd.
     * @param omschrijving de omschrijving van de melding die meer informatie geeft over hetgeen is opgetreden.
     * @param groep de groep waarop de melding van toepassing is.
     * @return een te retourneren bericht resultaat met een enkele melding.
     */
    protected Y bouwBerichtResultaatMetMelding(final MeldingCode meldingCode, final String omschrijving,
            final Identificeerbaar groep)
    {
        Melding melding = new Melding(Soortmelding.FOUT, meldingCode, groep, "");
        if (omschrijving != null) {
            melding.setOmschrijving(omschrijving);
        }
        Y resultaat = getResultaatInstantie(Arrays.asList(melding));
        resultaat.markeerVerwerkingAlsFoutief();
        return resultaat;
    }

    /**
     * Haalt het unieke authenticatiemiddel op voor het opgegeven bericht op basis van het certificaat en de partij
     * welke uit het bericht en de bijbehorende context worden gehaald. Indien er geen uniek authenticatiemiddel kan
     * worden gevonden voor de opgegeven partij en het uit de context gehaalde certificaat, dan zal deze methode
     * <code>null</code> retourneren, wat dus betekent dat de partij niet geauthenticeerd is. Indien er wel een uniek
     * authenticatiemiddel kan worden gevonden, dan zal deze worden geretourneerd.
     *
     * @param bericht Het bericht.
     * @param berichtenIds de ids van het ingaande en het uitgaande bericht.
     * @return het voor het bericht geldende authenticatiemiddel of <code>null</code> indien er geen uniek
     *         authenticatiemiddel kon worden gevonden.
     */
    protected AuthenticatieMiddelModel checkAuthenticatieMiddel(final T bericht, final BerichtenIds berichtenIds) {
        final X509Certificate certificaat = WebserviceUtil.haalClientCertificaatOp(wsContext);
        Short partijId = null;

        try {
            partijId = bericht.getPartijId();
        } catch (NumberFormatException ne) {
            return null;
        }

        AuthenticatieMiddelModel resultaat;
        if (certificaat != null && partijId != null) {
            List<AuthenticatieMiddelModel> authenticatieMiddelen =
                authenticatieService.haalAuthenticatieMiddelenOp(partijId, certificaat);
            if (authenticatieMiddelen.size() == 1) {
                resultaat = authenticatieMiddelen.get(0);
            } else {
                LOG.debug("Authenticatie fout: Partij {} niet geauthenticeerd voor bericht {}", partijId,
                    berichtenIds.getIngaandBerichtId());
                resultaat = null;
                if (authenticatieMiddelen.size() > 1) {
                    LOG.warn(
                        "Misconfiguratie: Partij {} beschikt over niet uniek authenticatiemiddel voor certificaat {}.",
                        partijId, certificaat.getSerialNumber());
                }
            }
        } else {
            LOG.warn("Authenticatie fout: geen partij en/of geen certificaat aanwezig in bericht {}",
                berichtenIds.getIngaandBerichtId());
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Creeert een concrete instantie van het bericht resultaat.
     *
     * @param meldingen De meldingen die in het resultaat moeten zitten.
     * @return Een instantie van een bericht resultaat.
     */
    protected abstract Y getResultaatInstantie(final List<Melding> meldingen);

}
