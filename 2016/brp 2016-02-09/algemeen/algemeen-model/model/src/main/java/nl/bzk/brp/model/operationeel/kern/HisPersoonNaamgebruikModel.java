/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonNaamgebruikGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersNaamgebruik")
@GroepAccessor(dbObjectId = 3487)
public class HisPersoonNaamgebruikModel extends AbstractHisPersoonNaamgebruikModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonNaamgebruikGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonNaamgebruikModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonNaamgebruikModel(final PersoonHisVolledig persoonHisVolledig, final PersoonNaamgebruikGroep groep,
        final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonNaamgebruikModel(final AbstractHisPersoonNaamgebruikModel kopie) {
        super(kopie);
    }

    /**
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon                      persoon van HisPersoonNaamgebruikModel
     * @param naamgebruik                  naamgebruik van HisPersoonNaamgebruikModel
     * @param indicatieNaamgebruikAfgeleid indicatieNaamgebruikAfgeleid van HisPersoonNaamgebruikModel
     * @param predicaatNaamgebruik         predicaatNaamgebruik van HisPersoonNaamgebruikModel
     * @param voornamenNaamgebruik         voornamenNaamgebruik van HisPersoonNaamgebruikModel
     * @param adellijkeTitelNaamgebruik    adellijkeTitelNaamgebruik van HisPersoonNaamgebruikModel
     * @param voorvoegselNaamgebruik       voorvoegselNaamgebruik van HisPersoonNaamgebruikModel
     * @param scheidingstekenNaamgebruik   scheidingstekenNaamgebruik van HisPersoonNaamgebruikModel
     * @param geslachtsnaamstamNaamgebruik geslachtsnaamstamNaamgebruik van HisPersoonNaamgebruikModel
     * @param actieInhoud                  Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonNaamgebruikModel(final PersoonHisVolledig persoon, final NaamgebruikAttribuut naamgebruik,
        final JaNeeAttribuut indicatieNaamgebruikAfgeleid, final PredicaatAttribuut predicaatNaamgebruik,
        final VoornamenAttribuut voornamenNaamgebruik, final AdellijkeTitelAttribuut adellijkeTitelNaamgebruik,
        final VoorvoegselAttribuut voorvoegselNaamgebruik,
        final ScheidingstekenAttribuut scheidingstekenNaamgebruik,
        final GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik, final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(persoon, naamgebruik, indicatieNaamgebruikAfgeleid, predicaatNaamgebruik, voornamenNaamgebruik,
            adellijkeTitelNaamgebruik, voorvoegselNaamgebruik, scheidingstekenNaamgebruik,
            geslachtsnaamstamNaamgebruik, actieInhoud);
    }

}
