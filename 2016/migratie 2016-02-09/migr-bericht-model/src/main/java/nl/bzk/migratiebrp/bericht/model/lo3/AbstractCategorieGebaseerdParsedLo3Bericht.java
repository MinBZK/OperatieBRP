/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

/**
 * Lo3 bericht waarin de bewerking gebaseerd zijn op een lijst van categorieen.
 */
public abstract class AbstractCategorieGebaseerdParsedLo3Bericht extends AbstractParsedLo3Bericht {

    private static final long serialVersionUID = 1L;

    private static final Lo3PersoonslijstFormatter LO3_FORMATTER = new Lo3PersoonslijstFormatter();
    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();

    private List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param header
     *            header velden van dit bericht
     * @param berichtType
     *            berichtnummer van dit bericht
     * @param startCyclus
     *            cyclus die dit bericht kan starten
     */
    protected AbstractCategorieGebaseerdParsedLo3Bericht(final Lo3Header header, final String berichtType, final String startCyclus) {
        super(header, berichtType, startCyclus);
    }

    @Override
    protected final List<String> getGerelateerdeAnummers() {
        return getGerelateerdeAnummer(categorieen);
    }

    @Override
    protected final void parseInhoud(final List<Lo3CategorieWaarde> lo3Categorieen) throws BerichtInhoudException {
        categorieen = lo3Categorieen;
    }

    @Override
    public final List<Lo3CategorieWaarde> formatInhoud() {
        return categorieen;
    }

    /**
     * Geef de categorieen.
     *
     * @return categorieen
     */
    public final List<Lo3CategorieWaarde> getCategorieen() {
        return categorieen;
    }

    /**
     * Zet de categorieen.
     *
     * @param categorieen
     *            categorieen
     */
    public final void setCategorieen(final List<Lo3CategorieWaarde> categorieen) {
        this.categorieen = categorieen;
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
    public final void set(final Lo3CategorieEnum categorie, final Lo3ElementEnum element, final String waarde) {
        if (categorieen == null) {
            categorieen = new ArrayList<>();
        }
        Lo3CategorieWaardeUtil.setElementWaarde(
            categorieen,
            Lo3CategorieEnum.bepaalActueleCategorie(categorie),
            0,
            categorie.isActueel() ? 0 : 1,
            element,
            waarde);
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
    public final String get(final Lo3CategorieEnum categorie, final Lo3ElementEnum element) {
        return Lo3CategorieWaardeUtil.getElementWaarde(
            categorieen,
            Lo3CategorieEnum.bepaalActueleCategorie(categorie),
            0,
            categorie.isActueel() ? 0 : 1,
            element);
    }

    /* ************************************************************************************************************* */

    /**
     * Zet de categorieen obv een lo3 persoonslijst.
     *
     * @param lo3Persoonslijst
     *            lo3 persoonslijst
     */
    public final void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        categorieen = LO3_FORMATTER.format(lo3Persoonslijst);
    }

    /**
     * Geef een lo3 persoonlijst obv de categorieen.
     *
     * @return lo3 persoonslijst
     */
    public final Lo3Persoonslijst getLo3Persoonslijst() {
        return LO3_PARSER.parse(categorieen);
    }
}
