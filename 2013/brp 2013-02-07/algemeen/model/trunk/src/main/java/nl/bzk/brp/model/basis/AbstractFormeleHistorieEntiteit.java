/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;


/**
 * Abstracte implementatie voor historie entiteiten die een formele historie patroon volgen.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractFormeleHistorieEntiteit implements FormeleHistorieEntiteit {

    @Embedded
    private FormeleHistorieImpl formeleHistorie = new FormeleHistorieImpl();

    /**
     * Copy constructor.
     *
     * @param kopie de kopie
     */
    protected AbstractFormeleHistorieEntiteit(final AbstractFormeleHistorieEntiteit kopie) {
        formeleHistorie = new FormeleHistorieImpl((FormeleHistorieImpl) kopie.getFormeleHistorie());
    }

    /**
     * Default constructor.
     */
    protected AbstractFormeleHistorieEntiteit() {

    }

    @Override
    @JsonProperty
    public FormeleHistorie getFormeleHistorie() {
        return formeleHistorie;
    }
}
