/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus.AbstractCasusTest.Document;

/**
 * Test class om makkelijk de inhoud van de test casus op te nemen
 *
 * @param <T>
 */
public class Lo3TestObject<T extends Lo3CategorieInhoud> {

    private T inhoud;
    private Lo3IndicatieOnjuist onjuist;
    private Lo3Datum ingang;
    private Lo3Datum opneming;
    private Lo3Documentatie akteOfDocument;
    private final Document documentSoort;
    private Lo3Herkomst lo3Herkomst;

    /**
     * @param documentSoort
     *            mag null zijn
     */
    public Lo3TestObject(final Document documentSoort) {
        super();
        this.documentSoort = documentSoort;
    }

    protected void vul(
        final T inhoud,
        final Lo3IndicatieOnjuist onjuist,
        final Integer ingangsdatumGeldigheid,
        final Integer datumOpneming,
        final Integer docId,
        final Lo3Herkomst lo3Herkomst)
    {
        this.inhoud = inhoud;
        this.onjuist = onjuist;
        this.ingang = ingangsdatumGeldigheid == null ? null : new Lo3Datum(ingangsdatumGeldigheid);
        this.opneming = datumOpneming == null ? null : new Lo3Datum(datumOpneming);
        if (docId != null && Document.AKTE.equals(documentSoort)) {
            this.akteOfDocument = AbstractCasusTest.buildLo3Akte(docId);
        } else if (docId != null && Document.DOCUMENT.equals(documentSoort)) {
            this.akteOfDocument = AbstractCasusTest.buildLo3Document(docId, datumOpneming, "d" + docId);
        }
        this.lo3Herkomst = lo3Herkomst;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return inhoud
     */
    public T getInhoud() {
        return inhoud;
    }

    /**
     * Geef de waarde van onjuist.
     *
     * @return onjuist
     */
    public Lo3IndicatieOnjuist getOnjuist() {
        return onjuist;
    }

    /**
     * Geef de waarde van ingang.
     *
     * @return ingang
     */
    public Lo3Datum getIngang() {
        return ingang;
    }

    /**
     * Geef de waarde van opneming.
     *
     * @return opneming
     */
    public Lo3Datum getOpneming() {
        return opneming;
    }

    /**
     * Geef de waarde van akte.
     *
     * @return akte
     */
    public Lo3Documentatie getAkte() {
        return akteOfDocument;
    }

    /**
     * Geef de waarde van historie.
     *
     * @return historie
     */
    public Lo3Historie getHistorie() {
        return new Lo3Historie(onjuist, ingang, opneming);
    }

    /**
     * Geef de waarde van lo3 herkomst.
     *
     * @return lo3 herkomst
     */
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }
}
