/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.AanduidingInhoudingOfVermissingReisdocumentConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.SoortNederlandsReisdocumentConverter;
import org.springframework.stereotype.Component;

/**
 * Reisdocument converter.
 */
@Component
public final class PersoonReisdocumentHistorieConverter extends AbstractEntityHistorieConverter<PersoonReisdocumentHistorie> {

    private static final String HEADER_TYPE = "kern.his_persreisdoc";
    private static final String HEADER_NUMMER = "nr";
    private static final String HEADER_AUTORITEIT_VAN_AFGIFTE = "autvanafgifte";
    private static final String HEADER_DATUM_AANVANG_GELDIGHEID_GEGEVENS_REISDOCUMENT = "dataanvgelgegevensreisdoc";
    private static final String HEADER_DATUM_UITGIFTE = "datuitgifte";
    private static final String HEADER_DATUM_EINDE_DOCUMENT = "dateindedoc";
    private static final String HEADER_DATUM_INHOUDING_VERMISSING = "datinhingvermissing";
    private static final String HEADER_REDEN_VERVALLEN = "rdnvervallen";

    @Inject
    private SoortNederlandsReisdocumentConverter soortNederlandsReisdocumentConverter;
    @Inject
    private AanduidingInhoudingOfVermissingReisdocumentConverter aanduidingInhoudingOfVermissingReisdocumentConverter;

    private Integer datumInhoudingVermissing;
    private Integer datumAanvangGeldigheidGegevensReisdocument;
    private Integer datumUitgifte;
    private Integer datumEindeDocument;
    private String nummer;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private String autoriteitVanAfgifteReisdocument;
    private SoortNederlandsReisdocument soort;
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonReisdocumentHistorieConverter() {
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
            case HEADER_SOORT:
                soort = soortNederlandsReisdocumentConverter.convert(value);
                break;
            case HEADER_NUMMER:
                nummer = value;
                break;
            case HEADER_AUTORITEIT_VAN_AFGIFTE:
                autoriteitVanAfgifteReisdocument = value;
                break;
            case HEADER_DATUM_AANVANG_GELDIGHEID_GEGEVENS_REISDOCUMENT:
                datumAanvangGeldigheidGegevensReisdocument = Integer.valueOf(value);
                break;
            case HEADER_DATUM_UITGIFTE:
                datumUitgifte = Integer.valueOf(value);
                break;
            case HEADER_DATUM_EINDE_DOCUMENT:
                datumEindeDocument = Integer.valueOf(value);
                break;
            case HEADER_DATUM_INHOUDING_VERMISSING:
                datumInhoudingVermissing = Integer.valueOf(value);
                break;
            case HEADER_REDEN_VERVALLEN:
                aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocumentConverter.convert(value);
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

    /**
     * Geef de waarde van or create reisdocument.
     * @return or create reisdocument
     */
    private PersoonReisdocument getOrCreateReisdocument() {
        if (persoon.getPersoonReisdocumentSet() != null) {
            for (final PersoonReisdocument reisdoc : persoon.getPersoonReisdocumentSet()) {
                if (reisdoc.getSoortNederlandsReisdocument().equals(soort)) {
                    return reisdoc;
                }
            }
        }

        final PersoonReisdocument reisdoc = new PersoonReisdocument(persoon, soort);
        persoon.addPersoonReisdocument(reisdoc);
        return reisdoc;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonReisdocument persoonReisdocument = getOrCreateReisdocument();
        final PersoonReisdocumentHistorie historie =
                new PersoonReisdocumentHistorie(
                        datumAanvangGeldigheidGegevensReisdocument,
                        datumUitgifte,
                        datumEindeDocument,
                        nummer,
                        autoriteitVanAfgifteReisdocument,
                        persoonReisdocument);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumInhoudingOfVermissing(datumInhoudingVermissing);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setAanduidingInhoudingOfVermissingReisdocument(aanduidingInhoudingOfVermissingReisdocument);

        persoonReisdocument.addPersoonReisdocumentHistorieSet(historie);
    }

    @Override
    protected void resetConverter() {
        datumInhoudingVermissing = null;
        datumAanvangGeldigheidGegevensReisdocument = null;
        datumUitgifte = null;
        datumEindeDocument = null;
        nummer = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
        autoriteitVanAfgifteReisdocument = null;
        soort = null;
        aanduidingInhoudingOfVermissingReisdocument = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        for (final PersoonReisdocument persoonReisdocument : persoon.getPersoonReisdocumentSet()) {
            final Set<PersoonReisdocumentHistorie> historieSet = persoonReisdocument.getPersoonReisdocumentHistorieSet();
            vulActueelVanuit(persoonReisdocument, getActueel(historieSet));
        }
    }
}
