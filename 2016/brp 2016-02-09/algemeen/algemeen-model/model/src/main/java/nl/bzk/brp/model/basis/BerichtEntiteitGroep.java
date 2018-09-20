/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Interface voor alle entiteit groepen in de 'Bericht' model boom. In deze interface worden de bericht specifieke attributen en functies gedefinieerd voor
 * groepen in een bericht. Het gaat hierbij met name om generieke attributen die in een bericht aan elementen kunnen worden toegewezen die groepen
 * representeren in een bericht. Denk hierbij bijvoorbeeld aan een voorkomen id van de groep.
 */
public interface BerichtEntiteitGroep extends Groep, BerichtIdentificeerbaar, MetaIdentificeerbaar {

    /**
     * Getter voor het voorkomen id van de groep; de id van de groep in de database (C/D laag).
     *
     * @return het voorkomen id van de groep.
     */
    String getVoorkomenId();

    /**
     * Setter voor het voorkomen id van de groep; de id van de groep in de database (C/D laag).
     *
     * @param id het voorkomen id van de groep.
     */
    void setVoorkomenId(String id);

}
