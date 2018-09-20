/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AdellijkeTitelPredikaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de adellijke titel/predikaat (LO3) enerzijds, dan wel de adellijke titel of predikaat (BRP) anderzijds.
 * <p/>
 * Bij de conversie wordt de waarde van de rubriek(LO3) omgezet in een waarde voor de adellijke titel of een waarde voor het predikaat. Voor de
 * terugconversie is het geslacht ook van belang: voor de meeste adellijke titels en predikaten geldt dat daar waar de BRP ��n waarde kent, het LO3 stelsel
 * er ��n kent voor de mannelijke en ��n voor de vrouwelijke variant.
 */
@Entity
@Table(schema = "Conv", name = "ConvAdellijkeTitelPredikaat")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieAdellijkeTitelPredikaat extends AbstractConversieAdellijkeTitelPredikaat {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieAdellijkeTitelPredikaat() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek0221AdellijkeTitelPredikaat
     *                            rubriek0221AdellijkeTitelPredikaat van ConversieAdellijkeTitelPredikaat.
     * @param geslachtsaanduiding geslachtsaanduiding van ConversieAdellijkeTitelPredikaat.
     * @param adellijkeTitel      adellijkeTitel van ConversieAdellijkeTitelPredikaat.
     * @param predicaat           predicaat van ConversieAdellijkeTitelPredicaat.
     */
    protected ConversieAdellijkeTitelPredikaat(
        final LO3AdellijkeTitelPredikaatAttribuut rubriek0221AdellijkeTitelPredikaat,
        final Geslachtsaanduiding geslachtsaanduiding, final AdellijkeTitel adellijkeTitel,
        final Predicaat predicaat)
    {
        super(rubriek0221AdellijkeTitelPredikaat, geslachtsaanduiding, adellijkeTitel, predicaat);
    }

}
