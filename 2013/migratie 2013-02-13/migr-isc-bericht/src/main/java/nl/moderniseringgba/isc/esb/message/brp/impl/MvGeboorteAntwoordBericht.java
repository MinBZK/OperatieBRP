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
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.FoutCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Geboorte antwoord.
 */
public final class MvGeboorteAntwoordBericht extends AbstractBrpBericht implements BrpBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";
    private static final String ANUMMER = "aNummer";

    private MvGeboorteAntwoordType mvGeboorteAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public MvGeboorteAntwoordBericht() {
        mvGeboorteAntwoordType = new MvGeboorteAntwoordType();
        mvGeboorteAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param mvGeboorteAntwoordType
     *            het mvGeboorteAntwoord type
     */
    public MvGeboorteAntwoordBericht(final MvGeboorteAntwoordType mvGeboorteAntwoordType) {
        this.mvGeboorteAntwoordType = mvGeboorteAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "MvGeboorteAntwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createMvGeboorteAntwoord(mvGeboorteAntwoordType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            mvGeboorteAntwoordType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, MvGeboorteAntwoordType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een MvGeboorteAntwoord bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de foutcode {@link FoutCode} van het bericht terug.
     * 
     * @return De foutcode {@link FoutCode} van het bericht.
     */
    public FoutCode getFoutCode() {
        return mvGeboorteAntwoordType.getFoutCode();
    }

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return mvGeboorteAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * 
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        mvGeboorteAntwoordType.setStatus(status);
    }

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public String getToelichting() {
        return mvGeboorteAntwoordType.getToelichting();
    }

    /**
     * Zet de toelichting op het bericht.
     * 
     * @param toelichting
     *            De te zetten toelichting.
     */
    public void setToelichting(final String toelichting) {
        mvGeboorteAntwoordType.setToelichting(toelichting);
    }

    /**
     * Geeft het A-nummer van het bericht terug.
     * 
     * @return Het A-nummer van het bericht.
     */
    public String getANummer() {
        return mvGeboorteAntwoordType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * 
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        mvGeboorteAntwoordType.setANummer(aNummer);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MvGeboorteAntwoordBericht)) {
            return false;
        }
        final MvGeboorteAntwoordBericht castOther = (MvGeboorteAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(mvGeboorteAntwoordType.getStatus(), castOther.getStatus())
                .append(mvGeboorteAntwoordType.getToelichting(), castOther.getToelichting())
                .append(mvGeboorteAntwoordType.getANummer(), castOther.getANummer()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(mvGeboorteAntwoordType.getStatus())
                .append(mvGeboorteAntwoordType.getToelichting()).append(mvGeboorteAntwoordType.getANummer())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(STATUS_ELEMENT, mvGeboorteAntwoordType.getStatus())
                .append(TOELICHTING_ELEMENT, mvGeboorteAntwoordType.getToelichting())
                .append(ANUMMER, mvGeboorteAntwoordType.getANummer()).toString();
    }
}
