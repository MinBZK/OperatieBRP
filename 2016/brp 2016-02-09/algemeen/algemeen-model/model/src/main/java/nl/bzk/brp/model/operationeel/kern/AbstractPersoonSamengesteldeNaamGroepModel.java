/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroepBasis;

/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een
 * Persoon.
 *
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en
 * Geslachtsnaamcomponent van een Persoon. In uitzonderingssituaties is dat niet mogelijk.
 *
 * De groep Samengestelde naam bevat de naam zoals die is opgebouwd uit de naamgegevens uit de groepen voornaam en
 * geslachtsnaamcomponent. Deze samengestelde gegevens hoeven bij het bijhouden van de groepen voornaam en
 * geslachtsnaamcomponent niet door de voor de bijhouding verantwoordelijke partij te worden ingevoerd. De centrale
 * voorzieningen stellen de gegevens uit de groep samengestelde naam op dat moment samen op basis van de groepen
 * voornaam en geslachtsnaamcomponent volgens het onderstaande voorschrift:
 *
 * Voornamen - de naam zoals opgenomen in de voornaam met volgnummer één, gevolgd de naam zoals opgenomen in de actuele
 * groep voornaam met volgnummer twee, enzovoort. De voornamen worden gescheiden door een spatie. Merk op dat de BRP is
 * voorbereid op het opnemen van voornamen als 'Jan Peter' of 'Aberto di Maria' of 'Wonder op aarde' als één enkele
 * voornaam; in de BRP is het namelijk niet nodig (om conform LO 3.x) de verschillende worden aan elkaar te plakken met
 * een koppelteken. Predicaat - het predicaat dat door de persoon gevoerd wordt voor diens voornaam. Dit komt overeen
 * met het predicaat van de eerste geslachtsnaamcomponent. Indien voor een persoon meerdere predikaten van toepassing
 * is, het predikaar dat voor de voornamen geplaatst mag worden. Adelijke titel - de adelijke titel zoals opgenomen in
 * geslachtsnaamcomponent met volgnummer gelijk aan '1'; Voorvoegsel - het voorvoegsel zoals opgenomen in de
 * geslachtsnaamcomponent met volgnummer gelijk aan '1'; Scheidingsteken - het scheidingsteken zoals opgenomen in de
 * geslachtsnaamcomponent met volgnummer '1'; Geslachtsnaam - bestaande uit de samenvoeging van alle
 * geslachtsnaamcomponenten, inclusief predikaten die niet voor de voornamen worden geplaatst. Ook eventuele adellijke
 * titels die niet voor de gehele geslachtsnaam wordt geplaatst, worden hierin opgenomen.
 *
 * Verplicht aanwezig bij persoon
 *
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van
 * terugwerkende kracht (met andere woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht
 * gelden vanaf de geboorte').
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonSamengesteldeNaamGroepModel implements PersoonSamengesteldeNaamGroepBasis {

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAfgeleid"))
    @JsonProperty
    private JaNeeAttribuut indicatieAfgeleid;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNreeks"))
    @JsonProperty
    private JaNeeAttribuut indicatieNamenreeks;

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Predicaat"))
    @JsonProperty
    private PredicaatAttribuut predicaat;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voornamen"))
    @JsonProperty
    private VoornamenAttribuut voornamen;

    @Embedded
    @AssociationOverride(name = AdellijkeTitelAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AdellijkeTitel"))
    @JsonProperty
    private AdellijkeTitelAttribuut adellijkeTitel;

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

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonSamengesteldeNaamGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param indicatieAfgeleid indicatieAfgeleid van Samengestelde naam.
     * @param indicatieNamenreeks indicatieNamenreeks van Samengestelde naam.
     * @param predicaat predicaat van Samengestelde naam.
     * @param voornamen voornamen van Samengestelde naam.
     * @param adellijkeTitel adellijkeTitel van Samengestelde naam.
     * @param voorvoegsel voorvoegsel van Samengestelde naam.
     * @param scheidingsteken scheidingsteken van Samengestelde naam.
     * @param geslachtsnaamstam geslachtsnaamstam van Samengestelde naam.
     */
    public AbstractPersoonSamengesteldeNaamGroepModel(
        final JaNeeAttribuut indicatieAfgeleid,
        final JaNeeAttribuut indicatieNamenreeks,
        final PredicaatAttribuut predicaat,
        final VoornamenAttribuut voornamen,
        final AdellijkeTitelAttribuut adellijkeTitel,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.indicatieAfgeleid = indicatieAfgeleid;
        this.indicatieNamenreeks = indicatieNamenreeks;
        this.predicaat = predicaat;
        this.voornamen = voornamen;
        this.adellijkeTitel = adellijkeTitel;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonSamengesteldeNaamGroep te kopieren groep.
     */
    public AbstractPersoonSamengesteldeNaamGroepModel(final PersoonSamengesteldeNaamGroep persoonSamengesteldeNaamGroep) {
        this.indicatieAfgeleid = persoonSamengesteldeNaamGroep.getIndicatieAfgeleid();
        this.indicatieNamenreeks = persoonSamengesteldeNaamGroep.getIndicatieNamenreeks();
        this.predicaat = persoonSamengesteldeNaamGroep.getPredicaat();
        this.voornamen = persoonSamengesteldeNaamGroep.getVoornamen();
        this.adellijkeTitel = persoonSamengesteldeNaamGroep.getAdellijkeTitel();
        this.voorvoegsel = persoonSamengesteldeNaamGroep.getVoorvoegsel();
        this.scheidingsteken = persoonSamengesteldeNaamGroep.getScheidingsteken();
        this.geslachtsnaamstam = persoonSamengesteldeNaamGroep.getGeslachtsnaamstam();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieNamenreeks() {
        return indicatieNamenreeks;
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
    public VoornamenAttribuut getVoornamen() {
        return voornamen;
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
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieAfgeleid != null) {
            attributen.add(indicatieAfgeleid);
        }
        if (indicatieNamenreeks != null) {
            attributen.add(indicatieNamenreeks);
        }
        if (predicaat != null) {
            attributen.add(predicaat);
        }
        if (voornamen != null) {
            attributen.add(voornamen);
        }
        if (adellijkeTitel != null) {
            attributen.add(adellijkeTitel);
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
        return attributen;
    }

}
