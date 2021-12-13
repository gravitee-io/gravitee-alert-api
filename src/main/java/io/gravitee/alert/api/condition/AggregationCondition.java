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
public class AggregationCondition extends WindowBasedCondition {

    public enum Operator {
        LT, LTE, GTE, GT
    }

    public enum Function {
        COUNT, AVG, MIN, MAX, P50, P90, P95, P99
    }

    private final Operator operator;

    private final double threshold;

    private final Function function;

    private final String property;

    @JsonCreator
    private AggregationCondition(
            @JsonProperty(value = "function", required = true) Function function,
            @JsonProperty(value = "property") String property,
            @JsonProperty(value = "operator", required = true) Operator operator,
            @JsonProperty(value = "threshold", required = true) Double threshold,
            @JsonProperty(value = "timeUnit") TimeUnit timeUnit,
            @JsonProperty(value = "duration", required = true) long duration,
            @JsonProperty(value = "projections") List<Projection> projections) {
        super(Type.AGGREGATION, timeUnit, duration, projections);

        this.property = property;
        this.operator = operator;
        this.threshold = threshold;
        this.function = function;
    }

    public static FunctionBuilder avg(String property) {
        return new FunctionBuilder(property, Function.AVG);
    }

    public static FunctionBuilder min(String property) {
        return new FunctionBuilder(property, Function.MIN);
    }

    public static FunctionBuilder max(String property) {
        return new FunctionBuilder(property, Function.MAX);
    }

    public static FunctionBuilder p50(String property) {
        return new FunctionBuilder(property, Function.P50);
    }

    public static FunctionBuilder p90(String property) {
        return new FunctionBuilder(property, Function.P90);
    }

    public static FunctionBuilder p95(String property) {
        return new FunctionBuilder(property, Function.P95);
    }

    public static FunctionBuilder p99(String property) {
        return new FunctionBuilder(property, Function.P99);
    }

    public static FunctionBuilder count() {
        return new FunctionBuilder(Function.COUNT);
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public Double getThreshold() {
        return threshold;
    }

    public Function getFunction() {
        return function;
    }

    public static class ThresholdBuilder {

        private final FunctionBuilder functionBuilder;
        private final DurationBuilder durationBuilder;

        private final Operator operator;
        private final Double threshold;

        private List<Projection> projections;

        ThresholdBuilder(FunctionBuilder functionBuilder, DurationBuilder durationBuilder,
                         Operator operator, Double threshold) {
            this.functionBuilder = functionBuilder;
            this.durationBuilder = durationBuilder;

            this.operator = operator;
            this.threshold = threshold;
        }

        public ThresholdBuilder projection(Projection projection) {
            if (projections == null) {
                projections = new ArrayList<>();
            }

            projections.add(projection);

            return this;
        }

        public AggregationCondition build() {
            return new AggregationCondition(
                    functionBuilder.getFunction(),
                    functionBuilder.getProperty(),
                    operator, threshold,
                    durationBuilder.getTimeUnit(), durationBuilder.getDuration(),
                    projections);
        }
    }

    public static class FunctionBuilder {

        private final String property;
        private final Function function;

        private FunctionBuilder(Function function) {
            this.function = function;
            this.property = null;
        }

        private FunctionBuilder(String property, Function function) {
            this.property = property;
            this.function = function;
        }

        public DurationBuilder duration(long duration, TimeUnit timeUnit) {
            return new DurationBuilder( this, duration, timeUnit);
        }

        public DurationBuilder duration(long duration) {
            return new DurationBuilder( this, duration);
        }

        private String getProperty() {
            return property;
        }

        private Function getFunction() {
            return function;
        }
    }

    public static class DurationBuilder {
        private final FunctionBuilder functionBuilder;

        private long duration;
        private TimeUnit timeUnit;

        private DurationBuilder(FunctionBuilder functionBuilder,
                         long duration, TimeUnit timeUnit) {
            this.functionBuilder = functionBuilder;
            this.duration = duration;
            this.timeUnit = timeUnit;
        }

        private DurationBuilder(FunctionBuilder functionBuilder,
                        long duration) {
            this.functionBuilder = functionBuilder;
            this.duration = duration;
        }

        public ThresholdBuilder lowerThan(Double threshold) {
            return new ThresholdBuilder(functionBuilder, this, Operator.LT, threshold);
        }

        public ThresholdBuilder lowerThanOrEquals(Double threshold) {
            return new ThresholdBuilder(functionBuilder, this, Operator.LTE, threshold);
        }

        public ThresholdBuilder greaterThanOrEquals(Double threshold) {
            return new ThresholdBuilder(functionBuilder, this, Operator.GTE, threshold);
        }

        public ThresholdBuilder greaterThan(Double threshold) {
            return new ThresholdBuilder(functionBuilder, this, Operator.GT, threshold);
        }

        private long getDuration() {
            return duration;
        }

        private TimeUnit getTimeUnit() {
            return timeUnit;
        }
    }
}
