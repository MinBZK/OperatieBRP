/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Populatiecriterium;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.TekstDoelbinding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractDoelbinding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;


/**
 * Het verband tussen enerzijds de welbepaalde, uitdrukkelijk omschreven en gerechtvaardigde taak van een Partij en
 * anderzijds de daarvoor door de BRP aan die Partij te verstrekken gegevens.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "Doelbinding")
public class Doelbinding extends AbstractDoelbinding {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Doelbinding() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param leveringsautorisatiebesluit leveringsautorisatiebesluit van Doelbinding.
     * @param geautoriseerde geautoriseerde van Doelbinding.
     * @param protocolleringsniveau protocolleringsniveau van Doelbinding.
     * @param tekstDoelbinding tekstDoelbinding van Doelbinding.
     * @param populatiecriterium populatiecriterium van Doelbinding.
     * @param indicatieVerstrekkingsbeperkingHonoreren indicatieVerstrekkingsbeperkingHonoreren van Doelbinding.
     * @param doelbindingStatusHis doelbindingStatusHis van Doelbinding.
     */
    protected Doelbinding(final Autorisatiebesluit leveringsautorisatiebesluit, final Partij geautoriseerde,
            final Protocolleringsniveau protocolleringsniveau, final TekstDoelbinding tekstDoelbinding,
            final Populatiecriterium populatiecriterium, final JaNee indicatieVerstrekkingsbeperkingHonoreren,
            final StatusHistorie doelbindingStatusHis)
    {
        super(leveringsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstDoelbinding, populatiecriterium,
                indicatieVerstrekkingsbeperkingHonoreren, doelbindingStatusHis);
    }

}
