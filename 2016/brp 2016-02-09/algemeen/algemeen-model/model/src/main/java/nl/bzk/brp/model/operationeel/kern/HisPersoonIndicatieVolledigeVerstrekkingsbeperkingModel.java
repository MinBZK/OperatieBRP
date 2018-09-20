/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;


/**
 * Subtype klasse voor indicatie Volledige verstrekkingsbeperking?
 */
@Entity
@DiscriminatorValue(value = "3")
public class HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel extends AbstractHisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel() {
        super();
    }

    /**
     * Copy constructor met een groep instantie.
     *
     * @param persoonIndicatieHisVolledig backreference
     * @param groep                       groep
     * @param historie                    historie
     * @param actieInhoud                 actieInhoud
     */
    public HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel(
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl persoonIndicatieHisVolledig,
        final PersoonIndicatieStandaardGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonIndicatieHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy constructor met alle atribuut waarden.
     *
     * @param persoonIndicatieHisVolledig backreference
     * @param waarde                      waarde
     * @param actieInhoud                 actieInhoud
     * @param historie                    historie
     */
    public HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel(
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl persoonIndicatieHisVolledig,
        final JaAttribuut waarde, final ActieModel actieInhoud, final MaterieleHistorie historie)
    {
        super(persoonIndicatieHisVolledig, waarde, actieInhoud, historie);
    }

    /**
     * Copy constructor.
     *
     * @param kopie kopie
     */
    public HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel(
        final AbstractHisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel kopie)
    {
        super(kopie);
    }

    @Override
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP;
    }
}
