/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.IPAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Certificaat;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Functie;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisAuthenticatiemiddel {

    @Id
    @SequenceGenerator(name = "HIS_AUTHENTICATIEMIDDEL", sequenceName = "AutAut.seq_His_Authenticatiemiddel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_AUTHENTICATIEMIDDEL")
    @JsonProperty
    private Integer             iD;

    @ManyToOne
    @JoinColumn(name = "Authenticatiemiddel")
    @Fetch(value = FetchMode.JOIN)
    private Authenticatiemiddel authenticatiemiddel;

    @Enumerated
    @Column(name = "Functie")
    @JsonProperty
    private Functie             functie;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvSSL")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Certificaat         certificaatTbvSSL;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvOndertekening")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Certificaat         certificaatTbvOndertekening;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IPAdres"))
    @JsonProperty
    private IPAdres             iPAdres;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisAuthenticatiemiddel() {
    }

    /**
     * Retourneert ID van His Authenticatiemiddel.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Authenticatiemiddel van His Authenticatiemiddel.
     *
     * @return Authenticatiemiddel.
     */
    public Authenticatiemiddel getAuthenticatiemiddel() {
        return authenticatiemiddel;
    }

    /**
     * Retourneert Functie van His Authenticatiemiddel.
     *
     * @return Functie.
     */
    public Functie getFunctie() {
        return functie;
    }

    /**
     * Retourneert Certificaat tbv SSL van His Authenticatiemiddel.
     *
     * @return Certificaat tbv SSL.
     */
    public Certificaat getCertificaatTbvSSL() {
        return certificaatTbvSSL;
    }

    /**
     * Retourneert Certificaat tbv Ondertekening van His Authenticatiemiddel.
     *
     * @return Certificaat tbv Ondertekening.
     */
    public Certificaat getCertificaatTbvOndertekening() {
        return certificaatTbvOndertekening;
    }

    /**
     * Retourneert IP Adres van His Authenticatiemiddel.
     *
     * @return IP Adres.
     */
    public IPAdres getIPAdres() {
        return iPAdres;
    }

}
