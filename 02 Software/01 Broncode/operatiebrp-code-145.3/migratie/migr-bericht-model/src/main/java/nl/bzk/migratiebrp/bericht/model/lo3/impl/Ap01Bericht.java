/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Ap01.
 */
public final class Ap01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING);

    private List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

    /**
     * Constructor.
     */
    public Ap01Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Ap01", "uc1003-plaatsen");
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
    protected void parseCategorieen(final List<Lo3CategorieWaarde> lo3Categorieen) throws BerichtInhoudException {
        categorieen = lo3Categorieen;
        if (!PersoonsidentificatieValidator.valideerActueel(lo3Categorieen) && !PersoonsidentificatieValidator.valideerHistorisch(lo3Categorieen)) {
            throw new BerichtInhoudException(
                    "Bericht bevat geen van de verplichte zoekcriteria (01.01.10, 01.01.20, 01.02.40, 08.11.60, 51.01.10, 51.01.20 of 51.02.40).");
        }
    }

    @Override
    public List<Lo3CategorieWaarde> formatInhoud() {
        return categorieen;
    }

    /**
     * Geef de categorieen.
     * @return categorieen
     */
    public List<Lo3CategorieWaarde> getCategorieen() {
        return categorieen;
    }

    /**
     * Zet de categorieen.
     * @param categorieen categorieen
     */
    public void setCategorieen(final List<Lo3CategorieWaarde> categorieen) {
        this.categorieen = categorieen;
    }

    private Lo3CategorieWaarde getCategorie(final Lo3CategorieEnum gewildeCategorie) {
        for (final Lo3CategorieWaarde categorie : categorieen) {
            if (categorie.getCategorie().equals(gewildeCategorie)) {
                return categorie;
            }
        }

        return new Lo3CategorieWaarde(gewildeCategorie, -1, -1);
    }

    /**
     * Geef de waarde van actueel anummer.
     * @return actueel a-nummer (01.01.10)
     */
    public String getActueelAnummer() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_01).getElement(Lo3ElementEnum.ELEMENT_0110);
    }

    /**
     * Geef de waarde van actueel burgerservicenummer.
     * @return actueel burgerservicenummer (01.01.20)
     */
    public String getActueelBurgerservicenummer() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_01).getElement(Lo3ElementEnum.ELEMENT_0120);
    }

    /**
     * Geef de waarde van actuele geslachtsnaam.
     * @return actuele geslachtsnaam (01.02.40)
     */
    public String getActueleGeslachtsnaam() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_01).getElement(Lo3ElementEnum.ELEMENT_0240);
    }

    /**
     * Geef de waarde van actuele postcode.
     * @return actuele postcode (08.11.60)
     */
    public String getActuelePostcode() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_08).getElement(Lo3ElementEnum.ELEMENT_1160);
    }

    /**
     * Geef de waarde van historisch anummer.
     * @return historisch a-nummer (51.01.10)
     */
    public String getHistorischAnummer() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_51).getElement(Lo3ElementEnum.ELEMENT_0110);
    }

    /**
     * Geef de waarde van historisch burgerservicenummer.
     * @return historisch burgerservicenummer (51.01.20)
     */
    public String getHistorischBurgerservicenummer() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_51).getElement(Lo3ElementEnum.ELEMENT_0120);
    }

    /**
     * Geef de waarde van historische geslachtsnaam.
     * @return historische geslachtsnaam (51.02.40)
     */
    public String getHistorischeGeslachtsnaam() {
        return getCategorie(Lo3CategorieEnum.CATEGORIE_51).getElement(Lo3ElementEnum.ELEMENT_0240);
    }

    /**
     * Bepaal of het bericht actuele zoekgegevens bevat (zie LO III.1.5.2).
     * @return true, als 01.01.10 of 01.01.20 of 01.02.40 of 08.11.60 voorkomt in het bericht
     */
    public boolean bevatActueleZoekGegevens() {
        return PersoonsidentificatieValidator.valideerActueel(getCategorieen());
    }

    /**
     * Bepaal of het bericht historische zoekgegevens bevat (zie LO III.1.5.2).
     * @return true, als 51.01.10 of 51.01.20 of 51.02.40 voorkomt in het bericht
     */
    public boolean bevatHistorischeZoekGegevens() {
        return PersoonsidentificatieValidator.valideerHistorisch(getCategorieen());
    }
}
