/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonBasis;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;


/**
 * Implementatie voor groep persoon inschrijving.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingGroepMdl extends AbstractGroep implements
        PersoonInschrijvingGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "versienr"))
    private Versienummer   versienummer;

    @OneToOne
    @JoinColumn(name = "vorigepers")
    private PersoonMdl     vorigePersoon;

    @OneToOne
    @JoinColumn(name = "volgendepers")
    private PersoonMdl     volgendePersoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "datinschr"))
    private Datum          datumInschrijving;

    @Transient
    private StatusHistorie statusHistorie;

    @Override
    public Versienummer getVersienummer() {
        return versienummer;
    }

    @Override
    public PersoonBasis getVorigePersoon() {
        return vorigePersoon;
    }

    @Override
    public PersoonBasis getVolgendePersoon() {
        return volgendePersoon;
    }

    @Override
    public Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}
