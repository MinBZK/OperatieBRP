/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.output;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Wrapper class om een List van Lo3CategorieWaarde-n te kunnen lezen en schrijven.
 */
@Root(name = "categorieen")
public final class Lo3CategorieWaarden {
    private final List<Lo3CategorieWaarde> categorieen;

    /**
     * Default constructor.
     */
    public Lo3CategorieWaarden() {
        categorieen = new ArrayList<>();
    }

    /**
     * Constructor.
     * 
     * @param categorieen
     *            categorieen
     */
    public Lo3CategorieWaarden(
        @ElementList(inline = true, entry = "categorie", type = Lo3CategorieWaarde.class, required = false) final List<Lo3CategorieWaarde> categorieen)
    {
        this.categorieen = categorieen;
    }

    /**
     * Geef de waarde van categorieen.
     *
     * @return categorieen
     */
    @ElementList(inline = true, entry = "categorie", type = Lo3CategorieWaarde.class, required = false)
    public List<Lo3CategorieWaarde> getCategorieen() {
        return categorieen;
    }
}
