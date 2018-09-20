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

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.service.AuthenticatieService;
import nl.bzk.brp.dataaccess.repository.BerichtRepository;
import nl.bzk.brp.model.attribuuttype.Applicatienaam;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.bericht.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.groep.bericht.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.BerichtResultaatGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.objecttype.operationeel.AuthenticatieMiddelModel;
import nl.bzk.brp.model.objecttype.operationeel.BerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Bijhoudingsresultaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verwerkingsresultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.interceptor.ArchiveringBericht;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO OuCho Refactoring nodig, bericht DTO klassen moeten afgeleid worden van het model.
 * Abstracte class die standaard methodes biedt die gebruikt worden door de verschillende service implementaties voor
 * generieke zaken als authenticatie, bericht context vulling etc.
 *
 * @param <T> Het type bericht dat door deze webservice wordt afgehandeld.
 * @param <Y> Het type bericht resultaat dat door de webservice wordt geretourneerd.
 */
public abstract class AbstractWebService<T extends BRPBericht, Y extends BerichtResultaat> {

    private static final Logger  LOG = LoggerFactory.getLogger(AbstractWebService.class);

    @Inject
    private AuthenticatieService authenticatieService;

    @Inject
    private BerichtRepository berichtRepository;

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
            String berichtReferentieNummer = null;

            // ToDo: Prevalidatie geldt niet voor bevraging; eigenlijk dient dit alleen in Bijhouding gezet te worden.
            Ja prevalidatie = null;
            final BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
            if (berichtStuurgegevens != null) {
                prevalidatie = berichtStuurgegevens.getPrevalidatieBericht();
                berichtReferentieNummer = berichtStuurgegevens.getReferentienummer();
            }

            werkIngaandBerichtInfoBij(berichtenIds.getIngaandBerichtId(), berichtStuurgegevens,
                    bericht.getSoortBericht());
            if (isGeauthenticeerd(authenticatieMiddel)) {
                BerichtContext context = bouwBerichtContext(berichtReferentieNummer, berichtenIds, authenticatieMiddel);
                resultaat = verwerkBericht(bericht, context);
            } else {
                LOG.info("Authenticatie Fout: Partij niet geauthenticeerd in bericht {}",
                        berichtenIds.getIngaandBerichtId());
                resultaat = bouwBerichtResultaatMetMelding(MeldingCode.AUTH0001, null, berichtStuurgegevens);
            }
            resultaat.setBerichtStuurgegevens(bouwStuurGegevens(berichtReferentieNummer, prevalidatie));
            werkUitgaandBerichtInfoBij(berichtenIds.getUitgaandBerichtId(), resultaat, bericht.getSoortBericht());
        } catch (Throwable t) {
            String berichtId = "<<onbekend>>";
            if (berichtenIds != null) {
                berichtId = berichtenIds.getIngaandBerichtId().toString();
            }
            LOG.error(String.format("Onbekende fout opgetreden in bericht %s", berichtId), t);
            resultaat =
                bouwBerichtResultaatMetMelding(MeldingCode.ALG0001,
                        "Er is een onbekende fout opgetreden in de verwerking. Probeer later nogmaals",
                        bericht.getBerichtStuurgegevens());
        }

        return resultaat;
    }

    /**
     * Werk de ingaande bericht info bij in de database. Ingaand bericht is al deels opgeslagen tijdens archivering.
     * @param ingaandBerichtId Id van het ingaande bericht.
     * @param berichtStuurgegevens Bericht stuurgegevens.
     * @param soortBericht Het soort binnenkomend bericht.
     */
    private void werkIngaandBerichtInfoBij(final Long ingaandBerichtId,
                                           final BerichtStuurgegevens berichtStuurgegevens,
                                           final Soortbericht soortBericht)
    {
        final BerichtModel ingaandBericht = berichtRepository.findOne(ingaandBerichtId);
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setApplicatie(new Applicatienaam(berichtStuurgegevens.getApplicatie()));
        stuurgegevensGroep.setIndPrevalidatie(berichtStuurgegevens.getPrevalidatieBericht());
        stuurgegevensGroep.setOrganisatie(new Organisatienaam(berichtStuurgegevens.getOrganisatie()));
        stuurgegevensGroep.setReferentienummer(new Sleutelwaardetekst(berichtStuurgegevens.getReferentienummer()));
        ingaandBericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepModel(stuurgegevensGroep));
        ingaandBericht.setSoortbericht(soortBericht);
        berichtRepository.save(ingaandBericht);
    }

    /**
     * Bouwt de stuurgegevens op voor het uitgaande bericht.
     * @param berichtReferentieNummer Referentienummer van het ingaande bericht.
     * @param prevalidatie Prevalidatie vlag in het ingaande bericht.
     * @return Bericht stuurgegevens.
     */
    private BerichtStuurgegevens bouwStuurGegevens(final String berichtReferentieNummer, final Ja prevalidatie)
    {
        BerichtStuurgegevens stuurgegevens = new BerichtStuurgegevens();
        stuurgegevens.setOrganisatie("Ministerie BZK");
        stuurgegevens.setApplicatie("BRP");
        stuurgegevens.setReferentienummer(bepaalReferentieNr());
        // Het crossreferentienummer is verplicht in het antwoord bericht.
        if (StringUtils.isBlank(berichtReferentieNummer)) {
            stuurgegevens.setCrossReferentienummer("onbekend");
        } else {
            stuurgegevens.setCrossReferentienummer(berichtReferentieNummer);
        }
        stuurgegevens.setPrevalidatieBericht(prevalidatie);
        return stuurgegevens;
    }

    /**
     * Haal de unieke id op dat correspondeert met de inkomende bericht.
     *
     * @return refentieId
     */
    private String bepaalReferentieNr() {
        // haal de (unieke) id dat toevallig correspondeert met de inkomend bericht id.
        Long berichtInId;
        String referentieNr = "OnbekendeID";
        try {
            berichtInId =
                (Long) PhaseInterceptorChain.getCurrentMessage().getExchange()
                        .get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID);
        } catch (NullPointerException e) {
            berichtInId = null;
            // in geval dat PhaseInterceptorChain.getCurrentMessage().getExchange() niet bestaat
        }
        if (berichtInId != null) {
            referentieNr = berichtInId.toString();
        }

        return referentieNr;
    }

    /**
     * Werk de uitgaande bericht info bij in de database. Uitgaand bericht is al deels opgeslagen tijdens archivering.
     * @param uitgaandBerichtId Id van het uitgaande bericht.
     * @param resultaat Resultaat van de verwerking van het ingaande bericht.
     * @param soortBericht Het soort ingaand bericht.
     */
    private void werkUitgaandBerichtInfoBij(final Long uitgaandBerichtId, final Y resultaat,
                                            final Soortbericht soortBericht)
    {
        BerichtModel uitgaandBericht = berichtRepository.findOne(uitgaandBerichtId);
        final BerichtStuurgegevensGroepBericht stuurgegevensGroep = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroep.setApplicatie(new Applicatienaam(resultaat.getBerichtStuurgegevens().getApplicatie()));
        stuurgegevensGroep.setCrossReferentienummer(new Sleutelwaardetekst(
                resultaat.getBerichtStuurgegevens().getCrossReferentienummer()));
        stuurgegevensGroep.setIndPrevalidatie(resultaat.getBerichtStuurgegevens().getPrevalidatieBericht());
        stuurgegevensGroep.setOrganisatie(new Organisatienaam(resultaat.getBerichtStuurgegevens().getOrganisatie()));
        stuurgegevensGroep.setReferentienummer(new Sleutelwaardetekst(
                resultaat.getBerichtStuurgegevens().getReferentienummer()));
        uitgaandBericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepModel(stuurgegevensGroep));

        final BerichtResultaatGroepBericht berichtResultaatGroep = new BerichtResultaatGroepBericht();
        if (resultaat.getResultaatCode() == ResultaatCode.GOED) {
            berichtResultaatGroep.setVerwerkingsresultaat(Verwerkingsresultaat.GOED);
        } else {
            berichtResultaatGroep.setVerwerkingsresultaat(Verwerkingsresultaat.FOUT);
        }

        if (resultaat instanceof BijhoudingResultaat) {
            BijhoudingResultaat bijhoudingResultaat = (BijhoudingResultaat) resultaat;
            if (bijhoudingResultaat.getBijhoudingCode() == BijhoudingCode.DIRECT_VERWERKT) {
                berichtResultaatGroep.setBijhoudingsresultaat(Bijhoudingsresultaat.VERWERKT);
            } else if (bijhoudingResultaat.getBijhoudingCode() == BijhoudingCode.UITGESTELD) {
                berichtResultaatGroep.setBijhoudingsresultaat(Bijhoudingsresultaat.VERWERKING_UITGESTELD_FIATTERING);
            }
        }

        berichtResultaatGroep.setHoogsteMeldingNiveau(bepaalHoogsteMeldingNiveau(resultaat.getMeldingen()));
        uitgaandBericht.setBerichtResultaatGroep(new BerichtResultaatGroepModel(berichtResultaatGroep));
        uitgaandBericht.setSoortbericht(mapUitgaandeSoortOpBasisIngaandeSoort(soortBericht));
        berichtRepository.save(uitgaandBericht);
    }

    /**
     * Een tijdelijke mapping die is gemaakt om de soort te bepalen van uitgaande berichten. Dit wordt nu gedaan op
     * basis van het soort ingaand bericht.
     * @param soortBericht Soort van het ingaande bericht.
     * @return Soort van het uitgaande bericht.
     */
    private Soortbericht mapUitgaandeSoortOpBasisIngaandeSoort(final Soortbericht soortBericht) {
        final Soortbericht uitgaandeSoort;
        switch (soortBericht) {
            case AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING:
                uitgaandeSoort = Soortbericht.AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING_ANTWOORD;
                break;
            case HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING:
                uitgaandeSoort = Soortbericht.HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING_ANTWOORD;
                break;
            case HUWELIJKPARTNERSCHAP_REGISTRATIEPARTNERSCHAP_BIJHOUDING:
                uitgaandeSoort = Soortbericht.HUWELIJKPARTNERSCHAP_REGISTRATIEPARTNERSCHAP_BIJHOUDING_ANTWOORD;
                break;
            case MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING:
                uitgaandeSoort = Soortbericht.MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING_ANTWOORD;
                break;
            case MIGRATIE_VERHUIZING_BIJHOUDING:
                uitgaandeSoort = Soortbericht.MIGRATIE_VERHUIZING_BIJHOUDING_ANTWOORD;
                break;
            case VRAAG_DETAILSPERSOON_BEVRAGEN:
                uitgaandeSoort = Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN_ANTWOORD;
                break;
            case VRAAG_KANDIDAATVADER_BEVRAGEN:
                uitgaandeSoort = Soortbericht.VRAAG_KANDIDAATVADER_BEVRAGEN_ANTWOORD;
                break;
            case VRAAG_PERSONENOPADRESINCLUSIEFBETROKKENHEDEN_BEVRAGEN:
                uitgaandeSoort = Soortbericht.VRAAG_PERSONENOPADRESINCLUSIEFBETROKKENHEDEN_BEVRAGEN_ANTWOORD;
                break;
            default:
                throw new IllegalStateException("Mapping van ingaande en uitgaande bericht soorten is niet compleet.");
        }
        return uitgaandeSoort;
    }

    /**
     * Bepaalt het hoogste melding niveau aan de hand van de lijst met meldingen.
     * @param meldingen Lijst van meldingen.
     * @return Soortmelding.
     */
    private Soortmelding bepaalHoogsteMeldingNiveau(final List<Melding> meldingen) {
        Soortmelding hoogste;
        if (meldingen == null || meldingen.isEmpty()) {
            hoogste = null;
        } else {
            int hoogsteOrdinal = -1;
            for (Melding melding : meldingen) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            hoogste = vertaalSoortMeldingNaarNiveau(SoortMelding.values()[hoogsteOrdinal]);
        }
        return hoogste;
    }

    /**
     * Tijdelijke vertaling van SoortMelding naar Soortmelding.
     * @param soortMelding SoortMelding.
     * @return Soortmelding.
     */
    private Soortmelding vertaalSoortMeldingNaarNiveau(final SoortMelding soortMelding) {
        Soortmelding resultaat;
        switch (soortMelding) {
            case FOUT_ONOVERRULEBAAR:
                resultaat = Soortmelding.FOUT;
                break;
            case FOUT_OVERRULEBAAR:
                resultaat = Soortmelding.OVERRULEBAAR;
                break;
            case WAARSCHUWING:
                resultaat = Soortmelding.WAARSCHUWING;
                break;
            case INFO:
                resultaat = Soortmelding.INFORMATIE;
                break;
            default:
                throw new IllegalArgumentException("Onbekende meldingsoort gedetecteerd: " + soortMelding);
        }
        return resultaat;
    }

    /**
     * Verwerkt het bericht via de bericht verwerker.
     *
     * @param bericht Het te verwerken bericht.
     * @param context De bericht context.
     * @return Een concrete instantie van een bericht resultaat.
     */
    protected abstract Y verwerkBericht(final BRPBericht bericht, BerichtContext context);

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
    protected BerichtContext bouwBerichtContext(final String berichtReferentieNummer, final BerichtenIds berichtenIds,
            final AuthenticatieMiddelModel authenticatieMiddel)
    {
        return new BerichtContext(berichtenIds, authenticatieMiddel.getId(), authenticatieMiddel.getPartij(),
                berichtReferentieNummer);
    }

    /**
     * Bouwt een standaard {@link BerichtResultaat} dat geretourneerd kan worden en voegt daar een enkele melding aan
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
        Melding melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, meldingCode, groep, "");
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
