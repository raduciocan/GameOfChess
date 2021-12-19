package com.hammerchess.gameofchess;

public class GameTileStyles {
    //region BLACK
    public static String defaultBlackTileStyle = "\n" +
            "    -fx-background-color: linear-gradient(#101020, #151540);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;";

    public static String hoverBlackTileStyle = "\n" +
            "    -fx-background-color: linear-gradient(#303050, #505080);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;";

    //selected ( + border)
    public static String defaultBlackTileStyleSelected = "\n" +
            "    -fx-background-color: linear-gradient(#101020, #151540);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;" +
            "    -fx-border-color: yellow;" +
            "    -fx-border-width: 4;" +
            "    -fx-border-insets: -2px;";

    public static String hoverBlackTileStyleSelected = "\n" +
            "    -fx-background-color: linear-gradient(#303050, #505080);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;" +
            "    -fx-border-color: white;" +
            "    -fx-border-width: 4;" +
            "    -fx-border-insets: -2px;";
    //endregion


    //region WHITE
    public static String defaultWhiteTileStyle = "\n" +
            "    -fx-background-color: linear-gradient(#a0a0c0, #d0d0f0);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;";

    public static String hoverWhiteTileStyle = "\n" +
            "    -fx-background-color: linear-gradient(#d0d0e0, #f0f0ff);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;";

    //selected ( + border)
    public static String defaultWhiteTileStyleSelected = "\n" +
            "    -fx-background-color: linear-gradient(#a0a0c0, #d0d0f0);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;" +
            "    -fx-border-color: yellow;" +
            "    -fx-border-width: 4;" +
            "    -fx-border-insets: -2px;";

    public static String hoverWhiteTileStyleSelected = "\n" +
            "    -fx-background-color: linear-gradient(#d0d0e0, #f0f0ff);\n" +
            "    -fx-background-insets: 0;\n" +
            "    -fx-font-size: 16;" +
            "    -fx-border-color: white;" +
            "    -fx-border-width: 4;" +
            "    -fx-border-insets: -2px;";

    //endregion
}
