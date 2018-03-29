/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Xq01: Ad hoc adresvraag.
 */
public class Xq01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING, Lo3HeaderVeld.AANTAL,
            Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, Lo3HeaderVeld.ADRESFUNCTIE, Lo3HeaderVeld.IDENTIFICATIE);

    private List<Lo3CategorieWaarde> categorieen = Collections.emptyList();

    /**
     * Constructor.
     */
    public Xq01Bericht() {
        super(HEADER, Lo3SyntaxControle.LENGTE_NUL_TOEGESTAAN, "Xq01", "uc1004-adres");
    }

    @Override
    protected List<String> getGerelateerdeAnummers() {
        return getGerelateerdeAnummer(categorieen);
    }

    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> lo3Categorieen) throws BerichtInhoudException {
        if ((isPersoonIdentificatie() && PersoonsidentificatieValidator.valideerActueel(lo3Categorieen))
                || (isAdresIdentificatie() && AdresidentificatieValidator.valideer(lo3Categorieen))) {
            this.categorieen = lo3Categorieen;
        } else {
            throw new BerichtInhoudException("Geen persoons- of adresidentificerende rubriek aanwezig.");
        }

        if (!gevraagdeRubriekenBevatANummer()) {
            throw new BerichtInhoudException("Header gevraagde rubrieken bevat de verplichte rubriek 01.01.10 niet.");
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return getCategorieen();
    }

    /**
     * Geef de categorieen.
     * @return categorieen
     */
    public List<Lo3CategorieWaarde> getCategorieen() {
        return Collections.unmodifiableList(categorieen);
    }

    /**
     * Zet de categorieen
     * @param categorieen categorieen
     */
    public void setCategorieen(List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        parseCategorieen(new ArrayList<>(categorieen));
    }

    private boolean isPersoonIdentificatie() {
        return IdentificatieSoort.P.name().equals(getHeaderWaarde(Lo3HeaderVeld.IDENTIFICATIE));
    }

    private boolean isAdresIdentificatie() {
        return IdentificatieSoort.A.name().equals(getHeaderWaarde(Lo3HeaderVeld.IDENTIFICATIE));
    }

    private boolean gevraagdeRubriekenBevatANummer() {
        return getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN).contains("010110");
    }

    private enum IdentificatieSoort {
        A,
        P
    }
}
