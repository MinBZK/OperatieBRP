/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Dit is de samengestelde sleutel voor {@link SoortActieBrongebruikSleutel}.
 */
@Embeddable
public class SoortActieBrongebruikSleutel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "srtactie", nullable = false)
    private int soortActieId;

    @Column(name = "srtadmhnd", nullable = false)
    private int soortAdministratieveHandelingId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "srtdoc", nullable = false)
    private SoortDocument soortDocument;

    /**
     * JPA default constructor.
     */
    protected SoortActieBrongebruikSleutel() {}

    /**
     * Maak een nieuw paar van voorvoegsel/scheidingsteken.
     *
     * @param soortActie soort actie
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @param soortDocument soort document
     */
    public SoortActieBrongebruikSleutel(final SoortActie soortActie, final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final SoortDocument soortDocument) {
        setSoortActie(soortActie);
        setSoortAdministratieveHandeling(soortAdministratieveHandeling);
        setSoortDocument(soortDocument);
    }

    /**
     * Geef de waarde van soort actie van SoortActieBrongebruik.
     *
     * @return de waarde van soort actie van SoortActieBrongebruik
     */
    public SoortActie getSoortActie() {
        return SoortActie.parseId(soortActieId);
    }

    /**
     * Zet de waarden voor soort actie van SoortActieBrongebruik.
     *
     * @param soortActie de nieuwe waarde voor soort actie van SoortActieBrongebruik
     */
    public void setSoortActie(final SoortActie soortActie) {
        ValidationUtils.controleerOpNullWaarden("soortActie mag niet null zijn", soortActie);
        soortActieId = soortActie.getId();
    }

    /**
     * Geef de waarde van soort administratieve handeling van SoortActieBrongebruik.
     *
     * @return de waarde van soort administratieve handeling van SoortActieBrongebruik
     */
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return SoortAdministratieveHandeling.parseId(soortAdministratieveHandelingId);
    }

    /**
     * Zet de waarden voor soort administratieve handeling van SoortActieBrongebruik.
     *
     * @param soortAdministratieveHandeling de nieuwe waarde voor soort administratieve handeling
     *        van SoortActieBrongebruik
     */
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("soortAdministratieveHandeling mag niet null zijn", soortAdministratieveHandeling);
        soortAdministratieveHandelingId = soortAdministratieveHandeling.getId();
    }

    /**
     * Geef de waarde van soort document van SoortActieBrongebruik.
     *
     * @return de waarde van soort document van SoortActieBrongebruik
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarden voor soort document van SoortActieBrongebruik.
     *
     * @param soortDocument de nieuwe waarde voor soort document van SoortActieBrongebruik
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        ValidationUtils.controleerOpNullWaarden("soortDocument mag niet null zijn", soortDocument);
        this.soortDocument = soortDocument;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            final SoortActieBrongebruikSleutel other = (SoortActieBrongebruikSleutel) obj;
            return new EqualsBuilder().append(soortActieId, other.soortActieId).append(soortAdministratieveHandelingId, other.soortAdministratieveHandelingId)
                    .append(soortDocument.getNaam(), other.soortDocument.getNaam()).isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortActieId).append(soortAdministratieveHandelingId).append(soortDocument.getNaam()).toHashCode();
    }
}
