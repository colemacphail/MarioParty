package marioparty.Items;

import DLibX.DConsole;
import marioparty.Console;

public abstract class Item {

    protected DConsole dc = Console.getInstance();

//       TWICE, THRICE, SLOWGO, VAMPIRE, BOWLO, SPRINGO, DUELO, CHARACTER_ITEM,
    public abstract void draw(int x, int y);

    public abstract void triggerEvent();
}
//SO FAR ITS JUST A STRUCTURE
