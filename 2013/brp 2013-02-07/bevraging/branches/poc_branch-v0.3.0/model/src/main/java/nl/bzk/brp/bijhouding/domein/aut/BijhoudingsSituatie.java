/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.aut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.StatusHistorie;
import nl.bzk.brp.bijhouding.domein.SoortActie;
import nl.bzk.brp.bijhouding.domein.SoortDocument;


/**
 * Situatie waarin de {@link BijhoudingsAutorisatie} de bijhouding toestaat.
 *
 * Bijhoudingsautorisaties zijn restrictief: alleen door iets expliciets toe te staan is bijhouding mogelijk.
 * Situaties waarin bijhouden is toegestaan, worden gekenmerkt door een soort actie, of een actie en een soort document.
 * Om bijvoorbeeld toe te staan dat de bijhoudingsautorisatie iets toestaat voor acties van 'soort 1' mits onderbouwt
 * met document van 'soort 2', dient er een bijhoudingssituatie te zijn met soort actie =1 en soort document =2.
 */
@Entity
@Table(name = "bijhsituatie", schema = "autaut")
@Access(value = AccessType.FIELD)
public class BijhoudingsSituatie {

    @SequenceGenerator(name = "BIJHSITUATIE_SEQUENCE_GENERATOR", sequenceName = "AutAut.seq_Bijhsituatie")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BIJHSITUATIE_SEQUENCE_GENERATOR")
    private Long                   id;
    @ManyToOne
    @JoinColumn(name = "bijhautorisatie")
    private BijhoudingsAutorisatie bijhoudingsAutorisatie;
    @Column(name = "categoriesrtactie")
    private Long                   categorieSrtActie;
    @ManyToOne
    @JoinColumn(name = "srtactie")
    private SoortActie             soortActie;
    @ManyToOne
    @JoinColumn(name = "srtdoc")
    private SoortDocument          soortDocument;
    @Column(name = "categoriesrtdoc")
    private Long                   categorieSoortDocument;
    @Column(name = "bijhsituatiestatushis")
    @Enumerated(EnumType.STRING)
    private StatusHistorie         bijhSituatieStatusHis;

    public Long getId() {
        return id;
    }

    /**
     * BijhoudingsAutorisatie waarop deze bijhoudingssituatie van toepassing is.
     *
     * @return BijhoudingsAutorisatie waarop deze bijhoudingssituatie van toepassing is.
     */
    public BijhoudingsAutorisatie getBijhoudingsAutorisatie() {
        return bijhoudingsAutorisatie;
    }

    public Long getCategorieSrtActie() {
        return categorieSrtActie;
    }

    /**
     * De soort BRP actie die onderdeel maakt van de Bijhoudingssituatie.
     *
     * @return De soort BRP actie die onderdeel maakt van de Bijhoudingssituatie.
     */
    public SoortActie getSoortActie() {
        return soortActie;
    }

    /**
     * Het soort document die onderdeel maakt van de Bijhoudingssituatie.
     *
     * @return Het soort document die onderdeel maakt van de Bijhoudingssituatie.
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    public Long getCategorieSoortDocument() {
        return categorieSoortDocument;
    }

    /**
     * Status Historie.
     *
     * @return De Status Historie.
     */
    public StatusHistorie getBijhSituatieStatusHis() {
        return bijhSituatieStatusHis;
    }
}
