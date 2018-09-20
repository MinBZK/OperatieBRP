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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroepBasis;

/**
 * Vorm van historie: beiden. Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele
 * geslachtsnaamcomponent (bijv. die met volgnummer 1 voor persoon X) in de loop van de tijd veranderen, dus nog los van
 * eventuele registratiefouten. Er is dus óók sprake van materiële historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel implements PersoonGeslachtsnaamcomponentStandaardGroepBasis {

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Predicaat"))
    @JsonProperty
    private PredicaatAttribuut predicaat;

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
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Stam"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut stam;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param predicaat predicaat van Standaard.
     * @param adellijkeTitel adellijkeTitel van Standaard.
     * @param voorvoegsel voorvoegsel van Standaard.
     * @param scheidingsteken scheidingsteken van Standaard.
     * @param stam stam van Standaard.
     */
    public AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel(
        final PredicaatAttribuut predicaat,
        final AdellijkeTitelAttribuut adellijkeTitel,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut stam)
    {
        this.predicaat = predicaat;
        this.adellijkeTitel = adellijkeTitel;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.stam = stam;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsnaamcomponentStandaardGroep te kopieren groep.
     */
    public AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel(
        final PersoonGeslachtsnaamcomponentStandaardGroep persoonGeslachtsnaamcomponentStandaardGroep)
    {
        this.predicaat = persoonGeslachtsnaamcomponentStandaardGroep.getPredicaat();
        this.adellijkeTitel = persoonGeslachtsnaamcomponentStandaardGroep.getAdellijkeTitel();
        this.voorvoegsel = persoonGeslachtsnaamcomponentStandaardGroep.getVoorvoegsel();
        this.scheidingsteken = persoonGeslachtsnaamcomponentStandaardGroep.getScheidingsteken();
        this.stam = persoonGeslachtsnaamcomponentStandaardGroep.getStam();

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
    public GeslachtsnaamstamAttribuut getStam() {
        return stam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (predicaat != null) {
            attributen.add(predicaat);
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
        if (stam != null) {
            attributen.add(stam);
        }
        return attributen;
    }

}
