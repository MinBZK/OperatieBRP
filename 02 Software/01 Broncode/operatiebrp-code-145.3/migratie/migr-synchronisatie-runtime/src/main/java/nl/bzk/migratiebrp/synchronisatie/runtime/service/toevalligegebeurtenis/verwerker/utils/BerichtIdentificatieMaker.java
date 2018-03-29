/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils;

/**
 * Verzorgt de aanmaak van de correcte id's voor de toevalligegebeurtenisverzoek berichten.
 */
public class BerichtIdentificatieMaker {

    /**
     * Communicatie identifier voor bron.
     */
    protected static final String COMMUNICATIE_TYPE_BRON = "Bron";

    /**
     * Communicatie identifier voor identificatie.
     */
    protected static final String COMMUNICATIE_TYPE_IDENTIFICATIE = "Identificatie";

    /**
     * Volgnummer voor bron communicatieId.
     */
    private int bronIdVolgnummer;

    /**
     * Volgnummer voor bron identificatieId.
     */
    private int identificatieIdVolgnummer;

    /**
     * Constructor.
     */
    public BerichtIdentificatieMaker() {
        bronIdVolgnummer = 1;
        identificatieIdVolgnummer = 1;
    }

    /**
     * Volgend IdentificatieId.
     * @return IdentificatieId
     */
    public final String volgendIdentificatieId() {
        final String resultaat = COMMUNICATIE_TYPE_IDENTIFICATIE + identificatieIdVolgnummer;
        identificatieIdVolgnummer++;
        return resultaat;
    }

    /**
     * Volgend bronId.
     * @return bronId
     */
    public final String volgendBronId() {
        final String resultaat = COMMUNICATIE_TYPE_BRON + bronIdVolgnummer;
        bronIdVolgnummer++;
        return resultaat;
    }
}
