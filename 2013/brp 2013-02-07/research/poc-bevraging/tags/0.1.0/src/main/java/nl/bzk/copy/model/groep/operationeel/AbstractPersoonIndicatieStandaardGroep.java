/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.attribuuttype.Ja;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonIndicatieStandaardGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortIndicatie;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor de groep ouderlijk gezag van objecttype betrokkenheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIndicatieStandaardGroep extends AbstractGroep implements
        PersoonIndicatieStandaardGroepBasis
{

    @Column(name = "Srt")
    @NotNull
    private SoortIndicatie soort;

    @Column(name = "Waarde")
    @Type(type = "Ja")
    private Ja waarde;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonIndicatieStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonIndicatieStandaardGroepBasis
     *         PersoonIndicatieStandaardGroepBasis
     */
    protected AbstractPersoonIndicatieStandaardGroep(
            final PersoonIndicatieStandaardGroepBasis persoonIndicatieStandaardGroepBasis)
    {
        soort = persoonIndicatieStandaardGroepBasis.getSoort();
        waarde = persoonIndicatieStandaardGroepBasis.getWaarde();
    }

    @Override
    public SoortIndicatie getSoort() {
        return soort;
    }

    @Override
    public Ja getWaarde() {
        return waarde;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }

    public void setWaarde(final Ja waarde) {
        this.waarde = waarde;
    }
}
