/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * Betrokkenheid converter.
 */
@Component
public final class BetrokkenheidConverter extends EntityConverter {
    private static final String HEADER_TYPE = "kern.betr";
    private static final String HEADER_RELATIE = "relatie";
    private static final String HEADER_ROL = "rol";

    private Integer id;
    private Relatie relatie;
    private Persoon persoon;
    private SoortBetrokkenheid rol;

    /**
     * Default constructor.
     */
    public BetrokkenheidConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                id = Integer.valueOf(value);
                break;
            case HEADER_RELATIE:
                relatie = context.getRelatie(Integer.parseInt(value));
                break;
            case HEADER_ROL:
                rol = SoortBetrokkenheid.parseId(Short.parseShort(value));
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        final Betrokkenheid betrokkenheid = new Betrokkenheid(rol, relatie);
        betrokkenheid.setPersoon(persoon);
        relatie.addBetrokkenheid(betrokkenheid);
        context.storeBetrokkenheid(id, betrokkenheid);
    }

    @Override
    protected void resetConverter() {
        id = null;
        relatie = null;
        persoon = null;
        rol = null;
    }
}
