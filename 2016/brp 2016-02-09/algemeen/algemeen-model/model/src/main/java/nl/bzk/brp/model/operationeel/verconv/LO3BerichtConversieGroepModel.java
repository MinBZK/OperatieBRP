/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3FoutcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3VerwerkingsmeldingAttribuut;
import nl.bzk.brp.model.logisch.verconv.LO3BerichtConversieGroep;


/**
 *
 *
 */
@Embeddable
public class LO3BerichtConversieGroepModel extends AbstractLO3BerichtConversieGroepModel implements
    LO3BerichtConversieGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected LO3BerichtConversieGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param tijdstipConversie  tijdstipConversie van Conversie.
     * @param foutcode           foutcode van Conversie.
     * @param verwerkingsmelding verwerkingsmelding van Conversie.
     */
    public LO3BerichtConversieGroepModel(final DatumTijdAttribuut tijdstipConversie,
        final LO3FoutcodeAttribuut foutcode, final LO3VerwerkingsmeldingAttribuut verwerkingsmelding)
    {
        super(tijdstipConversie, foutcode, verwerkingsmelding);
    }

}
