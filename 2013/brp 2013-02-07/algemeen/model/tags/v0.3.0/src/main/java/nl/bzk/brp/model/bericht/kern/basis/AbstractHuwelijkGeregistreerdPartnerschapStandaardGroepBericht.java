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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.HuwelijkGeregistreerdPartnerschapStandaardGroepBasis;


/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap.
 * 2. Vorm van historie: alleen formeel. Motivatie: alle (materi�le) tijdsaspecten zijn uitgemodelleerd (met datum
 * aanvang en datum einde), waardoor dus geen (extra) materi�le historie van toepassing is. Verder 'herleeft' een
 * Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk be�indigd. Met andere woorden: twee personen
 * die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende) exemplaren van
 * Relatie: het eerste Huwelijk, en het tweede.
 * Door deze zienswijze (die volgt uit de definitie van Relatie) is er DUS geen sprake van materi�le historie, en
 * volstaat dus de formele historie.
 * RvdP 17 jan 2012.
 *
 *
 *
 */
public abstract class AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepBericht extends AbstractGroepBericht
        implements HuwelijkGeregistreerdPartnerschapStandaardGroepBasis
{

    private Datum                   datumAanvang;
    private String                  gemeenteAanvangCode;
    private Partij                  gemeenteAanvang;
    private String                  woonplaatsAanvangCode;
    private Plaats                  woonplaatsAanvang;
    private BuitenlandsePlaats      buitenlandsePlaatsAanvang;
    private BuitenlandseRegio       buitenlandseRegioAanvang;
    private LocatieOmschrijving     omschrijvingLocatieAanvang;
    private String                  landAanvangCode;
    private Land                    landAanvang;
    private String                  redenEindeCode;
    private RedenBeeindigingRelatie redenEinde;
    private Datum                   datumEinde;
    private String                  gemeenteEindeCode;
    private Partij                  gemeenteEinde;
    private String                  woonplaatsEindeCode;
    private Plaats                  woonplaatsEinde;
    private BuitenlandsePlaats      buitenlandsePlaatsEinde;
    private BuitenlandseRegio       buitenlandseRegioEinde;
    private LocatieOmschrijving     omschrijvingLocatieEinde;
    private String                  landEindeCode;
    private Land                    landEinde;

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Retourneert Gemeente aanvang van Standaard.
     *
     * @return Gemeente aanvang.
     */
    public String getGemeenteAanvangCode() {
        return gemeenteAanvangCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Retourneert Woonplaats aanvang van Standaard.
     *
     * @return Woonplaats aanvang.
     */
    public String getWoonplaatsAanvangCode() {
        return woonplaatsAanvangCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaatsAanvang() {
        return woonplaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegio getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Retourneert Land aanvang van Standaard.
     *
     * @return Land aanvang.
     */
    public String getLandAanvangCode() {
        return landAanvangCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandAanvang() {
        return landAanvang;
    }

    /**
     * Retourneert Reden einde van Standaard.
     *
     * @return Reden einde.
     */
    public String getRedenEindeCode() {
        return redenEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie getRedenEinde() {
        return redenEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Gemeente einde van Standaard.
     *
     * @return Gemeente einde.
     */
    public String getGemeenteEindeCode() {
        return gemeenteEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Retourneert Woonplaats einde van Standaard.
     *
     * @return Woonplaats einde.
     */
    public String getWoonplaatsEindeCode() {
        return woonplaatsEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaatsEinde() {
        return woonplaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegio getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Retourneert Land einde van Standaard.
     *
     * @return Land einde.
     */
    public String getLandEindeCode() {
        return landEindeCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandEinde() {
        return landEinde;
    }

    /**
     * Zet Datum aanvang van Standaard.
     *
     * @param datumAanvang Datum aanvang.
     */
    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Zet Gemeente aanvang van Standaard.
     *
     * @param gemeenteAanvangCode Gemeente aanvang.
     */
    public void setGemeenteAanvangCode(final String gemeenteAanvangCode) {
        this.gemeenteAanvangCode = gemeenteAanvangCode;
    }

    /**
     * Zet Gemeente aanvang van Standaard.
     *
     * @param gemeenteAanvang Gemeente aanvang.
     */
    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Zet Woonplaats aanvang van Standaard.
     *
     * @param woonplaatsAanvangCode Woonplaats aanvang.
     */
    public void setWoonplaatsAanvangCode(final String woonplaatsAanvangCode) {
        this.woonplaatsAanvangCode = woonplaatsAanvangCode;
    }

    /**
     * Zet Woonplaats aanvang van Standaard.
     *
     * @param woonplaatsAanvang Woonplaats aanvang.
     */
    public void setWoonplaatsAanvang(final Plaats woonplaatsAanvang) {
        this.woonplaatsAanvang = woonplaatsAanvang;
    }

    /**
     * Zet Buitenlandse plaats aanvang van Standaard.
     *
     * @param buitenlandsePlaatsAanvang Buitenlandse plaats aanvang.
     */
    public void setBuitenlandsePlaatsAanvang(final BuitenlandsePlaats buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Zet Buitenlandse regio aanvang van Standaard.
     *
     * @param buitenlandseRegioAanvang Buitenlandse regio aanvang.
     */
    public void setBuitenlandseRegioAanvang(final BuitenlandseRegio buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    /**
     * Zet Omschrijving locatie aanvang van Standaard.
     *
     * @param omschrijvingLocatieAanvang Omschrijving locatie aanvang.
     */
    public void setOmschrijvingLocatieAanvang(final LocatieOmschrijving omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Zet Land aanvang van Standaard.
     *
     * @param landAanvangCode Land aanvang.
     */
    public void setLandAanvangCode(final String landAanvangCode) {
        this.landAanvangCode = landAanvangCode;
    }

    /**
     * Zet Land aanvang van Standaard.
     *
     * @param landAanvang Land aanvang.
     */
    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    /**
     * Zet Reden einde van Standaard.
     *
     * @param redenEindeCode Reden einde.
     */
    public void setRedenEindeCode(final String redenEindeCode) {
        this.redenEindeCode = redenEindeCode;
    }

    /**
     * Zet Reden einde van Standaard.
     *
     * @param redenEinde Reden einde.
     */
    public void setRedenEinde(final RedenBeeindigingRelatie redenEinde) {
        this.redenEinde = redenEinde;
    }

    /**
     * Zet Datum einde van Standaard.
     *
     * @param datumEinde Datum einde.
     */
    public void setDatumEinde(final Datum datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Zet Gemeente einde van Standaard.
     *
     * @param gemeenteEindeCode Gemeente einde.
     */
    public void setGemeenteEindeCode(final String gemeenteEindeCode) {
        this.gemeenteEindeCode = gemeenteEindeCode;
    }

    /**
     * Zet Gemeente einde van Standaard.
     *
     * @param gemeenteEinde Gemeente einde.
     */
    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Zet Woonplaats einde van Standaard.
     *
     * @param woonplaatsEindeCode Woonplaats einde.
     */
    public void setWoonplaatsEindeCode(final String woonplaatsEindeCode) {
        this.woonplaatsEindeCode = woonplaatsEindeCode;
    }

    /**
     * Zet Woonplaats einde van Standaard.
     *
     * @param woonplaatsEinde Woonplaats einde.
     */
    public void setWoonplaatsEinde(final Plaats woonplaatsEinde) {
        this.woonplaatsEinde = woonplaatsEinde;
    }

    /**
     * Zet Buitenlandse plaats einde van Standaard.
     *
     * @param buitenlandsePlaatsEinde Buitenlandse plaats einde.
     */
    public void setBuitenlandsePlaatsEinde(final BuitenlandsePlaats buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Zet Buitenlandse regio einde van Standaard.
     *
     * @param buitenlandseRegioEinde Buitenlandse regio einde.
     */
    public void setBuitenlandseRegioEinde(final BuitenlandseRegio buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    /**
     * Zet Omschrijving locatie einde van Standaard.
     *
     * @param omschrijvingLocatieEinde Omschrijving locatie einde.
     */
    public void setOmschrijvingLocatieEinde(final LocatieOmschrijving omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Zet Land einde van Standaard.
     *
     * @param landEindeCode Land einde.
     */
    public void setLandEindeCode(final String landEindeCode) {
        this.landEindeCode = landEindeCode;
    }

    /**
     * Zet Land einde van Standaard.
     *
     * @param landEinde Land einde.
     */
    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

}
