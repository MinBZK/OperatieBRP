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
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.ist.StapelRelatieBasis;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;

/**
 * Het relateren van een stapel aan een relatie.
 *
 * Een stapel heeft normaliter betrekking op één relatie. Uitzonderingen hierop zijn omzettingen van huwelijk in een
 * geregistreerd partnerschap (die leiden tot twee relaties) en stapels met alleen onjuiste voorkomens (die leiden tot
 * géén relaties). Omdat één stapel altijd behoort tot (de persoonslijst van) één persoon, kunnen meerdere stapels
 * behoren tot één relatie.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelRelatieModel extends AbstractDynamischObject implements StapelRelatieBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Stapel")
    private StapelModel stapel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieModel relatie;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelRelatieModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel \ Relatie.
     * @param relatie relatie van Stapel \ Relatie.
     */
    public AbstractStapelRelatieModel(final StapelModel stapel, final RelatieModel relatie) {
        this();
        this.stapel = stapel;
        this.relatie = relatie;

    }

    /**
     * Retourneert ID van Stapel \ Relatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "STAPELRELATIE", sequenceName = "IST.seq_StapelRelatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "STAPELRELATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StapelModel getStapel() {
        return stapel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieModel getRelatie() {
        return relatie;
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
        return attributen;
    }

}
