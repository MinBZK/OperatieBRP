/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The persistent class for the lo3ber database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "lo3ber", schema = "verconv", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class Lo3Bericht extends AbstractDeltaEntiteit implements Serializable {
    private static final String REFERENTIE_MAG_NIET_NULL_ZIJN = "referentie mag niet null zijn";
    private static final String UTF_8 = "UTF-8";
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "lo3ber_id_generator", sequenceName = "verconv.seq_lo3ber", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3ber_id_generator")
    @Column(updatable = false)
    private Long id;

    @Column(name = "anr")
    private Long anummer;

    @Column(name = "berdata", nullable = false)
    private byte[] berichtdata;

    @Column(nullable = false)
    private String checksum;

    private String foutcode;

    @Column(name = "verwerkingsmelding")
    private String verwerkingsmelding;

    @Column(name = "indbersrtonderdeello3stelsel", nullable = false)
    private boolean isBerichtsoortOnderdeelLo3Stelsel;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers")
    private Persoon persoon;

    @Column(nullable = false)
    private String referentie;

    @Column(name = "tsconversie")
    private Timestamp tijdstipConversie;

    @Column(name = "bron", nullable = false)
    private short berichtenBronId;

    // bi-directional many-to-one association to Lo3Voorkomen
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bericht", cascade = {CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<Lo3Voorkomen> voorkomens = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Lo3Bericht() {
    }

    /**
     * Maak een nieuwe lo3 bericht.
     *
     * @param referentie
     *            referentie
     * @param berichtenBron
     *            berichten bron
     * @param tijdstipConversie
     *            tijdstip conversie
     * @param berichtdata
     *            berichtdata
     * @param isBerichtsoortOnderdeelLo3Stelsel
     *            is berichtsoort onderdeel lo3 stelsel
     */
    public Lo3Bericht(
        final String referentie,
        final Lo3BerichtenBron berichtenBron,
        final Timestamp tijdstipConversie,
        final String berichtdata,
        final boolean isBerichtsoortOnderdeelLo3Stelsel)
    {
        ValidationUtils.controleerOpNullWaarden(REFERENTIE_MAG_NIET_NULL_ZIJN, referentie);
        Validatie.controleerOpLegeWaarden("referentie mag geen lege string zijn", referentie);
        this.referentie = referentie;
        setBerichtenBron(berichtenBron);
        setTijdstipConversie(tijdstipConversie);
        setBerichtdata(berichtdata);
        this.isBerichtsoortOnderdeelLo3Stelsel = isBerichtsoortOnderdeelLo3Stelsel;
    }

    /**
     * Maakt een Lo3Bericht obv de huidige datum en tijd.
     * 
     * @param referentie
     *            een referentie naar het bericht (bijv. message id)
     * @param berichtenBron
     *            de bron waar het bericht vandaan komt (bijv. queue naam)
     * @param berichtData
     *            de inhoud van het bericht
     * @param isBerichtsoortOnderdeelLo3Stelsel
     *            true als dit bericht onderdeel is van het LO3 stelsel
     * @return een nieuw BerichtLog object
     */
    public static Lo3Bericht newInstance(
        final String referentie,
        final Lo3BerichtenBron berichtenBron,
        final String berichtData,
        final boolean isBerichtsoortOnderdeelLo3Stelsel)
    {
        return new Lo3Bericht(referentie, berichtenBron, new Timestamp(System.currentTimeMillis()), berichtData, isBerichtsoortOnderdeelLo3Stelsel);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    protected void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van anummer.
     *
     * @return anummer
     */
    public Long getAnummer() {
        return anummer;
    }

    /**
     * Zet de waarde van anummer.
     *
     * @param anummer
     *            anummer
     */
    public void setAnummer(final Long anummer) {
        this.anummer = anummer;
    }

    /**
     * Geef de waarde van berichtdata.
     *
     * @return berichtdata
     */
    public String getBerichtdata() {
        // TODO: datatype moet text worden ipv bytea
        return new String(berichtdata, Charset.forName(UTF_8));
    }

    /**
     * Zet de waarde van berichtdata.
     *
     * @param berichtdata
     *            berichtdata
     */
    public void setBerichtdata(final String berichtdata) {
        // TODO: datatype moet text worden ipv bytea
        ValidationUtils.controleerOpNullWaarden("berichtdata mag niet null zijn", berichtdata);
        this.berichtdata = berichtdata.getBytes(Charset.forName(UTF_8));
        setChecksum("" + new HashCodeBuilder().append(berichtdata).build());
    }

    /**
     * Geef de waarde van checksum.
     *
     * @return checksum
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Zet de waarde van checksum.
     *
     * @param checksum
     *            checksum
     */
    private void setChecksum(final String checksum) {
        this.checksum = checksum;
    }

    /**
     * Geef de waarde van foutcode.
     *
     * @return foutcode
     */
    public String getFoutcode() {
        return foutcode;
    }

    /**
     * Zet de waarde van foutcode.
     *
     * @param foutcode
     *            foutcode
     */
    public void setFoutcode(final String foutcode) {
        this.foutcode = foutcode;
    }

    /**
     * Geef de waarde van verwerkingsmelding.
     *
     * @return verwerkingsmelding
     */
    public String getVerwerkingsmelding() {
        return verwerkingsmelding;
    }

    /**
     * Zet de waarde van verwerkingsmelding.
     *
     * @param verwerkingsmelding
     *            verwerkingsmelding
     */
    public void setVerwerkingsmelding(final String verwerkingsmelding) {
        this.verwerkingsmelding = verwerkingsmelding;
    }

    /**
     * Geef de berichtsoort onderdeel lo3 stelsel.
     *
     * @return berichtsoort onderdeel lo3 stelsel
     */
    public boolean isBerichtsoortOnderdeelLo3Stelsel() {
        return isBerichtsoortOnderdeelLo3Stelsel;
    }

    /**
     * Zet de waarde van controleert op berichtsoort onderdeel lo3 stelsel.
     *
     * @param isBerichtsoortOnderdeelLo3Stelsel
     *            controleert op berichtsoort onderdeel lo3 stelsel
     */
    public void setIsBerichtsoortOnderdeelLo3Stelsel(final boolean isBerichtsoortOnderdeelLo3Stelsel) {
        this.isBerichtsoortOnderdeelLo3Stelsel = isBerichtsoortOnderdeelLo3Stelsel;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        if (persoon == null) {
            setAnummer(null);
        } else {
            setAnummer(persoon.getAdministratienummer());
        }
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van referentie.
     *
     * @return referentie
     */
    public String getReferentie() {
        return referentie;
    }

    /**
     * Zet de waarde van referentie.
     *
     * @param referentie
     *            referentie
     */
    public void setReferentie(final String referentie) {
        ValidationUtils.controleerOpNullWaarden(REFERENTIE_MAG_NIET_NULL_ZIJN, referentie);
        this.referentie = referentie;
    }

    /**
     * Geef de waarde van tijdstip conversie.
     *
     * @return tijdstip conversie
     */
    public Timestamp getTijdstipConversie() {
        return Kopieer.timestamp(tijdstipConversie);
    }

    /**
     * Zet de waarde van tijdstip conversie.
     *
     * @param tijdstipConversie
     *            tijdstip conversie
     */
    public void setTijdstipConversie(final Timestamp tijdstipConversie) {
        ValidationUtils.controleerOpNullWaarden("tijdstipConversie mag niet null zijn", tijdstipConversie);
        this.tijdstipConversie = Kopieer.timestamp(tijdstipConversie);
    }

    /**
     * Geef de waarde van berichten bron.
     *
     * @return berichten bron
     */
    public Lo3BerichtenBron getBerichtenBron() {
        return Lo3BerichtenBron.parseId(berichtenBronId);
    }

    /**
     * Zet de waarde van berichten bron.
     *
     * @param berichtenBron
     *            berichten bron
     */
    public void setBerichtenBron(final Lo3BerichtenBron berichtenBron) {
        ValidationUtils.controleerOpNullWaarden("berichtenBron mag niet null zijn", berichtenBron);
        berichtenBronId = berichtenBron.getId();
    }

    /**
     * Geef de waarde van voorkomens.
     *
     * @return voorkomens
     */
    public Set<Lo3Voorkomen> getVoorkomens() {
        return voorkomens;
    }

    /**
     * Zet de waarde van voorkomens.
     *
     * @param voorkomens
     *            voorkomens
     */
    public void setVoorkomens(final Set<Lo3Voorkomen> voorkomens) {
        this.voorkomens = voorkomens;
    }

    /**
     * Voegt de voorkomens van het meegegeven lo3bericht samen met de bestaand voorkomens als categorie, stapel en
     * voorkomen overeenkomst. Anders wordt het voorkomen toegevoegd aan de bestaande set van voorkomens. De voorkomens
     * worden vervolgens uit het meegegeven bericht verwijderd en dit bericht wordt gekoppeld aan de persoon van het
     * meegegeven bericht.
     * 
     * @param anderBericht
     *            het bericht met de voorkomens die in dit bericht gemerged dienen te worden
     */
    public void mergeVoorkomensUitAnderBericht(final Lo3Bericht anderBericht) {
        final Set<Lo3Voorkomen> lo3Voorkomens = anderBericht.getVoorkomens();
        for (final Lo3Voorkomen voorkomen : lo3Voorkomens) {
            mergeVoorkomen(voorkomen);
        }
        anderBericht.setBerichtdata(getBerichtdata());
        anderBericht.setBerichtenBron(getBerichtenBron());
        anderBericht.setFoutcode(getFoutcode());
        anderBericht.setVerwerkingsmelding(getVerwerkingsmelding());
        anderBericht.setIsBerichtsoortOnderdeelLo3Stelsel(isBerichtsoortOnderdeelLo3Stelsel());
        anderBericht.setReferentie(getReferentie());
        anderBericht.setTijdstipConversie(getTijdstipConversie());
        anderBericht.setVoorkomens(getVoorkomens());
    }

    /**
     * Merge voorkomen.
     *
     * @param voorkomen
     *            voorkomen
     */
    private void mergeVoorkomen(final Lo3Voorkomen voorkomen) {
        final Lo3Voorkomen gevondenVoorkomen =
                addVoorkomen(voorkomen.getCategorie(), voorkomen.getStapelvolgnummer(), voorkomen.getVoorkomenvolgnummer(), voorkomen.getActie());
        if (voorkomen.getConversieSortering() != null) {
            gevondenVoorkomen.setConversieSortering(voorkomen.getConversieSortering());
        }
        for (final Lo3Melding melding : voorkomen.getMeldingen()) {
            gevondenVoorkomen.addMelding(melding);
        }
    }

    /**
     * Als voor de meegegeven categorie, stapel- en voorkomenvolgnummer nog geen voorkomen bestaat dan wordt deze
     * aangemaakt toegevoegd aan het bericht en geretourneerd. Als deze al wel bestaat wordt de bestaande voorkomen
     * geretourneerd en als actie ongelijk aan null is dan wordt deze waarde ook gezet.
     *
     * @param categorie
     *            de lo3 categorie
     * @param stapelvolgnummer
     *            het stapel volgnummer
     * @param voorkomenvolgnummer
     *            het voorkomen volgnummer
     * @param actie
     *            de BRP actie die volgt uit dit voorkomen
     * @return lo3 voorkomen
     */
    public final Lo3Voorkomen addVoorkomen(
        final String categorie,
        final Integer stapelvolgnummer,
        final Integer voorkomenvolgnummer,
        final BRPActie actie)
    {
        Lo3Voorkomen voorkomen = zoekLo3Voorkomen(categorie, stapelvolgnummer, voorkomenvolgnummer);
        if (voorkomen == null) {
            voorkomen = new Lo3Voorkomen(this, categorie, stapelvolgnummer, voorkomenvolgnummer);
            addVoorkomen(voorkomen);
        }
        if (actie != null) {
            voorkomen.setActie(actie);
        }
        return voorkomen;
    }

    /**
     * Toevoegen van een voorkomen.
     *
     * @param voorkomen
     *            voorkomen
     */
    private void addVoorkomen(final Lo3Voorkomen voorkomen) {
        voorkomen.setBericht(this);
        voorkomens.add(voorkomen);
    }

    /**
     * Voegt een lo3 melding toe aan dit bericht.
     * 
     * @param categorie
     *            de lo3 categorie
     * @param stapelvolgnummer
     *            het stapel volgnummer
     * @param voorkomenvolgnummer
     *            het voorkomen volgnummer
     * @param actie
     *            de BRP actie die volgt uit dit voorkomen
     * @param soortMelding
     *            de soort melding
     * @param logSeverity
     *            de log severity
     * @param groep
     *            het lo3 groep nummer
     * @param rubriek
     *            het lo3 rubriek nummer
     */
    @SuppressWarnings("checkstyle:ParameterNumberCheck")
    public final void addMelding(
        final String categorie,
        final Integer stapelvolgnummer,
        final Integer voorkomenvolgnummer,
        final BRPActie actie,
        final Lo3SoortMelding soortMelding,
        final Lo3Severity logSeverity,
        final String groep,
        final String rubriek)
    {
        final Lo3Voorkomen voorkomen = addVoorkomen(categorie, stapelvolgnummer, voorkomenvolgnummer, actie);
        voorkomen.addMelding(new Lo3Melding(voorkomen, soortMelding, logSeverity, groep, rubriek));
    }

    /**
     * Zoek lo3 voorkomen.
     *
     * @param categorie
     *            categorie
     * @param stapelvolgnummer
     *            stapelvolgnummer
     * @param voorkomenvolgnummer
     *            voorkomenvolgnummer
     * @return lo3 voorkomen
     */
    private Lo3Voorkomen zoekLo3Voorkomen(final String categorie, final Integer stapelvolgnummer, final Integer voorkomenvolgnummer) {
        final Lo3Voorkomen zoekSleutel = new Lo3Voorkomen(this, categorie, stapelvolgnummer, voorkomenvolgnummer);
        Lo3Voorkomen result = null;
        for (final Lo3Voorkomen voorkomen : getVoorkomens()) {
            if (zoekSleutel.equals(voorkomen)) {
                result = voorkomen;
                break;
            }
        }
        return result;
    }
}
