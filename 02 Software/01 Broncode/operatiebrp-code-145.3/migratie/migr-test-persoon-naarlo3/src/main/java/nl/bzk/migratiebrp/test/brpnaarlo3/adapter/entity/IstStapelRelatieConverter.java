/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * IST StapelRelatie converter.
 */
@Component
public final class IstStapelRelatieConverter extends EntityConverter {
    private static final String HEADER_TYPE = "ist.stapelrelatie";
    private static final String HEADER_STAPEL = "stapel";
    private static final String HEADER_RELATIE = "relatie";

    private Relatie relatie;
    private Stapel stapel;

    /**
     * Default constructor.
     */
    protected IstStapelRelatieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_STAPEL:
                stapel = context.getStapel(Integer.parseInt(value));
                break;
            case HEADER_RELATIE:
                relatie = context.getRelatie(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        stapel.addRelatie(relatie);
    }

    @Override
    protected void resetConverter() {
        relatie = null;
        stapel = null;
    }
}
