/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.resultaat;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.Objects;
import javax.annotation.Nonnull;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.springframework.util.Assert;

/**
 * Value object voor het bijhouden van meldingen in een resultaat. Moet immutable zijn.
 */
public final class ResultaatMelding implements Comparable<ResultaatMelding> {

    private SoortMelding soort;
    private Regel        regel;
    private String       meldingTekst;
    private String       attribuutNaam;
    private String       referentieID;

    private ResultaatMelding() {
        // alleen instantieerbaar voor de eigen Builder
    }

    public SoortMelding getSoort() {
        return soort;
    }

    public Regel getRegel() {
        return regel;
    }

    public String getMeldingTekst() {
        return meldingTekst;
    }

    public String getAttribuutNaam() {
        return attribuutNaam;
    }

    public String getReferentieID() {
        return referentieID;
    }

    /**
     * Geeft een kale builder.
     * @return de kale builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Geeft een builder met de gegeven resultaatmelding als standaard vulling.
     * @param resultaatMelding de resultaatmelding
     * @return de builder
     */
    public static Builder builder(final ResultaatMelding resultaatMelding) {
        return new Builder()
            .withAttribuutNaam(resultaatMelding.getAttribuutNaam())
            .withMeldingTekst(resultaatMelding.getMeldingTekst())
            .withReferentieID(resultaatMelding.getReferentieID())
            .withRegel(resultaatMelding.getRegel())
            .withSoort(resultaatMelding.getSoort());
    }

    public boolean isVerwerkingStoppend() {
        return soort == SoortMelding.DEBLOKKEERBAAR || soort == SoortMelding.FOUT;
    }

    /**
     * Deblokkeer deze ResultaatMelding door de {@link SoortMelding} op WAARSCHUWING te zetten.
     * @return de aangepaste ResultaatMelding
     */
    public ResultaatMelding deblokkeer() {
        Assert.state(soort == SoortMelding.DEBLOKKEERBAAR);
        return builder(this)
            .withSoort(SoortMelding.WAARSCHUWING).build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(soort, regel, meldingTekst, attribuutNaam, referentieID);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ResultaatMelding other = (ResultaatMelding) obj;
        return Objects.equals(this.soort, other.soort)
            && Objects.equals(this.regel, other.regel)
            && Objects.equals(this.meldingTekst, other.meldingTekst)
            && Objects.equals(this.attribuutNaam, other.attribuutNaam)
            && Objects.equals(this.referentieID, other.referentieID);
    }

    @Override
    public int compareTo(@Nonnull final ResultaatMelding that) {
        return ComparisonChain.start()
            .compare(regel, that.regel)
            .compare(attribuutNaam, that.attribuutNaam, Ordering.natural().nullsFirst())
            .compare(referentieID, that.referentieID)
            .compare(soort, that.soort, Ordering.natural().nullsFirst())
            .compare(meldingTekst, that.meldingTekst, Ordering.natural().nullsFirst())
            .result();
    }

    @Override
    public String toString() {
        return "ResultaatMelding{" +
            "soort=" + soort +
            ", regel=" + regel +
            ", meldingTekst='" + meldingTekst + '\'' +
            ", attribuutNaam='" + attribuutNaam + '\'' +
            ", referentieID='" + referentieID + '\'' +
            '}';
    }

    /**
     * Builder voor ResultaatMelding.
     */
    public static final class Builder {
        private SoortMelding soort = SoortMelding.FOUT;
        private Regel        regel = Regel.ALG0001;
        private String       meldingTekst = "Onbekende fout opgetreden";
        private String       attribuutNaam;
        private String       referentieID;

        /**
         * Instantieert de {@link ResultaatMelding}.
         * @return de {@link ResultaatMelding}
         */
        public ResultaatMelding build() {
            final ResultaatMelding resultaatMelding = new ResultaatMelding();

            Assert.notNull(regel);

            if (referentieID == null) {
                // Zie TEAMBRP-1237.
                // In sommige gevallen, kunnen we geen referentieID opvissen. Om WEL valide antwoord te genereren, moeten we dit vullen met 'onbekend'.
                // Een van de voorbeelden is dat de partij is niet geauthenticeerd. De groep stuurgegevens heeft geen communicatieID.
                referentieID = "onbekend";
            }

            resultaatMelding.soort = soort;
            resultaatMelding.regel = regel;
            resultaatMelding.meldingTekst = meldingTekst;
            resultaatMelding.attribuutNaam = attribuutNaam;
            resultaatMelding.referentieID = referentieID;
            return resultaatMelding;
        }

        /**
         * Voegt de soort melding toe aan de Builder.
         * @param withSoort de {@link SoortMelding}
         * @return de Builder
         */
        public Builder withSoort(final SoortMelding withSoort) {
            soort = withSoort;
            return this;
        }

        /**
         * Voegt de {@link Regel} toe aan de Builder.
         * @param withRegel de {@link Regel}
         * @return de Builder
         */
        public Builder withRegel(final Regel withRegel) {
            regel = withRegel;
            return this;
        }

        /**
         * Voegt de meldingtekst toe aan de Builder.
         * @param withMeldingTekst de meldingtekst
         * @return de Builder
         */
        public Builder withMeldingTekst(final String withMeldingTekst) {
            meldingTekst = withMeldingTekst;
            return this;
        }

        /**
         * Voegt de attribuutnaam toe aan de Builder.
         * @param withAttribuutNaam de attribuutnaam
         * @return de Builder
         */
        public Builder withAttribuutNaam(final String withAttribuutNaam) {
            attribuutNaam = withAttribuutNaam;
            return this;
        }

        /**
         * Voegt de referentieID toe aan de Builder.
         * @param withReferentieID de referentieID
         * @return de Builder
         */
        public Builder withReferentieID(final String withReferentieID) {
            referentieID = withReferentieID;
            return this;
        }
    }
}
