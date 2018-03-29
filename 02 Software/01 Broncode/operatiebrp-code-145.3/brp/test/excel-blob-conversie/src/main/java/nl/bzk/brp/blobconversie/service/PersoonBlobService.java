/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie.service;


import nl.bzk.algemeenbrp.services.blobber.BlobException;

/**
 * Interface voor het volledig opnieuw blobben van de persoon en zijn afnemerindicaties.
 */
@FunctionalInterface
public interface PersoonBlobService {

    /**
     * Maak een persoon en afnemerindicatie blob obv het genormaliseerde databasemodel.
     * @param technischId De technisch id waarvan de persoon 'afdruk' moet worden gemaakt.
     * @throws BlobException als de blob niet gemaakt kan worden
     */
    void blobify(Integer technischId) throws BlobException;

}
