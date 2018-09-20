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
import nl.bzk.copy.model.attribuuttype.Omschrijving;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonGeboorteGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeboorteGroep extends AbstractGroep implements PersoonGeboorteGroepBasis {

    @AttributeOverride(name = "waarde", column = @Column(name = "datgeboorte"))
    @Embedded
    private Datum datumGeboorte;

    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    @Fetch(FetchMode.JOIN)
    private Partij gemeenteGeboorte;

    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    @Fetch(FetchMode.JOIN)
    private Plaats woonplaatsGeboorte;

    @AttributeOverride(name = "waarde", column = @Column(name = "blgeboorteplaats"))
    @Embedded
    private BuitenlandsePlaats buitenlandseGeboortePlaats;

    @AttributeOverride(name = "waarde", column = @Column(name = "blregiogeboorte"))
    @Embedded
    private BuitenlandseRegio buitenlandseRegioGeboorte;

    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    @Fetch(FetchMode.JOIN)
    private Land landGeboorte;

    @AttributeOverride(name = "waarde", column = @Column(name = "omsgeboorteloc"))
    @Embedded
    private Omschrijving omschrijvingGeboorteLocatie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeboorteGroep() {

    }

    /**
     * .
     *
     * @param persoonGeboorteGroepBasis PersoonGeboorteGroepBasis
     */
    protected AbstractPersoonGeboorteGroep(final PersoonGeboorteGroepBasis persoonGeboorteGroepBasis) {
        datumGeboorte = persoonGeboorteGroepBasis.getDatumGeboorte();
        gemeenteGeboorte = persoonGeboorteGroepBasis.getGemeenteGeboorte();
        woonplaatsGeboorte = persoonGeboorteGroepBasis.getWoonplaatsGeboorte();
        buitenlandseGeboortePlaats = persoonGeboorteGroepBasis.getBuitenlandseGeboortePlaats();
        buitenlandseRegioGeboorte = persoonGeboorteGroepBasis.getBuitenlandseRegioGeboorte();
        landGeboorte = persoonGeboorteGroepBasis.getLandGeboorte();
        omschrijvingGeboorteLocatie = persoonGeboorteGroepBasis.getOmschrijvingGeboorteLocatie();
    }

    @Override
    public Datum getDatumGeboorte() {
        return datumGeboorte;
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandseGeboortePlaats() {
        return buitenlandseGeboortePlaats;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    @Override
    public Land getLandGeboorte() {
        return landGeboorte;
    }

    @Override
    public Omschrijving getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    public void setDatumGeboorte(final Datum datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    public void setBuitenlandseGeboortePlaats(final BuitenlandsePlaats buitenlandseGeboortePlaats) {
        this.buitenlandseGeboortePlaats = buitenlandseGeboortePlaats;
    }

    public void setBuitenlandseRegioGeboorte(final BuitenlandseRegio buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    public void setOmschrijvingGeboorteLocatie(final Omschrijving omschrijvingGeboorteLocatie) {
        this.omschrijvingGeboorteLocatie = omschrijvingGeboorteLocatie;
    }
}
