/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import java.util.HashSet;
import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;

/**
 * Subtype klasse voor indicatie Bijzondere verblijfsrechtelijke positie?
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl extends
        PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> implements
        PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigBasis
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl() {
    }

    /**
     * Constructor met backreference naar persoon.
     *
     * @param persoon backreference
     */
    public AbstractPersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        super(persoon, new SoortIndicatieAttribuut(SoortIndicatie.INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> getPersoonIndicatieHistorie() {
        if (hisPersoonIndicatieLijst == null) {
            hisPersoonIndicatieLijst = new HashSet<>();
        }
        if (persoonIndicatieHistorie == null) {
            persoonIndicatieHistorie = new FormeleHistorieSetImpl<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>(hisPersoonIndicatieLijst);
        }
        return (FormeleHistorieSet<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>) persoonIndicatieHistorie;

    }

}
