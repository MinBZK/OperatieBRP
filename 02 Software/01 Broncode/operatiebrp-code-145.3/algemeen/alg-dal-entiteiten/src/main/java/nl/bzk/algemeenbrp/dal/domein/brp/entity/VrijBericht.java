/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the perscache database table.
 */
@Entity
@Table(name = "vrijber", schema = "beh", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class VrijBericht implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "vrijber_id_generator", sequenceName = "beh.seq_vrijber", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vrijber_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "srtber", nullable = false)
    private Integer soortBerichtVrijBericht;

    @OneToOne
    @JoinColumn(name = "srt", nullable = false)
    private SoortVrijBericht soortVrijBericht;

    @Column(name = "tsreg", nullable = false)
    private Timestamp tijdstipRegistratie;

    @Column(name = "data", length = 99_999, nullable = false)
    private String data;

    @Column(name = "indgelezen")
    private Boolean indicatieGelezen;

    @OneToMany(mappedBy = "vrijBericht", cascade = CascadeType.PERSIST)
    private List<VrijBerichtPartij> vrijBerichtPartijen;

    /**
     * JPA default constructor.
     */
    protected VrijBericht() {
    }

    /**
     * Constructor.
     * @param soortBerichtVrijBericht het soort bericht vrij bericht
     * @param soortVrijBericht het soort vrij bericht
     * @param tijdstipRegistratie het tijdstip van registratie
     * @param data de data
     * @param indicatieGelezen indicatie of het bericht gelezen is
     */
    public VrijBericht(final SoortBerichtVrijBericht soortBerichtVrijBericht, final SoortVrijBericht soortVrijBericht, final Timestamp tijdstipRegistratie,
                       final String data, final Boolean indicatieGelezen) {

        ValidationUtils.controleerOpNullWaarden("soort vrij bericht mag niet null zijn", soortVrijBericht);
        ValidationUtils.controleerOpNullWaarden("soort bericht vrijbericht mag niet null zijn", soortBerichtVrijBericht);
        ValidationUtils.controleerOpNullWaarden("tsreg mag niet null zijn", tijdstipRegistratie);
        ValidationUtils.controleerOpNullWaarden("data mag niet null zijn", data);

        setSoortBerichtVrijBericht(soortBerichtVrijBericht);
        this.soortVrijBericht = soortVrijBericht;
        this.tijdstipRegistratie = tijdstipRegistratie;
        this.data = data;
        this.indicatieGelezen = indicatieGelezen;
        vrijBerichtPartijen = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    /**
     * Geef de waarde van soort bericht voor vrij bericht.
     * @return de waarde van soort bericht van vrij bericht
     */
    public SoortBerichtVrijBericht getSoortBerichtVrijBericht() {
        return SoortBerichtVrijBericht.parseId(soortBerichtVrijBericht);
    }

    /**
     * Zet de waarde voor soort bericht van vrij bericht
     * @param soortBerichtVrijBericht de nieuwe waarde voor soort bericht van vrij bericht
     */
    public void setSoortBerichtVrijBericht(final SoortBerichtVrijBericht soortBerichtVrijBericht) {
        ValidationUtils.controleerOpNullWaarden("soortBerichtVrijBericht mag niet null zijn", soortBerichtVrijBericht);
        this.soortBerichtVrijBericht = soortBerichtVrijBericht.getId();
    }

    public SoortVrijBericht getSoortVrijBericht() {
        return soortVrijBericht;
    }

    public Timestamp getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    public String getData() {
        return data;
    }

    public Boolean getIndicatieGelezen() {
        return indicatieGelezen;
    }

    public List<VrijBerichtPartij> getVrijBerichtPartijen() {
        return vrijBerichtPartijen;
    }

    public void setIndicatieGelezen(final Boolean indicatieGelezen) {
        this.indicatieGelezen = indicatieGelezen;
    }
}
