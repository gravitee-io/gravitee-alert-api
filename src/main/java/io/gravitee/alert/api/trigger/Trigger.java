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
package io.gravitee.alert.api.trigger;

import com.fasterxml.jackson.annotation.*;
import io.gravitee.alert.api.condition.Condition;
import io.gravitee.alert.api.condition.Filter;
import io.gravitee.common.utils.UUID;
import io.gravitee.notifier.api.Notification;

import java.io.Serializable;
import java.util.*;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "source", "enabled", "conditions", "filters", "dampening", "notifications", "metadata"})
public class Trigger implements Serializable {

    private static final long serialVersionUID = 19504799855563L;

    private String id;
    private Severity severity = Severity.INFO;
    private String source;
    private String name;
    private String description;
    private List<Condition> conditions;
    private List<Notification> notifications;
    private Dampening dampening;
    private Map<String, Map<String, String>> metadata;
    private boolean enabled;
    private List<Filter> filters;

    @JsonCreator
    protected Trigger (
            @JsonProperty(value = "id", required = true) String id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "severity") Severity severity,
            @JsonProperty(value = "source", required = true) String source,
            @JsonProperty(value = "enabled") boolean enabled
    ) {
        // Default private constructor to force builder usage.
        this.source = source;
        this.id = id;
        this.name = name;
        this.severity = (severity == null) ? Severity.INFO : severity;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Map<String, Map<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Map<String, String>> metadata) {
        this.metadata = metadata;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Dampening getDampening() {
        return dampening;
    }

    public void setDampening(Dampening dampening) {
        this.dampening = dampening;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trigger)) return false;
        Trigger trigger = (Trigger) o;
        return Objects.equals(id, trigger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", dampening='" + dampening + '\'' +
                ", notifications=" + notifications +
                ", conditions=" + conditions +
                ", filters=" + filters +
                ", enabled=" + enabled +
                '}';
    }



    public static Builder on(String source) {
        return new Builder(source);
    }

    public enum Severity {
        INFO,
        WARNING,
        CRITICAL
    }

    public static class Builder {
        private String id;
        private final String source;
        private String name;
        private String description;
        private Severity severity;
        private Map<String, Map<String, String>> metadata;
        private List<Notification> notifications = new ArrayList<>();
        private List<Condition> conditions = new ArrayList<>();
        private List<Filter> filters = new ArrayList<>();
        private boolean enabled = true;
        private Dampening dampening;

        private Builder(String source) {
            this.source = source;
        }

        public Trigger.Builder id(String id) {
            this.id = id;
            return this;
        }

        public Trigger.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Trigger.Builder description(String description) {
            this.description = description;
            return this;
        }

        public Trigger.Builder severity(Severity severity) {
            this.severity = severity;
            return this;
        }

        public Trigger.Builder metadata(String reference, String key, String value) {
            if (metadata == null) {
                metadata = new HashMap<>();
            }

            Map<String, String> refMetadata = this.metadata.get(reference);
            if (refMetadata == null) {
                refMetadata = new HashMap<>();
                this.metadata.put(reference, refMetadata);
            }
            refMetadata.put(key, value);

            return this;
        }

        public Trigger.Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Trigger.Builder notification(Notification notification) {
            notifications.add(notification);
            return this;
        }

        public Trigger.Builder dampening(Dampening dampening) {
            this.dampening = dampening;
            return this;
        }

        public Trigger.Builder condition(Condition condition) {
            conditions.add(condition);
            return this;
        }

        public Trigger.Builder filter(Filter filter) {
            if (filters == null) {
                filters = new ArrayList<>();
            }

            filters.add(filter);
            return this;
        }

        public Trigger.Builder filters(List<Filter> filters) {
            if (filters != null) {
                for (Filter filter : filters) {
                    filter(filter);
                }
            }
            return this;
        }

        public Trigger build() {
            final Trigger trigger = new Trigger(
                    (id == null) ? UUID.random().toString() : id,
                    name, severity, source, enabled);

            trigger.setDescription(description);
            trigger.setNotifications(notifications);
            trigger.setEnabled(enabled);
            trigger.setMetadata(metadata);

            if (conditions == null || conditions.isEmpty()) {
                throw new IllegalStateException("A trigger need, at least, one condition defined");
            }

            trigger.setConditions(conditions);
            trigger.setFilters(filters);
            if (dampening != null) {
                trigger.setDampening(dampening);
            } else {
                trigger.setDampening(Dampening.strictCount(1));
            }

            return trigger;
        }
    }
}
