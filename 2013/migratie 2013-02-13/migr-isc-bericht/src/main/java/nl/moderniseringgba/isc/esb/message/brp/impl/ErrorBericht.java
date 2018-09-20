/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.Serializable;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.AbstractBrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErrorType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Generiek error bericht.
 */
public final class ErrorBericht extends AbstractBrpBericht implements BrpAntwoordBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private ErrorType errorType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ErrorBericht() {
        errorType = new ErrorType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * 
     * @param errorType
     *            het errorType type
     */
    public ErrorBericht(final ErrorType errorType) {
        this.errorType = errorType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "GeboorteVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc306";
    }

    /* ************************************************************************************************************* */

    /**
     * @return status
     */
    public StatusType getStatus() {
        return errorType.getStatus();
    }

    /**
     * Zet status.
     * 
     * @param status
     *            status
     */
    @Override
    public void setStatus(final StatusType status) {
        errorType.setStatus(status);
    }

    /**
     * @return toelichting
     */
    public String getToelichting() {
        return errorType.getToelichting();
    }

    /**
     * Zet toelichting.
     * 
     * @param toelichting
     *            toelichting
     */
    @Override
    public void setToelichting(final String toelichting) {
        errorType.setToelichting(toelichting);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory().createError(errorType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            errorType = BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, ErrorType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een GeboorteVerzoek bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("errorType", errorType).toString();
    }

}
