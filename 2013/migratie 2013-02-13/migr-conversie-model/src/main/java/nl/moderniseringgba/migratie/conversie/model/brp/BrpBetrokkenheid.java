/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert een BRP betrokkenheid. Een betrokkenheid heeft een rol en stapels met gegevens
 * (gerelateerde, ouder indicatie en ouderlijk gezag).
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpBetrokkenheid {

    private final BrpSoortBetrokkenheidCode rol;
    private final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
    private final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final BrpStapel<BrpGeboorteInhoud> geboorteStapel;
    private final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel;
    private final BrpStapel<BrpOuderInhoud> ouderStapel;

    /**
     * Maak een BrpBetrokkenheid.
     * 
     * @param rol
     *            rol
     * @param identificatienummersStapel
     *            identificatienummer stapel
     * @param geslachtsaanduidingStapel
     *            geslachtsaanduiding stapel
     * @param geboorteStapel
     *            geboortestapel
     * @param ouderlijkGezagStapel
     *            ouderlijkgezag stapel
     * @param samengesteldeNaamStapel
     *            samengestelde naam stapel
     * @param ouderStapel
     *            ouder stapel
     */
    public BrpBetrokkenheid(
            @Element(name = "rol", required = false) final BrpSoortBetrokkenheidCode rol,
            @Element(name = "identificatienummerStapel", required = false) final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
            @Element(name = "geslachtsaanduidingStapel", required = false) final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
            @Element(name = "geboorteStapel", required = false) final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
            @Element(name = "ouderlijkGezagStapel", required = false) final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel,
            @Element(name = "samengesteldeNaamStapel", required = false) final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
            @Element(name = "ouderStapel", required = false) final BrpStapel<BrpOuderInhoud> ouderStapel) {
        this.rol = rol;
        this.identificatienummersStapel = identificatienummersStapel;
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        this.geboorteStapel = geboorteStapel;
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        this.ouderlijkGezagStapel = ouderlijkGezagStapel;
        this.ouderStapel = ouderStapel;
    }

    /**
     * @return the rol
     */
    @Element(name = "rol", required = false)
    public BrpSoortBetrokkenheidCode getRol() {
        return rol;
    }

    /**
     * @return the identificatienummersStapel
     */
    @Element(name = "identificatienummerStapel", required = false)
    public BrpStapel<BrpIdentificatienummersInhoud> getIdentificatienummersStapel() {
        return identificatienummersStapel;
    }

    /**
     * @return the geslachtsaanduidingStapel
     */
    @Element(name = "geslachtsaanduidingStapel", required = false)
    public BrpStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * @return the geboorteStapel
     */
    @Element(name = "geboorteStapel", required = false)
    public BrpStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * @return the samengesteldeNaamStapel
     */
    @Element(name = "samengesteldeNaamStapel", required = false)
    public BrpStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {
        return samengesteldeNaamStapel;
    }

    /**
     * @return the ouderlijkGezagStapel
     */
    @Element(name = "ouderlijkGezagStapel", required = false)
    public BrpStapel<BrpOuderlijkGezagInhoud> getOuderlijkGezagStapel() {
        return ouderlijkGezagStapel;
    }

    /**
     * @return the ouderStapel
     */
    @Element(name = "ouderStapel", required = false)
    public BrpStapel<BrpOuderInhoud> getOuderStapel() {
        return ouderStapel;
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
                .append(ouderStapel, castOther.ouderStapel).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rol).append(identificatienummersStapel).append(geslachtsaanduidingStapel)
                .append(geboorteStapel).append(samengesteldeNaamStapel).append(ouderlijkGezagStapel)
                .append(ouderStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("rol", rol)
                .append("identificatienummersStapel", identificatienummersStapel)
                .append("geslachtsaanduidingStapel", geslachtsaanduidingStapel)
                .append("geboorteStapel", geboorteStapel).append("samengesteldeNaamStapel", samengesteldeNaamStapel)
                .append("ouderlijkGezagStapel", ouderlijkGezagStapel).append("ouderStapel", ouderStapel).toString();
    }

}
