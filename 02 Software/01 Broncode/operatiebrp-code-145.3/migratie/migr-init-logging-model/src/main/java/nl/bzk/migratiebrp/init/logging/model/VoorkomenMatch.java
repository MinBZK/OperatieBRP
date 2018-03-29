/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Object waarin voorkomens met dezelfde herkomst van zowel de oorspronkelijke LO3 als de
 * teruggeconverteerde BRP persoonslijsten worden opgeslagen.
 * @param <T> moet een implementatie zijn van {@link Lo3CategorieInhoud}
 */
public final class VoorkomenMatch<T extends Lo3CategorieInhoud> {

    private final Lo3Herkomst herkomst;
    private final List<Lo3Categorie<T>> lo3Voorkomens;
    private final List<Lo3Categorie<T>> brpLo3Voorkomens;

    private boolean isLo3Actueel;
    private boolean isBrpLo3Actueel;

    /**
     * Constructor.
     * @param herkomst herkomst waarmee een match wordt gemaakt
     */
    public VoorkomenMatch(final Lo3Herkomst herkomst) {
        this.herkomst = herkomst;
        lo3Voorkomens = new ArrayList<>();
        brpLo3Voorkomens = new ArrayList<>();
    }

    /**
     * Voegt een LO3 voorkomen toe aan de match.
     * @param voorkomen LO3 voorkomen
     */
    public void addLo3Voorkomen(final Lo3Categorie<T> voorkomen) {
        lo3Voorkomens.add(voorkomen);
    }

    /**
     * Voegt een BRP/LO3 voorkomen toe aan de match.
     * @param voorkomen BRP/LO3 voorkomen
     */
    public void addBrpLo3Voorkomen(final Lo3Categorie<T> voorkomen) {
        brpLo3Voorkomens.add(voorkomen);
    }

    /**
     * Geeft de volgende mogelijke waarden terug.<br>
     * <ul>
     * <li>{@link VerschilType#ADDED} - als er geen voorkomen is gevonden in LO3, maar wel in
     * BRP</li>
     * <li>{@link VerschilType#MATCHED} - als er precies 1 voorkomen is gevonden in zowel LO3 als
     * BRP</li>
     * <li>{@link VerschilType#NON_UNIQUE_MATCHED} - als er meer dan 1 voorkomen is gevonden in of
     * LO3 of BRP</li>
     * <li>{@link VerschilType#REMOVED} - als er een voorkomen is gevonden in LO3, maar niet in
     * BRP</li>
     * <li>{@link VerschilType#NOT_ACTUAL} - als er het voorkomen in LO3 actueel is, maar in BRP
     * niet actueel is</li>
     * </ul>
     * @return de {@link VerschilType} zoals beschreven
     */
    public VerschilType getVerschilType() {
        VerschilType result = VerschilType.NON_UNIQUE_MATCHED;
        if (lo3Voorkomens.isEmpty()) {
            if (!brpLo3Voorkomens.isEmpty()) {
                result = VerschilType.ADDED;
            }
        } else {
            if (brpLo3Voorkomens.isEmpty()) {
                result = VerschilType.REMOVED;
            } else {
                if (isUniqueMatched()) {
                    result = VerschilType.MATCHED;
                }
                if (isLo3Actueel && !isBrpLo3Actueel) {
                    result = VerschilType.NOT_ACTUAL;
                }
            }
        }

        return result;
    }

    /**
     * Geef de unique matched.
     * @return true als er precies 1 is van zowel LO3 als BRP/LO3 voorkomens.
     */
    public boolean isUniqueMatched() {
        return lo3Voorkomens.size() == 1 && brpLo3Voorkomens.size() == 1;
    }

    /**
     * Geef de waarde van herkomst.
     * @return de herkomst van deze match.
     */
    public Lo3Herkomst getHerkomst() {
        return herkomst;
    }

    /**
     * Geef de waarde van lo3 voorkomens.
     * @return de LO3 voorkomens
     */
    public List<Lo3Categorie<T>> getLo3Voorkomens() {
        return lo3Voorkomens;
    }

    /**
     * Geef de waarde van brp lo3 voorkomens.
     * @return de BRP/LO3 voorkomens
     */
    public List<Lo3Categorie<T>> getBrpLo3Voorkomens() {
        return brpLo3Voorkomens;
    }

    @Override
    public boolean equals(final Object o) {
        boolean result = false;
        if (o instanceof VoorkomenMatch) {
            final VoorkomenMatch<?> o2 = (VoorkomenMatch<?>) o;
            result = herkomst.equals(o2.herkomst);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(herkomst).toHashCode();
    }

    /**
     * Geef de lo3 actueel.
     * @return true als het voorkomen in LO3 actueel is.
     */
    public boolean isLo3Actueel() {
        return isLo3Actueel;
    }

    /**
     * Zet de waarde van checks if is lo3 actueel.
     * @param isLo3Actueel checks if is lo3 actueel
     */
    public void setIsLo3Actueel(final boolean isLo3Actueel) {
        this.isLo3Actueel = isLo3Actueel;
    }

    /**
     * Geef de brp lo3 actueel.
     * @return true als het voorkomen in BRP/LO3 actueel is.
     */
    public boolean isBrpLo3Actueel() {
        return isBrpLo3Actueel;
    }

    /**
     * Zet de waarde van checks if is brp lo3 actueel.
     * @param isBrpLo3Actueel checks if is brp lo3 actueel
     */
    public void setIsBrpLo3Actueel(final boolean isBrpLo3Actueel) {
        this.isBrpLo3Actueel = isBrpLo3Actueel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("Lo3Herkomst", herkomst).toString();
    }
}
