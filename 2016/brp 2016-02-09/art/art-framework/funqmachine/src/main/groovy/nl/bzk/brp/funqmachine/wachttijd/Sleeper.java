/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
Copyright 2011 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package nl.bzk.brp.funqmachine.wachttijd;

import java.util.concurrent.TimeUnit;

/**
 * Abstraction around {@link Thread#sleep(long)} to permit better testability.
 */
public interface Sleeper {

    public static final Sleeper SYSTEM_SLEEPER = new Sleeper() {
        public void sleep(Duration duration) throws InterruptedException {
            Thread.sleep(duration.in(TimeUnit.MILLISECONDS));
        }
    };

    /**
     * Sleeps for the specified duration of time.
     *
     * @param duration How long to sleep.
     * @throws InterruptedException If hte thread is interrupted while sleeping.
     */
    void sleep(Duration duration) throws InterruptedException;
}
