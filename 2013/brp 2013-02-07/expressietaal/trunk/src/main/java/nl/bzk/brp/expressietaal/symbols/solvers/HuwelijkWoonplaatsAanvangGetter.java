/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NullValue;
import nl.bzk.brp.expressietaal.parser.syntaxtree.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.symbols.AttributeGetter;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;


/**
 * Getter voor 'woonplaats_aanvang' in objecttype 'HuwelijkGeregistreerdPartnerschap'.
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.SymbolTableGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-31 14:31:48.
 * Gegenereerd op: Thu Jan 31 15:38:26 CET 2013.
 */
public class HuwelijkWoonplaatsAanvangGetter implements AttributeGetter {

    /**
     * Geeft de waarde van attribuut 'woonplaats_aanvang' uit het gegeven (root)object.
     *
     * @param rootObject Object waarvan het attribuut bepaald moet worden.
     * @return Waarde van het attribuut.
     */
    @Override
    public Expressie getAttribuutWaarde(final RootObject rootObject) {
        if (rootObject != null && rootObject instanceof HuwelijkGeregistreerdPartnerschap) {
            HuwelijkGeregistreerdPartnerschap v = (HuwelijkGeregistreerdPartnerschap) rootObject;
            if (v.getStandaard() != null && v.getStandaard().getWoonplaatsAanvang() != null
                    && v.getStandaard().getWoonplaatsAanvang().getCode() != null)
            {
                return new StringLiteralExpressie(v.getStandaard().getWoonplaatsAanvang().getCode().getWaarde());
            }
        }
        return new NullValue();
    }

    /**
     * Geeft de waarde van attribuut 'woonplaats_aanvang' uit een bepaald
     * geïndiceerd attribuut van een (root)object.
     *
     * @param rootObject Object waarvan het attribuut bepaald moet worden.
     * @param index      Index van het attribuut waartoe het gezochte attribuut behoort.
     * @return Waarde van het attribuut.
     */
    @Override
    public Expressie getAttribuutWaarde(final RootObject rootObject, final Integer index) {
        return getAttribuutWaarde(rootObject);
    }

    /**
     * Geeft de hoogste indexwaarde voor 'woonplaats_aanvang' uit een bepaald
     * geïndiceerd attribuut van een (root)object.
     * Dit is gelijk aan het aantal elementen in de verzameling.
     *
     * @param rootObject Object waarvan de hoogste indexwaarde bepaald moet worden.
     * @return Maximale index.
     */
    @Override
    public Integer getMaxIndex(final RootObject rootObject) {
        return 1;
    }
}
