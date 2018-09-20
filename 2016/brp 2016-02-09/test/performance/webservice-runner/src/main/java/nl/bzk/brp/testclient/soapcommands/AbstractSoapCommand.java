/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.brp0200.ObjecttypeBericht;
import nl.bzk.brp.brp0200.ObjecttypeMelding;
import nl.bzk.brp.brp0200.VerwerkingsresultaatNaamS;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.DuurDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class AbstractSoapCommand.
 *
 * @param <P> het generic type
 * @param <V> het value type
 * @param <A> het generic type
 */
public abstract class AbstractSoapCommand<P extends Object, V extends ObjecttypeBericht, A extends ObjecttypeBericht> {

    /** De Constante LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSoapCommand.class);

    /** De vraag. */
    private final V vraag;

    /** De soap fault. */
    private SOAPFault soapFault;


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
     * @return duur dto
     */
    public final DuurDto voerUit(final P portType) {
        boolean succes = true;

        V vraagBericht = getVraag();
        final DuurDto duurDto;
        long start = System.currentTimeMillis();
        try {
            ;

            A antwoordBericht = bepaalEnSetAntwoord(portType);

            // we zijn niet zozeer geinteresseerd of het bericht al dan overrulebare meldingen heeft.
            // we willen weten of het bericht verwerkt is, zo ja, dan is het succes, zo nee, geen
            // succes en we laten (configureerbaar) zien welke meldingen er zijn gekomen
            if (antwoordBericht != null
                    && antwoordBericht.getResultaat() != null
                    && antwoordBericht.getResultaat().getValue() != null
                    && antwoordBericht.getResultaat().getValue().getVerwerking().getValue() != null
                    && antwoordBericht.getResultaat().getValue().getVerwerking().getValue() != null
                    && antwoordBericht.getResultaat().getValue().getVerwerking().getValue().getValue()
                    == VerwerkingsresultaatNaamS.GESLAAGD)
            {
                succes = true;
                // TODO bolie: bij bevraging, is 'persoon niet gevonden OOK 'G' (!!!)
                // moet overleggen of dit als goed of fout gemeten moet worden.
            } else {
                succes = false;
                if (antwoordBericht != null
                        && antwoordBericht.getMeldingen() != null
                        && antwoordBericht.getMeldingen().getValue() != null
                        && antwoordBericht.getMeldingen().getValue().getMelding() != null
                        && TestClient.eigenschappen.getMeldingenLoggen())
                {
                    for (ObjecttypeMelding melding : antwoordBericht.getMeldingen().getValue().getMelding()) {
                        LOG.info(
                            new StringBuffer("Melding ")
                                .append(getClass().getSimpleName())
                                .append(" refnr ")
                                .append(getVraag().getStuurgegevens().getValue().getReferentienummer())
                                .append(": \"").append(melding.getRegelCode().getValue().getValue()).append("\")")
                                .append(" refId=").append(melding.getReferentieID()).append("")
                                .append(" level=").append(melding.getSoortNaam().getValue().getValue()).append("")
                                .append(" (regel: ").append(melding.getMelding().getValue().getValue()).append(")")
                                .toString());
                    }
                }
            }
        } catch (SOAPFaultException se) {
            setSoapFault(se.getFault());
            LOG.error("SOAPFaultException: ["
                              + vraagBericht.getStuurgegevens().getValue().getReferentienummer()
                              + "] "
                              + se.getMessage());
            succes = false;
        }
        long duur = System.currentTimeMillis() - start;

        Bericht.STATUS berichtStatus;
        if (succes) {
            berichtStatus = Bericht.STATUS.GOED;
        } else {
            berichtStatus = Bericht.STATUS.FOUT;
        }

        duurDto = new DuurDto(duur, null, null, berichtStatus);
        return duurDto;
    }

    /**
     * Bepaal en set antwoord.
     *
     * @param portType port type
     */
    public abstract A bepaalEnSetAntwoord(final P portType);

    /**
     * Haalt een vraag op.
     *
     * @return vraag
     */
    public V getVraag() {
        return vraag;
    }

    /**
     * Instellen van soap fault.
     *
     * @param soapFault de nieuwe soap fault
     */
    protected void setSoapFault(final SOAPFault soapFault) {
        this.soapFault = soapFault;
    }

    /**
     * Verkrijgt administratieve handeling uit antwoord.
     *
     * @param antwoordParam antwoord parameter
     * @return administratieve handeling uit antwoord
     */
    protected abstract ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(final A antwoordParam);
}
