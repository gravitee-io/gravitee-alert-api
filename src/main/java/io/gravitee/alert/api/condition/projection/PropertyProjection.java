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
package io.gravitee.alert.api.condition.projection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.gravitee.alert.api.event.Event;

import java.util.Objects;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class PropertyProjection extends AbstractProjection {

    private final String property;

    @JsonCreator
    PropertyProjection(@JsonProperty(value = "property", required = true) String property) {
        super(Type.PROPERTY);

        Objects.requireNonNull(property, "Property can not be null");

        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String key(Event event) {
        if (event == null || event.properties() == null) {
            return "";
        }

        Object value = event.properties().get(this.property);
        return (value != null) ? value.toString() : "";
    }
}
