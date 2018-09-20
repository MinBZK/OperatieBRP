/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;

/**
 * Subtype klasse voor indicatie Bijzondere verblijfsrechtelijke positie?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder
        extends
        PersoonIndicatieHisVolledigImplBuilder<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel, PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder>
{

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        super(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     */
    public PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder() {
        super();
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
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
    protected PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl maakPersoonIndicatieHisVolledig(final PersoonHisVolledigImpl persoon) {
        return new PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel maakHisPersoonIndicatieModel(
        final PersoonIndicatieStandaardGroep groep,
        final ActieModel actie)
    {
        HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel record =
                new HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel(build(), groep, actie, actie);
        if (record.getWaarde() != null) {
            record.getWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        return record;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl build() {
        return (PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl) getHisVolledigImpl();
    }

}
