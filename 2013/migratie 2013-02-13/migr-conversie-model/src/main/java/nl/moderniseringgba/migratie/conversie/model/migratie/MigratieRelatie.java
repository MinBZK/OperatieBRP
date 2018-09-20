/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.migratie;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert een specifieke MigratieStapel, namelijk de MigratieRelatie stapel. Deze stapel heeft
 * associaties met betrokkenheid stapels. Deze associaties reflecteren de relaties in de BRP A-laag (zonder historie).
 * 
 */
public final class MigratieRelatie {

    private final BrpSoortRelatieCode soortRelatieCode;
    private final BrpSoortBetrokkenheidCode rolCode;
    private final List<MigratieBetrokkenheid> betrokkenheden;
    private final MigratieStapel<BrpRelatieInhoud> relatieStapel;

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
     * @throws IllegalArgumentException
     *             als groepen een lege lijst is of als betrokkenheidStapels minder dan 1 of meer dan 4 stapels bevat
     * @throws NullPointerException
     *             als soortRelatieCode, ikBetrokkenheidStapel, betrokkenheidStapels of groepen null is
     */
    public MigratieRelatie(
            @Element(name = "soortRelatieCode", required = false) final BrpSoortRelatieCode soortRelatieCode,
            @Element(name = "rolCode", required = false) final BrpSoortBetrokkenheidCode rolCode,
            @ElementList(name = "betrokkenheden", entry = "betrokkenheid", type = MigratieBetrokkenheid.class,
                    required = false) final List<MigratieBetrokkenheid> betrokkenheden, @Element(
                    name = "relatieStapel", required = false) final MigratieStapel<BrpRelatieInhoud> relatieStapel) {

        if (soortRelatieCode == null) {
            throw new NullPointerException("soortRelatieCode mag niet null zijn");
        }
        if (rolCode == null) {
            throw new NullPointerException("rolCode mag niet null zijn");
        }
        if (betrokkenheden == null) {
            throw new NullPointerException("betrokkenheden mag niet null zijn");
        }
        if (betrokkenheden.size() < 1) {
            throw new IllegalArgumentException("er moet minimaal 1 betrokkenheid zijn");
        }
        this.soortRelatieCode = soortRelatieCode;
        this.rolCode = rolCode;
        this.betrokkenheden = new ArrayList<MigratieBetrokkenheid>(betrokkenheden);
        this.relatieStapel = relatieStapel;
    }

    /**
     * @return the soortRelatieCode
     */
    @Element(name = "soortRelatieCode", required = false)
    public BrpSoortRelatieCode getSoortRelatieCode() {
        return soortRelatieCode;
    }

    /**
     * @return the rolCode
     */
    @Element(name = "rolCode", required = false)
    public BrpSoortBetrokkenheidCode getRolCode() {
        return rolCode;
    }

    /**
     * @return the betrokkenheden
     */
    @ElementList(name = "betrokkenheden", entry = "betrokkenheid", type = MigratieBetrokkenheid.class,
            required = false)
    public List<MigratieBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * @return the relatieStapel
     */
    @Element(name = "relatieStapel", required = false)
    public MigratieStapel<BrpRelatieInhoud> getRelatieStapel() {
        return relatieStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MigratieRelatie)) {
            return false;
        }
        final MigratieRelatie castOther = (MigratieRelatie) other;
        return new EqualsBuilder().append(soortRelatieCode, castOther.soortRelatieCode)
                .append(rolCode, castOther.rolCode).append(betrokkenheden, castOther.betrokkenheden)
                .append(relatieStapel, castOther.relatieStapel).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortRelatieCode).append(rolCode).append(betrokkenheden)
                .append(relatieStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("soortRelatieCode", soortRelatieCode).append("rolCode", rolCode)
                .append("betrokkenheden", betrokkenheden).append("relatieStapel", relatieStapel).toString();
    }
}
