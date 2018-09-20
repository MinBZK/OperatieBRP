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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.NationaliteitConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.RedenVerkrijgingNLNationaliteitConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.RedenVerliesNLNationaliteitConverter;
import org.springframework.stereotype.Component;

/**
 * Nationaliteit converter.
 */
@Component
public final class PersoonNationaliteitHistorieConverter extends AbstractEntityHistorieConverter<PersoonNationaliteitHistorie> {
    private static final String HEADER_TYPE = "kern.his_persnation";
    private static final String HEADER_PERSOON_NATIONALITEIT = "nation";
    private static final String HEADER_REDEN_VERKRIJGING = "rdnverk";
    private static final String HEADER_REDEN_VERLIES = "rdnverlies";

    @Inject
    private NationaliteitConverter nationaliteitConverter;
    @Inject
    private RedenVerkrijgingNLNationaliteitConverter redenVerkrijgingNLNationaliteitConverter;
    @Inject
    private RedenVerliesNLNationaliteitConverter redenVerliesNLNationaliteitConverter;

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Nationaliteit nationaliteit;
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonNationaliteitHistorieConverter() {
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
            case HEADER_PERSOON_NATIONALITEIT:
                nationaliteit = nationaliteitConverter.convert(value);
                break;
            case HEADER_REDEN_VERKRIJGING:
                redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteitConverter.convert(value);
                break;
            case HEADER_REDEN_VERLIES:
                redenVerliesNLNationaliteit = redenVerliesNLNationaliteitConverter.convert(value);
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

    /**
     * Geef de waarde van or create nationaliteit.
     *
     * @return or create nationaliteit
     */
    private PersoonNationaliteit getOrCreateNationaliteit() {
        if (persoon.getPersoonNationaliteitSet() != null) {
            for (final PersoonNationaliteit natPersoon : persoon.getPersoonNationaliteitSet()) {
                if (natPersoon.getNationaliteit().equals(nationaliteit)) {
                    return natPersoon;
                }
            }
        }

        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, nationaliteit);
        persoon.addPersoonNationaliteit(persoonNationaliteit);

        return persoonNationaliteit;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonNationaliteit persoonNationaliteit = getOrCreateNationaliteit();
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setRedenVerkrijgingNLNationaliteit(redenVerkrijgingNLNationaliteit);
        historie.setRedenVerliesNLNationaliteit(redenVerliesNLNationaliteit);

        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
        nationaliteit = null;
        redenVerkrijgingNLNationaliteit = null;
        redenVerliesNLNationaliteit = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        for (final PersoonNationaliteit persoonNationaliteit : persoon.getPersoonNationaliteitSet()) {
            final Set<PersoonNationaliteitHistorie> historieSet = persoonNationaliteit.getPersoonNationaliteitHistorieSet();
            vulActueelVanuit(persoonNationaliteit, getActueel(historieSet));
        }
    }
}
