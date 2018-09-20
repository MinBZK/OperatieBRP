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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * Implementatie voor groep persoon inschrijving.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingGroep extends AbstractGroep implements
        PersoonInschrijvingGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "versienr"))
    private Versienummer versienummer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vorigepers")
    private PersoonModel vorigePersoon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volgendepers")
    private PersoonModel volgendePersoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "datinschr"))
    private Datum        datumInschrijving;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonInschrijvingGroep(final PersoonInschrijvingGroepBasis groep) {
        super(groep);
        versienummer = groep.getVersienummer();
        datumInschrijving = groep.getDatumInschrijving();
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonInschrijvingGroep() {

    }

    @Override
    public Versienummer getVersienummer() {
        return versienummer;
    }

    @Override
    public PersoonModel getVorigePersoon() {
        return vorigePersoon;
    }

    @Override
    public PersoonModel getVolgendePersoon() {
        return volgendePersoon;
    }

    @Override
    public Datum getDatumInschrijving() {
        return datumInschrijving;
    }
}
