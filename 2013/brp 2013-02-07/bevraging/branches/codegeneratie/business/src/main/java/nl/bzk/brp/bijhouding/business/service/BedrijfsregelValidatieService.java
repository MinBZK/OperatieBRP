/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.AbstractValidatie;
import nl.bzk.brp.bijhouding.business.service.exception.OnbekendeValidatieExceptie;
import nl.bzk.brp.domein.brm.Regel;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;


/**
 * Service om een bedrijfsregelvalidatie te creeren en aan te roepen.
 * Voor het valideren van bedrijfsregels wordt het Command pattern gebruikt.
 * Dit is de "aanroeper" uit het Command pattern.
 */
public interface BedrijfsregelValidatieService {

    /**
     * Creeert de validatie.
     *
     * @param regel de bedrijfsregel waarvan een validatie moet worden gecreeerd
     * @param bericht het bericht dat gevalideerd moet worden
     * @throws OnbekendeValidatieExceptie als er geen Spring bean is met als naam de bedrijfsregelcode
     * @return een nieuwe validatie instantie voor de gegeven bedrijfsregel
     */
    AbstractValidatie<?> creeerValidatie(Regel regel, BerichtVerzoek<?> bericht) throws OnbekendeValidatieExceptie;

    /**
     * Voert de gegeven validatie uit en verwerkt het resultaat.
     *
     * @param validatie de uit te voeren validatie
     * @param gedrag het gedrag dat hoort bij de validatie
     * @return Bij resultaat {@link nl.bzk.brp.bijhouding.business.bedrijfsregel.ValidatieResultaat#CONFORM} null.<br>
     *         Bij resultaat {@link nl.bzk.brp.bijhouding.business.bedrijfsregel.ValidatieResultaat#AFWIJKEND} een
     *         gewone {@link BerichtVerwerkingsFout} die hoort bij de gegeven validatie en gedrag.<br>
     *         Als de gegeven validatie null is, een {@link BerichtVerwerkingsFout} met zwaarte
     *         {@link nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte#SYSTEEM}
     */
    BerichtVerwerkingsFout roepValidatieAan(AbstractValidatie<?> validatie, Regelimplementatiesituatie gedrag);

}
