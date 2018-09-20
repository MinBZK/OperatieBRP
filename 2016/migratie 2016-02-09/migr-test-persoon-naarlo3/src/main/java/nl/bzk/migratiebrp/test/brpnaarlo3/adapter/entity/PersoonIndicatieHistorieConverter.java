/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * Indicatie converter.
 */
@Component
public final class PersoonIndicatieHistorieConverter extends AbstractEntityHistorieConverter<PersoonIndicatieHistorie> {

    private static final String HEADER_TYPE = "kern.his_persindicatie";
    private static final String HEADER_SOORT_INDICATIE = "srtIndicatie";
    private static final String HEADER_WAARDE = "waarde";

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private Boolean waarde;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Persoon persoon;
    private SoortIndicatie soortIndicatie;

    /**
     * Default constructor.
     */
    protected PersoonIndicatieHistorieConverter() {
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
            case HEADER_SOORT_INDICATIE:
                soortIndicatie = SoortIndicatie.parseId(Short.valueOf(value));
                break;
            case HEADER_WAARDE:
                waarde = Boolean.valueOf(value);
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

    private PersoonIndicatie getOrCreateIndicatie(final Persoon indicatiePersoon, final SoortIndicatie indicatieSoort) {
        for (final PersoonIndicatie indicatieSet : indicatiePersoon.getPersoonIndicatieSet()) {
            if (indicatieSet.getSoortIndicatie().equals(indicatieSoort)) {
                return indicatieSet;
            }
        }

        return new PersoonIndicatie(indicatiePersoon, indicatieSoort);
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonIndicatie persoonIndicatie = getOrCreateIndicatie(persoon, soortIndicatie);
        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie(persoonIndicatie, waarde);
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);

        persoonIndicatie.addPersoonIndicatieHistorie(historie);
        persoon.addPersoonIndicatie(persoonIndicatie);
    }

    @Override
    protected void resetConverter() {
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        waarde = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
        persoon = null;
        soortIndicatie = null;
    }

    @Override
    protected void vulActueelLaag() {
        for (final PersoonIndicatie persoonIndicatie : persoon.getPersoonIndicatieSet()) {
            final Set<PersoonIndicatieHistorie> historieSet = persoonIndicatie.getPersoonIndicatieHistorieSet();
            vulActueelVanuit(persoonIndicatie, getActueel(historieSet));
        }
    }
}
