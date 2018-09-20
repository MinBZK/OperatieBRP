/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonBijhoudingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersBijhouding")
@GroepAccessor(dbObjectId = 3664)
public class HisPersoonBijhoudingModel extends AbstractHisPersoonBijhoudingModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonBijhoudingGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonBijhoudingModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param historie           historie
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonBijhoudingModel(final PersoonHisVolledig persoonHisVolledig, final PersoonBijhoudingGroep groep,
        final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonBijhoudingModel(final AbstractHisPersoonBijhoudingModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon               persoon van HisPersoonBijhoudingModel
     * @param bijhoudingspartij     bijhoudingspartij van HisPersoonBijhoudingModel
     * @param bijhoudingsaard       bijhoudingsaard van HisPersoonBijhoudingModel
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van HisPersoonBijhoudingModel
     * @param indicatieOnverwerktDocumentAanwezig
     *                              indicatieOnverwerktDocumentAanwezig van HisPersoonBijhoudingModel
     * @param actieInhoud           Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie              De groep uit een bericht
     */
    public HisPersoonBijhoudingModel(final PersoonHisVolledig persoon, final PartijAttribuut bijhoudingspartij,
        final BijhoudingsaardAttribuut bijhoudingsaard, final NadereBijhoudingsaardAttribuut nadereBijhoudingsaard,
        final JaNeeAttribuut indicatieOnverwerktDocumentAanwezig, final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        super(persoon, bijhoudingspartij, bijhoudingsaard, nadereBijhoudingsaard, indicatieOnverwerktDocumentAanwezig,
            actieInhoud, historie);
    }

}
