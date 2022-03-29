/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.alert.api.event;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public class DefaultEventTest {

    @Test
    public void shouldSetOrganization() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").organization("orga#1").build();

        assertEquals("orga#1", event.properties().get(Event.PROPERTY_ORGANIZATION));
    }

    @Test
    public void shouldSetAllWhenOrganizationIsNull() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").organization(null).build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ORGANIZATION));
    }

    @Test
    public void shouldSetAllWhenOrganizationIsEmpty() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").organization("").build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ORGANIZATION));
    }

    @Test
    public void shouldSetOrganizations() {
        final List<String> orgas = Arrays.asList("orga#1", "orga#2", "orga#3");
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").organizations(new HashSet<>(orgas)).build();

        assertEquals(orgas.stream().collect(Collectors.joining(",")), event.properties().get(Event.PROPERTY_ORGANIZATION));
    }

    @Test
    public void shouldSetAllWhenOrganizationsIsNull() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").organizations(null).build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ORGANIZATION));
    }

    @Test
    public void shouldSetAllWhenOrganizationsIsEmpty() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").organizations(Collections.emptySet()).build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ORGANIZATION));
    }

    @Test
    public void shouldSetEnvironment() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").environment("env#1").build();

        assertEquals("env#1", event.properties().get(Event.PROPERTY_ENVIRONMENT));
    }

    @Test
    public void shouldSetAllWhenEnvironmentIsNull() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").environment(null).build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ENVIRONMENT));
    }

    @Test
    public void shouldSetAllWhenEnvironmentIsEmpty() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").environment("").build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ENVIRONMENT));
    }

    @Test
    public void shouldSetEnvironments() {
        final List<String> orgas = Arrays.asList("env#1", "orga#2", "orga#3");
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").environments(new HashSet<>(orgas)).build();

        assertEquals(orgas.stream().collect(Collectors.joining(",")), event.properties().get(Event.PROPERTY_ENVIRONMENT));
    }

    @Test
    public void shouldSetAllWhenEnvironmentsIsNull() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").environments(null).build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ENVIRONMENT));
    }

    @Test
    public void shouldSetAllWhenEnvironmentsIsEmpty() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").environments(Collections.emptySet()).build();

        assertEquals("*", event.properties().get(Event.PROPERTY_ENVIRONMENT));
    }

    @Test
    public void shouldSetInstallation() {
        final Event event = new DefaultEvent.Builder(System.currentTimeMillis()).type("test").installation("install#1").build();

        assertEquals("install#1", event.properties().get(Event.PROPERTY_INSTALLATION));
    }
}
