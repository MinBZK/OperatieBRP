/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import java.util.HashSet;
import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;

/**
 * Subtype klasse voor indicatie Behandeld als Nederlander?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl extends
        PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieBehandeldAlsNederlanderModel> implements
        PersoonIndicatieBehandeldAlsNederlanderHisVolledigBasis
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl() {
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public AbstractPersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon, new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonIndicatieBehandeldAlsNederlanderModel> getPersoonIndicatieHistorie() {
        if (hisPersoonIndicatieLijst == null) {
            hisPersoonIndicatieLijst = new HashSet<>();
        }
        if (persoonIndicatieHistorie == null) {
            persoonIndicatieHistorie = new MaterieleHistorieSetImpl<HisPersoonIndicatieBehandeldAlsNederlanderModel>(hisPersoonIndicatieLijst);
        }
        return (MaterieleHistorieSet<HisPersoonIndicatieBehandeldAlsNederlanderModel>) persoonIndicatieHistorie;

    }

}
