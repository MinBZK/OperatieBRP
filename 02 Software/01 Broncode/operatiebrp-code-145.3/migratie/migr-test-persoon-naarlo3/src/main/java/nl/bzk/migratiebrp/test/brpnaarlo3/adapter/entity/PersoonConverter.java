/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;

import org.springframework.stereotype.Component;

/**
 * Persoon converter.
 */
@Component
public final class PersoonConverter extends EntityConverter {

    private static final String HEADER_TYPE = "kern.pers";
    private static final String HEADER_TIJDSTIP_LAATSTE_WIJZIGING = "tslaatstewijz";

    private Integer id;
    private SoortPersoon soortPersoon;
    private Timestamp tijdstipLaatsteWijziging;

    /**
     * Default constructor.
     */
    public PersoonConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void resetConverter() {
        id = null;
        soortPersoon = null;
        tijdstipLaatsteWijziging = null;
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        final Persoon persoon = new Persoon(soortPersoon);
        persoon.setTijdstipLaatsteWijziging(tijdstipLaatsteWijziging);
        context.storePersoon(id, persoon);
    }

    @Override
    public void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                id = Integer.valueOf(value);
                break;
            case HEADER_SOORT:
                soortPersoon = SoortPersoon.parseId(Integer.valueOf(value));
                break;
            case HEADER_TIJDSTIP_LAATSTE_WIJZIGING:
                tijdstipLaatsteWijziging = maakTimestamp(value);
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }
}
