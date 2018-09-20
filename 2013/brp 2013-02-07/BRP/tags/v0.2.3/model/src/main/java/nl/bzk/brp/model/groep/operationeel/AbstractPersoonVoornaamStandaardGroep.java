/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonVoornaamStandaardGroepBasis;


/**
 * Implementatie voor standaard groep van persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonVoornaamStandaardGroep extends AbstractGroep implements
        PersoonVoornaamStandaardGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naam"))
    private Voornaam voornaam;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonVoornaamStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonVoornaamStandaardGroepBasis PersoonVoornaamStandaardGroepBasis
     */
    protected AbstractPersoonVoornaamStandaardGroep(
            final PersoonVoornaamStandaardGroepBasis persoonVoornaamStandaardGroepBasis)
    {
        voornaam = persoonVoornaamStandaardGroepBasis.getVoornaam();
    }

    @Override
    public Voornaam getVoornaam() {
        return voornaam;
    }

}
