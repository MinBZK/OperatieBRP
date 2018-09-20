/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.PartijConverter;
import org.springframework.stereotype.Component;

/**
 * Administratieve Handeling Converter.
 */
@Component
public final class AdministratieveHandelingConverter extends EntityConverter {

    private static final String HEADER_TYPE = "kern.admhnd";
    private static final String HEADER_TOELICHTING = "toelichting";
    private static final String HEADER_TIJDSTIP_LEVERING = "tslev";

    @Inject
    private PartijConverter partijConverter;
    private Integer id;
    private SoortAdministratieveHandeling soortAdminHandeling;
    private Partij partij;
    private String toelichtingOntlening;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdLevering;

    /**
     * Default constructor.
     */
    public AdministratieveHandelingConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void maakEntity(final ConverterContext context) {
        final AdministratieveHandeling adminHandeling = new AdministratieveHandeling(partij, soortAdminHandeling);
        adminHandeling.setDatumTijdLevering(datumTijdLevering);
        adminHandeling.setDatumTijdRegistratie(datumTijdRegistratie);
        adminHandeling.setToelichtingOntlening(toelichtingOntlening);

        context.storeAdministratieveHandeling(id, adminHandeling);
    }

    @Override
    protected void resetConverter() {
        id = null;
        soortAdminHandeling = null;
        partij = null;
        toelichtingOntlening = null;
        datumTijdRegistratie = null;
        datumTijdLevering = null;
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                id = Integer.valueOf(value);
                break;
            case HEADER_SOORT:
                soortAdminHandeling = SoortAdministratieveHandeling.parseId(Short.valueOf(value));
                break;
            case HEADER_TOELICHTING:
                toelichtingOntlening = value;
                break;
            case HEADER_PARTIJ:
                partij = partijConverter.convert(value);
                break;
            case HEADER_TIJDSTIP_REGISTRATIE:
                datumTijdRegistratie = maakTimestamp(value);
                break;
            case HEADER_TIJDSTIP_LEVERING:
                datumTijdLevering = maakTimestamp(value);
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }
}
