/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.EnWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.StandaardOperator;
import org.springframework.stereotype.Component;

/**
 * Voorwaarde regel KV rubriek en KNV rubriek. Uitgezonderd de rubriek 07.67.10
 */
@Component
public class KomtVoorVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    private static final String REGEX_PATROON = "^(KV|KNV).*";
    private static final int VOLGORDE = 110;
    private static final String KV = "KV";
    private static final String KNV = "KNV";
    private static final int DEEL_FACTOR_INVERSE = 2;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public KomtVoorVoorwaardeRegel(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public final Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(" ");
        final Expressie result;
        try {
            final BrpType[] brpTypen = vertaalRubriekNaarBrpTypen(delen[1]);
            if (brpTypen.length == 1) {
                result = verwerkVoorwaarde(delen, brpTypen[0], voorwaardeRegel.getLo3Expressie());
            } else {
                final List<Expressie> expressies = maakDeelExpressies(voorwaardeRegel, delen, brpTypen);
                result = stelSamengesteldeVoorwaardeSamen(voorwaardeRegel, delen[0], expressies);
            }
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), groe);
        }

        return result;
    }

    private List<Expressie> maakDeelExpressies(final RubriekWaarde voorwaardeRegel, final String[] delen, final BrpType[] brpTypen)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final List<Expressie> expressies = new ArrayList<>();
        for (final BrpType brpType : brpTypen) {
            expressies.add(verwerkVoorwaarde(delen, brpType, voorwaardeRegel.getLo3Expressie()));
        }
        return expressies;
    }

    private Expressie stelSamengesteldeVoorwaardeSamen(final RubriekWaarde voorwaardeRegel, final String operator, final List<Expressie> expressies)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie result;

        switch (operator) {
            case KV:
                result = stelSamengesteldeVoorwaardeKVSamen(expressies);
                break;
            case KNV:
                result = stelSamengesteldeVoorwaardeKNVSamen(expressies);
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }
        return result;
    }

    private Expressie stelSamengesteldeVoorwaardeKVSamen(final List<Expressie> expressies) {
        final Expressie result;
        final List<Expressie> ofExpressies = verwerkInverseExpressiesVoorKV(expressies);
        if (ofExpressies.size() == 1) {
            result = ofExpressies.get(0);
        } else {
            result = new OfWaarde(ofExpressies.toArray(new Expressie[ofExpressies.size()]));
        }
        return result;
    }

    private Expressie stelSamengesteldeVoorwaardeKNVSamen(final List<Expressie> expressies) {
        final Expressie result;
        final List<Expressie> enExpressies = verwerkInverseExpressieVoorKNV(expressies);
        result = new EnWaarde(enExpressies.toArray(new Expressie[enExpressies.size()]));
        return result;
    }

    private List<Expressie> verwerkInverseExpressiesVoorKV(List<Expressie> expressies) {
        final List<Expressie> ofExpressies = new ArrayList<>();
        if (expressies.size() % DEEL_FACTOR_INVERSE == 0) {
            for (int x = 0; x < expressies.size(); x = x + DEEL_FACTOR_INVERSE) {
                if (expressies.get(x + 1).getCriteria().get(0).getOperator() instanceof KNVOperator) {
                    ofExpressies.add(new EnWaarde(expressies.get(x), expressies.get(x + 1)));
                } else {
                    ofExpressies.add(expressies.get(x));
                    ofExpressies.add(expressies.get(x + 1));

                }
            }
        } else {
            ofExpressies.addAll(expressies);
        }
        return ofExpressies;
    }

    private List<Expressie> verwerkInverseExpressieVoorKNV(List<Expressie> expressies) {
        final List<Expressie> enExpressies = new ArrayList<>();
        if (expressies.size() % DEEL_FACTOR_INVERSE == 0) {
            for (int x = 0; x < expressies.size(); x = x + DEEL_FACTOR_INVERSE) {
                if (expressies.get(x + 1).getCriteria().get(0).getOperator() instanceof KVOperator) {
                    enExpressies.add(new OfWaarde(expressies.get(x), expressies.get(x + 1)));
                } else {
                    enExpressies.add(expressies.get(x));
                    enExpressies.add(expressies.get(x + 1));

                }
            }
        } else {
            enExpressies.addAll(expressies);
        }
        return enExpressies;
    }

    private BrpType[] vertaalRubriekNaarBrpTypen(final String rubriek) throws GbaRubriekOnbekendExceptie {
        final BrpType[] tijdelijkResultaat;
        if (rubriek.matches("04\\.05\\.10")) {
            // Uitzondering voor nationaliteit
            tijdelijkResultaat = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(rubriek + ".voorkomens");
        } else {
            tijdelijkResultaat = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(rubriek);
        }
        final List<BrpType> brpTypeLijst = new ArrayList<>();
        for (final BrpType brpType : tijdelijkResultaat) {
            brpTypeLijst.add(brpType);
        }
        return brpTypeLijst.toArray(new BrpType[brpTypeLijst.size()]);
    }

    private Expressie verwerkVoorwaarde(final String[] delen, final BrpType brpType, final String voorwaardeRegel)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        String operator = delen[0];
        if (brpType.isInverse()) {
            switch (operator) {
                case KV:
                    operator = KNV;
                    break;
                case KNV:
                    operator = KV;
                    break;
                default:
            }
        }
        final Expressie resultaat;
        switch (operator) {
            case KV:
                resultaat = verwerkKvVoorwaarde(brpType);
                break;
            case KNV:
                resultaat = verwerkKnvVoorwaarde(brpType);
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
        }
        return resultaat;
    }

    private Expressie verwerkKnvVoorwaarde(BrpType brpType) {
        Expressie resultaat;
        if (brpType.getType().contains("MAP")) {
            resultaat = new ElementWaarde(new Criterium(brpType.getType(), new StandaardOperator("NIET(%1$s E<> NULL)"), null));
        } else {
            resultaat = new ElementWaarde(new Criterium(brpType.getType(), new KNVOperator(), null));
        }
        return resultaat;
    }

    private Expressie verwerkKvVoorwaarde(BrpType brpType) {
        Expressie resultaat;
        if (brpType.getType().contains("MAP")) {
            resultaat = new ElementWaarde(new Criterium(brpType.getType(), new StandaardOperator("%1$s E<> NULL"), null));
        } else {
            resultaat = new ElementWaarde(new Criterium(brpType.getType(), new KVOperator(), null));
        }
        return resultaat;
    }
}
