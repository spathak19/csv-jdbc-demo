package com.pathaks.jdbc.POJOs;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",")
public class Item {

    @DataField(pos = 1, trim = true)
    private String transactionType;

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @DataField(pos = 2, required = false, trim = true)
    private String itemId;

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @DataField(pos = 3, trim = true)
    private String itemName;

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @DataField(pos = 4, trim = true)
    private Integer itemPrice;

    public Integer getItemPrice() {
        return this.itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }


    @Override
    public String toString()
    {
        itemId = itemId == "" ? "INSERT" : itemId;
        String str = "Item: [" + transactionType + ", " + itemId + ", " + itemName + ", " + itemPrice.toString() +"]";
        return str;
    }

}