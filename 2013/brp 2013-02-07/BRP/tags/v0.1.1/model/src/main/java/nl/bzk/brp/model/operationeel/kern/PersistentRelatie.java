/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.RedenBeeindigingRelatie;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.operationeel.StatusHistorie;

/**
 * Operationeel gegevens model object voor Relatie.
 */
@Entity
@Table(schema = "kern", name = "relatie")
@Access(AccessType.FIELD)
public class PersistentRelatie {

    @Id
    @SequenceGenerator(name = "RELATIE", sequenceName = "Kern.seq_Relatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIE")
    private Long id;
    @Column(name = "srt")
    private SoortRelatie soortRelatie;
    @Column(name = "dataanv")
    private Integer datumAanvang;
    @ManyToOne
    @JoinColumn(name = "gemaanv")
    private Partij gemeenteAanvang;
    @ManyToOne
    @JoinColumn(name = "wplaanv")
    private Plaats plaatsAanvang;
    @Column(name = "blplaatsaanv")
    private String buitenlandsePlaatsAanvang;
    @Column(name = "blregioaanv")
    private String buitenlandseRegioAanvang;
    @ManyToOne
    @JoinColumn(name = "landaanv")
    private Land landAanvang;
    @Column(name = "omslocaanv")
    private String omschrijvingLocatieAanvang;
    @ManyToOne
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    @Column(name = "dateinde")
    private Integer datumEinde;
    @ManyToOne
    @JoinColumn(name = "gemeinde")
    private Partij gemeenteEinde;
    @ManyToOne
    @JoinColumn(name = "wpleinde")
    private Plaats plaatsEinde;
    @Column(name = "blplaatseinde")
    private String buitenlandsePlaatsEinde;
    @Column(name = "blregioeinde")
    private String buitenlandseRegioEinde;
    @ManyToOne
    @JoinColumn(name = "landeinde")
    private Land landEinde;
    @Column(name = "omsloceinde")
    private String omschrijvingLocatieEinde;
    @Column(name = "relatiestatushis")
    private StatusHistorie statusHistorie;
    @OneToMany
    @JoinColumn(name = "relatie")
    private Set<PersistentBetrokkenheid> betrokkenheden;

    public Long getId() {
        return id;
    }

    public SoortRelatie getSoortRelatie() {
        return soortRelatie;
    }

    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    public Plaats getPlaatsAanvang() {
        return plaatsAanvang;
    }

    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    public Land getLandAanvang() {
        return landAanvang;
    }

    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    public Integer getDatumEinde() {
        return datumEinde;
    }

    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    public Plaats getPlaatsEinde() {
        return plaatsEinde;
    }

    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    public Land getLandEinde() {
        return landEinde;
    }

    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

    public Set<PersistentBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    public Integer getDatumAanvang() {
        return datumAanvang;
    }
}
