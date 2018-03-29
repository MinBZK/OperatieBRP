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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * UitsluitingKiesrecht converter.
 */
@Component
public final class PersoonUitsluitingKiesrechtHistorieConverter extends AbstractEntityHistorieConverter<PersoonUitsluitingKiesrechtHistorie> {
    private static final String HEADER_TYPE = "kern.his_persuitslkiesr";
    private static final String HEADER_INDICATIE_UITSLUITING_KIESRECHT = "induitslkiesr";
    private static final String HEADER_DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT = "datvoorzeindeuitslkiesr";

    private Integer datumVoorzienEindeUitsluitingKiesrecht;
    private Boolean indicatieUitsluitingKiesrecht;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonUitsluitingKiesrechtHistorieConverter() {
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
            case HEADER_INDICATIE_UITSLUITING_KIESRECHT:
                indicatieUitsluitingKiesrecht = Boolean.valueOf(value);
                break;
            case HEADER_DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT:
                datumVoorzienEindeUitsluitingKiesrecht = Integer.valueOf(value);
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
        final PersoonUitsluitingKiesrechtHistorie historie = new PersoonUitsluitingKiesrechtHistorie(persoon, indicatieUitsluitingKiesrecht);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumVoorzienEindeUitsluitingKiesrecht(datumVoorzienEindeUitsluitingKiesrecht);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);

        persoon.addPersoonUitsluitingKiesrechtHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumVoorzienEindeUitsluitingKiesrecht = null;
        indicatieUitsluitingKiesrecht = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonUitsluitingKiesrechtHistorie> historieSet = persoon.getPersoonUitsluitingKiesrechtHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
