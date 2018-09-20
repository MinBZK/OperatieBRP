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
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.BetrokkenheidBasis;

/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid.
 *
 * Er zit geen unique constraint (meer) op de Relatie, Persoon combinatie. In een FRB kan het zo zijn dat het kind
 * beëindigd is op de PL van het ouder en de ouder niet op de PL van het kind (of vice versa). Bij migratie worden dan
 * twee ouderbetrekkingen aangemaakt met dezelfde ouder: de één ontkend door de ouder, de ander door het kind. Dit gaat
 * niet met een UC. Dit kan theoretisch ook voor komen bij 'heradoptie', waarbij op de ene PL de FRB is beeindigd en
 * daarna weer is opgenomen en op de andere PL niet is beëindigd, of is beëindigd maar niet weer opnieuw opgenomen.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBetrokkenheidModel extends AbstractDynamischObject implements BetrokkenheidBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieModel relatie;

    @Embedded
    @AttributeOverride(name = SoortBetrokkenheidAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rol", updatable = false, insertable = false))
    @JsonProperty
    private SoortBetrokkenheidAttribuut rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBetrokkenheidModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Betrokkenheid.
     * @param rol rol van Betrokkenheid.
     * @param persoon persoon van Betrokkenheid.
     */
    public AbstractBetrokkenheidModel(final RelatieModel relatie, final SoortBetrokkenheidAttribuut rol, final PersoonModel persoon) {
        this();
        this.relatie = relatie;
        this.rol = rol;
        this.persoon = persoon;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param betrokkenheid Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractBetrokkenheidModel(final Betrokkenheid betrokkenheid, final RelatieModel relatie, final PersoonModel persoon) {
        this();
        this.relatie = relatie;
        this.rol = betrokkenheid.getRol();
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Betrokkenheid.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BETROKKENHEID", sequenceName = "Kern.seq_Betr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BETROKKENHEID")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieModel getRelatie() {
        return relatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBetrokkenheidAttribuut getRol() {
        return rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (rol != null) {
            attributen.add(rol);
        }
        return attributen;
    }

}
