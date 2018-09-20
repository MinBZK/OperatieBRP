/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.impl.usr.PersoonVoornaamStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonVoornaamBasis;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonVoornaamMdl extends AbstractDynamischObjectType implements PersoonVoornaamBasis {

    @Id
    @SequenceGenerator(name = "seq_PersVoornaam", sequenceName = "Kern.seq_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersVoornaam")
    private Long                             id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @NotNull
    private PersoonMdl                       persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "volgnr"))
    @NotNull
    private Volgnummer                       volgnummer;

    @Embedded
    @NotNull
    private PersoonVoornaamStandaardGroepMdl gegevens;


    public Long getId() {
        return id;
    }

    @Override
    public PersoonMdl getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonVoornaamStandaardGroepMdl getGegevens() {
        return gegevens;
    }
}
