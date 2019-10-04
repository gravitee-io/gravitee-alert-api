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

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class CompareCondition extends AbstractCondition implements Filter {

    public enum Operator {
        LT, LTE, GTE, GT
    }

    private final String property;

    private final Operator operator;

    private final String property2;

    private final Double multiplier;

    @JsonCreator
    private CompareCondition(
            @JsonProperty(value = "property", required = true) String property,
            @JsonProperty(value = "operator", required = true) Operator operator,
            @JsonProperty(value = "multiplier", required = true) Double multiplier,
            @JsonProperty(value = "property2", required = true) String property2) {
        super(Type.COMPARE);

        this.property = property;
        this.operator = operator;
        this.multiplier = multiplier;
        this.property2 = property2;
    }

    public static FilterBuilder lowerThan(String property, Double multiplier, String property2) {
        return new FilterBuilder(property, Operator.LT, multiplier, property2);
    }

    public static FilterBuilder lowerThanOrEquals(String property, Double multiplier, String property2) {
        return new FilterBuilder(property, Operator.LTE, multiplier, property2);
    }

    public static FilterBuilder greaterThan(String property, Double multiplier, String property2) {
        return new FilterBuilder(property, Operator.GT, multiplier, property2);
    }

    public static FilterBuilder greaterThanOrEquals(String property, Double multiplier, String property2) {
        return new FilterBuilder(property, Operator.GTE, multiplier, property2);
    }

    public static class FilterBuilder {

        private final String property;
        private final Operator operator;
        private final String property2;
        private final Double multiplier;

        FilterBuilder(String property, Operator operator, Double multiplier, String property2) {
            this.property = property;
            this.operator = operator;
            this.multiplier = multiplier;
            this.property2 = property2;
        }

        public CompareCondition build() {
            return new CompareCondition(property, operator, multiplier, property2);
        }
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getProperty2() {
        return property2;
    }

    public Double getMultiplier() {
        return multiplier;
    }
}
