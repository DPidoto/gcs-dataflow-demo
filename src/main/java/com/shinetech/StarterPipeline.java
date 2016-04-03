/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.shinetech;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.ParDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * When running the following application with google-cloud-dataflow-java-sdk-all version 1.4.0 the ParDo will 
 * process every row in the csv.  When using google-cloud-dataflow-java-sdk-all version 1.5.0 only the header 
 * row is processed.
 */
@SuppressWarnings("serial")
public class StarterPipeline {
  private static final Logger LOG = LoggerFactory.getLogger(StarterPipeline.class);

  public static void main(String[] args) {
	
	String fileName = "gs://bucket/file.gz";
	
    Pipeline p = Pipeline.create(
        PipelineOptionsFactory.fromArgs(args).withValidation().create());

    p.apply(TextIO.Read.named("Read file from GCS").from(fileName))
    .apply(ParDo.of(new DoFn<String, String>() {
      @Override
      public void processElement(ProcessContext c) {
        c.output(c.element().toUpperCase());
      }
    }))
    .apply(ParDo.of(new DoFn<String, Void>() {
      @Override
      public void processElement(ProcessContext c)  {
        LOG.info(c.element());
      }
    }));

    p.run();
  }
}