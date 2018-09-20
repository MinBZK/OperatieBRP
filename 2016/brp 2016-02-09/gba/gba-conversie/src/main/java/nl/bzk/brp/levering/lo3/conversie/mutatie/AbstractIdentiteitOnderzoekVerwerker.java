/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Basis onderzoek verwerker op a-laag niveau.
 *
 * @param <L>
 *            type lo3 inhoud
 */
public abstract class AbstractIdentiteitOnderzoekVerwerker<L extends Lo3CategorieInhoud> {
    private final ElementEnum identiteitGroepElement;

    /**
     * Constructor.
     *
     * @param identiteitGroepElement
     *            element van identiteit groep
     */
    public AbstractIdentiteitOnderzoekVerwerker(final ElementEnum identiteitGroepElement) {
        this.identiteitGroepElement = identiteitGroepElement;
    }

    /**
     * Verwerk.
     *
     * @param wijzigingen
     *            wijzigingen
     * @param acties
     *            acties
     * @param objectSleutels
     *            object sleutels
     * @param onderzoekMapper
     *            onderzoek mapper
     */
    public final void verwerk(
        final Lo3Wijzigingen<L> wijzigingen,
        final List<Long> acties,
        final List<Long> objectSleutels,
        final OnderzoekMapper onderzoekMapper)
    {
        final Lo3Onderzoek actueelOnderzoek =
                onderzoekMapper.bepaalActueelOnderzoek(acties, objectSleutels, Collections.<Long>emptyList(), identiteitGroepElement);
        if (actueelOnderzoek != null) {
            wijzigingen.setActueleOnderzoek(actueelOnderzoek);
        }

        final Lo3Onderzoek historischOnderzoek =
                onderzoekMapper.bepaalHistorischOnderzoek(acties, objectSleutels, Collections.<Long>emptyList(), identiteitGroepElement);
        if (historischOnderzoek != null) {
            wijzigingen.setHistorischOnderzoek(historischOnderzoek);
        }

    }

}
