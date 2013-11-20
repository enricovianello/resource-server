/*
 * Copyright 2013
 *     tarent AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osiam.resources.helper;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.osiam.storage.entities.InternalIdSkeleton;

public interface FilterChain<T extends InternalIdSkeleton> {
    Predicate createPredicateAndJoin(AbstractQuery<Long> query, Root<T> root);
}
