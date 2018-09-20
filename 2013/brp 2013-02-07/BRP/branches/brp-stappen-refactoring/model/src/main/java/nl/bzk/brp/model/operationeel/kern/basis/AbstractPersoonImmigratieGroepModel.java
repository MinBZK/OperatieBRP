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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonImmigratieGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Vorm van historie: beiden. Motivatie: je kunt vaker immigreren. Alleen vastleggen van materi�le tijdsaspecten is dus
 * niet voldoende: je moet meerdere (niet overlappende maar wel gaten hebbende) perioden kunnen aanwijzen waarin je
 * 'geimmigreerd bent'. (Logischerwijs is de datum einde geldigheid immigratie gelijk aan de datum ingang emigratie.) In
 * de praktijk zal de datum immigratie 'dicht' bij de datum liggen waarop de immigratie geregistreerd wordt; dit kan
 * echter afwijken. Om die reden materi�le historie vastgelegd NAAST de formele historie.
 *
 * De datum ingang geldigheid komt normaliter overeen met de datum vestiging in Nederland; de laatste is (ook) opgenomen
 * omdat hier vanuit migratie verschillende waarden in kunnen staan.
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
public abstract class AbstractPersoonImmigratieGroepModel implements PersoonImmigratieGroepBasis {

    @ManyToOne
    @JoinColumn(name = "LandVanwaarGevestigd")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land  landVanwaarGevestigd;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVestigingInNederland"))
    @JsonProperty
    private Datum datumVestigingInNederland;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonImmigratieGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param landVanwaarGevestigd landVanwaarGevestigd van Immigratie.
     * @param datumVestigingInNederland datumVestigingInNederland van Immigratie.
     */
    public AbstractPersoonImmigratieGroepModel(final Land landVanwaarGevestigd, final Datum datumVestigingInNederland) {
        this.landVanwaarGevestigd = landVanwaarGevestigd;
        this.datumVestigingInNederland = datumVestigingInNederland;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonImmigratieGroep te kopieren groep.
     */
    public AbstractPersoonImmigratieGroepModel(final PersoonImmigratieGroep persoonImmigratieGroep) {
        this.landVanwaarGevestigd = persoonImmigratieGroep.getLandVanwaarGevestigd();
        this.datumVestigingInNederland = persoonImmigratieGroep.getDatumVestigingInNederland();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandVanwaarGevestigd() {
        return landVanwaarGevestigd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }

}
