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
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;

/**
 * Subtype klasse voor indicatie Staatloos?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonIndicatieStaatloosHisVolledigImpl extends PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieStaatloosModel>
        implements PersoonIndicatieStaatloosHisVolledigBasis
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonIndicatieStaatloosHisVolledigImpl() {
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public AbstractPersoonIndicatieStaatloosHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon, new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_STAATLOOS));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonIndicatieStaatloosModel> getPersoonIndicatieHistorie() {
        if (hisPersoonIndicatieLijst == null) {
            hisPersoonIndicatieLijst = new HashSet<>();
        }
        if (persoonIndicatieHistorie == null) {
            persoonIndicatieHistorie = new MaterieleHistorieSetImpl<HisPersoonIndicatieStaatloosModel>(hisPersoonIndicatieLijst);
        }
        return (MaterieleHistorieSet<HisPersoonIndicatieStaatloosModel>) persoonIndicatieHistorie;

    }

}
