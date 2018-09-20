/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import nl.bzk.migratiebrp.test.dal.AbstractConversieTestResultaat;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Resultaat class voor de conversie tests waarbij een SQL-statement als controle gebruikt kan worden.
 */
@Root
public final class DbConversieTestConversieResultaat extends AbstractConversieTestResultaat {

    @Attribute(name = "id", required = false)
    private final int id;

    /**
     * Constructor.
     *
     * @param id
     *            ID van het testgeval
     */
    public DbConversieTestConversieResultaat(final int id) {
        super(null, null);
        this.id = id;
    }

    /**
     * Geeft het ID terug van de testcase.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Geef de succes.
     *
     * @return succes
     */
    @Override
    public boolean isSucces() {
        return isSucces(
            getSyntaxPrecondities(),
            getLo3NaarBrp(),
            getConversieLog(),
            getOpslaanBrp(),
            getLezenBrp(),
            getBrpNaarLo3(),
            getBrpNaarLo3VerschilAnalyse(),
            getKruimelpad());
    }

}
