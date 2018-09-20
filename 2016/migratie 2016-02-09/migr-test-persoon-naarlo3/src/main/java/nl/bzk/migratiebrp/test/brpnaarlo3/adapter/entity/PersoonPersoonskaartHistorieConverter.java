/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonPersoonskaartHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.PartijConverter;
import org.springframework.stereotype.Component;

/**
 * Persoonskaart converter.
 */
@Component
public final class PersoonPersoonskaartHistorieConverter extends AbstractEntityHistorieConverter<PersoonPersoonskaartHistorie> {

    private static final String HEADER_TYPE = "kern.his_perspk";
    private static final String HEADER_GEMEENTE_PK = "gempk";
    private static final String HEADER_INDICATIE_VOLLEDIG_GECONVERTEERD = "indpkvollediggeconv";

    @Inject
    private PartijConverter partijConverter;

    private Boolean indicatiePersoonskaartVolledigGeconverteerd;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Partij partij;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonPersoonskaartHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_GEMEENTE_PK:
                partij = partijConverter.convert(value);
                break;
            case HEADER_INDICATIE_VOLLEDIG_GECONVERTEERD:
                indicatiePersoonskaartVolledigGeconverteerd = Boolean.valueOf(value);
                break;
            case HEADER_TIJDSTIP_REGISTRATIE:
                datumTijdRegistratie = maakTimestamp(value);
                break;
            case HEADER_TIJDSTIP_VERVAL:
                datumTijdVerval = maakTimestamp(value);
                break;
            case HEADER_ACTIE_INHOUD:
                actieInhoud = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_VERVAL:
                actieVerval = context.getActie(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonPersoonskaartHistorie historie = new PersoonPersoonskaartHistorie(persoon, indicatiePersoonskaartVolledigGeconverteerd);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setPartij(partij);

        persoon.addPersoonPersoonskaartHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        indicatiePersoonskaartVolledigGeconverteerd = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
        partij = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonPersoonskaartHistorie> historieSet = persoon.getPersoonPersoonskaartHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
