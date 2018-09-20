/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieGerelateerdenGroepBasis;

/**
 * Gegevens in deze groep komen uit categorie 02, 03, 05 of 09.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenCategorieGerelateerdenGroepModel implements StapelVoorkomenCategorieGerelateerdenGroepBasis {

    @Embedded
    @AttributeOverride(name = AktenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Aktenr"))
    @JsonProperty
    private AktenummerAttribuut aktenummer;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ANr"))
    @JsonProperty
    private AdministratienummerAttribuut administratienummer;

    @Embedded
    @AttributeOverride(name = BurgerservicenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BSN"))
    @JsonProperty
    private BurgerservicenummerAttribuut burgerservicenummer;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voornamen"))
    @JsonProperty
    private VoornamenAttribuut voornamen;

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Predicaat"))
    @JsonProperty
    private PredicaatAttribuut predicaat;

    @Embedded
    @AssociationOverride(name = AdellijkeTitelAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AdellijkeTitel"))
    @JsonProperty
    private AdellijkeTitelAttribuut adellijkeTitel;

    @Embedded
    @AttributeOverride(name = GeslachtsaanduidingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "GeslachtBijAdellijkeTitelPre"))
    @JsonProperty
    private GeslachtsaanduidingAttribuut geslachtBijAdellijkeTitelPredicaat;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingsteken;

    @Embedded
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Geslnaamstam"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatGeboorte"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumGeboorte;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemGeboorte"))
    @JsonProperty
    private GemeenteAttribuut gemeenteGeboorte;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsGeboorte"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocGeboorte"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedGeboorte"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedGeboorte;

    @Embedded
    @AttributeOverride(name = GeslachtsaanduidingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Geslachtsaand"))
    @JsonProperty
    private GeslachtsaanduidingAttribuut geslachtsaanduiding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelVoorkomenCategorieGerelateerdenGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param aktenummer aktenummer van Categorie gerelateerden.
     * @param administratienummer administratienummer van Categorie gerelateerden.
     * @param burgerservicenummer burgerservicenummer van Categorie gerelateerden.
     * @param voornamen voornamen van Categorie gerelateerden.
     * @param predicaat predicaat van Categorie gerelateerden.
     * @param adellijkeTitel adellijkeTitel van Categorie gerelateerden.
     * @param geslachtBijAdellijkeTitelPredicaat geslachtBijAdellijkeTitelPredicaat van Categorie gerelateerden.
     * @param voorvoegsel voorvoegsel van Categorie gerelateerden.
     * @param scheidingsteken scheidingsteken van Categorie gerelateerden.
     * @param geslachtsnaamstam geslachtsnaamstam van Categorie gerelateerden.
     * @param datumGeboorte datumGeboorte van Categorie gerelateerden.
     * @param gemeenteGeboorte gemeenteGeboorte van Categorie gerelateerden.
     * @param buitenlandsePlaatsGeboorte buitenlandsePlaatsGeboorte van Categorie gerelateerden.
     * @param omschrijvingLocatieGeboorte omschrijvingLocatieGeboorte van Categorie gerelateerden.
     * @param landGebiedGeboorte landGebiedGeboorte van Categorie gerelateerden.
     * @param geslachtsaanduiding geslachtsaanduiding van Categorie gerelateerden.
     */
    public AbstractStapelVoorkomenCategorieGerelateerdenGroepModel(
        final AktenummerAttribuut aktenummer,
        final AdministratienummerAttribuut administratienummer,
        final BurgerservicenummerAttribuut burgerservicenummer,
        final VoornamenAttribuut voornamen,
        final PredicaatAttribuut predicaat,
        final AdellijkeTitelAttribuut adellijkeTitel,
        final GeslachtsaanduidingAttribuut geslachtBijAdellijkeTitelPredicaat,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam,
        final DatumEvtDeelsOnbekendAttribuut datumGeboorte,
        final GemeenteAttribuut gemeenteGeboorte,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte,
        final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte,
        final LandGebiedAttribuut landGebiedGeboorte,
        final GeslachtsaanduidingAttribuut geslachtsaanduiding)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.aktenummer = aktenummer;
        this.administratienummer = administratienummer;
        this.burgerservicenummer = burgerservicenummer;
        this.voornamen = voornamen;
        this.predicaat = predicaat;
        this.adellijkeTitel = adellijkeTitel;
        this.geslachtBijAdellijkeTitelPredicaat = geslachtBijAdellijkeTitelPredicaat;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;
        this.datumGeboorte = datumGeboorte;
        this.gemeenteGeboorte = gemeenteGeboorte;
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
        this.omschrijvingLocatieGeboorte = omschrijvingLocatieGeboorte;
        this.landGebiedGeboorte = landGebiedGeboorte;
        this.geslachtsaanduiding = geslachtsaanduiding;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AktenummerAttribuut getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getAdministratienummer() {
        return administratienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoornamenAttribuut getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PredicaatAttribuut getPredicaat() {
        return predicaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitelAttribuut getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsaanduidingAttribuut getGeslachtBijAdellijkeTitelPredicaat() {
        return geslachtBijAdellijkeTitelPredicaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedGeboorte() {
        return landGebiedGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsaanduidingAttribuut getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (aktenummer != null) {
            attributen.add(aktenummer);
        }
        if (administratienummer != null) {
            attributen.add(administratienummer);
        }
        if (burgerservicenummer != null) {
            attributen.add(burgerservicenummer);
        }
        if (voornamen != null) {
            attributen.add(voornamen);
        }
        if (predicaat != null) {
            attributen.add(predicaat);
        }
        if (adellijkeTitel != null) {
            attributen.add(adellijkeTitel);
        }
        if (geslachtBijAdellijkeTitelPredicaat != null) {
            attributen.add(geslachtBijAdellijkeTitelPredicaat);
        }
        if (voorvoegsel != null) {
            attributen.add(voorvoegsel);
        }
        if (scheidingsteken != null) {
            attributen.add(scheidingsteken);
        }
        if (geslachtsnaamstam != null) {
            attributen.add(geslachtsnaamstam);
        }
        if (datumGeboorte != null) {
            attributen.add(datumGeboorte);
        }
        if (gemeenteGeboorte != null) {
            attributen.add(gemeenteGeboorte);
        }
        if (buitenlandsePlaatsGeboorte != null) {
            attributen.add(buitenlandsePlaatsGeboorte);
        }
        if (omschrijvingLocatieGeboorte != null) {
            attributen.add(omschrijvingLocatieGeboorte);
        }
        if (landGebiedGeboorte != null) {
            attributen.add(landGebiedGeboorte);
        }
        if (geslachtsaanduiding != null) {
            attributen.add(geslachtsaanduiding);
        }
        return attributen;
    }

}
