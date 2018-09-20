/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the stapelvoorkomen database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "stapelvoorkomen", schema = "ist", uniqueConstraints = @UniqueConstraint(columnNames = {"stapel", "volgnr", "admhnd" }))
@SuppressWarnings("checkstyle:designforextension")
public class StapelVoorkomen extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    /* Identiteit. */
    @Id
    @SequenceGenerator(name = "stapelvoorkomen_id_generator", sequenceName = "ist.seq_stapelvoorkomen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stapelvoorkomen_id_generator")
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "stapel", nullable = false)
    private Stapel stapel;

    @Column(name = "volgnr", nullable = false)
    private int volgnummer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    /* Standaard. */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srtdoc")
    private SoortDocument soortDocument;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij")
    private Partij partij;

    @Column(name = "rubr8220datdoc")
    private Integer rubriek8220DatumDocument;

    @Column(name = "docoms", length = 40)
    private String documentOmschrijving;

    @Column(name = "rubr8310aandgegevensinonderz")
    private Integer rubriek8310AanduidingGegevensInOnderzoek;

    @Column(name = "rubr8320datingangonderzoek")
    private Integer rubriek8320DatumIngangOnderzoek;

    @Column(name = "rubr8330dateindeonderzoek")
    private Integer rubriek8330DatumEindeOnderzoek;

    @Column(name = "rubr8410onjuiststrijdigopenb")
    private Character rubriek8410OnjuistOfStrijdigOpenbareOrde;

    @Column(name = "rubr8510ingangsdatgel")
    private Integer rubriek8510IngangsdatumGeldigheid;

    @Column(name = "rubr8610datvanopneming")
    private Integer rubriek8610DatumVanOpneming;

    /* Categorie gerelateerden. */

    @Column(name = "rubr6210datingangfamilierech")
    private Integer rubriek6210DatumIngangFamilierechtelijkeBetrekking;

    @Column(name = "aktenr", length = 7)
    private String aktenummer;

    @Column(name = "anr")
    private Long anummer;

    @Column(name = "bsn")
    private Integer bsn;

    @Column(length = 200)
    private String voornamen;

    @Column(name = "predicaat")
    private Short predicaatId;

    @Column(name = "adellijketitel")
    private Short adellijkeTitelId;

    @Column(name = "geslachtbijadellijketitelpre")
    private Short geslachtsaanduidingBijAdellijkeTitelOfPredikaatId;

    @Column(length = 10)
    private String voorvoegsel;

    @Column
    private Character scheidingsteken;

    @Column(name = "geslnaamstam", length = 200)
    private String geslachtsnaamstam;

    @Column(name = "datgeboorte")
    private Integer datumGeboorte;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemgeboorte")
    private Gemeente gemeenteGeboorte;

    @Column(name = "blplaatsgeboorte", length = 40)
    private String buitenlandsePlaatsGeboorte;

    @Column(name = "omslocgeboorte", length = 40)
    private String omschrijvingLocatieGeboorte;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedgeboorte")
    private LandOfGebied landOfGebiedGeboorte;

    @Column(name = "geslachtsaand")
    private Short geslachtsaanduidingId;

    @Column(name = "dataanv")
    private Integer datumAanvang;

    /* Categorie huwelijk / geregistreerd partnerschap. */

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemaanv")
    private Gemeente gemeenteAanvang;

    @Column(name = "blplaatsaanv", length = 40)
    private String buitenlandsePlaatsAanvang;

    @Column(name = "omslocaanv", length = 40)
    private String omschrijvingLocatieAanvang;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedaanv")
    private LandOfGebied landOfGebiedAanvang;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemeinde")
    private Gemeente gemeenteEinde;

    @Column(name = "blplaatseinde", length = 40)
    private String buitenlandsePlaatsEinde;

    @Column(name = "omsloceinde", length = 40)
    private String omschrijvingLocatieEinde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedeinde")
    private LandOfGebied landOfGebiedEinde;

    @Column(name = "srtrelatie")
    private Short soortRelatieId;

    /* Categorie gezagsverhouding. */

    @Column(name = "indouder1heeftgezag")
    private Boolean indicatieOuder1HeeftGezag;

    @Column(name = "indouder2heeftgezag")
    private Boolean indicatieOuder2HeeftGezag;

    @Column(name = "indderdeheeftgezag")
    private Boolean indicatieDerdeHeeftGezag;

    @Column(name = "indondercuratele")
    private Boolean indicatieOnderCuratele;

    /**
     * JPA default constructor.
     */
    protected StapelVoorkomen() {
    }

    /**
     * Maakt een StapelVoorkomen object.
     *
     * @param stapel
     *            de stapel, mag niet null zijn
     * @param volgnummer
     *            het volgnummer van het voorkomen binnen een stapel beginnend bij 0 (actueel), mag niet null zijn
     * @param administratieveHandeling
     *            de administratieveHandeling, mag niet null zijn
     */
    public StapelVoorkomen(final Stapel stapel, final int volgnummer, final AdministratieveHandeling administratieveHandeling) {
        setStapel(stapel);
        setVolgnummer(volgnummer);
        setAdministratieveHandeling(administratieveHandeling);
    }

    /* Identiteit. */

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van stapel.
     *
     * @return stapel
     */
    public Stapel getStapel() {
        return stapel;
    }

    /**
     * Zet de waarde van stapel.
     *
     * @param stapel
     *            stapel
     */
    void setStapel(final Stapel stapel) {
        ValidationUtils.controleerOpNullWaarden("stapel mag niet null zijn", stapel);
        this.stapel = stapel;
    }

    /**
     * Geef de waarde van volgnummer.
     *
     * @return volgnummer
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarde van volgnummer.
     *
     * @param volgnummer
     *            volgnummer
     */
    void setVolgnummer(final int volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef de waarde van administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarde van administratieve handeling.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /* Standaard. */

    /**
     * Geef de waarde van soort document.
     *
     * @return soort document
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarde van soort document.
     *
     * @param soortDocument
     *            soort document
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        this.soortDocument = soortDocument;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * Geef de waarde van rubriek8220 datum document.
     *
     * @return rubriek8220 datum document
     */
    public Integer getRubriek8220DatumDocument() {
        return rubriek8220DatumDocument;
    }

    /**
     * Zet de waarde van rubriek8220 datum document.
     *
     * @param rubriek8220DatumDocument
     *            rubriek8220 datum document
     */
    public void setRubriek8220DatumDocument(final Integer rubriek8220DatumDocument) {
        this.rubriek8220DatumDocument = rubriek8220DatumDocument;
    }

    /**
     * Geef de waarde van document omschrijving.
     *
     * @return document omschrijving
     */
    public String getDocumentOmschrijving() {
        return documentOmschrijving;
    }

    /**
     * Zet de waarde van document omschrijving.
     *
     * @param documentOmschrijving
     *            document omschrijving
     */
    public void setDocumentOmschrijving(final String documentOmschrijving) {
        Validatie.controleerOpLegeWaarden("documentOmschrijving mag geen lege string zijn", documentOmschrijving);
        this.documentOmschrijving = documentOmschrijving;
    }

    /**
     * Geef de waarde van rubriek8310 aanduiding gegevens in onderzoek.
     *
     * @return rubriek8310 aanduiding gegevens in onderzoek
     */
    public Integer getRubriek8310AanduidingGegevensInOnderzoek() {
        return rubriek8310AanduidingGegevensInOnderzoek;
    }

    /**
     * Zet de waarde van rubriek8310 aanduiding gegevens in onderzoek.
     *
     * @param rubriek8310AanduidingGegevensInOnderzoek
     *            rubriek8310 aanduiding gegevens in onderzoek
     */
    public void setRubriek8310AanduidingGegevensInOnderzoek(final Integer rubriek8310AanduidingGegevensInOnderzoek) {
        this.rubriek8310AanduidingGegevensInOnderzoek = rubriek8310AanduidingGegevensInOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8320 datum ingang onderzoek.
     *
     * @return rubriek8320 datum ingang onderzoek
     */
    public Integer getRubriek8320DatumIngangOnderzoek() {
        return rubriek8320DatumIngangOnderzoek;
    }

    /**
     * Zet de waarde van rubriek8320 datum ingang onderzoek.
     *
     * @param rubriek8320DatumIngangOnderzoek
     *            rubriek8320 datum ingang onderzoek
     */
    public void setRubriek8320DatumIngangOnderzoek(final Integer rubriek8320DatumIngangOnderzoek) {
        this.rubriek8320DatumIngangOnderzoek = rubriek8320DatumIngangOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8330 datum einde onderzoek.
     *
     * @return rubriek8330 datum einde onderzoek
     */
    public Integer getRubriek8330DatumEindeOnderzoek() {
        return rubriek8330DatumEindeOnderzoek;
    }

    /**
     * Zet de waarde van rubriek8330 datum einde onderzoek.
     *
     * @param rubriek8330DatumEindeOnderzoek
     *            rubriek8330 datum einde onderzoek
     */
    public void setRubriek8330DatumEindeOnderzoek(final Integer rubriek8330DatumEindeOnderzoek) {
        this.rubriek8330DatumEindeOnderzoek = rubriek8330DatumEindeOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8410 onjuist of strijdig openbare orde.
     *
     * @return rubriek8410 onjuist of strijdig openbare orde
     */
    public Character getRubriek8410OnjuistOfStrijdigOpenbareOrde() {
        return rubriek8410OnjuistOfStrijdigOpenbareOrde;
    }

    /**
     * Zet de waarde van rubriek8410 onjuist of strijdig openbare orde.
     *
     * @param rubriek8410OnjuistOfStrijdigOpenbareOrde
     *            rubriek8410 onjuist of strijdig openbare orde
     */
    public void setRubriek8410OnjuistOfStrijdigOpenbareOrde(final Character rubriek8410OnjuistOfStrijdigOpenbareOrde) {
        this.rubriek8410OnjuistOfStrijdigOpenbareOrde = rubriek8410OnjuistOfStrijdigOpenbareOrde;
    }

    /**
     * Geef de waarde van rubriek8510 ingangsdatum geldigheid.
     *
     * @return rubriek8510 ingangsdatum geldigheid
     */
    public Integer getRubriek8510IngangsdatumGeldigheid() {
        return rubriek8510IngangsdatumGeldigheid;
    }

    /**
     * Zet de waarde van rubriek8510 ingangsdatum geldigheid.
     *
     * @param rubriek8510IngangsdatumGeldigheid
     *            rubriek8510 ingangsdatum geldigheid
     */
    public void setRubriek8510IngangsdatumGeldigheid(final Integer rubriek8510IngangsdatumGeldigheid) {
        this.rubriek8510IngangsdatumGeldigheid = rubriek8510IngangsdatumGeldigheid;
    }

    /**
     * Geef de waarde van rubriek8610 datum van opneming.
     *
     * @return rubriek8610 datum van opneming
     */
    public Integer getRubriek8610DatumVanOpneming() {
        return rubriek8610DatumVanOpneming;
    }

    /**
     * Zet de waarde van rubriek8610 datum van opneming.
     *
     * @param rubriek8610DatumVanOpneming
     *            rubriek8610 datum van opneming
     */
    public void setRubriek8610DatumVanOpneming(final Integer rubriek8610DatumVanOpneming) {
        this.rubriek8610DatumVanOpneming = rubriek8610DatumVanOpneming;
    }

    /**
     * Geef de waarde van rubriek6210 datum ingang familierechtelijke betrekking.
     *
     * @return rubriek6210 datum ingang familierechtelijke betrekking
     */
    public Integer getRubriek6210DatumIngangFamilierechtelijkeBetrekking() {
        return rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    }

    /**
     * Zet de waarde van rubriek6210 datum ingang familierechtelijke betrekking.
     *
     * @param rubriek6210DatumIngangFamilierechtelijkeBetrekking
     *            rubriek6210 datum ingang familierechtelijke betrekking
     */
    public void setRubriek6210DatumIngangFamilierechtelijkeBetrekking(final Integer rubriek6210DatumIngangFamilierechtelijkeBetrekking) {
        this.rubriek6210DatumIngangFamilierechtelijkeBetrekking = rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    }

    /* Categorie gerelateerden. */

    /**
     * Geef de waarde van aktenummer.
     *
     * @return aktenummer
     */
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * Zet de waarde van aktenummer.
     *
     * @param aktenummer
     *            aktenummer
     */
    public void setAktenummer(final String aktenummer) {
        Validatie.controleerOpLegeWaarden("aktenummer mag geen lege string zijn", aktenummer);
        this.aktenummer = aktenummer;
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
     * Geef de waarde van bsn.
     *
     * @return bsn
     */
    public Integer getBsn() {
        return bsn;
    }

    /**
     * Zet de waarde van bsn.
     *
     * @param bsn
     *            bsn
     */
    public void setBsn(final Integer bsn) {
        this.bsn = bsn;
    }

    /**
     * Geef de waarde van voornamen.
     *
     * @return voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarde van voornamen.
     *
     * @param voornamen
     *            voornamen
     */
    public void setVoornamen(final String voornamen) {
        Validatie.controleerOpLegeWaarden("voornamen mag geen lege string zijn", voornamen);
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van predicaat.
     *
     * @return predicaat
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarde van predicaat.
     *
     * @param predicaat
     *            predicaat
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarde van adellijke titel.
     *
     * @param adellijkeTitel
     *            adellijke titel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van geslachtsaanduiding bij adellijke titel of predikaat.
     *
     * @return geslachtsaanduiding bij adellijke titel of predikaat
     */
    public Geslachtsaanduiding getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingBijAdellijkeTitelOfPredikaatId);
    }

    /**
     * Zet de waarde van geslachtsaanduiding bij adellijke titel of predikaat.
     *
     * @param geslachtsaanduidingBijAdellijkeTitelOfPredikaat
     *            geslachtsaanduiding bij adellijke titel of predikaat
     */
    public void setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(final Geslachtsaanduiding geslachtsaanduidingBijAdellijkeTitelOfPredikaat) {
        if (geslachtsaanduidingBijAdellijkeTitelOfPredikaat == null) {
            geslachtsaanduidingBijAdellijkeTitelOfPredikaatId = null;
        } else {
            geslachtsaanduidingBijAdellijkeTitelOfPredikaatId = geslachtsaanduidingBijAdellijkeTitelOfPredikaat.getId();
        }
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarde van voorvoegsel.
     *
     * @param voorvoegsel
     *            voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        Validatie.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarde van scheidingsteken.
     *
     * @param scheidingsteken
     *            scheidingsteken
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van geslachtsnaamstam.
     *
     * @return geslachtsnaamstam
     */
    public String getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet de waarde van geslachtsnaamstam.
     *
     * @param geslachtsnaamstam
     *            geslachtsnaamstam
     */
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        Validatie.controleerOpLegeWaarden("geslachtsnaamstam mag geen lege string zijn", geslachtsnaamstam);
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van datum geboorte.
     *
     * @return datum geboorte
     */
    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Zet de waarde van datum geboorte.
     *
     * @param datumGeboorte
     *            datum geboorte
     */
    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Geef de waarde van gemeente geboorte.
     *
     * @return gemeente geboorte
     */
    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * Zet de waarde van gemeente geboorte.
     *
     * @param gemeenteGeboorte
     *            gemeente geboorte
     */
    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte.
     *
     * @return buitenlandse plaats geboorte
     */
    public String getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet de waarde van buitenlandse plaats geboorte.
     *
     * @param buitenlandsePlaatsGeboorte
     *            buitenlandse plaats geboorte
     */
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsGeboorte mag geen lege string zijn", buitenlandsePlaatsGeboorte);
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van omschrijving locatie geboorte.
     *
     * @return omschrijving locatie geboorte
     */
    public String getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * Zet de waarde van omschrijving locatie geboorte.
     *
     * @param omschrijvingLocatieGeboorte
     *            omschrijving locatie geboorte
     */
    public void setOmschrijvingLocatieGeboorte(final String omschrijvingLocatieGeboorte) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieGeboorte mag geen lege string zijn", omschrijvingLocatieGeboorte);
        this.omschrijvingLocatieGeboorte = omschrijvingLocatieGeboorte;
    }

    /**
     * Geef de waarde van land of gebied geboorte.
     *
     * @return land of gebied geboorte
     */
    public LandOfGebied getLandOfGebiedGeboorte() {
        return landOfGebiedGeboorte;
    }

    /**
     * Zet de waarde van land of gebied geboorte.
     *
     * @param landOfGebiedGeboorte
     *            land of gebied geboorte
     */
    public void setLandOfGebiedGeboorte(final LandOfGebied landOfGebiedGeboorte) {
        this.landOfGebiedGeboorte = landOfGebiedGeboorte;
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     *
     * @return geslachtsaanduiding
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarde van geslachtsaanduiding.
     *
     * @param geslachtsaanduiding
     *            geslachtsaanduiding
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            geslachtsaanduidingId = null;
        } else {
            geslachtsaanduidingId = geslachtsaanduiding.getId();
        }
    }

    /**
     * Geef de waarde van datum aanvang.
     *
     * @return datum aanvang
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarde van datum aanvang.
     *
     * @param datumAanvang
     *            datum aanvang
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /* Categorie huwelijk / geregistreerd partnerschap. */

    /**
     * Geef de waarde van gemeente aanvang.
     *
     * @return gemeente aanvang
     */
    public Gemeente getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Zet de waarde van gemeente aanvang.
     *
     * @param gemeenteAanvang
     *            gemeente aanvang
     */
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang.
     *
     * @return buitenlandse plaats aanvang
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Zet de waarde van buitenlandse plaats aanvang.
     *
     * @param buitenlandsePlaatsAanvang
     *            buitenlandse plaats aanvang
     */
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsAanvang mag geen lege string zijn", buitenlandsePlaatsAanvang);
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang.
     *
     * @return omschrijving locatie aanvang
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Zet de waarde van omschrijving locatie aanvang.
     *
     * @param omschrijvingLocatieAanvang
     *            omschrijving locatie aanvang
     */
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieAanvang mag geen lege string zijn", omschrijvingLocatieAanvang);
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van land of gebied aanvang.
     *
     * @return land of gebied aanvang
     */
    public LandOfGebied getLandOfGebiedAanvang() {
        return landOfGebiedAanvang;
    }

    /**
     * Zet de waarde van land of gebied aanvang.
     *
     * @param landOfGebiedAanvang
     *            land of gebied aanvang
     */
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        this.landOfGebiedAanvang = landOfGebiedAanvang;
    }

    /**
     * Geef de waarde van reden beeindiging relatie.
     *
     * @return reden beeindiging relatie
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarde van reden beeindiging relatie.
     *
     * @param redenBeeindigingRelatie
     *            reden beeindiging relatie
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van gemeente einde.
     *
     * @return gemeente einde
     */
    public Gemeente getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Zet de waarde van gemeente einde.
     *
     * @param gemeenteEinde
     *            gemeente einde
     */
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde.
     *
     * @return buitenlandse plaats einde
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Zet de waarde van buitenlandse plaats einde.
     *
     * @param buitenlandsePlaatsEinde
     *            buitenlandse plaats einde
     */
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsEinde mag geen lege string zijn", buitenlandsePlaatsEinde);
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie einde.
     *
     * @return omschrijving locatie einde
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Zet de waarde van omschrijving locatie einde.
     *
     * @param omschrijvingLocatieEinde
     *            omschrijving locatie einde
     */
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieEinde mag geen lege string zijn", omschrijvingLocatieEinde);
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van land of gebied einde.
     *
     * @return land of gebied einde
     */
    public LandOfGebied getLandOfGebiedEinde() {
        return landOfGebiedEinde;
    }

    /**
     * Zet de waarde van land of gebied einde.
     *
     * @param landOfGebiedEinde
     *            land of gebied einde
     */
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        this.landOfGebiedEinde = landOfGebiedEinde;
    }

    /**
     * Geef de waarde van soort relatie.
     *
     * @return soort relatie
     */
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.parseId(soortRelatieId);
    }

    /**
     * Zet de waarde van soort relatie.
     *
     * @param soortRelatie
     *            soort relatie
     */
    public void setSoortRelatie(final SoortRelatie soortRelatie) {
        if (soortRelatie == null) {
            soortRelatieId = null;
        } else {
            soortRelatieId = soortRelatie.getId();
        }
    }

    /* Categorie gezagsverhouding. */

    /**
     * Geef de waarde van indicatie ouder1 heeft gezag.
     *
     * @return indicatie ouder1 heeft gezag
     */
    public Boolean getIndicatieOuder1HeeftGezag() {
        return indicatieOuder1HeeftGezag;
    }

    /**
     * Zet de waarde van indicatie ouder1 heeft gezag.
     *
     * @param indicatieOuder1HeeftGezag
     *            indicatie ouder1 heeft gezag
     */
    public void setIndicatieOuder1HeeftGezag(final Boolean indicatieOuder1HeeftGezag) {
        this.indicatieOuder1HeeftGezag = indicatieOuder1HeeftGezag;
    }

    /**
     * Geef de waarde van indicatie ouder2 heeft gezag.
     *
     * @return indicatie ouder2 heeft gezag
     */
    public Boolean getIndicatieOuder2HeeftGezag() {
        return indicatieOuder2HeeftGezag;
    }

    /**
     * Zet de waarde van indicatie ouder2 heeft gezag.
     *
     * @param indicatieOuder2HeeftGezag
     *            indicatie ouder2 heeft gezag
     */
    public void setIndicatieOuder2HeeftGezag(final Boolean indicatieOuder2HeeftGezag) {
        this.indicatieOuder2HeeftGezag = indicatieOuder2HeeftGezag;
    }

    /**
     * Geef de waarde van indicatie derde heeft gezag.
     *
     * @return indicatie derde heeft gezag
     */
    public Boolean getIndicatieDerdeHeeftGezag() {
        return indicatieDerdeHeeftGezag;
    }

    /**
     * Zet de waarde van indicatie derde heeft gezag.
     *
     * @param indicatieDerdeHeeftGezag
     *            indicatie derde heeft gezag
     */
    public void setIndicatieDerdeHeeftGezag(final Boolean indicatieDerdeHeeftGezag) {
        this.indicatieDerdeHeeftGezag = indicatieDerdeHeeftGezag;
    }

    /**
     * Geef de waarde van indicatie onder curatele.
     *
     * @return indicatie onder curatele
     */
    public Boolean getIndicatieOnderCuratele() {
        return indicatieOnderCuratele;
    }

    /**
     * Zet de waarde van indicatie onder curatele.
     *
     * @param indicatieOnderCuratele
     *            indicatie onder curatele
     */
    public void setIndicatieOnderCuratele(final Boolean indicatieOnderCuratele) {
        this.indicatieOnderCuratele = indicatieOnderCuratele;
    }
}
