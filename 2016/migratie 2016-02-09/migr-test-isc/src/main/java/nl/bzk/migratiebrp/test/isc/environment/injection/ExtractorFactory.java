/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

/**
 * Extractor factory.
 */
public final class ExtractorFactory {

    /**
     * Geef een extractor.
     *
     * @param type
     *            type
     * @return extractor, null als type onbekend is
     */
    public Extractor getExtractor(final String type) {
        final Extractor resultaat;

        switch (type.toLowerCase()) {
            case "header":
                resultaat = new ExtractorHeader();
                break;
            case "proces":
                resultaat = new ExtractorProces();
                break;
            case "file":
                resultaat = new ExtractorFile();
                break;
            case "static":
                resultaat = new ExtractorStatic();
                break;
            case "test":
                resultaat = new ExtractorTest();
                break;
            case "vospg":
                resultaat = new ExtractorVospg();
                break;
            case "xpath":
                resultaat = new ExtractorXpath();
                break;
            default:
                resultaat = null;
        }

        return resultaat;
    }
}
