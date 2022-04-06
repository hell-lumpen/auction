package com.example.auctionback.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyValue {
    private int integerPart;
    private int fractionalPart;

    public MoneyValue(String line) {

        int index = line.indexOf('.');

        if (index == -1) {
            this.integerPart = Integer.parseInt(line);
        }
        else {
            this.integerPart = Integer.parseInt(line.substring(0, index));
            this.fractionalPart = Integer.parseInt(line.substring(index + 1));
        }
    }

    public MoneyValue Add(MoneyValue second_param) {
        return new MoneyValue(this.integerPart + second_param.getIntegerPart(),
                this.fractionalPart + second_param.getFractionalPart());
    }

    public MoneyValue Diff(MoneyValue second_param) {
        return new MoneyValue(this.integerPart - second_param.getIntegerPart(),
                this.fractionalPart - second_param.getFractionalPart());
    }

    public MoneyValue Mult(MoneyValue second_param) {
        return new MoneyValue(this.integerPart * second_param.getIntegerPart(),
                this.fractionalPart * second_param.getFractionalPart());
    }

    public boolean lessThen(MoneyValue second_param) {
        if (this.integerPart < second_param.getIntegerPart()) return true;
        else
            return this.integerPart == second_param.integerPart && this.fractionalPart < second_param.fractionalPart;
    }

    @Override
    public String toString() {
        return Integer.toString(this.integerPart) + "." + Integer.toString(this.fractionalPart);
    }
}
