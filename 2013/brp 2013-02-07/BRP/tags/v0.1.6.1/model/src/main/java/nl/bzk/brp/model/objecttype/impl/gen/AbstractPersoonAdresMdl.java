/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.impl.usr.PersoonAdresStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonAdresBasis;


/**
 * Implementatie voor objecttype Persoon adres.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresMdl extends AbstractDynamischObjectType implements PersoonAdresBasis {

    @Id
    @SequenceGenerator(name = "PERSOONADRES", sequenceName = "Kern.seq_PersAdres")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOONADRES")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @NotNull
    private PersoonMdl persoon;

    @Embedded
    @NotNull
    private PersoonAdresStandaardGroepMdl gegevens;

    public Long getId() {
        return id;
    }

    @Override
    public PersoonMdl getPersoon() {
        return persoon;
    }

    @Override
    public PersoonAdresStandaardGroepMdl getGegevens() {
        return gegevens;
    }
}
