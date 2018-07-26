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
package io.gravitee.alert.api.service;

import io.gravitee.alert.api.event.Alertable;
import io.gravitee.alert.api.trigger.Trigger;
import io.gravitee.common.service.Service;

import java.util.concurrent.CompletableFuture;

public interface Alert extends Service {

    CompletableFuture<Void> send(Alertable alertable);
    default boolean canHandle(Alertable alertable) {
        return true;
    }

    CompletableFuture<Void> send(Trigger trigger);
    default boolean canHandle(Trigger trigger) {
        return true;
    }
}
