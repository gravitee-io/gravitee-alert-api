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
package io.gravitee.alert.api.condition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.gravitee.alert.api.condition.projection.Projection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class WindowBasedCondition extends AbstractCondition {

    private final long duration;
    private final TimeUnit timeUnit;

    WindowBasedCondition(Type type, TimeUnit timeUnit, long duration) {
        this(type, timeUnit, duration, Collections.emptyList());
    }

    WindowBasedCondition(Type type, TimeUnit timeUnit, long duration, List<Projection> projections) {
        super(type, projections);
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    public long getDuration() {
        return duration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @JsonIgnore
    public long getWindowTime() {
        return (timeUnit != null) ? timeUnit.toMillis(duration) : duration;
    }
}
