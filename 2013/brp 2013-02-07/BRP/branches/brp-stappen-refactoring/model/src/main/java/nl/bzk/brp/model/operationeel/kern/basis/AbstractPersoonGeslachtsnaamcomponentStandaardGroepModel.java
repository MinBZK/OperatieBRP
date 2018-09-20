/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Vorm van historie: beiden.
 * Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele geslachtsnaamcomponent (bijv. die met
 * volgnummer 1 voor persoon X) in de loop van de tijd veranderen, dus nog los van eventuele registratiefouten.
 * Er is dus ��k sprake van materi�le historie.
 * RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel implements
        PersoonGeslachtsnaamcomponentStandaardGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "Predikaat")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Predikaat              predikaat;

    @ManyToOne
    @JoinColumn(name = "AdellijkeTitel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AdellijkeTitel         adellijkeTitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private Voorvoegsel            voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private Scheidingsteken        scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    @JsonProperty
    private Geslachtsnaamcomponent naam;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param predikaat predikaat van Standaard.
     * @param adellijkeTitel adellijkeTitel van Standaard.
     * @param voorvoegsel voorvoegsel van Standaard.
     * @param scheidingsteken scheidingsteken van Standaard.
     * @param naam naam van Standaard.
     */
    public AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel(final Predikaat predikaat,
            final AdellijkeTitel adellijkeTitel, final Voorvoegsel voorvoegsel, final Scheidingsteken scheidingsteken,
            final Geslachtsnaamcomponent naam)
    {
        this.predikaat = predikaat;
        this.adellijkeTitel = adellijkeTitel;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.naam = naam;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsnaamcomponentStandaardGroep te kopieren groep.
     */
    public AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel(
            final PersoonGeslachtsnaamcomponentStandaardGroep persoonGeslachtsnaamcomponentStandaardGroep)
    {
        this.predikaat = persoonGeslachtsnaamcomponentStandaardGroep.getPredikaat();
        this.adellijkeTitel = persoonGeslachtsnaamcomponentStandaardGroep.getAdellijkeTitel();
        this.voorvoegsel = persoonGeslachtsnaamcomponentStandaardGroep.getVoorvoegsel();
        this.scheidingsteken = persoonGeslachtsnaamcomponentStandaardGroep.getScheidingsteken();
        this.naam = persoonGeslachtsnaamcomponentStandaardGroep.getNaam();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Geslachtsnaamcomponent getNaam() {
        return naam;
    }

}
