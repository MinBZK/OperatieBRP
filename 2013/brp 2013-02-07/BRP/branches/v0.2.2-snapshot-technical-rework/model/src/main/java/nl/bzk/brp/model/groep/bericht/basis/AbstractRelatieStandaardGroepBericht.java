/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.attribuuttype.Woonplaatscode;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.RelatieStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;

/**
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractRelatieStandaardGroepBericht extends AbstractGroepBericht
    implements RelatieStandaardGroepBasis
{
    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum datumAanvang;
    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum  datumEinde;
    private Land landAanvang;
    private Landcode landAanvangCode;
    private Land landEinde;
    private Landcode landEindeCode;
    private Partij gemeenteAanvang;
    private Gemeentecode gemeenteAanvangCode;
    private Partij gemeenteEinde;
    private Gemeentecode gemeenteEindeCode;
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    private OmschrijvingEnumeratiewaarde omschrijvingLocatieAanvang;
    private OmschrijvingEnumeratiewaarde omschrijvingLocatieEinde;
    private Plaats woonPlaatsAanvang;
    private Woonplaatscode woonPlaatsAanvangCode;
    private Plaats woonPlaatsEinde;
    private Woonplaatscode woonPlaatsEindeCode;
    private BuitenlandsePlaats buitenlandsePlaatsAanvang;
    private BuitenlandsePlaats buitenlandsePlaatsEinde;
    private BuitenlandseRegio buitenlandseRegioAanvang;
    private BuitenlandseRegio buitenlandseRegioEinde;

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    @Override
    public Datum getDatumEinde() {
        return datumEinde;
    }

    @Override
    public Land getLandAanvang() {
        return landAanvang;
    }

    @Override
    public Land getLandEinde() {
        return landEinde;
    }

    @Override
    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    @Override
    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    @Override
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    @Override
    public OmschrijvingEnumeratiewaarde getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    @Override
    public OmschrijvingEnumeratiewaarde getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    @Override
    public Plaats getWoonPlaatsAanvang() {
        return woonPlaatsAanvang;
    }

    @Override
    public Plaats getWoonPlaatsEinde() {
        return woonPlaatsEinde;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    public Landcode getLandAanvangCode() {
        return landAanvangCode;
    }

    public Landcode getLandEindeCode() {
        return landEindeCode;
    }

    public Gemeentecode getGemeenteAanvangCode() {
        return gemeenteAanvangCode;
    }

    public Gemeentecode getGemeenteEindeCode() {
        return gemeenteEindeCode;
    }

    public Woonplaatscode getWoonPlaatsAanvangCode() {
        return woonPlaatsAanvangCode;
    }

    public Woonplaatscode getWoonPlaatsEindeCode() {
        return woonPlaatsEindeCode;
    }

    public void setDatumEinde(final Datum datumEinde) {
        this.datumEinde = datumEinde;
    }

    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    public void setLandAanvangCode(final Landcode landAanvangCode) {
        this.landAanvangCode = landAanvangCode;
    }

    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

    public void setLandEindeCode(final Landcode landEindeCode) {
        this.landEindeCode = landEindeCode;
    }

    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    public void setGemeenteAanvangCode(final Gemeentecode gemeenteAanvangCode) {
        this.gemeenteAanvangCode = gemeenteAanvangCode;
    }

    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    public void setGemeenteEindeCode(final Gemeentecode gemeenteEindeCode) {
        this.gemeenteEindeCode = gemeenteEindeCode;
    }

    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    public void setOmschrijvingLocatieAanvang(final OmschrijvingEnumeratiewaarde omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    public void setOmschrijvingLocatieEinde(final OmschrijvingEnumeratiewaarde omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    public void setWoonPlaatsAanvang(final Plaats woonPlaatsAanvang) {
        this.woonPlaatsAanvang = woonPlaatsAanvang;
    }

    public void setWoonPlaatsAanvangCode(final Woonplaatscode woonPlaatsAanvangCode) {
        this.woonPlaatsAanvangCode = woonPlaatsAanvangCode;
    }

    public void setWoonPlaatsEinde(final Plaats woonPlaatsEinde) {
        this.woonPlaatsEinde = woonPlaatsEinde;
    }

    public void setWoonPlaatsEindeCode(final Woonplaatscode woonPlaatsEindeCode) {
        this.woonPlaatsEindeCode = woonPlaatsEindeCode;
    }

    public void setBuitenlandsePlaatsAanvang(final BuitenlandsePlaats buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    public void setBuitenlandsePlaatsEinde(final BuitenlandsePlaats buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    public void setBuitenlandseRegioAanvang(final BuitenlandseRegio buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    public void setBuitenlandseRegioEinde(final BuitenlandseRegio buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }


}
