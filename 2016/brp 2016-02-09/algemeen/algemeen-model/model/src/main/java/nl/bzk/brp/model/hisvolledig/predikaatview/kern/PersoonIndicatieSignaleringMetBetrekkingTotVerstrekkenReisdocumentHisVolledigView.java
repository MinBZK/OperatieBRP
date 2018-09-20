/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Subtype klasse voor indicatie Signalering met betrekking tot verstrekken reisdocument?
 */
public final class PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigView extends
    AbstractPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigView implements
    PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig
{

    /**
     * Constructor met predikaat en peilmoment.
     *
     * @param persoonIndicatieHisVolledig wrapped indicatie
     * @param predikaat                   predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                                    peilmoment voor altijd tonen groepen
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigView(
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig persoonIndicatieHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonIndicatieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * Constructor met predikaat.
     *
     * @param persoonIndicatieHisVolledig wrapped indicatie
     * @param predikaat                   predikaat
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigView(
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledig persoonIndicatieHisVolledig,
        final Predicate predikaat)
    {
        super(persoonIndicatieHisVolledig, predikaat);
    }

}
