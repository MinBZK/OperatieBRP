/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerificatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;

/**
 * Utility klasse voor de DAL-laag van de BRP Services.
 */
public final class DalUtil {

    private DalUtil() {
    }

    /**
     * Verzamel alle acties die onder de Persoon hangen.
     *
     * @param persoon
     *            De Persoon
     * @return Een Map met alle acties, en bij elke actie een set met de historie records waar ze aan gekoppeld zijn.
     */
    public static Map<BRPActie, Set<FormeleHistorie>> verzamelActies(final Persoon persoon) {
        final Map<BRPActie, Set<FormeleHistorie>> resultaat = new IdentityHashMap<>();

        resultaat.putAll(verzamelActiesUitgezonderdRelaties(persoon));
        resultaat.putAll(verzamelActiesVoorRelaties(persoon));

        return resultaat;
    }

    /**
     * Verzamel alle acties die onder de Persoon hangen, uitgezonderd acties die aan relatie-groepen hangen.
     *
     * @param persoon
     *            De Persoon
     * @return Een Map met alle acties, en bij elke actie een set met de historie records waar ze aan gekoppeld zijn.
     */
    public static Map<BRPActie, Set<FormeleHistorie>> verzamelActiesUitgezonderdRelaties(final Persoon persoon) {
        final Map<BRPActie, Set<FormeleHistorie>> resultaat = new IdentityHashMap<>();

        verzamelActies(resultaat, persoon.getPersoonAfgeleidAdministratiefHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonBijhoudingHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonDeelnameEuVerkiezingenHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonGeboorteHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonGeslachtsaanduidingHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonIDHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonInschrijvingHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonMigratieHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonNaamgebruikHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonNummerverwijzingHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonOverlijdenHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonPersoonskaartHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonSamengesteldeNaamHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonUitsluitingKiesrechtHistorieSet());
        verzamelActies(resultaat, persoon.getPersoonVerblijfsrechtHistorieSet());

        verzamelActiesBijCollecties(persoon, resultaat);

        return resultaat;
    }

    private static void verzamelActiesBijCollecties(final Persoon persoon, final Map<BRPActie, Set<FormeleHistorie>> resultaat) {
        for (final PersoonAdres persoonAdres : persoon.getPersoonAdresSet()) {
            verzamelActies(resultaat, persoonAdres.getPersoonAdresHistorieSet());
        }
        for (final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : persoon.getPersoonGeslachtsnaamcomponentSet()) {
            verzamelActies(resultaat, persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet());
        }
        for (final PersoonIndicatie persoonIndicatie : persoon.getPersoonIndicatieSet()) {
            verzamelActies(resultaat, persoonIndicatie.getPersoonIndicatieHistorieSet());
        }
        for (final PersoonNationaliteit persoonNationaliteit : persoon.getPersoonNationaliteitSet()) {
            verzamelActies(resultaat, persoonNationaliteit.getPersoonNationaliteitHistorieSet());
        }
        for (final PersoonReisdocument persoonReisdocument : persoon.getPersoonReisdocumentSet()) {
            verzamelActies(resultaat, persoonReisdocument.getPersoonReisdocumentHistorieSet());
        }
        for (final PersoonVerificatie persoonVerificatie : persoon.getPersoonVerificatieSet()) {
            verzamelActies(resultaat, persoonVerificatie.getPersoonVerificatieHistorieSet());
        }
        for (final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking : persoon.getPersoonVerstrekkingsbeperkingSet()) {
            verzamelActies(resultaat, persoonVerstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet());
        }
        for (final PersoonVoornaam persoonVoornaam : persoon.getPersoonVoornaamSet()) {
            verzamelActies(resultaat, persoonVoornaam.getPersoonVoornaamHistorieSet());
        }
    }

    private static Map<BRPActie, Set<FormeleHistorie>> verzamelActiesVoorRelaties(final Persoon persoon) {
        final Map<BRPActie, Set<FormeleHistorie>> acties = new IdentityHashMap<>();

        for (final Relatie relatie : persoon.getRelaties()) {
            verzamelActies(acties, relatie.getRelatieHistorieSet());

            for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
                verzamelActies(acties, betrokkenheid.getBetrokkenheidOuderHistorieSet());
                verzamelActies(acties, betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());

                if (betrokkenheid.getPersoon() != null
                    && persoon != betrokkenheid.getPersoon()
                    && SoortPersoon.ONBEKEND.equals(betrokkenheid.getPersoon().getSoortPersoon()))
                {
                    final Map<BRPActie, Set<FormeleHistorie>> gerelateerdeActies = verzamelActiesUitgezonderdRelaties(betrokkenheid.getPersoon());
                    acties.putAll(gerelateerdeActies);
                }
            }
        }

        return acties;
    }

    private static void verzamelActies(final Map<BRPActie, Set<FormeleHistorie>> resultaat, final Set<? extends FormeleHistorie> historieSet) {
        if (historieSet == null || historieSet.isEmpty()) {
            return;
        }

        for (final FormeleHistorie formeleHistorie : historieSet) {
            if (formeleHistorie.getActieInhoud() != null) {
                verzamelActie(resultaat, formeleHistorie.getActieInhoud(), formeleHistorie);
            }
            if (formeleHistorie.getActieVerval() != null) {
                verzamelActie(resultaat, formeleHistorie.getActieVerval(), formeleHistorie);
            }
            if (formeleHistorie instanceof MaterieleHistorie && ((MaterieleHistorie) formeleHistorie).getActieAanpassingGeldigheid() != null) {
                verzamelActie(resultaat, ((MaterieleHistorie) formeleHistorie).getActieAanpassingGeldigheid(), formeleHistorie);
            }
        }
    }

    private static void verzamelActie(final Map<BRPActie, Set<FormeleHistorie>> resultaat, final BRPActie actie, final FormeleHistorie historie) {
        final BRPActie actiePojo = PersistenceUtil.getPojoFromObject(actie);
        final FormeleHistorie historiePojo = PersistenceUtil.getPojoFromObject(historie);

        final Set<FormeleHistorie> actieHistorieSet;
        if (resultaat.containsKey(actiePojo)) {
            actieHistorieSet = resultaat.get(actiePojo);
        } else {
            actieHistorieSet = new IdentitySet<>();
            resultaat.put(actiePojo, actieHistorieSet);
        }

        actieHistorieSet.add(historiePojo);

        if (!(historiePojo instanceof DocumentHistorie)) {
            for (final Document document : actiePojo.getDocumentSet()) {
                verzamelActies(resultaat, document.getDocumentHistorieSet());
            }
        }
    }
}
