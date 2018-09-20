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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.PartijConverter;
import org.springframework.stereotype.Component;

/**
 * Bijhouding historie converter.
 */
@Component
public final class PersoonBijhoudingHistorieConverter extends AbstractEntityHistorieConverter<PersoonBijhoudingHistorie> {

    private static final String HEADER_TYPE = "kern.his_persbijhouding";
    private static final String HEADER_BIJHOUDINGSPARTIJ = "bijhpartij";
    private static final String HEADER_BIJHOUDINGSAARD = "bijhaard";
    private static final String HEADER_NADERE_BIJHOUDINGSAARD = "naderebijhaard";
    private static final String HEADER_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG = "indonverwdocaanw";

    @Inject
    private PartijConverter partijConverter;

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Bijhoudingsaard bijhoudingsaard;
    private NadereBijhoudingsaard nadereBijhoudingsaard;
    private Boolean indicatieOnverwerktDocumentAanwezig;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Partij partij;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonBijhoudingHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void resetConverter() {
        persoon = null;
        partij = null;
        bijhoudingsaard = null;
        nadereBijhoudingsaard = null;
        indicatieOnverwerktDocumentAanwezig = null;

        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, bijhoudingsaard, nadereBijhoudingsaard, indicatieOnverwerktDocumentAanwezig);
        persoonBijhoudingHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        persoonBijhoudingHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        persoonBijhoudingHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonBijhoudingHistorie.setDatumTijdVerval(datumTijdVerval);
        persoonBijhoudingHistorie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        persoonBijhoudingHistorie.setActieInhoud(actieInhoud);
        persoonBijhoudingHistorie.setActieVerval(actieVerval);

        persoon.addPersoonBijhoudingHistorie(persoonBijhoudingHistorie);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_BIJHOUDINGSPARTIJ:
                partij = partijConverter.convert(value);
                break;
            case HEADER_BIJHOUDINGSAARD:
                bijhoudingsaard = Bijhoudingsaard.parseId(Short.valueOf(value));
                break;
            case HEADER_NADERE_BIJHOUDINGSAARD:
                nadereBijhoudingsaard = NadereBijhoudingsaard.parseId(Short.valueOf(value));
                break;
            case HEADER_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG:
                indicatieOnverwerktDocumentAanwezig = Boolean.valueOf(value);
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
        final Set<PersoonBijhoudingHistorie> historieSet = persoon.getPersoonBijhoudingHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
