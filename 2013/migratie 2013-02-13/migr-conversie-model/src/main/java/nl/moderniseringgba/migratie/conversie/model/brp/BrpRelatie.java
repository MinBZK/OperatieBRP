/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

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
 * Deze class representeert de BrpRelatie. Deze relatie heeft associaties met een huwelijk en een of meerdere
 * betrokkenheid stapels. Deze associaties reflecteren de relaties in de BRP A-laag (zonder historie).
 * 
 */
public final class BrpRelatie {

    private final BrpSoortRelatieCode soortRelatieCode;
    private final BrpSoortBetrokkenheidCode rolCode;
    private final List<BrpBetrokkenheid> betrokkenheden;
    private final BrpStapel<BrpRelatieInhoud> relatieStapel;

    /**
     * Maakt een BrpRelatieStapel object.
     * 
     * @param soortRelatieCode
     *            de soort relatie code, mag niet null zijn
     * @param rolCode
     *            de IK-betrokkenheid rol, mag niet null zijn
     * @param betrokkenheden
     *            de betrokkenheden, mag niet null zijn en moet minimaal 1 betrokkenheid bevatten
     * @param relatieStapel
     *            stapel de stapel met inhoudelijk BRP relatie groepen. Deze mag null zijn.
     * @throws IllegalArgumentException
     *             als groepen een lege lijst is of als betrokkenheidStapels minder dan 1 of meer dan 4 stapels bevat
     * @throws NullPointerException
     *             als soortRelatieCode, ikBetrokkenheidStapel, betrokkenheidStapels of groepen null is
     */
    public BrpRelatie(
            @Element(name = "soortRelatieCode") final BrpSoortRelatieCode soortRelatieCode,
            @Element(name = "rolCode") final BrpSoortBetrokkenheidCode rolCode,
            @ElementList(name = "betrokkenheden", entry = "betrokkenheid", type = BrpBetrokkenheid.class,
                    required = false) final List<BrpBetrokkenheid> betrokkenheden, @Element(name = "relatieStapel",
                    required = false) final BrpStapel<BrpRelatieInhoud> relatieStapel) {
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
        this.betrokkenheden = new ArrayList<BrpBetrokkenheid>(betrokkenheden);
        this.relatieStapel = relatieStapel;
    }

    /**
     * Valideer alle relaties in de relatiestapel.
     */
    public void valideer() {
        if (relatieStapel != null) {
            for (final BrpGroep<BrpRelatieInhoud> relatie : relatieStapel) {
                relatie.getInhoud().valideer();
            }
        }
    }

    /**
     * @return the soortRelatieCode
     */
    @Element(name = "soortRelatieCode")
    public BrpSoortRelatieCode getSoortRelatieCode() {
        return soortRelatieCode;
    }

    /**
     * @return the rolCode
     */
    @Element(name = "rolCode")
    public BrpSoortBetrokkenheidCode getRolCode() {
        return rolCode;
    }

    /**
     * @return the betrokkenheden
     */
    @ElementList(name = "betrokkenheden", entry = "betrokkenheid", type = BrpBetrokkenheid.class, required = false)
    public List<BrpBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * @return the relatieStapel
     */
    @Element(name = "relatieStapel", required = false)
    public BrpStapel<BrpRelatieInhoud> getRelatieStapel() {
        return relatieStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpRelatie)) {
            return false;
        }
        final BrpRelatie castOther = (BrpRelatie) other;
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
