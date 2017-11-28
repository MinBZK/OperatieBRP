/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;

/**
 * Een literal expressie voor een {@link MetaAttribuut}.
 */
public final class MetaAttribuutLiteral implements Literal {

    private final MetaAttribuut metaAttribuut;

    /**
     * Constructor.
     * @param metaAttribuut een {@link MetaAttribuut}
     */
    public MetaAttribuutLiteral(final MetaAttribuut metaAttribuut) {
        this.metaAttribuut = metaAttribuut;
    }

    public MetaAttribuut getMetaAttribuut() {
        return metaAttribuut;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BRP_METAATTRIBUUT;
    }

    /**
     * Factory method voor het maken van een expressie voor de waarde van een MetaAttribuut.
     * <p>
     * Verantwoording is in de elementtabel van type {@link ElementBasisType#GROOTGETAL}. Het selecteren van
     * de getallen mbt verantwoording heeft functioneel geen betekenis.
     * Om deze reden geven we direct het {@link Actie} object terug in een {@link ActieLiteral}
     * @param attribuut een MetaAttribuut
     * @return een Expressie
     */
    public static Literal maakAttribuutWaardeLiteral(final MetaAttribuut attribuut) {
        final ElementBasisType datatype = attribuut.getAttribuutElement().getDatatype();
        final Literal literal;

        if (attribuut.getWaarde() instanceof Actie) {
            return new ActieLiteral(attribuut.getWaarde());
        }

        switch (datatype) {
            case DATUMTIJD:
                literal = new DatumtijdLiteral(attribuut.getWaarde());
                break;
            case DATUM:
                literal = new DatumLiteral(attribuut.<Number>getWaarde().intValue());
                break;
            case GETAL:
            case GROOTGETAL:
                literal = new GetalLiteral(attribuut.<Number>getWaarde().longValue());
                break;
            case BOOLEAN:
                literal = BooleanLiteral.valueOf(attribuut.getWaarde());
                break;
            default:
                literal = new StringLiteral(attribuut.getWaarde().toString());
                break;
        }
        return literal;
    }

    @Override
    public String toString() {
        return String.format("@%s[%s]", metaAttribuut.getAttribuutElement().getNaam(), metaAttribuut.getGeformatteerdeWaarde());
    }

}
