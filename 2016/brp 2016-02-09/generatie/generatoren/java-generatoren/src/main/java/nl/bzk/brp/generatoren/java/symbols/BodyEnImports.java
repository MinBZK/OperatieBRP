/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.generatoren.java.model.JavaType;

/**
 * Wrapper klasse voor het resultaat van een methode die een body van een methode opbouwt, waarbij tevens extra imports
 * worden toegevoegd.
 */
public final class BodyEnImports {

    private final String         body;
    private final List<JavaType> extraImports;

    /**
     * Constructor voor deze klasse die direct de waardes initialiseert.
     *
     * @param body de body van een methode.
     * @param extraImports de extra imports gebruikt in de body van een methode.
     */
    public BodyEnImports(final String body, final List<JavaType> extraImports) {
        this.body = body;
        this.extraImports = extraImports;
    }

    /**
     * Retourneert de body van een methode.
     * @return de body van een methode
     */
    public String getBody() {
        return body;
    }

    /**
     * Retourneert de extra imports.
     * @return de extra imports.
     */
    public List<JavaType> getExtraImports() {
        return Collections.unmodifiableList(extraImports);
    }

    /**
     * Retourneert de extra imports.
     * @return de extra imports.
     */
    public JavaType[] getExtraImportsAlsArray() {
        final JavaType[] extraImportsArray = new JavaType[extraImports.size()];
        extraImports.toArray(extraImportsArray);
        return extraImportsArray;
    }

}

