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
import nl.moderniseringgba.isc.esb.message.brp.format.BrpFormat;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.WijzigingANummerSignaalType;
import nl.moderniseringgba.isc.esb.message.brp.parse.BrpParse;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Signaal uit BRP dat een anummer is gwijzigd.
 */
public final class WijzigingANummerSignaalBericht extends AbstractBrpBericht implements BrpBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private WijzigingANummerSignaalType wijzigingANummerSignaalType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public WijzigingANummerSignaalBericht() {
        wijzigingANummerSignaalType = new WijzigingANummerSignaalType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param wijzigingANummerSignaalType
     *            het wijzigingANummerSignaal type
     */
    public WijzigingANummerSignaalBericht(final WijzigingANummerSignaalType wijzigingANummerSignaalType) {
        this.wijzigingANummerSignaalType = wijzigingANummerSignaalType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "WijzigingANummerSignaal";
    }

    @Override
    public String getStartCyclus() {
        return "uc311";
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createWijzigingANummerSignaal(wijzigingANummerSignaalType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            wijzigingANummerSignaalType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, WijzigingANummerSignaalType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException("Onbekende fout tijdens het unmarshallen van een bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(wijzigingANummerSignaalType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        wijzigingANummerSignaalType.setIscGemeenten(setBrpGemeente(wijzigingANummerSignaalType.getIscGemeenten(),
                gemeente));
    }

    /* ************************************************************************************************************* */

    /**
     * @return oud a-nummer
     */
    public Long getOudANummer() {
        return BrpParse.parseLong(wijzigingANummerSignaalType.getOudANummer());
    }

    /**
     * @param aNummer
     *            oud a-nummer
     */
    public void setOudANummer(final Long aNummer) {
        wijzigingANummerSignaalType.setOudANummer(BrpFormat.format(aNummer));
    }

    /**
     * @return nieuw a-nummer
     */
    public Long getNieuwANummer() {
        return BrpParse.parseLong(wijzigingANummerSignaalType.getNieuwANummer());
    }

    /**
     * @param aNummer
     *            nieuw a-nummer
     */
    public void setNieuwANummer(final Long aNummer) {
        wijzigingANummerSignaalType.setNieuwANummer(BrpFormat.format(aNummer));
    }

    /**
     * @return nieuw a-nummer
     */
    public BrpDatum getDatumGeldigheid() {
        return BrpParse.parseBrpDatum(wijzigingANummerSignaalType.getDatumGeldigheid());
    }

    /**
     * @param datumGeldigheid
     *            datum geldigheid
     */
    public void setDatumGeldigheid(final BrpDatum datumGeldigheid) {
        wijzigingANummerSignaalType.setDatumGeldigheid(BrpFormat.format(datumGeldigheid));
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WijzigingANummerSignaalBericht)) {
            return false;
        }
        final WijzigingANummerSignaalBericht castOther = (WijzigingANummerSignaalBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(getOudANummer(), castOther.getOudANummer())
                .append(getNieuwANummer(), castOther.getNieuwANummer())
                .append(getDatumGeldigheid(), castOther.getDatumGeldigheid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(getOudANummer()).append(getNieuwANummer())
                .append(getDatumGeldigheid()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("oudANummer", getOudANummer()).append("nieuwANummer", getNieuwANummer())
                .append("datumGeldigheid", getDatumGeldigheid()).toString();
    }
}
