/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

/**
 * Een specialisatie van Java type voor gebruik bij generatie gap generaties.
 **/
public class GenerationGapJavaType extends JavaType {

    private final JavaType basisType;

    public GenerationGapJavaType(final JavaType javaType, final JavaType basisType) {
        super(javaType.getNaam(), javaType.getPackagePad());
        this.basisType = basisType;
    }

    public JavaType getBasisType() {
        return basisType;
    }
}
