/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverAdreshoudingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractAangeverAdreshouding;


/**
 * De mogelijke hoedanigheid waarmee een persoon die aangifte doet van verblijf en adres of van adreswijziging kan staan
 * ten opzichte van de Persoon wiens adres is aangegeven.
 *
 * In veel gevallen is degene wiens adres wijzigt ook degene die de adreswijziging doorgeeft. Maar het is ook mogelijk
 * dat anderen de wijziging doorgeven, bijvoorbeeld een meerderjarig gemachtigde.
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "AangAdresh")
public class AangeverAdreshouding extends AbstractAangeverAdreshouding {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AangeverAdreshouding() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AangeverAdreshouding.
     * @param naam naam van AangeverAdreshouding.
     * @param omschrijving omschrijving van AangeverAdreshouding.
     */
    public AangeverAdreshouding(final AangeverAdreshoudingCode code, final NaamEnumeratiewaarde naam,
            final OmschrijvingEnumeratiewaarde omschrijving)
    {
        super(code, naam, omschrijving);
    }

}
