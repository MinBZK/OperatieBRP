/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert een specifieke TussenStapel, namelijk de TussenRelatie stapel. Deze stapel heeft associaties
 * met betrokkenheid stapels. Deze associaties reflecteren de relaties in de BRP A-laag (zonder historie).
 *
 */
public final class TussenRelatie {

    private static final String SOORT_BETROKKENHEID_ONTBREEKT = "rolCode mag niet null zijn";
    private static final String SOORT_RELATIE_CODE_ONTBREEKT = "soortRelatieCode mag niet null zijn";

    private final BrpSoortRelatieCode soortRelatieCode;
    private final BrpSoortBetrokkenheidCode rolCode;
    private final List<TussenBetrokkenheid> betrokkenheden;
    private final TussenStapel<BrpRelatieInhoud> relatieStapel;
    // IST stapels
    private final TussenStapel<BrpIstRelatieGroepInhoud> istOuder1;
    private final TussenStapel<BrpIstRelatieGroepInhoud> istOuder2;
    private final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGp;
    private final TussenStapel<BrpIstRelatieGroepInhoud> istKind;
    private final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhouding;

    /**
     * Maakt een MigratieRelatieStapel object.
     *
     * @param soortRelatieCode
     *            de soort relatie code, mag niet null zijn
     * @param rolCode
     *            de IK-rol, mag niet null zijn
     * @param betrokkenheden
     *            de lijst met betrokkenheden, deze lijst moet minimaal 1 stapel bevatten en mag niet null zijn
     * @param relatieStapel
     *            de stapel met relatie inhoud. Deze mag null zijn.
     * @param istOuder1
     *            lijst van IST-gegevens van de ouder1 relatie. Alleen gevuld als relatie over de ouders gaat
     * @param istOuder2
     *            lijst van IST-gegevens van de ouder2 relatie. Alleen gevuld als relatie over de ouders gaat
     * @param istHuwelijkOfGp
     *            lijst van IST-gegevens van het huwelijkOfGp relatie. Alleen gevuld als relatie over een huwelijk/
     *            geregistreerd partnerschap gaat
     * @param istKind
     *            lijst van IST-gegevens van de kind relatie. Alleen gevuld als relatie over kind gaat
     * @param istGezagsverhouding
     *            lijst van IST-gegevens van de gezagsverhouding. Alleen gevuld als relatie over de ouders gaat
     * @throws IllegalArgumentException
     *             als groepen een lege lijst is of als betrokkenheidStapels minder dan 1 of meer dan 4 stapels bevat
     * @throws NullPointerException
     *             als soortRelatieCode, ikBetrokkenheidStapel, betrokkenheidStapels of groepen null is
     */
    public TussenRelatie(
        final BrpSoortRelatieCode soortRelatieCode,
        final BrpSoortBetrokkenheidCode rolCode,
        final List<TussenBetrokkenheid> betrokkenheden,
        final TussenStapel<BrpRelatieInhoud> relatieStapel,
        final TussenStapel<BrpIstRelatieGroepInhoud> istOuder1,
        final TussenStapel<BrpIstRelatieGroepInhoud> istOuder2,
        final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGp,
        final TussenStapel<BrpIstRelatieGroepInhoud> istKind,
        final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhouding)
    {
        if (soortRelatieCode == null) {
            throw new NullPointerException(SOORT_RELATIE_CODE_ONTBREEKT);
        }
        if (rolCode == null) {
            throw new NullPointerException(SOORT_BETROKKENHEID_ONTBREEKT);
        }
        this.soortRelatieCode = soortRelatieCode;
        this.rolCode = rolCode;
        this.betrokkenheden = new ArrayList<>(betrokkenheden);
        this.relatieStapel = relatieStapel;
        this.istOuder1 = istOuder1;
        this.istOuder2 = istOuder2;
        this.istHuwelijkOfGp = istHuwelijkOfGp;
        this.istKind = istKind;
        this.istGezagsverhouding = istGezagsverhouding;
    }

    private TussenRelatie(final Builder builder) {
        soortRelatieCode = builder.soortRelatieCode;
        rolCode = builder.rolCode;
        betrokkenheden = builder.betrokkenheden;
        relatieStapel = builder.relatieStapel;
        istOuder1 = builder.istOuder1;
        istOuder2 = builder.istOuder2;
        istHuwelijkOfGp = builder.istHuwelijkOfGp;
        istKind = builder.istKind;
        istGezagsverhouding = builder.istGezagsverhouding;
    }

    /**
     * Geef de waarde van soort relatie code.
     *
     * @return the soortRelatieCode
     */

    public BrpSoortRelatieCode getSoortRelatieCode() {
        return soortRelatieCode;
    }

    /**
     * Geef de waarde van rol code.
     *
     * @return the rolCode
     */

    public BrpSoortBetrokkenheidCode getRolCode() {
        return rolCode;
    }

    /**
     * Geef de waarde van betrokkenheden.
     *
     * @return the betrokkenheden
     */
    public List<TussenBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Geef de waarde van relatie stapel.
     *
     * @return the relatieStapel
     */
    public TussenStapel<BrpRelatieInhoud> getRelatieStapel() {
        return relatieStapel;
    }

    /**
     * Geef de waarde van ist ouder1.
     *
     * @return IST gegevens voor ouder1.
     */
    public TussenStapel<BrpIstRelatieGroepInhoud> getIstOuder1() {
        return istOuder1;
    }

    /**
     * Geef de waarde van ist ouder2.
     *
     * @return IST gegevens voor ouder2.
     */
    public TussenStapel<BrpIstRelatieGroepInhoud> getIstOuder2() {
        return istOuder2;
    }

    /**
     * Geef de waarde van ist huwelijk of gp.
     *
     * @return IST gegevens voor huwelijkOfGp.
     */
    public TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> getIstHuwelijkOfGp() {
        return istHuwelijkOfGp;
    }

    /**
     * Geef de waarde van ist kind.
     *
     * @return IST gegevens voor kind.
     */
    public TussenStapel<BrpIstRelatieGroepInhoud> getIstKind() {
        return istKind;
    }

    /**
     * Geef de waarde van ist gezagsverhouding.
     *
     * @return IST gegevens voor gezagsverhouding.
     */
    public TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> getIstGezagsverhouding() {
        return istGezagsverhouding;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenRelatie)) {
            return false;
        }
        final TussenRelatie castOther = (TussenRelatie) other;
        return new EqualsBuilder().append(soortRelatieCode, castOther.soortRelatieCode)
                                  .append(rolCode, castOther.rolCode)
                                  .append(betrokkenheden, castOther.betrokkenheden)
                                  .append(relatieStapel, castOther.relatieStapel)
                                  .append(istOuder1, castOther.istOuder1)
                                  .append(istOuder2, castOther.istOuder2)
                                  .append(istHuwelijkOfGp, castOther.istHuwelijkOfGp)
                                  .append(istKind, castOther.istKind)
                                  .append(istGezagsverhouding, castOther.istGezagsverhouding)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortRelatieCode)
                                    .append(rolCode)
                                    .append(betrokkenheden)
                                    .append(relatieStapel)
                                    .append(istOuder1)
                                    .append(istOuder2)
                                    .append(istHuwelijkOfGp)
                                    .append(istKind)
                                    .append(istGezagsverhouding)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("soortRelatieCode", soortRelatieCode)
                                                                          .append("rolCode", rolCode)
                                                                          .append("betrokkenheden", betrokkenheden)
                                                                          .append("relatieStapel", relatieStapel)
                                                                          .append("istOuder1", istOuder1)
                                                                          .append("istOuder2", istOuder2)
                                                                          .append("istHuwelijkOfGp", istHuwelijkOfGp)
                                                                          .append("istKind", istKind)
                                                                          .append("istGezagsverhouding", istGezagsverhouding)
                                                                          .toString();
    }

    /**
     * Buider class voor TussenRelatie.
     */
    public static class Builder {
        private final BrpSoortRelatieCode soortRelatieCode;
        private final BrpSoortBetrokkenheidCode rolCode;
        private List<TussenBetrokkenheid> betrokkenheden = new ArrayList<>();
        private TussenStapel<BrpRelatieInhoud> relatieStapel;
        private TussenStapel<BrpIstRelatieGroepInhoud> istOuder1;
        private TussenStapel<BrpIstRelatieGroepInhoud> istOuder2;
        private TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGp;
        private TussenStapel<BrpIstRelatieGroepInhoud> istKind;
        private TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhouding;

        /**
         * Constructor voor Builder met verplichte velden soortRelatieCode en rolCode.
         *
         * @param soortRelatieCode
         *            soort relatie code
         * @param rolCode
         *            rol code
         */
        public Builder(final BrpSoortRelatieCode soortRelatieCode, final BrpSoortBetrokkenheidCode rolCode) {
            if (soortRelatieCode == null) {
                throw new NullPointerException(SOORT_RELATIE_CODE_ONTBREEKT);
            }
            if (rolCode == null) {
                throw new NullPointerException(SOORT_BETROKKENHEID_ONTBREEKT);
            }
            this.soortRelatieCode = soortRelatieCode;
            this.rolCode = rolCode;
        }

        /**
         * Maakt en vult een Builder op basis van een bestaande relatie.
         *
         * @param relatie
         *            bestaande migratie relatie
         */
        public Builder(final TussenRelatie relatie) {
            soortRelatieCode = relatie.soortRelatieCode;
            rolCode = relatie.rolCode;
            betrokkenheden = new ArrayList<>(relatie.betrokkenheden);
            relatieStapel = relatie.relatieStapel;
            istOuder1 = relatie.istOuder1;
            istOuder2 = relatie.istOuder2;
            istHuwelijkOfGp = relatie.istHuwelijkOfGp;
            istKind = relatie.istKind;
            istGezagsverhouding = relatie.istGezagsverhouding;
        }

        /**
         * zet de betrokkenheden.
         *
         * @param param
         *            lijst van betrokkenheden de relatie
         * @return de builder
         */
        public final Builder betrokkenheden(final List<TussenBetrokkenheid> param) {
            betrokkenheden = param;
            return this;
        }

        /**
         * zet de relatiestapel.
         *
         * @param param
         *            details van deze relatie
         * @return de builder
         */
        public final Builder relatieStapel(final TussenStapel<BrpRelatieInhoud> param) {
            relatieStapel = param;
            return this;
        }

        /**
         * zet de IST-stapel voor ouder1.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor ouder1
         * @return de builder
         */
        public final Builder istOuder1(final TussenStapel<BrpIstRelatieGroepInhoud> param) {
            istOuder1 = param;
            return this;
        }

        /**
         * zet de IST-stapel voor ouder2.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor ouder2
         * @return de builder
         */
        public final Builder istOuder2(final TussenStapel<BrpIstRelatieGroepInhoud> param) {
            istOuder2 = param;
            return this;
        }

        /**
         * zet de IST-stapel voor huwelijkOfGp.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor HuwelijkOfGp
         * @return de builder
         */
        public final Builder istHuwelijkOfGp(final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> param) {
            istHuwelijkOfGp = param;
            return this;
        }

        /**
         * zet de IST-stapel voor kind.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor kind
         * @return de builder
         */
        public final Builder istKind(final TussenStapel<BrpIstRelatieGroepInhoud> param) {
            istKind = param;
            return this;
        }

        /**
         * zet de IST-stapel voor Gezagsverhouding.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor gezagsverhouding
         * @return de builder
         */
        public final Builder istGezagsverhouding(final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> param) {
            istGezagsverhouding = param;
            return this;
        }

        /**
         * Maakt een nieuwe {@link TussenRelatie} object aan met de opgegeven gegevens.
         *
         * @return een nieuwe {@link TussenRelatie}
         */
        public final TussenRelatie build() {
            return new TussenRelatie(this);
        }
    }
}
