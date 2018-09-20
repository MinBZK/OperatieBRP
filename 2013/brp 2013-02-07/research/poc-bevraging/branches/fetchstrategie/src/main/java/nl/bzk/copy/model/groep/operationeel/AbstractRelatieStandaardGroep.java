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
import nl.bzk.copy.model.groep.logisch.basis.RelatieStandaardGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.copy.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractRelatieStandaardGroep extends AbstractGroep implements RelatieStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanv"))
    private Datum datumAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum datumEinde;

    @ManyToOne
    @JoinColumn(name = "landaanv")
    @Fetch(FetchMode.JOIN)
    private Land landAanvang;

    @ManyToOne
    @JoinColumn(name = "landeinde")
    @Fetch(FetchMode.JOIN)
    private Land landEinde;

    @ManyToOne
    @JoinColumn(name = "gemaanv")
    @Fetch(FetchMode.JOIN)
    private Partij gemeenteAanvang;

    @ManyToOne
    @JoinColumn(name = "gemeinde")
    @Fetch(FetchMode.JOIN)
    private Partij gemeenteEinde;

    @ManyToOne
    @JoinColumn(name = "rdneinde")
    @Fetch(FetchMode.JOIN)
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "omslocaanv"))
    private Omschrijving omschrijvingLocatieAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "omsloceinde"))
    private Omschrijving omschrijvingLocatieEinde;

    @ManyToOne
    @JoinColumn(name = "wplaanv")
    @Fetch(FetchMode.JOIN)
    private Plaats woonPlaatsAanvang;

    @ManyToOne
    @JoinColumn(name = "wpleinde")
    @Fetch(FetchMode.JOIN)
    private Plaats woonPlaatsEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "blplaatsaanv"))
    private BuitenlandsePlaats buitenlandsePlaatsAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "blplaatseinde"))
    private BuitenlandsePlaats buitenlandsePlaatsEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "blregioaanv"))
    private BuitenlandseRegio buitenlandseRegioAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "blregioeinde"))
    private BuitenlandseRegio buitenlandseRegioEinde;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractRelatieStandaardGroep() {

    }

    /**
     * .
     *
     * @param relatieStandaardGroepBasis RelatieStandaardGroepBasis
     */
    public AbstractRelatieStandaardGroep(final RelatieStandaardGroepBasis relatieStandaardGroepBasis) {
        datumAanvang = relatieStandaardGroepBasis.getDatumAanvang();
        datumEinde = relatieStandaardGroepBasis.getDatumEinde();
        landAanvang = relatieStandaardGroepBasis.getLandAanvang();
        landEinde = relatieStandaardGroepBasis.getLandEinde();
        gemeenteAanvang = relatieStandaardGroepBasis.getGemeenteAanvang();
        gemeenteEinde = relatieStandaardGroepBasis.getGemeenteEinde();
        redenBeeindigingRelatie = relatieStandaardGroepBasis.getRedenBeeindigingRelatie();
        omschrijvingLocatieAanvang = relatieStandaardGroepBasis.getOmschrijvingLocatieAanvang();
        omschrijvingLocatieEinde = relatieStandaardGroepBasis.getOmschrijvingLocatieEinde();
        woonPlaatsAanvang = relatieStandaardGroepBasis.getWoonPlaatsAanvang();
        woonPlaatsEinde = relatieStandaardGroepBasis.getWoonPlaatsEinde();
        buitenlandsePlaatsAanvang = relatieStandaardGroepBasis.getBuitenlandsePlaatsAanvang();
        buitenlandsePlaatsEinde = relatieStandaardGroepBasis.getBuitenlandsePlaatsEinde();
        buitenlandseRegioAanvang = relatieStandaardGroepBasis.getBuitenlandseRegioAanvang();
        buitenlandseRegioEinde = relatieStandaardGroepBasis.getBuitenlandseRegioEinde();
    }

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    @Override
    public Datum getDatumEinde() {
        return datumEinde;
    }

    @Override
    public Land getLandAanvang() {
        return landAanvang;
    }

    @Override
    public Land getLandEinde() {
        return landEinde;
    }

    @Override
    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    @Override
    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    @Override
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    @Override
    public Omschrijving getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    @Override
    public Omschrijving getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    @Override
    public Plaats getWoonPlaatsAanvang() {
        return woonPlaatsAanvang;
    }

    @Override
    public Plaats getWoonPlaatsEinde() {
        return woonPlaatsEinde;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    // Setters zijn aangemaakt voor Jibx.

    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public void setDatumEinde(final Datum datumEinde) {
        this.datumEinde = datumEinde;
    }

    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    public void setOmschrijvingLocatieAanvang(final Omschrijving omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    public void setOmschrijvingLocatieEinde(final Omschrijving omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    public void setWoonPlaatsAanvang(final Plaats woonPlaatsAanvang) {
        this.woonPlaatsAanvang = woonPlaatsAanvang;
    }

    public void setWoonPlaatsEinde(final Plaats woonPlaatsEinde) {
        this.woonPlaatsEinde = woonPlaatsEinde;
    }

    public void setBuitenlandsePlaatsAanvang(final BuitenlandsePlaats buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    public void setBuitenlandsePlaatsEinde(final BuitenlandsePlaats buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    public void setBuitenlandseRegioAanvang(final BuitenlandseRegio buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    public void setBuitenlandseRegioEinde(final BuitenlandseRegio buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }
}
