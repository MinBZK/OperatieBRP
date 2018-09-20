/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractBijhoudingsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategoriePersonen;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;


/**
 * De aan een Partij - of aan Partijen van een soort - uitgereikte autorisatie om bijhoudingen te doen.
 *
 * Een bijhouding in de BRP vindt plaats doordat een Partij expliciet geautoriseerd is om een bepaalde soort bijhouding
 * te doen, voor een bepaalde soort bijhoudingspopulatie. De vastlegging hiervan gebeurd middels de
 * Bijhoudingsautorisatie.
 * De autorisatie kan zijn doordat de Partij zelf geautoriseerd is, doordat de Partij van een Soort partij is die
 * geautoriseerd is. Hierbij dient de Autoriseerderende Partij zelf ook geautoriseerd te zijn.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "Bijhautorisatie")
public class Bijhoudingsautorisatie extends AbstractBijhoudingsautorisatie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Bijhoudingsautorisatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param bijhoudingsautorisatiebesluit bijhoudingsautorisatiebesluit van Bijhoudingsautorisatie.
     * @param soortBevoegdheid soortBevoegdheid van Bijhoudingsautorisatie.
     * @param geautoriseerdeSoortPartij geautoriseerdeSoortPartij van Bijhoudingsautorisatie.
     * @param geautoriseerdePartij geautoriseerdePartij van Bijhoudingsautorisatie.
     * @param toestand toestand van Bijhoudingsautorisatie.
     * @param categoriePersonen categoriePersonen van Bijhoudingsautorisatie.
     * @param omschrijving omschrijving van Bijhoudingsautorisatie.
     * @param datumIngang datumIngang van Bijhoudingsautorisatie.
     * @param datumEinde datumEinde van Bijhoudingsautorisatie.
     * @param bijhoudingsautorisatieStatusHis bijhoudingsautorisatieStatusHis van Bijhoudingsautorisatie.
     */
    protected Bijhoudingsautorisatie(final Autorisatiebesluit bijhoudingsautorisatiebesluit,
            // CHECKSTYLE-BRP:ON - MAX PARAMS
            final SoortBevoegdheid soortBevoegdheid, final SoortPartij geautoriseerdeSoortPartij,
            final Partij geautoriseerdePartij, final Toestand toestand, final CategoriePersonen categoriePersonen,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumIngang, final Datum datumEinde,
            final StatusHistorie bijhoudingsautorisatieStatusHis)
    {
        super(bijhoudingsautorisatiebesluit, soortBevoegdheid, geautoriseerdeSoortPartij, geautoriseerdePartij,
                toestand, categoriePersonen, omschrijving, datumIngang, datumEinde, bijhoudingsautorisatieStatusHis);
    }

}
