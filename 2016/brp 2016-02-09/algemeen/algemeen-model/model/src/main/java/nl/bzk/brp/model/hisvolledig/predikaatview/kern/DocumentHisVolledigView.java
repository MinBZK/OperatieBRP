/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.FormeleHistorieEntiteitComparator;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Document.
 */
public final class DocumentHisVolledigView extends AbstractDocumentHisVolledigView implements DocumentHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param documentHisVolledig document
     * @param predikaat           predikaat
     */
    public DocumentHisVolledigView(final DocumentHisVolledig documentHisVolledig, final Predicate predikaat) {
        super(documentHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param documentHisVolledig document
     * @param predikaat           predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                            peilmomentVoorAltijdTonenGroepen
     */
    public DocumentHisVolledigView(final DocumentHisVolledig documentHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(documentHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * @return Retourneert historie waarvoor alle relevante levervlaggen op true staan.
     */
    public final FormeleHistorieSet<HisDocumentModel> getDocumentHistorieVoorLeveren() {
        final Set<HisDocumentModel> teTonenHistorie = new TreeSet<>(new FormeleHistorieEntiteitComparator<HisDocumentModel>());
        FormeleHistorieSet<HisDocumentModel> historieSet = getDocumentHistorie();
        for (HisDocumentModel hisDocumentModel : historieSet) {
            if (hisDocumentModel.getAktenummer() != null && hisDocumentModel.getAktenummer().isMagGeleverdWorden()) {
                teTonenHistorie.add(hisDocumentModel);
                continue;
            }
            if (hisDocumentModel.getPartij() != null && hisDocumentModel.getPartij().isMagGeleverdWorden()) {
                teTonenHistorie.add(hisDocumentModel);
                continue;
            }
            if (hisDocumentModel.getIdentificatie() != null && hisDocumentModel.getIdentificatie().isMagGeleverdWorden()) {
                teTonenHistorie.add(hisDocumentModel);
                continue;
            }
            if (hisDocumentModel.getOmschrijving() != null && hisDocumentModel.getOmschrijving().isMagGeleverdWorden()) {
                teTonenHistorie.add(hisDocumentModel);
                continue;
            }

        }
        return new FormeleHistorieSetImpl<>(teTonenHistorie);
    }

}
