/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;

/**
 * Subtype klasse voor indicatie Signalering met betrekking tot verstrekken reisdocument?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder
        extends
        PersoonIndicatieHisVolledigImplBuilder<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel, PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder>
{

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        super(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder() {
        super();
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
        super(defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     *
     *
     * @param datumRegistratie
     * @return
     */
    public PersoonIndicatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
        return super.nieuwStandaardRecord(null, null, datumRegistratie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonIndicatieHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl maakPersoonIndicatieHisVolledig(
        final PersoonHisVolledigImpl persoon)
    {
        return new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel maakHisPersoonIndicatieModel(
        final PersoonIndicatieStandaardGroep groep,
        final ActieModel actie)
    {
        HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel record =
                new HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel(build(), groep, actie, actie);
        if (record.getWaarde() != null) {
            record.getWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        return record;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl build() {
        return (PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl) getHisVolledigImpl();
    }

}
