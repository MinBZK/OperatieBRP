/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.w3c.dom.Document;

/**
 * Erkenning geboorte antwoord.
 */
public final class ErkenningAntwoordBericht extends BrpBijhoudingAntwoordBericht {

    private static final long serialVersionUID = 1L;

    private ErkenningAntwoordType erkenningAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ErkenningAntwoordBericht() {
        erkenningAntwoordType = new ErkenningAntwoordType();
        erkenningAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param erkenningAntwoordType
     *            het erkenningAntwoord type
     */
    public ErkenningAntwoordBericht(final ErkenningAntwoordType erkenningAntwoordType) {
        this.erkenningAntwoordType = erkenningAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "ErkenningAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createErkenningAntwoord(erkenningAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            erkenningAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, ErkenningAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ErkenningAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    @Override
    public StatusType getStatus() {
        return erkenningAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    @Override
    public void setStatus(final StatusType status) {
        erkenningAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    @Override
    public String getToelichting() {
        return erkenningAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    @Override
    public void setToelichting(final String toelichting) {
        erkenningAntwoordType.setToelichting(toelichting);
    }

}
