/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * Tagging interface voor leveringen. Dit kunnen zowel {@link AbstractSynchronisatieBericht}en zijn, als LO3 berichten.
 */
public interface SynchronisatieBericht {

    /**
     * Geeft het soort synchronisatie bericht terug.
     *
     * @return soort synchronisatie
     */
    SoortSynchronisatie geefSoortSynchronisatie();

    /**
     * Geeft de stuurgegevens terug.
     *
     * @return stuurgegevens
     */
    BerichtStuurgegevensGroepBericht getStuurgegevens();

    /**
     * Zet de stuurgegevens.
     *
     * @param stuurgegevens stuurgegevens
     */
    void setStuurgegevens(final BerichtStuurgegevensGroepBericht stuurgegevens);

    /**
     * Geeft de administratieve handeling terug.
     *
     * @return administratieve handeling
     */
    AdministratieveHandelingSynchronisatie getAdministratieveHandeling();

    /**
     * Zet de administratieve handeling.
     *
     * @param administratieveHandeling administratieve handeling
     */
    void setAdministratieveHandeling(final AdministratieveHandelingSynchronisatie administratieveHandeling);

    /**
     * Geeft de bericht parameters terug.
     *
     * @return bericht parameters
     */
    BerichtParametersGroepBericht getBerichtParameters();

    /**
     * Zet de bericht parameters.
     *
     * @param berichtParameters bericht parameters
     */
    void setBerichtParameters(final BerichtParametersGroepBericht berichtParameters);

    /**
     * Geeft de meldingen terug.
     *
     * @return meldingen
     */
    List<MeldingBericht> getMeldingen();

    /**
     * Zet de meldingen.
     *
     * @param meldingen meldingen
     */
    void setMeldingen(final List<MeldingBericht> meldingen);

    /**
     * Voegt een melding toe aan de meldingen.
     *
     * @param melding toe te voegen melding
     */
    void addMelding(final MeldingBericht melding);

    /**
     * Bepaalt of er meldingen zijn.
     *
     * @return true, als er meldingen zijn, anders false
     */
    boolean heeftMeldingenVoorLeveren();

    /**
     * Voegt een persoon toe aan de administratieve handeling in dit synchronisatiebericht.
     *
     * @param persoonHisVolledigView de view om toe te voegen
     * @return de index waarop de view is toegevoegd.
     */
    int addPersoon(final PersoonHisVolledigView persoonHisVolledigView);

    /**
     * Bepaalt of er personen zijn in de administratieve handeling in dit synchronisatiebericht.
     *
     * @return true, als er personen zijn, anders false
     */
    boolean heeftPersonen();
}
