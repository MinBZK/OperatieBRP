/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.usr.tabel;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.operationeel.gen.tabel.AbstractPersoon;

/**
 * Persoon
  */
@Entity(name = "PersoonOperationeel")
@Table(schema = "Kern", name = "Pers")
public class Persoon extends AbstractPersoon {

    public Persoon() {
        this.setIdentificatienummersStatusHis(StatusHistorie.ACTUEEL);
        this.setGeslachtsaanduidingStatusHis(StatusHistorie.ACTUEEL);
        this.setSamengesteldeNaamStatusHis(StatusHistorie.ACTUEEL);
        this.setAanschrijvingStatusHis(StatusHistorie.ACTUEEL);
        this.setGeboorteStatusHis(StatusHistorie.ACTUEEL);
        this.setOverlijdenStatusHis(StatusHistorie.ACTUEEL);
        this.setVerblijfsrechtStatusHis(StatusHistorie.ACTUEEL);
        this.setUitsluitingNLKiesrechtStatusHis(StatusHistorie.ACTUEEL);
        this.setEUVerkiezingenStatusHis(StatusHistorie.ACTUEEL);
        this.setBijhoudingsverantwoordelijkheidStatusHis(StatusHistorie.ACTUEEL);
        this.setOpschortingStatusHis(StatusHistorie.ACTUEEL);
        this.setBijhoudingsgemeenteStatusHis(StatusHistorie.ACTUEEL);
        this.setPersoonskaartStatusHis(StatusHistorie.ACTUEEL);
        this.setImmigratieStatusHis(StatusHistorie.ACTUEEL);
        this.setInschrijvingStatusHis(StatusHistorie.ACTUEEL);
    }
}
