/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Ib01.
 */
public final class Ib01Bericht extends AbstractLo3Bericht implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.HERHALING, Lo3HeaderVeld.STATUS, Lo3HeaderVeld.DATUM);

    private static final Lo3PersoonslijstParser PL_PARSER = new Lo3PersoonslijstParser();
    private static final Lo3PersoonslijstFormatter PL_FORMATTER = new Lo3PersoonslijstFormatter();

    private transient Lo3Persoonslijst lo3Persoonslijst;

    /**
     * Constructor.
     */
    public Ib01Bericht() {
        super(HEADER);
    }

    @Override
    public String getBerichtType() {
        return "Ib01";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        try {
            lo3Persoonslijst = PL_PARSER.parse(categorieen);
            // CHECKSTYLE:OFF - Catch all, anders klapt de ESB er lelijk uit
        } catch (final Exception e) {
            // CHECSTYLE:ON
            throw new BerichtInhoudException("Fout bij parsen lo3 persoonslijst", e);
        }

    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        if (lo3Persoonslijst == null) {
            throw new IllegalStateException("Lo3 persoonslijst mag niet leeg zijn.");
        }
        return PL_FORMATTER.format(lo3Persoonslijst);
    }

    /* ************************************************************************************************************* */

    public Lo3Persoonslijst getLo3Persoonslijst() {
        return lo3Persoonslijst;
    }

    public void setLo3Persoonslijst(final Lo3Persoonslijst persoonslijst) {
        lo3Persoonslijst = persoonslijst;
    }

    /* ************************************************************************************************************* */

    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        is.defaultReadObject();

        lo3Persoonslijst = PersoonslijstDecoder.decodeLo3Persoonslijst(is);
    }

    private void writeObject(final ObjectOutputStream os) throws IOException {
        // perform the default serialization for all non-transient, non-static fields
        os.defaultWriteObject();

        PersoonslijstEncoder.encodePersoonslijst(lo3Persoonslijst, os);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Ib01Bericht)) {
            return false;
        }
        final Ib01Bericht castOther = (Ib01Bericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(lo3Persoonslijst, castOther.lo3Persoonslijst).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(lo3Persoonslijst).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("lo3Persoonslijst", lo3Persoonslijst).toString();
    }

}
