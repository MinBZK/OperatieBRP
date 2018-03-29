/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BrpIstGezagsVerhoudingGroepInhoud
 */
public class BrpIstGezagsVerhoudingGroepInhoud extends AbstractBrpIstGroepInhoud {
    @Element(name = "standaardGegevens", required = true)
    private final BrpIstStandaardGroepInhoud standaardGegevens;
    /* Categorie gezagsverhouding. */
    @Element(name = "indicatieOuder1HeeftGezag", required = false)
    private final BrpBoolean indicatieOuder1HeeftGezag;
    @Element(name = "indicatieOuder2HeeftGezag", required = false)
    private final BrpBoolean indicatieOuder2HeeftGezag;
    @Element(name = "indicatieDerdeHeeftGezag", required = false)
    private final BrpBoolean indicatieDerdeHeeftGezag;
    @Element(name = "indicatieOnderCuratele", required = false)
    private final BrpBoolean indicatieOnderCuratele;

    private BrpIstGezagsVerhoudingGroepInhoud(final Builder builder) {
        standaardGegevens = builder.standaardGegevens;
        indicatieOuder1HeeftGezag = builder.indicatieOuder1HeeftGezag;
        indicatieOuder2HeeftGezag = builder.indicatieOuder2HeeftGezag;
        indicatieDerdeHeeftGezag = builder.indicatieDerdeHeeftGezag;
        indicatieOnderCuratele = builder.indicatieOnderCuratele;
    }

    /**
     * Maakt een BrpIstGezagsVerhoudingStapelInhoud object.
     * @param standaardGroepInhoud standaard IST gegevens
     * @param ouder1HeeftGezag indicatie dat ouder1 gezag geeft
     * @param ouder2HeeftGezag indicatie dat ouder2 gezag geeft
     * @param derdeHeeftGezag indicatie dat een derde gezag geeft
     * @param onderCuratele indicatie dat persoon onder curatele staat
     */
    public BrpIstGezagsVerhoudingGroepInhoud(
            @Element(name = "standaardGegevens", required = true) final BrpIstStandaardGroepInhoud standaardGroepInhoud,
            @Element(name = "indicatieOuder1HeeftGezag", required = false) final BrpBoolean ouder1HeeftGezag,
            @Element(name = "indicatieOuder2HeeftGezag", required = false) final BrpBoolean ouder2HeeftGezag,
            @Element(name = "indicatieDerdeHeeftGezag", required = false) final BrpBoolean derdeHeeftGezag,
            @Element(name = "indicatieOnderCuratele", required = false) final BrpBoolean onderCuratele) {
        standaardGegevens = standaardGroepInhoud;
        indicatieOuder1HeeftGezag = ouder1HeeftGezag;
        indicatieOuder2HeeftGezag = ouder2HeeftGezag;
        indicatieDerdeHeeftGezag = derdeHeeftGezag;
        indicatieOnderCuratele = onderCuratele;
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof BrpIstGezagsVerhoudingGroepInhoud)) {
            return false;
        }
        final BrpIstGezagsVerhoudingGroepInhoud castOther = (BrpIstGezagsVerhoudingGroepInhoud) other;
        return new EqualsBuilder().append(standaardGegevens, castOther.standaardGegevens)
                .append(indicatieOuder1HeeftGezag, castOther.indicatieOuder1HeeftGezag)
                .append(indicatieOuder2HeeftGezag, castOther.indicatieOuder2HeeftGezag)
                .append(indicatieDerdeHeeftGezag, castOther.indicatieDerdeHeeftGezag)
                .append(indicatieOnderCuratele, castOther.indicatieOnderCuratele)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder().append(standaardGegevens)
                .append(indicatieOuder1HeeftGezag)
                .append(indicatieOuder2HeeftGezag)
                .append(indicatieDerdeHeeftGezag)
                .append(indicatieOnderCuratele)
                .toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("standaardGegevens", standaardGegevens)
                .append("indicatieOuder1HeeftGezag", indicatieOuder1HeeftGezag)
                .append("indicatieOuder2HeeftGezag", indicatieOuder2HeeftGezag)
                .append("indicatieDerdeHeeftGezag", indicatieDerdeHeeftGezag)
                .append("indicatieOnderCuratele", indicatieOnderCuratele)
                .toString();

    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getCategorie()
     */
    @Override
    public final Lo3CategorieEnum getCategorie() {
        return standaardGegevens.getCategorie();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getStapel()
     */
    @Override
    public final int getStapel() {
        return standaardGegevens.getStapel();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getVoorkomen()
     */
    @Override
    public final int getVoorkomen() {
        return standaardGegevens.getVoorkomen();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getStandaardGegevens()
     */
    @Override
    public final BrpIstStandaardGroepInhoud getStandaardGegevens() {
        return standaardGegevens;
    }

    /**
     * Geef de waarde van indicatie ouder1 heeft gezag van BrpIstGezagsVerhoudingGroepInhoud.
     * @return de waarde van indicatie ouder1 heeft gezag van BrpIstGezagsVerhoudingGroepInhoud
     */
    public final BrpBoolean getIndicatieOuder1HeeftGezag() {
        return indicatieOuder1HeeftGezag;
    }

    /**
     * Geef de waarde van indicatie ouder2 heeft gezag van BrpIstGezagsVerhoudingGroepInhoud.
     * @return de waarde van indicatie ouder2 heeft gezag van BrpIstGezagsVerhoudingGroepInhoud
     */
    public final BrpBoolean getIndicatieOuder2HeeftGezag() {
        return indicatieOuder2HeeftGezag;
    }

    /**
     * Geef de waarde van indicatie derde heeft gezag van BrpIstGezagsVerhoudingGroepInhoud.
     * @return de waarde van indicatie derde heeft gezag van BrpIstGezagsVerhoudingGroepInhoud
     */
    public final BrpBoolean getIndicatieDerdeHeeftGezag() {
        return indicatieDerdeHeeftGezag;
    }

    /**
     * Geef de waarde van indicatie onder curatele van BrpIstGezagsVerhoudingGroepInhoud.
     * @return de waarde van indicatie onder curatele van BrpIstGezagsVerhoudingGroepInhoud
     */
    public final BrpBoolean getIndicatieOnderCuratele() {
        return indicatieOnderCuratele;
    }

    /**
     * Builder object voor BrpIstGezagVerhoudingGroepInhoud.
     */
    public static class Builder {
        private final BrpIstStandaardGroepInhoud standaardGegevens;
        private BrpBoolean indicatieOuder1HeeftGezag;
        private BrpBoolean indicatieOuder2HeeftGezag;
        private BrpBoolean indicatieDerdeHeeftGezag;
        private BrpBoolean indicatieOnderCuratele;

        /**
         * Constructor met verplichte velden categorie, stapel en voorkomen.
         * @param standaardGroepInhoud standaard IST gegevens
         */
        public Builder(final BrpIstStandaardGroepInhoud standaardGroepInhoud) {
            standaardGegevens = standaardGroepInhoud;
        }

        /**
         * zet de indicatie dat ouder1 gezag heeft.
         * @param param indictatie ouder1 heeft gezag
         * @return builder object
         */
        public final Builder indicatieOuder1HeeftGezag(final BrpBoolean param) {
            indicatieOuder1HeeftGezag = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de indicatie dat ouder2 gezag heeft.
         * @param param indictatie ouder2 heeft gezag
         * @return builder object
         */
        public final Builder indicatieOuder2HeeftGezag(final BrpBoolean param) {
            indicatieOuder2HeeftGezag = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de indicatie dat derde gezag heeft.
         * @param param indictatie derde heeft gezag
         * @return builder object
         */
        public final Builder indicatieDerdeHeeftGezag(final BrpBoolean param) {
            indicatieDerdeHeeftGezag = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de indicatie onder curatele.
         * @param param indicatie onder curatele
         * @return builder object
         */
        public final Builder indicatieOnderCuratele(final BrpBoolean param) {
            indicatieOnderCuratele = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * @return een nieuw geconstrueerde {@link BrpIstGezagsVerhoudingGroepInhoud}
         */
        public final BrpIstGezagsVerhoudingGroepInhoud build() {
            return new BrpIstGezagsVerhoudingGroepInhoud(this);
        }
    }
}
