/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonVerblijfsrechtGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verblijfsrecht;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonVerblijfsrechtGroep extends AbstractGroep
        implements PersoonVerblijfsrechtGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "verblijfsr")
    @Fetch(FetchMode.JOIN)
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
     *
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

    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    public void setDatumAanvangVerblijfsrecht(final Datum datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    public void setDatumAanvangAaneensluitendVerblijfsrecht(final Datum datumAanvangAaneensluitendVerblijfsrecht) {
        this.datumAanvangAaneensluitendVerblijfsrecht = datumAanvangAaneensluitendVerblijfsrecht;
    }

    public void setDatumVoorzienEindeVerblijfsrecht(final Datum datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }
}
