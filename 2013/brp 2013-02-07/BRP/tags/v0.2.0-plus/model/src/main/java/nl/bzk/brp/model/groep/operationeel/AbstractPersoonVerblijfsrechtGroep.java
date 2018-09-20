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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonVerblijfsrechtGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verblijfsrecht;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonVerblijfsrechtGroep extends AbstractGroep implements PersoonVerblijfsrechtGroepBasis {

    @ManyToOne
    @JoinColumn(name = "verblijfsr")
    private Verblijfsrecht verblijfsrecht;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvVerblijfsr"))
    private Datum datumAanvangVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAaneenslVerblijfsr"))
    private Datum datumAanvangAaneensluitendVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzEindeVerblijfsr"))
    private Datum datumVoorzienEindeVerblijfsrecht;

    /**
     * .
     * @param groep PersoonOpschortingGroepBasis
     */
    protected AbstractPersoonVerblijfsrechtGroep(final PersoonVerblijfsrechtGroepBasis groep) {
        super(groep);
        // kopieerslag hier.
        verblijfsrecht = groep.getVerblijfsrecht();
        datumAanvangAaneensluitendVerblijfsrecht = groep.getDatumAanvangAaneensluitendVerblijfsrecht();
        datumAanvangVerblijfsrecht = groep.getDatumAanvangVerblijfsrecht();
        datumVoorzienEindeVerblijfsrecht = groep.getDatumVoorzienEindeVerblijfsrecht();
    }

    /**
     * .
     */
    protected AbstractPersoonVerblijfsrechtGroep() {
        super();
    }

    @Override
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    @Override
    public Datum getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    @Override
    public Datum getDatumAanvangAaneensluitendVerblijfsrecht() {
        return datumAanvangAaneensluitendVerblijfsrecht;
    }

    @Override
    public Datum getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }
}
