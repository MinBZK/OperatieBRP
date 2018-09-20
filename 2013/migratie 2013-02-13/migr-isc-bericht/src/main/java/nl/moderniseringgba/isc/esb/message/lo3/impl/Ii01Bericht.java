/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Ii01.
 */
public final class Ii01Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.HERHALING);

    private List<Lo3CategorieWaarde> categorieen;

    /**
     * Constructor.
     */
    public Ii01Bericht() {
        super(HEADER);
    }

    @Override
    public String getBerichtType() {
        return "Ii01";
    }

    @Override
    public String getStartCyclus() {
        return "uc301";
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        this.categorieen = categorieen;
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return categorieen;
    }

    /* ************************************************************************************************************* */

    /**
     * Set een element waarde.
     * 
     * @param categorie
     *            categorie
     * @param element
     *            element
     * @param waarde
     *            waarde
     */
    public void set(final Lo3CategorieEnum categorie, final Lo3ElementEnum element, final String waarde) {
        if (categorieen == null) {
            categorieen = new ArrayList<Lo3CategorieWaarde>();
        }
        Lo3CategorieWaardeUtil.setElementWaarde(categorieen, Lo3CategorieEnum.bepaalActueleCategorie(categorie), 0,
                categorie.isActueel() ? 0 : 1, element, waarde);
    }

    /**
     * Get een element waarde.
     * 
     * @param categorie
     *            categorie
     * @param element
     *            element
     * @return waarde
     */
    public String get(final Lo3CategorieEnum categorie, final Lo3ElementEnum element) {
        return Lo3CategorieWaardeUtil.getElementWaarde(categorieen,
                Lo3CategorieEnum.bepaalActueleCategorie(categorie), 0, categorie.isActueel() ? 0 : 1, element);
    }

    /**
     * Geeft de categoriewaarden terug.
     * 
     * @return De lijst met categoriewaarden
     */
    public List<Lo3CategorieWaarde> getCategorieen() {
        return Lo3CategorieWaardeUtil.deepCopy(categorieen);
    }

    /**
     * Zet de categoriewaarden.
     * 
     * @param categorieen
     *            De te zetten categoriewaarden.
     */
    public void setCategorieen(final List<Lo3CategorieWaarde> categorieen) {
        this.categorieen = Lo3CategorieWaardeUtil.deepCopy(categorieen);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Ii01Bericht)) {
            return false;
        }
        final Ii01Bericht castOther = (Ii01Bericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(categorieen, castOther.categorieen)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(categorieen).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("categorieen", categorieen).toString();
    }
}
