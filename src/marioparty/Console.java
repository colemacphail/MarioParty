/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty;

import DLibX.DConsole;

/**
 *
 * @author Cole
 */
public class Console {

    private static DConsole instance = null;

    public static DConsole getInstance() {
        if (instance == null) {
            instance = new DConsole();
        }

        return instance;
    }
}
