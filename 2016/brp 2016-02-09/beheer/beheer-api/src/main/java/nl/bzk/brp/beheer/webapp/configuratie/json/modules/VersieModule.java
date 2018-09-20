/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.versie.Versie;

/**
 * Versie module.
 */
public class VersieModule extends SimpleModule {

    /** artifact. */
    public static final String ARTIFACT = "artifact";
    /** build. */
    public static final String BUILD = "build";
    /** group. */
    public static final String GROUP = "group";
    /** name. */
    public static final String NAME = "name";
    /** revision. */
    public static final String REVISION = "revision";
    /** versie. */
    public static final String VERSIE = "versie";
    /** componenten. */
    public static final String COMPONENTEN = "componenten";

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public VersieModule() {
        addSerializer(Versie.class, new VersieSerializer());
    }

    @Override
    public final String getModuleName() {
        return "VersieModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
