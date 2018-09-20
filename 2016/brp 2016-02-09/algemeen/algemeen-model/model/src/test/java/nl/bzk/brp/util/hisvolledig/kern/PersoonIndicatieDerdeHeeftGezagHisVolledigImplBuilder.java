/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieDerdeHeeftGezagModel;

/**
 * Subtype klasse voor indicatie Derde heeft gezag?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder extends
        PersoonIndicatieHisVolledigImplBuilder<HisPersoonIndicatieDerdeHeeftGezagModel, PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder>
{

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        super(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     */
    public PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder() {
        super();
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
        super(defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        return super.nieuwStandaardRecord(datumAanvangGeldigheid, datumEindeGeldigheid, datumRegistratie);
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
    protected PersoonIndicatieDerdeHeeftGezagHisVolledigImpl maakPersoonIndicatieHisVolledig(final PersoonHisVolledigImpl persoon) {
        return new PersoonIndicatieDerdeHeeftGezagHisVolledigImpl(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HisPersoonIndicatieDerdeHeeftGezagModel maakHisPersoonIndicatieModel(final PersoonIndicatieStandaardGroep groep, final ActieModel actie) {
        HisPersoonIndicatieDerdeHeeftGezagModel record = new HisPersoonIndicatieDerdeHeeftGezagModel(build(), groep, actie, actie);
        if (record.getWaarde() != null) {
            record.getWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        return record;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieDerdeHeeftGezagHisVolledigImpl build() {
        return (PersoonIndicatieDerdeHeeftGezagHisVolledigImpl) getHisVolledigImpl();
    }

}
