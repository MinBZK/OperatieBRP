/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoek;
import nl.bzk.brp.model.logisch.kern.basis.PersoonOnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.OnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 * De constatering dat een Onderzoek een Persoon raakt.
 *
 * Als er gegevens van een Persoon in Onderzoek staan, dan wordt (redundant) vastgelegd dat de Persoon onderwerp is van
 * een Onderzoek. Er wordt een koppeling tussen een Persoon en een Onderzoek gelegd indien er een gegeven in onderzoek
 * is dat behoort tot het objecttype Persoon, of tot de naar de Persoon verwijzende objecttypen. Een speciaal geval is
 * de Relatie: is de Relatie zelf in onderzoek, dan zijn alle Personen die betrokken zijn in die Relatie ook in
 * onderzoek.
 *
 * Het objecttype 'Persoon/Onderzoek' had ook de naam "Persoon in Onderzoek" kunnen heten. We sluiten echter aan bij de
 * naamgeving van andere koppeltabellen.
 * RvdP 30 maart 2012
 *
 * De exemplaren van Persoon/Onderzoek zijn volledig afleidbaar uit de exemplaren van Gegevens-in-onderzoek. We leggen
 * dit gegeven echter redundant vast om snel de vraag te kunnen beantwoorden of 'de gegevens over de Persoon' in
 * onderzoek zijn.
 * RvdP 30 maart 2012
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractPersoonOnderzoekModel extends AbstractDynamischObjectType implements
        PersoonOnderzoekBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONONDERZOEK", sequenceName = "Kern.seq_PersOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONONDERZOEK")
    @JsonProperty
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel   persoon;

    @ManyToOne
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private OnderzoekModel onderzoek;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonOnderzoekModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     * @param onderzoek onderzoek van Persoon \ Onderzoek.
     */
    public AbstractPersoonOnderzoekModel(final PersoonModel persoon, final OnderzoekModel onderzoek) {
        this();
        this.persoon = persoon;
        this.onderzoek = onderzoek;

    }

    /**
     * Retourneert ID van Persoon \ Onderzoek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Onderzoek.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Onderzoek van Persoon \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

}
