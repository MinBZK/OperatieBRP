/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * IST Stapel converter.
 */
@Component
public final class IstStapelConverter extends EntityConverter {

    private static final String HEADER_TYPE = "ist.stapel";
    private static final String HEADER_CATEGORIE = "categorie";
    private static final String HEADER_VOLGNUMMER = "volgnr";

    private Integer id;
    private String categorie;
    private Persoon persoon;
    private Integer volgnummer;

    /**
     * Default constructor.
     */
    protected IstStapelConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                id = Integer.valueOf(value);
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_CATEGORIE:
                categorie = value;
                break;
            case HEADER_VOLGNUMMER:
                volgnummer = Integer.valueOf(value);
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        final Stapel stapel = new Stapel(persoon, categorie, volgnummer);
        persoon.addStapel(stapel);
        context.storeStapel(id, stapel);
    }

    @Override
    protected void resetConverter() {
        id = null;
        categorie = null;
        persoon = null;
        volgnummer = null;
    }
}
