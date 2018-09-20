/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;


/**
 * HisVolledig klasse voor Gegeven in onderzoek.
 */
@Entity
@Table(schema = "Kern", name = "GegevenInOnderzoek")
public class GegevenInOnderzoekHisVolledigImpl extends AbstractGegevenInOnderzoekHisVolledigImpl implements
    HisVolledigImpl, GegevenInOnderzoekHisVolledig, ElementIdentificeerbaar
{
    @Transient
    private String encryptedObjectSleutelGegeven;

    /**
     * Default contructor voor JPA.
     */
    protected GegevenInOnderzoekHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param onderzoek        onderzoek van Gegeven in onderzoek.
     * @param soortGegeven     soortGegeven van Gegeven in onderzoek.
     * @param objectSleutel    objectSleutel van Gegeven in onderzoek.
     * @param voorkomenSleutel voorkomenSleutel van Gegeven in onderzoek.
     */
    public GegevenInOnderzoekHisVolledigImpl(
        final OnderzoekHisVolledigImpl onderzoek,
        final ElementAttribuut soortGegeven,
        final SleutelwaardeAttribuut objectSleutel,
        final SleutelwaardeAttribuut voorkomenSleutel)
    {
        super(onderzoek, soortGegeven, objectSleutel, voorkomenSleutel);
    }

    /**
     * @return encryptedObjectSleutelGegeven.
     */
    public final String getEncryptedObjectSleutelGegeven() {
        return encryptedObjectSleutelGegeven;
    }

    /**
     * Zet object sleutelgegeven.
     *
     * @param objectSleutelGegeven De encrypted (persoon) sleutel.
     */
    public final void setEncryptedObjectSleutelGegeven(final String objectSleutelGegeven) {
        this.encryptedObjectSleutelGegeven = objectSleutelGegeven;
    }

    /**
     * Retourneert Object sleutel gegeven van Gegeven in onderzoek. Voor gebruik in binding. Encrypted voor persoon referentie.
     *
     * @return Object sleutel gegeven.
     */
    public final String getObjectSleutelGegevenVoorBinding() {
        if (this.getEncryptedObjectSleutelGegeven() == null) {
            return super.getObjectSleutelGegeven() != null ? super.getObjectSleutelGegeven().getWaarde().toString() : null;
        }
        return this.getEncryptedObjectSleutelGegeven();
    }
}
