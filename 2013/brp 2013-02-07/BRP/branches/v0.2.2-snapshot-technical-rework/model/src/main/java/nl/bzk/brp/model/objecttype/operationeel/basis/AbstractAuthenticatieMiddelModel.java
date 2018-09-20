/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.attribuuttype.IpAdres;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.logisch.basis.AuthenticatieMiddelBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Certificaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Functie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Rol;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * AbstractAuthenticatieMiddelModel.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractAuthenticatieMiddelModel extends AbstractDynamischObjectType implements AuthenticatieMiddelBasis {

    @Id
    @SequenceGenerator(name = "AUTHENTICATIEMIDDEL", sequenceName = "AutAut.seq_Authenticatiemiddel")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHENTICATIEMIDDEL")
    private Integer        id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Partij")
    private Partij         partij;

    @Column(name = "Rol")
    @Enumerated
    private Rol            rol;

    @Column(name = "Functie")
    @Enumerated
    private Functie        functie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CertificaatTbvOndertekening")
    private Certificaat    ondertekeningsCertificaat;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvSSL")
    private Certificaat    sslCertificaat;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "ipadres"))
    private IpAdres ipAdres;

    @Column(name = "AuthenticatiemiddelStatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie statushistorie;


    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param authenticatiemiddel Object type dat gekopieerd dient te worden.
     */
    protected AbstractAuthenticatieMiddelModel(final AuthenticatieMiddelBasis authenticatiemiddel) {
        super(authenticatiemiddel);

        partij = authenticatiemiddel.getPartij();
        rol = authenticatiemiddel.getRol();
        functie = authenticatiemiddel.getFunctie();
        ondertekeningsCertificaat = authenticatiemiddel.getOndertekeningsCertificaat();
        sslCertificaat = authenticatiemiddel.getSslCertificaat();
        ipAdres = getIpAdres();
    }

    /**
     * Default constructor. Vereist voor Hibernate.
     */
    protected AbstractAuthenticatieMiddelModel() {
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Partij getPartij() {
        return partij;
    }

    @Override
    public Rol getRol() {
        return rol;
    }

    @Override
    public Functie getFunctie() {
        return functie;
    }

    @Override
    public Certificaat getOndertekeningsCertificaat() {
        return ondertekeningsCertificaat;
    }

    @Override
    public Certificaat getSslCertificaat() {
        return sslCertificaat;
    }

    @Override
    public IpAdres getIpAdres() {
        return ipAdres;
    }

    @Override
    public StatusHistorie getStatushistorie() {
        return statushistorie;
    }
}
