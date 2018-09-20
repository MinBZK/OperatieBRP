/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;

/**
 * JaNeeAttribuut converter.
 */
public class DatumEvtDeelsOnbekendAttribuutConverter extends AbstractConverter<Integer, DatumEvtDeelsOnbekendAttribuut> {

    @Override
    public final DatumEvtDeelsOnbekendAttribuut convert(final Integer value) {
        return new DatumEvtDeelsOnbekendAttribuut(value);
    }
}
