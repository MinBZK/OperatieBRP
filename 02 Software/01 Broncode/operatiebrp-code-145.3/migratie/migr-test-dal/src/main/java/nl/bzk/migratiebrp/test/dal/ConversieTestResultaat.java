/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

import nl.bzk.migratiebrp.test.common.resultaat.TestStap;

/**
 * Interface voor resultaten van tests mbt conversie.
 */
public interface ConversieTestResultaat {

    /**
     * Geef de waarde van syntax precondities.
     * @return syntax precondities
     */
    TestStap getSyntaxPrecondities();

    /**
     * Zet de waarde van syntax precondities.
     * @param syntaxPrecondities syntax precondities
     */
    void setSyntaxPrecondities(final TestStap syntaxPrecondities);

    /**
     * Geef de waarde van lo3 naar brp.
     * @return lo3 naar brp
     */
    TestStap getLo3NaarBrp();

    /**
     * Zet de waarde van lo3 naar brp.
     * @param lo3NaarBrp lo3 naar brp
     */
    void setLo3NaarBrp(final TestStap lo3NaarBrp);

    /**
     * Geef de waarde van opslaan brp.
     * @return opslaan brp
     */
    TestStap getOpslaanBrp();

    /**
     * Zet de waarde van opslaan brp.
     * @param opslaanBrp opslaan brp
     */
    void setOpslaanBrp(final TestStap opslaanBrp);

    /**
     * Geef de waarde van lezen brp.
     * @return lezen brp
     */
    TestStap getLezenBrp();

    /**
     * Zet de waarde van lezen brp.
     * @param lezenBrp lezen brp
     */
    void setLezenBrp(final TestStap lezenBrp);

    /**
     * Geef de waarde van brp naar lo3.
     * @return brp naar lo3
     */
    TestStap getBrpNaarLo3();

    /**
     * Zet de waarde van brp naar lo3.
     * @param brpNaarLo3 brp naar lo3
     */
    void setBrpNaarLo3(final TestStap brpNaarLo3);

    /**
     * Geef de waarde van brp naar lo3 verschil analyse.
     * @return brp naar lo3 verschil analyse
     */
    TestStap getBrpNaarLo3VerschilAnalyse();

    /**
     * Zet de waarde van brp naar lo3 verschil analyse.
     * @param brpNaarLo3VerschilAnalyse brp naar lo3 verschil analyse
     */
    void setBrpNaarLo3VerschilAnalyse(final TestStap brpNaarLo3VerschilAnalyse);

    /**
     * Geef de waarde van conversie log.
     * @return conversie log
     */
    TestStap getConversieLog();

    /**
     * Zet de waarde van conversie log.
     * @param conversieLog conversie log
     */
    void setConversieLog(final TestStap conversieLog);

    /**
     * Geef de waarde van het kruimelpad.
     * @return kruimelpad
     */
    TestStap getKruimelpad();

    /**
     * Zet de waarde van het kruimelpad.
     * @param kruimelpad het kruimelpad
     */
    void setKruimelpad(final TestStap kruimelpad);
}
