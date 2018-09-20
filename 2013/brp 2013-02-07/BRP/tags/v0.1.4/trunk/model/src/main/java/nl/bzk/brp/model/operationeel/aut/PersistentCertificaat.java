/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.aut;

import java.math.BigInteger;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class voor certificaat.
 */
@Entity
@Access(AccessType.FIELD)
@Table(schema = "autaut", name = "certificaat")
public class PersistentCertificaat {

    @Id
    @SequenceGenerator(name = "CERTIFICAAT", sequenceName = "AutAut.seq_Certificaat")
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "CERTIFICAAT")
    private Integer id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "serial")
    private BigInteger serial;

    @Column(name = "signature")
    private String signature;

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public BigInteger getSerial() {
        return serial;
    }

    public String getSignature() {
        return signature;
    }
}
