/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.logisch.ber.BerichtZoekcriteriaPersoonGroep;


/**
 * Zoekcriteria, waarmee een persoon - of meerdere personen - wordt gezocht.
 */
@Embeddable
public class BerichtZoekcriteriaPersoonGroepModel extends AbstractBerichtZoekcriteriaPersoonGroepModel implements
    BerichtZoekcriteriaPersoonGroep
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected BerichtZoekcriteriaPersoonGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param burgerservicenummer  burgerservicenummer van Zoekcriteria persoon.
     * @param administratienummer  administratienummer van Zoekcriteria persoon.
     * @param objectSleutel        objectSleutel van Zoekcriteria persoon.
     * @param geslachtsnaamstam    geslachtsnaamstam van Zoekcriteria persoon.
     * @param geboortedatum        geboortedatum van Zoekcriteria persoon.
     * @param geboortedatumKind    geboortedatumKind van Zoekcriteria persoon.
     * @param gemeenteCode         gemeenteCode van Zoekcriteria persoon.
     * @param naamOpenbareRuimte   naamOpenbareRuimte van Zoekcriteria persoon.
     * @param huisnummer           huisnummer van Zoekcriteria persoon.
     * @param huisletter           huisletter van Zoekcriteria persoon.
     * @param huisnummertoevoeging huisnummertoevoeging van Zoekcriteria persoon.
     * @param postcode             postcode van Zoekcriteria persoon.
     */
    public BerichtZoekcriteriaPersoonGroepModel(final BurgerservicenummerAttribuut burgerservicenummer,
        final AdministratienummerAttribuut administratienummer, final SleutelwaardetekstAttribuut objectSleutel,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam, final DatumEvtDeelsOnbekendAttribuut geboortedatum,
        final DatumEvtDeelsOnbekendAttribuut geboortedatumKind, final GemeenteCodeAttribuut gemeenteCode,
        final NaamOpenbareRuimteAttribuut naamOpenbareRuimte,
        final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte,
        final HuisnummerAttribuut huisnummer, final HuisletterAttribuut huisletter,
        final HuisnummertoevoegingAttribuut huisnummertoevoeging, final PostcodeAttribuut postcode,
        final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(burgerservicenummer, administratienummer, objectSleutel, geslachtsnaamstam, geboortedatum,
            geboortedatumKind, gemeenteCode, naamOpenbareRuimte, afgekorteNaamOpenbareRuimte, huisnummer,
            huisletter, huisnummertoevoeging, postcode, identificatiecodeNummeraanduiding);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtZoekcriteriaPersoonGroep
     *         te kopieren groep.
     */
    public BerichtZoekcriteriaPersoonGroepModel(final BerichtZoekcriteriaPersoonGroep berichtZoekcriteriaPersoonGroep) {
        super(berichtZoekcriteriaPersoonGroep);
    }

}
