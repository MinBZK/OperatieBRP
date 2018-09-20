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
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.RedenBeeindigingRelatie;

/** Een entity voor de historische/operationele variant van de Relatie. */
@Entity
@Table(schema = "kern", name = "his_relatie")
@Access(AccessType.FIELD)
public class HisRelatie extends AbstractFormeleHistorieEntiteit implements Cloneable {

    @Id
    @SequenceGenerator(name = "HIS_RELATIE", sequenceName = "Kern.seq_his_relatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_RELATIE")
    private Long id;

    @ManyToOne(targetEntity = PersistentRelatie.class)
    @JoinColumn(name = "relatie")
    @NotNull
    private PersistentRelatie       relatie;
    @Column(name = "dataanv")
    private Integer                 datumAanvang;
    @ManyToOne
    @JoinColumn(name = "gemaanv")
    private Partij                  gemeenteAanvang;
    @ManyToOne
    @JoinColumn(name = "wplaanv")
    private Plaats                  plaatsAanvang;
    @Column(name = "blplaatsaanv")
    private String                  buitenlandsePlaatsAanvang;
    @Column(name = "blregioaanv")
    private String                  buitenlandseRegioAanvang;
    @ManyToOne
    @JoinColumn(name = "landaanv")
    private Land                    landAanvang;
    @Column(name = "omslocaanv")
    private String                  omschrijvingLocatieAanvang;
    @ManyToOne
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    @Column(name = "dateinde")
    private Integer                 datumEinde;
    @ManyToOne
    @JoinColumn(name = "gemeinde")
    private Partij                  gemeenteEinde;
    @ManyToOne
    @JoinColumn(name = "wpleinde")
    private Plaats                  plaatsEinde;
    @Column(name = "blplaatseinde")
    private String                  buitenlandsePlaatsEinde;
    @Column(name = "blregioeinde")
    private String                  buitenlandseRegioEinde;
    @ManyToOne
    @JoinColumn(name = "landeinde")
    private Land                    landEinde;
    @Column(name = "omsloceinde")
    private String                  omschrijvingLocatieEinde;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PersistentRelatie getRelatie() {
        return relatie;
    }

    public void setRelatie(final PersistentRelatie relatie) {
        this.relatie = relatie;
    }

    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    public Plaats getPlaatsAanvang() {
        return plaatsAanvang;
    }

    public void setPlaatsAanvang(final Plaats plaatsAanvang) {
        this.plaatsAanvang = plaatsAanvang;
    }

    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    public Land getLandAanvang() {
        return landAanvang;
    }

    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    public Integer getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    public Plaats getPlaatsEinde() {
        return plaatsEinde;
    }

    public void setPlaatsEinde(final Plaats plaatsEinde) {
        this.plaatsEinde = plaatsEinde;
    }

    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    public Land getLandEinde() {
        return landEinde;
    }

    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    @Override
    public HisRelatie clone() throws CloneNotSupportedException {
        // super.clone maakt al een shallow copy van het gehele object
        HisRelatie copy = (HisRelatie) super.clone();

        copy.setId(null);
        copy.setDatumTijdRegistratie(null);
        return copy;
    }
}
