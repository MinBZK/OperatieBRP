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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.RegelverantwoordingBasis;

/**
 * De verantwoording van het onderdrukken van een regel.
 *
 * De BRP signaleert indien een bijhouding(svoorstel) één of meer regels raakt. Elke regel waarvan wordt gesignaleerd
 * dat deze wordt geraakt wordt teruggekoppeld, waarna de ambtenaar in voorkomende gevallen toch vastlegging kan
 * afdwingen. De gevallen waarbij een regel wordt onderdrukt, worden expliciet vastgelegd.
 *
 *
 * In de praktijk is het niet de regel die is 'afgegaan', maar de 'regel zoals die is geïmplementeerd' die afgaat. De
 * link naar 'regelimplementatie' (c.q. Regel/bericht zoals deze is gaan heten) is echter niet relevant: in de praktijk
 * zal er vooral interesse zijn in de vraag 'Welke regel is afgegaan'. We leggen daarom de link naar de regel vast, en
 * niet naar regel/bericht. Mocht in een bepaalde situatie er behoefte zijn aan informatie over welke implementatie van
 * de regel het betrof, dan kan deze informatie worden achterhaald: het is immers achterhaalbaar welk soort bericht
 * heeft geleid tot de actie, en dus welke implementatie van de regel het betrof.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractRegelverantwoordingModel extends AbstractDynamischObject implements RegelverantwoordingBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Actie")
    @JsonProperty
    private ActieModel actie;

    @Embedded
    @AttributeOverride(name = RegelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Regel"))
    @JsonProperty
    private RegelAttribuut regel;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractRegelverantwoordingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Regelverantwoording.
     * @param regel regel van Regelverantwoording.
     */
    public AbstractRegelverantwoordingModel(final ActieModel actie, final RegelAttribuut regel) {
        this();
        this.actie = actie;
        this.regel = regel;

    }

    /**
     * Retourneert ID van Regelverantwoording.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "REGELVERANTWOORDING", sequenceName = "Kern.seq_Regelverantwoording")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REGELVERANTWOORDING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActieModel getActie() {
        return actie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegelAttribuut getRegel() {
        return regel;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
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
        if (regel != null) {
            attributen.add(regel);
        }
        return attributen;
    }

}
