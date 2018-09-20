/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonVerblijfsrechtGroepModel extends AbstractPersoonVerblijfsrechtGroepModel implements
    PersoonVerblijfsrechtGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonVerblijfsrechtGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param aanduidingVerblijfsrecht         aanduidingVerblijfsrecht van Verblijfsrecht.
     * @param datumAanvangVerblijfsrecht       datumAanvangVerblijfsrecht van Verblijfsrecht.
     * @param datumMededelingVerblijfsrecht    datumMededelingVerblijfsrecht van Verblijfsrecht.
     * @param datumVoorzienEindeVerblijfsrecht datumVoorzienEindeVerblijfsrecht van Verblijfsrecht.
     */
    public PersoonVerblijfsrechtGroepModel(final AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht)
    {
        super(aanduidingVerblijfsrecht, datumAanvangVerblijfsrecht, datumMededelingVerblijfsrecht, datumVoorzienEindeVerblijfsrecht);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerblijfsrechtGroep te kopieren groep.
     */
    public PersoonVerblijfsrechtGroepModel(final PersoonVerblijfsrechtGroep persoonVerblijfsrechtGroep) {
        super(persoonVerblijfsrechtGroep);
    }

}
