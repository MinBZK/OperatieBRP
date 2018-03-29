/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.List;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;

/**
 * Interface voor het consolideren van data.
 */
public interface ConsolidatieData {

    /**
     * Bewaar de relatie tussen de oude en de nieuwe actie.
     * @param nieuweActieParam de nieuwe actie
     * @param oudeActieParam de oude actie
     */
    void koppelNieuweActieMetOudeActie(final BRPActie nieuweActieParam, final BRPActie oudeActieParam);

    /**
     * Bewaar de relatie tussen de actie en het voorkomen.
     * @param actieParam de actie
     * @param voorkomenParam het voorkomen
     */
    void koppelActieMetVoorkomen(final BRPActie actieParam, final FormeleHistorie voorkomenParam);

    /**
     * Bewaar de relatie tussen het voorkomen en de stapel.
     * @param voorkomenParam het voorkomen
     * @param deltaStapelMatchParam de stapel
     */
    void koppelVoorkomenMetStapel(final FormeleHistorie voorkomenParam, final DeltaStapelMatch deltaStapelMatchParam);

    /**
     * @param mRijenParam de m-rijen
     * @param nieuweRijAanpassingen de verschillen op nieuwe rijen
     * @return een kopie van de actie consolidatie data waaruit acties zijn verwijderd die alleen voorkomen in m-rijen en niet gebruikt worden in nieuwe rijen
     */
    ConsolidatieData verwijderActieInMRijen(final Set<FormeleHistorie> mRijenParam, final List<Verschil> nieuweRijAanpassingen);

    /**
     * Voegt de gegeven nieuwe acties toe aan de mapping wanneer er een mapping bestaat van de nieuwe actie naar een
     * oude actie. Op deze manier kan later geanalyseerd worden of een nieuwe actie een oude vervangt en ook of de
     * nieuwe actie op een rij is toegevoegd.
     * @param nieuweActiesParam de set van nieuwe acties
     * @return een kopie van de actie consolidatie data waarin de nieuwe actie zijn toegevoegd
     */
    ConsolidatieData voegNieuweActiesToe(final Set<BRPActie> nieuweActiesParam);

    /**
     * Verwijderd de acties die een herkomst hebben in categorie 7 of 13.
     * @return een kopie van de actie consolidatie data waarin acties afkomstig uit Categorie 7 en 13 zijn verwijderd
     */
    ConsolidatieData verwijderCat07Cat13Acties();

    /**
     * Analyseert de mapping tussen oude en nieuwe acties en bepaald welke actie moeten worden geconsolideerd. De
     * stapels waar deze acties in voorkomen worden geretourneerd zodat de verschillen voor deze stapel kunnen worden
     * aangepast voor consolidatie.
     * @return de lijst met te consolideren stapels
     */
    Set<DeltaStapelMatch> bepaalTeVervangenStapels();

    /**
     * Voegt consolidatie data toe uit andere vergelijkingen.
     * @param data de consilidatie data uit een andere vergelijking.
     */
    void addConsoldatieData(final ConsolidatieData data);

}
