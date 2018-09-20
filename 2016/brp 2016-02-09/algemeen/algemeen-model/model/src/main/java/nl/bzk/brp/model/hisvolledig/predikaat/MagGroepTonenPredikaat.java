/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.apache.commons.collections.Predicate;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

/**
 * Dit predikaat filtert groep voorkomens op basis van groepautorisaties
 * die afgeleid zijn van attribuutautorisaties. Indien een attribuut autorisatie
 * bestaat voor een gegeven attribuut is impliciet ook de groep geautoriseerd.
 */
@Regels(Regel.VR00120)
public class MagGroepTonenPredikaat implements Predicate {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Query voor ophalen van groep-ids die standaard geautoriseerd zijn, aangezien deze geen te-autoriseren attributen
     * bevatten. Hiervan is ouderschap en ouderlijk gezag op de hoofdpersoon uitgesloten, aangezien deze groepen nooit
     * geautoriseerd zullen mogen worden.
     *
     select id from kern.element where srt = 2
     and id not in (select groep from kern.element where srt = 3 and expressie is not null)
     and naam not in ('Ouder.OuderlijkGezag', 'Ouder.Ouderschap')
     order by id
     */
    private static final int[] GROEPEN_ZONDER_ATTRIBUUT_EXPRESSIE = {
        2068, 2070, 2072, 2075, 2077, 2078, 2081, 2154, 3599, 3774, 3784, 4618, 6128,
        6217, 6223, 8119, 9019, 10051, 10717, 10739, 10754, 10841, 11095, 12704, 12716,
        12786, 12798, 13156, 13238, 13320, 13402, 14347, 14370,
    };

    /**
     * negeer groepen die attributen bevatten zonder ook maar één expressie (predikaat geeft true voor het voorkomen).
     */
    private static final Set<ElementEnum> GROEPEN_ZONDER_ATTRIBUUT_EXPRESSIES_ENUM_SET = new HashSet<ElementEnum>() {
        {
            for (final int groepId : GROEPEN_ZONDER_ATTRIBUUT_EXPRESSIE) {
                for (final ElementEnum elementEnum : ElementEnum.values()) {
                    if (elementEnum.getId().equals(groepId)) {
                        add(elementEnum);
                    }
                }
            }
        }
    };

    private final Set<ElementEnum> geautoriseerdeGroepen;

    /**
     *
     * @param geautoriseerdeGroepen set van groep element enums waarvoor bepaald is dat ze geautoriseerd zijn.
     */
    public MagGroepTonenPredikaat(final Set<ElementEnum> geautoriseerdeGroepen) {
        this.geautoriseerdeGroepen = geautoriseerdeGroepen;
    }

    /**
     * Doet de feitelijke evaluatie of een voorkomen behoort tot een geautoriseerde groep.
     *
     * @param object het voorkomen
     * @return boolean indicatie of het tot de geautoriseerde groepen behoort
     */
    @Override
    public final boolean evaluate(final Object object) {
        ElementEnum objectElement = ((ElementIdentificeerbaar) object).getElementIdentificatie();
        // indien het een object van een gerelateerde persoon dient een andere enum gebruikt worden
        if (object instanceof GerelateerdIdentificeerbaar) {
            if (object instanceof HisRelatieModel) {
                // Bij HisRelatieModel is geen subtypering aanwezig op de elemenidentificatie, maar altijd RELATIE_STANDAARD of RELATIE_IDENTIFICATIE
                zetJuisteGerelateerdObjecttypeVoorRelatie((HisRelatieModel) object);
            }

            final GerelateerdIdentificeerbaar gerelateerdIdentificeerbaar = (GerelateerdIdentificeerbaar) object;
            if (gerelateerdIdentificeerbaar.getGerelateerdeObjectType() != null) {
                objectElement = gerelateerdIdentificeerbaar.getGerelateerdeObjectType();
            }
        }
        return geautoriseerdeGroepen.contains(objectElement) || GROEPEN_ZONDER_ATTRIBUUT_EXPRESSIES_ENUM_SET.contains(objectElement);
    }

    /**
     * Zet het juiste gerelateerd objecttype voor relatie.
     *
     * @param hisRelatieModel the his relatie model
     */
    private void zetJuisteGerelateerdObjecttypeVoorRelatie(final HisRelatieModel hisRelatieModel) {
        final ElementEnum elementEnum;
        final SoortRelatie soortRelatie = hisRelatieModel.getRelatie().getSoort().getWaarde();
        if (hisRelatieModel.getElementIdentificatie().name().contains("_STANDAARD")) {
            elementEnum = ElementIdentificatieUtil.geefElementEnumVoorRelatieStandaardGroep(soortRelatie);
        } else if (hisRelatieModel.getElementIdentificatie().name().contains("_IDENTITEIT")) {
            elementEnum = ElementIdentificatieUtil.geefElementEnumVoorRelatieIdentiteitGroep(soortRelatie);
        } else {
            elementEnum = hisRelatieModel.getElementIdentificatie();
            LOGGER.warn("Er is een groep van hisrelatiemodel die nog niet ondersteund wordt in het predikaat: "
                + hisRelatieModel.getElementIdentificatie());
        }

        hisRelatieModel.setGerelateerdeObjectType(elementEnum);
    }
}
