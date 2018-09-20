/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;

/**
 * TODO: Add documentation
 */
public class MaterieleHistorieBuilder {

    private DatumTijd datumTijdRegistratie;
    private DatumTijd datumTijdVerval;
    private Datum     datumAanvangGeldigheid;
    private Datum     datumEindeGeldigheid;

    public MaterieleHistorieBuilder() {
    }

    public static MaterieleHistorieBuilder defaultValues() {
        return new MaterieleHistorieBuilder();
    }

    public MaterieleHistorieBuilder aanvangGeldigheid(final Datum datum) {
        this.datumAanvangGeldigheid = datum;
        return this;
    }

    public MaterieleHistorieBuilder eindeGeldigheid(final Datum datum) {
        this.datumEindeGeldigheid = datum;
        return this;
    }

    public MaterieleHistorieBuilder datumTijdRegistratie(final DatumTijd datumTijd) {
        this.datumTijdRegistratie = datumTijd;
        return this;
    }

    public MaterieleHistorieBuilder datumTijdVerval(final DatumTijd datumTijd) {
        this.datumTijdVerval = datumTijd;
        return this;
    }

    public MaterieleHistorieBuilder metHistorie(final FormeleHistorie historie) {
        this.datumTijdRegistratie = historie.getDatumTijdRegistratie();
        this.datumTijdVerval = historie.getDatumTijdVerval();
        return this;
    }

    public MaterieleHistorie build() {
        MaterieleHistorie historie = new MaterieleHistorieImpl();
        historie.setDatumAanvangGeldigheid(this.datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(this.datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(this.datumTijdRegistratie);
        historie.setDatumTijdVerval(this.datumTijdVerval);

        return historie;
    }
}
