/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import java.util.List;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.sync.AbstractSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Verzoek bericht voor het converteren van gegevens van LO3 naar BRP.
 */
public final class ConverteerNaarBrpVerzoekBericht extends AbstractSyncBericht {

    private static final long serialVersionUID = 1L;

    private ConverteerNaarBrpVerzoekType converteerNaarBrpVerzoektype;

    /**
     * Default constructor.
     */
    public ConverteerNaarBrpVerzoekBericht() {
        converteerNaarBrpVerzoektype = new ConverteerNaarBrpVerzoekType();
    }

    /**
     * Convenient constructor.
     * 
     * @param converteerNaarBrpVerzoekType
     *            Het ConverteerNaarBrpVerzoekType met daarin de informatie.
     */
    public ConverteerNaarBrpVerzoekBericht(final ConverteerNaarBrpVerzoekType converteerNaarBrpVerzoekType) {
        converteerNaarBrpVerzoektype = converteerNaarBrpVerzoekType;
    }

    /**
     * Convenient constructor.
     * 
     * @param persoonslijst
     *            De te converteren persoonslijst.
     */
    public ConverteerNaarBrpVerzoekBericht(final Lo3Persoonslijst persoonslijst) {
        this();

        final List<Lo3CategorieWaarde> formattedLo3Pl = new Lo3PersoonslijstFormatter().format(persoonslijst);
        final String lo3BerichtAsTeletexString = Lo3Inhoud.formatInhoud(formattedLo3Pl);

        converteerNaarBrpVerzoektype.setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
    }

    // ****************************** SyncBericht methodes ******************************
    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            converteerNaarBrpVerzoektype =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, ConverteerNaarBrpVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ConverteerNaarBrpVerzoekBericht bericht.", e);
        }
    }

    // ****************************** Bericht methodes ******************************
    @Override
    public String getBerichtType() {
        return "ConverteerNaarBrpVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createConverteerNaarBrpVerzoek(converteerNaarBrpVerzoektype));
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConverteerNaarBrpVerzoekBericht)) {
            return false;
        }
        final ConverteerNaarBrpVerzoekBericht castOther = (ConverteerNaarBrpVerzoekBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(converteerNaarBrpVerzoektype.getLo3BerichtAsTeletexString(),
                        castOther.converteerNaarBrpVerzoektype.getLo3BerichtAsTeletexString()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode())
                .append(converteerNaarBrpVerzoektype.getLo3BerichtAsTeletexString()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(converteerNaarBrpVerzoektype.getLo3BerichtAsTeletexString()).toString();
    }

    /**
     * Geeft het LO3 bericht als teletex string terug.
     * 
     * @return Het LO3 bericht als teletex string.
     */
    public String getLo3BerichtAsTeletexString() {
        return converteerNaarBrpVerzoektype != null ? converteerNaarBrpVerzoektype.getLo3BerichtAsTeletexString()
                : null;
    }
}
