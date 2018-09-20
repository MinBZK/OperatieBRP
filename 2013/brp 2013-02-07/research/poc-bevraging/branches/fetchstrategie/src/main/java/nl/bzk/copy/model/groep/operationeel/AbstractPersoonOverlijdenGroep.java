/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.copy.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonOverlijdenGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


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
    @Fetch(FetchMode.JOIN)
    private Partij overlijdenGemeente;

    @ManyToOne
    @JoinColumn(name = "WplOverlijden")
    @Fetch(FetchMode.JOIN)
    private Plaats woonplaatsOverlijden;

    @AttributeOverride(name = "waarde", column = @Column(name = "BlPlaatsOverlijden"))
    @Embedded
    private BuitenlandsePlaats buitenlandsePlaatsOverlijden;

    @AttributeOverride(name = "waarde", column = @Column(name = "BlRegioOverlijden"))
    @Embedded
    private BuitenlandseRegio buitenlandseRegioOverlijden;

    @ManyToOne
    @JoinColumn(name = "LandOverlijden")
    @Fetch(FetchMode.JOIN)
    private Land landOverlijden;

    @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocOverlijden"))
    @Embedded
    private LocatieOmschrijving omschrijvingLocatieOverlijden;

    /**
     * Standaard lege constructor.
     */
    protected AbstractPersoonOverlijdenGroep() {
    }

    /**
     * Constructor die op basis van de opgegeven groep een nieuwe instantie creeert met direct alle waardes gezet
     * naar de waardes uit de opgegeven (voorbeeld) groep.
     *
     * @param persoonOverlijdenGroepBasis de groep met daarin de initiele waardes voor de velden van de nieuwe
     *                                    instantie.
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

    public void setDatumOverlijden(final Datum datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    public void setOverlijdenGemeente(final Partij overlijdenGemeente) {
        this.overlijdenGemeente = overlijdenGemeente;
    }

    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    public void setBuitenlandsePlaatsOverlijden(final BuitenlandsePlaats buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    public void setBuitenlandseRegioOverlijden(final BuitenlandseRegio buitenlandseRegioOverlijden) {
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    public void setLandOverlijden(final Land landOverlijden) {
        this.landOverlijden = landOverlijden;
    }

    public void setOmschrijvingLocatieOverlijden(final LocatieOmschrijving omschrijvingLocatieOverlijden) {
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }
}
