/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
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
public final class TussenBetrokkenheid {

    private final BrpSoortBetrokkenheidCode rol;
    private final TussenStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
    private final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final TussenStapel<BrpGeboorteInhoud> geboorteStapel;
    private final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final TussenStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel;
    private final TussenStapel<BrpOuderInhoud> ouderStapel;

    /**
     * Maak een betrokkenheid.
     * 
     * @param rol
     *            rol
     * @param identificatienummersStapel
     *            identificatienummers (gerelateerde) stapel
     * @param geslachtsaanduidingStapel
     *            geslachtsaanduiding (gerelateerde) stapel
     * @param geboorteStapel
     *            geboorte (gerelateerde) stapel
     * @param ouderlijkGezagStapel
     *            ouderlijk gezag (gerelateerde) stapel
     * @param samengesteldeNaamStapel
     *            samengestelde naam (gerelateerde) stapel
     * @param ouderStapel
     *            ouder stapel
     */
    public TussenBetrokkenheid(
        final BrpSoortBetrokkenheidCode rol,
        final TussenStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
        final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
        final TussenStapel<BrpGeboorteInhoud> geboorteStapel,
        final TussenStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel,
        final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
        final TussenStapel<BrpOuderInhoud> ouderStapel)
    {
        this.rol = rol;
        this.identificatienummersStapel = identificatienummersStapel;
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        this.geboorteStapel = geboorteStapel;
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        this.ouderlijkGezagStapel = ouderlijkGezagStapel;
        this.ouderStapel = ouderStapel;
    }

    /**
     * Geef de waarde van rol.
     *
     * @return the rol
     */
    public BrpSoortBetrokkenheidCode getRol() {
        return rol;
    }

    /**
     * Geef de waarde van identificatienummers stapel.
     *
     * @return the identificatienummersStapel
     */
    public TussenStapel<BrpIdentificatienummersInhoud> getIdentificatienummersStapel() {
        return identificatienummersStapel;
    }

    /**
     * Geef de waarde van geslachtsaanduiding stapel.
     *
     * @return the geslachtsaanduidingStapel
     */
    public TussenStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * Geef de waarde van geboorte stapel.
     *
     * @return the geboorteStapel
     */
    public TussenStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * Geef de waarde van samengestelde naam stapel.
     *
     * @return the samengesteldeNaamStapel
     */
    public TussenStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {
        return samengesteldeNaamStapel;
    }

    /**
     * Geef de waarde van ouderlijk gezag stapel.
     *
     * @return the ouderlijkGezagStapel
     */
    public TussenStapel<BrpOuderlijkGezagInhoud> getOuderlijkGezagStapel() {
        return ouderlijkGezagStapel;
    }

    /**
     * Geef de waarde van ouder stapel.
     *
     * @return the ouderStapel
     */
    public TussenStapel<BrpOuderInhoud> getOuderStapel() {
        return ouderStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenBetrokkenheid)) {
            return false;
        }
        final TussenBetrokkenheid castOther = (TussenBetrokkenheid) other;
        return new EqualsBuilder().append(rol, castOther.rol)
                                  .append(identificatienummersStapel, castOther.identificatienummersStapel)
                                  .append(geslachtsaanduidingStapel, castOther.geslachtsaanduidingStapel)
                                  .append(geboorteStapel, castOther.geboorteStapel)
                                  .append(samengesteldeNaamStapel, castOther.samengesteldeNaamStapel)
                                  .append(ouderlijkGezagStapel, castOther.ouderlijkGezagStapel)
                                  .append(ouderStapel, castOther.ouderStapel)
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
                                                                          .toString();
    }

}
