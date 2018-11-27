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

import io.gravitee.common.utils.UUID;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Azize ELAMRANI (azize.elamrani at graviteesource.com)
 * @author GraviteeSource Team
 */
public class Event extends AbstractAlertable implements Alertable, Serializable {

    private static final long serialVersionUID = 1379928246655907008L;

    private Map<String, String> context;
    private Map<String, Object> props;

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public Map<String, Object> getProps() {
        return props;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Event{" +
                ", context=" + context +
                ", props=" + props +
                "} " + super.toString();
    }

    public static class Builder {
        private long timestamp;
        private String type;
        private Map<String, String> context;
        private Map<String, Object> props;

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
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

        public Builder prop(String key, Object value) {
            if (props == null) {
                props = new LinkedHashMap<>();
            }
            props.put(key, value);
            return this;
        }


        public Event build() {
            final Event alertEvent = new Event();
            alertEvent.setId(UUID.toString(UUID.random()));
            alertEvent.setTimestamp(timestamp);
            alertEvent.setType(type);
            alertEvent.setContext(context);
            alertEvent.setProps(props);
            return alertEvent;
        }
    }
}
