/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.ber.BerichtBasis;

/**
 * Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden zijn. Hierbij geldt dat; Uitsluitend berichten aan en van 'de buitenwereld' gearchiveerd worden. Dus niet
 * de interne berichtenstroom tussen de diverse modules. De definitie van 'verzonden' is; door de BRP op de queue gezet.
 * De verzending aan de afnemer valt hier buiten scope.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtBericht implements BrpObject, BerichtBasis {

    private SoortBerichtAttribuut soort;
    private RichtingAttribuut richting;
    private BerichtStuurgegevensGroepBericht stuurgegevens;
    private BerichtParametersGroepBericht parameters;
    private BerichtResultaatGroepBericht resultaat;
    private BerichtStandaardGroepBericht standaard;
    private BerichtZoekcriteriaPersoonGroepBericht zoekcriteriaPersoon;
    private List<BerichtMeldingBericht> meldingen;

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBerichtAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RichtingAttribuut getRichting() {
        return richting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtStuurgegevensGroepBericht getStuurgegevens() {
        return stuurgegevens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtParametersGroepBericht getParameters() {
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtResultaatGroepBericht getResultaat() {
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtZoekcriteriaPersoonGroepBericht getZoekcriteriaPersoon() {
        return zoekcriteriaPersoon;
    }

    /**
     * Retourneert Bericht \ Meldingen van Bericht.
     *
     * @return Bericht \ Meldingen van Bericht.
     */
    public List<BerichtMeldingBericht> getMeldingen() {
        return meldingen;
    }

    /**
     * Zet Soort van Bericht.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortBerichtAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Richting van Bericht.
     *
     * @param richting Richting.
     */
    public void setRichting(final RichtingAttribuut richting) {
        this.richting = richting;
    }

    /**
     * Zet Stuurgegevens van Bericht.
     *
     * @param stuurgegevens Stuurgegevens.
     */
    public void setStuurgegevens(final BerichtStuurgegevensGroepBericht stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    /**
     * Zet Parameters van Bericht.
     *
     * @param parameters Parameters.
     */
    public void setParameters(final BerichtParametersGroepBericht parameters) {
        this.parameters = parameters;
    }

    /**
     * Zet Resultaat van Bericht.
     *
     * @param resultaat Resultaat.
     */
    public void setResultaat(final BerichtResultaatGroepBericht resultaat) {
        this.resultaat = resultaat;
    }

    /**
     * Zet Standaard van Bericht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final BerichtStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Zoekcriteria persoon van Bericht.
     *
     * @param zoekcriteriaPersoon Zoekcriteria persoon.
     */
    public void setZoekcriteriaPersoon(final BerichtZoekcriteriaPersoonGroepBericht zoekcriteriaPersoon) {
        this.zoekcriteriaPersoon = zoekcriteriaPersoon;
    }

    /**
     * Zet Bericht \ Meldingen van Bericht.
     *
     * @param meldingen Bericht \ Meldingen.
     */
    public void setMeldingen(final List<BerichtMeldingBericht> meldingen) {
        this.meldingen = meldingen;
    }

}
