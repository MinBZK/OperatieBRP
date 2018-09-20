/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonOverlijdenGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;


/**
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonOverlijdenGroep extends AbstractGroep implements PersoonOverlijdenGroepBasis {

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

    /** Standaard lege constructor. */
    protected AbstractPersoonOverlijdenGroep() {
    }

    /**
     * Constructor die op basis van de opgegeven groep een nieuwe instantie creeert met direct alle waardes gezet
     * naar de waardes uit de opgegeven (voorbeeld) groep.
     *
     * @param persoonOverlijdenGroepBasis de groep met daarin de initiele waardes voor de velden van de nieuwe
     * instantie.
     */
    protected AbstractPersoonOverlijdenGroep(final PersoonOverlijdenGroepBasis persoonOverlijdenGroepBasis) {
        super(persoonOverlijdenGroepBasis);
        datumOverlijden = persoonOverlijdenGroepBasis.getDatumOverlijden();
        overlijdenGemeente = persoonOverlijdenGroepBasis.getOverlijdenGemeente();
        woonplaatsOverlijden = persoonOverlijdenGroepBasis.getWoonplaatsOverlijden();
        buitenlandsePlaatsOverlijden = persoonOverlijdenGroepBasis.getBuitenlandsePlaatsOverlijden();
        buitenlandseRegioOverlijden = persoonOverlijdenGroepBasis.getBuitenlandseRegioOverlijden();
        landOverlijden = persoonOverlijdenGroepBasis.getLandOverlijden();
        omschrijvingLocatieOverlijden = persoonOverlijdenGroepBasis.getOmschrijvingLocatieOverlijden();
    }

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
}
