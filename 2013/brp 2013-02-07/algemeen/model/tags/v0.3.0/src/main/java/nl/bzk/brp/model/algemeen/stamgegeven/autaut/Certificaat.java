/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractCertificaat;


/**
 * Het Certificaat zoals bedoeld in de standaard X509.3.
 *
 * Certificaten worden gebruikt voor authenticatiedoeleinden.
 *
 * Van Certificaten nemen we een beperkt aantal gegevens op in deze entiteit:
 * Het betreft het subject van het certificaat, dat uitsluitsel geeft over de eigenaar ervan, en het seriel nummer.
 * Overige gegevens van het certificaat, zoals de publieke sleutel, worden hier niet opgenomen: deze worden in de
 * technische laag gebruikt, en staan op dedicated hardware.
 * RvdP 10 oktober 2011.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "Certificaat")
public class Certificaat extends AbstractCertificaat {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Certificaat() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param subject subject van Certificaat.
     * @param serial serial van Certificaat.
     * @param signature signature van Certificaat.
     */
    protected Certificaat(final Certificaatsubject subject, final Certificaatserial serial,
                          final PubliekeSleutel signature)
    {
        super(subject, serial, signature);
    }

}
