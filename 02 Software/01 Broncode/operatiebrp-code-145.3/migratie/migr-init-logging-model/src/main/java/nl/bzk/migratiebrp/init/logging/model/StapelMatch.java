/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Object waarin de stapel(s) met dezelfde herkomst van zowel de oorspronkelijke LO3 als de
 * teruggeconverteerde BRP persoonslijsten worden opgeslagen.
 * @param <T> moet een implementatie zijn van {@link Lo3CategorieInhoud}
 */
public final class StapelMatch<T extends Lo3CategorieInhoud> {

    private final Lo3Herkomst herkomst;
    private final List<Lo3Stapel<T>> lo3Stapels;
    private final List<Lo3Stapel<T>> brpLo3Stapels;

    /**
     * Constructor.
     * @param herkomst herkomst waar deze {@link StapelMatch} voor gaat gelden.
     */
    public StapelMatch(final Lo3Herkomst herkomst) {
        this.herkomst = herkomst;
        lo3Stapels = new ArrayList<>();
        brpLo3Stapels = new ArrayList<>();
    }

    /**
     * Voegt een stapel toe vanuit de oorspronkelijke LO3 persoonslijst.
     * @param stapel oorspronkelijke LO3 stapel
     */
    public void addLo3Stapel(final Lo3Stapel<T> stapel) {
        lo3Stapels.add(stapel);
    }

    /**
     * Voegt een stapel toe vanuit de teruggeconverteerde BRP persoonslijst.
     * @param stapel teruggeconverteerde BRP stapel
     */
    public void addBrpLo3Stapel(final Lo3Stapel<T> stapel) {
        brpLo3Stapels.add(stapel);
    }

    /**
     * Geeft de volgende mogelijke waarden terug.<br>
     * <ul>
     * <li>{@link VerschilType#ADDED} - als er geen stapel is gevonden in LO3, maar wel in BRP</li>
     * <li>{@link VerschilType#MATCHED} - als er precies 1 stapel is gevonden in zowel LO3 als
     * BRP</li>
     * <li>{@link VerschilType#NON_UNIQUE_MATCHED} - als er meer dan 1 stapel is gevonden in of LO3
     * of BRP</li>
     * <li>{@link VerschilType#REMOVED} - als er een stapel is gevonden in LO3, maar niet in
     * BRP</li>
     * </ul>
     * @return de {@link VerschilType} zoals beschreven
     */
    public VerschilType getVerschilType() {
        VerschilType result = VerschilType.NON_UNIQUE_MATCHED;
        if (lo3Stapels.isEmpty()) {
            if (!brpLo3Stapels.isEmpty()) {
                result = VerschilType.ADDED;
            }
        } else {
            if (brpLo3Stapels.isEmpty()) {
                result = VerschilType.REMOVED;
            } else if (isUniqueMatched()) {
                result = VerschilType.MATCHED;
            }
        }

        return result;
    }

    /**
     * Geef de unique matched.
     * @return true als er precies 1 stapel is voor zowel LO3 als BRP
     */
    public boolean isUniqueMatched() {
        return lo3Stapels.size() == 1 && brpLo3Stapels.size() == 1;
    }

    /**
     * Geef de waarde van herkomst.
     * @return de {@link Lo3Herkomst} waar deze {@link StapelMatch} voor is
     */
    public Lo3Herkomst getHerkomst() {
        return herkomst;
    }

    /**
     * Geef de waarde van lo3 stapels.
     * @return de lijst van {@link Lo3Stapel} vanuit LO3
     */
    public List<Lo3Stapel<T>> getLo3Stapels() {
        return lo3Stapels;
    }

    /**
     * Geef de waarde van brp lo3 stapels.
     * @return de lijst van {@link Lo3Stapel} vanuit BRP
     */
    public List<Lo3Stapel<T>> getBrpLo3Stapels() {
        return brpLo3Stapels;
    }

    @Override
    public boolean equals(final Object o) {
        boolean result = false;
        if (o instanceof StapelMatch) {
            final StapelMatch<?> o2 = (StapelMatch<?>) o;
            result = herkomst == null ? o2.herkomst == null : herkomst.equalsCategorieStapelOnly(o2.herkomst);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(herkomst).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("Lo3Herkomst", herkomst).toString();
    }
}
