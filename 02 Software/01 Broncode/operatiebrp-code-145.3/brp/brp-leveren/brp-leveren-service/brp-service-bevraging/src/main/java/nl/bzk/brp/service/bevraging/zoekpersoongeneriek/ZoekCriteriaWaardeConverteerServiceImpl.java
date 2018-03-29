/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import org.springframework.stereotype.Service;

/**
 * ZoekCriteriaConverteerServiceImpl.
 */
@Service
final class ZoekCriteriaWaardeConverteerServiceImpl implements ZoekCriteriaWaardeConverteerService {

    @Inject
    private StamTabelService stamTabelService;

    private ZoekCriteriaWaardeConverteerServiceImpl() {
    }

    @Override
    public Object converteerWaarde(final AttribuutElement attribuutElement, final String waarde) throws StapException {
        if (waarde == null) {
            return null;
        }
        final Object waardeGeconverteerd;
        if (attribuutElement.isStamgegevenReferentie()) {
            waardeGeconverteerd = bepaalStamgegevenWaarde(attribuutElement, waarde);
        } else {
            waardeGeconverteerd = bepaalWaarde(attribuutElement, waarde);
        }
        if (waardeGeconverteerd == null) {
            throw new StapException("kan waarde niet bepalen");
        }
        return waardeGeconverteerd;
    }

    private Object bepaalWaarde(final AttribuutElement attribuutElement, final String waarde) throws StapException {
        final Object waardeGeconverteerd;
        final ElementBasisType datatype = attribuutElement.getDatatype();
        if (datatype == ElementBasisType.DATUM) {
            //kunnen onbekende delen inzitten, dus geen iso formatter
            waardeGeconverteerd = bepaalDatumWaarde(waarde);
        } else if (datatype == ElementBasisType.DATUMTIJD) {
            waardeGeconverteerd = DatumUtil.vanXsdDatumTijdNaarDate(waarde);
        } else if (datatype == ElementBasisType.BOOLEAN) {
            waardeGeconverteerd = "j".equalsIgnoreCase(waarde);
        } else if (attribuutElement.isGetal()) {
            waardeGeconverteerd = Long.parseLong(waarde);
        } else {
            waardeGeconverteerd = waarde;
        }
        return waardeGeconverteerd;
    }

    private Integer bepaalDatumWaarde(final String waarde) throws StapException {
        final Integer datum = DatumFormatterUtil.deelsOnbekendeDatumAlsStringNaarGetal(waarde);
        if (datum == null) {
            throw new StapMeldingException(Regel.R2308);
        }
        return datum;
    }

    private Object bepaalStamgegevenWaarde(final AttribuutElement attribuutElement, final String waarde)
            throws StapException {
        final ElementObject elementObject = attribuutElement.getTypeObject();
        final StamtabelGegevens gegevens = stamTabelService.geefStamgegevens(elementObject.getNaam() + StamtabelGegevens.TABEL_POSTFIX);
        if (gegevens == null) {
            throw new StapException("kan stamgegevens niet bepalen");
        }
        Object waardeGeconverteerd = null;
        for (final Map<String, Object> gegeven : gegevens.getStamgegevens()) {
            //voor zoeken ondersteunen we alleen code zoeken voor stamgegevens.
            final Object code = gegeven.get("code");
            if (code != null && waarde.equals(code.toString())) {
                waardeGeconverteerd = gegeven.get("id");
                break;
            }
        }
        if (waardeGeconverteerd == null) {
            throw new StapMeldingException(Regel.R2308);
        }
        return waardeGeconverteerd;
    }
}
