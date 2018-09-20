/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Een middel waarmee een Partij zich kan authenticeren.
 *
 * Een Authenticatiemiddel wordt gebruikt om de "Authenticiteit" van een Partij te verifiëren ('is degene die zich
 * voordoet als een Partij ook daadwerkelijk deze Partij?').
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Authenticatiemiddel", schema = "AutAut")
@Access(AccessType.FIELD)
public class AuthenticatieMiddel implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatieMiddel.class);

    @SequenceGenerator(name = "AUTHMIDDEL_SEQUENCE_GENERATOR", sequenceName = "AutAut.seq_Authenticatiemiddel")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHMIDDEL_SEQUENCE_GENERATOR")
    private Long                id;
    @ManyToOne
    @JoinColumn(name = "Partij", insertable = false, updatable = false)
    private Partij              partij;
    @Column(name = "rol")
    private Rol                 rol;
    @Column(name = "functie")
    private Functie             functie;
    @ManyToOne
    @JoinColumn(name = "CertificaatTbvSSL")
    private Certificaat         certificaatTbvSsl;
    @ManyToOne
    @JoinColumn(name = "CertificaatTbvOndertekening")
    private Certificaat         certificaatTbvOndertekening;
    @Column(name = "IPAdres")
    private String              ipAdres;

    /**
     * No-arg constructor voor JPA.
     */
    protected AuthenticatieMiddel() {
    }

    public Long getId() {
        return id;
    }

    /**
     * De Partij die eigenaar is van het authenticatiemiddel.
     * Dit attribuut verwijst naar de Partij die ermee kan worden geauthenticeert.
     *
     * @return de Partij die eigenaar is van het authenticatiemiddel.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * De rol waarin de Partij dit Authenticatiemiddel gebruikt/kan gebruiken.
     * Het is denkbaar dat een Partij voor verschillende rollen verschillende Authenticatiemiddelen gebruikt. Denk aan
     * het éne certificaat voor bijhouden, en het andere voor de rol afnemer. De BRP ondersteunt de mogelijkheid om de
     * beperking vast te leggen dat een bepaald Authenticatiemiddel ALLEEN in mag worden gezet voor een specifieke rol.
     *
     * @return de rol waarin de Partij dit Authenticatiemiddel gebruikt/kan gebruiken.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * De Functie ten behoeve waarvan het Authenticatiemiddel wordt ingezet.
     *
     * @return de Functie ten behoeve waarvan het Authenticatiemiddel wordt ingezet.
     */
    public Functie getFunctie() {
        return functie;
    }

    /**
     * Het Certificaat dat gebruikt wordt/gebruikt kan worden voor de SSL sessies ten behoeve van het berichtenverkeer
     * tussen Partij en de BRP. Het berichtenverkeer tussen de BRP en de aangesloten Partijen zal o.a. via SSL (Secure
     * Sockets Layer) beveiligd zijn. Het gaat hier om het Certificaat dat gebruikt is/gebruikt wordt voor het
     * versleutelen van deze sessies.
     *
     * @return het certificaat ten behoeve van de SSL sessie.
     */
    public Certificaat getCertificaatTbvSsl() {
        return certificaatTbvSsl;
    }

    /**
     * Het Certificaat waarmee de Partij het berichtenverkeer naar de BRP ondertekend.
     * Naast het versleutelen van het bericht via SSL is ook voorzien in een electronische ondertekening van het
     * bericht door de Partij. Door het zetten van deze handtekening staat de Partij ervoor in dat het bericht
     * daadwerkelijk van de Partij zelf afkomstig is.
     * Het is denkbaar dat het Certificaat afwijkt van het Certificaat dat gebruikt is voor het opzetten van de SSL
     * sessie waarmee het bericht is verstuurd, bijvoorbeeld als de Partij gebruik maakt van voorzieningen van derden
     * voor het versturen van berichten.
     *
     * @return het certificaat ten behoeve van de signing.
     */
    public Certificaat getCertificaatTbvOndertekening() {
        return certificaatTbvOndertekening;
    }

    /**
     * Het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     * De BRP ondersteunt de mogelijkheid om zogenaamde 'whitelisting' te gebruiken: in dat geval mag een bepaald
     * bericht van een Partij (eventueel: een Partij in een bepaalde rol) alleen afkomstig zijn van een bepaald IP
     * adres. Als er sprake is van een gevuld IP adres, dan worden alleen berichten afkomstig van dat IP adres als
     * potentieel authentieke berichten beschouwd. Deze voorzorgmaatregel is additioneel bovenop maatregelen als
     * Certificaten, en vervangt die beveiligingsdoeleinden niet.
     *
     * @return het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     */
    public InetAddress getIpAdres() {
        InetAddress inetAddress = null;

        if (ipAdres != null) {
            try {
                inetAddress = InetAddress.getByName(ipAdres);
            } catch (UnknownHostException e) {
                LOGGER.error("Converteren van Inet Adres ({}) uit de database voor authenticatiemiddel met id {} is "
                    + "mislukt.", ipAdres, id);
            }
        }
        return inetAddress;
    }

    /**
     * Zet het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     *
     * @param inetAdres het IP adres dat door de Partij gebruikt wordt voor het aansluiten op de BRP.
     */
    public void setIpAdres(final InetAddress inetAdres) {
        inetAdres.getHostAddress();
    }

}
