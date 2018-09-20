/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Serial;
import nl.bzk.brp.model.attribuuttype.Signature;
import nl.bzk.brp.model.attribuuttype.Subject;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Entity class voor certificaat.
 *
 * TODO deze klasse extends abstractStatischObjectType, dit is conceptueel niet correct want Certificaat is niet Nullable en Onderzoekbaar
 */
@Entity
@Access(AccessType.FIELD)
@Table(schema = "autaut", name = "certificaat")
public class Certificaat extends AbstractStatischObjectType {

    @Id
    @SequenceGenerator(name = "CERTIFICAAT", sequenceName = "AutAut.seq_Certificaat")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CERTIFICAAT")
    private Integer   id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "subject"))
    private Subject   subject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "serial"))
    private Serial    serial;

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
