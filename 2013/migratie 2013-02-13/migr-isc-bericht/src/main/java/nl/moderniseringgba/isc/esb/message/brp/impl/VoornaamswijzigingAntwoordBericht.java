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
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VoornaamswijzigingAntwoordType;

import org.w3c.dom.Document;

/**
 * Erkenning geboorte antwoord.
 */
public final class VoornaamswijzigingAntwoordBericht extends BrpBijhoudingAntwoordBericht {

    private static final long serialVersionUID = 1L;

    private VoornaamswijzigingAntwoordType voornaamswijzigingAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VoornaamswijzigingAntwoordBericht() {
        voornaamswijzigingAntwoordType = new VoornaamswijzigingAntwoordType();
        voornaamswijzigingAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een voornaamswijzigingAntwoord
     * te maken.
     * 
     * @param voornaamswijzigingAntwoordType
     *            het voornaamswijzigingAntwoord type
     */
    public VoornaamswijzigingAntwoordBericht(final VoornaamswijzigingAntwoordType voornaamswijzigingAntwoordType) {
        this.voornaamswijzigingAntwoordType = voornaamswijzigingAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "VoornaamswijzigingAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createVoornaamswijzigingAntwoord(voornaamswijzigingAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            voornaamswijzigingAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, VoornaamswijzigingAntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een GeslachtsnaamwijzigingAntwoord bericht.", e);
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
        return voornaamswijzigingAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    @Override
    public void setStatus(final StatusType status) {
        voornaamswijzigingAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    @Override
    public String getToelichting() {
        return voornaamswijzigingAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    @Override
    public void setToelichting(final String toelichting) {
        voornaamswijzigingAntwoordType.setToelichting(toelichting);
    }

}
