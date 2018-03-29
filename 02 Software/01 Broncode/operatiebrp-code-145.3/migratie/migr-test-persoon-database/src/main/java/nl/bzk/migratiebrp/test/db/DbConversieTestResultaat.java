/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementMap;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;

/**
 * Test rsultaten voor de conversie software en test SQL-scripts op de DB.
 */
public final class DbConversieTestResultaat extends TestResultaat {

    @ElementList(name = "conversieResultaten", entry = "conversieResultaat", type = DbConversieTestConversieResultaat.class, required = false)
    private final List<DbConversieTestConversieResultaat> conversieResultaten = new LinkedList<>();

    @ElementMap(name = "sqlControles", entry = "sqlControle", key = "filename", keyType = String.class, valueType = TestStap.class, attribute = false,
            inline = false, required = false)
    private Map<String, TestStap> sqlControles;

    @Attribute(name = "aantalResultaten", required = false)
    private int aantalResultaten;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     */
    protected DbConversieTestResultaat(
            @Attribute(name = "thema", required = false) final String thema,
            @Attribute(name = "naam", required = false) final String naam) {
        super(thema, naam);
        aantalResultaten = 1;
    }

    /**
     * Geef de waarde van sql controles.
     * @return sql controles
     */
    public Map<String, TestStap> getSqlControles() {
        return sqlControles;
    }

    /**
     * Sets the sql controles.
     * @param sqlControles the sql controles
     */
    public void setSqlControles(final Map<String, TestStap> sqlControles) {
        this.sqlControles = sqlControles;
    }

    /**
     * Voegt een conversie resultaat toe aan de bestaande set.
     * @param conversieResult nieuw conversieresultaat.
     */
    public void addConversieResultaat(final DbConversieTestConversieResultaat conversieResult) {
        conversieResultaten.add(conversieResult);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        boolean result = true;
        for (final DbConversieTestConversieResultaat conversieResultaat : conversieResultaten) {
            result &= conversieResultaat.isSucces();
        }
        if (sqlControles != null) {
            for (final TestStap testStap : sqlControles.values()) {
                result &= TestResultaat.isSucces(testStap);
            }
        }
        return result;
    }

    /**
     * Geef de waarde van aantal resultaten.
     * @return aantal resultaten
     */
    public int getAantalResultaten() {
        return aantalResultaten;
    }

    /**
     * Zet de waarde van aantal resultaten.
     * @param aantalResultaten aantal resultaten
     */
    public void setAantalResultaten(final int aantalResultaten) {
        this.aantalResultaten = aantalResultaten;
    }
}
