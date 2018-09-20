/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.aut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.gedeeld.Functie;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Rol;
import nl.bzk.brp.model.operationeel.StatusHistorie;

/**
 * Entity class voor Authenticatiemiddel.
 */
@Entity
@Access(AccessType.FIELD)
@Table(schema = "autaut", name = "Authenticatiemiddel")
public class PersistentAuthenticatieMiddel {

    @Id
    @SequenceGenerator(name = "AUTHENTICATIEMIDDEL", sequenceName = "AutAut.seq_Authenticatiemiddel")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHENTICATIEMIDDEL")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Partij")
    private Partij partij;

    @Column(name = "Rol")
    private Rol rol;

    @Column(name = "Functie")
    private Functie functie;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvOndertekening")
    private PersistentCertificaat ondertekeningsCertificaat;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvSSL")
    private PersistentCertificaat sSLcertificaat;

    @Column(name = "IPAdres")
    private String ipAdres;

    @Column(name = "AuthenticatiemiddelStatusHis")
    private StatusHistorie statushistorie;

    public Integer getId() {
        return id;
    }

    public Partij getPartij() {
        return partij;
    }

    public Rol getRol() {
        return rol;
    }

    public Functie getFunctie() {
        return functie;
    }

    public PersistentCertificaat getOndertekeningsCertificaat() {
        return ondertekeningsCertificaat;
    }

    public PersistentCertificaat getSSLcertificaat() {
        return sSLcertificaat;
    }

    public String getIpAdres() {
        return ipAdres;
    }

    public StatusHistorie getStatushistorie() {
        return statushistorie;
    }
}
