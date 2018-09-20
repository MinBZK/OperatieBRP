/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ist;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3AfnemersverstrekkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3BerichtaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3SelectiesoortAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Autorisatietabel (tabel 35) uit LO3.x, bevatte een opsomming van door de Minister geautoriseerde en op het GBA-netwerk aangesloten afnemers,
 * gecombineerd met aangesloten gemeenten, de BV BSN, GBA-V, het agentschap BPR.
 * <p/>
 * De gegevens uit de autorisatietabel uit LO3.x zijn grotendeels overgezet naar corresponderende tabellen in de BRP. Niet alle gegevens hebben echter een
 * evenknie in BPR. Deze gegevens worden in de onderhavige tabel vastgelegd.
 */
@Entity
@Table(schema = "IST", name = "Autorisatietabel")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Autorisatietabel extends AbstractAutorisatietabel {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Autorisatietabel() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param partijcode            partijcode van Autorisatietabel.
     * @param datumIngangTabelregel datumIngangTabelregel van Autorisatietabel.
     * @param datumEindeTabelregel  datumEindeTabelregel van Autorisatietabel.
     * @param selectiesoort         selectiesoort van Autorisatietabel.
     * @param berichtaanduiding     berichtaanduiding van Autorisatietabel.
     * @param afnemersverstrekking  afnemersverstrekking van Autorisatietabel.
     */
    protected Autorisatietabel(final PartijCodeAttribuut partijcode,
        final DatumEvtDeelsOnbekendAttribuut datumIngangTabelregel,
        final DatumEvtDeelsOnbekendAttribuut datumEindeTabelregel,
        final LO3SelectiesoortAttribuut selectiesoort, final LO3BerichtaanduidingAttribuut berichtaanduiding,
        final LO3AfnemersverstrekkingAttribuut afnemersverstrekking)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(partijcode, datumIngangTabelregel, datumEindeTabelregel,
            selectiesoort, berichtaanduiding, afnemersverstrekking);
    }

}
