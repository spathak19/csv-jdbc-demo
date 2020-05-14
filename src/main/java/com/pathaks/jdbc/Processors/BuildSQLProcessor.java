package com.pathaks.jdbc.Processors;

import com.pathaks.jdbc.POJOs.Item;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildSQLProcessor implements Processor {

    @Autowired
    ProducerTemplate producer;

    @Override
    public void process(Exchange exchange) throws Exception {

        Item item = exchange.getIn().getBody(Item.class);
        System.out.println(item);

        String tableName ="items";
        StringBuilder query = new StringBuilder();
        if(item.getTransactionType().equals("ADD")){
            query.append("INSERT INTO "+tableName+ "(itemname,itemprice) VALUES ('");
            query.append(item.getItemName()+"',"+item.getItemPrice()+")");

        }else if(item.getTransactionType().equals("UPDATE")){
            query.append("UPDATE "+tableName+" SET itemprice =");
            query.append(item.getItemPrice()+" where itemid = '"+item.getItemId()+"'");

        }else if(item.getTransactionType().equals("DELETE")){
            query.append("DELETE FROM " + tableName + " where itemid = '"+item.getItemId()+"'");
        }
        System.out.println(query.toString());
        exchange.getIn().setBody(query.toString());
    }

}