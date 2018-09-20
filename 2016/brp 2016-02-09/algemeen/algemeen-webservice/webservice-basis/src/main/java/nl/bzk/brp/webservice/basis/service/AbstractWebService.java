/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.AntwoordBerichtFactory;
import nl.bzk.brp.webservice.business.service.ArchiveringService;
import nl.bzk.brp.webservice.business.service.AutorisatieException;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Abstracte class die standaard methodes biedt die gebruikt worden door de verschillende service implementaties voor generieke zaken als autorisatie,
 * bericht context vulling etc.
 *
 * @param <T> Het type bericht dat door deze webservice wordt afgehandeld.
 * @param <C> Het type context dat door deze webservice wordt gebruikt.
 * @param <Y> Het type bericht resultaat dat door de webservice wordt geretourneerd.
 */
@Regels(Regel.R1268)
public abstract class AbstractWebService<T extends BerichtBericht, C extends BerichtContext, Y extends BerichtVerwerkingsResultaat> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private ArchiveringService archiveringService;

    @Inject
    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Inject
    private AutorisatieService autorisatieService;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    private BerichtIdResolver berichtIdResolver = new CxfBerichtIdResolver();

    /**
     * Voert het bericht uit door eerst het authenticatiemiddel te controleren, de context te initialiseren en dan de business laag aan te roepen voor de
     * werkelijke verwerking.
     *
     * @param bericht het bericht dat verwerkt dient te worden.
     * @return het uiteindelijke resultaat van de verwerking.
     */
    protected final Y voerBerichtUit(final T bericht) {
        zetMDCLoggingGegevens(bericht);
        Y resultaat;
        BerichtenIds berichtenIds = null;
        try {
            berichtenIds = berichtIdResolver.haalBerichtenIdsOp();
            final ReferentienummerAttribuut berichtReferentieNummer;

            final BerichtStuurgegevensGroep berichtStuurgegevens = bericht.getStuurgegevens();
            berichtReferentieNummer = berichtStuurgegevens.getReferentienummer();

            try {
                AutorisatieOffloadGegevens autorisatieOffloadGegevens;
                if (bericht.getStuurgegevens().getZendendePartijCode() == null) {
                    throw new AutorisatieException(Regel.R2120);
                }
                final Partij zendendePartij = referentieDataRepository
                    .vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt(bericht.getStuurgegevens().getZendendePartijCode())));
                if (zendendePartij == null) {
                    throw new AutorisatieException(Regel.R2120);
                }
                final BerichtParametersGroepBericht parameters = bericht.getParameters();
                if (parameters != null && parameters.getRol() != null && parameters.getRol().getWaarde() != null) {
                    final Rol berichtRol = parameters.getRol().getWaarde();
                    if (zendendePartij.getRollen() == null || !zendendePartij.getRollen().contains(berichtRol)) {
                        throw new AutorisatieException(Regel.R2120);
                    }
                }

                try {
                    autorisatieOffloadGegevens = autorisatieService.geefAutorisatieOffloadGegevens();
                } catch (AutorisatieException e) {
                    // Voorlopig kan dit voorkomen, oin en transporteur worden nog niet correct doorgegeven uit offloader.
                    // in deze situatie gebruiken we de zendende partij.
                    // Deze code moet verwijderd worden als ondertekenaar en transporteur beschikbaar zijn
                    autorisatieOffloadGegevens = new AutorisatieOffloadGegevens(zendendePartij, zendendePartij);
                }
                checkAutorisatie(autorisatieOffloadGegevens, bericht);
                final C context = bouwBerichtContext(berichtReferentieNummer, berichtenIds, zendendePartij, bericht.getCommunicatieIdMap());
                resultaat = verwerkBericht(bericht, context);
            } catch (AutorisatieException e) {
                LOG.info(FunctioneleMelding.ALGEMEEN_PARTIJ_NIET_GEAUTHENTICEERD,
                    "Authenticatie Fout: Partij niet geauthenticeerd in bericht {}",
                    berichtenIds.getIngaandBerichtId());
                resultaat = bouwBerichtResultaatMetMelding(e.getRegel(), null, (BerichtIdentificeerbaar) berichtStuurgegevens);
            }
        } catch (final Exception e) {
            String berichtId = "<<onbekend>>";
            if (berichtenIds != null) {
                berichtId = berichtenIds.getIngaandBerichtId().toString();
            }
            LOG.error("Onbekende fout opgetreden in bericht {}", berichtId, e);
            resultaat =
                bouwBerichtResultaatMetMelding(Regel.ALG0001,
                    "Er is een onbekende fout opgetreden in de verwerking. Probeer later nogmaals",
                    bericht.getStuurgegevens());
        }

        return resultaat;
    }

    protected abstract void checkAutorisatie(final AutorisatieOffloadGegevens autenticatieOffloadGegevens, final T bericht)
        throws AutorisatieException;

    /**
     * Zet MDC gegevens zodat alle logging gekoppeld kan worden aan bericht.
     *
     * @param bericht het bericht wat verwerkt wordt
     */
    private void zetMDCLoggingGegevens(final T bericht) {
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, bericht.getStuurgegevens().getZendendePartijCode());
        MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER, bericht.getStuurgegevens().getReferentienummer().getWaarde());
        if (bericht.getParameters() != null && bericht.getParameters().getLeveringsautorisatieID() != null) {
            MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, bericht.getParameters().getLeveringsautorisatieID());
        }
    }

    /**
     * Het antwoord bericht dat door de webservice moet worden geretourneerd aan de client, wordt hier opgebouwd.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @param resultaat      Het resultaat van de verwerking van het ingaand bericht.
     * @return Het antwoord bericht.
     */
    @Regels(Regel.R1268)
    protected final AntwoordBericht stelAntwoordBerichtSamenOpBasisVanVerwerkingsResultaat(
        final Bericht ingaandBericht, final BerichtVerwerkingsResultaat resultaat)
    {
        final BerichtenIds berichtenIds = berichtIdResolver.haalBerichtenIdsOp();
        final BerichtParametersGroepBericht parametersIngaand =
            (BerichtParametersGroepBericht) ingaandBericht.getParameters();
        final BerichtStuurgegevensGroepBericht stuurgegevensIngaand =
            (BerichtStuurgegevensGroepBericht) ingaandBericht.getStuurgegevens();
        Long adminHandId = null;
        if (resultaat.getAdministratieveHandeling() != null) {
            adminHandId = resultaat.getAdministratieveHandeling().getID();
        }
        Leveringsautorisatie la = null;
        if (parametersIngaand != null && parametersIngaand.getLeveringsautorisatie() != null) {
            la = parametersIngaand.getLeveringsautorisatie().getWaarde();
        }

        archiveringService.werkIngaandBerichtInfoBij(berichtenIds.getIngaandBerichtId(), adminHandId,
            stuurgegevensIngaand, parametersIngaand, ingaandBericht.getSoort().getWaarde(),
            resultaat.getTeArchiverenPersonenIngaandBericht());

        final AntwoordBericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        VerwerkingswijzeAttribuut verwerkingswijze = null;
        if (parametersIngaand != null && parametersIngaand.getVerwerkingswijze() != null) {
            verwerkingswijze = parametersIngaand.getVerwerkingswijze();
        }

        archiveringService.werkUitgaandBerichtInfoBij(berichtenIds.getUitgaandBerichtId(), adminHandId,
            antwoordBericht.getStuurgegevens(), antwoordBericht.getResultaat(), antwoordBericht.getSoort(),
            la, verwerkingswijze, resultaat.getTeArchiverenPersonenUitgaandBericht());

        return antwoordBericht;
    }

    /**
     * Verwerkt het bericht via de bericht verwerker.
     *
     * @param bericht Het te verwerken bericht.
     * @param context De bericht context.
     * @return Een concrete instantie van een bericht resultaat.
     */
    protected abstract Y verwerkBericht(final T bericht, C context);


    /**
     * Bouwt een {@link nl.bzk.brp.webservice.business.stappen.AbstractBerichtContext} instantie met daarin onder andere de opgegeven berichten ids en het
     * opgegeven authenticatiemiddel. Tevens zal de partij in de context worden gezet.
     *
     * @param berichtReferentieNummer  Het referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param berichtenIds             de ids van het in- en uitgaande bericht behorende bij deze berichtverwerking.
     * @param partij                   het authenticatiemiddel waarmee de partij zich heeft geauthenticeerd.
     * @param identificeerbareObjecten de map met ids en objecten
     * @return een geinitialiseerde berichten context met daarin de berichtenids, het authenticatiemiddel en de partij.
     */
    protected abstract C bouwBerichtContext(final ReferentienummerAttribuut berichtReferentieNummer,
        final BerichtenIds berichtenIds, final Partij partij,
        final CommunicatieIdMap identificeerbareObjecten);

    /**
     * Bouwt een standaard {@link nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat} dat geretourneerd kan worden en voegt daar een enkele
     * melding aan toe met de opgegeven {@link nl.bzk.brp.model.validatie.Melding} en omschrijving. Indien de omschrijving <code>null</code> is, zal de
     * standaard omschrijving behorende bij de MeldingCode worden gebruikt als omschrijving.
     *
     * @param regel        de regel die aangeeft wat voor probleem er eventueel is geconstateerd.
     * @param omschrijving de omschrijving van de melding die meer informatie geeft over hetgeen is opgetreden.
     * @param groep        de groep waarop de melding van toepassing is.
     * @return een te retourneren bericht resultaat met een enkele melding.
     */
    protected final Y bouwBerichtResultaatMetMelding(final Regel regel, final String omschrijving,
        final BerichtIdentificeerbaar groep)
    {
        final Melding melding = new Melding(SoortMelding.FOUT, regel, groep, "");
        if (omschrijving != null) {
            melding.setMeldingTekst(omschrijving);
        }
        return getResultaatInstantie(Collections.singletonList(melding));
    }

    /**
     * Creeert een concrete instantie van het bericht resultaat.
     *
     * @param meldingen De meldingen die in het resultaat moeten zitten.
     * @return Een instantie van een bericht resultaat.
     */
    protected abstract Y getResultaatInstantie(final List<Melding> meldingen);

    public void setAutorisatieService(final AutorisatieService autorisatieService) {
        this.autorisatieService = autorisatieService;
    }

    public void setBerichtIdResolver(final BerichtIdResolver berichtIdResolver) {
        this.berichtIdResolver = berichtIdResolver;
    }
}
