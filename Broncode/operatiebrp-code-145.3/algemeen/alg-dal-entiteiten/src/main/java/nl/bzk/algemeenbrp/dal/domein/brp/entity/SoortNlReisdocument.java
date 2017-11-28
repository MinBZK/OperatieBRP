/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the soortnlreisdocument database table.
 */
@Entity
@Table(name = "convsrtnlreisdoc", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "SoortNlReisdocument" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from SoortNlReisdocument d left join fetch d.soortNederlandsReisdocument")
public class SoortNlReisdocument implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LO3_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN = "lo3NederlandsReisdocument mag niet null zijn";
    private static final String LO3_NEDERLANDS_REISDOCUMENT_MAG_GEEN_LEGE_STRING_ZIJN = "lo3NederlandsReisdocument mag geen lege string zijn";
    private static final String SOORT_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN = "soortNederlandsReisdocument mag niet null zijn";

    @Id
    @SequenceGenerator(name = "convsrtnlreisdoc_id_generator", sequenceName = "conv.seq_convsrtnlreisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convsrtnlreisdoc_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr3511nlreisdoc", length = 2, nullable = false, unique = true)
    private String lo3NederlandsReisdocument;

    @ManyToOne
    @JoinColumn(name = "srtnlreisdoc", nullable = false, unique = true)
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    /**
     * JPA default constructor.
     */
    protected SoortNlReisdocument() {}

    /**
     * Maak een nieuwe soort nl reisdocument.
     * 
     * @param lo3NederlandsReisdocument lo3 nederlands reisdocument
     * @param soortNederlandsReisdocument soort nederlands reisdocument
     */
    public SoortNlReisdocument(final String lo3NederlandsReisdocument, final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        setLo3NederlandsReisdocument(lo3NederlandsReisdocument);
        setSoortNederlandsReisdocument(soortNederlandsReisdocument);
    }

    /**
     * Geef de waarde van id van SoortNlReisdocument.
     * 
     * @return de waarde van id van SoortNlReisdocument
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van SoortNlReisdocument.
     * 
     * @param id de nieuwe waarde voor id van SoortNlReisdocument
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 nederlands reisdocument van SoortNlReisdocument.
     * 
     * @return de waarde van lo3 nederlands reisdocument van SoortNlReisdocument
     */
    public String getLo3NederlandsReisdocument() {
        return lo3NederlandsReisdocument;
    }

    /**
     * Zet de waarden voor lo3 nederlands reisdocument van SoortNlReisdocument.
     * 
     * @param lo3NederlandsReisdocument de nieuwe waarde voor lo3 nederlands reisdocument van
     *        SoortNlReisdocument
     */
    public void setLo3NederlandsReisdocument(final String lo3NederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden(LO3_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN, lo3NederlandsReisdocument);
        ValidationUtils.controleerOpLegeWaarden(LO3_NEDERLANDS_REISDOCUMENT_MAG_GEEN_LEGE_STRING_ZIJN, lo3NederlandsReisdocument);
        this.lo3NederlandsReisdocument = lo3NederlandsReisdocument;
    }

    /**
     * Geef de waarde van soort nederlands reisdocument van SoortNlReisdocument.
     * 
     * @return de waarde van soort nederlands reisdocument van SoortNlReisdocument
     */
    public SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * Zet de waarden voor soort nederlands reisdocument van SoortNlReisdocument.
     * 
     * @param soortNederlandsReisdocument de nieuwe waarde voor soort nederlands reisdocument van
     *        SoortNlReisdocument
     */
    public void setSoortNederlandsReisdocument(final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden(SOORT_NEDERLANDS_REISDOCUMENT_MAG_NIET_NULL_ZIJN, soortNederlandsReisdocument);
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
    }
}
