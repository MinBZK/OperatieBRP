/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.brm;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;


/**
 * Regelgedrag.
 */
@Entity
@Table(name = "Regelgedrag", schema = "BRM")
@Access(AccessType.FIELD)
public class RegelGedrag {

    @Id
    private Long               id;

    @ManyToOne
    @JoinColumn(name = "Regelimplementatie")
    private RegelImplementatie regelImplementatie;

    @Column(name = "IndActief")
    private boolean            actief;

    @Column(name = "Effect")
    private RegelEffect        effect;

    @Column(name = "Bijhverantwoordelijkheid")
    private Verantwoordelijke  verantwoordelijke;

    @Column(name = "IndOpgeschort")
    private Boolean            opschorting;

    @Column(name = "RdnOpschorting")
    private RedenOpschorting   redenOpschorting;

    /**
     * No-arg constructor voor JPA.
     */
    protected RegelGedrag() {
    }

    /**
     * @param gedrag het gedrag waarmee dit gedrag moet worden vergeleken
     * @return true als dit gedrag specifieker is dan het gegeven gedrag
     */
    public boolean isSpecifiekerDan(final RegelGedrag gedrag) {
        return gedrag == null || getSpecifiekheidsIndex() > gedrag.getSpecifiekheidsIndex();
    }

    /**
     * @return getal dat aangeeft hoe specifiek dit gedrag is (0 betekend totaal niet specifiek)
     */
    private int getSpecifiekheidsIndex() {
        int resultaat = 0;
        if (getVerantwoordelijke() != null) {
            resultaat += 1;
        }
        resultaat <<= 1;
        if (isOpschorting() != null) {
            resultaat += 1;
        }
        resultaat <<= 1;
        if (redenOpschorting != null) {
            resultaat += 1;
        }
        return resultaat;
    }

    /**
     * @return primaire sleutel
     */
    public Long getId() {
        return id;
    }

    public RegelImplementatie getRegelImplementatie() {
        return regelImplementatie;
    }

    public boolean isActief() {
        return actief;
    }

    public RegelEffect getEffect() {
        return effect;
    }

    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }

    public Boolean isOpschorting() {
        return opschorting;
    }

    public RedenOpschorting getRedenOpschorting() {
        return redenOpschorting;
    }

}
