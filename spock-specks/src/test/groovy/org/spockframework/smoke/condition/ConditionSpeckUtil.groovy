/*
 * Copyright 2008 the original author or authors.
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

package org.spockframework.smoke.condition

import org.spockframework.runtime.ConditionNotSatisfiedError
import spock.lang.Speck

/**
 * Utility methods for spec'ing conditions.
 *
 * @author Peter Niederwieser
 */

@Speck
abstract class ConditionSpeckUtil {
  static fails(Closure condition) {
    try {
      condition()
      throw new AssertionError("condition should have failed but didn't")
    } catch (ConditionNotSatisfiedError expected) {
      true
    }
  }

  static isRendered(String expectedRendering, Closure condition) {
    try {
      condition()
      throw new AssertionError("condition should have failed but didn't")
    } catch (ConditionNotSatisfiedError e) {
      assert expectedRendering.trim() == e.condition.render().trim()
      true
    }
  }
}