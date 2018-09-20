/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

/**
 * Service voor ObjectSleutels van personen.
 * @brp.bedrijfsregel BRBV0004
 */
public interface ObjectSleutelService {

    /**
     *
     * Genereer een objectsleutel string op basis van een persoon en partij.
     * Het tijdstip van uitgifte wordt automatisch toegevoegd.
     *
     * @param persoonId id van persoon.
     * @param partijCode code van de partij die de persoon bevraagt.
     * @return een encrypted objectsleutel.
     */
    String genereerObjectSleutelString(final int persoonId, final int partijCode);

    /**
     * Reconstrueer het persoonId op basis van een objectsleutel-string.
     *
     * @param objectSleutelString de versleutelde objectsleutel-string uit het bericht.
     * @param partijCode de partij die de objectsleutel tracht te gebruiken.
     * @throws OngeldigeObjectSleutelExceptie een exceptie als er iets niet goed is aan de objectsleutel. Meerdere
     * subclasses van deze exceptie bestaan om de specifieke reden aan te geven van de invaliditeit van
     * de sleutel.
     *
     * @return het onversleutelde persoonId
     */
    Integer bepaalPersoonId(final String objectSleutelString, final int partijCode)
        throws OngeldigeObjectSleutelExceptie;
}
