/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.annotation.Nonnull;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * Model class voor een melding in een antwoordbericht.
 */
public final class Melding implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoortMelding soort;
    private Regel regel;
    private String meldingTekst;
    private String referentieID;

    /**
     * Standaard constructor die een {@link Regel} gebruikt om de {@link SoortMelding} uit te halen.
     * @param regel de {@link Regel} (mag niet <code>null</code> zijn)
     */
    public Melding(@Nonnull final Regel regel) {
        this(regel.getSoortMelding(), regel);
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert, waarbij de omschrijving wordt gezet op de standaard omschrijving horende bij de
     * meldingcode.
     * @param soort de soort melding.
     * @param regel de regel voor de melding (mag niet <code>null</code> zijn)
     */
    public Melding(final SoortMelding soort, @Nonnull final Regel regel) {
        this(soort, regel, regel.getMelding(), null);
    }

    /**
     * Constructor.
     * @param soort de soort melding.
     * @param regel de regel van de melding.
     * @param meldingTekst de omschrijving van de melding.
     */
    public Melding(final SoortMelding soort, final Regel regel, final String meldingTekst) {
        this(soort, regel, meldingTekst, null);
    }

    /**
     * Constructor.
     * @param regel de regel van de melding.
     * @param meldingTekst de omschrijving van de melding.
     */
    public Melding(final Regel regel, final String meldingTekst) {
        this(regel.getSoortMelding(), regel, meldingTekst, null);
    }


    /**
     * Constructor.
     * @param soort soort
     * @param regel regel
     * @param meldingTekst meldingTekst
     * @param communicatieId communicatieID
     */
    public Melding(final SoortMelding soort, final Regel regel, final String meldingTekst,
                   final String communicatieId) {
        this.soort = soort;
        this.regel = regel;
        this.meldingTekst = meldingTekst;
        if (communicatieId != null) {
            referentieID = communicatieId;
        }
    }

    /**
     * Retourneert de soort melding; de soort geeft aan of het een melding is ter info of bijvoorbeeld een echte fout.
     * @return de soort melding
     */
    public SoortMelding getSoort() {
        return this.soort;
    }

    /**
     * Retourneert de code van de melding; de code geeft het type van de melding/fout aan.
     * @return de code van de melding.
     */
    public Regel getRegel() {
        return this.regel;
    }

    /**
     * Retourneert een omschrijving van de melding.
     * @return een omschrijving van de melding.
     */
    public String getMeldingTekst() {
        return this.meldingTekst;
    }


    public void setReferentieID(final String referentieID) {
        this.referentieID = referentieID;
    }

    public String getReferentieID() {
        return referentieID;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Melding melding = (Melding) o;
        return new EqualsBuilder()
                .append(soort, melding.soort)
                .append(regel, melding.regel)
                .append(meldingTekst, melding.meldingTekst)
                .append(referentieID, melding.referentieID).isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(soort, regel, meldingTekst, referentieID);
    }
}
