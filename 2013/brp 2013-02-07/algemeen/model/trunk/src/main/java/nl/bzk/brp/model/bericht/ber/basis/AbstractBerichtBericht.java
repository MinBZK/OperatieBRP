/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.basis.BerichtBasis;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 *
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die
 * verzonden gaan worden.
 *
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van
 * archiveren nog niet bekend zal zijn. RvdP 8 november 2011.
 *
 *
 *
 */
public abstract class AbstractBerichtBericht implements BerichtBasis {

    private SoortBericht                     soort;
    private AdministratieveHandelingBericht  administratieveHandeling;
    private Berichtdata                      data;
    private DatumTijd                        datumTijdOntvangst;
    private DatumTijd                        datumTijdVerzenden;
    private BerichtBericht                   antwoordOp;
    private Richting                         richting;
    private BerichtStuurgegevensGroepBericht stuurgegevens;
    private BerichtParametersGroepBericht    parameters;
    private BerichtResultaatGroepBericht     resultaat;
    private List<BerichtMeldingBericht>      meldingen;

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBericht getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Berichtdata getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getDatumTijdOntvangst() {
        return datumTijdOntvangst;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getDatumTijdVerzenden() {
        return datumTijdVerzenden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtBericht getAntwoordOp() {
        return antwoordOp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Richting getRichting() {
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
    public void setSoort(final SoortBericht soort) {
        this.soort = soort;
    }

    /**
     * Zet Administratieve Handeling van Bericht.
     *
     * @param administratieveHandeling Administratieve Handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Data van Bericht.
     *
     * @param data Data.
     */
    public void setData(final Berichtdata data) {
        this.data = data;
    }

    /**
     * Zet Datum/tijd ontvangst van Bericht.
     *
     * @param datumTijdOntvangst Datum/tijd ontvangst.
     */
    public void setDatumTijdOntvangst(final DatumTijd datumTijdOntvangst) {
        this.datumTijdOntvangst = datumTijdOntvangst;
    }

    /**
     * Zet Datum/tijd verzenden van Bericht.
     *
     * @param datumTijdVerzenden Datum/tijd verzenden.
     */
    public void setDatumTijdVerzenden(final DatumTijd datumTijdVerzenden) {
        this.datumTijdVerzenden = datumTijdVerzenden;
    }

    /**
     * Zet Antwoord op van Bericht.
     *
     * @param antwoordOp Antwoord op.
     */
    public void setAntwoordOp(final BerichtBericht antwoordOp) {
        this.antwoordOp = antwoordOp;
    }

    /**
     * Zet Richting van Bericht.
     *
     * @param richting Richting.
     */
    public void setRichting(final Richting richting) {
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
     * Zet Bericht \ Meldingen van Bericht.
     *
     * @param meldingen Bericht \ Meldingen.
     */
    public void setMeldingen(final List<BerichtMeldingBericht> meldingen) {
        this.meldingen = meldingen;
    }

}
