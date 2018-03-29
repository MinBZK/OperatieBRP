/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatiePKVolledigGeconverteerdCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.ggo.viewer.service.Lo3StamtabelService;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

/**
 * Implementatie van Lo3StamtabelService.
 */
@Component
public class Lo3StamtabelServiceImpl extends StamtabelServiceImpl implements Lo3StamtabelService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final ConversietabelFactory conversietabellen;

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository de repository voor de dynamische stamtabel
     * @param conversietabellen conversie tabellen
     */
    @Inject
    public Lo3StamtabelServiceImpl(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final ConversietabelFactory conversietabellen) {
        super(dynamischeStamtabelRepository);
        this.conversietabellen = conversietabellen;
    }

    @Override
    public final String getAanduidingInhoudingVermissingNlReisdocument(final String loAanduidingInhVermNlReisdoc) {
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum aanduiding =
                Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.getByCode(loAanduidingInhVermNlReisdoc);

        if (aanduiding != null) {
            return String.format(STRING_FORMAT, loAanduidingInhVermNlReisdoc, aanduiding.getLabel());
        } else {
            return loAanduidingInhVermNlReisdoc;
        }

    }

    @Override
    public final String getAangifteAdreshouding(final String lo3AangifteAdreshoudingCode) {
        final Lo3AangifteAdreshoudingEnum aangifte = Lo3AangifteAdreshoudingEnum.getByCode(lo3AangifteAdreshoudingCode);

        if (aangifte != null) {
            return String.format(STRING_FORMAT, lo3AangifteAdreshoudingCode, aangifte.getLabel());
        } else {
            return lo3AangifteAdreshoudingCode;
        }
    }

    @Override
    public final String getAdellijkeTitelPredikaat(final String lo3AdellijkeTitelPredikaatCode) {
        String formattedCode = lo3AdellijkeTitelPredikaatCode;
        try {
            final AdellijkeTitel adellijkeTitel = AdellijkeTitel.parseCode(lo3AdellijkeTitelPredikaatCode.substring(0, 1));
            if (adellijkeTitel != null) {
                formattedCode =
                        String.format(
                                STRING_FORMAT_GESL,
                                lo3AdellijkeTitelPredikaatCode,
                                adellijkeTitel.getNaamMannelijk(),
                                adellijkeTitel.getNaamVrouwelijk());
            }
        } catch (final IllegalArgumentException e) {
            LOG.debug("Code is geen adellijke titel, proberen predikaat te vinden", e);
            try {
                final Predicaat predikaat = Predicaat.parseCode(lo3AdellijkeTitelPredikaatCode.substring(0, 1));
                formattedCode =
                        String.format(STRING_FORMAT_GESL, lo3AdellijkeTitelPredikaatCode, predikaat.getNaamMannelijk(), predikaat.getNaamVrouwelijk());
            } catch (final IllegalArgumentException iae) {
                LOG.debug("Adellijketitel: " + lo3AdellijkeTitelPredikaatCode + " niet gevonden.", iae);
            }
        }

        return formattedCode;
    }

    @Override
    public final String getRedenOpschorting(final String lo3RedenOpschortingBijhoudingCode) {
        final Lo3RedenOpschortingBijhoudingCodeEnum reden = Lo3RedenOpschortingBijhoudingCodeEnum.getByCode(lo3RedenOpschortingBijhoudingCode);

        if (reden != null) {
            return String.format(STRING_FORMAT, lo3RedenOpschortingBijhoudingCode, reden.getLabel());
        } else {
            return lo3RedenOpschortingBijhoudingCode;
        }
    }

    @Override
    public final String getAanduidingBijzonderNederlandschap(final String aanduiding) {
        final Lo3AanduidingBijzonderNederlandschapEnum aanduidingEnum = Lo3AanduidingBijzonderNederlandschapEnum.getByCode(aanduiding);

        if (aanduidingEnum != null) {
            return String.format(STRING_FORMAT, aanduiding, aanduidingEnum.getLabel());
        } else {
            return aanduiding;
        }
    }

    @Override
    public final String getAanduidingEuropeesKiesrecht(final String aanduiding) {
        if (aanduiding != null) {
            Lo3AanduidingEuropeesKiesrechtEnum aanduidingEnum;
            try {
                aanduidingEnum = Lo3AanduidingEuropeesKiesrechtEnum.getByCode(Integer.parseInt(aanduiding));

                if (aanduidingEnum != null) {
                    return String.format(STRING_FORMAT, aanduiding, aanduidingEnum.getLabel());
                }
            } catch (final NumberFormatException e) {
                LOG.debug("Lo3AanduidingEuropeesKiesrecht is geen Integer");
            }
        }
        return aanduiding;
    }

    @Override
    public final String getAanduidingHuisnummer(final String aanduiding) {
        final Lo3AanduidingHuisnummerEnum aanduidingEnum = Lo3AanduidingHuisnummerEnum.getByCode(aanduiding);

        if (aanduidingEnum != null) {
            return String.format(STRING_FORMAT, aanduiding, aanduidingEnum.getLabel());
        } else {
            return aanduiding;
        }
    }

    @Override
    public final String getAanduidingUitgeslotenKiesrecht(final String aanduiding) {
        final Lo3AanduidingUitgeslotenKiesrechtEnum aanduidingEnum = Lo3AanduidingUitgeslotenKiesrechtEnum.getByCode(aanduiding);

        if (aanduidingEnum != null) {
            return String.format(STRING_FORMAT, aanduiding, aanduidingEnum.getLabel());
        } else {
            return aanduiding;
        }
    }

    @Override
    public final String getIndicatieGeheim(final String indicatieGeheim) {
        if (indicatieGeheim != null) {
            Lo3IndicatieGeheimCodeEnum indicatie = null;
            try {
                indicatie = Lo3IndicatieGeheimCodeEnum.getByCode(Integer.parseInt(indicatieGeheim));
            } catch (final NumberFormatException e) {
                LOG.debug("Lo3IndicatieGeheimCode is geen Integer");
            }

            if (indicatie != null) {
                return String.format(STRING_FORMAT, indicatieGeheim, indicatie.getLabel());
            }
        }
        return indicatieGeheim;
    }

    @Override
    public final String getIndicatiePKVolledigGeconverteerdCode(final String code) {
        final Lo3IndicatiePKVolledigGeconverteerdCodeEnum indicatieEnum = Lo3IndicatiePKVolledigGeconverteerdCodeEnum.getByCode(code);

        if (indicatieEnum != null) {
            return String.format(STRING_FORMAT, code, indicatieEnum.getLabel());
        } else {
            return code;
        }
    }

    @Override
    public final String getSoortVerbintenis(final String verbintenis) {
        final Lo3SoortVerbintenisEnum soort = Lo3SoortVerbintenisEnum.getByCode(verbintenis);

        if (soort != null) {
            return String.format(STRING_FORMAT, verbintenis, soort.getLabel());
        } else {
            return verbintenis;
        }
    }

    @Override
    public final String getIndicatieCurateleRegister(final String indicatie) {
        if (indicatie != null) {
            Lo3IndicatieCurateleregisterEnum indicatieEnum = null;
            try {
                indicatieEnum = Lo3IndicatieCurateleregisterEnum.getByCode(Integer.parseInt(indicatie));
            } catch (final NumberFormatException e) {
                LOG.debug("Lo3IndicatieCurateleregister is geen Integer");
            }

            if (indicatieEnum != null) {
                return String.format(STRING_FORMAT, indicatie, indicatieEnum.getLabel());
            }
        }
        return indicatie;
    }

    @Override
    public final String getIndicatieGezagMinderjarige(final String indicatie) {
        final Lo3IndicatieGezagMinderjarigeEnum indicatieEnum = Lo3IndicatieGezagMinderjarigeEnum.getByCode(indicatie);

        if (indicatieEnum != null) {
            return String.format(STRING_FORMAT, indicatie, indicatieEnum.getLabel());
        } else {
            return indicatie;
        }
    }

    @Override
    public final String getIndicatieOnjuist(final String indicatie) {
        final Lo3IndicatieOnjuistEnum indicatieEnum = Lo3IndicatieOnjuistEnum.getByCode(indicatie);

        if (indicatieEnum != null) {
            return String.format(STRING_FORMAT, indicatie, indicatieEnum.getLabel());
        } else {
            return indicatie;
        }
    }

    @Override
    public final String getIndicatieDocument(final String indicatie) {
        if (indicatie != null) {
            Lo3IndicatieDocumentEnum lo3IndicatieDocumentEnum = null;
            try {
                lo3IndicatieDocumentEnum = Lo3IndicatieDocumentEnum.getByCode(Integer.parseInt(indicatie));
            } catch (final NumberFormatException e) {
                LOG.debug("Lo3IndicatieDocument is geen Integer");
            }

            if (lo3IndicatieDocumentEnum != null) {
                return String.format(STRING_FORMAT, indicatie, lo3IndicatieDocumentEnum.getLabel());
            }
        }
        return indicatie;
    }

    /**
     * Zoek RNI deelnemer op in de BRP RNI-deelnemer conversietabel. Als dit niet lukt wordt de code geretourneerd.
     * @param lo3RniDeelnemerCode de lo3RniDeelnemerCode
     * @return De omschrijving van de RNI deelnemer volgens BRP, of de waarde van de code wanneer deze niet wordt gevonden.
     */
    @Override
    public final String getRNIDeelnemer(final Lo3RNIDeelnemerCode lo3RniDeelnemerCode) {
        Lo3RNIDeelnemerCode lo3RniDeelnemerCodeZonderOnderzoek = null;
        if (lo3RniDeelnemerCode != null) {
            lo3RniDeelnemerCodeZonderOnderzoek = new Lo3RNIDeelnemerCode(lo3RniDeelnemerCode.getWaarde(), null);
        }
        String result = lo3RniDeelnemerCode == null ? null : lo3RniDeelnemerCode.getWaarde();

        try {
            final BrpPartijCode rniPartij = conversietabellen.createRNIDeelnemerConversietabel().converteerNaarBrp(lo3RniDeelnemerCodeZonderOnderzoek);
            if (rniPartij != null && lo3RniDeelnemerCode != null) {
                result = getPartij(rniPartij.getWaarde(), lo3RniDeelnemerCode.getWaarde());
            }
        } catch (final IllegalArgumentException e) {
            // Dit kan gewoon gebeuren. De PL levert dan precondities op.
            LOG.debug("Geen RNI deelnemer gevonden voor code " + result, e);
        }

        return result;
    }

    private String getPartij(final String brpPartijCode, final String lo3RniDeelnemerCode) {
        String result;
        try {
            final Partij partij = getDynamischeStamtabelRepository().getPartijByCode(brpPartijCode);
            result = String.format(STRING_FORMAT, lo3RniDeelnemerCode, partij.getNaam());
        } catch (final InvalidDataAccessApiUsageException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "PartijCode", brpPartijCode);
            LOG.warn(logMsg, iae);
            result = String.valueOf(lo3RniDeelnemerCode);
        }
        return result;
    }

    @Override
    public final String getSignalering(final String signalering) {
        if (signalering != null) {
            Lo3SignaleringEnum signaleringEnum = null;
            try {
                signaleringEnum = Lo3SignaleringEnum.getByCode(Integer.parseInt(signalering));
            } catch (final NumberFormatException e) {
                LOG.debug("Lo3Signalering is geen Integer");
            }

            if (signaleringEnum != null) {
                return String.format(STRING_FORMAT, signalering, signaleringEnum.getLabel());
            }
        }
        return signalering;
    }
}
