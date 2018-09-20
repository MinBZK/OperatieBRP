/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonOverlijdenGroepBasis;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * g��n sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y ;-0
 * RvdP 9 jan 2012.
 *
 *
 *
 */
public abstract class AbstractPersoonOverlijdenGroepBericht extends AbstractGroepBericht implements
        PersoonOverlijdenGroepBasis
{

    private Datum               datumOverlijden;
    private String              gemeenteOverlijdenCode;
    private Partij              gemeenteOverlijden;
    private String              woonplaatsOverlijdenCode;
    private Plaats              woonplaatsOverlijden;
    private BuitenlandsePlaats  buitenlandsePlaatsOverlijden;
    private BuitenlandseRegio   buitenlandseRegioOverlijden;
    private LocatieOmschrijving omschrijvingLocatieOverlijden;
    private String              landOverlijdenCode;
    private Land                landOverlijden;

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Retourneert Gemeente overlijden van Overlijden.
     *
     * @return Gemeente overlijden.
     */
    public String getGemeenteOverlijdenCode() {
        return gemeenteOverlijdenCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * Retourneert Woonplaats overlijden van Overlijden.
     *
     * @return Woonplaats overlijden.
     */
    public String getWoonplaatsOverlijdenCode() {
        return woonplaatsOverlijdenCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Retourneert Land overlijden van Overlijden.
     *
     * @return Land overlijden.
     */
    public String getLandOverlijdenCode() {
        return landOverlijdenCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandOverlijden() {
        return landOverlijden;
    }

    /**
     * Zet Datum overlijden van Overlijden.
     *
     * @param datumOverlijden Datum overlijden.
     */
    public void setDatumOverlijden(final Datum datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * Zet Gemeente overlijden van Overlijden.
     *
     * @param gemeenteOverlijdenCode Gemeente overlijden.
     */
    public void setGemeenteOverlijdenCode(final String gemeenteOverlijdenCode) {
        this.gemeenteOverlijdenCode = gemeenteOverlijdenCode;
    }

    /**
     * Zet Gemeente overlijden van Overlijden.
     *
     * @param gemeenteOverlijden Gemeente overlijden.
     */
    public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    /**
     * Zet Woonplaats overlijden van Overlijden.
     *
     * @param woonplaatsOverlijdenCode Woonplaats overlijden.
     */
    public void setWoonplaatsOverlijdenCode(final String woonplaatsOverlijdenCode) {
        this.woonplaatsOverlijdenCode = woonplaatsOverlijdenCode;
    }

    /**
     * Zet Woonplaats overlijden van Overlijden.
     *
     * @param woonplaatsOverlijden Woonplaats overlijden.
     */
    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    /**
     * Zet Buitenlandse plaats overlijden van Overlijden.
     *
     * @param buitenlandsePlaatsOverlijden Buitenlandse plaats overlijden.
     */
    public void setBuitenlandsePlaatsOverlijden(final BuitenlandsePlaats buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * Zet Buitenlandse regio overlijden van Overlijden.
     *
     * @param buitenlandseRegioOverlijden Buitenlandse regio overlijden.
     */
    public void setBuitenlandseRegioOverlijden(final BuitenlandseRegio buitenlandseRegioOverlijden) {
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * Zet Omschrijving locatie overlijden van Overlijden.
     *
     * @param omschrijvingLocatieOverlijden Omschrijving locatie overlijden.
     */
    public void setOmschrijvingLocatieOverlijden(final LocatieOmschrijving omschrijvingLocatieOverlijden) {
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }

    /**
     * Zet Land overlijden van Overlijden.
     *
     * @param landOverlijdenCode Land overlijden.
     */
    public void setLandOverlijdenCode(final String landOverlijdenCode) {
        this.landOverlijdenCode = landOverlijdenCode;
    }

    /**
     * Zet Land overlijden van Overlijden.
     *
     * @param landOverlijden Land overlijden.
     */
    public void setLandOverlijden(final Land landOverlijden) {
        this.landOverlijden = landOverlijden;
    }

}
