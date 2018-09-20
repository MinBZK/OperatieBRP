/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Serial;
import nl.bzk.copy.model.attribuuttype.Signature;
import nl.bzk.copy.model.attribuuttype.Subject;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Entity class voor certificaat.
 * <p/>
 * TODO deze klasse extends abstractStatischObjectType, dit is conceptueel niet correct want Certificaat is niet Nullable en Onderzoekbaar
 */
@Entity
@Access(AccessType.FIELD)
@Table(schema = "autaut", name = "certificaat")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Certificaat extends AbstractStatischObjectType {

    @Id
    @SequenceGenerator(name = "CERTIFICAAT", sequenceName = "AutAut.seq_Certificaat")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CERTIFICAAT")
    private Integer id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "subject"))
    private Subject subject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "serial"))
    private Serial serial;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "signature"))
    private Signature signature;

    public Integer getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public Serial getSerial() {
        return serial;
    }

    public Signature getSignature() {
        return signature;
    }
}
