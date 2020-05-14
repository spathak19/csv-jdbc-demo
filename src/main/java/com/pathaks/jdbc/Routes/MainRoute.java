package com.pathaks.jdbc.Routes;

import com.pathaks.jdbc.POJOs.Item;
import com.pathaks.jdbc.Processors.BuildSQLProcessor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        BindyCsvDataFormat bindy = new BindyCsvDataFormat(Item.class);

        from("file:inbox?moveFailed=.failed")
        .log("Before Split: ${body}")
        .split().tokenize("\n",1,true)
            .log("Before Unmarshal: ${body}")
            .unmarshal(bindy)
            .log("After Unmarshal: ${body}")
            .process(new BuildSQLProcessor())
            .log("Body before sending query is ${body}")
            .to("jdbc:dataSource")
        .end()
        .log("done");
    }


}