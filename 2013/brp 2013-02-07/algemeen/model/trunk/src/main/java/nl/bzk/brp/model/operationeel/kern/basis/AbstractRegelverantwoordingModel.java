/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.basis.RegelverantwoordingBasis;
import nl.bzk.brp.model.operationeel.kern.ActieModel;


/**
 * De verantwoording van het onderdrukken van een regel.
 *
 * De BRP signaleert indien een bijhouding(svoorstel) ��n of meer regels raakt. Elke regel waarvan wordt gesignaleerd
 * dat deze wordt geraakt wordt teruggekoppeld, waarna de ambtenaar in voorkomende gevallen toch vastlegging kan
 * afdwingen. De gevallen waarbij een regel wordt onderdrukt, worden expliciet vastgelegd.
 *
 *
 * In de praktijk is het niet de regel die is 'afgegaan', maar de 'regel zoals die is ge�mplementeerd' die afgaat. De
 * link naar 'regelimplementatie' (c.q. Regel/bericht zoals deze is gaan heten) is echter niet relevant: in de praktijk
 * zal er vooral interesse zijn in de vraag 'Welke regel is afgegaan'. We leggen daarom de link naar de regel vast, en
 * niet naar regel/bericht. Mocht in een bepaalde situatie er behoefte zijn aan informatie over welke implementatie van
 * de regel het betrof, dan kan deze informatie worden achterhaald: het is immers achterhaalbaar welk soort bericht
 * heeft geleid tot de actie, en dus welke implementatie van de regel het betrof.
 * RvdP 16 april 2012.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractRegelverantwoordingModel extends AbstractDynamischObjectType implements
        RegelverantwoordingBasis
{

    @Id
    @SequenceGenerator(name = "REGELVERANTWOORDING", sequenceName = "Kern.seq_Regelverantwoording")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REGELVERANTWOORDING")
    @JsonProperty
    private Long       iD;

    @ManyToOne
    @JoinColumn(name = "Actie")
    @JsonProperty
    private ActieModel actie;

    @Enumerated
    @Column(name = "Regel")
    @JsonProperty
    private Regel      regel;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
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
    public AbstractRegelverantwoordingModel(final ActieModel actie, final Regel regel) {
        this();
        this.actie = actie;
        this.regel = regel;

    }

    /**
     * Retourneert ID van Regelverantwoording.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Actie van Regelverantwoording.
     *
     * @return Actie.
     */
    public ActieModel getActie() {
        return actie;
    }

    /**
     * Retourneert Regel van Regelverantwoording.
     *
     * @return Regel.
     */
    public Regel getRegel() {
        return regel;
    }

}
