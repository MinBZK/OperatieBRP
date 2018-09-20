/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0007;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * Vader moet voldoen aan kandidaatregels.
 * <p/>
 * Als volgens de kandidaatregels een kandidaat vader is geregistreerd in de BRP, dan en slechts dan moet door Actie
 * RegistratieGeboorte het Ouderschap van deze vader worden geregistreerd.
 * <p/>
 * Opmerkingen: 1. Vader is de Persoon waarbij de indicatie OuderUitWieHetKindIsVoortgekomen ontbreekt in de groep
 * Ouderschap. 2. Melding [1] van toepassing als in het Bericht geen vader is opgenomen terwijl die volgens de
 * kandidaatregels wel bestaat, bij elke andere foutsituatie is [2] van toepassing. 3. I.c.m. BRBY0147 voorkomt deze
 * regel tevens dat ErkenningNaGeboorte wordt uitgevoerd terwijl er wel degelijk een juridisch vader is. 4. Als er
 * volgens de kandidaatregels meerdere kandidaten zijn, dan moet één daarvan als vader worden geregistreerd.
 * <p/>
 * In deze klasse wordt alles behalve punt 4 getoetst.
 *
 * @brp.bedrijfsregel BRBY0033
 */
@Named("BRBY0033")
public class BRBY0033 extends AbstractKandidaatVaderRegel {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0033;
    }

    @Override
    protected boolean overtreedtKandidaatVaderGesteldeVoorwaarden(final List<PersoonView> kandidatenVaders,
            final PersoonBericht vader)
    {
        boolean isOvertreden = false;

        if (kandidatenVaders.size() > 0) {
            isOvertreden = vader == null || ontbreektVaderInLijstKandidaten(vader, kandidatenVaders);
        } else if (vader != null) {
            isOvertreden = true;
        }

        return isOvertreden;
    }

    /**
     * Controleert of de vader ontbreekt in lijst kandidaten vader.
     *
     * @param vader vader
     * @param kandidatenVaders kandidaten vaders
     * @return true als vader ontbreekt in lijst met kandidaten
     */
    private boolean ontbreektVaderInLijstKandidaten(final PersoonBericht vader, final List<PersoonView> kandidatenVaders)
    {
        boolean ontbreekt = true;
        for (final PersoonView kv : kandidatenVaders) {
            if (isGelijk(vader, kv)) {
                ontbreekt = false;
                break;
            }
        }
        return ontbreekt;
    }

    /**
     * Controleert of het persoon in het bericht overeenkomt met de persoon uit de view.
     *
     * @param persoonBericht persoon uit het Bericht
     * @param persoonHisVolledigView persoon uit de database
     * @return true als het dezelfde persoon betreft
     */
    private boolean isGelijk(final PersoonBericht persoonBericht, final PersoonView persoonHisVolledigView) {
        return isNietIngeschreveGelijk(persoonBericht, persoonHisVolledigView)
                || isIngeschreveneGelijk(persoonBericht, persoonHisVolledigView);
    }

    /**
     * Controleert of de niet ingeschreve overeenkomt met elkaar.
     *
     * @param persoonBericht persoon uit het bericht.
     * @param persoonHisVolledigView persoon het uit het model
     * @return trie indien gelijk
     */
    private boolean isNietIngeschreveGelijk(final PersoonBericht persoonBericht,
            final PersoonView persoonHisVolledigView)
    {
        return persoonBericht.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE
                && persoonHisVolledigView.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE
                && new BRBY0007().isGelijk(persoonBericht, persoonHisVolledigView);
    }

    /**
     * Controleert of de ingeschreve overeenkomt met elkaar.
     *
     * @param persoonBericht persoon uit het bericht.
     * @param persoonHisVolledigView persoon het uit het model
     * @return trie indien gelijk
     */
    private boolean isIngeschreveneGelijk(final PersoonBericht persoonBericht, final PersoonView persoonHisVolledigView)
    {
        return persoonBericht.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE
                && persoonHisVolledigView.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE
                && persoonBericht.getObjectSleutelDatabaseID().equals(persoonHisVolledigView.getID());
    }
}
