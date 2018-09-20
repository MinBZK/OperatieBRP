/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.IPAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Certificaat;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Functie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Een middel waarmee een Partij zich kan authenticeren.
 *
 * Een Authenticatiemiddel wordt gebruikt om de "Authenticiteit" van een Partij te verifi�ren ('is degene die zich
 * voordoet als een Partij ook daadwerkelijk deze Partij?').
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAuthenticatiemiddel extends AbstractStatischObjectType {

    @Id
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij         partij;

    @Column(name = "Rol")
    private Rol            rol;

    @Column(name = "Functie")
    private Functie        functie;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvSSL")
    @Fetch(value = FetchMode.JOIN)
    private Certificaat    certificaatTbvSSL;

    @ManyToOne
    @JoinColumn(name = "CertificaatTbvOndertekening")
    @Fetch(value = FetchMode.JOIN)
    private Certificaat    certificaatTbvOndertekening;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IPAdres"))
    private IPAdres        iPAdres;

    @Type(type = "StatusHistorie")
    @Column(name = "AuthenticatiemiddelStatusHis")
    private StatusHistorie authenticatiemiddelStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAuthenticatiemiddel() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partij partij van Authenticatiemiddel.
     * @param rol rol van Authenticatiemiddel.
     * @param functie functie van Authenticatiemiddel.
     * @param certificaatTbvSSL certificaatTbvSSL van Authenticatiemiddel.
     * @param certificaatTbvOndertekening certificaatTbvOndertekening van Authenticatiemiddel.
     * @param iPAdres iPAdres van Authenticatiemiddel.
     * @param authenticatiemiddelStatusHis authenticatiemiddelStatusHis van Authenticatiemiddel.
     */
    protected AbstractAuthenticatiemiddel(final Partij partij, final Rol rol, final Functie functie,
            final Certificaat certificaatTbvSSL, final Certificaat certificaatTbvOndertekening, final IPAdres iPAdres,
            final StatusHistorie authenticatiemiddelStatusHis)
    {
        this.partij = partij;
        this.rol = rol;
        this.functie = functie;
        this.certificaatTbvSSL = certificaatTbvSSL;
        this.certificaatTbvOndertekening = certificaatTbvOndertekening;
        this.iPAdres = iPAdres;
        this.authenticatiemiddelStatusHis = authenticatiemiddelStatusHis;

    }

    /**
     * Retourneert ID van Authenticatiemiddel.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Authenticatiemiddel.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Rol van Authenticatiemiddel.
     *
     * @return Rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Retourneert Functie van Authenticatiemiddel.
     *
     * @return Functie.
     */
    public Functie getFunctie() {
        return functie;
    }

    /**
     * Retourneert Certificaat tbv SSL van Authenticatiemiddel.
     *
     * @return Certificaat tbv SSL.
     */
    public Certificaat getCertificaatTbvSSL() {
        return certificaatTbvSSL;
    }

    /**
     * Retourneert Certificaat tbv Ondertekening van Authenticatiemiddel.
     *
     * @return Certificaat tbv Ondertekening.
     */
    public Certificaat getCertificaatTbvOndertekening() {
        return certificaatTbvOndertekening;
    }

    /**
     * Retourneert IP Adres van Authenticatiemiddel.
     *
     * @return IP Adres.
     */
    public IPAdres getIPAdres() {
        return iPAdres;
    }

    /**
     * Retourneert Authenticatiemiddel StatusHis van Authenticatiemiddel.
     *
     * @return Authenticatiemiddel StatusHis.
     */
    public StatusHistorie getAuthenticatiemiddelStatusHis() {
        return authenticatiemiddelStatusHis;
    }

}
