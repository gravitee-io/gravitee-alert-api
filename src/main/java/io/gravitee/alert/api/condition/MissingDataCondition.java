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
package io.gravitee.alert.api.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.gravitee.alert.api.condition.projection.Projection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class MissingDataCondition extends ProjectionsAwareCondition implements Filter {

    @JsonCreator
    private MissingDataCondition(@JsonProperty(value = "duration", required = true) long duration,
                                 @JsonProperty(value = "timeUnit") TimeUnit timeUnit,
                                 @JsonProperty(value = "projections") List<Projection> projections) {
        super(Type.MISSING_DATA, timeUnit, duration, projections);
    }

    public static DurationBuilder duration(long duration, TimeUnit timeUnit) {
        return new DurationBuilder(duration, timeUnit);
    }

    public static DurationBuilder duration(long duration) {
        return new DurationBuilder(duration);
    }

    public static class DurationBuilder {

        private final long duration;
        private final TimeUnit timeUnit;

        private List<Projection> projections;

        DurationBuilder(long duration, TimeUnit timeUnit) {
            this.duration = duration;
            this.timeUnit = timeUnit;
        }

        DurationBuilder(long duration) {
            this(duration, null);
        }

        public DurationBuilder projection(Projection projection) {
            if (projections == null) {
                projections = new ArrayList<>();
            }

            projections.add(projection);

            return this;
        }

        public MissingDataCondition build() {
            return new MissingDataCondition(duration, timeUnit, projections);
        }
    }
}
