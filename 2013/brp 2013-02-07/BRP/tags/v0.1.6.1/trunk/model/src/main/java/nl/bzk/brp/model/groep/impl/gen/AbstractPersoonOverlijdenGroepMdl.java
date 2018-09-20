/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonOverlijdenGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;

/**
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonOverlijdenGroepMdl extends AbstractGroep implements PersoonOverlijdenGroepBasis {

    @AttributeOverride(name = "waarde", column = @Column(name = "DatOverlijden"))
    @Embedded
    private Datum datumOverlijden;

    @ManyToOne
    @JoinColumn(name = "GemOverlijden")
    private Partij overlijdenGemeente;

    @ManyToOne
    @JoinColumn(name = "WplOverlijden")
    private Plaats woonplaatsOverlijden;

    @AttributeOverride(name = "waarde", column = @Column(name = "BlPlaatsOverlijden"))
    @Embedded
    private BuitenlandsePlaats buitenlandsePlaatsOverlijden;

    @AttributeOverride(name = "waarde", column = @Column(name = "BlRegioOverlijden"))
    @Embedded
    private BuitenlandseRegio buitenlandseRegioOverlijden;

    @ManyToOne
    @JoinColumn(name = "LandOverlijden")
    private Land landOverlijden;

    @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocOverlijden"))
    @Embedded
    private LocatieOmschrijving omschrijvingLocatieOverlijden;

    @Transient
    private StatusHistorie statusHistorie;

    @Override
    public Datum getDatumOverlijden() {
        return datumOverlijden;
    }

    @Override
    public Partij getOverlijdenGemeente() {
        return overlijdenGemeente;
    }

    @Override
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    @Override
    public Land getLandOverlijden() {
        return landOverlijden;
    }

    @Override
    public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }
    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}
