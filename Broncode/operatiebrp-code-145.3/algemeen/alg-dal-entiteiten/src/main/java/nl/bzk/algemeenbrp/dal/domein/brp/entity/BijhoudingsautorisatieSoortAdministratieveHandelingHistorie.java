/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_toegangbijhautsrtadm database table.
 *
 */
@Entity
@Table(name = "his_bijhautorisatiesrtadmhnd", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"bijhautorisatiesrtadmhnd", "tsreg"}))
@NamedQuery(name = "BijhoudingsautorisatieSoortAdministratieveHandelingHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "SELECT t FROM BijhoudingsautorisatieSoortAdministratieveHandelingHistorie t")
public class BijhoudingsautorisatieSoortAdministratieveHandelingHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_bijhautorisatiesrtadmhnd_id_generator", sequenceName = "autaut.seq_his_bijhautorisatiesrtadmhnd", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_bijhautorisatiesrtadmhnd_id_generator")
    @Column(updatable = false)
    private Integer id;

    // bi-directional many-to-one association to BijhoudingsautorisatieSoortAdministratieveHandeling
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bijhautorisatiesrtadmhnd", nullable = false)
    private BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling;

    /**
     * JPA no-args constructor.
     */
    BijhoudingsautorisatieSoortAdministratieveHandelingHistorie() {}

    /**
     * Maakt een nieuw BijhoudingsautorisatieSoortAdministratieveHandelingHistorie object.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandeling
     *        bijhoudingsautorisatieSoortAdministratieveHandeling
     * @param datumTijdRegistratie datumTijdRegistratie
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHistorie(
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling,
            final Timestamp datumTijdRegistratie) {
        setBijhoudingsautorisatieSoortAdministratieveHandeling(bijhoudingsautorisatieSoortAdministratieveHandeling);
        setDatumTijdRegistratie(datumTijdRegistratie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van BijhoudingsautorisatieSoortAdministratieveHandelingHistorie.
     *
     * @param id de nieuwe waarde voor id van
     *        BijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van bijhoudingsautorisatie soort administratieve handeling van
     * BijhoudingsautorisatieSoortAdministratieveHandelingHistorie.
     *
     * @return de waarde van bijhoudingsautorisatie soort administratieve handeling van
     *         BijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     */
    public BijhoudingsautorisatieSoortAdministratieveHandeling getBijhoudingsautorisatieSoortAdministratieveHandeling() {
        return bijhoudingsautorisatieSoortAdministratieveHandeling;
    }

    /**
     * Zet de waarden voor bijhoudingsautorisatie soort administratieve handeling van
     * BijhoudingsautorisatieSoortAdministratieveHandelingHistorie.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandeling de nieuwe waarde voor
     *        bijhoudingsautorisatie soort administratieve handeling van
     *        BijhoudingsautorisatieSoortAdministratieveHandelingHistorie
     */
    public void setBijhoudingsautorisatieSoortAdministratieveHandeling(
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("bijhoudingsautorisatieSoortAdministratieveHandeling mag niet null zijn",
                bijhoudingsautorisatieSoortAdministratieveHandeling);
        this.bijhoudingsautorisatieSoortAdministratieveHandeling = bijhoudingsautorisatieSoortAdministratieveHandeling;
    }
}
