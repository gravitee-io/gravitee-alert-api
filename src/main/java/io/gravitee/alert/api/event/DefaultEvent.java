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

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultEvent extends AbstractEvent implements Serializable {

    private static final long serialVersionUID = 1379928246655907008L;
    private static final String ALL = "*";

    private Map<String, String> context;
    private Map<String, Object> properties;

    protected DefaultEvent(long timestamp, String type) {
        super(timestamp, type);
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * getId() / getType() and getTimestamp() are used internally by jackson
     */
    public String getId() {
        return id();
    }

    public String getType() {
        return type();
    }

    public long getTimestamp() {
        return timestamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultEvent)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public Map<String, String> context() {
        return context;
    }

    @Override
    public Map<String, Object> properties() {
        return properties;
    }

    public static class Builder {

        private final long timestamp;
        private String type;
        private Map<String, String> context;
        private Map<String, Object> props;

        Builder(final long timestamp) {
            this.timestamp = timestamp;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder context(String key, String value) {
            if (context == null) {
                context = new LinkedHashMap<>();
            }
            context.put(key, value);
            return this;
        }

        public Builder context(String key, Supplier<String> supplier) {
            String value = supplier.get();
            if (value != null) {
                context(key, value);
            }

            return this;
        }

        public Builder property(String key, Object value) {
            if (props == null) {
                props = new LinkedHashMap<>();
            }
            props.put(key, value);
            return this;
        }

        public Builder property(String key, Supplier supplier) {
            Object value = supplier.get();
            if (value != null) {
                property(key, value);
            }

            return this;
        }

        public Builder installation(String installation) {
            property(PROPERTY_INSTALLATION, installation);

            return this;
        }

        public Builder environment(String environment) {
            if (environment == null || environment.isEmpty()) {
                property(PROPERTY_ENVIRONMENT, ALL);
            } else {
                property(PROPERTY_ENVIRONMENT, environment);
            }

            return this;
        }

        public Builder environments(Set<String> environments) {
            if (environments == null || environments.isEmpty()) {
                property(PROPERTY_ENVIRONMENT, ALL);
            } else {
                property(PROPERTY_ENVIRONMENT, environments.stream().collect(Collectors.joining(",")));
            }

            return this;
        }

        public Builder organization(String organization) {
            if (organization == null || organization.isEmpty()) {
                property(PROPERTY_ORGANIZATION, ALL);
            } else {
                property(PROPERTY_ORGANIZATION, organization);
            }

            return this;
        }

        public Builder organizations(Set<String> organizations) {
            if (organizations == null || organizations.isEmpty()) {
                property(PROPERTY_ORGANIZATION, ALL);
            } else {
                property(PROPERTY_ORGANIZATION, organizations.stream().collect(Collectors.joining(",")));
            }

            return this;
        }

        public Event build() {
            Objects.requireNonNull(type, "Event's type is required");
            final DefaultEvent alertEvent = new DefaultEvent(timestamp, type);
            alertEvent.setContext(context);
            alertEvent.setProperties(props);
            return alertEvent;
        }
    }
}
