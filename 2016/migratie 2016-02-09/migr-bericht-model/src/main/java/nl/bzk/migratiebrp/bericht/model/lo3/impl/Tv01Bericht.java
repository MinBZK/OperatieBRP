/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

/**
 * Tv01.
 */
public final class Tv01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING);

    private List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

    /**
     * Constructor.
     */
    public Tv01Bericht() {
        super(HEADER, "Tv01", null);
    }

    /**
     * Convenient constructor.
     *
     * @param categorieen
     *            De categorieën voor op het Tv01 bericht.
     */
    public Tv01Bericht(final List<Lo3CategorieWaarde> categorieen) {
        this();
        this.categorieen = categorieen;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return getGerelateerdeAnummer(categorieen);
    }

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> lo3Categorieen) throws BerichtInhoudException {
        // 01 Identificatienummers, 02 Naam, 03 Geboorte, 09 Gemeente, 70 Geheim, 83 Procedure, 85 Geldigheid

        categorieen = new ArrayList<>();
        for (final Lo3CategorieWaarde lo3CategorieWaarde : lo3Categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_21.equals(lo3CategorieWaarde.getCategorie())) {

                // alleen de volgende elementen:
                // 01 Identificatienummers, 02 Naam, 03 Geboorte, 09 Gemeente, 70 Geheim, 83 Procedure, 85 Geldigheid
                final Map<Lo3ElementEnum, String> elementen = lo3CategorieWaarde.getElementen();

                final Map<Lo3ElementEnum, String> filtered = new HashMap<>();
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

                categorieen.add(lo3CategorieWaarde);
            }
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return Lo3CategorieWaardeUtil.deepCopy(categorieen);
    }

    /**
     * Geeft de categoriewaarden terug.
     *
     * @return De lijst met categoriewaarden
     */
    public List<Lo3CategorieWaarde> getCategorieen() {
        return Lo3CategorieWaardeUtil.deepCopy(categorieen);
    }
}
