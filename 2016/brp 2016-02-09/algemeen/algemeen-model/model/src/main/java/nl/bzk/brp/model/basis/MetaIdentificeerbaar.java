/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Interface die aangeeft dat dit object op Meta niveau identificeerbaar is. Het bevat dus een Meta ID (afkomstig uit het BMR).
 */
public interface MetaIdentificeerbaar {

    /**
     * Retourneert het unieke Meta ID voor dit element.
     *
     * @return een uniek Meta ID.
     */
    Integer getMetaId();

    /**
     * Retourneert of een onderliggend element van dit element identificeerbaar is door de opgegeven Meta ID.
     *
     * @param id de gezochte Meta Id.
     * @return indicatie of onderliggend element identificeerbaar is door de opgegeven Meta ID.
     */
    boolean bevatElementMetMetaId(final Integer id);

}
