/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util.jackson;

import nl.gba.gbav.lo3.PL;

import org.junit.Test;

/**
 * Deze testen dekken de coverage van de Jackson mixin classes. Anders lijken ze niet gecoverd. Maar dat zijn ze dus
 * wel.
 */
public class JacksonMixinCoverageTest {
    @Test
    public void testJacksonMixinClasses() {
        new BrpActieJsonMixin() {
            @Override
            public long getId() {
                return 0;
            }
        };

        new GenericStapelGSJsonMixin() {
            @Override
            public PL getPL() {
                return null;
            }
        };

        new StapelJsonMixin() {
            @Override
            public Object getMeestRecenteElement() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };

        new Lo3DocumentatieJsonMixin() {
            @Override
            public long getId() {
                return 0;
            }

            @Override
            public boolean isAkte() {
                return false;
            }

            @Override
            public boolean isDocument() {
                return false;
            }
        };

        new Lo3CategorieInhoudJsonMixin() {

            @Override
            public boolean isLeeg() {
                return false;
            }

            @Override
            public boolean isJurischeGeenOuder() {
                return false;
            }

            @Override
            public boolean isOnbekendeOuder() {
                return false;
            }

            @Override
            public boolean isPuntAdres() {
                return false;
            }

            @Override
            public boolean isNederlandsAdres() {
                return false;
            }

            @Override
            public boolean isGroep80Leeg() {
                return false;
            }
        };

        new Lo3HistorieJsonMixin() {
            @Override
            public boolean isOnjuist() {
                return false;
            }
        };

        new BrpHistorieJsonMixin() {
            @Override
            public boolean isVervallen() {
                return false;
            }
        };

        new BrpGroepInhoudJsonMixin() {
            @Override
            public boolean isLeeg() {
                return false;
            }

            @Override
            public Boolean getHeeftIndicatie() {
                return null;
            }
        };

        new Lo3PersoonslijstJsonMixin() {
            @Override
            public boolean isGroep80VanInschrijvingStapelLeeg() {
                return false;
            }
        };
    }
}
