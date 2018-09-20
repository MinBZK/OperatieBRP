/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.KenmerkTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TekstTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmeldingAttribuut;
import nl.bzk.brp.model.logisch.kern.TerugmeldingStandaardGroep;


/**
 *
 *
 */
@Embeddable
public class TerugmeldingStandaardGroepModel extends AbstractTerugmeldingStandaardGroepModel implements
    TerugmeldingStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected TerugmeldingStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param onderzoek             onderzoek van Standaard.
     * @param status                status van Standaard.
     * @param toelichting           toelichting van Standaard.
     * @param kenmerkMeldendePartij kenmerkMeldendePartij van Standaard.
     */
    public TerugmeldingStandaardGroepModel(final OnderzoekModel onderzoek, final StatusTerugmeldingAttribuut status,
        final TekstTerugmeldingAttribuut toelichting, final KenmerkTerugmeldingAttribuut kenmerkMeldendePartij)
    {
        super(onderzoek, status, toelichting, kenmerkMeldendePartij);
    }

}
