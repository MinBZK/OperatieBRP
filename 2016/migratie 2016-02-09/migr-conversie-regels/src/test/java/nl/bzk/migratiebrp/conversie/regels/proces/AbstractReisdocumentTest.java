/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;

/**
 * Abstract class voor de tests voor reisdocument zodat alle tests dezelfde waardes kunnen gebruiken
 */
public abstract class AbstractReisdocumentTest extends AbstractLoggingTest {

    // Groep 35
    protected static final String SOORT_REISDOCUMENT = "P";
    protected static final String NUMMER_NL_REISDOCUMENT = "P12345678";
    protected static final int DATUM_UITGIFTE_NL_REISDOCUMENT = 20120101;
    protected static final String AUTORITEIT_VAN_AFGIFTE = "123456";
    protected static final int DATUM_EINDE_GELDIGHEID_NL_REISDOCUMENT = 20170101;
    protected static final int DATUM_INHOUDING_OF_VERMISSING = 20140101;
    protected static final char AANDUIDING_INHOUDING_OF_VERMISSING = 'I';

    // Groep 36
    protected static final Lo3Signalering LO3_SIGNALERING = Lo3SignaleringEnum.SIGNALERING.asElement();

    // Groep 85/86
    protected static final int DATUM_INGANG_GELDIGHEID = 20120101;
    protected static final int DATUM_OPNEMING = 20120102;
}
