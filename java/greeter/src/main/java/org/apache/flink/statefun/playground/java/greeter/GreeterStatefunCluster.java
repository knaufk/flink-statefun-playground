/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.statefun.playground.java.greeter;

import com.google.protobuf.Message;
import com.google.protobuf.MoreByteStrings;
import io.undertow.Undertow;
import org.apache.flink.statefun.flink.harness.Harness;
import org.apache.flink.statefun.flink.harness.io.SerializableSupplier;
import org.apache.flink.statefun.flink.io.generated.AutoRoutable;
import org.apache.flink.statefun.flink.io.generated.RoutingConfig;
import org.apache.flink.statefun.flink.io.generated.TargetFunctionType;
import org.apache.flink.statefun.playground.java.greeter.types.UserLogin;
import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;

import java.nio.charset.StandardCharsets;

import static org.apache.flink.statefun.playground.java.greeter.types.Types.USER_LOGIN_JSON_TYPE;

/**
 * Entry point to start an {@link Undertow} web server that exposes the functions that build up our
 * User Greeter application, {@link UserFn} and {@link GreetingsFn}.
 *
 * <p>Here we are using the {@link Undertow} web server just to show a very simple demonstration.
 * You may choose any web server that can handle HTTP request and responses, for example, Spring,
 * Micronaut, or even deploy your functions on popular FaaS platforms, like AWS Lambda.
 */
public final class GreeterStatefunCluster {

  public static void main(String[] args) throws Exception {

      UserLogin userLogin = new UserLogin();
      userLogin.setUserId("user_1");
      userLogin.setUserName("igal");
      userLogin.setLoginType(UserLogin.LoginType.MOBILE);

      Harness harness = new Harness();
      harness.withSupplyingIngress(new IngressIdentifier<>(Message.class, "greeter.io", "user-logins"), new SerializableSupplier<Message>() {

          private String value;

          @Override
          public Message get() {
              value = "{\"user_id\":\"user_1\"}";
              return AutoRoutable.newBuilder()
                      .setConfig(RoutingConfig.newBuilder().addTargetFunctionTypes(TargetFunctionType.newBuilder().setType("greeter.fns/user").build()))
                      .setId("user_1")
                      .setPayloadBytes(MoreByteStrings.wrap(value.getBytes(StandardCharsets.UTF_8)))
                      .build();
          }
      }
    );

    harness.withPrintingEgress(new EgressIdentifier<>("greeter.io", "user-greetings", TypedValue.class));

    harness.start();
  }
}
