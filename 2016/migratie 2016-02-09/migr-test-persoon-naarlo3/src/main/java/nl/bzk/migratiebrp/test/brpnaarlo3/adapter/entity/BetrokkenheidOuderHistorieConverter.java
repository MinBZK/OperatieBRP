/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * Ouder converter.
 */
@Component
public final class BetrokkenheidOuderHistorieConverter extends AbstractEntityHistorieConverter<BetrokkenheidOuderHistorie> {
    private static final String HEADER_TYPE = "kern.his_ouderouderschap";
    private static final String HEADER_INDICATIE_OUDER = "indouder";

    private Betrokkenheid betrokkenheid;
    private Boolean indicatieOuder;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieInhoud;
    private BRPActie actieVerval;
    private BRPActie actieAanpassingGeldigheid;

    /**
     * Default constructor.
     */
    public BetrokkenheidOuderHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void resetConverter() {
        betrokkenheid = null;
        indicatieOuder = null;
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieInhoud = null;
        actieVerval = null;
        actieAanpassingGeldigheid = null;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final BetrokkenheidOuderHistorie betrokkenOuderHistorie = new BetrokkenheidOuderHistorie(betrokkenheid, indicatieOuder);
        betrokkenOuderHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        betrokkenOuderHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        betrokkenOuderHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        betrokkenOuderHistorie.setDatumTijdVerval(datumTijdVerval);
        betrokkenOuderHistorie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        betrokkenOuderHistorie.setActieInhoud(actieInhoud);
        betrokkenOuderHistorie.setActieVerval(actieVerval);

        betrokkenheid.addBetrokkenheidOuderHistorie(betrokkenOuderHistorie);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_BETROKKENHEID:
                betrokkenheid = context.getBetrokkenheid(Integer.parseInt(value));
                break;
            case HEADER_INDICATIE_OUDER:
                indicatieOuder = Boolean.valueOf(value);
                break;
            case HEADER_DATUM_AANVANG_GELDIGHEID:
                datumAanvangGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_DATUM_EINDE_GELDIGHEID:
                datumEindeGeldigheid = Integer.valueOf(value);
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
            case HEADER_ACTIE_AANPASSING_GELDIGHEID:
                actieAanpassingGeldigheid = context.getActie(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void vulActueelLaag() {
        final Set<BetrokkenheidOuderHistorie> historieSet = betrokkenheid.getBetrokkenheidOuderHistorieSet();
        vulActueelVanuit(betrokkenheid, getActueel(historieSet));
    }
}
