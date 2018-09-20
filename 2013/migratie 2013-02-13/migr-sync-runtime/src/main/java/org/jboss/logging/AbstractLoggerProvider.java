/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2011 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.logging;

import java.util.ArrayDeque;

abstract class AbstractLoggerProvider {

    private final ThreadLocal<ArrayDeque<Entry>> ndcStack = new ThreadLocal<ArrayDeque<Entry>>();

    public void clearNdc() {
        final ArrayDeque<Entry> stack = ndcStack.get();
        if (stack != null) {
            stack.clear();
        }
    }

    public String getNdc() {
        final ArrayDeque<Entry> stack = ndcStack.get();
        return stack == null || stack.isEmpty() ? null : stack.peek().merged;
    }

    public int getNdcDepth() {
        final ArrayDeque<Entry> stack = ndcStack.get();
        return stack == null ? 0 : stack.size();
    }

    public String peekNdc() {
        final ArrayDeque<Entry> stack = ndcStack.get();
        return stack == null || stack.isEmpty() ? "" : stack.peek().current;
    }

    public String popNdc() {
        final ArrayDeque<Entry> stack = ndcStack.get();
        return stack == null || stack.isEmpty() ? "" : stack.pop().current;
    }

    public void pushNdc(final String message) {
        ArrayDeque<Entry> stack = ndcStack.get();
        if (stack == null) {
            stack = new ArrayDeque<Entry>();
            ndcStack.set(stack);
        }
        stack.push(stack.isEmpty() ? new Entry(message) : new Entry(stack.peek(), message));
    }

    public void setNdcMaxDepth(final int maxDepth) {
        final ArrayDeque<Entry> stack = ndcStack.get();
        if (stack != null) {
            while (stack.size() > maxDepth) {
                stack.pop();
            }
        }
    }

    private static class Entry {

        private final String merged;
        private final String current;

        Entry(final String current) {
            merged = current;
            this.current = current;
        }

        Entry(final Entry parent, final String current) {
            merged = parent.merged + ' ' + current;
            this.current = current;
        }
    }
}
