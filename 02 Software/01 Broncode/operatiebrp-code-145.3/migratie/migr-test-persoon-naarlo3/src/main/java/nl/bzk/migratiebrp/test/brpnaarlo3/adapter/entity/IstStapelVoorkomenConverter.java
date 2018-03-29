/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.GemeenteConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.LandOfGebiedConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.PartijConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.RedenBeeindigingRelatieConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.SoortDocumentConverter;

import org.springframework.stereotype.Component;

/**
 * IST StapelVoorkomen Converter.
 */
@Component
public final class IstStapelVoorkomenConverter extends EntityConverter {

    /**
     * HEADER_TYPE.
     **/
    static final String HEADER_TYPE = "ist.stapelvoorkomen";
    /**
     * HEADER_STAPEL.
     **/
    static final String HEADER_STAPEL = "stapel";
    /**
     * HEADER_VOLGNUMMER.
     **/
    static final String HEADER_VOLGNUMMER = "volgnr";
    /**
     * HEADER_ADMINISTRATIEVE_HANDELING.
     **/
    static final String HEADER_ADMINISTRATIEVE_HANDELING = "admhnd";
    /**
     * HEADER_SOORT_DOCUMENTATIE.
     **/
    static final String HEADER_SOORT_DOCUMENTATIE = "srtdoc";
    /**
     * HEADER_RUBRIEK_8220.
     **/
    static final String HEADER_RUBRIEK_8220 = "rubr8220datdoc";
    /**
     * HEADER_DOCUMENTATIE_OMSCHRIJVING.
     **/
    static final String HEADER_DOCUMENTATIE_OMSCHRIJVING = "docoms";
    /**
     * HEADER_RUBRIEK_8310.
     **/
    static final String HEADER_RUBRIEK_8310 = "rubr8310aandgegevensinonderz";
    /**
     * HEADER_RUBRIEK_8320.
     **/
    static final String HEADER_RUBRIEK_8320 = "rubr8320datingangonderzoek";
    /**
     * HEADER_RUBRIEK_8330.
     **/
    static final String HEADER_RUBRIEK_8330 = "rubr8330dateindeonderzoek";
    /**
     * HEADER_RUBRIEK_8410.
     **/
    static final String HEADER_RUBRIEK_8410 = "rub8410onjuiststrijdigopenb";
    /**
     * HEADER_RUBRIEK_8510.
     **/
    static final String HEADER_RUBRIEK_8510 = "rubr8510ingangsdatgel";
    /**
     * HEADER_RUBRIEK_8610.
     **/
    static final String HEADER_RUBRIEK_8610 = "rubr8610datvanopneming";
    /**
     * HEADER_RUBRIEK_6210.
     **/
    static final String HEADER_RUBRIEK_6210 = "rub6210datingangfamilierech";
    /**
     * HEADER_AKTE_NUMMER.
     **/
    static final String HEADER_AKTE_NUMMER = "aktenr";
    /**
     * HEADER_ANUMMER.
     **/
    static final String HEADER_ANUMMER = "anr";
    /**
     * HEADER_BSN.
     **/
    static final String HEADER_BSN = "bsn";
    /**
     * HEADER_VOORNAMEN.
     **/
    static final String HEADER_VOORNAMEN = "voornamen";
    /**
     * HEADER_PREDICAAT.
     **/
    static final String HEADER_PREDICAAT = "predicaat";
    /**
     * HEADER_ADELLIJKE_TITEL.
     **/
    static final String HEADER_ADELLIJKE_TITEL = "adellijketitel";
    /**
     * HEADER_GESLACHT_BIJ_ADELLIJKE_TITEL_PREDICAAT.
     **/
    static final String HEADER_GESLACHT_BIJ_ADELLIJKE_TITEL_PREDICAAT = "geslachtbijadellijketitelpre";
    /**
     * HEADER_VOORVOEGSEL.
     **/
    static final String HEADER_VOORVOEGSEL = "voorvoegsel";
    /**
     * HEADER_SCHEIDINGSTEKEN.
     **/
    static final String HEADER_SCHEIDINGSTEKEN = "scheidingsteken";
    /**
     * HEADER_GESLACHTSNAAMSTAM.
     **/
    static final String HEADER_GESLACHTSNAAMSTAM = "geslnaamstam";
    /**
     * HEADER_DATUM_GEBOORTE.
     **/
    static final String HEADER_DATUM_GEBOORTE = "datgeboorte";
    /**
     * HEADER_GEMEENTE_GEBOORTE.
     **/
    static final String HEADER_GEMEENTE_GEBOORTE = "gemgeboorte";
    /**
     * HEADER_BUITENLANDSE_PLAATS_GEBOORTE.
     **/
    static final String HEADER_BUITENLANDSE_PLAATS_GEBOORTE = "blplaatsgeboorte";
    /**
     * HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE.
     **/
    static final String HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE = "omslocgeboorte";
    /**
     * HEADER_LAND_OF_GEBIED_GEBOORTE.
     **/
    static final String HEADER_LAND_OF_GEBIED_GEBOORTE = "landgebiedgeboorte";
    /**
     * HEADER_GESLACHTS_AANDUIDING.
     **/
    static final String HEADER_GESLACHTS_AANDUIDING = "geslachtsaand";
    /**
     * HEADER_DATUM_AANVANG.
     **/
    static final String HEADER_DATUM_AANVANG = "dataanv";
    /**
     * HEADER_GEMEENTE_AANVANG.
     **/
    static final String HEADER_GEMEENTE_AANVANG = "gemaanv";
    /**
     * HEADER_BUITENLANDSE_PLAATS_AANVANG.
     **/
    static final String HEADER_BUITENLANDSE_PLAATS_AANVANG = "blplaatsaanv";
    /**
     * HEADER_OMSCHRIJVING_LOCATIE_AANVANG.
     **/
    static final String HEADER_OMSCHRIJVING_LOCATIE_AANVANG = "omslocaanv";
    /**
     * HEADER_LAND_OF_GEBIED_AANVANG.
     **/
    static final String HEADER_LAND_OF_GEBIED_AANVANG = "landgebiedaanv";
    /**
     * HEADER_REDEN_EINDE.
     **/
    static final String HEADER_REDEN_EINDE = "rdneinde";
    /**
     * HEADER_DATUM_EINDE.
     **/
    static final String HEADER_DATUM_EINDE = "dateinde";
    /**
     * HEADER_GEMEENTE_EINDE.
     **/
    static final String HEADER_GEMEENTE_EINDE = "gemeinde";
    /**
     * HEADER_BUITENLANDSE_PLAATS_EINDE.
     **/
    static final String HEADER_BUITENLANDSE_PLAATS_EINDE = "blplaatseinde";
    /**
     * HEADER_OMSCHRIJVING_LOCATIE_EINDE.
     **/
    static final String HEADER_OMSCHRIJVING_LOCATIE_EINDE = "omsloceinde";
    /**
     * HEADER_LAND_OF_GEBIED_EINDE.
     **/
    static final String HEADER_LAND_OF_GEBIED_EINDE = "landgebiedeinde";
    /**
     * HEADER_SOORT_RELATIE.
     **/
    static final String HEADER_SOORT_RELATIE = "srtrelatie";
    /**
     * HEADER_INDICATIE_OUDER1_HEEFT_GEZAG.
     **/
    static final String HEADER_INDICATIE_OUDER1_HEEFT_GEZAG = "indouder1heeftgezag";
    /**
     * HEADER_INDICATIE_OUDER2_HEEFT_GEZAG.
     **/
    static final String HEADER_INDICATIE_OUDER2_HEEFT_GEZAG = "indouder2heeftgezag";
    /**
     * HEADER_INDICATIE_DERDE_HEEFT_GEZAG.
     **/
    static final String HEADER_INDICATIE_DERDE_HEEFT_GEZAG = "indderdeheeftgezag";
    /**
     * HEADER_INDICATIE_ONDER_CURATELE.
     **/
    static final String HEADER_INDICATIE_ONDER_CURATELE = "indondercuratele";

    @Inject
    private SoortDocumentConverter soortDocumentConverter;
    @Inject
    private GemeenteConverter gemeenteConverter;
    @Inject
    private LandOfGebiedConverter landOfGebiedConverter;
    @Inject
    private RedenBeeindigingRelatieConverter redenBeeindigingRelatieConverter;
    @Inject
    private PartijConverter partijConverter;

    private Stapel stapel;
    private Integer volgnummer;
    private AdministratieveHandeling administratieveHandeling;
    private SoortDocument soortDocument;
    private Partij partij;
    private Integer rubriek8220;
    private String documentOmschrijving;
    private Integer rubriek8310;
    private Integer rubriek8320;
    private Integer rubriek8330;
    private Character rubriek8410;
    private Integer rubriek8510;
    private Integer rubriek8610;
    private Integer rubriek6210;
    private String aktenummer;
    private String anummer;
    private String bsn;
    private String voornamen;
    private Predicaat predicaat;
    private AdellijkeTitel adellijkeTitel;
    private Geslachtsaanduiding geslachtsaanduidingBijAdellijkeTitelOfPredicaat;
    private String voorvoegsel;
    private Character scheidingsteken;
    private String geslachtsnaamstam;
    private Integer datumGeboorte;
    private Gemeente gemeenteGeboorte;
    private String buitenlandsePlaatsGeboorte;
    private String omschrijvingLocatieGeboorte;
    private LandOfGebied landOfGebiedGeboorte;
    private Geslachtsaanduiding geslachtsaanduiding;
    private Integer datumAanvang;
    private Gemeente gemeenteAanvang;
    private String buitenlandsePlaatsAanvang;
    private String omschrijvingLocatieAanvang;
    private LandOfGebied landOfGebiedAanvang;
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    private Integer datumEinde;
    private Gemeente gemeenteEinde;
    private String buitenlandsePlaatsEinde;
    private String omschrijvingLocatieEinde;
    private LandOfGebied landOfGebiedEinde;
    private SoortRelatie soortRelatie;
    private Boolean indicatieOuder1HeeftGezag;
    private Boolean indicatieOuder2HeeftGezag;
    private Boolean indicatieDerdeHeeftGezag;
    private Boolean indicatieOnderCuratele;

    /**
     * Default constructor.
     */
    IstStapelVoorkomenConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_STAPEL:
                stapel = context.getStapel(Integer.parseInt(value));
                break;
            case HEADER_VOLGNUMMER:
                volgnummer = Integer.valueOf(value);
                break;
            case HEADER_ADMINISTRATIEVE_HANDELING:
                administratieveHandeling = context.getAdministratieveHandeling(Integer.parseInt(value));
                break;
            case HEADER_SOORT_DOCUMENTATIE:
                soortDocument = soortDocumentConverter.convert(value);
                break;
            case HEADER_PARTIJ:
                partij = partijConverter.convert(value);
                break;
            case HEADER_RUBRIEK_8220:
                rubriek8220 = Integer.valueOf(value);
                break;
            case HEADER_DOCUMENTATIE_OMSCHRIJVING:
                documentOmschrijving = value;
                break;
            case HEADER_RUBRIEK_8310:
                rubriek8310 = Integer.valueOf(value);
                break;
            case HEADER_RUBRIEK_8320:
                rubriek8320 = Integer.valueOf(value);
                break;
            case HEADER_RUBRIEK_8330:
                rubriek8330 = Integer.valueOf(value);
                break;
            case HEADER_RUBRIEK_8410:
                rubriek8410 = isEmpty(value) ? null : value.charAt(0);
                break;
            case HEADER_RUBRIEK_8510:
                rubriek8510 = Integer.valueOf(value);
                break;
            case HEADER_RUBRIEK_8610:
                rubriek8610 = Integer.valueOf(value);
                break;
            case HEADER_RUBRIEK_6210:
                rubriek6210 = Integer.valueOf(value);
                break;
            case HEADER_AKTE_NUMMER:
                aktenummer = value;
                break;
            case HEADER_ANUMMER:
                anummer = value;
                break;
            case HEADER_BSN:
                bsn = value;
                break;
            case HEADER_VOORNAMEN:
                voornamen = value;
                break;
            case HEADER_PREDICAAT:
                predicaat = Predicaat.parseId(Integer.valueOf(value));
                break;
            case HEADER_ADELLIJKE_TITEL:
                adellijkeTitel = AdellijkeTitel.parseId(Integer.valueOf(value));
                break;
            case HEADER_GESLACHT_BIJ_ADELLIJKE_TITEL_PREDICAAT:
                geslachtsaanduidingBijAdellijkeTitelOfPredicaat = Geslachtsaanduiding.parseId(Integer.valueOf(value));
                break;
            case HEADER_VOORVOEGSEL:
                voorvoegsel = value;
                break;
            case HEADER_SCHEIDINGSTEKEN:
                scheidingsteken = isEmpty(value) ? null : value.charAt(0);
                break;
            case HEADER_GESLACHTSNAAMSTAM:
                geslachtsnaamstam = value;
                break;
            case HEADER_DATUM_GEBOORTE:
                datumGeboorte = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_GEBOORTE:
                gemeenteGeboorte = gemeenteConverter.convert(value);
                break;
            case HEADER_BUITENLANDSE_PLAATS_GEBOORTE:
                buitenlandsePlaatsGeboorte = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE:
                omschrijvingLocatieGeboorte = value;
                break;
            case HEADER_LAND_OF_GEBIED_GEBOORTE:
                landOfGebiedGeboorte = landOfGebiedConverter.convert(value);
                break;
            case HEADER_GESLACHTS_AANDUIDING:
                geslachtsaanduiding = Geslachtsaanduiding.parseId(Integer.valueOf(value));
                break;
            case HEADER_DATUM_AANVANG:
                datumAanvang = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_AANVANG:
                gemeenteAanvang = gemeenteConverter.convert(value);
                break;
            case HEADER_BUITENLANDSE_PLAATS_AANVANG:
                buitenlandsePlaatsAanvang = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_AANVANG:
                omschrijvingLocatieAanvang = value;
                break;
            case HEADER_LAND_OF_GEBIED_AANVANG:
                landOfGebiedAanvang = landOfGebiedConverter.convert(value);
                break;
            case HEADER_REDEN_EINDE:
                redenBeeindigingRelatie = redenBeeindigingRelatieConverter.convert(value);
                break;
            case HEADER_DATUM_EINDE:
                datumEinde = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_EINDE:
                gemeenteEinde = gemeenteConverter.convert(value);
                break;
            case HEADER_BUITENLANDSE_PLAATS_EINDE:
                buitenlandsePlaatsEinde = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_EINDE:
                omschrijvingLocatieEinde = value;
                break;
            case HEADER_LAND_OF_GEBIED_EINDE:
                landOfGebiedEinde = landOfGebiedConverter.convert(value);
                break;
            case HEADER_SOORT_RELATIE:
                soortRelatie = SoortRelatie.parseId(Integer.valueOf(value));
                break;
            case HEADER_INDICATIE_OUDER1_HEEFT_GEZAG:
                indicatieOuder1HeeftGezag = Boolean.valueOf(value);
                break;
            case HEADER_INDICATIE_OUDER2_HEEFT_GEZAG:
                indicatieOuder2HeeftGezag = Boolean.valueOf(value);
                break;
            case HEADER_INDICATIE_DERDE_HEEFT_GEZAG:
                indicatieDerdeHeeftGezag = Boolean.valueOf(value);
                break;
            case HEADER_INDICATIE_ONDER_CURATELE:
                indicatieOnderCuratele = Boolean.valueOf(value);
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, volgnummer, administratieveHandeling);
        voorkomen.setAdellijkeTitel(adellijkeTitel);
        voorkomen.setAktenummer(aktenummer);
        voorkomen.setAnummer(anummer);
        voorkomen.setBsn(bsn);
        voorkomen.setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
        voorkomen.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
        voorkomen.setBuitenlandsePlaatsGeboorte(buitenlandsePlaatsGeboorte);
        voorkomen.setDatumAanvang(datumAanvang);
        voorkomen.setDatumEinde(datumEinde);
        voorkomen.setDatumGeboorte(datumGeboorte);
        voorkomen.setDocumentOmschrijving(documentOmschrijving);
        voorkomen.setGemeenteAanvang(gemeenteAanvang);
        voorkomen.setGemeenteEinde(gemeenteEinde);
        voorkomen.setGemeenteGeboorte(gemeenteGeboorte);
        voorkomen.setGeslachtsaanduiding(geslachtsaanduiding);
        voorkomen.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(geslachtsaanduidingBijAdellijkeTitelOfPredicaat);
        voorkomen.setGeslachtsnaamstam(geslachtsnaamstam);
        voorkomen.setIndicatieDerdeHeeftGezag(indicatieDerdeHeeftGezag);
        voorkomen.setIndicatieOnderCuratele(indicatieOnderCuratele);
        voorkomen.setIndicatieOuder1HeeftGezag(indicatieOuder1HeeftGezag);
        voorkomen.setIndicatieOuder2HeeftGezag(indicatieOuder2HeeftGezag);
        voorkomen.setLandOfGebiedAanvang(landOfGebiedAanvang);
        voorkomen.setLandOfGebiedEinde(landOfGebiedEinde);
        voorkomen.setLandOfGebiedGeboorte(landOfGebiedGeboorte);
        voorkomen.setOmschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
        voorkomen.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);
        voorkomen.setOmschrijvingLocatieGeboorte(omschrijvingLocatieGeboorte);
        voorkomen.setPartij(partij);
        voorkomen.setPredicaat(predicaat);
        voorkomen.setRedenBeeindigingRelatie(redenBeeindigingRelatie);
        voorkomen.setRubriek6210DatumIngangFamilierechtelijkeBetrekking(rubriek6210);
        voorkomen.setRubriek8220DatumDocument(rubriek8220);
        voorkomen.setRubriek8310AanduidingGegevensInOnderzoek(rubriek8310);
        voorkomen.setRubriek8320DatumIngangOnderzoek(rubriek8320);
        voorkomen.setRubriek8330DatumEindeOnderzoek(rubriek8330);
        voorkomen.setRubriek8410OnjuistOfStrijdigOpenbareOrde(rubriek8410);
        voorkomen.setRubriek8510IngangsdatumGeldigheid(rubriek8510);
        voorkomen.setRubriek8610DatumVanOpneming(rubriek8610);
        voorkomen.setScheidingsteken(scheidingsteken);
        voorkomen.setSoortDocument(soortDocument);
        voorkomen.setSoortRelatie(soortRelatie);
        voorkomen.setVoornamen(voornamen);
        voorkomen.setVoorvoegsel(voorvoegsel);

        stapel.addStapelVoorkomen(voorkomen);
    }

    @Override
    protected void resetConverter() {
        stapel = null;
        volgnummer = null;
        soortDocument = null;
        partij = null;
        rubriek8220 = null;
        documentOmschrijving = null;
        rubriek8310 = null;
        rubriek8320 = null;
        rubriek8330 = null;
        rubriek8410 = null;
        rubriek8510 = null;
        rubriek8610 = null;
        rubriek6210 = null;
        aktenummer = null;
        anummer = null;
        bsn = null;
        voornamen = null;
        predicaat = null;
        adellijkeTitel = null;
        geslachtsaanduidingBijAdellijkeTitelOfPredicaat = null;
        voorvoegsel = null;
        scheidingsteken = null;
        geslachtsnaamstam = null;
        datumGeboorte = null;
        gemeenteGeboorte = null;
        buitenlandsePlaatsGeboorte = null;
        omschrijvingLocatieGeboorte = null;
        landOfGebiedGeboorte = null;
        geslachtsaanduiding = null;
        datumAanvang = null;
        gemeenteAanvang = null;
        buitenlandsePlaatsAanvang = null;
        omschrijvingLocatieAanvang = null;
        landOfGebiedAanvang = null;
        redenBeeindigingRelatie = null;
        datumEinde = null;
        gemeenteEinde = null;
        buitenlandsePlaatsEinde = null;
        omschrijvingLocatieEinde = null;
        landOfGebiedEinde = null;
        soortRelatie = null;
        indicatieOuder1HeeftGezag = null;
        indicatieOuder2HeeftGezag = null;
        indicatieDerdeHeeftGezag = null;
        indicatieOnderCuratele = null;
    }
}
