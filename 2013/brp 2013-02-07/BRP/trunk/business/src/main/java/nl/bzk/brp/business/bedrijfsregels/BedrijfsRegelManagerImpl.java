/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;


/**
 * Standaard implementatie van de {@link BedrijfsRegelManager} interface. Deze bedrijfsregelmanager bepaalt welke
 * bedrijfsregels uitgevoerd dienen te worden voor een bepaalde soort actie of een specifieke bericht klasse. Merk op
 * dat er dus actie specifieke bedrijfsregels zijn en bericht specifieke bedrijfsregels.
 */
public class BedrijfsRegelManagerImpl implements BedrijfsRegelManager {

    private final Map<SoortActie,
            List<? extends ActieBedrijfsRegel>> bedrijfsRegelsPerActie;
    private final Map<Class<? extends AbstractBijhoudingsBericht>,
            List<? extends BijhoudingsBerichtBedrijfsRegel>> bedrijfsRegelsPerBericht;
    private final Map<Class<? extends AbstractBijhoudingsBericht>,
            List<? extends BerichtContextBedrijfsRegel>> bedrijfsRegelsPerBerichtContext;
    private final List<? extends BerichtBedrijfsRegel> bedrijfsRegelsAlleBerichten;

    /**
     * Constructor voor de bedrijfsregelmanager, als argumenten worden twee mappen meegegeven die per soort actie
     * en per bericht class de bedrijfsregels die dienen te worden uitgevoerd, bevatten.
     *
     * @param bedrijfsRegelsPerActie   De map die per soort actie een lijst van bedrijfsregels kent.
     * @param bedrijfsRegelsPerBericht De map die per bericht class een lijst van bedrijfsregels kent.
     * @param bedrijfsRegelsPerBerichtContext
     *                                 De map die per bericht class een lijst van bedrijfsregels kent.
     * @param bedrijfsRegelsAlleBerichten De lijst van bedrijfsregels die geldig is voor alle berichten.
     */

    public BedrijfsRegelManagerImpl(
            final Map<SoortActie,
                    List<? extends ActieBedrijfsRegel>> bedrijfsRegelsPerActie,
            final Map<Class<? extends AbstractBijhoudingsBericht>,
                    List<? extends BijhoudingsBerichtBedrijfsRegel>> bedrijfsRegelsPerBericht,
            final Map<Class<? extends AbstractBijhoudingsBericht>,
                    List<? extends BerichtContextBedrijfsRegel>>
                    bedrijfsRegelsPerBerichtContext,
            final List<? extends BerichtBedrijfsRegel> bedrijfsRegelsAlleBerichten)
    {
        this.bedrijfsRegelsPerActie = bedrijfsRegelsPerActie;
        this.bedrijfsRegelsPerBericht = bedrijfsRegelsPerBericht;
        this.bedrijfsRegelsPerBerichtContext = bedrijfsRegelsPerBerichtContext;
        this.bedrijfsRegelsAlleBerichten = bedrijfsRegelsAlleBerichten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ActieBedrijfsRegel> getUitTeVoerenActieBedrijfsRegels(final SoortActie soortActie) {
        List<? extends ActieBedrijfsRegel> regels;
        if (bedrijfsRegelsPerActie == null) {
            regels = null;
        } else {
            regels = bedrijfsRegelsPerActie.get(soortActie);
        }
        return regels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends BerichtBedrijfsRegel> getUitTeVoerenBerichtBedrijfsRegels() {
        return bedrijfsRegelsAlleBerichten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends BijhoudingsBerichtBedrijfsRegel> getUitTeVoerenBijhoudingsBerichtBedrijfsRegels(
            final Class<? extends AbstractBijhoudingsBericht> berichtClass)
    {
        List<? extends BijhoudingsBerichtBedrijfsRegel> regels;
        if (bedrijfsRegelsPerBericht == null) {
            regels = null;
        } else {
            regels = bedrijfsRegelsPerBericht.get(berichtClass);
        }
        return regels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends BerichtContextBedrijfsRegel> getUitTeVoerenBerichtContextBedrijfsRegels(
            final Class<? extends AbstractBijhoudingsBericht> berichtClass)
    {
        List<? extends BerichtContextBedrijfsRegel> regels;
        if (bedrijfsRegelsPerBerichtContext == null) {
            regels = null;
        } else {
            regels = bedrijfsRegelsPerBerichtContext.get(berichtClass);
        }
        return regels;
    }
}
