/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.VerblijfsrechtConverter;

import org.springframework.stereotype.Component;

/**
 * Verblijfsrecht converter.
 */
@Component
public final class PersoonVerblijfsrechtHistorieConverter extends AbstractEntityHistorieConverter<PersoonVerblijfsrechtHistorie> {

    private static final String HEADER_TYPE = "kern.his_persverblijfsr";
    private static final String HEADER_AANDUIDING_VERBLIJFSRECHT = "aandverblijfsr";
    private static final String HEADER_DATUM_AANVANG_VERBLIJFSRECHT = "dataanvverblijfsr";
    private static final String HEADER_DATUM_MEDEDELING_VERBLIJFSRECHT = "datmededelingverblijfsr";
    private static final String HEADER_DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT = "datvoorzeindeverblijfsr";

    @Inject
    private VerblijfsrechtConverter verblijfsrechtConverter;
    private Integer datumAanvangVerblijfsrecht;
    private Integer datumMededelingVerblijfsrecht;
    private Integer datumVoorzienEindeVerblijfsrecht;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Persoon persoon;
    private Verblijfsrecht verblijfsrecht;

    /**
     * Default constructor.
     */
    public PersoonVerblijfsrechtHistorieConverter() {
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
            case HEADER_AANDUIDING_VERBLIJFSRECHT:
                verblijfsrecht = verblijfsrechtConverter.convert(value);
                break;
            case HEADER_DATUM_AANVANG_VERBLIJFSRECHT:
                datumAanvangVerblijfsrecht = Integer.valueOf(value);
                break;
            case HEADER_DATUM_MEDEDELING_VERBLIJFSRECHT:
                datumMededelingVerblijfsrecht = Integer.valueOf(value);
                break;
            case HEADER_DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT:
                datumVoorzienEindeVerblijfsrecht = Integer.valueOf(value);
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
        final PersoonVerblijfsrechtHistorie historie =
                new PersoonVerblijfsrechtHistorie(persoon, verblijfsrecht, datumAanvangVerblijfsrecht, datumMededelingVerblijfsrecht);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setDatumVoorzienEindeVerblijfsrecht(datumVoorzienEindeVerblijfsrecht);
        historie.setVerblijfsrecht(verblijfsrecht);

        persoon.addPersoonVerblijfsrechtHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumAanvangVerblijfsrecht = null;
        datumMededelingVerblijfsrecht = null;
        datumVoorzienEindeVerblijfsrecht = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
        persoon = null;
        verblijfsrecht = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonVerblijfsrechtHistorie> historieSet = persoon.getPersoonVerblijfsrechtHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
