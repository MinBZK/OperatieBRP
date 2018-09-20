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
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonCacheBasis;

/**
 * De geserialiseerde opslag van actuele en historische gegevens van een persoon.
 *
 * Naast de relationele vastlegging van persoonsgegevens - waarin naast actuele gegevens ook materiële en formele
 * historie worden bewaard - is er ook een vastlegging met behulp van 'BLOBS', in casu JSON (JavaSimpleObjectNotation)
 * format. Deze representatie van dezelfde gegevens dient voor het snel kunnen leveren van gegevens.
 *
 * Zowel de Persoon historie volledig gegevens als de Afnemerindicatie gegevens zijn optioneel gedefinieerd. Het blijkt
 * namelijk, in een uitzonderlijk geval, voor te komen dat er geen Persoon blob is, maar wel een Afnemerindicatie blob.
 * Daarom zijn beide clusters optioneel.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonCacheModel extends AbstractDynamischObject implements PersoonCacheBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @JsonProperty
    @Column(name = "pers")
    private Integer persoonId;

    @Embedded
    @JsonProperty
    private PersoonCacheStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonCacheModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoonId persoon id van Persoon cache.
     */
    public AbstractPersoonCacheModel(final Integer persoonId) {
        this();
        this.persoonId = persoonId;
    }

    /**
     * Retourneert ID van Persoon cache.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONCACHE", sequenceName = "Kern.seq_PersCache")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONCACHE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon cache.
     *
     * @return Persoon.
     */
    public Integer getPersoonId() {
        return persoonId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonCacheStandaardGroepModel getStandaard() {
        return standaard;
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
     * Zet Standaard van Persoon cache.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonCacheStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
        }
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
