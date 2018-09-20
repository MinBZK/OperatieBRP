/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import nl.bprbzk.brp._0001.ObjecttypeBericht;
import nl.bprbzk.brp._0001.ObjecttypeMelding;
import nl.bprbzk.brp._0001.ObjecttypeOverrule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class AbstractSoapCommand.
 *
 * @param <P> het generic type
 * @param <V> het value type
 * @param <A> het generic type
 */
public abstract class AbstractSoapCommand<P, V extends ObjecttypeBericht, A extends ObjecttypeBericht> {

    /** De Constante LOG. */
    private static final Logger LOG                        = LoggerFactory.getLogger(AbstractSoapCommand.class);

    private static final String REGELCODE_OVERRULEBAAR_NVT = "ALG0001";

    private static final String REGELCODE_VERHUIZING_WONEN_AL_PERSONEN = "MR0502";

    /** De vraag. */
    private final V             vraag;

    /** De antwoord. */
    private A                   antwoord;

    /** De soap fault. */
    private SOAPFault           soapFault;

    private Boolean             succes                     = true;

    /**
     * Instantieert een nieuwe abstract soap command.
     *
     * @param vraag de vraag
     */
    protected AbstractSoapCommand(final V vraag) {
        this.vraag = vraag;
    }

    /**
     * Voer uit.
     *
     * @param portType de port type
     */
    public final void voerUit(final P portType) {
        Map<String, String> meldingMap = new HashMap<String, String>();
        List<String> overruleMeldingList = new ArrayList<String>();

        try {
            bepaalEnSetAntwoord(portType);

            A antwoord = getAntwoord();

            // Vul lijst met overrule meldingen
            if (antwoord != null) {
                overruleMeldingList = genereerOverruleLijst(antwoord);
            }

            // Vul lijst met meldingen en toon dit in log
            if (antwoord != null && antwoord.getMeldingen() != null && antwoord.getMeldingen().getValue() != null
                && antwoord.getMeldingen().getValue().getMelding() != null)
            {
                meldingMap = genereerMeldingMap(antwoord, overruleMeldingList);

                // Als er nog meldingen zijn, dan wordt bericht als mislukt beschouwd en tevens worden meldingen getoond
                // in log
                if (meldingMap.size() > 0) {
                    for (String melding : meldingMap.keySet()) {
                        LOG.debug("Melding " + getClass().getSimpleName() + " refnr "
                            + getVraag().getStuurgegevens().get(0).getReferentienummer().getValue() + ": \""
                            + meldingMap.get(melding) + "\" (regel: " + melding + ")");
                    }
                    succes = false;
                }
            }
        } catch (SOAPFaultException se) {
            setSoapFault(se.getFault());
            // TODO: aanzetten voor foutmeldingen in log
            LOG.error("SOAPFaultException: " + se.getMessage());
            succes = false;
        }
    }

    private Map<String, String> genereerMeldingMap(final A antwoord2, final List<String> overruleMeldingList) {
        Map<String, String> meldingMap = new HashMap<String, String>();

        for (ObjecttypeMelding melding : antwoord2.getMeldingen().getValue().getMelding()) {
            String regelcode = null;
            if (melding.getRegelCode().getValue() != null) {
                regelcode = melding.getRegelCode().getValue();
                meldingMap.put(regelcode, melding.getMelding().getValue());
            }
        }

        // Verwijder meldingen die overruled zijn
        for (String overruleMelding : overruleMeldingList) {
            meldingMap.remove(overruleMelding);
        }

        return meldingMap;
    }

    private List<String> genereerOverruleLijst(final A antwoord2) {
        List<String> overruleMeldingList = new ArrayList<String>();
        if (antwoord2.getOverruleMeldingen() != null) {
            for (ObjecttypeOverrule objecttypeOverrule : antwoord2.getOverruleMeldingen().getValue().getOverrule()) {
                if (objecttypeOverrule.getRegelCode().getValue() != null) {
                    overruleMeldingList.add(objecttypeOverrule.getRegelCode().getValue());
                }
            }
        }

        // Voeg overrulebare melding toe voor
        // "Er zijn overrulebare meldingen opgegeven die niet van toepassing zijn: [BRBY0106]. (regel: ALG0001)"
        overruleMeldingList.add(REGELCODE_OVERRULEBAAR_NVT);
        overruleMeldingList.add(REGELCODE_VERHUIZING_WONEN_AL_PERSONEN);

        return overruleMeldingList;
    }

    /**
     * Controleert of de waarde gelijk is aan succes.
     *
     * @return true, als waarde gelijk is aan succes
     */
    public boolean isSucces() {
        return succes;
    }

    /**
     * Bepaal en set antwoord.
     *
     * @param portType
     */
    public abstract void bepaalEnSetAntwoord(P portType);

    /**
     * Haalt een vraag op.
     *
     * @return vraag
     */
    public V getVraag() {
        return vraag;
    }

    /**
     * Haalt een antwoord op.
     *
     * @return antwoord
     */
    public A getAntwoord() {
        return antwoord;
    }

    /**
     * Instellen van antwoord.
     *
     * @param antwoord de nieuwe antwoord
     */
    protected void setAntwoord(final A antwoord) {
        this.antwoord = antwoord;
    }

    /**
     * Haalt een soap fault op.
     *
     * @return soap fault
     */
    public SOAPFault getSoapFault() {
        return soapFault;
    }

    /**
     * Instellen van soap fault.
     *
     * @param soapFault de nieuwe soap fault
     */
    protected void setSoapFault(final SOAPFault soapFault) {
        this.soapFault = soapFault;
    }

}
