/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;

/**
 * CRUD functionaliteit voor Blokkering entiteiten.
 * 
 */
public interface BlokkeringRepository {

    /**
     * Blokkeert een persoonslijst en voegt hiervoor een rij toe aan de mig_blokkering tabel.
     * 
     * @param blokkering
     *            De aangemaakte blokkering die in de database moet worden opgeslagen.
     * @return De opgeslagen blokkering.
     */
    Blokkering blokkeerPersoonslijst(final Blokkering blokkering);

    /**
     * Vraagt de info van de blokkering op.
     * 
     * @param aNummer
     *            Het aNummer waarvoor de info van de blokkering wordt opgevraagd.
     * @return De blokkering die hoort bij het opgegeven aNummer.
     */
    Blokkering statusBlokkering(final Long aNummer);

    /**
     * Deblokkeert de persoonslijst.
     * 
     * @param teVerwijderenBlokkering
     *            De te verwijderen blokkering.
     */
    void deblokkeerPersoonslijst(final Blokkering teVerwijderenBlokkering);
}
