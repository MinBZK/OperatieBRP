/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the stapelvoorkomen database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "stapelvoorkomen", schema = "ist", uniqueConstraints = @UniqueConstraint(columnNames = {"stapel", "volgnr", "admhnd"}))
public class StapelVoorkomen extends AbstractEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int BSN_LENGTE = 9;
    private static final int ANUMMER_LENGTE = 10;

    /* Identiteit. */
    @Id
    @SequenceGenerator(name = "stapelvoorkomen_id_generator", sequenceName = "ist.seq_stapelvoorkomen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stapelvoorkomen_id_generator")
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "stapel", nullable = false)
    private Stapel stapel;

    @Column(name = "volgnr", nullable = false)
    private int volgnummer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    /* Standaard. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srtdoc")
    private SoortDocument soortDocument;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "anr", length = 10)
    private String anummer;

    @Column(name = "bsn", length = 9)
    private String bsn;

    @Column(length = 200)
    private String voornamen;

    @Column(name = "predicaat")
    private Integer predicaatId;

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    @Column(name = "geslachtbijadellijketitelpre")
    private Integer geslachtsaanduidingBijAdellijkeTitelOfPredikaatId;

    @Column(length = 10)
    private String voorvoegsel;

    @Column
    private Character scheidingsteken;

    @Column(name = "geslnaamstam", length = 200)
    private String geslachtsnaamstam;

    @Column(name = "datgeboorte")
    private Integer datumGeboorte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemgeboorte")
    private Gemeente gemeenteGeboorte;

    @Column(name = "blplaatsgeboorte", length = 40)
    private String buitenlandsePlaatsGeboorte;

    @Column(name = "omslocgeboorte", length = 40)
    private String omschrijvingLocatieGeboorte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedgeboorte")
    private LandOfGebied landOfGebiedGeboorte;

    @Column(name = "geslachtsaand")
    private Integer geslachtsaanduidingId;

    @Column(name = "dataanv")
    private Integer datumAanvang;

    /* Categorie huwelijk / geregistreerd partnerschap. */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemaanv")
    private Gemeente gemeenteAanvang;

    @Column(name = "blplaatsaanv", length = 40)
    private String buitenlandsePlaatsAanvang;

    @Column(name = "omslocaanv", length = 40)
    private String omschrijvingLocatieAanvang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedaanv")
    private LandOfGebied landOfGebiedAanvang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemeinde")
    private Gemeente gemeenteEinde;

    @Column(name = "blplaatseinde", length = 40)
    private String buitenlandsePlaatsEinde;

    @Column(name = "omsloceinde", length = 40)
    private String omschrijvingLocatieEinde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedeinde")
    private LandOfGebied landOfGebiedEinde;

    @Column(name = "srtrelatie")
    private Integer soortRelatieId;

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
    protected StapelVoorkomen() {}

    /**
     * Maakt een StapelVoorkomen object.
     *
     * @param stapel de stapel, mag niet null zijn
     * @param volgnummer het volgnummer van het voorkomen binnen een stapel beginnend bij 0
     *        (actueel), mag niet null zijn
     * @param administratieveHandeling de administratieveHandeling, mag niet null zijn
     */
    public StapelVoorkomen(final Stapel stapel, final int volgnummer, final AdministratieveHandeling administratieveHandeling) {
        setStapel(stapel);
        setVolgnummer(volgnummer);
        setAdministratieveHandeling(administratieveHandeling);
    }

    /* Identiteit. */

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van StapelVoorkomen.
     *
     * @param id de nieuwe waarde voor id van StapelVoorkomen
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van stapel van StapelVoorkomen.
     *
     * @return de waarde van stapel van StapelVoorkomen
     */
    public Stapel getStapel() {
        return stapel;
    }

    /**
     * Zet de waarden voor stapel van StapelVoorkomen.
     *
     * @param stapel de nieuwe waarde voor stapel van StapelVoorkomen
     */
    void setStapel(final Stapel stapel) {
        ValidationUtils.controleerOpNullWaarden("stapel mag niet null zijn", stapel);
        this.stapel = stapel;
    }

    /**
     * Geef de waarde van volgnummer van StapelVoorkomen.
     *
     * @return de waarde van volgnummer van StapelVoorkomen
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarden voor volgnummer van StapelVoorkomen.
     *
     * @param volgnummer de nieuwe waarde voor volgnummer van StapelVoorkomen
     */
    void setVolgnummer(final int volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef de waarde van administratieve handeling van StapelVoorkomen.
     *
     * @return de waarde van administratieve handeling van StapelVoorkomen
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarden voor administratieve handeling van StapelVoorkomen.
     *
     * @param administratieveHandeling de nieuwe waarde voor administratieve handeling van
     *        StapelVoorkomen
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /* Standaard. */

    /**
     * Geef de waarde van soort document van StapelVoorkomen.
     *
     * @return de waarde van soort document van StapelVoorkomen
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarden voor soort document van StapelVoorkomen.
     *
     * @param soortDocument de nieuwe waarde voor soort document van StapelVoorkomen
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        this.soortDocument = soortDocument;
    }

    /**
     * Geef de waarde van partij van StapelVoorkomen.
     *
     * @return de waarde van partij van StapelVoorkomen
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van StapelVoorkomen.
     *
     * @param partij de nieuwe waarde voor partij van StapelVoorkomen
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * Geef de waarde van rubriek8220 datum document van StapelVoorkomen.
     *
     * @return de waarde van rubriek8220 datum document van StapelVoorkomen
     */
    public Integer getRubriek8220DatumDocument() {
        return rubriek8220DatumDocument;
    }

    /**
     * Zet de waarden voor rubriek8220 datum document van StapelVoorkomen.
     *
     * @param rubriek8220DatumDocument de nieuwe waarde voor rubriek8220 datum document van
     *        StapelVoorkomen
     */
    public void setRubriek8220DatumDocument(final Integer rubriek8220DatumDocument) {
        this.rubriek8220DatumDocument = rubriek8220DatumDocument;
    }

    /**
     * Geef de waarde van document omschrijving van StapelVoorkomen.
     *
     * @return de waarde van document omschrijving van StapelVoorkomen
     */
    public String getDocumentOmschrijving() {
        return documentOmschrijving;
    }

    /**
     * Zet de waarden voor document omschrijving van StapelVoorkomen.
     *
     * @param documentOmschrijving de nieuwe waarde voor document omschrijving van StapelVoorkomen
     */
    public void setDocumentOmschrijving(final String documentOmschrijving) {
        ValidationUtils.controleerOpLegeWaarden("documentOmschrijving mag geen lege string zijn", documentOmschrijving);
        this.documentOmschrijving = documentOmschrijving;
    }

    /**
     * Geef de waarde van rubriek8310 aanduiding gegevens in onderzoek van StapelVoorkomen.
     *
     * @return de waarde van rubriek8310 aanduiding gegevens in onderzoek van StapelVoorkomen
     */
    public Integer getRubriek8310AanduidingGegevensInOnderzoek() {
        return rubriek8310AanduidingGegevensInOnderzoek;
    }

    /**
     * Zet de waarden voor rubriek8310 aanduiding gegevens in onderzoek van StapelVoorkomen.
     *
     * @param rubriek8310AanduidingGegevensInOnderzoek de nieuwe waarde voor rubriek8310 aanduiding
     *        gegevens in onderzoek van StapelVoorkomen
     */
    public void setRubriek8310AanduidingGegevensInOnderzoek(final Integer rubriek8310AanduidingGegevensInOnderzoek) {
        this.rubriek8310AanduidingGegevensInOnderzoek = rubriek8310AanduidingGegevensInOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8320 datum ingang onderzoek van StapelVoorkomen.
     *
     * @return de waarde van rubriek8320 datum ingang onderzoek van StapelVoorkomen
     */
    public Integer getRubriek8320DatumIngangOnderzoek() {
        return rubriek8320DatumIngangOnderzoek;
    }

    /**
     * Zet de waarden voor rubriek8320 datum ingang onderzoek van StapelVoorkomen.
     *
     * @param rubriek8320DatumIngangOnderzoek de nieuwe waarde voor rubriek8320 datum ingang
     *        onderzoek van StapelVoorkomen
     */
    public void setRubriek8320DatumIngangOnderzoek(final Integer rubriek8320DatumIngangOnderzoek) {
        this.rubriek8320DatumIngangOnderzoek = rubriek8320DatumIngangOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8330 datum einde onderzoek van StapelVoorkomen.
     *
     * @return de waarde van rubriek8330 datum einde onderzoek van StapelVoorkomen
     */
    public Integer getRubriek8330DatumEindeOnderzoek() {
        return rubriek8330DatumEindeOnderzoek;
    }

    /**
     * Zet de waarden voor rubriek8330 datum einde onderzoek van StapelVoorkomen.
     *
     * @param rubriek8330DatumEindeOnderzoek de nieuwe waarde voor rubriek8330 datum einde onderzoek
     *        van StapelVoorkomen
     */
    public void setRubriek8330DatumEindeOnderzoek(final Integer rubriek8330DatumEindeOnderzoek) {
        this.rubriek8330DatumEindeOnderzoek = rubriek8330DatumEindeOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8410 onjuist of strijdig openbare orde van StapelVoorkomen.
     *
     * @return de waarde van rubriek8410 onjuist of strijdig openbare orde van StapelVoorkomen
     */
    public Character getRubriek8410OnjuistOfStrijdigOpenbareOrde() {
        return rubriek8410OnjuistOfStrijdigOpenbareOrde;
    }

    /**
     * Zet de waarden voor rubriek8410 onjuist of strijdig openbare orde van StapelVoorkomen.
     *
     * @param rubriek8410OnjuistOfStrijdigOpenbareOrde de nieuwe waarde voor rubriek8410 onjuist of
     *        strijdig openbare orde van StapelVoorkomen
     */
    public void setRubriek8410OnjuistOfStrijdigOpenbareOrde(final Character rubriek8410OnjuistOfStrijdigOpenbareOrde) {
        this.rubriek8410OnjuistOfStrijdigOpenbareOrde = rubriek8410OnjuistOfStrijdigOpenbareOrde;
    }

    /**
     * Geef de waarde van rubriek8510 ingangsdatum geldigheid van StapelVoorkomen.
     *
     * @return de waarde van rubriek8510 ingangsdatum geldigheid van StapelVoorkomen
     */
    public Integer getRubriek8510IngangsdatumGeldigheid() {
        return rubriek8510IngangsdatumGeldigheid;
    }

    /**
     * Zet de waarden voor rubriek8510 ingangsdatum geldigheid van StapelVoorkomen.
     *
     * @param rubriek8510IngangsdatumGeldigheid de nieuwe waarde voor rubriek8510 ingangsdatum
     *        geldigheid van StapelVoorkomen
     */
    public void setRubriek8510IngangsdatumGeldigheid(final Integer rubriek8510IngangsdatumGeldigheid) {
        this.rubriek8510IngangsdatumGeldigheid = rubriek8510IngangsdatumGeldigheid;
    }

    /**
     * Geef de waarde van rubriek8610 datum van opneming van StapelVoorkomen.
     *
     * @return de waarde van rubriek8610 datum van opneming van StapelVoorkomen
     */
    public Integer getRubriek8610DatumVanOpneming() {
        return rubriek8610DatumVanOpneming;
    }

    /**
     * Zet de waarden voor rubriek8610 datum van opneming van StapelVoorkomen.
     *
     * @param rubriek8610DatumVanOpneming de nieuwe waarde voor rubriek8610 datum van opneming van
     *        StapelVoorkomen
     */
    public void setRubriek8610DatumVanOpneming(final Integer rubriek8610DatumVanOpneming) {
        this.rubriek8610DatumVanOpneming = rubriek8610DatumVanOpneming;
    }

    /**
     * Geef de waarde van rubriek6210 datum ingang familierechtelijke betrekking van
     * StapelVoorkomen.
     *
     * @return de waarde van rubriek6210 datum ingang familierechtelijke betrekking van
     *         StapelVoorkomen
     */
    public Integer getRubriek6210DatumIngangFamilierechtelijkeBetrekking() {
        return rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    }

    /**
     * Zet de waarden voor rubriek6210 datum ingang familierechtelijke betrekking van
     * StapelVoorkomen.
     *
     * @param rubriek6210DatumIngangFamilierechtelijkeBetrekking de nieuwe waarde voor rubriek6210
     *        datum ingang familierechtelijke betrekking van StapelVoorkomen
     */
    public void setRubriek6210DatumIngangFamilierechtelijkeBetrekking(final Integer rubriek6210DatumIngangFamilierechtelijkeBetrekking) {
        this.rubriek6210DatumIngangFamilierechtelijkeBetrekking = rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    }

    /* Categorie gerelateerden. */

    /**
     * Geef de waarde van aktenummer van StapelVoorkomen.
     *
     * @return de waarde van aktenummer van StapelVoorkomen
     */
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * Zet de waarden voor aktenummer van StapelVoorkomen.
     *
     * @param aktenummer de nieuwe waarde voor aktenummer van StapelVoorkomen
     */
    public void setAktenummer(final String aktenummer) {
        ValidationUtils.controleerOpLegeWaarden("aktenummer mag geen lege string zijn", aktenummer);
        this.aktenummer = aktenummer;
    }

    /**
     * Geef de waarde van anummer van StapelVoorkomen.
     *
     * @return de waarde van anummer van StapelVoorkomen
     */
    public String getAnummer() {
        return anummer;
    }

    /**
     * Zet de waarden voor anummer van StapelVoorkomen.
     *
     * @param anummer de nieuwe waarde voor anummer van StapelVoorkomen
     */
    public void setAnummer(final String anummer) {
        if (anummer != null) {
            ValidationUtils.controleerOpLengte("anummer moet lengte 10 hebben", anummer, ANUMMER_LENGTE);
        }
        this.anummer = anummer;
    }

    /**
     * Geef de waarde van bsn van StapelVoorkomen.
     *
     * @return de waarde van bsn van StapelVoorkomen
     */
    public String getBsn() {
        return bsn;
    }

    /**
     * Zet de waarden voor bsn van StapelVoorkomen.
     *
     * @param bsn de nieuwe waarde voor bsn van StapelVoorkomen
     */
    public void setBsn(final String bsn) {
        if (bsn != null) {
            ValidationUtils.controleerOpLengte("bsn moet lengte 9 hebben", bsn, BSN_LENGTE);
        }
        this.bsn = bsn;
    }

    /**
     * Geef de waarde van voornamen van StapelVoorkomen.
     *
     * @return de waarde van voornamen van StapelVoorkomen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarden voor voornamen van StapelVoorkomen.
     *
     * @param voornamen de nieuwe waarde voor voornamen van StapelVoorkomen
     */
    public void setVoornamen(final String voornamen) {
        ValidationUtils.controleerOpLegeWaarden("voornamen mag geen lege string zijn", voornamen);
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van predicaat van StapelVoorkomen.
     *
     * @return de waarde van predicaat van StapelVoorkomen
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarden voor predicaat van StapelVoorkomen.
     *
     * @param predicaat de nieuwe waarde voor predicaat van StapelVoorkomen
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
    }

    /**
     * Geef de waarde van adellijke titel van StapelVoorkomen.
     *
     * @return de waarde van adellijke titel van StapelVoorkomen
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarden voor adellijke titel van StapelVoorkomen.
     *
     * @param adellijkeTitel de nieuwe waarde voor adellijke titel van StapelVoorkomen
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van geslachtsaanduiding bij adellijke titel of predikaat van StapelVoorkomen.
     *
     * @return de waarde van geslachtsaanduiding bij adellijke titel of predikaat van
     *         StapelVoorkomen
     */
    public Geslachtsaanduiding getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingBijAdellijkeTitelOfPredikaatId);
    }

    /**
     * Zet de waarden voor geslachtsaanduiding bij adellijke titel of predikaat van StapelVoorkomen.
     *
     * @param geslachtsaanduidingBijAdellijkeTitelOfPredikaat de nieuwe waarde voor
     *        geslachtsaanduiding bij adellijke titel of predikaat van StapelVoorkomen
     */
    public void setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(final Geslachtsaanduiding geslachtsaanduidingBijAdellijkeTitelOfPredikaat) {
        if (geslachtsaanduidingBijAdellijkeTitelOfPredikaat == null) {
            geslachtsaanduidingBijAdellijkeTitelOfPredikaatId = null;
        } else {
            geslachtsaanduidingBijAdellijkeTitelOfPredikaatId = geslachtsaanduidingBijAdellijkeTitelOfPredikaat.getId();
        }
    }

    /**
     * Geef de waarde van voorvoegsel van StapelVoorkomen.
     *
     * @return de waarde van voorvoegsel van StapelVoorkomen
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van StapelVoorkomen.
     *
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van StapelVoorkomen
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van StapelVoorkomen.
     *
     * @return de waarde van scheidingsteken van StapelVoorkomen
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van StapelVoorkomen.
     *
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van StapelVoorkomen
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van StapelVoorkomen.
     *
     * @return de waarde van geslachtsnaamstam van StapelVoorkomen
     */
    public String getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet de waarden voor geslachtsnaamstam van StapelVoorkomen.
     *
     * @param geslachtsnaamstam de nieuwe waarde voor geslachtsnaamstam van StapelVoorkomen
     */
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        ValidationUtils.controleerOpLegeWaarden("geslachtsnaamstam mag geen lege string zijn", geslachtsnaamstam);
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van datum geboorte van StapelVoorkomen.
     *
     * @return de waarde van datum geboorte van StapelVoorkomen
     */
    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Zet de waarden voor datum geboorte van StapelVoorkomen.
     *
     * @param datumGeboorte de nieuwe waarde voor datum geboorte van StapelVoorkomen
     */
    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Geef de waarde van gemeente geboorte van StapelVoorkomen.
     *
     * @return de waarde van gemeente geboorte van StapelVoorkomen
     */
    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * Zet de waarden voor gemeente geboorte van StapelVoorkomen.
     *
     * @param gemeenteGeboorte de nieuwe waarde voor gemeente geboorte van StapelVoorkomen
     */
    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte van StapelVoorkomen.
     *
     * @return de waarde van buitenlandse plaats geboorte van StapelVoorkomen
     */
    public String getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet de waarden voor buitenlandse plaats geboorte van StapelVoorkomen.
     *
     * @param buitenlandsePlaatsGeboorte de nieuwe waarde voor buitenlandse plaats geboorte van
     *        StapelVoorkomen
     */
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsGeboorte mag geen lege string zijn", buitenlandsePlaatsGeboorte);
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van omschrijving locatie geboorte van StapelVoorkomen.
     *
     * @return de waarde van omschrijving locatie geboorte van StapelVoorkomen
     */
    public String getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * Zet de waarden voor omschrijving locatie geboorte van StapelVoorkomen.
     *
     * @param omschrijvingLocatieGeboorte de nieuwe waarde voor omschrijving locatie geboorte van
     *        StapelVoorkomen
     */
    public void setOmschrijvingLocatieGeboorte(final String omschrijvingLocatieGeboorte) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieGeboorte mag geen lege string zijn", omschrijvingLocatieGeboorte);
        this.omschrijvingLocatieGeboorte = omschrijvingLocatieGeboorte;
    }

    /**
     * Geef de waarde van land of gebied geboorte van StapelVoorkomen.
     *
     * @return de waarde van land of gebied geboorte van StapelVoorkomen
     */
    public LandOfGebied getLandOfGebiedGeboorte() {
        return landOfGebiedGeboorte;
    }

    /**
     * Zet de waarden voor land of gebied geboorte van StapelVoorkomen.
     *
     * @param landOfGebiedGeboorte de nieuwe waarde voor land of gebied geboorte van StapelVoorkomen
     */
    public void setLandOfGebiedGeboorte(final LandOfGebied landOfGebiedGeboorte) {
        this.landOfGebiedGeboorte = landOfGebiedGeboorte;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van StapelVoorkomen.
     *
     * @return de waarde van geslachtsaanduiding van StapelVoorkomen
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarden voor geslachtsaanduiding van StapelVoorkomen.
     *
     * @param geslachtsaanduiding de nieuwe waarde voor geslachtsaanduiding van StapelVoorkomen
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            geslachtsaanduidingId = null;
        } else {
            geslachtsaanduidingId = geslachtsaanduiding.getId();
        }
    }

    /**
     * Geef de waarde van datum aanvang van StapelVoorkomen.
     *
     * @return de waarde van datum aanvang van StapelVoorkomen
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarden voor datum aanvang van StapelVoorkomen.
     *
     * @param datumAanvang de nieuwe waarde voor datum aanvang van StapelVoorkomen
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /* Categorie huwelijk / geregistreerd partnerschap. */

    /**
     * Geef de waarde van gemeente aanvang van StapelVoorkomen.
     *
     * @return de waarde van gemeente aanvang van StapelVoorkomen
     */
    public Gemeente getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Zet de waarden voor gemeente aanvang van StapelVoorkomen.
     *
     * @param gemeenteAanvang de nieuwe waarde voor gemeente aanvang van StapelVoorkomen
     */
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang van StapelVoorkomen.
     *
     * @return de waarde van buitenlandse plaats aanvang van StapelVoorkomen
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Zet de waarden voor buitenlandse plaats aanvang van StapelVoorkomen.
     *
     * @param buitenlandsePlaatsAanvang de nieuwe waarde voor buitenlandse plaats aanvang van
     *        StapelVoorkomen
     */
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsAanvang mag geen lege string zijn", buitenlandsePlaatsAanvang);
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang van StapelVoorkomen.
     *
     * @return de waarde van omschrijving locatie aanvang van StapelVoorkomen
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Zet de waarden voor omschrijving locatie aanvang van StapelVoorkomen.
     *
     * @param omschrijvingLocatieAanvang de nieuwe waarde voor omschrijving locatie aanvang van
     *        StapelVoorkomen
     */
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieAanvang mag geen lege string zijn", omschrijvingLocatieAanvang);
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van land of gebied aanvang van StapelVoorkomen.
     *
     * @return de waarde van land of gebied aanvang van StapelVoorkomen
     */
    public LandOfGebied getLandOfGebiedAanvang() {
        return landOfGebiedAanvang;
    }

    /**
     * Zet de waarden voor land of gebied aanvang van StapelVoorkomen.
     *
     * @param landOfGebiedAanvang de nieuwe waarde voor land of gebied aanvang van StapelVoorkomen
     */
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        this.landOfGebiedAanvang = landOfGebiedAanvang;
    }

    /**
     * Geef de waarde van reden beeindiging relatie van StapelVoorkomen.
     *
     * @return de waarde van reden beeindiging relatie van StapelVoorkomen
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarden voor reden beeindiging relatie van StapelVoorkomen.
     *
     * @param redenBeeindigingRelatie de nieuwe waarde voor reden beeindiging relatie van
     *        StapelVoorkomen
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van datum einde van StapelVoorkomen.
     *
     * @return de waarde van datum einde van StapelVoorkomen
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van StapelVoorkomen.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van StapelVoorkomen
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van gemeente einde van StapelVoorkomen.
     *
     * @return de waarde van gemeente einde van StapelVoorkomen
     */
    public Gemeente getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Zet de waarden voor gemeente einde van StapelVoorkomen.
     *
     * @param gemeenteEinde de nieuwe waarde voor gemeente einde van StapelVoorkomen
     */
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde van StapelVoorkomen.
     *
     * @return de waarde van buitenlandse plaats einde van StapelVoorkomen
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Zet de waarden voor buitenlandse plaats einde van StapelVoorkomen.
     *
     * @param buitenlandsePlaatsEinde de nieuwe waarde voor buitenlandse plaats einde van
     *        StapelVoorkomen
     */
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsEinde mag geen lege string zijn", buitenlandsePlaatsEinde);
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie einde van StapelVoorkomen.
     *
     * @return de waarde van omschrijving locatie einde van StapelVoorkomen
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Zet de waarden voor omschrijving locatie einde van StapelVoorkomen.
     *
     * @param omschrijvingLocatieEinde de nieuwe waarde voor omschrijving locatie einde van
     *        StapelVoorkomen
     */
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieEinde mag geen lege string zijn", omschrijvingLocatieEinde);
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van land of gebied einde van StapelVoorkomen.
     *
     * @return de waarde van land of gebied einde van StapelVoorkomen
     */
    public LandOfGebied getLandOfGebiedEinde() {
        return landOfGebiedEinde;
    }

    /**
     * Zet de waarden voor land of gebied einde van StapelVoorkomen.
     *
     * @param landOfGebiedEinde de nieuwe waarde voor land of gebied einde van StapelVoorkomen
     */
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        this.landOfGebiedEinde = landOfGebiedEinde;
    }

    /**
     * Geef de waarde van soort relatie van StapelVoorkomen.
     *
     * @return de waarde van soort relatie van StapelVoorkomen
     */
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.parseId(soortRelatieId);
    }

    /**
     * Zet de waarden voor soort relatie van StapelVoorkomen.
     *
     * @param soortRelatie de nieuwe waarde voor soort relatie van StapelVoorkomen
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
     * Geef de waarde van indicatie ouder1 heeft gezag van StapelVoorkomen.
     *
     * @return de waarde van indicatie ouder1 heeft gezag van StapelVoorkomen
     */
    public Boolean getIndicatieOuder1HeeftGezag() {
        return indicatieOuder1HeeftGezag;
    }

    /**
     * Zet de waarden voor indicatie ouder1 heeft gezag van StapelVoorkomen.
     *
     * @param indicatieOuder1HeeftGezag de nieuwe waarde voor indicatie ouder1 heeft gezag van
     *        StapelVoorkomen
     */
    public void setIndicatieOuder1HeeftGezag(final Boolean indicatieOuder1HeeftGezag) {
        this.indicatieOuder1HeeftGezag = indicatieOuder1HeeftGezag;
    }

    /**
     * Geef de waarde van indicatie ouder2 heeft gezag van StapelVoorkomen.
     *
     * @return de waarde van indicatie ouder2 heeft gezag van StapelVoorkomen
     */
    public Boolean getIndicatieOuder2HeeftGezag() {
        return indicatieOuder2HeeftGezag;
    }

    /**
     * Zet de waarden voor indicatie ouder2 heeft gezag van StapelVoorkomen.
     *
     * @param indicatieOuder2HeeftGezag de nieuwe waarde voor indicatie ouder2 heeft gezag van
     *        StapelVoorkomen
     */
    public void setIndicatieOuder2HeeftGezag(final Boolean indicatieOuder2HeeftGezag) {
        this.indicatieOuder2HeeftGezag = indicatieOuder2HeeftGezag;
    }

    /**
     * Geef de waarde van indicatie derde heeft gezag van StapelVoorkomen.
     *
     * @return de waarde van indicatie derde heeft gezag van StapelVoorkomen
     */
    public Boolean getIndicatieDerdeHeeftGezag() {
        return indicatieDerdeHeeftGezag;
    }

    /**
     * Zet de waarden voor indicatie derde heeft gezag van StapelVoorkomen.
     *
     * @param indicatieDerdeHeeftGezag de nieuwe waarde voor indicatie derde heeft gezag van
     *        StapelVoorkomen
     */
    public void setIndicatieDerdeHeeftGezag(final Boolean indicatieDerdeHeeftGezag) {
        this.indicatieDerdeHeeftGezag = indicatieDerdeHeeftGezag;
    }

    /**
     * Geef de waarde van indicatie onder curatele van StapelVoorkomen.
     *
     * @return de waarde van indicatie onder curatele van StapelVoorkomen
     */
    public Boolean getIndicatieOnderCuratele() {
        return indicatieOnderCuratele;
    }

    /**
     * Zet de waarden voor indicatie onder curatele van StapelVoorkomen.
     *
     * @param indicatieOnderCuratele de nieuwe waarde voor indicatie onder curatele van
     *        StapelVoorkomen
     */
    public void setIndicatieOnderCuratele(final Boolean indicatieOnderCuratele) {
        this.indicatieOnderCuratele = indicatieOnderCuratele;
    }
}
