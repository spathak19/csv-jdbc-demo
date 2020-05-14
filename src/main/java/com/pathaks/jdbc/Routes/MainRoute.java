package com.pathaks.jdbc.Routes;

import com.pathaks.jdbc.POJOs.Item;
import com.pathaks.jdbc.Processors.BuildSQLProcessor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
// import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        BindyCsvDataFormat bindy = new BindyCsvDataFormat(Item.class);

        // CsvDataFormat csv = new CsvDataFormat();
        // csv.setAllowMissingColumnNames(true);
        // csv.setSkipHeaderRecord(true);

        from("file:inbox?moveFailed=.failed")
        .setProperty("Original Body", simple("${body}"))
        .split().tokenize("\n",1,true)
            .log("Before Unmarshal: ${body}")
            .unmarshal(bindy)
            .log("After Unmarshal: ${body}")
            .process(new BuildSQLProcessor())
            .log("Body before sending query is ${body}")
            .wireTap("activemq:queue:queries")
            .setBody(simple("select * from items"))
            .to("jdbc:dataSource")
            .log("Body after sending query is ${body}")
            .to("activemq:queue:responses")
        .end()
        .setBody(exchangeProperty("Original Body"))
        .log("after everything: ${body}")
        .log("done");
    }


}