/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Verblijfstitel;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfstitelGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonVerblijfstitelGroepModel;


/**
 * 1. Vorm van historie: beiden (zowel materi�le als formele historie).
 * Het historiepatroon bij verblijfsrecht is bijzonder. De datum aanvang verblijfsrecht wordt aangeleverd door de IND,
 * en komt logischerwijs overeen met datum aanvang geldigheid.
 * De datum VOORZIEN einde kan in de toekomst liggen, en wijkt derhalve af van een 'normale' datum einde geldigheid, die
 * meestal in het verleden zal liggen.
 * Vanwege aanlevering vanuit migratie (met een andere granulariteit voor historie) kan datum aanvang geldigheid
 * afwijken van de datum aanvang verblijfsrecht.
 *
 *
 *
 */
@Embeddable
public class PersoonVerblijfstitelGroepModel extends AbstractPersoonVerblijfstitelGroepModel implements
        PersoonVerblijfstitelGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonVerblijfstitelGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param verblijfstitel verblijfstitel van Verblijfstitel.
     * @param datumAanvangVerblijfsrecht datumAanvangVerblijfsrecht van Verblijfstitel.
     * @param datumVoorzienEindeVerblijfsrecht datumVoorzienEindeVerblijfsrecht van Verblijfstitel.
     */
    public PersoonVerblijfstitelGroepModel(final Verblijfstitel verblijfstitel, final Datum datumAanvangVerblijfsrecht,
            final Datum datumVoorzienEindeVerblijfsrecht)
    {
        super(verblijfstitel, datumAanvangVerblijfsrecht, datumVoorzienEindeVerblijfsrecht);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerblijfstitelGroep te kopieren groep.
     */
    public PersoonVerblijfstitelGroepModel(final PersoonVerblijfstitelGroep persoonVerblijfstitelGroep) {
        super(persoonVerblijfstitelGroep);
    }

}
