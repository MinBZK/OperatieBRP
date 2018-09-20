/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Tv01.
 */
public final class Tv01Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.HERHALING);

    private List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

    /**
     * Constructor.
     */
    public Tv01Bericht() {
        super(HEADER);
    }

    /**
     * Convenient constructor.
     * 
     * @param categorieen
     *            De categorieën voor op het Tv01 bericht.
     */
    public Tv01Bericht(final List<Lo3CategorieWaarde> categorieen) {
        super(HEADER);
        this.categorieen = categorieen;
    }

    @Override
    public String getBerichtType() {
        return "Tv01";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // 01 Identificatienummers, 02 Naam, 03 Geboorte, 09 Gemeente, 70 Geheim, 83 Procedure, 85 Geldigheid

        this.categorieen = new ArrayList<Lo3CategorieWaarde>();
        for (final Lo3CategorieWaarde lo3CategorieWaarde : categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_21.equals(lo3CategorieWaarde.getCategorie())) {

                // alleen de volgende elementen:
                // 01 Identificatienummers, 02 Naam, 03 Geboorte, 09 Gemeente, 70 Geheim, 83 Procedure, 85 Geldigheid
                final Map<Lo3ElementEnum, String> elementen = lo3CategorieWaarde.getElementen();

                final Map<Lo3ElementEnum, String> filtered = new HashMap<Lo3ElementEnum, String>();
                filtered.put(Lo3ElementEnum.ELEMENT_0110, elementen.get(Lo3ElementEnum.ELEMENT_0110));
                filtered.put(Lo3ElementEnum.ELEMENT_0120, elementen.get(Lo3ElementEnum.ELEMENT_0120));

                filtered.put(Lo3ElementEnum.ELEMENT_0210, elementen.get(Lo3ElementEnum.ELEMENT_0210));
                filtered.put(Lo3ElementEnum.ELEMENT_0220, elementen.get(Lo3ElementEnum.ELEMENT_0220));
                filtered.put(Lo3ElementEnum.ELEMENT_0230, elementen.get(Lo3ElementEnum.ELEMENT_0230));
                filtered.put(Lo3ElementEnum.ELEMENT_0240, elementen.get(Lo3ElementEnum.ELEMENT_0240));

                filtered.put(Lo3ElementEnum.ELEMENT_0310, elementen.get(Lo3ElementEnum.ELEMENT_0310));
                filtered.put(Lo3ElementEnum.ELEMENT_0320, elementen.get(Lo3ElementEnum.ELEMENT_0320));
                filtered.put(Lo3ElementEnum.ELEMENT_0330, elementen.get(Lo3ElementEnum.ELEMENT_0330));

                filtered.put(Lo3ElementEnum.ELEMENT_0910, elementen.get(Lo3ElementEnum.ELEMENT_0910));
                filtered.put(Lo3ElementEnum.ELEMENT_0920, elementen.get(Lo3ElementEnum.ELEMENT_0920));

                filtered.put(Lo3ElementEnum.ELEMENT_7010, elementen.get(Lo3ElementEnum.ELEMENT_7010));

                filtered.put(Lo3ElementEnum.ELEMENT_8310, elementen.get(Lo3ElementEnum.ELEMENT_8310));
                filtered.put(Lo3ElementEnum.ELEMENT_8320, elementen.get(Lo3ElementEnum.ELEMENT_8320));
                filtered.put(Lo3ElementEnum.ELEMENT_8330, elementen.get(Lo3ElementEnum.ELEMENT_8330));

                filtered.put(Lo3ElementEnum.ELEMENT_8510, elementen.get(Lo3ElementEnum.ELEMENT_8510));

                lo3CategorieWaarde.getElementen().clear();
                lo3CategorieWaarde.getElementen().putAll(filtered);

                this.categorieen.add(lo3CategorieWaarde);
            }
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return Lo3CategorieWaardeUtil.deepCopy(categorieen);
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de categoriewaarden terug.
     * 
     * @return De lijst met categoriewaarden
     */
    public List<Lo3CategorieWaarde> getCategorieen() {
        return Lo3CategorieWaardeUtil.deepCopy(categorieen);
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Tv01Bericht)) {
            return false;
        }
        final Tv01Bericht castOther = (Tv01Bericht) other;
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
                .append("categorien", categorieen).toString();
    }

}
