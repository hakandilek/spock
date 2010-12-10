/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spockframework.builder;

import java.lang.reflect.Type;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.MetaClassHelper;

import groovy.lang.MetaMethod;

public class AddSlotFactory implements ISlotFactory {
  public ISlot create(Object owner, Type ownerType, String name) {
    String addName = "add" + MetaClassHelper.capitalize(name);
    MetaMethod addMethod = InvokerHelper.getMetaClass(owner).pickMethod(addName, new Class[] {Object.class});
    return addMethod != null ? new SetterLikeSlot(owner, ownerType, addMethod) : null;
  }
}
