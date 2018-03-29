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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;

import org.springframework.stereotype.Component;

/**
 * Geslachtsaanduiding converter.
 */
@Component
public final class PersoonGeslachtsaanduidingHistorieConverter extends AbstractEntityHistorieConverter<PersoonGeslachtsaanduidingHistorie> {

    private static final String HEADER_TYPE = "kern.his_persgeslachtsaand";
    private static final String HEADER_GESLACHTS_AANDUIDING = "geslachtsaand";

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Geslachtsaanduiding geslachtsaanduiding;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonGeslachtsaanduidingHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_GESLACHTS_AANDUIDING:
                geslachtsaanduiding = Geslachtsaanduiding.parseId(Integer.valueOf(value));
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
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie = new PersoonGeslachtsaanduidingHistorie(persoon, geslachtsaanduiding);
        persoonGeslachtsaanduidingHistorie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        persoonGeslachtsaanduidingHistorie.setActieInhoud(actieInhoud);
        persoonGeslachtsaanduidingHistorie.setActieVerval(actieVerval);
        persoonGeslachtsaanduidingHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        persoonGeslachtsaanduidingHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        persoonGeslachtsaanduidingHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonGeslachtsaanduidingHistorie.setDatumTijdVerval(datumTijdVerval);

        persoon.addPersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie);
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
        geslachtsaanduiding = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonGeslachtsaanduidingHistorie> historieSet = persoon.getPersoonGeslachtsaanduidingHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
