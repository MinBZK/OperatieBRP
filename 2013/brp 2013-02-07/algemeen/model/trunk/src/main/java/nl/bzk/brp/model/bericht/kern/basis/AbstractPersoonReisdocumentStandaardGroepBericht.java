/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LengteInCm;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVervallenReisdocument;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonReisdocumentStandaardGroepBasis;


/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter ��nmalig wijzigen, zoals
 * reden vervallen.
 * Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 * RvdP 26 jan 2012.
 *
 *
 *
 */
public abstract class AbstractPersoonReisdocumentStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonReisdocumentStandaardGroepBasis
{

    private ReisdocumentNummer               nummer;
    private LengteInCm                       lengteHouder;
    private String                           autoriteitVanAfgifteCode;
    private AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte;
    private Datum                            datumIngangDocument;
    private Datum                            datumUitgifte;
    private Datum                            datumVoorzieneEindeGeldigheid;
    private Datum                            datumInhoudingVermissing;
    private String                           redenVervallenCode;
    private RedenVervallenReisdocument       redenVervallen;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReisdocumentNummer getNummer() {
        return nummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LengteInCm getLengteHouder() {
        return lengteHouder;
    }

    /**
     * Retourneert Autoriteit van afgifte van Standaard.
     *
     * @return Autoriteit van afgifte.
     */
    public String getAutoriteitVanAfgifteCode() {
        return autoriteitVanAfgifteCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVoorzieneEindeGeldigheid() {
        return datumVoorzieneEindeGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * Retourneert Reden vervallen van Standaard.
     *
     * @return Reden vervallen.
     */
    public String getRedenVervallenCode() {
        return redenVervallenCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVervallenReisdocument getRedenVervallen() {
        return redenVervallen;
    }

    /**
     * Zet Nummer van Standaard.
     *
     * @param nummer Nummer.
     */
    public void setNummer(final ReisdocumentNummer nummer) {
        this.nummer = nummer;
    }

    /**
     * Zet Lengte houder van Standaard.
     *
     * @param lengteHouder Lengte houder.
     */
    public void setLengteHouder(final LengteInCm lengteHouder) {
        this.lengteHouder = lengteHouder;
    }

    /**
     * Zet Autoriteit van afgifte van Standaard.
     *
     * @param autoriteitVanAfgifteCode Autoriteit van afgifte.
     */
    public void setAutoriteitVanAfgifteCode(final String autoriteitVanAfgifteCode) {
        this.autoriteitVanAfgifteCode = autoriteitVanAfgifteCode;
    }

    /**
     * Zet Autoriteit van afgifte van Standaard.
     *
     * @param autoriteitVanAfgifte Autoriteit van afgifte.
     */
    public void setAutoriteitVanAfgifte(final AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte) {
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    /**
     * Zet Datum ingang document van Standaard.
     *
     * @param datumIngangDocument Datum ingang document.
     */
    public void setDatumIngangDocument(final Datum datumIngangDocument) {
        this.datumIngangDocument = datumIngangDocument;
    }

    /**
     * Zet Datum uitgifte van Standaard.
     *
     * @param datumUitgifte Datum uitgifte.
     */
    public void setDatumUitgifte(final Datum datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    /**
     * Zet Datum voorziene einde geldigheid van Standaard.
     *
     * @param datumVoorzieneEindeGeldigheid Datum voorziene einde geldigheid.
     */
    public void setDatumVoorzieneEindeGeldigheid(final Datum datumVoorzieneEindeGeldigheid) {
        this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
    }

    /**
     * Zet Datum inhouding/vermissing van Standaard.
     *
     * @param datumInhoudingVermissing Datum inhouding/vermissing.
     */
    public void setDatumInhoudingVermissing(final Datum datumInhoudingVermissing) {
        this.datumInhoudingVermissing = datumInhoudingVermissing;
    }

    /**
     * Zet Reden vervallen van Standaard.
     *
     * @param redenVervallenCode Reden vervallen.
     */
    public void setRedenVervallenCode(final String redenVervallenCode) {
        this.redenVervallenCode = redenVervallenCode;
    }

    /**
     * Zet Reden vervallen van Standaard.
     *
     * @param redenVervallen Reden vervallen.
     */
    public void setRedenVervallen(final RedenVervallenReisdocument redenVervallen) {
        this.redenVervallen = redenVervallen;
    }

}
