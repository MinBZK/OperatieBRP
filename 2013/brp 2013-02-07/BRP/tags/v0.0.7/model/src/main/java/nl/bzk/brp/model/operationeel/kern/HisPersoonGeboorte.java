/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractFormeleHistorie;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;


/**
 * Entity voor de historie van de groep "Geboorte" van Kern.Persoon.
 */
@Entity
@Table(schema = "kern", name = "His_PersGeboorte")
@Access(AccessType.FIELD)
public class HisPersoonGeboorte extends AbstractFormeleHistorie {

    @Id
    @SequenceGenerator(name = "HisPersGeboorte", sequenceName = "Kern.seq_His_PersGeboorte")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersGeboorte")
    @Column(name = "ID")
    private Long              id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersistentPersoon persoon;

    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    private Partij            gemeenteGeboorte;

    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    private Plaats            woonplaatsGeboorte;

    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    private Land              landGeboorte;

    @Column(name = "DatGeboorte")
    private Integer           datumGeboorte;

    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public Long getId() {
        return id;
    }
}
