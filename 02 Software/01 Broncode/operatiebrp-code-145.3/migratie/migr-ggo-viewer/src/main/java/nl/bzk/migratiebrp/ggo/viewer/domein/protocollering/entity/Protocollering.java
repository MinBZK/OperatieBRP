/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Logging regel.
 */
@Entity
@Table(name = "protocollering", schema = "viewer")
public class Protocollering {

    @Id
    @SequenceGenerator(name = "protocollering_id_generator", sequenceName = "viewer.seq_protocollering", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "protocollering_id_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gebruikersnaam", nullable = false, length = 36)
    private String gebruikersnaam;

    @Column(name = "datumtijd", nullable = false)
    private Timestamp datumtijd;

    @Column(name = "a_nummer", nullable = true)
    private String administratienummer;

    @Column(name = "geautoriseerd")
    private Boolean geautoriseerd;

    /**
     * JPA default constructor.
     */
    protected Protocollering() {
    }

    /**
     * Constructor voor Protocollering met de benodigde gegevens.
     * @param gebruikersnaam De gebruikersnaam.
     * @param datumtijd De datumtijd van het aanmaken van de protocollering.
     * @param administratienummer Het a-nummer.
     * @param geautoriseerd Of de gebruiker geautoriseerd was.
     */
    public Protocollering(
            final String gebruikersnaam,
            final Timestamp datumtijd,
            final String administratienummer,
            final Boolean geautoriseerd) {
        super();
        this.gebruikersnaam = gebruikersnaam;
        this.datumtijd = Kopieer.timestamp(datumtijd);
        this.administratienummer = administratienummer;
        this.geautoriseerd = geautoriseerd;
    }

    /**
     * Geef de waarde van id.
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     * @param id the id to set
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van gebruikersnaam.
     * @return the gebruikersnaam
     */
    public final String getGebruikersnaam() {
        return gebruikersnaam;
    }

    /**
     * Zet de waarde van gebruikersnaam.
     * @param gebruikersnaam the gebruikersnaam to set
     */
    public final void setGebruikersnaam(final String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    /**
     * Geef de waarde van datumtijd.
     * @return the datumtijd
     */
    public final Timestamp getDatumtijd() {
        return Kopieer.timestamp(datumtijd);
    }

    /**
     * Zet de waarde van datumtijd.
     * @param datumtijd the datumtijd to set
     */
    public final void setDatumtijd(final Timestamp datumtijd) {
        this.datumtijd = Kopieer.timestamp(datumtijd);
    }

    /**
     * Geef de waarde van administratienummer.
     * @return the administratienummer
     */
    public final String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarde van administratienummer.
     * @param administratienummer the administratienummer to set
     */
    public final void setAdministratienummer(final String administratienummer) {
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van geautoriseerd.
     * @return the geautoriseerd
     */
    public final Boolean getGeautoriseerd() {
        return geautoriseerd;
    }

    /**
     * Zet de waarde van geautoriseerd.
     * @param geautoriseerd the geautoriseerd to set
     */
    public final void setGeautoriseerd(final Boolean geautoriseerd) {
        this.geautoriseerd = geautoriseerd;
    }

}
