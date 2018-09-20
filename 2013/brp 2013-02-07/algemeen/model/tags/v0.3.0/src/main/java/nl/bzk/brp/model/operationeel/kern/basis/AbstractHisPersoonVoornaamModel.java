/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVoornaamStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamStandaardGroepModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonVoornaamModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonVoornaamStandaardGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONVOORNAAM", sequenceName = "Kern.seq_His_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONVOORNAAM")
    @JsonProperty
    private Integer              iD;

    @ManyToOne
    @JoinColumn(name = "PersVoornaam")
    private PersoonVoornaamModel persoonVoornaam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    @JsonProperty
    private Voornaam             naam;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonVoornaamModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonVoornaamModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonVoornaamModel(final PersoonVoornaamModel persoonVoornaamModel,
            final PersoonVoornaamStandaardGroepModel groep)
    {
        this.persoonVoornaam = persoonVoornaamModel;
        this.naam = groep.getNaam();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonVoornaamModel(final AbstractHisPersoonVoornaamModel kopie) {
        super(kopie);
        persoonVoornaam = kopie.getPersoonVoornaam();
        naam = kopie.getNaam();

    }

    /**
     * Retourneert ID van His Persoon \ Voornaam.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Voornaam van His Persoon \ Voornaam.
     *
     * @return Persoon \ Voornaam.
     */
    public PersoonVoornaamModel getPersoonVoornaam() {
        return persoonVoornaam;
    }

    /**
     * Retourneert Naam van His Persoon \ Voornaam.
     *
     * @return Naam.
     */
    public Voornaam getNaam() {
        return naam;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonVoornaamModel kopieer() {
        return new HisPersoonVoornaamModel(this);
    }
}
