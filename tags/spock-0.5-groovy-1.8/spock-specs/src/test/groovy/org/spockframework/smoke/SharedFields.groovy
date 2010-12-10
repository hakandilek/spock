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
import spock.lang.Issue

class SharedFields extends EmbeddedSpecification {
  def "can be accessed from subclass"() {
    when:
    runner.runWithImports """
class Foo extends Specification {
  @Shared x = "abc"
}

class Bar extends Foo {
  def bar() {
    expect:
    x.size() == 3
  }
}
    """

    then:
    noExceptionThrown()
  }

  @Issue("http://issues.spockframework.org/detail?id=112")
  def 'can have name that starts with $'() {
    when:
    runner.runWithImports '''
class Foo extends Specification {
  def $unshared = 0
  @Shared $shared = 0

  def test() {
    expect:
    ++$unshared == 1
    ++$shared == count

    where:
    count << [1,2,3]
  }
}
    '''

    then:
    noExceptionThrown()
  }
}