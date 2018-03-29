/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * Inschrijving converter.
 */
@Component
public final class PersoonInschrijvingHistorieConverter extends AbstractEntityHistorieConverter<PersoonInschrijvingHistorie> {

    private static final String HEADER_TYPE = "kern.his_persinschr";
    private static final String HEADER_DATUM_INSCHRIJVING = "datinschr";
    private static final String HEADER_VERSIE_NR = "versienr";
    private static final String HEADER_DATUMTIJDSTEMPEL = "dattijdstempel";

    private Integer datumInschrijving;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private Long versienummer;
    private Timestamp datumtijdstempel;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonInschrijvingHistorieConverter() {
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
            case HEADER_DATUM_INSCHRIJVING:
                datumInschrijving = Integer.valueOf(value);
                break;
            case HEADER_VERSIE_NR:
                versienummer = Long.valueOf(value);
                break;
            case HEADER_DATUMTIJDSTEMPEL:
                datumtijdstempel = maakTimestamp(value);
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
        final PersoonInschrijvingHistorie historie = new PersoonInschrijvingHistorie(persoon, datumInschrijving, versienummer, datumtijdstempel);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);

        persoon.addPersoonInschrijvingHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumInschrijving = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        versienummer = null;
        datumtijdstempel = null;
        actieVerval = null;
        actieInhoud = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonInschrijvingHistorie> historieSet = persoon.getPersoonInschrijvingHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
