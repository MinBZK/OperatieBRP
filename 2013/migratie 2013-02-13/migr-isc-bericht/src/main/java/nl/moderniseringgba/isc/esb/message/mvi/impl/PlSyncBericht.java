/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.mvi.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.mvi.AbstractMviBericht;
import nl.moderniseringgba.isc.esb.message.mvi.MviBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * Pl Sync bericht.
 */
public final class PlSyncBericht extends AbstractMviBericht implements MviBericht {
    private static final long serialVersionUID = 1L;

    private static final SimpleDateFormat DATUMTIJD_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private transient Lo3Persoonslijst lo3Persoonslijst;

    /**
     * Default constructor.
     */
    public PlSyncBericht() {

    }

    /**
     * Convience constructor.
     * 
     * @param lo3Persoonslijst
     *            lo3 persoonslijst
     */
    public PlSyncBericht(final Lo3Persoonslijst lo3Persoonslijst) {
        this.lo3Persoonslijst = lo3Persoonslijst;
    }

    @Override
    public String getBerichtType() {
        return "PlSync";
    }

    @Override
    public String getStartCyclus() {
        return "uc202";
    }

    @Override
    public void parse(final Document mviBericht) throws BerichtInhoudException {
        final String base64Pl = evaluateXpath(mviBericht, "//mvi:sync_payload");

        final String lo3Pl = new String(Base64.decodeBase64(base64Pl.getBytes()));

        List<Lo3CategorieWaarde> categorieen;
        try {
            categorieen = Lo3Inhoud.parseInhoud(lo3Pl);
        } catch (final BerichtSyntaxException e) {
            throw new BerichtInhoudException("Sync payload niet te parsen.", e);
        }
        lo3Persoonslijst = new Lo3PersoonslijstParser().parse(categorieen);

    }

    @Override
    public String format() {
        if (lo3Persoonslijst == null) {
            throw new IllegalStateException("Lo3 persoonlijst is leeg");
        }

        final Lo3Datumtijdstempel datumtijdstempel =
                lo3Persoonslijst.getInschrijvingStapel().get(0).getInhoud().getDatumtijdstempel();

        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(lo3Persoonslijst);
        final String lo3Pl = Lo3Inhoud.formatInhoud(categorieen);

        final String base64Pl = new String(Base64.encodeBase64(lo3Pl.getBytes()));

        return "<pl_sync xmlns=\"http://mvi.vospg.gbav.gba.nl/pl_sync\">" + "<sync_header>"
                + "<originatorOrRecipient>ISC</originatorOrRecipient>" + "<berichtnummer>" + getMessageId()
                + "</berichtnummer>" + "<datumtijdLaatsteWijzigingInBron>"
                + DATUMTIJD_FORMAT.format(new Date(datumtijdstempel.getDatum()))
                + "</datumtijdLaatsteWijzigingInBron>" + "<berichttype>Lg01</berichttype>" + "</sync_header>"
                + "<sync_payload>" + base64Pl + "</sync_payload>" + "</pl_sync>";
    }

    public Lo3Persoonslijst getLo3Persoonslijst() {
        return lo3Persoonslijst;
    }

    public void setLo3Persoonslijst(final Lo3Persoonslijst persoonslijst) {
        lo3Persoonslijst = persoonslijst;
    }

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
        if (!(other instanceof PlSyncBericht)) {
            return false;
        }
        final PlSyncBericht castOther = (PlSyncBericht) other;
        return new EqualsBuilder().append(lo3Persoonslijst, castOther.lo3Persoonslijst).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lo3Persoonslijst).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("lo3Persoonslijst", lo3Persoonslijst).toString();
    }

}
