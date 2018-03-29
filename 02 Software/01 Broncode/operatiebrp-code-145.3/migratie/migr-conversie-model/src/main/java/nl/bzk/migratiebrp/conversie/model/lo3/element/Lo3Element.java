/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

/**
 * De LO3 elementen van een LO3 categorie. Deze bevat naast de inhoudelijke gegevens ook onderzoeksgegevens.
 */
public interface Lo3Element {

    /**
     * True als het element inhoudelijk is gevuld. Onderzoek maakt geen deel uit van het element en wordt dus niet
     * meegenomen of het element gevuld is of niet.
     * @return true of de waarde van het element gevuld is ongeacht of onderzoek is gevuld.
     */
    boolean isInhoudelijkGevuld();

    /**
     * Geef de waarde van waarde.
     * @return de waarde van dit LO3 element.
     */
    String getWaarde();

    /**
     * Geef de waarde van onderzoek.
     * @return Een onderzoek als dit element in onderzoek staat.
     */
    Lo3Onderzoek getOnderzoek();

    /**
     * Vergelijkt de waarde en return <code>true</code> als deze gelijk is, of waarde en opgegeven element null is. Onderzoek wordt
     * buiten beschouwing gelaten in deze vergelijking.
     * @param element Het element waarmee vegeleken wordt
     * @return <code>true</code> als de waarde gelijk is of waarde en opgegeven element null is.
     */
    boolean equalsWaarde(Lo3Element element);
}
