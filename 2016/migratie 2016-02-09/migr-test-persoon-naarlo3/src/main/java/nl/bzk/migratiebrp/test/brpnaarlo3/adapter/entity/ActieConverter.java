/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.PartijConverter;
import org.springframework.stereotype.Component;

/**
 * Actie converter.
 */
@Component
public final class ActieConverter extends EntityConverter {
    private static final String HEADER_TYPE = "kern.actie";
    private static final String HEADER_ADMINISTRATIEVE_HANDELING = "admhndId";
    private static final String HEADER_DATUM_ONTLENING = "datontlening";

    private Integer id;
    private SoortActie soortActie;
    private AdministratieveHandeling adminHandeling;
    private Partij partij;
    private Timestamp datumTijdRegistratie;
    private Integer datumOntlening;

    @Inject
    private PartijConverter partijConverter;

    /**
     * Default constructor.
     */
    public ActieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        final BRPActie actie = new BRPActie(soortActie, adminHandeling, partij, datumTijdRegistratie);
        actie.setDatumOntlening(datumOntlening);
        context.storeActie(id, actie);
    }

    @Override
    protected void resetConverter() {
        id = null;
        soortActie = null;
        adminHandeling = null;
        partij = null;
        datumTijdRegistratie = null;
        datumOntlening = null;
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                id = Integer.valueOf(value);
                break;
            case HEADER_SOORT:
                soortActie = SoortActie.parseId(Short.valueOf(value));
                break;
            case HEADER_ADMINISTRATIEVE_HANDELING:
                adminHandeling = context.getAdministratieveHandeling(Short.valueOf(value));
                break;
            case HEADER_PARTIJ:
                partij = partijConverter.convert(value);
                break;
            case HEADER_TIJDSTIP_REGISTRATIE:
                datumTijdRegistratie = maakTimestamp(value);
                break;
            case HEADER_DATUM_ONTLENING:
                datumOntlening = Integer.valueOf(value);
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }
}
