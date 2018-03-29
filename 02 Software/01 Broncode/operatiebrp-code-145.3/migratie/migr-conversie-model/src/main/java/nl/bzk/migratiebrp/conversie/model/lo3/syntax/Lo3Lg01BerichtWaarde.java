/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import java.util.List;
import java.util.Objects;

/**
 * Class welke een lijst van {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde} en optioneel de
 * headers van een Lg01 bericht bevat.
 */
public final class Lo3Lg01BerichtWaarde {

    private static final String LEEG_ANUMMER = "0000000000";

    private List<Lo3CategorieWaarde> lo3CategorieWaardeList;
    private String anummer;
    private String oudAnummer;

    /**
     * Constructor waarin alleen de categoriewaarden wordt ingevuld.
     * @param lo3CategorieWaardeList lijst met daar in de waarden per categorie.
     */
    public Lo3Lg01BerichtWaarde(final List<Lo3CategorieWaarde> lo3CategorieWaardeList) {
        this(lo3CategorieWaardeList, null, null);
    }

    /**
     * Constructor waarin niet alleen categorie waarden wordt ingevuld, maar ook de header informatie van een Lg01
     * bericht.
     * @param lo3CategorieWaardeList lijst met daar in de waarden per categorie.
     * @param anummer het anummer van de persoonslijst
     * @param oudAnummer het oude anummer bij een anummer wijziging.
     */
    public Lo3Lg01BerichtWaarde(
            final List<Lo3CategorieWaarde> lo3CategorieWaardeList,
            final String anummer,
            final String oudAnummer) {
        this.lo3CategorieWaardeList = lo3CategorieWaardeList;
        this.anummer = anummer;
        this.oudAnummer = oudAnummer;
    }

    /**
     * Geef de waarde van lo3 categorie waarde list.
     * @return lo3 categorie waarde list
     */
    public List<Lo3CategorieWaarde> getLo3CategorieWaardeList() {
        return lo3CategorieWaardeList;
    }

    /**
     * Geef de waarde van anummer.
     * @return anummer
     */
    public String getAnummer() {
        return anummer;
    }

    /**
     * Geef de waarde van oud anummer.
     * @return oud anummer
     */
    public String getOudAnummer() {
        return oudAnummer;
    }

    /**
     * Geef de anummer wijziging.
     * @return anummer wijziging
     */
    public boolean isAnummerWijziging() {
        return !Objects.equals(oudAnummer, LEEG_ANUMMER) && !Objects.equals(anummer, oudAnummer);
    }
}
