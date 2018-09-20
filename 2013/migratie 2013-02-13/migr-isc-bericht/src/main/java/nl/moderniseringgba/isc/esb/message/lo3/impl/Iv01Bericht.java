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
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3VerwijzingParser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Iv01.
 */
public final class Iv01Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.HERHALING);

    private static final Lo3VerwijzingParser VERWIJZING_PARSER = new Lo3VerwijzingParser();
    private static final Lo3PersoonslijstFormatter VERWIJZING_FORMATTER = new Lo3PersoonslijstFormatter();

    // Categorie 21: Verwijzing
    private transient Lo3Categorie<Lo3VerwijzingInhoud> verwijzing;

    /**
     * Constructor.
     */
    public Iv01Bericht() {
        super(HEADER);
    }

    @Override
    public String getBerichtType() {
        return "Iv01";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        final Lo3Stapel<Lo3VerwijzingInhoud> stapel;
        try {
            stapel = VERWIJZING_PARSER.parse(categorieen);
            // CHECKSTYLE:OFF - Catch all, anders klapt de ESB er lelijk uit
        } catch (final Exception e) {
            // CHECSTYLE:ON
            throw new BerichtInhoudException("Fout bij parsen lo3 verwijzing.", e);
        }

        if (stapel == null || stapel.isEmpty()) {
            throw new BerichtInhoudException("Geen verwijzing gegevens aanwezig.");
        }
        if (stapel.size() > 1) {
            throw new BerichtInhoudException("Alleen actuele gegevens verwacht.");
        }

        verwijzing = stapel.get(0);
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return VERWIJZING_FORMATTER.formatVerwijzing(verwijzing);
    }

    /* ************************************************************************************************************* */

    public Lo3Categorie<Lo3VerwijzingInhoud> getVerwijzing() {
        return verwijzing;
    }

    public void setVerwijzing(final Lo3Categorie<Lo3VerwijzingInhoud> verwijzing) {
        this.verwijzing = verwijzing;
    }

    /* ************************************************************************************************************* */

    @SuppressWarnings("unchecked")
    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        is.defaultReadObject();

        verwijzing = PersoonslijstDecoder.decode(Lo3Categorie.class, is);
    }

    private void writeObject(final ObjectOutputStream os) throws IOException {
        // perform the default serialization for all non-transient, non-static fields
        os.defaultWriteObject();

        PersoonslijstEncoder.encode(verwijzing, os);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Iv01Bericht)) {
            return false;
        }
        final Iv01Bericht castOther = (Iv01Bericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(verwijzing, castOther.verwijzing)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(verwijzing).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("verwijzing", verwijzing).toString();
    }

}
