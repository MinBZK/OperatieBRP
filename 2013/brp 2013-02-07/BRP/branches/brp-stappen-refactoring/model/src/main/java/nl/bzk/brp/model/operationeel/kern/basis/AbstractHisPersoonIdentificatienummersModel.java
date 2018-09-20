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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
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
public abstract class AbstractHisPersoonIdentificatienummersModel extends AbstractMaterieleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONIDENTIFICATIENUMMERS", sequenceName = "Kern.seq_His_PersIDs")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONIDENTIFICATIENUMMERS")
    @JsonProperty
    private Integer             iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel        persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BSN"))
    @JsonProperty
    private Burgerservicenummer burgerservicenummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "ANr"))
    @JsonProperty
    private ANummer             administratienummer;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonIdentificatienummersModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonIdentificatienummersModel(final PersoonModel persoonModel,
            final PersoonIdentificatienummersGroepModel groep)
    {
        this.persoon = persoonModel;
        this.burgerservicenummer = groep.getBurgerservicenummer();
        this.administratienummer = groep.getAdministratienummer();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonIdentificatienummersModel(final AbstractHisPersoonIdentificatienummersModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        burgerservicenummer = kopie.getBurgerservicenummer();
        administratienummer = kopie.getAdministratienummer();

    }

    /**
     * Retourneert ID van His Persoon Identificatienummers.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Identificatienummers.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Burgerservicenummer van His Persoon Identificatienummers.
     *
     * @return Burgerservicenummer.
     */
    public Burgerservicenummer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Retourneert Administratienummer van His Persoon Identificatienummers.
     *
     * @return Administratienummer.
     */
    public ANummer getAdministratienummer() {
        return administratienummer;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public HisPersoonIdentificatienummersModel kopieer() {
        return new HisPersoonIdentificatienummersModel(this);
    }
}
