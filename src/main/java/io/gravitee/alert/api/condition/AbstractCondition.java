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

import io.gravitee.alert.api.condition.projection.Projection;

import java.util.Collections;
import java.util.List;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class AbstractCondition implements Condition {

    private final Type type;
    private final List<Projection> projections;

    AbstractCondition(Type type) {
        this(type, Collections.emptyList());
    }

    AbstractCondition(Type type, List<Projection> projections) {
        this.type = type;
        this.projections = projections;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public List<Projection> getProjections() {
        return projections;
    }
}
