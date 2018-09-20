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
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeboorteGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;


/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeboorteGroepBericht extends AbstractGroepBericht implements PersoonGeboorteGroepBasis {

    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum              datumGeboorte;
    private Partij             gemeenteGeboorte;
    private Gemeentecode       gemeenteGeboorteCode;
    private Plaats             woonplaatsGeboorte;
    private Woonplaatscode woonplaatsGeboorteCode;
    private BuitenlandsePlaats buitenlandseGeboortePlaats;
    private BuitenlandseRegio  buitenlandseRegioGeboorte;
    private Land               landGeboorte;
    private Landcode           landGeboorteCode;
    private OmschrijvingEnumeratiewaarde omschrijvingGeboorteLocatie;

    @Override
    public Datum getDatumGeboorte() {
        return datumGeboorte;
    }

    public void setDatumGeboorte(final Datum datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandseGeboortePlaats() {
        return buitenlandseGeboortePlaats;
    }

    public void setBuitenlandseGeboortePlaats(final BuitenlandsePlaats buitenlandseGeboortePlaats) {
        this.buitenlandseGeboortePlaats = buitenlandseGeboortePlaats;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    public void setBuitenlandseRegioGeboorte(final BuitenlandseRegio buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    @Override
    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    @Override
    public OmschrijvingEnumeratiewaarde getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    public void setOmschrijvingGeboorteLocatie(final OmschrijvingEnumeratiewaarde omschrijvingGeboorteLocatie) {
        this.omschrijvingGeboorteLocatie = omschrijvingGeboorteLocatie;
    }

    public Gemeentecode getGemeenteGeboorteCode() {
        return gemeenteGeboorteCode;
    }

    public Woonplaatscode getWoonplaatsGeboorteCode() {
        return woonplaatsGeboorteCode;
    }

    public Landcode getLandGeboorteCode() {
        return landGeboorteCode;
    }

    public void setGemeenteGeboorteCode(final Gemeentecode gemeenteGeboorteCode) {
        this.gemeenteGeboorteCode = gemeenteGeboorteCode;
    }

    public void setWoonplaatsGeboorteCode(final Woonplaatscode woonplaatsGeboorteCode) {
        this.woonplaatsGeboorteCode = woonplaatsGeboorteCode;
    }

    public void setLandGeboorteCode(final Landcode landGeboorteCode) {
        this.landGeboorteCode = landGeboorteCode;
    }
}
