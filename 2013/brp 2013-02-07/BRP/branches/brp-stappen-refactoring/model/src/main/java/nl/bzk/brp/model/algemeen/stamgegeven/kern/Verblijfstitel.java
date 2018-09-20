/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.CodeVerblijfstitel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractVerblijfstitel;


/**
 * Categorisatie van de Verblijfstitel van Persoon.
 *
 * 1. Naam aangepast: verblijfsrecht gaat verder dan de door de IND medegedeelde verblijfstitel, die overigens niet
 * alleen over verblijf gaat maar ook over het recht om arbeid te verrichten. Door te kiezen voor verblijfstitel (term
 * OOK gebruikt in GBA LO 3.7) en niet voor verblijfsrecht (term komt vooral voor in de HUP), blijft verblijfsrecht als
 * groep ook geschikt voor bijv. de geprivilegieerden en de militairen en anderen die verblijfsrecht hebben. RvdP 27
 * november 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischStamgegevensModelGenerator.
 * Metaregister versie: 1.1.15.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-28 16:34:59.
 * Gegenereerd op: Wed Nov 28 16:37:18 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "Verblijfstitel")
public class Verblijfstitel extends AbstractVerblijfstitel {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Verblijfstitel() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Verblijfstitel.
     * @param omschrijving omschrijving van Verblijfstitel.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Verblijfstitel.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Verblijfstitel.
     */
    protected Verblijfstitel(final CodeVerblijfstitel code, final OmschrijvingEnumeratiewaarde omschrijving,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        super(code, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
