/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.PartijConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.SoortDocumentConverter;
import org.springframework.stereotype.Component;

/**
 * Document converter.
 */
@Component
public final class DocumentConverter extends EntityConverter {
    private static final String HEADER_TYPE = "kern.doc";
    private static final String HEADER_ACTIE_ID = "actieId";
    private static final String HEADER_IDENT = "ident";
    private static final String HEADER_AKTENR = "aktenr";
    private static final String HEADER_OMSCHRIJVING = "oms";

    private BRPActie brpActie;
    private SoortDocument soortDocument;
    private String ident;
    private String akteNr;
    private String omschrijving;
    private Partij partij;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;

    @Inject
    private SoortDocumentConverter soortDocumentConverter;
    @Inject
    private PartijConverter partijConverter;

    /**
     * Default constructor.
     */
    public DocumentConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_SOORT:
                soortDocument = soortDocumentConverter.convert(value);
                break;
            case HEADER_ACTIE_ID:
                brpActie = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_PARTIJ:
                partij = partijConverter.convert(value);
                break;
            case HEADER_IDENT:
                ident = value;
                break;
            case HEADER_AKTENR:
                akteNr = value;
                break;
            case HEADER_OMSCHRIJVING:
                omschrijving = value;
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
    protected void maakEntity(final ConverterContext context) {
        final Document document = new Document(soortDocument);
        final DocumentHistorie historie = new DocumentHistorie(document, partij);
        historie.setAktenummer(akteNr);
        historie.setIdentificatie(ident);
        historie.setOmschrijving(omschrijving);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);

        document.addDocumentHistorie(historie);
        brpActie.koppelDocumentViaActieBron(document, null);
    }

    @Override
    protected void resetConverter() {
        brpActie = null;
        soortDocument = null;
        ident = null;
        akteNr = null;
        omschrijving = null;
        partij = null;
    }
}
