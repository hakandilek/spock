/*
 * Copyright 2009 the original author or authors.
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

package org.spockframework.smoke

import org.spockframework.EmbeddedSpecification
import org.spockframework.runtime.ConditionNotSatisfiedError

class SharedVsStaticFields extends EmbeddedSpecification {
  def "shared fields are not shared between subsequent runs"() {
    setup:
    runner.compiler.compileWithImports """
@Speck
@RunWith(Sputnik)
class SharedField {
  @Shared x = 42

  def "increment"() {
    x++
    expect: x == 43
  }

  def "increment again"() {
    x++
    expect: x == 44
  }
}
    """

    when:
    runner.run """
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import apackage.SharedField

@RunWith(Suite)
@SuiteClasses([SharedField, SharedField])
class SharedFieldSuite {}
    """

    then:
    noExceptionThrown()
  }

  def "static fields are shared between subsequent runs"() {
    setup:
    runner.compiler.compileWithImports """
@Speck
@RunWith(Sputnik)
class StaticField {
  static x = 42

  def "increment"() {
    x++
    expect: x == 43
  }

  def "increment again"() {
    x++
    expect: x == 44
  }
}
    """

    when:
    runner.run """
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import apackage.StaticField

@RunWith(Suite)
@SuiteClasses([StaticField, StaticField])
class StaticFieldSuite {}
    """

    then:
    thrown(ConditionNotSatisfiedError)
  }
}
