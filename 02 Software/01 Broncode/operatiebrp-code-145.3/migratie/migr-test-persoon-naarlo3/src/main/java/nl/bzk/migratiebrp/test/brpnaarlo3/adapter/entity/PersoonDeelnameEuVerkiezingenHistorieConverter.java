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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * EuVerkiezingen converter.
 */
@Component
public final class PersoonDeelnameEuVerkiezingenHistorieConverter extends AbstractEntityHistorieConverter<PersoonDeelnameEuVerkiezingenHistorie> {

    private static final String HEADER_TYPE = "kern.his_persdeelneuverkiezingen";
    private static final String HEADER_INDICATIE_DEELNAME_EU_VERKIEZINGEN = "inddeelneuverkiezingen";
    private static final String HEADER_DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN = "dataanlaanpdeelneuverkiezing";
    private static final String HEADER_DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN = "dateindeuitsleukiesr";

    private Persoon persoon;
    private Integer datumAanleidingAanpassingDeelnameEuVerkiezingen;
    private Integer datumVoorzienEindeUitsluitingEuVerkiezingen;
    private Boolean indicatieDeelnameEuVerkiezingen;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;

    /**
     * Default constructor.
     */
    public PersoonDeelnameEuVerkiezingenHistorieConverter() {
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
            case HEADER_INDICATIE_DEELNAME_EU_VERKIEZINGEN:
                indicatieDeelnameEuVerkiezingen = Boolean.valueOf(value);
                break;
            case HEADER_DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN:
                datumAanleidingAanpassingDeelnameEuVerkiezingen = Integer.valueOf(value);
                break;
            case HEADER_DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN:
                datumVoorzienEindeUitsluitingEuVerkiezingen = Integer.valueOf(value);
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
        final PersoonDeelnameEuVerkiezingenHistorie persoonDeelnameEuVerkiezingenHistorie =
                new PersoonDeelnameEuVerkiezingenHistorie(persoon, indicatieDeelnameEuVerkiezingen);
        persoonDeelnameEuVerkiezingenHistorie.setActieInhoud(actieInhoud);
        persoonDeelnameEuVerkiezingenHistorie.setActieVerval(actieVerval);
        persoonDeelnameEuVerkiezingenHistorie.setDatumAanleidingAanpassingDeelnameEuVerkiezingen(datumAanleidingAanpassingDeelnameEuVerkiezingen);
        persoonDeelnameEuVerkiezingenHistorie.setDatumVoorzienEindeUitsluitingEuVerkiezingen(datumVoorzienEindeUitsluitingEuVerkiezingen);
        persoonDeelnameEuVerkiezingenHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonDeelnameEuVerkiezingenHistorie.setDatumTijdVerval(datumTijdVerval);

        persoon.addPersoonDeelnameEuVerkiezingenHistorie(persoonDeelnameEuVerkiezingenHistorie);
    }

    @Override
    protected void resetConverter() {
        persoon = null;
        datumAanleidingAanpassingDeelnameEuVerkiezingen = null;
        datumVoorzienEindeUitsluitingEuVerkiezingen = null;
        indicatieDeelnameEuVerkiezingen = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonDeelnameEuVerkiezingenHistorie> historieSet = persoon.getPersoonDeelnameEuVerkiezingenHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
