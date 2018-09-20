/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;

/**
 * Subtype klasse voor indicatie Vastgesteld niet Nederlander?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder
        extends
        PersoonIndicatieHisVolledigImplBuilder<HisPersoonIndicatieVastgesteldNietNederlanderModel, PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder>
{

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     */
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        super(persoon);
    }

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        super(persoon, defaultMagGeleverdWordenVoorAttributen);
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     */
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder() {
        super();
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     * @param defaultMagGeleverdWordenVoorAttributen default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
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
    protected PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl maakPersoonIndicatieHisVolledig(final PersoonHisVolledigImpl persoon) {
        return new PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HisPersoonIndicatieVastgesteldNietNederlanderModel maakHisPersoonIndicatieModel(
        final PersoonIndicatieStandaardGroep groep,
        final ActieModel actie)
    {
        HisPersoonIndicatieVastgesteldNietNederlanderModel record = new HisPersoonIndicatieVastgesteldNietNederlanderModel(build(), groep, actie, actie);
        if (record.getWaarde() != null) {
            record.getWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        return record;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl build() {
        return (PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl) getHisVolledigImpl();
    }

}
