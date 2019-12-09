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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dampening implements Serializable {

    private final Mode mode;

    private final Integer trueEvaluations;

    private final Integer totalEvaluations;

    private final Long duration;

    private final TimeUnit timeUnit;

    public enum Mode {
        STRICT_COUNT,   // N consecutive true evaluations
        RELAXED_COUNT,  // N true evaluations out of M total evaluations
        RELAXED_TIME,   // N true evaluations in T time
        STRICT_TIME     // Only true evaluations for at least T time
    }

    @JsonCreator
    private Dampening(
            @JsonProperty(value = "mode", required = true) final Mode mode,
            @JsonProperty("trueEvaluations") final Integer trueEvaluations,
            @JsonProperty("totalEvaluations") final Integer totalEvaluations,
            @JsonProperty(value = "duration") final Long duration,
            @JsonProperty(value = "timeUnit") final TimeUnit timeUnit) {
        this.mode = mode;
        this.trueEvaluations = trueEvaluations;
        this.totalEvaluations = totalEvaluations;
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    private Dampening(final Mode mode, final Integer trueEvaluations, final Integer totalEvaluations, final Long duration) {
        this.mode = mode;
        this.trueEvaluations = trueEvaluations;
        this.totalEvaluations = totalEvaluations;
        this.duration = duration;
        this.timeUnit = null;
    }

    public static Dampening strictCount(int trueEvaluations) {
        return new Dampening(Mode.STRICT_COUNT, trueEvaluations, trueEvaluations, null);
    }

    public static Dampening relaxedCount(int trueEvaluations, int totalEvaluations) {
        return new Dampening(Mode.RELAXED_COUNT, trueEvaluations, totalEvaluations, null);
    }

    public static Dampening strictTime(long duration) {
        return new Dampening(Mode.STRICT_TIME, null, null, duration);
    }

    public static Dampening relaxedTime(int trueEvaluations, long duration) {
        return new Dampening(Mode.STRICT_TIME, trueEvaluations, null, duration);
    }

    public Mode getMode() {
        return mode;
    }

    public Integer getTrueEvaluations() {
        return trueEvaluations;
    }

    public Integer getTotalEvaluations() {
        return totalEvaluations;
    }

    public Long getDuration() {
        return duration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
