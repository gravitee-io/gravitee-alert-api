/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
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
package io.gravitee.alert.api.event;

import io.gravitee.common.utils.UUID;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class AbstractEvent implements Event, Serializable {

    private static final long serialVersionUID = 7810797943500895336L;

    private String id;

    private long timestamp;

    private String type;

    protected AbstractEvent(long timestamp, String type) {
        this.id = UUID.random().toString();
        this.timestamp = timestamp;
        this.type = type;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public long timestamp() {
        return timestamp;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEvent)) return false;
        AbstractEvent that = (AbstractEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AbstractEvent{" + "id='" + id + '\'' + ", timestamp=" + timestamp + ", type='" + type + '\'' + '}';
    }
}
