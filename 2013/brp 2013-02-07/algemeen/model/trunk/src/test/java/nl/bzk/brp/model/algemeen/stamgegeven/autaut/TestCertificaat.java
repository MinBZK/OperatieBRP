/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;

/**
*
* TempCertificaat ten behoeve van unnitest.
*
*/
public class TestCertificaat extends Certificaat {
    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param subject subject van Certificaat.
     * @param serial serial van Certificaat.
     * @param signature signature van Certificaat.
     */
    public TestCertificaat(final Certificaatsubject subject, final Certificaatserial serial, final PubliekeSleutel signature)
    {
        super(subject, serial, signature);
    }
}
