/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GegevenswaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInTerugmeldingHisVolledig;


/**
 * HisVolledig klasse voor Gegeven in terugmelding.
 */
@Entity
@Table(schema = "Kern", name = "GegevenInTerugmelding")
public class GegevenInTerugmeldingHisVolledigImpl extends AbstractGegevenInTerugmeldingHisVolledigImpl implements
    HisVolledigImpl, GegevenInTerugmeldingHisVolledig, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected GegevenInTerugmeldingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmelding      terugmelding van Gegeven in terugmelding.
     * @param gegevenselement   gegevenselement van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde   verwachteWaarde van Gegeven in terugmelding.
     */
    public GegevenInTerugmeldingHisVolledigImpl(final TerugmeldingHisVolledigImpl terugmelding,
        final ElementAttribuut gegevenselement, final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde)
    {
        super(terugmelding, gegevenselement, betwijfeldeWaarde, verwachteWaarde);
    }

}
