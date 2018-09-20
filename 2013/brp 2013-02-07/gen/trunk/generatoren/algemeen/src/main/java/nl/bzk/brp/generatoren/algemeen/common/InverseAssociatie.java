/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

import java.util.List;

import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Een dto objectje met de informatie over een inverse associatie. Namelijk:
 * - Vanuit welk object type
 * - Met welke ident code (property naam)
 * - Naar welk object type
 */
public class InverseAssociatie {

    private ObjectType vanObjectType;
    private String identCode;
    private List<ObjectType> naarObjectTypen;

    /**
     * Maak een nieuw dto objectje aan.
     *
     * @param vanObjectType van welk object type
     * @param identCode met welke ident code
     * @param naarObjectTypen naar welk object typen
     */
    public InverseAssociatie(final ObjectType vanObjectType,
            final String identCode, final List<ObjectType> naarObjectTypen)
    {
        this.vanObjectType = vanObjectType;
        this.identCode = identCode;
        this.naarObjectTypen = naarObjectTypen;
    }

    public ObjectType getVanObjectType() {
        return vanObjectType;
    }

    public String getIdentCode() {
        return identCode;
    }

    public List<ObjectType> getNaarObjectTypen() {
        return naarObjectTypen;
    }

}
