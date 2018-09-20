/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Voornaam;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonVoornaamStandaardGroepBasis;


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
     * @param persoonVoornaamStandaardGroepBasis
     *         PersoonVoornaamStandaardGroepBasis
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


    public void setVoornaam(final Voornaam voornaam) {
        this.voornaam = voornaam;
    }

}
