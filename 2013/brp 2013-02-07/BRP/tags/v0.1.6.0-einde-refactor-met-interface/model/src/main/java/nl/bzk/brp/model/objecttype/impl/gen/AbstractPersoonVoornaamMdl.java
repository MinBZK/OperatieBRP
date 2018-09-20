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
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.impl.usr.PersoonVoornaamStandaardGroepMdl;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonVoornaamBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import org.hibernate.annotations.Target;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonVoornaamMdl extends AbstractDynamischObjectType implements PersoonVoornaamBasis {

    @EmbeddedId
    @SequenceGenerator(name = "seq_PersVoornaam", sequenceName = "Kern.seq_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersVoornaam")
    @AttributeOverride(name = "waarde", column = @Column(name = "id"))
    private TechnischIdMiddel             id;

    @ManyToOne(targetEntity = PersoonMdl.class)
    @JoinColumn(name = "Pers")
    private Persoon                       persoon;

    @AttributeOverride(name = "waarde", column = @Column(name = "volgnr"))
    private Volgnummer                    volgnummer;

    @Embedded
    @Target(PersoonVoornaamStandaardGroepMdl.class)
    private PersoonVoornaamStandaardGroep gegevens;


    public TechnischIdMiddel getId() {
        return id;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonVoornaamStandaardGroep getGegevens() {
        return gegevens;
    }
}
