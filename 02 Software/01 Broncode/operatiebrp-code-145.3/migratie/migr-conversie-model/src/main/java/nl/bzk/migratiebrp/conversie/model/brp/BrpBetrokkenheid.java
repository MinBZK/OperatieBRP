/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Sortable;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert een BRP betrokkenheid. Een betrokkenheid heeft een rol en stapels met gegevens
 * (gerelateerde, ouder indicatie en ouderlijk gezag).
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpBetrokkenheid implements Sortable {

    private final BrpSoortBetrokkenheidCode rol;
    private final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
    private final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final BrpStapel<BrpGeboorteInhoud> geboorteStapel;
    private final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel;
    private final BrpStapel<BrpOuderInhoud> ouderStapel;
    private final BrpStapel<BrpIdentiteitInhoud> identiteitStapel;

    /**
     * Maak een BrpBetrokkenheid.
     * @param rol rol
     * @param identificatienummersStapel identificatienummer stapel
     * @param geslachtsaanduidingStapel geslachtsaanduiding stapel
     * @param geboorteStapel geboortestapel
     * @param ouderlijkGezagStapel ouderlijkgezag stapel
     * @param samengesteldeNaamStapel samengestelde naam stapel
     * @param ouderStapel ouder stapel
     * @param identiteitStapel de identiteit stapel van deze betrokkenheid
     */
    public BrpBetrokkenheid(
            @Element(name = "rol") final BrpSoortBetrokkenheidCode rol,
            @Element(name = "identificatienummerStapel") final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
            @Element(name = "geslachtsaanduidingStapel") final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
            @Element(name = "geboorteStapel") final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            @Element(name = "ouderlijkGezagStapel") final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel,
            @Element(name = "samengesteldeNaamStapel") final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
            @Element(name = "ouderStapel") final BrpStapel<BrpOuderInhoud> ouderStapel,
            @Element(name = "identiteitStapel") final BrpStapel<BrpIdentiteitInhoud> identiteitStapel) {
        this.rol = rol;
        this.identificatienummersStapel = identificatienummersStapel;
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        this.geboorteStapel = geboorteStapel;
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        this.ouderlijkGezagStapel = ouderlijkGezagStapel;
        this.ouderStapel = ouderStapel;
        this.identiteitStapel = identiteitStapel;
    }

    /**
     * Geef de waarde van rol.
     * @return the rol
     */
    @Element(name = "rol")
    public BrpSoortBetrokkenheidCode getRol() {
        return rol;
    }

    /**
     * Geef de waarde van identificatienummers stapel.
     * @return the identificatienummersStapel
     */
    @Element(name = "identificatienummerStapel")
    public BrpStapel<BrpIdentificatienummersInhoud> getIdentificatienummersStapel() {
        return identificatienummersStapel;
    }

    /**
     * Geef de waarde van geslachtsaanduiding stapel.
     * @return the geslachtsaanduidingStapel
     */
    @Element(name = "geslachtsaanduidingStapel")
    public BrpStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * Geef de waarde van geboorte stapel.
     * @return the geboorteStapel
     */
    @Element(name = "geboorteStapel")
    public BrpStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * Geef de waarde van samengestelde naam stapel.
     * @return the samengesteldeNaamStapel
     */
    @Element(name = "samengesteldeNaamStapel")
    public BrpStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {
        return samengesteldeNaamStapel;
    }

    /**
     * Geef de waarde van ouderlijk gezag stapel.
     * @return the ouderlijkGezagStapel
     */
    @Element(name = "ouderlijkGezagStapel")
    public BrpStapel<BrpOuderlijkGezagInhoud> getOuderlijkGezagStapel() {
        return ouderlijkGezagStapel;
    }

    /**
     * Geef de waarde van ouder stapel.
     * @return the ouderStapel
     */
    @Element(name = "ouderStapel")
    public BrpStapel<BrpOuderInhoud> getOuderStapel() {
        return ouderStapel;
    }

    /**
     * Geef de waarde van identiteit stapel.
     * @return the identiteitStapel
     */
    @Element(name = "identiteitStapel")
    public BrpStapel<BrpIdentiteitInhoud> getIdentiteitStapel() {
        return identiteitStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBetrokkenheid)) {
            return false;
        }
        final BrpBetrokkenheid castOther = (BrpBetrokkenheid) other;
        return new EqualsBuilder().append(rol, castOther.rol)
                .append(identificatienummersStapel, castOther.identificatienummersStapel)
                .append(geslachtsaanduidingStapel, castOther.geslachtsaanduidingStapel)
                .append(geboorteStapel, castOther.geboorteStapel)
                .append(samengesteldeNaamStapel, castOther.samengesteldeNaamStapel)
                .append(ouderlijkGezagStapel, castOther.ouderlijkGezagStapel)
                .append(ouderStapel, castOther.ouderStapel)
                .append(identiteitStapel, castOther.identiteitStapel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rol)
                .append(identificatienummersStapel)
                .append(geslachtsaanduidingStapel)
                .append(geboorteStapel)
                .append(samengesteldeNaamStapel)
                .append(ouderlijkGezagStapel)
                .append(ouderStapel)
                .append(identiteitStapel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("rol", rol)
                .append("identificatienummersStapel", identificatienummersStapel)
                .append("geslachtsaanduidingStapel", geslachtsaanduidingStapel)
                .append("geboorteStapel", geboorteStapel)
                .append("samengesteldeNaamStapel", samengesteldeNaamStapel)
                .append("ouderlijkGezagStapel", ouderlijkGezagStapel)
                .append("ouderStapel", ouderStapel)
                .append("identiteitStapel", identiteitStapel)
                .toString();
    }

    @Override
    public void sorteer() {
        sorteerStapel(identificatienummersStapel);
        sorteerStapel(geslachtsaanduidingStapel);
        sorteerStapel(geboorteStapel);
        sorteerStapel(samengesteldeNaamStapel);
        sorteerStapel(ouderlijkGezagStapel);
        sorteerStapel(ouderStapel);
        sorteerStapel(identiteitStapel);
    }

    private <T extends BrpGroepInhoud> void sorteerStapel(BrpStapel<T> stapel){
        if (stapel != null) {
            stapel.sorteer();
        }
    }

    /**
     * Buider class voor BrpBetrokkenheid.
     */
    public static class Builder {
        private BrpSoortBetrokkenheidCode rol;
        private BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
        private BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
        private BrpStapel<BrpGeboorteInhoud> geboorteStapel;
        private BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
        private BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel;
        private BrpStapel<BrpOuderInhoud> ouderStapel;
        private BrpStapel<BrpIdentiteitInhoud> identiteitStapel;

        /**
         * Constructor voor lege Builder.
         */
        public Builder() {
            /* just empty builder */
        }

        /**
         * Maakt en vul een Builder op basis van een bestaande BrpBetrokkenheid.
         * @param betrokkenheid BrpBetrokkenheid die gebruikt wordt om de waarden van de Builder te vullen
         */
        public Builder(final BrpBetrokkenheid betrokkenheid) {
            rol = betrokkenheid.rol;
            identificatienummersStapel = betrokkenheid.identificatienummersStapel;
            geslachtsaanduidingStapel = betrokkenheid.geslachtsaanduidingStapel;
            geboorteStapel = betrokkenheid.geboorteStapel;
            samengesteldeNaamStapel = betrokkenheid.samengesteldeNaamStapel;
            ouderlijkGezagStapel = betrokkenheid.ouderlijkGezagStapel;
            ouderStapel = betrokkenheid.ouderStapel;
            identiteitStapel = betrokkenheid.identiteitStapel;
        }

        /**
         * zet de rol.
         * @param param de rol van de betrokkenheid
         * @return de builder
         */
        public final Builder rol(final BrpSoortBetrokkenheidCode param) {
            rol = param;
            return this;
        }

        /**
         * zet de identificatienummersStapel.
         * @param param identificatienummersStapel
         * @return de builder
         */
        public final Builder identificatienummersStapel(final BrpStapel<BrpIdentificatienummersInhoud> param) {
            identificatienummersStapel = param;
            return this;
        }

        /**
         * zet de geslachtsaanduidingStapel.
         * @param param geslachtsaanduidingStapel
         * @return de builder
         */
        public final Builder geslachtsaanduidingStapel(final BrpStapel<BrpGeslachtsaanduidingInhoud> param) {
            geslachtsaanduidingStapel = param;
            return this;
        }

        /**
         * zet de geboorteStapel.
         * @param param geboorteStapel
         * @return de builder
         */
        public final Builder geboorteStapel(final BrpStapel<BrpGeboorteInhoud> param) {
            geboorteStapel = param;
            return this;
        }

        /**
         * zet de samengesteldeNaamStapel.
         * @param param samengesteldeNaamStapel
         * @return de builder
         */
        public final Builder samengesteldeNaamStapel(final BrpStapel<BrpSamengesteldeNaamInhoud> param) {
            samengesteldeNaamStapel = param;
            return this;
        }

        /**
         * zet de ouderlijkGezagStapel.
         * @param param ouderlijkGezagStapel
         * @return de builder
         */
        public final Builder ouderlijkGezagStapel(final BrpStapel<BrpOuderlijkGezagInhoud> param) {
            ouderlijkGezagStapel = param;
            return this;
        }

        /**
         * zet de ouderStapel.
         * @param param ouderStapel
         * @return de builder
         */
        public final Builder ouderStapel(final BrpStapel<BrpOuderInhoud> param) {
            ouderStapel = param;
            return this;
        }

        /**
         * zet de identiteitStapel.
         * @param param identiteitStapel
         * @return de builder
         */
        public final Builder identiteitStapel(final BrpStapel<BrpIdentiteitInhoud> param) {
            identiteitStapel = param;
            return this;
        }

        /**
         * Maakt een nieuwe {@link BrpBetrokkenheid} object aan met de opgegeven gegevens.
         * @return een nieuwe {@link BrpBetrokkenheid}
         */
        public final BrpBetrokkenheid build() {
            return new BrpBetrokkenheid(
                    rol,
                    identificatienummersStapel,
                    geslachtsaanduidingStapel,
                    geboorteStapel,
                    ouderlijkGezagStapel,
                    samengesteldeNaamStapel,
                    ouderStapel,
                    identiteitStapel);
        }
    }
}
