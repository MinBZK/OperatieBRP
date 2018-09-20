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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.operationeel.StatusHistorie;
import org.hibernate.annotations.Formula;

/**
 * Operationeel gegevens model object voor Betrokkenheid.
 */
@Entity
@Table(schema = "Kern", name = "Betr")
@Access(AccessType.FIELD)
public class PersistentBetrokkenheid {

    @Id
    @SequenceGenerator(name = "BETROKKENHEID", sequenceName = "Kern.seq_Betr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BETROKKENHEID")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "relatie")
    private PersistentRelatie relatie;
    @Column(name = "rol")
    private SoortBetrokkenheid soortBetrokkenheid;
    @OneToOne
    @JoinColumn(name = "betrokkene")
    private PersistentPersoon betrokkene;
    @Column(name = "indouder")
    private Boolean indOuder;
    @Formula("(select hbo.dataanvgel from kern.his_betrouder hbo where hbo.betr = id)")
    private Integer datumAanvangOuderschap;
    @Column(name = "ouderstatushis")
    private StatusHistorie ouderStatusHistorie;
    @Column(name = "indouderheeftgezag")
    private Boolean indOuderHeeftGezag;
    @Column(name = "ouderlijkgezagstatushis")
    private StatusHistorie ouderlijkGezagStatusHistorie;

    public Integer getId() {
        return id;
    }

    public PersistentRelatie getRelatie() {
        return relatie;
    }

    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return soortBetrokkenheid;
    }

    public PersistentPersoon getBetrokkene() {
        return betrokkene;
    }

    public Boolean isIndOuder() {
        return indOuder;
    }

    public Integer getDatumAanvangOuderschap() {
        return datumAanvangOuderschap;
    }

    public StatusHistorie getOuderStatusHistorie() {
        return ouderStatusHistorie;
    }

    public Boolean isIndOuderHeeftGezag() {
        return indOuderHeeftGezag;
    }

    public StatusHistorie getOuderlijkGezagStatusHistorie() {
        return ouderlijkGezagStatusHistorie;
    }
}
