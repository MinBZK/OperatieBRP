/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

/**
 * De Class Constants.
 */
public final class Constants {

    /** De Constante DEFAULT_AMBTENAAR_COUNT. */
    public final static int    DEFAULT_AMBTENAAR_COUNT = 26 * 26;

    /** De Constante BASE. */
    public final static String BASE                    =
                                                           "/home/bhuism/workspaceBRP/whiteboxvulling/src/main/resources/db";

    /** De Constante PSQLDIR. */
    public final static String PSQLDIR                 = "/usr/bin/";

    /** De Constante SOURCE_XLS_FILE. */
    public final static String SOURCE_XLS_FILE         = "src/main/resources/scenarios/Gegenereerde testpersonen.xls";

    /** De Constante TARGET_DIR. */
    public static final String TARGET_DIR              = "target/";

    /** De Constante TEMPDIR. */
    public static final String TEMPDIR                 = "/tmp";

    /** De Constante WHITEBOXFILENAME. */
    public final static String WHITEBOX_FILE_NAME      = "whiteboxfiller.sql";
}
