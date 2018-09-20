/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.sync.impl;

import java.util.List;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.AbstractSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarLo3AntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Antwoord bericht voor het converteren van gegevens van BRP naar LO3.
 */
public final class ConverteerNaarLo3AntwoordBericht extends AbstractSyncBericht {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final long serialVersionUID = 1L;

    private ConverteerNaarLo3AntwoordType converteerNaarLo3AntwoordType;

    /**
     * Default constructor (status = OK).
     */
    public ConverteerNaarLo3AntwoordBericht() {
        converteerNaarLo3AntwoordType = new ConverteerNaarLo3AntwoordType();
        setStatus(StatusType.OK);
    }

    /**
     * Convenient constructor.
     * 
     * @param type
     *            Het ConverteerNaarLo3AntwoordType met daarin de informatie benodigd voor het bericht.
     */
    public ConverteerNaarLo3AntwoordBericht(final ConverteerNaarLo3AntwoordType type) {
        converteerNaarLo3AntwoordType = type;
    }

    /**
     * Convenient constructor.
     * 
     * @param correlationId
     *            Het correlatie ID.
     */
    public ConverteerNaarLo3AntwoordBericht(final String correlationId) {
        this();
        setCorrelationId(correlationId);
    }

    /**
     * Convenient constructor.
     * 
     * @param correlationId
     *            Het correlatie ID.
     * @param lo3Pl
     *            De LO3 persoonslijst.
     */
    public ConverteerNaarLo3AntwoordBericht(final String correlationId, final Lo3Persoonslijst lo3Pl) {
        this();
        converteerNaarLo3AntwoordType.setLo3Pl(asString(lo3Pl));
        setCorrelationId(correlationId);
    }

    /**
     * Convenience constructor (status = FOUT).
     * 
     * @param correlationId
     *            correlatieId
     * @param foutmelding
     *            foutmelding
     */
    public ConverteerNaarLo3AntwoordBericht(final String correlationId, final String foutmelding) {
        this();
        setStatus(StatusType.FOUT);
        setFoutmelding(foutmelding);
        setCorrelationId(correlationId);
    }

    /**
     * Convenient constructor.
     * 
     * @param verzoekBericht
     *            Het ConverteerNaarLo3VerzoekBericht.
     */
    public ConverteerNaarLo3AntwoordBericht(final ConverteerNaarLo3VerzoekBericht verzoekBericht) {
        this();
        setCorrelationId(verzoekBericht.getMessageId());
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            converteerNaarLo3AntwoordType =
                    SyncBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, ConverteerNaarLo3AntwoordType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ConverteerNaarLo3AntwoordBericht bericht.", e);
        }
    }

    @Override
    public String getBerichtType() {
        return "ConverteerNaarLo3Antwoord";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    @Override
    public String format() {
        return SyncBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createConverteerNaarLo3Antwoord(converteerNaarLo3AntwoordType));
    }

    /**
     * Zet status.
     * 
     * @param status
     *            status
     * @throws NullPointerException
     *             als status null is
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }

        converteerNaarLo3AntwoordType.setStatus(status);
    }

    /**
     * Zet de foutmelding.
     * 
     * @param foutmelding
     *            De foutmelding om te zetten.
     */
    public void setFoutmelding(final String foutmelding) {
        converteerNaarLo3AntwoordType.setFoutmelding(foutmelding);
    }

    /**
     * Geeft de foutmelding terug.
     * 
     * @return De foutmelding.
     */
    public String getFoutmelding() {
        return converteerNaarLo3AntwoordType.getFoutmelding();
    }

    /**
     * Geeft de status terug van het bericht.
     * 
     * @return De status van het bericht.
     */
    public StatusType getStatus() {
        return converteerNaarLo3AntwoordType.getStatus();
    }

    /**
     * Geeft de LO3 persoonslijst terug.
     * 
     * @return De LO3 persoonslijst.
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        final String lo3PlAsTeletexString = converteerNaarLo3AntwoordType.getLo3Pl();

        Lo3Persoonslijst lo3Persoonslijst = null;
        try {
            final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3PlAsTeletexString);
            lo3Persoonslijst = new Lo3PersoonslijstParser().parse(categorieen);
        } catch (final BerichtSyntaxException e) {
            LOG.error(e.getMessage(), e);
        }

        return lo3Persoonslijst;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConverteerNaarLo3AntwoordBericht)) {
            return false;
        }
        final ConverteerNaarLo3AntwoordBericht castOther = (ConverteerNaarLo3AntwoordBericht) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(converteerNaarLo3AntwoordType.getStatus(),
                        castOther.converteerNaarLo3AntwoordType.getStatus())
                .append(converteerNaarLo3AntwoordType.getFoutmelding(),
                        castOther.converteerNaarLo3AntwoordType.getFoutmelding())
                .append(converteerNaarLo3AntwoordType.getLo3Pl(), castOther.converteerNaarLo3AntwoordType.getLo3Pl())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(converteerNaarLo3AntwoordType.getStatus())
                .append(converteerNaarLo3AntwoordType.getFoutmelding())
                .append(converteerNaarLo3AntwoordType.getLo3Pl()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(converteerNaarLo3AntwoordType.getStatus())
                .append(converteerNaarLo3AntwoordType.getFoutmelding())
                .append(converteerNaarLo3AntwoordType.getLo3Pl()).toString();
    }
}
