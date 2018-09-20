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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * OuderlijkGezag converter.
 */
@Component
public final class BetrokkenheidOuderlijkGezagHistorieConverter extends AbstractEntityHistorieConverter<BetrokkenheidOuderlijkGezagHistorie> {

    private static final String HEADER_TYPE = "kern.his_ouderouderlijkgezag";
    private static final String HEADER_INDICATIE_OUDER_HEEFT_GEZAG = "indouderheeftgezag";

    private Betrokkenheid betrokkenheid;
    private Boolean indicatieOuderHeeftGezag;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Timestamp datumTijdRegistratie;
    private BRPActie actieInhoud;
    private BRPActie actieVerval;
    private BRPActie actieAanpassingGeldigheid;

    /**
     * Default constructor.
     */
    public BetrokkenheidOuderlijkGezagHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void resetConverter() {
        betrokkenheid = null;
        indicatieOuderHeeftGezag = null;
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        datumTijdRegistratie = null;
        actieInhoud = null;
        actieVerval = null;
        actieAanpassingGeldigheid = null;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie =
                new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid, indicatieOuderHeeftGezag);
        betrokkenheidOuderlijkGezagHistorie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        betrokkenheidOuderlijkGezagHistorie.setActieInhoud(actieInhoud);
        betrokkenheidOuderlijkGezagHistorie.setActieVerval(actieVerval);
        betrokkenheidOuderlijkGezagHistorie.setBetrokkenheid(betrokkenheid);
        betrokkenheidOuderlijkGezagHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        betrokkenheidOuderlijkGezagHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        betrokkenheidOuderlijkGezagHistorie.setDatumTijdRegistratie(datumTijdRegistratie);

        betrokkenheid.addBetrokkenheidOuderlijkGezagHistorie(betrokkenheidOuderlijkGezagHistorie);
    }

    @Override
    public void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_BETROKKENHEID:
                betrokkenheid = context.getBetrokkenheid(Integer.parseInt(value));
                break;
            case HEADER_INDICATIE_OUDER_HEEFT_GEZAG:
                indicatieOuderHeeftGezag = Boolean.valueOf(value);
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
                datumTijdRegistratie = maakTimestamp(value);
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
        final Set<BetrokkenheidOuderlijkGezagHistorie> historieSet = betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet();
        vulActueelVanuit(betrokkenheid, getActueel(historieSet));
    }
}
